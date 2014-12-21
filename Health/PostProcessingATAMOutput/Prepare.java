package PostProcessingATAMOutput;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class Prepare {
	
	public static void main(String [] args)
	{
		try{	
		  FileInputStream fstream    = new FileInputStream
				  (args[0]);
		  DataInputStream in       = new DataInputStream(fstream);
		  BufferedReader br        = new BufferedReader(new InputStreamReader(in));
		  String line;
		  
		  String fileForKeyValue = "../healthfileforvisualization";
		  File fileKeyValue = new File(fileForKeyValue);
		  fileKeyValue.createNewFile();
		  BufferedWriter keyValueWriter = new BufferedWriter(new FileWriter(fileKeyValue,true));
		  
		  while((line = br.readLine())!=null)
		  {
			  String [] lineArray = line.split("\t");
			  String lineTobeWritten = "";
			  for(int i = 0 ; i < 11 ; i++)
			  {
				  
				  lineTobeWritten = lineTobeWritten.concat(lineArray[i]);
				  lineTobeWritten = lineTobeWritten.concat("\t");
			  }
			  lineTobeWritten = lineTobeWritten.concat(line.substring(line.lastIndexOf("\t")+1));
			  keyValueWriter.write(lineTobeWritten+"\n");
		  }
		 keyValueWriter.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
