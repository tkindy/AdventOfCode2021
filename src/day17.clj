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

(defn -main [])
