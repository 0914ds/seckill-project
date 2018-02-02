package cloud.seckilled.service.web;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cloud.seckilled.service.service.SeckillService;
import seckill.common.dto.SeckillExecution;
import seckill.common.model.Seckill;


@RestController
@RequestMapping("/seckill")//url:/模块/资源/{id}/细分
public class SeckillController {

    private Log LOG = LogFactory.getLog(this.getClass());

    @Autowired
    private SeckillService seckillService;

     @Value("${server.port}")
     private String port;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Seckill> list() {
        System.out.println("==========>>>>>>this is service:  "+port);
        List<Seckill> list = seckillService.getSeckillList();
        return list;
    }


    @RequestMapping(value = "/{seckillId}", method = RequestMethod.GET)
    public Seckill getById(@PathVariable("seckillId") Long seckillId) {
      return	seckillService.getById(seckillId);
    }

    @RequestMapping(value = "/{seckillId}/{killPhone}/{md5}", method = RequestMethod.POST)
    public SeckillExecution execute(@PathVariable("seckillId") Long seckillId,@PathVariable(value = "killPhone") Long killPhone, @PathVariable("md5") String md5){
    	SeckillExecution execution = seckillService.executeSeckill(seckillId, killPhone, md5);
    	return execution;
    	
    }
    
  

}
