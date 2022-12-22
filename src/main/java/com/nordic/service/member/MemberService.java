package com.nordic.service.member;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nordic.dto.common.ExceptionResponseDto;
import com.nordic.dto.common.ResponseDto;
import com.nordic.dto.member.MemberDto;
import com.nordic.dto.member.MemberModifyDto;
import com.nordic.dto.member.SearchDto;
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
	
	public PageInfo<MemberDto> pointArrange (int pageNum, String pointArrange) {
		PageHelper.startPage(pageNum, 10);
		List<MemberDto> memberList = memberRepository.pointArrange(pointArrange);
		return PageInfo.of(memberList);
	}
	
	public PageInfo<MemberDto> memberState (int pageNum, String memberState) {
		PageHelper.startPage(pageNum, 10);
		List<MemberDto> memberList = memberRepository.memberState(memberState);
		return PageInfo.of(memberList);
	}
	
	public int doApproval (MemberDto memberDto) {
		return memberRepository.doApproval(memberDto);
	}
	
	public int doAdmin (MemberDto memberDto) {
		return memberRepository.doAdmin(memberDto);
	}
	
	public int doUnadmin (MemberDto memberDto) {
		return memberRepository.doUnadmin(memberDto);
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
	
	public MemberDto findMe (String member_code) {
		MemberDto memberDto = memberRepository.findMe(member_code);
		return memberDto;
	}

	public int mbrRegister (MemberDto memberDto) {
		return memberRepository.mbrRegister(memberDto);
	}
	
	public int modifyOne (MemberModifyDto memberModifyDto) {
		return memberRepository.modifyOne(memberModifyDto);
	}
	
	public int delOne (MemberDto memberDto) {
		return memberRepository.delOne(memberDto);
	}
	
	public int undoDelete (MemberDto memberDto) {
		return memberRepository.undoDelete(memberDto);
	}
	
	public PageInfo<MemberDto> doSearch(int pageNum, 
										SearchDto searchDto) {
		PageHelper.startPage(pageNum, 10);
		List<MemberDto> memberDto = memberRepository.doSearch(searchDto);
		return PageInfo.of(memberDto);
	}
	
	public static Map getUserInfo() {
	       final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	       if (authentication == null || authentication.getName() == null) {
	           throw new RuntimeException("No authentication information.");
	       }
	       
	       Map user_info = new HashMap();
	       user_info.put("member_code", authentication.getName());
	       user_info.put("role", authentication.getAuthorities());
	       return user_info;
	   }
	
}
