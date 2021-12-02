(ns day1-test
  (:require day1
            [clojure.test :refer [deftest is]]))

(deftest num-increases
  (is (= (day1/num-increases []) 0)))
