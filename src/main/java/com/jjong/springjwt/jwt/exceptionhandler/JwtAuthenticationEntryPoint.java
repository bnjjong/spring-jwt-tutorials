/*
 * Created By dogfootmaster@gmail.com on 2022
 * This program is free software
 *
 * @author <a href=“mailto:dogfootmaster@gmail.com“>Jongsang Han</a>
 * @since 2022/02/15
 */

package com.jjong.springjwt.jwt.exceptionhandler;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

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
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException) throws IOException {
    // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
  }
}
