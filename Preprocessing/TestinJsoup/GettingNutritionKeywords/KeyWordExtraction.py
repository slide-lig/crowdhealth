'''
Created on Sep 25, 2014

@author: sumit
'''


from BeautifulSoup import BeautifulStoneSoup
from BeautifulSoup import BeautifulSoup
import urllib2

page = urllib2.urlopen("http://www.enchantedlearning.com/wordlist/food.shtml")
soup = BeautifulSoup(page)
file = open("/media/windows/NutrientList/fooditemskeywords",'w')
table = soup.findAll('table', width="100%", border="1", cellpadding="2", cellspacing="0")
for tags in table:
    tdata = tags.findAll('td')
    for tabledata in tdata:
        f = tabledata.findAll('font',size = "+0")
        for items in f:
            for item in items.contents:
                print item.string
                if item.string != '\n' or item.string != None:
                    file.write(str(item.string)+"\n")
                

#             print items
#             print len(items)
#             print items.text
#             for i in range( 1 , len(items)):
#                 print items(i)

