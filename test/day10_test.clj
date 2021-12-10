(ns day10-test
  (:require [day10 :as d]
            [clojure.test :refer [deftest is]]))

(def example-input (slurp "examples/day10.txt"))
(def example (d/parse-input example-input))

(deftest parse-input
  (is (= (d/parse-input "()\n(]\n")
         '((\( \))
           (\( \]))))
  (is (= (d/parse-input "<([{}])>")
         '((\< \( \[ \{ \} \] \) \>)))))
