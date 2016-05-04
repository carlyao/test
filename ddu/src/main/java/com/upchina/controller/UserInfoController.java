package com.upchina.controller;

import io.rong.models.FormatType;
import io.rong.models.SdkHttpResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tk.mybatis.mapper.entity.Example;

import com.upchina.Exception.UpChinaError;
import com.upchina.api.rongYun.RongYunApi;
import com.upchina.auth.EncryptParam;
import com.upchina.model.UserGroup;
import com.upchina.model.UserInfo;
import com.upchina.model.UserProfile;
import com.upchina.service.DictionaryService;
import com.upchina.service.LiveService;
import com.upchina.service.TagService;
import com.upchina.service.UserFriendService;
import com.upchina.service.UserGroupService;
import com.upchina.service.UserInfoService;
import com.upchina.service.UserOrderService;
import com.upchina.service.UserProfileService;
import com.upchina.service.UserTagService;
import com.upchina.util.Constants;
import com.upchina.util.Criterias;
import com.upchina.util.ImagePathUtil;
import com.upchina.util.LoginUtil;
import com.upchina.vo.BaseOutVo;
import com.upchina.vo.PageRequestVo;
import com.upchina.vo.jqGridResponseVo;
import com.upchina.vo.rest.CurrentUserInfoVo;
import com.upchina.vo.rest.UserVo;
import com.upchina.vo.rest.input.DicTypeInVo;
import com.upchina.vo.rest.input.InvestmentDetailInVo;
import com.upchina.vo.rest.input.RecommentUserInVo;
import com.upchina.vo.rest.input.TagInVo;
import com.upchina.vo.rest.input.UserInVo;
import com.upchina.vo.rest.input.UserInfoInVo;
import com.upchina.vo.rest.input.UserInfoOrderInVo;
import com.upchina.vo.rest.output.DictionaryVo;
import com.upchina.vo.rest.output.InvestmentDetailOutVo;
import com.upchina.vo.rest.output.LiveOutVo;
import com.upchina.vo.rest.output.RecommentUserOutVo;
import com.upchina.vo.rest.output.TagOutVo;
import com.upchina.vo.rest.output.UserAdviserOutVo;
import com.upchina.vo.rest.output.UserInfoOrderOutVo;
import com.upchina.vo.rest.output.UserOrderOutVo;
import com.upchina.vo.rest.output.UserProfileOutVo;
import com.upchina.vo.rest.output.UserProfileVo;

/**
 * Created by Administrator on 2015-12-11 15:08:32
 */
@Controller
@RequestMapping("/userInfo")
public class UserInfoController extends BaseController {

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private TagService tagService;

	@Autowired
	private UserTagService userTagService;

	@Autowired
	private UserFriendService userFriendService;

	@Autowired
	private UserOrderService userOrderService;

	@Autowired
	private LiveService liveService;

	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private UserGroupService userGroupService;

