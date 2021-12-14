(ns day14-test
  (:require [day14 :as d]
            [clojure.test :refer [deftest is]]))

(def example-input (slurp "examples/day14.txt"))
(def example (d/parse-input example-input))

(deftest parse-input
  (is (= example
         {:template [\N \N \C \B]
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
  (is (= (d/step [\N \N \C \B] (:rules example))
         [\N \C \N \B \C \H \B]))
  (is (= (d/step [\N \C \N \B \C \H \B] (:rules example))
         [\N \B \C \C \N \B \B \B \C \B \H \C \B]))
  (is (= (d/step [\N \B \C \C \N \B \B \B \C \B \H \C \B] (:rules example))
         [\N \B \B \B \C \N \C \C \N \B \B \N \B \N \B \B \C \H \B \H \H \B \C \H \B]))
  (is (= (d/step [\N \B \B \B \C \N \C \C \N \B \B \N \B \N \B \B \C \H \B \H \H \B \C \H \B] (:rules example))
         (into [] "NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB"))))

(deftest part1
  (is (= (d/part1 example) 1588)))
