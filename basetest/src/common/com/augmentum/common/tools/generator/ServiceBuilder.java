package com.augmentum.common.tools.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.augmentum.common.freemaker.FreeMarkerUtil;
import com.augmentum.common.util.ArrayUtil;
import com.augmentum.common.util.ArrayUtil_IW;
import com.augmentum.common.util.DocumentUtil;
import com.augmentum.common.util.FileUtil;
import com.augmentum.common.util.GetterUtil;
import com.augmentum.common.util.SetUtil;
import com.augmentum.common.util.SourceFormatter;
import com.augmentum.common.util.StringPool;
import com.augmentum.common.util.StringUtil;
import com.augmentum.common.util.StringUtil_IW;
import com.augmentum.common.util.Time;
import com.augmentum.common.util.Validator;
import com.augmentum.common.util.XMLFormatter;
import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;
import com.thoughtworks.qdox.model.Type;

import de.hunsicker.io.FileFormat;
import de.hunsicker.jalopy.Jalopy;
import de.hunsicker.jalopy.storage.Convention;
import de.hunsicker.jalopy.storage.ConventionKeys;
import de.hunsicker.jalopy.storage.Environment;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;

/**
 * 
 */
public class ServiceBuilder {

	private static final String _AUTHOR = "Service Builder";

	private static final int _SESSION_TYPE_LOCAL = 1;

	private static final String _SQL_CREATE_TABLE = "create table ";

	private static final String _TPL_ROOT = "com/augmentum/common/tools/generator/dependencies/";

	private String _tplBadColumnNames = _TPL_ROOT + "bad_column_names.txt";
	private String _tplBadTableNames = _TPL_ROOT + "bad_table_names.txt";
	private String _tplEjbPk = _TPL_ROOT + "ejb_pk.ftl";
	private String _tplException = _TPL_ROOT + "exception.ftl";
	private String _tplHbmXml = _TPL_ROOT + "hbm_xml.ftl";
	private String _tplModel = _TPL_ROOT + "model.ftl";
	private String _tplModelImpl = _TPL_ROOT + "model_impl.ftl";
	private String _tplDAO = _TPL_ROOT + "dao.ftl";
	private String _tplDAOBaseImpl = _TPL_ROOT + "dao_base_impl.ftl";
	private String _tplDAOImpl = _TPL_ROOT + "dao_impl.ftl";
	private String _tplService = _TPL_ROOT + "service.ftl";
	private String _tplServiceBaseImpl = _TPL_ROOT + "service_base_impl.ftl";
	private String _tplServiceImpl = _TPL_ROOT + "service_impl.ftl";
	private String _tplServiceOnly = _TPL_ROOT + "service_only.ftl";
	private String _tplServiceOnlyImpl = _TPL_ROOT + "service_only_impl.ftl";
	private String _tplSpringXml = _TPL_ROOT + "spring_xml.ftl";
	private Set<String> _badTableNames;
	private Set<String> _badColumnNames;
	private String _hbmFileName;
	private String _springFileName;
	private String _springDataSourceFileName;
	private String _implDir;
	private String _sqlDir;
	private String _sqlFileName;
	private String _sqlIndexesFileName;
	private String _sqlIndexesPropertiesFileName;
	private String _sqlSequencesFileName;
	private String _sqlForeignKeyFileName;
	private boolean _autoNamespaceTables;
	private String _testDir;
	private String _author;
	private String _outputPath;
	private String _packagePath;
	private List<Entity> _ejbList;
	private Map<String, EntityMapping> _entityMappings;
	private List<ServiceOnly> _serviceOnlyList;

	private static Map<String, Entity> _entityLookup = new HashMap<String, Entity>();

	// track imports to avoid circular imports
	private static Set<String> _importList = new HashSet<String>();
	private static String rootXml = null;

	public static void main(String[] args) {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();

		ServiceBuilder serviceBuilder = null;

		if (args.length >= 1) {
			String fileName = args[0];
			String hbmFileName = args[1];
			String springFileName = args[2];
			String springDataSourceFileName = "";
			String implDir = "src/service";
			String sqlDir = "sql/generated";
			String sqlFileName = "tables.sql";
			String sqlIndexesFileName = "indexes.sql";
			String sqlIndexesPropertiesFileName = "indexes.properties";
			String sqlSequencesFileName = "sequences.sql";
			String sqlForeignKeyFileName = "foreignkeys.sql";
			boolean autoNamespaceTables = false;
			String testDir = "";

			serviceBuilder = new ServiceBuilder(fileName, hbmFileName, springFileName,
					springDataSourceFileName, implDir, sqlDir, sqlFileName, sqlIndexesFileName,
					sqlIndexesPropertiesFileName, sqlSequencesFileName, sqlForeignKeyFileName, autoNamespaceTables,
					testDir);
		} else if (args.length == 0) {
			String fileName = System.getProperty("service.input.file");
			String hbmFileName = System.getProperty("service.hbm.file");
			String springFileName = System.getProperty("service.spring.file");
			String springDataSourceFileName = System.getProperty("service.spring.data.source.file");
			String implDir = System.getProperty("service.impl.dir");
			String sqlDir = System.getProperty("service.sql.dir");
			String sqlFileName = System.getProperty("service.sql.file");
			String sqlIndexesFileName = System.getProperty("service.sql.indexes.file");
			String sqlIndexesPropertiesFileName = System
					.getProperty("service.sql.indexes.properties.file");
			String sqlSequencesFileName = System.getProperty("service.sql.sequences.file");
			String sqlForeignKeyFileName = System.getProperty("service.sql.foreignkey.file");
			boolean autoNamespaceTables = GetterUtil.getBoolean(System
					.getProperty("service.auto.namespace.tables"));
			String testDir = System.getProperty("service.test.dir");

			serviceBuilder = new ServiceBuilder(fileName, hbmFileName, springFileName,
					springDataSourceFileName, implDir, sqlDir, sqlFileName, sqlIndexesFileName,
					sqlIndexesPropertiesFileName, sqlSequencesFileName, sqlForeignKeyFileName, autoNamespaceTables,
					testDir);
		}

		if (serviceBuilder == null) {
			String msg = "Please set these required system properties. "
					+ "Sample values are:\n"
					+ "\n"
					+ "\t-Dservice.input.file=${service.file}\n"
					+ "\t-Dservice.hbm.file=src/META-INF/generated/hbm-base.xml\n"
					+ "\t-Dservice.spring.file=src/META-INF/generated/spring-base.xml\n"
					+ "\t-Dservice.impl.dir=src/service\n"
					+ "\t-Dservice.sql.dir=../sql/generated\n"
					+ "\t-Dservice.sql.file=tables.sql\n"
					+ "\t-Dservice.sql.indexes.file=indexes.sql\n"
					+ "\t-Dservice.sql.indexes.properties.file=indexes.properties\n"
					+ "\t-Dservice.sql.sequences.file=sequences.sql\n"
					+ "\t-Dservice.sql.foreignkey.file=foreignkey.sql\n"
					+ "\n"
					+ "You can also customize the generated code by overriding the default templates with these optional properties:\n"
					+ "\n" + "\t-Dservice.tpl.bad_column_names="
					+ _TPL_ROOT
					+ "bad_column_names.txt\n"
					+ "\t-Dservice.tpl.bad_table_names="
					+ _TPL_ROOT
					+ "bad_table_names.txt\n"
					+ "\t-Dservice.tpl.copyright.txt=copyright.txt\n"
					+ "\t-Dservice.tpl.ejb_pk="
					+ _TPL_ROOT
					+ "ejb_pk.ftl\n"
					+ "\t-Dservice.tpl.exception="
					+ _TPL_ROOT
					+ "exception.ftl\n"
					+ "\t-Dservice.tpl.hbm_xml="
					+ _TPL_ROOT
					+ "hbm_xml.ftl\n"
					+ "\t-Dservice.tpl.model="
					+ _TPL_ROOT
					+ "model.ftl\n"
					+ "\t-Dservice.tpl.model_impl="
					+ _TPL_ROOT
					+ "model_impl.ftl\n"
					+ "\t-Dservice.tpl.dao="
					+ _TPL_ROOT
					+ "dao.ftl\n"
					+ "\t-Dservice.tpl.dao_base_impl="
					+ _TPL_ROOT
					+ "dao_base_impl.ftl\n"
					+ "\t-Dservice.tpl.dao_impl="
					+ _TPL_ROOT
					+ "dao_impl.ftl\n"
					+ "\t-Dservice.tpl.service="
					+ _TPL_ROOT
					+ "service.ftl\n"
					+ "\t-Dservice.tpl.service_base_impl="
					+ _TPL_ROOT
					+ "service_base_impl.ftl\n"
					+ "\t-Dservice.tpl.service_impl="
					+ _TPL_ROOT
					+ "service_only.ftl\n"
					+ "\t-Dservice.tpl.service_only="
					+ _TPL_ROOT
					+ "service_only_impl.ftl\n"
					+ "\t-Dservice.tpl.service_only_impl="
					+ _TPL_ROOT
					+ "service_impl.ftl\n"
					+ "\t-Dservice.tpl.spring_xml="
					+ _TPL_ROOT
					+ "spring_xml.ftl\n"
					+ "\t-Dservice.tpl.spring_xml_session=" + _TPL_ROOT + "spring_xml_session.ftl";
			System.out.println(msg);
		}
	}

	public static void writeFile(File file, String content) throws IOException {

		writeFile(file, content, _AUTHOR);
	}

	public static void writeFile(File file, String content, String author) throws IOException {

		writeFile(file, content, author, null);
	}

	public static void writeFile(File file, String content, String author,
			Map<String, Object> jalopySettings) throws IOException {
		writeFile(file, content, author, jalopySettings, null);
	}

	public static void writeFile(File file, String content, String author,
			Map<String, Object> jalopySettings, String tempFileName) throws IOException {

		String packagePath = _getPackagePath(file);

		String className = file.getName();

		className = className.substring(0, className.length() - 5);

		content = SourceFormatter.stripImports(content, packagePath, className);

		// handle jalopy reserved words
		//content = SourceFormatter.encodeReservedWords(content);

		File tempFile = null;
		if (StringUtils.isNotBlank(tempFileName)) {
			tempFile = new File(tempFileName);
		} else {
			tempFile = new File("ServiceBuilder.temp");
		}

		FileUtils.writeStringToFile(tempFile, content);

		// Beautify

		StringBuffer sb = new StringBuffer();

		Jalopy jalopy = new Jalopy();

		jalopy.setFileFormat(FileFormat.UNIX);
		jalopy.setInput(tempFile);
		jalopy.setOutput(sb);

		try {
			Jalopy.setConvention("../tools/jalopy.xml");
		} catch (FileNotFoundException fnne) {
		}

		try {
			Jalopy.setConvention("../../misc/jalopy.xml");
		} catch (FileNotFoundException fnne) {
		}

		if (jalopySettings == null) {
			jalopySettings = new HashMap<String, Object>();
		}

		Environment env = Environment.getInstance();

		// Author

		author = GetterUtil.getString((String) jalopySettings.get("author"), author);

		env.set("author", author);

		// File name

		env.set("fileName", file.getName());

		Convention convention = Convention.getInstance();

		String classMask = "/**\n"
				+ " * <a href=\"$fileName$.html\"><b><i>View Source</i></b></a>\n" + " *\n"
				+ " * @author $author$\n" + " *\n" + "*/";

		convention.put(ConventionKeys.COMMENT_JAVADOC_TEMPLATE_CLASS, env.interpolate(classMask));

		convention.put(ConventionKeys.COMMENT_JAVADOC_TEMPLATE_INTERFACE, env
				.interpolate(classMask));

		jalopy.format();

		String newContent = sb.toString();

		// decode back reserved words
		//newContent = SourceFormatter.decodeReservedWords(newContent);

		/*
		 * // Remove blank lines after try {
		 * 
		 * newContent = StringUtil.replace(newContent, "try {\n\n", "try {\n"); //
		 * Remove blank lines after ) {
		 * 
		 * newContent = StringUtil.replace(newContent, ") {\n\n", ") {\n"); //
		 * Remove blank lines empty braces { }
		 * 
		 * newContent = StringUtil.replace(newContent, "\n\n\t}", "\n\t}"); //
		 * Add space to last }
		 * 
		 * newContent = newContent.substring(0, newContent.length() - 2) +
		 * "\n\n}";
		 */

		// Write file if and only if the file has changed
		String oldContent = null;

		if (file.exists()) {

			// Read file

			oldContent = FileUtils.readFileToString(file);

			// Keep old version number

			int x = oldContent.indexOf("@version $Revision:");

			if (x != -1) {
				int y = oldContent.indexOf("$", x);
				y = oldContent.indexOf("$", y + 1);

				String oldVersion = oldContent.substring(x, y + 1);

				newContent = StringUtil.replace(newContent, "@version $Rev: $", oldVersion);
			}
		} else {
			newContent = StringUtil.replace(newContent, "@version $Rev: $",
					"@version $Revision: 1.183 $");
		}

		if (oldContent == null || !oldContent.equals(newContent)) {
			FileUtils.writeStringToFile(file, newContent);

			System.out.println("Writing " + file);

			// Workaround for bug with XJavaDoc

			file.setLastModified(System.currentTimeMillis() - (Time.SECOND * 5));
		}

		tempFile.deleteOnExit();
	}

