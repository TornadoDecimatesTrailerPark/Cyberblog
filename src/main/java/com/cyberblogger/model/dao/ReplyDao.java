package com.cyberblogger.model.dao;

import com.cyberblogger.model.Reply;
import com.cyberblogger.model.dto.CommentDTO;
import com.cyberblogger.model.dto.ReplyDTO;
import com.cyberblogger.model.dto.UserDTO;
import com.cyberblogger.util.DateUtils;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class ReplyDao {
  public static List<ReplyDTO> get1stReplyListByCommentId (int commentId, Connection conn) throws SQLException, ParseException {
    try (PreparedStatement statement =
        conn.prepareStatement(
            "  select cr.id, cr.content, cr.reply_type, cr.update_time, "
                + " cr.from_user_id, u.avatar_url, cr.to_user_id, cc.avatar_url, cr.comment_id "
                + " from user u inner join comment_reply cr on u.id = cr.from_user_id inner join user cc on cr.to_user_id = cc.id "
                + " where cr.comment_id = ? and reply_type = 0;")) {
      statement.setInt(1, commentId);
        return getReplyDTOS(statement);
      }
  }

  public static List<ReplyDTO> getChildReplyList (int parentReplyId, Connection conn) throws SQLException, ParseException {
    try (PreparedStatement statement =
           conn.prepareStatement(
             "select cr.id, cr.content, cr.reply_type, cr.update_time," +
               " cr.from_user_id, u.avatar_url, cr.to_user_id, c.avatar_url, cr.comment_id " +
               " from user u inner join comment_reply cr on cr.from_user_id = u.id" +
               " inner join user c on cr.to_user_id = c.id where reply_type = 1 and cr.reply_id = ?")){
      statement.setInt(1, parentReplyId);
      return getReplyDTOS(statement);
    }
  }

  private static List<ReplyDTO> getReplyDTOS(PreparedStatement statement) throws SQLException, ParseException {
    List<ReplyDTO> replyDTOS = new ArrayList<>();
    try (ResultSet rs = statement.executeQuery()) {
      while (rs.next()) {
        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setId(rs.getInt(1));
        replyDTO.setContent(rs.getString(2));
        replyDTO.setReplyType(rs.getInt(3));
        replyDTO.setUpdateTime(DateUtils.formatDate(new Date(rs.getTimestamp(4).getTime()),
          "dd/MM/yyyy HH:mm:ss"));
        replyDTO.setCommentId(rs.getInt(9));
        UserDTO fromUserDTO = new UserDTO();
        fromUserDTO.setId(rs.getInt(5));
        fromUserDTO.setAvatarUrl(rs.getString(6));
        UserDTO toUserDTO = new UserDTO();
        toUserDTO.setId(rs.getInt(7));
        toUserDTO.setAvatarUrl(rs.getString(8));
        replyDTO.setFromUser(fromUserDTO);
        replyDTO.setToUser(toUserDTO);
        replyDTOS.add(replyDTO);
      }
      return replyDTOS;
    }
  }

  public static boolean insertReply(Reply reply, Connection conn) throws SQLException {
    try (PreparedStatement statement =
           conn.prepareStatement(
             "insert into comment_reply(comment_id, reply_id, reply_type, content, from_user_id, " +
               "to_user_id, create_time, update_time) values (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)){
      statement.setInt(1, reply.getCommentId());
      statement.setInt(2, reply.getReplyId());//1st reply no parent replyId
      statement.setInt(3, reply.getReplyType());
      statement.setString(4, reply.getContent());
      statement.setInt(5, reply.getFromUserId());
      statement.setInt(6, reply.getToUserId());
      statement.setTimestamp(7, reply.getCreateTime());
      statement.setTimestamp(8, reply.getUpdateTime());
      int rowAffected = statement.executeUpdate();
      if (rowAffected != 1) {
        return false;
      }else {
        try (ResultSet rs = statement.getGeneratedKeys()) {
          rs.next();
          int id = rs.getInt(1);
          reply.setId(id);
          return true;
        }
      }
    }
  }

  public static int getUIdByReplyId(int replyId, Connection conn) throws SQLException {
    try (PreparedStatement statement =
           conn.prepareStatement(
             "select from_user_id from comment_reply where id = ?")){
      statement.setInt(1, replyId);
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          return rs.getInt(1);
        }
      }
      return 0;
    }
  }

  public static void deleteReplyAndChidren(int reply, Connection conn) throws SQLException {
    try (PreparedStatement statement =
        conn.prepareStatement(
            "delete from comment_reply where id = ? ",
            Statement.RETURN_GENERATED_KEYS)) {
      statement.setInt(1, reply);

      statement.executeUpdate();
     }
  }

  public static void deleteAllReplyByCommentId(int commentId, Connection conn) throws SQLException {
    try (PreparedStatement statement =
           conn.prepareStatement(
             "delete from comment_reply where comment_id = ? ",
             Statement.RETURN_GENERATED_KEYS)) {
      statement.setInt(1, commentId);

      statement.executeUpdate();
    }
  }

  public static List<Integer> getChildIdByParentId(int parentId, Connection conn) throws SQLException {
    try (PreparedStatement statement =
           conn.prepareStatement(
             "select id from comment_reply where reply_id = ?")){
      statement.setInt(1, parentId);
      List<Integer> temp = new ArrayList<>();
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          temp.add(rs.getInt(1));
        }
        return temp;
      }
    }
  }

  public static List<Integer> getAllReplyIdByFromUserId(int uId, Connection conn) throws SQLException {
    try (PreparedStatement statement =
           conn.prepareStatement(
             "select id from comment_reply where from_user_id = ?")){
      statement.setInt(1, uId);
      List<Integer> rIds = new ArrayList<>();
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          rIds.add(rs.getInt(1));
        }
      }
      return rIds;
    }
  }
}
