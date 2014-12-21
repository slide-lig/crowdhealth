package Classification;

import java.io.*;
import java.util.*;

public class Extract {
	
	public static void main(String [] args)
	{ 
		try{
			
		
		  FileInputStream fstream1    = new FileInputStream(args[0]);
		  DataInputStream in1       = new DataInputStream(fstream1);
		  BufferedReader br1        = new BufferedReader(new InputStreamReader(in1));
		  FileInputStream fstream2    = new FileInputStream(args[1]);
		  DataInputStream in2       = new DataInputStream(fstream2);
		  BufferedReader br2        = new BufferedReader(new InputStreamReader(in2));
		  String strLine1,strLine2;
			System.out.println(System.getProperty("user.dir"));
		  String file_temp = "../HealthFinalVersion";
		  File file = new File(file_temp);
		  file.createNewFile();
		  BufferedWriter output = new BufferedWriter(new FileWriter(file,true));
		  int iterator = 0;
		  List<Integer> labels = new ArrayList<Integer> ();
		  while((strLine2 = br2.readLine())!=null)
		  {
			  labels.add(Integer.parseInt(strLine2));
		  }
		  //System.out.println(labels.size());
		  while((strLine1 = br1.readLine())!=null)
		  {	
			  //if(iterator == 2841536)
			  //System.out.println(strLine1);
			  if((labels.get(iterator)) == 1)
			  {
				  output.write(strLine1+"\n");
			  }
			  iterator++;
		  }
		  output.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
