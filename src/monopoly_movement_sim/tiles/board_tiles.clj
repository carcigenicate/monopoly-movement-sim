(ns monopoly-movement-sim.tiles.board-tiles
  (:require [monopoly-movement-sim.tiles.property-tiles :as pt]
            [monopoly-movement-sim.tiles.action-tiles :as at]
            [monopoly-movement-sim.tiles.misc-tiles :as mt]))

(def property-markers #{::brown ::light-blue ::violet ::orange
                        ::red ::yellow ::green ::blue})