package com.nordic.config.security;

import com.nordic.jwt.JwtAccessDeniedHandler;
import com.nordic.jwt.JwtAuthenticationEntryPoint;
import com.nordic.jwt.JwtSecurityConfig;
import com.nordic.jwt.TokenProvider;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import com.nordic.jwt.JwtAccessDeniedHandler;
import com.nordic.jwt.JwtAuthenticationEntryPoint;
import com.nordic.jwt.JwtSecurityConfig;
import com.nordic.jwt.TokenProvider;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final TokenProvider tokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(
            TokenProvider tokenProvider,
            CorsFilter corsFilter,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler
    ) {
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .antMatchers( "/favicon.ico", "/error","/v2/api-docs",  "/configuration/ui",
                                "/swagger-resources", "/configuration/security",
                                "/swagger-ui.html", "/webjars/**","/swagger-ui/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .formLogin().usernameParameter("member_code")
                .and()
                // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
                .csrf().disable()

                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // enable h2-console
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                // 토큰을 예외할 api 경로
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/api/hello").permitAll()
                .antMatchers("/api/member/**").permitAll()
                .antMatchers("/api/admin/mission/result/image/**").permitAll()
                .antMatchers("/api/admin/mission/result/point/**").permitAll()
                .antMatchers("/api/mission/image/**").permitAll()
                .antMatchers("/api/intro/**").permitAll()
                .antMatchers("/api/origin/**").permitAll()
                .antMatchers("/api/goods/avail").permitAll()
                //황준성
                .antMatchers("/api/image/**").permitAll()
                .antMatchers("/api/countImage/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/list/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/mission/{mission_no}").permitAll()
                //동민님
                .antMatchers("/api/img/**").permitAll()
                .antMatchers("/api/board/**").permitAll()
                .antMatchers("/api/board/view/{page}/{board_no}").permitAll()
                .antMatchers("/api/board/{searchType}/{searchContent}").permitAll()
                .antMatchers("/api/board/{board_no}/replies").permitAll()
                //자영님
                .antMatchers(HttpMethod.GET, "/api/goods/{no}").permitAll()
                .antMatchers("/api/goods/image/**").permitAll()
                //민지님
                .antMatchers("/api/member/register").permitAll()
                .antMatchers("/api/member/registerForm/**").permitAll()
                .antMatchers("/api/goods/image/**").permitAll()
                .antMatchers("/api/admin/mission/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtSecurityConfig(tokenProvider));

        return httpSecurity.build();
    }
}