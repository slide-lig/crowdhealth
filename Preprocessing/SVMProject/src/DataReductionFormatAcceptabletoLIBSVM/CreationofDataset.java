package DataReductionFormatAcceptabletoLIBSVM;
import java.io.*;
import java.util.*;

public class CreationofDataset {
	public  HashMap<String,Integer> wordMap;
    public List <Integer> tweetAllWords;
    public Set <Integer> tweetUniqueWords;
    
	public static void main(String [] args)
	{
		try{
			  //FileInputStream fstream   = new FileInputStream("/home/andreas/Nutrition Project/Datasets/health_tweets.tsv");
			FileInputStream fstream   = new FileInputStream("/media/Sumit/InputFileforHealthdataset/TrainAndTestTweets20000");
			  DataInputStream in       = new DataInputStream(fstream);
			  BufferedReader br        = new BufferedReader(new InputStreamReader(in));
			  String line;
			  
			  //String file_temp = "/media/Sumit/InputFileforHealthdataset/InputFile";
			  String file_temp = "/media/Sumit/InputFileforHealthdataset/TrainingTweetsInLibSVMFORMAT20000";
			  File file = new File(file_temp);
			  file.createNewFile();
			  BufferedWriter output = new BufferedWriter(new FileWriter(file,true));
			  
			  CreationofDataset creationofDataset = new CreationofDataset();
			  creationofDataset.wordMap = new HashMap<String,Integer>();
			  boolean ignorefirstline = true ;
			  while((line  = br.readLine())!=null)
			  {
				  if(ignorefirstline)
				  {
					  ignorefirstline = false;
					  continue ;
				  }
				  
				  int indexofActualTweet = line.lastIndexOf("\t");
				  String strLine = line.substring(indexofActualTweet+1);
				  
				  	creationofDataset.tweetAllWords = new ArrayList<Integer>();
				  	String [] tokens = strLine.split("\\s+");
				  	int N = tokens.length;
				  	 
				  	
				  	for(int n = 0 ; n < N ; n++)
				  	{
				  		int key = creationofDataset.wordMap.size();
				  		String word = tokens[n];
				  		if(!(creationofDataset.wordMap.containsKey(tokens[n])))
				  		{
				  			creationofDataset.wordMap.put(tokens[n], new Integer(key));
				  		}
				  		else
				  		{
				  			key = creationofDataset.wordMap.get(tokens[n]);
				  		}
				  		creationofDataset.tweetAllWords.add(key);
				  	}
				  	
				  	Collections.sort(creationofDataset.tweetAllWords);
				  	creationofDataset.tweetUniqueWords = new TreeSet<Integer>();
				  	output.write(1+" ");
				  	
				  	for(int i = 0 ; i < N ; i++)
				  	{
				  	int count = 1;
				  	int	key = creationofDataset.tweetAllWords.get(i);
				  		
				  		for(int j = i+1 ; j < N ; j++)
				  		{
				  			if(creationofDataset.tweetUniqueWords.contains(key))
				  			{
				  				continue;
				  			}
				  			
				  			int key1 = creationofDataset.tweetAllWords.get(j);
				  			
				  			if(key == key1)
				  			{
				  				count++;
				  			}
				  		}
				  		
				  		if(!(creationofDataset.tweetUniqueWords.contains(key)))
				  		{	
				  		if(i<(N-1))
				  		output.write(key+":"+count+" ");
				  		else
				  		output.write(key+":"+count);
				  		}
				  		
		
				  		creationofDataset.tweetUniqueWords.add(key);
				  		
				  	}
				  	
				  	output.write("\n");
			  }
			  System.out.println(creationofDataset.wordMap.size());
			  output.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
