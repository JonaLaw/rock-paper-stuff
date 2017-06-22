(ns rock-paper-stuff.util)

(def stuff [:rock :paper :scissors :fire :water])

(defn possessions
  "Returns a collections of all of the kinds of stuff for which the player
  has non-zero inventory."
  [player]
  (map first (filter (comp pos? second) 
                     (:inventory player))))

(defn legitimize
  "Returns a legitimate kind of stuff for the player to play. If the provided
  thing is legitimate, then it is returned. Otherwise, a random legitimate
  kind of stuff is returned."
  [thing player]
  (if (and (some #{thing} stuff)
           (pos? (thing (:inventory player))))
    thing
    (rand-nth (possessions player))))

(defn player
  "Retuns a Rock Paper Stuff player with the provided name and player function.
  A player is a map of: 
  {:name <string>
   :inventory <map with integers for keys:rock :paper :scissors :fire :water>
   :function <fn -- player_functions.clj>
   :history <vec of maps of :other, :play, :other-play; grows at the end>
   :memory <map under player's control>
   :skin <map under player's control except includes honest :inventory>
   :alive <initially true, becomes false if inventory depleted>}"
  [name function]
  {:name name
   :inventory {:rock 0 :paper 0 :scissors 0 :fire 0 :water 0}
   :function function
   :history []
   :memory {}
   :skin {}
   :alive true})

