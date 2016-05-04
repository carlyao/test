package com.upchina.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.upchina.dao.NoteMapper;
import com.upchina.model.ActionLog;
import com.upchina.model.Note;
import com.upchina.model.NoteTag;
import com.upchina.model.Tag;
import com.upchina.model.UserComment;
import com.upchina.model.UserFavorite;
import com.upchina.model.UserInfo;
import com.upchina.model.UserOrder;
import com.upchina.util.Constants;
import com.upchina.util.ImagePathUtil;
import com.upchina.vo.PageVo;
import com.upchina.vo.jqGridResponseVo;
import com.upchina.vo.rest.input.NoteHotInVo;
import com.upchina.vo.rest.input.NoteInVo;
import com.upchina.vo.rest.output.NoteOutVo;
import com.upchina.vo.rest.output.PushMessageNoteOutVo;
import com.upchina.vo.rest.output.PushMessageUserOutVo;
import com.upchina.vo.rest.output.TagOutVo;
import com.upchina.vo.rest.output.UserCommentOutVo;
import com.upchina.vo.rest.output.UserProfileVo;
import com.upchina.vo.rest.output.UserTagOutVo;
/**
 * Created by codesmith on 2015
 */

@Service("noteService")
public class NoteService  extends BaseService<Note, Integer>{
	
	@Autowired
	private NoteMapper noteMapper;
	 
    @Autowired
    private NoteTagService noteTagService;
	
    @Autowired
    private UserOrderService userOrderService;
    
    @Autowired
    private UserCommentService userCommentService;
    
    @Autowired
    private UserFavoriteService userFavoriteService;
    
    @Autowired
	private UserInfoService userInfoService;

    @Autowired
    private PushMessageService pushMessageService;
	
	@Autowired
	private UserFriendService userFriendService;
	
	@Autowired
	private ActionLogService actionLogService;
     /**
     * 判断字段是否存在（插入和修改时可用,修改本身时如判断字段未发生修改会忽略）
     * @param inputName 要判断的字段名
     * @param value 要判断的值
     * @param id 当前记录id
     * @return 是否存在
     */
    public boolean isExist(String inputName,String value,int id) {
        Example exp = new Example(Note.class);
        Example.Criteria criteria = exp.createCriteria();
        criteria.andEqualTo(inputName, value);
        List<Note> list=selectByExample(exp);
        if(list!=null&&list.size()>0){//有同名的
            if(id==0){//是添加的
                return true;
            }
            else{//是修改的
                criteria.andNotEqualTo("id", id);
                list=selectByExample(exp);
                if(list.size()>0)//说明不是他本身
                {
                    return true;
                }
            }
        }
        return false;
    }
    
	/**
	 * 根据关键字搜索笔记
	 * @param key
	 * @return
	 */
	public List<Note> getNoteListByKey(String key) {
		List<Note> noteList = new ArrayList<Note>();
		noteList = this.noteMapper.getNoteListByKey(key,Constants.NOTE_PUBLISH);
		return noteList;
	}


	public void add(NoteInVo noteInVo) throws Exception {
		Note note=new Note();
		BeanUtils.copyProperties(note, noteInVo);
		note.setCreateTime(new Date());
		
		if(note.getStatus() != null && 0 == note.getStatus()){//0为发布状态，web设置
			note.setPublishTime(new Date());
		}
		
		if(null == note.getStatus()){//为接口
			note.setStatus(Constants.NOTE_PUBLISH);
			note.setPublishTime(new Date());
		}
		
		insertSelective(note);
		
		Integer noteId = note.getId();
		noteInVo.setId(note.getId());
		List<Integer> tagIds=noteInVo.getTagId();
		if(tagIds!=null&&tagIds.size()>0){
			for (Integer tagId : tagIds) {
				NoteTag noteTag=new NoteTag();
				noteTag.setNoteId(noteId);
				noteTag.setTagId(tagId);
				noteTag.setCreateTime(new Date());
				noteTagService.insert(noteTag);
			}
		}
		
		//发布笔记成功后，将发布记录保存到日志表
		if(0 == note.getStatus()){//笔记为发布状态
			ActionLog actionLog = new ActionLog();
			actionLog.setModuleId(noteId);
			actionLog.setModuleType(1);
			actionLog.setUserId(note.getUserId());
			actionLog.setUserName("");
			actionLog.setTitle(note.getTitle());
			actionLog.setSummary(note.getSummary());
			actionLog.setCreateTime(new Date());
			this.actionLogService.insert(actionLog);
		}
		
		//发布笔记成功后，推送消息给好友
		List<UserInfo> users = userFriendService.getListAllFriend(noteInVo.getUserId());
		PushMessageUserOutVo userMessageInVo = userInfoService.findByUserId(noteInVo.getUserId());
		pushMessageService.pushPublishNoteMessage(users, noteInVo.getUserId(), userMessageInVo.getUserName(), userMessageInVo.getAvatar(), noteInVo.getId(), noteInVo.getTitle());
	}

