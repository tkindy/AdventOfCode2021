(ns day12-test
  (:require [day12 :as d]
            [clojure.test :refer [deftest is]]))

(def example1-input (slurp "examples/day12-1.txt"))
(def example2-input (slurp "examples/day12-2.txt"))
(def example3-input (slurp "examples/day12-3.txt"))

(deftest parse-input
  (is (= (d/parse-input example1-input)
         {:start #{:A :b}
          :A     #{:start :b :c :end}
          :b     #{:start :A :d :end}
          :c     #{:A}
          :d     #{:b}
          :end   #{:A :b}})))
