package DataReductionFormatAcceptabletoSVMlight;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class FormatforSVM {
	
	public void generateFileforSVM(String pathToFile,String delim)
	{
		/*
		 * Assuming that file input is
		 * <Tweet id,TweetText,Label>- A file containing just the tweet text and the tweet label.
		 * <Tweetid,Label> is the majority table.
		 * < Tweetid, <Userid,Label> <Userid,Label>......Ten Times > is the annotation table
		 */
		try{
		
		  FileInputStream fstream                = new FileInputStream(pathToFile);
		  DataInputStream in                     = new DataInputStream(fstream);
		  BufferedReader br                      = new BufferedReader(new InputStreamReader(in));
		  String strLine;
		  Map<String,Double> termCorpusFrequency = new HashMap<String,Double>(); 
		  Map<String,Double> termTweetFrequency  = new HashMap<String,Double>(); 
		  Map<String,Integer> termIndexNumber    = new HashMap<String,Integer>();
		  Map<Integer,Double> tokenTF_IDFstore   = new TreeMap<Integer,Double>();
		  Map<String,Boolean> captureDuplicateStringsinOneTweet = new HashMap<String,Boolean>();
		  BufferedWriter  bw                     = new BufferedWriter(new FileWriter("/media/Sumit/fileforSVM",true));
		  
		  int countofTotalUniqueWordsinCorpus = 0;
		  double sizeofCorpus = 0 ;
		  
		  while((strLine = br.readLine())!=null)
		  {
			  captureDuplicateStringsinOneTweet.clear();
			  sizeofCorpus++;
			  int indexofFirstDelimiter = strLine.indexOf(delim);
			  int indexofLastDelmiter   = strLine.lastIndexOf(delim);
			  String tweetText = strLine.substring(indexofFirstDelimiter+1, indexofLastDelmiter);
			  String [] arrayofWordsinTweetText = tweetText.split(" ");
			  for(int i = 0 ; i < arrayofWordsinTweetText.length ; i++)
			  {
				  captureDuplicateStringsinOneTweet.put(arrayofWordsinTweetText[i],false);
			  }
			  for(int i = 0 ; i < arrayofWordsinTweetText.length ; i++)
			  {
				  if(termCorpusFrequency.containsKey(arrayofWordsinTweetText[i]))
				  {

					  if(captureDuplicateStringsinOneTweet.get(arrayofWordsinTweetText[i]).equals(false))
					  {
					  termCorpusFrequency.put(arrayofWordsinTweetText[i], termCorpusFrequency.get(arrayofWordsinTweetText[i])+1);
					  captureDuplicateStringsinOneTweet.put(arrayofWordsinTweetText[i], true);
					  }
					  
				  }
				  else
				  {
					  termCorpusFrequency.put(arrayofWordsinTweetText[i], 1.0);
					  captureDuplicateStringsinOneTweet.put(arrayofWordsinTweetText[i], true);
				  }
				  
			  if(!(termIndexNumber.containsKey(arrayofWordsinTweetText[i])))
			  termIndexNumber.put(arrayofWordsinTweetText[i], countofTotalUniqueWordsinCorpus++);
			  }
			  
		  }
		  fstream = new FileInputStream(pathToFile);
		  in = new DataInputStream(fstream);
		  br = new BufferedReader(new InputStreamReader(in));
		  
		  while((strLine = br.readLine())!=null)
		  {
			  int indexofFirstDelmiter = strLine.indexOf(delim);
			  int indexofLastDelmiter = strLine.lastIndexOf(delim);
			  String classLabel = strLine.substring(indexofLastDelmiter+1);
			  String tweetText = strLine.substring(indexofFirstDelmiter+1, indexofLastDelmiter);
			  String [] arrayofWordsinTweetText = tweetText.split(" ");
			  termTweetFrequency.clear();
			  for(int i = 0 ; i < arrayofWordsinTweetText.length; i++)
			  {
				  if(termTweetFrequency.containsKey(arrayofWordsinTweetText[i]))
					  termTweetFrequency.put(arrayofWordsinTweetText[i], termTweetFrequency.get(arrayofWordsinTweetText[i])+1);
				  else
					  termTweetFrequency.put(arrayofWordsinTweetText[i], 1.0);
			  }
			  bw.write(classLabel+" ");
			  tokenTF_IDFstore = new TreeMap<Integer,Double>();
			  for(int i = 0 ; i < arrayofWordsinTweetText.length ; i++)
			  {
				  int indexofTokeninCorpus = termIndexNumber.get(arrayofWordsinTweetText[i]);
				  double tf = termTweetFrequency.get(arrayofWordsinTweetText[i]);
				  double idf = sizeofCorpus/termCorpusFrequency.get(arrayofWordsinTweetText[i]);
				  idf = Math.log(idf)/Math.log(10);
				  double tf_idf = tf*idf;
				  System.out.println(arrayofWordsinTweetText[i]+"::"+indexofTokeninCorpus);
				  tokenTF_IDFstore.put(indexofTokeninCorpus, tf_idf);    
			  }
			  Iterator it = tokenTF_IDFstore.entrySet().iterator();
			  while(it.hasNext())
			  {
				  Map.Entry pairs = (Map.Entry) it.next();
				  bw.write(pairs.getKey()+":"+pairs.getValue()+" ");
				  
			  }
			  bw.newLine();
		  }
		  bw.close();
		  br.close();
		  
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
	}

}
