package seckill.common.dto;

import java.io.Serializable;

/**
 * Created by wchb7 on 16-5-23.
 */

//DTO:完成WEB层到Service层的数据传�??
//�?有的ajax请求的返回类型封装JSON结果
public class SeckillResult<T> implements Serializable {

    private static final long serialVersionUID = -3936895148526393338L;

    private boolean success;

    private T data;

    private String error;

    public SeckillResult(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public SeckillResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "SeckillResult{" +
                "success=" + success +
                ", data=" + data +
                ", error='" + error + '\'' +
                '}';
    }
}
