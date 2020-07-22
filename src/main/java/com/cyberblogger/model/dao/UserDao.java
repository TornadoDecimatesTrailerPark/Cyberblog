package com.cyberblogger.model.dao;

import com.cyberblogger.model.User;

import java.sql.*;


public class UserDao {

  // create new user profile
  public static User getUserInfoByUsername(String userName, Connection conn) throws SQLException {
    int userId = LocalAuthDao.getUserIdByUsername(userName, conn);
    User user = new User();
    if (userId == 0) {
      // login through third auth
      userId = ThirdAuthDao.getUserIdByUsername(userName, "google", conn);
    }
    user = getUserInfoByuId(conn, userId);
    return user;

    }

  public static User getUserInfoByuId(Connection conn, int userId) throws SQLException {
    try (PreparedStatement psmt = conn.prepareStatement(
      "select * from user where id = ?"
    )) {
      psmt.setInt(1, userId);
      try (ResultSet rs = psmt.executeQuery()) {
        User user = new User();
        while (rs.next()) {
        user.setId(rs.getInt(1));
        user.setF_name(rs.getString(2));
            user.setL_name(rs.getString(3));
            user.setEmail(rs.getString(4));
            user.setBirthDay(rs.getDate(5));
            user.setDescription(rs.getString(6));
            user.setAvatarUrl(rs.getString(7));
            user.setUpdateTime(rs.getTimestamp(8));
            user.setCreateTime(rs.getTimestamp(9));
        }
        return user;
      }
    }
  }

  public static String getUsernameByuId(int userId, Connection conn) throws SQLException {
    String userName;
    if ((userName = LocalAuthDao.getUserNameIdByuId(userId, conn)).equals("")){
      userName = ThirdAuthDao.getUserNameByuId4Thid(userId, "google", conn);
    }
    return userName;
  }
  //insert
  public static boolean insertNewUser(User user, Connection conn) throws SQLException {
    try (PreparedStatement psmt = conn.prepareStatement(
      "insert into user(f_name, l_name, birthday, " +
        "description, avatar_url, update_time, create_time, email) VALUES (?,?,?,?,?,?,?,?)",
      Statement.RETURN_GENERATED_KEYS)) {
      psmt.setString(1, user.getF_name());
      psmt.setString(2, user.getL_name());
      psmt.setDate(3, user.getBirthDay());
      psmt.setString(4, user.getDescription());
      psmt.setString(5, user.getAvatarUrl());
      psmt.setTimestamp(6, user.getUpdateTime());
      psmt.setTimestamp(7, user.getCreateTime());
      psmt.setString(8, user.getEmail());
      int rowAffected = psmt.executeUpdate();
      if (rowAffected == 0) {
        return false;
      }
      try (ResultSet rs = psmt.getGeneratedKeys()) {
        rs.next();
        int userId = rs.getInt(1);
        user.setId(userId);
        return true;
      }
    }
  }

 public static boolean updateUserInfo(User user, Connection conn) {
    return true;
 }

 public static void deleteUserFollowerInfo(int uId, Connection conn) throws SQLException {
   try (PreparedStatement statement =
          conn.prepareStatement("delete from user_follow where user_id = ? or followed_user_id = ?")) {
     statement.setInt(1, uId);
     statement.setInt(2, uId);
     statement.executeUpdate();
   }
 }

  public static void deleteUserFavoutrite(int uId, Connection conn) throws SQLException {
    try (PreparedStatement statement =
           conn.prepareStatement("delete from user_favourite_article where user_id = ? ")) {
      statement.setInt(1, uId);
      statement.executeUpdate();
    }
  }

  public static void deleteUserLocalAuth(int uId, Connection conn) throws SQLException {
    try (PreparedStatement statement =
           conn.prepareStatement("delete from local_auth where user_id = ? ")) {
      statement.setInt(1, uId);
      statement.executeUpdate();
    }
  }

  public static void deleteUserThirdAuth(int uId, Connection conn) throws SQLException {
    try (PreparedStatement statement =
           conn.prepareStatement("delete from third_auth where user_id = ? ")) {
      statement.setInt(1, uId);
      statement.executeUpdate();
    }
  }

  public static void deleteUser(int uId, Connection conn) throws SQLException {
    try (PreparedStatement statement =
           conn.prepareStatement("delete from user where id = ? ")) {
      statement.setInt(1, uId);
      statement.executeUpdate();
    }
  }


}
