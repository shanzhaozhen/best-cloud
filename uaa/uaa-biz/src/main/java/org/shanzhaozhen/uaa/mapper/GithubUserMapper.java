package org.shanzhaozhen.uaa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.shanzhaozhen.uaa.pojo.entity.GithubUser;


/**
 * @Author: shanzhaozhen
 * @Date: 2022-09-02
 * @Description:
 */
public interface GithubUserMapper extends BaseMapper<GithubUser> {

    GithubUser getGithubUserByLogin(@Param("login") String login);

    GithubUser getGithubUserByUsername(@Param("username") String username);

}
