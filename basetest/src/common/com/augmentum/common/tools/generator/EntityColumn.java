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

import com.augmentum.common.util.TextFormatter;

/**
 * <a href="EntityColumn.java.html"><b><i>View Source</i></b></a>
 * 
 * @author Brian Wing Shun Chan
 * @author Charles May
 * 
 */
public class EntityColumn implements Cloneable, Comparable<EntityColumn> {

	private String _name;
	private String _dbName;
	private String _type;
	private boolean _primary;
	private String _methodName;
	private String _ejbName;
	private String[] _mappingKey;
	private String _mappingTable;
	private boolean _caseSensitive;
	private boolean _orderByAscending;
	private String _comparator;
	private String _idType;
	private String _idParam;
	private boolean _convertNull;

	private String dbType;
	private String dbSize;
	private String defaultValue;
	private boolean notNull;
	private String dbCollate;

	private boolean noInsert;
	private boolean noUpdate;

	private String idParamTable;
	private String idParamColumn;
	private String idParamMaxLo;

	private String relationship;
	private String ejbClass;
	private String cascade;
	private String orderBy;
	private String lazy;
	private boolean isSubclassDiscriminator;
	private String formula;
	private String inverse;
	private boolean fkConstrained;
	private boolean oneToOne;
	private boolean doIgnoreNotFound;
	private String fetch;
	private String propertyRef;
	private boolean unique;
	private boolean version;

	public EntityColumn(String name) {
		this(name, null, null, false, null, null, null, true, true, null, null,
				null, null, null, null, true);
	}

	public EntityColumn(String name, String dbName, String type,
			boolean primary, String ejbName, String[] mappingKey,
			String mappingTable, String idType, String idParam,
			String idParamTable, String idParamColumn, String idParamMaxLo,
			boolean convertNull) {

		this(name, dbName, type, primary, ejbName, mappingKey, mappingTable,
				true, true, null, idType, idParam, idParamTable, idParamColumn,
				idParamMaxLo, convertNull);
	}

	public EntityColumn(String name, String dbName, String type,
			boolean primary, String ejbName, String[] mappingKey,
			String mappingTable, boolean caseSensitive,
			boolean orderByAscending, String comparator, String idType,
			String idParam, String idParamTable, String idParamColumn,
			String idParamMaxLo, boolean convertNull) {

		this(name, dbName, type, primary, ejbName, mappingKey, mappingTable,
				caseSensitive, orderByAscending, comparator, idType, idParam,
				idParamTable, idParamColumn, idParamMaxLo, convertNull, null,
				null, null, false, null, false, false, null, null, null, null, null, false, null, null,
				false, false, false, null, null, false, false);
	}

	public EntityColumn(String name, String dbName, String type,
			boolean primary, String ejbName, String[] mappingKey,
			String mappingTable, boolean caseSensitive,
			boolean orderByAscending, String comparator, String idType,
			String idParam, String idParamTable, String idParamColumn,
			String idParamMaxLo, boolean convertNull, String dbType,
			String dbSize, String defaultValue, boolean notNull, String dbCollate,
			boolean noInsert, boolean noUpdate, 
			String relationship, String ejbClass, String cascade, String orderBy, String lazy, 
			boolean isSubclassDiscriminator, String formula, String inverse,
			boolean fkConstrained, boolean oneToOne, boolean doIgnoreNotFound,
			String fetch, String propertyRef, boolean unique, boolean version) {

		_name = name;
		_dbName = dbName;
		_type = type;
		_primary = primary;
		_methodName = TextFormatter.format(name, TextFormatter.G);
		_ejbName = ejbName;
		_mappingKey = mappingKey;
		_mappingTable = mappingTable;
		_caseSensitive = caseSensitive;
		_orderByAscending = orderByAscending;
		_comparator = comparator;
		_idType = idType;
		_idParam = idParam;
		_convertNull = convertNull;

		this.dbType = dbType;
		this.dbSize = dbSize;
		this.defaultValue = defaultValue;
		this.notNull = notNull;
		this.dbCollate = dbCollate;

		this.noInsert = noInsert;
		this.noUpdate = noUpdate;

		this.idParamTable = idParamTable;
		this.idParamColumn = idParamColumn;
		this.idParamMaxLo = idParamMaxLo;
		
		this.relationship = relationship;
		this.ejbClass = ejbClass;
		
		this.cascade = cascade;
		this.orderBy = orderBy;
		this.isSubclassDiscriminator = isSubclassDiscriminator;
		this.lazy = lazy;
		this.formula = formula;
		this.inverse = inverse;
		this.fkConstrained = fkConstrained;
		this.oneToOne = oneToOne;
		this.doIgnoreNotFound = doIgnoreNotFound;
		this.fetch = fetch;
		this.propertyRef = propertyRef;
		this.unique = unique;
		this.version = version;
	}

	public String getName() {
		return _name;
	}

	public String getDBName() {
		return _dbName;
	}

