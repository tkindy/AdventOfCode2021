(ns day12
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(defn parse-line [line]
  (let [[a b] (map keyword (str/split line #"-"))]
    {a #{b}
     b #{a}}))

(defn parse-input [input]
  (->> input
       str/split-lines
       (map parse-line)
       (apply merge-with set/union)))

(defn read-input []
  (parse-input (slurp "inputs/day12.txt")))

(defn part1 [edges]
  0)

(defn -main []
  (let [edges (read-input)]
    (println "Part 1:" (part1 edges))))
