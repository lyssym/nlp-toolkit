package com.ssym.ner.crfpp;

public class IOBHelper {
	private final String tag;
	private final String name;
	
	public IOBHelper(String value) {
		String[] sp = value.split("_");
		int len = sp.length;
		if (len > 1) { // 存在需要拆分情形
			tag = sp[0];
			name = sp[1];
		} else { // 单标签, 即O标签
			tag = sp[0];
			name = null;
		}
	}

	public String getTag() {
		return tag;
	}

	public String getName() {
		return name;
	}

}
