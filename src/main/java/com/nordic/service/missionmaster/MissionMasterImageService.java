package com.nordic.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.nordic.dto.missionmasterbean.MissionMasterImageBean;
import com.nordic.repository.missionmasterrepo.MissionMasterImageImp;
import com.sun.tools.doclint.Entity;
import jdk.internal.loader.Resource;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.json.simple.parser.JSONParser;

import javax.servlet.ServletContext;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Service
public class MissionMasterImageService {

    @Autowired
    private MissionMasterImageImp mmi;

    public byte[] getImgByMissionNo(int mission_no) throws Exception {
        String name = mmi.getImgByMissionNo(mission_no);

        JSONParser jsonParser = new JSONParser();
        JSONObject o = (JSONObject) jsonParser.parse(name);

        JSONArray jsonArray = (JSONArray) o.get("picture");
        JSONObject object =  (JSONObject) jsonArray.get(0);
        String filename = (String) object.get("0");

        String path = "C:\\Users\\hwangjoonsoung\\Desktop\\IntelliJ WorkSpace\\Nordic\\src\\main\\resources\\static\\img\\missionmaster\\";
        String fullFileName = path+filename;
        InputStream in = new FileInputStream(fullFileName);
        byte[] bytes = IOUtils.toByteArray(in);

        return bytes;
    }

 /*   public MultiValueMap<String,Object> getImgByMissionNo(int mission_no) throws Exception {
        String name = mmi.getImgByMissionNo(mission_no);
        MultiValueMap<String,Object> multiValueMap = new LinkedMultiValueMap<String,Object>();
        JSONParser jsonParser = new JSONParser();
        JSONObject o = (JSONObject) jsonParser.parse(name);

        JSONArray jsonArray = (JSONArray) o.get("picture");
        JSONObject object =  (JSONObject) jsonArray.get(0);
        String filename = (String) object.get("0");

        String path = "C:\\Users\\hwangjoonsoung\\Desktop\\IntelliJ WorkSpace\\Nordic\\src\\main\\resources\\static\\img\\";
        String fullFileName = path+filename;
        multiValueMap.add("test", new FileSystemResource(fullFileName));
        multiValueMap.add("test2", "testvalue");

        System.out.println(multiValueMap);
        return multiValueMap;
    }*/

    public void insertImage(MultipartFile[] uploadfiles, String create_member) throws IOException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        String path = "C:\\Users\\hwangjoonsoung\\Desktop\\IntelliJ WorkSpace\\Nordic\\src\\main\\resources\\static\\img\\missionmaster\\";

        int b = 0;
        for (MultipartFile file : uploadfiles) {
            String originName =  file.getOriginalFilename();

            String[] originNameArr = originName.split("\\.");
            String randomString = RandomStringUtils.randomAlphabetic(10);
            String fileFullName  =randomString+"."+originNameArr[1];
            String save = path + fileFullName;



            try (FileOutputStream fos = new FileOutputStream(save);
                 InputStream is = file.getInputStream()
            ) {
                int readCount = 0;
                byte[] buffer = new byte[1024];
                while ((readCount = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, readCount);
                }
            }
            //json으로 변경----------------------------------------------------------
            JSONObject data = new JSONObject();
            data.put(b++, fileFullName);
            jsonArray.add(data);
        }
        jsonObject.put("picture", jsonArray);

        mmi.insertImage(jsonObject.toString(), create_member);
    }

    public void updateImgByMissionNo(MultipartFile[] uploadfiles, String update_member, int mission_no) throws IOException, ParseException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONParser jsonParser = new JSONParser();

        String path = "C:\\Users\\hwangjoonsoung\\Desktop\\IntelliJ WorkSpace\\Nordic\\src\\main\\resources\\static\\img\\missionmaster\\";

        String dbConfirm_file = mmi.getImgByMissionNo(mission_no);
        Object o = jsonParser.parse(dbConfirm_file);

        JSONObject object = (JSONObject) o;
        JSONArray array = (JSONArray) object.get("picture");
        for(int z = 0; z<array.size();z++){
            JSONObject test = (JSONObject) array.get(z);
            String fileName = (String) test.get(Integer.toString(z));
            File file = new File(path);
            File[] files = file.listFiles();
            for(File f : files ){
                if(f.getName().equals(fileName)){
                    f.delete();
                }
            }
        }

        int b = 0;
        for (MultipartFile file : uploadfiles) {

            String originName =  file.getOriginalFilename();

            String[] originNameArr = originName.split("\\.");
            String randomString = RandomStringUtils.randomAlphabetic(10);
            String fileFullName  =randomString+"."+originNameArr[1];
            String save = path + fileFullName;
            try (FileOutputStream fos = new FileOutputStream(save);
                 InputStream is = file.getInputStream()
            ) {
                int readCount = 0;
                byte[] buffer = new byte[1024];
                while ((readCount = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, readCount);
                }
            }
            //json으로 변경----------------------------------------------------------
            JSONObject data = new JSONObject();
            data.put(b++, fileFullName);
            jsonArray.add(data);
        }
        jsonObject.put("picture", jsonArray);

        mmi.updateImgByMissionNo(jsonObject.toString(), update_member, mission_no);
    }

    public void deleteImgByMissionNo(int mission_no) {
        mmi.deleteImgByMissionNo(mission_no);
    }

    public List<MissionMasterImageBean> getImgAll() {
        return mmi.getImgAll();
    }
}
