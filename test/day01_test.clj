(ns day01-test
  (:require day01
            [clojure.test :refer [deftest is]]))

(deftest sliding-windows
  (is (= (day01/sliding-windows 2 [1 2 3 4])
         [[1 2] [2 3] [3 4]]))
  (is (= (day01/sliding-windows 3 [1 2 3 4])
         [[1 2 3] [2 3 4]])))

(deftest num-increases
  (is (= (day01/num-increases []) 0))
  (is (= (day01/num-increases [100]) 0))
  (is (= (day01/num-increases [100 50]) 0))
  (is (= (day01/num-increases [100 200]) 1))
  (is (= (day01/num-increases [100 100 100]) 0))
  (is (= (day01/num-increases
          [199 200 208 210 200 207 240 269 260 263])
         7)
      "given example"))

(deftest num-sum-increases
  (is (= (day01/num-sum-increases
          [199 200 208 210 200 207 240 269 260 263])
         5)
      "given example"))
