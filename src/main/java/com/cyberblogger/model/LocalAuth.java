package com.cyberblogger.model;

public class LocalAuth {

  private Integer id;
  private String userName;
  private String password;
  private String salt;
  private int saltLength;
  private int iterationNum;
  private int userId;

  public LocalAuth(
      Integer id, String userName, String password, String salt,
      int saltLength, int iterationNum, int userId) {
    this.id = id;
    this.userName = userName;
    this.password = password;
    this.salt = salt;
    this.saltLength = saltLength;
    this.iterationNum = iterationNum;
    this.userId = userId;
  }

  public LocalAuth(
      String userName, String password, String salt,
      int saltLength, int iterationNum, int userId) {
    this.userName = userName;
    this.password = password;
    this.salt = salt;
    this.saltLength = saltLength;
    this.iterationNum = iterationNum;
    this.userId = userId;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  public int getSaltLength() {
    return saltLength;
  }

  public void setSaltLength(int saltLength) {
    this.saltLength = saltLength;
  }

  public int getIterationNum() {
    return iterationNum;
  }

  public void setIterationNum(int iterationNum) {
    this.iterationNum = iterationNum;
  }

  public int getUserId() {
    return userId;
  }
}
