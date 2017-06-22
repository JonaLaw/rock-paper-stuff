(ns rock-paper-stuff.player-functions
  (:require [rock-paper-stuff.util :as u]))

;; a player function is called on the player itself and the skin of
;; another player, and should return a map that contains the following
;; key/value pairs (but all are actually optional):
;;
;; {:play <item from #{:rock :paper :scissors :fire :water}>
;;  :memory <map that will be retained as :memory>
;;  :skin <map that will be retained as :skin, with :name and :inventory added}

(defn random-pf
  "A player function that returns a nil :play, which means that the system
  will pick a random kind of stuff to play."
  [self other-skin]
  {:play nil})

(defn rock-pf
  "A player function that always plays :rock."
  [self other-skin]
  {:play :rock})

(defn paper-pf
  "A player function that always plays :paper."
  [self other-skin]
  {:play :paper})

(defn scissors-pf
  "A player function that always plays :scissors."
  [self other-skin]
  {:play :scissors})

(defn fire-pf
  "A player function that always plays :fire."
  [self other-skin]
  {:play :fire})

(defn water-pf
  "A player function that always plays :water."
  [self other-skin]
  {:play :water})

(defn majority-pf
  "A player function that plays one of the kinds of stuff of which the 
  player has most."
  [self other-skin]
  {:play (let [max-val (apply max (vals (:inventory self)))
               candidates (filter (fn [[k v]] (= v max-val)) (:inventory self))]
           (first (first (shuffle candidates))))})

(defn minority-pf
  "A player function that plays one of the kinds of stuff of which the
  player has least."
  [self other-skin]
  {:play (let [min-val (apply min (vals (:inventory self)))
               candidates (filter (fn [[k v]] (= v min-val)) (:inventory self))]
           (first (first (shuffle candidates))))})

(defn water-hoarder-pf
  "A player function that never plays :water, but otherwise plays randomly."
  [self other-skin]
  {:play (let [non-water (remove #(= % :water) (u/possessions self))]
           (if (empty? non-water)
             nil
             (rand-nth non-water)))})

(defn harry-hater-pf
  "A player function that plays :paper against players named Harry, and
  :water against all others."
  [self other-skin]
  {:play (if (= (:name other-skin) "Harry")
           :paper
           :water)})

(defn margie-hater-pf
  "A player that assumes that its opponent is using the majority-pf player
  function, and plays in a way intended to harm that opponent."
  [self other-skin]
  {:play (case (:play (majority-pf {:inventory (:inventory other-skin)} {}))
           :paper :water
           :scissors :fire
           :fire :water
           :rock :water
           :water :scissors)})

