/**
 * 
 */
package com.upchina.util;

import java.util.Comparator;

import com.upchina.vo.rest.output.PortfolioListVoBig;

/**
 * @author shiwei
 *
 * 2016年3月24日
 */
public class ComparatorMaxdown implements Comparator{

	public int compare(Object obj0,Object obj1){
		
		PortfolioListVoBig portfolioListVoBig0 = (PortfolioListVoBig) obj0;
		PortfolioListVoBig portfolioListVoBig1 = (PortfolioListVoBig) obj1;
		
		Double d1 = new Double(portfolioListVoBig0.getMaxDrawdown());
		Double d2 = new Double(portfolioListVoBig1.getMaxDrawdown());
		
		int flag = d1.compareTo(d2);
		
		return flag;
		
	}
	
}
