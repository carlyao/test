package com.upchina.service;

import com.upchina.model.NoteTag;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
/**
 * Created by codesmith on 2015
 */

@Service("noteTagService")
public class NoteTagService  extends BaseService<NoteTag, Integer>{
     /**
     * 判断字段是否存在（插入和修改时可用,修改本身时如判断字段未发生修改会忽略）
     * @param inputName 要判断的字段名
     * @param value 要判断的值
     * @param id 当前记录id
     * @return 是否存在
     */
    public boolean isExist(String inputName,String value,int id) {
        Example exp = new Example(NoteTag.class);
        Example.Criteria criteria = exp.createCriteria();
        criteria.andEqualTo(inputName, value);
        List<NoteTag> list=selectByExample(exp);
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
}