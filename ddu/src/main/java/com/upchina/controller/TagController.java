package com.upchina.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.upchina.Exception.UpChinaError;
import com.upchina.model.Tag;
import com.upchina.service.TagService;
import com.upchina.vo.BaseOutVo;
import com.upchina.vo.jqGridResponseVo;
import com.upchina.vo.rest.TagTypeInVo;
import com.upchina.vo.rest.output.NiuGroupTagOutVo;
import com.upchina.vo.rest.output.TagOutVo;

/**
 * Created by Administrator on 2015-12-11 15:08:32
 */
@Controller
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;
    
    /**
     * 根据标签类型查询标签
     * @param tagTypeInVo
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getNiuGroupTag")
    public Object getNiuGroupTag(HttpServletRequest request) {
		BaseOutVo res = new BaseOutVo();
		List<Tag> initiateTags = tagService.selectNiuGroupTagType(4);
		List<Tag> decisionTags = tagService.selectNiuGroupTagType(5);
		List<Tag> shareTags = tagService.selectNiuGroupTagType(6);
		NiuGroupTagOutVo groupTagOutVo = new NiuGroupTagOutVo();
		groupTagOutVo.setInitiateTags(initiateTags);
		groupTagOutVo.setDecisionTags(decisionTags);
		groupTagOutVo.setShareTags(shareTags);
		res.setResultData(groupTagOutVo);
		res.setResultCode(UpChinaError.SUCCESS.code);
		res.setResultMsg(UpChinaError.SUCCESS.message);
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "getGroupHotTag", method = RequestMethod.GET)
    public Object getGroupHotTag(HttpServletRequest request) {
    	BaseOutVo baseOutVo = new BaseOutVo();
    	List<TagOutVo> tagVos = tagService.getGroupHotTag();
    	baseOutVo.setResultCode(UpChinaError.SUCCESS.code);
    	baseOutVo.setResultMsg(UpChinaError.SUCCESS.message);
    	baseOutVo.setResultData(tagVos);
    	return baseOutVo;
    }
    
    /*
     * 根据类型返回标签
     */
    @ResponseBody
    @RequestMapping(value = "getList")
    public Object getList(TagTypeInVo tagTypeInVo,HttpServletRequest request) {
        jqGridResponseVo<Tag> pageList = tagService.selectTagByType(tagTypeInVo);
        return pageList;
    }
    
}
