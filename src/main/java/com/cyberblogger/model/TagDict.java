package com.cyberblogger.model;

import lombok.Data;


@Data
public class TagDict {
  private int tagId;
  private String tagName;

  public TagDict(int tagId, String tagName) {
    this.tagId = tagId;
    this.tagName = tagName;
  }
}
