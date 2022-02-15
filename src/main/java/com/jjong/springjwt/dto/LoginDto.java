/*
 * Created By dogfootmaster@gmail.com on 2022
 * This program is free software
 *
 * @author <a href=“mailto:dogfootmaster@gmail.com“>Jongsang Han</a>
 * @since 2022/02/15
 */

package com.jjong.springjwt.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.ToString;

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
@Getter
@ToString
public class LoginDto {

  @NotNull(message = "name field must not be null.")
  @Size(min = 3, max = 50)
  private String name;

  @NotNull(message = "password field must not be null.")
  @Size(min = 3, max = 100)
  private String password;

  public LoginDto(String name, String password) {
    this.name = name;
    this.password = password;
  }
}
