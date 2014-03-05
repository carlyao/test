package com.augmentum.common.generator;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.io.FilenameUtils;

public class VoDocBuilder extends AbstractBaseBuilder {

	String templateFile = "/com/augmentum/common/generator/template/vo.ftl";
	String packageToFindVo = "com.augmentum.masterchef.vo";

	Vector<ApiObject> apiObjects = new Vector<ApiObject>();
	Vector<ValueObject> valueObjects = new Vector<ValueObject>();
	Vector<Class<?>> parsedClasses = new Vector<Class<?>>();

	public static void main(String[] args) throws Exception {
		VoDocBuilder voDocBuilder = new VoDocBuilder();
		voDocBuilder.build();
	}

	public void build() throws Exception {
		initTemplate(templateFile);

		Collection<Class<?>> classes = getVoClasses();
		for (Class<?> clazz : classes) {
			parseVoClass(clazz);
		}

		sort(valueObjects);

		outputDoc();
	}

	private void sort(Vector<ValueObject> valueObjects2) {
		Collections.sort(valueObjects, new Comparator<ValueObject>() {
			@Override
			public int compare(ValueObject o1, ValueObject o2) {
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});
	}

	private Collection<Class<?>> getVoClasses() throws ClassNotFoundException,
			IOException {
		List<Class<?>> voClasses = new ArrayList<Class<?>>();
		Enumeration<URL> urls = Thread.currentThread().getContextClassLoader()
				.getResources("");
		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();
			File file = new File(url.getFile() + "/"
					+ packageToPath(packageToFindVo));
			if (file.exists()) {
				for (String filename : file.list()) {
					String baseName = FilenameUtils.getBaseName(filename);
					String className = packageToFindVo + "." + baseName;

					Class<?> voClass = toClass(className);

					if (isMeetRequirement(voClass)) {
						voClasses.add(voClass);
					}
				}
			}
			
		}
		return voClasses;
	}

	private void parseVoClass(Class<?> clazz) throws ClassNotFoundException {
		if (clazz == null) {
			return;
		}
		if (clazz.isPrimitive()) {
			return;
		}
		String className = clazz.getName();
		if (className.startsWith("java.")) {
			return;
		}
		if (className.startsWith("javax.")) {
			return;
		}
		if (className.startsWith("sun.")) {
			return;
		}
		if (hasParsed(clazz)) {
			return;
		}

		log("Find VO: " + clazz.getSimpleName());

		List<VoProperty> properties = new ArrayList<VoProperty>();
		Class<?> _clazz = clazz;
		if (className.endsWith("VO") || className.endsWith("Vo")) {
			// by field
			List<Field> fields = new ArrayList<Field>();
			do {
				for (Field field : _clazz.getDeclaredFields()) {
					fields.add(field);
				}
				_clazz = _clazz.getSuperclass();
			} while (_clazz != Object.class);
			for (Field field : fields) {
				String propName = field.getName();
				Class<?>[] typeClassArray = parseGenericType(field
						.getGenericType());
				Class<?> typeClass = typeClassArray[0];
				Class<?> genericTypeClass = typeClassArray[1];
				properties.add(new VoProperty(typeClass, genericTypeClass,
						propName));
				parseVoClass(typeClass);
				parseVoClass(genericTypeClass);
			}
		} else {
			// by getter() method
			List<Method> methods = new ArrayList<Method>();
			do {
				for (Method method : _clazz.getDeclaredMethods()) {
					methods.add(method);
				}
				_clazz = _clazz.getSuperclass();
			} while (_clazz != Object.class);
			for (Method method : methods) {
				if (isExpectedGetter(method)) {
					String propName = getPropertyName(method);
					Class<?>[] typeClassArray = parseGenericType(method
							.getGenericReturnType());
					Class<?> typeClass = typeClassArray[0];
					Class<?> genericTypeClass = typeClassArray[1];
					properties.add(new VoProperty(typeClass, genericTypeClass,
							propName));
					parseVoClass(typeClass);
					parseVoClass(genericTypeClass);
				}
			}
		}

		ValueObject valueObject = new ValueObject();
		valueObject.setName(clazz.getSimpleName());
		valueObject.setProperties(properties);
		valueObjects.add(valueObject);
		parsedClasses.add(clazz);
	}

	private boolean hasParsed(Class<?> clazz) {
		return parsedClasses.contains(clazz);
	}

	private String getPropertyName(Method method) {
		String methodName = method.getName();
		String propertyName = methodName.substring(3, 4).toLowerCase();
		propertyName += methodName.substring(4);
		return propertyName;
	}

	private boolean isExpectedGetter(Method method) {
		String methodName = method.getName();
		if (methodName.length() <= 3) {
			return false;
		}
		if (!methodName.startsWith("get")) {
			return false;
		}
		if (methodName.contains("CacheKey")) {
			return false;
		}
		if (methodName.contains("PrimaryKey")) {
			return false;
		}
		if (method.getParameterTypes().length > 0) {
			return false;
		}
		return true;
	}

	private void outputDoc() throws Exception {
		Map<String, Object> context = new HashMap<String, Object>();

		context.put("builderUtil", new BuilderUtil());
		context.put("valueObjects", valueObjects);

		StringWriter writer = new StringWriter();
		template.process(context, writer);

		String result = writer.toString();

		writeStringToFile(result, outputDir1, "/vo.html");
		writeStringToFile(result, outputDir2, "/vo.html");
	}

}
