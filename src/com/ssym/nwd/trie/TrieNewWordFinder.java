package com.ssym.nwd.trie;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.ansj.dic.LearnTool;
import org.ansj.domain.Nature;
import org.ansj.domain.NewWord;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.nlpcn.commons.lang.util.IOUtil;

import com.ssym.io.FileIO;

/**
 * 基于trie树统计信息, 支持线性链模式
 * @author liuyong
 *
 */

public class TrieNewWordFinder {
	/**
	 * 从句子中发现新词
	 * @param learnTool
	 * @param sentence
	 */
	public static void extractWords(LearnTool learnTool, String sentence) {
		NlpAnalysis nlpAnalysis = new NlpAnalysis().setLearnTool(learnTool);
	    nlpAnalysis.parseStr(sentence);
	}
	
	
	/**
	 * 从大型文本中发现新词
	 * @param learnTool
	 * @param sentence
	 */
	public static void extractWords(String name, LearnTool learnTool) throws IOException {
		NlpAnalysis nlpAnalysis = new NlpAnalysis().setLearnTool(learnTool);
		BufferedReader br = FileIO.loadResource(name, 2);
		String s = null;
		while ((s = br.readLine()) != null) {
			nlpAnalysis.parseStr(s);
		}
	}
   
   
	/**
	 * 保存新词发现结果
	 */
	public static void save(LearnTool learnTool, String dstPath) {
		List<Entry<String, Double>> topTree = learnTool.getTopTree(0);
		StringBuilder sb = new StringBuilder();
		for (Entry<String, Double> entry : topTree) {
			sb.append(entry.getKey() + "\t" + entry.getValue()+"\n");
		}
		
		IOUtil.Writer(dstPath, IOUtil.UTF8, sb.toString());
	}

	
   /**
    * 加载新词发现结果
    * @param filePath
    * @throws IOException
    */
	public static void load(String filePath) throws IOException {
		LearnTool learnTool = new LearnTool();
	    HashMap<String, Double> loadMap = IOUtil.loadMap(filePath, IOUtil.UTF8, 
	    		String.class, Double.class);
	    for (Entry<String, Double> entry : loadMap.entrySet()) {
	    	learnTool.addTerm(new NewWord(entry.getKey(), Nature.NW, 
	    			entry.getValue()));
		    learnTool.active(entry.getKey());  // 激活新词, 即新词变旧词
	    }
	    System.out.println(learnTool.getTopTree(10));
	}


	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		//构建一个新词学习的工具类。这个对象。保存了所有分词中出现的新词。出现次数越多。相对权重越大。
		LearnTool learnTool = new LearnTool();
		NlpAnalysis nlpAnalysis = new NlpAnalysis().setLearnTool(learnTool);
  		
  		//进行词语分词。也就是nlp方式分词，这里可以分多篇文章
		nlpAnalysis.parseStr("说过，社交软件也是打着沟通的平台，让无数寂寞男女有了肉体与精神的寄托。");
		nlpAnalysis.parseStr("其实可以打着这个需求点去运作的互联网公司不应只是社交类软件与可穿戴设备，还有携程网，去哪儿网等等，订房订酒店多好的寓意");
		nlpAnalysis.parseStr("张艺谋的卡宴，马明哲的戏");
  
  		//取得学习到的topn新词,返回前10个。这里如果设置为0则返回全部
  		System.out.println(learnTool.getTopTree(10));
  		
  		//只取得词性为Nature.NR的新词
  		System.out.println(learnTool.getTopTree(10,Nature.NR));
	}

}
