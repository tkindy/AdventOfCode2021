(ns day09
  (:require [clojure.string :as str]
            [clojure.set :as set]))

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

(defn neighbors [[x y]]
  (set (list [(dec x) y] ; left
             [(inc x) y] ; right
             [x (dec y)] ; up
             [x (inc y)] ; down
             )))

(defn get-height [heightmap [x y]]
  (-> heightmap
      (safe-nth y)
      (safe-nth x)))

(defn low-points [heightmap]
  (let [points
        (reduce
         (fn [low-points y]
           (let [row (nth heightmap y)]
             (reduce
              (fn [low-points x]
                (let [height (nth row x)
                      neighbors (->> (neighbors [x y])
                                     (map (fn [neighbor] (get-height heightmap neighbor)))
                                     (filter (comp not nil?)))]
                  (if (low-point? height neighbors)
                    (conj low-points [x y])
                    low-points)))
              low-points
              (range (count row)))))
         '()
         (range (count heightmap)))]
    (set points)))

(defn height->risk [height]
  (inc height))

(defn part1 [heightmap]
  (->> heightmap
       low-points
       (map (fn [point] (get-height heightmap point)))
       (map height->risk)
       (apply +)))

(defn basinable? [heightmap point]
  (let [height (get-height heightmap point)]
    (and height (< height 9))))

;; TODO
(defn fill-basin [heightmap low-point]
  (loop [basin #{}
         frontier #{low-point}
         seen #{}]
    (if (empty? frontier)
      basin
      (let [point (first frontier)
            seen (conj seen point)
            basin (if (basinable? heightmap point)
                    (conj basin point)
                    basin)
            frontier (disj frontier point)]
        (if (basinable? heightmap point)
          (recur (conj basin point)
                 (set/union frontier
                            (set/difference (neighbors point) seen))
                 seen)
          (recur basin frontier seen))))))

(defn fill-basins [heightmap low-points]
  (map (fn [low-point] (fill-basin heightmap low-point))
       low-points))

(defn part2 [heightmap]
  (let [low-points (low-points heightmap)
        basins (fill-basins heightmap low-points)]
    (->> basins
         (sort-by #(* -1 (count %)))
         (take 3)
         (map count)
         (apply *))))

(defn -main []
  (let [heightmap (read-input)]
    (println "Part 1: " (part1 heightmap))
    (println "Part 2: " (part2 heightmap))))
