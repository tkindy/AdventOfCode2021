(ns day12-test
  (:require [day12 :as d]
            [clojure.test :refer [deftest is]]))

(def example1-input (slurp "examples/day12-1.txt"))
(def example1 (d/parse-input example1-input))
(def example2-input (slurp "examples/day12-2.txt"))
(def example2 (d/parse-input example2-input))
(def example3-input (slurp "examples/day12-3.txt"))
(def example3 (d/parse-input example3-input))

(deftest parse-input
  (is (= example1
         {:start #{:A :b}
          :A     #{:start :b :c :end}
          :b     #{:start :A :d :end}
          :c     #{:A}
          :d     #{:b}
          :end   #{:A :b}})))

(deftest part1
  (is (= (d/part1 example1) 10))
  (is (= (d/part1 example2) 19))
  (is (= (d/part1 example3) 226)))
