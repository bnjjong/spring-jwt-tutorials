/*
 * Created By dogfootmaster@gmail.com on 2022
 * This program is free software
 *
 * @author <a href=“mailto:dogfootmaster@gmail.com“>Jongsang Han</a>
 * @since 2022/02/15
 */

package com.jjong.springjwt.service;

import com.jjong.springjwt.dto.UserDto;
import com.jjong.springjwt.entity.Authority;
import com.jjong.springjwt.entity.User;
import com.jjong.springjwt.repository.UserRepository;
import com.jjong.springjwt.util.SecurityUtil;
import java.util.Collections;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional
  public UserDto signup(UserDto userDto) {
    if (userRepository.findOneWithAuthoritiesByName(userDto.getUsername()).orElse(null) != null) {
      throw new RuntimeException("이미 가입되어 있는 유저입니다.");
    }

    Authority authority = Authority.builder()
        .authorityName("ROLE_USER") // 이 사람의 권한.
        .build();

    User user = User.builder()
        .name(userDto.getUsername())
        .password(passwordEncoder.encode(userDto.getPassword()))
        .nickName(userDto.getNickname())
        .authorities(Collections.singleton(authority))
        .activated(true)
        .build();

    return UserDto.from(userRepository.save(user));
  }

  /**
   * 인자값의 username 으로 조회한 UserDto를 리턴 함.
   *
   * @param username
   * @return
   */
  @Transactional(readOnly = true)
  public UserDto getUserWithAuthorities(String username) {
    return UserDto.from(userRepository.findOneWithAuthoritiesByName(username).orElse(null));
  }

  /**
   * 현제 SecurityContext에 저장된 User를 조회
   * @return
   */
  @Transactional(readOnly = true)
  public UserDto getMyUserWithAuthorities() {
    return UserDto.from(
        SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByName).orElse(null));
  }
}
