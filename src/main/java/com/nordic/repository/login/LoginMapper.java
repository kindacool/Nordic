package com.nordic.repository.login;

import org.apache.ibatis.annotations.Mapper;

import com.nordic.dto.member.MemberDto;

import java.util.Optional;

@Mapper
public interface LoginMapper {
    Optional<MemberDto> findByMemberCode(String member_code);
}
