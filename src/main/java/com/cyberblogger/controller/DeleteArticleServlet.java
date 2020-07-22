package com.cyberblogger.controller;

import com.cyberblogger.model.dto.JsonResult;
import com.cyberblogger.service.ArticleService;
import com.cyberblogger.util.JsonResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;


@WebServlet(name = "DeleteArticleServlet", urlPatterns = {"/deleteArticle"})
public class DeleteArticleServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String aId = request.getParameter("aId");
      JsonResult<Integer> result = new JsonResult<>(1, "success");
      try {
        ArticleService.deleteArticleById(Integer.parseInt(aId));
        JsonResponse.send(response, result);
      } catch (SQLException | ParseException e) {
        result.setCode(-1);
        result.setMessage(e.getMessage());
        JsonResponse.send(response, result);
      }
    }
}
