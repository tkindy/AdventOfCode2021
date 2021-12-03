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

(defn most-common [digits]
  (let [counts (reduce (fn [counts digit]
                         (update counts digit
                                 (fn [old] (if (nil? old) 1 (+ old 1)))))
                       {}
                       digits)]
    (key (apply max-key val counts))))

(defn most-common-digits [numbers]
  (let [length (count (first numbers))]
    (map (fn [i] (->> numbers
                      (get-digits i)
                      most-common))
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

(defn -main []
  (let [numbers (read-input)]
    (println "Part 1: " (part1 numbers))))
