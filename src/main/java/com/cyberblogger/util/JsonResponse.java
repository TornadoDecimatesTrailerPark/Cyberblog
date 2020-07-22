package com.cyberblogger.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by foxi.chen on 7/02/20.
 *
 * @author foxi.chen
 */
public class JsonResponse {
  public static void send(HttpServletResponse response, Object object) throws IOException {
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    ObjectMapper objectMapper = new ObjectMapper();

    // By default, Jackson 2 will only work with with fields that are either public, or have a public getter methods
    // see link https://www.baeldung.com/jackson-jsonmappingexception
    try{
        objectMapper.writeValue(response.getWriter(), object);
    } catch (Exception e){
        e.getStackTrace();
    }
  }

  public static String recieve(HttpServletRequest request) throws IOException {
    try (BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
      String json = "";
      json = br.readLine();
      System.out.println(json);
      return json;
    }
  }
}
