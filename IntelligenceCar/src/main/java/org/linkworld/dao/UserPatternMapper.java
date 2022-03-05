package org.linkworld.dao;

/*
 *@Author  LXC BlueProtocol
 *@Since   2022/3/5
 */


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.linkworld.persist.emtity.UserPattern;

@Mapper
public interface UserPatternMapper extends BaseMapper<UserPattern> {
}
