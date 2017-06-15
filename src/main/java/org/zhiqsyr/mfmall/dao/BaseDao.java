package org.zhiqsyr.mfmall.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.zhiqsyr.mfmall.domain.dto.Page;
import org.zhiqsyr.mfmall.domain.enums.Validity;

import java.io.Serializable;
import java.util.*;

/**
 * Spring Boot Hibernate DAO 基类
 * 
 * @author dongbz 2016-08-02
 */
public class BaseDao {
	
	/** 批量操作数量 */
	private static final int BATCH = 50; 
	
	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/** 当前时间 */
	public Date getCurrentTime() {
		return new Date();
	}
	
	public <T> T get(Class<T> entityClass, Serializable id) {
		Assert.notNull(entityClass);
		if (id == null) {
			return null;
		}
		
		return getSession().get(entityClass, id);
	}
	
	/**
	 * 获得满足条件记录条数
	 * 
	 * @param wheres
	 * @param values
	 * @return
	 * @author dongbz 
	 * @since  2016-08-04
	 */
	public <T> long getCount(Class<T> entityClass, List<String> wheres, Object... values) {
		Assert.notNull(entityClass);

		StringBuffer hql = new StringBuffer();
		hql.append(" select count(1) ");
		hql.append(" from ").append(entityClass.getName());
		hql.append(" where 1 = 1 ");
		if (CollectionUtils.isEmpty(wheres)) {
			Query query = getSession().createQuery(hql.toString());
			return (long) query.uniqueResult();
		}
		
		for (int i = 0; i < wheres.size(); i++) {
			hql.append(buildWhereClause(wheres.get(i), values[i]));
		}
			
		Query query = getSession().createQuery(hql.toString());
		for (int i = 0; i < wheres.size(); i++) {
			setQueryParameter(query, wheres.get(i), values[i]);
		}
		
		return (long) query.uniqueResult();
	}		
	
	/**
	 * 根据给定属性查询，返回唯一结果
	 * 
	 * @param entityClass
	 * @param propertyName
	 * @param propertyValue
	 * @return
	 * @author dongbz 2016-08-02
	 */
	public <T> T getByProperty(Class<T> entityClass, String propertyName, Object propertyValue) {
		List<T> result = findByProperty(entityClass, propertyName, propertyValue);
		if (result.size() == 0) {
			return null;
		} else if (result.size() == 1) {
			return result.get(0);
		}
		throw new RuntimeException(String.format("There are %d records of %s.%s = %s", result.size(), entityClass.getName(), propertyName, propertyValue));
	}
	
