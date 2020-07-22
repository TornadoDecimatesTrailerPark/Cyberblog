package com.cyberblogger.controller;

import com.cyberblogger.model.dto.ArticleDTO;
import com.cyberblogger.service.ArticleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;



@WebServlet(name = "ModifyArticleServlet", urlPatterns = {"/modify"})
public class ModifyArticleServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String aId = request.getParameter("aId");
        HttpSession httpSession = request.getSession();
        httpSession.removeAttribute("articleSession");
        try {
            ArticleDTO articleDTO = ArticleService.getArticleInfoById(Integer.parseInt(aId));
            httpSession.setAttribute("articleSession", articleDTO);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("WEB-INF/view/modifyArticle.jsp").forward(request, response);
    }
}
