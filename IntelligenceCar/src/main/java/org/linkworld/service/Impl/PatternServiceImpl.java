package org.linkworld.service.Impl;

/*
 *@Author  LXC BlueProtocol
 *@Since   2022/3/5
 */


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.linkworld.dao.PatternMapper;
import org.linkworld.persist.emtity.Pattern;
import org.linkworld.service.PatternService;
import org.springframework.stereotype.Service;

@Service
public class PatternServiceImpl extends ServiceImpl<PatternMapper, Pattern> implements PatternService {
}
