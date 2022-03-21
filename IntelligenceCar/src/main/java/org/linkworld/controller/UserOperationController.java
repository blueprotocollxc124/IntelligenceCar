package org.linkworld.controller;

/*
 *@Author  LXC BlueProtocol
 *@Since   2022/3/5
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Param;
import org.linkworld.check.sequence.PatternDTOSequence;
import org.linkworld.persist.dto.PatternDTO;
import org.linkworld.persist.emtity.Pattern;
import org.linkworld.persist.emtity.UserPattern;
import org.linkworld.persist.vo.ResultBean;
import org.linkworld.service.UserPatternService;
import org.linkworld.service.PatternService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pattern")
public class UserOperationController extends BaseController{

    @Autowired
    private PatternService patternService;


    @PostMapping("/addPattern")
    @ResponseBody
    @Transactional
    public ResultBean createOnePattern(@RequestBody @Validated({PatternDTOSequence.class})PatternDTO dto) {
        return patternService.createOnePattern(dto,getUserId());
    }

    @GetMapping("/getAllPattern")
    @ResponseBody
    public ResultBean getAllPattern() {
        return patternService.getAllPattern(getUserId());
    }



    @GetMapping("/getOnePattern")
    @ResponseBody
    public ResultBean getOnePattern(@Param("patternName")String patternName) {
        return patternService.getOnePattern(patternName);
    }


    @DeleteMapping("/deleteOnePattern")
    @ResponseBody
    public ResultBean deleteOnePattern(@Param("patternName")String patternName) {
       return patternService.deleteOnePattern(getUserId(),patternName);
    }




}
