package com.ssym.ner.crfpp;

import java.io.BufferedWriter;
import java.io.IOException;

import com.ssym.io.FileIO;

public class CrfTemplate {
	
	public static void createComplicateTemplate(String domain, int nFeatures) throws IOException {
		String fileName = domain + "_template.txt";
		BufferedWriter bw = FileIO.newBufferedWriter(fileName);
		bw.write("# Unigram Features" + "\n");
		
		int index = 0;
		int column = 0;
		String s = null;
		for (int i = -2; i <= 2; i++, column++) {
			s = "U" + index + column + ":%x" + "[" + i + "," + index + "]";
			bw.write(s + "\n");
		}
		for (int i = -1; i < 1; i ++, column++) {
			s = "U" + index + column + ":%x" + "[" + i + "," + index + "]/%x" + "[" + (i+1) + "," + index + "]";
			bw.write(s + "\n");
		}
		
		for (int k = ++index; k <= nFeatures; k++) {
			column = 0;
			for (int i = -2; i <= 2; i++, column++) {
				s = "U" + k + column + ":%x" + "[" + i + "," + k + "]";
				bw.write(s + "\n");
			}
			s = "U" + k + column + ":%x" + "[" + (-1) + "," + k + "]%x" + "[" + 0 + "," + k + "]";
			bw.write(s + "\n");
		}
		
		bw.write("# Bigram Features" + "\n");
		bw.write("B" + "\n");
		
		bw.close();
	}
	
	
	public static void createTemplate(String domain, int nFeatures) throws IOException {
		String fileName = domain + "_template.txt";
		BufferedWriter bw = FileIO.newBufferedWriter(fileName);
		bw.write("# Unigram Features" + "\n");
		
		int index = 0;
		int column = 0;
		String s = null;
		for (int i = -3; i <= 3; i++, column++) {
			s = "U" + index + column + ":%x" + "[" + i + "," + index + "]";
			bw.write(s + "\n");
		}
		
		for (int k = ++index; k <= nFeatures; k++) {
			column = 0;
			for (int i = -2; i <= 2; i++, column++) {
				s = "U" + k + column + ":%x" + "[" + i + "," + k + "]";
				bw.write(s + "\n");
			}
		}
		
		bw.write("# Bigram Features" + "\n");
		bw.write("B" + "\n");
		
		bw.close();
	}
	
	
	public static void main(String[] args) {
		String domain = "child";
		try {
			createTemplate(domain, 3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
