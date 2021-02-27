package cn.net.vidyo.dylink.common;

import java.io.Serializable;

/**
 * Created by laochai on 2020/3/12.
 */
public class Result implements Serializable {
    private static final long serialVersionUID = 4633451373316892528L;
    //
    public boolean success = true;

    public int code = 0;

    // 响应业务状态
    public int status = 200;

    // 响应消息
    public String msg = "操作成功";

    // 响应数据
    public Object data = null;

    int status_code=0;

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    /**
     *
     * <p>
     * Title: 响应失败
     * </p>
     *
     * @return Result
     */
    public static Result fail() {
        return fail("操作失败",100000);
    }

    /**
     *
     * <p>
     * Title: 响应失败
     * </p>
     *
     * @return Result
     */
    public static Result fail(IEnumCodes EnumCodes) {
        return bulid(400, EnumCodes.getDisplay(), null, false,EnumCodes.getCode());
    }

    /**
     *
     * <p>
     * Title: 响应失败，但是自定义响应消息l
     * </p>
     *
     * @param msg 需要自定义的响应消息
     * @return Result
     */
    public static Result fail(String msg, int code) {
        return bulid(400, msg, null, false, code);
    }

    /**
     *
     * <p>
     * Title: 成功并且传递数据，并且自定义响应消息内容
     * </p>
     *
     * @param msg  响应消息内容
     * @param data 响应数据
     * @return Result
     */
    public static Result ok(String msg, Object data) {
        return bulid(200, msg, data, true, 0);
    }

    /**
     *
     * <p>
     * Title: 成功并且传递数据，并且自定义响应消息内容
     * </p>
     *
     * @param data 响应数据
     * @return Result
     */
    public static Result ok(IEnumCodes EnumCodes, Object data) {
        return bulid(200, EnumCodes.getDisplay(), data, true, 0);
    }


    /**
     *
     * <p>
     * Title: 成功并且传递数据，但是不自定义消息
     * </p>
     *
     * @param data 需要传递的数据
     * @return Result
     */
    public static Result ok(Object data) {
        return ok("操作成功", data);
    }

    /**
     *
     * <p>
     * Title: 成功，不传递信息，也不自定义信息
     * </p>
     *
     * @return Result
     */
    public static Result ok() {
        return ok("操作成功", null);
    }

    /**
     * '
     *
     * <p>
     * Title: 成功，不传递数据，但是需要自定响应消息
     * </p>
     *
     * @param msg 需要自定义的响应消息
     * @return Result
     */
    public static Result ok(String msg) {
        return ok(msg, msg);
    }

    /**
     *
     * <p>
     * Title: 自定义响应结构
     * </p>
     *
     * @param status 响应状态
     * @param msg    响应消息
     * @param data   响应数据
     * @return Result
     */
    public static Result bulid(int status, String msg, Object data, boolean success, int code) {
        return new Result(status, msg, data, success, code);
    }


    public Result() {
        super();
    }

    /**
     *
     * @param status
     * @param msg
     * @param data
     */
    public Result(int status, String msg, Object data, boolean success, int code) {
        super();
        this.status = status;
        this.status_code=status;
        this.msg = msg;
        this.data = data;
        this.success = success;
        this.code = code;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
        this.status_code=status;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @return the data
     */
    public Object getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    /*
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Result [status=" + status + ", msg=" + msg + ", data=" + data + "]";
    }
}
