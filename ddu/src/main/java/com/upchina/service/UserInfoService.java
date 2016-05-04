package com.upchina.service;

import io.rong.ApiHttpClient;
import io.rong.models.FormatType;
import io.rong.models.SdkHttpResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.upchina.Exception.UpChinaError;
import com.upchina.dao.UserInfoMapper;
import com.upchina.model.UserInfo;
import com.upchina.model.UserProfile;
import com.upchina.model.UserTag;
import com.upchina.util.Constants;
import com.upchina.util.ImagePathUtil;
import com.upchina.util.JacksonUtil;
import com.upchina.util.LoginUtil;
import com.upchina.vo.BaseOutVo;
import com.upchina.vo.jqGridResponseVo;
import com.upchina.vo.rest.LoginVo;
import com.upchina.vo.rest.RegistVo;
import com.upchina.vo.rest.ReponseResultVo;
import com.upchina.vo.rest.UserVo;
import com.upchina.vo.rest.input.DicTypeInVo;
import com.upchina.vo.rest.input.InvestmentDetailInVo;
import com.upchina.vo.rest.input.RecommentUserInVo;
import com.upchina.vo.rest.input.TagInVo;
import com.upchina.vo.rest.input.UserInVo;
import com.upchina.vo.rest.output.InvestmentDetailOutVo;
import com.upchina.vo.rest.output.PushMessageUserOutVo;
import com.upchina.vo.rest.output.RecommentUserOutVo;
import com.upchina.vo.rest.output.ResultOutVo;
import com.upchina.vo.rest.output.TagOutVo;
import com.upchina.vo.rest.output.TokenOutVo;
import com.upchina.vo.rest.output.UserFriendAddOutVo;
import com.upchina.vo.rest.output.UserGroupOutVo;
import com.upchina.vo.rest.output.UserProfileOutVo;
import com.upchina.vo.rest.output.UserProfileVo;

/**
 * Created by codesmith on 2015
 */

@Service("userInfoService")
public class UserInfoService extends BaseService<UserInfo, Integer> {

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Autowired
	private UserFriendService userFriendService;

	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private UserTagService userTagService;

	@Autowired
	private JedisPool jedisPool;

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
		Example exp = new Example(UserInfo.class);
		Example.Criteria criteria = exp.createCriteria();
		criteria.andEqualTo(inputName, value);
		List<UserInfo> list = selectByExample(exp);
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
	 * @param pageVo
	 * @return
	 */
	public jqGridResponseVo<UserInfo> getListInvestmentAdvisorAndTags(TagInVo tagInVo) {
		int pageNum = tagInVo.getPageNum();
		int pageSize = tagInVo.getPageSize();
		if (0 == pageNum) {
			pageNum = 1;
		}
		if (0 == pageSize) {
			pageSize = 10;
		}
		PageHelper.startPage(pageNum, pageSize);
		List<UserInfo> investmentAdvisors = new ArrayList<UserInfo>();
		investmentAdvisors = this.userInfoMapper.getListInvestmentAdvisorAndTags(tagInVo.getUserId(),
				Constants.USER_TYPE_INVESTMENT, Constants.IS_FRIEND);

		PageInfo<UserInfo> pageInfo = new PageInfo<UserInfo>(investmentAdvisors);
		return new jqGridResponseVo<UserInfo>(pageInfo.getPages(), pageInfo.getList(), pageNum, pageInfo.getTotal());
	}

