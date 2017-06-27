(ns rock-paper-stuff.user
  (:use [rock-paper-stuff util play]
        [clojure.pprint]))

;; Include your player functions and other definitions here.

(defn user-main
  "This is the function that will be called when 'lein run' is run on the command line."
  []
  ;; Include your top-level call here.
  ;; In this example we play a game between two randomly-playing players, printing
  ;; trades as they happen, and return the summary of the game which will also be 
  ;; printed.
  (:summary (play-game [(player "Randy" random-pf)
                        (player "Sandy" random-pf)]
                       :print)))
