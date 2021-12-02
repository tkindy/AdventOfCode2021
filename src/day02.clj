(ns day02
  (:require [clojure.string :refer [split]]))

(defn parse-input [input]
  (let [lines (split input #"\n")]
    (map (fn [line]
           (let [[direction distance] (split line #" ")]
             [(symbol direction) (Integer/parseInt distance)]))
         lines)))

(defn read-input []
  (parse-input (slurp "inputs/day2")))

(defn final-position [commands]
  (reduce (fn [{x :x, y :y, :as pos} [direction distance]]
            (case direction
              forward (assoc pos :x (+ x distance))
              down    (assoc pos :y (+ y distance))
              up      (assoc pos :y (- y distance))))
          {:x 0 :y 0}
          commands))

(defn part1 [commands]
  (let [{x :x, y :y} (final-position commands)]
    (* x y)))

(defn final-position-aim [commands]
  (first
   (reduce (fn [[{x :x, y :y, :as pos} aim] [direction value]]
             (case direction
               down [pos (+ aim value)]
               up [pos (- aim value)]
               forward [{:x (+ x value), :y (+ y (* aim value))} aim]))
           [{:x 0 :y 0} 0]
           commands)))

(defn part2 [commands]
  (let [{x :x, y :y} (final-position-aim commands)]
    (* x y)))

(defn -main []
  (let [commands (read-input)]
    (println "Part 1: " (part1 commands))
    (println "Part 2: " (part2 commands))))
