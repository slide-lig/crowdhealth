<?php
require_once("../dao/DataSetDAO.php");
/**
 * Created by PhpStorm.
 * User: dhrumilu
 * Date: 10/10/2014
 * Time: 1:07 AM
 */

$dataSetName = $_GET['dataSetName'];
$tweetCount = 10;
if(array_key_exists('tweetCount',$_GET)) {
    $tweetCount = $_GET['tweetCount'];
}

$dataSetDAO = new DataSetDAO();
echo json_encode($dataSetDAO->getRandomTweets($dataSetName,$tweetCount));
