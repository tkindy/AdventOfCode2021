(ns day16-test
  (:require [day16 :as d]
            [clojure.test :refer [deftest is]]))

(deftest decode
  (is (= (d/decode "D2FE28\n")
         '(1 1 0 1 0 0 1 0 1 1 1 1
             1 1 1 0 0 0 1 0 1 0 0 0))))

(deftest bits->int
  (is (= (d/bits->int [0 1 1 1 1 1 1 0 0 1 0 1]) 2021)))

(deftest parse-input
  (is (= (d/parse-input "D2FE28\n")
         {:version 6
          :value 2021}))
  (is (= (d/parse-input "38006F45291200\n")
         {:version 1
          :subpackets [{:version 6
                        :value 10}
                       {:version 2
                        :value 20}]}))
  (is (= (d/parse-input "EE00D40C823060\n")
         {:version 7
          :subpackets [{:version 2
                        :value 1}
                       {:version 4
                        :value 2}
                       {:version 1
                        :value 3}]}))
  (is (= (d/parse-input "8A004A801A8002F478\n")
         {:version 4
          :subpackets [{:version 1
                        :subpackets [{:version 5
                                      :subpackets [{:version 6
                                                    :value 15}]}]}]})))

(deftest part1
  (is (= (d/part1 (d/parse-input "8A004A801A8002F478\n"))
         16))
  (is (= (d/part1 (d/parse-input "620080001611562C8802118E34\n"))
         12))
  (is (= (d/part1 (d/parse-input "C0015000016115A2E0802F182340\n"))
         23))
  (is (= (d/part1 (d/parse-input "A0016C880162017C3686B18A3D4780\n"))
         31)))
