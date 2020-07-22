package com.cyberblogger.model.dto;

import com.cyberblogger.model.Article;
import com.cyberblogger.model.TagDict;
import com.cyberblogger.model.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by foxi.chen on 10/02/20.
 *
 * @author foxi.chen
 */
@Data
public class ArticleDTO extends Article {
  private User user;
  private ArrayList<TagDict> tagsExt;

  public ArticleDTO(User user) {
    super();
    this.user = user;
  }
  public ArticleDTO(){}
}