	/**
	 * 通过标签搜索非好友的投顾(如果标签为空，则查询出所有非好友的投顾)
	 */
	@ResponseBody
	@RequestMapping(value = "getListInvestmentAdvisorByTags")
	public Object getListInvestmentAdvisorByTags(TagInVo tagInVo, HttpServletRequest request) {

		BaseOutVo res = new BaseOutVo();
		try {
			if (tagInVo.getUserId() == null || "".equals(tagInVo.getUserId())) {
				// res.setResultMsg("用户id为空！");
				res.setResultMsg(UpChinaError.USERID_NULL_ERROR.message);
				res.setResultCode(UpChinaError.USERID_NULL_ERROR.code);
			} else {
				if (tagInVo.getTagIds() == null || "".equals(tagInVo.getTagIds())) {
					jqGridResponseVo<UserInfo> pageList = this.userInfoService.getListInvestmentAdvisorAndTags(tagInVo);
					List<UserInfo> userInfos = pageList.getRows();
					for (UserInfo userInfoTmp : userInfos) {
						List<TagOutVo> tagVos = new ArrayList<TagOutVo>();
						Integer userId = userInfoTmp.getUserId();
//						Example example = new Example(UserTag.class);
//						example.createCriteria().andEqualTo("userId", userId);
//						List<UserTag> userTags = this.userTagService.selectByExample(example);
//
//						List<Integer> tagIds = new ArrayList<Integer>();
//						for (UserTag userTag : userTags) {
//							tagIds.add(userTag.getTagId());
//						}
						tagVos = userInfoService.findTag(userId);
						userInfoTmp.setTagVos(tagVos);
						UserProfileVo userProfileVo = userInfoService.findUserProfile(userId);
						if (null != userProfileVo) {
							userInfoTmp.setUserName(userProfileVo.getUserName());
							userInfoTmp.setAdviserType(userProfileVo.getAdviserType());
							userInfoTmp.setAvatar(userProfileVo.getAvatar());
						}
					}
					res.setResultCode(UpChinaError.SUCCESS.code);
					res.setResultData(pageList);
					res.setResultMsg(UpChinaError.SUCCESS.message);
					// res.setResultMsg("所有非好友投顾信息");
				} else {
					jqGridResponseVo<UserInfo> pageList = this.userInfoService.getListInvestmentAdvisorByTags(tagInVo);
					List<UserInfo> userInfos = pageList.getRows();
					for (UserInfo userInfoTmp : userInfos) {
						List<TagOutVo> tagVos = new ArrayList<TagOutVo>();
						Integer userId = userInfoTmp.getUserId();
//						Example example = new Example(UserTag.class);
//						example.createCriteria().andEqualTo("userId", userId);
//						List<UserTag> userTags = this.userTagService.selectByExample(example);
//						for (UserTag userTag : userTags) {
//							TagOutVo tagVo = new TagOutVo();
//							Integer tagId = userTag.getTagId();
//							Tag tag = this.tagService.selectByPrimaryKey(tagId);
//							tagVo.setTagId(tag.getId());
//							tagVo.setTagName(tag.getName());
//							tagVos.add(tagVo);
//						}
						tagVos = userInfoService.findTag(userId);
						userInfoTmp.setTagVos(tagVos);
						UserProfileVo userProfileVo = userInfoService.findUserProfile(userId);
						if (null != userProfileVo) {
							userInfoTmp.setUserName(userProfileVo.getUserName());
							userInfoTmp.setAdviserType(userProfileVo.getAdviserType());
							userInfoTmp.setAvatar(userProfileVo.getAvatar());
						}
					}
					res.setResultCode(UpChinaError.SUCCESS.code);
					res.setResultData(pageList);
					res.setResultMsg(UpChinaError.SUCCESS.message);
					// res.setResultMsg("根据标签查询的非好友投顾信息");
				}
			}
		} catch (Exception e) {
			res.setResultCode(UpChinaError.ERROR.code);
			res.setResultMsg(UpChinaError.ERROR.message);
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * UserInfo列表
	 **/
	@RequestMapping(value = "list")
	public String list(HttpServletRequest request) {
		return "userInfo/list";
	}

	@ResponseBody
	@RequestMapping(value = "getList")
	public Object getList(HttpServletRequest request) {
		PageRequestVo page = Criterias.buildCriteria(UserInfo.class, request);
		jqGridResponseVo<UserInfo> pageList = userInfoService.pageJqGrid(page);
		return pageList;
	}

	/**
	 * 查询类型是投顾的用户
	 * 
	 * @param request
	 * @return
	 */
	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "getListInvestmentAdvisor") public Object
	 * getListInvestmentAdvisor(PageVo pageVo,HttpServletRequest request) {
	 * jqGridResponseVo<UserInfo> pageList =
	 * this.userInfoService.getListInvestmentAdvisorAndTags(pageVo);
	 * List<UserInfo> userInfos = pageList.getRows(); for(UserInfo
	 * userInfoTmp:userInfos){ List<TagVo> tagVos = new ArrayList<TagVo>();
	 * Integer userId = userInfoTmp.getUserId(); Example example = new
	 * Example(UserTag.class); example.createCriteria().andEqualTo("userId",
	 * userId); List<UserTag> userTags =
	 * this.userTagService.selectByExample(example); for(UserTag
	 * userTag:userTags){ TagVo tagVo = new TagVo(); Integer tagId =
	 * userTag.getTagId(); Tag tag = this.tagService.selectByPrimaryKey(tagId);
	 * tagVo.setTagId(tag.getId()); tagVo.setTagName(tag.getName());
	 * tagVos.add(tagVo); } userInfoTmp.setTagVos(tagVos); } return pageList; }
	 */

	/**
	 * 根据投顾的id查询投顾的信息
	 *
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getInvestmentAdvisorInfo")
	public Object getInvestmentAdvisorInfo(InvestmentDetailInVo investmentUserVo, HttpServletRequest request) {
		BaseOutVo res = new BaseOutVo();
		try {
			Integer userId = investmentUserVo.getUserId();
			if (userId == null || 0 == userId) {
				res.setResultMsg(UpChinaError.USERID_NULL_ERROR.message);
				res.setResultCode(UpChinaError.USERID_NULL_ERROR.code);
				return res;
			}
			if (investmentUserVo.getCurrUserId() == null || "".equals(investmentUserVo.getCurrUserId())) {
				res.setResultMsg(UpChinaError.CURRENT_USERID_NULL_ERROR.message);
				res.setResultCode(UpChinaError.CURRENT_USERID_NULL_ERROR.code);
				return res;
			}
			InvestmentDetailOutVo investmentDetailOutVo = this.userInfoService
					.getInvestmentAdvisorAndTags(investmentUserVo);
			List<TagOutVo> tagVos = new ArrayList<TagOutVo>();
			tagVos = tagService.selectTagByUserId(userId);
			investmentDetailOutVo.setTagVos(tagVos);

			res.setResultCode(UpChinaError.SUCCESS.code);
			res.setResultMsg(UpChinaError.SUCCESS.message);
			res.setResultData(investmentDetailOutVo);
		} catch (Exception e) {
			e.printStackTrace();
			res.setResultCode(UpChinaError.ERROR.code);
			res.setResultMsg(UpChinaError.ERROR.message);
		}
		return res;
	}

	/**
	 * UserInfo添加和编辑
	 **/
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result = new ModelAndView("userInfo/edit");
		return result;
	}

