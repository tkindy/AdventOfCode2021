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

(deftest fill-basin
  (is (= (d/fill-basin example [1 0])
         #{[0 0] [1 0] [0 1]}))
  (is (= (d/fill-basin example [9 0])
         #{[9 0] [8 0] [7 0] [6 0] [5 0]
           [6 1] [8 1] [9 1] [9 2]}))
  (is (= (d/fill-basin example [2 2])
         #{[2 1] [3 1] [4 1] [1 2] [2 2]
           [3 2] [4 2] [5 2] [0 3] [1 3]
           [2 3] [3 3] [4 3] [1 4]}))
  (is (= (d/fill-basin example [6 4])
         #{[7 2] [6 3] [7 3] [8 3] [5 4]
           [6 4] [7 4] [8 4] [9 4]})))

(deftest part2
  (is (= (d/part2 example) 1134)))
