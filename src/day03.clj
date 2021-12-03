(ns day03
  (:require [clojure.string :refer [split]]))

(defn parse-input [input]
  (map (fn [line]
         (map #(Integer/parseInt (str %)) line))
       (split input #"\n")))

(defn read-input []
  (parse-input (slurp "inputs/day3")))

(defn -main []
  (let []))
