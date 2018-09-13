package cn.storm.common.bootstraptable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 封装了bootstrapTable插件传递的参数
 * 
 * @Copyright (c) 2012,北京吉威时代软件股份有限公司.All Rights Reserved.
 * @link http://www.geoway.com.cn
 * @author 吕晓飞
 * @date 2018-05-29 10:21
 * @version 1.0
 */
public class BootstrapTableModel implements Serializable {

	/** 每页大小 */
	private Long limit;

	/** 起始页码 */
	private Long offset;

	/** 排序字段 */
	private String sort;

	/** 排序方式 ASC/DESC */
	private String sortOrder;

	/** 模糊搜索语句 */
	private String search;

	/** 禁止搜索字段集合(非框架字段) */
	private List<String> searchExclude = new ArrayList<>();

	/** 禁止排序字段集合(非框架字段) */
	private List<String> sortExclude = new ArrayList<>();

	private static final long serialVersionUID = 1L;

	public BootstrapTableModel() {
		super();
	}

	public BootstrapTableModel(Long limit, Long offset, String sort, String sortOrder, String search,
			List<String> searchExclude, List<String> sortExclude) {
		super();
		this.limit = limit;
		this.offset = offset;
		this.sort = sort;
		this.sortOrder = sortOrder;
		this.search = search;
		this.searchExclude = searchExclude;
		this.sortExclude = sortExclude;
	}

	public Long getLimit() {
		return limit;
	}

	public void setLimit(Long limit) {
		this.limit = limit;
	}

	public Long getOffset() {
		return offset;
	}

	public void setOffset(Long offset) {
		this.offset = offset;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public List<String> getSearchExclude() {
		return searchExclude;
	}

	public void setSearchExclude(List<String> searchExclude) {
		this.searchExclude = searchExclude;
	}

	public List<String> getSortExclude() {
		return sortExclude;
	}

	public void setSortExclude(List<String> sortExclude) {
		this.sortExclude = sortExclude;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((limit == null) ? 0 : limit.hashCode());
		result = prime * result + ((offset == null) ? 0 : offset.hashCode());
		result = prime * result + ((search == null) ? 0 : search.hashCode());
		result = prime * result + ((searchExclude == null) ? 0 : searchExclude.hashCode());
		result = prime * result + ((sort == null) ? 0 : sort.hashCode());
		result = prime * result + ((sortExclude == null) ? 0 : sortExclude.hashCode());
		result = prime * result + ((sortOrder == null) ? 0 : sortOrder.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BootstrapTableModel other = (BootstrapTableModel) obj;
		if (limit == null) {
			if (other.limit != null)
				return false;
		} else if (!limit.equals(other.limit))
			return false;
		if (offset == null) {
			if (other.offset != null)
				return false;
		} else if (!offset.equals(other.offset))
			return false;
		if (search == null) {
			if (other.search != null)
				return false;
		} else if (!search.equals(other.search))
			return false;
		if (searchExclude == null) {
			if (other.searchExclude != null)
				return false;
		} else if (!searchExclude.equals(other.searchExclude))
			return false;
		if (sort == null) {
			if (other.sort != null)
				return false;
		} else if (!sort.equals(other.sort))
			return false;
		if (sortExclude == null) {
			if (other.sortExclude != null)
				return false;
		} else if (!sortExclude.equals(other.sortExclude))
			return false;
		if (sortOrder == null) {
			if (other.sortOrder != null)
				return false;
		} else if (!sortOrder.equals(other.sortOrder))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BootstrapTableModel [limit=" + limit + ", offset=" + offset + ", sort=" + sort + ", sortOrder="
				+ sortOrder + ", search=" + search + ", searchExclude=" + searchExclude + ", sortExclude=" + sortExclude
				+ "]";
	}

}
