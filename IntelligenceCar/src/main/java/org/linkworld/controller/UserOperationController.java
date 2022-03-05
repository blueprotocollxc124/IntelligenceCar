package org.linkworld.controller;

/*
 *@Author  LXC BlueProtocol
 *@Since   2022/3/5
 */

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

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


}
