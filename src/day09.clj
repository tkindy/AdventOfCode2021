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

(defn safe-nth [coll n]
  (if (< -1 n (count coll))
    (nth coll n)
    nil))

(defn low-point? [height neighbors]
  (< height (apply min neighbors)))

(defn low-points [heightmap]
  (reduce
   (fn [low-points y]
     (let [row (nth heightmap y)]
       (reduce
        (fn [low-points x]
          (let [height (nth row x)
                prev-row (safe-nth heightmap (dec y))
                next-row (safe-nth heightmap (inc y))
                up (nth prev-row x)
                down (nth next-row x)
                left (safe-nth row (dec x))
                right (safe-nth row (inc x))
                neighbors (filter (comp not nil?) (list up down left right))]
            (if (low-point? height neighbors)
              (conj low-points height)
              low-points)))
        low-points
        (range (count row)))))
   '()
   (range (count heightmap))))

(defn height->risk [height]
  (inc height))

(defn part1 [heightmap]
  (->> heightmap
       low-points
       (map height->risk)
       (apply +)))

(defn -main []
  (let [heightmap (read-input)]
    (println "Part 1: " (part1 heightmap))))
