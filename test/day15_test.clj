(ns day15-test
  (:require [day15 :as d]
            [clojure.test :refer [deftest is]]))

(def example-input (slurp "examples/day15.txt"))
(def example (d/parse-input example-input))
