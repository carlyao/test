<bean id="${packagePath}.service.${entity.name}Service" class="${packagePath}.service.${entity.name}ServiceImpl" lazy-init="true" >
	<property name="dao">
    <ref bean="${packagePath}.dao.${entity.name}DAO" />
	</property>
</bean>