	public jqGridResponseVo<NoteOutVo> getBestList(NoteInVo noteInVo) throws Exception {
		PageHelper.startPage(noteInVo.getPageNum(), noteInVo.getPageSize());
		List<NoteOutVo> pageList = noteMapper.getBestList(Constants.NOTE_PUBLISH);
		extendNote(pageList);
		for (NoteOutVo noteOutVo : pageList) {
			Integer iaUserId = noteOutVo.getUserId();
			UserProfileVo userProfileVo = userInfoService.findUserProfile(iaUserId);
			if(null != userProfileVo){
				noteOutVo.setUserName(userProfileVo.getUserName());
				noteOutVo.setAdviserType(userProfileVo.getAdviserType());
				noteOutVo.setAvatar(userProfileVo.getAvatar());
			}
		}
		PageInfo<NoteOutVo> pageInfo = new PageInfo(pageList);
		return new jqGridResponseVo(pageInfo.getPages(),pageInfo.getList(),noteInVo.getPageNum(),pageInfo.getTotal());
	}
	
	public jqGridResponseVo<NoteOutVo> getLatestList(NoteInVo noteInVo) throws Exception {
		PageHelper.startPage(noteInVo.getPageNum(), noteInVo.getPageSize());
		List<NoteOutVo> pageList = noteMapper.getLatestList(Constants.NOTE_PUBLISH);
		extendNote(pageList);
		for (NoteOutVo noteOutVo : pageList) {
			Integer iaUserId = noteOutVo.getUserId();
			UserProfileVo userProfileVo = userInfoService.findUserProfile(iaUserId);
			if(null != userProfileVo){
				noteOutVo.setUserName(userProfileVo.getUserName());
				noteOutVo.setAdviserType(userProfileVo.getAdviserType());
				noteOutVo.setAvatar(userProfileVo.getAvatar());
			}
		}
		PageInfo<NoteOutVo> pageInfo = new PageInfo(pageList);
		return new jqGridResponseVo(pageInfo.getPages(),pageInfo.getList(),noteInVo.getPageNum(),pageInfo.getTotal());
	}
	
	public jqGridResponseVo<NoteOutVo> getHotestList(NoteHotInVo noteInVo) throws Exception {
		PageHelper.startPage(noteInVo.getPageNum(), noteInVo.getPageSize());
		List<NoteOutVo> pageList = noteMapper.getHotestList(noteInVo);
		String appId = noteInVo.getAppId();
		if(Constants.APPID_DDU.equals(appId)){
			extendNoteHot(pageList);
		}else{
			for (NoteOutVo noteOutVo : pageList) {
				UserProfileVo userProfileVo = userInfoService.findUserProfile(noteOutVo.getUserId());
				if(null != userProfileVo){
					noteOutVo.setUserName(userProfileVo.getUserName());
					noteOutVo.setAdviserType(userProfileVo.getAdviserType());
					noteOutVo.setAvatar(userProfileVo.getAvatar());
				}
			}
		}
		
		PageInfo<NoteOutVo> pageInfo = new PageInfo(pageList);
		return new jqGridResponseVo(pageInfo.getPages(),pageInfo.getList(),noteInVo.getPageNum(),pageInfo.getTotal());
	}
	
	private void extendNoteHot(List<NoteOutVo> pageList) throws Exception {
		if(pageList!=null&&pageList.size()>0){
			NoteOutVo noteOutVo = pageList.get(0);
			noteOutVo.setAvatar(ImagePathUtil.getImgHost()+"/img/note/default.png");
//			for (NoteOutVo noteOutVo : pageList) {
//				String avatar=noteOutVo.getAvatar();
//				if(StringUtils.isEmpty(avatar)){
//					noteOutVo.setAvatar(ImagePathUtil.getImgHost()+"/img/note/default.png");
//				}else{
//					noteOutVo.setAvatar(ImagePathUtil.getImgHost()+avatar);
//				}
//				UserProfileVo userProfileVo = userInfoService.findUserProfile(noteOutVo.getUserId());
//				if(null != userProfileVo){
//					noteOutVo.setUserName(userProfileVo.getUserName());
//					noteOutVo.setAdviserType(userProfileVo.getAdviserType());
//					noteOutVo.setAvatar(userProfileVo.getAvatar());
//				}
//			}
		}
	}
	private void extendNote(List<NoteOutVo> pageList) {
		if(pageList!=null&&pageList.size()>0){
			StringBuffer userIds=new StringBuffer("");
			for (NoteOutVo noteOutVo : pageList) {
				userIds.append(","+noteOutVo.getUserId());
			}
			List<UserTagOutVo> userTags=noteMapper.getUserTag(userIds.substring(1),Constants.NOTE_USER_TAG);
			Map<Integer,List<UserTagOutVo>> userTagMap=transToMap(userTags);
			
			for (NoteOutVo noteOutVo : pageList) {
				noteOutVo.setTagList(userTagMap.get(noteOutVo.getUserId()));
			}
		}
	}

