package com.cyberblogger.model.dto;

import java.io.Serializable;

/**
 * Created by foxi.chen on 7/02/20.
 * All jsonresult use this entity, when making a new instance, give T a specific type
 * @author foxi.chen
 */
public class JsonResult<T> implements Serializable {

  private static final long serialVersionUID = -111613487157117492L;

  private int code;

  private String message;

  private T data;

  public JsonResult(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public JsonResult(int code, String message, T data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
