package com.upchina.service;

import io.rong.ApiHttpClient;
import io.rong.models.FormatType;
import io.rong.models.SdkHttpResult;
import io.rong.util.GsonUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SqlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.upchina.Exception.BusinessException;
import com.upchina.Exception.UpChinaError;
import com.upchina.dao.NiuGroupMapper;
import com.upchina.model.GroupTag;
import com.upchina.model.NiuGroup;
import com.upchina.model.Tag;
import com.upchina.model.UserGroup;
import com.upchina.model.UserInfo;
import com.upchina.util.Constants;
import com.upchina.util.ImagePathUtil;
import com.upchina.vo.BaseCodeVo;
import com.upchina.vo.BaseOutVo;
import com.upchina.vo.PageVo;
import com.upchina.vo.jqGridResponseVo;
import com.upchina.vo.rest.input.DicTypeInVo;
import com.upchina.vo.rest.input.GroupInVo;
import com.upchina.vo.rest.input.NiuGroupCareSelInVo;
import com.upchina.vo.rest.input.NiuGroupInVo;
import com.upchina.vo.rest.input.TagInVo;
import com.upchina.vo.rest.input.UserGroupVo;
import com.upchina.vo.rest.output.NiuGroupCareSelOutVo;
import com.upchina.vo.rest.output.NiuGroupSearchOutVo;
import com.upchina.vo.rest.output.NiuGroupVo;
import com.upchina.vo.rest.output.PushMessageUserOutVo;
import com.upchina.vo.rest.output.TagOutVo;
import com.upchina.vo.rest.output.UserProfileVo;

/**
 * Created by codesmith on 2015
 */

@Service("niuGroupService")
public class NiuGroupService extends BaseService<NiuGroup, Integer> {


    @Autowired
    private NiuGroupMapper niuGroupMapper;

    @Autowired
    private GroupTagService groupTagService;

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private TagService tagService;
	
	@Autowired
	private UserFriendService userFriendService;
    
