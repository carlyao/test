package com.upchina.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import tk.mybatis.mapper.entity.Example;

import com.upchina.Exception.UpChinaError;
import com.upchina.dao.ActionLogMapper;
import com.upchina.model.ActionLog;
import com.upchina.vo.BaseOutVo;
import com.upchina.vo.rest.output.UserAdviserOutVo;
/**
 * Created by codesmith on 2015
 */

@Service("actionLogService")
public class ActionLogService  extends BaseService<ActionLog, Integer>{
	
	@Autowired
	private ActionLogMapper actionLogMapper;
	
     /**
     * 判断字段是否存在（插入和修改时可用,修改本身时如判断字段未发生修改会忽略）
     * @param inputName 要判断的字段名
     * @param value 要判断的值
     * @param id 当前记录id
     * @return 是否存在
     */
    public boolean isExist(String inputName,String value,int id) {
        Example exp = new Example(ActionLog.class);
        Example.Criteria criteria = exp.createCriteria();
        criteria.andEqualTo(inputName, value);
        List<ActionLog> list=selectByExample(exp);
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

    public BaseOutVo addLog(Integer userId,String userName,Integer moduleId,Integer moduleType, String operate,String remark){
        ActionLog actionLog = new ActionLog();
        BaseOutVo res = new BaseOutVo();
        actionLog.setModuleId(moduleId);
        if(!StringUtils.isEmpty(operate)){
            actionLog.setOperate(operate);
        }else{
            res.setResultMsg("记录日志失败:操作内容为空");
            res.setResultCode(UpChinaError.ERROR.code);
            return res;
        }
        //备注可为空
        if(!StringUtils.isEmpty(remark)){
            actionLog.setRemark(remark);
        }else{
            actionLog.setRemark("");
        }
        actionLog.setUserId(userId);
        actionLog.setUserName(userName);
        actionLog.setModuleType(moduleType);
        actionLog.setCreateTime(new Date());

        try {
            insert(actionLog);
            res.setResultMsg("记录日志成功");
            res.setResultCode(UpChinaError.SUCCESS.code);
        } catch (Exception e) {
            res.setResultMsg("记录日志失败");
            res.setResultCode(UpChinaError.ERROR.code);
            e.printStackTrace();
        }
        return res;
    }

	public List<UserAdviserOutVo> getAdviserContent(Integer userId) {
		List<UserAdviserOutVo> userInfoOrderOutVos = actionLogMapper.getAdviserContent(userId);
		return userInfoOrderOutVos;
	}
}