package com.upchina.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import tk.mybatis.mapper.common.Mapper;

import com.upchina.model.EvaluationQuestion;
import com.upchina.vo.rest.output.EvaluationAnswerOutVo;
import com.upchina.vo.rest.output.EvaluationQuestionOutVo;
/**
 * Created by codesmith on 2015
 */
public interface EvaluationQuestionMapper extends Mapper<EvaluationQuestion> {

	/**
	 * 根据问题的id查询出问题
	 * @param id
	 * @return
	 */
	@Select("select e.id,e.Question,e.status,e.Index_ as 'index' from evaluation_question e order by e.Index_ asc")
	List<EvaluationQuestionOutVo> selectQuestion();

	/**
	 * 根据问题的id查询出答案
	 * @param i
	 * @return
	 */
	@Select("select e.Id,e.Tag_Id as tagId,t.`Name` as tag,e.Answer,e.index_ as 'index' from evaluation_answer e,tag t where e.Question_Id=#{id} and e.Tag_Id=t.Id order by e.Index_ asc")
	List<EvaluationAnswerOutVo> selectAnswerById(@Param("id")Integer id);

}