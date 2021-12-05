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
  (parse-input (slurp "inputs/day04")))

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
  (let [lines (concat (rows board) (columns board))]
    (and (some #(every? :marked %) lines) board)))

(defn play-game [{:keys [draws boards]}]
  (reductions (fn [[boards _ _] draw]
                (let [marked (mark-boards boards draw)
                      winner (some winner? marked)
                      not-yet-winners (filter (comp not winner?) marked)]
                  [not-yet-winners draw winner]))
              [boards nil nil]
              draws))

(defn first-winner [state]
  (->> (play-game state)
       (some (fn [[_ last-draw last-winner]]
               (and last-winner [last-winner last-draw])))))

(defn sum-unmarked [board]
  (->> board
       flatten
       (filter (comp not :marked))
       (map :value)
       (apply +)))

(defn score [[winner last-draw]]
  (* (sum-unmarked winner) last-draw))

(defn part1 [state]
  (score (first-winner state)))

(defn last-winner [{:keys [draws boards], :as state}]
  (->> (play-game state)
       (some (fn [[boards last-draw last-winner]]
               (and (empty? boards) [last-winner last-draw])))))

(defn part2 [state]
  (score (last-winner state)))

(defn -main []
  (let [state (-> (read-input)
                  prep-boards)]
    (println "Part 1: " (part1 state))
    (println "Part 2: " (part2 state))))
