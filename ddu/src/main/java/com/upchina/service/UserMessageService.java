package com.upchina.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.upchina.Exception.UpChinaError;
import com.upchina.dao.UserMessageMapper;
import com.upchina.model.Message;
import com.upchina.model.UserMessage;
import com.upchina.util.Constants;
import com.upchina.vo.BaseOutVo;
import com.upchina.vo.jqGridResponseVo;
import com.upchina.vo.rest.input.UserMessageInVo;
import com.upchina.vo.rest.input.UserReadMessageInVo;
import com.upchina.vo.rest.output.NiuGroupSearchOutVo;
import com.upchina.vo.rest.output.UserMessageOutVo;
/**
 * Created by codesmith on 2015
 */

@Service("userMessageService")
public class UserMessageService  extends BaseService<UserMessage, Integer>{
     /**
     * 判断字段是否存在（插入和修改时可用,修改本身时如判断字段未发生修改会忽略）
     * @param inputName 要判断的字段名
     * @param value 要判断的值
     * @param id 当前记录id
     * @return 是否存在
     */
	@Autowired
	private UserMessageMapper userMessageMapper;
	
    public boolean isExist(String inputName,String value,int id) {
        Example exp = new Example(UserMessage.class);
        Example.Criteria criteria = exp.createCriteria();
        criteria.andEqualTo(inputName, value);
        List<UserMessage> list=selectByExample(exp);
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

	public jqGridResponseVo<UserMessageOutVo> getList(UserMessageInVo userMessageInVo) {
		String sql = "select a.Id, a.Title, a.Summary, a.Content,a.Message_Type messageType from message a where a.Id not in(select b.Message_Id from user_message b where b.User_Id = #{userId}) and Date(a.Create_Time) >= Date(date_sub(now(),interval 7 day))";
		List<Message> list = sqlMapper.selectList(sql,userMessageInVo, Message.class);
		Date now = new Date();
		for (Message message : list) {
			UserMessage userMessage = new UserMessage();
			userMessage.setUserId(userMessageInVo.getUserId());
			userMessage.setMessageId(message.getId());
			userMessage.setCreateTime(now);
			userMessage.setUpdateTime(now);
			userMessage.setStatus(Constants.MESSAGE_NOT_READ);
			insert(userMessage);
		}
		int pageNum = userMessageInVo.getPageNum();
		int pageSize = userMessageInVo.getPageSize();
		PageHelper.startPage(pageNum, pageSize);
		String notReadsql = "select a.Message_Id messageId,a.User_Id userId, a.Create_Time createTime, a.`Status`, b.Title, b.Summary, b.Content from user_message a, message b where a.Message_Id = b.Id and a.User_Id = #{userId} and a.`Status` = 0";
		List<UserMessageOutVo> userMessageOutVos = sqlMapper.selectList(notReadsql, userMessageInVo, UserMessageOutVo.class);
		PageInfo<NiuGroupSearchOutVo> pageInfo = new PageInfo(userMessageOutVos);
		return new jqGridResponseVo(pageInfo.getPages(),pageInfo.getList(),pageNum,pageInfo.getTotal());
	}

	public BaseOutVo read(UserReadMessageInVo userMessageInVo) {
		userMessageMapper.updateReadMessage(userMessageInVo.getMessageId(),userMessageInVo.getUserId(),Constants.IS_READ);
		BaseOutVo baseOutVo = new BaseOutVo();
		baseOutVo.setResultCode(UpChinaError.SUCCESS.code);
		baseOutVo.setResultMsg(UpChinaError.SUCCESS.message);
		return baseOutVo;
	}
}