	/**
	 * UserInfo添加和编辑
	 **/
	@ResponseBody
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public Object edit(UserInfo userInfo, HttpServletRequest request) {
		String op = request.getParameter("oper");
		BaseOutVo res = new BaseOutVo();
		try {
			if (op.equals("add")) {
				userInfoService.insert(userInfo);
				res.setResultMsg(UpChinaError.SUCCESS.message);
			} else {
				userInfoService.updateByPrimaryKeySelective(userInfo);
				res.setResultMsg(UpChinaError.SUCCESS.message);
			}
			res.setResultCode(UpChinaError.SUCCESS.code);
		} catch (Exception e) {
			e.printStackTrace();
			res.setResultCode(UpChinaError.ERROR.code);
			res.setResultMsg(UpChinaError.ERROR.message);
		}
		return res;
	}

	/**
	 * UserInfo删除
	 **/
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public Object delete(HttpServletRequest request) {
		BaseOutVo res = new BaseOutVo();
		try {
			String idStr = request.getParameter("userId");
			String[] ids = idStr.split(",");
			List list = new ArrayList();
			list = Arrays.asList(ids);
			Example exp = new Example(UserInfo.class);
			exp.createCriteria().andIn("userId", list);
			userInfoService.deleteByExample(exp);
			res.setResultCode(UpChinaError.SUCCESS.code);
			res.setResultMsg(UpChinaError.SUCCESS.message);
		} catch (Exception e) {
			e.printStackTrace();
			res.setResultCode(UpChinaError.ERROR.code);
			res.setResultMsg(UpChinaError.ERROR.message);
		}
		return res;
	}

