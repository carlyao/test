package com.augmentum.common.generator;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.io.FileUtils;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public abstract class AbstractBaseBuilder {

	protected String outputDir1;
	protected String outputDir2;

	protected Template template;

	protected void log(String text) {
		System.out.println(text);
	}

	protected void log(String format, Object... args) {
		System.out.printf(format, args);
	}

	protected void initTemplate(String templateFile) throws Exception {
		Configuration cfg = new Configuration();
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		cfg.setTemplateLoader(new ClassTemplateLoader(getClass(), "/"));
		template = cfg.getTemplate(templateFile);
	}

	protected Class<?> toClass(String className) throws ClassNotFoundException {
		if ("int".equals(className)) {
			return int.class;
		} else if ("long".equals(className)) {
			return long.class;
		} else if ("boolean".equals(className)) {
			return boolean.class;
		} else {
			return Class.forName(className);
		}
	}

	protected Class<?>[] parseGenericType(Type genericType) {
		Class<?> raw, actual, actual2;
		if (!(genericType instanceof ParameterizedType)) {
			System.out.println((Class<?>) genericType);
			raw = (Class<?>) genericType;
			actual = null;
			actual2 = null;
		} else {
			ParameterizedType pt = (ParameterizedType) genericType;
			raw = (Class<?>) pt.getRawType();
			try {
				actual = (Class<?>) pt.getActualTypeArguments()[0];
				actual2 = null;
			} catch (Exception e) {
				ParameterizedType pt2 = (ParameterizedType) pt
						.getActualTypeArguments()[0];
				actual = (Class<?>) pt2.getRawType();
				actual2 = (Class<?>) pt2.getActualTypeArguments()[0];
			}
		}
		return new Class[] { raw, actual, actual2 };
	}

	protected String packageToPath(String packageName) {
		return packageName.replaceAll("\\.", "/");
	}

	protected boolean isMeetRequirement(Class<?> voClass) {
		if (Modifier.isAbstract(voClass.getModifiers())) {
			return false;
		}
		if (Modifier.isInterface(voClass.getModifiers())) {
			return false;
		}
		return true;
	}

	protected void writeStringToFile(String result, String outputDir,
			String filename) throws IOException {
		if (outputDir == null) {
			return;
		}
		File file = new File(outputDir + filename);
		FileUtils.writeStringToFile(file, result);
		log("Write file: " + file);
	}

	public String getOutputDir1() {
		return outputDir1;
	}

	public void setOutputDir1(String outputDir1) {
		this.outputDir1 = outputDir1;
	}

	public String getOutputDir2() {
		return outputDir2;
	}

	public void setOutputDir2(String outputDir2) {
		this.outputDir2 = outputDir2;
	}

}