	/**
	 * 根据给定属性查询，返回集合
	 * 
	 * @param entityClass
	 * @param propertyName
	 * @param propertyValue
	 * @return
	 * @author dongbz 2016-08-02
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> findByProperty(Class<T> entityClass, String propertyName, Object propertyValue) {
		Assert.notNull(entityClass);
		Assert.hasText(propertyName);
		
		StringBuffer hql = new StringBuffer();
		hql.append(" from ").append(entityClass.getName());
		hql.append(" where 1 = 1 ");
		hql.append(buildWhereClause(propertyName, propertyValue));
		
		Query query = getSession().createQuery(hql.toString());
		setQueryParameter(query, propertyName, propertyValue);
		
		return query.list();		
	}
	
	/**
	 * 根据给定属性以及validity查询，返回结果列表
	 * 
	 * @param entityClass
	 * @param propertyName
	 * @param propertyValue
	 * @return
	 * @author dongbz 2016-08-02
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> findValidsByProperty(Class<T> entityClass, String propertyName, Object propertyValue) {
		Assert.notNull(entityClass);
		Assert.hasText(propertyName);
		
		StringBuffer hql = new StringBuffer();
		hql.append(" from ").append(entityClass.getName());
		hql.append(buildWhereClause(propertyName, propertyValue));
		hql.append(" and validity = :validity");
		
		Query query = getSession().createQuery(hql.toString());
		setQueryParameter(query, propertyName, propertyValue);
		query.setParameter("validity", Validity.VALID);
		
		return query.list();
	}

	public <T> List<T> findBySql(Class<T> returnClass, String sql, Map<String, Object> args) {
		return findBySql(returnClass, sql, args, null).getContent();
	}
	
	/**
	 * 根据 SQL、查询条件 args 以及分页信息 page，返回结果集合
	 * 
	 * @param returnClass	返回类型
	 * @param sql	假如已经存在 order by 信息，忽略 page.orders 信息
	 * @param args	可以传null
	 * @param page	可以传null
	 * @return
	 * @author dongbz 
	 * @since  2016-08-04
	 */
	@SuppressWarnings("unchecked")
	public <T> org.springframework.data.domain.Page<T> findBySql(Class<T> returnClass, String sql, Map<String, Object> args, Page page) {
		Assert.hasText(sql);
		
		Query queryResult;					// 当前一页结果
		
		if (page == null) {					// 不用分页，直接封装结果返回 
			queryResult = getSession().createSQLQuery(sql);
			setQueryArgs(queryResult, args);
			queryResult.setResultTransformer(Transformers.aliasToBean(returnClass));
			return new PageImpl<>(queryResult.list());
		}
		
		// 查询总数
		String fromSql = sql.trim();
		if (fromSql.indexOf("from") != -1) { // 删除 from或FROM 之前的语句
			fromSql = fromSql.substring(fromSql.indexOf("from"));
		} else if (fromSql.indexOf("FROM") != -1) {
			fromSql = fromSql.substring(fromSql.indexOf("FROM"));
		}
		Query queryTotal = getSession().createSQLQuery(" select count(1) " + fromSql);
		setQueryArgs(queryTotal, args);
		long totalElements = (Long) queryTotal.uniqueResult();
		if (totalElements == 0) {
			return new PageImpl<>(new ArrayList<T>(0), page, totalElements);
		}

		StringBuffer appendSort = new StringBuffer(sql);
		if (sql.contains("order by")) { 	// 假如已经存在排序信息，直接查询
			queryResult = getSession().createSQLQuery(sql);
			setQueryArgs(queryResult, args);
			queryResult.setResultTransformer(Transformers.aliasToBean(returnClass));
			return new PageImpl<>(queryResult.list(), page, totalElements);
		}

		// 构造排序信息
		appendSort.append(" order by ");
		for (Sort.Order order : page.getOrders()) {
			if (order.isAscending()) {
				appendSort.append(order.getProperty()).append(" asc,");
			} else {
				appendSort.append(order.getProperty()).append(" desc,");
			}
		}
		queryResult = getSession().createSQLQuery(appendSort.deleteCharAt(appendSort.length() - 1).toString());
		setQueryArgs(queryResult, args);
		queryResult.setResultTransformer(Transformers.aliasToBean(returnClass));
		return new PageImpl<>(queryResult.list(), page, totalElements);
	}	
	
	@SuppressWarnings("unchecked")
	public <T> List<T> findByHql(String hql, Map<String, Object> args) {
		return (List<T>) findByHql(hql, args, null).getContent();
	}
	
