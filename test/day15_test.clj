(ns day15-test
  (:require [day15 :as d]
            [clojure.test :refer [deftest is]]
            [clojure.data.priority-map :refer [priority-map]]))

(def example-input (slurp "examples/day15.txt"))
(def example (d/parse-input example-input))

(deftest parse-input
  (is (= example
         [[1 1 6 3 7 5 1 7 4 2]
          [1 3 8 1 3 7 3 6 7 2]
          [2 1 3 6 5 1 1 3 2 8]
          [3 6 9 4 9 3 1 5 6 9]
          [7 4 6 3 4 1 7 1 2 1]
          [1 3 1 9 1 2 8 1 3 7]
          [1 3 5 9 9 1 2 4 2 1]
          [3 1 2 5 4 2 1 6 3 9]
          [1 2 9 3 1 3 8 5 2 1]
          [2 3 1 1 9 4 4 5 8 1]])))

(deftest step
  (is (= (d/step [0 0]
                 (priority-map [1 0] Double/POSITIVE_INFINITY
                               [0 1] Double/POSITIVE_INFINITY)
                 {[0 0] {:distance 0, :through nil}}
                 example)
         [(priority-map [1 0] 1, [0 1] 1)
          {[0 0] {:distance 0, :through nil}
           [1 0] {:distance 1, :through [0 0]}
           [0 1] {:distance 1, :through [0 0]}}])))

(deftest cheapest-path
  (is (= (d/cheapest-path example)
         {:path [[0 1] [0 2] [1 2] [2 2] [3 2] [4 2]
                 [5 2] [6 2] [6 3] [7 3] [7 4] [7 5]
                 [8 5] [8 6] [8 7] [8 8] [9 8] [9 9]]
          :cost 40})))

(deftest part1
  (is (= (d/part1 example) 40)))
