(ns rock-paper-stuff.core
  (:require [rock-paper-stuff.user])
  (:gen-class))

(defn -main
  "Runs the user-main function in rock-paper-stuff.user, printing its result."
  [& args]
  (println (rock-paper-stuff.user/user-main)))
