package com.cyberblogger.model.dto;

import com.cyberblogger.model.Comment;
import com.cyberblogger.model.User;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by foxi.chen on 1/02/20.
 *
 * @author foxi.chen
 */
@Data
public class CommentDTO {
  private int id;
  private int articleId;
  private String content;
  private String updateTime;
  private int userId;
  private String username;
  private String userAvatarUrl;
  private int replyNumber;


  public CommentDTO(){}
}
