(ns day05-test
  (:require [day05 :as d]
            [clojure.test :refer [deftest is]]))

(def example-input (slurp "examples/day05.txt"))
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

(deftest points
  (is (= (d/points {:x1 0, :y1 9, :x2 5, :y2 9})
         '([0 9] [1 9] [2 9] [3 9] [4 9] [5 9])))
  (is (= (d/points {:x1 8, :y1 0, :x2 0, :y2 8})
         '([8 0] [7 1] [6 2] [5 3] [4 4] [3 5] [2 6] [1 7] [0 8])))
  (is (= (d/points {:x1 9, :y1 4, :x2 3, :y2 4})
         '([9 4] [8 4] [7 4] [6 4] [5 4] [4 4] [3 4])))
  (is (= (d/points {:x1 2, :y1 2, :x2 2, :y2 1})
         '([2 2] [2 1])))
  (is (= (d/points {:x1 7, :y1 0, :x2 7, :y2 4})
         '([7 0] [7 1] [7 2] [7 3] [7 4])))
  (is (= (d/points {:x1 6, :y1 4, :x2 2, :y2 0})
         '([6 4] [5 3] [4 2] [3 1] [2 0])))
  (is (= (d/points {:x1 0, :y1 9, :x2 2, :y2 9})
         '([0 9] [1 9] [2 9])))
  (is (= (d/points {:x1 3, :y1 4, :x2 1, :y2 4})
         '([3 4] [2 4] [1 4])))
  (is (= (d/points {:x1 0, :y1 0, :x2 8, :y2 8})
         '([0 0] [1 1] [2 2] [3 3] [4 4] [5 5] [6 6] [7 7] [8 8])))
  (is (= (d/points {:x1 5, :y1 5, :x2 8, :y2 2})
         '([5 5] [6 4] [7 3] [8 2]))))

(deftest part1
  (is (= (d/part1 example-parsed) 5)))

(deftest part2
  (is (= (d/part2 example-parsed) 12)))
