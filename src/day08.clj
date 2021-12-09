(ns day08
  (:require [clojure.string :as str]
            [clojure.set :as set]))

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

(defn common-components [signals]
  (apply set/intersection signals))

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
(def reverse-digits
  (->> digits
       (map (fn [[k v]] [v k]))
       (into {})))
(def digits-by-num-segments
  (group-by (comp count digits) (keys digits)))
(def common-components-by-length
  (->> digits-by-num-segments
       (map (fn [[num-segments digits-of-len]]
              [num-segments
               (->> digits-of-len
                    (map digits)
                    common-components)]))
       (into {})))

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

(defn common-components [signals]
  (apply set/intersection signals))

(defn remove-solved [segment-candidates solved]
  (let [solved-segment (first (segment-candidates solved))]
    (->> segment-candidates
         (map (fn [[signal candidates]]
                [signal
                 (if (= signal solved)
                   candidates
                   (disj candidates solved-segment))]))
         (into {}))))

(defn eliminate-candidates [segment-candidates length signals]
  (let [common-in-signals (common-components signals)
        common-in-display (common-components-by-length length)]
    (reduce (fn [segment-candidates signal]
              (let [new-candidates (set/intersection (segment-candidates signal) common-in-display)
                    segment-candidates (assoc segment-candidates
                                              signal
                                              new-candidates)]
                (if (= (count new-candidates) 1)
                  (remove-solved segment-candidates signal)
                  segment-candidates)))
            segment-candidates
            common-in-signals)))

(defn find-segment-mapping [signals]
  (->> signals
       (group-by count)
       (reduce (fn [segment-candidates [length signals]]
                 (eliminate-candidates segment-candidates length signals))
               init-segment-candidates)
       (map (fn [[signal segments]] [signal (first segments)]))
       (into {})))

(defn decode-output [output segment-map]
  (->> output
       (map (comp segment-map symbol str))
       set
       reverse-digits))

(defn decode-outputs [{:keys [signals outputs]}]
  (let [segment-map (find-segment-mapping signals)
        digits (map (fn [output] (decode-output output segment-map))
                    outputs)]
    (Integer/parseInt (apply str digits))))

(defn decode-all-outputs [notes]
  (map decode-outputs notes))

(defn part2 [notes]
  (->> notes
       decode-all-outputs
       (apply +)))

(defn -main []
  (let [notes (read-input)]
    (println "Part 1: " (part1 notes))))
