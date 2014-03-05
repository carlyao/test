package com.augmentum.common.generator;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.web.bind.annotation.RequestMapping;

public class ApiDocBuilder extends AbstractBaseBuilder {

	ParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

	String templateFile = "/com/augmentum/common/generator/template/api.ftl";
	String packageToFindController = "com.augmentum.masterchef.web.controller";

	Map<String, Vector<ApiObject>> apiObjectGroup = new HashMap<String, Vector<ApiObject>>();

	public static void main(String[] args) throws Exception {
		ApiDocBuilder apiDocBuilder = new ApiDocBuilder();
		apiDocBuilder.build();
	}

	public void build() throws Exception {
		initTemplate(templateFile);

		Collection<Class<?>> classes = getControllerClasses();
		for (Class<?> clazz : classes) {
			parseControllerClass(clazz);
		}

		outputDoc();
	}

	private Collection<Class<?>> getControllerClasses()
			throws ClassNotFoundException, IOException {
		List<Class<?>> ctrClasses = new ArrayList<Class<?>>();
		Enumeration<URL> urls = Thread.currentThread().getContextClassLoader()
				.getResources("");
		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();
			File file = new File(url.getFile() + "/"
					+ packageToPath(packageToFindController));
			if (file.exists()) {
				for (String filename : file.list()) {
					String baseName = FilenameUtils.getBaseName(filename);
					String className = packageToFindController + "." + baseName;
					Class<?> ctrClass = toClass(className);

					if (isMeetRequirement(ctrClass)) {
						log("Find controller: " + ctrClass.getSimpleName());
						ctrClasses.add(ctrClass);
					}
				}
			}
		}
		return ctrClasses;
	}

	private void parseControllerClass(Class<?> clazz)
			throws ClassNotFoundException {

		Vector<ApiObject> apiObjects = new Vector<ApiObject>();

		String baseReqMapping = "";
		for (Annotation annotation : clazz.getDeclaredAnnotations()) {
			if (annotation instanceof RequestMapping) {
				RequestMapping reqMapping = (RequestMapping) annotation;
				baseReqMapping += reqMapping.value()[0];
				break;
			}
		}

		for (Method method : clazz.getDeclaredMethods()) {
			ApiObject apiObject = new ApiObject();

			boolean hasRequestMapping = false;
			for (Annotation annotation : method.getDeclaredAnnotations()) {
				if (annotation instanceof RequestMapping) {
					hasRequestMapping = true;
					RequestMapping reqMapping = (RequestMapping) annotation;
					apiObject.setMethod(reqMapping.method()[0].name());
					apiObject.setRequestUrl(baseReqMapping
							+ reqMapping.value()[0]);
					break;
				}
			}
			if (!hasRequestMapping) {
				continue;
			}

			apiObject.setName(method.getName());

			List<ApiParameter> parameters = new ArrayList<ApiParameter>();
			Class<?>[] pmClasses = method.getParameterTypes();
			String[] pmNames = nameDiscoverer.getParameterNames(method);
			for (int i = 0; i < pmClasses.length; i++) {
				Class<?> pmClass = pmClasses[i];
				String pmName = pmNames[i];
				if (pmClass != HttpServletRequest.class) {
					parameters.add(new ApiParameter(pmClass, pmName));
				}
			}

			Class<?>[] typeClassArray = parseGenericType(method
					.getGenericReturnType());
			Class<?> genericTypeClass = typeClassArray[1];
			Class<?> genericTypeClass2 = typeClassArray[2];

			apiObject.setParameters(parameters);
			apiObject.setReturnObjectClass(genericTypeClass);
			if (genericTypeClass2 != null) {
				apiObject.setReturnObjectClass2(genericTypeClass2);
			}

			apiObjects.add(apiObject);
		}

		String className = clazz.getSimpleName();
		String groupName = className.substring(4,
				className.indexOf("Controller"));
		apiObjectGroup.put(groupName, apiObjects);

	}

	private void outputDoc() throws Exception {
		Map<String, Object> context = new HashMap<String, Object>();

		context.put("builderUtil", new BuilderUtil());
		context.put("apiObjectGroup", apiObjectGroup);

		StringWriter writer = new StringWriter();
		template.process(context, writer);

		String result = writer.toString();

		writeStringToFile(result, outputDir1, "/api.html");
		writeStringToFile(result, outputDir2, "/api.html");
	}

}
