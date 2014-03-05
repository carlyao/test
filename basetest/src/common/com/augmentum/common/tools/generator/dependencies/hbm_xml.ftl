<#list entities as entity>
	<#if entity.hasColumns()>
		<class name="${entity.packagePath}.model.${entity.name}" table="${entity.table}"
			<#-- default value discriminator -->
			<#if entity.isSuperclass()>
				discriminator-value="0"
			</#if>
		>
			<#if entity.isCacheEnabled()>
				<cache usage="read-write" />
			</#if>

			<#if entity.hasCompoundPK()>
				<composite-id name="primaryKey" class="${entity.packagePath}.model.${entity.name}PK">
					<#assign pkList = entity.getPKList()>

					<#list pkList as column>
						<key-property name="${column.name}"

						<#if column.name != column.DBName>
							column="${column.DBName}"
						</#if>

						/>
					</#list>
				</composite-id>
			<#else>
				<#assign column = entity.getPKList()?first>

				<id name="${column.name}"
					<#if column.name != column.DBName>
						column="${column.DBName}"
					</#if>

					type="<#if !entity.hasPrimitivePK()>java.lang.</#if>${column.type}">

					<#if column.idType??>
						<#assign class = serviceBuilder.getGeneratorClass("${column.idType}")>

						<#if class == "class">
							<#assign class = column.idParam>
						</#if>
					<#else>
						<#assign class = "assigned">
					</#if>

					<generator class="${class}"

					<#if class == "sequence">
							><param name="sequence">${column.idParam}</param>
						</generator>
					<#elseif class == "foreign">
							><param name="property">${column.idParam}</param>
						</generator>
					<#elseif (class == "hilo") && (column.idParamTable??) && (column.idParamColumn??) && (column.idParamMaxLo??)>
							>
								<param name="table">${column.idParamTable}</param>
								<param name="column">${column.idParamColumn}</param>
								<param name="max_lo">${column.idParamMaxLo}</param>
						</generator>
					<#else>
						/>
					</#if>
				</id>
			</#if>
			
			<#list entity.columnList as column>
				<#if column.isSubclassDiscriminator()>
					<#-- discriminator -->
					<discriminator column="${column.DBName}" 
						<#if column.getFormula()??>
							formula="${column.formula}"
						</#if>
					/>
				</#if>
				<#if column.EJBName??>
					<#assign ejbName = true>
				<#else>
					<#assign ejbName = false>
				</#if>

				<#if column.getRelationship()?? >
					<#if column.type = "List">
						<#assign collectionType = "bag">
					<#else>
						<#assign collectionType = "set">
					</#if>
				
					<#if column.getRelationship() = "oneToOne" >
						<one-to-one name="${column.name}" class="${column.ejbClass}" 
						<#-- cascade="persist,merge,delete,save-update,evict,replicate,lock,refresh; second, special values: delete-orphan; and third,all comma-separated combinations of operation names: cascade="persist,merge,evict" or cascade="all,delete-orphan" -->
						<#if column.cascade?? >
							cascade="${column.cascade}"
						</#if>
						<#-- lazy="proxy|no-proxy|false" -->
						<#if column.lazy?? >
							<#if column.lazy = "true">
								<#assign lazyType = "no-proxy">
							<#else>
								<#assign lazyType = "${column.lazy}">
							</#if>
						
							lazy="${lazyType}"
						</#if>
						<#if column.fkConstrained >
							constrained="true"
						</#if>
						<#if column.propertyRef?? >
							property-ref="${column.propertyRef}"
						</#if>
						<#if column.fetch?? >
							fetch="${column.fetch}"
						</#if>
						/>
					</#if>
					<#if column.getRelationship() = "oneToMany" >
						<${collectionType} name="${column.name}" table="${column.DBName}" fetch="subselect"
							<#if column.orderBy?? >
								order-by="${column.orderBy}"
							</#if>
							<#-- cascade="all|none|save-update|delete|all-delete-orphan" -->
							<#if column.cascade?? >
								cascade="${column.cascade}"
							</#if>
							<#-- lazy="true|false" -->
							<#if column.lazy?? >
								lazy="${column.lazy}"
							</#if>
							<#if column.fetch?? >
								fetch="${column.fetch}"
							</#if>
							<#if column.inverse?? >
								inverse="${column.inverse}"
							</#if>
						>
							<key>
								<#list column.mappingKeys as joinColumn>
								<column name="${joinColumn}" />
								</#list>
							</key>
							<one-to-many class="${column.ejbClass}" />
						</${collectionType}>
					</#if>
					<#if column.getRelationship() = "manyToOne" >
						<many-to-one name="${column.name}" class="${column.ejbClass}"
						<#if column.noInsert>
							 insert="false"
						</#if>
						<#if column.noUpdate>
							 update="false"
						</#if>
						<#-- cascade="persist,merge,delete,save-update,evict,replicate,lock,refresh; second, special values: delete-orphan; and third,all comma-separated combinations of operation names: cascade="persist,merge,evict" or cascade="all,delete-orphan" -->
						<#if column.cascade?? >
							cascade="${column.cascade}"
						</#if>
						<#-- lazy="proxy|no-proxy|false" -->
						<#if column.lazy?? >
							<#if column.lazy = "true">
								<#assign lazyType = "proxy">
							<#else>
								<#assign lazyType = "${column.lazy}">
							</#if>
						
							lazy="${lazyType}"
							<#if (column.doIgnoreNotFound) >
							
								not-found="ignore"
							</#if>
						</#if>
						<#if column.fetch?? >
							fetch="${column.fetch}"
						</#if>
						>
							<#list column.mappingKeys as joinColumn>
							<column name="${joinColumn}" />
							</#list>
						</many-to-one>
					</#if>
					<#if column.getRelationship() = "manyToMany" >
						<${collectionType} name="${column.name}" table="${column.mappingTable}" fetch="subselect"
							<#if column.orderBy?? >
								order-by="${column.orderBy}"
							</#if>
							<#-- cascade="all|none|save-update|delete|all-delete-orphan" -->
							<#if column.cascade?? >
								cascade="${column.cascade}"
							</#if>
							<#-- lazy="true|false" -->
							<#if column.lazy?? >
								lazy="${column.lazy}"
							</#if>
							<#if column.inverse?? >
								inverse="${column.inverse}"
							</#if>
							<#if column.fetch?? >
								fetch="${column.fetch}"
							</#if>
						>
						    <key>
							<#list entity.getPKList() as pkColumn>
							<column name="${pkColumn.DBName}"/>
							</#list>
						    </key>
						    <many-to-many class="${column.ejbClass}" 
								<#if (column.doIgnoreNotFound) >
									not-found="ignore"
								</#if>
							>
							<#list column.mappingKeys as joinColumn>
							<column name="${joinColumn}" />
							</#list>
						    </many-to-many>
						</${collectionType}>					
					
					</#if>
				<#else>
				<#if !column.getRelationship()?? && !column.isPrimary() && !column.isCollection() && !ejbName>
					<#if column.version >
						<version
					<#else>
						<property
					</#if>
					
					name="${column.name}"

					<#if column.name != column.DBName>
						column="${column.DBName}"
					</#if>

					<#if column.type == "Date">
						type="org.hibernate.type.TimestampType"
					<#elseif column.type == "BigDecimal">
						type="org.hibernate.type.BigDecimalType"
					<#elseif column.type == "Blob">
						type="org.hibernate.type.BlobType"
					<#elseif column.type == "Clob">
						type="org.hibernate.type.ClobType"
					<#else>
						type="org.hibernate.type.${serviceBuilder.getPrimitiveObj("${column.type}")}Type"
					</#if>
					
					<#if column.noInsert>
						 insert="false"
					</#if>
					<#if column.noUpdate>
						 update="false"
					</#if>
					
					<#if column.unique >
						unique="true"
					</#if>

					/>
				</#if>
				</#if>
			</#list>
			
			<#-- list subclasses -->
			<#if entity.isSuperclass() >
				<#list entity.subclasses as subclass>
					<subclass name="${subclass.packagePath}.model.${subclass.name}" 
						<#if subclass.getSuperclass().discriminatorValue?? >
							discriminator-value="${subclass.getSuperclass().discriminatorValue}"
						</#if>
					>
						<#-- subclass object relationships -->
						<#list subclass.columnList as column>
							<#if column.getRelationship()?? >
								<#if column.type = "List">
									<#assign collectionType = "bag">
								<#else>
									<#assign collectionType = "set">
								</#if>
								
								<#if column.getRelationship() = "oneToOne" >
										<one-to-one name="${column.name}" class="${column.ejbClass}" 
										<#-- cascade="persist,merge,delete,save-update,evict,replicate,lock,refresh; second, special values: delete-orphan; and third,all comma-separated combinations of operation names: cascade="persist,merge,evict" or cascade="all,delete-orphan" -->
										<#if column.cascade?? >
											cascade="${column.cascade}"
										</#if>
										<#-- lazy="proxy|no-proxy|false" -->
										<#if column.lazy?? >
											<#if column.lazy = "true">
												<#assign lazyType = "no-proxy">
											<#else>
												<#assign lazyType = "${column.lazy}">
											</#if>
										
											lazy="${lazyType}"
										</#if>
										<#if column.fkConstrained >
											constrained="true"
										</#if>
										<#if column.propertyRef?? >
											property-ref="${column.propertyRef}"
										</#if>
										<#if column.fetch?? >
											fetch="${column.fetch}"
										</#if>
										/>
									</#if>
							
								<#if column.getRelationship() = "oneToMany" >
									<${collectionType} name="${column.name}" table="${column.DBName}" fetch="subselect"
										<#if column.orderBy?? >
											order-by="${column.orderBy}"
										</#if>
										<#-- cascade="all|none|save-update|delete|all-delete-orphan" -->
										<#if column.cascade?? >
											cascade="${column.cascade}"
										</#if>
										<#-- lazy="true|false" -->
										<#if column.lazy?? >
											lazy="${column.lazy}"
										</#if>
										<#if column.fetch?? >
											fetch="${column.fetch}"
										</#if>
										<#if column.inverse?? >
											inverse="${column.inverse}"
										</#if>
									>
										<key>
											<#list column.mappingKeys as joinColumn>
											<column name="${joinColumn}" />
											</#list>
										</key>
										<one-to-many class="${column.ejbClass}" />
									</${collectionType}>
								</#if>
								
								<#if column.getRelationship() = "manyToMany" >
									<${collectionType} name="${column.name}" table="${column.mappingTable}" fetch="subselect"
										<#if column.orderBy?? >
											order-by="${column.orderBy}"
										</#if>
										<#-- cascade="all|none|save-update|delete|all-delete-orphan" -->
										<#if column.cascade?? >
											cascade="${column.cascade}"
										</#if>
										<#-- lazy="true|false" -->
										<#if column.lazy?? >
											lazy="${column.lazy}"
										</#if>
										<#if column.inverse?? >
											inverse="${column.inverse}"
										</#if>
										<#if column.fetch?? >
											fetch="${column.fetch}"
										</#if>
									>
									    <key>
									    	<#list entity.getPKList() as pkColumn>
										<column name="${pkColumn.DBName}"/>
										</#list>
									    </key>
									    <many-to-many class="${column.ejbClass}"
											<#if (column.doIgnoreNotFound) >
												not-found="ignore"
											</#if>
										>
										<#list column.mappingKeys as joinColumn>
										<column name="${joinColumn}" />
										</#list>
									    </many-to-many>
									</${collectionType}>					
								
								</#if>
							</#if>
						</#list>
						
						<join table="${subclass.table}">
							<key column="${subclass.getSuperclass().joinColumn}" update="false" unique="true" />

							<#list subclass.columnList as column>
								
								<#if column.getRelationship()?? >
									<#if column.getRelationship() = "manyToOne" >
										<many-to-one name="${column.name}" class="${column.ejbClass}"
										<#if column.noInsert>
											 insert="false"
										</#if>
										<#if column.noUpdate>
											 update="false"
										</#if>
										<#-- cascade="persist,merge,delete,save-update,evict,replicate,lock,refresh; second, special values: delete-orphan; and third,all comma-separated combinations of operation names: cascade="persist,merge,evict" or cascade="all,delete-orphan" -->
										<#if column.cascade?? >
											cascade="${column.cascade}"
										</#if>
										<#-- lazy="proxy|no-proxy|false" -->
										<#if column.lazy?? >
											<#if column.lazy = "true">
												<#assign lazyType = "proxy">
											<#else>
												<#assign lazyType = "${column.lazy}">
											</#if>
										
											lazy="${lazyType}"
											<#if (column.doIgnoreNotFound) >
											
												not-found="ignore"
											</#if>
										</#if>
										<#if column.fetch?? >
											fetch="${column.fetch}"
										</#if>
										>
											<#list column.mappingKeys as joinColumn>
											<column name="${joinColumn}" />
											</#list>
										</many-to-one>
									</#if>
								<#elseif !column.isPrimary() && !column.EJBName??>
									<#if column.version >
										<version
									<#else>
										<property
									</#if>
								
									name="${column.name}"
				
									<#if column.name != column.DBName>
										column="${column.DBName}"
									</#if>
				
									<#if column.type == "Date">
										type="org.hibernate.type.TimestampType"
									<#elseif column.type == "BigDecimal">
										type="org.hibernate.type.BigDecimalType"
									<#elseif column.type == "Blob">
										type="org.hibernate.type.BlobType"
									<#elseif column.type == "Clob">
										type="org.hibernate.type.ClobType"
									<#else>
										type="org.hibernate.type.${serviceBuilder.getPrimitiveObj("${column.type}")}Type"
									</#if>
									
									<#if column.unique >
										unique="true"
									</#if>
									
									/>
								</#if>
							</#list>
						</join>
					</subclass>
				</#list>
			</#if>
		</class>
	</#if>
</#list>