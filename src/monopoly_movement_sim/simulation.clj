(ns monopoly-movement-sim.simulation
  (:require [monopoly-movement-sim.decks :as d]
            [monopoly-movement-sim.game-state.game-state :as s]
            [monopoly-movement-sim.tiles.action-tiles :as at]
            [monopoly-movement-sim.tiles.misc-tiles :as mt]

            [helpers.general-helpers :as g]
            [monopoly-movement-sim.board-layout :as bl]
            [monopoly-movement-sim.game-state.helpers :as sh]))

(def n-dice 2)
(def max-allowed-consec-multiples 3)

(defrecord Simulation-State [game-state tiles-visited])

(defn new-state [rand-gen]
  (->Simulation-State (s/new-state rand-gen) []))

(defn roll-dice [n-dice rand-gen]
  (mapv (fn [_]
          (g/random-int 0 6 rand-gen))
        (range n-dice)))

(defn react-to-roll [game-state dice-rolls]
  (let [sum (apply + dice-rolls)]
    (-> game-state
      (sh/move-player-by sum)

      (update :consec-match-dice #(if (apply = dice-rolls)
                                    (inc %)
                                    0)))))

(defn too-many-consec-multiples? [game-state]
  (> (:consec-match-dice game-state)
     max-allowed-consec-multiples))

(defn handle-too-many-consec-multiples [game-state]
  (if (too-many-consec-multiples? game-state)
    (sh/goto-tile game-state ::mt/jail)
    game-state))

(defn tile-player-landed-on [game-state]
  (bl/enumerated-board (sh/player-position game-state)))

(defn sim-turn [state rand-gen]
  (let [{gs :game-state} state

        rolls(roll-dice n-dice rand-gen)
        moved-gs (react-to-roll gs rolls)
        landed-tile (tile-player-landed-on moved-gs)
        tile-action (s/tile-action landed-tile rand-gen)

        affected-gs (if tile-action
                      (tile-action moved-gs)
                      moved-gs)]
    (-> state
        (update :tiles-visited #(conj % landed-tile))
        (assoc :game-state affected-gs))))

(defn sim-turns-while [state pred rand-gen]
  (loop [sim-state state]
    (let [{gs :game-state tv :tiles-visited} state]

      (if (pred sim-state)
        (let [jailed-gs (handle-too-many-consec-multiples gs)
              advanced-sim-state (sim-turn sim-state rand-gen)]
          (recur advanced-sim-state))

        sim-state))))






