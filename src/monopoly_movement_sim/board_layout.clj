(ns monopoly-movement-sim.board-layout
  (:require [monopoly-movement-sim.tiles.property-tiles :as pt]
            [monopoly-movement-sim.tiles.action-tiles :as at]
            [monopoly-movement-sim.tiles.misc-tiles :as mt]

            [helpers.general-helpers :as g]))

(def board-layout
  [::mt/go

   ::pt/mediterranean
   ::at/community-chest
   ::pt/baltic

   ::mt/income-tax
   ::pt/reading-rr

   ::pt/oriental
   ::at/chance
   ::pt/vermont
   ::pt/connecticut

   ::mt/jail

   ::pt/st-charles
   ::pt/electric
   ::pt/states
   ::pt/virginia

   ::pt/pennsylvania-rr

   ::pt/st-james
   ::at/community-chest
   ::pt/tennessee
   ::pt/new-york

   ::mt/free-parking

   ::pt/kentucky
   ::at/chance
   ::pt/indiana
   ::pt/illinois

   ::pt/bo-rr

   ::pt/atlantic
   ::pt/ventor
   ::pt/water
   ::pt/marvin

   ::at/goto-jail

   ::pt/pacific
   ::pt/north-carolina
   ::at/community-chest
   ::pt/pennsylvania

   ::pt/short-rr
   ::at/chance

   ::pt/park
   ::mt/luxury-tax
   ::pt/boardwalk])

(def enumerated-board
  (mapv vector (range) board-layout))

(def tile-indices
  (reduce (fn [acc [i tile]]
            (update acc tile #(conj % i)))
          {}
          enumerated-board))

(defn indexes-satisfying [pred]
  (map first
    (filter #(pred (second %)) enumerated-board)))

(defn closest-index [target-indices current-index]
  (first
    (reduce (fn [[acc-i acc-s-dist :as old] i]
              (let [raw-dist (- i current-index)
                    sqd-dist (* raw-dist raw-dist)]
                (if (< sqd-dist acc-s-dist)
                  [i sqd-dist]
                  old)))

            [nil Long/MAX_VALUE]

            target-indices)))
