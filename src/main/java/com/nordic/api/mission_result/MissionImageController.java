package com.nordic.api.mission_result;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mission")
public class MissionImageController {
	
	/* 이미지 불러오기 */
	@ApiOperation(value = "이미지 불러오기")
	@GetMapping( value="/image/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody byte[] ImageView(@PathVariable String fileName, @RequestParam String path) throws Exception {
		log.info("=== fileName = "+fileName+" ===");
		String res = System.getProperty("user.dir") + "/src/main/resources/static/img/"+path+"/"+fileName;
		System.out.println(res);
		InputStream in = new FileInputStream(res);
		return IOUtils.toByteArray(in);
	}
}
