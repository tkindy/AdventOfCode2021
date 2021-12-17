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
