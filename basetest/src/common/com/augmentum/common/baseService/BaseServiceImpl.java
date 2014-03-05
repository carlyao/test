package com.augmentum.common.baseService;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.augmentum.common.basedao.BaseDao;
import com.augmentum.common.basemodel.BaseModel;

/**   
 * This class is used for ...   
 * @author carl.yao  
 *  2013-8-1 下午01:55:52   
 */
public class BaseServiceImpl<T extends BaseModel,D extends BaseDao<T>, ID extends Serializable> implements BaseService<T, D, ID>,ApplicationContextAware{

	private ApplicationContext ctx;
	private D dao;
	
	public D getDao() {
		if (dao == null) {
			this.dao = instantiateDao();
		}

		return dao;
	}

	@Override
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		this.ctx = ctx;
	}
	
	@SuppressWarnings("unchecked")
	private D instantiateDao() {
		Class<D> daoClass = null;

		Class clazz = getClass();
		boolean correctType = false;
		while (!correctType) {
			Type type = clazz.getGenericSuperclass();
			if (type instanceof ParameterizedType) {
				Type rawType = ((ParameterizedType) type).getRawType();
				if (rawType instanceof Class) {
					if (((Class) rawType).getName().equalsIgnoreCase(
							BaseServiceImpl.class.getName())) {
						correctType = true;
						System.out.println(((ParameterizedType) type).getActualTypeArguments());
						daoClass = (Class<D>) ((ParameterizedType) type).getActualTypeArguments()[1];

						break;
					}
				}
			}

			clazz = (Class) clazz.getGenericSuperclass();
		}
		return (D) ctx.getBean(daoClass);
	}

	public void setDao(D dao) {
		this.dao = dao;
	}
	
	@Override
	public void delete(T entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(ID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T fetchById(ID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findByExample(T exampleInstance, String... excludeProperty) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T findById(ID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findPageByPage(int firstResult, int maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T merge(T entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T persist(T entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T update(T entity) {
		// TODO Auto-generated method stub
		return null;
	}

}

