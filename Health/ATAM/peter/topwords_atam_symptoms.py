#!/usr/bin/python

import sys
from operator import itemgetter

def main(): 
	filename = sys.argv[1]
	infile = file(filename, "r")

	Y = 2
	Z = 0
	A = 0
	count = {}
	countA = {}
	countA0 = {}
	countB = {}
	countBY = {}
	countY = {}

	for line in infile:
		tokens = line.split()
		id = int(tokens.pop(0))
		a = int(tokens.pop(0))

		if a not in countA:
			countA0[a] = 0
		countA0[a] += 1

		if a > A: A = a

		for token in tokens:
			parts = token.split(":")
			b = int(parts.pop())
			x = int(parts.pop())
			l = int(parts.pop())
			y = int(parts.pop())
			z = int(parts.pop())
			word = ":".join(parts)

			if b == 0:
				if l == 1 and x == 1:
					if y not in countBY:
						countBY[y] = {}
					if word not in countBY[y]:
						countBY[y][word] = 0
					countBY[y][word] += 1
				else:
					if word not in countB:
						countB[word] = 0
					countB[word] += 1
			else:
				if y > Y: Y = y
				if z > Z: Z = z

				if l == 0: x = 0

				if l == 0:
					if z not in count:
						count[z] = {}
					if word not in count[z]:
						count[z][word] = 0
					count[z][word] += 1
				else:
					if x == 0:	
						if a not in countA:
							countA[a] = {}
						if word not in countA[a]:
							countA[a][word] = 0
						countA[a][word] += 1
					else:
						if y not in countY:
							countY[y] = {}
						if a not in countY[y]:
							countY[y][a] = {}
						if word not in countY[y][a]:
							countY[y][a][word] = 0
						countY[y][a][word] += 1

	infile.close()

	#Z += 1
	A += 1


	for z in range(0,A):
		if z == -1: print "Background"
		else: 
			if z not in countA0: continue
			print "Ailment %d (%d)" % (z+1,countA0[z])


		y = 0
		print '*Symptoms'
		w = 0
		if (y not in countY or z not in countY[y]):
			words = {}
		else:
			words = sorted(countY[y][z].items(), key=itemgetter(1), reverse=True)
		for word, v in words:
			print "  "+word+" "+str(v) 
			w += 1
			if w >= 20: break


		print "\n"

if __name__ ==  "__main__":
  main()
