package com.nordic.repository.member;

import java.lang.reflect.Member;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.nordic.dto.common.ExceptionResponseDto;
import com.nordic.dto.common.ResponseDto;
import com.nordic.dto.member.MemberDto;
import com.nordic.dto.member.MemberModifyDto;
import com.nordic.dto.member.SearchDto;

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
	public List<MemberDto> pointArrange(String pointArrange) {
		return memberMapper.pointArrange(pointArrange);
	}
	
	@Override
	public List<MemberDto> memberState (String memberState) {
		return memberMapper.memberState(memberState);
	}

	@Override
	public int doApproval (MemberDto memberDto) {
		return memberMapper.doApproval(memberDto);
	}
	
	@Override
	public int doAdmin (MemberDto memberDto) {
		return memberMapper.doAdmin(memberDto);
	}
	
	@Override
	public int doUnadmin (MemberDto memberDto) {
		return memberMapper.doUnadmin(memberDto);
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
	public MemberDto findMe (String member_code) {
		return memberMapper.findMe(member_code);
	}
	
	@Override
	public int mbrRegister (MemberDto memberDto) {
		return memberMapper.mbrRegister(memberDto);
	}
	
	@Override
	public int modifyOne (MemberModifyDto memberModifyDto) {
		return memberMapper.modifyOne(memberModifyDto);
	}
	
	@Override
	public int delOne (MemberDto memberDto) {
		return memberMapper.delOne(memberDto);
	}
	
	@Override
	public int undoDelete (MemberDto memberDto) {
		return memberMapper.undoDelete(memberDto);
	}
	
	@Override
	public List<MemberDto> doSearch (SearchDto searchDto) {
		return memberMapper.doSearch(searchDto);
	}
	
//	@Override
//	public void createMember(MemberDto memberDto) {
//        memberMapper.createMember(memberDto);
//    }
//
//    @Override
//    public int getNewCode() {
//        int newCode = memberMapper.getNewCode();
//        return newCode;
//    }

}
