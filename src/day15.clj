(ns day15
  (:require [clojure.string :as str]
            [clojure.set :as set]
            [day09 :refer [safe-nth]]
            [clojure.data.priority-map :refer [priority-map]]))

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
  (->> (neighbors current risk-map)
       (filter (partial contains? unvisited))
       (reduce (fn [[unvisited distances] point]
                 (let [distance (+ (:distance (distances current))
                                   (get-risk risk-map point))]
                   (if (< distance (get-distance point distances))
                     [(assoc unvisited point distance)
                      (assoc distances point {:distance distance
                                              :through current})]
                     [unvisited distances])))
               [unvisited distances])))

(defn build-init-unvisited [risk-map]
  (-> (reduce (fn [pm point]
                (assoc pm point Double/POSITIVE_INFINITY))
              (priority-map)
              (all-points risk-map))
      (assoc [0 0] 0)))

(defn cheapest-path [risk-map]
  (let [end [(dec (count (first risk-map)))
             (dec (count risk-map))]]
    (loop [unvisited (build-init-unvisited risk-map)
           distances {[0 0] {:distance 0, :through nil}}]
      (let [[current _] (peek unvisited)
            unvisited (pop unvisited)]
        (if (= current end)
          (build-path distances current)
          (let [[unvisited distances] (step current unvisited distances risk-map)]
            (recur unvisited distances)))))))

(defn part1 [risk-map]
  (:cost (cheapest-path risk-map)))

(defn -main []
  (let [risk-map (read-input)]
    (println "Part 1:" (part1 risk-map))))
