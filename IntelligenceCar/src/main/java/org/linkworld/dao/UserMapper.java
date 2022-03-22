package org.linkworld.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.linkworld.persist.emtity.User;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    @Select("select `user`.`id`,`user`.`password` \n" +
            "from `user`\n" +
            "inner join wechat_user as wu\n" +
            "on wu.`id`= `user`.`id`  " +
            "where wu.`open_id`=#{openId}")
    @ResultType(User.class)
    User getUserByOpen(@Param("openId") String openId);

    @Select("select `user`.`id`,`user`.`password` \n" +
            "from `user`\n" +
            "inner join wechat_user as wu\n" +
            "on wu.`id`= `user`.`id`  " +
            "where wu.`open_id`=#{openId}")
    @ResultType(User.class)
    User getWechatUserByOpenId(@Param("openId") String openId);

    @Insert("insert into `wechat_user` (`open_id`,`id`) value (#{openId},#{userId})")
    void saveWechatUser(@Param("openId") String openId, @Param("userId") BigInteger userId);

    @Update("update `wechat_user` set `id`=#{userId}  where  `open_id`=#{openId}")
    void Update(@Param("openId") String openId, @Param("userId") BigInteger userId);

}
