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

javac Preprocessing/FilteronHealthKeyword.java
echo "Filtering on Health KeyWords..."
java -cp . Preprocessing.FilteronHealthKeyword ../TweetsTobeFiltered ../DataSets/

echo "Preprocessing Done!"

# Join the clean health file and the file to be classified

echo "Joining clean Health File and Filtered Tweets..."
cat ../TrainandTestTweets100000Ngrams ../FilteredTweetsWithOptimization > ../FilteredTweetsTobeTested


echo 'Converting Input into LIBSVMFormat' 
javac  Classification/OneClassClassification/UnigramsBigramsTrigrams.java
java -cp ./Classification/  OneClassClassification.UnigramsBigramsTrigrams ../FilteredTweetsTobeTested ../stopwords.txt


echo 'Creating Training File'
head -99999 ../InputFile > ../TrainFile

echo 'Creating Test File'
sed -i '1,99999d' ../InputFile

echo 'Changing directory to where libsvm is installed'
cd libsvm-3.20/matlab/ #directory where libsvm is installed

echo  ' Running Classifier - Might Take some Time ...'
./Codefor100000WithStopWordsRemovedLowerCase.m

echo 'Changing Directory Back to Script File'
cd -

echo 'Extracting Health Tweets from Filtered Tweets on the basis of Classification'
javac ../Health/Classification/Extract.java
java -cp ../Health/ Classification.Extract ../FilteredTweetsWithOptimization ../predictedlabels

echo 'Converting into ATAM input'
sh TopicModels/scriptToExecute.sh

echo 'Changing Directory to where ATAM is'
cd ATAM/peter/

echo 'executing ATAM'
javac -cp commons-math-2.1.jar *.java
java -cp commons-math-2.1.jar:. LearnTopicModel -model atam -Z 25 -A 25 -iters 100 -input ../../../InputToATAMUsingLemmatizer > out

echo 'Changing directory back to the directory of script '
cd -
echo 'Extracting all kinds of top words  from ATAM output'
python ATAM/peter/topwords_atam.py ../InputToATAMUsingLemmatizer.assign > ../output_atam.txt

echo 'Extracting just the symptoms from ATAM output'
python ATAM/peter/topwords_atam_symptoms.py ../InputToATAMUsingLemmatizer.assign > ../output_atam_symptoms_UsingTwoClassClassifierLowerCase.txt

echo 'Assigning names to ailments'
javac PostProcessingATAMOutput/Operate.java
java -cp . PostProcessingATAMOutput.Operate ../DiseaseWithSymptomsWithoutStopWords ../output_atam_symptoms_UsingTwoClassClassifierLowerCase.txt

echo 'Assigning ailments to all health classified tweets'
javac PostProcessingATAMOutput/OutputofATAMonHealthTweets.java
java -cp . PostProcessingATAMOutput.OutputofATAMonHealthTweets ../AilmentAssignmentstoOuputofATAMUsingLemmatizerNormalizedTF ../InputToATAMUsingLemmatizer.assign ../HealthFinalVersion

echo 'Joining First Row With column ailment side by side'
paste -d'\t' ../FirstRow ../Ailments > ../FirstRowWithSideAttachment

echo 'Adding header row With Output of ATAM on Geo Located Tweets'
cat ../FirstRowWithSideAttachment ../OutputofATAMonGeoLocatedTweetsNormalizedTF > ../OutputofATAMonGeoLocatedTweetsWithHeaderRow

echo 'Generating Input File for Visualization Module'
javac PostProcessingATAMOutput/Prepare.java	 
java -cp . PostProcessingATAMOutput.Prepare ../OutputofATAMonGeoLocatedTweetsWithHeaderRow

#javac Classification/UnigramsBigramsTrigrams.java 	
#echo "Generating Input Format for libsvm One Class Classifier "
#java Classification.UnigramsBigramsTrigrams.java

# Assuming Files have been split - Tricky Part - Number of lines have to be hard coded - Where to split.

# Octave Code
# Join Predicted Labels and Filtered Tweets With Optimization.
# Extract final health tweets.
# Convert Final Health tweets to ATAM input specific format.
# Run ATAM on Input.
# ....

