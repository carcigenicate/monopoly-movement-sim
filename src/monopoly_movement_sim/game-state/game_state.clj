(ns monopoly-movement-sim.game-state.game-state
  (:require [monopoly-movement-sim.board-layout :as bl]
            [monopoly-movement-sim.game-state.helpers :as sh]

            [monopoly-movement-sim.tiles.action-tiles :as at]
            [monopoly-movement-sim.tiles.misc-tiles :as mt]

            [helpers.general-helpers :as g]
            [monopoly-movement-sim.decks :as d]))

(defrecord Game-State [player-index consec-match-dice chance-deck community-deck])

(defn new-state [rand-gen]
  (->Game-State 0 0
                (d/shuffled-chance-deck rand-gen)
                (d/shuffled-community-deck rand-gen)))

(defn- draw-from [state deck-key new-deck-f]
  (let [[card r-deck] (d/draw (deck-key state))
        new-deck (if (empty? r-deck)
                   (new-deck-f)
                   r-deck)]

    [card (assoc state deck-key new-deck)]))

(defn draw-from-chance-deck [state rand-gen]
  (draw-from state :chance-deck #(d/shuffled-chance-deck rand-gen)))

(defn draw-from-community-deck [state rand-gen]
  (draw-from state :community-deck #(d/shuffled-community-deck rand-gen)))

(defn tile-action [tile rand-gen]
  (case tile
    ::at/chance #(draw-from-chance-deck % rand-gen)
    ::at/community-chest #(draw-from-community-deck % rand-gen)
    ::at/goto-jail #(sh/goto-tile % ::mt/jail)))