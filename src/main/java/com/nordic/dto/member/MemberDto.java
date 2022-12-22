package com.nordic.dto.member;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("MemberDto")
@Alias("MemberDto")
public class MemberDto {
	
//	@NotBlank (message = "아이디를 입력해주세요")
//	@Max(value = 12)
	private String member_code;		// 1. 회원코드
	
//	@NotBlank (message = "이름을 입력해주세요")
    private String member_name;		// 2. 회원명
	
//	@NotBlank (message = "휴대폰 번호를 입력해주세요")
	@Size(min=11, max=11)
    private String mobile_no;		// 3. 핸드폰
	
//	@NotBlank (message = "주소를 입력해주세요")
    private String address;			// 4. 주소
	
//	@Positive	// 양수만 허용
//	@NotNull (message = "나이를 입력해주세요")
    private String age;				// 5. 나이
	
//	@NotBlank (message = "성별을 입력해주세요")
    private String sex;				// 6. 성별
	
    private String agree_yn;		// 7. 동의여부
    
//    @Pattern (regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호를 4 ~ 10자 내외로 입력해주세요")
//    @NotBlank (message = "비밀번호를 입력해주세요")
    private String password;		// 8. 비밀번호
    
    private String approval_yn;		// 9. 승인여부
    
//    @JsonFormat(pattern = "yyyy-MM-dd")
    private String approval_date;	// 10. 승인일시
    
    private String stop_yn;			// 11. 중지여부
    
//    @JsonFormat(pattern = "yyyy-MM-dd")
    private String stop_date;		// 12. 중지일시
    
    private String admin_yn;		// 13. 관리자여부
    
//    @JsonFormat(pattern = "yyyy-MM-dd")
    private String admin_date;		// 14. 관리자등록일
    
    private int total_point;		// 15. 총포인트
    
    private int req_point;			// 16. 상품신청포인트
    
    private int use_point;			// 17. 사용한포인트
    
    private String remark;			// 18. 비고
    
    private String create_member;	// 19. 등록자
    
//    @JsonFormat(pattern = "yyyy-MM-dd")
    private String create_date;		// 20. 등록일시
    
    private String update_member;	// 21. 변경자
    
//    @JsonFormat(pattern = "yyyy-MM-dd")
    private String update_date;		// 22. 변경일시
    
    

	
}
