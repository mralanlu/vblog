package com.northbund.vblog.common.enums;

public enum AttachmentTypeEnum {

    CALENDAR(1,"影像日历"),
    AVATAR(2,"头像"),
    WORKS(3,"征稿作品"),
    SCORE_GOODS(4,"积分商品"),
    FAST_NEWS(5,"热点快讯"),
    THUMBNAIL(6,"影像日历缩略图"),
    POSTER(7,"影像日历海报"),
    BANNER(8,"Banner"),
    ;


    private int code;
    private String desc;

    AttachmentTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(Byte code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
