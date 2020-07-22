package com.cyberblogger.controller;

import com.cyberblogger.model.dto.CommentDTO;
import com.cyberblogger.model.dto.JsonResult;
import com.cyberblogger.service.CommentService;
import com.cyberblogger.util.JsonResponse;
import com.cyberblogger.util.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;


@WebServlet(name = "InsertCommentServlet", urlPatterns = {"/addNewComment"})
public class InsertCommentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String jsonStr = JsonResponse.recieve(request);
      CommentDTO commentDTO = JsonUtils.json2Bean(jsonStr, CommentDTO.class);
      JsonResult<CommentDTO> result = new JsonResult<>(1, "success");
      try {
        if (CommentService.addNewComment(commentDTO)){
          result.setData(commentDTO);
        } else {
          result.setCode(-1);
          result.setMessage("adding new comment failed");
        }
        JsonResponse.send(response, result);
        return;
      } catch (SQLException | ParseException e) {
        result.setCode(-1);
        result.setMessage(e.getMessage());
        JsonResponse.send(response, result);
      }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
