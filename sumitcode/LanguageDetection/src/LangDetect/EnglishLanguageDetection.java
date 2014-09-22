package LangDetect;



import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;
import java.io.*;

public class EnglishLanguageDetection extends java.lang.Object {
	
	
	public String languageDetect(String str)
	{

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
		try {
			DetectorFactory.loadProfile("/home/andreas/Downloads/langdetect-09-13-2011/profiles");
		} catch (LangDetectException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try{
			  // Open the file that is 	the first 
			  // command line parameter
			  FileInputStream fstream = new FileInputStream("/home/andreas/Nutrition Project/Datasets/1mbchunk.tsv");
			  // Get the object of DataInputStream
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/home/andreas/Nutrition Project/Datasets/onlyEnglishTweetsExtracted", true)));
			  //Read File Line By Line
			  while ((strLine = br.readLine()) != null)   {
			  // Print the content on the console
			EnglishLanguageDetection englLanguageDetection = new EnglishLanguageDetection();
			  String language = englLanguageDetection.languageDetect(strLine.substring(strLine.lastIndexOf("\t")));
			 // System.out.print(strLine.substring(strLine.lastIndexOf("\t"))+":   ");
			  //System.out.println(language);  
			  if(language.equalsIgnoreCase("en"))
			  {
				  out.println(strLine);				  
			  }
			  }
			  //Close the input stream
			  in.close();
			    }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }
	}
	
	
	
	
	
	
	
	
}