package RemovalofURL;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.apache.commons.validator.routines.*;;


public class RemoveURLfromTweets {
	
	

	public static void main(String [] args)
	{
		  String[] schemes = {"http","https"};
		UrlValidator urlValidator = new UrlValidator(schemes);
		boolean flagWriteStringintoFile = true;
		try{
			  FileInputStream fstream = new FileInputStream("/media/Sumit/finalenglish");
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/media/Sumit/finalenglishwithoutURLs", true)));
			  while ((strLine = br.readLine()) != null)   {
			  int indexoflastTab = strLine.lastIndexOf("\t");
			  if(indexoflastTab == -1)
				  continue;
			  
			  String tweetText = strLine.substring(indexoflastTab);
			  String []tokenspresentInTweetText = tweetText.split(" ");
			  for(int i = 0 ; i < tokenspresentInTweetText.length ; i++)
			  {
				  if(urlValidator.isValid(tokenspresentInTweetText[i]))
				  {
					 flagWriteStringintoFile = false;
					 //System.out.println("String containes url :" + strLine);
					 break;
				  }
			  }
//			  if(tweetText.contains("RT @"))
//			  {
//				 
//				  flagWriteStringintoFile = false;
//			  }
			  if(flagWriteStringintoFile)
			  {
			  out.println(strLine);
			  }
			  
			  flagWriteStringintoFile = true;
			  
			    }
			  in.close();
			}catch (Exception e){
			  System.err.println("Error: " + e.getMessage());
			  }
		
	}
	
	

}