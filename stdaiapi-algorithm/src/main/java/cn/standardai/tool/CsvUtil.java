/**
* ImageConverter.java
* Copyright 2014 standardai Co.ltd.
*/
package cn.standardai.tool;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 * MNIST parser
 * @author 韩晴
 *
 */
public class CsvUtil {

	private static final int width = 28;

	private static final int height = 28;

	public static void main(String[] args) {

		String inFile1 = "/Users/athvillar/Downloads/JData/JData_Action_201602.csv";
		String inFile2 = "/Users/athvillar/Downloads/JData/JData_Action_201603.csv";
		String inFile3 = "/Users/athvillar/Downloads/JData/JData_Action_201604.csv";
		String inFiles[] = { inFile1, inFile2, inFile3 };
		int[] keyIndice = {0};
		String[] values = {"287842.0"};
		int[] includeIndice = {0,1,4};
		String outFile = "/Users/athvillar/Documents/W_JData_Action_" + values[0] + ".csv";
		String[][] result = parse(inFiles, outFile, keyIndice, values, includeIndice, false, true);

		return;
	}

	public static String[][] parse(String[] inFileNames, String outFileName,
			int[] keyIndice, String[] values,
			int[] includeIndice, boolean keep1stRow,
			boolean distinct) {

		FileReader fr = null;
		BufferedReader br = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		List<String[]> result = new ArrayList<String[]>();
		Map<String, String> resultMap = new HashMap<String, String>();

		try {
			if (outFileName != null) {
				fw = new FileWriter(outFileName);
				bw = new BufferedWriter(fw);
			}
			for (String inFileName : inFileNames) {

				fr = new FileReader(inFileName);
				br = new BufferedReader(fr);

				if (!keep1stRow) br.readLine();
		
				String line;
				while ((line = br.readLine()) != null) {
					String[] items = line.split(",");
					String[] result1;
					if (includeIndice != null && includeIndice.length != 0) {
						result1 = new String[includeIndice.length];
					} else {
						result1 = new String[items.length];
					}
					
					boolean drop = false;
					if (keyIndice != null) {
						for (int i = 0; i < keyIndice.length; i++) {
							if (!values[i].equals(items[keyIndice[i]])) {
								drop = true;
								break;
							}
						}
					}
					if (drop) continue;

					if (includeIndice != null && includeIndice.length != 0) {
						for (int i = 0; i < includeIndice.length; i++) {
							result1[i] = items[includeIndice[i]];
						}
					} else {
						for (int i = 0; i < items.length; i++) {
							result1[i] = items[i];
						}
					}

					if (distinct) {
						String s = stringList2String(result1);
						if (resultMap.containsKey(s)) {
							continue;
						} else {
							resultMap.put(s, "");
						}
					}
					if (outFileName != null) {
						bw.write(stringList2String(result1));
					} else {
						result.add(result1);
					}
				}
				closeReader(fr, br);
			}

			return result.toArray(new String[result.size()][]);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			closeAll(fr, br, fw, bw);
		}
	}

	private static String stringList2String(String[] s) {
		String result = "";
		for (int i = 0; i < s.length; i++) {
			result += s[i];
			if (i == s.length - 1) {
				result += "\n";
			} else {
				result += ",";
			}
		}
		return result;
	}

	private static void closeReader(FileReader fr, BufferedReader br) {

		if (br != null) {
			try {
				br.close();
				br = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (fr != null) {
			try {
				fr.close();
				fr = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void closeWriter(FileWriter fw, BufferedWriter bw) {
		if (bw != null) {
			try {
				bw.close();
				bw = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (fw != null) {
			try {
				fw.close();
				fw = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void closeAll(FileReader fr, BufferedReader br, FileWriter fw, BufferedWriter bw) {
		closeReader(fr, br);
		closeWriter(fw, bw);
	}

	public static void write(String fileName, List<String[]> data) {

		FileWriter fw = null;
		BufferedWriter bw = null;

		try {
			if (fileName != null) {
				fw = new FileWriter(fileName);
				bw = new BufferedWriter(fw);
			}
			for (String[] data1 : data) {
				bw.write(stringList2String(data1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeWriter(fw, bw);
		}
	}
}
