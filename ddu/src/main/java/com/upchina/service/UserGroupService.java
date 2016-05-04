package com.upchina.service;

import io.rong.ApiHttpClient;
import io.rong.models.FormatType;
import io.rong.models.SdkHttpResult;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.upchina.Exception.BusinessException;
import com.upchina.Exception.UpChinaError;
import com.upchina.dao.UserGroupMapper;
import com.upchina.dao.UserNoSayingTimeMapper;
import com.upchina.model.NiuGroup;
import com.upchina.model.UserGroup;
import com.upchina.model.UserInfo;
import com.upchina.model.UserNoSayingTime;
import com.upchina.util.Constants;
import com.upchina.util.ImagePathUtil;
import com.upchina.vo.BaseOutVo;
import com.upchina.vo.jqGridResponseVo;
import com.upchina.vo.rest.input.UserGroupExtVo;
import com.upchina.vo.rest.input.UserGroupExtendVo;
import com.upchina.vo.rest.input.UserGroupVo;
import com.upchina.vo.rest.output.PushMessageUserOutVo;
import com.upchina.vo.rest.output.UserInfoVo;

/**
 * Created by codesmith on 2015
 */

@Service("userGroupService")
public class UserGroupService extends BaseService<UserGroup, Integer> {


    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserGroupMapper userGroupMapper;

    @Autowired
    private NiuGroupService niuGroupService;

    @Autowired
    private UserFriendService userFriendService;

    @Autowired
    private PushMessageService pushMessageService;


    @Autowired
    private UserNoSayingTimeMapper userNoSayingTimeMapper;

    /**
     * 判断字段是否存在（插入和修改时可用,修改本身时如判断字段未发生修改会忽略）
     *
     * @param inputName 要判断的字段名
     * @param value     要判断的值
     * @param id        当前记录id
     * @return 是否存在
     */
    public boolean isExist(String inputName, String value, int id) {
        Example exp = new Example(UserGroup.class);
        Example.Criteria criteria = exp.createCriteria();
        criteria.andEqualTo(inputName, value);
        List<UserGroup> list = selectByExample(exp);
        if (list != null && list.size() > 0) {//有同名的
            if (id == 0) {//是添加的
                return true;
            } else {//是修改的
                criteria.andNotEqualTo("id", id);
                list = selectByExample(exp);
                if (list.size() > 0)//说明不是他本身
                {
                    return true;
                }
            }
        }
        return false;
    }

