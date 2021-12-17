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
          :type :literal
          :value 2021}))
  (is (= (d/parse-input "38006F45291200\n")
         {:version 1
          :type :less-than
          :subpackets [{:version 6
                        :type :literal
                        :value 10}
                       {:version 2
                        :type :literal
                        :value 20}]}))
  (is (= (d/parse-input "EE00D40C823060\n")
         {:version 7
          :type :max
          :subpackets [{:version 2
                        :type :literal
                        :value 1}
                       {:version 4
                        :type :literal
                        :value 2}
                       {:version 1
                        :type :literal
                        :value 3}]}))
  (is (= (d/parse-input "8A004A801A8002F478\n")
         {:version 4
          :type :min
          :subpackets [{:version 1
                        :type :min
                        :subpackets [{:version 5
                                      :type :min
                                      :subpackets [{:version 6
                                                    :type :literal
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

(deftest part2
  (is (= (d/part2 (d/parse-input "C200B40A82")) 3))
  (is (= (d/part2 (d/parse-input "04005AC33890")) 54))
  (is (= (d/part2 (d/parse-input "880086C3E88112")) 7))
  (is (= (d/part2 (d/parse-input "CE00C43D881120")) 9))
  (is (= (d/part2 (d/parse-input "D8005AC2A8F0")) 1))
  (is (= (d/part2 (d/parse-input "F600BC2D8F")) 0))
  (is (= (d/part2 (d/parse-input "9C005AC2F8F0")) 0))
  (is (= (d/part2 (d/parse-input "9C0141080250320F1802104A08")) 1)))
