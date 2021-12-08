(ns day08-test
  (:require [day08 :as d]
            [clojure.test :refer [deftest is]]))

(def example-input (slurp "examples/day08.txt"))
(def example (d/parse-input example-input))

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
                          :outputs #{#{'f 'd 'g 'a 'c 'b 'e}
                                     #{'c 'e 'f 'd 'b}
                                     #{'c 'e 'f 'b 'g 'd}
                                     #{'g 'c 'b 'e}}}))
  (is (= (count example) 10)))
