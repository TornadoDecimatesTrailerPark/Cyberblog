package com.cyberblogger.service;

import com.cyberblogger.model.TagDict;
import com.cyberblogger.model.dao.DictDao;
import com.cyberblogger.util.DBConnectionUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class DictService {
  public static List<TagDict> getTags() throws IOException, SQLException {
    try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
      return DictDao.getTagDictList(conn);
    }
  }
}
