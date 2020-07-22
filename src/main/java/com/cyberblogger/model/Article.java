package com.cyberblogger.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.util.ArrayList;

@EqualsAndHashCode(callSuper = true)
@Data
public class Article extends BasePoJo {

    private String title;
    private int authorId;
    private String content;
    private String excerpt;
    private int commentStatus;
    private int postType;

    //所有点赞，浏览，18禁，转发字数，被收藏次数。。。都是额外属性，
    //用一个通用对象装起来，然后放在一个list中， tag也是同样的道理
    private ArrayList<Integer> tags;
    private ArrayList<ExtraTypeDict> extraTypeList;
    private ArrayList<ExtraTypeDict> counterTypeList;


    public Article(int id, String title, int authorId, String content, String excerpt,
                   int commentStatus, int postType,
                   Timestamp updateTime, Timestamp createTime) {
        super(id, createTime, updateTime);
        this.title = title;
        this.authorId = authorId;
        this.content = content;
        this.excerpt = excerpt;
        this.commentStatus = commentStatus;
        this.postType = postType;
    }

    //你在新建文章时， 并无法获取id，只有在插入数据库后才能获取到，所以你需要一个没有id的构造器
    public Article(String title, int authorId, String content, String excerpt,
                   int commentStatus, int postType,
                   Timestamp updateTime, Timestamp createTime) {
        super(createTime, updateTime);
        this.title = title;
        this.authorId = authorId;
        this.content = content;
        this.excerpt = excerpt;
        this.commentStatus = commentStatus;
        this.postType = postType;
    }


    public Article(int id, String title, int authorId, String content, String excerpt,
                   int commentStatus, int postType, ArrayList<Integer> tags,
                   ArrayList<ExtraTypeDict> extraTypeList,
                   ArrayList<ExtraTypeDict> counterTypeList,
                   Timestamp updateTime, Timestamp createTime) {
        super(id, createTime, updateTime);
        this.title = title;
        this.authorId = authorId;
        this.content = content;
        this.excerpt = excerpt;
        this.commentStatus = commentStatus;
        this.postType = postType;
        this.tags = tags;
        this.extraTypeList = extraTypeList;
        this.counterTypeList = counterTypeList;
    }

    public Article(String title, int authorId, String content, String excerpt,
                   int commentStatus, int postType, ArrayList<Integer> tags,
                   ArrayList<ExtraTypeDict> extraTypeList,
                   ArrayList<ExtraTypeDict> counterTypeList,
                   Timestamp updateTime, Timestamp createTime) {
        super(createTime, updateTime);
        this.title = title;
        this.authorId = authorId;
        this.content = content;
        this.excerpt = excerpt;
        this.commentStatus = commentStatus;
        this.postType = postType;
        this.tags = tags;
        this.extraTypeList = extraTypeList;
        this.counterTypeList = counterTypeList;
    }



    public Article() {
    }
}
