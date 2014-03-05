/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.augmentum.common.tools.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.augmentum.common.util.GetterUtil;
import com.augmentum.common.util.TextFormatter;

/**
 * <a href="Entity.java.html"><b><i>View Source</i></b></a>
 * 
 * @author Brian Wing Shun Chan
 * 
 */
public class Entity {

    public static final String DEFAULT_DATA_SOURCE = "gpDataSource";

    public static final String DEFAULT_SESSION_FACTORY = "gpSessionFactory";

    public static final String DEFAULT_TX_MANAGER = "gpTransactionManager";

    private String _packagePath;
//    private String _portletName;
//    private String _portletShortName;
    private String _name;
    private String _table;
    private boolean _uuid;
    private boolean _localService;
//    private String _persistenceClass;
    private String _dataSource;
    private String _sessionFactory;
    private String _txManager;
    private boolean _cacheEnabled;
    private List<EntityColumn> _pkList;
    private List<EntityColumn> _regularColList;
    private List<EntityColumn> _collectionList;
    private List<EntityColumn> _oneToOneList;
    private List<EntityColumn> _oneToManyList;
    private List<EntityColumn> _manyToOneList;
    private List<EntityColumn> _manyToManyList;
    
    private List<EntityColumn> _columnList;
    private Map<String, List<String>> _uniqueColumns;

	private EntityOrder _order;
    private List<EntityFinder> _finderList;
    private boolean _portalReference;
    private List<Entity> _referenceList;
    private List<String> _txRequiredList;
    private boolean refServiceOnly;
    private EntityColumn _discriminator;
    private EntitySuperclass _superclass;

    private List<Entity> _subclasses;

	private String _modelBase;
    private String _daoBase;
    private String _serviceBase;
    private String _daoIntfBase;
    private String _serviceIntfBase;
    

    public static EntityColumn getColumn(String name, List<EntityColumn> columnList) {

        int pos = columnList.indexOf(new EntityColumn(name));

        if (pos == -1) {
            throw new RuntimeException("Column " + name + " not found");
        }

        return columnList.get(pos);
    }

