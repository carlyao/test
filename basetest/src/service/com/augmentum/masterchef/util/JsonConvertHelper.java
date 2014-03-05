package com.augmentum.masterchef.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

 
public abstract class JsonConvertHelper {

	private static Logger logger = LoggerFactory
			.getLogger(JsonConvertHelper.class);

	public static final <T> T convertJsonResponseObject(String jsonString,
			Class<T> clazz) throws Exception {
		try {
			return JsonUtil.convertJsonResponseObject(jsonString, clazz);
		} catch (Exception e) {
			logError(jsonString, e);
			throw e;
		}
	}

	public static final <T> List<T> convertJsonResponseObjectCollection(
			String jsonString, Class<T> clazz) throws Exception {
		try {
			return JsonUtil.convertJsonResponseObjectCollection(jsonString,
					clazz);
		} catch (Exception e) {
			logError(jsonString, e);
			throw e;
		}
	}

	private static void logError(String jsonString, Exception e) {
		logger.error("Can't parse JsonResponse:[" + jsonString + "]", e);
	}

	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static <T> List<T> fromStringAsList(String str, Class<T> clazz)
			throws IOException {
		List<T> result = new ArrayList<T>();
		JsonNode jn = objectMapper.readTree(str);
		Iterator<JsonNode> iterator = jn.getElements();
		while (iterator.hasNext()) {
			String content = iterator.next().toString();
			result.add(objectMapper.readValue(content, clazz));
		}
		return result;
	}

}
