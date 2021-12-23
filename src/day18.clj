(ns day18
  (:require [clojure.string :as str]))

(defn parse-number-helper [[left right]]
  (letfn [(process [v]
            (if (number? v)
              v
              (parse-number-helper v)))]
    {:left (process left), :right (process right)}))

(defn parse-number [line]
  (let [vector (read-string line)]
    (parse-number-helper vector)))

(defn parse-input [input]
  (map parse-number (str/split-lines input)))

(defn read-input []
  (parse-input (slurp "inputs/day18.txt")))

(defn part1 [numbers]
  0)

(defn -main []
  (let [numbers (read-input)]
    (println "Part 1:" (part1 numbers))))
