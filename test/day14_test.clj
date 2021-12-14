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
