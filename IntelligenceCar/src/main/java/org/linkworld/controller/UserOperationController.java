package org.linkworld.controller;

/*
 *@Author  LXC BlueProtocol
 *@Since   2022/3/5
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.linkworld.check.sequence.PatternDTOSequence;
import org.linkworld.config.LoginSessionParams;
import org.linkworld.persist.dto.PatternDTO;
import org.linkworld.persist.emtity.Pattern;
import org.linkworld.persist.emtity.UserPattern;
import org.linkworld.persist.vo.ResultBean;
import org.linkworld.service.PatternService;
import org.linkworld.service.UserPatternService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pattern")
public class UserOperationController extends BaseController{

    @Autowired
    private PatternService patternService;

    @Autowired
    private UserPatternService userPatternService;


    @PostMapping("/addPattern")
    @ResponseBody
    @Transactional
    public ResultBean createOnePattern(HttpServletRequest httpRequest, @RequestBody @Validated({PatternDTOSequence.class})PatternDTO dto, @RequestHeader("token") String token) {
        HttpSession session=request.getSession();
        String userIdStr = getUserId();
        Pattern pattern = new Pattern(dto);
        patternService.save(pattern);
        UserPattern userPattern = new UserPattern(new BigInteger(userIdStr), dto.getPatternName());
        userPatternService.save(userPattern);
        return loginNum(session,ResultBean.ok());
    }

    @GetMapping("/getAllPattern")
    @ResponseBody
    public ResultBean getAllPattern(HttpServletRequest request,@RequestHeader("token") String token) {
        HttpSession session=request.getSession();
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
        return loginNum(session,ResultBean.ok().setData(patternList));
    }


    public ResultBean loginNum(HttpSession session, ResultBean resultBean){

        if(session.getAttribute(LoginSessionParams.userLogin)!=null){
            resultBean.setUserLogin(1);

        }

        if(session.getAttribute(LoginSessionParams.wechatLogin)!=null){
            resultBean.setWechatLogin(1);
        }
        return resultBean;
    }
}
