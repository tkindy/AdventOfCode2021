(ns day10
  [:require [clojure.string :as str]])

(defn parse-line [line]
  (apply list line))

(defn parse-input [input]
  (map parse-line (str/split-lines input)))

(defn read-input []
  (parse-input (slurp "inputs/day10.txt")))

(defn -main [])
