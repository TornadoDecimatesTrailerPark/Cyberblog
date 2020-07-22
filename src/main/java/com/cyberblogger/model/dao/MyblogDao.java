package com.cyberblogger.model.dao;

import com.cyberblogger.model.Article;
import com.cyberblogger.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * Created by chris on 08/02/20.
 *
 * @author chris
 */
public class MyblogDao {
    public static User getUserInfoById(int id, Connection conn) throws SQLException {
        try (PreparedStatement statement =
                     conn.prepareStatement("select * from user where id=?")) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    User user =
                            new User(
                                    rs.getInt(1),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getString(4),
                                    rs.getDate(5),
                                    rs.getString(6),
                                    rs.getString(7),
                                    rs.getTimestamp(8),
                                    rs.getTimestamp(9));
                    return user;
                }
            }
        }
        return null;
    }

    public static ArrayList<Article> getArticlesById(int id, Connection conn) throws SQLException {
        try (PreparedStatement statement =
                     conn.prepareStatement("select author_id, post_type, title, create_time, excerpt\n" +
                             "from article\n" +
                             "inner join user\n" +
                             "on user.id = article.author_id\n" +
                             "where user.id=?\n" +
                             "order by update_time")) {
            statement.setInt(1, id);
            ArrayList<Article> articles = new ArrayList<>();
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Article article = new Article();
                    article.setAuthorId(rs.getInt(1));
                    article.setPostType(rs.getInt(2));
                    article.setTitle(rs.getString(3));
                    article.setCreateTime(rs.getTimestamp(4));
                    article.setExcerpt(rs.getString(5));
                    articles.add(article);
                    return articles;
                }

            }
        }
        return null;
    }

    public static ArrayList<User> getFollowerByUserId(int id, Connection conn) throws SQLException {
        try (PreparedStatement statement =
                     conn.prepareStatement("select id, avatar_url, f_name\n" +
                             "from user\n" +
                             "where id in (select followed_user_id from user_follow where user_id = '')")) {
            statement.setInt(1, id);
            ArrayList<User> users = new ArrayList<>();
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt(1));
                    user.setAvatarUrl(rs.getString(2));
                    user.setF_name(rs.getString(3));
                    users.add(user);
                    return users;
                }

            }
        }
        return null;
    }

    public static ArrayList<User> getFollowingByUserId(int id, Connection conn) throws SQLException {
        try (PreparedStatement statement =
                     conn.prepareStatement("select id, avatar_url, f_name\n" +
                             "from user\n" +
                             "where id in (select user_id from user_follow where followed_user_id = '')")) {
            statement.setInt(1, id);
            ArrayList<User> users = new ArrayList<>();
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt(1));
                    user.setAvatarUrl(rs.getString(2));
                    user.setF_name(rs.getString(3));
                    users.add(user);
                    return users;
                }
            }
        }
        return null;
    }


}
