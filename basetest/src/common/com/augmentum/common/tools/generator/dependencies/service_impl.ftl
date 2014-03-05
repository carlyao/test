package ${packagePath}.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service
public class ${entity.name}ServiceImpl extends ${entity.name}ServiceBaseImpl 
	implements ${entity.name}Service {
	private static Log log = LogFactory.getLog(${entity.name}ServiceImpl.class);
}