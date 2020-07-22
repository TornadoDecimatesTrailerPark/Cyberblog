package com.cyberblogger.model;

import lombok.Data;


@Data
public class ThirdAuth {
  private int id;
  private int userId;
  private String oauthName;
  private String oauthId;
  private String oauthAccessToken;
  private int oauthExpires;

  public ThirdAuth(int id, int userId, String oauthName,
                   String oauthId, String oauthAccessToken, int oauthExpires) {
    this.id = id;
    this.userId = userId;
    this.oauthName = oauthName;
    this.oauthId = oauthId;
    this.oauthAccessToken = oauthAccessToken;
    this.oauthExpires = oauthExpires;
  }

  public ThirdAuth(int userId, String oauthName,
                   String oauthId, String oauthAccessToken, int oauthExpires) {
    this.userId = userId;
    this.oauthName = oauthName;
    this.oauthId = oauthId;
    this.oauthAccessToken = oauthAccessToken;
    this.oauthExpires = oauthExpires;
  }

  public ThirdAuth() {

  }
}
