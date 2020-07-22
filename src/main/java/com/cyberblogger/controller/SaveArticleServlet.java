package com.cyberblogger.controller;

import com.cyberblogger.model.Article;
import com.cyberblogger.model.User;
import com.cyberblogger.model.dto.ArticleDTO;
import com.cyberblogger.model.dto.JsonResult;
import com.cyberblogger.service.ArticleService;
import com.cyberblogger.service.UserAccountService;
import com.cyberblogger.util.JsonResponse;
import com.cyberblogger.util.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import static com.cyberblogger.service.ArticleService.convertToArticle;


@WebServlet(name = "SaveArticleServlet", urlPatterns = {"/saveArticle"})
public class SaveArticleServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String requestJsonStr = JsonResponse.recieve(request);
      JsonResult<Integer> result = new JsonResult<>(1, "success");
      ArticleDTO articleDTO = JsonUtils.json2Bean(requestJsonStr, ArticleDTO.class);
      if (articleDTO == null) {
        result.setCode(-1);
        result.setMessage("articleDTO is null");
        JsonResponse.send(response, result);
        return;
      }
      Article article = convertToArticle(articleDTO);
      try {
        if (article.getId() == 0){
          //insert article
          if(ArticleService.addNewArticle(article)){
            result.setData(article.getId());
          }else {
            result.setMessage("adding new article failed");
            result.setCode(-1);
          }
        }else {
          //edit article update
          if (!ArticleService.updateArticle(article)){
            result.setMessage("updating article failed");
            result.setCode(-1);
          }
        }
      } catch (SQLException e) {
        result.setCode(-1);
        result.setMessage(e.getMessage());
        e.printStackTrace();
        JsonResponse.send(response, result);
        return;
      }
      JsonResponse.send(response, result);
    }



  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
