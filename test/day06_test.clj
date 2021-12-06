(ns day06-test
  (:require [day06 :as d]
            [clojure.test :refer [deftest is]]))

(def example-input "3,4,3,1,2")
(def example-parsed [3 4 3 1 2])

(deftest parse-input
  (is (= (d/parse-input example-input) example-parsed)))

(deftest simulate
  (is (= (d/simulate example-parsed 0) example-parsed))
  (is (= (d/simulate example-parsed 1) [2 3 2 0 1]))
  (is (= (d/simulate example-parsed 2) [1 2 1 6 0 8]))
  (is (= (d/simulate example-parsed 3) [0 1 0 5 6 7 8]))
  (is (= (d/simulate example-parsed 18) [6 0 6 4 5 6 0 1 1 2 6 0 1 1 1 2 2 3 3 4 6 7 8 8 8 8])))

(deftest part1
  (is (= (d/part1 example-parsed) 5934)))
