package com.nordic.dto.member;

import org.apache.ibatis.type.Alias;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
//@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("MemberDto")
@Alias("MemberModifyDto")
public class MemberModifyDto {
	
	private String member_code;		// 1. 회원코드
    private String member_name;		// 2. 회원명
    private String mobile_no;		// 3. 핸드폰
    private String address;			// 4. 주소
    private int age;				// 5. 나이
    private String sex;				// 6. 성별
    private String password;		// 7. 비밀번호
    private String update_member;	// 8. 변경자
    private String update_date;		// 9. 변경일시
    
}
