(ns day18-test
  (:require [day18 :as d]
            [clojure.test :refer [deftest is]]))

(def example-input (slurp "examples/day18.txt"))
(def example (d/parse-input example-input))

(deftest parse-number
  (is (= (d/parse-number "[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]")
         {:left {:left {:left 0
                        :right {:left 4
                                :right 5}}
                 :right {:left 0
                         :right 0}}
          :right {:left {:left {:left 4
                                :right 5}
                         :right {:left 2
                                 :right 6}}
                  :right {:left 9
                          :right 5}}})))

(deftest parse-input
  (is (= (d/parse-input "[1,1]\n[[2,3],6]\n")
         [{:left 1
           :right 1}
          {:left {:left 2
                  :right 3}
           :right 6}])))

(deftest vec->number
  (is (= (d/vec->number 5) 5))
  (is (= (d/vec->number [0,0]) {:left 0, :right 0}))
  (is (= (d/vec->number [[0,[4,5]],[0,0]])
         {:left {:left 0
                 :right {:left 4
                         :right 5}}
          :right {:left 0
                  :right 0}})))

(deftest number->vec
  (is (= (d/number->vec 5) 5))
  (is (= (d/number->vec {:left 0, :right 5}) [0 5]))
  (is (= (d/number->vec {:left {:left 0
                                :right {:left 4
                                        :right 5}}
                         :right {:left 0
                                 :right 0}})
         [[0,[4,5]],[0,0]])))

(deftest first-left
  (is (= (d/first-left {:left {:left {:left {:left {:left 1
                                                    :right 1}
                                             :right {:left 2
                                                     :right 2}}
                                      :right {:left 3
                                              :right 3}}
                               :right {:left 4
                                       :right 4}}
                        :right {:left 5
                                :right 5}}
                       [:left :left :left :left :left])
         nil))
  (is (= (d/first-left {:left {:left {:left {:left {:left 1
                                                    :right 1}
                                             :right {:left 2
                                                     :right 2}}
                                      :right {:left 3
                                              :right 3}}
                               :right {:left 4
                                       :right 4}}
                        :right {:left 5
                                :right 5}}
                       [:left :right])
         [:left :left :right :right])))

(deftest first-right
  (is (= (d/first-right {:left {:left {:left {:left 0
                                              :right 7}
                                       :right 4}
                                :right {:left {:left 7
                                               :right 8}
                                        :right {:left 0
                                                :right {:left 6
                                                        :right 7}}}}
                         :right {:left 1
                                 :right 1}}
                        [:left :right :right :right])
         [:right :left])))

(deftest find-reducable
  (is (= (d/find-reducable {:left 1 :right 1}) nil)
      (= (d/find-reducable {:left {:left {:left {:left {:left 1
                                                        :right 1}
                                                 :right {:left 2
                                                         :right 2}}
                                          :right {:left 3
                                                  :right 3}}
                                   :right {:left 4
                                           :right 4}}
                            :right {:left 5
                                    :right 5}})
         [:left :left :left :left])))

(deftest explode
  (is (= (d/explode {:left {:left {:left {:left {:left 1
                                                 :right 1}
                                          :right {:left 2
                                                  :right 2}}
                                   :right {:left 3
                                           :right 3}}
                            :right {:left 4
                                    :right 4}}
                     :right {:left 5
                             :right 5}}
                    [:left :left :left :left])
         {:left {:left {:left {:left 0
                               :right {:left 3
                                       :right 2}}
                        :right {:left 3
                                :right 3}}
                 :right {:left 4
                         :right 4}}
          :right {:left 5
                  :right 5}})))

