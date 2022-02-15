/*
 * Created By dogfootmaster@gmail.com on 2022
 * This program is free software
 *
 * @author <a href=“mailto:dogfootmaster@gmail.com“>Jongsang Han</a>
 * @since 2022/02/14
 */

package com.jjong.springjwt.config;

import com.jjong.springjwt.jwt.exceptionhandler.JwtAccessDeniedHandler;
import com.jjong.springjwt.jwt.exceptionhandler.JwtAuthenticationEntryPoint;
import com.jjong.springjwt.jwt.JwtSecurityConfig;
import com.jjong.springjwt.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * create on 2022/02/14. create by IntelliJ IDEA.
 *
 * <p> 클래스 설명 </p>
 * <p> {@link } and {@link }관련 클래스 </p>
 *
 * @author Jongsang Han
 * @version 1.0
 * @see
 * @since 1.0
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // @PreAuthorize 메소드 단위로 적용할 수 있게 해줌.
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final TokenProvider tokenProvider;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

  public SecurityConfig(
      TokenProvider tokenProvider,
      JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
      JwtAccessDeniedHandler jwtAccessDeniedHandler
  ) {
    this.tokenProvider = tokenProvider;
    this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


  @Override
  public void configure(WebSecurity web) throws Exception {
    // h2 console 권한
    web
        .ignoring()
        .antMatchers(
            "/h2-console/**"
            , "favicon.ico"
            , "/error"
        );
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
        .csrf().disable()

//        .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

        // Exception 핸들러
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
        .authorizeRequests() // httpServletRequest 를 사용하는 요청들에 대한 접근 제한 설정
        // 아래 URL 은 허용하겠다는 의미
        .antMatchers("/api/hello").permitAll()
        .antMatchers("/api/authenticate").permitAll() // 토큰을 받기 위한 API
        .antMatchers("/api/signup").permitAll() // 회원 가입 API
        .anyRequest().authenticated() // 그 외에는 인증을 수행 함.

        .and()
        .apply(new JwtSecurityConfig(tokenProvider)); // JwtFilter 추가

  }
}
