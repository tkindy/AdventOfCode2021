(ns day06-test
  (:require [day06 :as d]
            [clojure.test :refer [deftest is]]))

(def example-input "3,4,3,1,2")
(def example-parsed {3 2, 4 1, 1 1, 2 1})

(deftest parse-input
  (is (= (d/parse-input example-input) example-parsed)))

(deftest simulate
  (is (= (d/simulate example-parsed 0) example-parsed))
  (is (= (d/simulate example-parsed 1) {2 2, 3 1, 0 1, 1 1}))
  (is (= (d/simulate example-parsed 2) {1 2, 2 1, 6 1, 0 1, 8 1}))
  (is (= (d/simulate example-parsed 3) {0 2, 1 1, 5 1, 6 1, 7 1, 8 1}))
  (is (= (d/simulate example-parsed 18) {0 3, 7 1, 1 5, 4 2, 6 5, 3 2, 2 3, 5 1, 8 4})))

(deftest part1
  (is (= (d/part1 example-parsed) 5934)))

(deftest part2
  (is (= (d/part2 example-parsed) 26984457539)))