	private Map<Integer, List<UserTagOutVo>> transToMap(
			List<UserTagOutVo> userTags) {
		Map<Integer, List<UserTagOutVo>> userTagMap=new HashMap<Integer, List<UserTagOutVo>>();
		if(userTags!=null){
			for (UserTagOutVo userTagOutVo : userTags) {
				Integer userId = userTagOutVo.getUserId();
				List<UserTagOutVo> userIdMap = userTagMap.get(userId);
				if(userIdMap==null){
					userIdMap=new ArrayList<UserTagOutVo>();
					userTagMap.put(userId, userIdMap);
				}
				userIdMap.add(userTagOutVo);
			}
		}
		
		return userTagMap;
	}

	public NoteOutVo view(NoteInVo noteInVo) throws Exception {
		NoteOutVo noteOutVo=noteMapper.selectByPrimaryKeyExtend(noteInVo);
		if(noteOutVo!=null){
			Integer subscribed=userOrderService.getSubscribeStatus(noteInVo.getUserId(), noteOutVo.getType(), noteOutVo.getUserId(), noteOutVo.getId(), Constants.ORDER_TYPE_NOTE);
			noteOutVo.setSubscribed(subscribed);
			
			List<Tag> noteTagList = noteMapper.getNoteTag(noteOutVo.getId(), Constants.NOTE_TAG);
			noteOutVo.setNoteTagList(noteTagList);
			UserProfileVo userProfileVo = userInfoService.findUserProfile(noteOutVo.getUserId());
			if(null != userProfileVo){
				noteOutVo.setUserName(userProfileVo.getUserName());
				noteOutVo.setAdviserType(userProfileVo.getAdviserType());
				noteOutVo.setAvatar(userProfileVo.getAvatar());
			}
			
			noteMapper.increaseReadCount(noteInVo.getId(),1);
		}
		
		return noteOutVo;
	}

	public void comment(UserComment userComment) throws Exception {
		userComment.setTargetType(Constants.ORDER_TYPE_NOTE);
		userComment.setCreateTime(new Date());
		int count = userCommentService.insertSelective(userComment);
		
		if(count>0){
			Note note=selectByPrimaryKey(userComment.getTargetId());
			if(note!=null){
				note.setCommentCount(note.getCommentCount()+1);
				updateByPrimaryKeySelective(note);
    			
				//用户评论成功后，向笔记的创建者推送消息
    			List<Integer> users = new ArrayList<Integer>();
    			users.add(note.getUserId());
    			PushMessageUserOutVo pushMessageUserOutVo = userInfoService.findByUserId(userComment.getUserId());
    			pushMessageService.pushCommentNoteMessage(users, pushMessageUserOutVo.getUserId(), pushMessageUserOutVo.getUserName(), pushMessageUserOutVo.getAvatar(), note.getId(), note.getTitle());
			}
		}
	}

	public jqGridResponseVo<UserCommentOutVo> getCommentList(NoteInVo noteInVo) {
		PageHelper.startPage(noteInVo.getPageNum(), noteInVo.getPageSize());
		List<UserCommentOutVo> pageList = noteMapper.getCommentList(noteInVo.getId(),Constants.ORDER_TYPE_NOTE);
		
		PageInfo<UserCommentOutVo> pageInfo = new PageInfo(pageList);
		return new jqGridResponseVo(pageInfo.getPages(),pageInfo.getList(),noteInVo.getPageNum(),pageInfo.getTotal());
		
	}

