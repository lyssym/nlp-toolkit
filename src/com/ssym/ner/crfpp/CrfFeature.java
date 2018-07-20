package com.ssym.ner.crfpp;

import java.util.ArrayList;
import java.util.List;


public class CrfFeature {
	private String sentence = null;              // 文本信息
	private List<List<String>> elements = null;  // 正则或者关键词等特征
	private List<String> labels = null;          // 目标标签信息
	
	public CrfFeature(String sentence, List<List<String>> elements, List<String> labels) {
		this.sentence = sentence;
		this.elements = elements;
		this.labels = labels;
	}
	
	/**
	 * 根据参数将特征信息根据位置坐标进行整理
	 * @return
	 */
	public List<String> features() {
		if (sentence.length() != elements.get(0).size() || elements.get(0).size() != labels.size()) { // 大小不一致
			return null;
		}
		
		List<String> ret = new ArrayList<String>();
		int size = sentence.length();
		int len = elements.size();
		for (int i = 0; i < size; i++) {
			String s = sentence.charAt(i) + " ";
			for (int j = 0; j < len; j++) {
				s += elements.get(j).get(i) + " ";
			}
			s += labels.get(i);
			
			ret.add(s);
		}
		return ret;
	}

}
