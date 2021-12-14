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

(defn step [chain rules]
  (let [new-chain
        (->> chain
             (partition 2 1)
             (reduce (fn [chain pair]
                       (conj chain (first pair) (rules pair)))
                     []))]
    (conj new-chain (peek chain))))

(defn count-elements [chain]
  (->> chain
       (map (fn [element] {element 1}))
       (apply merge-with +)))

(defn run [{:keys [template rules]} steps]
  (let [chain (reduce (fn [chain _] (step chain rules))
                      template
                      (range steps))
        freqs (count-elements chain)
        min-count (apply min (vals freqs))
        max-count (apply max (vals freqs))]
    (- max-count min-count)))

(defn part1 [state]
  (run state 10))

(defn part2 [state]
  (run state 40))

(defn -main []
  (let [state (read-input)]
    (println "Part 1:" (part1 state))
    (println "Part 2:" (part2 state))))
