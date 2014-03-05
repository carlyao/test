package com.augmentum.common.baseService;

import java.io.Serializable;
import java.util.List;

import com.augmentum.common.basedao.BaseDao;
import com.augmentum.common.basemodel.BaseModel;

/**   
 * This class is used for ...   
 * @author carl.yao  
 *  2013-8-1 下午01:51:40   
 */
public interface BaseService<T extends BaseModel, D extends BaseDao<T>,ID extends Serializable> {

	T findById(ID id);

    T fetchById(ID id);

    List<T> findAll();

    List<T> findPageByPage(int firstResult, int maxResults);

    List<T> findByExample(T exampleInstance, String... excludeProperty);

    T update(T entity);
    
    T merge(T entity);
    
    T persist(T entity);

    void delete(T entity);

    void delete(ID id);
}

