package LangDetect;



import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;
import java.io.*;

public class EnglishLanguageDetection extends java.lang.Object {
/*
 * This class detects English Language.
 * Uses the library at https://code.google.com/p/language-detection/wiki/Tutorial
 *	
 */
	
	public String languageDetect(String str)
	{
		/*
		 * This function detects the language
		 * detector can be created by the static method create 
		 * of DetectorFactory class.
		 * detect method helps to detect the language
		 * en is the code for English Language
		 */

		String language = "";
		try {
			
			Detector detector = DetectorFactory.create();
			detector.append(str);
			 language = detector.detect();
			
		} catch (LangDetectException e) {
			// TODO Auto-generated catch block
			
			//e.printStackTrace();
			return "something";	
		}
		return language;
	}
	public static void main(String[] args)
	{
		/*
		 * First we need to load the profile.
		 * First we read each line of tweets.
		 * Extract the tweet text by extracting text after last tab.
		 * Then see what is the language of that tweet text.
		 * if it is english write the file into new file.
		 * 
		 */
		try {
			DetectorFactory.loadProfile("/home/andreas/Downloads/langdetect-09-13-2011/profiles");
		} catch (LangDetectException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try{
			  FileInputStream fstream = new FileInputStream("/home/andreas/Nutrition Project/Datasets/20140719_052319:No_Query_Terms.tsv");

			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/media/C284B5B784B5ADF3/onlyEnglishTweetsExtracted", true)));
			  while ((strLine = br.readLine()) != null)   {
			EnglishLanguageDetection englLanguageDetection = new EnglishLanguageDetection();
			  int indexoflastTab = strLine.lastIndexOf("\t");
			  if(indexoflastTab == -1)
				  continue;
			  String language = englLanguageDetection.languageDetect(strLine.substring(strLine.lastIndexOf("\t"))); 
			  if(language.equalsIgnoreCase("en"))
			  {
				  out.println(strLine);				  
			  }
			  }
			  in.close();
			    }catch (Exception e){
			  System.err.println("Error: " + e.getMessage());
			  }
	}
	
	
	
	
	
	
	
	
}
