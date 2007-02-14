#! /bin/bash
for f in *.png
do
  echo "converting $f to format eps"
  convert $f `basename $f .png`.eps
done