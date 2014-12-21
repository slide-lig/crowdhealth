package PostProcessingATAMOutput;

import java.io.*;
import java.util.*;

public class OutputofATAMonHealthTweets {
	
	public static void main(String [] args)
	{
		try{
			  FileInputStream fstream    = new FileInputStream
					  (args[0]);
			  DataInputStream in       = new DataInputStream(fstream);
			  BufferedReader br        = new BufferedReader(new InputStreamReader(in));
			  String line;
			  Map<Integer,String> ailmentNumberToAilmentNameMap = new HashMap<Integer,String>();
			  while((line = br.readLine())!=null)
			  {
				  Integer ailmentNumber = Integer.parseInt(line.substring(0, line.indexOf(";;")));
				  String ailmentName = line.substring(line.indexOf(";;")+2);
				  ailmentNumberToAilmentNameMap.put(ailmentNumber-1,ailmentName);
			  }
			  
			  FileInputStream fstream11    = new FileInputStream
					  (args[1]);
			  DataInputStream in11       = new DataInputStream(fstream11);
			  BufferedReader br11        = new BufferedReader(new InputStreamReader(in11));
			  String line11;
			  List<String>ailmentNames = new ArrayList<String>();
			  
			  while((line11 = br11.readLine())!=null){
				 String ailmentName  = ailmentNumberToAilmentNameMap.get(Integer.parseInt
						  ((line11.substring(line11.indexOf(" ")+1, line11.indexOf
								  (" ", line11.indexOf(" ")+1)))));
				 if(ailmentName ==null)
				 {
					 System.out.println
					 (line11.substring(line11.indexOf(" ")+1, line11.indexOf
							 (" ", line11.indexOf(" ")+1)));
				 }
				  ailmentNames.add(ailmentNumberToAilmentNameMap.get(Integer.parseInt
						  ((line11.substring(line11.indexOf(" ")+1, line11.indexOf
								  (" ", line11.indexOf(" ")+1))))));
			  }
				
			  
			  FileInputStream fstream1    = new FileInputStream
					  (args[2]);
			  DataInputStream in1       = new DataInputStream(fstream1);
			  BufferedReader br1        = new BufferedReader(new InputStreamReader(in1));
			  String line1;
			  
			  String fileForKeyValue = "../OutputofATAMonGeoLocatedTweetsNormalizedTF";
			  File fileKeyValue = new File(fileForKeyValue);
			  fileKeyValue.createNewFile();
			  BufferedWriter keyValueWriter = new BufferedWriter(new FileWriter(fileKeyValue,true));
			  
			  int iterator = 0;
			  
			  while((line1 = br1.readLine())!=null)
			  {
				  line1 = line1.concat("\t"+ailmentNames.get(iterator));
				  iterator++;
				  keyValueWriter.write(line1+"\n");
			  }
			  keyValueWriter.close();
			  }
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
		
