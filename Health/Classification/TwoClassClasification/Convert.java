package ConvertIntoHealthandNonHealth;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.*;

public class Convert {
	public static void main(String [] args)
	{
		try{
			FileInputStream fstream    = new FileInputStream
					("/media/windows/TwoClassClassification/TrainingDataHealth/annotated_tweets.txt");
			DataInputStream in       = new DataInputStream(fstream);
			BufferedReader br        = new BufferedReader(new InputStreamReader(in));
			String line;
			String fileForKeyValue = "/media/windows/TwoClassClassification/TrainingDataHealth" +
					"/healthandnonhealth";
			File fileKeyValue = new File(fileForKeyValue);
			fileKeyValue.createNewFile();
			BufferedWriter keyValueWriter = new BufferedWriter(new FileWriter(fileKeyValue,true));
			int labelToBeWriten = 0;
			while((line = br.readLine())!=null)
			{
				String [] strLine = line.split(" ");
				String tweetId = strLine[0];
				String label = strLine[1];
//				String tweetText = strLine[2];
				int indexofFirstSpace = line.indexOf(" ");
				int indexofSecondSpace = line.indexOf(" ",indexofFirstSpace+1);
				String tweetText = line.substring(indexofSecondSpace+1);
				if(label.equalsIgnoreCase("notsure"))
						continue;																			
				else if(label.equalsIgnoreCase("notenglish"))
					labelToBeWriten = -1;
				else if(label.equalsIgnoreCase("no"))
					labelToBeWriten = -1;
				else if(label.equalsIgnoreCase("sick"))
					labelToBeWriten = 1;
				else if(label.equalsIgnoreCase("health"))
					labelToBeWriten = 1;
					
				keyValueWriter.write(tweetId+"\t"+labelToBeWriten+"\t"+tweetText+"\n");
			}
			keyValueWriter.close();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
