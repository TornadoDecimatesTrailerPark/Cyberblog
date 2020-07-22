package com.cyberblogger.model.dao;

import com.cyberblogger.model.Article;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * Created by Chris on 08/02/20.
 *
 * @author Chris
 */
public class TopFiveDao {

    public static ArrayList<Article> getTopFive(int articleId, Connection conn) throws SQLException {
        try (PreparedStatement statement =
                     conn.prepareStatement("select\n" +
                             "article.id,\n" +
                             "article.title\n" +
                             "from\n" +
                             "article\n" +
                             "left join\n" +
                             "article_counter\n" +
                             "where article_id=id and counter_type=4\n" +          //the counter type of watched is 5
                             "order by article_counter.counter_num desc limit 5")) {
            statement.setInt(1, articleId);
            ArrayList<Article> articles = new ArrayList<>();
            try (ResultSet rs = statement.executeQuery()) { //rs就是取出来的sql
                while (rs.next()) {
                    Article article = new Article();
                    article.setId(rs.getInt(1));
                    article.setTitle(rs.getString(2));
                    articles.add(article);
                    return articles;
                }
            }
        }
        return null;
    }
}
