package ${packagePath}.dao;

import com.kt.common.persistence.dao.GenericDao;

import ${packagePath}.model.${entity.name};
<#if entity.hasCompoundPK()>
	import ${packagePath}.model.${entity.name}PK;
</#if>

import java.util.Date;
import com.augmentum.common.basedao.BaseDao;

public interface ${entity.name}DAO extends  
	<#if entity.DAOIntfBase ??>
		${entity.DAOIntfBase}
	<#else>
		BaseDao<${entity.name}>
	</#if>
{

	<#list methods as method>
		<#if !method.isConstructor() && method.isPublic() && serviceBuilder.isCustomMethod(method)>
			<#if method.name == "update">
				<#if arrayUtil.getLength(method.parameters) == 1>
	/**
	 * @deprecated Use <code>update(${entity.name} ${entity.varName}, boolean merge)</code>.
	 */
				<#else>
	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param		${entity.varName} the entity to add, update, or merge
	 * @param		merge boolean value for whether to merge the entity. The
	 *				default value is false. Setting merge to true is more
	 *				expensive and should only be true when ${entity.varName} is
	 *				transient. See LEP-5473 for a detailed discussion of this
	 *				method.
	 * @return		true if the portlet can be displayed via Ajax
	 */
				</#if>
			</#if>

			public ${method.returns.toGenericString()} ${method.name} (

			<#assign parameters = method.parameters>

			<#list parameters as parameter>
				${parameter.type.toGenericString()}

				<#if parameter.isVarArgs() >
					...
				</#if>

				${parameter.name}

				<#if parameter_has_next>
					,
				</#if>
			</#list>

			)

			<#list method.exceptions as exception>
				<#if exception_index == 0>
					throws
				</#if>

				${exception.value}

				<#if exception_has_next>
					,
				</#if>
			</#list>

			;
		</#if>
	</#list>

}