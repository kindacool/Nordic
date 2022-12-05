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
	public List<MemberDto> findAdmins() {
		return memberMapper.findAdmins();
	}
	
	@Override
	public MemberDto findOne (String member_code) {
		MemberDto memberDto = memberMapper.findOne(member_code);
		log.info("memberDto : " + memberDto);
		System.out.println("memberDto : " + memberDto);
		return memberDto;
	}
	
	@Override
	public int mbrRegister (MemberDto memberDto) {
		return memberMapper.mbrRegister(memberDto);
	}
	
	@Override
	public int modifyOne (MemberModifyDto memberDto) {
		return memberMapper.modifyOne(memberDto);
	}

}
