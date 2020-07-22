package com.cyberblogger.service;

import com.cyberblogger.model.*;
import com.cyberblogger.model.dao.*;
import com.cyberblogger.model.dto.ArticleDTO;
import com.cyberblogger.model.dto.UserDTO;
import com.cyberblogger.util.AuthenticatorUtils;
import com.cyberblogger.util.DBConnectionUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class UserAccountService {

  public static void signUpAccount(User user, String userName, String password) throws IOException, SQLException {
    try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
      if (UserDao.insertNewUser(user, conn)){
        LocalAuth localAuth = AuthenticatorUtils.createLocalAuthAccount(userName, password, user.getId());
        LocalAuthDao.insertLocalAuth(localAuth, conn);
      } else {
        throw new SQLException();
      }
    }
  }

  public static boolean signInAccount(String userName, String password) throws IOException, SQLException {
    try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
        LocalAuth localAuth = LocalAuthDao.getLocalAuthInfoByUsername(userName, conn);
        if (localAuth != null){
          return AuthenticatorUtils.authenticate(localAuth, password);
        } else {
          return false;
        }
    }
  }

  public static void signUpAccountByThirdAuth(User user, ThirdAuth thirdAuth) throws IOException, SQLException {
    try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
      if (ThirdAuthDao.isUniqueTokenId(thirdAuth.getOauthName(), thirdAuth.getOauthId(), conn)){
        UserDao.insertNewUser(user, conn);
        thirdAuth.setUserId(user.getId());
        ThirdAuthDao.insertThirdAuth(thirdAuth, conn);
      }else{
        //already login with google
        user.setId(ThirdAuthDao.getUserIdByUsername(thirdAuth.getOauthId(), thirdAuth.getOauthName(), conn));
      }
    }
  }

  public static boolean isUniqueUsername(String username) throws IOException, SQLException {
    try(Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")){
      return LocalAuthDao.isUniqueUsername(username, conn);
    }
  }

  public static int getUidByUserName(String username) throws IOException, SQLException {

    try(Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")){
      return LocalAuthDao.getUserIdByUsername(username, conn);
    }
  }

  public static UserDTO getUserInfoByuId(int uId) throws IOException, SQLException {
    try(Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")){
      User user = UserDao.getUserInfoByuId(conn, uId);
      UserDTO userDTO = new UserDTO();
      userDTO.setId(uId);
      userDTO.setF_name(user.getF_name());
      userDTO.setL_name(user.getL_name());
      userDTO.setEmail(user.getEmail());
      userDTO.setBirthDay(user.getBirthDay());
      userDTO.setDescription(user.getDescription());
      userDTO.setAvatarUrl(user.getAvatarUrl());
      userDTO.setCreateTime(user.getCreateTime());
      userDTO.setUpdateTime(user.getUpdateTime());
      userDTO.setFollowers(getFollowerByuId(uId));
      userDTO.setFollowings(getFollowingByuId(uId));
      for (Follower follower : userDTO.getFollowers()) {
        follower.setFollowerName(UserDao.getUsernameByuId(follower.getFollowerId(), conn));
      }
      for (Follower follower : userDTO.getFollowings()) {
        follower.setFollowerName(UserDao.getUsernameByuId(follower.getFollowerId(), conn));
      }
      return userDTO;
    }
  }

  public static List<Follower> getFollowerByuId(int uId) throws IOException, SQLException {
    try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
      return FollowerDao.getFollowerByUserId(uId, conn);
    }
  }

  public static List<Follower> getFollowingByuId(int uId) throws IOException, SQLException {
    try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
      return FollowerDao.getFollowingByUserId(uId, conn);
    }
  }

  public static void deleteUserInfo(int uId) throws IOException, SQLException, ParseException {
    try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
      //delete comments
       List<Integer> cids = CommentDao.getAllCommentIdByUserId(uId, conn);
      for (Integer cid : cids) {
        if (CommentService.getReplyNumByCommentId(cid) != 0) {
          CommentService.deleteAllReplyByCommentId(cid);
        }
        CommentService.deleteOneComment(cid);
      }
      List<Integer> rids = ReplyDao.getAllReplyIdByFromUserId(uId, conn);

      for (Integer rid : rids) {
        List<Integer> childrenList = new ArrayList<>();
        CommentService.recursiveSearch(rid, childrenList);
        if (childrenList.size() != 0) {
          for (Integer item : childrenList) {
            CommentService.deleteOneReply(item);
          }
        }
        CommentService.deleteOneReply(rid);
      }
      //delete article
      List<ArticleDTO> articleDTOS =  ArticleDao.getArticlesByuId(uId, conn);
      for (ArticleDTO articleDTO : articleDTOS) {
        ArticleService.deleteArticleById(articleDTO.getId());
      }

      //delete other
      UserDao.deleteUserFavoutrite(uId, conn);
      UserDao.deleteUserFollowerInfo(uId, conn);
      UserDao.deleteUserLocalAuth(uId, conn);
      UserDao.deleteUserThirdAuth(uId, conn);
      UserDao.deleteUser(uId, conn);
    }
  }
}
