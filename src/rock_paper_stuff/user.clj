(ns rock-paper-stuff.user
  (:use [rock-paper-stuff util play]
        [clojure.pprint]))

;; Include your player functions and other definitions here.

(defn random-pf
  "A player function that returns a random kind of stuff to play."
  [self other-skin]
  {:play (rand-nth [:rock :paper :scissors :fire :water])})

(defn user-main
  "This is the function that will be called when 'lein run' is run on the command line."
  []
  ;; Include your top-level call here.
  ;;
  ;; In this example we play a game between two randomly-playing players, and 
  ;; pretty-print the summaries of all of the players at the end of the game.
  (pprint (map summary (:players (play-game [(player "Randy" random-pf)
                                             (player "Sandy" random-pf)])))))
