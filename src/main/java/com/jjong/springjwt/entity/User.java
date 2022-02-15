/*
 * Created By dogfootmaster@gmail.com on 2022
 * This program is free software
 *
 * @author <a href=“mailto:dogfootmaster@gmail.com“>Jongsang Han</a>
 * @since 2022/02/14
 */

package com.jjong.springjwt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

  @JsonIgnore
  @Id
  @Column(name = "user_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", length = 50, unique = true)
  private String name;

  @JsonIgnore
  @Column(name = "password", length = 100)
  private String password;

  @Column(name = "nick_name", length = 50)
  private String nickName;

  @JsonIgnore
  @Column(name = "activated")
  private boolean activated;

  @ManyToMany
  @JoinTable(
      name = "user_authority",
      joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")}
  )
  private Set<Authority> authorities;

  @Builder
  public User(String name, String password, String nickName, boolean activated,
      Set<Authority> authorities) {
    this.name = name;
    this.password = password;
    this.nickName = nickName;
    this.activated = activated;
    this.authorities = authorities;
  }
}
