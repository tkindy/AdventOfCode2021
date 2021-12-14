(ns day14
  (:require [clojure.string :as str]))

(defn parse-template [template]
  (into [] template))

(defn parse-rule [rule]
  (let [[pair result] (str/split rule #" -> ")]
    {(into [] pair) (first result)}))

(defn parse-rules [rules]
  (->> rules
       str/split-lines
       (map parse-rule)
       (apply merge)))

(defn parse-input [input]
  (let [[template rules] (str/split input #"\n\n")]
    {:template (parse-template template)
     :rules (parse-rules rules)}))

(defn read-input []
  (parse-input (slurp "inputs/day14.txt")))

(defn -main [])
