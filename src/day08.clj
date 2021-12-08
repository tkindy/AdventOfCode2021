(ns day08
  (:require [clojure.string :as str]))

(defn parse-segments [segments]
  (->> segments
       (map (comp symbol str))
       set))

(defn parse-segments-set [segments-set]
  (->> (str/split segments-set #" ")
       (filter (comp not str/blank?))
       (map parse-segments)
       set))

(defn parse-line [line]
  (let [[signals outputs]
        (map parse-segments-set (str/split line #"\|"))]
    {:signals signals :outputs outputs}))

(defn parse-input [input]
  (->> input
       str/split-lines
       (map parse-line)))

(defn read-input []
  (parse-input (slurp "inputs/day08.txt")))

(defn -main [])
