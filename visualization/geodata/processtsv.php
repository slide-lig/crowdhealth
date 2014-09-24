<?
if (isset($_POST['submit'])) {
    if (is_uploaded_file($_FILES['filetsv']['tmp_name'])) {
        echo "<h1>" . "File ". $_FILES['filetsv']['name'] ."uploaded successfully." . "</h1>";
        echo "<h2>Displaying contents:</h2>";
    }
    $filename = $_FILES['filetsv']['tmp_name'];
    //filter strange character
    $filteredChar='/[<>()!#$%\^&=+~`*"\'¡¤¢£¥¦§¨©ª«¬­®\.¯°±²³´µ¶·,:?|{¹º»¼½¾¿×÷„ÉàÇúÓêå;]/';
    $file = fopen($filename, "r");
    //create array variable
    $resloc=array();
    $tres=array();
    $comp=array();
    
    if ($file !== FALSE) {
        $i=0;
        while (!feof($file)) {
	
            $data = fgets($file);
            $nparse = explode("\t", $data, 6);
            if($nparse[4] != null && $nparse[5] != null){
            //change to the lower case
            $write[$i]=strtolower($nparse[4]);
            $tweetw[$i]=strtolower($nparse[5]);
            $writefil[$i] = preg_replace($filteredChar," ", $write[$i]);
            $tweetwfil[$i] = preg_replace($filteredChar," ", $tweetw[$i]);
            $compw[$i]= $writefil[$i]." # ".$tweetwfil[$i];

            }
            $i++;
        }
        
        $resloc=$writefil;
        $tres=$tweetwfil;
        $comp=$compw;
        
        //sort result
        asort($resloc);
        //write json file
        $wfile = 'data/csv/autoGenGeoLocation.csv';
        //print with pretty println
        if(file_put_contents($wfile,implode("\n",$resloc)))
       
            echo "Preprocessing succeeded";
        else
            echo "Preprocessing failed";
        
        
        $wtweet = 'data/csv/autoGenTweet.csv';
        if(file_put_contents($wtweet,implode("\n",$tres)))
            
            echo "Preprocessing tweet data succeeded";
        else
            echo "Preprocessing failed";
        
        //compilation geolocation and tweet
        
        $wcomp = 'data/csv/autoGenCompilation.csv';
        if(file_put_contents($wcomp,implode("\n",$comp)))
            
            echo "Preprocessing compilation data succeeded";
        else
            echo "Preprocessing failed";

        
        fclose($file) or die("can not write file");

        }
    
    
   
    }
?>
