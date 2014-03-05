<#list entities as entity>
	<#include "spring_xml_session.ftl">

	<#if entity.hasColumns()>
		<bean id="${packagePath}.dao.${entity.name}DAO" class="${packagePath}.dao.${entity.name}DAOImpl" lazy-init="true">

		</bean>       
	</#if>
</#list>