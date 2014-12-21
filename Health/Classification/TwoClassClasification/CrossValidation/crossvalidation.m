% How to do cross validation using libsvm - http://www.csie.ntu.edu.tw/~cjlin/libsvmtools/eval/


[label_vector,instance_matrix] = libsvmread('/media/windows/TwoClassClassification/TrainingDataHealth/InputFile')
do_binary_cross_validation(label_vector, instance_matrix, '-s 1 -t 0  -n .7', 10);


%After running above two lines we get precision = 87.7159% and recall of 24.3996
%There is still scope of improvement as original authors achieve 90.4% precision and recall of 24.3996%
