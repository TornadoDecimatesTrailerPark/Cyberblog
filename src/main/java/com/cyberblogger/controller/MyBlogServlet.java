package com.cyberblogger.controller;

import com.cyberblogger.model.dto.UserDTO;
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

@WebServlet(name = "MyBlogServlet", urlPatterns = {"/myblog"})
public class MyBlogServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        httpSession.removeAttribute("articleSession");
        int uId = (int) httpSession.getAttribute("uid");
        UserDTO userDTO = new UserDTO();
        // get data from database
        String createTime = "", birthDay = "";
        try {
            userDTO = UserAccountService.getUserInfoByuId(uId);
            if (StringUtils.isBlank(userDTO.getDescription())){
                userDTO.setDescription("This guy is lazy....");
            }
            createTime= DateUtils.formatDate(new Date(userDTO.getCreateTime().getTime()),"dd/MM/yyyy");
            if (userDTO.getBirthDay() != null){
                birthDay = DateUtils.formatDate(new Date(userDTO.getBirthDay().getTime()),"dd/MM/yyyy");
            } else {
                birthDay = "Secret";
            }

        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        request.setAttribute("userObj", userDTO);
        request.setAttribute("createTime", createTime);
        request.setAttribute("birthDay", birthDay);
        request.getRequestDispatcher("WEB-INF/view/myblog.jsp").forward(request, response);
    }
}
