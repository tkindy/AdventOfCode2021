(ns day1-test
  (:require day1
            [clojure.test :refer [deftest is]]))

(deftest consecutive-pairs
  (is (= (day1/consecutive-pairs [1 2 3 4])
         [[1 2] [2 3] [3 4]])))

(deftest num-increases
  (is (= (day1/num-increases []) 0))
  (is (= (day1/num-increases [100]) 0))
  (is (= (day1/num-increases [100 50]) 0))
  (is (= (day1/num-increases [100 200]) 1))
  (is (= (day1/num-increases [100 100 100]) 0))
  (is (= (day1/num-increases
          [199 200 208 210 200 207 240 269 260 263])
         7)
      "given example"))
