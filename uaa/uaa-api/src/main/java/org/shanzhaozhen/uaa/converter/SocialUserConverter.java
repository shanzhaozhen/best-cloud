package org.shanzhaozhen.uaa.converter;

import org.shanzhaozhen.uaa.pojo.dto.GithubUserInfo;
import org.shanzhaozhen.uaa.pojo.entity.GithubUser;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-09-02
 * @Description:
 */
public class SocialUserConverter {

    public static GithubUser convertGithubUser(Map<String, Object> map, String userNameAttributeName) {
        String username = map.getOrDefault(userNameAttributeName, "").toString();
        Assert.hasText(username, "没有获取到Github用户信息");

        GithubUser githubUser = new GithubUser();
        githubUser
                .setLogin(map.getOrDefault("login", "").toString())
                .setGithubId(map.getOrDefault("id", "").toString())
                .setNodeId(map.getOrDefault("node_id", "").toString())
                .setAvatarUrl(map.getOrDefault("avatar_url", "").toString())
                .setEmail(map.getOrDefault("email", "").toString())
                .setName(map.getOrDefault("name", "").toString())
//                .setOther(JacksonUtils.toJSONString(map))
                .setUsername(username)
        ;

        return githubUser;
    }

    public static GithubUserInfo convertGithubUserInfo(GithubUser githubUser) {
        if (githubUser == null) return null;

        return GithubUserInfo.builder()
                .username(githubUser.getUsername())
                .avatarUrl(githubUser.getAvatarUrl())
                .bindDate(githubUser.getCreatedDate())
                .build();
    }
}
