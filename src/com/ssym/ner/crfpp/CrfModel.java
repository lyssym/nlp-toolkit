package com.ssym.ner.crfpp;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ssym.util.SystemConfig;

public class CrfModel {
	private static Logger log = LoggerFactory.getLogger(CrfModel.class);
	public static int F = SystemConfig.FREQ;         // 属性不少于设定值
	public static double C = SystemConfig.COST;      // 代价系数
	public static int M = SystemConfig.MAXITER;      // 最大迭代次数
	public static int P = SystemConfig.NUMTHREAD;    // CPU线程个数
	
	/**
	 * 根据最大迭代次数训练CRF模型
	 * @param templateName
	 * @param fileName
	 * @param targetName
	 */
	public static void trainWithMaxIter(String templateName, String fileName, String targetName) {
		String cmd = "crf_learn -f " + F + " -c " + C + " -m " + M + " -p " + P +
				templateName + " " + fileName + " " + targetName + " -C";
		try {
			train(cmd, templateName, fileName, targetName);
		} catch(Exception e) {
			log.error("train with max iter failed");
		}
	}
	
	
	/**
	 * 未设置最大迭代次数训练CRF模型
	 * @param templateName
	 * @param fileName
	 * @param targetName
	 */
	public static void train(String templateName, String fileName, String targetName) {
		String cmd = "crf_learn -f " + F + " -c " + C + " -m " + M + " -p " + P +
				templateName + " " + fileName + " " + targetName + " -C";
		try {
			train(cmd, templateName, fileName, targetName);
		} catch(Exception e) {
			log.error("train with crf failed");
		}
	}
	

	/**
	 * 根据命令行训练模型
	 * @param cmd
	 * @param templateName
	 * @param fileName
	 * @param targetName
	 * @throws Exception
	 */
	public static void train(String cmd, String templateName, String fileName, String targetName) throws Exception {
		Process process = Runtime.getRuntime().exec(cmd);
		BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));   
	    String line = null;
	    while ((line = br.readLine()) != null) {  
	    	System.out.println(line);  
	    }
	    br.close();
	    process.waitFor();
	    process.destroy();
	}


	/**
	 * 测试模型
	 * @param modelName
	 * @param testName
	 * @param resultName
	 */
	public static void test(String modelName, String testName, String resultName) {
		String cmd = "crf_test -m " + modelName + " " + testName + " >> " + resultName;
		try {
			Process process = Runtime.getRuntime().exec(cmd);
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));   
		    String line = null;
		    while ((line = br.readLine()) != null) {  
		    	System.out.println(line);  
		    }
		    br.close();
		    process.waitFor();
		    process.destroy();
		} catch(Exception e) {
			log.error("test crf model failed");
		}
	}
	
	
	public static void predict(String modelName, CrfFeature feature) {
		
	}
}
