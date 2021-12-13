(ns day13
  (:require [clojure.string :as str]))

(defn parse-dot [line]
  (map #(Integer/parseInt %) (str/split line #",")))

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

(defn -main [])
