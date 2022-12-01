package com.nordic.api.goods;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nordic.dto.common.ResponseDto;
import com.nordic.dto.goods.GoodsDto;
import com.nordic.service.goods.GoodsService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goods")
public class GoodsController {

	private final GoodsService goodsService;
	
	@ApiOperation(value="굿즈 등록")
	@PostMapping
    public ResponseDto createGoods(@RequestPart(value="key") GoodsDto goodsDto, 
    		@RequestPart(value="file", required = false) List<MultipartFile> fileList,
    		HttpSession session) throws Exception {
		System.out.println("controller 도착");
		System.out.println(goodsDto);
		System.out.println(fileList);
		
		String writer = "Lee"; // 토큰 구현전까지 일시로
		goodsDto.setCreate_member(writer);
		goodsDto.setUpdate_member(writer);
		
		if(!fileList.isEmpty()) {
			for(int i = 0 ; i < fileList.size() ; i ++) {
				String fileName = (fileList.get(i)).getOriginalFilename();
				int fileSize = (int) (fileList.get(i)).getSize(); // 단위 : Byte
				System.out.println(fileName);
				
				String file[] = new String[2];
				String newfilename = "";
				
				//파일 중복문제 해결
				String extension = fileName.substring(fileName.lastIndexOf("."), fileName.length());
				System.out.println("extension: " + extension);
						
				UUID uuid = UUID.randomUUID();
						
				newfilename = uuid.toString() + extension;
				System.out.println("newfilename; " + newfilename);
				
				// 첨부파일 업로드
				//String path = "C:/file";
				String path = System.getProperty("user.dir") + "/src/main/resources/static/img/goods";
						//session.getServletContext().getRealPath("resources/static/img/goods");
				System.out.println("path : " + path);
						
				FileOutputStream fos = new FileOutputStream(path + "/" + newfilename);
				fos.write((fileList.get(i)).getBytes());
				fos.close();
				
				switch (i) {
		        case 0:         // 1 인 경우
		        	goodsDto.setImage1(newfilename);
		        	System.out.println(goodsDto.getImage1());
		            break;
		        case 1:            // 2 인 경우
		        	goodsDto.setImage2(newfilename);
		        	System.out.println(goodsDto.getImage2());
		            break;
		        case 2:            // 3 인 경우
		        	goodsDto.setImage3(newfilename);
		        	System.out.println(goodsDto.getImage3());
		            break;
		        case 3:            // 4 인 경우
		        	goodsDto.setImage4(newfilename);
		        	System.out.println(goodsDto.getImage3());
		            break;
		        case 4:            // 5 인 경우
		        	goodsDto.setImage5(newfilename);
		        	System.out.println(goodsDto.getImage3());
		            break;
		        default:        // 모두 해당이 안되는 경우
		            System.out.println("기타");
		            break;    
		        }
			}
		}

		goodsService.createGoods(goodsDto);	
		
        return new ResponseDto("굿즈가 등록되었습니다.", goodsDto);
    }
	
	@GetMapping("/{no}")
	public ResponseDto readOneGoods(@PathVariable int no) throws IOException {
	
		System.out.println("controller 도착");
		System.out.println(no);
		
		GoodsDto goodsDto = goodsService.readOneGoods(no);
		String path = System.getProperty("user.dir") + "/src/main/resources/static/img/goods/";
		InputStream imageStream = new FileInputStream(
				path + goodsDto.getImage1());
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int read;
        byte[] imageByteArray = new byte[imageStream.available()];
        while ((read = imageStream.read(imageByteArray, 0, imageByteArray.length)) != -1) {
            buffer.write(imageByteArray, 0, read);
        }
        buffer.flush();
        byte[] targetArray = buffer.toByteArray();
        imageStream.close();
        
        goodsDto.setByte_image(targetArray);
		
		return new ResponseDto("상세정보", goodsDto);
	}
	
	@GetMapping
	public ResponseDto readAllGoods() {
	
		System.out.println("controller 도착");
		
		List<GoodsDto> goodsList = goodsService.readAllGoods();
		
		return new ResponseDto("전체목록", goodsList);
	}
	
	@GetMapping("/avail")
	public ResponseDto readAvailableGoods() {
	
		System.out.println("controller 도착");
		
		List<GoodsDto> goodsList = goodsService.readAvailableGoods();
		
		return new ResponseDto("구매가능한 굿즈 목록", goodsList);
	}
	
	@DeleteMapping("/{no}")
	public ResponseDto deleteGoods(@PathVariable int no) {
		System.out.println("controller 도착");
		System.out.println(no);
		
		goodsService.deleteGoods(no);
		return new ResponseDto("굿즈가 삭제되었습니다.", no);
	}
	
	@PutMapping("/{no}")
	public ResponseDto updateGoods(@PathVariable int no, @RequestBody GoodsDto goodsDto) throws IOException {
		System.out.println("controller 도착");
		System.out.println(no);
		
		// 수정자 정보
		String writer = "Kim"; // 토큰 구현전까지 일시로
		goodsDto.setUpdate_member(writer);
		
		goodsDto.setGoods_no(no);
		// 이미지를 변경하지 않을때
		GoodsDto old = (GoodsDto)((this.readOneGoods(no)).getData());
		goodsDto.setImage1(old.getImage1());
		goodsDto.setImage2(old.getImage2());
		goodsDto.setImage3(old.getImage3());
		goodsDto.setImage4(old.getImage4());
		goodsDto.setImage5(old.getImage5());
		
		
		goodsService.updateGoods(goodsDto);
		return new ResponseDto("굿즈가 수정되었습니다.", no);
	}

}