/**
 * 
 */
package com.upchina.vo.rest.output;

import java.util.List;

import com.upchina.model.Tag;

/**
 * @author shiwei
 *
 * 2016年1月5日
 */
public class NiuGroupTagOutVo {

	private List<Tag> initiateTags;//倡导
	private List<Tag> decisionTags;//决策
	private List<Tag> shareTags;//分享
	
	public List<Tag> getInitiateTags() {
		return initiateTags;
	}
	public void setInitiateTags(List<Tag> initiateTags) {
		this.initiateTags = initiateTags;
	}
	public List<Tag> getDecisionTags() {
		return decisionTags;
	}
	public void setDecisionTags(List<Tag> decisionTags) {
		this.decisionTags = decisionTags;
	}
	public List<Tag> getShareTags() {
		return shareTags;
	}
	public void setShareTags(List<Tag> shareTags) {
		this.shareTags = shareTags;
	}
	
	
}
