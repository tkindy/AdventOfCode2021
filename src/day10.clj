(ns day10
  [:require [clojure.string :as str]])

(defn parse-line [line]
  (apply list line))

(defn parse-input [input]
  (map parse-line (str/split-lines input)))

(defn read-input []
  (parse-input (slurp "inputs/day10.txt")))

(def matches {\( \)
              \[ \]
              \{ \}
              \< \>})

(defn open? [char]
  (matches char))

(defn illegal-char [line]
  (let [char (reduce (fn [stack char]
                       (cond
                         (open? char) (conj stack char)
                         (= (matches (peek stack)) char) (pop stack)
                         :else (reduced char)))
                     '()
                     line)]
    (if (char? char)
      char
      nil)))

(defn illegal-chars [code]
  (map illegal-char code))

(def scores {\) 3
             \] 57
             \} 1197
             \> 25137})

(defn part1 [code]
  (->> code
       illegal-chars
       (filter (comp not nil?))
       (map scores)
       (apply +)))

(defn -main []
  (let [code (read-input)]
    (println "Part 1: " (part1 code))))
