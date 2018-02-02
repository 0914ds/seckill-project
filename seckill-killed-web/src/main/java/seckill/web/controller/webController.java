package seckill.web.controller;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import seckill.common.dto.Exposer;
import seckill.common.dto.SeckillExecution;
import seckill.common.dto.SeckillResult;
import seckill.common.enu.SeckillStatEnum;
import seckill.common.exception.RepeatKillException;
import seckill.common.exception.SeckillCloseException;
import seckill.common.model.Seckill;
import seckill.web.service.WebService;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/seckill")
public class webController {

	private Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private WebService webService;

	@RequestMapping(value = "/")
	public String indexJsp() {
		return "index";
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		List<Seckill> list = webService.getSeckillList();
		model.addAttribute("list", list);
		log.info("get SeckillList ==>"+list);
		return "list";
	}

	@RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
	public String detail(@PathVariable("seckillId") Long seckillId, Model model) {

		if (seckillId == null) {
			return "redirect:/seckill/list";
		}

		Seckill seckill = webService.getById(seckillId);

		if (seckill == null) {
			return "forward:/seckill/list";
		}

		model.addAttribute("seckill", seckill);

		log.info("get by id ==>"+seckill);
		return "detail";

	}

	// ajax json
	@RequestMapping(value = "/{seckillId}/exposer", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId) {

		SeckillResult<Exposer> result;

		try {
			Exposer exposer = webService.exportSeckillUrl(seckillId);
			result = new SeckillResult<Exposer>(true, exposer);
		} catch (Exception e) {
			log.error(e.getMessage());
			result = new SeckillResult<Exposer>(false, e.getMessage());
		}

		return result;
	}

	@RequestMapping(value = "/{seckillId}/{md5}/execution", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
			@PathVariable("md5") String md5, @CookieValue(value = "killPhone", required = false) Long killPhone) {

		if (killPhone == null) {
			return new SeckillResult<SeckillExecution>(false, SeckillStatEnum.NOT_LOGIN.getStateInfo());
		}

		try {
			SeckillExecution execution = webService.execute(seckillId, killPhone, md5);
			// SeckillExecution execution =
			// seckillService.executeSeckillProcedure(seckillId, killPhone, md5);
			return new SeckillResult<SeckillExecution>(true, execution);
		} catch (RepeatKillException e) {
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.REPEAT_KILL);
			return new SeckillResult<SeckillExecution>(true, execution);

		} catch (SeckillCloseException e2) {
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.END);
			return new SeckillResult<SeckillExecution>(true, execution);

		} catch (Exception e) {
			log.error(e.getMessage());
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
			return new SeckillResult<SeckillExecution>(true, execution);
		}

	}

	@RequestMapping(value = "/time/now", method = RequestMethod.GET)
	@ResponseBody
	public SeckillResult<Long> time() {
		Date now = new Date();
		return new SeckillResult(true, now.getTime());
	}
}