	/**
	 * 判断UserInfo是否存在
	 **/
	@ResponseBody
	@RequestMapping(value = "isExist", method = RequestMethod.POST)
	public Object isExistKey(HttpServletRequest request) {
		boolean isOk = false;
		try {
			String inputName = request.getParameter("inputName");
			String name = request.getParameter(inputName);
			int id = Integer.parseInt(request.getParameter("id"));
			isOk = !userInfoService.isExist(inputName, name, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isOk ? "{\"ok\":\"\"}" : "{\"error\":\"当前UserInfo已存在\"}";
	}

	/**
	 * 调用融云生成token,并保存token到数据库
	 *
	 * @param userInVo
	 *            (userId必传,name 用户名字必传,portraitUri 用户头像必传)
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getToken", method = RequestMethod.POST)
	@EncryptParam(paramName = "userInVo", paramClass = UserInVo.class)
	public Object getToken(UserInVo userInVo, HttpServletRequest request) {
		BaseOutVo baseOutVo = new BaseOutVo();
		try {
			baseOutVo = userInfoService.getToken(userInVo);
		} catch (Exception e) {
			baseOutVo.setResultCode(UpChinaError.ERROR.code);
			baseOutVo.setResultMsg(UpChinaError.ERROR.message);
			e.printStackTrace();
		}
		return baseOutVo;
	}

	@ResponseBody
	@RequestMapping(value = "getCurrentUserInfo", method = RequestMethod.POST)
	public Object getCurrentUserInfo(UserVo userVo, HttpServletRequest request) {
		BaseOutVo baseOutVo = new BaseOutVo();
		try {
			CurrentUserInfoVo currentUserInfoVo = LoginUtil.getCurrentUserInfo(userVo);
			Integer userId = currentUserInfoVo.getUserId();
			UserInfo userInfo = userInfoService.selectByPrimaryKey(userId);
			if (null != userInfo) {
				currentUserInfoVo.setType(userInfo.getType());
			}
			baseOutVo.setResultCode(UpChinaError.SUCCESS.code);
			baseOutVo.setResultData(currentUserInfoVo);
			baseOutVo.setResultMsg(UpChinaError.SUCCESS.message);
		} catch (Exception e) {
			baseOutVo.setResultCode(UpChinaError.ERROR.code);
			baseOutVo.setResultMsg(UpChinaError.ERROR.message);
			e.printStackTrace();
		}
		return baseOutVo;
	}

	// 首页投顾推荐
	@ResponseBody
	@RequestMapping(value = "getRecommentUserInfo", method = RequestMethod.GET)
	public Object getRecommentUserInfo(RecommentUserInVo recommentUserInVo, HttpServletRequest request) {
		BaseOutVo baseOutVo = new BaseOutVo();
		try {
			jqGridResponseVo<RecommentUserOutVo> pageList = userInfoService.getRecommentUserInfo(recommentUserInVo);
			baseOutVo.setResultData(pageList);
			baseOutVo.setResultCode(UpChinaError.SUCCESS.code);
			baseOutVo.setResultMsg(UpChinaError.SUCCESS.message);
		} catch (Exception e) {
			baseOutVo.setResultCode(UpChinaError.ERROR.code);
			baseOutVo.setResultMsg(UpChinaError.ERROR.message);
			e.printStackTrace();
		}
		return baseOutVo;
	}

	// 投顾直播室
	@ResponseBody
	@RequestMapping(value = "live", method = RequestMethod.GET)
	public Object getUserLive(UserInfoInVo userInfoInVo, HttpServletRequest request) {
		BaseOutVo baseOutVo = new BaseOutVo();
		try {
			if (null != userInfoInVo) {
				Integer userId = userInfoInVo.getUserId();
				if (null != userId && 0 != userId) {
					LiveOutVo liveOutVo = liveService.getUserLive(userId);
					baseOutVo.setResultCode(UpChinaError.SUCCESS.code);
					baseOutVo.setResultMsg(UpChinaError.SUCCESS.message);
					baseOutVo.setResultData(liveOutVo);
				}
			}
		} catch (Exception e) {
			baseOutVo.setResultCode(UpChinaError.ERROR.code);
			baseOutVo.setResultMsg(UpChinaError.ERROR.message);
			e.printStackTrace();
		}
		return baseOutVo;
	}

	// 投顾个人信息
	@ResponseBody
	@RequestMapping(value = "profile", method = RequestMethod.GET)
	public Object getUserProfile(UserInfoInVo userInfoInVo, HttpServletRequest request) {
		BaseOutVo baseOutVo = new BaseOutVo();
		try {
			if (null != userInfoInVo) {
				Integer userId = userInfoInVo.getUserId();
				if (null != userId && 0 != userId) {
					UserProfileOutVo profileOutVo = userInfoService.getUserProfile(userId);
					baseOutVo.setResultCode(UpChinaError.SUCCESS.code);
					baseOutVo.setResultMsg(UpChinaError.SUCCESS.message);
					baseOutVo.setResultData(profileOutVo);
				}
			}
		} catch (Exception e) {
			baseOutVo.setResultCode(UpChinaError.ERROR.code);
			baseOutVo.setResultMsg(UpChinaError.ERROR.message);
			e.printStackTrace();
		}
		return baseOutVo;
	}

	@ResponseBody
	@RequestMapping(value = "order", method = RequestMethod.POST)
	@EncryptParam(paramName = "userInfoOrderInVo", paramClass = UserInfoOrderInVo.class)
	public Object getUserOrder(UserInfoOrderInVo userInfoOrderInVo, HttpServletRequest request) {
		BaseOutVo baseOutVo = new BaseOutVo();
		try {
			if (null != userInfoOrderInVo) {
				Integer userId = userInfoOrderInVo.getUserId();
				if (null != userId && 0 != userId) {
					jqGridResponseVo<UserInfoOrderOutVo> pageList = userOrderService.getUserOrder(userInfoOrderInVo);
					baseOutVo.setResultData(pageList);
					baseOutVo.setResultCode(UpChinaError.SUCCESS.code);
					baseOutVo.setResultMsg(UpChinaError.SUCCESS.message);
				}
			}
		} catch (Exception e) {
			baseOutVo.setResultCode(UpChinaError.ERROR.code);
			baseOutVo.setResultMsg(UpChinaError.ERROR.message);
			e.printStackTrace();
		}
		return baseOutVo;
	}
	
	@ResponseBody
	@RequestMapping(value = "orderCount", method = RequestMethod.POST)
	public Object getUserOrderCount(UserInfoOrderInVo userInfoOrderInVo, HttpServletRequest request) {
		BaseOutVo baseOutVo = new BaseOutVo();
		try {
			if (null != userInfoOrderInVo) {
				Integer userId = userInfoOrderInVo.getUserId();
				if (null != userId && 0 != userId) {
					int orderCount = userOrderService.getUserOrderCount(userInfoOrderInVo);
					baseOutVo.setResultData(orderCount);
					baseOutVo.setResultCode(UpChinaError.SUCCESS.code);
					baseOutVo.setResultMsg(UpChinaError.SUCCESS.message);
				}
			}
		} catch (Exception e) {
			baseOutVo.setResultCode(UpChinaError.ERROR.code);
			baseOutVo.setResultMsg(UpChinaError.ERROR.message);
			e.printStackTrace();
		}
		return baseOutVo;
	}

	@ResponseBody
	@RequestMapping(value = "service", method = RequestMethod.POST)
	@EncryptParam(paramName = "userInfoInVo", paramClass = UserInfoInVo.class)
	public Object getUserService(UserInfoInVo userInfoInVo, HttpServletRequest request) {
		BaseOutVo baseOutVo = new BaseOutVo();
		try {
			if (null != userInfoInVo) {
				Integer userId = userInfoInVo.getUserId();
				if (null != userId && 0 != userId) {
					jqGridResponseVo<UserInfoOrderOutVo> pageList = userOrderService.getUserService(userInfoInVo);
					baseOutVo.setResultData(pageList);
					baseOutVo.setResultCode(UpChinaError.SUCCESS.code);
					baseOutVo.setResultMsg(UpChinaError.SUCCESS.message);
				}
			}
		} catch (Exception e) {
			baseOutVo.setResultCode(UpChinaError.ERROR.code);
			baseOutVo.setResultMsg(UpChinaError.ERROR.message);
			e.printStackTrace();
		}
		return baseOutVo;
	}
	
	@ResponseBody
	@RequestMapping(value = "permission", method = RequestMethod.POST)
	@EncryptParam(paramName = "userInfoInVo", paramClass = UserInfoInVo.class)
	public Object getUserPermission(UserInfoInVo userInfoInVo, HttpServletRequest request) {
		BaseOutVo baseOutVo = new BaseOutVo();
		try {
			if (null != userInfoInVo) {
				Integer userId = userInfoInVo.getUserId();
				if (null != userId && 0 != userId) {
					List<UserOrderOutVo> userOrderOutVoList = userOrderService.getUserPermission(userInfoInVo.getUserId());
					
					baseOutVo.setResultData(userOrderOutVoList);
					baseOutVo.setResultCode(UpChinaError.SUCCESS.code);
					baseOutVo.setResultMsg(UpChinaError.SUCCESS.message);
				}
			}
		} catch (Exception e) {
			baseOutVo.setResultCode(UpChinaError.ERROR.code);
			baseOutVo.setResultMsg(UpChinaError.ERROR.message);
			e.printStackTrace();
		}
		return baseOutVo;
	}

	@ResponseBody
	@RequestMapping(value = "adviserContent", method = RequestMethod.POST)
	public Object getAdviserContent(UserInfoInVo userInfoInVo, HttpServletRequest request) {
		BaseOutVo baseOutVo = new BaseOutVo();
		try {
			if (null != userInfoInVo) {
				Integer userId = userInfoInVo.getUserId();
				if (null != userId && 0 != userId) {
					jqGridResponseVo<UserAdviserOutVo> userOrderOutVoList = userOrderService.getAdviserContent(userInfoInVo);
					baseOutVo.setResultData(userOrderOutVoList);
					baseOutVo.setResultCode(UpChinaError.SUCCESS.code);
					baseOutVo.setResultMsg(UpChinaError.SUCCESS.message);
				}
			}
		} catch (Exception e) {
			baseOutVo.setResultCode(UpChinaError.ERROR.code);
			baseOutVo.setResultMsg(UpChinaError.ERROR.message);
			e.printStackTrace();
		}
		return baseOutVo;
	}

	// 获取用户个人信息
	@ResponseBody
	@RequestMapping(value = "getUserInfo", method = RequestMethod.POST)
	public Object getUserInfo(HttpServletRequest request) {
		BaseOutVo baseOutVo = new BaseOutVo();
		String userId = request.getParameter("userId");
		try {
			if (StringUtils.isEmpty(userId)) {
				baseOutVo.setResultCode(UpChinaError.USERID_NULL_ERROR.code);
				baseOutVo.setResultMsg(UpChinaError.USERID_NULL_ERROR.message);
				return baseOutVo;
			}
			UserInfo userInfo = userInfoService.selectByPrimaryKey(Integer.valueOf(userId));
			baseOutVo.setResultCode(UpChinaError.SUCCESS.code);
			baseOutVo.setResultMsg(UpChinaError.SUCCESS.message);
			baseOutVo.setResultData(userInfo);
		} catch (Exception e) {
			baseOutVo.setResultCode(UpChinaError.ERROR.code);
			baseOutVo.setResultMsg(UpChinaError.ERROR.message);
			e.printStackTrace();
		}
		return baseOutVo;
	}

    @ResponseBody
    @RequestMapping(value = "queryRecommendNiuers")
    public Object queryRecommendNiuers() throws Exception {
    	BaseOutVo res = new BaseOutVo();
    	try{
    		List<UserInfo> recommendUsers = userInfoService.findRecommendNiuers();
    			List<UserInfo> users = new ArrayList<UserInfo>();
    			for (UserInfo userInfo : recommendUsers) {
    				UserProfileVo userProfileVo = userInfoService.findUserProfile(userInfo.getUserId());
    				if (userProfileVo != null) {
    					userInfo.setUserId(userInfo.getUserId());
    					userInfo.setUserName(userProfileVo.getUserName());
    					userInfo.setAvatar(userProfileVo.getAvatar());
    					userInfo.setAdviserType(userProfileVo.getAdviserType());
    				}
    				UserProfile userProfile = userProfileService.findByUserId(userInfo.getUserId());
    				if (userProfile != null) {
    					userInfo.setIntro(userProfile.getIntro());
    				}
    				users.add(userInfo);
    			}
    			res.setResultData(users);
    			res.setResultCode(UpChinaError.SUCCESS.code);
    			res.setResultMsg(UpChinaError.SUCCESS.message);
    	}catch(Exception e){
    		res.setResultCode(UpChinaError.ERROR.code);
    		res.setResultMsg(UpChinaError.ERROR.message);
    	}
        return res;
    }

	@ResponseBody
	@RequestMapping(value = "queryTypes")
	public Object queryTypes() {
		List<DictionaryVo> niuTypes = dictionaryService.getListBySystemNameAndModelName(
				Constants.SYSTEM_NAME_FOR_NIUER, Constants.MODEL_NAME_FOR_NIUER);
		BaseOutVo res = new BaseOutVo();
		res.setResultData(niuTypes);
		res.setResultCode(UpChinaError.SUCCESS.code);
		res.setResultMsg(UpChinaError.SUCCESS.message);
		return res;
	}

	@ResponseBody
	@RequestMapping(value = "queryByNiuerType")
	public Object queryByNiuerType(DicTypeInVo dicTypeInVo) throws Exception {
		jqGridResponseVo<UserInfo> pageList = null;
		if (dicTypeInVo.getTypeId() == 1) {
			pageList = userInfoService.findAdvisers(dicTypeInVo, Constants.USER_TYPE_INVESTMENT);
		} else if (dicTypeInVo.getTypeId() == 2) {
			pageList = userInfoService.findAdvisers(dicTypeInVo, Constants.USER_TYPE_USER);
		} else if (dicTypeInVo.getTypeId() == 3) {
			pageList = userInfoService.findNewers(dicTypeInVo);
		}
		List<UserInfo> userInfos = pageList.getRows();
		for (UserInfo userInfo : userInfos) {
			UserProfileVo userProfileVo = userInfoService.findUserProfile(userInfo.getUserId());
			if (userProfileVo != null) {
				userInfo.setUserName(userProfileVo.getUserName());
				userInfo.setAvatar(userProfileVo.getAvatar());
				userInfo.setAdviserType(userProfileVo.getAdviserType());
			}
		}
		BaseOutVo res = new BaseOutVo();
		res.setResultData(pageList);
		res.setResultCode(UpChinaError.SUCCESS.code);
		res.setResultMsg(UpChinaError.SUCCESS.message);
		return res;
	}

	/**
	 * 禁止用户发言
	 *
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "noSaying", method = RequestMethod.POST)
	public Object noSaying(HttpServletRequest request) {
		BaseOutVo baseOutVo = new BaseOutVo();
		String userId = request.getParameter("userId");
		String groupId = request.getParameter("groupId");
		try {
			if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(groupId)) {
				baseOutVo.setResultCode(UpChinaError.ERROR.code);
				baseOutVo.setResultMsg(UpChinaError.ERROR.message);
				return baseOutVo;
			}
			String key = ImagePathUtil.getKey();
			String minute = ImagePathUtil.getNoSayingTime();// 默认禁言10分钟
			String secret = ImagePathUtil.getSecret();
			SdkHttpResult result = RongYunApi.noSaying(key, secret, userId, groupId, minute, FormatType.json);
			if (200 == result.getHttpCode()) {
				Example example = new Example(UserGroup.class);
				example.createCriteria().andEqualTo("userId", userId).andEqualTo("groupId", groupId);
				List<UserGroup> list = userGroupService.selectByExample(example);
				if (list != null && !list.isEmpty()) {
					UserGroup userGroup = list.get(0);
					userGroup.setStatus(3);// status为3表示禁言
					userGroupService.userNoSaying(userGroup);
				}
				baseOutVo.setResultCode(UpChinaError.SUCCESS.code);
				baseOutVo.setResultMsg(UpChinaError.SUCCESS.message);
				return baseOutVo;
			}
		} catch (Exception e) {
			baseOutVo.setResultCode(UpChinaError.ERROR.code);
			baseOutVo.setResultMsg(UpChinaError.ERROR.message);
			e.printStackTrace();
		}
		return baseOutVo;
	}

	/**
	 * 退出群组
	 *
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "quitGroup", method = RequestMethod.POST)
	public Object quitGroup(HttpServletRequest request) {
		BaseOutVo baseOutVo = new BaseOutVo();
		String userId = request.getParameter("userId");
		String groupId = request.getParameter("groupId");
		try {
			if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(groupId)) {
				baseOutVo.setResultCode(UpChinaError.ERROR.code);
				baseOutVo.setResultMsg(UpChinaError.ERROR.message);
				return baseOutVo;
			}
			String key = ImagePathUtil.getKey();
			String secret = ImagePathUtil.getSecret();
			SdkHttpResult result = RongYunApi.quitGroup(key, secret, userId, groupId, FormatType.json);
			if (200 == result.getHttpCode()) {
				baseOutVo.setResultCode(UpChinaError.SUCCESS.code);
				baseOutVo.setResultMsg(UpChinaError.SUCCESS.message);
				/**
				 * 从该组中将好友状态置为退出，同时删除该用户被禁言的记录(如果该记录存在的话)
				 */
				Example example = new Example(UserGroup.class);
				example.createCriteria().andEqualTo("userId", userId).andEqualTo("groupId", groupId);
				List<UserGroup> list = userGroupService.selectByExample(example);
				if (list != null && !list.isEmpty()) {
					UserGroup userGroup = list.get(0);
					userGroup.setStatus(2);
					userGroupService.userQuitGroup(userGroup);
				}
				return baseOutVo;
			}
		} catch (Exception e) {
			baseOutVo.setResultCode(UpChinaError.ERROR.code);
			baseOutVo.setResultMsg(UpChinaError.ERROR.message);
			e.printStackTrace();
		}
		return baseOutVo;
	}

	@ResponseBody
	@RequestMapping(value = "synchronization", method = RequestMethod.POST)
	public Object synchronization(HttpServletRequest request) {
		BaseOutVo baseOutVo = new BaseOutVo();
		try {
			userInfoService.synchronization();
		} catch (Exception e) {
			baseOutVo.setResultCode(UpChinaError.ERROR.code);
			baseOutVo.setResultMsg(UpChinaError.ERROR.message);
			e.printStackTrace();
		}
		return baseOutVo;
	}
	
	@ResponseBody
	@RequestMapping(value = "clear", method = RequestMethod.POST)
	public Object clear(HttpServletRequest request) {
		BaseOutVo baseOutVo = new BaseOutVo();
		try {
			userInfoService.clear();
		} catch (Exception e) {
			baseOutVo.setResultCode(UpChinaError.ERROR.code);
			baseOutVo.setResultMsg(UpChinaError.ERROR.message);
			e.printStackTrace();
		}
		return baseOutVo;
	}


}
