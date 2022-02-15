/*
 * Created By dogfootmaster@gmail.com on 2022
 * This program is free software
 *
 * @author <a href=“mailto:dogfootmaster@gmail.com“>Jongsang Han</a>
 * @since 2022/02/14
 */

package com.jjong.springjwt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "authorities")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Authority {

  @Id
  @Column(name = "authority_name", nullable = false, length = 50)
  private String authorityName;

  @Builder
  public Authority(String authorityName) {
    this.authorityName = authorityName;
  }
}
