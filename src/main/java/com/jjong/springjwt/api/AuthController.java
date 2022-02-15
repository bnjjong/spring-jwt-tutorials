/*
 * Created By dogfootmaster@gmail.com on 2022
 * This program is free software
 *
 * @author <a href=“mailto:dogfootmaster@gmail.com“>Jongsang Han</a>
 * @since 2022/02/15
 */

package com.jjong.springjwt.api;

import com.jjong.springjwt.dto.LoginDto;
import com.jjong.springjwt.dto.TokenDto;
import com.jjong.springjwt.jwt.JwtFilter;
import com.jjong.springjwt.jwt.TokenProvider;
import javax.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create on 2022/02/15. create by IntelliJ IDEA.
 *
 * <p> 클래스 설명 </p>
 * <p> {@link } and {@link }관련 클래스 </p>
 *
 * @author Jongsang Han
 * @version 1.0
 * @see
 * @since 1.0
 */
@RestController
@RequestMapping("/api")
public class AuthController {
  private final TokenProvider tokenProvider;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  public AuthController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
    this.tokenProvider = tokenProvider;
    this.authenticationManagerBuilder = authenticationManagerBuilder;
  }

  @PostMapping("/authenticate")
  public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {

    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(loginDto.getName(), loginDto.getPassword());

    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwt = tokenProvider.createToken(authentication);

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

    return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
  }

}