    public Entity(String name) {
        this(null, name, null, false, false, null, null, null, true, null, null, null, null, null, 
                null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    public Entity(String packagePath, String name,
            String table, boolean uuid, boolean localService,
            String dataSource, String sessionFactory, String txManager, boolean cacheEnabled,
            List<EntityColumn> pkList, List<EntityColumn> regularColList, List<EntityColumn> collectionList, 
            List<EntityColumn> oneToOneList, List<EntityColumn> oneToManyList, List<EntityColumn> manyToOneList, List<EntityColumn> manyToManyList,
            List<EntityColumn> columnList, Map<String, List<String>> uniqueColumns, EntityOrder order,
            List<EntityFinder> finderList, List<Entity> referenceList, List<String> txRequiredList,
            EntityColumn discriminator, EntitySuperclass superclass, String modelBase, String daoBase, String serviceBase, String daoIntfBase, String serviceIntfBase) {

        _packagePath = packagePath;
//        _portletName = portletName;
//        _portletShortName = portletShortName;
        _name = name;
        _table = table;
        _uuid = uuid;
        _localService = localService;
//        _persistenceClass = persistenceClass;
        _dataSource = GetterUtil.getString(dataSource, DEFAULT_DATA_SOURCE);
        _sessionFactory = GetterUtil.getString(sessionFactory, DEFAULT_SESSION_FACTORY);
        _txManager = GetterUtil.getString(txManager, DEFAULT_TX_MANAGER);
        _cacheEnabled = cacheEnabled;
        _pkList = pkList;
        _regularColList = regularColList;
        _collectionList = collectionList;
        _oneToOneList = oneToOneList;
        _oneToManyList = oneToManyList;
        _manyToOneList = manyToOneList;
        _manyToManyList = manyToManyList;
        _columnList = columnList;
        _uniqueColumns = uniqueColumns;
        _order = order;
        _finderList = finderList;
        _referenceList = referenceList;
        _txRequiredList = txRequiredList;
        _discriminator = discriminator;
        _superclass = superclass;
        
        _subclasses = new ArrayList<Entity>();
        
        _modelBase = modelBase;
        _daoBase = daoBase;
        _serviceBase = serviceBase;
        _daoIntfBase = daoIntfBase;
        _serviceIntfBase = serviceIntfBase;
    }

    public String getPackagePath() {
        return _packagePath;
    }

//    public String getPortletName() {
//        return _portletName;
//    }
//
//    public String getPortletShortName() {
//        return _portletShortName;
//    }

    public String getName() {
        return _name;
    }

    public String getNames() {
        return TextFormatter.formatPlural(new String(_name));
    }

    public String getVarName() {
        return TextFormatter.format(_name, TextFormatter.I);
    }

    public String getVarNames() {
        return TextFormatter.formatPlural(new String(getVarName()));
    }

//    public String getShortName() {
//        if (_name.startsWith(_portletShortName)) {
//            return _name.substring(_portletShortName.length());
//        } else {
//            return _name;
//        }
//    }

    public String getTable() {
        return _table;
    }

    public boolean hasUuid() {
        return _uuid;
    }

    public boolean hasLocalService() {
        return _localService;
    }

    public String getDataSource() {
        return _dataSource;
    }

    public boolean isDefaultDataSource() {
        if (_dataSource.equals(DEFAULT_DATA_SOURCE)) {
            return true;
        } else {
            return false;
        }
    }

    public String getSessionFactory() {
        return _sessionFactory;
    }

    public boolean isDefaultSessionFactory() {
        if (_sessionFactory.equals(DEFAULT_SESSION_FACTORY)) {
            return true;
        } else {
            return false;
        }
    }

    public String getTXManager() {
        return _txManager;
    }

    public boolean isDefaultTXManager() {
        if (_txManager.equals(DEFAULT_TX_MANAGER)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isCacheEnabled() {
        return _cacheEnabled;
    }

    public String getPKClassName() {
        if (hasCompoundPK()) {
            return _name + "PK";
        } else {
            EntityColumn col = _pkList.get(0);

            return col.getType();
        }
    }

    public String getPKVarName() {
        if (hasCompoundPK()) {
            return getVarName() + "PK";
        } else {
            EntityColumn col = _pkList.get(0);

            return col.getName();
        }
    }

    public boolean hasPrimitivePK() {
        if (hasCompoundPK()) {
            return false;
        } else {
            EntityColumn col = _pkList.get(0);

            if (col.isPrimitiveType()) {
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean hasCompoundPK() {
        if (_pkList.size() > 1) {
            return true;
        } else {
            return false;
        }
    }

    public List<EntityColumn> getPKList() {
        return _pkList;
    }

    public List<EntityColumn> getRegularColList() {
        return _regularColList;
    }

    public List<EntityColumn> getCollectionList() {
        return _collectionList;
    }

    public List<EntityColumn> getOneToOneList() {
        return _oneToOneList;
    }

    public List<EntityColumn> getOneToManyList() {
        return _oneToManyList;
    }

    public List<EntityColumn> getManyToOneList() {
        return _manyToOneList;
    }

    public List<EntityColumn> getManyToManyList() {
        return _manyToManyList;
    }

    public List<EntityColumn> getColumnList() {
        return _columnList;
    }

    public boolean hasColumns() {
        if ((_columnList == null) || (_columnList.size() == 0)) {
            return false;
        } else {
            return true;
        }
    }
    
    public Map<String, List<String>> getUniqueColumns() {
		return _uniqueColumns;
	}

	public void setUniqueColumns(Map<String, List<String>> uniqueColumns) {
		_uniqueColumns = uniqueColumns;
	}

    public EntityOrder getOrder() {
        return _order;
    }

    public boolean isOrdered() {
        if (_order != null) {
            return true;
        } else {
            return false;
        }
    }

    public EntityColumn getDiscriminator() {
        return _discriminator;
    }

    public boolean isSuperclass() {
        if (_discriminator != null) {
            return true;
        } else {
            return false;
        }
    }

    public EntitySuperclass getSuperclass() {
        return _superclass;
    }

    public boolean isSubclass() {
        if (_superclass != null) {
            return true;
        } else {
            return false;
        }
    }

    public List<EntityFinder> getFinderList() {
        return _finderList;
    }

    public boolean isPortalReference() {
        return _portalReference;
    }

    public void setPortalReference(boolean portalReference) {
        _portalReference = portalReference;
    }

    public List<Entity> getReferenceList() {
        return _referenceList;
    }

    public List<String> getTxRequiredList() {
        return _txRequiredList;
    }

    public EntityColumn getColumn(String name) {
        return getColumn(name, _columnList);
    }

    public EntityColumn getColumnByMappingTable(String mappingTable) {
        for (int i = 0; i < _columnList.size(); i++) {
            EntityColumn col = _columnList.get(i);

            if (col.getMappingTable() != null && col.getMappingTable().equals(mappingTable)) {

                return col;
            }
        }

        return null;
    }

    public boolean equals(Object obj) {
        Entity entity = (Entity) obj;

        String name = entity.getName();

        if (_name.equals(name)) {
            return true;
        } else {
            return false;
        }
    }

	public void setPackagePath(String path) {
		_packagePath = path;
	}

	public boolean isRefServiceOnly() {
		return refServiceOnly;
	}

	public void setRefServiceOnly(boolean refServiceOnly) {
		this.refServiceOnly = refServiceOnly;
	}
	
    public List<Entity> getSubclasses() {
        return _subclasses;
    }
    
    public String getModelBase() {
		return _modelBase;
	}

	public void setModelBase(String modelBase) {
		_modelBase = modelBase;
	}

	public String getDAOBase() {
		return _daoBase;
	}

	public void setDAOBase(String daoBase) {
		_daoBase = daoBase;
	}

	public String getServiceBase() {
		return _serviceBase;
	}

	public void setServiceBase(String serviceBase) {
		_serviceBase = serviceBase;
	}
	
	public String getDAOIntfBase() {
		return _daoIntfBase;
	}

	public void setDAOIntfBase(String daoIntfBase) {
		_daoIntfBase = daoIntfBase;
	}

	public String getServiceIntfBase() {
		return _serviceIntfBase;
	}

	public void setServiceIntfBase(String serviceIntfBase) {
		_serviceIntfBase = serviceIntfBase;
	}
}