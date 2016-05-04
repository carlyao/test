package com.upchina.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.upchina.Exception.UpChinaError;
import com.upchina.dao.UserFriendMapper;
import com.upchina.dao.UserGroupMapper;
import com.upchina.model.NiuGroup;
import com.upchina.model.UserFriend;
import com.upchina.model.UserInfo;
import com.upchina.util.Constants;
import com.upchina.util.ImagePathUtil;
import com.upchina.vo.BaseOutVo;
import com.upchina.vo.PageVo;
import com.upchina.vo.jqGridResponseVo;
import com.upchina.vo.rest.input.UserFriendVo;
import com.upchina.vo.rest.output.FriendDetailVo;
import com.upchina.vo.rest.output.PushMessageUserOutVo;
import com.upchina.vo.rest.output.UserFriendAddOutVo;
import com.upchina.vo.rest.output.UserProfileVo;
/**
 * Created by codesmith on 2015
 */

@Service("userFriendService")
public class UserFriendService  extends BaseService<UserFriend, Integer>{
	
	@Autowired
	private UserFriendMapper userFriendMapper;
	
	@Autowired
    private UserGroupMapper userGroupMapper;
	
	@Autowired
    private UserInfoService userInfoService;
    
    @Autowired
    private PushMessageService pushMessageService;
    
