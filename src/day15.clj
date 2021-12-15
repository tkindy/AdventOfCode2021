(ns day15
  (:require [clojure.string :as str]
            [clojure.set :as set]
            [day09 :refer [safe-nth]]))

(defn parse-line [line]
  (mapv #(Integer/parseInt (str %)) line))

(defn parse-input [input]
  (mapv parse-line (str/split-lines input)))

(defn read-input []
  (parse-input (slurp "inputs/day15.txt")))

(defn all-points [risk-map]
  (for [y (range (count risk-map))
        x (range (count (first risk-map)))]
    [x y]))

(defn possible-neighbors [[x y]]
  (set (list [(dec x) y] ; left
             [(inc x) y] ; right
             [x (dec y)] ; up
             [x (inc y)] ; down
             )))

(defn get-risk [risk-map [x y]]
  (-> risk-map
      (safe-nth y)
      (safe-nth x)))

(defn neighbors [point risk-map]
  (->> (possible-neighbors point)
       (filter (fn [neighbor] (get-risk risk-map neighbor)))
       set))

(defn get-distance [node distances]
  (or (:distance (distances node)) Double/POSITIVE_INFINITY))

(defn next-node [unvisited distances]
  (apply min-key
         (fn [node] (get-distance node distances))
         unvisited))

(defn build-path [distances end]
  (let [cost (:distance (distances end))]
    (loop [path (list), cur end]
      (if (= cur [0 0])
        {:cost cost, :path path}
        (let [{:keys [through]} (distances cur)]
          (recur (conj path cur) through))))))

(defn step [current unvisited distances risk-map]
  (let [neighbors (set/intersection unvisited (neighbors current risk-map))
        distance (:distance (distances current))]
    (reduce (fn [distances neighbor]
              (let [distance (+ distance (get-risk risk-map neighbor))]
                (if (< distance (get-distance neighbor distances))
                  (assoc distances neighbor {:distance distance
                                             :through current})
                  distances)))
            distances
            neighbors)))

(defn cheapest-path [risk-map]
  (let [end [(dec (count (first risk-map)))
             (dec (count risk-map))]]
    (loop [unvisited (set (all-points risk-map))
           distances {[0 0] {:distance 0, :through nil}}]
      (let [current (next-node unvisited distances)]
        (if (= current end)
          (build-path distances current)
          (let [distances (step current unvisited distances risk-map)]
            (recur (disj unvisited current)
                   distances)))))))

(defn part1 [risk-map]
  (:cost (cheapest-path risk-map)))

(defn -main []
  (let [risk-map (read-input)]
    (println "Part 1:" (part1 risk-map))))
