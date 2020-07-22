package com.cyberblogger.model.dao;

import com.cyberblogger.model.Article;
import com.cyberblogger.model.TagDict;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DictDao {

  public static ArrayList<TagDict> getTagDictList(Connection conn)
    throws SQLException {
    try (PreparedStatement statement =
           conn.prepareStatement("select * from tag")) {
      ArrayList<TagDict> tagDicts= new ArrayList<>();
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          tagDicts.add(new TagDict(rs.getInt(1), rs.getString(2)));

        }
        return tagDicts;
      }
    }
  }
}
