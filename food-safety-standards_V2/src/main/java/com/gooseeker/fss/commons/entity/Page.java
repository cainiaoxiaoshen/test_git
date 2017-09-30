package com.gooseeker.fss.commons.entity;

import java.util.Collection;
import java.util.List;

/**
 * 用于分页的工具类
 * 
 */
public class Page<T> {
	private int total = 0; // 总记录数
	private int limit = 20; // 每页显示记录数
	private int pages = 1; // 总页数
	private int currentPage = 1; // 当前页
	private int startNum = 0;

	private int navigatePages = 6; // 导航页码数
	private int[] navigatePageNumbers; // 所有导航页号
	
	private Collection<T> items;
	
	private List<T> list;

	public Page() {
	}

	public Page(int total, int currentPage, int limit) {
		// 设置基本参数
		this.total = total;
		this.limit = limit;
		this.pages = (this.total % this.limit == 0 ? this.total / this.limit
				: (this.total / this.limit + 1));
		// 根据输入可能错误的当前号码进行自动纠正
		if (currentPage < 1) {
			this.currentPage = 1;
		} else if (currentPage > this.pages) {
			this.currentPage = this.pages;
		} else {
			this.currentPage = currentPage;
		}
		if (this.currentPage < 1) {
			this.startNum = 0;
		} else {
			this.startNum = (this.currentPage - 1) * limit;
		}
		// 基本参数设定之后进行导航页面的计算
		calcNavigatePageNumbers();
	}

	/**
	 * 计算导航页
	 */
	private void calcNavigatePageNumbers() {
		// 当总页数小于或等于导航页码数时
		if (pages <= navigatePages) {
			navigatePageNumbers = new int[pages];
			for (int i = 0; i < pages; i++) {
				navigatePageNumbers[i] = i + 1;
			}
		} else { // 当总页数大于导航页码数时
			navigatePageNumbers = new int[navigatePages];
			int startNum = currentPage - navigatePages / 2;
			int endNum = currentPage + navigatePages / 2;

			if (startNum < 1) {
				startNum = 1;
				// (最前navPageCount页
				for (int i = 0; i < navigatePages; i++) {
					navigatePageNumbers[i] = startNum++;
				}
			} else if (endNum > pages) {
				endNum = pages;
				// 最后navPageCount页
				for (int i = navigatePages - 1; i >= 0; i--) {
					navigatePageNumbers[i] = endNum--;
				}
			} else {
				// 所有中间页
				for (int i = 0; i < navigatePages; i++) {
					navigatePageNumbers[i] = startNum++;
				}
			}
		}
	}

	/**
	 * 得到记录总数
	 * 
	 * @return {int}
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * 得到每页显示多少条记录
	 * 
	 * @return {int}
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * 得到页面总数
	 * 
	 * @return {int}
	 */
	public int getPages() {
		return pages;
	}

	/**
	 * 得到当前页号
	 * 
	 * @return {int}
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * 得到所有导航页号
	 * 
	 * @return {int[]}
	 */
	public int[] getNavigatePageNumbers() {
		return navigatePageNumbers;
	}

	public int getStartNum() {
		return startNum;
	}

	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public Collection<T> getItems() {
		return items;
	}

	public void setItems(Collection<T> items) {
		this.items = items;
	}

    public List<T> getList()
    {
        return list;
    }

    public void setList(List<T> list)
    {
        this.list = list;
    }
	
	
}
