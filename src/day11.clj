(ns day11
  (:require [clojure.string :as str]))

(defn parse-line [line]
  (mapv #(Integer/parseInt (str %)) line))

(defn parse-input [input]
  (mapv parse-line (str/split-lines input)))

(defn read-input []
  (parse-input (slurp "inputs/day11.txt")))

(defn inc-all [energies]
  (mapv (fn [row] (mapv inc row))
        energies))

(defn points [energies]
  (for [y (range (count energies))
        x (range (count (first energies)))]
    [x y]))

(defn get-energy [[x y] energies]
  (-> energies
      (nth y)
      (nth x)))

(defn ready-to-flash? [point energies]
  (> (get-energy point energies) 9))

(defn ready-to-flash [energies]
  (filter (fn [point] (ready-to-flash? point energies))
          (points energies)))

(defn neighbors [[x y] energies]
  (let [min-x 0
        max-x (dec (count (first energies)))
        min-y 0
        max-y (dec (count energies))
        all-points (for [y (range (dec y) (+ y 2))
                         x (range (dec x) (+ x 2))]
                     [x y])]
    (filter (fn [[nx ny]]
              (and (not (and (= nx x) (= ny y)))
                   (<= min-x nx max-x)
                   (<= min-y ny max-y)))
            all-points)))

(defn inc-energy [[x y] energies]
  (update-in energies [y x] inc))

(defn handle-flash [energies point]
  (reduce (fn [[energies newly-ready] neighbor]
            (let [energies (inc-energy neighbor energies)
                  newly-ready (if (ready-to-flash? neighbor energies)
                                (conj newly-ready neighbor)
                                newly-ready)]
              [energies newly-ready]))
          [energies []]
          (neighbors point energies)))

(defn handle-flashes [energies]
  (loop [energies energies
         flashed #{}
         frontier (ready-to-flash energies)]
    (if (empty? frontier)
      energies
      (let [point (first frontier)]
        (if (flashed point)
          (recur energies flashed (rest frontier))
          (let [[energies newly-ready] (handle-flash energies point)]
            (recur energies (conj flashed point) (concat (rest frontier) newly-ready))))))))

(defn reset-flashed [energies]
  (map (fn [row]
         (map (fn [energy]
                (if (> energy 9)
                  0
                  energy))
              row))
       energies))

(defn step [energies]
  (-> energies
      inc-all
      handle-flashes
      reset-flashed))

(defn count-zeroes [energies]
  (->> energies
       flatten
       (filter zero?)
       count))

(defn count-flashes [energies steps]
  (let [[_ flashes]
        (reduce (fn [[energies flashes] _]
                  (let [energies (step energies)
                        new-flashes (count-zeroes energies)]
                    [energies (+ flashes new-flashes)]))
                [energies 0]
                (range steps))]
    flashes))

(defn part1 [energies]
  (count-flashes energies 100))

(defn all-zeroes? [energies]
  (every? (fn [row] (every? zero? row)) energies))

(defn part2 [energies]
  (loop [i 0
         energies energies]
    (if (all-zeroes? energies)
      i
      (recur (inc i) (step energies)))))

(defn -main []
  (let [energies (read-input)]
    (println "Part 1: " (part1 energies))
    (println "Part 2: " (part2 energies))))
