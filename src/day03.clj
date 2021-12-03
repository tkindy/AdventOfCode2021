(ns day03
  (:require [clojure.string :refer [split]]))

(defn parse-input [input]
  (->> (split input #"\n")
       (map (fn [line]
              (map #(Integer/parseInt (str %)) line)))
       (into [])))

(defn read-input []
  (parse-input (slurp "inputs/day3")))

(defn -main []
  (let []))
