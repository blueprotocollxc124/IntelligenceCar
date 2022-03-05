package org.linkworld.persist.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/*
 *@Author  LXC BlueProtocol
 *@Since   2022/3/5
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ResultBean {

    private Integer code;

    private String message;

    private Boolean success;

    private Object data;


    public static ResultBean ok() {
        ResultBean resultBean = new ResultBean();
        resultBean
                .setCode(ResultBeanEnum.RESULT_OK.getCode())
                .setMessage(ResultBeanEnum.RESULT_OK.getMessage())
                .setSuccess(ResultBeanEnum.RESULT_OK.getSuccess());
        return resultBean;
    }

    public static ResultBean bad() {
        ResultBean resultBean = new ResultBean();
        resultBean
                .setCode(ResultBeanEnum.RESULT_FAILURE.getCode())
                .setMessage(ResultBeanEnum.RESULT_FAILURE.getMessage())
                .setSuccess(ResultBeanEnum.RESULT_FAILURE.getSuccess());
        return resultBean;
    }
}