     /**
     * 判断字段是否存在（插入和修改时可用,修改本身时如判断字段未发生修改会忽略）
     * @param inputName 要判断的字段名
     * @param value 要判断的值
     * @param id 当前记录id
     * @return 是否存在
     */
    public boolean isExist(String inputName,String value,int id) {
        Example exp = new Example(UserFriend.class);
        Example.Criteria criteria = exp.createCriteria();
        criteria.andEqualTo(inputName, value);
        List<UserFriend> list=selectByExample(exp);
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
	 * @param pageVo
	 * @param userFriend
	 * @return
	 */
	public jqGridResponseVo<UserInfo> getListFriendRequest(PageVo pageVo,UserFriend userFriend) {
		int pageNum = pageVo.getPageNum();
		int pageSize = pageVo.getPageSize();
		if(0 == pageNum){
			pageNum = 1;
		}
		if(0 == pageSize){
			pageSize = 10;
		}
		PageHelper.startPage(pageNum, pageSize);
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		userInfos = this.userFriendMapper.getListFriendRequest(userFriend.getFriendId(),Constants.REQUEST_FRIEND);
		
		PageInfo<UserInfo> pageInfo = new PageInfo<UserInfo>(userInfos);
		return new jqGridResponseVo<UserInfo>(pageInfo.getPages(),pageInfo.getList(),pageNum,pageInfo.getTotal());
	}

	/**
	 * 我的投顾好友
	 * @param pageVo
	 * @param userFriend
	 * @return
	 * @throws Exception 
	 */
	public jqGridResponseVo<UserInfo> getListFriend(PageVo pageVo,
			Integer userId) throws Exception {
		int pageNum = pageVo.getPageNum();
		int pageSize = pageVo.getPageSize();
		if(0 == pageNum){
			pageNum = 1;
		}
		if(0 == pageSize){
			pageSize = 10;
		}
		PageHelper.startPage(pageNum, pageSize);
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		userInfos = this.userFriendMapper.getListFriend(userId,Constants.IS_FRIEND,Constants.USER_TYPE_INVESTMENT);
		for (UserInfo userInfo : userInfos) {
			int iaUserId = userInfo.getUserId();
			UserProfileVo userProfileVo = userInfoService.findUserProfile(iaUserId);
			if(null != userProfileVo){
				userInfo.setUserName(userProfileVo.getUserName());
				userInfo.setAdviserType(userProfileVo.getAdviserType());
				userInfo.setAvatar(userProfileVo.getAvatar());
			}
		}
		PageInfo<UserInfo> pageInfo = new PageInfo<UserInfo>(userInfos);
		return new jqGridResponseVo<UserInfo>(pageInfo.getPages(),pageInfo.getList(),pageNum,pageInfo.getTotal());
	}
	/**
	 * 我的所有好友
	 * @param pageVo
	 * @param userFriend
	 * @return
	 * @throws Exception 
	 */
	public List<UserInfo> getListAllFriend(Integer userId) throws Exception {
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		userInfos = this.userFriendMapper.getListAllFriend(userId,Constants.IS_FRIEND);
		for (UserInfo userInfo : userInfos) {
			Integer iaUserId = userInfo.getUserId();
			UserProfileVo userProfileVo = userInfoService.findUserProfile(iaUserId);
			if(null != userProfileVo){
				userInfo.setAdviserType(userProfileVo.getAdviserType());
				userInfo.setUserName(userProfileVo.getUserName());
				userInfo.setAvatar(userProfileVo.getAvatar());
			}
		}
		return userInfos;
	}
	
	/**
	 * 我的粉丝
	 * @param userId
	 * @return
	 */
	public List<UserInfo> getMyFans(Integer userId) {
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		userInfos = this.userFriendMapper.getMyFans(userId,Constants.IS_FRIEND,Constants.USER_TYPE_USER);
		return userInfos;
	}
	
	
	/**
	 * 根据名称搜索非好友的投顾
	 * @param pageVo
	 * @param userFriend
	 * @param userInfo
	 * @return
	 * @throws Exception 
	 */
	public jqGridResponseVo<UserInfo> getListUserByUserName(PageVo pageVo,
			UserFriend userFriend,UserInfo userInfo) throws Exception {
		int pageNum = pageVo.getPageNum();
		int pageSize = pageVo.getPageSize();
		
		if(0 == pageNum){
			pageNum = 1;
		}
		if(0 == pageSize){
			pageSize = 10;
		}
		PageHelper.startPage(pageNum, pageSize);
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		userInfos = this.userFriendMapper.getListUserByUserName(userFriend.getUserId(),userInfo.getUserName(),Constants.IS_FRIEND,Constants.USER_TYPE_INVESTMENT);
		for (UserInfo userInfo2 : userInfos) {
			Integer iaUserId = userInfo2.getUserId();
			UserProfileVo userProfileVo = userInfoService.findUserProfile(iaUserId);
			if(null != userProfileVo){
				userInfo2.setUserName(userProfileVo.getUserName());
				userInfo2.setAdviserType(userProfileVo.getAdviserType());
				userInfo2.setAvatar(userProfileVo.getAvatar());
			}
		}
		
		PageInfo<UserInfo> pageInfo = new PageInfo<UserInfo>(userInfos);
		return new jqGridResponseVo<UserInfo>(pageInfo.getPages(),pageInfo.getList(),pageNum,pageInfo.getTotal());
		
	}

	/** 判断两个用户是否为好友,不是好友返回的值等于0，是好友返回的值大于0
	 * @param userFriend
	 * @return
	 */
	public Integer isFriendOrNot(Integer userId,Integer FriendId) {
		Integer Counts = this.userFriendMapper.isFriendOrNot(userId,FriendId,Constants.IS_FRIEND);
		return Counts ;
	}

	/**
	 * 好友资料
	 * @param userFriend
	 * @return
	 */
	public FriendDetailVo getFriendDetail(UserFriend userFriend) {
		
		FriendDetailVo friendDetailVo = new FriendDetailVo();
		
		UserInfo userInfo = new UserInfo();
		List<NiuGroup> groups = new ArrayList<NiuGroup>();
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		
		userInfo = this.userInfoService.selectByPrimaryKey(userFriend.getFriendId());//查询好友基本信息
		groups = this.userGroupMapper.selectUserGroup(userFriend.getFriendId(),Constants.STATUS_GROUP_ON,Constants.STATUS_JOIN);//好友所在的圈子
		for (NiuGroup niuGroup : groups) {
			niuGroup.setImg(ImagePathUtil.getImgHost()+niuGroup.getImg());
		}
		userInfos = this.userFriendMapper.getListAllFriend(userFriend.getFriendId(),Constants.IS_FRIEND);//好友的所有好友
		
		//判断用户是否为好友关系
		Example example=new Example(UserFriend.class);
		example.createCriteria().andEqualTo("userId", userFriend.getUserId()).andEqualTo("friendId", userFriend.getFriendId()).andEqualTo("status", Constants.RELATION_FRIEND);
		example.or().andEqualTo("userId",userFriend.getFriendId() ).andEqualTo("friendId",userFriend.getUserId() ).andEqualTo("status", Constants.RELATION_FRIEND);
		List<UserFriend>  friendship = selectByExample(example);
		if(null != friendship && friendship.size() > 0){
			friendDetailVo.setRelation(Constants.RELATION_FRIEND);
		}else{
			friendDetailVo.setRelation(Constants.RELATION_NONE);
		}
		
		friendDetailVo.setUserInfo(userInfo);
		friendDetailVo.setGroup(groups);
		friendDetailVo.setFriendInfo(userInfos);
		
		return friendDetailVo;
	}

	
	public Integer getFriendStatus(Integer userId, Integer currUserId){
		if(userId==null||currUserId==null){
			return Constants.RELATION_NONE;
		}else{
			Example example = new Example(UserFriend.class);
			example.createCriteria().andEqualTo("userId", userId).andEqualTo("friendId", currUserId)
			.andEqualTo("status", Constants.RELATION_FRIEND);
			example.or().andEqualTo("userId", currUserId).andEqualTo("friendId", userId)
			.andEqualTo("status", Constants.RELATION_FRIEND);
			List<UserFriend> friendship = selectByExample(example);
			if (friendship != null && friendship.size() > 0) {
				return Constants.RELATION_FRIEND;
			} else {
				return Constants.RELATION_NONE;
			}
		}
	}

	/**
	 * 删除好友
	 * @param userId
	 * @param friendId
	 * @return
	 */
	public int deleteFriend(Integer userId, Integer friendId) {
		Integer delOrNot = this.userFriendMapper.deleteFriend(userId,friendId);
		return delOrNot;
	}
	
	public jqGridResponseVo<UserInfo> getUsersByName(String userName,PageVo pageVo){
		PageHelper.startPage(pageVo.getPageNum(), pageVo.getPageSize());
		List<UserInfo> userInfos = userFriendMapper.getUsersByName(userName, Constants.USER_TYPE_INVESTMENT);
		PageInfo<UserInfo> pageInfo = new PageInfo<UserInfo>(userInfos);
		return new jqGridResponseVo<UserInfo>(pageInfo.getPages(),pageInfo.getList(),pageVo.getPageNum(),pageInfo.getTotal());
	}

	public BaseOutVo addFriends(UserFriendVo userFriendVo) {
		BaseOutVo res=new BaseOutVo();
        Integer userType = Constants.USER_TYPE_INVESTMENT;
        try {
            if(userFriendVo.getUserId() != null && userFriendVo.getFriendId().size() > 0){
            	Integer userId = userFriendVo.getUserId();
            	UserInfo user = userInfoService.selectByPrimaryKey(userId);
            	if(userType == user.getType()){
            		//res.setResultMsg("当前用户是投顾，不能添加好友");
            		res.setResultMsg(UpChinaError.PORTFOLIO_USER.message);
            		res.setResultCode(UpChinaError.PORTFOLIO_USER.code);
            		return res;
            	}
            	
            	Example example = new Example(UserFriend.class);
            	example.createCriteria().andEqualTo("userId", userId);
            	int count = selectCountByExample(example);
            	int maxCreateGroupNum = user.getMaxSubscribeAdviserNum();
            	if(count >= maxCreateGroupNum){
            		res.setResultMsg(UpChinaError.USER_SUBSCRIBULE_ADVISER_MAX_NUM_ERROR.message);
            		res.setResultCode(UpChinaError.USER_SUBSCRIBULE_ADVISER_MAX_NUM_ERROR.code);
            		return res;
            	}
            	List<Integer> friends = userFriendVo.getFriendId();
            	
            	//判断userId是否为投顾，如果为投顾，则返回提示信息:userId是投顾，不能添加用户为好友
            	
            	for(Integer friendId:friends){
            		UserFriend userFriend = new UserFriend();
            		userFriend.setCreateTime(new Date());
            		userFriend.setStatus(Constants.RELATION_FRIEND);//2:好友（请求成功后，即与投顾成为好友）
            		userFriend.setUserId(userFriendVo.getUserId());
            		userFriend.setFriendId(friendId);
            		
            		//判断即将添加的好友是否为投顾，如果不是投顾，返回提示信息，不能添加普通用户为好友
            		UserInfo userInfoTmp = this.userInfoService.selectByPrimaryKey(friendId);
            		if(userType != userInfoTmp.getType()){//添加为好友的用户不是投顾，不能添加
            			//res.setResultMsg("即将添加为好友的用户不是投顾，不能添加为好友！");
            			res.setResultMsg(UpChinaError.ADD_USER_NOTE_PORTFOLIO.message);
            			res.setResultCode(UpChinaError.ADD_USER_NOTE_PORTFOLIO.code);
            			return res;
            		}
            		
            		Integer alreadyFriend = isFriendOrNot(userFriend.getUserId(),userFriend.getFriendId());//查询是否是好友
            		if(alreadyFriend > 0){//已经是好友，不能重复添加
            			//res.setResultMsg("即将添加为好友的投顾已经是好友，不能重复添加！");
            			res.setResultMsg(UpChinaError.PORTFOLIO_IS_FRIEND.message);
            			res.setResultCode(UpChinaError.PORTFOLIO_IS_FRIEND.code);
            			return res;
            		}
            		if(alreadyFriend == 0){//不是好友，添加为好友
            			Integer result = userFriendMapper.insert(userFriend);//添加成功，返回1，否则返回0
            			if(1 == result){
	            			//添加好友成功后，将投顾的好友数+1
	            			UserInfo userInfo = this.userInfoService.selectByPrimaryKey(friendId);
	            			if(null != userInfo){
		            			Integer friendCount = userInfo.getFriendCount() + 1;
		            			userInfo.setUserId(friendId);
		            			userInfo.setFriendCount(friendCount);
		            			this.userInfoService.updateByPrimaryKey(userInfo);
	            			}
            			}
            		}
            	}
            	
            	//添加好友成功后，将好友的userId和userName返回
            	List<UserFriendAddOutVo> list = new ArrayList<UserFriendAddOutVo>();
            	for(Integer friendId:friends){
            		UserFriendAddOutVo userFriendAddOutVo = new UserFriendAddOutVo();
            		userFriendAddOutVo = this.userInfoService.selectByUserId(friendId);
            		list.add(userFriendAddOutVo);
            	}
                //res.setResultMsg("用户请求添加投顾为好友成功,返回添加的好友ID和名称");
                res.setResultMsg(UpChinaError.SUCCESS.message);
            	res.setResultCode(UpChinaError.SUCCESS.code);
                res.setResultData(list);
                
                PushMessageUserOutVo pushMessageUserOutVo = userInfoService.findByUserId(user.getUserId());
                pushMessageService.pushAddFriendMessage(friends, user.getUserId(), pushMessageUserOutVo.getUserName(), user.getAvatar());
            }else{
            	//res.setResultMsg("userId或者friendId为空，添加好友失败！");
                res.setResultMsg(UpChinaError.USERID_OR_GRIENDID_IS＿NULL.message);
            	res.setResultCode(UpChinaError.USERID_OR_GRIENDID_IS＿NULL.code);
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setResultCode(UpChinaError.ERROR.code);
            res.setResultMsg(UpChinaError.ERROR.message);
        }
        return res;
	}
	
	public BaseOutVo deleteFriends(UserFriendVo userFriendVo) {
		BaseOutVo res=new BaseOutVo();
        try {
        	Integer userId = userFriendVo.getUserId();
        	List<Integer> friendIds = userFriendVo.getFriendId();
        	if(null == userId){
        		res.setResultCode(UpChinaError.USERID_NULL_ERROR.code);
	            res.setResultMsg(UpChinaError.USERID_NULL_ERROR.message);
	            return res;
        	}
        	if(friendIds.size() <= 0){
        		res.setResultCode(UpChinaError.FRIENDID_NULL_ERROR.code);
	            res.setResultMsg(UpChinaError.FRIENDID_NULL_ERROR.message);
	            return res;
        	}
        	if(null != userId || friendIds.size() > 0){
        		for(Integer friendId:friendIds){
        			//判断userId与friendId是否为好友
        			Integer alreadyFriend = isFriendOrNot(userId,friendId);//判断用户是否为好友
        			if(alreadyFriend > 0){
        				int result = deleteFriend(userId,friendId);//数据库中存在符合条件被删除的记录，删除成功后，返回结果1；数据库中不存在符合条件的记录，删除失败后，返回结果0
        				if(1 == result){
        					//删除好友成功后，将投顾的好友数-1
        					UserInfo userInfo = this.userInfoService.selectByPrimaryKey(friendId);
        					if(userInfo.getFriendCount() > 0){//如果投顾的好友数大于零，则好友数减一，否则好友数不发生变化
        						Integer friendCount = userInfo.getFriendCount() - 1;
        						userInfo.setUserId(friendId);
        						userInfo.setFriendCount(friendCount);
        						this.userInfoService.updateByPrimaryKey(userInfo);
        					}
        				}
        			}
        			if(alreadyFriend == 0){
        				res.setResultCode(UpChinaError.ERROR.code);
        	            //res.setResultMsg("userId与friendId不是好友");
        				res.setResultMsg(UpChinaError.ERROR.message);
        	            return res;
        			}
        		}
	            res.setResultCode(UpChinaError.SUCCESS.code);
	            res.setResultMsg(UpChinaError.SUCCESS.message);
	            //res.setResultMsg("删除好友成功!");
	            
	            PushMessageUserOutVo userMessageOutVo = userInfoService.findByUserId(userId);
	            //删除好友成功后，向好友推送信息
	            pushMessageService.pushRemoveFriendMessage(friendIds, userId, userMessageOutVo.getUserName(), userMessageOutVo.getAvatar());
        	}
        } catch (Exception e) {
            e.printStackTrace();
            res.setResultCode(UpChinaError.ERROR.code);
            res.setResultMsg(UpChinaError.ERROR.message);
        }
        return res;
	}
	
}