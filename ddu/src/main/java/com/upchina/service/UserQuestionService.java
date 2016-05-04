package com.upchina.service;

import com.upchina.model.UserDirectQuestion;
import com.upchina.model.UserQuestion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
/**
 * Created by codesmith on 2015
 */

@Service("userQuestionService")
public class UserQuestionService  extends BaseService<UserQuestion, Integer>{
	
	 @Autowired
	 private UserDirectQuestionService userDirectQuestionService;
	
     /**
     * 判断字段是否存在（插入和修改时可用,修改本身时如判断字段未发生修改会忽略）
     * @param inputName 要判断的字段名
     * @param value 要判断的值
     * @param id 当前记录id
     * @return 是否存在
     */
    public boolean isExist(String inputName,String value,int id) {
        Example exp = new Example(UserQuestion.class);
        Example.Criteria criteria = exp.createCriteria();
        criteria.andEqualTo(inputName, value);
        List<UserQuestion> list=selectByExample(exp);
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

	public void insertExtend(UserQuestion userQuestion, String userIdDirect) {
		if(!StringUtils.isEmpty(userIdDirect)){
			insert(userQuestion);
			String[] userIds = userIdDirect.split(",");
			for (int i = 0; i < userIds.length; i++) {
				UserDirectQuestion UserDirectQuestion=new UserDirectQuestion();
				UserDirectQuestion.setQuestionId(userQuestion.getId());
				UserDirectQuestion.setUserId(Integer.parseInt(userIds[i]));
				UserDirectQuestion.setCreateTime(new Date());
				userDirectQuestionService.insert(UserDirectQuestion);
			}
		}
	}
}