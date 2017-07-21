(ns monopoly-movement-sim.decks
  (:require [helpers.general-helpers :as g]))

; TODO: Find out how many cards are in a standard monopoly deck.
(def standard-deck-size 52)

(defn shuffle-deck [coll rand-gen]
  (g/shuffle coll rand-gen))

(defn filled-deck [legit-cards filler deck-size]
  (let [n-filler (- deck-size (count legit-cards))]
    (into
      legit-cards
      (repeat n-filler filler))))


(def community-chest-move-cards [])
(def community-chest-deck
  (filled-deck community-chest-move-cards nil standard-deck-size))

(def chance-move-cards [])
(def chance-deck
  (filled-deck chance-move-cards nil standard-deck-size))