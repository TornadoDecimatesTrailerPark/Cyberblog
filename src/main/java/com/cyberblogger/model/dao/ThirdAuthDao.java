package com.cyberblogger.model.dao;

import com.cyberblogger.model.LocalAuth;
import com.cyberblogger.model.ThirdAuth;

import java.sql.*;


public class ThirdAuthDao {

  public static boolean insertThirdAuth(ThirdAuth thirdAuth, Connection conn) throws SQLException {
    try (PreparedStatement psmt =
           conn.prepareStatement(
             "insert into third_auth(user_id, oauth_name, oauth_id, " +
               "oauth_access_token, oauth_expires) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
      psmt.setInt(1, thirdAuth.getUserId());
      psmt.setString(2, thirdAuth.getOauthName());
      psmt.setString(3, thirdAuth.getOauthId());
      psmt.setString(4, thirdAuth.getOauthAccessToken());
      psmt.setInt(5, thirdAuth.getOauthExpires());
      int rowAffected = psmt.executeUpdate();
      if (rowAffected == 0) {
        return false;
      }
      try (ResultSet rs = psmt.getGeneratedKeys()) {
        rs.next();
        int thirdAuthId = rs.getInt(1);
        thirdAuth.setId(thirdAuthId);
        return true;
      }
    }
  }

  public static int getUserIdByUsername(String userName, String oauthName, Connection conn) throws SQLException {
    try (PreparedStatement statement =
           conn.prepareStatement("select user_id from third_auth where oauth_id = ? and oauth_name = ?")) {
      statement.setString(1, userName);
      statement.setString(2, oauthName);
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          return rs.getInt(1);
        }
      }
    }
    return 0;
  }

  public static boolean isUniqueTokenId(String oAuthName, String oAuthId, Connection conn) throws SQLException {
    try (PreparedStatement statement =
           conn.prepareStatement("select count(*) from third_auth where oauth_name = ? and oauth_id = ?")) {
      statement.setString(1, oAuthName);
      statement.setString(2, oAuthId);
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          if (rs.getInt(1) == 0) return true;
        }
      }
    }
    return false;
  }

  public static String getUserNameByuId4Thid(int uId, String oAuthName, Connection conn) throws SQLException {
    try (PreparedStatement statement =
           conn.prepareStatement("select oauth_name from third_auth where user_id = ? and oauth_name = ?")) {
      statement.setInt(1, uId);
      statement.setString(2, oAuthName);
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          return rs.getString(1);
        }
      }
    }
    return "";
  }
}
