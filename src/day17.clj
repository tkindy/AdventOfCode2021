(ns day17)

(def input-pattern #"^target area: x=(-?\d+)\.\.(-?\d+), y=(-?\d+)..(-?\d+)\n$")

(defn parse-input [input]

  (let [[min-x max-x min-y max-y]
        (->> input
             (re-matches input-pattern)
             (drop 1)
             (map #(Integer/parseInt %)))]
    {:x [min-x max-x] :y [min-y max-y]}))

(defn read-input []
  (parse-input (slurp "inputs/day17.txt")))

(defn y-values-help [vy y]
  (lazy-seq
   (cons y (y-values-help (dec vy) (+ y vy)))))

(defn y-values [vy0]
  (y-values-help vy0 0))

(defn relevant-y-values [vy0 min-range]
  (->> vy0
       y-values
       (take-while (fn [y] (>= y min-range)))))

(defn in-range? [n [min max]]
  (<= min n max))

(defn hits-y-range? [vy0 y-range]
  (let [[min-range _] y-range]
    (some (fn [y] (in-range? y y-range))
          (relevant-y-values vy0 min-range))))

(defn max-vy0 [y-range]
  (->> (range)
       (take-while (fn [vy0] (hits-y-range? vy0 y-range)))
       last))

;; TODO
(defn max-vx0 [[min-x max-x]]
  0)

(defn max-v0 [{:keys [x y]}]
  [(max-vx0 x) (max-vy0 y)])

(defn apex [vy0]
  (/ (* vy0 (inc vy0)) 2))

(defn part1 [target]
  (let [[_ vy0] (max-v0 target)]
    (apex vy0)))

(defn -main []
  (let [target (read-input)]
    (println "Part 1:" (part1 target))))
