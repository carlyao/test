package com.upchina.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tk.mybatis.mapper.entity.Example;

import com.upchina.Exception.UpChinaError;
import com.upchina.auth.EncryptParam;
import com.upchina.auth.EncryptResponse;
import com.upchina.model.Note;
import com.upchina.model.UserComment;
import com.upchina.model.UserOrder;
import com.upchina.service.NoteService;
import com.upchina.service.PushMessageService;
import com.upchina.service.UserInfoService;
import com.upchina.util.Constants;
import com.upchina.vo.BaseOutVo;
import com.upchina.vo.PageRequestVo;
import com.upchina.vo.jqGridResponseVo;
import com.upchina.vo.rest.input.ClickZanInVo;
import com.upchina.vo.rest.input.NoteHotInVo;
import com.upchina.vo.rest.input.NoteInVo;
import com.upchina.vo.rest.output.NoteOutVo;
import com.upchina.vo.rest.output.UserCommentOutVo;
import com.upchina.vo.rest.output.UserProfileVo;

/**
 * Created by Administrator on 2015-12-10 16:37:43
 */
@Controller
@RequestMapping("/note")
public class NoteController extends BaseController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private PushMessageService pushMessageService;
    
    @Autowired
    private UserInfoService userInfoService;
	
    @ResponseBody
    @RequestMapping(value = "getMyList")
    @EncryptParam(paramName="noteInVo",paramClass=NoteInVo.class)
    public Object getMyList(NoteInVo noteInVo,HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	try {
    		if(noteInVo.getUserId()!=null){
    			PageRequestVo page = new PageRequestVo(noteInVo.getPageNum(),noteInVo.getPageSize());
    			Example example=new Example(Note.class);
    			example.createCriteria().andEqualTo("userId", noteInVo.getUserId()).andEqualTo("status", Constants.NOTE_PUBLISH);
    			example.setOrderByClause(" publishTime desc");
    			page.setExample(example);
    			jqGridResponseVo<Note> pageList = noteService.pageJqGrid(page);
    			res.setResultData(pageList);
        		res.setResultCode(UpChinaError.SUCCESS.code);
//    			return pageList;
    		}else{
    			res.setResultCode(UpChinaError.PARAM_ERROR.code);
    			res.setResultMsg("userId不能为空");
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    		res.setResultCode(UpChinaError.ERROR.code);
    		res.setResultMsg(UpChinaError.ERROR.message);
    	}
    	return res;
    }
    
    @ResponseBody
    @RequestMapping(value = "getBestList")
    public Object getBestList(NoteInVo noteInVo,HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	try {
    		jqGridResponseVo<NoteOutVo> pageList = noteService.getBestList(noteInVo);
    		res.setResultData(pageList);
    		res.setResultCode(UpChinaError.SUCCESS.code);
    	} catch (Exception e) {
    		e.printStackTrace();
    		res.setResultCode(UpChinaError.ERROR.code);
    		res.setResultMsg(UpChinaError.ERROR.message);
    	}
		return res;
    }
    
    @ResponseBody
    @RequestMapping(value = "getLatestList")
    public Object getLatestList(NoteInVo noteInVo,HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	try {
    		jqGridResponseVo<NoteOutVo> pageList = noteService.getLatestList(noteInVo);
    		res.setResultData(pageList);
    		res.setResultCode(UpChinaError.SUCCESS.code);
    	} catch (Exception e) {
    		e.printStackTrace();
    		res.setResultCode(UpChinaError.ERROR.code);
    		res.setResultMsg(UpChinaError.ERROR.message);
    	}
		return res;
    }
    
    @ResponseBody
    @RequestMapping(value = "getHotestList")
    public Object getHotestList(NoteHotInVo noteInVo,HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	try {
    		jqGridResponseVo<NoteOutVo> pageList = noteService.getHotestList(noteInVo);
    		res.setResultData(pageList);
    		res.setResultCode(UpChinaError.SUCCESS.code);
//    		if(noteInVo.getUserId()!=null){
//    		}else{
//    			res.setResultCode(UpChinaError.USERID_NULL_ERROR.code);
//    			res.setResultMsg(UpChinaError.USERID_NULL_ERROR.message);
//    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    		res.setResultCode(UpChinaError.ERROR.code);
    		res.setResultMsg(UpChinaError.ERROR.message);
    	}
    	return res;
    }

    /**
     * 根据关键字搜索笔记
     * @param key
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "searchNote")
    public Object searchNote(String key,HttpServletRequest request) {
    	BaseOutVo res = new BaseOutVo();
    	try{
    		if(null != key && !"".equals(key)){
    			List<Note> noteList = this.noteService.getNoteListByKey(key);
    			res.setResultData(noteList);
    			res.setResultCode(UpChinaError.SUCCESS.code);
    			res.setResultMsg(UpChinaError.SUCCESS.message);
    		}else{
    			res.setResultCode(UpChinaError.SEARCH_CONDITION_IS_NULL.code);
                res.setResultMsg(UpChinaError.SEARCH_CONDITION_IS_NULL.message);
    		}
    	}catch(Exception e){
    		e.printStackTrace();
            res.setResultCode(UpChinaError.ERROR.code);
            res.setResultMsg(UpChinaError.ERROR.message);
    	}
        return res;
    }
    
    /**
     * 给笔记点赞
     * @param noteId
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "clickZan")
    @EncryptParam(paramName="clickZanInVo",paramClass=ClickZanInVo.class)
    public Object clickZan(ClickZanInVo clickZanInVo,HttpServletRequest request) {
    	BaseOutVo res = new BaseOutVo();
    	
    	Integer noteId = clickZanInVo.getNoteId();
    	Integer userId = clickZanInVo.getUserId();
    	
    	try{
    		if(null == noteId || "".equals(noteId)){
    			res.setResultCode(UpChinaError.NOTE_ID_IS_NULL.code);
                res.setResultMsg(UpChinaError.NOTE_ID_IS_NULL.message);
    		}
    		if(null == userId || "".equals(userId)){
    			res.setResultCode(UpChinaError.USERID_NULL_ERROR.code);
                res.setResultMsg(UpChinaError.USERID_NULL_ERROR.message);
    		}
    		if(null != noteId && !"".equals(noteId) && null != userId && !"".equals(userId)){
    			String favoritesCount = this.noteService.clickZan(noteId,userId);
    			if(Constants.NOTE_NOT_EXIST.equals(favoritesCount)){
    				res.setResultCode(UpChinaError.NOTE_NOT_EXIST.code);
                    res.setResultMsg(UpChinaError.NOTE_NOT_EXIST.message);
    			}else if(Constants.ZAN_FAIL.equals(favoritesCount)){
    				res.setResultCode(UpChinaError.NOTE_CHECK_ZAN_FAIL.code);
                    res.setResultMsg(UpChinaError.NOTE_CHECK_ZAN_FAIL.message);
    			}else if(Constants.NOT_ALLOWED_ZAN.equals(favoritesCount)){
    				res.setResultCode(UpChinaError.NOTE_NOTE_ALLOWED_ZAN.code);
                    res.setResultMsg(UpChinaError.NOTE_NOTE_ALLOWED_ZAN.message);
    			}else if(Constants.ALREADY_ZAN.equals(favoritesCount)){
    				res.setResultCode(UpChinaError.NOTE_ALREADY_ZAN.code);
                    res.setResultMsg(UpChinaError.NOTE_ALREADY_ZAN.message);
    			}else{
    				res.setResultData(favoritesCount);
    				res.setResultCode(UpChinaError.SUCCESS.code);
    				res.setResultMsg(UpChinaError.SUCCESS.message);
    				//点赞成功后，向user_favorite表中插入数据
    				this.noteService.insertUserFavorite(noteId,userId);
    			}
    		}
    	}catch(Exception e){
    		e.printStackTrace();
            res.setResultCode(UpChinaError.ERROR.code);
            res.setResultMsg(UpChinaError.ERROR.message);
    	}
        return res;
    }
    
    /**
     * 购买笔记
     * @param key
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "buyNote")
    @EncryptParam(paramName="userOrder",paramClass=UserOrder.class)
    public Object buyNote(UserOrder userOrder,HttpServletRequest request) {
    	BaseOutVo res = new BaseOutVo();
    	try{
    		Integer userId = userOrder.getUserId();//用户ID
    		Double payment = userOrder.getPayment();//付费金额
    		Integer orderId = userOrder.getOrderId();//这里指的是笔记ID
    		Note note = noteService.selectByPrimaryKey(orderId);
    		if(null != userId && null != payment && null != orderId&&null!=note){
    			if(note.getType()==1){
    				userOrder.setStatus(2);
    				userOrder.setTradeType(Constants.TRADE_TYPE_SUBSCRIBE);
    			}
    			Integer flag = this.noteService.buyhNote(userOrder,note);
    			if(1 == flag){
    				res.setResultCode(UpChinaError.SUCCESS.code);
    				res.setResultMsg(UpChinaError.SUCCESS.message);
    				//笔记购买成功后，笔记的销售数量增加1
    				this.noteService.saleCountsAddOne(note);
    			}else{
    				res.setResultCode(UpChinaError.NOTE_BUY_FAIL.code);
                    res.setResultMsg(UpChinaError.NOTE_BUY_FAIL.message);
    			}
    		}else{
    			res.setResultCode(UpChinaError.NOTE_CONDITION_IS_NULL.code);
                res.setResultMsg(UpChinaError.NOTE_CONDITION_IS_NULL.message);
    		}
    	}catch(Exception e){
    		e.printStackTrace();
            res.setResultCode(UpChinaError.ERROR.code);
            res.setResultMsg(UpChinaError.ERROR.message);
    	}
        return res;
    }
    
    /**
     * Note添加和编辑
     * **/
    @EncryptParam(paramName="noteInVo",paramClass=NoteInVo.class)
    @ResponseBody
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Object add(@ModelAttribute NoteInVo noteInVo,HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	try {
    		String msg=addValidate(noteInVo);
    		if("".equals(msg)){
    			noteService.add(noteInVo);
    			res.setResultCode(UpChinaError.SUCCESS.code);
    		}else{
    			res.setResultCode(UpChinaError.PARAM_ERROR.code);
    			res.setResultMsg(msg);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    		res.setResultCode(UpChinaError.ERROR.code);
    		res.setResultMsg(UpChinaError.ERROR.message);
    	}
    	return res;
    }
    
    private String addValidate(Note note) {
    	String msg="";
    	if (null == note.getUserId()) {
			msg = "userId不能为空";
		} else if (StringUtils.isEmpty(note.getTitle())) {
			msg = "title不能为空";
		} else if (StringUtils.isEmpty(note.getContent())) {
			msg = "content不能为空";
		} else if (null == note.getType()) {
			msg = "type不能为空";
		} else if (note.getType()==2&&null == note.getCost()) {
			msg = "cost不能为空";
		}
		return msg;
	}
    /**
     * Note添加和编辑
     * **/
    @EncryptResponse
    @EncryptParam(paramName="noteInVo",paramClass=NoteInVo.class)
    @ResponseBody
    @RequestMapping(value = "view", method = RequestMethod.POST)
    public Object view(NoteInVo noteInVo,HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	try {
    		String msg=viewValidate(noteInVo);
    		if("".equals(msg)){
    			NoteOutVo noteOutVo=noteService.view(noteInVo);
    			res.setResultData(noteOutVo);
    			res.setResultCode(UpChinaError.SUCCESS.code);
    		}else{
    			res.setResultCode(UpChinaError.PARAM_ERROR.code);
    			res.setResultMsg(msg);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    		res.setResultCode(UpChinaError.ERROR.code);
    		res.setResultMsg(UpChinaError.ERROR.message);
    	}
    	return res;
    }
    
    /**
     * Note添加和编辑
     * **/
    @ResponseBody
    @RequestMapping(value = "viewFree", method = RequestMethod.POST)
    public Object viewFree(NoteInVo noteInVo,HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	try {
    		String msg=viewValidate(noteInVo);
    		if("".equals(msg)){
    			NoteOutVo noteOutVo=noteService.viewFree(noteInVo);
    			res.setResultData(noteOutVo);
    			res.setResultCode(UpChinaError.SUCCESS.code);
    		}else{
    			res.setResultCode(UpChinaError.PARAM_ERROR.code);
    			res.setResultMsg(msg);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    		res.setResultCode(UpChinaError.ERROR.code);
    		res.setResultMsg(UpChinaError.ERROR.message);
    	}
    	return res;
    }
    
    private String viewValidate(Note note) {
    	String msg="";
    	if (null == note.getId()) {
    		msg = "id不能为空";
    	}
    	return msg;
    }
    /**
     * Note添加和编辑
     * **/
    @ResponseBody
    @RequestMapping(value = "comment", method = RequestMethod.POST)
    @EncryptParam(paramName="userComment",paramClass=UserComment.class)
    public Object comment(UserComment userComment,HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	try {
    		String msg=commentValidate(userComment);
    		if("".equals(msg)){
    			noteService.comment(userComment);
    			res.setResultCode(UpChinaError.SUCCESS.code);
    		}else{
    			res.setResultCode(UpChinaError.PARAM_ERROR.code);
    			res.setResultMsg(msg);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    		res.setResultCode(UpChinaError.ERROR.code);
    		res.setResultMsg(UpChinaError.ERROR.message);
    	}
    	return res;
    }
    
    private String commentValidate(UserComment userComment) {
    	String msg="";
    	if (null == userComment.getTargetId()) {
    		msg = "targetId不能为空";
    	}else if(null == userComment.getUserId()){
    		msg = "userId不能为空";
    	}else if(StringUtils.isEmpty(userComment.getContent())){
    		msg = "content不能为空";
    	}
    	return msg;
    }
    /**
     * Note添加和编辑
     * **/
    @ResponseBody
    @RequestMapping(value = "commentList", method = RequestMethod.POST)
    public Object commentList(NoteInVo noteInVo,HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	try {
    		String msg=commentListValidate(noteInVo);
    		if("".equals(msg)){
    			jqGridResponseVo<UserCommentOutVo> pageList=noteService.getCommentList(noteInVo);
    			res.setResultData(pageList);
        		res.setResultCode(UpChinaError.SUCCESS.code);
    		}else{
    			res.setResultCode(UpChinaError.PARAM_ERROR.code);
    			res.setResultMsg(msg);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    		res.setResultCode(UpChinaError.ERROR.code);
    		res.setResultMsg(UpChinaError.ERROR.message);
    	}
    	return res;
    }
    
    private String commentListValidate(NoteInVo noteInVo) {
    	String msg="";
    	if (null == noteInVo.getId()) {
    		msg = "id不能为空";
    	}
    	return msg;
    }
    
	@ResponseBody
	@RequestMapping(value = "queryRecommendNotes")
	public Object queryRecommendNotes(){
		BaseOutVo res=new BaseOutVo();
		try{
			List<NoteOutVo> notes = noteService.getRecommendNotes();
			for(NoteOutVo note:notes){
				UserProfileVo userProfileVo = userInfoService.findUserProfile(note.getUserId());
				if(userProfileVo!=null){
					note.setUserName(userProfileVo.getUserName());
					note.setAvatar(userProfileVo.getAvatar());
					note.setAdviserType(userProfileVo.getAdviserType());
				}
			}
			res.setResultData(notes);
			res.setResultCode(UpChinaError.SUCCESS.code);
			res.setResultMsg(UpChinaError.SUCCESS.message);
		}catch(Exception e){
			res.setResultCode(UpChinaError.ERROR.code);
			res.setResultMsg(UpChinaError.ERROR.message);
		}
		return res;
	}

}
