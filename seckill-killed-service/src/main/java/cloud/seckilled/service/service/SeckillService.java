package cloud.seckilled.service.service;
import java.util.List;

import seckill.common.dto.SeckillExecution;
import seckill.common.exception.RepeatKillException;
import seckill.common.exception.SeckillCloseException;
import seckill.common.exception.SeckillException;
import seckill.common.model.Seckill;

public interface SeckillService {


    /**
     * 查询所有秒杀记录
     *
     * @return
     */
    List<Seckill> getSeckillList();


    /**
     * 查询单个秒杀记录
     *
     * @param seckillId
     * @return
     */
    Seckill getById(Long seckillId);


    /**
     * 执行秒杀操作
     *
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    SeckillExecution executeSeckill(Long seckillId, Long userPhone, String md5) throws SeckillException
            , RepeatKillException, SeckillCloseException;
    
}
