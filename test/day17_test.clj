(ns day17-test
  (:require [day17 :as d]
            [clojure.test :refer [deftest is]]))

(def example-input "target area: x=20..30, y=-10..-5\n")
(def example (d/parse-input example-input))

(deftest parse-input
  (is (= example {:x [20 30] :y [-10 -5]})))
