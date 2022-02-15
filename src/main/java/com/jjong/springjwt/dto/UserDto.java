/*
 * Created By dogfootmaster@gmail.com on 2022
 * This program is free software
 *
 * @author <a href=“mailto:dogfootmaster@gmail.com“>Jongsang Han</a>
 * @since 2022/02/15
 */

package com.jjong.springjwt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jjong.springjwt.entity.User;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
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
public class UserDto {

  @NotNull
  @Size(min = 3, max = 50)
  private String username;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @NotNull
  @Size(min = 3, max = 100)
  private String password;

  @NotNull
  @Size(min = 3, max = 50)
  private String nickname;

  private Set<AuthorityDto> authorityDtoSet;

  @Builder
  public UserDto(String username, String password, String nickname,
      Set<AuthorityDto> authorityDtoSet) {
    this.username = username;
    this.password = password;
    this.nickname = nickname;
    this.authorityDtoSet = authorityDtoSet;
  }






  public static UserDto from(User user) {
    if(user == null) return null;

    return UserDto.builder()
        .username(user.getName())
        .nickname(user.getNickName())
        .authorityDtoSet(user.getAuthorities().stream()
            .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
            .collect(Collectors.toSet()))
        .build();
  }
}
