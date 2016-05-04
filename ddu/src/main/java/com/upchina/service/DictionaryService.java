package com.upchina.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.upchina.dao.DictionaryMapper;
import com.upchina.model.Dictionary;
import com.upchina.util.Constants;
import com.upchina.vo.jqGridResponseVo;
import com.upchina.vo.rest.output.DictionaryVo;
/**
 * Created by codesmith on 2015
 */

@Service("dictionaryService")
public class DictionaryService  extends BaseService<Dictionary, Integer>{
	
	@Autowired
	private DictionaryMapper dictionaryMapper;
	
     /**
     * 判断字段是否存在（插入和修改时可用,修改本身时如判断字段未发生修改会忽略）
     * @param inputName 要判断的字段名
     * @param value 要判断的值
     * @param id 当前记录id
     * @return 是否存在
     */
    public boolean isExist(String inputName,String value,int id) {
        Example exp = new Example(Dictionary.class);
        Example.Criteria criteria = exp.createCriteria();
        criteria.andEqualTo(inputName, value);
        List<Dictionary> list=selectByExample(exp);
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
	 * 根据条件查询dictionary表(适用人群)
	 * @param systemName
	 * @param modelName
	 * @return
	 */
	public List<DictionaryVo> getListBySystemNameAndModelName(
			String systemName,String modelName) {
		List<DictionaryVo> dictionarys = new ArrayList<DictionaryVo>();
		dictionarys = this.dictionaryMapper.getListBySystemNameAndModelName(systemName,modelName);
		return dictionarys;
	}
	
	/**
	 * 搜索类型
	 * @param systemName
	 * @param modelName
	 * @return
	 */
	public List<DictionaryVo> getListForSearchType(
			String systemName,String modelName) {
		List<DictionaryVo> dictionarys = new ArrayList<DictionaryVo>();
		dictionarys = this.dictionaryMapper.getListBySystemNameAndModelName(systemName,modelName);
		return dictionarys;
	}
	
	public String getListByPortfoliTarget(Integer portfolioId){
		List<DictionaryVo> dictionarys = dictionaryMapper.getListByPortfoliTarget(portfolioId);
		String target = "";
		for(DictionaryVo dictionaryVo:dictionarys){
			target = dictionaryVo.getDicKey() + ",";
		}
		return target.length()>0?target.substring(0,target.length()-1):"";
	}

}