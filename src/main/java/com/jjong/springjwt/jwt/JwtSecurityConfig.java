/*
 * Created By dogfootmaster@gmail.com on 2022
 * This program is free software
 *
 * @author <a href=“mailto:dogfootmaster@gmail.com“>Jongsang Han</a>
 * @since 2022/02/15
 */

package com.jjong.springjwt.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * create on 2022/02/15. create by IntelliJ IDEA.
 *
 * <p> JwtFilter security filte에 등록 함.  </p>
 * <p> {@link } and {@link }관련 클래스 </p>
 *
 * @author Jongsang Han
 * @version 1.0
 * @see
 * @since 1.0
 */
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  private TokenProvider tokenProvider;

  public JwtSecurityConfig(TokenProvider tokenProvider) {
    this.tokenProvider = tokenProvider;
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    JwtFilter customFilter = new JwtFilter(tokenProvider);
    http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
