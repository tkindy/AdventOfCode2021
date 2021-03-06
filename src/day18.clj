(ns day18
  (:require [clojure.string :as str]))

(defn parse-number-helper [[left right]]
  (letfn [(process [v]
            (if (number? v)
              v
              (parse-number-helper v)))]
    {:left (process left), :right (process right)}))

(defn parse-number [line]
  (let [vector (read-string line)]
    (parse-number-helper vector)))

(defn parse-input [input]
  (map parse-number (str/split-lines input)))

(defn vec->number [v]
  (if (number? v)
    v
    (let [[left right] v]
      {:left (vec->number left)
       :right (vec->number right)})))

(defn number->vec [number]
  (if (number? number)
    number
    (let [{:keys [left right]} number]
      [(number->vec left), (number->vec right)])))

(defn read-input []
  (parse-input (slurp "inputs/day18.txt")))

(defn find-explode [number]
  (letfn [(helper [number path]
            (cond
              (number? number) nil
              :else (if (>= (count path) 4)
                      path
                      (or (helper (:left number) (conj path :left))
                          (helper (:right number) (conj path :right))))))]
    (helper number [])))

(defn find-split [number]
  (letfn [(helper [number path]
            (cond
              (number? number) (if (>= number 10)
                                 path
                                 nil)
              :else (or (helper (:left number) (conj path :left))
                        (helper (:right number) (conj path :right)))))]
    (helper number [])))

(defn find-reducable [number]
  (or (find-explode number)
      (find-split number)))

(defn split [number reduce-path]
  (update-in number
             reduce-path
             (fn [n]
               (let [left (quot n 2)
                     right (- n left)]
                 {:left left, :right right}))))

(defn first-towards [dir number path]
  (if (every? #(= % dir) path)
    nil
    (let [stem (->> path
                    reverse
                    (drop-while #(= % dir))
                    (drop 1)
                    reverse
                    vec)
          opp (if (= dir :left) :right :left)]
      (loop [path (conj stem dir)]
        (if (number? (get-in number path))
          path
          (recur (conj path opp)))))))

(defn first-left [number path]
  (first-towards :left number path))

(defn first-right [number path]
  (first-towards :right number path))

(defn explode [number reduce-path]
  (let [{:keys [left right]} (get-in number reduce-path)
        left-path (first-left number reduce-path)
        right-path (first-right number reduce-path)
        number (if left-path
                 (update-in number left-path + left)
                 number)
        number (if right-path
                 (update-in number right-path + right)
                 number)]
    (assoc-in number reduce-path 0)))

(defn reduce-once [number reduce-path]
  (let [reducable (get-in number reduce-path)]
    (if (number? reducable)
      (split number reduce-path)
      (explode number reduce-path))))

(defn reduction-steps [number]
  (lazy-seq
   (let [reduce-path (find-reducable number)]
     (if (nil? reduce-path)
       nil
       (let [next (reduce-once number reduce-path)]
         (cons next
               (reduction-steps next)))))))

(defn reduce-number [number]
  (or (last (reduction-steps number))
      number))

(defn add [n1 n2]
  (reduce-number {:left n1, :right n2}))

(defn sum-steps [numbers]
  (reductions add numbers))

(defn sum [numbers]
  (last (sum-steps numbers)))

(defn magnitude [number]
  (if (number? number)
    number
    (+ (* 3 (magnitude (:left number)))
       (* 2 (magnitude (:right number))))))

(defn part1 [numbers]
  (-> numbers
      sum
      magnitude))

(defn all-pairs [numbers]
  (mapcat (fn [number]
            (->> numbers
                 (filter #(not (= number %)))
                 (map (fn [n2] [number n2]))))
          numbers))

(defn part2 [numbers]
  (->> numbers
       all-pairs
       (map #(apply add %))
       (map magnitude)
       (apply max)))

(defn -main []
  (let [numbers (read-input)]
    (println "Part 1:" (part1 numbers))
    (println "Part 2:" (part2 numbers))))
