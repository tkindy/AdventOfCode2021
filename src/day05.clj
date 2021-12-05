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

(defn straight? [{:keys [x1 y1 x2 y2]}]
  (or (= x1 x2) (= y1 y2)))

(defn between-inclusive [a b]
  (range (min a b) (inc (max a b))))

(defn points [{:keys [x1 y1 x2 y2]}]
  (cond
    (= x1 x2) (map (fn [y] [x1 y]) (between-inclusive y1 y2))
    (= y1 y2) (map (fn [x] [x y1]) (between-inclusive x1 x2))
    (= (Math/abs (- x1 x2)) (Math/abs (- y1 y2)))
    (let [[[x1 y1] [x2 y2]]
          (if (< x1 x2)
            [[x1 y1] [x2 y2]]
            [[x2 y2] [x1 y1]])]
      (map (fn [i] [(+ x1 i) (+ y1 i)])
           (range (inc (- x2 x1)))))
    :else (throw (new IllegalArgumentException))))

(defn build-hit-map1 [line]
  (->> line
       points
       (reduce (fn [hit-map point] (assoc hit-map point 1))
               {})))

(defn build-hit-map [lines]
  (->> lines
       (map build-hit-map1)
       (apply merge-with +)))

(defn count-intersections [lines]
  (->> lines
       build-hit-map
       (filter (fn [[_ hits]] (>= hits 2)))
       count))

(defn part1 [lines]
  (->> lines
       (filter straight?)
       count-intersections))

(defn part2 [lines]
  (count-intersections lines))

(defn -main []
  (let [lines (read-input)]
    (println "Part 1: " (part1 lines))
    (println "Part 2: " (part2 lines))))
