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
	List<MemberDto> totalPointAsc();
	List<MemberDto> totalPointDesc();
	List<MemberDto> reqPointAsc();
	List<MemberDto> reqPointDesc();
	List<MemberDto> usePointAsc();
	List<MemberDto> usePointDesc();
	List<MemberDto> approvalYList();
	List<MemberDto> approvalNList();
	List<MemberDto> findAdmins();
	MemberDto findOne (String member_code);
	int mbrRegister (MemberDto memberDto);
	int admRegister (MemberDto memberDto);
	int modifyOne (MemberModifyDto memberModifyDto);
	int delOne (MemberDto memberDto);

}
