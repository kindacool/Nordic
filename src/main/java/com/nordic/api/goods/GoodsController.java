package com.nordic.api.goods;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.nordic.dto.common.ResponseDto;
import com.nordic.dto.goods.BestSellingGoodsDto;
import com.nordic.dto.goods.GoodsDto;
import com.nordic.exception.GoodsNotFoundException;
import com.nordic.exception.ImageInvalidFormatException;
import com.nordic.service.goods.GoodsService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goods")
public class GoodsController {

	private final GoodsService goodsService;
	
	@ApiOperation(value="굿즈 등록")
	@PostMapping
    public ResponseDto createGoods(@Valid @RequestPart(value="key") GoodsDto goodsDto, 
    		@RequestPart(value="file", required = false) List<MultipartFile> fileList,
    		//@RequestPart(value="fileOrder", required = false) List<Integer> fileOrder,
    		HttpSession session) throws Exception {
		log.info("굿즈 등록 Controller 도착");
		//System.out.println(fileOrder);
		String writer = "Lee"; // 토큰 구현전까지 일시로
		goodsDto.setCreate_member(writer);
		
		if(fileList != null) {
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
				
				StringTokenizer st = new StringTokenizer(fileName, ".");
				file[0] = st.nextToken(); // 파일명
				file[1] = st.nextToken(); // 확장자

				if (fileSize > 1000000) { // 1MB
					throw new ImageInvalidFormatException(ImageInvalidFormatException.ERR_0007);

				} else if (!(file[1].toLowerCase()).equals("jpg") && 
						!(file[1].toLowerCase()).equals("gif") && 
						!(file[1].toLowerCase()).equals("png") &&
						!(file[1].toLowerCase()).equals("jfif")) {
					throw new ImageInvalidFormatException(ImageInvalidFormatException.ERR_0006);
				}
				
				// 첨부파일 업로드
				//String path = 
				String path = "C:/file";
						//System.getProperty("user.dir") + "/src/main/resources/static/img/goods";
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
		        	System.out.println(goodsDto.getImage4());
		            break;
		        case 4:            // 5 인 경우
		        	goodsDto.setImage5(newfilename);
		        	System.out.println(goodsDto.getImage5());
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
	
	@ApiOperation(value="굿즈 상세정보")
	@GetMapping("/{no}")
	public ResponseDto readOneGoods(@PathVariable int no) throws Exception {
	
		log.info("하나의 굿즈 상세정보 Controller 도착");
		GoodsDto goodsDto = goodsService.readOneGoods(no);
		
		if(goodsDto == null) {
			throw new GoodsNotFoundException(GoodsNotFoundException.ERR_0002);
		} else {
			return new ResponseDto("상세정보", goodsDto);
		}

	}
	
    //파일 url 호출
	//@Cacheable(value="goodsImage", key="image")
	@ApiOperation(value="굿즈 사진 파일 url 호출")
    @GetMapping(value = "/image/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] fileView(@PathVariable String fileName) throws IOException {
		//log.info("cacheable 실행");

    	String path = "C:/file"; // 파일이 저장된 로컬 폴더
        InputStream in = new FileInputStream(path + "/" + fileName);
        System.out.println("파일시스템" + in);
        byte[] temp = IOUtils.toByteArray(in);
        in.close();
        return temp;
    }
    
	@ApiOperation(value="모든 굿즈(삭제된 굿즈 포함) 목록")
	@GetMapping("/all")
	public ResponseDto readAllGoods(@RequestParam(value = "pageNum",
			required = false,
			defaultValue = "1") int pageNum, 
			@RequestParam(value="keyword", required = false) String keyword) {
	
		log.info("모든 굿즈 Controller 도착");

		List<GoodsDto> goodsList = goodsService.readAllGoods(pageNum, keyword);
		
		return new ResponseDto("전체목록", PageInfo.of(goodsList));
	}
	
	@ApiOperation(value="구매가능상태 굿즈 목록")
	@GetMapping("/avail")
	public ResponseDto readAvailableGoods(@RequestParam(value = "pageNum",
			required = false,
			defaultValue = "1") int pageNum) {
		log.info("구매가능 굿즈 Controller 도착");
		
		List<GoodsDto> goodsList = goodsService.readAvailableGoods(pageNum);
		
		return new ResponseDto("구매가능한 굿즈 목록", PageInfo.of(goodsList));
	}
	
	//@CacheEvict(value="goodsImage", key="image")
	@ApiOperation(value="굿즈 삭제")
	@DeleteMapping("/{no}")
	public ResponseDto deleteGoods(@PathVariable int no) {
		log.info("삭제 Controller 도착");
		
		goodsService.deleteGoods(no);
		return new ResponseDto("굿즈가 삭제되었습니다.", no);
	}
	
	//@CacheEvict(value="goodsImage", key="image")
	@ApiOperation(value="굿즈 수정")
	@PutMapping("/{no}")
	public ResponseDto updateGoods(@PathVariable int no, 
			@Valid @RequestPart(value="key") GoodsDto goodsDto,
			@RequestPart(value="file", required = false) List<MultipartFile> fileList,
			@RequestPart(value="fileOrder", required = false) List<Integer> fileOrder,
    		HttpSession session) throws Exception {
		log.info("수정 Controller 도착");
		System.out.println(no);
		System.out.println(fileOrder);
		
		// 수정자 정보
		String writer = "Kim"; // 토큰 구현전까지 일시로
		goodsDto.setUpdate_member(writer);
		goodsDto.setGoods_no(no);
		
		GoodsDto old = goodsService.readOneGoods(no);
		// 먼저 기존에 있던 걸 그대로 넣고
		goodsDto.setImage1(old.getImage1());
		goodsDto.setImage2(old.getImage2());
		goodsDto.setImage3(old.getImage3());
		goodsDto.setImage4(old.getImage4());
		goodsDto.setImage5(old.getImage5());

		if(fileList != null) {
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
				
				StringTokenizer st = new StringTokenizer(fileName, ".");
				file[0] = st.nextToken(); // 파일명
				file[1] = st.nextToken(); // 확장자

				if (fileSize > 1000000) { // 1MB
					throw new ImageInvalidFormatException(ImageInvalidFormatException.ERR_0007);

				} else if (!(file[1].toLowerCase()).equals("jpg") && 
						!(file[1].toLowerCase()).equals("gif") && 
						!(file[1].toLowerCase()).equals("png") &&
						!(file[1].toLowerCase()).equals("jfif")) {
					throw new ImageInvalidFormatException(ImageInvalidFormatException.ERR_0006);
				}
				
				// 첨부파일 업로드
				//String path = 
				String path = "C:/file";
						//System.getProperty("user.dir") + "/src/main/resources/static/img/goods";
						//session.getServletContext().getRealPath("resources/static/img/goods");
				System.out.println("path : " + path);
						
				FileOutputStream fos = new FileOutputStream(path + "/" + newfilename);
				fos.write((fileList.get(i)).getBytes());
				fos.close();
						
				if(fileOrder.get(i) == 0) {
					if(old.getImage1() != null) {
						// 기존 파일 삭제하기
						String imagePath = "C:/file";
								
						//현재 게시판에 존재하는 파일객체를 만듬
						File imageFile = new File(imagePath + "/" + old.getImage1());
						System.out.println(path + "/" + old.getImage1());
						if(imageFile.exists()) { // 파일이 존재하면
							log.info("1번 파일존재");
							imageFile.delete();
						}
					}
					goodsDto.setImage1(newfilename);
					System.out.println("도착");
				} else if(fileOrder.get(i) == 1) {
					goodsDto.setImage2(newfilename);
					if(old.getImage2() != null) {
						// 기존 파일 삭제하기
						String imagePath = "C:/file";
								
						//현재 게시판에 존재하는 파일객체를 만듬
						File imageFile = new File(imagePath + "/" + old.getImage2());
						System.out.println(path + "/" + old.getImage2());
						if(imageFile.exists()) { // 파일이 존재하면
							log.info("2번 파일존재");
							imageFile.delete();
						}
					}
				} else if(fileOrder.get(i) == 2) {
					goodsDto.setImage3(newfilename);
					if(old.getImage3() != null) {
						// 기존 파일 삭제하기
						String imagePath = "C:/file";
								
						//현재 게시판에 존재하는 파일객체를 만듬
						File imageFile = new File(imagePath + "/" + old.getImage3());
						System.out.println(path + "/" + old.getImage3());
						if(imageFile.exists()) { // 파일이 존재하면
							log.info("3번 파일존재");
							imageFile.delete();
						}
					}
				} else if(fileOrder.get(i) == 3) {
					goodsDto.setImage4(newfilename);
					if(old.getImage4() != null) {
						// 기존 파일 삭제하기
						String imagePath = "C:/file";
								
						//현재 게시판에 존재하는 파일객체를 만듬
						File imageFile = new File(imagePath + "/" + old.getImage4());
						System.out.println(path + "/" + old.getImage4());
						if(imageFile.exists()) { // 파일이 존재하면
							log.info("4번 파일존재");
							imageFile.delete();
						}
					}
				} else if(fileOrder.get(i) == 4) {
					goodsDto.setImage5(newfilename);
					if(old.getImage5() != null) {
						// 기존 파일 삭제하기
						String imagePath = "C:/file";
								
						//현재 게시판에 존재하는 파일객체를 만듬
						File imageFile = new File(imagePath + "/" + old.getImage5());
						System.out.println(path + "/" + old.getImage5());
						if(imageFile.exists()) { // 파일이 존재하면
							log.info("5번 파일존재");
							imageFile.delete();
						}
					}
				}
			}
		}
		
		goodsService.updateGoods(goodsDto);
		return new ResponseDto("굿즈가 수정되었습니다.", no);
	}
	
	// Best Selling Goods 
	@ApiOperation(value="가장 많이 팔린 굿즈")
	@GetMapping("/best")
	public ResponseDto getBestSellingGoods(@RequestParam(value = "pageNum",
			required = false,
			defaultValue = "1") int pageNum) throws IOException {
		List<BestSellingGoodsDto> bsGoodsList = goodsService.getBestSellingGoods(pageNum);
		return new ResponseDto("가장 많이 팔린 굿즈",PageInfo.of(bsGoodsList));
	}

}