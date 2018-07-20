package com.ssym.ner.crfpp;

public class NamedEntity {
	private String name = null;      // 实体名
	private String value = null;     // 实体值/实体对齐
	private String original = null;  // 原始值
	 
	public NamedEntity(String name, String original) {
		this.name = name;
		this.original = original;
		this.value = original;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) { // 名称映射
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) { // 实体对齐
		this.value = value;
	}

	public String getOriginal() {
		return original;
	}
}
