(ns day13-test
  (:require [day13 :as d]
            [clojure.test :refer [deftest is]]))

(def example-input (slurp "examples/day13.txt"))
(def example (d/parse-input example-input))

(deftest parse-input
  (is (= example
         {:dots #{{:x 8, :y 4} {:x 3, :y 4} {:x 3, :y 0}
                  {:x 9, :y 0} {:x 0, :y 13} {:x 8, :y 10}
                  {:x 0, :y 14} {:x 1, :y 10} {:x 4, :y 11}
                  {:x 4, :y 1} {:x 0, :y 3} {:x 10, :y 12}
                  {:x 10, :y 4} {:x 6, :y 10} {:x 6, :y 12}
                  {:x 2, :y 14} {:x 9, :y 10} {:x 6, :y 0}}
          :folds [{:axis :y :value 7}
                  {:axis :x :value 5}]})))

(deftest part1
  (is (= (d/part1 example) 17)))
