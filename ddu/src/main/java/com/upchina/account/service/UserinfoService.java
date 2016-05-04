package com.upchina.account.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.upchina.account.model.UserProfile;
import com.upchina.service.BaseService;
import com.upchina.util.SqlMapper;
/**
 * Created by codesmith on 2015
 */

@Service("userinfoService")
public class UserinfoService  extends BaseService<UserProfile, Integer>{
     /**
     * 判断字段是否存在（插入和修改时可用,修改本身时如判断字段未发生修改会忽略）
     * @param inputName 要判断的字段名
     * @param value 要判断的值
     * @param id 当前记录id
     * @return 是否存在
     */
	@Autowired
	private SqlMapper accountSqlMapper;
	
    public boolean isExist(String inputName,String value,int id) {
        Example exp = new Example(UserProfile.class);
        Example.Criteria criteria = exp.createCriteria();
        criteria.andEqualTo(inputName, value);
        List<UserProfile> list=selectByExample(exp);
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

	public List<Map<String, Object>> selectByUserId(String userId) {
		String sql = "select USERNAME from userinfo where USERCODE = "+ userId;
		List<Map<String,Object>>  list =accountSqlMapper.selectList(sql);
		return list;
	}
	

	public Integer getUserCode(Integer portfolioId) {
		Integer userCode =null;
		Example userExample=new Example(UserProfile.class);
		userExample.createCriteria().andEqualTo("username", portfolioId);
		List<UserProfile> user = selectByExample(userExample);
		if(user!=null&&user.size()>0){
			userCode = user.get(0).getUsercode();
		}
		return userCode;
	}
	
}