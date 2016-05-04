package com.upchina.util;

import java.util.Comparator;

import com.upchina.model.LiveContent;

public class LiveContentComparator implements Comparator<LiveContent>{

	@Override
	public int compare(LiveContent o1, LiveContent o2) {
		if(o1.getId() > o2.getId()){
			return -1;
		}else {
			return 1;
		}
	}

}
