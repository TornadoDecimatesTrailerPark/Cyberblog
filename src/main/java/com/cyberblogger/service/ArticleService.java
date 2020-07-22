package com.cyberblogger.service;

import com.cyberblogger.model.Article;
import com.cyberblogger.model.ExtraTypeDict;
import com.cyberblogger.model.dao.ArticleDao;
import com.cyberblogger.model.dao.CommentDao;
import com.cyberblogger.model.dto.ArticleDTO;
import com.cyberblogger.model.dto.CommentDTO;
import com.cyberblogger.util.DBConnectionUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ArticleService {
  public static boolean addNewArticle(Article article) throws SQLException, IOException {
    try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
      return ArticleDao.insertArticle(article, conn);
    }
  }

  public static boolean updateArticle(Article article) throws SQLException, IOException {
    try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
      Article compareArticle = ArticleDao.getArticleInfo(article.getId(), conn);
      if (article.getAuthorId() != compareArticle.getAuthorId()){
        return false;
      }
      if(!isSameTwoArticles(article, compareArticle)){
        ArticleDao.updateArticleInfo(article, conn);
      }
      return true;
    }
  }

  public static Article convertToArticle(ArticleDTO articleDTO) {
    if (articleDTO.getId() == 0){
      articleDTO.setCreateTime(new Timestamp(System.currentTimeMillis()));
    }
    articleDTO.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    return articleDTO;
  }

  public static boolean addNewArticleAllInfo(Article article) throws SQLException, IOException {

    try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
      return ArticleDao.insertArticle(article, conn)
              && ArticleDao.insertArticleExtraInfo(article, conn)
              && ArticleDao.insertArticleTags(article, conn)
              && ArticleDao.insertArticleCounter(article, conn);
    }
  }

  public static boolean updateArticleAllInfo(Article article) throws IOException, SQLException {

    try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
      Article compareArticle = ArticleDao.getArticleInfo(article.getId(), conn);
      if (article.getAuthorId() != compareArticle.getAuthorId()){
        return false;
      }
      if(!isSameTwoArticles(article, compareArticle)){
        ArticleDao.updateArticleInfo(article, conn);
      }
      compareArticle.setExtraTypeList(ArticleDao.getAllExtraTypeInfoByArticleId(article.getId(), conn));
      if (!isSameExtraDictInfo(article, compareArticle)){
        if (compareArticle.getExtraTypeList().size() == 0) {
          ArticleDao.insertArticleExtraInfo(article, conn);
        } else {
          ArticleDao.updateArticleExtraInfo(article, conn);
        }
      }
      compareArticle.setTags(ArticleDao.getTags(article.getId(), conn));
      if (!isSamTagInfo(article.getTags(), compareArticle.getTags())){
        ArticleDao.deleteArticleTags(article.getId(), conn);
        ArticleDao.insertArticleTags(article, conn);
      }
      compareArticle.setCounterTypeList(ArticleDao.getAllCounterTypeInfoByArticleId(article.getId(), conn));
      if (compareArticle.getCounterTypeList().size() == 0){
        ArticleDao.insertArticleCounter(article, conn);
      }else {
        ArticleDao.updateArticleCounterInfo(article, conn);
      }
      return true;
    }
  }

  private static boolean isSamTagInfo(ArrayList<Integer> tags, ArrayList<Integer> compareTags) {
    if (tags.size() != compareTags.size()){
      return false;
    }
    for (Integer tag : tags) {
      boolean isFound = false;
      for (Integer compareTag : compareTags) {
        if (compareTag.equals(tag)) {
          isFound = true;
        }
      }
      if (!isFound) return false;
    }
    return true;
  }

  private static boolean isSameExtraDictInfo(Article article, Article compareArticle) {
    if(article.getExtraTypeList().size() != compareArticle.getExtraTypeList().size()){
      return false;
    }
    for (ExtraTypeDict extraTypeDict : article.getExtraTypeList()) {
      boolean isFound = false;
      for (ExtraTypeDict compareItem : compareArticle.getExtraTypeList()) {
        if (extraTypeDict.getDictTypeId() == compareItem.getDictTypeId()){
          isFound = true;
          if (extraTypeDict.getDictTypeVal() != compareItem.getDictTypeVal()){
            return false;
          }
        }
      }
      if (!isFound) return false;
    }
    return true;
  }

  private static boolean isSameTwoArticles(Article article, Article compareArticle) {
    return article.getTitle().equals(compareArticle.getTitle())
            && article.getAuthorId() == compareArticle.getAuthorId()
            && article.getContent().equals(compareArticle.getContent())
            && article.getExcerpt().equals(compareArticle.getExcerpt())
            && article.getCommentStatus() == compareArticle.getCommentStatus()
            && article.getPostType() == compareArticle.getPostType();

  }

  public static ArrayList<Article> getMostCounterNumArticles(int counterType, int topNum) throws IOException, SQLException {

    try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {

      return ArticleDao.getTopFive(counterType, topNum, conn);
    }
  }

  public static ArrayList<ArticleDTO> getPagedArticleInfo(int start, int size) throws IOException, SQLException {
    try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {

      return ArticleDao.getArticleInfoNoLogIn(start, size, conn);
    }
  }

  public static ArticleDTO getArticleInfoById(int articleId) throws IOException, SQLException {
    try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
      new ArticleDTO();
      ArticleDTO articleDTO;
      articleDTO = ArticleDao.getArticleInfoByArticleId(articleId, conn);
      articleDTO.setExtraTypeList(ArticleDao.getAllExtraTypeInfoByArticleId(articleId, conn));
      articleDTO.setTagsExt(ArticleDao.getTagsExt(articleId, conn));
      articleDTO.setCounterTypeList(ArticleDao.getAllCounterTypeInfoByArticleId(articleId, conn));
      articleDTO.setExtraTypeList(ArticleDao.getAllExtraTypeInfoByArticleId(articleId, conn));
      return articleDTO;
    }
  }

  public static List<ArticleDTO> getRecentArticlesByuId(int uId, int aId, int number) throws IOException, SQLException {
    try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
      List<ArticleDTO> articleDTOS = ArticleDao.getRecentArticlesByuId(uId, aId, number, conn);
      return articleDTOS;
    }
  }

  public static List<ArticleDTO> getArticlesByuId(int uId) throws IOException, SQLException {
    try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
      List<ArticleDTO> articleDTOS = ArticleDao.getArticlesByuId(uId, conn);
      return articleDTOS;
    }
  }

  public static int incCounterNumberByTypeAndId(int aId, int counterType) throws IOException, SQLException {

    try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
      int oldNumber = 0;
      List<ExtraTypeDict> countTypeList = ArticleDao.getAllCounterTypeInfoByArticleId(aId, conn);
      for (ExtraTypeDict extraTypeDict : countTypeList) {
        if (extraTypeDict.getDictTypeId() == counterType){
          int newNumber = extraTypeDict.getDictTypeVal() + 1;
          if (ArticleDao.updateArticleCounterInfo(aId, counterType, newNumber, conn)){
            return newNumber;
          }else {
            return extraTypeDict.getDictTypeVal();
          }
        }
      }
      return oldNumber;
    }
  }

  public static boolean addArticleFavForuId(int uId, int aId) throws IOException, SQLException {
    try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
      return ArticleDao.addArticleFav(uId, aId, conn);
    }
  }

  public static void deleteArticleById(int aId) throws IOException, SQLException, ParseException {
    try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
      ArticleDao.deleteArticleTags(aId, conn);
      ArticleDao.deleteArticleCounterInfo(aId, conn);
      ArticleDao.deleteArticleExtraType(aId, conn);
      List<CommentDTO> commentDTOS = CommentDao.getAllCommentsByaId(aId, conn);
      for (CommentDTO commentDTO : commentDTOS) {
        if (CommentService.getReplyNumByCommentId(commentDTO.getId()) != 0) {
          CommentService.deleteAllReplyByCommentId(commentDTO.getId());
        }
        CommentService.deleteOneComment(commentDTO.getId());
      }
      ArticleDao.deleteArticle(aId, conn);
    }
  }
}
