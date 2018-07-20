package com.ssym.ner.crf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.hankcs.hanlp.model.crf.crfpp.Encoder.Algorithm;
import com.hankcs.hanlp.model.crf.CRFNERecognizer;

import com.ssym.util.SystemConfig;


public class CrfNER {
	
	public static void train() {
        try {
        	CRFNERecognizer recognizer = new CRFNERecognizer(null);
        	Algorithm algorithm = Algorithm.fromString(SystemConfig.ALGORITHM);
        	recognizer.train(SystemConfig.CRFCWSTemplate, SystemConfig.CRFCWSTrain, SystemConfig.CRFCWS, SystemConfig.MAXITER, 
					SystemConfig.FREQ, SystemConfig.ETA, SystemConfig.COST, SystemConfig.NUMTHREAD, SystemConfig.SHRINKSIZE,
					algorithm);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void trainAccelerate() {
		try {
			CRFNERecognizer recognizer = new CRFNERecognizer(null);
			recognizer.convertCorpus(SystemConfig.CRFCWSTrain, SystemConfig.CRFCWSTrain);
			recognizer.dumpTemplate(SystemConfig.CRFCWSTemplate);
			String cmd = "crf_learn -f " + SystemConfig.FREQ 
					+ " -c " + SystemConfig.COST 
					+ " -p " + SystemConfig.NUMTHREAD
					+ " -m " + SystemConfig.MAXITER + " "
					+ SystemConfig.CRFCWSTemplate + " " 
					+ SystemConfig.CRFCWSTrain + " " 
					+ SystemConfig.CRFCWS + " -t";
			train(cmd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void train(String cmd) throws Exception {
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
 
		
	public static void main() {
		try {
			CRFNERecognizer nerRecognizer = new CRFNERecognizer(SystemConfig.CRFCWS);
			String[] wordArray = {"商品和服务"};
			String[] posArray = {"商品和服务"};
			String[] wordList = nerRecognizer.recognize(wordArray, posArray);
	        System.out.println(wordList);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
	}

}

