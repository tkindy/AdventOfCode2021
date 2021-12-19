(ns day17-test
  (:require [day17 :as d]
            [clojure.test :refer [deftest is]]))

(def example-input "target area: x=20..30, y=-10..-5\n")
(def example (d/parse-input example-input))

(deftest parse-input
  (is (= example {:x [20 30] :y [-10 -5]})))

(deftest hits-y-range?
  (is (= (d/hits-y-range? 9 [-10 -5]) true)))

(deftest relevant-y-values
  (is (= (d/relevant-y-values 9 -10)
         (list 0 9 17 24 30 35 39 42 44 45
               45 44 42 39 35 30 24 17 9 0 -10)))
  (is (= (take-last 3 (d/relevant-y-values 49 -150))
         (list 0 -50 -101))))

(deftest in-range?
  (is (d/in-range? -150 [-150 -108])))

(deftest max-vy0
  (is (= (d/max-vy0 [-10 -5]) 9))
  (is (= (d/max-vy0 [-150 -108]) 48)))

(deftest apex
  (is (= (d/apex 0) 0))
  (is (= (d/apex 1) 1))
  (is (= (d/apex 2) 3))
  (is (= (d/apex 3) 6))
  (is (= (d/apex 4) 10))
  (is (= (d/apex 9) 45)))

(deftest part1
  (is (= (d/part1 example) 45)))
