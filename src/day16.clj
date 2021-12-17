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
  (Integer/parseInt (apply str bits) 2))

(defn take-bits [n bits]
  [(take n bits) (drop n bits)])

(defn take-int [n bits]
  (let [[int-bits bits] (take-bits n bits)]
    [(bits->int int-bits) bits]))

(defn parse-input [input]
  (letfn [(parse-packet [bits]
            (let [[{:keys [type], :as header} bits] (parse-header bits)
                  body (if (= type 4)
                         (parse-literal bits)
                         (parse-operator bits))]
              [(-> (merge header body)
                   (dissoc :type))
               bits]))
          (parse-operator [bits]
            (let [[length-type bits] (take-int 1 bits)]
              (if (= length-type 0)
                (parse-operator-by-length bits)
                (parse-operator-by-count bits))))
          ;; TODO
          (parse-operator-by-length [bits]
            (let [[length bits] (take-int 15 bits)
                  bits (take length bits)
                  subpackets (loop [subpackets []
                                    bits bits]
                               ())]))
          ;; TODO
          (parse-operator-by-count [bits]
            {})
          (parse-literal [bits]
            {})
          (parse-header [bits]
            (let [[version bits] (take-int 3 bits)
                  [type bits] (take-int 3 bits)]
              [{:version version, :type type}, bits]))]
    (->> input
         decode
         parse-packet
         first)))

(defn read-input []
  (parse-input (slurp "inputs/day16.txt")))

(defn -main [])
