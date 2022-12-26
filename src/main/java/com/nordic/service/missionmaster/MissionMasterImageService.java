package com.nordic.service.missionmaster;

import com.nordic.dto.missionmasterbean.MissionMasterImageBean;
import com.nordic.repository.missionmasterrepo.MissionMasterImageImp;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Arrays;
import java.util.List;

@Service
public class MissionMasterImageService {

    @Autowired
    private MissionMasterImageImp mmi;
    
    //사진 수량 산출
    public int countImage(int mission_no) throws ParseException {
        String name = mmi.getImgByMissionNo(mission_no);

        JSONParser jsonParser = new JSONParser();
        //String to json
        JSONObject o = (JSONObject) jsonParser.parse(name);

        //getting picture's value and convert jsonarray
        JSONArray jsonArray = (JSONArray) o.get("picture");
        int count = jsonArray.size();
        return count;

    }

    //사진 1개 출력 (detail에서 사용)
    public byte[] getImgByMissionNoCount(int mission_no , int count) throws Exception {
        String name = mmi.getImgByMissionNo(mission_no);

        JSONParser jsonParser = new JSONParser();
        //String to json
        JSONObject o = (JSONObject) jsonParser.parse(name);

        //getting picture's value and convert jsonarray
        JSONArray jsonArray = (JSONArray) o.get("picture");
        //getting index 0 and convert jsonobject
        JSONObject object = (JSONObject) jsonArray.get(count);
        //getting 0's value
        String filename = (String) object.get(Integer.toString(count));

        String path1 = System.getProperty("user.dir");
        String path = path1+"\\src\\main\\resources\\static\\img\\missionmaster\\";
        String fullFileName = path + filename;
        InputStream in = new FileInputStream(fullFileName);
        byte[] bytes = IOUtils.toByteArray(in);

        return bytes;
    }

    //사진 1개 출력 (list에서 사용)
    public byte[] getImgByMissionNo(int mission_no) throws Exception {
        String name = mmi.getImgByMissionNo(mission_no);


        JSONParser jsonParser = new JSONParser();
        //String to json
        JSONObject o = (JSONObject) jsonParser.parse(name);

        //getting picture's value and convert jsonarray
        JSONArray jsonArray = (JSONArray) o.get("picture");
        //getting index 0 and convert jsonobject
        JSONObject object = (JSONObject) jsonArray.get(0);
        //getting 0's value
        String filename = (String) object.get("0");

        String path1 = System.getProperty("user.dir");
        String path = path1+"\\src\\main\\resources\\static\\img\\missionmaster\\";
        String fullFileName = path + filename;

        InputStream in = new FileInputStream(fullFileName);
        byte[] bytes = IOUtils.toByteArray(in);

        return bytes;
    }

    //image insert
    public void insertImage(MultipartFile[] uploadfiles, String create_member) throws IOException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        String path1 = System.getProperty("user.dir");
        String path = path1+"\\src\\main\\resources\\static\\img\\missionmaster\\";

        int b = 0;
        for (MultipartFile file : uploadfiles) {
            String originName = file.getOriginalFilename();

            String[] originNameArr = originName.split("\\.");
            String randomString = RandomStringUtils.randomAlphabetic(10);
            String fileFullName = randomString + "." + originNameArr[1];
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

    //image update
    public void updateImgByMissionNo(MultipartFile[] uploadfiles, String update_member, int mission_no) throws IOException, ParseException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONParser jsonParser = new JSONParser();
        String path1 = System.getProperty("user.dir");
        String path = path1+"\\src\\main\\resources\\static\\img\\missionmaster\\";
        String dbConfirm_file = mmi.getImgByMissionNo(mission_no);
        Object o = jsonParser.parse(dbConfirm_file);

        JSONObject object = (JSONObject) o;
        JSONArray array = (JSONArray) object.get("picture");
        for (int z = 0; z < array.size(); z++) {
            JSONObject test = (JSONObject) array.get(z);
            String fileName = (String) test.get(Integer.toString(z));
            File file = new File(path);
            File[] files = file.listFiles();
            for (File f : files) {
                if (f.getName().equals(fileName)) {
                    f.delete();
                }
            }
        }

        int b = 0;
        for (MultipartFile file : uploadfiles) {

            String originName = file.getOriginalFilename();

            String[] originNameArr = originName.split("\\.");
            String randomString = RandomStringUtils.randomAlphabetic(10);
            String fileFullName = randomString + "." + originNameArr[1];
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

    //image delete
    public void deleteImgByMissionNo(int mission_no) {
        mmi.deleteImgByMissionNo(mission_no);
    }

    //image all
    public List<MissionMasterImageBean> getImgAll() {
        return mmi.getImgAll();
    }


}
