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

(deftest illegal-char
  (is (= (d/illegal-char '(\])) \]))
  (is (= (d/illegal-char (d/parse-line "{([(<{}[<>[]}>{[]{[(<()>"))
         \}))
  (is (= (d/illegal-char (d/parse-line "[[<[([]))<([[{}[[()]]]"))
         \)))
  (is (= (d/illegal-char (d/parse-line "[{[{({}]{}}([{[{{{}}([]"))
         \])))

(deftest part1
  (is (= (d/part1 example) 26397)))

(deftest part2
  (is (= (d/part2 example) 288957)))
