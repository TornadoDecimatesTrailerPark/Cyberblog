package com.cyberblogger.model;

import lombok.Data;

import java.sql.Timestamp;


@Data
public class Reply extends BasePoJo {

  private int commentId;
  private int replyId;
  private String content;
  private int fromUserId;
  private int toUserId;
  private int replyType;

  public Reply(int id, Timestamp createTime, Timestamp updateTime,
               int commentId, int replyId, String content,
               int fromUserId, int toUserId, int replyType) {
    super(id, createTime, updateTime);
    this.commentId = commentId;
    this.replyId = replyId;
    this.content = content;
    this.fromUserId = fromUserId;
    this.toUserId = toUserId;
    this.replyType = replyType;
  }

  public Reply(Timestamp createTime, Timestamp updateTime, int commentId,
               int replyId, String content, int fromUserId, int toUserId,
               int replyType) {
    super(createTime, updateTime);
    this.commentId = commentId;
    this.replyId = replyId;
    this.content = content;
    this.fromUserId = fromUserId;
    this.toUserId = toUserId;
    this.replyType = replyType;
  }

  public Reply(int commentId, int replyId, String content, int fromUserId,
               int toUserId, int replyType) {
    this.commentId = commentId;
    this.replyId = replyId;
    this.content = content;
    this.fromUserId = fromUserId;
    this.toUserId = toUserId;
    this.replyType = replyType;
  }

  public Reply(){}
}
