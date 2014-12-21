package Preprocessing;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class DetectUserNameHashTag {
	
	static String parse(String tweetText) {
		
	     String patternStr = "(?:\\s|\\A)[##]+([A-Za-z0-9-_]+)";
	     Pattern pattern = Pattern.compile(patternStr);
	     Matcher matcher = pattern.matcher(tweetText);
	     String result = "";
	 
	     // Search for Hashtags
	     while (matcher.find()) {
	         result = matcher.group();
	         result = result.replace(" ", "");
//	         String search = result.replace("#", "");
//	         String searchHTML="<a href='http://search.twitter.com/search?q=" + search + "'>" + result + "</a>";
	         String searchHTML = "";
	         tweetText = tweetText.replace(result,searchHTML);
	     }
	 
	     // Search for Users
	     patternStr = "(?:\\s|\\A)[@]+([A-Za-z0-9-_]+)";
	     pattern = Pattern.compile(patternStr);
	     matcher = pattern.matcher(tweetText);
	     while (matcher.find()) {
	         result = matcher.group();
	         result = result.replace(" ", "");
//	         String rawName = result.replace("@", "");
//	         String userHTML="<a href='http://twitter.com/${rawName}'>" + result + "</a>";
	         String userHTML = "";
	         tweetText = tweetText.replace(result,userHTML);
	     }
	     return tweetText;
	 }
                   public static void main(String [] args)
                   {
                	try{
                		FileInputStream fileInputStream = new FileInputStream(args[0]);
                		DataInputStream dataInputStream = new DataInputStream(fileInputStream);
                		BufferedReader bufferedReader =   new BufferedReader(new InputStreamReader(dataInputStream));
                		String strLine;
                		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("../TweetsTobeFiltered", true)));
                		while((strLine = bufferedReader.readLine())!=null)
                		{
                            int indexoffirstTab = strLine.indexOf("\t");
                            if(indexoffirstTab == -1)
                                    continue;
                            int indexofSecondTab = strLine.indexOf("\t",indexoffirstTab + 1);
                            if(indexofSecondTab == -1)
                                    continue ;
                            int indexofThirdTab = strLine.indexOf("\t",indexofSecondTab + 1);
                            if(indexofThirdTab == -1)
                                    continue;
                			
                			String tweetTextBefore = strLine.substring(indexofSecondTab+1,indexofThirdTab);
//                			System.out.println("before : "+ tweetTextBefore);
                			String tweetTextAfter = parse(tweetTextBefore);
//                			System.out.println("Afer : "+ tweetTextAfter);
                			String strTobeWritten = strLine.replace(tweetTextBefore, tweetTextAfter);
//                			System.out.println("String to be Written : " + strTobeWritten);
                			out.println(strTobeWritten);	
                		}
				out.close();
                	}
                	catch(Exception e)
                	{
                		e.printStackTrace();
                	}
                   }
}
