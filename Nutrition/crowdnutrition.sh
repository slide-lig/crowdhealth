#!/bin/bash

# select * from tweet join twitter_user on tweet.user_id = twitter_user.user_id where twitter_user.lang_id = 2 limit k;
# Execute the above query to retrieve k tweets from the join of tweet table and twitter_user tables and language id set to English.
# After executing the query, keep the file generated in your <git folder/> and rename it as StartFile
# For me it is /home/sumit/crowdhealth/StartFile.
echo "Preprocessing..."

javac -cp ../binaries/commons-validator-1.4.0/commons-validator-1.4.0.jar Preprocessing/RemoveURLfromTweets.java
echo "Removing url's and RT Tags..."
java -cp ../binaries/commons-validator-1.4.0/commons-validator-1.4.0.jar:. Preprocessing.RemoveURLfromTweets ../StartFile

javac   Preprocessing/DetectUserNameHashTag.java
echo "Removing Usernames and Hashtags Tags..."
java -cp . Preprocessing.DetectUserNameHashTag ../TweetsWithoutURLandRTTag

echo "Filtering on Nutrition KeyWords..."
javac Preprocessing/FilteronFirstFoodList.java
java -cp . Preprocessing.FilteronFirstFoodList ../TweetsTobeFiltered ../DataSetsForFoodItems/

echo "Preprocessing Done!"

paste -d'\t' ../FirstRow ../Food > ../FirstRowWithSideAttachment
cat ../FirstRowWithSideAttachment ../FilteredTweetsWithFoodItemsLowerCase > ../FilteredTweetsWithFoodItemsLowerCaseWithHeaderRow
echo 'Generating Input File for Visualization Module'
javac PostProcessing/Prepare.java	 
java -cp . PostProcessing.Prepare ../FilteredTweetsWithFoodItemsLowerCaseWithHeaderRow
