(ns monopoly-movement-sim.main
  (require [monopoly-movement-sim.simulation :as sim]
           [monopoly-movement-sim.tiles.property-tiles :as pt]

           [helpers.general-helpers :as g]

           [clojure.string :as s])

  (:gen-class))

(defn sorted-visited-freqs [sim-state]
  (sort-by second
           (frequencies (:tiles-visited sim-state))))

(defn pretty-tile [tile]
  (let [cap #(s/capitalize (name %))
        pname (cap tile)
        raw-color (pt/property-colors tile)
        pcolor (if raw-color (cap raw-color) nil)]

    (str pname
         (if pcolor
           (str " - " pcolor)
           ""))))

(defn pretty-tile-freqs [tile-freqs]
  (s/join "\n"
    (mapv (fn [[tile freq]]
            (str (pretty-tile tile) ": " freq))
          tile-freqs)))

(defn -main [n-tile-max]
  ; Recasting to string to allow easy use in the REPL
  (if-let [parsed-n-tiles (g/parse-double (str n-tile-max))]
    (let [r (g/new-rand-gen)
          s (sim/new-state r)
          end-state (sim/sim-turns-while s
                      #(< (count (:tiles-visited %)) parsed-n-tiles)
                      r)]

      (println
        (pretty-tile-freqs
          (sorted-visited-freqs end-state))))

    (println n-tile-max "isn't a valid number of tiles to visit.")))



