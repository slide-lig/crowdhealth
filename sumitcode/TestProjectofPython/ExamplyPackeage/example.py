'''
Created on Sep 21, 2014

@author: andreas
'''
from __future__ import division

import sys
import re
import enchant
d = enchant.Dict("en_US")
fileTobeWritten=open('/home/andreas/Nutrition Project/Datasets/Weight0.9','w')
#a = d.check("peculiar")
with open("/home/andreas/Nutrition Project/Datasets/1mbchunk.tsv") as f:
    for line in f:
        lasttab = line.rfind("\t")
        tweettext =  line[lasttab:]
        listofwordsintweettext=tweettext.split(" ")
        listofTotalWords = 0
        listofValidEnglishWords = 0
        for word in listofwordsintweettext:
            listofTotalWords = listofTotalWords + 1
            if re.match("^[A-Za-z]*$", word):
                if word:
                    a=d.check(word)
                    #print word + ":"+ str(a)
                    listofValidEnglishWords = listofValidEnglishWords + 1;
                    
        #print listofValidEnglishWords;
        #print listofTotalWords;
        #print listofValidEnglishWords/listofTotalWords;
        probabilityOfaTweetTexttobeinEnglishLanguage = listofValidEnglishWords/listofTotalWords;
        if probabilityOfaTweetTexttobeinEnglishLanguage > 0.9:
            print tweettext
            fileTobeWritten.write(line)
fileTobeWritten.close()
            
        
        
                