	public void setDBName(String dbName) {
		_dbName = dbName;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	public boolean isPrimitiveType() {
		if (Character.isLowerCase(_type.charAt(0))) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isCollection() {
		if (_type.equals("Collection")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isPrimary() {
		return _primary;
	}

	public String getMethodName() {
		return _methodName;
	}

	public String getEJBName() {
		return _ejbName;
	}

	public String[] getMappingKeys() {
		return _mappingKey;
	}

	public String getMappingTable() {
		return _mappingTable;
	}

	public boolean isMappingOneToMany() {
		return relationship.equals("oneToMany");
		//return Validator.isNotNull(_mappingKey);
	}

	public boolean isMappingManyToMany() {
		return relationship.equals("manyToMany");
		//return Validator.isNotNull(_mappingTable);
	}

	public boolean isCaseSensitive() {
		return _caseSensitive;
	}

	public void setCaseSensitive(boolean caseSensitive) {
		_caseSensitive = caseSensitive;
	}

	public boolean isOrderByAscending() {
		return _orderByAscending;
	}

	public void setOrderByAscending(boolean orderByAscending) {
		_orderByAscending = orderByAscending;
	}

	public String getComparator() {
		return _comparator;
	}

	public void setComparator(String comparator) {
		_comparator = comparator;
	}

	public String getIdType() {
		return _idType;
	}

	public void setIdType(String idType) {
		_idType = idType;
	}

	public String getIdParam() {
		return _idParam;
	}

	public void setIdParam(String idParam) {
		_idParam = idParam;
	}

	public boolean isConvertNull() {
		return _convertNull;
	}

	public void setConvertNull(boolean convertNull) {
		_convertNull = convertNull;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getDbSize() {
		return dbSize;
	}

	public void setDbSize(String dbSize) {
		this.dbSize = dbSize;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public boolean isNotNull() {
		return notNull;
	}

	public void setNotNull(boolean notNull) {
		this.notNull = notNull;
	}
	
	public String getDbCollate() {
		return dbCollate;
	}

	public void setDbCollate(String dbCollate) {
		this.dbCollate = dbCollate;
	}

	public boolean isNoInsert() {
		return noInsert;
	}

	public void setNoInsert(boolean noInsert) {
		this.noInsert = noInsert;
	}

	public boolean isNoUpdate() {
		return noUpdate;
	}

	public void setNoUpdate(boolean noUpdate) {
		this.noUpdate = noUpdate;
	}

	public boolean hasRelationship() {
		return getRelationship() != null;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getEjbClass() {
		return ejbClass;
	}

	public void setEjbClass(String ejbClass) {
		this.ejbClass = ejbClass;
	}

	public String getCascade() {
		return cascade;
	}

	public void setCascade(String cascade) {
		this.cascade = cascade;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getLazy() {
		return lazy;
	}

	public void setLazy(String lazy) {
		this.lazy = lazy;
	}
	
	public void setIsSubclassDiscriminator(boolean isSubclassDiscriminator) {
		this.isSubclassDiscriminator = isSubclassDiscriminator;
	}

	public boolean isSubclassDiscriminator() {
		return isSubclassDiscriminator;
	}
	
	public Object clone() {
		return new EntityColumn(getName(), getDBName(), getType(), isPrimary(),
				getEJBName(), getMappingKeys(), getMappingTable(),
				isCaseSensitive(), isOrderByAscending(), getComparator(),
				getIdType(), getIdParam(), getIdParamTable(),
				getIdParamColumn(), getIdParamMaxLo(), isConvertNull(),
				getDbType(), getDbSize(), getDefaultValue(), isNotNull(), getDbCollate(),
				isNoInsert(), isNoUpdate(), getRelationship(), getEjbClass(), getCascade(), 
				getOrderBy(), getLazy(), isSubclassDiscriminator(), getFormula(),
				getInverse(), isFkConstrained(), isOneToOne(), isDoIgnoreNotFound(),
				getFetch(), getPropertyRef(), getUnique(), isVersion());
	}

	public boolean equals(Object obj) {
		EntityColumn col = (EntityColumn) obj;

		String name = col.getName();

		if (_name.equals(name)) {
			return true;
		} else {
			return false;
		}
	}

	public String getIdParamTable() {
		return idParamTable;
	}

	public void setIdParamTable(String idParamTable) {
		this.idParamTable = idParamTable;
	}

	public String getIdParamColumn() {
		return idParamColumn;
	}

	public void setIdParamColumn(String idParamColumn) {
		this.idParamColumn = idParamColumn;
	}

	public String getIdParamMaxLo() {
		return idParamMaxLo;
	}

	public void setIdParamMaxLo(String idParamMaxLo) {
		this.idParamMaxLo = idParamMaxLo;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getInverse() {
		return inverse;
	}

	public void setInverse(String inverse) {
		this.inverse = inverse;
	}

	public boolean isFkConstrained() {
		return fkConstrained;
	}

	public void setFkConstrained(boolean fkConstrained) {
		this.fkConstrained = fkConstrained;
	}

	public boolean isOneToOne() {
		return oneToOne;
	}

	public void setOneToOne(boolean oneToOne) {
		this.oneToOne = oneToOne;
	}

	public boolean isDoIgnoreNotFound() {
		return doIgnoreNotFound;
	}

	public void setDoIgnoreNotFound(boolean doIgnoreNotFound) {
		this.doIgnoreNotFound = doIgnoreNotFound;
	}

	public String getFetch() {
		return fetch;
	}

	public void setFetch(String fetch) {
		this.fetch = fetch;
	}

	public String getPropertyRef() {
		return propertyRef;
	}

	public void setPropertyRef(String propertyRef) {
		this.propertyRef = propertyRef;
	}

	public boolean getUnique() {
		return unique;
	}

	public void isUnique(boolean unique) {
		this.unique = unique;
	}

	public boolean isVersion() {
		return version;
	}

	public void setVersion(boolean version) {
		this.version = version;
	}
	
	// return in order that Hibernate mapping XML DTD expects
	public int compareTo(EntityColumn column2) {
		int result = order(this) - order(column2);
		
		return (result != 0) ? result : getName().compareTo(column2.getName());   
	}
	
	protected int order(EntityColumn column) {
		// determine the order
		if (column.isSubclassDiscriminator()) {
			return 1;
		}
		else if (column.isVersion()) {
			return 2;
		}
		else if (!column.hasRelationship()) {
			return 3;
		}
		else {
			return 4;
		}
	}
}