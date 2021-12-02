(ns day02-test
  (:require day02
            [clojure.test :refer [is deftest]]))

(deftest parse-input
  (is (= (day02/parse-input "forward 5\ndown 3\ndown 2\nforward 7\nup 8\n")
         [['forward 5] ['down 3] ['down 2] ['forward 7] ['up 8]])))

(deftest final-position
  (is (= (day02/final-position []) {:x 0 :y 0}))
  (is (= (day02/final-position [['forward 2]]) {:x 2 :y 0}))
  (is (= (day02/final-position [['down 3]]) {:x 0 :y 3}))
  (is (= (day02/final-position [['up 4]]) {:x 0 :y -4}))
  (is (= (day02/final-position
          [['forward 5] ['down 5] ['forward 8] ['up 3] ['down 8] ['forward 2]])
         {:x 15 :y 10})))

(deftest final-position-aim
  (is (= (day02/final-position-aim []) {:x 0 :y 0}))
  (is (= (day02/final-position-aim
          [['forward 5] ['down 5] ['forward 8] ['up 3] ['down 8] ['forward 2]])
         {:x 15 :y 60})))
