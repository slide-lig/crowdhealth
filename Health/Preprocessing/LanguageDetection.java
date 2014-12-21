package LangDetectionForNewTweets;





import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;
import java.io.*;

public class LanguageDetection extends java.lang.Object {
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
			DetectorFactory.loadProfile("/home/slide/sidana /langdetect-09-13-2011/profiles");
		} catch (LangDetectException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try{
			  FileInputStream fstream = new FileInputStream("/home/slide/sidana/tweet.csv");

			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/home/slide/sidana/onlyEnglishTweetsExtractedfromSample", true)));
			  while ((strLine = br.readLine()) != null)   {
			LanguageDetection englLanguageDetection = new LanguageDetection();
			  int indexoffirstTab = strLine.indexOf("\t");
			  if(indexoffirstTab == -1)
				  continue;
			  int indexofSecondTab = strLine.indexOf("\t",indexoffirstTab + 1);
			  if(indexofSecondTab == -1)
				  continue ;
			  int indexofThirdTab = strLine.indexOf("\t",indexofSecondTab + 1);
			  if(indexofThirdTab == -1)
				  continue;
			  
			  
			  String language = englLanguageDetection.languageDetect(strLine.substring(indexofSecondTab+1, indexofThirdTab)); 
			  if(language.equalsIgnoreCase("en"))
			  {
				  out.println(strLine);				  
			  }
			  }
			  out.close();
			  in.close();
			    }catch (Exception e){
			  System.err.println("Error: " + e.getMessage());
			  }
	}
	
	
	
	
	
	
	
	
}

