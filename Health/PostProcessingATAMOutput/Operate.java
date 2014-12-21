package PostProcessingATAMOutput;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Operate {
	
	public static void main(String [] args)
	{
		try{
			
			

			Map<String,Map<String,Integer>> diseasesWithSymptoms = new HashMap<String,Map<String,Integer>>();
			FileInputStream fstream  = new FileInputStream
					(args[0]);
			DataInputStream in       = new DataInputStream(fstream);
			BufferedReader br        = new BufferedReader(new InputStreamReader(in));
			String line;
			int numberofDiseases = 0;
			Map<String,Integer>numberofDiseasesContainingThisSymptom = new HashMap<String, Integer>();
			//Used for IDF.
			
			while((line = br.readLine())!=null){
				numberofDiseases++;
				int diseaseIndex = line.indexOf(";");
				String disease = line.substring(0,diseaseIndex);
				String []listofSymptoms = line.substring(diseaseIndex+2).split("\t");
				Map<String,Integer> symptoms = new HashMap<String,Integer>();
				
				for(int i = 0 ; i < listofSymptoms.length ; i++)
				{
					String symptom = listofSymptoms[i].substring(0,listofSymptoms[i].indexOf(":"));
					
					if(numberofDiseasesContainingThisSymptom.containsKey(symptom))
					{
							int numberofDiseasesContainingThisSymptomtillNow = 
									numberofDiseasesContainingThisSymptom.get(symptom.toLowerCase());
							numberofDiseasesContainingThisSymptomtillNow = 
									numberofDiseasesContainingThisSymptomtillNow + 1;
							numberofDiseasesContainingThisSymptom.put
							(symptom.toLowerCase(), numberofDiseasesContainingThisSymptomtillNow);
						
					}
					else
					{
						numberofDiseasesContainingThisSymptom.put(symptom.toLowerCase(), 1);
					}
					String symptomName = listofSymptoms[i].substring(0, listofSymptoms[i].indexOf(":"));
					int symptomValue = Integer.parseInt(listofSymptoms[i].
							substring(listofSymptoms[i].indexOf(":")+1));
					symptoms.put(symptomName.toLowerCase(), symptomValue);
				}

				diseasesWithSymptoms.put(disease.toLowerCase(), symptoms);

			}




			FileInputStream fstream11  = new FileInputStream(args[1]);
			DataInputStream in11       = new DataInputStream(fstream11);
			BufferedReader br11        = new BufferedReader(new InputStreamReader(in11));
			String line11;
			String ailment;
			String symptom;
			int ailmentNumber = 0;
			
			Map<Integer,Map<String,Double>>ailmentSymptoms = new HashMap<Integer,Map<String,Double>>();
			double numberofTimesAilmentOccured = 0;
			double numberofTimesthatWordWasAssignedtoThatParticularAilment = 0;
			while((line11 = br11.readLine()) != null)
			{
				System.out.println("line : "+ line11);
				if(line11.contains("Ailment "))
				{
					int indexofFirstSpace = line11.indexOf(" ");
					int indexofSecondSpace = line11.indexOf(" ",indexofFirstSpace+1);
					ailment = line11.substring(indexofFirstSpace+1, indexofSecondSpace);
					ailmentNumber  = Integer.parseInt(ailment);
					String overHead = line11.substring(indexofSecondSpace+1);
					numberofTimesAilmentOccured = Double.parseDouble
							(overHead.substring(1, overHead.length()-1));
					
				}
				else if(line11.equals(""))
				{
					continue;
				}
				else if(line11.equals("*Symptoms"))
				{
					continue;
				}
				else
				{
					int indexofTwoSpaces = line11.indexOf("  ");
					int indexofThirdSpace = line11.indexOf(" ",indexofTwoSpaces+2);
					symptom = line11.substring(indexofTwoSpaces+2, indexofThirdSpace);
					Map<String,Double>Symptoms = null;
					if(ailmentSymptoms.containsKey(ailmentNumber))
					{
						Symptoms = ailmentSymptoms.get(ailmentNumber);
					}

					else
					{
						Symptoms = new HashMap<String,Double>();
					}
					numberofTimesthatWordWasAssignedtoThatParticularAilment = Integer.parseInt
							(line11.substring(indexofThirdSpace+1));

					Symptoms.put(symptom.toLowerCase(),
							numberofTimesthatWordWasAssignedtoThatParticularAilment
							/numberofTimesAilmentOccured);
					ailmentSymptoms.put(ailmentNumber, Symptoms);
				}
			}
			
			
			///Computation of TF-IDF STARTS
			Map<Integer,String>ailmentNumberToNameMap = new HashMap<Integer, String>();
			
			Iterator it = ailmentSymptoms.entrySet().iterator();
			
			  String fileForKeyValue = "../" +
			  		"AilmentAssignmentstoOuputofATAMUsingLemmatizerNormalizedTF";
			  File fileKeyValue = new File(fileForKeyValue);
			  fileKeyValue.createNewFile();
			  BufferedWriter keyValueWriter = new BufferedWriter(new FileWriter(fileKeyValue,true));
			  
			while(it.hasNext())
			{
				Map.Entry pairs = (Map.Entry)it.next();
				ailmentNumber = (int)pairs.getKey();
				HashMap<String,Double>Symptoms = (HashMap<String,Double>)pairs.getValue();
				Iterator parentFileIterator = diseasesWithSymptoms.entrySet().iterator();
				double maxTfIdf = 0 ;
				String ailmentForWhichThisHappens = "";
				while(parentFileIterator.hasNext())
				{
					double totalTfIdf = 0;
					Map.Entry parentFilePairs = (Map.Entry)parentFileIterator.next();
					String ailmentName1 = (String) parentFilePairs.getKey();
					Map<String,Integer> symptomList = (Map<String, Integer>) parentFilePairs.getValue();
					Iterator itSymptoms = Symptoms.entrySet().iterator();
					while(itSymptoms.hasNext())
					{
						Map.Entry pairsSymptoms = (Map.Entry)itSymptoms.next();
						String symptom1 = (String) pairsSymptoms.getKey();
						double weight = (double) pairsSymptoms.getValue();
						double idf = 0;
						if(numberofDiseasesContainingThisSymptom.containsKey(symptom1.toLowerCase()))
						{
						 idf = numberofDiseasesContainingThisSymptom.get(symptom1.toLowerCase());
							idf = Math.log(numberofDiseases/idf)/Math.log(10);
						}
						double tf = 0;

						
						if(symptomList.containsKey(symptom1.toLowerCase()))
						{
							tf = symptomList.get(symptom1.toLowerCase());
							tf = tf/symptomList.size();
						}
						totalTfIdf = totalTfIdf + tf*idf * weight;
						
					}
					if(totalTfIdf > maxTfIdf)
					{
						ailmentForWhichThisHappens = ailmentName1;
						maxTfIdf = totalTfIdf;
					}
						
					
					
				}
				System.out.println(ailmentNumber +"::"+ailmentForWhichThisHappens);

				  keyValueWriter.write(ailmentNumber+";;"+ailmentForWhichThisHappens+"\n");
			}		
			keyValueWriter.close();

		}	
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
