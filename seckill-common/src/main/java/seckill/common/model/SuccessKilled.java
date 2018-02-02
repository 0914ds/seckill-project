package seckill.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wchb7 on 16-5-8.
 */
public class SuccessKilled implements Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = -7643201214336645766L;


	/**
     * 涓?涓鏉?seckill瀵瑰簲澶氫釜鎴愬姛璁板綍
     */
    private Seckill seckill;

    private long seckillId;

    private long userPhone;

    private short state;

    private Date createTime;

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Seckill getSeckill() {
        return seckill;
    }

    public void setSeckill(Seckill seckill) {
        this.seckill = seckill;
    }

    @Override
    public String toString() {
        return "SuccessKilled{" +
                "seckill=" + seckill +
                ", seckillId=" + seckillId +
                ", userPhone=" + userPhone +
                ", state=" + state +
                ", createTime=" + createTime +
                '}';
    }
}
