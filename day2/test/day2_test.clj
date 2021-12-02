(ns day2-test
  (:require day2
            [clojure.test :refer [is deftest]]))

(deftest parse-input
  (is (= (day2/parse-input "forward 5\ndown 3\ndown 2\nforward 7\nup 8\n")
  [['forward 5] ['down 3] ['down 2] ['forward 7] ['up 8]])))
