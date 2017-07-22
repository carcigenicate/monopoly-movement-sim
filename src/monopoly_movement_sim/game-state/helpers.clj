(ns monopoly-movement-sim.game-state.helpers
  (:require [monopoly-movement-sim.board-layout :as bl]
            [helpers.general-helpers :as g]))

(defn wrap-board-index [i]
  (g/wrap i
          0 (dec (count bl/board-layout))))

(defn move-player-by [state n-tiles]
  (update state :player-index
          #(wrap-board-index
             (+ % n-tiles))))

(defn move-player-to [state tile-i]
  (assoc state :player-index
               (wrap-board-index tile-i)))

(defn player-position [state]
  (get state :player-index))

(defn- goto-closest [state indices]
  (update state :player-index
          #(bl/closest-index indices %)))

(defn goto-tile [state tile-key]
  (goto-closest state (bl/tile-indices tile-key)))

(defn goto-closest-satisfying [state pred]
  (goto-closest state (bl/indexes-satisfying pred)))