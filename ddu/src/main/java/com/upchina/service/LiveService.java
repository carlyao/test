package com.upchina.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.upchina.Exception.UpChinaError;
import com.upchina.dao.LiveMapper;
import com.upchina.dao.UserInfoMapper;
import com.upchina.model.Live;
import com.upchina.model.LiveContent;
import com.upchina.model.LiveMessage;
import com.upchina.model.UserFavorite;
import com.upchina.model.UserInfo;
import com.upchina.model.UserLive;
import com.upchina.util.Constants;
import com.upchina.vo.BaseOutVo;
import com.upchina.vo.PageVo;
import com.upchina.vo.jqGridResponseVo;
import com.upchina.vo.rest.input.DicTypeInVo;
import com.upchina.vo.rest.input.LiveInVo;
import com.upchina.vo.rest.input.LiveMessageInVo;
import com.upchina.vo.rest.output.LiveOutVo;
import com.upchina.vo.rest.output.UserProfileVo;

/**
 * Created by codesmith on 2015
 */

@Service("liveService")
public class LiveService extends BaseService<Live, Integer> {
	@Autowired
	private UserFavoriteService userFavoriteService;

	@Autowired
	private UserLiveService userLiveService;

	@Autowired
	private LiveMapper liveMapper;

	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private UserInfoMapper userInfoMapper;
	
    @Autowired
    private LiveContentService liveContentService;
    
    @Autowired
    private LiveMessageService liveMessageService;

