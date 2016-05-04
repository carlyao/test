package com.upchina.util;

import java.util.Comparator;

import com.upchina.vo.rest.output.LiveMessageOutVo;

public class LiveMessageComparator implements Comparator<LiveMessageOutVo> {

	@Override
	public int compare(LiveMessageOutVo o1, LiveMessageOutVo o2) {
		// TODO Auto-generated method stub
		if(o1.getId() > o2.getId()){
			return -1;
		}else {
			return 1;
		}
	}

}
