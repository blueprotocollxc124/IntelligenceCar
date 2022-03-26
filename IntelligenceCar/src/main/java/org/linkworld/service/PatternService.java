package org.linkworld.service;

/*
 *@Author  LXC BlueProtocol
 *@Since   2022/3/5
 */


import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.linkworld.check.sequence.PatternDTOSequence;
import org.linkworld.dao.PatternMapper;
import org.linkworld.persist.dto.PatternDTO;
import org.linkworld.persist.emtity.Pattern;
import org.linkworld.persist.vo.ResultBean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigInteger;

public interface PatternService extends IService<Pattern> {

    ResultBean createOnePattern( PatternDTO dto, String userId);


    ResultBean getAllPattern(String userId);


    ResultBean getOnePattern(BigInteger patternId);


    ResultBean deleteOnePattern(String userId, BigInteger patternId);


    ResultBean updateOnePatten(Pattern newPattern, BigInteger patternId);


}
