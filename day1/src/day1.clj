(ns day1
  (:require [clojure.string :refer [split]]))

(defn parse-input [input]
  (->> (split input #"\n")
       (map #(Integer/parseInt %))))

(defn read-input []
  (parse-input (slurp "input")))

(defn -main []
  (println "Hello, world!"))
