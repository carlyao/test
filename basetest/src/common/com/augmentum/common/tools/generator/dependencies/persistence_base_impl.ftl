package ${packagePath}.persistence;

<#assign noSuchEntity = serviceBuilder.getNoSuchEntityException(entity)>

import ${packagePath}.${noSuchEntity}Exception;
import ${packagePath}.model.${entity.name};
import ${packagePath}.model.${entity.name}ModelImpl;

import com.kt.common.persistence.dao.hibernate.AbstractHibernateDao;

import com.kt.common.persistence.dao.orm.QueryPos;
import com.kt.common.persistence.dao.orm.QueryUtil;
import com.kt.common.util.CalendarUtil;
import com.kt.common.util.GetterUtil;
import com.kt.common.util.OrderByComparator;
import com.kt.common.util.StringPool;
import com.kt.common.util.StringUtil;
import com.kt.common.util.Validator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ${entity.name}PersistenceBaseImpl extends AbstractHibernateDao<${entity.name}, ${entity.PKClassName}> {

	private static Log _log = LogFactory.getLog(${entity.name}PersistenceBaseImpl.class);
	
	<#list entity.getFinderList() as finder>
		<#assign finderColsList = finder.getColumns()>

		<#if finder.isCollection()>
		
			public List<${entity.name}> findBy${finder.name} (

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name}

				<#if finderCol_has_next>
					,
				</#if>
			</#list>

			)  {
				return findBy${finder.name}(

				<#list finderColsList as finderCol>
					${finderCol.name},
				</#list>

				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
			}

			public List<${entity.name}> findBy${finder.name} (

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name},
			</#list>

			int start, int maxResults) {
				return findBy${finder.name}(

				<#list finderColsList as finderCol>
					${finderCol.name},
				</#list>

				start, maxResults, null);
			}

			public List<${entity.name}> findBy${finder.name} (

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name},
			</#list>

			int start, int maxResults, OrderByComparator obc) {

				Session session = getSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM ${packagePath}.model.${entity.name} WHERE ");

				<#list finderColsList as finderCol>
					<#if !finderCol.isPrimitiveType()>
						if (${finderCol.name} == null) {
							<#if finderCol.comparator == "=">
								query.append("${finderCol.DBName} IS NULL");
							<#elseif finderCol.comparator == "<>" || finderCol.comparator = "!=">
								query.append("${finderCol.DBName} IS NOT NULL");
							<#else>
								query.append("${finderCol.DBName} ${finderCol.comparator} null");
							</#if>
						}
						else {
					</#if>

					<#if finderCol.type == "String" && !finderCol.isCaseSensitive()>
						query.append("lower(${finderCol.DBName}) ${finderCol.comparator} lower(?)");
					<#else>
						query.append("${finderCol.DBName} ${finderCol.comparator} ?");
					</#if>

					<#if !finderCol.isPrimitiveType()>
						}
					</#if>

					<#if finderCol_has_next>
						query.append(" AND ");
					<#elseif finder.where?? && !validator.isNull(finder.getWhere())>
						query.append(" AND ${finder.where} ");
					<#else>
						query.append(" ");
					</#if>
				</#list>

				<#if finder.getOrder()??>
						query.append("ORDER BY ");

						<#assign orderList = finder.getOrder().getColumns()>

						<#list orderList as order>
							<#if !order.isCaseSensitive()>
								query.append("lower(");
							</#if>
							query.append("${order.DBName}");
							<#if !order.isCaseSensitive()>
								query.append(")");
							</#if>
							query.append("	<#if order.isOrderByAscending()>ASC<#else>DESC</#if><#if order_has_next>, </#if>");
						</#list>
				<#else>
					if (obc != null) {
						query.append("ORDER BY ");
						query.append(obc.getOrderBy());
					}
	
					<#if entity.getOrder()??>
						else {
							query.append("ORDER BY ");
	
							<#assign orderList = entity.getOrder().getColumns()>
	
							<#list orderList as order>
								<#if !order.isCaseSensitive()>
									query.append("lower(");
								</#if>
								query.append("${order.DBName}");
								<#if !order.isCaseSensitive()>
									query.append(")");
								</#if>
								query.append("	<#if order.isOrderByAscending()>ASC<#else>DESC</#if><#if order_has_next>, </#if>");
							</#list>
						}
					</#if>
				</#if>

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				<#list finderColsList as finderCol>
					<#if !finderCol.isPrimitiveType()>
						if (${finderCol.name} != null) {
					</#if>

					qPos.add(

					<#if finderCol.type == "Date">
						CalendarUtil.getTimestamp(
					</#if>

					${finderCol.name}${serviceBuilder.getPrimitiveObjValue("${finderCol.type}")}

					<#if finderCol.type == "Date">
						)
					</#if>

					);

					<#if !finderCol.isPrimitiveType()>
						}
					</#if>
				</#list>
				
				if (maxResults != QueryUtil.ALL_POS)
					q.setMaxResults(maxResults);
				if (start != QueryUtil.ALL_POS)
					q.setFirstResult(start);

				List<${entity.name}> list = q.list();

				return list;
			}

			public ${entity.name} findBy${finder.name}_First(

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name},
			</#list>

			OrderByComparator obc) throws ${noSuchEntity}Exception {
				List<${entity.name}> list = findBy${finder.name}(

				<#list finderColsList as finderCol>
					${finderCol.name},
				</#list>

				0, 1, obc);

				if (list.size() == 0) {
					StringBuilder msg = new StringBuilder();

					msg.append("No ${entity.name} exists with the key {");

					<#list finderColsList as finderCol>
						msg.append("${finderCol.name}=" + ${finderCol.name});

						<#if finderCol_has_next>
							msg.append(", ");
						<#else>
							msg.append(StringPool.CLOSE_CURLY_BRACE);
						</#if>
					</#list>

					throw new ${noSuchEntity}Exception(msg.toString());
				}
				else {
					return list.get(0);
				}
			}

			public ${entity.name} findBy${finder.name}_Last(

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name},
			</#list>

			OrderByComparator obc) throws ${noSuchEntity}Exception {
				int count = countBy${finder.name}(

				<#list finderColsList as finderCol>
					${finderCol.name}

					<#if finderCol_has_next>
						,
					</#if>
				</#list>

				);

				List<${entity.name}> list = findBy${finder.name}(

				<#list finderColsList as finderCol>
					${finderCol.name},
				</#list>

				count - 1, count, obc);

				if (list.size() == 0) {
					StringBuilder msg = new StringBuilder();

					msg.append("No ${entity.name} exists with the key {");

					<#list finderColsList as finderCol>
						msg.append("${finderCol.name}=" + ${finderCol.name});

						<#if finderCol_has_next>
							msg.append(", ");
						<#else>
							msg.append(StringPool.CLOSE_CURLY_BRACE);
						</#if>
					</#list>

					throw new ${noSuchEntity}Exception(msg.toString());
				}
				else {
					return list.get(0);
				}
			}
			
		<#else>
		
			public ${entity.name} findBy${finder.name}(

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name}

				<#if finderCol_has_next>
					,
				</#if>
			</#list>

			) throws ${noSuchEntity}Exception {
				${entity.name} ${entity.varName} = fetchBy${finder.name}(

				<#list finderColsList as finderCol>
					${finderCol.name}

					<#if finderCol_has_next>
						,
					</#if>
				</#list>

				);

				if ( ${entity.varName} == null) {
					StringBuilder msg = new StringBuilder();

					msg.append("No ${entity.name} exists with the key {");

					<#list finderColsList as finderCol>
						msg.append("${finderCol.name}=" + ${finderCol.name});

						<#if finderCol_has_next>
							msg.append(", ");
						<#else>
							msg.append(StringPool.CLOSE_CURLY_BRACE);
						</#if>
					</#list>

					if (_log.isWarnEnabled()) {
						_log.warn(msg.toString());
					}

					throw new ${noSuchEntity}Exception(msg.toString());
				}

				return ${entity.varName};
			}

			public ${entity.name} fetchBy${finder.name}(

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name}

				<#if finderCol_has_next>
					,
				</#if>
			</#list>

			)  {
				Session session = getSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM ${packagePath}.model.${entity.name} WHERE ");

				<#list finderColsList as finderCol>
					<#if !finderCol.isPrimitiveType()>
						if (${finderCol.name} == null) {
							<#if finderCol.comparator == "=">
								query.append("${finderCol.DBName} IS NULL");
							<#elseif finderCol.comparator == "<>" || finderCol.comparator = "!=">
								query.append("${finderCol.DBName} IS NOT NULL");
							<#else>
								query.append("${finderCol.DBName} ${finderCol.comparator} null");
							</#if>
						}
						else {
					</#if>

					<#if finderCol.type == "String" && !finderCol.isCaseSensitive()>
						query.append("lower(${finderCol.DBName}) ${finderCol.comparator} lower(?)");
					<#else>
						query.append("${finderCol.DBName} ${finderCol.comparator} ?");
					</#if>

					<#if !finderCol.isPrimitiveType()>
						}
					</#if>

					<#if finderCol_has_next>
						query.append(" AND ");
					<#elseif finder.where?? && !validator.isNull(finder.getWhere())>
						query.append(" AND ${finder.where} ");
					<#else>
						query.append(" ");
					</#if>
				</#list>
				
				<#if finder.getOrder()??>
						query.append("ORDER BY ");

						<#assign orderList = finder.getOrder().getColumns()>

						<#list orderList as order>
							<#if !order.isCaseSensitive()>
								query.append("lower(");
							</#if>
							query.append("${order.DBName}");
							<#if !order.isCaseSensitive()>
								query.append(")");
							</#if>
							query.append("	<#if order.isOrderByAscending()>ASC<#else>DESC</#if><#if order_has_next>, </#if>");
						</#list>
				<#else>
					<#if entity.getOrder()??>
						else {
							query.append("ORDER BY ");
	
							<#assign orderList = entity.getOrder().getColumns()>
	
							<#list orderList as order>
								<#if !order.isCaseSensitive()>
									query.append("lower(");
								</#if>
								query.append("${order.DBName}");
								<#if !order.isCaseSensitive()>
									query.append(")");
								</#if>
								query.append("	<#if order.isOrderByAscending()>ASC<#else>DESC</#if><#if order_has_next>, </#if>");
							</#list>
						}
					</#if>
				</#if>

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				<#list finderColsList as finderCol>
					<#if !finderCol.isPrimitiveType()>
						if (${finderCol.name} != null) {
					</#if>

					qPos.add(

					<#if finderCol.type == "Date">
						CalendarUtil.getTimestamp(
					</#if>

					${finderCol.name}${serviceBuilder.getPrimitiveObjValue("${finderCol.type}")}

					<#if finderCol.type == "Date">
						)
					</#if>

					);

					<#if !finderCol.isPrimitiveType()>
						}
					</#if>
				</#list>

				List<${entity.name}> list = q.list();

				if (list.size() == 0) {
					return null;
				}
				else {
					return list.get(0);
				}
			}
		</#if>
	</#list>
	
	<#list entity.getFinderList() as finder>
		<#assign finderColsList = finder.getColumns()>

		<#if finder.isCollection()>
			public void removeBy${finder.name}(

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name}<#if finderCol_has_next>,</#if>
			</#list>

			) {
				for (${entity.name} ${entity.varName} : findBy${finder.name}(

				<#list finderColsList as finderCol>
					${finderCol.name}

					<#if finderCol_has_next>
						,
					</#if>
				</#list>

				)) {
					remove(${entity.varName});
				}
			}
		<#else>
			public void removeBy${finder.name}(

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name}

				<#if finderCol_has_next>
					,
				</#if>
			</#list>

			) throws ${noSuchEntity}Exception {
				${entity.name} ${entity.varName} = findBy${finder.name}(

				<#list finderColsList as finderCol>
					${finderCol.name}

					<#if finderCol_has_next>
						,
					</#if>
				</#list>

				);

				remove(${entity.varName});
			}
		</#if>
	</#list>

	public void removeAll() {
		for (${entity.name} ${entity.varName} : findAll()) {
			remove(${entity.varName});
		}
	}

	<#list entity.getFinderList() as finder>
		<#assign finderColsList = finder.getColumns()>

		public int countBy${finder.name}(

		<#list finderColsList as finderCol>
			${finderCol.type} ${finderCol.name}

			<#if finderCol_has_next>
				,
			</#if>
		</#list>

		) {
			Session session = getSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT COUNT(*) ");
			query.append("FROM ${packagePath}.model.${entity.name} WHERE ");

			<#list finderColsList as finderCol>
				<#if !finderCol.isPrimitiveType()>
					if (${finderCol.name} == null) {
						<#if finderCol.comparator == "=">
							query.append("${finderCol.DBName} IS NULL");
						<#elseif finderCol.comparator == "<>" || finderCol.comparator = "!=">
							query.append("${finderCol.DBName} IS NOT NULL");
						<#else>
							query.append("${finderCol.DBName} ${finderCol.comparator} null");
						</#if>
					}
					else {
				</#if>

				<#if finderCol.type == "String" && !finderCol.isCaseSensitive()>
					query.append("lower(${finderCol.DBName}) ${finderCol.comparator} lower(?)");
				<#else>
					query.append("${finderCol.DBName} ${finderCol.comparator} ?");
				</#if>

				<#if !finderCol.isPrimitiveType()>
					}
				</#if>

				<#if finderCol_has_next>
					query.append(" AND ");
				<#elseif finder.where?? && !validator.isNull(finder.getWhere())>
					query.append(" AND ${finder.where} ");
				<#else>
					query.append(" ");
				</#if>
			</#list>

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			<#list finderColsList as finderCol>
				<#if !finderCol.isPrimitiveType()>
					if (${finderCol.name} != null) {
				</#if>

				qPos.add(

				<#if finderCol.type == "Date">
					CalendarUtil.getTimestamp(
				</#if>

				${finderCol.name}${serviceBuilder.getPrimitiveObjValue("${finderCol.type}")}

				<#if finderCol.type == "Date">
					)
				</#if>

				);

				<#if !finderCol.isPrimitiveType()>
					}
				</#if>
			</#list>

			Long count = null;

			Iterator<Long> itr = q.list().iterator();

			if (itr.hasNext()) {
				count = itr.next();
			}

			if (count == null) {
				count = new Long(0);
			}

			return count.intValue();
		}
	</#list>

	public int countAll() {
		Session session = getSession();

		Query q = session.createQuery("SELECT COUNT(*) FROM ${packagePath}.model.${entity.name}");

		Long count = null;

		Iterator<Long> itr = q.list().iterator();

		if (itr.hasNext()) {
			count = itr.next();
		}

		if (count == null) {
			count = new Long(0);
		}

		return count.intValue();
	}
}