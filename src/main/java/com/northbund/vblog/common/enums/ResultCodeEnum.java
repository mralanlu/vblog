package com.northbund.vblog.common.enums;


import org.apache.commons.lang3.StringUtils;

/**
 * Created by Owen Jia on 2017/6/14.
 */
public enum ResultCodeEnum {

    //common
    SYSTEM_SQL_ERROR(-1, "系统SQL注入异常"),
    CODE_ERROR(-2,"验证码错误"),
    ID_ERROR(-3,"ID错误"),
    SYSTEM_ERROR(0, "系统异常"),
    SUCCESS(1, "操作成功"),
    PARAM_ERROR(2,"参数错误"),
    DB_DATA_ERROR(3, "数据库数据错误"),
    ADD_ERROR(4, "添加失败"),
    UPDATE_ERROR(5, "更新失败"),
    DELETE_ERROR(6, "删除失败"),
    QUERY_ERROR(7, "查询失败"),
    VERIFICATION_OUT_OF_TIME(8, "验证码过期"),
    GET_VERIFICATION_UNKNOWN_ERROR(9, "验证码获取失败，未知错误，请联系客服！"),
    GET_VERIFICATION_STATUS_UNKNOWN_ERROR(10, "查询短信发送状态失败！"),
    GET_VERIFICATION_FREQUENTLY(11, "请勿频繁获取验证码，间隔60秒后重试！"),



    //user
    LOGIN(1, "登录成功"),
    LOGIN_ERROR(100, "登录失败"),
    LOGIN_ERROR_INFO(110, "请先登陆"),
    LOGIN_ERROR_EM_NOT_EXISTENT(101, "登录失败,非法用户"),
    GET_TOKEN_ERROR(102, "获取TOKEN失败"),
    GET_TOKEN_ERROR_TIEMOUT(104, "登陆验证超时"),
    USER_NAME_EXEITS(105,"登录账号已存在"),
    USER_DATA_ERROR(106,"账号保存错误"),
    USER_NOT_EXIST_ERROR(107,"账号不存在"),
    USER_ID_NOT_EXIST_ERROR(108,"用户id不存在"),
    VERIFICATION_CODE_ERROR(109,"验证码错误"),
    REGISTER_ERROR(111,"注册失败"),
    GET_VERIFY_CODE_ERROR(112,"获取验证码失败"),
    USER_ID_NULL(113,"用户id不能为null"),
    PLEASE_REGISTER(114, "请先注册"),

    //role
    ROLE_PARAM_ERROR(201,"角色参数错误"),
    ROLE_NAME_EXEITS(202,"角色名称已存在"),
    ROLE_ID_ERROR(203,"角色id错误"),

    IMAGE_CALENDAR_EXISTS(204,"该展示时间已存在数据，请核对！"),

    //sign in
    ALREADY_SIGN_IN(301,"重复签到！"),
    ERROR_SIGN_IN(302,"签到失败！"),

    //score shop
    ERROR_EXCHANGE_SCORE_GOODS(401,"兑换失败！"),
    SCORE_OR_USER_PARAM_ERROR(402,"用户或积分商品参数id不能为空！"),
    SCORE_OR_USER__NOT_EXIST(403,"用户或积分商品不存在！"),
    SCORE_NOT_ENOUGH(404,"积分不足！"),
    SCORE_DELETE_ERROR_HAS_EXCHANGE_RECORD(405,"存在兑换记录，不予删除，只能下架！"),

    //文件上传下载
    UPLOAD_ERROR(900,"上传失败"),
    UPLOAD_OUTBOUND(902,"文件超出最大允许"),
    UPLOAD_ILLEGAL(903,"文件格式不合法"),
    UPLOAD_CONTENT_INVALID(904, "文件内容不合法"),
    DOWNLOAD_ERROR(901,"下载失败"),

    //works collection
    ALREADY_LIKES_TODAY(500,"一天只能点一个赞！"),

    //image calendar
    THUMBNAIL_CANNOT_EMPTY(600,"请上传缩略图！"),
    POSTER_CANNOT_EMPTY(600,"请上传海报！"),

    ;

    private Integer code;
    private String desc;

    ResultCodeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 根据desc查找enum
     * @param desc
     * @return
     */
    public static ResultCodeEnum getCodeByDesc(String desc){
        if(StringUtils.isEmpty(desc)) return null;
        for(ResultCodeEnum t : ResultCodeEnum.values()){

            if(desc.equals(t.toString())){
                return t;
            }
        }
        return null;
    }
}