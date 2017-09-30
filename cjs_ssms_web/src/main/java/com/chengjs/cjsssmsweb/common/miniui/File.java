package com.chengjs.cjsssmsweb.common.miniui;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class File {
	public static String encoding = "UTF-8";
	public static String read(String path){		 
		StringBuffer   buf = new StringBuffer();
		try {
			FileInputStream in = new FileInputStream(path);
			// 指定读取文件时以UTF-8的格式读取
			BufferedReader br = new BufferedReader(new UnicodeReader(in, encoding));			
			String line = br.readLine();
			while (line != null) {	
				buf.append(line);
				line = br.readLine();
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}			
		return buf.toString();	
	}
	public static void write(String path, String content){
		try
        {                     
            OutputStreamWriter out = new OutputStreamWriter(
            		new FileOutputStream(path),"UTF-8");
            //out.write("\n"+content);
            out.write(content);
            out.flush();
            out.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
	}
	public static String getFileName(String fileName){
		String[] ss = fileName.split(".");
		fileName = ss[0];
		String[] ss2 = fileName.split("/");
		fileName = ss[ss.length-1];
		return fileName;
	}
}
