package com.ssym.cws.util;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.collection.AhoCorasick.AhoCorasickDoubleArrayTrie;
import com.hankcs.hanlp.dictionary.CoreDictionary;
import com.hankcs.hanlp.dictionary.CustomDictionary;

public class CustomDict {
	/**
	 * 动态增加用户词典, 默认词性为名词
	 * @param word
	 */
	public static void insert(String word) {
		CustomDictionary.add(word);
	}
	
	
	/**
	 * 动态添加用户词典
	 * @param word
	 * @param nature
	 */
	public static void insert(String word, String nature) {
		CustomDictionary.add(word, nature);
	}
	
	
	/**
	 * 删除用户自定义词典
	 * @param word
	 */
	public static void remove(String word) {
		CustomDictionary.remove(word);
	}
	
	
	public static void main(String[] args) {
		String text = "攻城狮逆袭单身狗，迎娶白富美，走上人生巅峰";
		final char[] charArray = text.toCharArray();
        CustomDictionary.parseText(charArray, 
        		new AhoCorasickDoubleArrayTrie.IHit<CoreDictionary.Attribute>() {
            @Override
            public void hit(int begin, int end, CoreDictionary.Attribute value) {
                System.out.printf("[%d:%d]=%s %s\n", begin, end, 
                		new String(charArray, begin, end - begin), value);
            }
        });
        
        // 自定义词典在所有分词器中都有效
        System.out.println(HanLP.segment(text));
	}
}
