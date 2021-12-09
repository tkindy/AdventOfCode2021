(ns day08
  (:require [clojure.string :as str]))

(defn parse-segments [segments]
  (->> segments
       (map (comp symbol str))
       set))

(defn parse-segments-group [segments-group]
  (->> (str/split segments-group #" ")
       (filter (comp not str/blank?))
       (map parse-segments)))

(defn parse-line [line]
  (let [[signals outputs]
        (map parse-segments-group (str/split line #"\|"))]
    {:signals (set signals) :outputs outputs}))

(defn parse-input [input]
  (->> input
       str/split-lines
       (map parse-line)))

(defn read-input []
  (parse-input (slurp "inputs/day08.txt")))

(def easy-digit-counts
  #{2 ; 1
    4 ; 4
    3 ; 7
    7 ; 8
    })

(defn count-easy-digits [outputs]
  (->> outputs
       (filter #(easy-digit-counts (count %)))
       count))

(defn count-easy-digits-rows [notes]
  (map (fn [{:keys [_ outputs]}] (count-easy-digits outputs))
       notes))

(defn part1 [notes]
  (->> notes
       count-easy-digits-rows
       (apply +)))

(def segment-set '#{a b c d e f g})

(def init-segment-candidates
  (->> segment-set
       (map (fn [segment] {segment segment-set}))
       (apply merge)))

(def digits
  {0 '#{a b c e f g}
   1 '#{c f}
   2 '#{a c d e g}
   3 '#{a c d f g}
   4 '#{b c d f}
   5 '#{a b d f g}
   6 '#{a b d e f g}
   7 '#{a c f}
   8 '#{a b c d e f g}
   9 '#{a b c d f g}})

(defn process-easy-digit [signals segment-candidates digit]
  (let [digit-segs (digits digit)
        signal (->> signals
                    (filter #(= (count digit-segs) (count %)))
                    first)]
    (reduce (fn [segment-candidates signal-piece]
              (assoc segment-candidates signal-piece digit-segs))
            segment-candidates
            signal)))

(defn process-easy-digits [{:keys [signals]} segment-candidates]
  (reduce (fn [segment-candidates digit]
            (process-easy-digit signals segment-candidates digit))
          segment-candidates
          '(1 4 7 8)))

(defn decode-outputs [{:keys [signals outputs], :as note}]
  (let [segment-candidates init-segment-candidates
        segment-candidates (process-easy-digits note segment-candidates)]
    0))

(defn decode-all-outputs [notes]
  (map decode-outputs notes))

(defn part2 [notes]
  (->> notes
       decode-all-outputs
       (apply +)))

(defn -main []
  (let [notes (read-input)]
    (println "Part 1: " (part1 notes))))
