(ns day07
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (->> (str/split input #",")
       (map #(Integer/parseInt (str/trim %)))
       (map (fn [loc] {loc 1}))
       (apply merge-with +)))

(defn read-input []
  (parse-input (slurp "inputs/day07.txt")))

(defn fuel-cost [locs pos]
  (->> locs
       (map (fn [[loc count]] (* (Math/abs (- loc pos)) count)))
       (apply +)))

(defn best-position [locs]
  (let [unique-locs (map key locs)
        loc-range (range (apply min unique-locs)
                         (inc (apply max unique-locs)))]
    (->> loc-range
         (map (fn [loc] [loc (fuel-cost locs loc)]))
         (apply min-key second))))

(defn part1 [locs]
  (second (best-position locs)))

(defn -main []
  (let [locs (read-input)]
    (println "Part 1: " (part1 locs))))
