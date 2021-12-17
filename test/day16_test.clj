(ns day16-test
  (:require [day16 :as d]
            [clojure.test :refer [deftest is]]))

(def example-input (slurp "examples/day16.txt"))
(def example (d/parse-input example-input))