(deftest reduction-steps
  (is (= (d/reduction-steps (d/vec->number [[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]))
         (map d/vec->number (list [[[[0,7],4],[7,[[8,4],9]]],[1,1]]
                                  [[[[0,7],4],[15,[0,13]]],[1,1]]
                                  [[[[0,7],4],[[7,8],[0,13]]],[1,1]]
                                  [[[[0,7],4],[[7,8],[0,[6,7]]]],[1,1]]
                                  [[[[0,7],4],[[7,8],[6,0]]],[8,1]])))))

(deftest reduce-number
  (is (= (d/reduce-number {:left {:left {:left {:left {:left 4
                                                       :right 3}
                                                :right 4}
                                         :right 4}
                                  :right {:left 7
                                          :right {:left {:left 8
                                                         :right 4}
                                                  :right 9}}}
                           :right {:left 1
                                   :right 1}})
         {:left {:left {:left {:left 0
                               :right 7}
                        :right 4}
                 :right {:left {:left 7
                                :right 8}
                         :right {:left 6
                                 :right 0}}}
          :right {:left 8
                  :right 1}})))

(deftest add
  (is (= (d/number->vec (d/add (d/vec->number [[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]])
                               (d/vec->number [7,[[[3,7],[4,3]],[[6,3],[8,8]]]])))
         [[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]])))

(deftest sum
  (is (= (d/sum [{:left 1, :right 1}
                 {:left 2, :right 2}])
         {:left {:left 1
                 :right 1}
          :right {:left 2
                  :right 2}}))
  (is (= (d/sum [{:left 1, :right 1}
                 {:left 2, :right 2}
                 {:left 3, :right 3}])
         {:left {:left {:left 1
                        :right 1}
                 :right {:left 2
                         :right 2}}
          :right {:left 3
                  :right 3}}))
  (is (= (d/sum [{:left 1, :right 1}
                 {:left 2, :right 2}
                 {:left 3, :right 3}
                 {:left 4, :right 4}])
         {:left {:left {:left {:left 1
                               :right 1}
                        :right {:left 2
                                :right 2}}
                 :right {:left 3
                         :right 3}}
          :right {:left 4
                  :right 4}}))
  (is (= (d/sum [{:left 1, :right 1}
                 {:left 2, :right 2}
                 {:left 3, :right 3}
                 {:left 4, :right 4}
                 {:left 5, :right 5}])
         {:left {:left {:left {:left 3
                               :right 0}
                        :right {:left 5
                                :right 3}}
                 :right {:left 4
                         :right 4}}
          :right {:left 5
                  :right 5}}))
  (is (= (d/number->vec
          (d/sum (map d/vec->number
                      [[1 1]
                       [2 2]
                       [3 3]
                       [4 4]
                       [5 5]
                       [6 6]])))
         [[[[5,0],[7,4]],[5,5]],[6,6]]))
  (is (= (d/sum example)
         {:left {:left {:left {:left 6
                               :right 6}
                        :right {:left 7
                                :right 6}}
                 :right {:left {:left 7
                                :right 7}
                         :right {:left 7
                                 :right 0}}}
          :right {:left {:left {:left 7
                                :right 7}
                         :right {:left 7
                                 :right 7}}
                  :right {:left {:left 7
                                 :right 8}
                          :right {:left 9
                                  :right 9}}}})))

(deftest magnitude
  (is (= (d/magnitude {:left 9, :right 1}) 29))
  (is (= (d/magnitude {:left 1, :right 9}) 21))
  (is (= (d/magnitude {:left {:left 9
                              :right 1}
                       :right {:left 1
                               :right 9}})
         129))
  (is (= (d/magnitude {:left {:left {:left {:left 6
                                            :right 6}
                                     :right {:left 7
                                             :right 6}}
                              :right {:left {:left 7
                                             :right 7}
                                      :right {:left 7
                                              :right 0}}}
                       :right {:left {:left {:left 7
                                             :right 7}
                                      :right {:left 7
                                              :right 7}}
                               :right {:left {:left 7
                                              :right 8}
                                       :right {:left 9
                                               :right 9}}}})
         4140)))

(deftest part1
  (is (= (d/part1 example) 4140)))
