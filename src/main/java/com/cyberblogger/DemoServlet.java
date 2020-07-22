package com.cyberblogger;

import com.cyberblogger.util.DBConnectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;


@WebServlet(name = "DemoServlet", urlPatterns = {"/test"})
public class DemoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      Logger logger = LoggerFactory.getLogger(DemoServlet.class);
      logger.info(conn.toString());
      out.println("<h3>Hello World!</h3>");
      out.println("<h3>" + conn.toString() + "</h3>");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
