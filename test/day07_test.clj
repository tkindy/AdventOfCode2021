(ns day07-test
  (:require [day07 :as d]
            [clojure.test :refer [deftest is]]))

(def example-input "16,1,2,0,4,2,7,1,2,14\n")
(def example {16 1, 1 2, 2 3, 0 1, 4 1, 7 1, 14 1})

(deftest parse-input
  (is (= (d/parse-input example-input) example)))

(deftest fuel-cost
  (is (= (d/fuel-cost example 1) 41))
  (is (= (d/fuel-cost example 2) 37))
  (is (= (d/fuel-cost example 3) 39))
  (is (= (d/fuel-cost example 10) 71)))

(deftest best-position
  (is (= (d/best-position example) 2)))

(deftest part1
  (is (= (d/part1 example) 37)))
