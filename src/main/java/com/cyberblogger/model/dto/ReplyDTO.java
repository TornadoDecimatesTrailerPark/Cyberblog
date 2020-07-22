package com.cyberblogger.model.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by foxi.chen on 1/02/20.
 *
 * @author foxi.chen
 */
@Data
public class ReplyDTO {
  private int id;
  private String content;
  private UserDTO fromUser;
  private UserDTO toUser;
  private ReplyDTO parent;
  private int replyType;
  private String updateTime;
  private int commentId;
  private int replyNum;


  public ReplyDTO(){}
}
