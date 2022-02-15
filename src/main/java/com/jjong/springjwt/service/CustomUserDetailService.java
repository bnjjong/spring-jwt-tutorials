/*
 * Created By dogfootmaster@gmail.com on 2022
 * This program is free software
 *
 * @author <a href=“mailto:dogfootmaster@gmail.com“>Jongsang Han</a>
 * @since 2022/02/15
 */

package com.jjong.springjwt.service;

import com.jjong.springjwt.entity.User;
import com.jjong.springjwt.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
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
@Component
public class CustomUserDetailService implements UserDetailsService {

  private final UserRepository userRepository;

  public CustomUserDetailService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
    return userRepository.findOneWithAuthoritiesByName(name)
        .map(user -> createUser(name, user))
        .orElseThrow(() -> new UsernameNotFoundException(name + " -> can't find data on database."));
  }

  private org.springframework.security.core.userdetails.User createUser(String name, User user) {
    if (!user.isActivated()) {
      throw new RuntimeException(name + " -> not activated.!");
    }
    List<SimpleGrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
        .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
        .collect(Collectors.toList());

    return new org.springframework.security.core.userdetails.User(
        user.getName(),
        user.getPassword(),
        grantedAuthorities);
  }
}
