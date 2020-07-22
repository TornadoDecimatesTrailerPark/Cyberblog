package com.cyberblogger.model.dao;

import com.cyberblogger.model.Comment;
import com.cyberblogger.model.dto.CommentDTO;
import com.cyberblogger.util.DateUtils;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class CommentDao {

  public static List<CommentDTO> getAllCommentsByaId(int aId, Connection conn) throws SQLException, ParseException {
    try (PreparedStatement statement =
           conn.prepareStatement(
             "select c.id, c.content, c.update_time, u.avatar_url, u.id " +
               "from comment c inner join user u on c.user_id = u.id where article_id = ? order by c.update_time desc")){
      statement.setInt(1, aId);
      List<CommentDTO> commentDTOS = new ArrayList<>();
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          CommentDTO commentDTO = new CommentDTO();
          commentDTO.setId(rs.getInt(1));
          commentDTO.setContent(rs.getString(2));
          commentDTO.setUpdateTime(DateUtils.formatDate(new Date(rs.getTimestamp(3).getTime()),
            "dd/MM/yyyy HH:mm:ss"));
          commentDTO.setUserAvatarUrl(rs.getString(4));
          commentDTO.setUserId(rs.getInt(5));
          commentDTOS.add(commentDTO);
        }
        return commentDTOS;
      }
    }
  }

  public static int getAllReplyNumBycId(int cId, Connection conn) throws SQLException {
    try (PreparedStatement statement =
           conn.prepareStatement(
             "select count(*)" +
               "from comment c inner join comment_reply cr on c.id = cr.comment_id where cr.reply_type = 0 " +
               "and c.id = ?")){
      statement.setInt(1, cId);
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          return rs.getInt(1);
        }
      }
      return 0;
    }
  }

  public static int getAllChildReplyNumById(int rId, Connection conn) throws SQLException {
    try (PreparedStatement statement =
           conn.prepareStatement(
             "select count(*)" +
               "from comment_reply c1 inner join comment_reply c2 on c1.id = c2.reply_id where c2.reply_type = 1 " +
               "and c2.reply_id = ?")){
      statement.setInt(1, rId);
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          return rs.getInt(1);
        }
      }
      return 0;
    }
  }

  public static boolean insertComment(Comment comment, Connection conn) throws SQLException {
    try (PreparedStatement statement =
           conn.prepareStatement(
             "insert into comment(user_id, article_id, content, create_time, update_time) values " +
               "(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)){
      statement.setInt(1, comment.getUserId());
      statement.setInt(2, comment.getArticleId());
      statement.setString(3, comment.getContent());
      statement.setTimestamp(4, comment.getCreateTime());
      statement.setTimestamp(5, comment.getUpdateTime());
      int rowAffected = statement.executeUpdate();
      if (rowAffected != 1) {
        return false;
      }else {
        try (ResultSet rs = statement.getGeneratedKeys()) {
          rs.next();
          int commentId = rs.getInt(1);
          comment.setId(commentId);
          return true;
        }
      }
    }
  }

  public static int getUIdByCommentId(int commentId, Connection conn) throws SQLException {
    try (PreparedStatement statement =
           conn.prepareStatement(
             "select user_id from comment where id = ?")){
      statement.setInt(1, commentId);
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          return rs.getInt(1);
        }
      }
      return 0;
    }
  }

  public static void deleteComment(int id, Connection conn) throws SQLException {
    try (PreparedStatement statement =
           conn.prepareStatement(
             "delete from comment where id = ? ",
             Statement.RETURN_GENERATED_KEYS)) {
      statement.setInt(1, id);

      statement.executeUpdate();
    }
  }

  public static List<Integer> getAllCommentIdByUserId(int uId, Connection conn) throws SQLException {
    try (PreparedStatement statement =
           conn.prepareStatement(
             "select id from comment where user_id = ?")){
      statement.setInt(1, uId);
      List<Integer> cIds = new ArrayList<>();
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          cIds.add(rs.getInt(1));
        }
      }
      return cIds;
    }
  }
}
