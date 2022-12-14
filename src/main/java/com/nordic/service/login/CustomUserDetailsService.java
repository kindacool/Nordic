package com.nordic.service.login;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nordic.dto.member.MemberDto;
import com.nordic.repository.login.LoginRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
   private final LoginRepository loginRepository;

   @Override
   @Transactional
   public UserDetails loadUserByUsername(final String member_code) {

      User getUser = loginRepository.findByMemberCode(member_code)
              .map(user -> createUser(member_code, user))
              .orElseThrow(() -> new UsernameNotFoundException(member_code + " -> 데이터베이스에서 찾을 수 없습니다."));
      System.out.println("getUser = " + getUser);

      return getUser;
   }

   private org.springframework.security.core.userdetails.User createUser(String member_code, MemberDto memberDto) {
      String Role = "USER";
      if(memberDto.getAdmin_yn().equals("Y"))
         Role = "ADMIN";

      List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
      authorities.add(new SimpleGrantedAuthority(Role));

      User user = new User(member_code, memberDto.getPassword(), authorities);
      System.out.println("createUser user = " + user);

      return user;
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
