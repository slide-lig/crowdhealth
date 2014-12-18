import random
import csv
import sys

if len(sys.argv)!=2:
	print "Usage:"
	print sys.argv[0]+" <path-to-file>"
	sys.exit(1)

fileName=sys.argv[1]
tweets=file(fileName,'r')
r1=csv.reader(tweets,delimiter='\t')

a=set()
b=set()
i=0
for line in r1:
	a.add(line[0])
	b.add(line[1])
	i+=1

print "Unique Tweets:", len(a)
print "Unique Users:", len(b)
print "Total Tweets:", i
