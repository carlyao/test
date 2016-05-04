package com.upchina.account.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.upchina.account.dao.BargainHisMapper;
import com.upchina.account.model.BargainHis;
import com.upchina.service.BaseService;
import com.upchina.vo.jqGridResponseVo;
import com.upchina.vo.rest.output.PortfolioStockRecordOutVo;
/**
 * Created by codesmith on 2015
 */

@Service("bargainHisService")
public class BargainHisService  extends BaseService<BargainHis, Integer>{
	
	@Autowired
	private BargainHisMapper bargainHisMapper;
	
     /**
     * 判断字段是否存在（插入和修改时可用,修改本身时如判断字段未发生修改会忽略）
     * @param inputName 要判断的字段名
     * @param value 要判断的值
     * @param id 当前记录id
     * @return 是否存在
     */
    public boolean isExist(String inputName,String value,int id) {
        Example exp = new Example(BargainHis.class);
        Example.Criteria criteria = exp.createCriteria();
        criteria.andEqualTo(inputName, value);
        List<BargainHis> list=selectByExample(exp);
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
    
	public jqGridResponseVo<PortfolioStockRecordOutVo> selectBase(Integer userCode,
			Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		List<PortfolioStockRecordOutVo> list=bargainHisMapper.selectBase(userCode);
		PageInfo<PortfolioStockRecordOutVo> pageInfo = new PageInfo(list);
		return new jqGridResponseVo(pageInfo.getPages(),pageInfo.getList(),pageNum,pageInfo.getTotal());
	}
	
}