	/**
	 * 根据 HQL、查询条件 args 以及分页信息 page，返回结果集合
	 * 
	 * @param hql	假如已经存在 order by 信息，忽略 page.orders 信息
	 * @param args	可以传null
	 * @param page	可以传null
	 * @return
	 * @author dongbz 
	 * @since  2016-08-04
	 */
	@SuppressWarnings("unchecked")
	public <T> org.springframework.data.domain.Page<T> findByHql(String hql, Map<String, Object> args, Page page) {
		Assert.hasText(hql);
		
		Query queryResult;					// 当前一页结果
		
		if (page == null) {					// 不用分页，直接封装结果返回 
			queryResult = getSession().createQuery(hql);
			setQueryArgs(queryResult, args);
			return new PageImpl<>(queryResult.list());
		}
		
		// 查询总数
		String fromHql = hql.trim();
		if (fromHql.indexOf("from") != -1) { // 删除 from或FROM 之前的语句
			fromHql = fromHql.substring(fromHql.indexOf("from"));
		} else if (fromHql.indexOf("FROM") != -1) {
			fromHql = fromHql.substring(fromHql.indexOf("FROM"));
		}
		Query queryTotal = getSession().createQuery(" select count(1) " + fromHql);
		setQueryArgs(queryTotal, args);
		long totalElements = (Long) queryTotal.uniqueResult();
		if (totalElements == 0) {
			return new PageImpl<>(new ArrayList<T>(0), page, totalElements);
		}

		StringBuffer appendSort = new StringBuffer(hql);
		if (hql.contains("order by")) { 	// 假如已经存在排序信息，直接查询
			queryResult = getSession().createQuery(hql);
			setQueryArgs(queryResult, args);
			return new PageImpl<>(queryResult.list(), page, totalElements);
		}

		// 构造排序信息
		appendSort.append(" order by ");
		for (Sort.Order order : page.getOrders()) {
			if (order.isAscending()) {
				appendSort.append(order.getProperty()).append(" asc,");
			} else {
				appendSort.append(order.getProperty()).append(" desc,");
			}
		}
		queryResult = getSession().createQuery(appendSort.deleteCharAt(appendSort.length() - 1).toString());
		setQueryArgs(queryResult, args);
		return new PageImpl<>(queryResult.list(), page, totalElements);
	}	
	
	/** 
	 * 构建 where 子句
	 * 
	 * @param propertyName
	 * @param propertyValue
	 * @return
	 * @author dongbz 2016-08-02
	 */
	private String buildWhereClause(String propertyName, Object propertyValue) {
		StringBuffer whereClause = new StringBuffer(" and ");
		if (propertyValue instanceof Collection) {
			Collection<?> col = (Collection<?>) propertyValue;
			if (col.size() == 1) {
				whereClause.append(propertyName).append(" = :").append(propertyName);
			} else {
				whereClause.append(propertyName).append(" in (:").append(propertyName).append(")");
			}
		} else if (propertyValue instanceof Object[]) {
			Object[] arr = (Object[]) propertyValue;
			if (arr.length == 1) {
				whereClause.append(propertyName).append(" = :").append(propertyName);
			} else {
				whereClause.append(propertyName).append(" in (:").append(propertyName).append(")");
			}
		} else {
			whereClause.append(propertyName).append(" = :").append(propertyName);
		}
		return whereClause.toString();
	}
	
	/**
	 * 设置属性的值
	 * 
	 * @param query
	 * @param propertyName
	 * @param propertyValue
	 * @author dongbz 2016-08-02
	 */
	private void setQueryParameter(Query query, String propertyName, Object propertyValue) {
		if (propertyValue instanceof Collection) {
			Collection<?> col = (Collection<?>) propertyValue;
			if (col.size() == 1) {
				query.setParameter(propertyName, propertyValue);
			} else {
				query.setParameterList(propertyName, col);
			}
		} else if (propertyValue instanceof Object[]) {
			Object[] arr = (Object[]) propertyValue;
			if (arr.length == 1) {
				query.setParameter(propertyName, propertyValue);
			} else {
				query.setParameterList(propertyName, arr);
			}
		} else {
			query.setParameter(propertyName, propertyValue);
		}
	}	
	
	private void setQueryArgs(Query query, final Map<String, Object> args) {
		if (args == null || args.size() == 0) {
			return;
		}
		
		for (Map.Entry<String, Object> arg : args.entrySet()) {
			setQueryParameter(query, arg.getKey(), arg.getValue());
		}
	}	
	
	public <T> List<T> findByExample(Class<T> entityClass, T example) {
		return findByExample(entityClass, example, null).getContent();
	}
	
