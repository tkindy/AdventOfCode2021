(ns day18-test
  (:require [day18 :as d]
            [clojure.test :refer [deftest is]]))

(def example-input (slurp "examples/day18.txt"))
(def example (d/parse-input example-input))

(deftest parse-number
  (is (= (d/parse-number "[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]")
         {:left {:left {:left 0
                        :right {:left 4
                                :right 5}}
                 :right {:left 0
                         :right 0}}
          :right {:left {:left {:left 4
                                :right 5}
                         :right {:left 2
                                 :right 6}}
                  :right {:left 9
                          :right 5}}})))

(deftest parse-input
  (is (= (d/parse-input "[1,1]\n[[2,3],6]\n")
         [{:left 1
           :right 1}
          {:left {:left 2
                  :right 3}
           :right 6}])))
