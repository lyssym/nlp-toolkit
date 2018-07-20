package com.ssym.pos.perceptron;

import java.io.IOException;
import java.util.Arrays;

import com.hankcs.hanlp.model.perceptron.POSTrainer;
import com.hankcs.hanlp.model.perceptron.PerceptronPOSTagger;
import com.hankcs.hanlp.model.perceptron.PerceptronTrainer;
import com.hankcs.hanlp.model.perceptron.PerceptronTrainer.Result;
import com.hankcs.hanlp.model.perceptron.model.LinearModel;
import com.ssym.util.SystemConfig;

public class PerceptronPOS {
	public static void train() {
		PerceptronTrainer trainer = new POSTrainer();
		 try {
				Result result = trainer.train(
						SystemConfig.PerceptronPOSTrain, SystemConfig.PerceptronPOSTrain,
						SystemConfig.PerceptronPOS, SystemConfig.CPRSRATIO,
						SystemConfig.MAXITER, SystemConfig.NUMTHREAD);
				
				System.out.printf("准确率F1:%.2f\n", result.getAccuracy());
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public static void onlineLearning(PerceptronPOSTagger tagger, String tagSentence) {
		tagger.learn(tagSentence);
	}
	
	
	public static void onlineLearning(PerceptronPOSTagger tagger, String[] tagSentences) {
		for (String tagSentence : tagSentences) {
			onlineLearning(tagger, tagSentence);
		}
	}
	
	
	public static void save(PerceptronPOSTagger tagger, String dstPath, double ratio) {
		LinearModel linearModel = tagger.getModel();
		try {
			linearModel.save(dstPath, ratio);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void save(PerceptronPOSTagger tagger, String dstPath) {
		LinearModel linearModel = tagger.getModel();
		try {
			linearModel.save(dstPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		PerceptronPOSTagger tagger;
		try {
			tagger = new PerceptronPOSTagger(SystemConfig.PerceptronPOS);
			System.out.println(Arrays.toString(tagger.tag("中国 交响乐团 谭利华 在 布达拉宫 广场 演出".split(" "))));
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	}
}
