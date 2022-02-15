/*
 * Created By dogfootmaster@gmail.com on 2022
 * This program is free software
 *
 * @author <a href=“mailto:dogfootmaster@gmail.com“>Jongsang Han</a>
 * @since 2022/02/15
 */

package com.jjong.springjwt.util;

import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

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
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtil {

  /**
   * Secutity context의 Authentication 객체를 이용해서 username을 리턴한다.
   *
   * @return username
   * @see UserDetails
   * @see com.jjong.springjwt.jwt.JwtFilter#doFilter(ServletRequest, ServletResponse, FilterChain) 여기서 context에 추가 됨.  
   */
  public static Optional<String> getCurrentUsername() {
    // security context에서 Authentication 객체를 가져 옴.
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null) {
      log.debug("Security Context에 인증 정보가 없습니다.");
      return Optional.empty();
    }

    String username = null;
    if (authentication.getPrincipal() instanceof UserDetails) {
      UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
      username = springSecurityUser.getUsername();
    } else if (authentication.getPrincipal() instanceof String) {
      username = (String) authentication.getPrincipal();
    }

    return Optional.ofNullable(username);
  }
}
