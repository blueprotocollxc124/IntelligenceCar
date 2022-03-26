package org.linkworld.persist.emtity;

/*
 *@Author  LXC BlueProtocol
 *@Since   2022/3/5
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.linkworld.persist.dto.PatternDTO;

import java.math.BigInteger;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName(value = "pattern")
public class Pattern {

    @TableId(value = "pattern_id",type = IdType.AUTO)
    private BigInteger patternId;

    private String patternName;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date datetimeChoice;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="HH:mm")
    private Date beginTime;

    private String patroRegion;

    private String patroSpeed;

    private String collectTimes;

    private int light;

    private int photo;

    private int lightHeightDetect;

    private int wetDegreeDetect;

    private int smokeDegreeDetect;

    private int barrierDetect;

    private int cooperation;

    private int lowTurnBack;

    private String tag;

    public Pattern (PatternDTO dto) {
        this.patternId = dto.getPatternId();
        this.patternName = dto.getPatternName();
        this.datetimeChoice = dto.getDatetimeChoice();
        this.beginTime = dto.getBeginTime();
        this.patroRegion = dto.getPatroRegion();
        this.patroSpeed = dto.getPatroSpeed();
        this.collectTimes = dto.getCollectTimes();
        this.light = dto.getLight();
        this.photo = dto.getPhoto();
        this.lightHeightDetect = dto.getLightHeightDetect();
        this.wetDegreeDetect = dto.getWetDegreeDetect();
        this.smokeDegreeDetect = dto.getSmokeDegreeDetect();
        this.barrierDetect = dto.getBarrierDetect();
        this.cooperation = dto.getCooperation();
        this.tag =dto.getTag();
    }

}
