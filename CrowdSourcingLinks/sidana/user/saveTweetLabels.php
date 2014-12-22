<?php
/**
 * Created by PhpStorm.
 * User: dhrumilu
 * Date: 10/10/2014
 * Time: 4:24 AM
 */

require_once("../dao/DataSetDAO.php");

//var_dump($_POST);
//echo "\n";
if(array_key_exists('label',$_POST) && array_key_exists('uniqueTweetId',$_POST)
    && array_key_exists('tweetId',$_POST)&& array_key_exists('labelId',$_POST)) {

    $label = $_POST['label'];
    $uniqueTweetId= $_POST['uniqueTweetId'];
    $tweetId= $_POST['tweetId'];
    $labelId= $_POST['labelId'];

    $dataSetDao = new DataSetDAO();
    //echo "firingQuery\n";
    $dataSetDao->upsetInTweetLabels($label,$uniqueTweetId,$tweetId,$labelId);
    //echo "done\n";
}
//echo("returning\n");
echo(json_encode(array("status" => "done")));