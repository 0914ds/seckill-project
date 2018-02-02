package cloud.seckilled.service.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import seckill.common.model.Seckill;

public interface SeckillDao {

    /**
     * 减库存
     *
     * @param seckillId
     * @param killTime
     * @return　如果更新行数大于1,表示更新的行数
     */
    Integer reduceNumber(@Param("seckillId") Long seckillId, @Param("killTime") Date killTime);


    /**
     * 根据ID查询秒杀对象
     *
     * @param seckillId
     * @return
     */
    Seckill queryById(Long seckillId);


    /**
     * 根据偏移量查询秒杀商品列表
     *
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryAll(@Param("offset") Integer offset, @Param("limit") Integer limit);

}
