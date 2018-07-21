package com.ssym.nwd.inf;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.mining.word.WordInfo;
import com.hankcs.hanlp.mining.word.NewWordDiscover;

/**
 * 根据信息熵和互信息实现新词发现
 * @author liuyong
 */

public class NewWordFinder {
	/**
	 * 从大型文件中获取新词发现
	 * @param text : 大型文件
	 * @param size
	 * @param isOnlyNew : 是否只提取词典中没有的词语
	 * @return
	 */
	public static List<String> extractWords(String text, int size, boolean isOnlyNew) {
		List<String> ret = new ArrayList<String>();
		List<WordInfo> wordInfos = HanLP.extractWords(text, size, isOnlyNew);
		for (WordInfo wi : wordInfos) {
			ret.add(wi.text);
		}
		
		return ret;
	}


	/**
	 * 从文本流中获取新词发现
	 * @param br : 文本流
	 * @param size
	 * @param isOnlyNew : 是否只提取词典中没有的词语
	 * @return
	 */
	public static List<String> extractWords(BufferedReader br, int size, boolean isOnlyNew) throws IOException {
		List<String> ret = new ArrayList<String>();
		List<WordInfo> wordInfos = HanLP.extractWords(br, size, isOnlyNew);
		for (WordInfo wi : wordInfos) {
			ret.add(wi.text);
		}
		
		return ret;
	}
	
	
	/**
	 * 调整模型参数以便进行自定义
	 * @param max_word_len : 默认为4, 该值越大，运算量越大，结果中出现短语的数量也会越多
	 * @param min_freq : 控制结果中词语的最低频率，低于该频率的将会被过滤掉，减少一些运算量
	 * @param min_entropy : 控制结果中词语的最低信息熵的值，一般取0.5左右。该值越大，越短的词语就越容易被提取出来
	 * @param min_aggregation : 控制结果中词语的最低互信息值，一般取50到200.该值越大，越长的词语就越容易被提取出来，有时候会出现一些短语
	 * @param filter : 设为true的时候将使用内部词库过滤掉"旧词"
	 * @param text : 大文本
	 * @param size : 新词数据规模
	 * @return
	 */
	public static List<String> extractWords(int max_word_len, float min_freq, float min_entropy, 
			float min_aggregation, boolean filter, String text, int size) {
		NewWordDiscover newWordDiscover = new NewWordDiscover(max_word_len, min_freq, min_entropy,
				min_aggregation, filter);
		List<WordInfo> wordInfos = newWordDiscover.discover(text, size);
		List<String> ret = new ArrayList<String>();
		for (WordInfo wi : wordInfos) {
			ret.add(wi.text);
		}
		
		return ret;
	}
	
	
	/**
	 * 调整模型参数以便进行自定义
	 * @param max_word_len : 默认为4, 该值越大，运算量越大，结果中出现短语的数量也会越多
	 * @param min_freq : 控制结果中词语的最低频率，低于该频率的将会被过滤掉，减少一些运算量
	 * @param min_entropy : 控制结果中词语的最低信息熵的值，一般取0.5左右。该值越大，越短的词语就越容易被提取出来
	 * @param min_aggregation : 控制结果中词语的最低互信息值，一般取50到200.该值越大，越长的词语就越容易被提取出来，有时候会出现一些短语
	 * @param filter : 设为true的时候将使用内部词库过滤掉"旧词"
	 * @param text : 大文本
	 * @param size : 新词数据规模
	 * @return
	 */
	public static List<String> extractWords(int max_word_len, float min_freq, float min_entropy, 
			float min_aggregation, boolean filter, BufferedReader br, int size, boolean isOnlyNew) 
					throws IOException {
		NewWordDiscover newWordDiscover = new NewWordDiscover(max_word_len, min_freq, min_entropy,
				min_aggregation, filter);
		List<WordInfo> wordInfos = newWordDiscover.discover(br, size);
		List<String> ret = new ArrayList<String>();
		for (WordInfo wi : wordInfos) {
			ret.add(wi.text);
		}
		
		return ret;
	}

}
