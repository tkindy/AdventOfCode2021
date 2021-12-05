(ns day05-test
  (:require [day05 :as d]
            [clojure.test :refer [deftest is]]))

(def example-input (slurp "examples/day05"))
(def example-parsed
  [{:x1 0, :y1 9, :x2 5, :y2 9}
   {:x1 8, :y1 0, :x2 0, :y2 8}
   {:x1 9, :y1 4, :x2 3, :y2 4}
   {:x1 2, :y1 2, :x2 2, :y2 1}
   {:x1 7, :y1 0, :x2 7, :y2 4}
   {:x1 6, :y1 4, :x2 2, :y2 0}
   {:x1 0, :y1 9, :x2 2, :y2 9}
   {:x1 3, :y1 4, :x2 1, :y2 4}
   {:x1 0, :y1 0, :x2 8, :y2 8}
   {:x1 5, :y1 5, :x2 8, :y2 2}])

(deftest parse-input
  (is (= (d/parse-input "") []))
  (is (= (d/parse-input "0,9 -> 5,9")
         [{:x1 0, :y1 9, :x2 5, :y2 9}]))
  (is (= (d/parse-input example-input) example-parsed)))

(deftest part1
  (is (= (d/part1 example-parsed) 5)))

(deftest part2
  (is (= (d/part2 example-parsed) 12)))
