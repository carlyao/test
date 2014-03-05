package ${packagePath}.model;


import ${packagePath}.model.${entity.name};
<#if entity.hasCompoundPK()>
    import ${packagePath}.model.${entity.name}PK;
</#if>

import com.kt.common.util.DateUtil;
import com.kt.common.util.GetterUtil;
import com.kt.common.util.HtmlUtil;

import com.augmentum.common.basemodel.AbstractModel;
import com.augmentum.common.basemodel.BaseModel;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.EmbeddedId;
import org.apache.commons.collections.CollectionUtils;

import org.codehaus.jackson.annotate.JsonIgnore;



/**
 * <a href="${entity.name}ModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>${entity.name}</code> table
 * in the database.
 * </p>
 *
 */
@MappedSuperclass
@Table(name = "${entity.name}")
public class ${entity.name}ModelImpl extends 
    <#if entity.modelBase ??>
        ${entity.modelBase}
    <#else>
        <#if entity.isSubclass()>
            ${entity.getSuperclass().getPackagePath()}.model.${entity.getSuperclass().getName()}
        <#else>
            AbstractModel
        </#if>
    </#if>
 {

	<#if entity.hasCompoundPK()>
		@EmbeddedId
		private ${entity.name}PK ${entity.varName}PK;
		<#list entity.regularColList as column>
			<#if !column.isPrimary()>
			@Column(name = "${column.name}")
			private ${column.type} ${column.name};
			</#if>
		</#list>
		
	<#else>
		<#list entity.regularColList as column>
			<#if column.isPrimary()>
			@Id
			<#if column.type == "Long">
			@GeneratedValue
			<#else>
			</#if>
			private ${column.type} ${column.name};
			<#else>
			@Column(name = "${column.name}")
			private ${column.type} ${column.name};
			</#if>
		</#list>
	</#if>
	
   

	public ${entity.name}ModelImpl() {}
	

	<#if entity.hasCompoundPK()>
		public ${entity.name}PK get${entity.name}PK(){
			return ${entity.varName}PK;
		};
		public void set${entity.name}PK(${entity.name}PK ${entity.varName}PK){
			this.${entity.varName}PK = ${entity.varName}PK;
		}
		<#list entity.regularColList as column>
			<#if !column.isPrimary()>
				public void set${column.methodName}(${column.type} ${column.name}){
					this.${column.name} = ${column.name};
				}
				
				public ${column.type} get${column.methodName}(){
					return ${column.name};
				}
			</#if>
		</#list>
		
	<#else>
		<#list entity.regularColList as column>
			public void set${column.methodName}(${column.type} ${column.name}){
				this.${column.name} = ${column.name};
			}
			
			public ${column.type} get${column.methodName}(){
				return ${column.name};
			}
		</#list>
	</#if>
	


 public void lazyLoadAll() {
        <#list entity.oneToOneList as column>
            if ((get${column.methodName}() != null) && (get${column.methodName}().getPrimaryKey() != null)) {
                (get${column.methodName}()).getPrimaryKey().toString();
            }
        </#list>
        <#list entity.oneToManyList as column>
            if (CollectionUtils.isNotEmpty(get${column.methodName}())) {
                Iterator it = get${column.methodName}().iterator();
                while (it.hasNext()) {
                    ((IBaseModel)it.next()).lazyLoadAll();
                }
            }
        </#list>
        <#list entity.manyToOneList as column>
            if ((get${column.methodName}() != null) && (get${column.methodName}().getPrimaryKey() != null)) {
                (get${column.methodName}()).getPrimaryKey().toString();
            }
        </#list>
        <#list entity.manyToManyList as column>
            if (CollectionUtils.isNotEmpty(get${column.methodName}())) {
                Iterator it = get${column.methodName}().iterator();
                while (it.hasNext()) {
                    ((${column.ejbClass})it.next()).getPrimaryKey().toString();
                }
            }
        </#list>
    }
	
	public Object deepClone() {
         ${entity.name} clone = (${entity.name})clone();
         
         return deepClone(clone);
    }
	
	public Object deepClone(${entity.name} inputClone) {
    
        ${entity.name} clone = inputClone;
        
        <#if entity.isSubclass()>
            clone = (${entity.name}) super.deepClone(clone);
        </#if>

        <#list entity.oneToOneList as column>
            if (get${column.methodName}() != null) {
                clone.set${column.methodName}((${column.ejbClass})get${column.methodName}().clone());
            }
        </#list>
        <#list entity.oneToManyList as column>
            if (CollectionUtils.isNotEmpty(get${column.methodName}())) {
                <#if column.type = "Set">
                    <#assign collectionType = "HashSet">
                <#else>
                    <#assign collectionType = "ArrayList">
                </#if>
                ${column.type}<${column.ejbClass}> data= new ${collectionType}<${column.ejbClass}>();
                Iterator it = get${column.methodName}().iterator();
                while (it.hasNext()) {
                    data.add((${column.ejbClass})((IBaseModel)it.next()).deepClone());
                }
                clone.set${column.methodName}(data);
            }
        </#list>
        <#list entity.manyToOneList as column>
            if (get${column.methodName}() != null) {
                clone.set${column.methodName}((${column.ejbClass})get${column.methodName}().clone());
            }
        </#list>
        <#list entity.manyToManyList as column>
            if (CollectionUtils.isNotEmpty(get${column.methodName}())) {
                <#if column.type = "Set">
                    <#assign collectionType = "HashSet">
                <#else>
                    <#assign collectionType = "ArrayList">
                </#if>
                ${column.type}<${column.ejbClass}> data= new ${collectionType}<${column.ejbClass}>();
                Iterator it = get${column.methodName}().iterator();
                while (it.hasNext()) {
                    data.add((${column.ejbClass})((${column.ejbClass})it.next()).clone());
                }
                clone.set${column.methodName}(data);
            }
        </#list>

        return clone;
    }
	
	public Object clone() {
         ${entity.name} clone = new ${entity.name}();
         
         return clone(clone);
    }
	
	public Object clone(${entity.name} inputClone) {
    
        ${entity.name} clone = inputClone;
        
        <#if entity.isSubclass()>
            clone = (${entity.name}) super.clone(clone);
        </#if>

		
		<#if entity.hasCompoundPK()>
			clone.set${entity.name}PK(${entity.varName}PK);
			<#list entity.regularColList as column>
				<#if !column.isPrimary()>
					clone.set${column.methodName}(

					<#if column.EJBName??>
						(${column.EJBName})get${column.methodName}().clone()
					<#else>
						get${column.methodName}()
					</#if>

					);
				</#if>
			</#list>
			
		<#else>
			 <#list entity.regularColList as column>
				clone.set${column.methodName}(

				<#if column.EJBName??>
					(${column.EJBName})get${column.methodName}().clone()
				<#else>
					get${column.methodName}()
				</#if>

				);
			</#list>
		</#if>
		
       

        return clone;
    }
}