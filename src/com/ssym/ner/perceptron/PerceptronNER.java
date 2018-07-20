package com.ssym.ner.perceptron;

import com.hankcs.hanlp.model.perceptron.NERTrainer;
import com.hankcs.hanlp.model.perceptron.PerceptronTrainer;
import com.hankcs.hanlp.model.perceptron.model.LinearModel;
import com.hankcs.hanlp.model.perceptron.PerceptronNERecognizer;
import com.hankcs.hanlp.model.perceptron.tagset.NERTagSet;
import com.hankcs.hanlp.model.perceptron.tagset.TagSet;

import java.io.IOException;
import java.util.Arrays;

import com.ssym.util.SystemConfig;

public class PerceptronNER {
	
	public static void train() {
		PerceptronTrainer trainer = new NERTrainer() {
            @Override
            protected TagSet createTagSet() {
                NERTagSet tagSet = new NERTagSet();
                for (String label : SystemConfig.PerceptronNERLabel) {
                	tagSet.nerLabels.add(label);
                }
                
                return tagSet;
            }
        };
        
        try {
			trainer.train(SystemConfig.PerceptronNERTrain, SystemConfig.PerceptronNERTrain,
					SystemConfig.PerceptronNER, SystemConfig.CPRSRATIO,
					SystemConfig.MAXITER, SystemConfig.NUMTHREAD);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void onlineLearning(PerceptronNERecognizer recognizer, String nerSentence) {
		recognizer.learn(nerSentence);
	}
	
	
	public static void save(PerceptronNERecognizer recognizer, String dstPath, double ratio) {
		LinearModel linearModel = recognizer.getModel();
		try {
			linearModel.save(dstPath, ratio);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		PerceptronNERecognizer recognizer;
		try {
			recognizer = new PerceptronNERecognizer(SystemConfig.PerceptronNER);
			System.out.println(Arrays.toString(recognizer.recognize("吴忠市 乳制品 公司 谭利华 来到 布达拉宫 广场".split(" "), "ns n n nr p ns n".split(" "))));
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	}

}
