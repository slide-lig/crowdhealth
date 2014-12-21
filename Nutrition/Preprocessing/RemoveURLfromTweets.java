package Preprocessing;

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
			  FileInputStream fstream = new FileInputStream(args[0]);
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("../TweetsWithoutURLandRTTag", true)));
			  while ((strLine = br.readLine()) != null)   {
                          int indexoffirstTab = strLine.indexOf("\t");
                          if(indexoffirstTab == -1)
                                  continue;
                          int indexofSecondTab = strLine.indexOf("\t",indexoffirstTab + 1);
                          if(indexofSecondTab == -1)
                                  continue ;
                          int indexofThirdTab = strLine.indexOf("\t",indexofSecondTab + 1);
                          if(indexofThirdTab == -1)
                                  continue;

			  
			  String tweetText = strLine.substring(indexofSecondTab+1,indexofThirdTab);
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
			  if(tweetText.contains("RT @"))
			  {
				 
				  flagWriteStringintoFile = false;
			  }
			  if(flagWriteStringintoFile)
			  {
			  out.println(strLine);
			  }
			  
			  flagWriteStringintoFile = true;
			  
			    }
			  in.close();
			  out.close();
			}catch (Exception e){
			  System.err.println("Error: " + e.getMessage());
			  }
		
	}
	
	

}
