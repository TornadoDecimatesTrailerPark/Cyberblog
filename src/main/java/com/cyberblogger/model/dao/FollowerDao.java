package com.cyberblogger.model.dao;

import com.cyberblogger.model.Follower;
import com.cyberblogger.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FollowerDao {

    public static ArrayList<Follower> getFollowerByUserId(int id, Connection conn) throws SQLException {
        try (PreparedStatement statement =
                     conn.prepareStatement("select id, avatar_url, f_name " +
                             "from user " +
                             "where id in (select followed_user_id from user_follow where user_id = ?)")) {
            statement.setInt(1, id);
            ArrayList<Follower> followers = new ArrayList<>();
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Follower follower = new Follower();
                    follower.setFollowerId(rs.getInt(1));
                    follower.setFollowerAvatarUrl(rs.getString(2));
                    follower.setFollowerName(rs.getString(3));
                    followers.add(follower);
                    return followers;
                }

            }
            return followers;
        }
    }

    public static ArrayList<Follower> getFollowingByUserId(int id, Connection conn) throws SQLException {
        try (PreparedStatement statement =
                     conn.prepareStatement("select id, avatar_url, f_name " +
                             "from user " +
                             "where id in (select user_id from user_follow where followed_user_id = ?)")) {
            statement.setInt(1, id);
            ArrayList<Follower> followers = new ArrayList<>();
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Follower follower = new Follower();
                    follower.setFollowerId(rs.getInt(1));
                    follower.setFollowerAvatarUrl(rs.getString(2));
                    follower.setFollowerName(rs.getString(3));
                    followers.add(follower);
                    return followers;
                }
            }
            return followers;
        }
    }

}
