package com.augmentum.common.generator;

public class DocBuilder {

	public static void main(String[] args) throws Exception {
		String outputDir1 = System.getProperty("outputDir1");
		String outputDir2 = System.getProperty("outputDir2");

		if (outputDir1 == null && outputDir2 == null) {
			return;
		}

		VoDocBuilder voDocBuilder = new VoDocBuilder();
		voDocBuilder.setOutputDir1(outputDir1);
		voDocBuilder.setOutputDir2(outputDir2);
		voDocBuilder.build();

		ApiDocBuilder apiDocBuilder = new ApiDocBuilder();
		apiDocBuilder.setOutputDir1(outputDir1);
		apiDocBuilder.setOutputDir2(outputDir2);
		apiDocBuilder.build();
	}

}
