package com.cyberblogger.model;

import lombok.Data;

import java.sql.Timestamp;


@Data
public class Comment extends BasePoJo {

  private int userId;
  private int articleId;
  private String content;

  public Comment(int id, int userId, int articleId, String content, Timestamp createTime, Timestamp updateTime) {
    super(id, createTime, updateTime);
    this.userId = userId;
    this.articleId = articleId;
    this.content = content;
  }

  public Comment(int userId, int articleId, String content, Timestamp createTime, Timestamp updateTime) {
    super(createTime,updateTime);
    this.userId = userId;
    this.articleId = articleId;
    this.content = content;
  }
  public Comment(){}
}
