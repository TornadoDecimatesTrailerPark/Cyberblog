package com.cyberblogger.controller;

import com.cyberblogger.model.ThirdAuth;
import com.cyberblogger.model.User;
import com.cyberblogger.service.UserAccountService;
import com.cyberblogger.util.IdTokenVerifierAndParser;
import com.cyberblogger.util.PropertyUtils;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;


@WebServlet(
    name = "LoginServlet",
    urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    response.setContentType("text/html");
    try {
      String idToken = request.getParameter("id_token");
      String userName = request.getParameter("username");
      String password = request.getParameter("password");
      HttpSession httpSession = request.getSession(true);
      String name = null;
      if (idToken != null) {
        //come from google signIn
        GoogleIdToken.Payload payload = IdTokenVerifierAndParser.getPayLoadFroGoogle(idToken);
        assert payload != null;
        name = (String)payload.get("name");
        //insert into thirdAuth table
        //insert into user table
        User user = new User();
        ThirdAuth thirdAuth = new ThirdAuth();
        makeUserAndThirdAuth(idToken, name, payload, user, thirdAuth);
        UserAccountService.signUpAccountByThirdAuth(user,thirdAuth);
        httpSession.setAttribute("uid", user.getId());
      } else {
        //login with local password and username
        if (!UserAccountService.signInAccount(userName, password)){
          request.setAttribute("errorMsg", "error password or username");
          request.getRequestDispatcher("WEB-INF/view/login.jsp").forward(request, response);
        }else {
          name = userName;
          httpSession.setAttribute("uid", UserAccountService.getUidByUserName(userName));
        }
      }

      httpSession.setAttribute("userName", name);
      response.sendRedirect("./index");

    } catch (Exception e){
      response.setStatus(500);
      throw new ServletException(e);
    }
  }

  private void makeUserAndThirdAuth(String idToken, String name, GoogleIdToken.Payload payload, User user, ThirdAuth thirdAuth) throws IOException {
    // random generate avatar
    String avatarPath = PropertyUtils.getPropertyFromClasspath("app.properties", "pre_avatar_url");
    int randomNum = (int) (Math.random() * 4 + 1);
    user.setAvatarUrl(avatarPath + "avatar" + randomNum + ".png");
    user.setF_name(name.split(" ")[0]);
    user.setL_name(name.split(" ")[1]);
    user.setEmail(payload.getEmail());
    user.setCreateTime(new Timestamp(System.currentTimeMillis()));
    user.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    thirdAuth.setOauthAccessToken(idToken);
    thirdAuth.setOauthName("google");
    thirdAuth.setOauthId(name);
    thirdAuth.setOauthExpires(10000);
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.getRequestDispatcher("WEB-INF/view/login.jsp").forward(request, response);
  }
}
