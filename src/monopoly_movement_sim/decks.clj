(ns monopoly-movement-sim.decks
  (:require [helpers.general-helpers :as g]

            [monopoly-movement-sim.board-layout :as bl]
            [monopoly-movement-sim.game-state.helpers :as sh]

            [monopoly-movement-sim.tiles.misc-tiles :as mt]
            [monopoly-movement-sim.tiles.property-tiles :as pt]))

(def standard-deck-size 18)

(defn shuffle-deck [deck rand-gen]
  (g/shuffle deck rand-gen))

(defn filled-deck [legit-cards filler deck-size]
  (let [n-filler (- deck-size (count legit-cards))]
    (into
      legit-cards
      (repeat n-filler filler))))

(defn draw [deck]
  [(first deck) (rest deck)])

(def community-chest-move-cards
  [#(sh/goto-tile % ::mt/jail)
   #(sh/goto-tile % ::mt/go)])

; TODO: Currently doens't "activate" the tile that was landed on as the result of moving. If the "Go back 3 spaces" card causes the player to land on a community chest tile, a second card is not drawn. There's only a ~0.2% chance of this happening though, so the chance is low of it affecting results.
(def chance-move-cards
  (let [goto-rail #(sh/goto-closest-satisfying % pt/rail-roads)]
    [#(sh/goto-tile % ::mt/go)
     #(sh/goto-tile % ::pt/illinois)
     #(sh/goto-tile % ::pt/st-charles)
     #(sh/goto-closest-satisfying % pt/utilities)
     goto-rail goto-rail
     #(sh/move-player-by % -3)
     #(sh/goto-tile % ::pt/reading-rr)
     #(sh/goto-tile % ::pt/boardwalk)]))

(def community-chest-deck
  (filled-deck community-chest-move-cards nil standard-deck-size))

(def chance-deck
  (filled-deck chance-move-cards nil standard-deck-size))

(defn shuffled-chance-deck [rand-gen]
  (shuffle-deck chance-deck rand-gen))

(defn shuffled-community-deck [rand-gen]
  (shuffle-deck community-chest-deck rand-gen))