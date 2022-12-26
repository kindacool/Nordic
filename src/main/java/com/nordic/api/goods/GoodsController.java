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

import com.nordic.service.login.CustomUserDetailsService;
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

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goods")
public class GoodsController {

	private final GoodsService goodsService;
	private final CustomUserDetailsService customeservice;
	
	@ApiOperation(value="포인트 상품 등록", notes = "관리자 권한 / create form 을 통한 상품 등록")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "빈값 허용 불가 / 이미지가 아닌 파일은 업로드 불가능 합니다")
	})
	@PostMapping
    public ResponseDto createGoods(@Valid @RequestPart(value="key") GoodsDto goodsDto, 
    		@RequestPart(value="file", required = false) List<MultipartFile> fileList,
    		//@RequestPart(value="fileOrder", required = false) List<Integer> fileOrder,
    		@ApiIgnore HttpSession session) throws Exception {
		log.info("굿즈 등록 Controller 도착");
		//System.out.println(fileOrder);
		String writer = //"Lee"; // 토큰 구현전까지 일시로
		(String) customeservice.getUserInfo().get("member_code");
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
				String path = //"C:/Users/hwangjoonsoung/Desktop/tttttt/Nordic/src/main/resources/static/img/goods";
						//"C:/file";
						System.getProperty("user.dir") + "/src/main/resources/static/img/goods";
				
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
	
	@ApiOperation(value="포인트 상품 상세정보", notes = "관리자, 이용자 / 포인트 상품 1개 상세정보 보기")
	@ApiImplicitParams({
		@ApiImplicitParam(name="no", value="포인트 상품 번호")
	})
	@ApiResponses({
		@ApiResponse(responseCode = "404", description = "포인트 상품이 없습니다")
	})
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
	@ApiOperation(value="포인트 상품 사진 파일 url 호출", notes="관리자, 이용자 / 포인트 상품 사진 파일 url")
	@ApiImplicitParams({
		@ApiImplicitParam(name="fileName", value="파일명")
	})
	@ApiResponses({
		@ApiResponse(responseCode = "404", description = "이미지 파일이 없습니다")
	})
    @GetMapping(value = "/image/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] fileView(@PathVariable String fileName) throws IOException {
		//log.info("cacheable 실행");

    	String path = //"C:/Users/hwangjoonsoung/Desktop/tttttt/Nordic/src/main/resources/static/img/goods";
				//"C:/file"; // 파일이 저장된 로컬 폴더
    			 System.getProperty("user.dir") + "/src/main/resources/static/img/goods";
        InputStream in = new FileInputStream(path + "/" + fileName);
        System.out.println("파일시스템" + in);
        byte[] temp = IOUtils.toByteArray(in);
        in.close();
        return temp;
    }
    
	@ApiOperation(value="모든 포인트 상품 목록", notes="관리자 / 모든 포인트 상품 보기")
	@ApiImplicitParams({
		@ApiImplicitParam(name="yn", value="모든 포인트 상품 () / 사용가능한 포인트 상품만(y) / 삭제된 포인트 상품만(n)", required = false),
		@ApiImplicitParam(name="pageNum", value="페이지 번호", required = false, defaultValue = "1"),
		@ApiImplicitParam(name="search", value="검색기준", required = false),
		@ApiImplicitParam(name="keyword", value="검색어", required = false)
	})
	@GetMapping(value= {"/all","/all/{yn}"})
	public ResponseDto readAllGoods(@RequestParam(value = "pageNum",
			required = false,
			defaultValue = "1") int pageNum,
			@PathVariable(value="yn",required = false) String yn,
			@RequestParam(value="search", required = false) String search,
			@RequestParam(value="keyword", required = false) String keyword) {
		
		log.info("모든 굿즈 Controller 도착");
		log.info(search + " : "+ keyword);
		
		Map<String, Object> map = new HashMap<>();
		map.put("yn",yn);
		map.put("search",search);
		map.put("keyword",keyword);
		List<GoodsDto> goodsList = goodsService.readAllGoods(pageNum, map);
		
		return new ResponseDto("전체목록", PageInfo.of(goodsList));
	}
	
	@ApiOperation(value="구매 가능한 포인트 상품 목록", notes="이용자 / 구매 가능한 모든 상품 보기")
	@ApiImplicitParams({
		@ApiImplicitParam(name="pageNum", value="페이지 번호", required = false, defaultValue = "1"),
		@ApiImplicitParam(name="keyword", value="검색어", required = false)
	})
	@GetMapping("/avail")
	public ResponseDto readAvailableGoods(
			@RequestParam(value="keyword", required = false) String keyword,
			@RequestParam(value = "pageNum",
			required = false,
			defaultValue = "1") int pageNum) {
		log.info("구매가능 굿즈 Controller 도착");
		
		List<GoodsDto> goodsList = goodsService.readAvailableGoods(pageNum, keyword);
		
		return new ResponseDto("구매가능한 굿즈 목록", PageInfo.of(goodsList));
	}
	
	//@CacheEvict(value="goodsImage", key="image")
	@ApiOperation(value="포인트 상품 삭제", notes="관리자 / 포인트 상품 삭제")
	@ApiImplicitParams({
		@ApiImplicitParam(name="no", value="포인트 상품 번호", dataType="int")
	})
	@ApiResponses({
		@ApiResponse(responseCode = "404", description = "이미 삭제된 상품입니다")
	})
	@DeleteMapping("/{no}")
	public ResponseDto deleteGoods(@PathVariable int no) {
		log.info("삭제 Controller 도착");
		
		GoodsDto old = goodsService.readOneGoods(no);
		
		if(old.getUse_yn() == 'N') {
			throw new GoodsNotFoundException(GoodsNotFoundException.ERR_0010);
		} else {
		
		// 수정자 정보
		String writer = //"Kim"; // 토큰 구현전까지 일시로
		(String) customeservice.getUserInfo().get("member_code");
		
		GoodsDto goodsDto = new GoodsDto();
		goodsDto.setUpdate_member(writer);
		goodsDto.setGoods_no(no);
		
		goodsService.deleteGoods(goodsDto);
		return new ResponseDto("굿즈가 삭제되었습니다.", no);
		}
	}
	
	//@CacheEvict(value="goodsImage", key="image")
	@ApiOperation(value="포인트 상품 수정", notes="관리자 / 포인트 상품 수정")
	@ApiResponses({
		@ApiResponse(responseCode = "404", description = "빈값 허용 불가 / 이미지가 아닌 파일은 업로드 불가능 합니다 / 이미 삭제된 상품입니다")
	})
	@PutMapping("/{no}")
	public ResponseDto updateGoods(@PathVariable int no, 
			@Valid @RequestPart(value="key") GoodsDto goodsDto,
			@RequestPart(value="file", required = false) List<MultipartFile> fileList,
			@RequestPart(value="fileOrder", required = false) List<Integer> fileOrder,
			@ApiIgnore HttpSession session) throws Exception {
		log.info("수정 Controller 도착");
		
		GoodsDto old = goodsService.readOneGoods(no);
		if(old.getUse_yn() == 'N') {
			throw new GoodsNotFoundException(GoodsNotFoundException.ERR_0010);
		}
		
		// 수정자 정보
		String writer = //"Kim"; // 토큰 구현전까지 일시로
		(String) customeservice.getUserInfo().get("member_code");
		goodsDto.setUpdate_member(writer);
		goodsDto.setGoods_no(no);
		
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
				String path = //"C:/Users/hwangjoonsoung/Desktop/tttttt/Nordic/src/main/resources/static/img/goods";
						//"C:/file";
						System.getProperty("user.dir") + "/src/main/resources/static/img/goods";
						//System.getProperty("user.dir") + "/src/main/resources/static/img/goods";
						//session.getServletContext().getRealPath("resources/static/img/goods");
				System.out.println("path : " + path);
						
				FileOutputStream fos = new FileOutputStream(path + "/" + newfilename);
				fos.write((fileList.get(i)).getBytes());
				fos.close();
						
				if(fileOrder.get(i) == 0) {
					if(old.getImage1() != null) {
						// 기존 파일 삭제하기
						String imagePath = //"C:/Users/hwangjoonsoung/Desktop/tttttt/Nordic/src/main/resources/static/img/goods";
								//"C:/file";
								System.getProperty("user.dir") + "/src/main/resources/static/img/goods";
								
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
						String imagePath = //"C:/Users/hwangjoonsoung/Desktop/tttttt/Nordic/src/main/resources/static/img/goods";
								//"C:/file";
								System.getProperty("user.dir") + "/src/main/resources/static/img/goods";
						
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
						String imagePath = //"C:/Users/hwangjoonsoung/Desktop/tttttt/Nordic/src/main/resources/static/img/goods";
								//"C:/file";
								System.getProperty("user.dir") + "/src/main/resources/static/img/goods";
						
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
						String imagePath = //"C:/Users/hwangjoonsoung/Desktop/tttttt/Nordic/src/main/resources/static/img/goods";
								//"C:/file";
								System.getProperty("user.dir") + "/src/main/resources/static/img/goods";
						
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
						String imagePath = //"C:/Users/hwangjoonsoung/Desktop/tttttt/Nordic/src/main/resources/static/img/goods";
								//"C:/file";
								System.getProperty("user.dir") + "/src/main/resources/static/img/goods";
						
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
	@ApiOperation(value="가장 많이 지급된 포인트 상품", notes = "관리자 / 가장 많이 지급된 포인트 상품 보기")
	@ApiImplicitParams({
		@ApiImplicitParam(name="pageNum", value="페이지 번호", required = false, defaultValue = "1")
	})
	@GetMapping("/best")
	public ResponseDto getBestSellingGoods(@RequestParam(value = "pageNum",
			required = false,
			defaultValue = "1") int pageNum) throws IOException {
		List<BestSellingGoodsDto> bsGoodsList = goodsService.getBestSellingGoods(pageNum);
		return new ResponseDto("가장 많이 팔린 굿즈",PageInfo.of(bsGoodsList));
	}

}