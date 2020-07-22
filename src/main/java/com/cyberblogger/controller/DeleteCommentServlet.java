package com.cyberblogger.controller;

import com.cyberblogger.model.dto.JsonResult;
import com.cyberblogger.model.dto.ReplyDTO;
import com.cyberblogger.service.ArticleService;
import com.cyberblogger.service.CommentService;
import com.cyberblogger.util.JsonResponse;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(
    name = "DeleteCommentServlet",
    urlPatterns = {"/deleteComment"})
public class DeleteCommentServlet extends HttpServlet {
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {}

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String cId = request.getParameter("cId");
    String rId = request.getParameter("rId");
    String aId = request.getParameter("aId");
    HttpSession httpSession = request.getSession();
    Integer uId = (Integer) httpSession.getAttribute("uid");
    JsonResult<Integer> result = new JsonResult<>(1, "success");
    if (StringUtils.isEmpty(cId) && StringUtils.isEmpty(rId)) {
      result.setCode(-1);
      result.setMessage("Input parameter is wrong");
      JsonResponse.send(response, result);
      return;
    }
    if (StringUtils.isEmpty(aId)){
      result.setCode(-1);
      result.setMessage("Input parameter is wrong");
      JsonResponse.send(response, result);
      return;
    }
    boolean enAbleDelete = false;
    try {
      enAbleDelete = geEnableDelete(cId, rId, aId, uId);
    } catch (SQLException e) {
      result.setCode(-1);
      result.setMessage(e.getMessage());
      JsonResponse.send(response, result);
    }
    List<Integer> childrenList = new ArrayList<>();
    if (enAbleDelete) {
      if (StringUtils.isEmpty(cId)) {
        try {
          int rrId = Integer.parseInt(rId);
          CommentService.recursiveSearch(rrId, childrenList);
          if (childrenList.size() != 0) {
            for (Integer item : childrenList) {
              CommentService.deleteOneReply(item);
            }
          }
          CommentService.deleteOneReply(rrId);
          JsonResponse.send(response, result);
        } catch (SQLException | NumberFormatException e) {
          result.setCode(-1);
          result.setMessage(e.getMessage());
          JsonResponse.send(response, result);
        }
      } else {
        try {
          int rcId = Integer.parseInt(cId);
          if (CommentService.getReplyNumByCommentId(rcId) != 0) {
            CommentService.deleteAllReplyByCommentId(rcId);
          }
          CommentService.deleteOneComment(rcId);
          JsonResponse.send(response, result);
        } catch (SQLException | NumberFormatException e) {
          result.setCode(-1);
          result.setMessage(e.getMessage());
          JsonResponse.send(response, result);
        }
      }
    }
    else {
      result.setCode(-1);
      result.setMessage("don't be allowed to delete");
      JsonResponse.send(response, result);
    }
  }

  private boolean geEnableDelete(String cId, String rId, String aId, Integer uId)
      throws IOException, SQLException {
    boolean enAbleDelete = false;
    int authorId = ArticleService.getArticleInfoById(Integer.parseInt(aId)).getAuthorId();
    if (authorId != uId) {
      if (!StringUtils.isEmpty(cId)) {
        int commentUId = CommentService.getUidByCommentId(Integer.parseInt(cId));
        if (commentUId == uId) {
          enAbleDelete = true;
        }
      }
      if (!StringUtils.isEmpty(rId)) {
        int replyUid = CommentService.getUidByReplyId(Integer.parseInt(rId));
        if (replyUid == uId) {
          enAbleDelete = true;
        }
      }
    } else {
      enAbleDelete = true;
    }
    return enAbleDelete;
  }
}
