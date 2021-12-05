(ns day05
  (:require [clojure.string :as s]))

(defn parse-point [p]
  (let [[x y] (s/split p #",")]
    [(Integer/parseInt x) (Integer/parseInt y)]))

(defn parse-line [line]
  (let [[p1 p2] (s/split line #" -> ")
        [x1 y1] (parse-point p1)
        [x2 y2] (parse-point p2)]
    {:x1 x1, :y1 y1, :x2 x2, :y2 y2}))

(defn parse-input [input]
  (->> input
       s/split-lines
       (filter (comp not s/blank?))
       (map parse-line)))

(defn read-input []
  (parse-input (slurp "inputs/day5")))

(defn -main [])
