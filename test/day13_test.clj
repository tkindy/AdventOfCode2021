(ns day13-test
  (:require [day13 :as d]
            [clojure.test :refer [deftest is]]))

(def example-input (slurp "examples/day13.txt"))
(def example (d/parse-input example-input))

(deftest parse-input
  (is (= example
         {:dots #{[6 10] [0 14] [9 10] [0 3] [10 4]
                  [4 11] [6 0] [6 12] [4 1] [0 13]
                  [10 12] [3 4] [3 0] [8 4] [1 10]
                  [2 14] [8 10] [9 0]}
          :folds [{:axis :y :value 7}
                  {:axis :x :value 5}]})))
