package com.upchina.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.upchina.dao.TagMapper;
import com.upchina.model.Tag;
import com.upchina.model.UserInfo;
import com.upchina.util.Constants;
import com.upchina.vo.jqGridResponseVo;
import com.upchina.vo.rest.TagTypeInVo;
import com.upchina.vo.rest.output.TagOutVo;
/**
 * Created by codesmith on 2015
 */

@Service("tagService")
public class TagService  extends BaseService<Tag, Integer>{
     /**
     * 判断字段是否存在（插入和修改时可用,修改本身时如判断字段未发生修改会忽略）
     * @param inputName 要判断的字段名
     * @param value 要判断的值
     * @param id 当前记录id
     * @return 是否存在
     */
	@Autowired
	private TagMapper tagMapper;
	
    public boolean isExist(String inputName,String value,int id) {
        Example exp = new Example(Tag.class);
        Example.Criteria criteria = exp.createCriteria();
        criteria.andEqualTo(inputName, value);
        List<Tag> list=selectByExample(exp);
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

	public jqGridResponseVo<Tag> selectTagByType(TagTypeInVo tagTypeInVo) {
		Integer tagType = tagTypeInVo.getTagType();
		Integer pageSize = tagTypeInVo.getPageSize();
		Integer pageNum = tagTypeInVo.getPageNum();
		String sql ="select a.Id, a.Name, a.Tag_Type tagType, a.Create_Time createTime, a.Update_Time updateTime, a.`Status` from tag a where 1=1";
		Example example = new Example(Tag.class);
		if(null != tagType && 0 != tagType){
			example.createCriteria().andEqualTo("tagType", tagType);
			sql += " and a.Tag_Type =" + tagType;
		}
		if(null == pageSize || 0 == pageSize){
			pageSize = 10;
		}
		if(null == pageNum || 0 == pageNum){
			pageNum = 1;
		}
		
		PageHelper.startPage(pageNum, pageSize);
//		List<Tag> list = mapper.selectByExample(example);
		List<Tag> list = sqlMapper.selectList(sql, Tag.class);
		PageInfo<Tag> pageInfo = new PageInfo(list);
//		if(null == list){
//			list = new ArrayList<Tag>();
//		}
//		Tag tag = new Tag();
//		if(null != tagType && 0 != tagType){
//			tag.setTagType(tagType);
//		}
//		int count = selectCount(tag);
		return new jqGridResponseVo(pageInfo.getPages(),pageInfo.getList(),pageNum,pageInfo.getTotal());
	}

	/**
	 * @param tagIds
	 * @return
	 */
	public List<TagOutVo> selectTagByTagIds(List<Integer> tagIds) {
		List<TagOutVo> tagVos = new ArrayList<TagOutVo>();
		for(int i=0; i<tagIds.size(); i++){
			TagOutVo tagVo = this.tagMapper.selectTagByTagId(tagIds.get(i));
			tagVos.add(tagVo);
		}
		return tagVos;
	}

	public List<TagOutVo> getGroupHotTag() {
		String [] tagTypes = Constants.HOT_TAG_TYPES.split(",");
		List<String> list = Arrays.asList(tagTypes);
		List<TagOutVo> tagVos = tagMapper.getGroupHotTag(Constants.HOT_TAG_TYPES);
		return tagVos;
	}

	/**根据标签类型查询标签
	 * @param tagType
	 * @return
	 */
	public List<Tag> selectNiuGroupTagType(Integer tagType) {
		List<Tag> tags = new ArrayList<Tag>();
		if(0 != tagType){
			tags = this.tagMapper.selectNiuGroupTagType(tagType);
		}
		return tags;
	}
	
	//根据userID取得用户标签
	public List<TagOutVo> selectTagByUserId(Integer userId){
		List<TagOutVo> tagVos = tagMapper.selectTagByUserId(userId);
		return tagVos;
	}
	
	public List<TagOutVo> selectTagByGroupId(Integer groupId){
		return tagMapper.selectTagByGroupId(groupId);
	}
}