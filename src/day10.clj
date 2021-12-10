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

(defn match-chunks [line]
  (reduce (fn [stack char]
            (cond
              (open? char) (conj stack char)
              (= (matches (peek stack)) char) (pop stack)
              :else (reduced char)))
          '()
          line))

(defn illegal-char [line]
  (let [result (match-chunks line)]
    (if (char? result)
      result
      nil)))

(defn illegal-chars [code]
  (map illegal-char code))

(def illegal-char-score {\) 3
                         \] 57
                         \} 1197
                         \> 25137})

(defn part1 [code]
  (->> code
       illegal-chars
       (filter (comp not nil?))
       (map illegal-char-score)
       (apply +)))

(defn completion [line]
  (let [result (match-chunks line)]
    (if (list? result)
      (map matches result)
      nil)))

(defn completions [code]
  (map completion code))

(def completion-char-score {\) 1
                            \] 2
                            \} 3
                            \> 4})

(defn completion-score [completion]
  (reduce (fn [score char]
            (+ (* score 5) (completion-char-score char)))
          0
          completion))

(defn part2 [code]
  (let [scores (->> code
                    completions
                    (filter (comp not nil?))
                    (map completion-score))]
    (nth (sort scores)
         (int (/ (count scores) 2)))))

(defn -main []
  (let [code (read-input)]
    (println "Part 1: " (part1 code))
    (println "Part 2: " (part2 code))))
