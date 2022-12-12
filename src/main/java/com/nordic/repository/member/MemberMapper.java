package com.nordic.repository.member;

import java.lang.reflect.Member;
import java.util.List;

import com.nordic.dto.common.ExceptionResponseDto;
import com.nordic.dto.common.ResponseDto;
import com.nordic.dto.member.MemberDto;
import com.nordic.dto.member.MemberModifyDto;
import com.nordic.dto.member.SearchDto;

import org.apache.ibatis.annotations.Mapper;

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
	List<MemberDto> delMemList();
	int doApproval (MemberDto memberDto);
	int doAdmin (MemberDto memberDto);
	int doUnadmin (MemberDto memberDto);
	List<MemberDto> findAdmins();
	MemberDto findOne (String member_code);
	int mbrRegister (MemberDto memberDto);
	int modifyOne (MemberModifyDto memberModifyDto);
	int delOne (MemberDto memberDto);
	int undoDelete (MemberDto memberDto);
	List<MemberDto> doSearch (SearchDto searchDto);

}
