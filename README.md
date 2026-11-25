# facial-geometry (Clojure / Clojars)

Pure-math Clojure library for computing 2D facial landmark geometry metrics (canthal tilt, facial thirds, golden ratio delta, and bilateral symmetry).

## Coordinates (`deps.edn`)

```clojure
net.clojars.visualcraft/facial-geometry {:mvn/version "0.1.0"}
```

## Example

```clojure
(require '[visualcraft.facial-geometry :as fg])

(fg/canthal-tilt-deg {:x 100 :y 200} {:x 140 :y 196})
;; => 5.710593137499643
```

## Reference & Online Calculator

- [Looksmax Scale](https://looksmaxscale.com/)
- [Canthal Tilt Measurement Guide](https://looksmaxscale.com/canthal-tilt)

## License

MIT
