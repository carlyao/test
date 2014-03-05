package com.augmentum.masterchef.util;

import java.beans.PropertyEditorSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonObjectEditor extends PropertyEditorSupport {

	private static Log log = LogFactory.getLog(JsonObjectEditor.class);

	public JsonObjectEditor() {
		super();
	}

	public JsonObjectEditor(Object source) {
		super(source);
	}

	public void setAsText(String text) {
		ObjectMapper mapper = new ObjectMapper();
		//Ignore unknown properties
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		Object value = null;
		try {
			value = mapper.readValue(text, (Class) getSource());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		setValue(value);
	}
}