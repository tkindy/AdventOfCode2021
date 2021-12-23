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

(defn read-input []
  (parse-input (slurp "inputs/day18.txt")))

(defn find-reducable [number]
  (letfn [(helper [number path]
            (cond
              (number? number) (if (>= number 10)
                                 path
                                 nil)
              :else (if (>= (count path) 4)
                      path
                      (or (helper (:left number) (conj path :left))
                          (helper (:right number) (conj path :right))))))]
    (helper number [])))

;; TODO
(defn split [number reduce-path]
  number)

;; TODO
(defn explode [number reduce-path]
  number)

(defn reduce-once [number reduce-path]
  (let [reducable (get-in number reduce-path)]
    (if (number? reducable)
      (split number reduce-path)
      (explode number reduce-path))))

(defn reduce-number [number]
  (let [reduce-path (find-reducable number)]
    (if (nil? reduce-path)
      number
      (reduce-number (reduce-once number reduce-path)))))

(defn add [n1 n2]
  (reduce-number {:left n1, :right n2}))

(defn sum [numbers]
  (reduce add numbers))

(defn magnitude [number]
  (if (number? number)
    number
    (+ (* 3 (magnitude (:left number)))
       (* 2 (magnitude (:right number))))))

(defn part1 [numbers]
  (-> numbers
      sum
      magnitude))

(defn -main []
  (let [numbers (read-input)]
    (println "Part 1:" (part1 numbers))))
