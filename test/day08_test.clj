(ns day08-test
  (:require [day08 :as d]
            [clojure.test :refer [deftest is]]))

(def example-input (slurp "examples/day08.txt"))
(def example (d/parse-input example-input))
(def example2-input "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf")
(def example2 '({:signals #{#{a c e d g f b}
                            #{c d f b e}
                            #{g c d f a}
                            #{f b c a d}
                            #{d a b}
                            #{c e f a b d}
                            #{c d f g e b}
                            #{e a f b}
                            #{c a g e d b}
                            #{a b}}
                 :outputs [#{c d f e b}
                           #{f c a d b}
                           #{c d f e b}
                           #{c d b a f}]}))
(def example2-signal-map '{a c, e b, c g, g e, b f, d a, f d})

(deftest parse-input
  (is (= (first example) {:signals #{#{'b 'e}
                                     #{'c 'f 'b 'e 'g 'a 'd}
                                     #{'c 'b 'd 'g 'e 'f}
                                     #{'f 'g 'a 'e 'c 'd}
                                     #{'c 'g 'e 'b}
                                     #{'f 'd 'c 'g 'e}
                                     #{'a 'g 'e 'b 'f 'd}
                                     #{'f 'e 'c 'd 'b}
                                     #{'f 'a 'b 'c 'd}
                                     #{'e 'd 'b}}
                          :outputs [#{'f 'd 'g 'a 'c 'b 'e}
                                    #{'c 'e 'f 'd 'b}
                                    #{'c 'e 'f 'b 'g 'd}
                                    #{'g 'c 'b 'e}]}))
  (is (= (count example) 10)))

(deftest count-easy-digits
  (is (= (d/count-easy-digits (:outputs (first example)))
         2)))

(deftest count-easy-digits-rows
  (is (= (d/count-easy-digits-rows example)
         '(2 3 3 1 3 4 3 1 4 2))))

(deftest part1
  (is (= (d/part1 example) 26)))

(deftest process-easy-digit
  (is (= (d/process-easy-digit (:signals (first example2))
                               d/init-segment-candidates
                               1)
         '#{a d/segment-set
            b ˜})))

(deftest find-segment-mapping
  (is (= (d/find-segment-mapping (:signals (first example2)))
         example2-signal-map)))

(deftest decode-output
  (is (= (d/decode-output '#{c d f e b} example2-signal-map)
         5)))

(deftest part2
  (is (= (d/part2 example2) 5353))
  (is (= (d/part2 example) 61229)))
