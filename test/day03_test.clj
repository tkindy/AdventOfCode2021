(ns day03-test
  (:require day03
            [clojure.test :refer [deftest is]]))

(def example-input "00100\n11110\n10110\n10111\n10101\n01111\n00111\n11100\n10000\n11001\n00010\n01010\n")
(def example-numbers [[0 0 1 0 0]
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
                      [0 1 0 1 0]])

(deftest parse-input
  (is (= (day03/parse-input example-input) example-numbers)))

(deftest most-common
  (is (= (day03/most-common [1]) 1))
  (is (= (day03/most-common [0 1 0]) 0))
  (is (= (day03/most-common [0 1 0 0 1 1 0]) 0)))

(deftest part1
  (is (= (day03/part1 example-numbers) 198)))
