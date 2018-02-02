package cloud.seckilled.service.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cloud.seckilled.service.dao.RedisDao;
import cloud.seckilled.service.dao.SeckillDao;
import cloud.seckilled.service.dao.SuccessKilledDao;
import seckill.common.dto.SeckillExecution;
import seckill.common.enu.SeckillStatEnum;
import seckill.common.exception.RepeatKillException;
import seckill.common.exception.SeckillCloseException;
import seckill.common.exception.SeckillException;
import seckill.common.model.Seckill;
import seckill.common.model.SuccessKilled;
import seckill.common.tools.Tools;

@Service
public class SeckillServiceImpl implements SeckillService {

    private Log LOG = LogFactory.getLog(this.getClass());

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private RedisDao redisDao;
    @Autowired
    private  SuccessKilledDao successKilledDao ;



    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 1000);
    }

    public Seckill getById(Long seckillId) {
    	Seckill seckill = redisDao.getSeckill(seckillId);
        if (seckill == null) {
            //2.访问数据库
            seckill = seckillDao.queryById(seckillId);
            if (seckill == null) {
                return null;
            } else {
                //3.放入redis
                redisDao.putSeckill(seckill);
            }
        }
        return seckill;
        
        
    }

    @Transactional
    /**
     * 使用注解控制事务的优点:
     * 1.开发团队达成一致约定,明确标注事务方法的编程风格.
     * 2.保证事务方法的执行时间尽可能短,不要穿插其他网络操作RPC/HTTP请求或者剥离到事务方法外部.
     * 3.不是所有的方法都需要事务.如一些查询的service.只有一条修改操作的service.
     */
    public SeckillExecution executeSeckill(Long seckillId, Long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {

        if (StringUtils.isEmpty(md5) || !md5.equals(Tools.getMD5(seckillId))) {
            throw new SeckillException(SeckillStatEnum.DATA_REWRITE.getStateInfo());
        }

        //执行秒杀逻辑:1.减库存.2.记录购买行为
        Date nowTime = new Date();

        try {

            //记录购买行为
            int inserCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);

            if (inserCount <= 0) {
                //重复秒杀
                throw new RepeatKillException(SeckillStatEnum.REPEAT_KILL.getStateInfo());
            } else {
                //减库存  热点商品竞争
                int updateCount = seckillDao.reduceNumber(seckillId, nowTime);

                if (updateCount <= 0) {
                    //rollback
                    throw new SeckillCloseException(SeckillStatEnum.END.getStateInfo());
                } else {
                    //秒杀成功  commit
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
                }
            }
        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            //所有的编译期异常转化为运行期异常,spring的声明式事务做rollback
            throw new SeckillException("seckill inner error: " + e.getMessage());
        }


    }
    
  
}
