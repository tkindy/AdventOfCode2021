(ns day12-test
  (:require [day12 :as d]
            [clojure.test :refer [deftest is]]))

(def example1-input (slurp "examples/day12-1.txt"))
(def example1 (d/parse-input example1-input))
(def example2-input (slurp "examples/day12-2.txt"))
(def example2 (d/parse-input example2-input))
(def example3-input (slurp "examples/day12-3.txt"))
(def example3 (d/parse-input example3-input))

(deftest parse-input
  (is (= example1
         {:start #{:A :b}
          :A     #{:start :b :c :end}
          :b     #{:start :A :d :end}
          :c     #{:A}
          :d     #{:b}
          :end   #{:A :b}})))

(deftest finished?
  (is (= (d/finished? [:start :A :end]) true))
  (is (= (d/finished? [:start :A]) false)))

(deftest iterate-paths
  (is (= (d/iterate-paths #{[:start]} example1 d/part1-visitable?)
         [#{[:start :A] [:start :b]} #{}]))
  (is (= (d/iterate-paths #{[:start :A] [:start :b]} example1 d/part1-visitable?)
         [#{[:start :A :b] [:start :A :c] [:start :b :A] [:start :b :d]}
          #{[:start :A :end] [:start :b :end]}])))

(deftest all-paths
  (is (= (d/all-paths example1 d/part1-visitable?)
         #{[:start :A :b :A :c :A :end]
           [:start :A :b :A :end]
           [:start :A :b :end]
           [:start :A :c :A :b :A :end]
           [:start :A :c :A :b :end]
           [:start :A :c :A :end]
           [:start :A :end]
           [:start :b :A :c :A :end]
           [:start :b :A :end]
           [:start :b :end]})))

(deftest part1
  (is (= (d/part1 example1) 10))
  (is (= (d/part1 example2) 19))
  (is (= (d/part1 example3) 226)))

(deftest can-revisit?
  (is (= (d/can-revisit? :start [:start :A]) false))
  (is (= (d/can-revisit? :a [:start :a :b]) true))
  (is (= (d/can-revisit? :a [:start :a :b :a]) false))
  (is (= (d/can-revisit? :b [:start :a :b :a]) false)))

(deftest part2
  (is (= (d/part2 example1) 36))
  (is (= (d/part2 example2) 103))
  (is (= (d/part2 example3) 3509)))