	/**
	 * 判断字段是否存在（插入和修改时可用,修改本身时如判断字段未发生修改会忽略）
	 * 
	 * @param inputName
	 *            要判断的字段名
	 * @param value
	 *            要判断的值
	 * @param id
	 *            当前记录id
	 * @return 是否存在
	 */
	public boolean isExist(String inputName, String value, int id) {
		Example exp = new Example(Live.class);
		Example.Criteria criteria = exp.createCriteria();
		criteria.andEqualTo(inputName, value);
		List<Live> list = selectByExample(exp);
		if (list != null && list.size() > 0) {// 有同名的
			if (id == 0) {// 是添加的
				return true;
			} else {// 是修改的
				criteria.andNotEqualTo("id", id);
				list = selectByExample(exp);
				if (list.size() > 0)// 说明不是他本身
				{
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 创建直播
	 * 
	 * @param live
	 * @return
	 */
	public BaseOutVo createLive(Integer userId) {
		BaseOutVo res = new BaseOutVo();

		// 通过userId查询userName
		Example exampleUserInfo = new Example(UserInfo.class);
		exampleUserInfo.createCriteria().andEqualTo("userId", userId);
		UserInfo userInfo = this.userInfoService.selectByPrimaryKey(userId);

		Live live = new Live();
		live.setUserId(userId);
		if (null != userInfo.getUserName() && !"".equals(userInfo.getUserName())) {
			live.setTitle(userInfo.getUserName() + " 直播室");
		}
		live.setSummary("请修改直播主题。");
		live.setCreateTime(new Date());

		Integer returnFlag = this.insertSelective(live);// returnFlag=1表示数据插入成功
		if (1 == returnFlag) {
			res.setResultMsg("创建直播成功");
			res.setResultCode(UpChinaError.SUCCESS.code);
		} else {
			res.setResultMsg("创建直播失败");
			res.setResultCode(UpChinaError.ERROR.code);
		}
		return res;
	}

	public BaseOutVo join(LiveInVo live) {
		BaseOutVo res = new BaseOutVo();

		Example example = new Example(UserLive.class);
		example.createCriteria().andEqualTo("liveId", live.getLiveId()).andEqualTo("userId", live.getUserId());
		List<UserLive> userLiveList = userLiveService.selectByExample(example);
		if (userLiveList.size() == 0) {
			UserLive record = new UserLive();
			record.setLiveId(live.getLiveId());
			record.setUserId(live.getUserId());
			record.setCreateTime(new Date());
			record.setStatus(Constants.STATUS_JOIN);
			userLiveService.insert(record);
			liveMapper.increasePeopleNum(live.getLiveId());
		} else {
			UserLive record = userLiveList.get(0);
			Integer status = record.getStatus();
			if (status.equals(Constants.STATUS_QUIT)) {
				record.setUpdateTime(new Date());
				record.setStatus(Constants.STATUS_JOIN);
				userLiveService.updateByPrimaryKey(record);
				liveMapper.increasePeopleNum(live.getLiveId());
			}
		}

		res.setResultMsg("直播加入成功");
		res.setResultCode(UpChinaError.SUCCESS.code);

		return res;
	}

	public BaseOutVo quit(LiveInVo live) {
		BaseOutVo res = new BaseOutVo();

		Example example = new Example(UserLive.class);
		example.createCriteria().andEqualTo("liveId", live.getLiveId()).andEqualTo("userId", live.getUserId());
		List<UserLive> userLiveList = userLiveService.selectByExample(example);
		if (userLiveList.size() > 0) {
			UserLive record = userLiveList.get(0);
			Integer status = record.getStatus();
			if (status.equals(Constants.STATUS_JOIN)) {
				record.setUpdateTime(new Date());
				record.setStatus(Constants.STATUS_QUIT);
				userLiveService.updateByPrimaryKey(record);
//				liveMapper.decreasePeopleNum(live.getLiveId());
			}
		}

		res.setResultMsg("直播退出成功");
		res.setResultCode(UpChinaError.SUCCESS.code);

		return res;
	}

	/**
	 * 更新直播的标题和摘要
	 * 
	 * @param userId
	 * @param title
	 * @param summary
	 * @return
	 */
	public BaseOutVo updateLive(Integer id, String title, String summary) {
		BaseOutVo res = new BaseOutVo();
		Live live = new Live();
		live = this.liveMapper.selectById(id);

		if (null != title && !"".equals(title)) {
			live.setTitle(title);
		}
		if (null != summary && !"".equals(summary)) {
			live.setSummary(summary);
		}

		Integer returnFlag = this.liveMapper.updateLiveById(live.getId(), live.getTitle(), live.getSummary());
		if (1 == returnFlag) {
			if (null != title && !"".equals(title) && null != summary && !"".equals(summary)) {
				res.setResultMsg("修改直播标题和直播主题成功");
				res.setResultCode(UpChinaError.SUCCESS.code);
			}
			if (null != title && !"".equals(title) && (null == summary || "".equals(summary))) {
				res.setResultMsg("修改直播标题成功");
				res.setResultCode(UpChinaError.SUCCESS.code);
			}
			if (null != summary && !"".equals(summary) && (null == title || "".equals(title))) {
				res.setResultMsg("修改直播主题成功");
				res.setResultCode(UpChinaError.SUCCESS.code);
			}
		} else {
			if (null != title && !"".equals(title) && null != summary && !"".equals(summary)) {
				res.setResultMsg("修改直播标题和直播主题失败");
				res.setResultCode(UpChinaError.SUCCESS.code);
			}
			if (null != title && !"".equals(title) && (null == summary || "".equals(summary))) {
				res.setResultMsg("修改直播标题失败");
				res.setResultCode(UpChinaError.SUCCESS.code);
			}
			if (null != summary && !"".equals(summary) && (null == title || "".equals(title))) {
				res.setResultMsg("修改直播主题失败");
				res.setResultCode(UpChinaError.SUCCESS.code);
			}
		}
		return res;
	}

	/**
	 * 推荐直播列表
	 * 
	 * @param pageVo
	 * @return
	 * @throws Exception 
	 */
	public jqGridResponseVo<LiveOutVo> getRecommendLiveList(PageVo pageVo) throws Exception {
		int pageNum = pageVo.getPageNum();
		int pageSize = pageVo.getPageSize();
		if (0 == pageNum) {
			pageNum = 1;
		}
		if (0 == pageSize) {
			pageSize = 10;
		}
		PageHelper.startPage(pageNum, pageSize);

		List<LiveOutVo> lives = new ArrayList<LiveOutVo>();
		lives = this.liveMapper.recommendLiveList(Constants.IS_RECOMMEND_Y);
		for (LiveOutVo liveOutVo : lives) {
			Integer userId = liveOutVo.getUserId();
			UserProfileVo userProfileVo = userInfoService.findUserProfile(userId);
			if(null != userProfileVo){
				liveOutVo.setAdviserType(userProfileVo.getAdviserType());
				liveOutVo.setUserName(userProfileVo.getUserName());
				liveOutVo.setAvatar(userProfileVo.getAvatar());
			}
		}

		PageInfo<LiveOutVo> pageInfo = new PageInfo<LiveOutVo>(lives);
		return new jqGridResponseVo<LiveOutVo>(pageInfo.getPages(), pageInfo.getList(), pageNum, pageInfo.getTotal());
	}

	public BaseOutVo favorite(LiveInVo live) {
		BaseOutVo res = new BaseOutVo();

		Example example = new Example(UserFavorite.class);
		example.createCriteria().andEqualTo("targetId", live.getLiveId())
				.andEqualTo("targetType", Constants.ORDER_TYPE_lIVE).andEqualTo("userId", live.getUserId())
				.andGreaterThanOrEqualTo("createTime", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		List<UserFavorite> userFavoriteList = userFavoriteService.selectByExample(example);
		if (userFavoriteList.size() == 0) {
			UserFavorite record = new UserFavorite();
			record.setTargetId(live.getLiveId());
			record.setTargetType(Constants.ORDER_TYPE_lIVE);
			record.setUserId(live.getUserId());
			record.setCreateTime(new Date());
			record.setStatus(Constants.FAVORITE_YES);
			userFavoriteService.insert(record);
			liveMapper.increaseFavorites(live.getLiveId());
			res.setResultMsg("直播点赞成功");
			res.setResultCode(UpChinaError.SUCCESS.code);
		} else {
			res.setResultMsg(UpChinaError.LIVE_FAVORITE_ALREADY.message);
			res.setResultCode(UpChinaError.LIVE_FAVORITE_ALREADY.code);
			// UserFavorite record = userFavoriteList.get(0);
			// Integer status = record.getStatus();
			// if(status.equals(Constants.FAVORITE_NO)){
			// record.setUpdateTime(new Date());
			// record.setStatus(Constants.FAVORITE_YES);
			// liveMapper.increaseFavorites(live.getLiveId());
			// }
		}

		return res;
	}

	public jqGridResponseVo<LiveOutVo> getLatestList(LiveInVo liveInVo) throws Exception {
		PageHelper.startPage(liveInVo.getPageNum(), liveInVo.getPageSize());
		List<LiveOutVo> pageList = liveMapper.getLatestList(liveInVo);
		for (LiveOutVo liveOutVo : pageList) {
			Integer userId = liveOutVo.getUserId();
			UserProfileVo userProfileVo = userInfoService.findUserProfile(userId);
			if(null != userProfileVo){
				liveOutVo.setAdviserType(userProfileVo.getAdviserType());
				liveOutVo.setUserName(userProfileVo.getUserName());
				liveOutVo.setAvatar(userProfileVo.getAvatar());
			}
		}
		
		// extendLive(pageList);

		PageInfo<LiveOutVo> pageInfo = new PageInfo(pageList);
		return new jqGridResponseVo(pageInfo.getPages(), pageInfo.getList(), liveInVo.getPageNum(), pageInfo.getTotal());
	}

	public jqGridResponseVo<LiveOutVo> getHotestList(LiveInVo liveInVo) throws Exception {
		PageHelper.startPage(liveInVo.getPageNum(), liveInVo.getPageSize());
		List<LiveOutVo> pageList = liveMapper.getHotestList(liveInVo);
		for (LiveOutVo liveOutVo : pageList) {
			Integer userId = liveOutVo.getUserId();
			UserProfileVo userProfileVo = userInfoService.findUserProfile(userId);
			if(null != userProfileVo){
				liveOutVo.setAdviserType(userProfileVo.getAdviserType());
				liveOutVo.setUserName(userProfileVo.getUserName());
				liveOutVo.setAvatar(userProfileVo.getAvatar());
			}
		}
		PageInfo<LiveOutVo> pageInfo = new PageInfo(pageList);
		extendLive(pageInfo.getList(), pageInfo.getTotal());
		return new jqGridResponseVo(pageInfo.getPages(), pageInfo.getList(), liveInVo.getPageNum(), pageInfo.getTotal());
	}

	private void extendLive(List<LiveOutVo> pageList, Long total) {
		Double oneFifths = total / 5.0;
		Double twoFifths = total / 5.0 * 2;
		Double threeFifths = total / 5.0 * 3;
		Double fourFifths = total / 5.0 * 4;
		if (pageList != null && pageList.size() > 0) {
			for (LiveOutVo liveOutVo : pageList) {
				Integer rank = liveOutVo.getRank();
				if (rank < oneFifths) {
					liveOutVo.setHot(Constants.LIVE_HOT_FIVE);
				} else if (rank < twoFifths) {
					liveOutVo.setHot(Constants.LIVE_HOT_FOUR);
				} else if (rank < threeFifths) {
					liveOutVo.setHot(Constants.LIVE_HOT_THREE);
				} else if (rank < fourFifths) {
					liveOutVo.setHot(Constants.LIVE_HOT_TWO);
				} else {
					liveOutVo.setHot(Constants.LIVE_HOT_ONE);
				}
			}
		}
	}

	/**
	 * 精选直播列表(通过算法规则筛选)
	 * 
	 * @param pageVo
	 * @return
	 * @throws Exception 
	 */
	public jqGridResponseVo<LiveOutVo> getFeaturedLiveList(PageVo pageVo) throws Exception {
		int pageNum = pageVo.getPageNum();
		int pageSize = pageVo.getPageSize();
		if (0 == pageNum) {
			pageNum = 1;
		}
		if (0 == pageSize) {
			pageSize = 10;
		}
		PageHelper.startPage(pageNum, pageSize);
		List<LiveOutVo> featuredLives = new ArrayList<LiveOutVo>();
		featuredLives = this.liveMapper.featuredLiveList();
		for (LiveOutVo liveOutVo : featuredLives) {
			Integer userId = liveOutVo.getUserId();
			UserProfileVo userProfileVo = userInfoService.findUserProfile(userId);
			if(null != userProfileVo){
				liveOutVo.setAdviserType(userProfileVo.getAdviserType());
				liveOutVo.setUserName(userProfileVo.getUserName());
				liveOutVo.setAvatar(userProfileVo.getAvatar());
			}
		}
		PageInfo<LiveOutVo> pageInfo = new PageInfo<LiveOutVo>(featuredLives);
		return new jqGridResponseVo<LiveOutVo>(pageInfo.getPages(), pageInfo.getList(), pageNum, pageInfo.getTotal());
	}

	public BaseOutVo view(LiveInVo live) {
		BaseOutVo res = new BaseOutVo();

		Live liveInfo = selectByPrimaryKey(live.getLiveId());
		if (liveInfo == null) {
			res.setResultMsg("直播不存在");
			res.setResultCode(UpChinaError.ERROR.code);
		} else {
			LiveOutVo liveOutVo = new LiveOutVo();
			liveOutVo.setSummary(liveInfo.getSummary());
			liveOutVo.setTitle(liveInfo.getTitle());
			liveOutVo.setFavorites(liveInfo.getFavorites());
			liveOutVo.setUserId(liveInfo.getUserId());
			liveOutVo.setId(liveInfo.getId());

			if (live.getUserId() != null) {
				Example example = new Example(UserFavorite.class);
				example.createCriteria().andEqualTo("targetId", live.getLiveId())
						.andEqualTo("targetType", Constants.ORDER_TYPE_lIVE).andEqualTo("userId", live.getUserId())
						.andGreaterThanOrEqualTo("createTime", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				List<UserFavorite> userFavoriteList = userFavoriteService.selectByExample(example);
				if (userFavoriteList.size() == 0) {
					liveOutVo.setFavoriteStatus(Constants.FAVORITE_NO);
				} else {
					liveOutVo.setFavoriteStatus(Constants.FAVORITE_YES);
				}
			} else {
				liveOutVo.setFavoriteStatus(Constants.FAVORITE_NO);
			}
			res.setResultData(liveOutVo);
			res.setResultCode(UpChinaError.SUCCESS.code);
		}

		return res;
	}

	public void updateLiveComment(Live live, int count) {
		live.setCommentCount(live.getCommentCount() + count);
		updateByPrimaryKey(live);
	}

	public void updateLiveComment(Integer liveId, int count) {
		Live live = selectByPrimaryKey(liveId);
		updateLiveComment(live, count);
	}

	public LiveOutVo getUserLive(Integer userId) throws Exception {
		LiveOutVo liveOutVo = liveMapper.getUserLive(userId);
		if(null != liveOutVo){
			UserProfileVo userProfileVo = userInfoService.findUserProfile(userId);
			if(null != userProfileVo){
				liveOutVo.setAvatar(userProfileVo.getAvatar());
				liveOutVo.setUserName(userProfileVo.getUserName());
				liveOutVo.setAdviserType(userProfileVo.getAdviserType());
			}
		}
		return liveOutVo;
	}

	public jqGridResponseVo<LiveOutVo> getLivesOrderByContentCount(DicTypeInVo dicTypeInVo) {
		int pageNum = dicTypeInVo.getPageNum();
		int pageSize = dicTypeInVo.getPageSize();
		if (0 == pageNum) {
			pageNum = 1;
		}
		if (0 == pageSize) {
			pageSize = 10;
		}

		PageHelper.startPage(pageNum, pageSize);
		List<LiveOutVo> lives = liveMapper.getLivesOrderByContentCount();
		PageInfo<LiveOutVo> pageInfo = new PageInfo<LiveOutVo>(lives);
		return new jqGridResponseVo<LiveOutVo>(pageInfo.getPages(), pageInfo.getList(), pageNum, pageInfo.getTotal());
	}

	public jqGridResponseVo<LiveOutVo> getLivesOrderByCommentCount(DicTypeInVo dicTypeInVo) {
		int pageNum = dicTypeInVo.getPageNum();
		int pageSize = dicTypeInVo.getPageSize();
		if (0 == pageNum) {
			pageNum = 1;
		}
		if (0 == pageSize) {
			pageSize = 10;
		}

		PageHelper.startPage(pageNum, pageSize);
		List<LiveOutVo> lives = liveMapper.getLivesOrderByCommentCount();
		PageInfo<LiveOutVo> pageInfo = new PageInfo<LiveOutVo>(lives);
		return new jqGridResponseVo<LiveOutVo>(pageInfo.getPages(), pageInfo.getList(), pageNum, pageInfo.getTotal());
	}

	public jqGridResponseVo<LiveOutVo> getLivesOrderByFavourites(DicTypeInVo dicTypeInVo) {
		int pageNum = dicTypeInVo.getPageNum();
		int pageSize = dicTypeInVo.getPageSize();
		if (0 == pageNum) {
			pageNum = 1;
		}
		if (0 == pageSize) {
			pageSize = 10;
		}

		PageHelper.startPage(pageNum, pageSize);
		List<LiveOutVo> lives = liveMapper.getLivesOrderByFavourites();
		PageInfo<LiveOutVo> pageInfo = new PageInfo<LiveOutVo>(lives);
		return new jqGridResponseVo<LiveOutVo>(pageInfo.getPages(), pageInfo.getList(), pageNum, pageInfo.getTotal());
	}
	
	public jqGridResponseVo<LiveOutVo> getLivesByTitle(String title,PageVo pageVo){
		PageHelper.startPage(pageVo.getPageNum(), pageVo.getPageSize());
		List<LiveOutVo> lives = liveMapper.getLivesByTitle(title);
		PageInfo<LiveOutVo> pageInfo = new PageInfo<LiveOutVo>(lives);
		return new jqGridResponseVo<LiveOutVo>(pageInfo.getPages(),pageInfo.getList(),pageVo.getPageNum(),pageInfo.getTotal());
	}

	public BaseOutVo pushContent(LiveMessageInVo liveMessageInVo) throws Exception {
		BaseOutVo res = new BaseOutVo();
		Integer userId = liveMessageInVo.getUserId();
		UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
		if(null == userInfo){
			res.setResultCode(UpChinaError.USER_NOT_EXIST_ERROR.code);
			res.setResultMsg(UpChinaError.USER_NOT_EXIST_ERROR.message);
			return res;
		}
		
		Integer liveId = liveMessageInVo.getLiveId();
		Live live = mapper.selectByPrimaryKey(liveId);
		if(null == live){
			res.setResultCode(UpChinaError.LIVE_NOT_EXIST_ERROR.code);
			res.setResultMsg(UpChinaError.LIVE_NOT_EXIST_ERROR.message);
			return res;
		}
		int type = userInfo.getType();
		if(Constants.USER_TYPE_INVESTMENT != type){
			res.setResultCode(UpChinaError.LIVE_PUSH_CONTENT_ERROR.code);
			res.setResultMsg(UpChinaError.LIVE_PUSH_CONTENT_ERROR.message);
			return res;
		}
		String content = liveMessageInVo.getContent();
		List<String> imgs = liveMessageInVo.getImgs();
		String images = "";
		if (null != imgs && imgs.size() > 0) {
			for (int i = 0; i < imgs.size(); i++) {
				images += imgs.get(i);
				if (i < imgs.size() - 1) {
					images += ",";
				}
			}
		}
		LiveContent liveContent = new LiveContent();
		liveContent.setUserId(userId);
		liveContent.setLiveId(liveId);
		liveContent.setContent(content);
		liveContent.setImgs(images);
		if (!StringUtils.isEmpty(images)) {
			liveContent.setThumbnails(images.replaceAll("\\.", "-thumbnail."));
		}
		liveContent.setCreateTime(new Date());
		liveContentService.insert(liveContent);
		liveContentService.pushContent(liveContent);
		res.setResultCode(UpChinaError.SUCCESS.code);
		res.setResultMsg(UpChinaError.SUCCESS.message);
		return res;
	}

	public BaseOutVo pushMessage(LiveMessageInVo liveMessageInVo) throws Exception {
		BaseOutVo res = new BaseOutVo();
		Integer userId = liveMessageInVo.getUserId();
		UserInfo userInfo = userInfoService.selectByPrimaryKey(userId);
		if(null == userInfo){
			res.setResultCode(UpChinaError.USER_NOT_EXIST_ERROR.code);
			res.setResultMsg(UpChinaError.USER_NOT_EXIST_ERROR.message);
			return res;
		}
		
		Integer liveId = liveMessageInVo.getLiveId();
		Live live = selectByPrimaryKey(liveId);
		if(null == live){
			res.setResultCode(UpChinaError.LIVE_NOT_EXIST_ERROR.code);
			res.setResultMsg(UpChinaError.LIVE_NOT_EXIST_ERROR.message);
			return res;
		}
		int type = userInfo.getType();
		String content = liveMessageInVo.getContent();
		LiveMessage liveMessage = new LiveMessage();
		Integer replyMessageId = liveMessageInVo.getReplyMessageId();
		if(replyMessageId != null && 0 != replyMessageId){
			liveMessage.setParentId(replyMessageId);
		}
		liveMessage.setUserId(userId);
		liveMessage.setLiveId(liveId);
		liveMessage.setContent(content);
		liveMessage.setCreateTime(new Date());
		liveMessageService.insert(liveMessage);
		liveMessageService.pushMessage(liveMessage);
		updateLiveComment(liveId, 1);
		res.setResultCode(UpChinaError.SUCCESS.code);
		res.setResultMsg(UpChinaError.SUCCESS.message);
		return res;
	}

}