package com.nordic.repository.member;

import java.util.List;

import org.mapstruct.Mapper;

import com.nordic.dto.common.ExceptionResponseDto;
import com.nordic.dto.common.ResponseDto;
import com.nordic.dto.member.MemberDto;
import com.nordic.dto.member.MemberModifyDto;

@Mapper
public interface MemberMapper {
	
	List<MemberDto> findAll();
	List<MemberDto> findAdmins();
	MemberDto findOne (String member_code);
	int mbrRegister (MemberDto memberDto);
	int modifyOne (MemberModifyDto memberDto);

}
