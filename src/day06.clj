(ns day06
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (mapv #(Integer/parseInt (str/trim %)) (str/split input #",")))

(defn read-input []
  (parse-input (slurp "inputs/day06.txt")))

(defn simulate1 [timers]
  (let [[updated new]
        (reduce (fn [[updated new] timer]
                  (if
                   (= timer 0)
                    [(conj updated 6) (conj new 8)]
                    [(conj updated (- timer 1)) new]))
                [[] []]
                timers)]
    (into [] (concat updated new))))

(defn simulate [timers days]
  (reduce (fn [timers _] (simulate1 timers))
          timers
          (range days)))

(defn part1 [timers]
  (let [simulated (simulate timers 80)]
    (count simulated)))

(defn -main []
  (let [timers (read-input)]
    (println "Part 1: " (part1 timers))))
