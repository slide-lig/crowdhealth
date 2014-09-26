package TweetFilter;



import java.io.*;
import java.util.*;
public class FilteronHealthKeyword {


	
	 public static void main(String args[])
	  {
		 Map<String,Integer> scrapedTokens = new HashMap<String,Integer>();


		   try{
			   File dir = new File("/media/C284B5B784B5ADF3/NutritionRelatedKeyWords");
				String[] chld = dir.list();
				Arrays.sort(chld);
				int numberofHealthTokens = 0;
				for (int i = 0; i < chld.length; i++) {
					System.out.println("processing file" + chld[i]);
					   FileInputStream fstream = new FileInputStream("/media/C284B5B784B5ADF3/NutritionRelatedKeyWords/"+chld[i]);
					   DataInputStream in = new DataInputStream(fstream);
					   BufferedReader br = new BufferedReader(new InputStreamReader(in));
					   String strLine;
					   
					   while ((strLine = br.readLine()) != null)   {
						   strLine = strLine.toLowerCase();
						   strLine = strLine.trim();
							Stemmer s =new Stemmer();
							for(int j=0;j<strLine.length();j++)
								s.add(strLine.charAt(j));
							s.stem();
					   scrapedTokens.put(strLine.toLowerCase(),numberofHealthTokens++ );
					   }
					   in.close();
					     }
					
				}
catch (Exception e){//Catch exception if any
		   System.err.println("Error: " + e.getMessage());
		   }
		   
		 
	  try{
	  FileInputStream fstream = new FileInputStream("/media/Sumit/finalenglishwithoutURLsandRepeatTweets");
	  DataInputStream in = new DataInputStream(fstream);
	  BufferedReader br = new BufferedReader(new InputStreamReader(in));
	  String strLine;
	  PrintWriter out  = new PrintWriter(new BufferedWriter(new FileWriter("/media/Sumit/FileForWorkersandNutrition/NutritionfileforworkerwithoutURLsandRepeatTweetsremovedwordWithStem",true)));
	  long testIterator = 0 ;
	  while ((strLine = br.readLine()) != null)   {
		  String tweetText = strLine.substring(strLine.lastIndexOf("\t"));
		  	String[] tokens = tweetText.split(" ");
		  	testIterator++;
		  	for(int tokensIterator = 0 ; tokensIterator < tokens.length ; tokensIterator++)
		  	{
		  		tokens[tokensIterator] = tokens[tokensIterator].trim();
		  		tokens[tokensIterator] = tokens[tokensIterator].toLowerCase();
				Stemmer s =new Stemmer();
				for(int j=0;j<tokens[tokensIterator].length();j++)
					s.add(tokens[tokensIterator].charAt(j));
				s.stem();
		  		if(scrapedTokens.containsKey(tokens[tokensIterator].toLowerCase()))
		  		{
		  			
		  			System.out.println(tokens[tokensIterator]);
		  			out.println(strLine);
		  			break;
		  		}
		  	}
		  	
		  	
	  }
	  System.out.println(testIterator);
	  in.close();
	    }catch (Exception e){
	    	e.printStackTrace();
	  }
	  }
	
	
	
	
	
	
	

}
