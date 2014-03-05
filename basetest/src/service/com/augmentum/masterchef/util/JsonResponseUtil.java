package com.augmentum.masterchef.util;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

import com.augmentum.masterchef.constant.ICodeConstants;
import com.augmentum.masterchef.exception.RemoteApiCallException;

/**
 * This class is used for convert json
 * 
 * @author carl.yao 2013-5-16 下午04:19:06
 */
public class JsonResponseUtil {

	private static ObjectMapper objectMapper = new ObjectMapper();
	private static ObjectMapper objectMapperDateFormatLong = new ObjectMapper();
	static {
		objectMapper
				.configure(
						DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
						false);
		objectMapperDateFormatLong
				.configure(
						DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
						false);
	}

	// public static <U> List<U> convertJsonResponseObjectCollection(File file,
	// Class<U> type) throws IOException {
	// if (!file.exists()) {
	// return new ArrayList<U>();
	// }
	// String value = FileUtils.readFileToString(file, "UTF-8");
	// if (value != null && !value.equals("")) {
	// return convertJsonResponseObjectCollection(value, type);
	// } else {
	// return new ArrayList<U>();
	// }
	// }

	public static <U> List<U> convertJsonResponseObjectCollection(String value,
			Class<U> type) throws IOException {
		TypeFactory t = TypeFactory.defaultInstance();
		List<U> responseCollection = new ArrayList<U>();
		try {
			if (value == null || value.equals("")) {
				return new ArrayList<U>();
			}
			responseCollection = objectMapper.readValue(value, t
					.constructCollectionType(ArrayList.class, type));
		} catch (IOException ex) {
			throw ex;
		}
		return responseCollection;
	}

	public static String getRoot(String json, String value) throws Exception {
		String jsonResponse = objectMapper.readTree(value).get(json).toString();
		return jsonResponse;
	}

	public static <U> U convertJsonResponseObject(String value, Class<U> type)
			throws IOException, RemoteApiCallException {
		U responseObj = null;
		try {
			responseObj = objectMapper.readValue(value, type);
		} catch (IOException ex) {
		}
		return responseObj;
	}
	
	public static String toString(Object value) throws IOException {

		DateFormat dateFormat = new SimpleDateFormat(
				ICodeConstants.DATETIME_FORMAT);

		dateFormat.setTimeZone(TimeZone.getTimeZone("PST"));

		objectMapper.getSerializationConfig().setDateFormat(dateFormat);

		return objectMapper.writeValueAsString(value);
	}

	
	public static String readJsonFromFile(String realPath) throws IOException {
		File file = new File(realPath);
		if (!file.exists()) {
			return null;
		}
		String value = FileUtils.readFileToString(file, "UTF-8");
		return value;
	}

	public static ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	public static void setObjectMapper(ObjectMapper objectMapper) {
		JsonResponseUtil.objectMapper = objectMapper;
	}

	public static ObjectMapper getObjectMapperDateFormatLong() {
		return objectMapperDateFormatLong;
	}

	public static void setObjectMapperDateFormatLong(
			ObjectMapper objectMapperDateFormatLong) {
		JsonResponseUtil.objectMapperDateFormatLong = objectMapperDateFormatLong;
	}
	
}
