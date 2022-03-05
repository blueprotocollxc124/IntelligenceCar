package org.linkworld.persist.dto;

/*
 *@Author  LXC BlueProtocol
 *@Since   2022/3/5
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatternDTO {


    @NotBlank(message = "模式的名称不能为空")
    @Size(min = 0, max = 255,message = "模式名称的字符应在{min}-{max}之间")
    private String patternName;

    @NotNull(message = "时间选择不能为null")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date datetimeChoice;

    @NotNull(message = "开始不能为null")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date beginTime;

    @NotBlank(message = "巡逻区域不能为空")
    @Size(min = 0, max = 255,message = "巡逻区域的字符应在{min}-{max}之间")
    private String patroRegion;

    @NotBlank(message = "巡逻速度不能为空")
    @Size(min = 0, max = 255,message = "巡逻速度的字符应在{min}-{max}之间")
    private String patroSpeed;

    @NotBlank(message = "采集图片的频率不能为空")
    @Size(min = 0, max = 255,message = "采集图片的频率的字符应在{min}-{max}之间")
    private String collectTimes;

    private Integer light;

    private Integer photo;

    private Integer lightHeightDetect;

    private Integer wetDegreeDetect;

    private Integer smokeDegreeDetect;

    private Integer barrierDetect;

    private Integer cooperation;

    private Integer lowTurnBack;

    @Size(min = 0, max = 255,message = "备注的字符应在{min}-{max}之间")
    private String tag;

}
