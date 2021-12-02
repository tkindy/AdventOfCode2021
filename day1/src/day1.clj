(ns day1
  (:require [clojure.string :refer [split]]))

(defn parse-input [input]
  (->> (split input #"\n")
       (map #(Integer/parseInt %))))

(defn read-input []
  (parse-input (slurp "input")))

(defn consecutive-pairs [l]
  (partition 2 1 l))

(defn num-increases [depths]
  (->> (consecutive-pairs depths)
       (filter (fn [[a b]] (> b a)))
       count))

(defn -main []
  (let [depths (read-input)]
    (println "Part 1: " (num-increases depths))))
