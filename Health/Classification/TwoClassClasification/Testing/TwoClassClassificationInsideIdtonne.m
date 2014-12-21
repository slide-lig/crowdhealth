#!/usr/bin/octave -qf
[label_vector,instance_matrix] = libsvmread('../../../TrainFile');
model = svmtrain(label_vector,instance_matrix,'-s 1 -t 0 -n .7');
[test_label_vector,test_instance_matrix] = libsvmread('../../../InputFile');
[predicted_label,accuracy,decisionValue] = svmpredict(test_label_vector,test_instance_matrix,model);
dlmwrite('../../../predictedlabels',predicted_label)