	/**
	 * 根据id查询投顾的详细信息
	 * 
	 * @return
	 * @throws Exception 
	 */
	public InvestmentDetailOutVo getInvestmentAdvisorAndTags(InvestmentDetailInVo investmentUserVo) throws Exception {
		InvestmentDetailOutVo userInfoTmp = new InvestmentDetailOutVo();
		Integer userId = investmentUserVo.getUserId();
		Integer currUserId = investmentUserVo.getCurrUserId();
		userInfoTmp = this.userInfoMapper.getInvestmentAdvisorAndTags(userId, Constants.USER_TYPE_INVESTMENT);
		UserProfileVo userProfileVo = findUserProfile(userId);
		if (null != userProfileVo) {
			userInfoTmp.setUserName(userProfileVo.getUserName());
			userInfoTmp.setAdviserType(userProfileVo.getAdviserType());
			userInfoTmp.setAvatar(userProfileVo.getAvatar());
		}
		Integer status = userFriendService.getFriendStatus(userId, currUserId);
		userInfoTmp.setRelation(status);
		return userInfoTmp;
	}

	/**
	 * @param tagInVo
	 * @return
	 */
	public jqGridResponseVo<UserInfo> getListInvestmentAdvisorByTags(TagInVo tagInVo) {
		String tagIds = tagInVo.getTagIds();
		int pageNum = tagInVo.getPageNum();
		int pageSize = tagInVo.getPageSize();

		if (0 == pageNum) {
			pageNum = 1;
		}
		if (0 == pageSize) {
			pageSize = 10;
		}
		PageHelper.startPage(pageNum, pageSize);
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		userInfos = this.userInfoMapper.getListInvestmentAdvisorByTags(tagIds, tagInVo.getUserId(),
				Constants.USER_TYPE_INVESTMENT, Constants.IS_FRIEND);
		PageInfo<UserInfo> pageInfo = new PageInfo<UserInfo>(userInfos);
		return new jqGridResponseVo<UserInfo>(pageInfo.getPages(), pageInfo.getList(), pageNum, pageInfo.getTotal());
	}

