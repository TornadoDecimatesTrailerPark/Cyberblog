package com.cyberblogger.controller;

import com.cyberblogger.model.dto.CommentDTO;
import com.cyberblogger.model.dto.JsonResult;
import com.cyberblogger.model.dto.ReplyDTO;
import com.cyberblogger.service.CommentService;
import com.cyberblogger.util.JsonResponse;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;


@WebServlet(name = "getReplyServlet", urlPatterns = {"/getReply"})
public class getReplyServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String cId = request.getParameter("cId");
      String rId = request.getParameter("rId");
      JsonResult<List<ReplyDTO>> result = new JsonResult<>(1, "success");
      if (StringUtils.isEmpty(cId) && StringUtils.isEmpty(rId)){
        result.setCode(-1);
        result.setMessage("Input parameter is wrong");
        JsonResponse.send(response, result);
        return;
      }
      if (StringUtils.isEmpty(cId)){
        try {
          List<ReplyDTO> replyDTOS = CommentService.getChildReplyByrId(Integer.parseInt(rId));
          result.setData(replyDTOS);
          JsonResponse.send(response, result);
        } catch (SQLException | ParseException | NumberFormatException e) {
          result.setCode(-1);
          result.setMessage(e.getMessage());
          JsonResponse.send(response, result);
        }
      } else {
        try {
          List<ReplyDTO> replyDTOS = CommentService.getAllReplyByCommentId(Integer.parseInt(cId));
          result.setData(replyDTOS);
          JsonResponse.send(response, result);
        } catch (SQLException | ParseException | NumberFormatException e) {
          result.setCode(-1);
          result.setMessage(e.getMessage());
          JsonResponse.send(response, result);
        }
      }
    }
}
