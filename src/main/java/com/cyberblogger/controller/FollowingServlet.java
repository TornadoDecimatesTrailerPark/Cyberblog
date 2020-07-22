package com.cyberblogger.controller;

import com.cyberblogger.model.Follower;
import com.cyberblogger.model.User;
import com.cyberblogger.model.dao.FollowerDao;
import com.cyberblogger.util.DBConnectionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


@WebServlet(name = "FollowingServlet", urlPatterns = {"/following"})
public class FollowingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("aid");
        try {
//            User user = FollowerDao.getUserInfoById(Integer.parseInt(userId), DBConnectionUtils.getConnectionFromClasspath("connection.properties"));
            ArrayList<Follower> followers = FollowerDao.getFollowingByUserId(Integer.parseInt(userId), DBConnectionUtils.getConnectionFromClasspath("connection.properties"));
//            request.setAttribute("user", user);
            request.setAttribute("followers", followers);
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("WEB-INF/view/following.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}