    @Autowired
    private PushMessageService pushMessageService;
    /**
     * 判断字段是否存在（插入和修改时可用,修改本身时如判断字段未发生修改会忽略）
     *
     * @param inputName 要判断的字段名
     * @param value     要判断的值
     * @param id        当前记录id
     * @return 是否存在
     */
    public boolean isExist(String inputName, String value, int id) {
        Example exp = new Example(NiuGroup.class);
        Example.Criteria criteria = exp.createCriteria();
        criteria.andEqualTo(inputName, value);
        List<NiuGroup> list = selectByExample(exp);
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

    public BaseOutVo createGroup(GroupInVo groupInVo) {
        BaseOutVo baseOutVo = new BaseOutVo();
        try {
            String userIdStr = groupInVo.getUserId();
            String groupName = groupInVo.getGroupName();
            Integer groupId = groupInVo.getGroupId();
            if (StringUtils.isEmpty(userIdStr)) {
                baseOutVo.setResultCode(UpChinaError.USERID_NULL_ERROR.code);
                baseOutVo.setResultMsg(UpChinaError.USERID_NULL_ERROR.message);
                return baseOutVo;
            }
            if (StringUtils.isEmpty(groupName)) {//牛圈名称
                baseOutVo.setResultCode(UpChinaError.GROUP_NAME_ERROR.code);
                baseOutVo.setResultMsg("牛圈名称不得超过10个字");
                return baseOutVo;
            }
            if (StringUtils.isEmpty(groupInVo.getIntro())) {//牛圈简介
                baseOutVo.setResultCode(UpChinaError.GROUP_INTRO_EMPTY.code);
                baseOutVo.setResultMsg("牛圈简介只能是20~100个字");
                return baseOutVo;
            }
            if (!StringUtils.isNumeric(StringUtils.trim(groupName))) {
                //牛圈名称长度不能超过10个字符
                if (StringUtils.trim(groupName).length() < 1 || StringUtils.trim(groupName).length() > 10) {
                    baseOutVo.setResultCode(UpChinaError.GROUP_NAME_LONG.code);
                    baseOutVo.setResultMsg("牛圈名称不得超过10个字");
                    return baseOutVo;
                }
            }
            if (!StringUtils.isNumeric(StringUtils.trim(groupInVo.getIntro()))) {
                //牛圈简介为20到100字
                if (StringUtils.trim(groupInVo.getIntro()).length() < 20 || StringUtils.trim(groupInVo.getIntro()).length() > 100) {
                    baseOutVo.setResultCode(UpChinaError.GROUP_INTRO_LONG.code);
                    baseOutVo.setResultMsg("牛圈简介只能是20~100个字");
                    return baseOutVo;
                }
            }
            //TODO 判断牛圈存不存在 加入牛圈状态
            Integer userId = Integer.parseInt(userIdStr);
            UserInfo userInfo = userInfoService.selectByPrimaryKey(userId);
            if (null == userInfo) {
                baseOutVo.setResultCode(UpChinaError.USER_NOT_EXIST_ERROR.code);
                baseOutVo.setResultMsg(UpChinaError.USER_NOT_EXIST_ERROR.message);
                return baseOutVo;
            }
            Example example = new Example(NiuGroup.class);
            //判断牛圈名称是否重复
            example.createCriteria().andEqualTo("name", StringUtils.trim(groupName));
            List<NiuGroup> list = this.selectByExample(example);
            if (list != null && !list.isEmpty()) {
                baseOutVo.setResultCode(UpChinaError.GROUP_IS_EXIST_ERROR.code);
                baseOutVo.setResultMsg(UpChinaError.GROUP_IS_EXIST_ERROR.message);
                return baseOutVo;
            }
            example.clear();//清理按照牛圈名称匹配查询的条件
            example.createCriteria().andEqualTo("userId", userId);
            int count = selectCountByExample(example);
            int maxCreateGroupNum = userInfo.getMaxCreateGroupNum();
            if (count >= maxCreateGroupNum) {
                baseOutVo.setResultCode(UpChinaError.NIU_GROUP_CREATE_MAX_NUM_ERROR.code);
                baseOutVo.setResultMsg(UpChinaError.NIU_GROUP_CREATE_MAX_NUM_ERROR.message);
                return baseOutVo;
            }
            if (null != groupId && 0 != groupId) {
                baseOutVo = updateNiuGroup(groupInVo, userInfo);
            } else {
                baseOutVo = insertNiuGroup(groupInVo, userInfo);

            }
            
            //发布笔记成功后，推送消息给好友
            List<UserInfo> users = userFriendService.getListAllFriend(Integer.parseInt(groupInVo.getUserId()));
			PushMessageUserOutVo userMessageInVo = userInfoService.findByUserId(Integer.parseInt(groupInVo.getUserId()));
			pushMessageService.pushEstablishNiuGroupMessage(users, Integer.parseInt(groupInVo.getUserId()), userMessageInVo.getUserName(), userMessageInVo.getAvatar(), groupInVo.getGroupId(), groupInVo.getGroupName());

        } catch (Exception e) {
            baseOutVo.setResultCode(UpChinaError.ERROR.code);
            baseOutVo.setResultMsg(UpChinaError.ERROR.message);
            return baseOutVo;
        }
        return baseOutVo;

    }

    private BaseOutVo insertNiuGroup(GroupInVo groupInVo, UserInfo userInfo) throws Exception {
        BaseOutVo baseOutVo = new BaseOutVo();
        String userIdStr = groupInVo.getUserId();
        String groupName = groupInVo.getGroupName();
        String tags = groupInVo.getTagIds();
        String intro = groupInVo.getIntro();
        String img = groupInVo.getImg();
        Integer userId = Integer.parseInt(userIdStr);

        String key = ImagePathUtil.getKey();
        String secret = ImagePathUtil.getSecret();
        Date now = new Date();

        NiuGroup niuGroup = new NiuGroup();
        niuGroup.setUserId(userId);
        niuGroup.setName(groupName);
        niuGroup.setIntro(intro);
        niuGroup.setCreateTime(now);
        niuGroup.setUpdateTime(now);
        niuGroup.setImg(img);
        if (!StringUtils.isEmpty(img)) {
            niuGroup.setThumbnail(img.replaceAll("\\.", "-thumbnail."));
        }
        niuGroup.setStatus(Constants.STATUS_NORMAL);
        insertSelective(niuGroup);

        Integer groupId = niuGroup.getId();
        groupInVo.setGroupId(groupId);//消息推送调用时需要groupId
        List<String> list = new ArrayList<String>();
        list.add(userIdStr);
        SdkHttpResult result = ApiHttpClient.createGroup(key, secret, list, String.valueOf(groupId), groupName,
                FormatType.json);
        BaseCodeVo baseCodeVo = (BaseCodeVo) GsonUtil.fromJson(result.toString(), BaseCodeVo.class);
        if (!baseCodeVo.getCode().equals("200")) {
            baseOutVo.setResultCode(UpChinaError.GROUP_CREATE_ERROR.code);
            baseOutVo.setResultMsg(UpChinaError.GROUP_CREATE_ERROR.message);
            return baseOutVo;
        }

        if (null != tags && !"".equals(tags.trim())) {
            String[] tagIdStrs = tags.split(",");
            //将已经存在但客户端没有传过来的tagId标记为删除
            for (String tagIdStr : tagIdStrs) {
                Integer tagId = Integer.parseInt(tagIdStr);
                Tag tag = tagService.selectByPrimaryKey(tagId);
                if (null == tag) {
                    baseOutVo.setResultCode(UpChinaError.TAG_NOT_EXIST_ERROR.code);
                    baseOutVo.setResultMsg(UpChinaError.TAG_NOT_EXIST_ERROR.message);
                    return baseOutVo;
                }

                tag.setGroupCount(tag.getGroupCount() + 1);
                tagService.updateByPrimaryKeySelective(tag);

                //根据groupId和tagId查询是否存在
                GroupTag groupTag = new GroupTag();
                groupTag.setGroupId(groupId);
                groupTag.setTagId(tagId);
                groupTag.setCreateTime(now);
                groupTag.setUpdateTime(now);
                groupTag.setStatus(Constants.STATUS_ADD);
                groupTagService.insertSelective(groupTag);
            }
        }
        //根据groupId和userId查询是否存在

        UserGroup userGroup = new UserGroup();
        userGroup.setUserId(userId);
        userGroup.setGroupId(groupId);
        userGroup.setCreateTime(now);
        userGroup.setUpdateTime(now);
        userGroup.setType(1);
        userGroup.setStatus(1);
        userGroupService.insertSelective(userGroup);

        //增加牛圈人数
        updateUserCount(groupId, 1);

        baseOutVo.setResultCode(UpChinaError.SUCCESS.code);
        baseOutVo.setResultMsg(UpChinaError.SUCCESS.message);
        niuGroup.setUserName(userInfo.getUserName());
        niuGroup.setImg(ImagePathUtil.getImgHost() + niuGroup.getImg());
        niuGroup.setThumbnail(ImagePathUtil.getImgHost() + niuGroup.getThumbnail());
        baseOutVo.setResultData(niuGroup);
        return baseOutVo;
    }

    public void updateUserCount(Integer groupId, int change) {
        NiuGroup niuGroup = selectByPrimaryKey(groupId);
        Integer userCount = niuGroup.getUserCount();
        if (userCount == null) {
            userCount = 0;
        }
        int userCountNew = userCount + change;
        if (userCountNew < 0) {
            userCountNew = 0;
        }
        niuGroup.setUserCount(userCountNew);
        niuGroup.setUpdateTime(new Date());
        updateByPrimaryKey(niuGroup);
    }

    private BaseOutVo updateNiuGroup(GroupInVo groupInVo, UserInfo userInfo) throws Exception {
        BaseOutVo baseOutVo = new BaseOutVo();
        String userIdStr = groupInVo.getUserId();
        String groupName = groupInVo.getGroupName();
        String tags = groupInVo.getTagIds();
        String intro = groupInVo.getIntro();
        String img = groupInVo.getImg();
        Integer userId = Integer.parseInt(userIdStr);
        Integer groupId = groupInVo.getGroupId();

        NiuGroup niuGroup = new NiuGroup();

        Example example = new Example(NiuGroup.class);
        example.createCriteria().andEqualTo("id", groupId).andEqualTo("userId", userId);
        List<NiuGroup> niuGroups = selectByExample(example);
        if (null != niuGroups && niuGroups.size() == 1) {
            niuGroup = niuGroups.get(0);
        } else {
            baseOutVo.setResultCode(UpChinaError.GROUP_UPDATE_ERROR.code);
            baseOutVo.setResultMsg(UpChinaError.GROUP_UPDATE_ERROR.message);
            return baseOutVo;
        }
        String key = ImagePathUtil.getKey();
        String secret = ImagePathUtil.getSecret();
        Date now = new Date();
        niuGroup.setUserId(userId);
        niuGroup.setName(groupName);
        niuGroup.setIntro(intro);
        niuGroup.setCreateTime(now);
        niuGroup.setUpdateTime(now);
        niuGroup.setImg(img);
        if (!StringUtils.isEmpty(img)) {
            niuGroup.setThumbnail(img.replaceAll("\\.", "-thumbnail."));
        }
        niuGroup.setStatus(Constants.STATUS_NORMAL);
        if (null != groupId && 0 != groupId) {
            SdkHttpResult result = ApiHttpClient.refreshGroupInfo(key, secret, String.valueOf(groupId), groupName,
                    FormatType.json);
            BaseCodeVo baseCodeVo = (BaseCodeVo) GsonUtil.fromJson(result.toString(), BaseCodeVo.class);
            if (!baseCodeVo.getCode().equals("200")) {
                baseOutVo.setResultCode(UpChinaError.RONGYUN_ERROR.code);
                baseOutVo.setResultMsg(UpChinaError.RONGYUN_ERROR.message);
                return baseOutVo;
            }
            updateByPrimaryKey(niuGroup);
        }
        if (null != tags && !"".equals(tags.trim())) {
            String[] tagIdStrs = tags.split(",");
            List list = new ArrayList();
            list = Arrays.asList(tagIdStrs);
            //将已经存在但客户端没有传过来的tagId标记为删除
            example = new Example(GroupTag.class);
            example.createCriteria().andEqualTo("groupId", groupId).andNotIn("tagId", list).andEqualTo("status", Constants.STATUS_ADD);
            List<GroupTag> groupTags = groupTagService.selectByExample(example);
            for (GroupTag groupTag : groupTags) {
                groupTag.setStatus(Constants.STATUS_DELETE);
                groupTagService.updateByPrimaryKeySelective(groupTag);
                Integer tagId = groupTag.getTagId();
                Tag tag = tagService.selectByPrimaryKey(tagId);
                int groupCount = tag.getGroupCount() - 1;
                if (groupCount < 0) {
                    groupCount = 0;
                }
                tag.setGroupCount(groupCount);
                tagService.updateByPrimaryKey(tag);
            }
            for (String tagIdStr : tagIdStrs) {
                Integer tagId = Integer.parseInt(tagIdStr);
                Tag tag = tagService.selectByPrimaryKey(tagId);
                if (null == tag) {
                    baseOutVo.setResultCode(UpChinaError.TAG_NOT_EXIST_ERROR.code);
                    baseOutVo.setResultMsg(UpChinaError.TAG_NOT_EXIST_ERROR.message);
                    return baseOutVo;
                }
                tag.setGroupCount(tag.getGroupCount() + 1);
                tagService.updateByPrimaryKeySelective(tag);

                //根据groupId和tagId查询是否存在
                example = new Example(GroupTag.class);
                example.createCriteria().andEqualTo("groupId", groupId).andEqualTo("tagId", tagId);
                groupTags = groupTagService.selectByExample(example);
                if (null != groupTags && groupTags.size() > 0) {
                    continue;
                }
                GroupTag groupTag = new GroupTag();
                groupTag.setGroupId(groupId);
                groupTag.setTagId(tagId);
                groupTag.setCreateTime(now);
                groupTag.setUpdateTime(now);
                groupTag.setStatus(Constants.STATUS_ADD);
                groupTagService.insertSelective(groupTag);
            }
        }
        //根据groupId和userId查询是否存在
        example = new Example(UserGroup.class);
        example.createCriteria().andEqualTo("groupId", groupId).andEqualTo("userId", userId);
        List<UserGroup> userGroups = userGroupService.selectByExample(example);
        if (null == userGroups || userGroups.size() == 0) {
            UserGroup userGroup = new UserGroup();
            userGroup.setUserId(userId);
            userGroup.setGroupId(groupId);
            userGroup.setCreateTime(now);
            userGroup.setUpdateTime(now);
            userGroup.setType(1);
            userGroup.setStatus(1);
            userGroupService.insertSelective(userGroup);
        }
        baseOutVo.setResultCode(UpChinaError.SUCCESS.code);
        baseOutVo.setResultMsg(UpChinaError.SUCCESS.message);
        baseOutVo.setResultData(niuGroup);
        return baseOutVo;
    }

    /**
     * 解散牛圈
     *
     * @param userGroupVo
     * @throws Exception
     */
    public void dismiss(UserGroupVo userGroupVo) throws Exception {
        NiuGroup group = selectByPrimaryKey(userGroupVo.getGroupId());
        if (group != null && group.getUserId() != userGroupVo.getUserId()) {
            throw new RuntimeException("非创建者不可解散群组");
        }
        SdkHttpResult res = ApiHttpClient.dismissGroup(ImagePathUtil.getKey(), ImagePathUtil.getSecret(), userGroupVo.getUserId().toString(), userGroupVo.getGroupId().toString(), FormatType.json);
        if (200 != res.getHttpCode()) {
            throw new BusinessException(UpChinaError.RONGYUN_ERROR);
        }
        NiuGroup niuGroup = new NiuGroup();
        niuGroup.setId(userGroupVo.getGroupId());
        niuGroup.setUpdateTime(new Date());
        niuGroup.setStatus(Constants.STATUS_DISMISS);
        updateByPrimaryKeySelective(niuGroup);
    }

    /**
     * 牛圈详细信息
     *
     * @param
     * @return
     * @throws Exception
     */
    /*public NiuGroupVo view(Integer groupId) {
        NiuGroupVo niuGroup=niuGroupMapper.selectGroupById(groupId);
		if(niuGroup!=null){
			List<Tag> tagList=niuGroupMapper.selectGroupTagById(groupId);
			niuGroup.setGroupTag(tagList);
		}
		
		return niuGroup;
	}*/
    public NiuGroupVo view(UserGroupVo userGroupVo) throws Exception {
        NiuGroupVo niuGroup = niuGroupMapper.selectGroupById(userGroupVo.getGroupId());
        if (niuGroup != null) {
            List<Tag> tagList = niuGroupMapper.selectGroupTagById(userGroupVo.getGroupId());
            niuGroup.setGroupTag(tagList);
            niuGroup.setImg(ImagePathUtil.getImgHost() + niuGroup.getImg());
            niuGroup.setThumbnail(ImagePathUtil.getImgHost() + niuGroup.getThumbnail());
            if (userGroupVo.getUserId() != null) {
                Example example = new Example(UserGroup.class);
                example.createCriteria().andEqualTo("groupId", userGroupVo.getGroupId()).andEqualTo("userId", userGroupVo.getUserId()).andEqualTo("status", Constants.STATUS_JOIN);
                List<UserGroup> userGroupList = userGroupService.selectByExample(example);
                if (userGroupList != null && userGroupList.size() > 0) {
                    niuGroup.setRelation(Constants.STATUS_JOIN);
                } else {
                    niuGroup.setRelation(Constants.STATUS_QUIT);
                }
            } else {
                niuGroup.setRelation(Constants.STATUS_QUIT);
            }
            UserProfileVo userProfileVo = userInfoService.findUserProfile(niuGroup.getUserId());
            if(null != userProfileVo){
            	niuGroup.setUserName(userProfileVo.getUserName());
            }
            UserInfo userInfo = userInfoService.findUserInfo(niuGroup.getUserId());
            niuGroup.setUserInfo(userInfo);
        }
        return niuGroup;
    }

    public jqGridResponseVo<NiuGroupSearchOutVo> getNiuGroupListByTags(TagInVo tagInVo) throws Exception {
        String tagIds = tagInVo.getTagIds();
        Integer userId = tagInVo.getUserId();
        int pageNum = tagInVo.getPageNum();
        int pageSize = tagInVo.getPageSize();
        if (0 == pageNum) {
            pageNum = 1;
        }
        if (0 == pageSize) {
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);
        SqlBuilder sb = new SqlBuilder();
        String sql = "select a.Id groupId, a.User_Id as userId, b.User_Name userName, a.Name name, a.Intro intro, a.Img img, a.Create_Time createTime, a.Update_Time updateTime, a.`Status` status, a.User_Count userCount,a.Max_User_Count as maxUserCount,(a.Max_User_Count-a.User_Count) as remainedCount from niu_group a, user_info b where  a.User_Id = b.User_Id and a.`Status` in(1,3)";
        if (null != tagIds && !"".equals(tagIds.trim())) {
            sql += "and a.Id in(select b.Group_Id from group_tag b where b.Tag_Id in (#{tagIds}) and b.`Status` = 1)";
        }
        if (null != userId) {
            sql += " and not exists(select 1 from user_group t5 where a.id=t5.Group_Id and t5.User_Id=#{userId} and `status`=1) ";
        }
        sql += " order by a.User_Count desc";
        List<NiuGroupSearchOutVo> list = sqlMapper.selectList(sql, tagInVo, NiuGroupSearchOutVo.class);
        for (NiuGroupSearchOutVo niuGroupSearchOutVo : list) {
            niuGroupSearchOutVo.setImg(ImagePathUtil.getImgHost() + niuGroupSearchOutVo.getImg());
            Integer iaUserId = niuGroupSearchOutVo.getUserId();
            UserProfileVo userProfileVo = userInfoService.findUserProfile(iaUserId);
            if (null != userProfileVo) {
                niuGroupSearchOutVo.setUserName(userProfileVo.getUserName());
                niuGroupSearchOutVo.setAdviserType(userProfileVo.getAdviserType());
                niuGroupSearchOutVo.setAvatar(userProfileVo.getAvatar());
                List<TagOutVo> tagOutVos = userInfoService.findTag(iaUserId);
                niuGroupSearchOutVo.setUserTags(tagOutVos);
            }
            List<TagOutVo> groupTags = tagService.selectTagByGroupId(niuGroupSearchOutVo.getGroupId());
            niuGroupSearchOutVo.setGroupTags(groupTags);
        }
        PageInfo<NiuGroupSearchOutVo> pageInfo = new PageInfo(list);
        return new jqGridResponseVo(pageInfo.getPages(), pageInfo.getList(), pageNum, pageInfo.getTotal());
    }

    /**
     * 根据关键字模糊匹配(牛圈名称/牛圈创建者名称/牛圈标签名称)分页查询牛圈列表
     *
     * @param niuGroupInVo
     * @return
     * @throws Exception
     */
    public jqGridResponseVo<NiuGroupSearchOutVo> getListByKeyword(NiuGroupInVo niuGroupInVo) throws Exception {
        String keyword = niuGroupInVo.getKeyword();
        int pageNum = niuGroupInVo.getPageNum();
        int pageSize = niuGroupInVo.getPageSize();

        String sql = getListByKeywordSql(keyword, niuGroupInVo.getUserId());
        PageHelper.startPage(pageNum, pageSize);
        List<NiuGroupSearchOutVo> list = sqlMapper.selectList(sql, niuGroupInVo, NiuGroupSearchOutVo.class);
        for (NiuGroupSearchOutVo niuGroupSearchOutVo : list) {
            niuGroupSearchOutVo.setImg(ImagePathUtil.getImgHost() + niuGroupSearchOutVo.getImg());
            Integer iaUserId = niuGroupSearchOutVo.getUserId();
            UserProfileVo userProfileVo = userInfoService.findUserProfile(iaUserId);
            if (null != userProfileVo) {
                niuGroupSearchOutVo.setUserName(userProfileVo.getUserName());
                niuGroupSearchOutVo.setAdviserType(userProfileVo.getAdviserType());
                niuGroupSearchOutVo.setAvatar(userProfileVo.getAvatar());
            }
        }
//        List<NiuGroup> list = niuGroupMapper.getListByKeyword(keyword);
        PageInfo<NiuGroupSearchOutVo> pageInfo = new PageInfo(list);
        return new jqGridResponseVo(pageInfo.getPages(), pageInfo.getList(), pageNum, pageInfo.getTotal());
    }

    private String getListByKeywordSql(String keyword, Integer userId) {
        StringBuffer sql = new StringBuffer(
                "SELECT t1.Id groupId, t1.User_Id as userId, t1.Name name, t1.Intro intro, t1.Img img, t1.User_Count userCount,t1.Create_Time createTime, t1.Update_Time updateTime, t1.`Status` status ,t2.User_Name userName FROM niu_group t1  inner join user_info t2 on t1.user_id=t2.user_id  WHERE t1.`Status` in(" + Constants.STATUS_GROUP_ON + ") ");
        if (!StringUtils.isEmpty(keyword)) {
            sql.append(" AND (t1.name like CONCAT('%',#{keyword},'%') OR t1.Intro like CONCAT('%',#{keyword},'%') OR t2.user_name like CONCAT('%',#{keyword},'%') or exists(select 1 from group_tag t3 inner join tag t4 on t3.tag_Id=t4.id where t1.id=t3.group_id and t4.name like CONCAT('%',#{keyword},'%')))");
        }
        if (null != userId) {
            sql.append(" and not exists(select 1 from user_group t5 where t1.id=t5.Group_Id and t5.User_Id=#{userId} and status=" + Constants.STATUS_JOIN + ")");
        }
        sql.append(" order by t1.user_Count desc");
        return sql.toString();
    }

    public jqGridResponseVo<NiuGroupCareSelOutVo> carefulSelectNiuGroup(NiuGroupCareSelInVo niuGroupCareSelInVo) {
        Integer userId = niuGroupCareSelInVo.getUserId();
        int pageNum = niuGroupCareSelInVo.getPageNum();
        int pageSize = niuGroupCareSelInVo.getPageSize();
        if (0 == pageNum) {
            pageNum = 1;
        }
        if (0 == pageSize) {
            pageSize = 3;
        }
        PageHelper.startPage(pageNum, pageSize);
        StringBuffer sql = new StringBuffer();
        sql.append("select Id as id, User_Id as userId,Name as name,Intro as intro,Img as img,User_Count as userCount from niu_group where ");
        sql.append("(status='").append(Constants.STATUS_TOP).append("'");
        sql.append(" or status='").append(Constants.STATUS_NORMAL).append("')");
        if (niuGroupCareSelInVo.getUserId() != null) {
            sql.append(" and id not in (select group_id from user_group where User_id=#{userId} and Status='1') ");
        }
        sql.append(" order by User_Count desc");
        List<NiuGroupCareSelOutVo> niuGroups = sqlMapper.selectList(sql.toString(), niuGroupCareSelInVo, NiuGroupCareSelOutVo.class);

        for (NiuGroupCareSelOutVo niuGroupCareSelOutVo : niuGroups) {
            niuGroupCareSelOutVo.setImg(ImagePathUtil.getImgHost() + niuGroupCareSelOutVo.getImg());
        }

        PageInfo<NiuGroupCareSelOutVo> pageInfo = new PageInfo(niuGroups);
        return new jqGridResponseVo(pageInfo.getPages(), pageInfo.getList(), pageNum, pageInfo.getTotal());
    }

    public jqGridResponseVo<NiuGroupSearchOutVo> selectGroupsOrderByUserCount(DicTypeInVo dicTypeInVo) {
        int pageNum = dicTypeInVo.getPageNum();
        int pageSize = dicTypeInVo.getPageSize();
        if (0 == pageNum) {
            pageNum = 1;
        }
        if (0 == pageSize) {
            pageSize = 10;
        }

        PageHelper.startPage(pageNum, pageSize);
        List<NiuGroupSearchOutVo> niuGroups = niuGroupMapper.selectGroupsOrderByUserCount(dicTypeInVo.getUserId());
        PageInfo<NiuGroupSearchOutVo> pageInfo = new PageInfo<NiuGroupSearchOutVo>(niuGroups);
        return new jqGridResponseVo<NiuGroupSearchOutVo>(pageInfo.getPages(), pageInfo.getList(), pageNum, pageInfo.getTotal());
    }

    public jqGridResponseVo<NiuGroupSearchOutVo> selectGroupsByTag(DicTypeInVo dicTypeInVo, String tagName) {
        int pageNum = dicTypeInVo.getPageNum();
        int pageSize = dicTypeInVo.getPageSize();
        if (0 == pageNum) {
            pageNum = 1;
        }
        if (0 == pageSize) {
            pageSize = 10;
        }

        PageHelper.startPage(pageNum, pageSize);
        List<NiuGroupSearchOutVo> niuGroups = niuGroupMapper.selectGroupsByTag(tagName,dicTypeInVo.getUserId());
        PageInfo<NiuGroupSearchOutVo> pageInfo = new PageInfo<NiuGroupSearchOutVo>(niuGroups);
        return new jqGridResponseVo<NiuGroupSearchOutVo>(pageInfo.getPages(), pageInfo.getList(), pageNum, pageInfo.getTotal());
    }

    public jqGridResponseVo<NiuGroupSearchOutVo> getGroupsByName(String groupName, PageVo pageVo) {
        PageHelper.startPage(pageVo.getPageNum(), pageVo.getPageSize());
        List<NiuGroupSearchOutVo> niuGroups = niuGroupMapper.getGroupsByName(groupName);
        PageInfo<NiuGroupSearchOutVo> pageInfo = new PageInfo<NiuGroupSearchOutVo>(niuGroups);
        return new jqGridResponseVo<NiuGroupSearchOutVo>(pageInfo.getPages(), pageInfo.getList(), pageVo.getPageNum(), pageInfo.getTotal());
    }

}