package com.cyberblogger.controller;

import com.cyberblogger.model.Follower;
import com.cyberblogger.model.dao.FollowerDao;
import com.cyberblogger.model.dto.ArticleDTO;
import com.cyberblogger.model.dto.CommentDTO;
import com.cyberblogger.service.ArticleService;
import com.cyberblogger.service.CommentService;
import com.cyberblogger.service.UserAccountService;
import com.cyberblogger.util.DateUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;


@WebServlet(
    name = "BrowserArticleServlet",
    urlPatterns = {"/article"})
public class BrowserArticleServlet extends HttpServlet {
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {}

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String articleId = request.getParameter("aId");
    HttpSession httpSession = request.getSession();
    Integer userId = (Integer) httpSession.getAttribute("uid");
    if (articleId == null) {
      throw new ServletException("no ArticleId");
    }
    try {
      int aId = Integer.parseInt(articleId);
      ArticleDTO articleDTO = ArticleService.getArticleInfoById(aId);
      List<ArticleDTO> userRelatedArticles =
          ArticleService.getRecentArticlesByuId(
              articleDTO.getUser().getId(), articleDTO.getId(), 5);
      request.setAttribute("article", articleDTO);
      request.setAttribute(
          "formtUpdateDate",
          DateUtils.formatDate(new Date(articleDTO.getUpdateTime().getTime()), "dd/MM/yyyy"));
      request.setAttribute("userArticles", userRelatedArticles);
      List<CommentDTO> commentDTOS = CommentService.getCommentsByaId(aId);
      // if it's be commented
      if (articleDTO.getCommentStatus() == -1 || userId == null) {
        request.setAttribute("bCommented", 0);
      } else {
        if (userId != 0 && articleDTO.getCommentStatus() == 1) {
          int uId = userId;
          List<Follower> afollower = UserAccountService.getFollowerByuId(articleDTO.getAuthorId());
          boolean isFollower = false;
          for (Follower follower : afollower) {
            if (follower.getFollowerId() == uId) {
              isFollower = true;
              break;
            }
          }
          request.setAttribute("bCommented", isFollower ? 1 : 0);
        } else if (articleDTO.getCommentStatus() == 0) {
          request.setAttribute("bCommented", 1);
        }
      }
      request.setAttribute("comments", commentDTOS);
    } catch (SQLException | NumberFormatException | ParseException e) {
      e.printStackTrace();
    }

    request.getRequestDispatcher("WEB-INF/view/readarticle.jsp").forward(request, response);
  }
}