    public BaseOutVo joinMulti(UserGroupExtendVo userGroupVo) throws Exception {
        BaseOutVo baseOutVo = new BaseOutVo();
        String userId = userGroupVo.getUserId();
        List<String> groupIds = userGroupVo.getGroupId();
        List<String> groupNames = userGroupVo.getGroupName();
        for (int i = 0; i < groupIds.size(); i++) {
            String groupId = groupIds.get(i);
            String groupName = groupNames.get(i);
            SdkHttpResult res = ApiHttpClient.joinGroup(ImagePathUtil.getKey(), ImagePathUtil.getSecret(), userId, groupId, groupName, FormatType.json);
            if (200 != res.getHttpCode()) {
                throw new BusinessException(UpChinaError.RONGYUN_ERROR);
            }

            Example example = new Example(UserGroup.class);
            example.createCriteria().andEqualTo("userId", userId).andEqualTo("groupId", groupId);
            List<UserGroup> userGroupList = selectByExample(example);
            if (userGroupList != null && userGroupList.size() > 0) {
                UserGroup userGroupOld = userGroupList.get(0);
                if (userGroupOld.getStatus().equals(Constants.STATUS_QUIT)) {
                    userGroupOld.setUpdateTime(new Date());
                    userGroupOld.setStatus(Constants.STATUS_JOIN);
                    updateByPrimaryKey(userGroupOld);
                    niuGroupService.updateUserCount(Integer.parseInt(groupId), 1);
                    //加入牛圈成功后，向投顾推送消息
                    PushMessageUserOutVo userMessageInVo = userInfoService.findByUserId(Integer.parseInt(userId));
                    NiuGroup niuGroup = niuGroupService.selectByPrimaryKey(Integer.parseInt(groupId));
                    List<String> users = new ArrayList<String>();
                    users.add(String.valueOf(niuGroup.getUserId()));
                    pushMessageService.pushJoinGroupMessage(users, userMessageInVo.getUserId(), userMessageInVo.getUserName(), userMessageInVo.getAvatar(), Integer.parseInt(groupId), niuGroup.getName());
                }
            } else {
                UserGroup userGroup = new UserGroup();
                userGroup.setGroupId(Integer.parseInt(groupId));
                userGroup.setUserId(Integer.parseInt(userId));
                userGroup.setType(Constants.TYPE_MEMBER);
                userGroup.setCreateTime(new Date());
                userGroup.setStatus(Constants.STATUS_JOIN);
                insert(userGroup);
                niuGroupService.updateUserCount(Integer.parseInt(groupId), 1);

                //加入牛圈成功后，向投顾推送消息
                PushMessageUserOutVo userMessageInVo = userInfoService.findByUserId(Integer.parseInt(userId));
                NiuGroup niuGroup = niuGroupService.selectByPrimaryKey(Integer.parseInt(groupId));
                List<String> users = new ArrayList<String>();
                users.add(String.valueOf(niuGroup.getUserId()));
                pushMessageService.pushJoinGroupMessage(users, userMessageInVo.getUserId(), userMessageInVo.getUserName(), userMessageInVo.getAvatar(), Integer.parseInt(groupId), niuGroup.getName());
            }
        }
        baseOutVo.setResultCode(UpChinaError.SUCCESS.code);
        baseOutVo.setResultMsg(UpChinaError.SUCCESS.message);
        return baseOutVo;
    }

    public BaseOutVo join(UserGroupExtVo userGroupVo) throws Exception {
        BaseOutVo baseOutVo = new BaseOutVo();
        //TODO 先调融云，再入数据库
        SdkHttpResult res = ApiHttpClient.joinGroupBatch(ImagePathUtil.getKey(), ImagePathUtil.getSecret(), userGroupVo.getUserId(), userGroupVo.getGroupId().toString(), userGroupVo.getGroupName(), FormatType.json);
        if (200 != res.getHttpCode()) {
            throw new BusinessException(UpChinaError.RONGYUN_ERROR);
        }

        List<String> userIds = userGroupVo.getUserId();
        Integer groupId = userGroupVo.getGroupId();
        for (String userId : userIds) {

            Example example = new Example(UserGroup.class);
            example.createCriteria().andEqualTo("userId", userId).andEqualTo("groupId", userGroupVo.getGroupId());
            List<UserGroup> userGroupList = selectByExample(example);
            if (userGroupList != null && userGroupList.size() > 0) {
                UserGroup userGroupOld = userGroupList.get(0);
                if (userGroupOld.getStatus().equals(Constants.STATUS_QUIT)) {
                    userGroupOld.setUpdateTime(new Date());
                    userGroupOld.setStatus(Constants.STATUS_JOIN);
                    updateByPrimaryKey(userGroupOld);
                    niuGroupService.updateUserCount(userGroupVo.getGroupId(), 1);

                    //加入牛圈成功后，向投顾推送消息
                    PushMessageUserOutVo userMessageInVo = userInfoService.findByUserId(Integer.parseInt(userId));
                    NiuGroup niuGroup = niuGroupService.selectByPrimaryKey(groupId);
                    List<String> users = new ArrayList<String>();
                    users.add(String.valueOf(niuGroup.getUserId()));
                    pushMessageService.pushJoinGroupMessage(users, userMessageInVo.getUserId(), userMessageInVo.getUserName(), userMessageInVo.getAvatar(), groupId, niuGroup.getName());
                }
            } else {
                UserGroup userGroup = new UserGroup();
                userGroup.setGroupId(groupId);
                userGroup.setUserId(Integer.parseInt(userId));
                userGroup.setType(Constants.TYPE_MEMBER);
                userGroup.setCreateTime(new Date());
                userGroup.setStatus(Constants.STATUS_JOIN);
                insert(userGroup);
                niuGroupService.updateUserCount(userGroupVo.getGroupId(), 1);
                //加入牛圈成功后，向投顾推送消息
                PushMessageUserOutVo userMessageInVo = userInfoService.findByUserId(Integer.parseInt(userId));
                NiuGroup niuGroup = niuGroupService.selectByPrimaryKey(groupId);
                List<String> users = new ArrayList<String>();
                users.add(String.valueOf(niuGroup.getUserId()));
                pushMessageService.pushJoinGroupMessage(users, userMessageInVo.getUserId(), userMessageInVo.getUserName(), userMessageInVo.getAvatar(), groupId, niuGroup.getName());

            }
        }
        if(null != userGroupVo.getInviterId()){
			PushMessageUserOutVo pushMessageUserOutVo = userInfoService.findByUserId(userGroupVo.getInviterId());
			pushMessageService.pushInviteGroupMessage(userGroupVo.getUserId(), userGroupVo.getInviterId(), pushMessageUserOutVo.getUserName(), pushMessageUserOutVo.getAvatar(), userGroupVo.getGroupId(), userGroupVo.getGroupName());
		}
        baseOutVo.setResultCode(UpChinaError.SUCCESS.code);
        baseOutVo.setResultMsg(UpChinaError.SUCCESS.message);
        return baseOutVo;
    }

