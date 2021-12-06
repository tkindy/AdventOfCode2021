(ns day06
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (->> (str/split input #",")
       (map #(Integer/parseInt (str/trim %)))
       (map (fn [timer] {timer 1}))
       (apply merge-with +)))

(defn read-input []
  (parse-input (slurp "inputs/day06.txt")))

(defn simulate1 [timers]
  (reduce
   (fn [timers [timer count]]
     (let [add-count
           (fn [timers timer] (update timers timer #(+ (or % 0) count)))]
       (if (= timer 0)
         (-> timers
             (add-count 8)
             (add-count 6))
         (add-count timers (- timer 1)))))
   {}
   timers))

(defn simulate [timers days]
  (reduce (fn [timers _] (simulate1 timers))
          timers
          (range days)))

(defn count-fish [timers]
  (->> timers
       (map val)
       (apply +)))

(defn part1 [timers]
  (count-fish (simulate timers 80)))

(defn part2 [timers]
  (count-fish (simulate timers 256)))

(defn -main []
  (let [timers (read-input)]
    (println "Part 1: " (part1 timers))
    (println "Part 2: " (part2 timers))))
