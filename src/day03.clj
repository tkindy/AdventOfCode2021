(ns day03
  (:require [clojure.string :refer [split]]))

(defn parse-input [input]
  (->> (split input #"\n")
       (map (fn [line]
              (map #(Integer/parseInt (str %)) line)))
       (into [])))

(defn read-input []
  (parse-input (slurp "inputs/day3")))

(defn get-digits [i numbers]
  (map #(nth % i) numbers))

(defn max-count [counts]
  (key (apply max-key val counts)))

(defn bit-winner [selector tiebreaker bits]
  (let [{zeroes 0, ones 1, :as counts}
        (reduce (fn [counts digit]
                  (update counts digit
                          (fn [old] (if (nil? old) 1 (+ old 1)))))
                {}
                bits)]
    (if (= zeroes ones)
      tiebreaker
      (key (apply selector val counts)))))

(defn most-common-digit [numbers i]
  (->> numbers
       (get-digits i)
       (bit-winner max-key 0)))

(defn most-common-digits [numbers]
  (let [length (count (first numbers))]
    (map (fn [i] (most-common-digit numbers i))
         (range length))))

(defn bits->decimal [bits]
  (Integer/parseInt (apply str bits) 2))

(defn flip-bits [bits]
  (map (fn [bit] (if (= 0 bit) 1 0)) bits))

(defn part1 [numbers]
  (let [most-common (most-common-digits numbers)
        gamma (bits->decimal most-common)
        epsilon (bits->decimal (flip-bits most-common))]
    (* gamma epsilon)))

(defn filter1 [numbers i selector tiebreaker]
  (let [bits (get-digits i numbers)
        winner (bit-winner selector tiebreaker bits)]
    (filter (fn [number] (= (nth number i) winner))
            numbers)))

(defn filter-nums [numbers selector tiebreaker]
  (->> (count (first numbers))
       range
       (reduce (fn [nums i]
                 (filter1 nums i selector tiebreaker))
               numbers)
       first
       bits->decimal))

(defn part2 [numbers]
  (let [oxygen (filter-nums numbers max-key 1)
        co2 (filter-nums numbers min-key 0)]
    (* oxygen co2)))

(defn -main []
  (let [numbers (read-input)]
    (println "Part 1: " (part1 numbers))
    (println "Part 2: " (part2 numbers))))
