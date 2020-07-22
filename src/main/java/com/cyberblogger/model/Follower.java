package com.cyberblogger.model;

import lombok.Data;

/**
 * Created by Chris on 10/02/20.
 *
 * @author Chris
 */
@Data
public class Follower {

    private int followerId;
    private String followerAvatarUrl;
    private String followerName;

    public Follower(int followerId, String followerAvatarUrl, String followerName) {
        this.followerId = followerId;
        this.followerAvatarUrl = followerAvatarUrl;
        this.followerName = followerName;
    }

    public Follower(){}

}
