'''
Created on Sep 26, 2014

@author: sumit
'''
f = open('/media/Sumit/fileWithStemming')

lines = [line for line in f if line.strip()]
f.close()
lines.sort()
fileToWrite = open('/media/Sumit/sortedfileWithStemming','w')
for line in lines:
    fileToWrite.write(str(line)+"\n")
fileToWrite.close()
    