(ns day16)

(defn decode-char [char]
  (case char
    \0 [0 0 0 0]
    \1 [0 0 0 1]
    \2 [0 0 1 0]
    \3 [0 0 1 1]
    \4 [0 1 0 0]
    \5 [0 1 0 1]
    \6 [0 1 1 0]
    \7 [0 1 1 1]
    \8 [1 0 0 0]
    \9 [1 0 0 1]
    \A [1 0 1 0]
    \B [1 0 1 1]
    \C [1 1 0 0]
    \D [1 1 0 1]
    \E [1 1 1 0]
    \F [1 1 1 1]
    \newline []))

(defn decode [input]
  (mapcat decode-char input))

(defn bits->int [bits]
  (Long/parseLong (apply str bits) 2))

(defn take-bits [n bits]
  [(take n bits) (drop n bits)])

(defn take-int [n bits]
  (let [[int-bits bits] (take-bits n bits)]
    [(bits->int int-bits) bits]))

(defn parse-input [input]
  (letfn [(parse-packet [bits]
            (let [[{:keys [type], :as header} bits] (parse-header bits)
                  [body bits] (if (= type :literal)
                                (parse-literal bits)
                                (parse-operator bits))]
              [(merge header body) bits]))
          (parse-operator [bits]
            (let [[length-type bits] (take-int 1 bits)]
              (if (= length-type 0)
                (parse-operator-by-length bits)
                (parse-operator-by-count bits))))
          (parse-operator-by-length [bits]
            (let [[length bits] (take-int 15 bits)
                  [packet-bits bits] (take-bits length bits)
                  subpackets (loop [subpackets []
                                    bits packet-bits]
                               (if (empty? bits)
                                 subpackets
                                 (let [[packet bits] (parse-packet bits)]
                                   (recur (conj subpackets packet) bits))))]
              [{:subpackets subpackets} bits]))
          (parse-operator-by-count [bits]
            (let [[packet-count bits] (take-int 11 bits)
                  [subpackets bits] (reduce (fn [[subpackets bits] _]
                                              (let [[packet bits] (parse-packet bits)]
                                                [(conj subpackets packet) bits]))
                                            [[] bits]
                                            (range packet-count))]
              [{:subpackets subpackets} bits]))
          (parse-literal [bits]
            (loop [value-bits (list)
                   bits bits]
              (let [[chunk bits] (take-bits 5 bits)
                    value-bits (concat value-bits (drop 1 chunk))]
                (if (= (first chunk) 0)
                  [{:value (bits->int value-bits)} bits]
                  (recur value-bits bits)))))
          (parse-header [bits]
            (let [[version bits] (take-int 3 bits)
                  [type bits] (take-int 3 bits)
                  type (case type
                         0 :sum
                         1 :product
                         2 :min
                         3 :max
                         4 :literal
                         5 :greater-than
                         6 :less-than
                         7 :equal-to)]
              [{:version version, :type type}, bits]))]
    (->> input
         decode
         parse-packet
         first)))

(defn read-input []
  (parse-input (slurp "inputs/day16.txt")))

(defn sum-versions [{:keys [version subpackets]}]
  (apply + version (map sum-versions subpackets)))

(defn part1 [packet]
  (sum-versions packet))

(defn evaluate [{:keys [type value subpackets]}]
  (letfn [(aggregate [op]
            (apply op (map evaluate subpackets)))
          (compare [comp]
            (if (comp (evaluate (first subpackets))
                      (evaluate (second subpackets)))
              1
              0))]
    (case type
      :sum          (aggregate +)
      :product      (aggregate *)
      :min          (aggregate min)
      :max          (aggregate max)
      :literal      value
      :greater-than (compare >)
      :less-than    (compare <)
      :equal-to     (compare =))))

(defn part2 [packet]
  (evaluate packet))

(defn -main []
  (let [packet (read-input)]
    (println "Part 1:" (part1 packet))
    (println "Part 2:" (part2 packet))))
