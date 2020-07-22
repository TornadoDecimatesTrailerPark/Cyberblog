package com.cyberblogger.model;

import lombok.Data;


@Data
public class ExtraTypeDict {
  private int dictTypeId;
  private String dictTypeName;
  private int dictTypeVal;

  public ExtraTypeDict(int dictTypeId, String dictTypeName, int dictTypeVal) {
    this.dictTypeId = dictTypeId;
    this.dictTypeName = dictTypeName;
    this.dictTypeVal = dictTypeVal;
  }

  public ExtraTypeDict(int dictTypeId, String dictTypeName) {
    this.dictTypeId = dictTypeId;
    this.dictTypeName = dictTypeName;
  }

  public ExtraTypeDict(int dictTypeId, int dictTypeVal) {
    this.dictTypeId = dictTypeId;
    this.dictTypeVal = dictTypeVal;
  }

  public ExtraTypeDict() {
  }
}
