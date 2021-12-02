(ns day2
  (:require [clojure.string :refer [split]]))

(defn parse-input [input]
  (let [lines (split input #"\n")]
    (map (fn [line]
           (let [[direction distance] (split line #" ")]
             [(symbol direction) (Integer/parseInt distance)]))
         lines)))

(defn read-input []
  (parse-input (slurp "input")))

(defn final-position [commands]
  (reduce (fn [{x :x y :y} [direction distance]]
            (case direction
              forward {:x (+ x distance) :y y}
              down    {:x x :y (+ y distance)}
              up      {:x x :y (- y distance)}))
          {:x 0 :y 0}
          commands))

(defn part1 [commands]
  (let [{x :x y :y} (final-position commands)]
    (* x y)))

(defn final-position-aim [commands]
  (first
   (reduce (fn [[{x :x y :y} aim] [direction value]]
             (case direction
               down [{:x x :y y} (+ aim value)]
               up [{:x x :y y} (- aim value)]
               forward [{:x (+ x value) :y (+ y (* aim value))} aim]))
           [{:x 0 :y 0} 0]
           commands)))

(defn part2 [commands]
  (let [{x :x y :y} (final-position-aim commands)]
    (* x y)))

(defn -main []
  (let [commands (read-input)]
    (println "Part 1: " (part1 commands))
    (println "Part 2: " (part2 commands))))