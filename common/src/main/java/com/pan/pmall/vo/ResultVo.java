package com.pan.pmall.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 用来返回json数据
 * @author: Mr.Pan
 **/
@ApiModel(value = "响应的VO对象", description = "封装接口返回给前端的数据")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultVo {
    /*状态码: 返回200 - 成功, 返回300 - 失败*/
    @ApiModelProperty(value = "响应状态码", dataType = "integer")
    private Integer code;

    /*提示信息*/
    @ApiModelProperty(value = "响应信息", dataType = "string")
    private String msg;

    /*用户要返回给浏览器的数据*/
    @ApiModelProperty(value = "响应数据", dataType = "map")
    private Map<String, Object> data = new HashMap<>();

    /*返回成功信息*/
    public static ResultVo success() {
        ResultVo result = new ResultVo();
        result.setCode(ResultStatus.SUCCESS.getCode());
        result.setMsg(ResultStatus.SUCCESS.getMsg());
        return result;
    }

    /*返回成功信息, 自定义msg*/
    public static ResultVo success(String msg) {
        ResultVo result = new ResultVo();
        result.setCode(ResultStatus.SUCCESS.getCode());
        result.setMsg(msg);
        return result;
    }

    /*返回已创建信息*/
    public static ResultVo created() {
        ResultVo result = new ResultVo();
        result.setCode(ResultStatus.CREATED.getCode());
        result.setMsg(ResultStatus.CREATED.getMsg());
        return result;
    }

    /*返回已创建信息, 自定义msg*/
    public static ResultVo created(String msg) {
        ResultVo result = new ResultVo();
        result.setCode(ResultStatus.CREATED.getCode());
        result.setMsg(msg);
        return result;
    }

    /*返回已创建信息*/
    public static ResultVo deleted() {
        ResultVo result = new ResultVo();
        result.setCode(ResultStatus.DELETED.getCode());
        result.setMsg(ResultStatus.DELETED.getMsg());
        return result;
    }

    /*返回已创建信息, 自定义msg*/
    public static ResultVo deleted(String msg) {
        ResultVo result = new ResultVo();
        result.setCode(ResultStatus.DELETED.getCode());
        result.setMsg(msg);
        return result;
    }

    /*返回失败信息*/
    public static ResultVo failed() {
        ResultVo result = new ResultVo();
        result.setCode(ResultStatus.FAILED.getCode());
        result.setMsg(ResultStatus.SUCCESS.getMsg());
        return result;
    }

    /*返回失败信息, 自定义msg*/
    public static ResultVo failed(String msg) {
        ResultVo result = new ResultVo();
        result.setCode(ResultStatus.FAILED.getCode());
        result.setMsg(msg);
        return result;
    }
    
    /*返回未授权信息*/
    public static ResultVo unauthorized() {
        ResultVo result = new ResultVo();
        result.setCode(ResultStatus.UNAUTHORIZED.getCode());
        result.setMsg(ResultStatus.UNAUTHORIZED.getMsg());
        return result;
    }

    /*返回未授权信息, 自定义msg*/
    public static ResultVo unauthorized(String msg) {
        ResultVo result = new ResultVo();
        result.setCode(ResultStatus.UNAUTHORIZED.getCode());
        result.setMsg(msg);
        return result;
    }
    
    /*返回未授权信息*/
    public static ResultVo forbidden() {
        ResultVo result = new ResultVo();
        result.setCode(ResultStatus.FORBIDDEN.getCode());
        result.setMsg(ResultStatus.FORBIDDEN.getMsg());
        return result;
    }

    /*返回未授权信息, 自定义msg*/
    public static ResultVo forbidden(String msg) {
        ResultVo result = new ResultVo();
        result.setCode(ResultStatus.FORBIDDEN.getCode());
        result.setMsg(msg);
        return result;
    }

    /*用来增加一些附带信息 (返回ResultVO对象, 就可以链式操作, 连续添加)*/
    public ResultVo add(String key, Object value) {
        this.getData().put(key, value);
        return this;
    }
}
