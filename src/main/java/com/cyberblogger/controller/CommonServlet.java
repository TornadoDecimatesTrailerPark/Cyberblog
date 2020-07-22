package com.cyberblogger.controller;

import com.cyberblogger.model.TagDict;
import com.cyberblogger.model.dto.ArticleDTO;
import com.cyberblogger.model.dto.JsonResult;
import com.cyberblogger.service.ArticleService;
import com.cyberblogger.service.DictService;
import com.cyberblogger.service.UserAccountService;
import com.cyberblogger.util.JsonResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


@WebServlet(
    name = "CommonServlet",
    urlPatterns = {"/common"})
public class CommonServlet extends HttpServlet {
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String methodName = request.getParameter("method");
    if (methodName == null || methodName.isEmpty()) {
      throw new ServletException("No method parameter");
    }

    Class c = this.getClass();

    Method method = null;
    try {
      method = c.getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
    } catch (Exception e) {
      throw new RuntimeException("parameter: " + methodName + " doesn't exist");
    }

    /*
     * invoke the parameter which represents the real method
     */
    try {
      method.invoke(this, request, response);
    } catch (Exception e) {
      System.out.println("invoking " + methodName + " throw exception");
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doPost(request, response);
  }

  private void getTagDicts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    JsonResult<List<TagDict>> result = new JsonResult<>(1, "success");
    try {
      List<TagDict> tagDictList = DictService.getTags();
      result.setData(tagDictList);
    } catch (SQLException e) {
      result.setCode(-1);
      result.setMessage(e.getMessage());
      e.printStackTrace();
      JsonResponse.send(response, result);
      return;
    }
    JsonResponse.send(response, result);
  }

  private void getNewestArticlePaged(HttpServletRequest request, HttpServletResponse response) throws IOException{
    String size = request.getParameter("size");
    String start = request.getParameter("start");
    JsonResult<List<ArticleDTO>> result = new JsonResult<>(1, "success");
    ArrayList<ArticleDTO> articleDTOS = new ArrayList<>();
    try{
      articleDTOS = ArticleService.getPagedArticleInfo(Integer.parseInt(start), Integer.parseInt(size));
      result.setData(articleDTOS);
      JsonResponse.send(response, result);
    } catch (SQLException  | NumberFormatException e){
      result.setCode(-1);
      result.setMessage(e.getMessage());
      JsonResponse.send(response, result);
      return;
    }

  }

  private void incrNum(HttpServletRequest request, HttpServletResponse response) throws IOException {
    HttpSession httpSession = request.getSession();
    Object uId = httpSession.getAttribute("uid");
    String aId = request.getParameter("aId");
    String counterType = request.getParameter("type");
    JsonResult<Integer> result = new JsonResult<>(1, "success");
    // 7 is favourite dict val
    try {
      if (counterType.equals("7") && uId != null){
        ArticleService.addArticleFavForuId((int)uId, Integer.parseInt(aId));
      }
      int newNum = ArticleService.incCounterNumberByTypeAndId(Integer.parseInt(aId), Integer.parseInt(counterType));
      result.setData(newNum);
      JsonResponse.send(response, result);
    } catch (SQLException | NumberFormatException e) {
      result.setCode(-1);
      result.setMessage(e.getMessage());
      JsonResponse.send(response, result);
      return;
    }
  }

  private void getAllArticleByuId(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String uId = request.getParameter("uId");
    JsonResult<List<ArticleDTO>> result = new JsonResult<>(1, "success");
    if (uId != null) {
      try {
        List<ArticleDTO> articleDTOS = ArticleService.getArticlesByuId(Integer.parseInt(uId));
        result.setData(articleDTOS);
        JsonResponse.send(response, result);
      } catch (SQLException | NumberFormatException e) {
        result.setCode(-1);
        result.setMessage(e.getMessage());
        JsonResponse.send(response, result);
        return;
      }
    }
  }

  private void delUserByuId(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String uId = request.getParameter("uId");
    JsonResult<Integer>result = new JsonResult<>(1, "success");
    if (uId != null) {
      try {
        UserAccountService.deleteUserInfo(Integer.parseInt(uId));
        JsonResponse.send(response, result);
      } catch (SQLException | NumberFormatException | ParseException e) {
        result.setCode(-1);
        result.setMessage(e.getMessage());
        JsonResponse.send(response, result);
        return;
      }
    }
  }
}
