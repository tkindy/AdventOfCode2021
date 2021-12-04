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

(defn mark-board [board draw]
  (map (fn [row]
         (map (fn [{:keys [value marked], :as spot}]
                (let [marked (or marked (= value draw))]
                  (assoc spot :marked marked)))
              row))
       board))

(defn mark-boards [boards draw]
  (map #(mark-board % draw) boards))

(defn rows [board]
  board)

(defn columns [board]
  (map (fn [col]
         (map (fn [row]
                (nth row col))
              board))
       (range (count (first board)))))

(defn winner? [board]
  (->> (concat (rows board) (columns board))
       (some #(every? :marked %))))

(defn first-winner [{:keys [draws boards]}]
  (reduce (fn [boards draw]
            (let [boards (mark-boards boards draw)
                  winner (->> boards
                              (filter winner?)
                              first)]
              (if winner
                (reduced [winner draw])
                boards)))
          boards
          draws))

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
