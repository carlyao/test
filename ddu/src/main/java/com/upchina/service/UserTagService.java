package com.upchina.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.upchina.dao.UserTagMapper;
import com.upchina.model.UserTag;
/**
 * Created by codesmith on 2015
 */

@Service("userTagService")
public class UserTagService  extends BaseService<UserTag, Integer>{
	
	@Autowired
	private UserTagMapper userTagMapper;
	
     /**
     * 判断字段是否存在（插入和修改时可用,修改本身时如判断字段未发生修改会忽略）
     * @param inputName 要判断的字段名
     * @param value 要判断的值
     * @param id 当前记录id
     * @return 是否存在
     */
    public boolean isExist(String inputName,String value,int id) {
        Example exp = new Example(UserTag.class);
        Example.Criteria criteria = exp.createCriteria();
        criteria.andEqualTo(inputName, value);
        List<UserTag> list=selectByExample(exp);
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
	 * 保存评测的答案和标签
	 * @param userTag
	 * @return
	 */
	public Integer addAnswer(UserTag userTag) {
		int flag = this.insert(userTag);
		return flag;
	}


	/**
	 * @param userId
	 */
	public void delByUserId(Integer userId) {
		this.userTagMapper.delByUserId(userId);
	}

	/**
	 * 根据用户的userId查询用户的标签
	 * @param userId
	 * @return
	 */
	public List<UserTag> getTagByUserId(Integer userId) {
		Example example = new Example(UserTag.class);
		example.createCriteria().andEqualTo("userId", userId);
		List<UserTag> userTags = selectByExample(example);
		return userTags;
	}

	/**
	 * 根据组合ID，查询组合适用类型
	 * @param userId
	 * @return
	 */
	public String getPortfolioMoneyTagById(Integer id) {
		String userType = this.userTagMapper.getPortfolioMoneyTagById(id);
		return userType;
	}

	
}