	public BaseOutVo getMobileCode(RegistVo registVo) {
		BaseOutVo res = new BaseOutVo();
		try {
			ReponseResultVo reponseResultVo = LoginUtil.getMobileCode(registVo.getPhoneNum());
			res = LoginUtil.getResponseInfo(reponseResultVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public BaseOutVo comfirmCode(RegistVo registVo) {
		BaseOutVo res = new BaseOutVo();
		try {
			ReponseResultVo reponseResultVo = LoginUtil.comfirmCode(registVo.getPhoneNum(), registVo.getVerifyCode());
			res = LoginUtil.getResponseInfo(reponseResultVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public BaseOutVo existName(RegistVo registVo) {
		BaseOutVo res = new BaseOutVo();
		try {
			ReponseResultVo reponseResultVo = LoginUtil.exsitUser(registVo);
			res.setResultData(reponseResultVo);
			res.setResultCode(UpChinaError.SUCCESS.code);
			res.setResultMsg(UpChinaError.SUCCESS.message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public BaseOutVo register(RegistVo registVo) {
		BaseOutVo res = new BaseOutVo();
		try {
			ReponseResultVo registResultVo = LoginUtil.register(registVo);
			res = LoginUtil.getResponseInfo(registResultVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public List<Map<String, Object>> selectByUserId(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param userId
	 * @return
	 */
	public UserFriendAddOutVo selectByUserId(Integer userId) {
		UserFriendAddOutVo userFriendAddOutVo = new UserFriendAddOutVo();
		userFriendAddOutVo = this.userInfoMapper.selectByUserId(userId);
		return userFriendAddOutVo;
	}

	public BaseOutVo login(LoginVo loginVo, HttpServletResponse response, HttpSession httpSession) {
		BaseOutVo res = new BaseOutVo();
		try {
			UserVo userVo = LoginUtil.login(loginVo);
			res.setResultData(userVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public jqGridResponseVo<RecommentUserOutVo> getRecommentUserInfo(RecommentUserInVo recommentUserInVo) throws Exception {
		int pageNum = recommentUserInVo.getPageNum();
		int pageSize = recommentUserInVo.getPageSize();
		Integer userId = recommentUserInVo.getUserId();
		PageHelper.startPage(pageNum, pageSize);
		List<RecommentUserOutVo> list = null;
		if (null == userId) {
			list = userInfoMapper.getRecommentUser();
		} else {
			list = userInfoMapper.getRecommentUserInfo(recommentUserInVo.getUserId());
		}
		for (RecommentUserOutVo recommentUserOutVo : list) {
			Integer iaUserId = recommentUserOutVo.getUserId();
			UserProfileVo userProfileVo = findUserProfile(iaUserId);
			if (null != userProfileVo) {
				recommentUserOutVo.setUserName(userProfileVo.getUserName());
				recommentUserOutVo.setAdviserType(userProfileVo.getAdviserType());
				recommentUserOutVo.setAvatar(userProfileVo.getAvatar());
			}
		}
		PageInfo<RecommentUserOutVo> pageInfo = new PageInfo<RecommentUserOutVo>(list);
		return new jqGridResponseVo<RecommentUserOutVo>(pageInfo.getPages(), pageInfo.getList(), pageNum,
				pageInfo.getTotal());
	}

	public PushMessageUserOutVo findByUserId(Integer userId) {
		return userInfoMapper.findByUserId(userId);
	}

	public UserProfileOutVo getUserProfile(Integer userId) {
		UserProfileOutVo profileOutVo = userInfoMapper.getUserProfile(userId);
		return profileOutVo;
	}

	public List<TagOutVo> findTag(Integer userId) {
		List<TagOutVo> tagOutVos = userInfoMapper.findTag(userId);
		return tagOutVos;
	}

	public UserProfileVo findUserProfile(Integer userId) throws Exception {
		UserProfileVo userProfileVo = new UserProfileVo();
		Jedis jedis = jedisPool.getResource();
		String json = jedis.get(String.valueOf(userId));
		jedis.close();
		TokenOutVo tokenOutVo = (TokenOutVo) JacksonUtil.jsonToBean(json, TokenOutVo.class);
		if(null != tokenOutVo){
			int type = tokenOutVo.getType();
			Integer adviserType = tokenOutVo.getAdviserType();
			String userName = tokenOutVo.getResult().getUserName();
			String name = tokenOutVo.getName();
			String avatar = tokenOutVo.getPortraitUri();
			if(type == Constants.USER_TYPE_INVESTMENT){
				userProfileVo.setAdviserType(adviserType);
				userProfileVo.setUserName(name);
				userProfileVo.setAvatar(avatar);
			}else{
				userProfileVo.setUserName(userName);
				userProfileVo.setAvatar(avatar);
			}
		}
//		UserProfileVo userProfileVo = userInfoMapper.findUserProfile(userId);
		return userProfileVo;
	}

	public UserGroupOutVo getUserGroup(int iaUserId, int orderId) {
		UserGroupOutVo userGroupOutVo = userInfoMapper.getUserGroup(iaUserId, orderId);
		return userGroupOutVo;
	}

	public List<UserInfo> findRecommendNiuers() {
		return userInfoMapper.getRecommendUsers(Constants.IS_RECOMMEND_Y);
	}

	public jqGridResponseVo<UserInfo> findAdvisers(DicTypeInVo dicTypeInVo, Integer adviserType) {
		int pageNum = dicTypeInVo.getPageNum();
		int pageSize = dicTypeInVo.getPageSize();
		if (0 == pageNum) {
			pageNum = 1;
		}
		if (0 == pageSize) {
			pageSize = 10;
		}

		Integer typeId = dicTypeInVo.getTypeId();
		PageHelper.startPage(pageNum, pageSize);
		List<UserInfo> userInfoList = userInfoMapper.getAdvisers(typeId);
		PageInfo<UserInfo> pageInfo = new PageInfo<UserInfo>(userInfoList);
		return new jqGridResponseVo<UserInfo>(pageInfo.getPages(), pageInfo.getList(), pageNum, pageInfo.getTotal());
	}

	public jqGridResponseVo<UserInfo> findNewers(DicTypeInVo dicTypeInVo) {
		int pageNum = dicTypeInVo.getPageNum();
		int pageSize = dicTypeInVo.getPageSize();
		if (0 == pageNum) {
			pageNum = 1;
		}
		if (0 == pageSize) {
			pageSize = 10;
		}

		PageHelper.startPage(pageNum, pageSize);
		List<UserInfo> userInfoList = userInfoMapper.getNewers();
		PageInfo<UserInfo> pageInfo = new PageInfo<UserInfo>(userInfoList);
		return new jqGridResponseVo<UserInfo>(3, pageInfo.getList(), pageNum, pageInfo.getTotal());
	}

	public UserInfo findUserInfo(Integer userId) throws Exception {
		UserInfo userInfo = userInfoMapper.findUserInfo(userId);
		UserProfileVo userProfileVo = findUserProfile(userId);
		if(null != userInfo){
			if (null != userProfileVo) {
				userInfo.setUserName(userProfileVo.getUserName());
				userInfo.setAdviserType(userProfileVo.getAdviserType());
				userInfo.setAvatar(userProfileVo.getAvatar());
			}
			List<TagOutVo> tagOutVos = findTag(userId);
			userInfo.setTagVos(tagOutVos);
		}
		return userInfo;
	}

	public BaseOutVo getToken(UserInVo userInVo) throws Exception {
		BaseOutVo baseOutVo = new BaseOutVo();
		String userIdStr = userInVo.getUserId();
		String name = userInVo.getName();
		String portraitUri = userInVo.getPortraitUri();
		
		// 检查参数
		if (null == userIdStr || "".equals(userIdStr.trim())) {
			baseOutVo.setResultCode(UpChinaError.USERID_NULL_ERROR.code);
			baseOutVo.setResultMsg(UpChinaError.USERID_NULL_ERROR.message);
			return baseOutVo;
		} else if (null == name || "".equals(name.trim())) {
			baseOutVo.setResultCode(UpChinaError.USERNAME_NULL_ERROR.code);
			baseOutVo.setResultMsg(UpChinaError.USERNAME_NULL_ERROR.message);
			return baseOutVo;
		}
		String key = ImagePathUtil.getKey();
		String secret = ImagePathUtil.getSecret();
		// 请求融云api拿token
		SdkHttpResult result = ApiHttpClient.getToken(key, secret, userIdStr, name, portraitUri, FormatType.json);
		TokenOutVo tokenVo = (TokenOutVo) JacksonUtil.jsonToBean(result.toString(), TokenOutVo.class);
		// 如果返回code为200表示请求成功
		if (tokenVo.getCode().equals("200")) {

			Integer userId = Integer.parseInt(userIdStr);
			UserInfo userInfo = selectByPrimaryKey(userId);
			Date now = new Date();
			if (null != userInfo) {
				userInfo.setUserId(userId);
				userInfo.setUserName(name);
				if (portraitUri != null && !"".equals(portraitUri) && !"null".equals(portraitUri)) {
					userInfo.setAvatar(portraitUri);
				}
				userInfo.setUpdateTime(now);
				userInfo.setToken(tokenVo.getResult().getToken());
				updateByPrimaryKey(userInfo);
			} else {
				userInfo = new UserInfo();
				userInfo.setUserId(userId);
				userInfo.setUserName(name);
				if (portraitUri != null && !"".equals(portraitUri) && !"null".equals(portraitUri)) {
					userInfo.setAvatar(portraitUri);
				}
				userInfo.setType(Constants.USER_TYPE_USER);
				userInfo.setCreateTime(now);
				userInfo.setUpdateTime(now);
				userInfo.setToken(tokenVo.getResult().getToken());
				insertSelective(userInfo);
			}
			int type = userInfo.getType();
			tokenVo.setType(userInfo.getType());
			tokenVo.getResult().setUserName(name);
			if (portraitUri != null && !"".equals(portraitUri) && !"null".equals(portraitUri)) {
				tokenVo.setPortraitUri(portraitUri);
			} else {
				tokenVo.setPortraitUri(userInfo.getAvatar());
			}
			if (Constants.USER_TYPE_INVESTMENT == type) {
				tokenVo.setFlag(true);
				UserProfile userProfile = userProfileService.findByUserId(userId);
				if (null != userProfile) {
					Integer adviserType = userProfile.getAdviserType();
					String realName = userProfile.getRealName();
					String nickName = userProfile.getNickName();
					String avatar = userProfile.getAvatar();
					if (adviserType == Constants.USER_PROFILE_TYPE_INVESTMENT) {
						tokenVo.setName(realName);
					} else {
						tokenVo.setName(nickName);
					}
					tokenVo.setAdviserType(adviserType);
					if(null != avatar && !"".equals(avatar)){
						tokenVo.setPortraitUri(ImagePathUtil.getUploadCetificateImgHost() + avatar);
					}
				}
			} else {
				Example example = new Example(UserTag.class);
				example.createCriteria().andEqualTo("userId", userId);
				List<UserTag> userTags = this.userTagService.selectByExample(example);
				if (null != userTags && userTags.size() > 0) {
					tokenVo.setFlag(true);
				} else {
					tokenVo.setFlag(false);
				}
			}

			baseOutVo.setResultCode(UpChinaError.SUCCESS.code);
			baseOutVo.setResultMsg(UpChinaError.SUCCESS.message);
			baseOutVo.setResultData(tokenVo);
			Jedis jedis = jedisPool.getResource();
			jedis.set(userIdStr, JacksonUtil.beanToJson(tokenVo));
			jedis.close();
			return baseOutVo;
		} else {
			baseOutVo.setResultCode(tokenVo.getCode());
			return baseOutVo;
		}
	}

	public void synchronization() throws Exception {
		Example example = new Example(UserInfo.class);
		List<UserInfo> userInfos = selectByExample(example);
		Jedis jedis = jedisPool.getResource();
		for (UserInfo userInfo : userInfos) {
			TokenOutVo tokenVo = new TokenOutVo();
			Integer userId = userInfo.getUserId();
			String portraitUri = userInfo.getAvatar();
			String userName = userInfo.getUserName();
			String token = userInfo.getToken();
			Integer type = userInfo.getType();
			ResultOutVo resultOutVo = new ResultOutVo();
			resultOutVo.setUserId(String.valueOf(userId));
			resultOutVo.setToken(token);
			resultOutVo.setUserName(userName);
			tokenVo.setPortraitUri(portraitUri);
			
			UserProfile userProfile = userProfileService.findByUserId(userId);
			if (null != userProfile) {
				Integer adviserType = userProfile.getAdviserType();
				String realName = userProfile.getRealName();
				String nickName = userProfile.getNickName();
				String avatar = userProfile.getAvatar();
				if (adviserType == Constants.USER_PROFILE_TYPE_INVESTMENT) {
					tokenVo.setName(realName);
				} else {
					tokenVo.setName(nickName);
				}
				tokenVo.setAdviserType(adviserType);
				if(null != avatar && !"".equals(avatar)){
					tokenVo.setPortraitUri(ImagePathUtil.getUploadCetificateImgHost() + avatar);
				}
			}
			example = new Example(UserTag.class);
			example.createCriteria().andEqualTo("userId", userId);
			List<UserTag> userTags = this.userTagService.selectByExample(example);
			if (null != userTags && userTags.size() > 0) {
				tokenVo.setFlag(true);
			} else {
				tokenVo.setFlag(false);
			}
			tokenVo.setResult(resultOutVo);
			tokenVo.setType(type);
			jedis.set(String.valueOf(userId), JacksonUtil.beanToJson(tokenVo));
		}
		jedis.close();
	}

	public void clear() {
		Example example = new Example(UserInfo.class);
		List<UserInfo> userInfos = selectByExample(example);
		Jedis jedis = jedisPool.getResource();
		for (UserInfo userInfo : userInfos) {
			Integer userId = userInfo.getUserId();
			jedis.del(String.valueOf(userId));
		}
		jedis.close();
	}
}