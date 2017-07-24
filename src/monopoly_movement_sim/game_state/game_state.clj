(ns monopoly-movement-sim.game-state.game-state
  (:require [monopoly-movement-sim.board-layout :as bl]
            [monopoly-movement-sim.game-state.helpers :as sh]

            [monopoly-movement-sim.tiles.action-tiles :as at]
            [monopoly-movement-sim.tiles.misc-tiles :as mt]

            [monopoly-movement-sim.decks :as d]))

(defrecord Game-State [player-index consec-match-dice chance-deck community-deck])

(defn new-state [rand-gen]
  (->Game-State 0 0
                (d/shuffled-chance-deck rand-gen)
                (d/shuffled-community-deck rand-gen)))

(defn- draw-from
  "Returns the drawn card, and the state that was modified as a result of drawing the card.
  new-deck-f should be a function that accepts a random generator, and returns a new shuffled deck."
  [state deck-key new-deck-f]
  (let [[card r-deck] (d/draw (deck-key state))
        new-deck (if (empty? r-deck)
                   (new-deck-f)
                   r-deck)]

    [card (assoc state deck-key new-deck)]))

(defn- draw-from-chance-deck [state rand-gen]
  (draw-from state :chance-deck #(d/shuffled-chance-deck rand-gen)))

(defn- draw-from-community-deck [state rand-gen]
  (draw-from state :community-deck #(d/shuffled-community-deck rand-gen)))

(defn- draw-and-effect
  "Draws a card using the provided draw-f, and uses it to affect the state if it's non-nil.
  Draw-f should accept the current state and a random generator, and return the modified state."
  [state draw-f rand-gen]
  (let [[card drawn-state] (draw-f state rand-gen)]
    (if card
      (card drawn-state)
      drawn-state)))

(defn tile-action
  "Returns either an action that modifies a given state, or nil if the provided tile doesn't have an action associated with it."
  [tile rand-gen]
  (case tile
    ::at/chance #(draw-and-effect % draw-from-chance-deck rand-gen)
    ::at/community-chest #(draw-and-effect % draw-from-community-deck rand-gen)
    ::at/goto-jail #(sh/goto-tile % ::mt/jail)
    nil))