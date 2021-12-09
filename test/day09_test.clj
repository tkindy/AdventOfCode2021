(ns day09-test
  (:require [day09 :as d]
            [clojure.test :refer [deftest is]]))

(def example-input (slurp "examples/day09.txt"))
(def example [[2 1 9 9 9 4 3 2 1 0]
              [3 9 8 7 8 9 4 9 2 1]
              [9 8 5 6 7 8 9 8 9 2]
              [8 7 6 7 8 9 6 7 8 9]
              [9 8 9 9 9 6 5 6 7 8]])

(deftest parse-input
  (is (= (d/parse-input example-input) example)))

(deftest low-points
  (is (= (d/low-points example) #{[1 0] [9 0] [2 2] [6 4]})))

(deftest part1
  (is (= (d/part1 example) 15)))

(deftest part2
  (is (= (d/part2 example) 1134)))
