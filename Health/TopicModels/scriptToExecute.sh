#!/bin/bash
javac -cp TopicModels/stanford-corenlp-3.3.1.jar:TopicModels/stanford-corenlp-3.5.0.jar:TopicModels/stanford-corenlp-3.2.0-models.jar:.:  TopicModels/CreateATAMInputStopWordsUnleashedWithLowerCaseIssue.java TopicModels/StanfordLemmatizer.java
 java -cp ./:./TopicModels/:TopicModels/stanford-corenlp-3.3.1.jar:TopicModels/stanford-corenlp-3.5.0.jar:TopicModels/stanford-postagger-full-2014-01-04/:TopicModels/stanford-corenlp-3.2.0-models.jar TopicModels.CreateATAMInputStopWordsUnleashedWithLowerCaseIssue ../stopwords.txt ../DataSets/ ../HealthFinalVersion
