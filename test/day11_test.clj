(ns day11-test
  (:require [day11 :as d]
            [clojure.test :refer [deftest is]]))

(def example-input (slurp "examples/day11.txt"))
(def example [[5 4 8 3 1 4 3 2 2 3]
              [2 7 4 5 8 5 4 7 1 1]
              [5 2 6 4 5 5 6 1 7 3]
              [6 1 4 1 3 3 6 1 4 6]
              [6 3 5 7 3 8 5 4 7 8]
              [4 1 6 7 5 2 4 6 4 5]
              [2 1 7 6 8 4 1 7 2 1]
              [6 8 8 2 8 8 1 1 3 4]
              [4 8 4 6 8 4 8 5 5 4]
              [5 2 8 3 7 5 1 5 2 6]])

(deftest parse-input
  (is (= (d/parse-input example-input) example)))

(deftest inc-all
  (is (= (d/inc-all [[1 2 3]
                     [3 4 8]
                     [9 1 4]])
         [[2 3 4]
          [4 5 9]
          [10 2 5]])))

(deftest neighbors
  (is (= (d/neighbors [1 2] [[8 8 8]
                             [8 8 8]
                             [8 8 8]])
         '([0 1] [1 1] [2 1] [0 2] [2 2]))))

(deftest count-zeroes
  (is (= (d/count-zeroes example) 0))
  (is (= (d/count-zeroes [[1 2 0 3 4]
                          [0 2 1 3 0]
                          [2 0 3 1 0]])
         5)))

(deftest part1
  (is (= (d/part1 example) 1656)))

(deftest part2
  (is (= (d/part2 example) 195)))
