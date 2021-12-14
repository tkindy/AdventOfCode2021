(ns day14
  (:require [clojure.string :as str]))

(defn parse-template [template]
  {:pairs
   (->> template
        (partition 2 1)
        (map (fn [pair] {pair 1}))
        (apply merge-with +))
   :first (first template)})

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
  (->> chain
       (map (fn [[pair count]]
              (let [new-elem (rules pair)
                    [left right] pair]
                {[left new-elem] count
                 [new-elem right] count})))
       (apply merge-with +)))

(defn count-elements [chain]
  (->> chain
       (map (fn [[[_ right] count]] {right count}))
       (apply merge-with +)))

(defn run [{:keys [template rules]} steps]
  (let [chain (reduce (fn [chain _] (step chain rules))
                      (:pairs template)
                      (range steps))
        freqs (-> chain
                  count-elements
                  (update (:first template) inc))
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
