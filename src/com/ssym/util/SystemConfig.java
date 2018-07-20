package com.ssym.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置信息
 * @author liuyong
 * 
 */
public class SystemConfig {
	public static int FREQ = 3;                        // 属性不少于设定值
	public static double COST = 4.0;                   // 代价系数
	public static int MAXITER = 500;                   // 最大迭代次数
	public static int NUMTHREAD = 4;                   // CPU线程个数
	public static int SHRINKSIZE = 4;                  // CPU线程个数
	public static double ETA = 0.0000001;              // CPU线程个数
	public static String ALGORITHM = "CRF_L1";         // CPU线程个数
	
	public static String PerceptronCWSTrain = null;    // 感知机CWS模型训练文件
	public static String PerceptronCWS = null;         // 感知机CWS模型存储文件
	public static String PerceptronPOSTrain = null;    // 感知机POS模型训练文件
	public static String PerceptronPOS = null;         // 感知机POS模型存储文件
	public static String PerceptronNERTrain = null;    // 感知机NER模型训练文件
	public static String PerceptronNER = null;         // 感知机NER模型存储文件
	public static String[] PerceptronNERLabel = null;  // 感知机NER模型
	public static float CPRSRATIO = 0.1f;              // 压缩比
	
	public static String CRFCWSTemplate = null;        // 条件随机场CWS模型模板文件
	public static String CRFCWSTrain = null;           // 条件随机场CWS模型训练文件
	public static String CRFCWS = null;                // 条件随机场CWS模型存储文件
	public static String CRFPOSTrain = null;           // 条件随机场POS模型训练文件
	public static String CRFPOS = null;                // 条件随机场POS模型存储文件
	public static String CRFNERTrain = null;           // 条件随机场NER模型训练文件
	public static String CRFNER = null;                // 条件随机场NER模型存储文件
	public static String[] CRFNERLabel = null;         // 条件随机场NER模型
	
	static {
		try {
			Properties p = new Properties();
			InputStream is = null;
			is = SystemConfig.class.getClassLoader().getResourceAsStream("config/config");
			p.load(is);
			
			FREQ = Integer.parseInt(p.getProperty("freq").trim());
			COST = Double.parseDouble(p.getProperty("cost").trim());
			MAXITER = Integer.parseInt(p.getProperty("maxIter").trim());
			NUMTHREAD = Integer.parseInt(p.getProperty("numThread").trim());
			ETA = Double.parseDouble(p.getProperty("eta").trim());
			SHRINKSIZE = Integer.parseInt(p.getProperty("shrink").trim());
			ALGORITHM = p.getProperty("algorithm").trim();
			
			PerceptronCWSTrain = p.getProperty("cwsTrain").trim();
			PerceptronCWS = p.getProperty("cwsFile").trim();
			
			PerceptronPOSTrain = p.getProperty("posTrain").trim();
			PerceptronPOS = p.getProperty("posFile").trim();
			
			PerceptronNERTrain = p.getProperty("nerTrain").trim();
			PerceptronNER = p.getProperty("nerFile").trim();
			PerceptronNERLabel = p.getProperty("nerLabels").trim().split(",");
			
			MAXITER = Integer.parseInt(p.getProperty("maxIter").trim());
			NUMTHREAD = Integer.parseInt(p.getProperty("numThread").trim());
			CPRSRATIO = Float.parseFloat(p.getProperty("cprsRatio").trim());
			
			CRFCWSTrain = p.getProperty("cwsCRFTrain").trim();
			CRFCWS = p.getProperty("cwsCRFFile").trim();
			
			CRFPOSTrain = p.getProperty("posCRFTrain").trim();
			CRFPOS = p.getProperty("posCRFFile").trim();
			
			CRFNERTrain = p.getProperty("nerCRFTrain").trim();
			CRFNER = p.getProperty("nerCRFFile").trim();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
