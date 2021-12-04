(ns day04
  (:require [clojure.string :as s]))

(defn parse-draws [draws]
  (->> (s/split draws #",")
       (map #(Integer/parseInt %))))

(defn split-board-lines [lines]
  (->> (subvec lines 2)
       (filter #(not (.isBlank %)))
       (partition 5)))

(defn parse-board-line [board-line]
  (->> (s/split board-line #"\s+")
       (filter #(not (.isBlank %)))
       (map #(Integer/parseInt %))
       vec))

(defn parse-board [board-lines]
  (->> board-lines
       (map parse-board-line)
       vec))

(defn parse-boards [lines]
  (->> lines
       split-board-lines
       (map parse-board)))

(defn parse-input [input]
  (let [lines (s/split-lines input)]
    {:draws (parse-draws (first lines))
     :boards (parse-boards lines)}))

(defn read-input []
  (parse-input (slurp "inputs/day4")))

(defn -main [])
