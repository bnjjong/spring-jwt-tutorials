/*
 * Created By dogfootmaster@gmail.com on 2022
 * This program is free software
 *
 * @author <a href=“mailto:dogfootmaster@gmail.com“>Jongsang Han</a>
 * @since 2022/02/15
 */

package com.jjong.springjwt.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
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
@Slf4j
public class TokenProvider implements InitializingBean {

  private static final String AUTHORITIES_KEY = "auth";

  private final String secret;
  private final long tokenValidityInMilliseconds;

  private Key key;


  public TokenProvider(
      @Value("${jwt.secret}") String secret,
      @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {
    this.secret = secret;
    this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
  }

  @Override
  public void afterPropertiesSet() {
    // key base 64로 디코딩하고
    byte[] keyBytes = Decoders.BASE64.decode(secret);
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  /**
   * Token 생성 한다.
   *
   * @param authentication
   * @return
   */
  public String createToken(Authentication authentication) {
    String authorities = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));

    long now = (new Date()).getTime();
    Date validity = new Date(now + this.tokenValidityInMilliseconds);

    return Jwts.builder()
        .setSubject(authentication.getName())
        .claim(AUTHORITIES_KEY, authorities)
        .signWith(key, SignatureAlgorithm.HS512)
        .setExpiration(validity) // token expire
        .compact();
  }

  /**
   * 권한 정보를 가져온다.
   * @param token
   * @return
   */
  public Authentication getAuthentication(String token) {
    Claims claims = Jwts
        .parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();

    Collection<? extends GrantedAuthority> authorities =
        Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

    User principal = new User(claims.getSubject(), "", authorities);

    return new UsernamePasswordAuthenticationToken(principal, token, authorities);
  }


  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (SecurityException | MalformedJwtException e) {
      log.info("잘못된 JWT 서명입니다.");
    } catch (ExpiredJwtException e) {
      log.info("만료된 JWT 토큰입니다.");
    } catch (UnsupportedJwtException e) {
      log.info("지원되지 않는 JWT 토큰입니다.");
    } catch (IllegalArgumentException e) {
      log.info("JWT 토큰이 잘못되었습니다.");
    }
    return false;
  }
}