	/**
	 * 给笔记点赞
	 * @param noteId
	 * @return
	 * @throws Exception 
	 */
	public String clickZan(Integer noteId,Integer userId) throws Exception {
		Note note = new Note();
		note = this.selectByPrimaryKey(noteId);
		if(null != note){
			if(note.getUserId() == userId){//如果点赞的是自己的笔记，不准许点赞
				return Constants.NOT_ALLOWED_ZAN;
			}
			Integer zanCount = this.userFavoriteService.selectZanCountsByUserId(noteId,Constants.ORDER_TYPE_NOTE,userId);//笔记ID,类型为笔记,用户ID
			if(zanCount > 0){
				return Constants.ALREADY_ZAN;
			}
			Integer favorites = note.getFavorites();
			Integer favoritesTmp = favorites + 1;
			note.setFavorites(favoritesTmp);
			Integer flag = this.updateByPrimaryKey(note);
			if(1 == flag){
				//点赞成功后，向笔记创建者推送消息
				List<Integer> users = new ArrayList<Integer>();
				users.add(note.getUserId());
				PushMessageUserOutVo pushMessageUserOutVo = userInfoService.findByUserId(userId);
				pushMessageService.pushFavouriteNoteMessage(users, pushMessageUserOutVo.getUserId(), pushMessageUserOutVo.getUserName(), pushMessageUserOutVo.getAvatar(), noteId, note.getTitle());

				return favoritesTmp.toString();//数据插入成功返回最新点赞数
			}else{
				//return favorites.toString();//数据插入失败返回原始点赞数
				return Constants.ZAN_FAIL;//数据插入失败
			}
		}else{
			return Constants.NOTE_NOT_EXIST;//通过笔记ID查询的笔记不存在
		}
	}

	/**
	 * 购买笔记
	 * @param userOrder
	 * @param note 
	 * @return 
	 * @throws Exception 
	 */
	public Integer buyhNote(UserOrder userOrder, Note note) throws Exception {
		//TODO 支付
		Date date = new Date();
		userOrder.setOrderType(Constants.ORDER_TYPE_NOTE);//1为笔记2为直播3为问答4为组合
		userOrder.setPaymentTime(date);//付费时间
		userOrder.setPaymentType(0);//付费类型
		userOrder.setCreateTime(date);//创建时间
		userOrder.setIaUserId(note.getUserId());
		Integer result =  this.userOrderService.insert(userOrder);
		if(result==1){
			//笔记购买成功后，向笔记发布者推送消息
			PushMessageUserOutVo userMessageInVo = userInfoService.findByUserId(userOrder.getUserId());
			PushMessageNoteOutVo pushMessageNoteOutVo = findByNoteId(userOrder.getOrderId());
			List<Integer> users = new ArrayList<Integer>();
			users.add(pushMessageNoteOutVo.getUserId());
			pushMessageService.pushSubScribeNoteMessage(users, userOrder.getUserId(), userMessageInVo.getUserName(), userMessageInVo.getAvatar(), userOrder.getOrderId(), pushMessageNoteOutVo.getNoteName());
		}
		return result;
	}

	/**
	 * 笔记购买成功后，笔记的销售数量增加1
	 * @param orderId
	 */
	public void saleCountsAddOne(Note note) {
		Integer saleCounts = note.getSaleCount();
		Integer saleCountsTmp = saleCounts + 1;
		note.setSaleCount(saleCountsTmp);
		this.updateByPrimaryKey(note);
	}

	/**
	 * 点赞成功后，向user_favorite表中插入数据
	 * @param noteId
	 * @param userId
	 */
	public void insertUserFavorite(Integer noteId, Integer userId) {
		UserFavorite UserFavorite = new UserFavorite();
		UserFavorite.setTargetId(noteId);
		UserFavorite.setTargetType(Constants.ORDER_TYPE_NOTE);//1为笔记2为直播3为问答
		UserFavorite.setUserId(userId);
		UserFavorite.setCreateTime(new Date());
		this.userFavoriteService.insert(UserFavorite);
	}
	public List<TagOutVo> findTag(Integer nodeId) {
		List<TagOutVo> tagOutVos = noteMapper.findTag(nodeId);
		return tagOutVos;
	}

	
	public PushMessageNoteOutVo findByNoteId(Integer noteId){
		return noteMapper.selectByNoteId(noteId);
	}
	
	public List<NoteOutVo> getRecommendNotes(){
		return noteMapper.getRecommendNotes(Constants.IS_RECOMMEND_Y);
	}
	
	public jqGridResponseVo<NoteOutVo> getNotesByTitle(String title,PageVo pageVo){
		PageHelper.startPage(pageVo.getPageNum(), pageVo.getPageSize());
		List<NoteOutVo> notes = noteMapper.getNotesByTitle(title);
		PageInfo<NoteOutVo> pageInfo = new PageInfo<NoteOutVo>(notes);
		return new jqGridResponseVo<NoteOutVo>(pageInfo.getPages(),pageInfo.getList(),pageVo.getPageNum(),pageInfo.getTotal());
	}

	public NoteOutVo viewFree(NoteInVo noteInVo) {
		NoteOutVo noteOutVo=noteMapper.selectByFreeNote(noteInVo);
		return noteOutVo;
	}
	
}