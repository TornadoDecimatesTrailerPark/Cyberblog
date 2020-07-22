package com.cyberblogger.util;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

/**
 * Created by foxi.chen on 29/01/20.
 *
 * @author foxi.chen
 */
public class IdTokenVerifierAndParser {
  private static final String GOOGlE_CLIENT_ID =
    "651753574163-nbhpdiviu0o5h2b0gikaogphh1t6c1ao.apps.googleusercontent.com";
  public static GoogleIdToken.Payload getPayLoadFroGoogle(String tokenString) throws GeneralSecurityException, IOException {
    GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
      // Specify the CLIENT_ID of the app that accesses the backend:
      .setAudience(Collections.singletonList(GOOGlE_CLIENT_ID))
      // Or, if multiple clients access the backend:
      //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
      .build();
    GoogleIdToken idToken = verifier.verify(tokenString);
    if (idToken != null){
      GoogleIdToken.Payload payload = idToken.getPayload();
      return payload;
    } else {
      System.out.println("Invalid ID token.");
    }
    return null;
  }
}
