package com.upchina.controller;

import com.upchina.Exception.UpChinaError;
import com.upchina.auth.EncryptParam;
import com.upchina.model.Live;
import com.upchina.model.NiuGroup;
import com.upchina.model.NiuGroupContent;
import com.upchina.model.UserInfo;
import com.upchina.service.LiveContentService;
import com.upchina.service.NiuGroupContentService;
import com.upchina.service.NiuGroupService;
import com.upchina.util.Constants;
import com.upchina.vo.BaseOutVo;
import com.upchina.vo.rest.input.LiveMessageInVo;
import com.upchina.vo.rest.input.NiuGroupContentInVo;
import com.upchina.vo.rest.input.PullLiveContentInVo;
import com.upchina.vo.rest.output.PullLiveContentOutVo;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/**
 * Created by Administrator on 2015-12-11 15:08:32
 */
@Controller
@RequestMapping("/niuGroupContent")
public class NiuGroupContentController {

    private static Logger logger = LoggerFactory.getLogger(NiuGroupContentController.class);

    @Autowired
    private NiuGroupContentService niuGroupContentService;

    @Autowired
    private NiuGroupService niuGroupService;

    @ResponseBody
    @RequestMapping(value = "pushNiuGroupContent", method = RequestMethod.POST)
    public Object pushNiuGroupContent(@ModelAttribute("niuGroupContentInVo") NiuGroupContentInVo niuGroupContentInVo, HttpServletRequest request) {
        BaseOutVo res = new BaseOutVo();
        NiuGroupContent niuGroupContent = new NiuGroupContent();
        try {
            PropertyUtils.copyProperties(niuGroupContent, niuGroupContentInVo);
            niuGroupContent.setCreateTime(new Date());
            niuGroupContentService.insertSelective(niuGroupContent);
            /*niuGroupContentService.pushContent(niuGroupContent);*/
            res.setResultMsg("保存牛圈消息成功");
            res.setResultCode(UpChinaError.SUCCESS.code);
        } catch (Exception e) {
            res.setResultMsg("保存牛圈消息失败");
            res.setResultCode(UpChinaError.ERROR.code);
            logger.error(e.getLocalizedMessage());
        }
        return res;
    }
}
