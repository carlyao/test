package ${packagePath}.model;

import com.kt.common.util.DateUtil;
import com.kt.common.util.StringPool;

import java.io.Serializable;

import java.util.Date;
import java.util.Calendar;

public class ${entity.PKClassName} implements Comparable<${entity.PKClassName}>, Serializable {

	<#list entity.PKList as column>
		public ${column.type} ${column.name};
	</#list>

	public ${entity.PKClassName}() {
	}

	public ${entity.PKClassName}(

	<#list entity.PKList as column>
		${column.type} ${column.name}

		<#if column_has_next>
			,
		</#if>
	</#list>

	) {
		<#list entity.PKList as column>
			this.${column.name} = ${column.name};
		</#list>
	}

	<#list entity.PKList as column>
		<#if !column.isCollection()>
			public ${column.type} get${column.methodName}() {
				return ${column.name};
			}

			<#if column.type== "boolean">
				public ${column.type} is${column.methodName}() {
					return ${column.name};
				}
			</#if>

			public void set${column.methodName}(${column.type} ${column.name}) {
				this.${column.name} = ${column.name};
			}
		</#if>
	</#list>

	public int compareTo(${entity.PKClassName} pk) {
		if (pk == null) {
			return -1;
		}

		int value = 0;

		<#list entity.PKList as column>
			<#if column.isPrimitiveType()>
				<#if column.type == "boolean">
					if (!${column.name} && pk.${column.name}) {
						value = -1;
					}
					else if (${column.name} && !pk.${column.name}) {
						value = 1;
					}
					else {
						value = 0;
					}
				<#else>
					if (${column.name} < pk.${column.name}) {
						value = -1;
					}
					else if (${column.name} > pk.${column.name}) {
						value = 1;
					}
					else {
						value = 0;
					}
				</#if>
			<#else>
				<#if column.type == "Date">
					value = DateUtil.compareTo(${column.name}, pk.${column.name});
				<#else>
					value = ${column.name}.compareTo(pk.${column.name});
				</#if>
			</#if>

			if (value != 0) {
				return value;
			}
		</#list>

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		${entity.PKClassName} pk = null;

		try {
			pk = (${entity.PKClassName})obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		if (

		<#list entity.PKList as column>
			<#if column.isPrimitiveType()>
				(${column.name} == pk.${column.name})
			<#else>
				(${column.name}.equals(pk.${column.name}))
			</#if>

			<#if column_has_next> && </#if>
		</#list>

		) {
			return true;
		}
		else{
			return false;
		}
	}

	public int hashCode() {
		return (

		<#list entity.PKList as column>
			<#if !column.isPrimitiveType() && column.type != "String">
				${column.name}.toString()
			<#else>
				String.valueOf(${column.name})
			</#if>

			<#if column_has_next>
				+
			</#if>
		</#list>

		).hashCode();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		

		<#list entity.PKList as column>
			sb.append("${column.name}");
			sb.append(${column.name});

			
		</#list>

		

		return sb.toString();
	}

}