package com.nordic.repository.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import com.nordic.dto.member.MemberDto;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class LoginRepository implements LoginMapper{
    private final LoginMapper loginMapper;

    public Optional<MemberDto> findByMemberCode(String member_code) {
        return loginMapper.findByMemberCode(member_code);
    }
}
