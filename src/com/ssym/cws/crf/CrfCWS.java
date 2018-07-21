package com.ssym.cws.crf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.hankcs.hanlp.model.crf.crfpp.Encoder.Algorithm;
import com.hankcs.hanlp.model.crf.CRFSegmenter;

import com.ssym.util.SystemConfig;


public class CrfCWS {
	
	public static void train() {
        try {
        	CRFSegmenter segmenter = new CRFSegmenter(null);
        	Algorithm algorithm = Algorithm.fromString(SystemConfig.ALGORITHM);
			segmenter.train(SystemConfig.CRFCWSTemplate, SystemConfig.CRFCWSTrain, SystemConfig.CRFCWS, SystemConfig.MAXITER, 
					SystemConfig.FREQ, SystemConfig.ETA, SystemConfig.COST, SystemConfig.NUMTHREAD, SystemConfig.SHRINKSIZE,
					algorithm);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void trainAccelerate() {
		try {
			CRFSegmenter segmenter = new CRFSegmenter(null);
			segmenter.convertCorpus(SystemConfig.CRFCWSTrain, SystemConfig.CRFCWSTrain);
			segmenter.dumpTemplate(SystemConfig.CRFCWSTemplate);
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
 
		
	public static void main(String[] args) {
		try {
			CRFSegmenter segmenter = new CRFSegmenter(SystemConfig.CRFCWS);
			List<String> wordList = segmenter.segment("商品和服务");
	        System.out.println(wordList);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
	}

}
