(ns day09
  (:require [clojure.string :as str]))

(defn parse-line [line]
  (mapv #(Integer/parseInt (str %)) line))

(defn parse-input [input]
  (->> input
       str/split-lines
       (filter (comp not str/blank?))
       (mapv parse-line)))

(defn read-input []
  (parse-input (slurp "inputs/day09.txt")))

(defn -main [])
