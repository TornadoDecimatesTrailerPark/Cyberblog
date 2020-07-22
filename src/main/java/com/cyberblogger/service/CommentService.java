package com.cyberblogger.service;

import com.cyberblogger.model.Comment;
import com.cyberblogger.model.Reply;
import com.cyberblogger.model.dao.CommentDao;
import com.cyberblogger.model.dao.ReplyDao;
import com.cyberblogger.model.dao.UserDao;
import com.cyberblogger.model.dto.CommentDTO;
import com.cyberblogger.model.dto.ReplyDTO;
import com.cyberblogger.model.dto.UserDTO;
import com.cyberblogger.util.DBConnectionUtils;
import com.cyberblogger.util.DateUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CommentService {

  public static List<CommentDTO> getCommentsByaId(int aId) throws IOException, SQLException, ParseException {
    try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
      List<CommentDTO> commentDTOS = CommentDao.getAllCommentsByaId(aId, conn);
      for (CommentDTO commentDTO : commentDTOS) {
         String username = UserDao.getUsernameByuId(commentDTO.getUserId(), conn);
         int count = CommentDao.getAllReplyNumBycId(commentDTO.getId(), conn);
         commentDTO.setUsername(username);
         commentDTO.setReplyNumber(count);
      }
      return commentDTOS;
    }
  }

  public static boolean addNewComment(CommentDTO commentDTO) throws IOException, SQLException, ParseException {
    try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
      Comment comment = convertToComment(commentDTO);
      if (CommentDao.insertComment(comment, conn)){
        commentDTO.setUserAvatarUrl(UserDao.getUserInfoByuId(conn, comment.getUserId()).getAvatarUrl());
        commentDTO.setId(comment.getId());
//        commentDTO.setUsername(UserDao.getUsernameByuId());
        commentDTO.setUpdateTime(DateUtils.formatDate(new Date(comment.getUpdateTime().getTime()), "dd/MM/yyyy HH:mm:ss"));
        return true;
      }
      return false;
    }
  }

  private static Comment convertToComment(CommentDTO commentDTO) {
    Comment comment = new Comment();
    comment.setArticleId(commentDTO.getArticleId());
    comment.setUserId(commentDTO.getUserId());
    comment.setContent(commentDTO.getContent());
    comment.setCreateTime(new Timestamp(System.currentTimeMillis()));
    comment.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    return comment;
  }

  public static List<ReplyDTO> getAllReplyByCommentId(int commentId) throws IOException, SQLException, ParseException {
    try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
      List<ReplyDTO> replyDTOS = ReplyDao.get1stReplyListByCommentId(commentId, conn);
      for (ReplyDTO replyDTO : replyDTOS) {
        makeUserNameByuid(conn, replyDTO);
        replyDTO.setReplyNum(CommentDao.getAllChildReplyNumById(replyDTO.getId(), conn));
      }
      return replyDTOS;
    }
  }

  public static List<ReplyDTO> getChildReplyByrId(int pRId)
      throws IOException, SQLException, ParseException {
    try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
      List<ReplyDTO> replyDTOS = ReplyDao.getChildReplyList(pRId, conn);
      for (ReplyDTO replyDTO : replyDTOS) {
        makeUserNameByuid(conn, replyDTO);
        replyDTO.setReplyNum(CommentDao.getAllChildReplyNumById(replyDTO.getId(), conn));
      }
      return replyDTOS;
    }
}

  private static void makeUserNameByuid(Connection conn, ReplyDTO replyDTO) throws SQLException {
    String fromUserName = UserDao.getUsernameByuId(replyDTO.getFromUser().getId(), conn);
    String toUserName = UserDao.getUsernameByuId(replyDTO.getToUser().getId(), conn);
    replyDTO.getFromUser().setUserName(fromUserName);
    replyDTO.getToUser().setUserName(toUserName);
  }

  public static boolean addNewReply(ReplyDTO replyDTO) throws IOException, SQLException, ParseException {
    try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
      Reply reply = convertToReply(replyDTO);
      if (ReplyDao.insertReply(reply, conn)){
        replyDTO.getFromUser().setAvatarUrl(UserDao.getUserInfoByuId(conn, reply.getFromUserId()).getAvatarUrl());
        replyDTO.getFromUser().setUserName(UserDao.getUsernameByuId(reply.getFromUserId(), conn));
        replyDTO.getToUser().setAvatarUrl(UserDao.getUserInfoByuId(conn, reply.getToUserId()).getAvatarUrl());
        replyDTO.getToUser().setUserName(UserDao.getUsernameByuId(reply.getToUserId(), conn));
        replyDTO.setId(reply.getId());
        replyDTO.setUpdateTime(DateUtils.formatDate(new Date(reply.getUpdateTime().getTime()), "dd/MM/yyyy HH:mm:ss"));
        return true;
      }
      return false;
      }
    }

  private static Reply convertToReply(ReplyDTO replyDTO) {
    Reply reply = new Reply();
    reply.setCommentId(replyDTO.getCommentId());
    reply.setFromUserId(replyDTO.getFromUser().getId());
    reply.setToUserId(replyDTO.getToUser().getId());
    reply.setReplyId(replyDTO.getParent().getId());
    reply.setReplyType(replyDTO.getReplyType());
    reply.setContent(replyDTO.getContent());
    reply.setCreateTime(new Timestamp(System.currentTimeMillis()));
    reply.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    return reply;
  }

  public static int getUidByCommentId(int commentId) throws IOException, SQLException {
    try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
      int uid = CommentDao.getUIdByCommentId(commentId,conn);
      return uid;
    }

  }

  public static int getUidByReplyId(int replyId) throws IOException, SQLException {
    try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
      int uid = ReplyDao.getUIdByReplyId(replyId, conn);
      return uid;
    }
  }

  public static void recursiveSearch(int i, List<Integer> ids) throws SQLException, IOException {
    /// list<Integer> a = process(i)
    /// if a.size = 0  return
    /// for i in a
    ///  list add(i)
    //   recursiveSearch(i, list)
    try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
    List<Integer> temp = ReplyDao.getChildIdByParentId(i, conn);
    if (temp.size() == 0) {
      return ;
    }
    for (Integer item : temp) {
        ids.add(item);
        recursiveSearch(item, ids);
    }
    }
  }

  public static void deleteOneReply(int i) throws IOException, SQLException {
    try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
      ReplyDao.deleteReplyAndChidren(i, conn);
    }
  }

  public static void deleteOneComment(int cId ) throws IOException, SQLException {
    try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
      CommentDao.deleteComment(cId, conn);
    }
  }

  public static void deleteAllReplyByCommentId (int cId) throws IOException, SQLException {
    try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
      ReplyDao.deleteAllReplyByCommentId(cId, conn);
    }
  }

  public static int getReplyNumByCommentId (int commentId) throws IOException, SQLException {
    try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
      return CommentDao.getAllReplyNumBycId(commentId, conn);
    }
  }

//  public static void main(String[] args) throws IOException, SQLException {
//    List<Integer> a = new ArrayList<>();
//    recursiveSearch(40, a);
//    System.out.println(a);
//  }
}
