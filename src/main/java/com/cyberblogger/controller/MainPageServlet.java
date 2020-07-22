package com.cyberblogger.controller;

import com.cyberblogger.model.Article;
import com.cyberblogger.service.ArticleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


@WebServlet(name = "MainPageServlet", urlPatterns = {"/index"})
public class MainPageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      //get session
      HttpSession httpSession = request.getSession();
      int uid = 0;
      if (httpSession.getAttribute("uid") != null) {
        uid = (int) httpSession.getAttribute("uid");
      }
//      if (uid != 0) {
//        // login article show
//      } else {
        // no login
        try {
          ArrayList<Article> mostCounterArticles = ArticleService.getMostCounterNumArticles(4, 5);
          request.setAttribute("topArticles", mostCounterArticles);
        } catch (SQLException e) {
          e.printStackTrace();
        }
//      }
      request.getRequestDispatcher("WEB-INF/view/main.jsp").forward(request, response);
    }
}
