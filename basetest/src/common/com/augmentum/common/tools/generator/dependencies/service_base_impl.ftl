package ${packagePath}.service;

import com.augmentum.common.baseService.BaseServiceImpl;
import com.augmentum.common.util.OrderByComparator;

import ${packagePath}.dao.${entity.name}DAO;

<#if (entity.TXManager != "none") && (entity.referenceList??)>
	<#list entity.referenceList as tempEntity>
		<#if (!tempEntity.isRefServiceOnly())>
			import ${tempEntity.packagePath}.dao.${tempEntity.name}DAO;
		</#if>
		import ${tempEntity.packagePath}.service.${tempEntity.name}Service;
	</#list>
</#if>

<#if entity.hasColumns()>
	<#if entity.hasCompoundPK()>
		import ${packagePath}.model.${entity.name}PK;
	</#if>

	import ${packagePath}.model.${entity.name};
	import ${packagePath}.model.${entity.name}Impl;
<#if entity.hasCompoundPK()>
	import ${packagePath}.model.${entity.name}PK;
</#if>

	import java.util.Date;
	import java.util.List;
</#if>

<#if entity.getFinderList()??>
	import ${packagePath}.${noSuchEntity}Exception;
</#if>

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import com.kt.common.service.SpringServiceLocator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public abstract class ${entity.name}ServiceBaseImpl extends
	<#if entity.serviceBase ??>
		${entity.serviceBase}
	<#else>
		BaseServiceImpl<${entity.name}, ${entity.name}DAO, ${entity.PKClassName}> 
	</#if>
	{

	private static Log _log = LogFactory.getLog(${entity.name}ServiceBaseImpl.class);
	
	<#if (entity.TXManager != "none") && (entity.referenceList??)>
		<#list entity.referenceList as tempEntity>
			<#if (!tempEntity.isRefServiceOnly())>
				protected ${tempEntity.name}DAO ${tempEntity.varName}DAO;
			</#if>
			protected ${tempEntity.name}Service ${tempEntity.varName}Service;
		</#list>
		
		<#list entity.referenceList as tempEntity>
			<#if (!tempEntity.isRefServiceOnly())>
				public ${tempEntity.name}DAO get${tempEntity.name}DAO() {
					return ${tempEntity.varName}DAO;
				}
		
				public void set${tempEntity.name}DAO(${tempEntity.name}DAO ${tempEntity.varName}DAO) {
					this.${tempEntity.varName}DAO = ${tempEntity.varName}DAO;
				}
			</#if>
			
			public ${tempEntity.name}Service get${tempEntity.name}Service() {
				return ${tempEntity.varName}Service;
			}
	
			public void set${tempEntity.name}Service(${tempEntity.name}Service ${tempEntity.varName}Service) {
				this.${tempEntity.varName}Service = ${tempEntity.varName}Service;
			}
		</#list>
	</#if>

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

			) {
				return getDao().findBy${finder.name} (
					<#list finderColsList as finderCol>
						${finderCol.name}
						<#if finderCol_has_next>
							,
						</#if>
					</#list>
				);
			}
			
			public List<${entity.name}> findBy${finder.name} (

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name},
			</#list>

			int start, int maxResults) {
				return getDao().findBy${finder.name} (
					<#list finderColsList as finderCol>
						${finderCol.name},
					</#list>
					start, maxResults
				);
			}
			
			public List<${entity.name}> findBy${finder.name} (

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name},
			</#list>

			int start, int maxResults, OrderByComparator obc) {
				return getDao().findBy${finder.name} (
					<#list finderColsList as finderCol>
						${finderCol.name},
					</#list>
					start, maxResults, obc
				);
			}
			
			public ${entity.name} findBy${finder.name}_First(

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name},
			</#list>

			OrderByComparator obc) throws ${noSuchEntity}Exception {
				return getDao().findBy${finder.name}_First (
					<#list finderColsList as finderCol>
						${finderCol.name},
					</#list>
					obc
				);
			}
			
			public ${entity.name} findBy${finder.name}_Last(

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name},
			</#list>

			OrderByComparator obc) throws ${noSuchEntity}Exception {
				return getDao().findBy${finder.name}_Last (
					<#list finderColsList as finderCol>
						${finderCol.name},
					</#list>
					obc
				);
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
				return getDao().findBy${finder.name} (
					<#list finderColsList as finderCol>
						${finderCol.name}
		
						<#if finderCol_has_next>
							,
						</#if>
					</#list>
				);
			}
			
			public ${entity.name} fetchBy${finder.name}(

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name}

				<#if finderCol_has_next>
					,
				</#if>
			</#list>

			) {
				return getDao().fetchBy${finder.name} (
					<#list finderColsList as finderCol>
						${finderCol.name}
		
						<#if finderCol_has_next>
							,
						</#if>
					</#list>
				);
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
				getDao().removeBy${finder.name} (
					<#list finderColsList as finderCol>
						${finderCol.name}<#if finderCol_has_next>,</#if>
					</#list>
				);
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
				getDao().removeBy${finder.name} (
					<#list finderColsList as finderCol>
						${finderCol.name}<#if finderCol_has_next>,</#if>
					</#list>
				);
			}
		</#if>
	</#list>
	
	public void removeAll() {
		getDao().removeAll();
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
			return getDao().countBy${finder.name} (
				<#list finderColsList as finderCol>
					${finderCol.name}<#if finderCol_has_next>,</#if>
				</#list>
			);
		}
	</#list>
		
	public int countAll() {
		return getDao().countAll();
	}

}