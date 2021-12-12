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

(defn iterate-path [path edges visitable?]
  (let [cur-cave (peek path)
        adjacents (edges cur-cave)]
    (->> adjacents
         (reduce (fn [next-paths adjacent]
                   (if (visitable? adjacent path)
                     (conj next-paths (conj path adjacent))
                     next-paths))
                 #{}))))

(defn finished? [path]
  (= (peek path) :end))

(defn iterate-paths [paths edges visitable?]
  (let [iterated (set (mapcat #(iterate-path % edges visitable?) paths))
        finished (set (filter finished? iterated))
        in-progress (set/difference iterated finished)]
    [in-progress finished]))

(defn all-paths [edges visitable?]
  (loop [in-progress #{[:start]}
         finished    #{}]
    (if (empty? in-progress)
      finished
      (let [[in-progress newly-finished]
            (iterate-paths in-progress edges visitable?)]
        (recur in-progress (set/union finished newly-finished))))))

(defn big-cave? [cave]
  (let [name (name cave)]
    (= (str/upper-case name) name)))

(defn visited? [cave path]
  (some #(= % cave) path))

(defn part1-visitable? [cave path]
  (or (big-cave? cave) (not (visited? cave path))))

(defn can-revisit? [cave path]
  (and (not (#{:start :end} cave))
       (let [visit-counts (->> path
                               (map (fn [cave] {cave 1}))
                               (apply merge-with +))]
         (not-any? (fn [[cave visit-count]]
                     (and (not (big-cave? cave)) (> visit-count 1)))
                   visit-counts))))

(defn part2-visitable? [cave path]
  (or (part1-visitable? cave path) (can-revisit? cave path)))

(defn count-paths [edges visitable?]
  (count (all-paths edges visitable?)))

(defn part1 [edges]
  (count-paths edges part1-visitable?))

(defn part2 [edges]
  (count-paths edges part2-visitable?))

(defn -main []
  (let [edges (read-input)]
    (println "Part 1:" (part1 edges))
    (println "Part 2:" (part2 edges))))
