package org.zhiqsyr.mfmall.web.conf;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zhiqsyr.mfmall.dao.BaseDao;

import javax.persistence.EntityManagerFactory;

/**
 * DAO 相关配置
 * 
 * @author dongbz 
 * @since  2016-08-04
 */
@Configuration
public class DaoConf {

	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	/** 注入 sessionFactory */
	@Bean  
	public SessionFactory sessionFactory(HibernateEntityManagerFactory hemf){  
	    return hemf.getSessionFactory();  
	}  
	
	@Bean
	public BaseDao autowireBaseDao() {
		BaseDao baseDao = new BaseDao();
		baseDao.setSessionFactory(entityManagerFactory.unwrap(SessionFactory.class));
		return baseDao;
	}
	
}
