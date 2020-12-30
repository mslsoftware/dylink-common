package cn.net.vidyo.dylink.common;

public enum  CommonEnumCodes implements IEnumCodes{


    NOT_EXIST(1201,"不存在"),
    ALREADY(1202,"已经存在"),
    IS_EMPT(1203,"空置"),


    DB_ACCESS_EXCEPTION(1403,"DB操作错误"),

    UNKNOW(999999,"未知")

    ;

    int code=0;
    String display="";

    CommonEnumCodes(int code, String display) {
        this.code = code;
        this.display = display;
    }

    @Override
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }
}
