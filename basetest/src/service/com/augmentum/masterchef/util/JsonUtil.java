package com.augmentum.masterchef.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import com.augmentum.masterchef.constant.GameConstants;
import com.augmentum.masterchef.exception.RemoteApiCallException;

public class JsonUtil {

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
	private static final String JSON_RESPONSE_FIELD = "jsonResponse";
	private static final String STATUS_CODE_FIELD = "statusCode";
	private static final String RESPONSE_FIELD = "response";
	private static final String RESPONSE_INFO = "additionalInfo";

	/**
	 * Deserialize a JSON string into a Java Object
	 * 
	 * @param value
	 *            The JSON string to be deserialized
	 * @param type
	 *            The Java Object class type
	 * @return
	 * @throws IOException
	 */
	public static <U> U fromString(String value, Class<U> type)
			throws IOException {
		return objectMapper.readValue(value, type);
	}

	/**
	 * Extract the entity from a JsonResponse string. If status code is not 0,
	 * throw Exception
	 * 
	 * @param <U>
	 * @param value
	 *            The JsonResponse string to be extracted
	 * @param type
	 *            The entity type to be extracted
	 * @return
	 * @throws IOException
	 * @throws RemoteApiCallException
	 */
	public static <U> U convertJsonResponseObject(String value, Class<U> type)
			throws IOException, RemoteApiCallException {
		JsonNode jsonResponse = null;
		JsonNode rootResponse = null;
		JsonNode responseInfo = null;
		U responseObj = null;
		try {
			jsonResponse = (JsonNode) objectMapper.readTree(value).get(
					JSON_RESPONSE_FIELD);
			responseInfo = jsonResponse.get(RESPONSE_INFO);
			rootResponse = jsonResponse.get(RESPONSE_FIELD);
			responseObj = objectMapper.readValue(rootResponse.toString(), type);
		} catch (IOException ex) {
			if (rootResponse != null) {
				throw new RemoteApiCallException(responseInfo.getValueAsText(),
						jsonResponse.get(STATUS_CODE_FIELD).getIntValue(),
						responseInfo.toString());
			} else if (jsonResponse != null) {
				throw new RemoteApiCallException(jsonResponse.get(
						RESPONSE_FIELD).getValueAsText(), jsonResponse.get(
						STATUS_CODE_FIELD).getIntValue(), jsonResponse);
			} else {
				throw ex;
			}
		}
		if (jsonResponse.get(STATUS_CODE_FIELD).getIntValue() != 0) {
			throw new RemoteApiCallException(responseInfo.getValueAsText(),
					jsonResponse.get(STATUS_CODE_FIELD).getIntValue(),
					responseObj);
		}
		return responseObj;
	}

	/**
	 * Extract the entity list from a JsonResponse string. If status code is not
	 * 0, throw Exception
	 * 
	 * @param <U>
	 * @param value
	 *            The JsonResponse string to be extracted
	 * @param type
	 *            The entity type to be extracted
	 * @return The entity list
	 * @throws IOException
	 * @throws RemoteApiCallException
	 */
	public static <U> List<U> convertJsonResponseObjectCollection(String value,
			Class<U> type) throws IOException, RemoteApiCallException {
		JsonNode jsonResponse = null;
		JsonNode rootResponse = null;
		JsonNode responseInfo = null;
		List<U> responseCollection = new ArrayList<U>();
		try {
			jsonResponse = (JsonNode) objectMapper.readTree(value).get(
					JSON_RESPONSE_FIELD);
			responseInfo = jsonResponse.get(RESPONSE_INFO);
			rootResponse = jsonResponse.get(RESPONSE_FIELD);
			Iterator<JsonNode> iterator = rootResponse.getElements();
			while (iterator.hasNext()) {
				String content = iterator.next().toString();
				responseCollection.add(objectMapper.readValue(content, type));
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			
			if (jsonResponse != null) {
				if (responseInfo != null) {
					throw new RemoteApiCallException(responseInfo
							.getValueAsText(), jsonResponse.get(
							STATUS_CODE_FIELD).getIntValue(), jsonResponse);

				} else {
					throw new RemoteApiCallException(jsonResponse.get(
							RESPONSE_FIELD).getValueAsText(), jsonResponse.get(
							STATUS_CODE_FIELD).getIntValue(), jsonResponse);
				}
			} else {
				throw ex;
			}
		}
		if (jsonResponse.get(STATUS_CODE_FIELD).getIntValue() != 0) {
			throw new RemoteApiCallException(responseInfo.getValueAsText(),
					jsonResponse.get(STATUS_CODE_FIELD).getIntValue(),
					responseCollection);
		}
		return responseCollection;
	}

	/**
	 * Serialize an entity into JSON string
	 * 
	 * @param value
	 * @return
	 * @throws IOException
	 */
	public static String toString(Object value) throws IOException {

		DateFormat dateFormat = new SimpleDateFormat(
				GameConstants.DATETIME_FORMAT);

		dateFormat.setTimeZone(TimeZone.getTimeZone("PST"));

		objectMapper.getSerializationConfig().setDateFormat(dateFormat);

		return objectMapper.writeValueAsString(value);
	}

	public static String toString(Object value, boolean isDateFormat)
			throws IOException {
		if (isDateFormat) {
			return toString(value);
		} else {
			return objectMapperDateFormatLong.writeValueAsString(value);
		}
	}

	public static Integer getJsonResponseStatusCode(String value) {

		JsonNode jsonResponse = null;
		Integer result = -1;
		try {
			jsonResponse = (JsonNode) objectMapper.readTree(value).get(
					JSON_RESPONSE_FIELD);
		} catch (JsonProcessingException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		if (jsonResponse != null) {
			result = jsonResponse.get(STATUS_CODE_FIELD).getIntValue();
		}

		return result;

	}

}
