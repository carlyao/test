package ${packagePath}.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class ${entity.name}DAOImpl extends ${entity.name}DAOBaseImpl 
	implements ${entity.name}DAO {
	private static Log log = LogFactory.getLog(${entity.name}DAOImpl.class);
}