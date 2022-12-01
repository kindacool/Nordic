package com.nordic.dto.member;

import java.util.Date;

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
   
   private String member_code;      // 1. 회원코드
    private String member_name;      // 2. 회원명
    private String mobile_no;      // 3. 핸드폰
    private String address;         // 4. 주소
    private int age;            // 5. 나이
    private String sex;            // 6. 성별
    private String agree_yn;      // 7. 동의여부
    private String password;      // 8. 비밀번호
    private String approval_yn;      // 9. 승인여부
    private String approval_date;   // 10. 승인일시
    private String stop_yn;         // 11. 중지여부
    private String stop_date;      // 12. 중지일시
    private String admin_yn;      // 13. 관리자여부
    private String admin_date;      // 14. 관리자등록일
    private int total_point;      // 15. 총포인트
    private int req_point;         // 16. 상품신청포인트
    private int use_point;         // 17. 사용한포인트
    private String remark;         // 18. 비고
    private String create_member;   // 19. 등록자
    private String create_date;      // 20. 등록일시
    private String update_member;   // 21. 변경자
    private String update_date;      // 22. 변경일시

   
}