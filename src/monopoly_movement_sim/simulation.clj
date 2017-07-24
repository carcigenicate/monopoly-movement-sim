(ns monopoly-movement-sim.simulation
  (:require [monopoly-movement-sim.decks :as d]
            [monopoly-movement-sim.game-state.game-state :as s]
            [monopoly-movement-sim.game-state.helpers :as sh]
            [monopoly-movement-sim.tiles.action-tiles :as at]
            [monopoly-movement-sim.tiles.misc-tiles :as mt]
            [monopoly-movement-sim.board-layout :as bl]

            [helpers.general-helpers :as g]))


(def n-dice 2)
(def max-allowed-consec-multiples
  "The maximum number of \"doubles\" a player is allowed to roll before being sent to jail."
  3)

(defrecord Simulation-State [game-state tiles-visited])

(defn new-state [rand-gen]
  (->Simulation-State (s/new-state rand-gen) []))

(defn roll-dice [n-dice rand-gen]
  (mapv (fn [_]
          (g/random-int 1 7 rand-gen))
        (range n-dice)))

(defn react-to-roll
  "Moves the player according to the roll, and records the roll if all the dice are the same."
  [game-state dice-rolls]
  (let [sum (apply + dice-rolls)]
    (-> game-state
      (sh/move-player-by sum)
      (update :consec-match-dice #(if (apply = dice-rolls)
                                    (inc %)
                                    0)))))

(defn too-many-consec-multiples? [game-state]
  (>= (:consec-match-dice game-state)
      max-allowed-consec-multiples))

(defn handle-too-many-consec-multiples
  "Sends the player to jail if they've rolled max-allowed-consec-multiples many \"doubles\"."
  [game-state]
  (if (too-many-consec-multiples? game-state)
    (-> game-state
      (sh/goto-tile ::mt/jail)
      (assoc :consec-match-dice 0))

    game-state))

(defn tile-occupied-by-player [game-state]
  (bl/board-layout (sh/player-position game-state)))

(defn record-tile [state tile]
  (update state :tiles-visited #(conj % tile)))

(defn same-player-position? [& game-states]
  (apply =
    (map :player-index game-states)))

(defn move-player
  "Moves the player according to the roll, and deals with the concequences of landing on a tile."
  [state dice-rolls rand-gen]
  (let [{gs :game-state tv :tiles-visited} state
        moved-gs (react-to-roll gs dice-rolls)
        landed-tile (tile-occupied-by-player moved-gs)
        tile-action (s/tile-action landed-tile rand-gen)
        recorded-sim-state (record-tile state landed-tile)

        affected-game-state (if tile-action
                              (tile-action moved-gs)
                              moved-gs)

        recorded-altered-sim-state
        (if (same-player-position? moved-gs affected-game-state)
          recorded-sim-state
          (record-tile recorded-sim-state (tile-occupied-by-player affected-game-state)))]

    (assoc recorded-altered-sim-state :game-state affected-game-state)))

(defn sim-turn [state rand-gen]
  (let [rolls (roll-dice n-dice rand-gen)
        moved-state (move-player state rolls rand-gen)]

    moved-state))

(defn sim-turns-while [state pred rand-gen]
  (loop [sim-state state]
    (let [{gs :game-state tv :tiles-visited} state]

      (if (pred sim-state)
        (let [jailed-state (update sim-state :game-state
                                   handle-too-many-consec-multiples)
              advanced-sim-state (sim-turn jailed-state rand-gen)]
          (recur advanced-sim-state))

        sim-state))))






