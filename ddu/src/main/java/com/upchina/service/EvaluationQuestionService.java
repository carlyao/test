package com.upchina.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.Constant;
import com.upchina.dao.EvaluationQuestionMapper;
import com.upchina.dao.NiuGroupMapper;
import com.upchina.dao.TagMapper;
import com.upchina.dao.UserInfoMapper;
import com.upchina.model.EvaluationQuestion;
import com.upchina.model.NiuGroup;
import com.upchina.model.UserInfo;
import com.upchina.util.Constants;
import com.upchina.util.ImagePathUtil;
import com.upchina.vo.rest.output.EvaluationAnswerOutVo;
import com.upchina.vo.rest.output.EvaluationQuestionListOutVo;
import com.upchina.vo.rest.output.EvaluationQuestionOutVo;
import com.upchina.vo.rest.output.EvaluationTagsVo;
import com.upchina.vo.rest.output.RecommendServiceOutVo;
/**
 * Created by codesmith on 2015
 */

@Service("evaluationQuestionService")
public class EvaluationQuestionService  extends BaseService<EvaluationQuestion, Integer>{
	
	@Autowired
	private EvaluationQuestionMapper evaluationQuestionMapper;
	
	@Autowired
	private TagMapper tagMapper;
	
	@Autowired
	private UserTagService userTagService;
	
	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@Autowired
	private NiuGroupMapper niuGroupMapper;
	
     /**
     * 判断字段是否存在（插入和修改时可用,修改本身时如判断字段未发生修改会忽略）
     * @param inputName 要判断的字段名
     * @param value 要判断的值
     * @param id 当前记录id
     * @return 是否存在
     */
    public boolean isExist(String inputName,String value,int id) {
        Example exp = new Example(EvaluationQuestion.class);
        Example.Criteria criteria = exp.createCriteria();
        criteria.andEqualTo(inputName, value);
        List<EvaluationQuestion> list=selectByExample(exp);
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

	/**查询评测的问题及答案
	 * @return
	 */
	public EvaluationQuestionListOutVo selectQuestions() {
		
		EvaluationQuestionListOutVo evaluationQuestionListOutVo = new EvaluationQuestionListOutVo();
		
		List<EvaluationQuestionOutVo> question = this.evaluationQuestionMapper.selectQuestion();
		for(EvaluationQuestionOutVo evaluationQuestionOutVo:question){
			List<EvaluationAnswerOutVo> answer = new ArrayList<EvaluationAnswerOutVo>();
			answer.addAll(this.evaluationQuestionMapper.selectAnswerById(evaluationQuestionOutVo.getId()));
			evaluationQuestionOutVo.setAnswer(answer);
		}
		
		evaluationQuestionListOutVo.setQuestion(question);
		
		return evaluationQuestionListOutVo;
	}

	/**
	 * 根据用户提交的评测答案，返回标签
	 * @param tagIds
	 * @return
	 */
	public List<EvaluationTagsVo> getEvaluationResult(String tagIds) {
		
		String[] tagIdsTmp = tagIds.split(",");
		List<EvaluationTagsVo> evaluationTags = new ArrayList<EvaluationTagsVo>();
		for(String s:tagIdsTmp){
			EvaluationTagsVo evaluationTag = new EvaluationTagsVo();
			evaluationTag = this.tagMapper.getEvaluationResult(Integer.parseInt(s));
			evaluationTags.add(evaluationTag);
		}
		return evaluationTags;
		
	}
	
	/**
	 * 根据用户所在区域和用户关注的标签返回投顾和牛圈
	 * @param userId
	 * @return
	 */
	public RecommendServiceOutVo recommendService(Integer userId) {
		
		/**TODO:地区相关查询暂时没有..
		 * 
		 * 根据userID查出用户的标签、区域，通过标签、区域查出有此标签、区域的投顾，此为符合条件的投顾
		 * 
		 * 根据userID查出用户的标签、区域，通过标签、区域查出由此标签、区域的牛圈，此为符合条件的牛圈
		 * 
		 */
		
		RecommendServiceOutVo recommendServiceOutVo = new RecommendServiceOutVo();
		
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		List<NiuGroup> niuGroups = new ArrayList<NiuGroup>();
		
		userInfos = this.userInfoMapper.selectUserInfos(userId,Constants.USER_TYPE_INVESTMENT);
		niuGroups = this.niuGroupMapper.selectNiuGroups(userId,Constants.STATUS_TOP,Constants.STATUS_NORMAL);
		for (NiuGroup niuGroup : niuGroups) {
			niuGroup.setImg(ImagePathUtil.getImgHost()+niuGroup.getImg());
		}
		recommendServiceOutVo.setUserInfos(userInfos);
		recommendServiceOutVo.setNiuGroups(niuGroups);
		
		return recommendServiceOutVo;
	}

}