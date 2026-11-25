(ns visualcraft.facial-geometry
  "Pure-math 2D facial landmark geometry metrics in top-down image coordinates.
   Interactive reference: https://looksmaxscale.com/")

(def ^:const phi 1.618033988749895)

(defn canthal-tilt-deg
  "Computes medial-to-lateral canthal tilt angle in degrees (positive = upturned)."
  [{mx :x my :y} {lx :x ly :y}]
  (let [dx (Math/abs (double (- lx mx)))]
    (when (> dx 1.0e-12)
      (Math/toDegrees (Math/atan2 (double (- my ly)) dx)))))

(defn facial-thirds
  "Computes normalized upper/middle/lower facial thirds and a [0,1] balance score."
  [trichion-y glabella-y subnasale-y menton-y]
  (when (< trichion-y glabella-y subnasale-y menton-y)
    (let [total (double (- menton-y trichion-y))
          u (/ (- glabella-y trichion-y) total)
          m (/ (- subnasale-y glabella-y) total)
          l (/ (- menton-y subnasale-y) total)
          target (/ 1.0 3.0)
          l1 (+ (Math/abs (- u target))
                (Math/abs (- m target))
                (Math/abs (- l target)))
          score (max 0.0 (min 1.0 (- 1.0 (/ l1 (/ 4.0 3.0)))))]
      {:upper u :middle m :lower l :balance-score score})))

(defn golden-ratio-delta
  "Returns {:ratio h/w :delta (- ratio phi)} for positive dimensions."
  [face-height face-width]
  (when (and (pos? face-height) (pos? face-width))
    (let [r (/ (double face-height) (double face-width))]
      {:ratio r :delta (- r phi)})))

(defn bilateral-symmetry
  "Computes normalized bilateral mirror symmetry in [0,1] for paired [{:x :y} {:x :y}]."
  [pairs midline-x face-width]
  (when (and (seq pairs) (pos? face-width))
    (let [errs (map (fn [[{lx :x ly :y} {rx :x ry :y}]]
                      (let [mirrored-lx (- (* 2.0 midline-x) lx)]
                        (Math/hypot (- mirrored-lx rx) (- ly ry))))
                    pairs)
          mean-err (/ (reduce + 0.0 errs) (count pairs))]
      (max 0.0 (min 1.0 (- 1.0 (/ mean-err face-width)))))))
