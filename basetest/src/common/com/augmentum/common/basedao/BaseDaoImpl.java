package com.augmentum.common.basedao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augmentum.common.basedao.support.GenericsUtils;
import com.augmentum.common.util.PagingData;


/**
 * This class is used for ...
 * 
 * @author carl.yao 2013-8-1 上午10:06:02
 */
@Transactional(rollbackFor = Exception.class)
public class BaseDaoImpl<T> implements BaseDao<T> {
	@Autowired
	private SessionFactory sessionFactory;
	@SuppressWarnings("rawtypes")
	private Class entityClass;
	private String pkname;
	private final String HQL_LIST_ALL;
	private final String HQL_COUNT_ALL;

	@SuppressWarnings("rawtypes")
	public Class getEntityClass() {
		return entityClass;
	}

	@SuppressWarnings("rawtypes")
	public void setEntityClass(Class entityClass) {
		this.entityClass = entityClass;
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("rawtypes")
	public BaseDaoImpl() {
		System.out.println("1=========="+this.getClass().getGenericSuperclass().toString());
//		System.out.println(((ParameterizedType) this.getClass()
//				.getGenericSuperclass()).getActualTypeArguments().toString());
		System.out.println("2=========="+GenericsUtils.getSuperClassGenricType(getClass().getSuperclass()));
		entityClass = GenericsUtils.getSuperClassGenricType(getClass().getSuperclass());
//		Type type = this.getClass().getGenericSuperclass();
//		if (type.toString().indexOf("BaseDao") != -1) {
//			ParameterizedType type1 = (ParameterizedType) type;
//			Type[] types = type1.getActualTypeArguments();
//			setEntityClass((Class) types[0]);
//		} else {
//			type = ((Class) type).getGenericSuperclass();
//			ParameterizedType type1 = (ParameterizedType) type;
//			Type[] types = type1.getActualTypeArguments();
//			setEntityClass((Class) types[0]);
//		}
		getPkname();
		HQL_LIST_ALL = "from " + this.entityClass.getSimpleName()
				+ " order by " + pkname + " desc";
		HQL_COUNT_ALL = "select count(*) from "
				+ this.entityClass.getSimpleName();
	}

	/**
	 * @return
	 */
	public String getPkname() {
		Field[] fields = this.entityClass.getDeclaredFields();
		for (Field field : fields) {
			field.isAnnotationPresent(Id.class);
			this.pkname = field.getName();
			break;
		}
		return pkname;
	}

	/**
	 * 
	 * @param t
	 * @throws HibernateException
	 */
	public void save(T t) throws HibernateException {
		Session session = getSession();
		session.saveOrUpdate(t);
	}

	/**
	 * 
	 * @param t
	 * @throws HibernateException
	 */
	public void update(T t) throws HibernateException {
		Session session = getSession();
		session.update(t);
	}

	/**
	 * 
	 * @param t
	 * @throws HibernateException
	 */
	public void delete(T t) throws HibernateException {
		Session session = getSession();
		session.delete(t);
	}

	/**
	 * 
	 * @param id
	 * @throws HibernateException
	 */
	@SuppressWarnings("unchecked")
	public T findById(Serializable id) {
		Session session = getSession();
		T t = null;
		t = (T) session.get(getEntityClass(), id);
		return t;
	}

	/**
	 * 
	 * @throws HibernateException
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		List<T> list = new ArrayList<T>();
		Session session = getSession();
		Query query = session.createQuery(HQL_LIST_ALL);
		list = query.list();
		return list;
	}

	/**
	 * 
	 * @throws HibernateException
	 */
	public int findAllCount() {
		Session session = getSession();
		Query query = session.createQuery(HQL_COUNT_ALL);
		Long count = (Long) query.uniqueResult();
		if(null == count){
			return 0;
		}
		return count.intValue();
	}

	/**
	 * 
	 * @param criteria
	 * @throws HibernateException
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(Criteria criteria) {
		List<T> list = new ArrayList<T>();
		Session session = getSession();
		Criteria criteria1 = session.createCriteria(getEntityClass());
		criteria1 = criteria;
		list = criteria1.list();
		return list;
	}

	/**
	 * 
	 * @param t
	 * @throws HibernateException
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByExample(T t) {
		List<T> list = new ArrayList<T>();
		Session session = getSession();
		Example example = Example.create(t);
		Criteria criteria = session.createCriteria(getEntityClass());
		criteria.add(example);
		list = criteria.list();
		return list;
	}

	/**
	 * 
	 * @param hql
	 * @param objects
	 * @throws HibernateException
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(String hql, final Object... objects)
			{
		List list = new ArrayList();
		Session session = getSession();
		Query query = session.createQuery(hql);
		for (int i = 0; i < objects.length; i++) {
			query.setParameter(i, objects[i]);
		}
		list = query.list();
		return  list;
	}

	/**
	 * SQL��ѯ
	 * 
	 * @param hql
	 * @param objects
	 * @throws HibernateException
	 */
	@SuppressWarnings("unchecked")
	public List<T> findBySql(String sql, final Object... objects) {
		List list = new ArrayList();
		Session session = getSession();
		Query query = session.createSQLQuery(sql);
		for (int i = 0; i < objects.length; i++) {
			query.setParameter(i, objects[i]);
		}
		list = query.list();
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.augmentum.zongmu.dao.BaseDao#findByPager(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PagingData<T> findByPager(int firstResult,int pagesize) {
		List<T> list = new ArrayList<T>();
		Session session = getSession();
		Query query = session.createQuery(HQL_LIST_ALL);
		query.setFirstResult(firstResult);
		query.setMaxResults(pagesize);
		list = query.list();
		int count = findAllCount();
		PagingData<T> pagingData = new PagingData<T>();
		pagingData.setPageNo(firstResult/pagesize);
		pagingData.setTotalResult(count);
		int totalPages = 0;
		if (count % 10 == 0) {
			totalPages = count / 10;
		} else {
			totalPages = count / 10 + 1;
		}
		pagingData.setTotalPage(totalPages);
		pagingData.setResult(list);
		return pagingData;
	}

	@Override
	public List<T> getList(String hql, int offset, int length, Object... values) {
		List list = new ArrayList();
		Session session = getSession();
		Query query = session.createQuery(hql);
		query.setFirstResult(offset);
		query.setMaxResults(length);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		list = query.list();
		return list;
	}

	@Override
	public int getCounts() {
		List list = findAll();
		if(list != null && list.size() > 0){
			return list.size();
		}
		return 0;
	}
}