    @Transactional
    public void quit(UserGroupVo userGroupVo) throws Exception {
        //TODO 先调融云，再入数据库
        SdkHttpResult res = ApiHttpClient.quitGroup(ImagePathUtil.getKey(), ImagePathUtil.getSecret(), userGroupVo.getUserId().toString(), userGroupVo.getGroupId().toString(), FormatType.json);
        if (200 != res.getHttpCode()) {
            throw new BusinessException(UpChinaError.RONGYUN_ERROR);
        }

        UserGroup userGroup = new UserGroup();
        userGroup.setUpdateTime(new Date());
        userGroup.setStatus(Constants.STATUS_QUIT);
        Example example = new Example(UserGroup.class);
        example.createCriteria().andEqualTo("userId", userGroupVo.getUserId()).andEqualTo("groupId", userGroupVo.getGroupId());
        updateByExampleSelective(userGroup, example);
        niuGroupService.updateUserCount(userGroupVo.getGroupId(), -1);
		
		//用户成功退出牛圈后，向牛圈的创建者推送消息
		PushMessageUserOutVo userMessageInVo = userInfoService.findByUserId(userGroupVo.getUserId());
		NiuGroup niuGroup = niuGroupService.selectByPrimaryKey(userGroupVo.getGroupId());
		List<Integer> users = new ArrayList<Integer>();
		users.add(niuGroup.getUserId());
		pushMessageService.pushQuitNiuGroupMessage(users, userMessageInVo.getUserId(), userMessageInVo.getUserName(), userMessageInVo.getAvatar(), niuGroup.getId(), niuGroup.getName());
    }


