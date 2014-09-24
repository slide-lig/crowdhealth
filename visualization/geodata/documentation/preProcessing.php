<?
$tmp_name= $_FILES['filepath']['temp_name'];
$name=$_POST['filepath']['name'];
echo $tmp_name;
$path="data/csv/csvExample.csv";
/*if ($_FILES['filepath']['error'] > 0) {
  	echo "Error: " . $_FILES['filepath']['error'] . "<br />"; 
}else{ */
	if (($fp = fopen($path, "r")) !== FALSE) {
			$i = 0;
			while($csv_line = fgetcsv($fp,1024)) {
    
    			$json[$i]['gender'] = $csv_line[0];
    			$json[$i]['occupation'] = $csv_line[1];
    			$json[$i]['age'] = $csv_line[2];
    			$json[$i]['city'] = $csv_line[3];
    			$json[$i]['state'] = $csv_line[4];
    			$json[$i]['size'] = $csv_line[5]; 
				$i++;       
			}
	print json_encode($json);
	fclose($fp) or die("**! can't close file\n\n");
	//}
}	
?>
