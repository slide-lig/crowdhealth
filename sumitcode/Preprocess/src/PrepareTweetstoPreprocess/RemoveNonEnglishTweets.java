package PrepareTweetstoPreprocess;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RemoveNonEnglishTweets {
 public static void main(String [] args)
 {
	 try{
		 FileInputStream fileInputStream = new FileInputStream("/home/andreas/Nutrition Project/Datasets/1mbchunk.tsv");
		 DataInputStream 	dataInputStream = new DataInputStream(fileInputStream);
		 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
		 String strLine ; 
		 while((strLine = bufferedReader.readLine())!=null)
		 {
//			 System.out.println(strLine);
			 int indexofLastTab = strLine.lastIndexOf("\t");
			 String tweetWithouttheTweetText = strLine.substring(0,indexofLastTab-1);
			 String location = tweetWithouttheTweetText.substring(tweetWithouttheTweetText.lastIndexOf("\t"));
			String tweetText = strLine.substring(indexofLastTab);
			  PatternforMatchingNonEnglishCharacters patternforMatchingNonEnglishCharacters=new PatternforMatchingNonEnglishCharacters();
			if(patternforMatchingNonEnglishCharacters.containsSpecialCharacters(location))
			 {
				 System.out.println(strLine);
				  
			 }
			 
		 }
		 dataInputStream.close();
		 
	 }
	 catch(Exception e)
	 {
		 e.printStackTrace();
	 }
	 
 }

}
