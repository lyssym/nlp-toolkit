package com.ssym.pos.crf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.hankcs.hanlp.model.crf.crfpp.Encoder.Algorithm;
import com.hankcs.hanlp.model.crf.CRFPOSTagger;

import com.ssym.util.SystemConfig;


public class CrfPOS {
	
	public static void train() {
        try {
        	CRFPOSTagger posTagger = new CRFPOSTagger(null);
        	Algorithm algorithm = Algorithm.fromString(SystemConfig.ALGORITHM);
        	posTagger.train(SystemConfig.CRFCWSTemplate, SystemConfig.CRFCWSTrain, SystemConfig.CRFCWS, SystemConfig.MAXITER, 
					SystemConfig.FREQ, SystemConfig.ETA, SystemConfig.COST, SystemConfig.NUMTHREAD, SystemConfig.SHRINKSIZE,
					algorithm);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void trainAccelerate() {
		try {
			CRFPOSTagger posTagger = new CRFPOSTagger(null);
			posTagger.convertCorpus(SystemConfig.CRFCWSTrain, SystemConfig.CRFCWSTrain);
			posTagger.dumpTemplate(SystemConfig.CRFCWSTemplate);
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
			CRFPOSTagger posTagger = new CRFPOSTagger(SystemConfig.CRFCWS);
			String[] words = {"商品和服务"};
			String[] wordList = posTagger.tag(words);
	        System.out.println(wordList);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
	}

}
