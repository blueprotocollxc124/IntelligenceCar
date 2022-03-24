package org.linkworld.service.Impl;

/*
 *@Author  LXC BlueProtocol
 *@Since   2022/3/5
 */


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.linkworld.dao.PatternMapper;
import org.linkworld.dao.UserPatternMapper;
import org.linkworld.persist.dto.PatternDTO;
import org.linkworld.persist.emtity.Pattern;
import org.linkworld.persist.emtity.UserPattern;
import org.linkworld.persist.vo.ResultBean;
import org.linkworld.service.PatternService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PatternServiceImpl extends ServiceImpl<PatternMapper, Pattern> implements PatternService {


    @Autowired
    private PatternMapper patternMapper;

    @Autowired
    private UserPatternMapper userPatternMapper;

    @Autowired
    private PlatformTransactionManager manager;

    @Autowired
    private TransactionDefinition transactionDefinition;


    @Override
    public ResultBean createOnePattern(PatternDTO dto, String userIdStr) {
        Pattern pattern = new Pattern(dto);
        patternMapper.insert(pattern);
        UserPattern userPattern = new UserPattern(new BigInteger(userIdStr), dto.getPatternName());
        userPatternMapper.insert(userPattern);
        return ResultBean.ok();
    }

    @Override
    public ResultBean getAllPattern(String userId) {
        ArrayList<Pattern> patternList = new ArrayList<>();
        LambdaQueryWrapper<UserPattern> wrapper = new QueryWrapper<UserPattern>().lambda().eq(UserPattern::getUserId, userId);
        List<UserPattern> userPatternList = userPatternMapper.selectList(wrapper);
        userPatternList.forEach(userPattern -> {
            LambdaQueryWrapper<Pattern> patternWrapper = new QueryWrapper<Pattern>().lambda().eq(Pattern::getPatternName, userPattern.getPatternName());
            Pattern pattern = patternMapper.selectOne(patternWrapper);
            Pattern realPattern = Optional.ofNullable(pattern).orElseThrow(() -> {
                return new RuntimeException("没有这样的pattern");
            });
            patternList.add(realPattern);
        });
        return ResultBean.ok().setData(patternList);
    }

    @Override
    public ResultBean getOnePattern(String patternName) {
        if(!StringUtils.hasText(patternName)) {
            return ResultBean.bad().setMessage("模式名不能为空");
        }
        LambdaQueryWrapper<Pattern> patternWrapper = new QueryWrapper<Pattern>().lambda().eq(Pattern::getPatternName, patternName);
        Pattern pattern = Optional.ofNullable(patternMapper.selectOne(patternWrapper)).orElseThrow(() -> {
            return new RuntimeException("没有这样的pattern");
        });
        return ResultBean.ok().setData(pattern);
    }

    @Override
    public ResultBean deleteOnePattern(String userId,String patternName) {
        if(!StringUtils.hasText(patternName)) {
            return ResultBean.bad().setMessage("模式名不能为空");
        }
        LambdaQueryWrapper<UserPattern> userPatternWrapper = new QueryWrapper<UserPattern>().lambda()
                .eq(UserPattern::getUserId, userId)
                .eq(UserPattern::getPatternName, patternName);
        LambdaQueryWrapper<Pattern> patternWrapper = new QueryWrapper<Pattern>().lambda().eq(Pattern::getPatternName, patternName);
        TransactionStatus status = manager.getTransaction(transactionDefinition);
        try {
            userPatternMapper.delete(userPatternWrapper );
            patternMapper.delete(patternWrapper);
            manager.commit(status);
        } catch (Exception e) {
            manager.rollback(status);
         return ResultBean.bad().setMessage("在删除的过程中发生错误");
        }
        return ResultBean.ok();
    }

    @Override
    public ResultBean updateOnePatten(Pattern newPattern, String patternName) {
        if(!StringUtils.hasText(patternName)) {
            return ResultBean.bad().setMessage("模式名不能为空");
        }
        LambdaQueryWrapper<Pattern> patternWrapper = new QueryWrapper<Pattern>().lambda().eq(Pattern::getPatternName, patternName);
        Pattern pattern = Optional.ofNullable(patternMapper.selectOne(patternWrapper)).orElseThrow(() -> {
            return new RuntimeException("没有找到对应的模式");
        });
        patternMapper.update(newPattern,patternWrapper);
        return ResultBean.ok();
    }
}
