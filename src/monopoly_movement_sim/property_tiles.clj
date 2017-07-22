(ns monopoly-movement-sim.property-tiles)

(def colored-tiles
  #{})

(def rail-roads
  #{::reading-rr ::pennsylvania-rr ::bo-rr ::short-rr})

(def utilities
  #{::electric ::water})

(def colors
  #{::brown ::light-blue ::violet ::orange ::red ::yellow ::green ::blue})
#_
(def colored-properties
  #{; Brown
    ::mediterranean ::baltic

    ; Light Blue
    ::oriental ::vermont ::connecticut

    ; Violet
    ::st-charles ::states ::virginia

    ; Orange
    ::st-james ::tennessee ::new-york

    ; Red
    ::kentucky ::indiana ::illinois

    ; Yellow
    ::atlantic ::ventor ::marvin

    ; Green
    ::pacific ::north-carolina ::pennsylvania

    ; Blue
    ::park ::boardwalk})

; TODO: Make less repetitive. Duplicate color values.
(def property-colors
  {::mediterranean ::brown,
   ::baltic ::brown

   ::oriental ::light-blue,
   ::vermont ::light-blue,
   ::connecticut ::light-blue,

   ::st-charles ::violet,
   ::states ::violet,
   ::virginia ::violet,

   ::st-james ::orange,
   ::tennessee ::orange,
   ::new-york ::orange,

   ::kentucky ::red,
   ::indiana ::red,
   ::illinois, ::red,

   ::atlantic ::yellow,
   ::ventor ::yellow,
   ::marvin ::yellow,

   ::pacific ::green,
   ::north-carolina ::green,
   ::pennsylvania ::green,

   ::park ::blue,
   ::boardwalk ::blue})