package com.ssym.cws.perceptron;

import java.io.IOException;

import com.hankcs.hanlp.model.perceptron.CWSTrainer;
import com.hankcs.hanlp.model.perceptron.PerceptronSegmenter;
import com.hankcs.hanlp.model.perceptron.PerceptronTrainer;
import com.hankcs.hanlp.model.perceptron.PerceptronTrainer.Result;
import com.hankcs.hanlp.model.perceptron.model.LinearModel;
import com.ssym.util.SystemConfig;

public class PerceptronCWS {

	public static void train() {
		PerceptronTrainer trainer = new CWSTrainer();
        try {
			Result result = trainer.train(
					SystemConfig.PerceptronCWSTrain, SystemConfig.PerceptronCWSTrain,
					SystemConfig.PerceptronCWS, SystemConfig.CPRSRATIO,
					SystemConfig.MAXITER, SystemConfig.NUMTHREAD);
			
			System.out.printf("准确率F1:%.2f\n", result.getAccuracy());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void onlineLearning(PerceptronSegmenter segmenter, String sentence) {
		segmenter.learn(sentence);
	}
	
	
	public static void onlineLearnning(PerceptronSegmenter segmenter, String[] sentences) {
		for (String sentence : sentences) {
			onlineLearning(segmenter, sentence);
		}
	}
	
	
	public static void save(PerceptronSegmenter segmenter, String dstPath, double ratio) {
		LinearModel linearModel = segmenter.getModel();
		try {
			linearModel.save(dstPath, ratio);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void save(PerceptronSegmenter segmenter, String dstPath) {
		LinearModel linearModel = segmenter.getModel();
		try {
			linearModel.save(dstPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) {
		PerceptronSegmenter segmenter;
		try {
			segmenter = new PerceptronSegmenter(SystemConfig.PerceptronCWS);
			System.out.println(segmenter.segment("商品和服务"));
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
}
