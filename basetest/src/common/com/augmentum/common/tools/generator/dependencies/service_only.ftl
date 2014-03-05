package ${packagePath}.service;

/**
 * <a href="${service.name}.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * <code>${packagePath}.service.${service.name}Impl</code>.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 */
public interface ${service.name}
{

	<#list methods as method>
		<#if !method.isConstructor() && method.isPublic() && serviceBuilder.isCustomMethod(method) && !serviceBuilder.isDuplicateMethod(method, tempMap)>
			public ${method.returns.toGenericString()} ${method.name}(

			<#list method.parameters as parameter>
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