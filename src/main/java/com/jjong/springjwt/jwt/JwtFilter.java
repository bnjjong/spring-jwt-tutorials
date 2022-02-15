/*
 * Created By dogfootmaster@gmail.com on 2022
 * This program is free software
 *
 * @author <a href=“mailto:dogfootmaster@gmail.com“>Jongsang Han</a>
 * @since 2022/02/15
 */

package com.jjong.springjwt.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

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
@Slf4j
public class JwtFilter extends GenericFilterBean {

  public static final String AUTHORIZATION_HEADER = "Authorization";
  private TokenProvider tokenProvider;

  public JwtFilter(TokenProvider tokenProvider) {
    this.tokenProvider = tokenProvider;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    // 실제 filtering 로직 수행
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    String jwt = resolveToken(httpServletRequest);
    String requestURI = httpServletRequest.getRequestURI();

    // jwt 인증 정보를 securityContext 에 저장 함
    if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
      Authentication authentication = tokenProvider.getAuthentication(jwt);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      log.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
    } else {
      log.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
    }

    chain.doFilter(request, response);

  }

  /**
   * Request Header에서 토큰 정보를 꺼내 옴.
   * @param request
   * @return
   */
  private String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }
}
