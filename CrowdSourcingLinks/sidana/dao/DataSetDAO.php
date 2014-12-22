<?php
/**
 * Created by PhpStorm.
 * User: dhrumilu
 * Date: 10/9/2014
 * Time: 10:43 PM
 */

class DataSetDAO {

    protected $dbUserName = "root";
    protected $dbPassword = "root";
    protected $hostName = "localhost";
    protected $dbName = "tweet_crowd";

    protected $mysqli;

    /**
     * constructor. connects to database.
     */
    function __construct() {

        $this->mysqli = new mysqli($this->hostName,$this->dbUserName,$this->dbPassword,$this->dbName);
        if (mysqli_connect_errno()) {
            die("Failed to connect to MySQL: " . mysqli_connect_error());
        }
    }

    /**
     * @param $dataSetName
     * @param $tweetId
     * @param $tweetText
     */
    function insertTweetToDataSet($dataSetName, $tweetId, $tweetText){
        $dataSetName = $this->mysqli->real_escape_string($dataSetName);
        $tweetId = $this->mysqli->real_escape_string($tweetId);
        $tweetText = $this->mysqli->real_escape_string(trim($tweetText));

        $query = "INSERT INTO master_tweets (DATASET_NAME, TWEET_ID, TWEET_TEXT) VALUES ('$dataSetName','$tweetId','$tweetText')";
        $this->mysqli->query($query);
    }

    function getRandomTweets($dataSetName, $count) {
        if($count > 100) {
            die("cannot supply more than 100 tweets in one call");
        }
        $dataSetName = $this->mysqli->real_escape_string($dataSetName);
        $query = "SELECT * FROM master_tweets WHERE DATASET_NAME = '$dataSetName' ORDER BY RAND() LIMIT $count";
        $rs = $this->mysqli->query($query);
        $newTweets = array();
        while($row = $rs->fetch_assoc()) {
            array_push($newTweets,array(
                "uniqueTweetId" => utf8_encode($row['unique_tweet_id']),
                "tweetId" => utf8_encode($row['tweet_id']),
                "dataSetName" => utf8_encode($row['dataset_name']),
                "tweetText" => utf8_encode($row['tweet_text'])
            ));
        }
        return $newTweets;
    }

    function upsetInTweetLabels($label,$uniqueTweetId,$tweetId,$labelId) {
        $query = "INSERT INTO tweet_labels(label_id,tweet_id,label,unique_tweet_id) ".
            "VALUES ('$labelId','$tweetId','$label','$uniqueTweetId') " .
            "ON DUPLICATE KEY UPDATE label = '$label'";
        $this->mysqli->query($query);
    }
}
