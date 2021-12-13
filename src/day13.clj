(ns day13
  (:require [clojure.string :as str]))

(defn parse-dot [line]
  (let [[x y] (map #(Integer/parseInt %) (str/split line #","))]
    {:x x, :y y}))

(defn parse-dots [dots]
  (set (map parse-dot (str/split-lines dots))))

(def fold-pattern #"^fold along (x|y)=(\d+)$")

(defn parse-fold [line]
  (let [[_ axis value] (re-find fold-pattern line)]
    {:axis (keyword axis) :value (Integer/parseInt value)}))

(defn parse-folds [folds]
  (map parse-fold (str/split-lines folds)))

(defn parse-input [input]
  (let [[dots folds] (str/split input #"\n\n")]
    {:dots (parse-dots dots)
     :folds (parse-folds folds)}))

(defn read-input []
  (parse-input (slurp "inputs/day13.txt")))

(defn make-fold-point [point {axis-value :value, :keys [axis]}]
  (let [point-value (point axis)]
    (if (< point-value axis-value)
      point
      (assoc point axis (- (* 2 axis-value) point-value)))))

(defn make-fold [dots fold]
  (->> dots
       (map (fn [dot] (make-fold-point dot fold)))
       set))

(defn part1 [{:keys [dots folds]}]
  (count (make-fold dots (first folds))))

(defn dots->map [dots]
  (reduce (fn [m {:keys [x y] :as dot}]
            (assoc-in m [y x] dot))
          {}
          dots))

(defn draw-line [y line-map]
  (let [xs (keys line-map)]
    (->> (reduce (fn [line x]
                   (conj line (if (line-map x) "#" " ")))
                 []
                 (range (apply min xs) (inc (apply max xs))))
         (apply str))))

(defn draw-dots [dots]
  (let [dot-map (dots->map dots)
        ys (keys dot-map)]
    (->> (reduce (fn [drawing y]
                   (conj drawing (draw-line y (dot-map y))))
                 []
                 (range (apply min ys) (inc (apply max ys))))
         (str/join "\n"))))

(defn part2 [{:keys [dots folds]}]
  (let [dots (reduce make-fold dots folds)]
    (draw-dots dots)))

(defn -main []
  (let [state (read-input)]
    (println "Part 1:" (part1 state))
    (println "Part 2:")
    (println (part2 state))))
