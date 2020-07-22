package com.cyberblogger.model.dto;

import com.cyberblogger.model.Article;
import com.cyberblogger.model.Follower;
import com.cyberblogger.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Created by foxi.chen on 11/02/20.
 *
 * @author foxi.chen
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserDTO extends User {
  private List<Follower> followers;
  private List<Follower> followings;
  private List<Article> ownArticleList;
  private String userName;

  public UserDTO(){ }
}