	public ServiceBuilder(String fileName, String hbmFileName, String springFileName,
			String springDataSourceFileName, String implDir, String sqlDir, String sqlFileName,
			String sqlIndexesFileName, String sqlIndexesPropertiesFileName,
			String sqlSequencesFileName, String sqlForeignKeyFileName, boolean autoNamespaceTables, String testDir) {

		this(fileName, hbmFileName, springFileName, springDataSourceFileName, implDir, sqlDir,
				sqlFileName, sqlIndexesFileName, sqlIndexesPropertiesFileName,
				sqlSequencesFileName, sqlForeignKeyFileName, autoNamespaceTables, testDir, true);
	}

	public ServiceBuilder(String implDir, String fileName) {

		// do no build, just get metadata for <import>
		this(fileName, null, null, null, implDir, null, null, null, null, null, null, false, null, false);
	}

	public ServiceBuilder(String fileName, String hbmFileName, String springFileName,
			String springDataSourceFileName, String implDir, String sqlDir, String sqlFileName,
			String sqlIndexesFileName, String sqlIndexesPropertiesFileName,
			String sqlSequencesFileName, String sqlForeignKeyFileName, boolean autoNamespaceTables, String testDir, boolean build) {

		_tplBadColumnNames = _getTplProperty("bad_column_names", _tplBadColumnNames);
		_tplBadTableNames = _getTplProperty("bad_table_names", _tplBadTableNames);
		_tplEjbPk = _getTplProperty("ejb_pk", _tplEjbPk);
		_tplException = _getTplProperty("exception", _tplException);
		_tplHbmXml = _getTplProperty("hbm_xml", _tplHbmXml);
		_tplModel = _getTplProperty("model", _tplModel);
		_tplModelImpl = _getTplProperty("model_impl", _tplModelImpl);
		_tplDAO = _getTplProperty("dao", _tplDAO);
		_tplDAOBaseImpl = _getTplProperty("dao_base_impl", _tplDAOBaseImpl);
		_tplDAOImpl = _getTplProperty("dao_impl", _tplDAOImpl);
		_tplService = _getTplProperty("service", _tplService);
		_tplServiceBaseImpl = _getTplProperty("service_base_impl", _tplServiceBaseImpl);
		_tplServiceImpl = _getTplProperty("service_impl", _tplServiceImpl);
		_tplServiceOnly = _getTplProperty("service_only", _tplServiceOnly);
		_tplServiceOnlyImpl = _getTplProperty("service_only_impl", _tplServiceOnlyImpl);
		_tplSpringXml = _getTplProperty("spring_xml", _tplSpringXml);

		try {
			System.out.println(_tplBadTableNames);
			_badTableNames = SetUtil.fromString(StringUtil.read(getClass().getClassLoader(),
					_tplBadTableNames));
			_badColumnNames = SetUtil.fromString(StringUtil.read(getClass().getClassLoader(),
					_tplBadColumnNames));
			_hbmFileName = hbmFileName;
			_springFileName = springFileName;
			_springDataSourceFileName = springDataSourceFileName;
			_implDir = implDir;
			_sqlDir = sqlDir;
			_sqlFileName = sqlFileName;
			_sqlIndexesFileName = sqlIndexesFileName;
			_sqlIndexesPropertiesFileName = sqlIndexesPropertiesFileName;
			_sqlSequencesFileName = sqlSequencesFileName;
			_sqlForeignKeyFileName = sqlForeignKeyFileName;
			_autoNamespaceTables = autoNamespaceTables;
			_testDir = testDir;

			Document doc = DocumentUtil.readDocumentFromFile(new File(fileName), true);

			Element root = doc.getRootElement();

			String packagePath = root.attributeValue("package-path");

			_outputPath = _implDir + "/" + StringUtil.replace(packagePath, ".", "/");

			_packagePath = packagePath;

			Element author = root.element("author");

			if (author != null) {
				_author = author.getText();
			} else {
				_author = _AUTHOR;
			}

			_ejbList = new ArrayList<Entity>();
			_entityMappings = new HashMap<String, EntityMapping>();
			_serviceOnlyList = new ArrayList<ServiceOnly>();

			List<Element> entities = root.elements("entity");

			Iterator<Element> itr1 = entities.iterator();

			while (itr1.hasNext()) {
				Element entityEl = itr1.next();

				String ejbName = entityEl.attributeValue("name");

				String table = entityEl.attributeValue("table");

				if (Validator.isNull(table)) {
					table = ejbName;

					if (_badTableNames.contains(ejbName)) {
						table += StringPool.UNDERLINE;
					}
				}

				boolean uuid = GetterUtil.getBoolean(entityEl.attributeValue("uuid"), false);
				boolean localService = GetterUtil.getBoolean(entityEl
						.attributeValue("local-service"), false);

				String dataSource = entityEl.attributeValue("data-source");
				String sessionFactory = entityEl.attributeValue("session-factory");
				String txManager = entityEl.attributeValue("tx-manager");
				boolean cacheEnabled = GetterUtil.getBoolean(entityEl
						.attributeValue("cache-enabled"), true);

				String modelBase = entityEl.attributeValue("model-base");
				String daoBase = entityEl.attributeValue("dao-base");
				String serviceBase = entityEl.attributeValue("service-base");
				String daoIntfBase = entityEl.attributeValue("dao-intf-base");
				String serviceIntfBase = entityEl.attributeValue("service-intf-base");

				// unescape xml
				if (StringUtils.isNotBlank(modelBase)) {
					modelBase = StringEscapeUtils.unescapeXml(modelBase);
				}
				if (StringUtils.isNotBlank(daoBase)) {
					daoBase = StringEscapeUtils.unescapeXml(daoBase);
				}
				if (StringUtils.isNotBlank(serviceBase)) {
					serviceBase = StringEscapeUtils.unescapeXml(serviceBase);
				}
				if (StringUtils.isNotBlank(daoIntfBase)) {
					daoIntfBase = StringEscapeUtils.unescapeXml(daoIntfBase);
				}
				if (StringUtils.isNotBlank(serviceIntfBase)) {
					serviceIntfBase = StringEscapeUtils.unescapeXml(serviceIntfBase);
				}

				List<EntityColumn> pkList = new ArrayList<EntityColumn>();
				List<EntityColumn> regularColList = new ArrayList<EntityColumn>();
				List<EntityColumn> collectionList = new ArrayList<EntityColumn>();
				List<EntityColumn> columnList = new ArrayList<EntityColumn>();
				List<EntityColumn> oneToOneList = new ArrayList<EntityColumn>();
				List<EntityColumn> oneToManyList = new ArrayList<EntityColumn>();
				List<EntityColumn> manyToOneList = new ArrayList<EntityColumn>();
				List<EntityColumn> manyToManyList = new ArrayList<EntityColumn>();
				Map<String, List<String>> uniqueColumns = new TreeMap<String, List<String>>();

				EntityColumn discriminator = null;

				List<Element> columns = entityEl.elements("column");

				if (uuid) {
					Element column = DocumentHelper.createElement("column");

					column.addAttribute("name", "uuid");
					column.addAttribute("type", "String");

					columns.add(0, column);
				}

				Iterator<Element> itr2 = columns.iterator();

				while (itr2.hasNext()) {
					Element column = itr2.next();

					// String columnName = column.attributeValue("name");
					//
					// String columnDBName = column.attributeValue("db-name");
					//
					// if (Validator.isNull(columnDBName)) {
					// columnDBName = columnName;
					//
					// if (_badColumnNames.contains(columnName)) {
					// columnDBName += StringPool.UNDERLINE;
					// }
					// }
					//
					// String columnType = column.attributeValue("type");
					// boolean primary = GetterUtil.getBoolean(
					// column.attributeValue("primary"), false);
					// String collectionEntity =
					// column.attributeValue("entity");
					// String mappingKey = column.attributeValue("mapping-key");
					// String mappingTable = column.attributeValue(
					// "mapping-table");
					// String idType = column.attributeValue("id-type");
					// String idParam = column.attributeValue("id-param");
					// boolean convertNull = GetterUtil.getBoolean(
					// column.attributeValue("convert-null"), true);
					//					
					// // add db column type and size
					// String columnDBType =
					// column.attributeValue(IServiceBuilderConstants.ATT_DB_TYPE);
					// String columnDBSize =
					// column.attributeValue(IServiceBuilderConstants.ATT_DB_SIZE);
					// String columnDBDefaultValue = column
					// .attributeValue(IServiceBuilderConstants.ATT_DB_DEFAULT_VALUE);
					// boolean columnDbNotNull = GetterUtil.getBoolean(column
					// .attributeValue(IServiceBuilderConstants.ATT_DB_NOT_NULL),
					// false);
					//
					// boolean noInsert = false;
					// String insertStr =
					// column.attributeValue(IServiceBuilderConstants.ATT_INSERT);
					// if (StringUtils.isNotBlank(insertStr) &&
					// !Boolean.getBoolean(insertStr))
					// noInsert = true;
					//
					// boolean noUpdate = false;
					// String updateStr =
					// column.attributeValue(IServiceBuilderConstants.ATT_UPDATE);
					// if (StringUtils.isNotBlank(updateStr) &&
					// !Boolean.getBoolean(updateStr))
					// noUpdate = true;
					//
					// EntityColumn col = new EntityColumn(
					// columnName, columnDBName, columnType, primary,
					// collectionEntity, mappingKey, mappingTable, true, true,
					// null, idType,
					// idParam, convertNull,
					// columnDBType, columnDBSize, columnDBDefaultValue,
					// columnDbNotNull, noInsert, noUpdate);
					//
					// if (primary) {
					// pkList.add(col);
					// }
					//
					// if (columnType.equals("Collection")) {
					// collectionList.add(col);
					// }
					// else {
					// regularColList.add(col);
					// }
					//
					// columnList.add(col);
					//
					// if (Validator.isNotNull(collectionEntity) &&
					// Validator.isNotNull(mappingTable)) {
					//
					// EntityMapping entityMapping = new EntityMapping(
					// mappingTable, ejbName, collectionEntity);
					//
					// int ejbNameWeight = StringUtil.startsWithWeight(
					// mappingTable, ejbName);
					// int collectionEntityWeight =
					// StringUtil.startsWithWeight(mappingTable,
					// collectionEntity);
					//
					// if (ejbNameWeight > collectionEntityWeight) {
					// _entityMappings.put(mappingTable, entityMapping);
					// }
					// }

					// call create column method
					EntityColumn col = createColumn(column, pkList, collectionList, regularColList,
							columnList, oneToOneList, oneToManyList, manyToOneList, manyToManyList,
							uniqueColumns, ejbName);

					if (col.isSubclassDiscriminator()) {
						discriminator = col;
					}
				}
				
				EntitySuperclass superclass = createSuperclass(entityEl, "superclass");

				// EntityOrder order = null;
				//
				// Element orderEl = entityEl.element("order");
				//
				// if (orderEl != null) {
				// boolean asc = true;
				//
				// if ((orderEl.attribute("by") != null) &&
				// (orderEl.attributeValue("by").equals("desc"))) {
				//
				// asc = false;
				// }
				//
				// List<EntityColumn> orderColsList =
				// new ArrayList<EntityColumn>();
				//
				// order = new EntityOrder(asc, orderColsList);
				//
				// List<Element> orderCols = orderEl.elements("order-column");
				//
				// Iterator<Element> itr3 = orderCols.iterator();
				//
				// while (itr3.hasNext()) {
				// Element orderColEl = itr3.next();
				//
				// String orderColName =
				// orderColEl.attributeValue("name");
				// boolean orderColCaseSensitive = GetterUtil.getBoolean(
				// orderColEl.attributeValue("case-sensitive"),
				// true);
				//
				// boolean orderColByAscending = asc;
				//
				// String orderColBy = GetterUtil.getString(
				// orderColEl.attributeValue("order-by"));
				//
				// if (orderColBy.equals("asc")) {
				// orderColByAscending = true;
				// }
				// else if (orderColBy.equals("desc")) {
				// orderColByAscending = false;
				// }
				//
				// EntityColumn col = Entity.getColumn(
				// orderColName, columnList);
				//
				// col = (EntityColumn)col.clone();
				//
				// col.setCaseSensitive(orderColCaseSensitive);
				// col.setOrderByAscending(orderColByAscending);
				//
				// orderColsList.add(col);
				// }
				// }

				// call create order method
				EntityOrder order = createOrder(entityEl, "order", columnList);

				List<EntityFinder> finderList = new ArrayList<EntityFinder>();

				List<Element> finders = entityEl.elements("finder");

				if (uuid) {
					Element finderEl = DocumentHelper.createElement("finder");

					finderEl.addAttribute("name", "Uuid");
					finderEl.addAttribute("return-type", "Collection");

					Element finderColEl = finderEl.addElement("finder-column");

					finderColEl.addAttribute("name", "uuid");

					finders.add(0, finderEl);

					if (columnList.contains(new EntityColumn("groupId"))) {
						finderEl = DocumentHelper.createElement("finder");

						finderEl.addAttribute("name", "UUID_G");
						finderEl.addAttribute("return-type", ejbName);

						finderColEl = finderEl.addElement("finder-column");

						finderColEl.addAttribute("name", "uuid");

						finderColEl = finderEl.addElement("finder-column");

						finderColEl.addAttribute("name", "groupId");

						finders.add(1, finderEl);
					}
				}

				itr2 = finders.iterator();

				while (itr2.hasNext()) {
					Element finderEl = itr2.next();

					// String finderName = finderEl.attributeValue("name");
					// String finderReturn =
					// finderEl.attributeValue("return-type");
					// String finderWhere =
					// finderEl.attributeValue("where");
					// boolean finderDBIndex = GetterUtil.getBoolean(
					// finderEl.attributeValue("db-index"), true);
					//
					// List<EntityColumn> finderColsList =
					// new ArrayList<EntityColumn>();
					//
					// List<Element> finderCols = finderEl.elements(
					// "finder-column");
					//
					// Iterator<Element> itr3 = finderCols.iterator();
					//
					// while (itr3.hasNext()) {
					// Element finderColEl = itr3.next();
					//
					// String finderColName =
					// finderColEl.attributeValue("name");
					//
					// boolean finderColCaseSensitive = GetterUtil.getBoolean(
					// finderColEl.attributeValue("case-sensitive"),
					// true);
					//
					// String finderColComparator = GetterUtil.getString(
					// finderColEl.attributeValue("comparator"), "=");
					//
					// EntityColumn col = Entity.getColumn(
					// finderColName, columnList);
					//
					// col = (EntityColumn)col.clone();
					//
					// col.setCaseSensitive(finderColCaseSensitive);
					// col.setComparator(finderColComparator);
					//
					// finderColsList.add(col);
					// }
					//
					// finderList.add(
					// new EntityFinder(
					// finderName, finderReturn, finderColsList,
					// finderWhere, finderDBIndex));

					// call create finder method
					EntityFinder finder = createFinder(finderEl, columnList);

					finderList.add(finder);
				}

				List<Entity> referenceList = new ArrayList<Entity>();

				if (build) {
					List<Element> references = entityEl.elements("reference");

					itr2 = references.iterator();

					while (itr2.hasNext()) {
						Element reference = itr2.next();

						String refPackage = reference.attributeValue("package-path");
						String refEntity = reference.attributeValue("entity");
						boolean refServiceOnly = GetterUtil.getBoolean(reference
								.attributeValue("service-only"), false);

						// referenceList.add(getEntity(refPackage + "."
						// + refEntity));
						referenceList.add(getEntity(refEntity, refPackage, refServiceOnly));
					}
				}

				List<String> txRequiredList = new ArrayList<String>();

				itr2 = entityEl.elements("tx-required").iterator();

				while (itr2.hasNext()) {
					Element txRequiredEl = itr2.next();

					String txRequired = txRequiredEl.getText();

					txRequiredList.add(txRequired);
				}

				// _ejbList.add(
				// new Entity(
				// _packagePath, _portletName, _portletShortName, ejbName,
				// table, uuid, localService,
				// daoClass, dataSource,
				// sessionFactory, txManager, cacheEnabled, pkList,
				// regularColList, collectionList, columnList, order,
				// finderList, referenceList, txRequiredList));
				
				// sort to satisfy the ordering of type Hibernate mapping XML DTD
				Collections.sort(columnList);
				
				Entity entity = new Entity(_packagePath, ejbName, table, uuid, localService,
						dataSource, sessionFactory, txManager, cacheEnabled, pkList,
						regularColList, collectionList, oneToOneList, oneToManyList, manyToOneList,
						manyToManyList, columnList, uniqueColumns, order, finderList, referenceList,
						txRequiredList, discriminator, superclass, modelBase, daoBase, serviceBase,
						daoIntfBase, serviceIntfBase);

				_ejbList.add(entity);

				_entityLookup.put(ejbName, entity);
			}

			List<String> exceptionList = new ArrayList<String>();

			if (root.element("exceptions") != null) {
				List<Element> exceptions = root.element("exceptions").elements("exception");

				itr1 = exceptions.iterator();

				while (itr1.hasNext()) {
					Element exception = itr1.next();

					exceptionList.add(exception.getText());
				}
			}

			// process imports
			Element importsEl = root.element("imports");

			if (importsEl != null) {
				// mark the root XML as read
				if (rootXml == null) {
					rootXml = fileName;
					_importList.add(rootXml);
				}

				List<Element> imports = importsEl.elements("import");
				Iterator<Element> itr = imports.iterator();

				while (itr.hasNext()) {
					Element importEl = itr.next();

					String serviceXml = _implDir + "/" + importEl.getTextTrim();

					if (!_importList.contains(serviceXml)) {
						_importList.add(serviceXml);

						File file = new File(serviceXml);

						if (!file.canRead()) {
							System.out.println("File not found: " + serviceXml);
							continue;
						}

						// fetch the entity information, it will add to _entityLookup
						ServiceBuilder importSB = new ServiceBuilder(_implDir, serviceXml);
					}
				}
			}

			// add the subclasses
			for (int x = 0; x < _ejbList.size(); x++) {
				Entity entity = _ejbList.get(x);
				
				// sort the list so that <version> is before <property>
				Collections.sort(entity.getColumnList());

				// merge full lookup for references
				Map<String, Entity> fullEntityLookup = new HashMap<String, Entity>(_entityLookup);
				for (Entity ref : entity.getReferenceList()) {
					fullEntityLookup.put(ref.getName(), ref);
				}

				if (entity.isSubclass()) {
					String superclass = entity.getSuperclass().getName();
					Entity superEntity = fullEntityLookup.get(superclass);

					if (superEntity == null) {
						throw new RuntimeException("Superclass " + superclass + " not found @ "
								+ entity.getName());
					} else {
						superEntity.getSubclasses().add(entity);

						// set package path
						entity.getSuperclass().setPackagePath(superEntity.getPackagePath());
					}
				}

				// full class names for one to one relationship
				for (EntityColumn column : entity.getOneToOneList()) {
					Entity parent = fullEntityLookup.get(column.getType());

					if (parent == null) {
						throw new RuntimeException("Entity " + column.getType() + " not found @ "
								+ entity.getName() + "." + column.getName());
					}

					column.setEjbClass(parent.getPackagePath() + ".model." + parent.getName());
				}

				// full class names for many to one relationship
				for (EntityColumn column : entity.getManyToOneList()) {
					Entity parent = fullEntityLookup.get(column.getType());

					if (parent == null) {
						throw new RuntimeException("Entity " + column.getType() + " not found @ "
								+ entity.getName() + "." + column.getName());
					}

					column.setEjbClass(parent.getPackagePath() + ".model." + parent.getName());
				}

				// full class names for one to many relationship
				for (EntityColumn column : entity.getOneToManyList()) {
					Entity child = fullEntityLookup.get(column.getEJBName());

					if (child == null) {
						throw new RuntimeException("Entity " + column.getEJBName()
								+ " not found @ " + entity.getName() + "." + column.getName());
					}

					// set the DBName to the child table
					column.setDBName(child.getTable());

					// resolve the 
					column.setEjbClass(child.getPackagePath() + ".model." + child.getName());
				}

				// full class names for many to many relationship
				for (EntityColumn column : entity.getManyToManyList()) {
					Entity child = fullEntityLookup.get(column.getEJBName());

					if (child == null) {
						throw new RuntimeException("Entity " + column.getEJBName()
								+ " not found @ " + entity.getName() + "." + column.getName());
					}

					// set the DBName to the child table
					column.setDBName(child.getTable());

					// resolve the 
					column.setEjbClass(child.getPackagePath() + ".model." + child.getName());
				}
			}

			// add pure service classes without entities
			List<ServiceOnly> serviceOnlyList = new ArrayList<ServiceOnly>();

			if (root.element("service-classes") != null) {
				List<Element> serviceList = root.element("service-classes").elements("service");

				itr1 = serviceList.iterator();

				while (itr1.hasNext()) {
					Element service = itr1.next();

					ServiceOnly serviceOnly = createServiceOnly(service);

					serviceOnlyList.add(serviceOnly);
				}
			}

			if (build) {

				for (int x = 0; x < _ejbList.size(); x++) {
					Entity entity = _ejbList.get(x);

					System.out.println("Building " + entity.getName());

					if (true || entity.getName().equals("EmailAddress")
							|| entity.getName().equals("User")) {

						if (entity.hasColumns()) {
							_createDAOImpl(entity);
							_createDAOBaseImpl(entity);
							_createDAO(entity);

							_createModelImpl(entity);
							_createModel(entity);

							if (entity.getPKList().size() > 1) {
								_createEJBPK(entity);
							}
						}

						_createServiceBaseImpl(entity, _SESSION_TYPE_LOCAL);
						_createServiceImpl(entity, _SESSION_TYPE_LOCAL);
						_createService(entity, _SESSION_TYPE_LOCAL);
					}
				}

				_createHBMXML();
				_createSpringXML();

				if (FileUtil.exists(_sqlDir)) {
					_createSQLIndexes();
					_createSQLTables();
					_createSQLSequences();
					_createSQLForeignKeyConstraints();
				}
				else {
					System.err.println("Folder "+_sqlDir+" not found, SQL files will not be written");
				}					

				_createExceptions(exceptionList);

				_createServiceOnlyClasses(serviceOnlyList);

				// _createSpringDataSourceXML();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getClassName(Type type) {
		int dimensions = type.getDimensions();
		String name = type.getValue();

		if (dimensions > 0) {
			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < dimensions; i++) {
				sb.append("[");
			}

			if (name.equals("boolean")) {
				return sb.toString() + "Z";
			} else if (name.equals("byte")) {
				return sb.toString() + "B";
			} else if (name.equals("char")) {
				return sb.toString() + "C";
			} else if (name.equals("double")) {
				return sb.toString() + "D";
			} else if (name.equals("float")) {
				return sb.toString() + "F";
			} else if (name.equals("int")) {
				return sb.toString() + "I";
			} else if (name.equals("long")) {
				return sb.toString() + "J";
			} else if (name.equals("short")) {
				return sb.toString() + "S";
			} else {
				return sb.toString() + "L" + name + ";";
			}
		}

		return name;
	}

	public String getCreateTableSQL(Entity entity) {
		String createTableSQL = _getCreateTableSQL(entity);

		createTableSQL = StringUtil.replace(createTableSQL, "\n", "");
		createTableSQL = StringUtil.replace(createTableSQL, "\t", "");
		createTableSQL = createTableSQL.substring(0, createTableSQL.length() - 1);

		return createTableSQL;
	}

	public String getDimensions(String dims) {
		return getDimensions(Integer.parseInt(dims));
	}

	public String getDimensions(int dims) {
		String dimensions = "";

		for (int i = 0; i < dims; i++) {
			dimensions += "[]";
		}

		return dimensions;
	}

	public Entity getEntity(String name) throws IOException {
		return getEntity(name, null, false);
	}

	public Entity getEntity(String name, boolean serviceOnly) throws IOException {
		return getEntity(name, null, serviceOnly);
	}

	public Entity getEntity(String name, String refPackage, boolean serviceOnly) throws IOException {
		Entity entity = _entityLookup.get(name);

		if (entity != null) {
			return entity;
		}

		int pos = name.lastIndexOf(".");

		if (pos == -1) {
			pos = _ejbList.indexOf(new Entity(name));

			if (pos != -1) {
				entity = _ejbList.get(pos);

				_entityLookup.put(name, entity);

				return entity;
			}
		}
		// } else {
		// String refPackage = name.substring(0, pos);
		// String refPackageDir = StringUtil.replace(refPackage, ".", "/");
		// String refEntity = name.substring(pos + 1, name.length());
		// String refFileName = _implDir + "/" + refPackageDir
		// + "/service.xml";
		//
		// File refFile = new File(refFileName);
		//
		// boolean useTempFile = false;
		//
		// if (!refFile.exists()) {
		// refFileName = Time.getTimestamp();
		// refFile = new File(refFileName);
		//
		// ClassLoader classLoader = getClass().getClassLoader();
		//
		// FileUtils.writeStringToFile(refFile, StringUtil.read(
		// classLoader, refPackageDir + "/service.xml"));
		//
		// useTempFile = true;
		// }
		//
		// ServiceBuilder serviceBuilder = new ServiceBuilder(refFileName,
		// _hbmFileName, _springFileName, _springDataSourceFileName,
		// _implDir, _sqlDir, _sqlFileName, _sqlIndexesFileName,
		// _sqlIndexesPropertiesFileName, _sqlSequencesFileName,
		// _autoNamespaceTables, _testDir, false);
		//
		// entity = serviceBuilder.getEntity(refEntity);
		//
		// entity.setPortalReference(useTempFile);
		//
		// _entityLookup.put(name, entity);
		//
		// if (useTempFile) {
		// refFile.deleteOnExit();
		// }

		entity = new Entity(name);
		entity.setPackagePath(refPackage);
		entity.setRefServiceOnly(serviceOnly);

		return entity;
		// }
	}

	public Entity getEntityByGenericsName(String genericsName) {
		try {
			String name = genericsName.substring(1, genericsName.length() - 1);

			name = StringUtil.replace(name, ".model.", ".");

			return getEntity(name);
		} catch (Exception e) {
			return null;
		}
	}

	public Entity getEntityByParameterTypeValue(String parameterTypeValue) {
		try {
			String name = parameterTypeValue;

			name = StringUtil.replace(name, ".model.", ".");

			return getEntity(name);
		} catch (Exception e) {
			return null;
		}
	}

	public String getGeneratorClass(String idType) {
		if (Validator.isNull(idType)) {
			idType = "assigned";
		}

		return idType;
	}

	public String getNoSuchEntityException(Entity entity) {
		String noSuchEntityException = entity.getName();

		// if (Validator.isNull(entity.getPortletShortName()) ||
		// noSuchEntityException.startsWith(entity.getPortletShortName())) {
		//
		// noSuchEntityException = noSuchEntityException.substring(
		// entity.getPortletShortName().length());
		// }

		noSuchEntityException = "NoSuch" + noSuchEntityException;

		return noSuchEntityException;
	}

	public String getPrimitiveObj(String type) {
		if (type.equals("boolean")) {
			return "Boolean";
		} else if (type.equals("byte")) {
			return "Byte";
		} else if (type.equals("double")) {
			return "Double";
		} else if (type.equals("float")) {
			return "Float";
		} else if (type.equals("int")) {
			return "Integer";
		} else if (type.equals("long")) {
			return "Long";
		} else if (type.equals("short")) {
			return "Short";
		} else {
			return type;
		}
	}

	public String getPrimitiveObjValue(String colType) {
		if (colType.equals("Boolean")) {
			return ".booleanValue()";
		} else if (colType.equals("Byte")) {
			return ".byteValue()";
		} else if (colType.equals("Double")) {
			return ".doubleValue()";
		} else if (colType.equals("Float")) {
			return ".floatValue()";
		} else if (colType.equals("Integer")) {
			return ".intValue()";
		} else if (colType.equals("Long")) {
			return ".longValue()";
		} else if (colType.equals("Short")) {
			return ".shortValue()";
		}

		return StringPool.BLANK;
	}

	public String getSqlType(String model, String field, String type, String dbSize) {
		if (type.equals("boolean") || type.equals("Boolean")) {
			return "BOOLEAN";
		} else if (type.equals("byte") || type.equals("Byte")) {
			return "SMALLINT";
		} else if (type.equals("double") || type.equals("Double")) {
			return "DOUBLE";
		} else if (type.equals("float") || type.equals("Float")) {
			return "FLOAT";
		} else if (type.equals("int") || type.equals("Integer")) {
			return "INTEGER";
		} else if (type.equals("long") || type.equals("Long")) {
			return "BIGINT";
		} else if (type.equals("short") || type.equals("Short")) {
			return "INTEGER";
		} else if (type.equals("Date")) {
			return "DATE";
		} else if (type.equals("Calendar")) {
			return "TIMESTAMP";
		} else if (type.equals("BigDecimal")) {
			return "DECIMAL";
		} else if (type.equals("Blob")) {
			return "BLOB";
		} else if (type.equals("Clob")) {
			return "CLOB";
		} else if (type.equals("String")) {
			if (StringUtils.isNotBlank(dbSize)) {
				int maxLength = GetterUtil.getInteger(dbSize, 0);

				if (maxLength == -1) {
					return "LONGVARCHAR";
				} else if (maxLength > 4000) {
					return "LONGVARCHAR";
				}
			}

			return "VARCHAR";
		} else {
			return null;
		}
	}

	public boolean hasEntityByGenericsName(String genericsName) {
		if (Validator.isNull(genericsName)) {
			return false;
		}

		if (genericsName.indexOf(".model.") == -1) {
			return false;
		}

		if (getEntityByGenericsName(genericsName) == null) {
			return false;
		} else {
			return true;
		}
	}

	public boolean hasEntityByParameterTypeValue(String parameterTypeValue) {
		if (Validator.isNull(parameterTypeValue)) {
			return false;
		}

		if (parameterTypeValue.indexOf(".model.") == -1) {
			return false;
		}

		if (getEntityByParameterTypeValue(parameterTypeValue) == null) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isCustomMethod(JavaMethod method) {
		String methodName = method.getName();

		if (methodName.equals("afterPropertiesSet") || methodName.equals("equals")
				|| methodName.equals("getClass") || methodName.equals("hashCode")
				|| methodName.equals("notify") || methodName.equals("notifyAll")
				|| methodName.equals("toString") || methodName.equals("wait")
				// custom Spring methods
				|| methodName.equals("setApplicationContext") || methodName.equals("getInstance")) {

			return false;
		} else if (methodName.equals("getPermissionChecker")) {
			return false;
		} else if (methodName.equals("getUser") && method.getParameters().length == 0) {

			return false;
		} else if (methodName.equals("getUserId") && method.getParameters().length == 0) {

			return false;
		} else if ((methodName.endsWith("Finder"))
				&& (methodName.startsWith("get") || methodName.startsWith("set"))) {

			return false;
		} else if ((methodName.endsWith("DAO"))
				&& (methodName.startsWith("get") || methodName.startsWith("set"))) {

			return false;
		} else if ((methodName.endsWith("Service"))
				&& (methodName.startsWith("get") || methodName.startsWith("set"))) {

			return false;
		} else {
			return true;
		}
	}

	public boolean isDuplicateMethod(JavaMethod method, Map<String, Object> tempMap) {

		StringBuilder sb = new StringBuilder();

		sb.append("isDuplicateMethod ");
		sb.append(method.getReturns().toGenericString());
		sb.append(StringPool.SPACE);
		sb.append(method.getName());
		sb.append(StringPool.OPEN_PARENTHESIS);

		JavaParameter[] parameters = method.getParameters();

		for (int i = 0; i < parameters.length; i++) {
			JavaParameter javaParameter = parameters[i];

			sb.append(javaParameter.getType().toGenericString());

			if ((i + 1) != parameters.length) {
				sb.append(StringPool.COMMA);
			}
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);

		String key = sb.toString();

		if (tempMap.containsKey(key)) {
			return true;
		} else {
			tempMap.put(key, key);

			return false;
		}
	}

	private static String _getPackagePath(File file) {
		String fileName = StringUtil.replace(file.toString(), "\\", "/");

		int x = fileName.indexOf("src/");

		if (x == -1) {
			x = fileName.indexOf("test/");
		}

		int y = fileName.lastIndexOf("/");

		fileName = fileName.substring(x + 4, y);

		return StringUtil.replace(fileName, "/", ".");
	}

	private EntityFinder createFinder(Element finderEl, List columnList) {
		String finderName = finderEl.attributeValue("name");
		String finderReturn = finderEl.attributeValue("return-type");
		String finderWhere = finderEl.attributeValue("where");
		boolean finderDBIndex = GetterUtil.getBoolean(finderEl.attributeValue("db-index"), true);
		boolean cacheable = GetterUtil.getBoolean(finderEl
				.attributeValue(IServiceBuilderConstants.ATT_CACHEABLE), true);

		List<EntityColumn> finderColsList = new ArrayList<EntityColumn>();

		List<Element> finderCols = finderEl.elements("finder-column");

		Iterator<Element> itr3 = finderCols.iterator();

		while (itr3.hasNext()) {
			Element finderColEl = itr3.next();

			String finderColName = finderColEl.attributeValue("name");

			boolean finderColCaseSensitive = GetterUtil.getBoolean(finderColEl
					.attributeValue("case-sensitive"), true);

			String finderColComparator = GetterUtil.getString(finderColEl
					.attributeValue("comparator"), "=");

			EntityColumn col = Entity.getColumn(finderColName, columnList);

			col = (EntityColumn) col.clone();

			col.setCaseSensitive(finderColCaseSensitive);
			col.setComparator(finderColComparator);

			finderColsList.add(col);
		}

		// call create order method
		EntityOrder order = createOrder(finderEl, IServiceBuilderConstants.ELEMENT_FINDER_ORDER,
				columnList);

		return new EntityFinder(finderName, finderReturn, finderColsList, finderWhere,
				finderDBIndex, order, cacheable);
	}

	protected EntityColumn createColumn(Element column, List<EntityColumn> pkList,
			List collectionList, List regularColList, List columnList, List oneToOneList,
			List oneToManyList, List manyToOneList, List manyToManyList, Map<String, List<String>> uniqueColumns, String ejbName) {
		String columnName = column.attributeValue("name");

		String columnDBName = column.attributeValue("db-name");

		String columnType = column.attributeValue("type");

		boolean primary = GetterUtil.getBoolean(column.attributeValue("primary"), false);
		String collectionEntity = column.attributeValue("entity");

		String mappingTable = column.attributeValue("mapping-table");
		String idType = column.attributeValue("id-type");
		String idParam = column.attributeValue("id-param");
		boolean convertNull = GetterUtil.getBoolean(column.attributeValue("convert-null"), true);

		// add db column type and size
		String columnDBType = column.attributeValue(IServiceBuilderConstants.ATT_DB_TYPE);
		String columnDBSize = column.attributeValue(IServiceBuilderConstants.ATT_DB_SIZE);
		String columnDBDefaultValue = column
				.attributeValue(IServiceBuilderConstants.ATT_DB_DEFAULT_VALUE);
		boolean columnDbNotNull = GetterUtil.getBoolean(column
				.attributeValue(IServiceBuilderConstants.ATT_DB_NOT_NULL), false);
		String columnDBCollate = column.attributeValue(IServiceBuilderConstants.ATT_DB_COLLATE);

		boolean noInsert = false;
		String insertStr = column.attributeValue(IServiceBuilderConstants.ATT_INSERT);
		if (StringUtils.isNotBlank(insertStr) && !Boolean.parseBoolean(insertStr))
			noInsert = true;

		boolean noUpdate = false;
		String updateStr = column.attributeValue(IServiceBuilderConstants.ATT_UPDATE);
		if (StringUtils.isNotBlank(updateStr) && !Boolean.parseBoolean(updateStr))
			noUpdate = true;

		// check mapping keys, handles composite keys
		String mappingKey = column.attributeValue("mapping-key");
		String[] mappingKeys = null;
		if (mappingKey != null) {
			// trim spaces & split
			mappingKeys = mappingKey.replaceAll("\\s+", "").split(",");

			// if the mapping key contains a primary key column, then it must be immutable
			// required for composite key joins
			boolean hasPrimaryKey = false;

			for (EntityColumn pkCol : pkList) {
				for (String joinColumn : mappingKeys) {
					if (pkCol.getDBName().equalsIgnoreCase(joinColumn)) {
						hasPrimaryKey = true;
						break;
					}
				}
			}

			if (hasPrimaryKey) {
				noInsert = true;
				noUpdate = true;
			}
		}

		String idParamTable = column.attributeValue("id-param-table");
		String idParamColumn = column.attributeValue("id-param-column");
		String idParamMaxlo = column.attributeValue("id-param-maxlo");

		boolean oneToOne = BooleanUtils.toBoolean(column.attributeValue("one-to-one"));

		String relationship = null;

		if (oneToOne) {
			relationship = "oneToOne";
		} else if (collectionEntity == null && columnType != null && mappingTable == null
				&& mappingKey != null) {
			if (columnDBName != null) {
				throw new RuntimeException(
						"Many-to-one should not specify db-name, specify the column in mapping-key @ "
								+ ejbName + "." + columnName);
			}

			// if the mappingKey is part of PK, this it is immutable
			int matches = 0;
			for (String joinColumn : mappingKeys) {
				for (EntityColumn pkCol : (List<EntityColumn>) pkList) {
					if (pkCol.getDBName().equalsIgnoreCase(joinColumn)) {
						noInsert = true;
						noUpdate = true;

						matches++;
					}
				}
			}
			/*			
						if (matches == pkList.size() && pkList.size() == mappingKeys.length) {
							// the PK exactly matches the mapping keys, then this is probably a one-to-one
							System.out.println("WARN: many-to-one appears to be a one-to-one: "+ejbName+"."+columnType);
						}
			*/
			relationship = "manyToOne";
		} else if (collectionEntity != null && columnType != null && mappingTable == null
				&& mappingKey != null) {
			columnType = validateToManyCollectionType(columnType);
			relationship = "oneToMany";
		} else if (collectionEntity != null && columnType != null && mappingTable != null
				&& mappingKey != null) {
			columnType = validateToManyCollectionType(columnType);
			relationship = "manyToMany";
		}

		// check this after many-to-one check
		if (Validator.isNull(columnDBName)) {
			columnDBName = columnName;

			if (_badColumnNames.contains(columnName)) {
				columnDBName += StringPool.UNDERLINE;
			}
		}

		String cascade = column.attributeValue("cascade");
		String orderBy = column.attributeValue("order-by");
		String lazy = column.attributeValue("lazy", "true");

		if (StringUtils.isNotBlank(cascade)) {
			if ((cascade.toLowerCase().indexOf("all") != -1)) {
				// do nothing
			} else if ((cascade.toLowerCase().indexOf("delete-orphan") != -1)
					&& StringUtils.isNotBlank(relationship)
					&& (relationship.equalsIgnoreCase("oneToOne") || relationship
							.equalsIgnoreCase("manyToOne"))) {
				// do nothing
			} else { // add evict
				cascade = "evict," + cascade;
			}
		}

		boolean isSubclassDiscriminator = false;
		String isSubclassDiscriminatorStr = column.attributeValue("subclass-discriminator");
		if (StringUtils.isNotBlank(isSubclassDiscriminatorStr)
				&& Boolean.valueOf(isSubclassDiscriminatorStr)) {
			isSubclassDiscriminator = true;

			// if this is many to one discriminator, set the discriminator column to the mapping key
			if ("manyToOne".equals(relationship)) {
				columnDBName = mappingKey;
			}

			// discriminator can't be modified
			noInsert=true;
			noUpdate=true;
		}

		String formula = column.attributeValue("formula");

		String inverse = column.attributeValue("inverse");
		
		if (StringUtils.isBlank(inverse)) {
			if (StringUtils.isNotBlank(relationship)
					&& (relationship.equalsIgnoreCase("oneToMany"))) {
				inverse = "true";
			}
		}

		boolean fkConstrained = BooleanUtils.toBoolean(column.attributeValue("fkconstrained"));

		boolean ignoreNotFound = BooleanUtils.toBoolean(column.attributeValue("ignore-not-found"));

		boolean unique = false;
		String uniqueStr = column.attributeValue("unique");
		
		if (uniqueStr != null) {
			// name of the combination of columns that are unique
			String uniqueComboName = null;
			
			if (uniqueStr.equalsIgnoreCase("true")) {
				unique = true;
				
				uniqueComboName = columnDBName;
			}
			else if (uniqueStr.equalsIgnoreCase("false")) {
				unique = false;
			}
			else {
				uniqueComboName = uniqueStr;
			}
			
			if (uniqueComboName != null) {
				// save to list of unique columns
				List<String> uniqueCombo = uniqueColumns.get(uniqueStr);
				
				if (uniqueCombo == null) {
					uniqueCombo = new ArrayList<String>();
					
					uniqueColumns.put(uniqueStr, uniqueCombo);
				}
				
				uniqueCombo.add(columnDBName);
			}
		}

		boolean version = BooleanUtils.toBoolean(column.attributeValue("version"));

		String fetch = column.attributeValue("fetch");

		String propertyRef = column.attributeValue("property-ref");

		// this is set by the ejb resolver
		String ejbClass = null;

		EntityColumn col = new EntityColumn(columnName, columnDBName, columnType, primary,
				collectionEntity, mappingKeys, mappingTable, true, true, null, idType, idParam,
				idParamTable, idParamColumn, idParamMaxlo, convertNull, columnDBType, columnDBSize,
				columnDBDefaultValue, columnDbNotNull, columnDBCollate, noInsert, noUpdate, relationship, ejbClass,
				cascade, orderBy, lazy, isSubclassDiscriminator, formula, inverse, fkConstrained,
				oneToOne, ignoreNotFound, fetch, propertyRef, unique, version);

		if (primary) {
			pkList.add(col);
		}

		// determine the relationship
		if (relationship == null) {
			if (columnType.equals("Collection")) {
				collectionList.add(col);
			} else {
				regularColList.add(col);
			}
		} else if (relationship.equals("oneToOne")) {
			oneToOneList.add(col);
		} else if (relationship.equals("manyToOne")) {
			manyToOneList.add(col);
		} else if (relationship.equals("oneToMany")) {
			oneToManyList.add(col);
		} else if (relationship.equals("manyToMany")) {
			manyToManyList.add(col);
		}
		
		columnList.add(col);

		if (Validator.isNotNull(collectionEntity) && Validator.isNotNull(mappingTable)) {

			EntityMapping entityMapping = new EntityMapping(mappingTable, ejbName, collectionEntity);

			int ejbNameWeight = StringUtil.startsWithWeight(mappingTable, ejbName);
			int collectionEntityWeight = StringUtil
					.startsWithWeight(mappingTable, collectionEntity);

			if (ejbNameWeight > collectionEntityWeight) {
				_entityMappings.put(mappingTable, entityMapping);
			}
		}

		return col;
	}

	protected EntityOrder createOrder(Element entityEl, String orderElementName, List columnList) {
		EntityOrder order = null;

		Element orderEl = entityEl.element(orderElementName);

		if (orderEl != null) {
			boolean asc = true;

			if ((orderEl.attribute("by") != null) && (orderEl.attributeValue("by").equals("desc"))) {

				asc = false;
			}

			List<EntityColumn> orderColsList = new ArrayList<EntityColumn>();

			order = new EntityOrder(asc, orderColsList);

			List<Element> orderCols = orderEl.elements("order-column");

			Iterator<Element> itr3 = orderCols.iterator();

			while (itr3.hasNext()) {
				Element orderColEl = itr3.next();

				String orderColName = orderColEl.attributeValue("name");
				boolean orderColCaseSensitive = GetterUtil.getBoolean(orderColEl
						.attributeValue("case-sensitive"), true);

				boolean orderColByAscending = asc;

				String orderColBy = GetterUtil.getString(orderColEl.attributeValue("order-by"), "");

				if (orderColBy.equals("asc")) {
					orderColByAscending = true;
				} else if (orderColBy.equals("desc")) {
					orderColByAscending = false;
				}

				EntityColumn col = Entity.getColumn(orderColName, columnList);

				col = (EntityColumn) col.clone();

				col.setCaseSensitive(orderColCaseSensitive);
				col.setOrderByAscending(orderColByAscending);

				orderColsList.add(col);
			}
		}

		return order;
	}

	private ServiceOnly createServiceOnly(Element service) {
		String name = service.attributeValue("name");
		String txManager = service.attributeValue("tx-manager");

		ServiceOnly serviceOnly = new ServiceOnly(name, txManager);
		_serviceOnlyList.add(serviceOnly);

		return serviceOnly;
	}

	private void _createEJBPK(Entity entity) throws Exception {
		Map<String, Object> context = _getContext();

		context.put("entity", entity);

		// Content

		String content = _processTemplate(_tplEjbPk, context);

		// Write file

		File ejbFile = new File(_outputPath + "/model/" + entity.getPKClassName() + ".java");

		writeFile(ejbFile, content, _author);
	}

	private void _createExceptions(List<String> exceptions) throws Exception {
		Map<String, Object> context = _getContext();

		for (int i = 0; i < _ejbList.size(); i++) {
			Entity entity = _ejbList.get(i);

			if (entity.hasColumns()) {
				String noSuchEntityException = getNoSuchEntityException(entity);

				context.put("noSuchEntity", noSuchEntityException);

				exceptions.add(noSuchEntityException);
			}
		}

		for (String exception : exceptions) {
			File exceptionFile = new File(_outputPath + "/" + exception + "Exception.java");

			if (!exceptionFile.exists()) {

				context.put("exception", exception);

				String content = _processTemplate(_tplException, context);

				FileUtils.writeStringToFile(exceptionFile, content);
			}
		}
	}

	private void _createServiceOnlyClasses(List<ServiceOnly> serviceOnlyList) throws Exception {
		_createServiceOnlyImpl(serviceOnlyList);
		for (ServiceOnly serviceOnly : serviceOnlyList) {
			_createServiceOnlyIntf(serviceOnly);
		}
	}

	private void _createServiceOnlyImpl(List<ServiceOnly> serviceOnlyList) throws Exception {
		Map<String, Object> context = _getContext();

		for (ServiceOnly serviceOnly : serviceOnlyList) {
			File serviceOnlyImplFile = new File(_outputPath + "/service/" + serviceOnly.getName()
					+ "Impl.java");

			if (!serviceOnlyImplFile.exists()) {

				context.put("service", serviceOnly);

				String content = _processTemplate(_tplServiceOnlyImpl, context);

				FileUtils.writeStringToFile(serviceOnlyImplFile, content);
			}
		}
	}

	private void _createServiceOnlyIntf(ServiceOnly serviceOnly) throws Exception {
		JavaClass javaClass = _getJavaClass(_outputPath + "/service/" + serviceOnly.getName()
				+ "Impl.java");

		JavaMethod[] methods = _getMethods(javaClass);

		Map<String, Object> context = _getContext();

		context.put("service", serviceOnly);
		context.put("methods", methods);

		// Content

		String content = _processTemplate(_tplServiceOnly, context);

		// Write file

		File file = new File(_outputPath + "/service/" + serviceOnly.getName() + ".java");

		Map<String, Object> jalopySettings = new HashMap<String, Object>();

		jalopySettings.put("keepJavadoc", Boolean.TRUE);

		writeFile(file, content, _author, jalopySettings);
	}

	/**
	 * TODO create hbm file for each service.xml
	 * 
	 * @throws Exception
	 */
	private void _createHBMXML() throws Exception {
		Map<String, Object> context = _getContext();

		// do not include Entity subclasses as separate definitions
		List<Entity> hibernateList = new ArrayList<Entity>();
		Set<Entity> externalSuperClasses = new LinkedHashSet<Entity>();

		for (Entity entity : _ejbList) {
			if (!entity.isSubclass()) {
				hibernateList.add(entity);
			} else {
				Entity superClass = _entityLookup.get(entity.getSuperclass().getName());

				// if the super class is defined externally then it needs to be updated
				if (!superClass.getPackagePath().equals(_packagePath)) {
					externalSuperClasses.add(superClass);
				}
			}
		}

		context.put("entities", hibernateList);

		// Content

		String content = hbmXmlPostProcess(_processTemplate(_tplHbmXml, context));

		File xmlFile = new File(_hbmFileName);

		if (!xmlFile.exists()) {
			String xml = "<?xml version=\"1.0\"?>\n"
					+ "<!DOCTYPE hibernate-mapping PUBLIC \"-//Hibernate/Hibernate Mapping DTD 3.0//EN\" \"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd\">\n"
					+ "\n"
					+ "<hibernate-mapping default-lazy=\"true\" auto-import=\"false\" default-cascade=\"evict\">\n"
					+ "</hibernate-mapping>";

			FileUtils.writeStringToFile(xmlFile, xml);
		}

		String oldContent = FileUtils.readFileToString(xmlFile);
		String newContent = _fixHBMXML(oldContent);

		int firstClass = newContent.indexOf("<class name=\"" + _packagePath + ".model.");
		int lastClass = newContent.lastIndexOf("<class name=\"" + _packagePath + ".model.");

		if (firstClass == -1) {
			int x = newContent.indexOf("</hibernate-mapping>");

			newContent = newContent.substring(0, x) + content
					+ newContent.substring(x, newContent.length());
		} else {
			firstClass = newContent.lastIndexOf("<class", firstClass) - 1;
			lastClass = newContent.indexOf("</class>", lastClass) + 9;

			newContent = newContent.substring(0, firstClass) + content
					+ newContent.substring(lastClass, newContent.length());
		}

		/////////////////////////////////////////////////////////
		// regenerate Hibernate mapping for external superclasses 
		// when a subclass was updated

		for (Entity superClass : externalSuperClasses) {
			int classStart = newContent.indexOf("<class name=\"" + superClass.getPackagePath()
					+ ".model." + superClass.getName());

			if (classStart > 0) {
				int classEnd = newContent.indexOf("</class>", classStart) + 9;

				hibernateList.clear();
				hibernateList.add(superClass);
				context.put("entities", hibernateList);
				content = hbmXmlPostProcess(_processTemplate(_tplHbmXml, context));

				newContent = newContent.substring(0, classStart) + content
						+ newContent.substring(classEnd, newContent.length());
			} else {
				System.err.println(xmlFile + " does not contain definition for " + superClass
						+ ", could not replace.");
			}
		}

		/////////////////////////////////////////////////////////

		newContent = _formatXML(newContent);

		if (!oldContent.equals(newContent)) {
			FileUtils.writeStringToFile(xmlFile, newContent);
		}
	}

	private void _createModel(Entity entity) throws Exception {
		Map<String, Object> context = _getContext();

		context.put("entity", entity);

		// Content

		String content = _processTemplate(_tplModel, context);

		// Write file

		File modelFile = new File(_outputPath + "/model/" + entity.getName() + ".java");

		Map<String, Object> jalopySettings = new HashMap<String, Object>();

		jalopySettings.put("keepJavadoc", Boolean.TRUE);

		if (!modelFile.exists()) {
			writeFile(modelFile, content, _author, jalopySettings);
		}
	}

	private void _createModelImpl(Entity entity) throws Exception {
		Map<String, Object> context = _getContext();

		context.put("entity", entity);

		// Content

		String content = _processTemplate(_tplModelImpl, context);

		// Write file

		File modelFile = new File(_outputPath + "/model/" + entity.getName() + "ModelImpl.java");

		Map<String, Object> jalopySettings = new HashMap<String, Object>();

		jalopySettings.put("keepJavadoc", Boolean.TRUE);

		writeFile(modelFile, content, _author, jalopySettings, modelFile.getName());
	}

	private void _createDAO(Entity entity) throws Exception {
		JavaClass javaClass = _getJavaClass(_outputPath + "/dao/" + entity.getName()
				+ "DAOImpl.java");

		Map<String, Object> context = _getContext();

		JavaMethod[] methods = _getMethods(javaClass);

		if (javaClass.getSuperClass().getValue().endsWith(entity.getName() + "DAOBaseImpl")) {

			JavaClass parentJavaClass = _getJavaClass(_outputPath + "/dao/" + entity.getName()
					+ "DAOBaseImpl.java");

			JavaMethod[] parentMethods = parentJavaClass.getMethods();

			JavaMethod[] allMethods = new JavaMethod[parentMethods.length + methods.length];

			ArrayUtil.combine(parentMethods, methods, allMethods);

			methods = allMethods;
		}

		context.put("entity", entity);
		context.put("methods", methods);

		// Content

		String content = _processTemplate(_tplDAO, context);

		// Write file

		File ejbFile = new File(_outputPath + "/dao/" + entity.getName() + "DAO.java");

		writeFile(ejbFile, content, _author);

		// if (!_outputPath.equals(_outputPath)) {
		// ejbFile = new File(
		// _outputPath + "/dao/" + entity.getName() +
		// "DAO.java");
		//
		// if (ejbFile.exists()) {
		// System.out.println("Relocating " + ejbFile);
		//
		// ejbFile.delete();
		// }
		// }
	}

	private void _createDAOBaseImpl(Entity entity) throws Exception {
		Map<String, Object> context = _getContext();

		context.put("entity", entity);

		// Content

		String content = _processTemplate(_tplDAOBaseImpl, context);

		// Write file

		File ejbFile = new File(_outputPath + "/dao/" + entity.getName() + "DAOBaseImpl.java");

		writeFile(ejbFile, content, _author);
	}

	private void _createDAOImpl(Entity entity) throws Exception {
		Map<String, Object> context = _getContext();

		context.put("entity", entity);

		// Content

		String content = _processTemplate(_tplDAOImpl, context);

		// Write file

		File ejbFile = new File(_outputPath + "/dao/" + entity.getName() + "DAOImpl.java");

		if (!ejbFile.exists()) {
			writeFile(ejbFile, content, _author);
		}
	}

	private void _createService(Entity entity, int sessionType) throws Exception {

		String serviceComments = "This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.";

		JavaClass javaClass = _getJavaClass(_outputPath + "/service/" + entity.getName()
				+ "ServiceImpl.java");

		JavaMethod[] methods = _getMethods(javaClass);

		if (sessionType == _SESSION_TYPE_LOCAL) {
			if (javaClass.getSuperClass().getValue().endsWith(entity.getName() + "ServiceBaseImpl")) {

				JavaClass parentJavaClass = _getJavaClass(_outputPath + "/service/"
						+ entity.getName() + "ServiceBaseImpl.java");

				JavaMethod[] parentMethods = parentJavaClass.getMethods();

				JavaMethod[] allMethods = new JavaMethod[parentMethods.length + methods.length];

				ArrayUtil.combine(parentMethods, methods, allMethods);

				methods = allMethods;
			}

			serviceComments = "This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.";
		}

		Map<String, Object> context = _getContext();

		context.put("entity", entity);
		context.put("methods", methods);
		context.put("sessionTypeName", _getSessionTypeName(sessionType));
		context.put("serviceComments", serviceComments);

		String noSuchEntityException = getNoSuchEntityException(entity);
		context.put("noSuchEntity", noSuchEntityException);

		// Content

		String content = _processTemplate(_tplService, context);

		// Write file

		File ejbFile = new File(_outputPath + "/service/" + entity.getName() + "Service.java");

		Map<String, Object> jalopySettings = new HashMap<String, Object>();

		jalopySettings.put("keepJavadoc", Boolean.TRUE);

		writeFile(ejbFile, content, _author, jalopySettings);
	}

	private void _createServiceBaseImpl(Entity entity, int sessionType) throws Exception {

		Map<String, Object> context = _getContext();

		context.put("entity", entity);
		context.put("sessionTypeName", _getSessionTypeName(sessionType));
		context.put("referenceList", _mergeReferenceList(entity.getReferenceList()));

		String noSuchEntityException = getNoSuchEntityException(entity);
		context.put("noSuchEntity", noSuchEntityException);

		// Content

		String content = _processTemplate(_tplServiceBaseImpl, context);

		// Write file

		File ejbFile = new File(_outputPath + "/service/" + entity.getName()
				+ "ServiceBaseImpl.java");

		writeFile(ejbFile, content, _author);
	}

	private void _createServiceImpl(Entity entity, int sessionType) throws Exception {

		Map<String, Object> context = _getContext();

		context.put("entity", entity);
		context.put("sessionTypeName", _getSessionTypeName(sessionType));

		String noSuchEntityException = getNoSuchEntityException(entity);
		context.put("noSuchEntity", noSuchEntityException);

		// Content

		String content = _processTemplate(_tplServiceImpl, context);

		// Write file

		File ejbFile = new File(_outputPath + "/service/" + entity.getName() + "ServiceImpl.java");

		if (!ejbFile.exists()) {
			writeFile(ejbFile, content, _author);
		}
	}

	// private void _createSpringDataSourceXML() throws Exception {
	// if (Validator.isNull(_springDataSourceFileName)) {
	// return;
	// }
	//
	// // Content
	//
	// String content = _processTemplate(_tplSpringDataSourceXml);
	//
	// // Write file
	//
	// File ejbFile = new File(_springDataSourceFileName);
	//
	// FileUtil.write(ejbFile, content, true);
	// }

	/**
	 * TODO create spring xml for each service.xml
	 * 
	 * @throws Exception
	 */
	private void _createSpringXML() throws Exception {
		Map<String, Object> context = _getContext();

		context.put("entities", _ejbList);
		context.put("serviceOnlyList", _serviceOnlyList);

		// Content

		String content = _processTemplate(_tplSpringXml, context);

		File xmlFile = new File(_springFileName);

		if (!xmlFile.exists()) {
			String xml = "<?xml version=\"1.0\"?>\n"
					+ "<!DOCTYPE beans PUBLIC \"-//SPRING//DTD BEAN//EN\" \"http://www.springframework.org/dtd/spring-beans.dtd\">\n"
					+ "\n"
					+ "<beans default-init-method=\"init\" default-destroy-method=\"destroy\">\n"
					+ "</beans>";

			FileUtils.writeStringToFile(xmlFile, xml);
		}

		String oldContent = FileUtils.readFileToString(xmlFile);
		String newContent = _fixSpringXML(oldContent);

		int x = oldContent
				.indexOf("<beans default-init-method=\"init\" default-destroy-method=\"destroy\">");
		int y = oldContent.lastIndexOf("</beans>");

		int firstSession = newContent.indexOf("<bean id=\"" + _packagePath + ".service.", x);

		int lastSession = newContent.lastIndexOf("<bean id=\"" + _packagePath + ".dao.", y);

		if (firstSession == -1 || firstSession > y) {
			x = newContent.indexOf("</beans>");
			newContent = newContent.substring(0, x) + content
					+ newContent.substring(x, newContent.length());
		} else {
			firstSession = newContent.lastIndexOf("<bean", firstSession) - 1;
			lastSession = newContent.indexOf("</bean>", lastSession) + 8;

			// String tmpStr = newContent.substring(0, firstSession);
			// String tmpStr2 = newContent.substring(lastSession,
			// newContent.length());

			newContent = newContent.substring(0, firstSession) + content
					+ newContent.substring(lastSession, newContent.length());
		}

		newContent = _formatXML(newContent);

		if (!oldContent.equals(newContent)) {
			FileUtils.writeStringToFile(xmlFile, newContent);
		}
	}

	private void _createSQLIndexes() throws IOException {
		if (!FileUtil.exists(_sqlDir)) {
			return;
		}

		// indexes.sql

		File sqlFile = new File(_sqlDir + "/" + _sqlIndexesFileName);

		if (!sqlFile.exists()) {
			FileUtils.writeStringToFile(sqlFile, "");
		}

		Map<String, String> indexSQLs = new TreeMap<String, String>();

		BufferedReader br = new BufferedReader(new FileReader(sqlFile));

		while (true) {
			String indexSQL = br.readLine();

			if (indexSQL == null) {
				break;
			}

			if (Validator.isNotNull(indexSQL.trim())) {
				int pos = indexSQL.indexOf(" on ");

				String indexSpec = indexSQL.substring(pos + 4);

				indexSQLs.put(indexSpec, indexSQL);
			}
		}

		br.close();

		// indexes.properties

		File propsFile = new File(_sqlDir + "/" + _sqlIndexesPropertiesFileName);

		if (!propsFile.exists()) {
			FileUtils.writeStringToFile(propsFile, "");
		}

		Map<String, String> indexProps = new TreeMap<String, String>();

		br = new BufferedReader(new FileReader(propsFile));

		while (true) {
			String indexMapping = br.readLine();

			if (indexMapping == null) {
				break;
			}

			if (Validator.isNotNull(indexMapping.trim())) {
				String[] splitIndexMapping = indexMapping.split("\\=");

				indexProps.put(splitIndexMapping[1], splitIndexMapping[0]);
			}
		}

		br.close();

		// indexes.sql

		for (int i = 0; i < _ejbList.size(); i++) {
			Entity entity = _ejbList.get(i);

			if (!entity.isDefaultDataSource()) {
				continue;
			}
			
			// to detect column sets that exist in both unique & finder indexes, for merging into a single index
			HashSet<SortedSet<String>> uniqueIndexColumns = new HashSet<SortedSet<String>>();
			
			// map column set to index name, so it can be removed for merging
			Map<SortedSet<String>, String> columnsIndexMap = new HashMap<SortedSet<String>, String>();
			
			// unique indexes
			for (List<String> columnList : entity.getUniqueColumns().values()) {
				StringBuilder sb = new StringBuilder();
				
				SortedSet<String> columnSet = new TreeSet<String>();
				
				for (int k = 0; k < columnList.size(); k++) {
					sb.append(columnList.get(k));
					columnSet.add(columnList.get(k));

					if ((k + 1) != columnList.size()) {
						sb.append(", ");
					}
				}

				sb.append(");");
				uniqueIndexColumns.add(columnSet);

				String indexSpec = sb.toString();
				
				String indexHash = Integer.toHexString(indexSpec.hashCode()).toUpperCase();
				String indexName = "IX_" + indexHash;
				
				sb = new StringBuilder();
				
				sb.append("create unique index " + indexName + " on ");
				sb.append(entity.getTable() + " (");
				sb.append(indexSpec);
				
				indexSQLs.put(indexSpec, sb.toString());
				columnsIndexMap.put(columnSet, indexSpec);
			}

			// indexes for finders
			List<EntityFinder> finderList = entity.getFinderList();

			for (int j = 0; j < finderList.size(); j++) {
				EntityFinder finder = finderList.get(j);

				if (finder.isDBIndex()) {
					StringBuilder sb = new StringBuilder();

					sb.append(entity.getTable() + " (");

					List<EntityColumn> finderColsList = finder.getColumns();
					
					SortedSet<String> columnSet = new TreeSet<String>();
					
					for (int k = 0; k < finderColsList.size(); k++) {
						EntityColumn col = finderColsList.get(k);

						sb.append(col.getDBName());
						columnSet.add(col.getDBName());

						if ((k + 1) != finderColsList.size()) {
							sb.append(", ");
						}
					}

					sb.append(");");
					
					boolean isUnique = false;
					
					// check if unique index has the same columns
					if (uniqueIndexColumns.contains(columnSet)) {
						// remove the other definition
						String indexId = columnsIndexMap.get(columnSet);
						indexSQLs.remove(indexId);

						isUnique = true;
					}

					String indexSpec = sb.toString();

					String indexHash = Integer.toHexString(indexSpec.hashCode()).toUpperCase();
					String indexName = "IX_" + indexHash;

					sb = new StringBuilder();

					sb.append("create ");
					
					if (isUnique) {
						sb.append("unique ");
					}
					
					sb.append("index " + indexName + " on ");
					sb.append(indexSpec);

					indexSQLs.put(indexSpec, sb.toString());

					String finderName = entity.getTable() + StringPool.PERIOD + finder.getName();

					indexProps.put(finderName, indexName);
				}
			}
		}
		
		StringBuilder sb = new StringBuilder();

		Iterator<String> itr = indexSQLs.values().iterator();

		String prevEntityName = null;

		while (itr.hasNext()) {
			String indexSQL = itr.next();

			String entityName = indexSQL.split(" ")[4];

			if ((prevEntityName != null) && (!prevEntityName.equals(entityName))) {

				sb.append("\n");
			}

			sb.append(indexSQL);

			if (itr.hasNext()) {
				sb.append("\n");
			}

			prevEntityName = entityName;
		}

		FileUtil.write(sqlFile, sb.toString(), true);

		// indexes.properties

		sb = new StringBuilder();

		itr = indexProps.keySet().iterator();

		prevEntityName = null;

		while (itr.hasNext()) {
			String finderName = itr.next();

			String indexName = indexProps.get(finderName);

			String entityName = finderName.split("\\.")[0];

			if ((prevEntityName != null) && (!prevEntityName.equals(entityName))) {

				sb.append("\n");
			}

			sb.append(indexName + StringPool.EQUAL + finderName);

			if (itr.hasNext()) {
				sb.append("\n");
			}

			prevEntityName = entityName;
		}

		FileUtil.write(propsFile, sb.toString(), true);
	}

	private void _createSQLMappingTables(File sqlFile, String newCreateTableString,
			EntityMapping entityMapping, boolean addMissingTables) throws IOException {

		if (!sqlFile.exists()) {
			FileUtils.writeStringToFile(sqlFile, StringPool.BLANK);
		}

		String content = FileUtils.readFileToString(sqlFile);

		int x = content.indexOf(_SQL_CREATE_TABLE + entityMapping.getTable() + " (");
		int y = content.indexOf(");", x);

		if (x != -1) {
			String oldCreateTableString = content.substring(x + 1, y);

			if (!oldCreateTableString.equals(newCreateTableString)) {
				content = content.substring(0, x) + newCreateTableString
						+ content.substring(y + 2, content.length());

				FileUtils.writeStringToFile(sqlFile, content);
			}
		} else if (addMissingTables) {
			StringBuilder sb = new StringBuilder();

			BufferedReader br = new BufferedReader(new StringReader(content));

			String line = null;
			boolean appendNewTable = true;

			while ((line = br.readLine()) != null) {
				if (appendNewTable && line.startsWith(_SQL_CREATE_TABLE)) {
					x = _SQL_CREATE_TABLE.length();
					y = line.indexOf(" ", x);

					String tableName = line.substring(x, y);

					if (tableName.compareTo(entityMapping.getTable()) > 0) {
						sb.append(newCreateTableString + "\n\n");

						appendNewTable = false;
					}
				}

				sb.append(line);
				sb.append("\n");
			}

			if (appendNewTable) {
				sb.append("\n" + newCreateTableString);
			}

			br.close();

			FileUtil.write(sqlFile, sb.toString(), true);
		}
	}

	private void _createSQLSequences() throws IOException {
		if (!FileUtil.exists(_sqlDir)) {
			return;
		}

		File sqlFile = new File(_sqlDir + "/" + _sqlSequencesFileName);

		if (!sqlFile.exists()) {
			FileUtils.writeStringToFile(sqlFile, "");
		}

		Set<String> sequenceSQLs = new TreeSet<String>();

		BufferedReader br = new BufferedReader(new FileReader(sqlFile));

		while (true) {
			String sequenceSQL = br.readLine();

			if (sequenceSQL == null) {
				break;
			}

			if (Validator.isNotNull(sequenceSQL)) {
				sequenceSQLs.add(sequenceSQL);
			}
		}

		br.close();

		for (int i = 0; i < _ejbList.size(); i++) {
			Entity entity = _ejbList.get(i);

			if (!entity.isDefaultDataSource()) {
				continue;
			}

			List<EntityColumn> columnList = entity.getColumnList();

			for (int j = 0; j < columnList.size(); j++) {
				EntityColumn column = columnList.get(j);

				if ("sequence".equals(column.getIdType())) {
					StringBuilder sb = new StringBuilder();

					String sequenceName = column.getIdParam();

					if (sequenceName.length() > 30) {
						sequenceName = sequenceName.substring(0, 30);
					}

					sb.append("create sequence " + sequenceName + ";");

					String sequenceSQL = sb.toString();

					if (!sequenceSQLs.contains(sequenceSQL)) {
						sequenceSQLs.add(sequenceSQL);
					}
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		Iterator<String> itr = sequenceSQLs.iterator();

		while (itr.hasNext()) {
			String sequenceSQL = itr.next();

			sb.append(sequenceSQL);

			if (itr.hasNext()) {
				sb.append("\n");
			}
		}

		FileUtil.write(sqlFile, sb.toString(), true);
	}

	private void _createSQLTables() throws IOException {
		if (!FileUtil.exists(_sqlDir)) {
			return;
		}

		File sqlFile = new File(_sqlDir + "/" + _sqlFileName);

		if (!sqlFile.exists()) {
			FileUtils.writeStringToFile(sqlFile, StringPool.BLANK);
		}

		for (int i = 0; i < _ejbList.size(); i++) {
			Entity entity = _ejbList.get(i);

			if (!entity.isDefaultDataSource()) {
				continue;
			}

			String createTableSQL = _getCreateTableSQL(entity);

			if (Validator.isNotNull(createTableSQL)) {
				_createSQLTables(sqlFile, createTableSQL, entity, true);
			}
		}

		for (Map.Entry<String, EntityMapping> entry : _entityMappings.entrySet()) {

			EntityMapping entityMapping = entry.getValue();

			String createMappingTableSQL = _getCreateMappingTableSQL(entityMapping);

			if (Validator.isNotNull(createMappingTableSQL)) {
				_createSQLMappingTables(sqlFile, createMappingTableSQL, entityMapping, true);
			}
		}
	}

	private void _createSQLTables(File sqlFile, String newCreateTableString, Entity entity,
			boolean addMissingTables) throws IOException {

		if (!sqlFile.exists()) {
			FileUtils.writeStringToFile(sqlFile, StringPool.BLANK);
		}

		String content = FileUtils.readFileToString(sqlFile);

		int x = content.indexOf(_SQL_CREATE_TABLE + entity.getTable() + " (");
		int y = content.indexOf(");", x);

		if (x != -1) {
			String oldCreateTableString = content.substring(x + 1, y);

			if (!oldCreateTableString.equals(newCreateTableString)) {
				content = content.substring(0, x) + newCreateTableString
						+ content.substring(y + 2, content.length());

				FileUtils.writeStringToFile(sqlFile, content);
			}
		} else if (addMissingTables) {
			StringBuilder sb = new StringBuilder();

			BufferedReader br = new BufferedReader(new StringReader(content));

			String line = null;
			boolean appendNewTable = true;

			while ((line = br.readLine()) != null) {
				if (appendNewTable && line.startsWith(_SQL_CREATE_TABLE)) {
					x = _SQL_CREATE_TABLE.length();
					y = line.indexOf(" ", x);

					String tableName = line.substring(x, y);

					if (tableName.compareTo(entity.getTable()) > 0) {
						sb.append(newCreateTableString + "\n\n");

						appendNewTable = false;
					}
				}

				sb.append(line);
				sb.append("\n");
			}

			if (appendNewTable) {
				sb.append("\n" + newCreateTableString);
			}

			br.close();

			FileUtil.write(sqlFile, sb.toString(), true);
		}
	}

	private void _createSQLForeignKeyConstraints() throws IOException {
		File sqlFile = new File(_sqlDir + "/" + _sqlForeignKeyFileName);

		if (!sqlFile.exists()) {
			FileUtils.writeStringToFile(sqlFile, StringPool.BLANK);
		}

		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < _ejbList.size(); i++) {
			Entity entity = _ejbList.get(i);

			if (!entity.isDefaultDataSource()) {
				continue;
			}

			String createFKConstraintSQL = _getFKConstraintSQL(entity);

			if (Validator.isNotNull(createFKConstraintSQL)) {
				sb.append(createFKConstraintSQL);
			}
		}
		
		FileUtil.write(sqlFile, sb.toString(), true);
	}
	
	private String _getFKConstraintSQL(Entity entity) {
		List<EntityColumn> foreignKeyColumns = new ArrayList<EntityColumn>(); 

		foreignKeyColumns.addAll(entity.getManyToOneList());
		foreignKeyColumns.addAll(entity.getManyToManyList());
		foreignKeyColumns.addAll(entity.getOneToOneList());

		if (foreignKeyColumns.isEmpty() && entity.getOneToManyList().isEmpty()) {
			// no relationships to create foreign keys for
			return null;
		}

		StringBuilder sb = new StringBuilder();

		// create constraint for child tables
		for (EntityColumn fkColumns : entity.getOneToManyList()) {
			String[] pkColumns = new String[entity.getPKList().size()];
			
			int i=0;
			for (EntityColumn column : entity.getPKList()) {
				pkColumns[i++] = column.getDBName();
			}

			_createSQLForeignKeyConstraintSQL(sb, fkColumns.getDBName(), entity.getTable(), fkColumns.getMappingKeys(), entity.getTable(), pkColumns);
		}
		
		// foreign keys
		for (EntityColumn fkColumns : foreignKeyColumns) {
			if (fkColumns.getMappingTable() != null) {
				// many to many
				_createSQLForeignKeyConstraintSQL(sb, entity.getTable(), fkColumns.getName(), fkColumns.getMappingKeys(), fkColumns.getMappingTable(), fkColumns.getMappingKeys());
			}
			else {
				Entity parentEntity = _entityLookup.get(fkColumns.getEJBName() != null ? fkColumns.getEJBName() : fkColumns.getType());

				String[] pkColumns = new String[parentEntity.getPKList().size()];
				
				int i=0;
				for (EntityColumn column : parentEntity.getPKList()) {
					pkColumns[i++] = column.getDBName();
				}
				
				_createSQLForeignKeyConstraintSQL(sb, entity.getTable(), fkColumns.getName(), fkColumns.getMappingKeys(), parentEntity.getTable(), pkColumns);
			}
		}
		
		return sb.toString();
	}

	// to prevent duplicate foreign key constraints
	protected Set<String> fkConstraints = new HashSet<String>(); 

	protected void _createSQLForeignKeyConstraintSQL(StringBuilder sb, String table, String property, String[] fkColumns, String referenceTable, String[] referenceColumns) {
		String constraintName = "FK_" + table.toUpperCase() + "_" + property.toUpperCase();
		
		if (fkConstraints.contains(constraintName)) {
			return;
		}
		else {
			fkConstraints.add(constraintName);
		}
		
		sb.append("alter table ");
		sb.append(table);
		sb.append(" add foreign key ");
		sb.append(constraintName);
		sb.append("(");
		
		boolean first = true;
		for (String column : fkColumns) {
			if (!first) {
				sb.append(", ");
			}
			else {
				first = false;
			}
			
			sb.append(column);
		}
		
		sb.append(") references ");
		
		sb.append(referenceTable);
		
		sb.append("(");
			
		first = true;
		for (String column : referenceColumns) {
			if (!first) {
				sb.append(", ");
			}
			else {
				first = false;
			}
			
			sb.append(column);
		}
		
		sb.append(");\n");
	}
	
	private String _fixHBMXML(String content) throws IOException {
		StringBuilder sb = new StringBuilder();

		BufferedReader br = new BufferedReader(new StringReader(content));

		String line = null;

		while ((line = br.readLine()) != null) {
			if (line.startsWith("\t<class name=\"")) {
				line = StringUtil.replace(line, new String[] { ".dao.", "HBM\" table=\"" },
						new String[] { ".model.", "\" table=\"" });

				if (line.indexOf(".model.") == -1) {
					line = StringUtil.replace(line, new String[] { ".model.", "\" table=\"" },
							new String[] { ".model.", "\" table=\"" });
				}
			}

			sb.append(line);
			sb.append('\n');
		}

		br.close();

		return sb.toString().trim();
	}

	private String _fixSpringXML(String content) {
		return StringUtil.replace(content, ".service.spring.", ".service.");
	}

	private String _formatXML(String xml) throws DocumentException, IOException {

		String doctype = null;

		int x = xml.indexOf("<!DOCTYPE");

		if (x != -1) {
			int y = xml.indexOf(">", x) + 1;

			doctype = xml.substring(x, y);

			xml = xml.substring(0, x) + "\n" + xml.substring(y);
		}

		xml = StringUtil.replace(xml, '\r', "");

		try {
			xml = XMLFormatter.toString(xml);
		} catch (DocumentException de) {
			String errorFile = "DocumentException.xml";
			System.err.println("*** XML input causing error written to " + errorFile + " ***");

			FileUtil.write(new File(errorFile), xml, false);

			throw de;
		}

		xml = StringUtil.replace(xml, "\"/>", "\" />");

		if (Validator.isNotNull(doctype)) {
			x = xml.indexOf("?>") + 2;

			xml = xml.substring(0, x) + "\n" + doctype + xml.substring(x);
		}

		return xml;
	}

	private Map<String, Object> _getContext() throws TemplateModelException {
		BeansWrapper wrapper = BeansWrapper.getDefaultInstance();

		TemplateHashModel staticModels = wrapper.getStaticModels();

		Map<String, Object> context = new HashMap<String, Object>();

		context.put("hbmFileName", _hbmFileName);
		context.put("springFileName", _springFileName);
		context.put("springDataSourceFileName", _springDataSourceFileName);
		// context.put("springMiscFileName", _springMiscFileName);
		context.put("implDir", _implDir);
		context.put("sqlDir", _sqlDir);
		context.put("sqlFileName", _sqlFileName);
		// context.put("portletName", _portletName);
		// context.put("portletShortName", _portletShortName);
		// context.put("portletPackageName", _portletPackageName);
		context.put("outputPath", _outputPath);
		context.put("packagePath", _packagePath);
		context.put("serviceBuilder", this);

		context.put("arrayUtil", ArrayUtil_IW.getInstance());
		context.put("stringUtil", StringUtil_IW.getInstance());
		context.put("system", staticModels.get("java.lang.System"));
		context.put("tempMap", wrapper.wrap(new HashMap<String, Object>()));
		context.put("validator", staticModels.get("com.augmentum.common.util.Validator"));

		return context;
	}

	private String _getCreateMappingTableSQL(EntityMapping entityMapping) throws IOException {

		Entity[] entities = new Entity[2];

		for (int i = 0; i < entities.length; i++) {
			entities[i] = getEntity(entityMapping.getEntity(i));

			if (entities[i] == null) {
				return null;
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(_SQL_CREATE_TABLE + entityMapping.getTable() + " (\n");

		for (Entity entity : entities) {
			List<EntityColumn> pkList = entity.getPKList();

			for (int i = 0; i < pkList.size(); i++) {
				EntityColumn col = pkList.get(i);

				// String colName = col.getName();
				String colType = col.getType();

				String colDbSize = col.getDbSize();
				String colDbType = col.getDbType();
				String dbDefaultValue = col.getDefaultValue();
				boolean dbNotNull = col.isNotNull();

				boolean notNull = false;

				sb.append("\t" + col.getDBName());
				sb.append(" ");

				String maxLengthStr = colDbSize;

				if (StringUtils.isNotBlank(colDbType)) {
					sb.append(colDbType);
					if (StringUtils.isNotBlank(maxLengthStr)) {
						sb.append("(");
						sb.append(maxLengthStr);
						sb.append(")");
					}
				} else if (colType.equalsIgnoreCase("boolean")) {
					sb.append("BOOLEAN");
				} else if (colType.equalsIgnoreCase("double") || colType.equalsIgnoreCase("float")) {
					if (StringUtils.isNotBlank(maxLengthStr))
						sb.append("DOUBLE(" + maxLengthStr + ")");
					else
						sb.append("DOUBLE");
				} else if (colType.equals("int") || colType.equals("Integer")
						|| colType.equalsIgnoreCase("short")) {
					if (StringUtils.isNotBlank(maxLengthStr))
						sb.append("INTEGER(" + maxLengthStr + ")");
					else
						sb.append("INTEGER");
				} else if (colType.equalsIgnoreCase("long")) {
					if (StringUtils.isNotBlank(maxLengthStr))
						sb.append("BIGINT(" + maxLengthStr + ")");
					else
						sb.append("BIGINT");
				} else if (colType.equals("String")) {
					// Map<String, String> hints = ModelHintsUtil.getHints(
					// _packagePath + ".model." + entity.getName(), colName);

					int maxLength = 75;

					// if (hints != null) {
					// maxLength = GetterUtil.getInteger(
					// hints.get("max-length"), maxLength);
					// }

					if (colDbSize != null) {
						maxLength = GetterUtil.getInteger(colDbSize, maxLength);
					}

					if (maxLength == -1) {
						sb.append("LONGTEXT");
					} else if (maxLength < 4000) {
						sb.append("VARCHAR(" + maxLength + ")");
					}
					// else if (maxLength == 4000) {
					// sb.append("STRING");
					// }
					else if (maxLength > 4000) {
						sb.append("TEXT");
					}
				} else if (colType.equals("Date")) {
					sb.append("DATE null");
				} else {
					sb.append("invalid");
				}

				if (StringUtils.isNotBlank(dbDefaultValue)) {
					sb.append(" default ");
					if (colType.equals("String"))
						sb.append("'");
					sb.append(dbDefaultValue);
					if (colType.equals("String"))
						sb.append("'");
					sb.append(" ");
				}

				if (col.isPrimary()) {
					sb.append(" not null");
				}

				if (!notNull && dbNotNull) {
					sb.append(" not null ");
				}

				sb.append(",\n");
			}
		}

		sb.append("\tprimary key (");

		for (int i = 0; i < entities.length; i++) {
			Entity entity = entities[i];

			List<EntityColumn> pkList = entity.getPKList();

			for (int j = 0; j < pkList.size(); j++) {
				EntityColumn col = pkList.get(j);

				String colName = col.getName();

				if ((i != 0) || (j != 0)) {
					sb.append(", ");
				}

				sb.append(colName);
			}
		}

		sb.append(")\n");
		sb.append(");");

		return sb.toString();
	}

	private String _getCreateTableSQL(Entity entity) {
		List<EntityColumn> pkList = entity.getPKList();
		List<EntityColumn> regularColList = entity.getRegularColList();

		if (regularColList.size() == 0) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		sb.append(_SQL_CREATE_TABLE + entity.getTable() + " (\n");

		for (int i = 0; i < regularColList.size(); i++) {
			EntityColumn col = regularColList.get(i);

			// String colName = col.getName();
			String colType = col.getType();
			String colIdType = col.getIdType();

			String colDbSize = col.getDbSize();
			String colDbType = col.getDbType();
			String dbDefaultValue = col.getDefaultValue();
			boolean dbNotNull = col.isNotNull();
			String dbCollate = col.getDbCollate();

			boolean notNull = false;

			sb.append("\t" + col.getDBName());
			sb.append(" ");

			String maxLengthStr = colDbSize;

			if (StringUtils.isNotBlank(colDbType)) {
				sb.append(colDbType);
				if (StringUtils.isNotBlank(maxLengthStr)) {
					sb.append("(");
					sb.append(maxLengthStr);
					sb.append(")");
				}
			} else if (colType.equalsIgnoreCase("boolean")) {
				sb.append("BOOLEAN");
			} else if (colType.equalsIgnoreCase("double") || colType.equalsIgnoreCase("float")) {
				if (StringUtils.isNotBlank(maxLengthStr))
					sb.append("DOUBLE(" + maxLengthStr + ")");
				else
					sb.append("DOUBLE");
			} else if (colType.equals("int") || colType.equals("Integer")
					|| colType.equalsIgnoreCase("short")) {
				if (StringUtils.isNotBlank(maxLengthStr))
					sb.append("INTEGER(" + maxLengthStr + ")");
				else
					sb.append("INTEGER");
			} else if (colType.equalsIgnoreCase("long")) {
				if (StringUtils.isNotBlank(maxLengthStr))
					sb.append("BIGINT(" + maxLengthStr + ")");
				else
					sb.append("BIGINT");
			} else if (colType.equals("String")) {
				// Map<String, String> hints = ModelHintsUtil.getHints(
				// _packagePath + ".model." + entity.getName(), colName);

				int maxLength = 75;

				// if (hints != null) {
				// maxLength = GetterUtil.getInteger(
				// hints.get("max-length"), maxLength);
				// }

				if (colDbSize != null) {
					maxLength = GetterUtil.getInteger(colDbSize, maxLength);
				}

				if (maxLength == -1) {
					sb.append("LONGTEXT");
				} else if (maxLength <= 4000) {
					sb.append("VARCHAR(" + maxLength + ")");
				}
				// else if (maxLength == 4000) {
				// sb.append("STRING");
				// }
				else if (maxLength > 4000) {
					sb.append("TEXT");
				}
				
				if (dbCollate != null) {
					sb.append(" collate ");
					sb.append(dbCollate);
				}
			} else if (colType.equals("Date")) {
				sb.append("DATETIME null");
			} else {
				sb.append("invalid");
			}

			if (StringUtils.isNotBlank(dbDefaultValue)) {
				sb.append(" default ");
				if (colType.equals("String"))
					sb.append("'");
				sb.append(dbDefaultValue);
				if (colType.equals("String"))
					sb.append("'");
				sb.append(" ");
			}

			if (col.isPrimary()) {
				notNull = true;
				sb.append(" not null");

				if (!entity.hasCompoundPK()) {
					sb.append(" primary key");
				}
			} else if (colType.equals("String")) {
				if (!dbNotNull)
					sb.append(" null");
			}

			if (!notNull && dbNotNull) {
				sb.append(" not null ");
			}

			if (Validator.isNotNull(colIdType) && colIdType.equals("identity")) {

				sb.append(" IDENTITY");
			}

			if (Validator.isNotNull(colIdType) && colIdType.equals("native")) {

				sb.append(" AUTO_INCREMENT");
			}

			if (((i + 1) != regularColList.size()) || (entity.hasCompoundPK())) {

				sb.append(",");
			}

			sb.append("\n");
		}

		if (entity.hasCompoundPK()) {
			sb.append("\tprimary key (");

			for (int j = 0; j < pkList.size(); j++) {
				EntityColumn pk = pkList.get(j);

				sb.append(pk.getDBName());

				if ((j + 1) != pkList.size()) {
					sb.append(", ");
				}
			}

			sb.append(")\n");
		}

		sb.append(");");

		return sb.toString();
	}

	private String _getDimensions(Type type) {
		String dimensions = "";

		for (int i = 0; i < type.getDimensions(); i++) {
			dimensions += "[]";
		}

		return dimensions;
	}

	private JavaClass _getJavaClass(String fileName) throws IOException {
		int pos = fileName.indexOf(_implDir + "/") + _implDir.length();

		String srcFile = fileName.substring(pos + 1, fileName.length());
		String className = StringUtil.replace(srcFile.substring(0, srcFile.length() - 5), "/", ".");

		JavaDocBuilder builder = new JavaDocBuilder();

		File file = new File(fileName);

		if (!file.exists()) {
			return null;
		}

		builder.addSource(file);

		return builder.getClassByName(className);
	}

	private JavaMethod[] _getMethods(JavaClass javaClass) {
		JavaMethod[] methods = javaClass.getMethods();

		for (JavaMethod method : methods) {
			Arrays.sort(method.getExceptions());
		}

		return methods;
	}

	private String _getSessionTypeName(int sessionType) {
		if (sessionType == _SESSION_TYPE_LOCAL) {
			return "Local";
		} else {
			return "";
		}
	}

	private String _getTplProperty(String key, String defaultValue) {
		return System.getProperty("service.tpl." + key, defaultValue);
	}

	private List<Entity> _mergeReferenceList(List<Entity> referenceList) {
		List<Entity> list = new ArrayList<Entity>(_ejbList.size() + referenceList.size());

		list.addAll(_ejbList);
		list.addAll(referenceList);

		return list;
	}

	private String _processTemplate(String name) throws Exception {
		return _processTemplate(name, _getContext());
	}

	private String _processTemplate(String name, Map<String, Object> context) throws Exception {

		return FreeMarkerUtil.process(name, context);
	}

	protected EntitySuperclass createSuperclass(Element entityEl, String superclassElementName) {
		EntitySuperclass superclass = null;

		Element superclassEl = entityEl.element(superclassElementName);

		if (superclassEl != null) {
			String name = superclassEl.attributeValue("name");
			String discriminatorValue = superclassEl.attributeValue("discriminator-value");
			String joinColumn = superclassEl.attributeValue("join-column");

			superclass = new EntitySuperclass(name, discriminatorValue, joinColumn);
		}

		return superclass;
	}

	protected String validateToManyCollectionType(String type) {
		if (!type.equals("List") && !type.equals("Set")) {
			throw new RuntimeException("Invalid collection type " + type + " specified.");
		}

		return type;
	}
	
	protected String hbmXmlPostProcess(String xml) throws Exception {
		// to fix the Hibernate Mapping XML DTD error that occurs with a discriminator that is a many-to-one object
		// change the output as follows:
		// <discriminator/><many-to-one/><version/> -> <discriminator/><version/><many-to-one/>
		
		// add tags to make XML well formed for processing
		// note: parsing full document added default attributes from DTD.
		String rootBegin = "<root>";
		String rootEnd = "</root>";
		
		Document doc = DocumentUtil.readDocumentFromXML(rootBegin+xml+rootEnd, false);
		Element rootEl = doc.getRootElement();

		boolean modified = false;
		List<Element> versionEls = rootEl.selectNodes( "//version" );
		
		for (Element versionEl : versionEls) {
			List<Element> precedingSiblings = versionEl.selectNodes("preceding-sibling::*");
			
			if (precedingSiblings != null && !precedingSiblings.isEmpty()) {
				Element sibling = precedingSiblings.get(precedingSiblings.size()-1);
				
				if (sibling.getName().indexOf("-to-") > 0) {
					List<Element> followingSiblings = versionEl.selectNodes("following-sibling::*");
					
					List<Element> newOrder = new ArrayList<Element>();
					newOrder.addAll(precedingSiblings);
					newOrder.add(precedingSiblings.size()-1, versionEl);
					
					newOrder.addAll(followingSiblings);
					
					Element parent = versionEl.getParent();
					parent.setContent(newOrder);

					modified = true;
				}
			}
		}
		
		if (modified) {
			// remove the root wrapper
			xml = rootEl.asXML().substring(rootBegin.length());
			return xml.substring(0, xml.length() - rootEnd.length());
		}
		else {
			return xml;
		}
	}
}