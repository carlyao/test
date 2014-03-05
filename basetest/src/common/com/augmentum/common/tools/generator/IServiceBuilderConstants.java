package com.augmentum.common.tools.generator;

public interface IServiceBuilderConstants
{
	// Element names
	//
	String ELEMENT_FINDER_ORDER = "finderOrder";

	// Element attributes
	//
	String ATT_EJB_SERVICE = "ejb-service";
	String ATT_CACHEABLE = "cacheable";
	String ATT_TX_MANAGER = "tx-manager";
	String ATT_DATASOURCE = "datasource";
	String ATT_SESSION_FACTORY = "session-factory";
	String ATT_NULLS_LAST = "nulls-last";

	String ATT_DB_TYPE = "db-type";
	String ATT_DB_SIZE = "db-size";
	String ATT_DB_DEFAULT_VALUE = "db-default-value";
	String ATT_DB_NOT_NULL = "db-not-null";
	String ATT_DB_COLLATE = "db-collate";
	String ATT_INSERT = "insert";
	String ATT_UPDATE = "update";

	// DEFAULT VALUES
	//
	String DEF_DOUBLE_SIZE = "20,10";
	String DEF_INTEGER_SIZE = "20,0";
	int DEF_STRING_SIZE = 75;
	String DEF_TX_MANAGER = "scTransactionManager";
	String DEF_DATASOURCE = "scDataSource";
	String DEF_SESSION_FACTORY = "scSessionFactory";

	// HINTS NAMES
	//
	String HINTS_NAME_MAX_LENGTH = "max-length";
}
