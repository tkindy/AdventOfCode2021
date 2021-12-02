(ns day2
  (:require [clojure.string :refer [split]]))

(defn parse-input [input]
  (let [lines (split input #"\n")]
    (map (fn [line]
           (let [[direction distance] (split line #" ")]
             [(symbol direction) (Integer/parseInt distance)]))
         lines)))

(defn read-input []
  (parse-input (slurp "input")))

(defn -main []
  (println "Hello, world!"))
