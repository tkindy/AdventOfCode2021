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

(defn prep-board [board]
  (mapv (fn [row]
          (mapv (fn [value]
                  {:value value, :marked false})
                row))
        board))

(defn prep-boards [{boards :boards, :as state}]
  (assoc state :boards (map prep-board boards)))

;; TODO
(defn first-winner [{:keys [draws boards]}]
  [(first boards) (first draws)])

(defn sum-unmarked [board]
  (->> board
       flatten
       (filter (comp not :marked))
       (map :value)
       (apply +)))

(defn part1 [state]
  (let [[winner last-draw] (first-winner state)]
    (* (sum-unmarked winner) last-draw)))

(defn -main []
  (let [state (-> (read-input)
                  prep-boards)]
    (println "Part 1: " (part1 state))))
