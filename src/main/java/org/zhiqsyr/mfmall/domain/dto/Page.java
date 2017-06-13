package org.zhiqsyr.mfmall.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * 自定义 Page
 * 
 * @author dongbz 2016-08-03
 */
@SuppressWarnings("serial")
public class Page extends PageRequest {

	private static final int DEFAULT_NUMBER = 0;
	private static final int DEFAULT_SIZE = 10;
	
	/*
	requestBody:	{"number":0,"size":10,"sort":[{"direction":"ASC","property":"id"}]}
	
	response: 
	content			结果集合
	totalPages		总共页数
	totalElements	记录总数
	last			是否最后一页
	number			当前页码（从0开始）
	size			每页条数
	sort.0
		direction	正序/逆序
		property	属性名称
		ignoreCase	是否忽略大小写
		nullHandling空值处理方式，默认NATIVE，由数据库决定
		ascending	是否正序
	numberOfElements当前页记录条数
	first			是否第一页
	 */
	
	private List<Sort.Order> orders;
	
	public Page() {
		this(DEFAULT_NUMBER, DEFAULT_SIZE, ImmutableList.of(Order.DEFAULT_ORDER));
	}
	
	/** 默认 page 信息：第一页，每页10条，主键倒序 */
	@JsonCreator
	public Page(@JsonProperty("number") int number, @JsonProperty("size") int size, @JsonProperty("sort") List<Order> orders) {
		super(number, size, new Sort(Order.convert(orders)));

		this.orders = Order.convert(orders);
	}
	
	public List<Sort.Order> getOrders() {
		return orders;
	}
	
}
