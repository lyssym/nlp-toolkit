package com.ssym.ner.crfpp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.chasen.crfpp.Model;
import org.chasen.crfpp.Tagger;


public class Crf {
	private final Model model;
	private final String name;
	
	static {
		String path = System.getProperty("java.library.path");
		if (!path.contains("CRFPP")) {
			try {
				System.loadLibrary("CRFPP");
			} catch (UnsatisfiedLinkError e) {
			    System.err.println("Cannot load the example native code.\nMake sure your LD_LIBRARY_PATH contains \'.\'\n" + e);
			    System.exit(1);
			}
		}
	}
	
	/**
	 * 加载模型
	 * @param modelName
	 */
	public Crf(String modelName) {
		this.name = modelName;
		this.model = new Model("-m " + modelName);
	}
	
	/**
	 * 根据特征获取标记信息
	 * @param features
	 * @return
	 */
	public List<String> tag(List<String> features) {
		Tagger tg = this.model.createTagger();
		try {
			 long nFeaturesExpected = tg.xsize(); // 获取期待的特征个数
	         tg.clear();

	         for (String feature : features) {
	              long nFeaturesFound = StringUtils.countMatches(feature, " ") + 1L;
	              if (nFeaturesFound != nFeaturesExpected) {
	                  throw new IllegalArgumentException(String.format(
	                        "CRF feature mismatch: %s: CRF expects %d features, but %d were extracted",
	                        name,
	                        nFeaturesExpected,
	                        nFeaturesFound));
	              }
	              tg.add(feature);
	         }
	         
	         if (!tg.parse()) { // 判断CRF模型是否可用
	             throw new IllegalStateException(String.format(
	                   "%s: CRF failed",
	                   name));
	         }
	         
	         int nTagsExpected = features.size(); // 期待返回的标签数
	         long nTagsFound = tg.size();         // 模型标签数
	         if (nTagsFound != nTagsExpected) {
	             throw new IllegalStateException(String.format(
	                   "CRF returned a different number of tags than tokens: %s: expected %d tags, found %d",
	                   name,
	                   nTagsExpected,
	                   nTagsFound));
	         }

	         List<String> tags = new ArrayList<String>(nTagsExpected);
	         for (int i = 0; i < nTagsExpected; i++) {
	             tags.add(tg.y2(i));   // 获取标签个数
	         }
	         tg.clear();

	         return tags;
		} finally {
            tg.delete();
        }
	}
	
	public static List<NamedEntity> entityHelper(String sentence, List<String> tags) {
		List<IOBHelper> iobs = new ArrayList<IOBHelper>();
		for (int i = 0; i < tags.size(); i++) {
			iobs.add(new IOBHelper(tags.get(i)));
		}	
		return iobHelper(sentence, iobs);
	}
	
	
	public static List<NamedEntity> iobHelper(String sentence, List<IOBHelper> iobs) {
		int size = sentence.length();
		if (size != iobs.size()) return null;
		
		List<NamedEntity> ret = new ArrayList<NamedEntity>();
		int end = 0;
		for (int i = 0; i < size; i++) {
			IOBHelper iobEntity = iobs.get(i);
			String tag = iobEntity.getTag();
			if (tag.equals(IOB.B.name())) { // 以B开始, 以此开始计算才有意义
				String name = iobEntity.getName();  // 以初始信息作为标签
				int begin = i;
				while (!tag.equals(IOB.O.name())) { // 尚未到达结束位置
					++i;
					if (i == size) { // 到达字符串结束位置
						break;
					}
				}
			    end = i;
			    
			    if (i == size) { // 到达字符串结束位置
			    	ret.add(new NamedEntity(name, sentence.substring(begin, end)));
			    	break;
			    } else {
			    	ret.add(new NamedEntity(name, sentence.substring(begin, end)));
			    }
			}
		}
		
		return ret;
	}
	
	
	public static void main(String[] argv) {
		String B = "B";
		System.out.println(B.equals(IOB.B.name()));
	}
}
