(ns day03-test
  (:require day03
            [clojure.test :refer [deftest is]]))

(def example-input "00100\n11110\n10110\n10111\n10101\n01111\n00111\n11100\n10000\n11001\n00010\n01010\n")

(deftest parse-input
  (is (= (day03/parse-input example-input)
         [[0 0 1 0 0]
          [1 1 1 1 0]
          [1 0 1 1 0]
          [1 0 1 1 1]
          [1 0 1 0 1]
          [0 1 1 1 1]
          [0 0 1 1 1]
          [1 1 1 0 0]
          [1 0 0 0 0]
          [1 1 0 0 1]
          [0 0 0 1 0]
          [0 1 0 1 0]])))