    /*public UserInfoVo viewMember(Integer userId) throws IllegalAccessException, InvocationTargetException {
        UserInfo userInfo = userInfoService.selectByPrimaryKey(userId);
        UserInfoVo member=new UserInfoVo();
        BeanUtils.copyProperties(member, userInfo);
        List<NiuGroup> group = userGroupMapper.selectUserGroup(userId);
        List<UserInfo> management = userGroupMapper.selectUserManagement(userId);
        member.setGroup(group);
        member.setManagement(management);;
        return member;

    }*/
    public UserInfoVo viewMember(Integer userId, Integer currUserId) throws IllegalAccessException, InvocationTargetException {
        UserInfo userInfo = userInfoService.selectByPrimaryKey(userId);
        UserInfoVo member = new UserInfoVo();
        if (userInfo != null) {
            BeanUtils.copyProperties(member, userInfo);
            List<NiuGroup> groups = userGroupMapper.selectUserGroup(userId, Constants.STATUS_GROUP_ON, Constants.STATUS_JOIN);
            for (NiuGroup niuGroup : groups) {
                niuGroup.setImg(ImagePathUtil.getImgHost() + niuGroup.getImg());
            }
            List<UserInfo> management = userGroupMapper.selectUserManagement(userId, Constants.USER_TYPE_INVESTMENT, Constants.RELATION_FRIEND);
            member.setGroup(groups);
            member.setManagement(management);
            ;

//		Example example=new Example(UserFriend.class);
//		example.createCriteria().andEqualTo("userId", userId).andEqualTo("friendId", currUserId).andEqualTo("status", Constants.RELATION_FRIEND);
//		example.or().andEqualTo("userId",currUserId ).andEqualTo("friendId",userId ).andEqualTo("status", Constants.RELATION_FRIEND);
//		List<UserFriend>  friendship= userFriendService.selectByExample(example);
//		 if(friendship!=null&&friendship.size()>0){
//			 member.setRelation(Constants.RELATION_FRIEND);
//		 }else{
//			 member.setRelation(Constants.RELATION_NONE);
//		 }
            Integer status = userFriendService.getFriendStatus(userId, currUserId);
            member.setRelation(status);
            return member;
        } else {
            throw new RuntimeException("圈友不存在");
        }

    }

    public List<UserInfo> listMember(Integer groupId, Integer pageNum,
                                     Integer pageSize) {
//		PageHelper.startPage(pageNum, pageSize);
        List<UserInfo> memberList = userGroupMapper.selectMemberList(groupId, Constants.STATUS_JOIN);
        return memberList;
//		PageInfo<UserInfo> pageInfo = new PageInfo(memberList);
//		return new jqGridResponseVo(pageInfo.getPages(),pageInfo.getList(),pageNum,pageInfo.getTotal());
    }

    public List<UserInfo> listAllMember(Integer groupId, Integer pageNum,
                                        Integer pageSize) {
        List<UserInfo> memberList = userGroupMapper.selectAllMemberList(groupId);
        return memberList;
    }


    public jqGridResponseVo<UserGroup> selectByUserId(UserGroupVo userGroupVo) throws Exception {
        Integer userId = userGroupVo.getUserId();
        Integer pageSize = userGroupVo.getPageSize();
        Integer pageNum = userGroupVo.getPageNum();
        String sql = "select a.Id,a.Group_Id groupId, a.`Type` type, a.User_Id userId,a.Create_Time createTime, a.Update_Time updateTime, a.`Status` status, b.Name groupName, c.User_Name userName, b.Img, b.Intro , b.User_Id masterId,b.User_Count userCount from user_group a, niu_group b, user_info c where a.Group_Id = b.Id and b.User_Id = c.User_Id and a.`Status` =1";
        if (null != userId && 0 != userId) {
            sql += " and a.User_Id = #{userId}";
        }
        if (null == pageSize || 0 == pageSize) {
            pageSize = 10;
        }
        if (null == pageNum || 0 == pageNum) {
            pageNum = 1;
        }

        PageHelper.startPage(pageNum, pageSize);
        List<UserGroup> userGroups = sqlMapper.selectList(sql, userGroupVo, UserGroup.class);
        PageInfo<UserGroup> pageInfo = new PageInfo(userGroups);
        return new jqGridResponseVo(pageInfo.getPages(), pageInfo.getList(), pageNum, pageInfo.getTotal());
    }

    @Transactional
    public void userNoSaying(UserGroup userGroup) {
        UserNoSayingTime userNoSayingTime = new UserNoSayingTime();
        userNoSayingTime.setUserGroupId(userGroup.getId());
        userNoSayingTime.setStartTime(new Date());
        userNoSayingTimeMapper.insertSelective(userNoSayingTime);
        this.updateByPrimaryKeySelective(userGroup);
    }

    @Transactional
    public void userQuitGroup(UserGroup userGroup) {
        Example example = new Example(UserNoSayingTime.class);
        example.createCriteria().andEqualTo("userGroupId", userGroup.getId());
        userNoSayingTimeMapper.deleteByExample(example);
        this.updateByPrimaryKeySelective(userGroup);
    }

}