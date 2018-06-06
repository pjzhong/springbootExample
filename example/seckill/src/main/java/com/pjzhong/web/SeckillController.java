package com.pjzhong.web;

import com.pjzhong.dto.Exporter;
import com.pjzhong.dto.Result;
import com.pjzhong.dto.SeckillExecution;
import com.pjzhong.entity.Seckill;
import com.pjzhong.enums.SeckillStatEnum;
import com.pjzhong.exception.RepeatKillException;
import com.pjzhong.exception.SeckillCloseException;
import com.pjzhong.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/seckill") //url:/模块/资源/{id}/细分
public class SeckillController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        List<Seckill> seckills = seckillService.getSeckills();
        model.addAttribute("seckills", seckills);
        return "page/list";
    }

    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String detai(@PathVariable("seckillId") Integer seckillId, Model model) {
        if(seckillId == null) { return "redirect:/seckill/list"; }

        Seckill seckill = seckillService.getSeckill(seckillId);
        if(seckill == null) { return "forward:/seckill/list"; }

        model.addAttribute("seckill", seckill);

        return "page/detail";
    }

    @RequestMapping(value = "/{seckillId}/exporter", method = RequestMethod.POST)
    @ResponseBody
    public Result<Exporter> /*todo*/ exporter(@PathVariable("seckillId") Integer seckillId) {
        Result<Exporter> result;
        try {
            Exporter exporter = seckillService.exportSeckillUrl(seckillId);
            result = new Result<Exporter>(true, exporter);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = new Result<Exporter>(false, e.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/{seckillId}/{md5}/execution", method = RequestMethod.POST)
    @ResponseBody
    public Result<SeckillExecution> execute(@PathVariable("seckillId") int seckillId,
                                            @PathVariable("md5") String md5,
                                            @CookieValue(value = "killPhone", required = false) Long phone) {

        Result<SeckillExecution> result = null;
        if(phone == null) { return new Result<>(false, "未注册"); }
        try {
            SeckillExecution execution = seckillService.executeSeckkill(seckillId, phone, md5);
            result = new Result<>(true, execution);
        } catch (RepeatKillException e) {
            result =  new Result<>(true, new SeckillExecution(seckillId, SeckillStatEnum.REPEAT_KILL));
        } catch (SeckillCloseException e) {
            result =  new Result<>(true, new SeckillExecution(seckillId, SeckillStatEnum.END));
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            result =  new Result<>(true, new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR));
        }

        return result;
    }

    @RequestMapping(value = "/time", method = RequestMethod.GET)
    @ResponseBody
    public Result<Long> time() {
        return new Result<>(true, new Date().getTime());
    }

}
