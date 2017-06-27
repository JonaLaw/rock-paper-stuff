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

(defn mean
  [coll]
  "https://github.com/clojure-cookbook/clojure-cookbook/blob/master/01_primitive-data/1-20_simple-statistics.asciidoc"
  (let [sum (apply + coll)
        count (count coll)]
    (if (pos? count)
      (/ sum count)
      0)))

(defn stdev [nums]
  (let [mean (mean nums)]
    (Math/sqrt (/ (apply +' (map #(* (- % mean) (- % mean))
                                 nums))
                  (dec (count nums))))))

(defn deviance
  [player]
  (let [amounts (vals (:inventory player))]
    (if (zero? (reduce + amounts))
      1000000000000000
      (stdev amounts))))

(defn summary
  [player]
  {:name (:name player)
   :deviance (:deviance player)
   :inventory (:inventory player)})

(defn player
  "Retuns a Rock Paper Stuff player with the provided name and player function.
  A player is a map of: 
  {:name <string>
   :inventory <map with integers for keys:rock :paper :scissors :fire :water>
   :function <fn -- player_functions.clj>
   :history <vec of maps of :other, :play, :other-play; grows at the end>
   :memory <map under player's control>
   :skin <map under player's control except includes honest :inventory>
   :alive <made true on init, becomes false when inventory depleted>}"
  [name function]
  {:name name
   :inventory {:rock 0 :paper 0 :scissors 0 :fire 0 :water 0}
   :function function
   :history []
   :memory {}
   :skin {}
   :alive false})
