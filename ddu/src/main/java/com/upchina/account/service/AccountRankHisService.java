package com.upchina.account.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.upchina.account.dao.AccountRankHisMapper;
import com.upchina.account.model.AccountRankHis;
import com.upchina.service.BaseService;
import com.upchina.util.SqlMapper;
/**
 * Created by codesmith on 2015
 */

@Service("accountRankHisService")
public class AccountRankHisService  extends BaseService<AccountRankHis, Integer>{
     /**
     * 判断字段是否存在（插入和修改时可用,修改本身时如判断字段未发生修改会忽略）
     * @param inputName 要判断的字段名
     * @param value 要判断的值
     * @param id 当前记录id
     * @return 是否存在
     */
	@Autowired
	private  AccountRankHisMapper accountRankHisMapper;
	
	@Autowired
	protected SqlMapper accountSqlMapper;
	
    public boolean isExist(String inputName,String value,int id) {
        Example exp = new Example(AccountRankHis.class);
        Example.Criteria criteria = exp.createCriteria();
        criteria.andEqualTo(inputName, value);
        List<AccountRankHis> list=selectByExample(exp);
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

	public List<AccountRankHis> getAccountRankHis(String date) {
		if(null != date){
			String sql = "select a.* from account_rank_his a where Date(a.UpdateTime)=Date(#{data})";
			List<AccountRankHis> accountRankHis = accountSqlMapper.selectList(sql, date, AccountRankHis.class);
			return accountRankHis;
		}else{
			List<AccountRankHis> accountRankHis = accountRankHisMapper.getAccountRankHis(date);
			return accountRankHis;
		}
	}

	public Integer getWinCount(Integer portfolioId) {
		Integer count = accountRankHisMapper.getWinCount(portfolioId);
		return count;
	}

	public Integer getLoseCount(Integer portfolioId) {
		Integer count = accountRankHisMapper.getLoseCount(portfolioId);
		return count;
	}

	public Integer getDrawCount(Integer portfolioId) {
		Integer count = accountRankHisMapper.getDrawCount(portfolioId);
		return count;
	}

	public List<AccountRankHis> selectLatest(String usercode) {
		return accountRankHisMapper.selectLatest(usercode);
	}
}