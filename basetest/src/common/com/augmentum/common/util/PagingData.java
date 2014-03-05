package com.augmentum.common.util;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class PagingData<T> {

	private int pageNo = 1;
	private int pageSize = 10;
	private List<T> result;
	private long totalResult = -1;
	private int totalPage = 0;
	private T object;
    private String chartFileName;
    private String orderByCol;
	private String orderBySeq;
	public PagingData() {
	}

	public PagingData(int pageNo, int pageSize, List<T> result, long totalResult) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.result = result;
		this.totalResult = totalResult;
	}
	
	public PagingData(int pageNo, int pageSize, T object, long totalResult) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.object = object;
		this.totalResult = totalResult;
	}
	
	public PagingData(int pageNo, int pageSize,List<T> result, long totalResult,String chartFileName) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.result = result;
		this.totalResult = totalResult;
		this.chartFileName=chartFileName;
	}
	
	public PagingData(int pageNo, int pageSize,List<T> result, long totalResult,String orderByCol,String orderBySeq) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.result = result;
		this.totalResult = totalResult;
		this.orderByCol=orderByCol;
		this.orderBySeq=orderBySeq;
	}

	public boolean hasPrePage() {
		return (getPageNo() > 1);
	}

	public boolean hasNextPage() {
		return (getPageNo() + 1 <= getTotalPage());
	}

	public boolean isFirstPage() {
		return !hasPrePage();
	}

	public boolean isLastPage() {
		return !hasNextPage();
	}

	public int getPrePage() {
		if (hasPrePage()) {
			return getPageNo() - 1;
		} else {
			return getPageNo();
		}
	}

	public int getNextPageNo() {
		if (hasNextPage()) {
			return getPageNo() + 1;
		} else {
			return getPageNo();
		}
	}

	public List<Integer> getPageNoNavigator(int count) {
		int halfSize = count / 2;
		int totalPage = getTotalPage();

		int startPageNo = Math.max(getPageNo() - halfSize, 1);
		int endPageNo = Math.min(startPageNo + count - 1, totalPage);

		if (endPageNo - startPageNo < count) {
			startPageNo = Math.max(endPageNo - count, 1);
		}

		List<Integer> result = new ArrayList<Integer>();
		for (int i = startPageNo; i <= endPageNo; i++) {
			result.add(i);
		}
		return result;
	}

	public int getShowPageNoFrom() {
		int showPageNoFrom = 0;
//		if (pageNo == 1) {
//			showPageNoFrom = 1;
//		} else {
//			showPageNoFrom = (pageNo - 1) * pageSize + 1;
//		}
		showPageNoFrom = (pageNo - 1) * pageSize + 1;
		return showPageNoFrom;

	}

	public int getShowPageNoTo() {
		int showPageNoTo = 0;
		if (((int) totalResult - pageSize * (pageNo - 1)) < pageSize) {
//			if (pageNo == 1) {
//				showPageNoTo = (int) totalResult;
//			} else {
//				showPageNoTo = (int) (totalResult - pageSize * (pageNo - 1))
//						+ getShowPageNoFrom();
//			}
			showPageNoTo = (int) totalResult;
		} else {
			showPageNoTo = pageNo * pageSize;
		}
		return showPageNoTo;

	}
	
	
	public String getShowPageNoFromDelimiter() {
		NumberFormat nf = NumberFormat.getInstance();
		return nf.format(getShowPageNoFrom());
		
	}
	
	public String getShowPageNoToDelimiter() {
		NumberFormat nf = NumberFormat.getInstance();
		return nf.format(getShowPageNoTo());
		
	}
	
	public String getTotalResultDelimiter() {
		NumberFormat nf = NumberFormat.getInstance();
		return nf.format(totalResult);
		
	}

	public List<Integer> getPageNoNavigator() {
		return getPageNoNavigator(10);
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public long getTotalResult() {
		return totalResult;
	}

	public void setTotalResult(long totalResult) {
		this.totalResult = totalResult;
	}

	public int getTotalPage() {
		totalPage = (int) Math.ceil((double) getTotalResult()
				/ (double) pageSize);
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

	public String getChartFileName() {
		return chartFileName;
	}

	public void setChartFileName(String chartFileName) {
		this.chartFileName = chartFileName;
	}

	public String getOrderByCol() {
		return orderByCol;
	}

	public void setOrderByCol(String orderByCol) {
		this.orderByCol = orderByCol;
	}

	public String getOrderBySeq() {
		return orderBySeq;
	}

	public void setOrderBySeq(String orderBySeq) {
		this.orderBySeq = orderBySeq;
	}

	@Override
	public String toString() {
		return "PagingData [pageNo=" + pageNo + ", pageSize=" + pageSize
				+ ", result=" + result + ", totalResult=" + totalResult
				+ ", totalPage=" + totalPage + ", object=" + object
				+ ", chartFileName=" + chartFileName + ", orderByCol="
				+ orderByCol + ", orderBySeq=" + orderBySeq + "]";
	}

	
}
