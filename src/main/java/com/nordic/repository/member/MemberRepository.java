package com.nordic.repository.member;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nordic.dto.common.ExceptionResponseDto;
import com.nordic.dto.common.ResponseDto;
import com.nordic.dto.member.MemberDto;
import com.nordic.dto.member.MemberModifyDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberRepository implements MemberMapper {
	
	private final MemberMapper memberMapper;
	
	@Override
	public List<MemberDto> findAll() {
		return memberMapper.findAll();
	}
	
	@Override
	public List<MemberDto> totalPointAsc() {
		return memberMapper.totalPointAsc();
	}
	
	@Override
	public List<MemberDto> totalPointDesc() {
		return memberMapper.totalPointDesc();
	}
	
	@Override
	public List<MemberDto> reqPointAsc() {
		return memberMapper.reqPointAsc();
	}
	
	@Override
	public List<MemberDto> reqPointDesc() {
		return memberMapper.reqPointDesc();
	}
	
	@Override
	public List<MemberDto> usePointAsc() {
		return memberMapper.usePointAsc();
	}
	
	@Override
	public List<MemberDto> usePointDesc() {
		return memberMapper.usePointDesc();
	}
	
	@Override
	public List<MemberDto> approvalYList() {
		log.info("레파지토리");
		return memberMapper.approvalYList();
	}
	
	@Override
	public List<MemberDto> approvalNList() {
		return memberMapper.approvalNList();
	}
	
	@Override
	public List<MemberDto> findAdmins() {
		return memberMapper.findAdmins();
	}
	
	@Override
	public MemberDto findOne (String member_code) {
		return memberMapper.findOne(member_code);
	}
	
	@Override
	public int mbrRegister (MemberDto memberDto) {
		return memberMapper.mbrRegister(memberDto);
	}
	
	@Override
	public int admRegister (MemberDto memberDto) {
		return memberMapper.admRegister(memberDto);
	}
	
	@Override
	public int modifyOne (MemberModifyDto memberModifyDto) {
		return memberMapper.modifyOne(memberModifyDto);
	}
	
	@Override
	public int delOne (MemberDto memberDto) {
		return memberMapper.delOne(memberDto);
	}

}
