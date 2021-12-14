(ns day14-test
  (:require [day14 :as d]
            [clojure.test :refer [deftest is]]))

(def example-input (slurp "examples/day14.txt"))
(def example (d/parse-input example-input))

(deftest parse-input
  (is (= example
         {:template {:pairs {[\N \N] 1
                             [\N \C] 1
                             [\C \B] 1}
                     :first \N}
          :rules {[\C \H] \B
                  [\H \H] \N
                  [\C \B] \H
                  [\N \H] \C
                  [\H \B] \C
                  [\H \C] \B
                  [\H \N] \C
                  [\N \N] \C
                  [\B \H] \H
                  [\N \C] \B
                  [\N \B] \B
                  [\B \N] \B
                  [\B \B] \N
                  [\B \C] \B
                  [\C \C] \N
                  [\C \N] \C}})))

(deftest step
  (is (= (d/step {[\N \N] 1, [\N \C] 1, [\C \B] 1} (:rules example))
         {[\N \C] 1
          [\C \N] 1
          [\N \B] 1
          [\B \C] 1
          [\C \H] 1
          [\H \B] 1}))
  (is (= (d/step {[\N \C] 1
                  [\C \N] 1
                  [\N \B] 1
                  [\B \C] 1
                  [\C \H] 1
                  [\H \B] 1}
                 (:rules example))
         {[\N \B] 2
          [\B \C] 2
          [\C \C] 1
          [\C \N] 1
          [\B \B] 2
          [\C \B] 2
          [\B \H] 1
          [\H \C] 1}))
  (is (= (d/step {[\N \B] 2
                  [\B \C] 2
                  [\C \C] 1
                  [\C \N] 1
                  [\B \B] 2
                  [\C \B] 2
                  [\B \H] 1
                  [\H \C] 1}
                 (:rules example))
         {[\N \B] 4
          [\B \B] 4
          [\B \C] 3
          [\C \N] 2
          [\N \C] 1
          [\C \C] 1
          [\B \N] 2
          [\C \H] 2
          [\H \B] 3
          [\B \H] 1
          [\H \H] 1}))
  (is (= (d/step {[\N \B] 4
                  [\B \B] 4
                  [\B \C] 3
                  [\C \N] 2
                  [\N \C] 1
                  [\C \C] 1
                  [\B \N] 2
                  [\C \H] 2
                  [\H \B] 3
                  [\B \H] 1
                  [\H \H] 1}
                 (:rules example))
         {[\N \B] 9
          [\B \B] 9
          [\B \N] 6
          [\B \C] 4
          [\C \C] 2
          [\C \N] 3
          [\N \C] 1
          [\C \B] 5
          [\B \H] 3
          [\H \C] 3
          [\H \H] 1
          [\H \N] 1
          [\N \H] 1})))

(deftest part1
  (is (= (d/part1 example) 1588)))

(deftest part2
  (is (= (d/part2 example) 2188189693529)))
