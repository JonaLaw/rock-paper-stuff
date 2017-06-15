(ns rock-paper-stuff.util)

(def stuff [:rock :paper :scissors :fire :water])

(defn legitimize
  "Returns a legitimate kind of stuff for Rock Paper Stuff. If the provided
  thing is legitimate, then it is returned. Otherwise, a random legitimate
  kind of stuff is returned."
  [thing]
  (if (some #{thing} stuff)
    thing
    (rand-nth stuff)))

(defn score
  "Returns the score of the provided player."
  [player]
  (+ (:water (:inventory player))
     (reduce + (filter neg? (vals (:inventory player))))))

(defn player
  "Retuns a Rock Paper Stuff player with the provided name and player function.
  A player is a map of: 
  {:name <string>
   :inventory <map with integers for keys:rock :paper :scissors :fire :water>
   :function <fn -- player_functions.clj>
   :history <vec of maps of :other, :play, :other-play; grows at the end>
   :memory <map under player's control>
   :skin <map under player's control except includes honest :inventory>}"
  [name function]
  {:name name
   :inventory {:rock 0 :paper 0 :scissors 0 :fire 0 :water 0}
   :function function
   :history []
   :memory {}
   :skin {}})
