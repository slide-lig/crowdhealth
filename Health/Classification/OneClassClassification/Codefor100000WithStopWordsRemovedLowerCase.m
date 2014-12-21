disp('Starting Training for 100000 Tweets')
[TrainingLabels,InstanceMatrix] = libsvmread('/home/slide/sidana/OneClassClassificationStopWordsRemovedLowerCase/TrainFile')

 modelsvm = svmtrain(TrainingLabels, InstanceMatrix,'-s 2 -t 0 -n 0.1')
 disp('Training Finished')

 disp('Testing Started')

 disp('Reading Test File')
[TestLabels,TestSet] = libsvmread('/home/slide/sidana/OneClassClassificationStopWordsRemovedLowerCase/InputFile')
 disp('Completed Reading Test File')
[predicted_label,accuracy,decisionValue] = svmpredict(TestLabels,TestSet,modelsvm)
disp('Testing Finished and Labels Predicted');
disp('Writing Predicted Labels')
dlmwrite('/home/slide/sidana/OneClassClassificationStopWordsRemovedLowerCase/predictedlabelsOptimizedParameter',predicted_label)
disp('Writing the model')
