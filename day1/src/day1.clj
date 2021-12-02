(ns day1
  (:require [clojure.string :refer [split]]))

(defn parse-input [input]
  (->> (split input #"\n")
       (map #(Integer/parseInt %))))

(defn read-input []
  (parse-input (slurp "input")))

(defn num-increases [depths]
  0)

(defn -main []
  (let [depths (read-input)]
    (println "Part 1: " (num-increases depths))))
