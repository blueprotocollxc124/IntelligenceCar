package org.linkworld.service.Impl;

/*
 *@Author  LXC BlueProtocol
 *@Since   2022/3/5
 */


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.linkworld.dao.UserPatternMapper;
import org.linkworld.persist.emtity.UserPattern;
import org.springframework.stereotype.Service;

@Service
public class UserPatternServiceImpl extends ServiceImpl<UserPatternMapper, UserPattern> implements UserPatternService {
}
