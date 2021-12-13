(ns day13
  (:require [clojure.string :as str]))

(defn parse-dot [line]
  (let [[x y] (map #(Integer/parseInt %) (str/split line #","))]
    {:x x, :y y}))

(defn parse-dots [dots]
  (set (map parse-dot (str/split-lines dots))))

(def fold-pattern #"^fold along (x|y)=(\d+)$")

(defn parse-fold [line]
  (let [[_ axis value] (re-find fold-pattern line)]
    {:axis (keyword axis) :value (Integer/parseInt value)}))

(defn parse-folds [folds]
  (map parse-fold (str/split-lines folds)))

(defn parse-input [input]
  (let [[dots folds] (str/split input #"\n\n")]
    {:dots (parse-dots dots)
     :folds (parse-folds folds)}))

(defn read-input []
  (parse-input (slurp "inputs/day13.txt")))

(defn make-fold-point [point {axis-value :value, :keys [axis]}]
  (let [point-value (point axis)]
    (if (< point-value axis-value)
      point
      (assoc point axis (- (* 2 axis-value) point-value)))))

(defn make-fold [dots fold]
  (->> dots
       (map (fn [dot] (make-fold-point dot fold)))
       set))

(defn part1 [{:keys [dots folds]}]
  (count (make-fold dots (first folds))))

(defn -main []
  (let [state (read-input)]
    (println "Part 1:" (part1 state))))
