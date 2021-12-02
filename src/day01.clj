(ns day01
  (:require [clojure.string :refer [split]]))

(defn parse-input [input]
  (->> (split input #"\n")
       (map #(Integer/parseInt %))))

(defn read-input []
  (parse-input (slurp "inputs/day1")))

(defn sliding-windows [n l]
  (partition n 1 l))

(defn num-increases [depths]
  (->> (sliding-windows 2 depths)
       (filter (fn [[a b]] (> b a)))
       count))

(defn num-sum-increases [depths]
  (->> (sliding-windows 3 depths)
       (map #(apply + %))
       (sliding-windows 2)
       (filter (fn [[a b]] (> b a)))
       count))

(defn -main []
  (let [depths (read-input)]
    (println "Part 1: " (num-increases depths))
    (println "Part 2: " (num-sum-increases depths))))
