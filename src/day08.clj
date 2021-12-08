(ns day08
  (:require [clojure.string :as str]))

(defn parse-segments [segments]
  (->> segments
       (map (comp symbol str))
       set))

(defn parse-segments-group [segments-group]
  (->> (str/split segments-group #" ")
       (filter (comp not str/blank?))
       (map parse-segments)))

(defn parse-line [line]
  (let [[signals outputs]
        (map parse-segments-group (str/split line #"\|"))]
    {:signals (set signals) :outputs outputs}))

(defn parse-input [input]
  (->> input
       str/split-lines
       (map parse-line)))

(defn read-input []
  (parse-input (slurp "inputs/day08.txt")))

(def easy-digit-counts
  #{2 ; 1
    4 ; 4
    3 ; 7
    7 ; 8
    })

(defn count-easy-digits [outputs]
  (->> outputs
       (filter #(easy-digit-counts (count %)))
       count))

(defn count-easy-digits-rows [notes]
  (map (fn [{:keys [_ outputs]}] (count-easy-digits outputs))
       notes))

(defn part1 [notes]
  (->> notes
       count-easy-digits-rows
       (apply +)))

(defn -main []
  (let [notes (read-input)]
    (println "Part 1: " (part1 notes))))
