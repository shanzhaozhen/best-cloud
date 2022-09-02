package org.shanzhaozhen.uaa.converter;

import org.shanzhaozhen.common.core.utils.JacksonUtils;
import org.shanzhaozhen.uaa.pojo.entity.GithubUser;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-09-02
 * @Description:
 */
public class SocialUserConverter {

    public static GithubUser convertGithubUser(Map<String, Object> map) {
        String login = (String) map.get("login");
        Assert.hasText(login, "没有获取到Github用户信息");

        GithubUser githubUser = new GithubUser();
        githubUser
                .setLogin(login)
                .setGithubId(map.getOrDefault("id", "").toString())
                .setNodeId(map.getOrDefault("node_id", "").toString())
                .setAvatarUrl(map.getOrDefault("avatar_url", "").toString())
                .setEmail(map.getOrDefault("email", "").toString())
                .setName(map.getOrDefault("name", "").toString())
                .setOther(JacksonUtils.toJSONString(map));

        return githubUser;
    }

}
