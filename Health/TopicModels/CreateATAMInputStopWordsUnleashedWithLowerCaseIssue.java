package TopicModels;
import java.util.*;
import java.io.*;
public class CreateATAMInputStopWordsUnleashedWithLowerCaseIssue {

	public static String Concat(List<String> source) {
	    String sb = new String();
	    for(int i = 0; i < source.size() ; i++)
	    {
	    	String s = source.get(i);
	    	if(i<source.size() - 1)
	    		sb = sb.concat(s+" ");
	    	else
	    		sb = sb.concat(s+"");
	    }
	    return sb;
	}
	
    public static List<String> ngrams(int n, String str) {
        List<String> ngrams = new ArrayList<String>();
        String[] words = str.split("[\\s+\\-*/\\^:;\\[\\]\\\\()_#@$%&|<>\'\",.?{}!=`~]+");
        for (int i = 0; i < words.length - n + 1; i++)
            ngrams.add(concat(words, i, i+n));
        return ngrams;
    }

    public static String concat(String[] words, int start, int end) {
        java.lang.StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++)
            sb.append((i > start ? " " : "") + words[i]);
        return sb.toString();
    }
    
    public static void main(String [] args)
    {
        TopicModels.StanfordLemmatizer slem = new TopicModels.StanfordLemmatizer();
        //System.out.println(slem.lemmatize(text));
    	Map<String,Integer> aspectofTokensOfTweet = new HashMap<String,Integer>();
    	Map<String,Integer> scrapedTokensDiseases = new HashMap<String,Integer>();
    	Map<String,Integer> scrapedTokensSymptoms = new HashMap<String,Integer>();
    	Map<String,Integer> scrapedTokensTreatments = new HashMap<String,Integer>();
    	Set<String> stopWords = new HashSet<String>();
    	try{
    		/*
    		 * Load the stopwords
    		 */
    		FileInputStream fileInputStreamSW = new FileInputStream
    				(args[0]);
    		DataInputStream dataInputStreamSW = new DataInputStream(fileInputStreamSW);
    		BufferedReader bufferedReaderSW = new BufferedReader
    				(new InputStreamReader(dataInputStreamSW));
    		String strLineSW;
    		while((strLineSW = bufferedReaderSW.readLine())!=null)
    		{
    			stopWords.add(strLineSW.toLowerCase());
    		}
    		
    		File dir = new File(args[1]);
    		String [] chld = dir.list();
    		Arrays.sort(chld);
    		int numberofHealthTokens = 0;
    		
    		for(int i = 0 ; i < chld.length ; i++)
    		{
    			if(chld[i].equalsIgnoreCase("keywords_diseases.txt"))
    			{
    				System.out.println("processing file"+chld[i]);
    				FileInputStream fstream = new FileInputStream(args[1]+
    						"keywords_diseases.txt");
    				DataInputStream in = new DataInputStream(fstream);
    				BufferedReader br = new BufferedReader(new InputStreamReader(in));
    				String strLine;
    				while((strLine = br.readLine())!=null)
    				{
    					List<String>listofStrings = slem.lemmatize(strLine);
    					strLine = Concat(listofStrings);
    					scrapedTokensDiseases.put(strLine.toLowerCase(), 
    							numberofHealthTokens++);
    				}
    				in.close();
    			}
    			else if(chld[i].equalsIgnoreCase("keywords_symptoms.txt"))
    			{
    				System.out.println("processing file"+chld[i]);
    				FileInputStream fstream = new FileInputStream
    						(args[1]+"keywords_symptoms.txt");
    				DataInputStream in = new DataInputStream(fstream);
    				BufferedReader br = new BufferedReader(new InputStreamReader(in));
    				String strLine;
    				while((strLine = br.readLine())!=null)
    				{
    					List<String>listofStrings = slem.lemmatize(strLine);
    					strLine = Concat(listofStrings);
    					scrapedTokensSymptoms.put(strLine.toLowerCase(), 
    							numberofHealthTokens++);
    				}
    				in.close();		
    			}
    			
    			
    			else if(chld[i].equalsIgnoreCase("keywords_treatments.txt"))
    			{
    				System.out.println("processing file"+chld[i]);
    				FileInputStream fstream = new FileInputStream
    						(args[1]+"keywords_treatments.txt");
    				DataInputStream in = new DataInputStream(fstream);
    				BufferedReader br = new BufferedReader(new InputStreamReader(in));
    				String strLine;
    				while((strLine = br.readLine())!=null)
    				{
    					List<String>listofStrings = slem.lemmatize(strLine);
    					strLine = Concat(listofStrings);
    					scrapedTokensTreatments.put
    					(strLine.toLowerCase(), numberofHealthTokens++);
    				}
    				in.close();
    			}
    			
    		}
    		
    		FileInputStream fstream = new FileInputStream(args[2]);
    		DataInputStream in = new DataInputStream(fstream);
    		BufferedReader br = new BufferedReader(new InputStreamReader(in));
    		String line;
    		while((line = br.readLine())!=null)
    		{
    	        int indexoffirstTab = line.indexOf("\t");
    	        if(indexoffirstTab == -1)
    	                continue;
    	        int indexofSecondTab = line.indexOf("\t",indexoffirstTab + 1);
    	        if(indexofSecondTab == -1)
    	                continue ;
    	        int indexofThirdTab = line.indexOf("\t",indexofSecondTab + 1);
    	        if(indexofThirdTab == -1)
    	                continue;
    	        String tweetText = line.substring(indexofSecondTab+1, indexofThirdTab);
    	        String [] tokenizer = tweetText.split
    	        		("[\\s+\\-*/\\^:;\\[\\]\\\\()_#@$%&|<>\'\",.?{}!=`~]+");
    	        for(int i = 0 ; i < tokenizer.length ; i++)
    	        {
    	        	aspectofTokensOfTweet.put(tokenizer[i].toLowerCase(), -1);
    	        }
    		}
       		 fstream = new FileInputStream(args[2]);
    		 in = new DataInputStream(fstream);
    		 br = new BufferedReader(new InputStreamReader(in));
    		 
    		while((line = br.readLine())!=null)
    		{
    	        int indexoffirstTab = line.indexOf("\t");
    	        if(indexoffirstTab == -1)
    	                continue;
    	        int indexofSecondTab = line.indexOf("\t",indexoffirstTab + 1);
    	        if(indexofSecondTab == -1)
    	                continue ;
    	        int indexofThirdTab = line.indexOf("\t",indexofSecondTab + 1);
    	        if(indexofThirdTab == -1)
    	                continue;
    	        String tweetText = line.substring(indexofSecondTab+1, indexofThirdTab);
    	        String [] tokenizer = tweetText.split
    	        		("[\\s+\\-*/\\^:;\\[\\]\\\\()_#@$%&|<>\'\",.?{}!=`~]+");
    	        int length = tokenizer.length;
    	        if(length == 0)
    	        continue;
    	        String [] tokens = null;
    	        String [] temp = null;
    	        ArrayList<String> tempforadding = new ArrayList<String>();
    	        ArrayList<String> nGrams = null;
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
    	        for(int i = 0 ; i < tokens.length ; i++)
    	        {
    	        	List<String> listofStrings = slem.lemmatize(tokens[i]);
    	        	String newStringForComparison = Concat(listofStrings);
    	         if(scrapedTokensSymptoms.containsKey(newStringForComparison.toLowerCase()))
    	        	{
    	 
    	        		String [] spliterofSymptomsToken = tokens[i].split
    	        				("[\\s+\\-*/\\^:;\\[\\]\\\\()_#@$%&|<>\'\",.?{}!=`~]+");
    	        		for(int j = 0 ; j < spliterofSymptomsToken.length ; j++)
    	        		{
    	        			aspectofTokensOfTweet.put
    	        			(spliterofSymptomsToken[j].toLowerCase(), 1);
    	        		}
    	        	}
 	        	
 	        	else if(scrapedTokensTreatments.containsKey
 	        			(newStringForComparison.toLowerCase()))
 	        	{
 	        		
 	        		String [] spliterofTreatmentsToken = tokens[i].split
 	        				("[\\s+\\-*/\\^:;\\[\\]\\\\()_#@$%&|<>\'\",.?{}!=`~]+");
 	        		for(int j = 0 ; j < spliterofTreatmentsToken.length ; j++)
 	        		{
 	        			aspectofTokensOfTweet.put
 	        			(spliterofTreatmentsToken[j].toLowerCase(), 2);
 	        		}
 	        	}
    	         else if(scrapedTokensDiseases.containsKey
    	        		 (newStringForComparison.toLowerCase()))
    	        	{
    	        		String [] spliterofDiseasesToken = tokens[i].split
    	        				("[\\s+\\-*/\\^:;\\[\\]\\\\()_#@$%&|<>\'\",.?{}!=`~]+");
    	        		for(int j = 0 ; j < spliterofDiseasesToken.length ; j++)
    	        		{
    	        			if((aspectofTokensOfTweet.get
    	        					(spliterofDiseasesToken[j].toLowerCase())!=1)||
    	        					(aspectofTokensOfTweet.get
    	        							(spliterofDiseasesToken[j].toLowerCase())!=2))
    	        			aspectofTokensOfTweet.put
    	        			(spliterofDiseasesToken[j].toLowerCase(), 0);
    	        		}
    	        	}



    	        	
    	        }
    		}
    		 fstream = new FileInputStream(args[2]);
    		 in = new DataInputStream(fstream);
    		 br = new BufferedReader(new InputStreamReader(in));
    		 PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter
    				 ("../InputToATAMUsingLemmatizer",true)));
    		while((line = br.readLine())!=null)
    		{
    	        int indexoffirstTab = line.indexOf("\t");
    	        if(indexoffirstTab == -1)
    	                continue;
    	        int indexofSecondTab = line.indexOf("\t",indexoffirstTab + 1);
    	        if(indexofSecondTab == -1)
    	                continue ;
    	        int indexofThirdTab = line.indexOf("\t",indexofSecondTab + 1);
    	        if(indexofThirdTab == -1)
    	                continue;
    	        out.write(line.substring(0, indexoffirstTab)+" ");
    	        String tweetText = line.substring(indexofSecondTab+1, indexofThirdTab);
    	        String []tokens = tweetText.split
    	        		("[\\s+\\-*/\\^:;\\[\\]\\\\()_#@$%&|<>\'\",.?{}!=`~]+");
    	        for(int i = 0 ; i < tokens.length ; i++)
    	        {
    	        	if(i < tokens.length - 1)
    	        	{
    	        		if(!(stopWords.contains(tokens[i].toLowerCase())))
    	        		out.write(
    	        				tokens[i].toLowerCase()+":"+aspectofTokensOfTweet.get
    	        				(tokens[i].toLowerCase())+" ");
    	        	}
    	        	else
    	        	{
    	        		if(!(stopWords.contains(tokens[i].toLowerCase())))
    	        		out.write
    	        		(tokens[i].toLowerCase()+":"+aspectofTokensOfTweet.get
    	        				(tokens[i].toLowerCase())+"");
    	        	}
    	        }
    	        out.write("\n");

    		}
    		out.close();
    		in.close();
    		br.close();
    		fstream.close();
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
}
