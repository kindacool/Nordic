package com.nordic.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nordic.dto.common.ExceptionResponseDto;
import com.nordic.dto.common.ResponseDto;
import com.nordic.dto.member.MemberDto;
import com.nordic.dto.member.MemberModifyDto;
import com.nordic.repository.member.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
	
	private final MemberRepository memberRepository;
	
	public PageInfo<MemberDto> findAll(int pageNum) {
		PageHelper.startPage(pageNum, 10);
		List<MemberDto> memberList = memberRepository.findAll();
		return PageInfo.of(memberList);
	}
	
	public PageInfo<MemberDto> totalPointAsc (int pageNum) {
		PageHelper.startPage(pageNum, 10);
		List<MemberDto> memberList = memberRepository.totalPointAsc();
		return PageInfo.of(memberList);
	}
	
	public PageInfo<MemberDto> totalPointDesc (int pageNum) {
		PageHelper.startPage(pageNum, 10);
		List<MemberDto> memberList = memberRepository.totalPointDesc();
		return PageInfo.of(memberList);
	}
	
	public PageInfo<MemberDto> reqPointAsc (int pageNum) {
		PageHelper.startPage(pageNum, 10);
		List<MemberDto> memberList = memberRepository.reqPointAsc();
		return PageInfo.of(memberList);
	}
	
	public PageInfo<MemberDto> reqPointDesc (int pageNum) {
		PageHelper.startPage(pageNum, 10);
		List<MemberDto> memberList = memberRepository.reqPointDesc();
		return PageInfo.of(memberList);
	}
	
	public PageInfo<MemberDto> usePointAsc (int pageNum) {
		PageHelper.startPage(pageNum, 10);
		List<MemberDto> memberList = memberRepository.usePointAsc();
		return PageInfo.of(memberList);
	}
	
	public PageInfo<MemberDto> usePointDesc (int pageNum) {
		PageHelper.startPage(pageNum, 10);
		List<MemberDto> memberList = memberRepository.usePointDesc();
		return PageInfo.of(memberList);
	}
	
	public PageInfo<MemberDto> approvalYList(int pageNum) {
		log.info("서비스");
		PageHelper.startPage(pageNum, 10);
		List<MemberDto> memberList = memberRepository.approvalYList();
		return PageInfo.of(memberList);
	}
	
	public PageInfo<MemberDto> approvalNList(int pageNum) {
		PageHelper.startPage(pageNum, 10);
		List<MemberDto> memberList = memberRepository.approvalNList();
		return PageInfo.of(memberList);
	}
	
	public PageInfo<MemberDto> findAdmins(int pageNum) {
		PageHelper.startPage(pageNum, 10);
		List<MemberDto> adminList = memberRepository.findAdmins();
		return PageInfo.of(adminList);
	}
	
	public MemberDto findOne (String member_code) {
		MemberDto memberDto = memberRepository.findOne(member_code);
		return memberDto;
	}

	public int mbrRegister (MemberDto memberDto) {
		return memberRepository.mbrRegister(memberDto);
	}
	
	public int admRegister (MemberDto memberDto) {
		return memberRepository.admRegister(memberDto);
	}
	
	public int modifyOne (MemberModifyDto memberModifyDto) {
		return memberRepository.modifyOne(memberModifyDto);
	}
	
	public int delOne (MemberDto memberDto) {
		return memberRepository.delOne(memberDto);
	}

}
