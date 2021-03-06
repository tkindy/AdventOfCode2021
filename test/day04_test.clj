(ns day04-test
  (:require day04
            [clojure.test :refer [deftest is]]
            [clojure.string :as s]))

(def example-input (slurp "examples/day04.txt"))
(def example-boards
  [[[22 13 17 11  0]
    [8  2 23  4 24]
    [21  9 14 16  7]
    [6 10  3 18  5]
    [1 12 20 15 19]]

   [[3 15  0  2 22]
    [9 18 13 17  5]
    [19  8  7 25 23]
    [20 11 10 24  4]
    [14 21 16 12  6]]

   [[14 21 17 24  4]
    [10 16 15  9 19]
    [18  8 23 26 20]
    [22 11 13  6  5]
    [2  0 12  3  7]]])
(def example-parsed
  {:draws '(7 4 9 5 11 17 23 2 0 14 21 24 10 16 13 6 15 25 12 22 18 20 8 19 3 26 1)
   :boards example-boards})
(def example-state (day04/prep-boards example-parsed))

(deftest split-board-lines
  (is (= (day04/split-board-lines (s/split-lines example-input))
         '(("22 13 17 11  0"
            " 8  2 23  4 24"
            "21  9 14 16  7"
            " 6 10  3 18  5"
            " 1 12 20 15 19")
           (" 3 15  0  2 22"
            " 9 18 13 17  5"
            "19  8  7 25 23"
            "20 11 10 24  4"
            "14 21 16 12  6")
           ("14 21 17 24  4"
            "10 16 15  9 19"
            "18  8 23 26 20"
            "22 11 13  6  5"
            " 2  0 12  3  7")))))

(deftest parse-boards
  (is (= (day04/parse-boards (s/split-lines example-input))
         example-boards)))

(deftest parse-input
  (is (= (day04/parse-input example-input)
         example-parsed)))

(deftest first-winner
  (is (= (day04/first-winner {:draws '(1 2 3 4 5)
                              :boards (map day04/prep-board
                                           '([[1 2 3]
                                              [2 3 4]
                                              [3 4 5]]
                                             [[7 8 9]
                                              [7 8 9]
                                              [7 8 9]]))})
         [[[{:value 1, :marked true} {:value 2, :marked true} {:value 3, :marked true}]
           [{:value 2, :marked true} {:value 3, :marked true} {:value 4, :marked false}]
           [{:value 3, :marked true} {:value 4, :marked false} {:value 5, :marked false}]]
          3])))

(deftest part1
  (is (= (day04/part1 example-state) 4512)))

(deftest part2
  (is (= (day04/part2 example-state) 1924)))
