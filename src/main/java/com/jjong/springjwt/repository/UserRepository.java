/*
 * Created By dogfootmaster@gmail.com on 2022
 * This program is free software
 *
 * @author <a href=“mailto:dogfootmaster@gmail.com“>Jongsang Han</a>
 * @since 2022/02/15
 */

package com.jjong.springjwt.repository;

import com.jjong.springjwt.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

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
public interface UserRepository extends JpaRepository<User, Long> {

  // 권한 정보도 같이 가져 옴.
  @EntityGraph(attributePaths = "authorities") // eager 조회로 바로 가져 옴.
  Optional<User> findOneWithAuthoritiesByName(String name);
}
