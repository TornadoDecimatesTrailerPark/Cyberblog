package com.cyberblogger.model.dao;

import com.cyberblogger.model.Article;
import com.cyberblogger.model.ExtraTypeDict;
import com.cyberblogger.model.TagDict;
import com.cyberblogger.model.User;
import com.cyberblogger.model.dto.ArticleDTO;
import com.cyberblogger.util.DBConnectionUtils;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ArticleDao {
  public static ArrayList<ArticleDTO> getArticleInfoNoLogIn(int curPageNum, int incrNum, Connection conn)
      throws SQLException {
    try (PreparedStatement statement =
        conn.prepareStatement(
            "select a.id, a.title, a.content, a.excerpt, a.update_time, a.author_id, u.avatar_url, "
                + "u.f_name, u.l_name "
                + "from article a inner join user u on a.author_id = u.id "
                + "where a.post_type = 0 "
                + "order by a.update_time desc limit ?,?")) {
      statement.setInt(1, curPageNum);
      statement.setInt(2, incrNum);
      ArrayList<ArticleDTO> articles = new ArrayList<>();
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          ArticleDTO articleDTO = new ArticleDTO();
          articleDTO.setId(rs.getInt(1));
          articleDTO.setTitle(rs.getString(2));
          articleDTO.setContent(rs.getString(3));
          articleDTO.setExcerpt(rs.getString(4));
          articleDTO.setUpdateTime(rs.getTimestamp(5));
          User user = new User();
          user.setId(rs.getInt(6));
          user.setAvatarUrl(rs.getString(7));
          user.setF_name(rs.getString(8));
          user.setL_name(rs.getString(9));
          articleDTO.setUser(user);
          articles.add(articleDTO);
        }
        return articles;
      }
    }
  }

  public static int getArticleCounter(int articleId, int counterType, Connection conn)
      throws SQLException {
    try (PreparedStatement statement =
        conn.prepareStatement(
            "select counter_num from article_counter where article_id = ? and counter_type = ?")) {
      statement.setInt(1, articleId);
      statement.setInt(1, counterType);
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          return rs.getInt(1);
        }
      }
      return 0;
    }
  }

  public static ArticleDTO getArticleInfoByArticleId(int articleId, Connection conn) throws SQLException {
    try (PreparedStatement statement =
           conn.prepareStatement(
             "select a.id, a.title, a.content, a.excerpt, a.comment_status, " +
               "a.update_time, a.author_id, u.avatar_url, "
               + "u.f_name, u.l_name "
               + "from article a inner join user u on a.author_id = u.id "
               + "where a.id = ?")) {
      statement.setInt(1, articleId);
      ArticleDTO articleDTO = new ArticleDTO();
      try (ResultSet rs = statement.executeQuery()) { // rs就是取出来的sql
        while (rs.next()) {
          articleDTO.setId(rs.getInt(1));
          articleDTO.setTitle(rs.getString(2));
          articleDTO.setContent(rs.getString(3));
          articleDTO.setExcerpt(rs.getString(4));
          articleDTO.setCommentStatus(rs.getInt(5));
          articleDTO.setUpdateTime(rs.getTimestamp(6));
          User user = new User();
          user.setId(rs.getInt(7));
          user.setAvatarUrl(rs.getString(8));
          user.setF_name(rs.getString(9));
          user.setL_name(rs.getString(10));
          articleDTO.setUser(user);
        }
        return articleDTO;
      }
    }
  }

  public static ArrayList<ExtraTypeDict> getAllExtraTypeInfoByArticleId(
      int articleId, Connection conn) throws SQLException {
    ArrayList<ExtraTypeDict> extraTypeDicts = new ArrayList<>();
    try (PreparedStatement statement =
        conn.prepareStatement(
            "select a.dict_type, a.dict_value, etd.dict_name from article_extra_type a "
                + "inner join extra_type_dict etd on a.dict_type = etd.id "
                + "where article_id = ?")) {
      statement.setInt(1, articleId);

      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          extraTypeDicts.add(new ExtraTypeDict(rs.getInt(1), rs.getString(3), rs.getInt(2)));
        }
      }
    }
    return extraTypeDicts;
  }

  public static ArrayList<ExtraTypeDict> getAllCounterTypeInfoByArticleId(
      int articleId, Connection conn) throws SQLException {
    ArrayList<ExtraTypeDict> counterDicts = new ArrayList<>();
    try (PreparedStatement statement =
        conn.prepareStatement(
            "select a.counter_type, a.counter_num, etd.dict_name from article_counter a "
                + "inner join extra_type_dict etd on a.counter_type = etd.id "
                + "where article_id = ?")) {
      statement.setInt(1, articleId);

      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          counterDicts.add(new ExtraTypeDict(rs.getInt(1), rs.getString(3), rs.getInt(2)));
        }
      }
    }
    return counterDicts;
  }

  public static boolean insertArticle(Article article, Connection conn) throws SQLException {
    try (PreparedStatement statement =
        conn.prepareStatement(
            "insert into article(title, author_id, content, excerpt, "
                + "comment_status, post_type, create_time, update_time) VALUES (?,?,?,?,?,?,?,?)",
            Statement.RETURN_GENERATED_KEYS)) {
      statement.setString(1, article.getTitle());
      statement.setInt(2, article.getAuthorId());
      statement.setString(3, article.getContent());
      statement.setString(4, article.getExcerpt());
      statement.setInt(5, article.getCommentStatus());
      statement.setInt(6, article.getPostType());
      statement.setTimestamp(7, article.getCreateTime());
      statement.setTimestamp(8, article.getUpdateTime());
      int rowAffected = statement.executeUpdate();
      if (rowAffected == 0) {
        return false;
      }
      try (ResultSet rs = statement.getGeneratedKeys()) {
        rs.next();
        int articleId = rs.getInt(1);
        article.setId(articleId);
        return true;
      }
    }
  }

  public static boolean insertArticleExtraInfo(Article article, Connection conn)
      throws SQLException {
    conn.setAutoCommit(false);
    try (PreparedStatement statement =
        conn.prepareStatement(
            "insert into article_extra_type(article_id, dict_type, dict_value) VALUES (?,?,?)")) {
      return batchExtraTypeInfo(article, conn, statement, article.getExtraTypeList(),1, 2, 3);
    }
  }

  public static boolean insertArticleCounter(Article article, Connection conn) throws SQLException {
    conn.setAutoCommit(false);
    try (PreparedStatement statement =
        conn.prepareStatement(
            "insert into article_counter(article_id, counter_type, counter_num) VALUES (?,?,?)")) {
      return batchExtraTypeInfo(article, conn, statement, article.getCounterTypeList(), 1, 2, 3);
    }
  }

  public static boolean updateArticleCounterInfo(int aId, int counterType, int counter_num, Connection conn) throws SQLException {
    try (PreparedStatement statement =
           conn.prepareStatement("update article_counter set counter_num = ? where article_id = ? and counter_type = ?")){
      statement.setInt(1, counter_num);
      statement.setInt(2, aId);
      statement.setInt(3, counterType);
      int rowAffected = statement.executeUpdate();
      if (rowAffected == 0) {
        return false;
      }
      return true;
    }
  }

  public static boolean updateArticleExtraInfo(Article article, Connection conn)
      throws SQLException {
    conn.setAutoCommit(false);
    try (PreparedStatement statement =
        conn.prepareStatement(
            "update article_extra_type set dict_value = ? where article_id = ? and dict_type = ?")) {
      return batchExtraTypeInfo(article, conn, statement, article.getExtraTypeList(), 2, 3, 1);
    }
  }

  public static boolean updateArticleCounterInfo(Article article, Connection conn)
      throws SQLException {
    conn.setAutoCommit(false);
    try (PreparedStatement statement =
        conn.prepareStatement(
            "update article_counter set counter_num = ? where article_id = ? and counter_type = ?")) {
      return batchExtraTypeInfo(article, conn, statement, article.getCounterTypeList(), 2, 3, 1);
    }
  }

  public static boolean insertArticleTags(Article article, Connection conn) throws SQLException {
    conn.setAutoCommit(false);
    try (PreparedStatement statement =
        conn.prepareStatement("insert into article_tag(article_id, tag_id) VALUES (?,?)")) {
      return batchTags(article, conn, statement);
    }
  }

  public static void deleteArticleTags(int articleId, Connection conn) throws SQLException {
    try (PreparedStatement statement =
        conn.prepareStatement("delete from article_tag where article_id = ?")) {
      statement.setInt(1, articleId);
      statement.executeUpdate();
    }
  }

  private static boolean batchTags(Article article, Connection conn, PreparedStatement statement)
      throws SQLException {
    for (int tag : article.getTags()) {
      statement.setInt(1, article.getId());
      statement.setInt(2, tag);
      statement.addBatch();
    }
    int[] counts = statement.executeBatch();
    for (int count : counts) {
      if (count != -2 && count < 0) {
        return false;
      }
    }
    conn.commit();
    conn.setAutoCommit(true);
    return true;
  }

  public static ArrayList<Integer> getTags(int articleId, Connection conn) throws SQLException {
    ArrayList<Integer> temp = new ArrayList<>();
    try (PreparedStatement statement =
        conn.prepareStatement("select tag_id from article_tag where article_id = ? ")) {
      statement.setInt(1, articleId);
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          temp.add(rs.getInt(1));
        }
        return temp;
      }
    }
  }

  public static ArrayList<TagDict> getTagsExt(int articleId, Connection conn) throws SQLException {
    ArrayList<TagDict> temp = new ArrayList<>();
    try (PreparedStatement statement =
           conn.prepareStatement("select at.tag_id, t.tag_name from article_tag at " +
             "inner join tag t on at.tag_id = t.id where article_id = ? ")) {
      statement.setInt(1, articleId);
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          temp.add(new TagDict(rs.getInt(1), rs.getString(2)));
        }
        return temp;
      }
    }
  }

  private static boolean batchExtraTypeInfo(
      Article article, Connection conn, PreparedStatement statement,
      ArrayList<ExtraTypeDict> typeList, int i1, int i2, int i3)
      throws SQLException {
    for (ExtraTypeDict extraTypeDict : typeList) {
      statement.setInt(i1, article.getId());
      statement.setInt(i2, extraTypeDict.getDictTypeId());
      statement.setInt(i3, extraTypeDict.getDictTypeVal());
      statement.addBatch();
    }
    int[] counts = statement.executeBatch();
    for (int count : counts) {
      if (count != -2 && count < 0) {
        return false;
      }
    }
    conn.commit();
    conn.setAutoCommit(true);
    return true;
  }

  public static boolean updateArticleInfo(Article article, Connection conn) throws SQLException {
    try (PreparedStatement statement =
        conn.prepareStatement(
            "update article set title = ?, content = ?, excerpt = ?, comment_status = ?, "
                + "post_type = ?, update_time = ?  where id = ?")) {
      statement.setString(1, article.getTitle());
      statement.setString(2, article.getContent());
      statement.setString(3, article.getExcerpt());
      statement.setInt(4, article.getCommentStatus());
      statement.setInt(5, article.getPostType());
      statement.setTimestamp(6, article.getUpdateTime());
      statement.setInt(7, article.getId());
      int rowAffected = statement.executeUpdate();
      return rowAffected != 1; // should effect one row
    }
  }

  public static Article getArticleInfo(int articleId, Connection conn) throws SQLException {
    try (PreparedStatement statement =
        conn.prepareStatement(
            "select title, author_id, content, excerpt, "
                + "comment_status, post_type, create_time, update_time from article where id = ?")) {
      statement.setInt(1, articleId);
      try (ResultSet rs = statement.executeQuery()) {
        Article article = new Article();
        while (rs.next()) {
          article.setId(articleId);
          article.setTitle(rs.getString(1));
          article.setAuthorId(rs.getInt(2));
          article.setContent(rs.getString(3));
          article.setExcerpt(rs.getString(4));
          article.setCommentStatus(rs.getInt(5));
          article.setPostType(rs.getInt(6));
          article.setCreateTime(rs.getTimestamp(7));
          article.setUpdateTime(rs.getTimestamp(8));
        }
        return article;
      }
    }
  }

  public static ArrayList<Article> getTopFive(int countType, int topNum, Connection conn) throws SQLException {
    try (PreparedStatement statement =
        conn.prepareStatement(
            "select article.id, article.title from article "
                + "inner join article_counter  on article.id = article_counter.article_id"
                + " where article_id = id and counter_type = ? and post_type = 0"
                + " order by article_counter.counter_num desc limit ?")) {
      statement.setInt(1, countType);
      statement.setInt(2, topNum);
      ArrayList<Article> articles = new ArrayList<>();
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          Article article = new Article();
          article.setId(rs.getInt(1));
          article.setTitle(rs.getString(2));
          articles.add(article);
        }
        return articles;
      }
    }
  }

  public static ArrayList<ArticleDTO> getRecentArticlesByuId(int uId, int aId, int number, Connection conn) throws SQLException {
    try (PreparedStatement statement =
           conn.prepareStatement(
             "select a.id, a.title from article a "
               + "inner join user u  on a.author_id = u.id"
               + " where u.id = ? and a.post_type = 0 and a.id != ?"
               + " order by a.update_time desc limit ?")) {
      statement.setInt(1, uId);
      statement.setInt(2, aId);
      statement.setInt(3, number);
      ArrayList<ArticleDTO> articles = new ArrayList<>();
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          ArticleDTO article = new ArticleDTO();
          article.setId(rs.getInt(1));
          article.setTitle(rs.getString(2));
          articles.add(article);
        }
        return articles;
      }

    }

  }

  public static boolean addArticleFav(int uId, int aId, Connection conn) throws SQLException {
    try (PreparedStatement statement =
           conn.prepareStatement(
             "insert into user_favourite_article(user_id, article_id, status) VALUES (?, ?, ?)")){
      statement.setInt(1, uId);
      statement.setInt(2, aId);
      statement.setInt(3, 0);
      int rowAffected = statement.executeUpdate();
      if (rowAffected == 0) {
        return false;
      }
      return true;
    }
  }

  public static ArrayList<ArticleDTO> getArticlesByuId(int uId, Connection conn) throws SQLException {
    try (PreparedStatement statement =
           conn.prepareStatement(
             "select a.id, a.title, a.excerpt, a.update_time, a.post_type from article a "
               + "inner join user u  on a.author_id = u.id"
               + " where u.id = ? "
               + " order by a.update_time desc")) {
      statement.setInt(1, uId);
      ArrayList<ArticleDTO> articles = new ArrayList<>();
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          ArticleDTO article = new ArticleDTO();
          article.setId(rs.getInt(1));
          article.setTitle(rs.getString(2));
          article.setExcerpt(rs.getString(3));
          article.setUpdateTime(rs.getTimestamp(4));
          article.setPostType(rs.getInt(5));
          articles.add(article);
        }
        return articles;
      }

    }

  }

  public static void deleteArticleExtraType(int aId, Connection conn) throws SQLException {
    try (PreparedStatement statement =
           conn.prepareStatement("delete from article_extra_type where article_id = ?")) {
      statement.setInt(1, aId);
      statement.executeUpdate();
    }
  }

  public static void deleteArticleCounterInfo(int aId, Connection conn) throws SQLException {
    try (PreparedStatement statement =
           conn.prepareStatement("delete from article_counter where article_id = ?")) {
      statement.setInt(1, aId);
      statement.executeUpdate();
    }
  }

  public static void deleteArticleFavouriteInfo(int aId, Connection conn) throws SQLException {
    try (PreparedStatement statement =
           conn.prepareStatement("delete from user_favourite_article where article_id = ?")) {
      statement.setInt(1, aId);
      statement.executeUpdate();
    }
  }

  public static void deleteArticle(int aId, Connection conn) throws SQLException {
    try (PreparedStatement statement =
           conn.prepareStatement("delete from article where id = ?")) {
      statement.setInt(1, aId);
      statement.executeUpdate();
    }
  }
}
