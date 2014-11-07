package CSVToARFF;

import java.io.*;
import java.util.Arrays;
public class ProperFormatting {

	/**
	 * @param args
	 */
	static int l=0;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try{
			
		
		String pathToInputFiles = "/home/sumit/Desktop/TrainingForNutrition/CrossValidation/RelatedUnrelated/Temp/";
		File dir = new File(pathToInputFiles);
		String[]child = dir.list();
		Arrays.sort(child);
		for(int i = 0 ; i < child.length ; i++)
		{
			BufferedReader reader = new BufferedReader(new FileReader(pathToInputFiles+child[i]));
			String line = null;
			String file_temp = "/media/windows/NutritionTrainingFileforWeka/outputforspoiledfiles/output_"+l++ + child[i];
			File file = new File(file_temp);
			file.createNewFile();
			BufferedWriter output = new BufferedWriter(new FileWriter(file,true));
				while ((line = reader.readLine()) != null) {	
					System.out.println(line);
					int firstOccurenceofSemicolon = 0;
					int secondOccurenceofSemicolon = 0;
					int thirdOccurenceofSemicolon = 0;
					String textenclosedbyInvertedCommas = null;
					String text = null;
					String restoftheLine = null;
					firstOccurenceofSemicolon = line.indexOf(";");	
					int variablefirstOccurenceofSemicolon = line.indexOf(";");
						while(true)
						{
						 
						 secondOccurenceofSemicolon = line.indexOf(";", variablefirstOccurenceofSemicolon+1);
						 thirdOccurenceofSemicolon = line.indexOf(";", secondOccurenceofSemicolon+1);
						 if((line.substring(secondOccurenceofSemicolon+1, thirdOccurenceofSemicolon)).equals("test_nutrition"))
						 {
							 break;
						 }
						 //int temp = firstOccurenceofSemicolon;
						 variablefirstOccurenceofSemicolon = secondOccurenceofSemicolon;
						
						}
						 textenclosedbyInvertedCommas = line.substring(firstOccurenceofSemicolon+1,secondOccurenceofSemicolon);
						 text = "\""+textenclosedbyInvertedCommas+"\"";
						 restoftheLine = line.substring(0,firstOccurenceofSemicolon+1)+text+line.substring(secondOccurenceofSemicolon)+"\n";
						
						output.write(restoftheLine);
						
	
			
			    // ...
			}
				output.close();
			
		
		


	}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
