package org.linkworld.controller;

/*
 *@Author  LXC BlueProtocol
 *@Since   2022/3/5
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.linkworld.check.sequence.PatternDTOSequence;
import org.linkworld.persist.dto.PatternDTO;
import org.linkworld.persist.emtity.Pattern;
import org.linkworld.persist.emtity.UserPattern;
import org.linkworld.persist.vo.ResultBean;
import org.linkworld.service.Impl.UserPatternService;
import org.linkworld.service.PatternService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class UserOperationController extends BaseController{

    @Autowired
    private PatternService patternService;

    @Autowired
    private UserPatternService userPatternService;


    @PostMapping("/addPattern")
    @ResponseBody
    @Transactional
    public ResultBean createOnePattern(@RequestBody @Validated({PatternDTOSequence.class})PatternDTO dto) {
        String userIdStr = getUserId();
        Pattern pattern = new Pattern(dto);
        patternService.save(pattern);
        UserPattern userPattern = new UserPattern(new BigInteger(userIdStr), dto.getPatternName());
        userPatternService.save(userPattern);
        return ResultBean.ok();
    }

    @GetMapping("/getAllPattern")
    @ResponseBody
    public ResultBean getAllPattern() {
        String userId = getUserId();
        ArrayList<Pattern> patternList = new ArrayList<>();
        LambdaQueryWrapper<UserPattern> wrapper = new QueryWrapper<UserPattern>().lambda().eq(UserPattern::getUserId, userId);
        List<UserPattern> userPatternList = userPatternService.list(wrapper);
        userPatternList.forEach(userPattern -> {
            LambdaQueryWrapper<Pattern> patternWrapper = new QueryWrapper<Pattern>().lambda().eq(Pattern::getPatternName, userPattern.getPatternName());
            Pattern pattern = patternService.getOne(patternWrapper);
            Pattern realPattern = Optional.ofNullable(pattern).orElseThrow(() -> {
                return new RuntimeException("没有这样的pattern");
            });
            patternList.add(realPattern);
        });
        return ResultBean.ok().setData(patternList);
    }


}
