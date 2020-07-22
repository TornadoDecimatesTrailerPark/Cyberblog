package com.cyberblogger.controller;

import com.cyberblogger.model.User;
import com.cyberblogger.model.dto.JsonResult;
import com.cyberblogger.model.dto.SignUpDTO;
import com.cyberblogger.service.UserAccountService;
import com.cyberblogger.util.JsonResponse;
import com.cyberblogger.util.JsonUtils;
import com.cyberblogger.util.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;


@WebServlet(
    name = "SignUpServlet",
    urlPatterns = {"/signUp"})
public class SignUpServlet extends HttpServlet {
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // get userinfo from http body
    // construct new User entity
    // use UserAccountService.signUpAccount(User, userName, password); to insert data to database
    // return json response to front-side
    String requestJsonStr = JsonResponse.recieve(request);
    JsonResult<String> result = new JsonResult<>(1, "success");
    SignUpDTO signUpDTO = JsonUtils.json2Bean(requestJsonStr, SignUpDTO.class);
    if (signUpDTO == null) {
      result.setCode(-1);
      result.setMessage("SignUpDTO is null");
      JsonResponse.send(response, result);
      return;
    }
    if (StringUtils.isBlank(signUpDTO.getFirstName())
        || StringUtils.isBlank(signUpDTO.getSecondName())
        || StringUtils.isBlank(signUpDTO.getUsername())
        || StringUtils.isBlank(signUpDTO.getPassword())) {
      result.setCode(-1);
      result.setMessage("name, username or password can not be empty");
      JsonResponse.send(response, result);
      return;
    }
    try {
      if (!UserAccountService.isUniqueUsername(signUpDTO.getUsername())){
        result.setCode(-1);
        result.setMessage("username is duplicate");
        JsonResponse.send(response, result);
        return;
      }
    } catch (SQLException e) {
      result.setCode(-1);
      result.setMessage(e.getMessage());
      e.printStackTrace();
      JsonResponse.send(response, result);
    }
    User user = convertDTOtoUser(signUpDTO);
    try {
      UserAccountService.signUpAccount(user, signUpDTO.getUsername(), signUpDTO.getPassword());
      HttpSession httpSession = request.getSession(true);
      httpSession.setAttribute("userName", signUpDTO.getUsername());
      httpSession.setAttribute("uid", user.getId());
    } catch (SQLException e) {
      result.setCode(-1);
      result.setMessage(e.getMessage());
      e.printStackTrace();
      JsonResponse.send(response, result);
      return;
    }

    JsonResponse.send(response, result);
  }

  private User convertDTOtoUser(SignUpDTO signUpDTO) throws IOException {
    // random generate avatar
    String avatarPath = PropertyUtils.getPropertyFromClasspath("app.properties", "pre_avatar_url");
    int randomNum = (int) (Math.random() * 4 + 1);
    signUpDTO.setAvatarUrl(avatarPath + "avatar" + randomNum + ".png");
    //
    return new User(
        signUpDTO.getFirstName(),
        signUpDTO.getSecondName(),
        signUpDTO.getEmail(),
        signUpDTO.getBirthday(),
        signUpDTO.getDescription(),
        signUpDTO.getAvatarUrl(),
        new Timestamp(System.currentTimeMillis()),
        new Timestamp(System.currentTimeMillis()));
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.getRequestDispatcher("WEB-INF/view/signUp.jsp").forward(request, response);
  }
}
