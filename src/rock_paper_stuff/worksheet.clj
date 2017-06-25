(ns rock-paper-stuff.worksheet
  (:require [gorilla-plot.core :as plot])
  (:use [rock-paper-stuff util play player-functions]
        [clojure.pprint]))
(play-game [(player "Randy" random-pf)
            (player "Sandy" random-pf)])
(pprint (play-game [(player "Randy" random-pf)
                    (player "Sandy" random-pf)]))
(:summary (play-game [(player "Randy" random-pf)
                      (player "Sandy" random-pf)]))
(:summary (play-game [(player "Randy" random-pf)
                      (player "Sandy" random-pf)]
                     :print))
(:summary (play-game [(player "Randy" random-pf) 
                      (player "Sandy" random-pf) 
                      (player "Rocky" rock-pf) 
                      (player "Wally" water-pf)]))
(pprint (:summary (play-game [(player "Randy" random-pf) 
                              (player "Sandy" random-pf) 
                              (player "Rocky" rock-pf) 
                              (player "Wally" water-pf)])))
(tournament 100
            [(player "Randy" random-pf) 
             (player "Rocky" rock-pf) 
             (player "Pappy" paper-pf)
             (player "Suzy" scissors-pf)
             (player "Fido" fire-pf)
             (player "Wally" water-pf)
             (player "Margie" majority-pf)
             (player "Minnie" minority-pf)
             (player "Harry" water-hoarder-pf)])
(tournament 100
            [(player "Margie" majority-pf)
             (player "Pappy" paper-pf)])
(tournament 100
            [(player "Rocky" rock-pf)
             (player "Suzy" scissors-pf)])
(tournament 100
            [(player "Randy" random-pf) 
             (player "Rudy" random-pf) 
             (player "Trudy" random-pf)  
             (player "Rocky" rock-pf) 
             (player "Suzy" scissors-pf)])
(apply merge-with + (map :inventory 
                         (:players (play-game [(player "Randy" random-pf) 
                                               (player "Rocky" rock-pf) 
                                               (player "Pappy" paper-pf)
                                               (player "Suzy" scissors-pf)
                                               (player "Fido" fire-pf)
                                               (player "Wally" water-pf)
                                               (player "Margie" majority-pf)
                                               (player "Minnie" minority-pf)]))))
(let [players [(player "Randy" random-pf) 
               (player "Rocky" rock-pf) 
               (player "Pappy" paper-pf)
               (player "Suzy" scissors-pf)
               (player "Fido" fire-pf)
               (player "Wally" water-pf)
               (player "Margie" majority-pf)
               (player "Minnie" minority-pf)
               (player "Harry" water-hoarder-pf)
               (player "Maggy" margie-hater-pf)]]
  (doseq [p1 players]
    (doseq [p2 players]
      (println (:name p1) 
               "vs"  
               (:name p2)
               (tournament 100 [p1 p2])))))
(tournament 100
            [;(player "Rocky" rock-pf) 
             ;(player "Suzy" scissors-pf)
             (player "Margie" majority-pf)
             ;(player "Pappy" paper-pf)
             (player "Maggy" margie-hater-pf)])
(tournament 100
            [(player "Maggy" margie-hater-pf)
             (player "Naggy" maggie-hater-pf)])
(tournament 100
            [(player "Randy" random-pf) 
             (player "Harry" water-hoarder-pf)
             (player "Margie" majority-pf)
             (player "Maggy" margie-hater-pf)])
(let [results (tournament 100
                          [(player "Randy" random-pf) 
                           (player "Harry" water-hoarder-pf)
                           (player "Margie" majority-pf)
                           (player "Maggy" margie-hater-pf)])]
  (plot/bar-chart (map first results) (map second results)))
(let [ghist (:global-history (play-game
                               [(player "Wally" water-pf)
                                (player "Harry" water-hoarder-pf)
                                (player "Randy" random-pf)]))
      dev (fn [inv] (deviance {:inventory inv}))
      wally-deviances (map dev (map #(get % "Wally") ghist))
      harry-deviances (map dev (map #(get % "Harry") ghist))
      randy-deviances (map dev (map #(get % "Randy") ghist))
      min-deviance (apply min (concat wally-deviances harry-deviances randy-deviances))
      max-deviance (apply max (concat wally-deviances harry-deviances randy-deviances))
      plot-fn (fn [data color]
                (plot/list-plot 
                  data 
                  :color color 
                  :joined true
                  :plot-range [:all [min-deviance max-deviance]]))]
  (plot/compose (plot-fn wally-deviances "Blue")
                (plot-fn harry-deviances "Red")
                (plot-fn randy-deviances "Green")))
