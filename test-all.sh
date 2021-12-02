#!/bin/sh

for dir in $(ls | grep day); do
  pushd -q $dir
  echo "Testing $dir"

  clojure -X:test

  popd -q
done
