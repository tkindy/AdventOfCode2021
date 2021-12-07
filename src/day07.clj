(ns day07
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (->> (str/split input #",")
       (map #(Integer/parseInt (str/trim %)))
       (map (fn [loc] {loc 1}))
       (apply merge-with +)))

(defn read-input []
  (parse-input (slurp "inputs/day07.txt")))

(def part1-cost-fn identity)

(defn fuel-cost
  [locs pos cost-fn]
  (->> locs
       (map (fn [[loc count]] (* (cost-fn (Math/abs (- loc pos))) count)))
       (apply +)))

(defn part1-fuel-cost [locs pos]
  (fuel-cost locs pos part1-cost-fn))

(defn best-position [locs cost-fn]
  (let [unique-locs (map key locs)
        loc-range (range (apply min unique-locs)
                         (inc (apply max unique-locs)))]
    (->> loc-range
         (map (fn [loc] [loc (fuel-cost locs loc cost-fn)]))
         (apply min-key second))))

(defn part1-best-position [locs]
  (best-position locs part1-cost-fn))

(defn part1 [locs]
  (second (part1-best-position locs)))

(defn -main []
  (let [locs (read-input)]
    (println "Part 1: " (part1 locs))))
