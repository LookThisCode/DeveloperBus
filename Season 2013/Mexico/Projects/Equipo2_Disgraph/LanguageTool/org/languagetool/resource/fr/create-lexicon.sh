#!/bin/sh
#
# How to create the POS tag 'french.dict' dictionary:
#
# 1) Download morfologik-stemming-1.4.0.zip from
#    http://sourceforge.net/projects/morfologik/files/morfologik-stemming/1.4.0/
#    $ unzip morfologik-stemming-1.4.0.zip
#    This creates morfologik-stemming-nodict-1.4.0.jar
# 2) Run the script:
#    $ ./create-lexicon.sh
#    This creates the dictionary 'french.dict'.
#
# Author: Dominique Pelle <dominique.pelle@gmail.com>
#

INPUT=lexique-dicollecte-fr-v4.12

if [ ! -f $INPUT.txt ]; then
  wget http://www.dicollecte.org/download/fr/$INPUT.zip
  unzip $INPUT.zip
fi

# The python script DL2LT.py is included in $INPUT.zip
chmod a+x DL2LT.py
vim  -c 'set fileformat=unix' -c 1s/python3.1/python3 -c wq DL2LT.py
./DL2LT.py $INPUT.txt

java -jar morfologik-stemming-nodict-1.4.0.jar tab2morph \
     -i $INPUT.txt.LT.txt \
     -o output.txt
java -jar morfologik-stemming-nodict-1.4.0.jar fsa_build \
     -i output.txt \
     -o french.dict
