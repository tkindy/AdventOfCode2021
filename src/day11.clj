(ns day11
  (:require [clojure.string :as str]))

(defn parse-line [line]
  (mapv #(Integer/parseInt (str %)) line))

(defn parse-input [input]
  (mapv parse-line (str/split-lines input)))

(defn read-input []
  (parse-input (slurp "inputs/day11.txt")))

(defn -main [])
