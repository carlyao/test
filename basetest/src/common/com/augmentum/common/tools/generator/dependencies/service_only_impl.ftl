package ${packagePath}.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import com.kt.common.service.SpringServiceLocator;

import com.kt.common.service.AbstractSimpleServiceBaseImpl;

@Service
public class ${service.name}Impl extends AbstractSimpleServiceBaseImpl 
	implements ${service.name} {
	private static Log log = LogFactory.getLog(${service.name}Impl.class);
	
	private static ApplicationContext applicationContext;
	
	public void setApplicationContext(ApplicationContext _applicationContext){
		applicationContext = _applicationContext;
	}
	
	public static ${service.name} getService() {
		if (applicationContext != null) {
			return (${service.name})applicationContext.getBean("${packagePath}.service.${service.name}.transaction");
		}
		else {
			return (${service.name})SpringServiceLocator.getBean("${packagePath}.service.${service.name}.transaction");
		}
	}	
}