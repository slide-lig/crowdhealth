package Preprocessing;

import java.io.*;
import java.util.*;

public class FilteronHealthKeyword {

	
    public static List<String> ngrams(int n, String str) {
        List<String> ngrams = new ArrayList<String>();
        String[] words = str.split(" ");
        for (int i = 0; i < words.length - n + 1; i++)
            ngrams.add(concat(words, i, i+n));
        return ngrams;
    }

    public static String concat(String[] words, int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++)
            sb.append((i > start ? " " : "") + words[i]);
        return sb.toString();
    }

	
	 public static void main(String args[])
	  {
		 Map<String,Integer> scrapedTokens = new HashMap<String,Integer>();


		   try{
			   File dir = new File(args[1]);
				String[] chld = dir.list();
				Arrays.sort(chld);
				int numberofHealthTokens = 0;
				for (int i = 0; i < chld.length; i++) {
					//System.out.println("processing file" + chld[i]);
					   FileInputStream fstream = new FileInputStream(args[1]+chld[i]);
					   DataInputStream in = new DataInputStream(fstream);
					   BufferedReader br = new BufferedReader(new InputStreamReader(in));
					   String strLine;
					   
					   while ((strLine = br.readLine()) != null)   {	
					   scrapedTokens.put(strLine.toLowerCase(),numberofHealthTokens++ );
					   }
					   in.close();
					     }
					
				}
catch (Exception e){//Catch exception if any
		 e.printStackTrace();
		   }
		   
		 
	  try{
	  FileInputStream fstream = new FileInputStream(args[0]);
	  DataInputStream in = new DataInputStream(fstream);
	  BufferedReader br = new BufferedReader(new InputStreamReader(in));
	  String strLine;
	  PrintWriter out  = new PrintWriter(new BufferedWriter(new FileWriter("../FilteredTweetsWithOptimization",true)));
	
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

		  String tweetText = strLine.substring(indexofSecondTab+1, indexofThirdTab);
		  String [] tokenizer = tweetText.split(" ");
		  int length = tokenizer.length;
			if(length ==0)
			continue;
		  	String [] tokens = null;
		  	String []temp = null;
		  	ArrayList<String>tempforadding = new ArrayList<String>();
		  	ArrayList<String>nGrams=null;
		  	
		  	for(int n = 1 ; n <= length ; n++)
		  	{
		  		
		  		nGrams = (ArrayList<String>) ngrams(n,tweetText);
		  		temp = nGrams.toArray(new String[nGrams.size()]);
		  		for(int m = 0 ; m < temp.length ; m++)
		  		{
		  			tempforadding.add(temp[m]);
		  		}
		  		tokens = tempforadding.toArray(new String[tempforadding.size()]);
		  	}
		  	for(int i = 0 ; i < tokens.length ; i ++)
		  	{
		  		if(scrapedTokens.containsKey(tokens[i].toLowerCase()))
		  		{
					//System.out.println("token : "+ tokens[i]);
		  			out.write(strLine+"\n");
		  			break;
		  		}
		  	}	  	
		  	
	  }
	  out.close();
	  in.close();
	    }catch (Exception e){
	    	e.printStackTrace();
	  }
	  }
	
	
	
	
	
	
	

}