	public <T> org.springframework.data.domain.Page<T> findByExample(Class<T> entityClass, T example, Page page) {
		Assert.notNull(entityClass);
		Assert.notNull(example);
		
		Criteria criteria = getSession().createCriteria(entityClass);
		criteria.add(Example.create(example));
		return findByCriteria(criteria, page);
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> findByDetachedCriteria(DetachedCriteria dc) {
		return (List<T>) findByDetachedCriteria(dc, null).getContent();
	}
	
	public <T> org.springframework.data.domain.Page<T> findByDetachedCriteria(DetachedCriteria dc, Page page) {
		Assert.notNull(dc);
		
		return findByCriteria(dc.getExecutableCriteria(getSession()), page);
	}
	
	@SuppressWarnings("unchecked")
	private <T> org.springframework.data.domain.Page<T> findByCriteria(Criteria criteria, Page page) {
		Assert.notNull(criteria);
		
		if (page == null) {
			return new PageImpl<>(criteria.list());
		}
		
		criteria.setProjection(Projections.rowCount());
		long totalElements = Long.parseLong(criteria.uniqueResult().toString());
		if (totalElements == 0) {
			return new PageImpl<>(new ArrayList<T>(0), page, totalElements);
		}
		
		for (Sort.Order order : page.getOrders()) {
			if (order.isAscending()) {
				criteria.addOrder(Order.asc(order.getProperty()));
			} else {
				criteria.addOrder(Order.desc(order.getProperty()));
			}
		}
		criteria.setFirstResult(page.getPageNumber() * page.getPageSize());
		criteria.setMaxResults(page.getPageSize());
		criteria.setProjection(null);
		return new PageImpl<>(criteria.list(), page, totalElements);
	}
	
	/**
	 * 查找满足条件的指定属性集合
	 * 
	 * @param entityClass	实体类
	 * @param returnClass	返回属性类型
	 * @param selected		指定属性名称
	 * @param wheres
	 * @param values
	 * @return
	 * @author dongbz 
	 * @since  2016-08-04
	 */
	@SuppressWarnings("unchecked")
	public <T, E> List<E> findSelecteds(Class<T> entityClass, Class<E> returnClass, String selected,
			List<String> wheres, final Object ...values) {
		Assert.notNull(returnClass);
		Assert.hasText(selected);
		org.springframework.util.Assert.notEmpty(wheres);
		org.springframework.util.Assert.notEmpty(values);
		org.springframework.util.Assert.isTrue(wheres.size() == values.length);		
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select distinct ").append(selected);
		hql.append(" from ").append(entityClass.getName());
		hql.append(" where 1 = 1 ");
		for (int i = 0; i < wheres.size(); i++) {
			hql.append(buildWhereClause(wheres.get(i), values[i]));
		}
			
		Query query = getSession().createQuery(hql.toString());
		for (int i = 0; i < wheres.size(); i++) {
			setQueryParameter(query, wheres.get(i), values[i]);
		}

		return query.list();
	}	
	
	public <T> void save(T entity) {
		Assert.notNull(entity);
		
		getSession().save(entity);
	}
	
	public <T> void saveAll(final Collection<T> entities) {
		org.springframework.util.Assert.notEmpty(entities);
		
		int size = 0;
		Session session = getSession();
		for (T entity : entities) {
			save(entity);
			if (++size % BATCH == 0) {
				session.flush();
				session.clear();
			}
		}
	}
	
	public <T> void update(T entity) {
		Assert.notNull(entity);
		
		getSession().update(entity);
	}
	
	public <T> void updateAll(final Collection<T> entities) {
		org.springframework.util.Assert.notEmpty(entities);
		
		int size = 0;
		Session session = getSession();
		for (T entity : entities) {
			update(entity);
			if (++size % BATCH == 0) {
				session.flush();
				session.clear();
			}
		}
	}
	
	public <T> void saveOrUpdate(T entity) {
		Assert.notNull(entity);
		
		getSession().saveOrUpdate(entity);
	}
	
	public <T> void delete(T entity) {
		Assert.notNull(entity);
		
		getSession().delete(entity);
	}
	
	public <T> void deleteById(Class<T> entityClass, Serializable id) {
		Assert.notNull(entityClass);
		Assert.notNull(id);
		
		delete(get(entityClass, id));
	}
	
	public <T> void deleteAll(Collection<T> entities) {
		org.springframework.util.Assert.notEmpty(entities);
		
		int size = 0;
		Session session = getSession();
		for (T entity : entities) {
			delete(entity);
			if (++size % BATCH == 0) {
				session.flush();
				session.clear();
			}
		}
	}
	
}
