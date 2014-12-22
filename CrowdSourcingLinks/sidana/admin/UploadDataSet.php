<?php

require_once("../dao/DataSetDAO.php");
/**
 * Created by PhpStorm.
 * User: dhrumilu
 * Date: 10/9/2014
 * Time: 10:36 PM
 */

$dataSetFilePath = $_GET['dataSetFilePath'];
$dataSetName = $_GET['dataSetName'];
$file = fopen($dataSetFilePath,"r");
$datasetDao = new DataSetDAO();
echo "UPLOADING..<BR/>";
$rowCount = 0;

while(! feof($file)) {
    $tweet = fgets($file);
    $tweetParts = explode("\t",$tweet);

    $tweetId = $tweetParts[0];
    $tweetText = $tweetParts[1];

    $datasetDao->insertTweetToDataSet($dataSetName, $tweetId, $tweetText);
    $rowCount ++;
}
echo "DONE<BR/>";
echo "UPLOADED ".$rowCount." tweets";
fclose($file);