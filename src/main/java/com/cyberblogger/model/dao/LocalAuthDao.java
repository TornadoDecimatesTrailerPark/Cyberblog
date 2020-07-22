package com.cyberblogger.model.dao;

import com.cyberblogger.model.LocalAuth;

import java.sql.*;

public class LocalAuthDao {

  // create new localAuth
  public static LocalAuth getLocalAuthInfoByUsername(String userName, Connection conn)
      throws SQLException {
    try (PreparedStatement statement =
        conn.prepareStatement("select * from local_auth where user_name = ?")) {
      statement.setString(1, userName);
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          LocalAuth localAuth =
              new LocalAuth(
                  rs.getString(1),
                  rs.getString(2),
                  rs.getString(3),
                  rs.getInt(4),
                  rs.getInt(5),
                  rs.getInt(6));
          return localAuth;
        }
      }
    }
    return null;
  }

  public static int getUserIdByUsername(String userName, Connection conn) throws SQLException {
    try (PreparedStatement statement =
        conn.prepareStatement("select user_id from local_auth where user_name = ?")) {
      statement.setString(1, userName);
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          return rs.getInt(1);
        }
      }
    }
    return 0;
  }

  public static String getUserNameIdByuId(int uId, Connection conn) throws SQLException {
    try (PreparedStatement statement =
           conn.prepareStatement("select user_name from local_auth where user_id = ?")) {
      statement.setInt(1, uId);
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          return rs.getString(1);
        }
      }
    }
    return "";
  }

  // if username is unique
  public static boolean isUniqueUsername(String userName, Connection conn) throws SQLException {
    try (PreparedStatement statement =
        conn.prepareStatement("select count(*) from local_auth where user_name = ?")) {
      statement.setString(1, userName);
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          if (rs.getInt(1) == 0) return true;
        }
      }
    }
    return false;
  }

  public static boolean isLocalAuthUser(int uId, Connection conn) throws SQLException {
    try (PreparedStatement statement =
           conn.prepareStatement("select count(*) from local_auth where user_id = ?")) {
      statement.setInt(1, uId);
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          if (rs.getInt(1) == 1) return true;
        }
      }
    }
    return false;
  }
  // insert
  public static boolean insertLocalAuth(LocalAuth localAuth, Connection conn) throws SQLException {
    try (PreparedStatement psmt =
        conn.prepareStatement(
            "insert into local_auth(user_name, password, salt, salt_length, iteration_num, user_id) VALUES (?,?,?,?,?,?)")) {
      psmt.setString(1, localAuth.getUserName());
      psmt.setString(2, localAuth.getPassword());
      psmt.setString(3, localAuth.getSalt());
      psmt.setInt(4, localAuth.getSaltLength());
      psmt.setInt(5, localAuth.getIterationNum());
      psmt.setInt(6, localAuth.getUserId());
      int rowAffected = psmt.executeUpdate();
      if (rowAffected == 0) {
        return false;
      }
      return true;
    }
  }
}
