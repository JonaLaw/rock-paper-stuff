(ns rock-paper-stuff.util)

(def stuff [:rock :paper :scissors :fire :water])

(defn legitimize
  "Returns a legitimate kind of stuff for the player to play. If the provided
  thing is legitimate, then it is returned. Otherwise, a random legitimate
  kind of stuff is returned."
  [thing player]
  (if (and (some #{thing} stuff)
           (pos? (thing (:inventory player))))
    thing
    (first (rand-nth (filter (comp pos? second) 
                             (:inventory player))))))

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
