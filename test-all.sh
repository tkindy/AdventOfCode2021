#!/bin/sh

for dir in $(ls | grep day); do
  cd $dir
  echo "Testing $dir"

  clojure -X:test

  cd ..
done
