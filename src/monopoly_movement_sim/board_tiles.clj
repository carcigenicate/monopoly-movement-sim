(ns monopoly-movement-sim.board-tiles)

(def non-property-tile-markers #{::jail ::goto-jail
                                 ::go ::chance ::community-chest
                                 ::free-parking ::income-tax ::luxury-tax})

(def property-markers #{::brown ::light-blue ::violet ::orange
                        ::red ::yellow ::green ::blue
                        ::railroad ::utility})

; TODO: Only 39!? Missing 1 tile
(def board-tiles
  [::brown
   ::community-chest
   ::brown

   ::income-tax
   ::railroad

   ::light-blue
   ::chance
   ::light-blue
   ::light-blue

   ::jail

   ::violet
   ::utility
   ::violet
   ::violet

   ::railroad

   ::orange
   ::community-chest
   ::orange
   ::orange

   ::free-parking

   ::red
   ::chance
   ::red
   ::red

   ::railroad

   ::yellow
   ::yellow
   ::utility
   ::yellow

   ::goto-jail

   ::green
   ::green
   ::community-chest
   ::green

   ::railroad
   ::chance

   ::blue
   ::luxury-tax
   ::blue])