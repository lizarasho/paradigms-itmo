(defn equalsSizes? [elements]
  (every? (fn [element] (== (count (first elements)) (count element))) elements))

(defn checkVectors [vectors]
  (every? (fn [x] (and (vector? x) (every? number? x))) vectors))

(defn checkScalar [k]
  (every? number? k))

(defn checkMatrices [matrices]
  (every? (fn [x] (and (vector? x)
                       (and (checkVectors x) (equalsSizes? x)))) matrices))

(defn checkTensor [tensor]
  (if (every? number? tensor)
    true
    (if (not-every? vector? tensor)
      false
      (if (equalsSizes? tensor)
        (every? checkTensor (apply mapv vector tensor))
        false))))

(defn compareTensors [tensors]
  (or (number? (first tensors)) (equalsSizes? tensors)))

(defn checkRecurrent [f & tensors]
  {:pre [(compareTensors tensors)]}
  (if (vector? (first tensors))
    (apply mapv (partial checkRecurrent f) tensors)
    (apply f tensors)))


(defn vectorOperation [f & vectors]
    {:pre [(and (checkVectors vectors)
                (equalsSizes? vectors))]}
    (apply mapv f vectors))

(defn matrixOperation [f & matrices]
  {:pre [(and (checkMatrices matrices)
              (equalsSizes? matrices))]}
  (apply mapv f matrices))

(defn tensorOperation [f & tensors]
  {:pre [(checkTensor (first tensors))]}
  (apply checkRecurrent f tensors))


(def v+ (partial vectorOperation +))
(def v- (partial vectorOperation -))
(def v* (partial vectorOperation *))

(def m+ (partial matrixOperation v+))
(def m- (partial matrixOperation v-))
(def m* (partial matrixOperation v*))

(def t+ (partial tensorOperation +))
(def t- (partial tensorOperation -))
(def t* (partial tensorOperation *))

(defn scalar [& vectors]
  {:pre [(and (checkVectors vectors)
              (equalsSizes? vectors))]}
  (apply + (apply v* vectors)))

(defn v*s [vector & s]
  {:pre [(and (checkVectors (list vector))
              (checkScalar s))]}
  (mapv (fn [x] (* x (apply * s))) vector))

(defn m*s [matrix & s] (mapv (fn [x] (apply v*s x s)) matrix))

(defn m*v [matrix & v] (mapv (fn [x] (apply scalar x v)) matrix))

(defn transpose [matrix]
  (apply mapv vector matrix))

(defn m*m [& matrices]
  {:pre [(checkMatrices matrices)]}
  (reduce (fn [x y]
            (mapv (fn [z] (m*v (transpose y) z)) x))
          matrices))

(defn coordSubtract [x y v1 v2]
  (- (* (nth x v1) (nth y v2))
     (* (nth y v1) (nth x v2))))

(defn vect [& vectors]
  {:pre [(and (checkVectors vectors) (equalsSizes? vectors))]}
  (reduce (fn [x y]
            (vector (coordSubtract x y 1 2)
                    (coordSubtract x y 2 0)
                    (coordSubtract x y 0 1)))
          vectors))