

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class UnigramsBigramsTrigrams {



	public  HashMap<String,Integer> wordMap;
	public List <Integer> tweetAllWords;
	public Set <Integer> tweetUniqueWords;

	public static List<String> ngrams(int n, String str) {
		List<String> ngrams = new ArrayList<String>();
		String[] words = str.split("[\\s+\\-*/\\^:;\\[\\]\\\\()_#@$%&|<>\'\",.?{}!=`~]+");
		for (int i = 0; i < words.length - n + 1; i++)
			ngrams.add(concat(words, i, i+n));
		return ngrams;
	}

	public static String concat(String[] words, int start, int end) {
		StringBuilder sb = new StringBuilder();
		for (int i = start; i < end; 	i++)
			sb.append((i > start ? " " : "") + words[i]);
		return sb.toString();
	}

	public static void main(String [] args)
	{
		// FileInputStream fstream    = new FileInputStream("/home/slide/sidana /HealthFile/OneFileForLibSVM100000Tweets");
		try{
			FileInputStream fstream1  = new FileInputStream("/media/windows/TwoClassClassification/TrainingDataHealth/stopwords.txt");
			DataInputStream in1       = new DataInputStream(fstream1);
			BufferedReader br1        = new BufferedReader(new InputStreamReader(in1));
			String line1;	  
			Set<String>stopWords = new HashSet<String>();
			while((line1 = br1.readLine())!=null)
			{
				stopWords.add(line1.toLowerCase());
			}
			
			FileInputStream fstream   = new FileInputStream("/media/windows/TwoClassClassification/TrainingDataHealth/healthandnonhealth");

			DataInputStream in       = new DataInputStream(fstream);
			BufferedReader br        = new BufferedReader(new InputStreamReader(in));
			String line;

			//String file_temp = "/media/Sumit/InputFileforHealthdataset/InputFile";
			String file_temp = "/media/windows/TwoClassClassification/TrainingDataHealth/InputFile";
			File file = new File(file_temp);
			file.createNewFile();
			BufferedWriter output = new BufferedWriter(new FileWriter(file,true));

			UnigramsBigramsTrigrams unigramsBigramsTrigrams = new UnigramsBigramsTrigrams();
			unigramsBigramsTrigrams.wordMap = new HashMap<String,Integer>();

			String fileForKeyValue = "/media/windows/TwoClassClassification/TrainingDataHealth/KeyValuePair";
			File fileKeyValue = new File(fileForKeyValue);
			fileKeyValue.createNewFile();
			BufferedWriter keyValueWriter = new BufferedWriter(new FileWriter(fileKeyValue,true));
			int lineNumber = 0;
			while((line  = br.readLine())!=null)
			{

				lineNumber ++;

				
				int indexofActualTweet = line.lastIndexOf("\t");
				int indexoffirstTab = line.indexOf("\t");
				int indexofSecondTab = line.indexOf("\t",indexoffirstTab + 1);

				
				
				String	strLine = line.substring(indexofActualTweet+1);
				String classofTweet = line.substring(indexoffirstTab+1,indexofSecondTab);

				unigramsBigramsTrigrams.tweetAllWords = new ArrayList<Integer>();

				String [] tokens = null;
				String []temp = null;
				ArrayList<String>tempforadding = new ArrayList<String>();
				ArrayList<String>nGrams=null;

				for(int n = 1 ; n <= 3 ; n++)
				{
					nGrams = (ArrayList<String>) ngrams(n,strLine);
					temp = nGrams.toArray(new String[nGrams.size()]);
					for(int m = 0 ; m < temp.length ; m++)
					{
						tempforadding.add(temp[m]);
					}

					tokens = tempforadding.toArray(new String[tempforadding.size()]);
				}

				int N = tokens.length;


				for(int n = 0 ; n < N ; n++)
				{
					int key = unigramsBigramsTrigrams.wordMap.size();
					String word = tokens[n].toLowerCase();
					if(stopWords.contains(tokens[n].toLowerCase()))
					continue;
					//System.out.println("word: "+word);
					if(!(unigramsBigramsTrigrams.wordMap.containsKey(tokens[n].toLowerCase())))
					{
						unigramsBigramsTrigrams.wordMap.put(tokens[n].toLowerCase(), new Integer(key));
					}
					else
					{
						key = unigramsBigramsTrigrams.wordMap.get(tokens[n].toLowerCase());
					}
					unigramsBigramsTrigrams.tweetAllWords.add(key);
					keyValueWriter.write(word+":"+key+"\n");
				}

				Collections.sort(unigramsBigramsTrigrams.tweetAllWords);
				unigramsBigramsTrigrams.tweetUniqueWords = new TreeSet<Integer>();
				output.write(classofTweet+" ");
				N = unigramsBigramsTrigrams.tweetAllWords.size();	
				for(int i = 0 ; i < N ; i++)
				{
					int count = 1;
					int     key = unigramsBigramsTrigrams.tweetAllWords.get(i);

					for(int j = i+1 ; j < N ; j++)

					{
						if(unigramsBigramsTrigrams.tweetUniqueWords.contains(key))
						{
							continue;
						}

						int key1 = unigramsBigramsTrigrams.tweetAllWords.get(j);

						if(key == key1)
						{
							count++;
						}
					}

					if(!(unigramsBigramsTrigrams.tweetUniqueWords.contains(key)))
					{
						if(i<(N-1))
							output.write(key+":"+count+" ");
						else
							output.write(key+":"+count);
					}


					unigramsBigramsTrigrams.tweetUniqueWords.add(key);

				}
				output.write("\n");
			}
			System.out.println(unigramsBigramsTrigrams.wordMap.size());
			output.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}







