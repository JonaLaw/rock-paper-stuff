;; gorilla-repl.fileformat = 1

;; **
;;; # Rock Paper Stuff
;; **

;; **
;;; This [Gorilla REPL](http://gorilla-repl.org) worksheet demonstrates basic features of the [Rock Paper Stuff](https://github.com/lspector/rock-paper-stuff) environment. If you scroll to the bottom you'll see a few graphs that also demonstrate Gorilla REPL's plotting features.
;;; 
;;; Here we define the namespace, pulling in names from some others.
;; **

;; @@
(ns rock-paper-stuff.worksheet
  (:require [gorilla-plot.core :as plot])
  (:use [rock-paper-stuff util play]
        [clojure.pprint]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; Players are controlled by "player functions," which we'll give names ending in "-pf" just as a reminder to ourselves that that's what they are. A player function is passed two arguments: the player itself, and the skin of the player it is facing. It should return a map that can contain keys for `:play` (which should have a value of `:rock`, `:paper`, `:scissors`, `:fire`, or `:water`) and optionally `:skin` (containing a map that will be presented to opponents, to which the player's name and inventory will be added) and `:memory` (containing a map that will be merged into the player's `:memory` map for possible later reference).
;;; 
;;; Here is a simple player function that just always says to play `:rock`.
;; **

;; @@
(defn rock-pf
  "A player function that always plays :rock."
  [self other-skin]
  {:play :rock})
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;rock-paper-stuff.worksheet/rock-pf</span>","value":"#'rock-paper-stuff.worksheet/rock-pf"}
;; <=

;; **
;;; Players are created with the `player` function, which takes a name (any string) and a player function. Games are played by passing a collection of players to the `play-game` function, which returns a map of the game's `:winner` (just the name of the player that won, or "Nobody" if there was no winner), the final `:players` (with all details), and the `:global-history` of the whole game. Because the `:players` and `:global-history` are *long*, here we'll play a game but just print the `:winner`.
;; **

;; @@
(:winner (play-game [(player "Rocky" rock-pf)
                     (player "Roxy" rock-pf)]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-string'>&quot;Nobody&quot;</span>","value":"\"Nobody\""}
;; <=

;; **
;;; Here is a player function that plays randomly. Note that if a player function says to play something that the player doesn't have, then the game engine will choose something else to play for the player, randomly.
;; **

;; @@
(defn random-pf
  "A player function that returns a random kind of stuff to play."
  [self other-skin]
  {:play (rand-nth [:rock :paper :scissors :fire :water])})
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;rock-paper-stuff.worksheet/random-pf</span>","value":"#'rock-paper-stuff.worksheet/random-pf"}
;; <=

;; **
;;; Now we can see what happens when a player that plays only `:rock` plays against a player that plays randomly.
;; **

;; @@
(:winner (play-game [(player "Rocky" rock-pf)
                     (player "Randy" random-pf)]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-string'>&quot;Randy&quot;</span>","value":"\"Randy\""}
;; <=

;; **
;;; If we want to see more detail about what happened, we can look at the final `:players`. Because each player contains a `:history` of every trade in which it participated, printing a full player produces a really *long* mess, and we won't do that here. Instead, wel look at the results of calling the `summary` function on each player, which will show us each player's name, deviance, and final inventory.
;; **

;; @@
(map summary 
     (:players (play-game [(player "Rocky" rock-pf)
                           (player "Randy" random-pf)])))
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:name</span>","value":":name"},{"type":"html","content":"<span class='clj-string'>&quot;Randy&quot;</span>","value":"\"Randy\""}],"value":"[:name \"Randy\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:deviance</span>","value":":deviance"},{"type":"html","content":"<span class='clj-double'>5.357238094391549</span>","value":"5.357238094391549"}],"value":"[:deviance 5.357238094391549]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:inventory</span>","value":":inventory"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:fire</span>","value":":fire"},{"type":"html","content":"<span class='clj-long'>9</span>","value":"9"}],"value":"[:fire 9]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:paper</span>","value":":paper"},{"type":"html","content":"<span class='clj-long'>18</span>","value":"18"}],"value":"[:paper 18]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:scissors</span>","value":":scissors"},{"type":"html","content":"<span class='clj-long'>7</span>","value":"7"}],"value":"[:scissors 7]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:water</span>","value":":water"},{"type":"html","content":"<span class='clj-long'>17</span>","value":"17"}],"value":"[:water 17]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:rock</span>","value":":rock"},{"type":"html","content":"<span class='clj-long'>18</span>","value":"18"}],"value":"[:rock 18]"}],"value":"{:fire 9, :paper 18, :scissors 7, :water 17, :rock 18}"}],"value":"[:inventory {:fire 9, :paper 18, :scissors 7, :water 17, :rock 18}]"}],"value":"{:name \"Randy\", :deviance 5.357238094391549, :inventory {:fire 9, :paper 18, :scissors 7, :water 17, :rock 18}}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:name</span>","value":":name"},{"type":"html","content":"<span class='clj-string'>&quot;Rocky&quot;</span>","value":"\"Rocky\""}],"value":"[:name \"Rocky\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:deviance</span>","value":":deviance"},{"type":"html","content":"<span class='clj-double'>6.708203932499369</span>","value":"6.708203932499369"}],"value":"[:deviance 6.708203932499369]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:inventory</span>","value":":inventory"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:fire</span>","value":":fire"},{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"}],"value":"[:fire 3]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:paper</span>","value":":paper"},{"type":"html","content":"<span class='clj-long'>11</span>","value":"11"}],"value":"[:paper 11]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:scissors</span>","value":":scissors"},{"type":"html","content":"<span class='clj-long'>17</span>","value":"17"}],"value":"[:scissors 17]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:water</span>","value":":water"},{"type":"html","content":"<span class='clj-long'>9</span>","value":"9"}],"value":"[:water 9]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:rock</span>","value":":rock"},{"type":"html","content":"<span class='clj-long'>0</span>","value":"0"}],"value":"[:rock 0]"}],"value":"{:fire 3, :paper 11, :scissors 17, :water 9, :rock 0}"}],"value":"[:inventory {:fire 3, :paper 11, :scissors 17, :water 9, :rock 0}]"}],"value":"{:name \"Rocky\", :deviance 6.708203932499369, :inventory {:fire 3, :paper 11, :scissors 17, :water 9, :rock 0}}"}],"value":"({:name \"Randy\", :deviance 5.357238094391549, :inventory {:fire 9, :paper 18, :scissors 7, :water 17, :rock 18}} {:name \"Rocky\", :deviance 6.708203932499369, :inventory {:fire 3, :paper 11, :scissors 17, :water 9, :rock 0}})"}
;; <=

;; **
;;; The standard Clojure pretty-printing function, `pprint`, makes this easier to read.
;; **

;; @@
(pprint
  (map summary
       (:players (play-game [(player "Rocky" rock-pf)
                             (player "Randy" random-pf)]))))
;; @@
;; ->
;;; ({:name &quot;Randy&quot;,
;;;   :deviance 6.6332495807108,
;;;   :inventory {:fire 4, :paper 13, :scissors 4, :water 18, :rock 16}}
;;;  {:name &quot;Rocky&quot;,
;;;   :deviance 7.162401831787993,
;;;   :inventory {:fire 13, :paper 10, :scissors 19, :water 6, :rock 0}})
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; Now let's see what happens when we pit the player that always plays `:rock` against one that always plays `:paper`.
;; **

;; @@
(defn paper-pf
  "A player function that always plays :paper."
  [self other-skin]
  {:play :paper})

(pprint
  (map summary
       (:players (play-game [(player "Rocky" rock-pf)
                             (player "Pappy" paper-pf)]))))
;; @@
;; ->
;;; ({:name &quot;Pappy&quot;,
;;;   :deviance 6.928203230275509,
;;;   :inventory {:fire 9, :paper 0, :scissors 13, :water 9, :rock 19}}
;;;  {:name &quot;Rocky&quot;,
;;;   :deviance 8.87130204648675,
;;;   :inventory {:fire 1, :paper 21, :scissors 8, :water 14, :rock 0}})
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; But is this a reliable result? We can get a better idea of this by running a tournament of many independent games among the same players. The `tournament` function takes a number of games to play and a collection of players, and returns a sequence of pairs, each of which contains a player name and the number of games won by that player in the tournament. The sequence is sorted, with the winningest player listed first.
;; **

;; @@
(tournament 100
            [(player "Rocky" rock-pf)
             (player "Pappy" paper-pf)])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Pappy&quot;</span>","value":"\"Pappy\""},{"type":"html","content":"<span class='clj-long'>53</span>","value":"53"}],"value":"[\"Pappy\" 53]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Rocky&quot;</span>","value":"\"Rocky\""},{"type":"html","content":"<span class='clj-long'>46</span>","value":"46"}],"value":"[\"Rocky\" 46]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Nobody&quot;</span>","value":"\"Nobody\""},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[\"Nobody\" 1]"}],"value":"([\"Pappy\" 53] [\"Rocky\" 46] [\"Nobody\" 1])"}
;; <=

;; **
;;; Let's try a tournament among five players, each of which just always plays the same one of the game's five resources.
;;; 
;;; First we'll define the three other simple strategies.
;; **

;; @@
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
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Pappy&quot;</span>","value":"\"Pappy\""},{"type":"html","content":"<span class='clj-long'>69</span>","value":"69"}],"value":"[\"Pappy\" 69]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Wally&quot;</span>","value":"\"Wally\""},{"type":"html","content":"<span class='clj-long'>20</span>","value":"20"}],"value":"[\"Wally\" 20]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Fido&quot;</span>","value":"\"Fido\""},{"type":"html","content":"<span class='clj-long'>8</span>","value":"8"}],"value":"[\"Fido\" 8]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Rocky&quot;</span>","value":"\"Rocky\""},{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"}],"value":"[\"Rocky\" 3]"}],"value":"([\"Pappy\" 69] [\"Wally\" 20] [\"Fido\" 8] [\"Rocky\" 3])"}
;; <=

;; **
;;; Now we can include them in a tournament.
;; **

;; @@
(tournament 100
            [(player "Rocky" rock-pf)
             (player "Pappy" paper-pf)
             (player "Suzy" scissors-pf)
             (player "Fido" fire-pf)
             (player "Wally" water-pf)])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Pappy&quot;</span>","value":"\"Pappy\""},{"type":"html","content":"<span class='clj-long'>64</span>","value":"64"}],"value":"[\"Pappy\" 64]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Wally&quot;</span>","value":"\"Wally\""},{"type":"html","content":"<span class='clj-long'>24</span>","value":"24"}],"value":"[\"Wally\" 24]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Fido&quot;</span>","value":"\"Fido\""},{"type":"html","content":"<span class='clj-long'>10</span>","value":"10"}],"value":"[\"Fido\" 10]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Rocky&quot;</span>","value":"\"Rocky\""},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[\"Rocky\" 1]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Suzy&quot;</span>","value":"\"Suzy\""},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[\"Suzy\" 1]"}],"value":"([\"Pappy\" 64] [\"Wally\" 24] [\"Fido\" 10] [\"Rocky\" 1] [\"Suzy\" 1])"}
;; <=

;; **
;;; Let's try that again but with the randomly-playing player also in the mix.
;; **

;; @@
(tournament 100
            [(player "Rocky" rock-pf)
             (player "Pappy" paper-pf)
             (player "Suzy" scissors-pf)
             (player "Fido" fire-pf)
             (player "Wally" water-pf)
             (player "Randy" random-pf)])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Randy&quot;</span>","value":"\"Randy\""},{"type":"html","content":"<span class='clj-long'>45</span>","value":"45"}],"value":"[\"Randy\" 45]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Wally&quot;</span>","value":"\"Wally\""},{"type":"html","content":"<span class='clj-long'>24</span>","value":"24"}],"value":"[\"Wally\" 24]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Pappy&quot;</span>","value":"\"Pappy\""},{"type":"html","content":"<span class='clj-long'>23</span>","value":"23"}],"value":"[\"Pappy\" 23]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Fido&quot;</span>","value":"\"Fido\""},{"type":"html","content":"<span class='clj-long'>4</span>","value":"4"}],"value":"[\"Fido\" 4]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Rocky&quot;</span>","value":"\"Rocky\""},{"type":"html","content":"<span class='clj-long'>4</span>","value":"4"}],"value":"[\"Rocky\" 4]"}],"value":"([\"Randy\" 45] [\"Wally\" 24] [\"Pappy\" 23] [\"Fido\" 4] [\"Rocky\" 4])"}
;; <=

;; **
;;; Here's a more complicated player, which plays whatever it has most of.
;; **

;; @@
(defn maximum-pf
  "A player function that plays one of the kinds of stuff of which the 
  player has most."
  [self other-skin]
  {:play (let [max-val (apply max (vals (:inventory self)))
               candidates (filter (fn [[k v]] (= v max-val)) (:inventory self))]
           (first (first (shuffle candidates))))})
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;rock-paper-stuff.worksheet/maximum-pf</span>","value":"#'rock-paper-stuff.worksheet/maximum-pf"}
;; <=

;; **
;;; Now let's run another tournament, including all of the previously defined player functions but also this new one.
;; **

;; @@
(tournament 100
            [(player "Rocky" rock-pf)
             (player "Pappy" paper-pf)
             (player "Suzy" scissors-pf)
             (player "Fido" fire-pf)
             (player "Wally" water-pf)
             (player "Randy" random-pf)
             (player "Maxy" maximum-pf)])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Maxy&quot;</span>","value":"\"Maxy\""},{"type":"html","content":"<span class='clj-long'>75</span>","value":"75"}],"value":"[\"Maxy\" 75]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Pappy&quot;</span>","value":"\"Pappy\""},{"type":"html","content":"<span class='clj-long'>15</span>","value":"15"}],"value":"[\"Pappy\" 15]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Fido&quot;</span>","value":"\"Fido\""},{"type":"html","content":"<span class='clj-long'>5</span>","value":"5"}],"value":"[\"Fido\" 5]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Suzy&quot;</span>","value":"\"Suzy\""},{"type":"html","content":"<span class='clj-long'>4</span>","value":"4"}],"value":"[\"Suzy\" 4]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Randy&quot;</span>","value":"\"Randy\""},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[\"Randy\" 1]"}],"value":"([\"Maxy\" 75] [\"Pappy\" 15] [\"Fido\" 5] [\"Suzy\" 4] [\"Randy\" 1])"}
;; <=

;; **
;;; Is that strategy, of playing what you have the most of, the best possible one? Here's a player function that assumes it is playing against that strategy, and then acts in a way intended to foil it.
;; **

;; @@
(defn max-foiler-pf
  "A player that assumes that its opponent is using the maximum-pf player
  function, and plays in a way intended to foil that opponent."
  [self other-skin]
  {:play (case (:play (maximum-pf {:inventory (:inventory other-skin)} {}))
           :paper :scissors
           :scissors :fire
           :fire :paper
           :rock :fire
           :water :rock)})
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;rock-paper-stuff.worksheet/max-foiler-pf</span>","value":"#'rock-paper-stuff.worksheet/max-foiler-pf"}
;; <=

;; **
;;; Let's see what happens with this one thrown into the full mix we played above:
;; **

;; @@
(tournament 100
            [(player "Rocky" rock-pf)
             (player "Pappy" paper-pf)
             (player "Suzy" scissors-pf)
             (player "Fido" fire-pf)
             (player "Wally" water-pf)
             (player "Randy" random-pf)
             (player "Maxy" maximum-pf)
             (player "Foxy" max-foiler-pf)])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Maxy&quot;</span>","value":"\"Maxy\""},{"type":"html","content":"<span class='clj-long'>42</span>","value":"42"}],"value":"[\"Maxy\" 42]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Foxy&quot;</span>","value":"\"Foxy\""},{"type":"html","content":"<span class='clj-long'>37</span>","value":"37"}],"value":"[\"Foxy\" 37]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Pappy&quot;</span>","value":"\"Pappy\""},{"type":"html","content":"<span class='clj-long'>12</span>","value":"12"}],"value":"[\"Pappy\" 12]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Fido&quot;</span>","value":"\"Fido\""},{"type":"html","content":"<span class='clj-long'>8</span>","value":"8"}],"value":"[\"Fido\" 8]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Randy&quot;</span>","value":"\"Randy\""},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[\"Randy\" 1]"}],"value":"([\"Maxy\" 42] [\"Foxy\" 37] [\"Pappy\" 12] [\"Fido\" 8] [\"Randy\" 1])"}
;; <=

;; **
;;; Not as effective as we might have hoped, but maybe this is because it's also playing against other strategies, and because those other strategies are also playing against Maxy. Let's see what happens when Maxy and Foxy play one-on-one: 
;; **

;; @@
(tournament 100
            [(player "Maxy" maximum-pf)
             (player "Foxy" max-foiler-pf)])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Foxy&quot;</span>","value":"\"Foxy\""},{"type":"html","content":"<span class='clj-long'>88</span>","value":"88"}],"value":"[\"Foxy\" 88]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Maxy&quot;</span>","value":"\"Maxy\""},{"type":"html","content":"<span class='clj-long'>12</span>","value":"12"}],"value":"[\"Maxy\" 12]"}],"value":"([\"Foxy\" 88] [\"Maxy\" 12])"}
;; <=

;; **
;;; Can Foxy's strategy be beaten one-on-one? Here's a strategy that assumes it's playing against Foxy's strategy, and tries to foil it in turn:
;; **

;; @@
(defn foil-foiler-pf
  "A player that assumes that its opponent is using the max-foiler-pf player
  function, and plays in a way intended to foil that opponent."
  [self other-skin]
  {:play (case (:play (max-foiler-pf {:inventory (:inventory other-skin)} self))
           :paper :scissors
           :scissors :fire
           :fire :paper
           :rock :fire
           :water :rock)})
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;rock-paper-stuff.worksheet/foil-foiler-pf</span>","value":"#'rock-paper-stuff.worksheet/foil-foiler-pf"}
;; <=

;; **
;;; Does it work?
;; **

;; @@
(tournament 100
            [(player "Foxy" max-foiler-pf)
             (player "Phyllis" foil-foiler-pf)])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Phyllis&quot;</span>","value":"\"Phyllis\""},{"type":"html","content":"<span class='clj-long'>78</span>","value":"78"}],"value":"[\"Phyllis\" 78]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Foxy&quot;</span>","value":"\"Foxy\""},{"type":"html","content":"<span class='clj-long'>22</span>","value":"22"}],"value":"[\"Foxy\" 22]"}],"value":"([\"Phyllis\" 78] [\"Foxy\" 22])"}
;; <=

;; **
;;; Yep! Can we keep doing this trick forever? Here's the next player function in the sequence.
;; **

;; @@
(defn foil-foiler-foiler-pf
  "A player that assumes that its opponent is using the max-foiler-pf player
  function, and plays in a way intended to foil that opponent."
  [self other-skin]
  {:play (case (:play (foil-foiler-pf {:inventory (:inventory other-skin)} self))
           :paper :scissors
           :scissors :fire
           :fire :paper
           :rock :fire
           :water :rock)})
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;rock-paper-stuff.worksheet/foil-foiler-foiler-pf</span>","value":"#'rock-paper-stuff.worksheet/foil-foiler-foiler-pf"}
;; <=

;; **
;;; Does it work?
;; **

;; @@
(tournament 100
            [(player "Pythagoras" foil-foiler-foiler-pf)
             (player "Phyllis" foil-foiler-pf)])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Pythagoras&quot;</span>","value":"\"Pythagoras\""},{"type":"html","content":"<span class='clj-long'>85</span>","value":"85"}],"value":"[\"Pythagoras\" 85]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Phyllis&quot;</span>","value":"\"Phyllis\""},{"type":"html","content":"<span class='clj-long'>15</span>","value":"15"}],"value":"[\"Phyllis\" 15]"}],"value":"([\"Pythagoras\" 85] [\"Phyllis\" 15])"}
;; <=

;; **
;;; Yes, but it's far from invincible.
;; **

;; @@
(tournament 100
            [(player "Pythagoras" foil-foiler-foiler-pf)
             (player "Fido" fire-pf)])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Fido&quot;</span>","value":"\"Fido\""},{"type":"html","content":"<span class='clj-long'>83</span>","value":"83"}],"value":"[\"Fido\" 83]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Pythagoras&quot;</span>","value":"\"Pythagoras\""},{"type":"html","content":"<span class='clj-long'>15</span>","value":"15"}],"value":"[\"Pythagoras\" 15]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Nobody&quot;</span>","value":"\"Nobody\""},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"}],"value":"[\"Nobody\" 2]"}],"value":"([\"Fido\" 83] [\"Pythagoras\" 15] [\"Nobody\" 2])"}
;; <=

;; **
;;; The moral is that you have to know who you're playing against in order to beat them. And you might not have any way of knowing, aside from watching them and observing their behavior.
;;; 
;;; You can see the play-by-play history of a game by looking at the `:global-history` of the result from `play-game`. This will contain records of all plays and the inventories of all players between plays. Since it's long, we'll look here only at the first bunch of events, which we'll pretty-print.
;; **

;; @@
(pprint 
  (take 10 (:global-history
             (play-game [(player "Pythagoras" foil-foiler-foiler-pf)
                         (player "Fido" fire-pf)]))))
;; @@
;; ->
;;; ({&quot;Pythagoras&quot;
;;;   {:fire 10, :paper 10, :scissors 10, :water 10, :rock 10},
;;;   &quot;Fido&quot; {:fire 10, :paper 10, :scissors 10, :water 10, :rock 10}}
;;;  {:player &quot;Fido&quot;, :plays :fire}
;;;  {:player &quot;Pythagoras&quot;, :plays :paper}
;;;  {&quot;Fido&quot; {:fire 11, :paper 10, :scissors 10, :water 10, :rock 10},
;;;   &quot;Pythagoras&quot; {:fire 10, :paper 9, :scissors 10, :water 10, :rock 10}}
;;;  {:player &quot;Pythagoras&quot;, :plays :fire}
;;;  {:player &quot;Fido&quot;, :plays :fire}
;;;  {&quot;Pythagoras&quot; {:fire 9, :paper 9, :scissors 10, :water 10, :rock 10},
;;;   &quot;Fido&quot; {:fire 12, :paper 10, :scissors 10, :water 10, :rock 10}}
;;;  {:player &quot;Fido&quot;, :plays :fire}
;;;  {:player &quot;Pythagoras&quot;, :plays :fire}
;;;  {&quot;Fido&quot; {:fire 13, :paper 10, :scissors 10, :water 10, :rock 10},
;;;   &quot;Pythagoras&quot; {:fire 8, :paper 9, :scissors 10, :water 10, :rock 10}})
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; This is a case in which Clojure's "threading macros" can probably improve readability:
;; **

;; @@
(->> [(player "Pythagoras" foil-foiler-foiler-pf)
      (player "Fido" fire-pf)]
     (play-game)
     (:global-history)
     (take 10)
     (pprint))
;; @@
;; ->
;;; ({&quot;Pythagoras&quot;
;;;   {:fire 10, :paper 10, :scissors 10, :water 10, :rock 10},
;;;   &quot;Fido&quot; {:fire 10, :paper 10, :scissors 10, :water 10, :rock 10}}
;;;  {:player &quot;Fido&quot;, :plays :fire}
;;;  {:player &quot;Pythagoras&quot;, :plays :fire}
;;;  {&quot;Fido&quot; {:fire 9, :paper 10, :scissors 10, :water 10, :rock 10},
;;;   &quot;Pythagoras&quot;
;;;   {:fire 11, :paper 10, :scissors 10, :water 10, :rock 10}}
;;;  {:player &quot;Fido&quot;, :plays :fire}
;;;  {:player &quot;Pythagoras&quot;, :plays :scissors}
;;;  {&quot;Fido&quot; {:fire 9, :paper 10, :scissors 10, :water 10, :rock 10},
;;;   &quot;Pythagoras&quot;
;;;   {:fire 11, :paper 10, :scissors 11, :water 10, :rock 10}}
;;;  {:player &quot;Fido&quot;, :plays :fire}
;;;  {:player &quot;Pythagoras&quot;, :plays :scissors}
;;;  {&quot;Fido&quot; {:fire 9, :paper 10, :scissors 10, :water 10, :rock 10},
;;;   &quot;Pythagoras&quot;
;;;   {:fire 11, :paper 10, :scissors 12, :water 10, :rock 10}})
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; We can use `merge-with +` on the inventories of all of the players at the end of a game, to see what the total final inventories/debts are:
;; **

;; @@
(apply merge-with + 
       (map :inventory 
            (:players (play-game [(player "Randy" random-pf) 
                                  (player "Rocky" rock-pf) 
                                  (player "Pappy" paper-pf)
                                  (player "Suzy" scissors-pf)
                                  (player "Fido" fire-pf)
                                  (player "Wally" water-pf)]))))
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:fire</span>","value":":fire"},{"type":"html","content":"<span class='clj-long'>335</span>","value":"335"}],"value":"[:fire 335]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:paper</span>","value":":paper"},{"type":"html","content":"<span class='clj-long'>181</span>","value":"181"}],"value":"[:paper 181]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:scissors</span>","value":":scissors"},{"type":"html","content":"<span class='clj-long'>372</span>","value":"372"}],"value":"[:scissors 372]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:water</span>","value":":water"},{"type":"html","content":"<span class='clj-long'>139</span>","value":"139"}],"value":"[:water 139]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:rock</span>","value":":rock"},{"type":"html","content":"<span class='clj-long'>223</span>","value":"223"}],"value":"[:rock 223]"}],"value":"{:fire 335, :paper 181, :scissors 372, :water 139, :rock 223}"}
;; <=

;; **
;;; Here we print the results of two-player tournaments among all pairs of players from the provided collection: 
;; **

;; @@
(let [players [(player "Rocky" rock-pf)
               (player "Pappy" paper-pf)
               (player "Suzy" scissors-pf)
               (player "Fido" fire-pf)
               (player "Wally" water-pf)
               (player "Randy" random-pf)
               (player "Maxy" maximum-pf)
               (player "Foxy" max-foiler-pf)
               (player "Phyllis" foil-foiler-pf)
               (player "Pythagoras" foil-foiler-foiler-pf)]]
  (doseq [p1 players]
    (doseq [p2 players]
      (println (:name p1) 
               "vs"  
               (:name p2)
               (tournament 100 [p1 p2])))))
;; @@
;; ->
;;; Rocky vs Rocky ([Rocky 54] [Nobody 46])
;;; Rocky vs Pappy ([Pappy 61] [Rocky 39])
;;; Rocky vs Suzy ([Suzy 65] [Rocky 35])
;;; Rocky vs Fido ([Rocky 74] [Fido 26])
;;; Rocky vs Wally ([Wally 77] [Rocky 23])
;;; Rocky vs Randy ([Randy 66] [Rocky 32] [Nobody 2])
;;; Rocky vs Maxy ([Maxy 94] [Rocky 5] [Nobody 1])
;;; Rocky vs Foxy ([Foxy 78] [Rocky 22])
;;; Rocky vs Phyllis ([Phyllis 53] [Rocky 46] [Nobody 1])
;;; Rocky vs Pythagoras ([Pythagoras 53] [Rocky 46] [Nobody 1])
;;; Pappy vs Rocky ([Pappy 66] [Rocky 34])
;;; Pappy vs Pappy ([Pappy 60] [Nobody 40])
;;; Pappy vs Suzy ([Suzy 100])
;;; Pappy vs Fido ([Pappy 58] [Fido 41] [Nobody 1])
;;; Pappy vs Wally ([Pappy 71] [Wally 29])
;;; Pappy vs Randy ([Pappy 75] [Randy 25])
;;; Pappy vs Maxy ([Maxy 90] [Pappy 10])
;;; Pappy vs Foxy ([Foxy 65] [Pappy 35])
;;; Pappy vs Phyllis ([Phyllis 100])
;;; Pappy vs Pythagoras ([Pappy 62] [Pythagoras 38])
;;; Suzy vs Rocky ([Suzy 64] [Rocky 35] [Nobody 1])
;;; Suzy vs Pappy ([Suzy 100])
;;; Suzy vs Suzy ([Suzy 64] [Nobody 36])
;;; Suzy vs Fido ([Fido 100])
;;; Suzy vs Wally ([Suzy 63] [Wally 37])
;;; Suzy vs Randy ([Suzy 67] [Randy 33])
;;; Suzy vs Maxy ([Maxy 64] [Suzy 36])
;;; Suzy vs Foxy ([Foxy 100])
;;; Suzy vs Phyllis ([Phyllis 100])
;;; Suzy vs Pythagoras ([Suzy 93] [Pythagoras 6] [Nobody 1])
;;; Fido vs Rocky ([Rocky 73] [Fido 27])
;;; Fido vs Pappy ([Pappy 57] [Fido 43])
;;; Fido vs Suzy ([Fido 100])
;;; Fido vs Fido ([Fido 62] [Nobody 38])
;;; Fido vs Wally ([Fido 55] [Wally 44] [Nobody 1])
;;; Fido vs Randy ([Randy 66] [Fido 34])
;;; Fido vs Maxy ([Fido 98] [Maxy 2])
;;; Fido vs Foxy ([Fido 80] [Foxy 19] [Nobody 1])
;;; Fido vs Phyllis ([Phyllis 56] [Fido 44])
;;; Fido vs Pythagoras ([Fido 89] [Pythagoras 11])
;;; Wally vs Rocky ([Wally 67] [Rocky 32] [Nobody 1])
;;; Wally vs Pappy ([Pappy 73] [Wally 27])
;;; Wally vs Suzy ([Suzy 59] [Wally 41])
;;; Wally vs Fido ([Fido 53] [Wally 47])
;;; Wally vs Wally ([Wally 59] [Nobody 41])
;;; Wally vs Randy ([Wally 54] [Randy 46])
;;; Wally vs Maxy ([Maxy 87] [Wally 12] [Nobody 1])
;;; Wally vs Foxy ([Foxy 60] [Wally 39] [Nobody 1])
;;; Wally vs Phyllis ([Phyllis 76] [Wally 24])
;;; Wally vs Pythagoras ([Pythagoras 66] [Wally 34])
;;; Randy vs Rocky ([Randy 62] [Rocky 38])
;;; Randy vs Pappy ([Pappy 79] [Randy 21])
;;; Randy vs Suzy ([Suzy 59] [Randy 41])
;;; Randy vs Fido ([Randy 60] [Fido 39] [Nobody 1])
;;; Randy vs Wally ([Wally 50] [Randy 50])
;;; Randy vs Randy ([Randy 100])
;;; Randy vs Maxy ([Maxy 98] [Randy 2])
;;; Randy vs Foxy ([Foxy 68] [Randy 32])
;;; Randy vs Phyllis ([Phyllis 73] [Randy 27])
;;; Randy vs Pythagoras ([Pythagoras 54] [Randy 45] [Nobody 1])
;;; Maxy vs Rocky ([Maxy 95] [Rocky 4] [Nobody 1])
;;; Maxy vs Pappy ([Maxy 93] [Pappy 6] [Nobody 1])
;;; Maxy vs Suzy ([Maxy 74] [Suzy 26])
;;; Maxy vs Fido ([Fido 98] [Maxy 2])
;;; Maxy vs Wally ([Maxy 92] [Wally 8])
;;; Maxy vs Randy ([Maxy 99] [Randy 1])
;;; Maxy vs Maxy ([Maxy 99] [Nobody 1])
;;; Maxy vs Foxy ([Foxy 94] [Maxy 6])
;;; Maxy vs Phyllis ([Maxy 52] [Phyllis 48])
;;; Maxy vs Pythagoras ([Maxy 58] [Pythagoras 40] [Nobody 2])
;;; Foxy vs Rocky ([Foxy 82] [Rocky 18])
;;; Foxy vs Pappy ([Foxy 70] [Pappy 30])
;;; Foxy vs Suzy ([Foxy 100])
;;; Foxy vs Fido ([Fido 82] [Foxy 18])
;;; Foxy vs Wally ([Foxy 62] [Wally 38])
;;; Foxy vs Randy ([Foxy 55] [Randy 45])
;;; Foxy vs Maxy ([Foxy 96] [Maxy 4])
;;; Foxy vs Foxy ([Foxy 100])
;;; Foxy vs Phyllis ([Phyllis 77] [Foxy 23])
;;; Foxy vs Pythagoras ([Foxy 88] [Pythagoras 11] [Nobody 1])
;;; Phyllis vs Rocky ([Rocky 53] [Phyllis 47])
;;; Phyllis vs Pappy ([Phyllis 100])
;;; Phyllis vs Suzy ([Phyllis 100])
;;; Phyllis vs Fido ([Fido 54] [Phyllis 45] [Nobody 1])
;;; Phyllis vs Wally ([Phyllis 70] [Wally 29] [Nobody 1])
;;; Phyllis vs Randy ([Phyllis 84] [Randy 16])
;;; Phyllis vs Maxy ([Phyllis 55] [Maxy 45])
;;; Phyllis vs Foxy ([Phyllis 83] [Foxy 17])
;;; Phyllis vs Phyllis ([Phyllis 91] [Nobody 9])
;;; Phyllis vs Pythagoras ([Pythagoras 84] [Phyllis 15] [Nobody 1])
;;; Pythagoras vs Rocky ([Rocky 57] [Pythagoras 42] [Nobody 1])
;;; Pythagoras vs Pappy ([Pappy 71] [Pythagoras 29])
;;; Pythagoras vs Suzy ([Suzy 94] [Pythagoras 6])
;;; Pythagoras vs Fido ([Fido 91] [Pythagoras 9])
;;; Pythagoras vs Wally ([Pythagoras 52] [Wally 48])
;;; Pythagoras vs Randy ([Pythagoras 56] [Randy 44])
;;; Pythagoras vs Maxy ([Maxy 69] [Pythagoras 31])
;;; Pythagoras vs Foxy ([Foxy 84] [Pythagoras 16])
;;; Pythagoras vs Phyllis ([Pythagoras 74] [Phyllis 26])
;;; Pythagoras vs Pythagoras ([Pythagoras 99] [Nobody 1])
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; WIth the `bar-chart` function provided by Gorilla REPL, we can easily produce a chart of tournament results.
;; **

;; @@
(let [results (tournament 100
                          [(player "Rocky" rock-pf)
                           (player "Pappy" paper-pf)
                           (player "Suzy" scissors-pf)
                           (player "Fido" fire-pf)
                           (player "Wally" water-pf)])]
  (plot/bar-chart (map first results) (map second results)))
;; @@
;; =>
;;; {"type":"vega","content":{"width":400,"height":247.2187957763672,"padding":{"top":10,"left":55,"bottom":40,"right":10},"data":[{"name":"7b372634-a007-4574-8cce-83367f48dc67","values":[{"x":"Pappy","y":69},{"x":"Wally","y":20},{"x":"Fido","y":10},{"x":"Rocky","y":1}]}],"marks":[{"type":"rect","from":{"data":"7b372634-a007-4574-8cce-83367f48dc67"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"width":{"scale":"x","band":true,"offset":-1},"y":{"scale":"y","field":"data.y"},"y2":{"scale":"y","value":0}},"update":{"fill":{"value":"steelblue"},"opacity":{"value":1}},"hover":{"fill":{"value":"#FF29D2"}}}}],"scales":[{"name":"x","type":"ordinal","range":"width","domain":{"data":"7b372634-a007-4574-8cce-83367f48dc67","field":"data.x"}},{"name":"y","range":"height","nice":true,"domain":{"data":"7b372634-a007-4574-8cce-83367f48dc67","field":"data.y"}}],"axes":[{"type":"x","scale":"x"},{"type":"y","scale":"y"}]},"value":"#gorilla_repl.vega.VegaView{:content {:width 400, :height 247.2188, :padding {:top 10, :left 55, :bottom 40, :right 10}, :data [{:name \"7b372634-a007-4574-8cce-83367f48dc67\", :values ({:x \"Pappy\", :y 69} {:x \"Wally\", :y 20} {:x \"Fido\", :y 10} {:x \"Rocky\", :y 1})}], :marks [{:type \"rect\", :from {:data \"7b372634-a007-4574-8cce-83367f48dc67\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :width {:scale \"x\", :band true, :offset -1}, :y {:scale \"y\", :field \"data.y\"}, :y2 {:scale \"y\", :value 0}}, :update {:fill {:value \"steelblue\"}, :opacity {:value 1}}, :hover {:fill {:value \"#FF29D2\"}}}}], :scales [{:name \"x\", :type \"ordinal\", :range \"width\", :domain {:data \"7b372634-a007-4574-8cce-83367f48dc67\", :field \"data.x\"}} {:name \"y\", :range \"height\", :nice true, :domain {:data \"7b372634-a007-4574-8cce-83367f48dc67\", :field \"data.y\"}}], :axes [{:type \"x\", :scale \"x\"} {:type \"y\", :scale \"y\"}]}}"}
;; <=

;; **
;;; Gorilla REPL's plotting functions aren't as full-featured as those of dedicated graphics packages, but they can be handy. For example, here we  use `list-plot` and `compose` to plot the water levels of players over a game, which are stored in the `:global-history` of the game result:
;; **

;; @@
(let [ghist (:global-history (play-game
                               [(player "Wally" water-pf)
                                (player "Fido" fire-pf)
                                (player "Randy" random-pf)]))
      dev (fn [inv] (deviance {:inventory inv}))
      wally-devs (map dev (filter identity (map #(get % "Wally") ghist)))
      fido-devs (map dev (filter identity (map #(get % "Fido") ghist)))
      randy-devs (map dev (filter identity (map #(get % "Randy") ghist)))
      min-dev (apply min (concat wally-devs fido-devs randy-devs))
      max-dev (apply max (concat wally-devs fido-devs randy-devs))
      plot-fn (fn [data color]
                (plot/list-plot 
                  data 
                  :color color 
                  :joined true
                  :plot-range [:all [min-dev max-dev]]))]
  (plot/compose (plot-fn wally-devs "Blue")
                (plot-fn fido-devs "Red")
                (plot-fn randy-devs "Green")))
;; @@
;; =>
;;; {"type":"vega","content":{"width":400,"height":247.2187957763672,"padding":{"top":10,"left":55,"bottom":40,"right":10},"scales":[{"name":"x","type":"linear","range":"width","zero":false,"domain":{"data":"60975aaa-915c-4d46-b470-859048bdcb46","field":"data.x"}},{"name":"y","type":"linear","range":"height","nice":true,"zero":false,"domain":[0.0,15.449919093639293]}],"axes":[{"type":"x","scale":"x"},{"type":"y","scale":"y"}],"data":[{"name":"60975aaa-915c-4d46-b470-859048bdcb46","values":[{"x":0,"y":0.0},{"x":1,"y":0.0},{"x":2,"y":0.0},{"x":3,"y":0.4472135954999579},{"x":4,"y":0.4472135954999579},{"x":5,"y":0.0},{"x":6,"y":0.0},{"x":7,"y":0.0},{"x":8,"y":0.4472135954999579},{"x":9,"y":0.0},{"x":10,"y":0.4472135954999579},{"x":11,"y":0.4472135954999579},{"x":12,"y":0.4472135954999579},{"x":13,"y":0.4472135954999579},{"x":14,"y":0.4472135954999579},{"x":15,"y":0.4472135954999579},{"x":16,"y":0.4472135954999579},{"x":17,"y":0.4472135954999579},{"x":18,"y":0.4472135954999579},{"x":19,"y":0.4472135954999579},{"x":20,"y":0.4472135954999579},{"x":21,"y":0.7071067811865476},{"x":22,"y":0.7071067811865476},{"x":23,"y":1.4142135623730951},{"x":24,"y":1.7888543819998317},{"x":25,"y":2.1908902300206643},{"x":26,"y":2.6076809620810595},{"x":27,"y":2.1908902300206643},{"x":28,"y":2.1908902300206643},{"x":29,"y":2.8809720581775866},{"x":30,"y":2.4899799195977463},{"x":31,"y":3.1937438845342623},{"x":32,"y":3.1937438845342623},{"x":33,"y":3.5777087639996634},{"x":34,"y":3.5777087639996634},{"x":35,"y":3.9749213828703582},{"x":36,"y":4.669047011971501},{"x":37,"y":4.277849927241488},{"x":38,"y":4.669047011971501},{"x":39,"y":4.277849927241488},{"x":40,"y":4.669047011971501},{"x":41,"y":4.277849927241488},{"x":42,"y":4.979959839195493},{"x":43,"y":4.604345773288535},{"x":44,"y":4.604345773288535},{"x":45,"y":4.604345773288535},{"x":46,"y":4.979959839195493},{"x":47,"y":4.979959839195493},{"x":48,"y":4.979959839195493},{"x":49,"y":4.979959839195493},{"x":50,"y":4.979959839195493},{"x":51,"y":4.979959839195493},{"x":52,"y":4.979959839195493},{"x":53,"y":4.979959839195493},{"x":54,"y":5.683308895353129},{"x":55,"y":5.683308895353129},{"x":56,"y":5.310367218940701},{"x":57,"y":5.310367218940701},{"x":58,"y":6.016643582596529},{"x":59,"y":5.656854249492381},{"x":60,"y":6.016643582596529},{"x":61,"y":6.387487769068525},{"x":62,"y":6.387487769068525},{"x":63,"y":6.387487769068525},{"x":64,"y":6.016643582596529},{"x":65,"y":5.656854249492381},{"x":66,"y":5.310367218940701},{"x":67,"y":5.310367218940701},{"x":68,"y":6.016643582596529},{"x":69,"y":6.016643582596529},{"x":70,"y":6.363961030678928},{"x":71,"y":6.363961030678928},{"x":72,"y":6.016643582596529},{"x":73,"y":5.683308895353129},{"x":74,"y":6.387487769068525},{"x":75,"y":6.723094525588644},{"x":76,"y":6.723094525588644},{"x":77,"y":6.387487769068525},{"x":78,"y":6.06630035524124},{"x":79,"y":5.761944116355173},{"x":80,"y":5.477225575051661},{"x":81,"y":5.215361924162119},{"x":82,"y":5.890670590009257},{"x":83,"y":6.572670690061994},{"x":84,"y":6.572670690061994},{"x":85,"y":6.572670690061994},{"x":86,"y":6.572670690061994},{"x":87,"y":6.855654600401044},{"x":88,"y":6.855654600401044},{"x":89,"y":6.855654600401044},{"x":90,"y":6.572670690061994},{"x":91,"y":6.572670690061994},{"x":92,"y":6.572670690061994},{"x":93,"y":6.572670690061994},{"x":94,"y":7.259476565152615},{"x":95,"y":6.985699678629192},{"x":96,"y":6.985699678629192},{"x":97,"y":6.985699678629192},{"x":98,"y":6.985699678629192},{"x":99,"y":7.6681158050723255},{"x":100,"y":7.402702209328699},{"x":101,"y":8.080841540334768},{"x":102,"y":8.35463942968217},{"x":103,"y":8.080841540334768},{"x":104,"y":8.080841540334768},{"x":105,"y":7.8230428862431785},{"x":106,"y":7.8230428862431785},{"x":107,"y":7.582875444051551},{"x":108,"y":8.246211251235321},{"x":109,"y":8.012490249604053},{"x":110,"y":8.67179335547152},{"x":111,"y":8.916277250063503},{"x":112,"y":8.916277250063503},{"x":113,"y":8.916277250063503},{"x":114,"y":8.916277250063503},{"x":115,"y":8.916277250063503},{"x":116,"y":9.17605579756357},{"x":117,"y":9.17605579756357},{"x":118,"y":9.17605579756357},{"x":119,"y":9.85900603509299},{"x":120,"y":9.85900603509299},{"x":121,"y":9.85900603509299},{"x":122,"y":9.85900603509299},{"x":123,"y":10.13903348450926},{"x":124,"y":9.85900603509299},{"x":125,"y":9.85900603509299},{"x":126,"y":9.591663046625438},{"x":127,"y":9.591663046625438},{"x":128,"y":9.338094023943002},{"x":129,"y":9.591663046625438},{"x":130,"y":9.338094023943002},{"x":131,"y":9.591663046625438},{"x":132,"y":9.591663046625438},{"x":133,"y":9.85900603509299},{"x":134,"y":9.85900603509299},{"x":135,"y":9.85900603509299},{"x":136,"y":9.85900603509299},{"x":137,"y":10.545141061171254},{"x":138,"y":11.233877335986895},{"x":139,"y":11.233877335986895},{"x":140,"y":11.233877335986895},{"x":141,"y":11.924764148611073},{"x":142,"y":11.924764148611073},{"x":143,"y":11.924764148611073},{"x":144,"y":12.617448236470002},{"x":145,"y":12.617448236470002},{"x":146,"y":12.328828005937952},{"x":147,"y":12.328828005937952},{"x":148,"y":12.328828005937952},{"x":149,"y":13.019216566291536},{"x":150,"y":13.019216566291536},{"x":151,"y":13.019216566291536},{"x":152,"y":13.711309200802088},{"x":153,"y":14.007141035914502},{"x":154,"y":13.612494260788505},{"x":155,"y":13.612494260788505},{"x":156,"y":13.202272531651511},{"x":157,"y":12.817956155331473},{"x":158,"y":12.817956155331473},{"x":159,"y":12.817956155331473},{"x":160,"y":12.817956155331473},{"x":161,"y":12.856904759700136},{"x":162,"y":12.856904759700136},{"x":163,"y":12.856904759700136},{"x":164,"y":12.856904759700136},{"x":165,"y":12.856904759700136},{"x":166,"y":12.817956155331473},{"x":167,"y":12.794530081249565},{"x":168,"y":12.794530081249565},{"x":169,"y":12.794530081249565},{"x":170,"y":12.794530081249565},{"x":171,"y":12.794530081249565},{"x":172,"y":13.179529581893277},{"x":173,"y":13.179529581893277},{"x":174,"y":13.171939872319491},{"x":175,"y":13.198484761517134},{"x":176,"y":13.198484761517134},{"x":177,"y":13.198484761517134},{"x":178,"y":13.24009063413087},{"x":179,"y":13.24009063413087},{"x":180,"y":13.24009063413087},{"x":181,"y":13.21741275741966},{"x":182,"y":13.21741275741966},{"x":183,"y":13.21741275741966},{"x":184,"y":13.171939872319491},{"x":185,"y":13.171939872319491},{"x":186,"y":13.171939872319491},{"x":187,"y":13.171939872319491},{"x":188,"y":13.171939872319491},{"x":189,"y":12.794530081249565},{"x":190,"y":12.794530081249565},{"x":191,"y":12.794530081249565},{"x":192,"y":12.794530081249565},{"x":193,"y":12.794530081249565},{"x":194,"y":12.421755109484328},{"x":195,"y":12.421755109484328},{"x":196,"y":12.417729261020309},{"x":197,"y":12.429802894656053},{"x":198,"y":12.457929201917949},{"x":199,"y":12.457929201917949},{"x":200,"y":12.457929201917949},{"x":201,"y":12.794530081249565},{"x":202,"y":12.794530081249565},{"x":203,"y":13.164345787011218},{"x":204,"y":13.164345787011218},{"x":205,"y":13.164345787011218},{"x":206,"y":13.126309458488324},{"x":207,"y":13.501851724856113},{"x":208,"y":13.501851724856113},{"x":209,"y":13.903237033151669},{"x":210,"y":13.939153489362258},{"x":211,"y":13.939153489362258},{"x":212,"y":13.939153489362258},{"x":213,"y":13.553597308463905},{"x":214,"y":13.601470508735444},{"x":215,"y":13.601470508735444},{"x":216,"y":13.601470508735444},{"x":217,"y":13.601470508735444},{"x":218,"y":14.053469322555197},{"x":219,"y":14.053469322555197},{"x":220,"y":14.45683229480096},{"x":221,"y":14.446452851824906},{"x":222,"y":14.474114826130128},{"x":223,"y":14.515508947329405},{"x":224,"y":14.474114826130128},{"x":225,"y":14.446452851824906},{"x":226,"y":14.449913494550755},{"x":227,"y":14.53272169966796},{"x":228,"y":14.560219778561036},{"x":229,"y":14.601369798755183},{"x":230,"y":14.690132742763083},{"x":231,"y":14.720733677368122},{"x":232,"y":14.679918255903198},{"x":233,"y":14.679918255903198},{"x":234,"y":14.788509052639485},{"x":235,"y":15.15585695366646},{"x":236,"y":15.182226450688976},{"x":237,"y":15.182226450688976},{"x":238,"y":15.182226450688976},{"x":239,"y":15.182226450688976},{"x":240,"y":15.182226450688976},{"x":241,"y":14.679918255903198},{"x":242,"y":14.679918255903198},{"x":243,"y":14.679918255903198},{"x":244,"y":14.679918255903198},{"x":245,"y":14.720733677368122},{"x":246,"y":14.832396974191326},{"x":247,"y":14.933184523068078},{"x":248,"y":14.933184523068078},{"x":249,"y":14.889593681494468},{"x":250,"y":14.889593681494468},{"x":251,"y":14.959946523968593},{"x":252,"y":15.043270920913443},{"x":253,"y":15.043270920913443},{"x":254,"y":14.553350129781116},{"x":255,"y":14.652644812456213},{"x":256,"y":14.7648230602334},{"x":257,"y":14.7648230602334},{"x":258,"y":14.889593681494468},{"x":259,"y":15.016657417681207},{"x":260,"y":15.15585695366646},{"x":261,"y":15.297058540778355},{"x":262,"y":15.297058540778355},{"x":263,"y":15.254507530562892},{"x":264,"y":15.449919093639293},{"x":265,"y":15.449919093639293},{"x":266,"y":15.449919093639293},{"x":267,"y":15.449919093639293},{"x":268,"y":15.449919093639293},{"x":269,"y":15.449919093639293},{"x":270,"y":15.404544783926593},{"x":271,"y":15.404544783926593},{"x":272,"y":14.926486525636232},{"x":273,"y":14.926486525636232},{"x":274,"y":14.467204291085407},{"x":275,"y":14.467204291085407},{"x":276,"y":14.467204291085407},{"x":277,"y":14.515508947329405},{"x":278,"y":14.515508947329405},{"x":279,"y":14.515508947329405},{"x":280,"y":14.515508947329405},{"x":281,"y":14.515508947329405},{"x":282,"y":14.19506956657839},{"x":283,"y":14.078352176302452},{"x":284,"y":14.078352176302452},{"x":285,"y":14.028542333400146},{"x":286,"y":14.345731072343439},{"x":287,"y":14.237275020171522},{"x":288,"y":14.237275020171522},{"x":289,"y":14.237275020171522},{"x":290,"y":14.142135623730951},{"x":291,"y":14.060583202698243},{"x":292,"y":14.060583202698243},{"x":293,"y":14.289856542317},{"x":294,"y":14.289856542317},{"x":295,"y":14.289856542317},{"x":296,"y":14.289856542317},{"x":297,"y":14.289856542317},{"x":298,"y":14.68672870315238},{"x":299,"y":14.68672870315238},{"x":300,"y":14.604793733565703}]},{"name":"3fbff5f7-e3e1-412d-b281-19a1d347c58f","values":[{"x":0,"y":0.0},{"x":1,"y":0.4472135954999579},{"x":2,"y":0.8944271909999159},{"x":3,"y":0.8944271909999159},{"x":4,"y":1.3416407864998738},{"x":5,"y":1.3416407864998738},{"x":6,"y":1.7888543819998317},{"x":7,"y":2.23606797749979},{"x":8,"y":2.23606797749979},{"x":9,"y":2.23606797749979},{"x":10,"y":2.23606797749979},{"x":11,"y":2.6832815729997477},{"x":12,"y":3.1304951684997055},{"x":13,"y":3.1304951684997055},{"x":14,"y":3.5777087639996634},{"x":15,"y":4.024922359499621},{"x":16,"y":4.604345773288535},{"x":17,"y":5.049752469181039},{"x":18,"y":5.495452665613635},{"x":19,"y":5.941380311005179},{"x":20,"y":6.387487769068525},{"x":21,"y":6.387487769068525},{"x":22,"y":6.833739825307955},{"x":23,"y":6.833739825307955},{"x":24,"y":6.833739825307955},{"x":25,"y":6.833739825307955},{"x":26,"y":6.833739825307955},{"x":27,"y":6.745368781616021},{"x":28,"y":6.745368781616021},{"x":29,"y":6.745368781616021},{"x":30,"y":6.745368781616021},{"x":31,"y":6.819090848492928},{"x":32,"y":6.760177512462229},{"x":33,"y":6.760177512462229},{"x":34,"y":6.648308055437865},{"x":35,"y":6.648308055437865},{"x":36,"y":6.648308055437865},{"x":37,"y":6.58027355054484},{"x":38,"y":6.58027355054484},{"x":39,"y":6.58027355054484},{"x":40,"y":6.58027355054484},{"x":41,"y":6.54217089351845},{"x":42,"y":6.54217089351845},{"x":43,"y":6.54217089351845},{"x":44,"y":6.54217089351845},{"x":45,"y":6.4265076052238514},{"x":46,"y":6.58027355054484},{"x":47,"y":6.58027355054484},{"x":48,"y":6.723094525588644},{"x":49,"y":6.723094525588644},{"x":50,"y":6.648308055437865},{"x":51,"y":6.648308055437865},{"x":52,"y":6.760177512462229},{"x":53,"y":6.855654600401044},{"x":54,"y":7.035623639735144},{"x":55,"y":7.035623639735144},{"x":56,"y":6.942621983083913},{"x":57,"y":6.730527468185536},{"x":58,"y":6.9498201415576215},{"x":59,"y":6.9498201415576215},{"x":60,"y":6.9498201415576215},{"x":61,"y":7.19027120489902},{"x":62,"y":7.127411872482185},{"x":63,"y":7.395944834840239},{"x":64,"y":7.368853370776216},{"x":65,"y":7.368853370776216},{"x":66,"y":7.395944834840239},{"x":67,"y":7.44983221287567},{"x":68,"y":7.44983221287567},{"x":69,"y":7.5960516059331775},{"x":70,"y":7.893034904268446},{"x":71,"y":8.049844718999243},{"x":72,"y":7.76530746332687},{"x":73,"y":7.76530746332687},{"x":74,"y":8.080841540334768},{"x":75,"y":8.080841540334768},{"x":76,"y":8.080841540334768},{"x":77,"y":8.080841540334768},{"x":78,"y":8.080841540334768},{"x":79,"y":8.080841540334768},{"x":80,"y":8.080841540334768},{"x":81,"y":8.167006795638168},{"x":82,"y":8.526429498916883},{"x":83,"y":8.526429498916883},{"x":84,"y":8.28854631404084},{"x":85,"y":8.154753215150045},{"x":86,"y":8.497058314499201},{"x":87,"y":8.497058314499201},{"x":88,"y":8.700574693662482},{"x":89,"y":8.700574693662482},{"x":90,"y":8.803408430829505},{"x":91,"y":8.803408430829505},{"x":92,"y":8.700574693662482},{"x":93,"y":8.700574693662482},{"x":94,"y":9.121403400793104},{"x":95,"y":9.121403400793104},{"x":96,"y":9.57601169589929},{"x":97,"y":9.669539802906858},{"x":98,"y":10.03493896344168},{"x":99,"y":10.03493896344168},{"x":100,"y":10.03493896344168},{"x":101,"y":10.52140675005011},{"x":102,"y":10.52140675005011},{"x":103,"y":10.14889156509222},{"x":104,"y":10.2810505299799},{"x":105,"y":10.2810505299799},{"x":106,"y":10.2810505299799},{"x":107,"y":9.924716620639604},{"x":108,"y":10.41633332799983},{"x":109,"y":10.41633332799983},{"x":110,"y":10.931605554537724},{"x":111,"y":10.931605554537724},{"x":112,"y":10.793516572461451},{"x":113,"y":10.56882207249228},{"x":114,"y":10.358571330062848},{"x":115,"y":10.16366075781753},{"x":116,"y":10.16366075781753},{"x":117,"y":10.358571330062848},{"x":118,"y":10.56882207249228},{"x":119,"y":10.56882207249228},{"x":120,"y":10.793516572461451},{"x":121,"y":10.793516572461451},{"x":122,"y":10.56882207249228},{"x":123,"y":10.56882207249228},{"x":124,"y":10.56882207249228},{"x":125,"y":10.793516572461451},{"x":126,"y":10.793516572461451},{"x":127,"y":11.031772296417289},{"x":128,"y":10.667708282475669},{"x":129,"y":10.667708282475669},{"x":130,"y":10.310189135025603},{"x":131,"y":10.667708282475669},{"x":132,"y":11.031772296417289},{"x":133,"y":11.031772296417289},{"x":134,"y":11.031772296417289},{"x":135,"y":11.031772296417289},{"x":136,"y":10.667708282475669},{"x":137,"y":10.667708282475669},{"x":138,"y":10.667708282475669},{"x":139,"y":10.667708282475669},{"x":140,"y":10.830512453249845},{"x":141,"y":10.830512453249845},{"x":142,"y":11.371015785759864},{"x":143,"y":11.631852818876277},{"x":144,"y":12.177848742696717},{"x":145,"y":12.739701723352868},{"x":146,"y":12.739701723352868},{"x":147,"y":13.107249902248755},{"x":148,"y":13.479614237803691},{"x":149,"y":13.479614237803691},{"x":150,"y":13.856406460551018},{"x":151,"y":13.379088160259652},{"x":152,"y":13.379088160259652},{"x":153,"y":13.379088160259652},{"x":154,"y":13.754999091239519},{"x":155,"y":13.754999091239519},{"x":156,"y":13.553597308463905},{"x":157,"y":13.939153489362258},{"x":158,"y":13.939153489362258},{"x":159,"y":13.939153489362258},{"x":160,"y":13.831124321616086},{"x":161,"y":13.649175799292792},{"x":162,"y":13.479614237803691},{"x":163,"y":13.442470011125186},{"x":164,"y":13.2664991614216},{"x":165,"y":13.103434664239753},{"x":166,"y":13.2664991614216},{"x":167,"y":13.442470011125186},{"x":168,"y":13.442470011125186},{"x":169,"y":13.63084736911099},{"x":170,"y":13.649175799292792},{"x":171,"y":13.557285864065861},{"x":172,"y":13.758633653092156},{"x":173,"y":13.663820841916802},{"x":174,"y":13.663820841916802},{"x":175,"y":13.663820841916802},{"x":176,"y":13.663820841916802},{"x":177,"y":13.479614237803691},{"x":178,"y":13.479614237803691},{"x":179,"y":14.060583202698243},{"x":180,"y":14.060583202698243},{"x":181,"y":14.247806848775006},{"x":182,"y":13.765899897936205},{"x":183,"y":13.638181696985855},{"x":184,"y":13.590437814875575},{"x":185,"y":13.638181696985855},{"x":186,"y":13.236313686219438},{"x":187,"y":13.638181696985855},{"x":188,"y":13.236313686219438},{"x":189,"y":13.19090595827292},{"x":190,"y":13.19090595827292},{"x":191,"y":13.236313686219438},{"x":192,"y":13.30413469565007},{"x":193,"y":13.171939872319491},{"x":194,"y":13.171939872319491},{"x":195,"y":13.171939872319491},{"x":196,"y":13.171939872319491},{"x":197,"y":13.171939872319491},{"x":198,"y":13.171939872319491},{"x":199,"y":13.367871932360812},{"x":200,"y":13.45362404707371},{"x":201,"y":13.341664064126334},{"x":202,"y":13.341664064126334},{"x":203,"y":13.442470011125186},{"x":204,"y":13.049904214207858},{"x":205,"y":12.660963628413123},{"x":206,"y":12.557866060760482},{"x":207,"y":12.477980605851252},{"x":208,"y":12.497999839974394},{"x":209,"y":12.735776379946374},{"x":210,"y":12.735776379946374},{"x":211,"y":12.884098726725126},{"x":212,"y":12.62933094031509},{"x":213,"y":12.62933094031509},{"x":214,"y":12.62933094031509},{"x":215,"y":12.62933094031509},{"x":216,"y":12.62933094031509},{"x":217,"y":12.103718436910205},{"x":218,"y":12.349089035228468},{"x":219,"y":12.103718436910205},{"x":220,"y":12.041594578792296},{"x":221,"y":12.041594578792296},{"x":222,"y":11.861703081766969},{"x":223,"y":11.696153213770756},{"x":224,"y":11.696153213770756},{"x":225,"y":11.696153213770756},{"x":226,"y":11.696153213770756},{"x":227,"y":11.545561917897285},{"x":228,"y":11.41052146047673},{"x":229,"y":11.291589790636214},{"x":230,"y":11.189280584559492},{"x":231,"y":11.189280584559492},{"x":232,"y":11.291589790636214},{"x":233,"y":11.41052146047673},{"x":234,"y":11.291589790636214},{"x":235,"y":11.291589790636214},{"x":236,"y":11.291589790636214},{"x":237,"y":11.189280584559492},{"x":238,"y":11.291589790636214},{"x":239,"y":11.224972160321824},{"x":240,"y":11.224972160321824},{"x":241,"y":11.224972160321824},{"x":242,"y":11.099549540409287},{"x":243,"y":11.099549540409287},{"x":244,"y":10.99090533122727},{"x":245,"y":10.99090533122727},{"x":246,"y":10.89954127475097},{"x":247,"y":10.89954127475097},{"x":248,"y":10.807404868885037},{"x":249,"y":10.922453936730518},{"x":250,"y":11.054410884348384},{"x":251,"y":11.054410884348384},{"x":252,"y":11.054410884348384},{"x":253,"y":11.20267825120404},{"x":254,"y":11.20267825120404},{"x":255,"y":11.20267825120404},{"x":256,"y":11.20267825120404},{"x":257,"y":11.224972160321824},{"x":258,"y":11.224972160321824},{"x":259,"y":11.054410884348384},{"x":260,"y":10.89954127475097},{"x":261,"y":10.89954127475097},{"x":262,"y":11.054410884348384},{"x":263,"y":11.054410884348384},{"x":264,"y":11.054410884348384},{"x":265,"y":11.224972160321824},{"x":266,"y":11.054410884348384},{"x":267,"y":11.224972160321824},{"x":268,"y":11.41052146047673},{"x":269,"y":11.497825881443848},{"x":270,"y":11.497825881443848},{"x":271,"y":11.627553482998906},{"x":272,"y":11.388590782006348},{"x":273,"y":11.313708498984761},{"x":274,"y":11.113055385446435},{"x":275,"y":11.166915420114902},{"x":276,"y":11.166915420114902},{"x":277,"y":11.166915420114902},{"x":278,"y":11.077003204838391},{"x":279,"y":11.313708498984761},{"x":280,"y":11.554220008291344},{"x":281,"y":11.734564329364767},{"x":282,"y":12.12435565298214},{"x":283,"y":12.12435565298214},{"x":284,"y":11.874342087037917},{"x":285,"y":11.874342087037917},{"x":286,"y":12.049896265113654},{"x":287,"y":12.049896265113654},{"x":288,"y":11.653325705565772},{"x":289,"y":11.653325705565772},{"x":290,"y":11.653325705565772},{"x":291,"y":11.588787684654509},{"x":292,"y":11.414902540100814},{"x":293,"y":11.414902540100814},{"x":294,"y":11.414902540100814},{"x":295,"y":11.588787684654509},{"x":296,"y":11.991663771137015},{"x":297,"y":11.781341180018513},{"x":298,"y":11.781341180018513},{"x":299,"y":11.371015785759864},{"x":300,"y":11.371015785759864}]},{"name":"714b7663-6990-43ef-8e70-6944439f39cb","values":[{"x":0,"y":0.0},{"x":1,"y":0.0},{"x":2,"y":0.4472135954999579},{"x":3,"y":0.4472135954999579},{"x":4,"y":0.4472135954999579},{"x":5,"y":0.7071067811865476},{"x":6,"y":0.7071067811865476},{"x":7,"y":0.7071067811865476},{"x":8,"y":0.8366600265340756},{"x":9,"y":0.8366600265340756},{"x":10,"y":1.140175425099138},{"x":11,"y":1.140175425099138},{"x":12,"y":1.140175425099138},{"x":13,"y":0.8944271909999159},{"x":14,"y":0.8944271909999159},{"x":15,"y":0.8944271909999159},{"x":16,"y":0.8944271909999159},{"x":17,"y":0.8944271909999159},{"x":18,"y":1.3038404810405297},{"x":19,"y":1.3038404810405297},{"x":20,"y":1.3038404810405297},{"x":21,"y":1.3038404810405297},{"x":22,"y":1.3038404810405297},{"x":23,"y":1.3038404810405297},{"x":24,"y":1.3038404810405297},{"x":25,"y":1.3038404810405297},{"x":26,"y":1.3038404810405297},{"x":27,"y":1.3038404810405297},{"x":28,"y":0.8944271909999159},{"x":29,"y":1.140175425099138},{"x":30,"y":1.3416407864998738},{"x":31,"y":1.3416407864998738},{"x":32,"y":1.140175425099138},{"x":33,"y":1.140175425099138},{"x":34,"y":1.6733200530681511},{"x":35,"y":1.9235384061671346},{"x":36,"y":2.588435821108957},{"x":37,"y":2.588435821108957},{"x":38,"y":2.588435821108957},{"x":39,"y":2.3021728866442674},{"x":40,"y":2.3021728866442674},{"x":41,"y":2.3021728866442674},{"x":42,"y":2.9664793948382653},{"x":43,"y":2.701851217221259},{"x":44,"y":2.4899799195977463},{"x":45,"y":3.1144823004794873},{"x":46,"y":3.1144823004794873},{"x":47,"y":3.2093613071762426},{"x":48,"y":3.2093613071762426},{"x":49,"y":3.1144823004794873},{"x":50,"y":3.3466401061363023},{"x":51,"y":3.1622776601683795},{"x":52,"y":3.0},{"x":53,"y":3.03315017762062},{"x":54,"y":3.03315017762062},{"x":55,"y":2.9664793948382653},{"x":56,"y":2.9664793948382653},{"x":57,"y":2.9664793948382653},{"x":58,"y":2.9664793948382653},{"x":59,"y":3.0495901363953815},{"x":60,"y":3.0495901363953815},{"x":61,"y":3.0495901363953815},{"x":62,"y":3.1304951684997055},{"x":63,"y":3.0495901363953815},{"x":64,"y":3.0495901363953815},{"x":65,"y":3.0495901363953815},{"x":66,"y":3.0495901363953815},{"x":67,"y":2.9664793948382653},{"x":68,"y":3.4351128074635335},{"x":69,"y":3.4351128074635335},{"x":70,"y":3.4351128074635335},{"x":71,"y":3.4351128074635335},{"x":72,"y":3.4351128074635335},{"x":73,"y":3.5071355833500366},{"x":74,"y":3.5071355833500366},{"x":75,"y":3.5071355833500366},{"x":76,"y":3.420526275297414},{"x":77,"y":3.5355339059327378},{"x":78,"y":3.7013511046643495},{"x":79,"y":3.646916505762094},{"x":80,"y":3.847076812334269},{"x":81,"y":3.847076812334269},{"x":82,"y":3.847076812334269},{"x":83,"y":4.219004621945797},{"x":84,"y":4.722287581247038},{"x":85,"y":4.878524367060187},{"x":86,"y":5.128352561983234},{"x":87,"y":5.128352561983234},{"x":88,"y":4.61519230368573},{"x":89,"y":4.54972526643093},{"x":90,"y":4.54972526643093},{"x":91,"y":4.358898943540674},{"x":92,"y":4.604345773288535},{"x":93,"y":4.898979485566356},{"x":94,"y":4.898979485566356},{"x":95,"y":5.167204273105526},{"x":96,"y":4.868264577855234},{"x":97,"y":4.636809247747852},{"x":98,"y":4.868264577855234},{"x":99,"y":5.167204273105526},{"x":100,"y":5.412947441089743},{"x":101,"y":5.412947441089743},{"x":102,"y":5.412947441089743},{"x":103,"y":5.412947441089743},{"x":104,"y":5.412947441089743},{"x":105,"y":5.683308895353129},{"x":106,"y":5.540758070878027},{"x":107,"y":5.540758070878027},{"x":108,"y":5.540758070878027},{"x":109,"y":5.830951894845301},{"x":110,"y":5.830951894845301},{"x":111,"y":5.830951894845301},{"x":112,"y":5.787918451395113},{"x":113,"y":5.495452665613635},{"x":114,"y":5.366563145999495},{"x":115,"y":5.079370039680118},{"x":116,"y":5.079370039680118},{"x":117,"y":5.079370039680118},{"x":118,"y":5.079370039680118},{"x":119,"y":5.504543577809154},{"x":120,"y":5.504543577809154},{"x":121,"y":5.224940191045253},{"x":122,"y":4.979959839195493},{"x":123,"y":4.979959839195493},{"x":124,"y":4.8166378315169185},{"x":125,"y":4.8166378315169185},{"x":126,"y":5.0990195135927845},{"x":127,"y":5.0990195135927845},{"x":128,"y":5.0990195135927845},{"x":129,"y":5.0990195135927845},{"x":130,"y":5.0990195135927845},{"x":131,"y":5.0990195135927845},{"x":132,"y":4.969909455915671},{"x":133,"y":4.969909455915671},{"x":134,"y":4.8270073544588685},{"x":135,"y":4.505552130427524},{"x":136,"y":4.505552130427524},{"x":137,"y":4.878524367060187},{"x":138,"y":5.319774431308154},{"x":139,"y":5.272570530585627},{"x":140,"y":5.029910535983717},{"x":141,"y":5.504543577809154},{"x":142,"y":5.029910535983717},{"x":143,"y":4.560701700396552},{"x":144,"y":4.560701700396552},{"x":145,"y":4.159326868617084},{"x":146,"y":4.33589667773576},{"x":147,"y":4.54972526643093},{"x":148,"y":4.358898943540674},{"x":149,"y":4.847679857416329},{"x":150,"y":5.06951674225463},{"x":151,"y":5.585696017507576},{"x":152,"y":6.1400325732035},{"x":153,"y":6.1400325732035},{"x":154,"y":6.1400325732035},{"x":155,"y":6.363961030678928},{"x":156,"y":6.363961030678928},{"x":157,"y":6.363961030678928},{"x":158,"y":6.363961030678928},{"x":159,"y":6.1400325732035},{"x":160,"y":6.300793600809346},{"x":161,"y":6.300793600809346},{"x":162,"y":6.06630035524124},{"x":163,"y":6.06630035524124},{"x":164,"y":5.856620185738529},{"x":165,"y":5.805170109479997},{"x":166,"y":5.805170109479997},{"x":167,"y":5.805170109479997},{"x":168,"y":5.449770637375485},{"x":169,"y":5.458937625582473},{"x":170,"y":5.458937625582473},{"x":171,"y":5.683308895353129},{"x":172,"y":5.683308895353129},{"x":173,"y":5.718391382198319},{"x":174,"y":5.718391382198319},{"x":175,"y":5.787918451395113},{"x":176,"y":5.585696017507576},{"x":177,"y":6.016643582596529},{"x":178,"y":6.016643582596529},{"x":179,"y":5.674504383644443},{"x":180,"y":5.549774770204643},{"x":181,"y":5.549774770204643},{"x":182,"y":6.024948132556827},{"x":183,"y":6.465291950097845},{"x":184,"y":6.465291950097845},{"x":185,"y":6.465291950097845},{"x":186,"y":6.465291950097845},{"x":187,"y":6.6558245169174945},{"x":188,"y":6.6558245169174945},{"x":189,"y":6.6558245169174945},{"x":190,"y":6.268971207462991},{"x":191,"y":6.06630035524124},{"x":192,"y":6.06630035524124},{"x":193,"y":6.2289646009589745},{"x":194,"y":6.3796551630946325},{"x":195,"y":6.670832032063167},{"x":196,"y":6.670832032063167},{"x":197,"y":6.670832032063167},{"x":198,"y":6.670832032063167},{"x":199,"y":6.723094525588644},{"x":200,"y":6.442049363362563},{"x":201,"y":6.442049363362563},{"x":202,"y":6.442049363362563},{"x":203,"y":6.442049363362563},{"x":204,"y":6.442049363362563},{"x":205,"y":6.610597552415364},{"x":206,"y":6.610597552415364},{"x":207,"y":6.610597552415364},{"x":208,"y":6.610597552415364},{"x":209,"y":6.610597552415364},{"x":210,"y":6.6932802122726045},{"x":211,"y":6.54217089351845},{"x":212,"y":6.9498201415576215},{"x":213,"y":7.12039324756716},{"x":214,"y":7.3824115301167},{"x":215,"y":7.0},{"x":216,"y":7.293833011524188},{"x":217,"y":7.791020472312982},{"x":218,"y":7.791020472312982},{"x":219,"y":8.258329128825032},{"x":220,"y":8.258329128825032},{"x":221,"y":8.258329128825032},{"x":222,"y":8.258329128825032},{"x":223,"y":8.258329128825032},{"x":224,"y":7.968688725254614},{"x":225,"y":7.694153624668538},{"x":226,"y":7.694153624668538},{"x":227,"y":7.694153624668538},{"x":228,"y":7.694153624668538},{"x":229,"y":7.694153624668538},{"x":230,"y":7.694153624668538},{"x":231,"y":7.694153624668538},{"x":232,"y":7.694153624668538},{"x":233,"y":7.745966692414834},{"x":234,"y":7.745966692414834},{"x":235,"y":8.031189202104505},{"x":236,"y":8.031189202104505},{"x":237,"y":7.791020472312982},{"x":238,"y":7.791020472312982},{"x":239,"y":7.791020472312982},{"x":240,"y":7.395944834840239},{"x":241,"y":6.942621983083913},{"x":242,"y":6.730527468185536},{"x":243,"y":6.418722614352485},{"x":244,"y":6.348228099241551},{"x":245,"y":6.348228099241551},{"x":246,"y":6.348228099241551},{"x":247,"y":6.348228099241551},{"x":248,"y":6.348228099241551},{"x":249,"y":6.348228099241551},{"x":250,"y":6.418722614352485},{"x":251,"y":6.418722614352485},{"x":252,"y":6.418722614352485},{"x":253,"y":6.418722614352485},{"x":254,"y":6.016643582596529},{"x":255,"y":6.016643582596529},{"x":256,"y":6.016643582596529},{"x":257,"y":6.016643582596529},{"x":258,"y":6.016643582596529},{"x":259,"y":6.016643582596529},{"x":260,"y":6.016643582596529},{"x":261,"y":6.016643582596529},{"x":262,"y":6.123724356957945},{"x":263,"y":5.974947698515862},{"x":264,"y":5.974947698515862},{"x":265,"y":5.974947698515862},{"x":266,"y":5.856620185738529},{"x":267,"y":5.856620185738529},{"x":268,"y":6.016643582596529},{"x":269,"y":6.016643582596529},{"x":270,"y":5.856620185738529},{"x":271,"y":5.856620185738529},{"x":272,"y":5.856620185738529},{"x":273,"y":5.856620185738529},{"x":274,"y":5.856620185738529},{"x":275,"y":5.856620185738529},{"x":276,"y":5.974947698515862},{"x":277,"y":5.974947698515862},{"x":278,"y":6.1400325732035},{"x":279,"y":6.284902544988268},{"x":280,"y":6.041522986797286},{"x":281,"y":6.300793600809346},{"x":282,"y":6.300793600809346},{"x":283,"y":6.300793600809346},{"x":284,"y":6.534523701081817},{"x":285,"y":6.442049363362563},{"x":286,"y":6.442049363362563},{"x":287,"y":6.442049363362563},{"x":288,"y":6.442049363362563},{"x":289,"y":6.534523701081817},{"x":290,"y":6.442049363362563},{"x":291,"y":6.442049363362563},{"x":292,"y":6.610597552415364},{"x":293,"y":6.610597552415364},{"x":294,"y":6.348228099241551},{"x":295,"y":6.610597552415364},{"x":296,"y":6.8044103344816},{"x":297,"y":7.127411872482185},{"x":298,"y":7.503332592921628},{"x":299,"y":7.503332592921628},{"x":300,"y":7.503332592921628}]}],"marks":[{"type":"line","from":{"data":"60975aaa-915c-4d46-b470-859048bdcb46"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"stroke":{"value":"Blue"},"strokeWidth":{"value":2},"strokeOpacity":{"value":1}}}},{"type":"line","from":{"data":"3fbff5f7-e3e1-412d-b281-19a1d347c58f"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"stroke":{"value":"Red"},"strokeWidth":{"value":2},"strokeOpacity":{"value":1}}}},{"type":"line","from":{"data":"714b7663-6990-43ef-8e70-6944439f39cb"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"stroke":{"value":"Green"},"strokeWidth":{"value":2},"strokeOpacity":{"value":1}}}}]},"value":"#gorilla_repl.vega.VegaView{:content {:width 400, :height 247.2188, :padding {:top 10, :left 55, :bottom 40, :right 10}, :scales [{:name \"x\", :type \"linear\", :range \"width\", :zero false, :domain {:data \"60975aaa-915c-4d46-b470-859048bdcb46\", :field \"data.x\"}} {:name \"y\", :type \"linear\", :range \"height\", :nice true, :zero false, :domain [0.0 15.449919093639293]}], :axes [{:type \"x\", :scale \"x\"} {:type \"y\", :scale \"y\"}], :data ({:name \"60975aaa-915c-4d46-b470-859048bdcb46\", :values ({:x 0, :y 0.0} {:x 1, :y 0.0} {:x 2, :y 0.0} {:x 3, :y 0.4472135954999579} {:x 4, :y 0.4472135954999579} {:x 5, :y 0.0} {:x 6, :y 0.0} {:x 7, :y 0.0} {:x 8, :y 0.4472135954999579} {:x 9, :y 0.0} {:x 10, :y 0.4472135954999579} {:x 11, :y 0.4472135954999579} {:x 12, :y 0.4472135954999579} {:x 13, :y 0.4472135954999579} {:x 14, :y 0.4472135954999579} {:x 15, :y 0.4472135954999579} {:x 16, :y 0.4472135954999579} {:x 17, :y 0.4472135954999579} {:x 18, :y 0.4472135954999579} {:x 19, :y 0.4472135954999579} {:x 20, :y 0.4472135954999579} {:x 21, :y 0.7071067811865476} {:x 22, :y 0.7071067811865476} {:x 23, :y 1.4142135623730951} {:x 24, :y 1.7888543819998317} {:x 25, :y 2.1908902300206643} {:x 26, :y 2.6076809620810595} {:x 27, :y 2.1908902300206643} {:x 28, :y 2.1908902300206643} {:x 29, :y 2.8809720581775866} {:x 30, :y 2.4899799195977463} {:x 31, :y 3.1937438845342623} {:x 32, :y 3.1937438845342623} {:x 33, :y 3.5777087639996634} {:x 34, :y 3.5777087639996634} {:x 35, :y 3.9749213828703582} {:x 36, :y 4.669047011971501} {:x 37, :y 4.277849927241488} {:x 38, :y 4.669047011971501} {:x 39, :y 4.277849927241488} {:x 40, :y 4.669047011971501} {:x 41, :y 4.277849927241488} {:x 42, :y 4.979959839195493} {:x 43, :y 4.604345773288535} {:x 44, :y 4.604345773288535} {:x 45, :y 4.604345773288535} {:x 46, :y 4.979959839195493} {:x 47, :y 4.979959839195493} {:x 48, :y 4.979959839195493} {:x 49, :y 4.979959839195493} {:x 50, :y 4.979959839195493} {:x 51, :y 4.979959839195493} {:x 52, :y 4.979959839195493} {:x 53, :y 4.979959839195493} {:x 54, :y 5.683308895353129} {:x 55, :y 5.683308895353129} {:x 56, :y 5.310367218940701} {:x 57, :y 5.310367218940701} {:x 58, :y 6.016643582596529} {:x 59, :y 5.656854249492381} {:x 60, :y 6.016643582596529} {:x 61, :y 6.387487769068525} {:x 62, :y 6.387487769068525} {:x 63, :y 6.387487769068525} {:x 64, :y 6.016643582596529} {:x 65, :y 5.656854249492381} {:x 66, :y 5.310367218940701} {:x 67, :y 5.310367218940701} {:x 68, :y 6.016643582596529} {:x 69, :y 6.016643582596529} {:x 70, :y 6.363961030678928} {:x 71, :y 6.363961030678928} {:x 72, :y 6.016643582596529} {:x 73, :y 5.683308895353129} {:x 74, :y 6.387487769068525} {:x 75, :y 6.723094525588644} {:x 76, :y 6.723094525588644} {:x 77, :y 6.387487769068525} {:x 78, :y 6.06630035524124} {:x 79, :y 5.761944116355173} {:x 80, :y 5.477225575051661} {:x 81, :y 5.215361924162119} {:x 82, :y 5.890670590009257} {:x 83, :y 6.572670690061994} {:x 84, :y 6.572670690061994} {:x 85, :y 6.572670690061994} {:x 86, :y 6.572670690061994} {:x 87, :y 6.855654600401044} {:x 88, :y 6.855654600401044} {:x 89, :y 6.855654600401044} {:x 90, :y 6.572670690061994} {:x 91, :y 6.572670690061994} {:x 92, :y 6.572670690061994} {:x 93, :y 6.572670690061994} {:x 94, :y 7.259476565152615} {:x 95, :y 6.985699678629192} {:x 96, :y 6.985699678629192} {:x 97, :y 6.985699678629192} {:x 98, :y 6.985699678629192} {:x 99, :y 7.6681158050723255} {:x 100, :y 7.402702209328699} {:x 101, :y 8.080841540334768} {:x 102, :y 8.35463942968217} {:x 103, :y 8.080841540334768} {:x 104, :y 8.080841540334768} {:x 105, :y 7.8230428862431785} {:x 106, :y 7.8230428862431785} {:x 107, :y 7.582875444051551} {:x 108, :y 8.246211251235321} {:x 109, :y 8.012490249604053} {:x 110, :y 8.67179335547152} {:x 111, :y 8.916277250063503} {:x 112, :y 8.916277250063503} {:x 113, :y 8.916277250063503} {:x 114, :y 8.916277250063503} {:x 115, :y 8.916277250063503} {:x 116, :y 9.17605579756357} {:x 117, :y 9.17605579756357} {:x 118, :y 9.17605579756357} {:x 119, :y 9.85900603509299} {:x 120, :y 9.85900603509299} {:x 121, :y 9.85900603509299} {:x 122, :y 9.85900603509299} {:x 123, :y 10.13903348450926} {:x 124, :y 9.85900603509299} {:x 125, :y 9.85900603509299} {:x 126, :y 9.591663046625438} {:x 127, :y 9.591663046625438} {:x 128, :y 9.338094023943002} {:x 129, :y 9.591663046625438} {:x 130, :y 9.338094023943002} {:x 131, :y 9.591663046625438} {:x 132, :y 9.591663046625438} {:x 133, :y 9.85900603509299} {:x 134, :y 9.85900603509299} {:x 135, :y 9.85900603509299} {:x 136, :y 9.85900603509299} {:x 137, :y 10.545141061171254} {:x 138, :y 11.233877335986895} {:x 139, :y 11.233877335986895} {:x 140, :y 11.233877335986895} {:x 141, :y 11.924764148611073} {:x 142, :y 11.924764148611073} {:x 143, :y 11.924764148611073} {:x 144, :y 12.617448236470002} {:x 145, :y 12.617448236470002} {:x 146, :y 12.328828005937952} {:x 147, :y 12.328828005937952} {:x 148, :y 12.328828005937952} {:x 149, :y 13.019216566291536} {:x 150, :y 13.019216566291536} {:x 151, :y 13.019216566291536} {:x 152, :y 13.711309200802088} {:x 153, :y 14.007141035914502} {:x 154, :y 13.612494260788505} {:x 155, :y 13.612494260788505} {:x 156, :y 13.202272531651511} {:x 157, :y 12.817956155331473} {:x 158, :y 12.817956155331473} {:x 159, :y 12.817956155331473} {:x 160, :y 12.817956155331473} {:x 161, :y 12.856904759700136} {:x 162, :y 12.856904759700136} {:x 163, :y 12.856904759700136} {:x 164, :y 12.856904759700136} {:x 165, :y 12.856904759700136} {:x 166, :y 12.817956155331473} {:x 167, :y 12.794530081249565} {:x 168, :y 12.794530081249565} {:x 169, :y 12.794530081249565} {:x 170, :y 12.794530081249565} {:x 171, :y 12.794530081249565} {:x 172, :y 13.179529581893277} {:x 173, :y 13.179529581893277} {:x 174, :y 13.171939872319491} {:x 175, :y 13.198484761517134} {:x 176, :y 13.198484761517134} {:x 177, :y 13.198484761517134} {:x 178, :y 13.24009063413087} {:x 179, :y 13.24009063413087} {:x 180, :y 13.24009063413087} {:x 181, :y 13.21741275741966} {:x 182, :y 13.21741275741966} {:x 183, :y 13.21741275741966} {:x 184, :y 13.171939872319491} {:x 185, :y 13.171939872319491} {:x 186, :y 13.171939872319491} {:x 187, :y 13.171939872319491} {:x 188, :y 13.171939872319491} {:x 189, :y 12.794530081249565} {:x 190, :y 12.794530081249565} {:x 191, :y 12.794530081249565} {:x 192, :y 12.794530081249565} {:x 193, :y 12.794530081249565} {:x 194, :y 12.421755109484328} {:x 195, :y 12.421755109484328} {:x 196, :y 12.417729261020309} {:x 197, :y 12.429802894656053} {:x 198, :y 12.457929201917949} {:x 199, :y 12.457929201917949} {:x 200, :y 12.457929201917949} {:x 201, :y 12.794530081249565} {:x 202, :y 12.794530081249565} {:x 203, :y 13.164345787011218} {:x 204, :y 13.164345787011218} {:x 205, :y 13.164345787011218} {:x 206, :y 13.126309458488324} {:x 207, :y 13.501851724856113} {:x 208, :y 13.501851724856113} {:x 209, :y 13.903237033151669} {:x 210, :y 13.939153489362258} {:x 211, :y 13.939153489362258} {:x 212, :y 13.939153489362258} {:x 213, :y 13.553597308463905} {:x 214, :y 13.601470508735444} {:x 215, :y 13.601470508735444} {:x 216, :y 13.601470508735444} {:x 217, :y 13.601470508735444} {:x 218, :y 14.053469322555197} {:x 219, :y 14.053469322555197} {:x 220, :y 14.45683229480096} {:x 221, :y 14.446452851824906} {:x 222, :y 14.474114826130128} {:x 223, :y 14.515508947329405} {:x 224, :y 14.474114826130128} {:x 225, :y 14.446452851824906} {:x 226, :y 14.449913494550755} {:x 227, :y 14.53272169966796} {:x 228, :y 14.560219778561036} {:x 229, :y 14.601369798755183} {:x 230, :y 14.690132742763083} {:x 231, :y 14.720733677368122} {:x 232, :y 14.679918255903198} {:x 233, :y 14.679918255903198} {:x 234, :y 14.788509052639485} {:x 235, :y 15.15585695366646} {:x 236, :y 15.182226450688976} {:x 237, :y 15.182226450688976} {:x 238, :y 15.182226450688976} {:x 239, :y 15.182226450688976} {:x 240, :y 15.182226450688976} {:x 241, :y 14.679918255903198} {:x 242, :y 14.679918255903198} {:x 243, :y 14.679918255903198} {:x 244, :y 14.679918255903198} {:x 245, :y 14.720733677368122} {:x 246, :y 14.832396974191326} {:x 247, :y 14.933184523068078} {:x 248, :y 14.933184523068078} {:x 249, :y 14.889593681494468} {:x 250, :y 14.889593681494468} {:x 251, :y 14.959946523968593} {:x 252, :y 15.043270920913443} {:x 253, :y 15.043270920913443} {:x 254, :y 14.553350129781116} {:x 255, :y 14.652644812456213} {:x 256, :y 14.7648230602334} {:x 257, :y 14.7648230602334} {:x 258, :y 14.889593681494468} {:x 259, :y 15.016657417681207} {:x 260, :y 15.15585695366646} {:x 261, :y 15.297058540778355} {:x 262, :y 15.297058540778355} {:x 263, :y 15.254507530562892} {:x 264, :y 15.449919093639293} {:x 265, :y 15.449919093639293} {:x 266, :y 15.449919093639293} {:x 267, :y 15.449919093639293} {:x 268, :y 15.449919093639293} {:x 269, :y 15.449919093639293} {:x 270, :y 15.404544783926593} {:x 271, :y 15.404544783926593} {:x 272, :y 14.926486525636232} {:x 273, :y 14.926486525636232} {:x 274, :y 14.467204291085407} {:x 275, :y 14.467204291085407} {:x 276, :y 14.467204291085407} {:x 277, :y 14.515508947329405} {:x 278, :y 14.515508947329405} {:x 279, :y 14.515508947329405} {:x 280, :y 14.515508947329405} {:x 281, :y 14.515508947329405} {:x 282, :y 14.19506956657839} {:x 283, :y 14.078352176302452} {:x 284, :y 14.078352176302452} {:x 285, :y 14.028542333400146} {:x 286, :y 14.345731072343439} {:x 287, :y 14.237275020171522} {:x 288, :y 14.237275020171522} {:x 289, :y 14.237275020171522} {:x 290, :y 14.142135623730951} {:x 291, :y 14.060583202698243} {:x 292, :y 14.060583202698243} {:x 293, :y 14.289856542317} {:x 294, :y 14.289856542317} {:x 295, :y 14.289856542317} {:x 296, :y 14.289856542317} {:x 297, :y 14.289856542317} {:x 298, :y 14.68672870315238} {:x 299, :y 14.68672870315238} {:x 300, :y 14.604793733565703})} {:name \"3fbff5f7-e3e1-412d-b281-19a1d347c58f\", :values ({:x 0, :y 0.0} {:x 1, :y 0.4472135954999579} {:x 2, :y 0.8944271909999159} {:x 3, :y 0.8944271909999159} {:x 4, :y 1.3416407864998738} {:x 5, :y 1.3416407864998738} {:x 6, :y 1.7888543819998317} {:x 7, :y 2.23606797749979} {:x 8, :y 2.23606797749979} {:x 9, :y 2.23606797749979} {:x 10, :y 2.23606797749979} {:x 11, :y 2.6832815729997477} {:x 12, :y 3.1304951684997055} {:x 13, :y 3.1304951684997055} {:x 14, :y 3.5777087639996634} {:x 15, :y 4.024922359499621} {:x 16, :y 4.604345773288535} {:x 17, :y 5.049752469181039} {:x 18, :y 5.495452665613635} {:x 19, :y 5.941380311005179} {:x 20, :y 6.387487769068525} {:x 21, :y 6.387487769068525} {:x 22, :y 6.833739825307955} {:x 23, :y 6.833739825307955} {:x 24, :y 6.833739825307955} {:x 25, :y 6.833739825307955} {:x 26, :y 6.833739825307955} {:x 27, :y 6.745368781616021} {:x 28, :y 6.745368781616021} {:x 29, :y 6.745368781616021} {:x 30, :y 6.745368781616021} {:x 31, :y 6.819090848492928} {:x 32, :y 6.760177512462229} {:x 33, :y 6.760177512462229} {:x 34, :y 6.648308055437865} {:x 35, :y 6.648308055437865} {:x 36, :y 6.648308055437865} {:x 37, :y 6.58027355054484} {:x 38, :y 6.58027355054484} {:x 39, :y 6.58027355054484} {:x 40, :y 6.58027355054484} {:x 41, :y 6.54217089351845} {:x 42, :y 6.54217089351845} {:x 43, :y 6.54217089351845} {:x 44, :y 6.54217089351845} {:x 45, :y 6.4265076052238514} {:x 46, :y 6.58027355054484} {:x 47, :y 6.58027355054484} {:x 48, :y 6.723094525588644} {:x 49, :y 6.723094525588644} {:x 50, :y 6.648308055437865} {:x 51, :y 6.648308055437865} {:x 52, :y 6.760177512462229} {:x 53, :y 6.855654600401044} {:x 54, :y 7.035623639735144} {:x 55, :y 7.035623639735144} {:x 56, :y 6.942621983083913} {:x 57, :y 6.730527468185536} {:x 58, :y 6.9498201415576215} {:x 59, :y 6.9498201415576215} {:x 60, :y 6.9498201415576215} {:x 61, :y 7.19027120489902} {:x 62, :y 7.127411872482185} {:x 63, :y 7.395944834840239} {:x 64, :y 7.368853370776216} {:x 65, :y 7.368853370776216} {:x 66, :y 7.395944834840239} {:x 67, :y 7.44983221287567} {:x 68, :y 7.44983221287567} {:x 69, :y 7.5960516059331775} {:x 70, :y 7.893034904268446} {:x 71, :y 8.049844718999243} {:x 72, :y 7.76530746332687} {:x 73, :y 7.76530746332687} {:x 74, :y 8.080841540334768} {:x 75, :y 8.080841540334768} {:x 76, :y 8.080841540334768} {:x 77, :y 8.080841540334768} {:x 78, :y 8.080841540334768} {:x 79, :y 8.080841540334768} {:x 80, :y 8.080841540334768} {:x 81, :y 8.167006795638168} {:x 82, :y 8.526429498916883} {:x 83, :y 8.526429498916883} {:x 84, :y 8.28854631404084} {:x 85, :y 8.154753215150045} {:x 86, :y 8.497058314499201} {:x 87, :y 8.497058314499201} {:x 88, :y 8.700574693662482} {:x 89, :y 8.700574693662482} {:x 90, :y 8.803408430829505} {:x 91, :y 8.803408430829505} {:x 92, :y 8.700574693662482} {:x 93, :y 8.700574693662482} {:x 94, :y 9.121403400793104} {:x 95, :y 9.121403400793104} {:x 96, :y 9.57601169589929} {:x 97, :y 9.669539802906858} {:x 98, :y 10.03493896344168} {:x 99, :y 10.03493896344168} {:x 100, :y 10.03493896344168} {:x 101, :y 10.52140675005011} {:x 102, :y 10.52140675005011} {:x 103, :y 10.14889156509222} {:x 104, :y 10.2810505299799} {:x 105, :y 10.2810505299799} {:x 106, :y 10.2810505299799} {:x 107, :y 9.924716620639604} {:x 108, :y 10.41633332799983} {:x 109, :y 10.41633332799983} {:x 110, :y 10.931605554537724} {:x 111, :y 10.931605554537724} {:x 112, :y 10.793516572461451} {:x 113, :y 10.56882207249228} {:x 114, :y 10.358571330062848} {:x 115, :y 10.16366075781753} {:x 116, :y 10.16366075781753} {:x 117, :y 10.358571330062848} {:x 118, :y 10.56882207249228} {:x 119, :y 10.56882207249228} {:x 120, :y 10.793516572461451} {:x 121, :y 10.793516572461451} {:x 122, :y 10.56882207249228} {:x 123, :y 10.56882207249228} {:x 124, :y 10.56882207249228} {:x 125, :y 10.793516572461451} {:x 126, :y 10.793516572461451} {:x 127, :y 11.031772296417289} {:x 128, :y 10.667708282475669} {:x 129, :y 10.667708282475669} {:x 130, :y 10.310189135025603} {:x 131, :y 10.667708282475669} {:x 132, :y 11.031772296417289} {:x 133, :y 11.031772296417289} {:x 134, :y 11.031772296417289} {:x 135, :y 11.031772296417289} {:x 136, :y 10.667708282475669} {:x 137, :y 10.667708282475669} {:x 138, :y 10.667708282475669} {:x 139, :y 10.667708282475669} {:x 140, :y 10.830512453249845} {:x 141, :y 10.830512453249845} {:x 142, :y 11.371015785759864} {:x 143, :y 11.631852818876277} {:x 144, :y 12.177848742696717} {:x 145, :y 12.739701723352868} {:x 146, :y 12.739701723352868} {:x 147, :y 13.107249902248755} {:x 148, :y 13.479614237803691} {:x 149, :y 13.479614237803691} {:x 150, :y 13.856406460551018} {:x 151, :y 13.379088160259652} {:x 152, :y 13.379088160259652} {:x 153, :y 13.379088160259652} {:x 154, :y 13.754999091239519} {:x 155, :y 13.754999091239519} {:x 156, :y 13.553597308463905} {:x 157, :y 13.939153489362258} {:x 158, :y 13.939153489362258} {:x 159, :y 13.939153489362258} {:x 160, :y 13.831124321616086} {:x 161, :y 13.649175799292792} {:x 162, :y 13.479614237803691} {:x 163, :y 13.442470011125186} {:x 164, :y 13.2664991614216} {:x 165, :y 13.103434664239753} {:x 166, :y 13.2664991614216} {:x 167, :y 13.442470011125186} {:x 168, :y 13.442470011125186} {:x 169, :y 13.63084736911099} {:x 170, :y 13.649175799292792} {:x 171, :y 13.557285864065861} {:x 172, :y 13.758633653092156} {:x 173, :y 13.663820841916802} {:x 174, :y 13.663820841916802} {:x 175, :y 13.663820841916802} {:x 176, :y 13.663820841916802} {:x 177, :y 13.479614237803691} {:x 178, :y 13.479614237803691} {:x 179, :y 14.060583202698243} {:x 180, :y 14.060583202698243} {:x 181, :y 14.247806848775006} {:x 182, :y 13.765899897936205} {:x 183, :y 13.638181696985855} {:x 184, :y 13.590437814875575} {:x 185, :y 13.638181696985855} {:x 186, :y 13.236313686219438} {:x 187, :y 13.638181696985855} {:x 188, :y 13.236313686219438} {:x 189, :y 13.19090595827292} {:x 190, :y 13.19090595827292} {:x 191, :y 13.236313686219438} {:x 192, :y 13.30413469565007} {:x 193, :y 13.171939872319491} {:x 194, :y 13.171939872319491} {:x 195, :y 13.171939872319491} {:x 196, :y 13.171939872319491} {:x 197, :y 13.171939872319491} {:x 198, :y 13.171939872319491} {:x 199, :y 13.367871932360812} {:x 200, :y 13.45362404707371} {:x 201, :y 13.341664064126334} {:x 202, :y 13.341664064126334} {:x 203, :y 13.442470011125186} {:x 204, :y 13.049904214207858} {:x 205, :y 12.660963628413123} {:x 206, :y 12.557866060760482} {:x 207, :y 12.477980605851252} {:x 208, :y 12.497999839974394} {:x 209, :y 12.735776379946374} {:x 210, :y 12.735776379946374} {:x 211, :y 12.884098726725126} {:x 212, :y 12.62933094031509} {:x 213, :y 12.62933094031509} {:x 214, :y 12.62933094031509} {:x 215, :y 12.62933094031509} {:x 216, :y 12.62933094031509} {:x 217, :y 12.103718436910205} {:x 218, :y 12.349089035228468} {:x 219, :y 12.103718436910205} {:x 220, :y 12.041594578792296} {:x 221, :y 12.041594578792296} {:x 222, :y 11.861703081766969} {:x 223, :y 11.696153213770756} {:x 224, :y 11.696153213770756} {:x 225, :y 11.696153213770756} {:x 226, :y 11.696153213770756} {:x 227, :y 11.545561917897285} {:x 228, :y 11.41052146047673} {:x 229, :y 11.291589790636214} {:x 230, :y 11.189280584559492} {:x 231, :y 11.189280584559492} {:x 232, :y 11.291589790636214} {:x 233, :y 11.41052146047673} {:x 234, :y 11.291589790636214} {:x 235, :y 11.291589790636214} {:x 236, :y 11.291589790636214} {:x 237, :y 11.189280584559492} {:x 238, :y 11.291589790636214} {:x 239, :y 11.224972160321824} {:x 240, :y 11.224972160321824} {:x 241, :y 11.224972160321824} {:x 242, :y 11.099549540409287} {:x 243, :y 11.099549540409287} {:x 244, :y 10.99090533122727} {:x 245, :y 10.99090533122727} {:x 246, :y 10.89954127475097} {:x 247, :y 10.89954127475097} {:x 248, :y 10.807404868885037} {:x 249, :y 10.922453936730518} {:x 250, :y 11.054410884348384} {:x 251, :y 11.054410884348384} {:x 252, :y 11.054410884348384} {:x 253, :y 11.20267825120404} {:x 254, :y 11.20267825120404} {:x 255, :y 11.20267825120404} {:x 256, :y 11.20267825120404} {:x 257, :y 11.224972160321824} {:x 258, :y 11.224972160321824} {:x 259, :y 11.054410884348384} {:x 260, :y 10.89954127475097} {:x 261, :y 10.89954127475097} {:x 262, :y 11.054410884348384} {:x 263, :y 11.054410884348384} {:x 264, :y 11.054410884348384} {:x 265, :y 11.224972160321824} {:x 266, :y 11.054410884348384} {:x 267, :y 11.224972160321824} {:x 268, :y 11.41052146047673} {:x 269, :y 11.497825881443848} {:x 270, :y 11.497825881443848} {:x 271, :y 11.627553482998906} {:x 272, :y 11.388590782006348} {:x 273, :y 11.313708498984761} {:x 274, :y 11.113055385446435} {:x 275, :y 11.166915420114902} {:x 276, :y 11.166915420114902} {:x 277, :y 11.166915420114902} {:x 278, :y 11.077003204838391} {:x 279, :y 11.313708498984761} {:x 280, :y 11.554220008291344} {:x 281, :y 11.734564329364767} {:x 282, :y 12.12435565298214} {:x 283, :y 12.12435565298214} {:x 284, :y 11.874342087037917} {:x 285, :y 11.874342087037917} {:x 286, :y 12.049896265113654} {:x 287, :y 12.049896265113654} {:x 288, :y 11.653325705565772} {:x 289, :y 11.653325705565772} {:x 290, :y 11.653325705565772} {:x 291, :y 11.588787684654509} {:x 292, :y 11.414902540100814} {:x 293, :y 11.414902540100814} {:x 294, :y 11.414902540100814} {:x 295, :y 11.588787684654509} {:x 296, :y 11.991663771137015} {:x 297, :y 11.781341180018513} {:x 298, :y 11.781341180018513} {:x 299, :y 11.371015785759864} {:x 300, :y 11.371015785759864})} {:name \"714b7663-6990-43ef-8e70-6944439f39cb\", :values ({:x 0, :y 0.0} {:x 1, :y 0.0} {:x 2, :y 0.4472135954999579} {:x 3, :y 0.4472135954999579} {:x 4, :y 0.4472135954999579} {:x 5, :y 0.7071067811865476} {:x 6, :y 0.7071067811865476} {:x 7, :y 0.7071067811865476} {:x 8, :y 0.8366600265340756} {:x 9, :y 0.8366600265340756} {:x 10, :y 1.140175425099138} {:x 11, :y 1.140175425099138} {:x 12, :y 1.140175425099138} {:x 13, :y 0.8944271909999159} {:x 14, :y 0.8944271909999159} {:x 15, :y 0.8944271909999159} {:x 16, :y 0.8944271909999159} {:x 17, :y 0.8944271909999159} {:x 18, :y 1.3038404810405297} {:x 19, :y 1.3038404810405297} {:x 20, :y 1.3038404810405297} {:x 21, :y 1.3038404810405297} {:x 22, :y 1.3038404810405297} {:x 23, :y 1.3038404810405297} {:x 24, :y 1.3038404810405297} {:x 25, :y 1.3038404810405297} {:x 26, :y 1.3038404810405297} {:x 27, :y 1.3038404810405297} {:x 28, :y 0.8944271909999159} {:x 29, :y 1.140175425099138} {:x 30, :y 1.3416407864998738} {:x 31, :y 1.3416407864998738} {:x 32, :y 1.140175425099138} {:x 33, :y 1.140175425099138} {:x 34, :y 1.6733200530681511} {:x 35, :y 1.9235384061671346} {:x 36, :y 2.588435821108957} {:x 37, :y 2.588435821108957} {:x 38, :y 2.588435821108957} {:x 39, :y 2.3021728866442674} {:x 40, :y 2.3021728866442674} {:x 41, :y 2.3021728866442674} {:x 42, :y 2.9664793948382653} {:x 43, :y 2.701851217221259} {:x 44, :y 2.4899799195977463} {:x 45, :y 3.1144823004794873} {:x 46, :y 3.1144823004794873} {:x 47, :y 3.2093613071762426} {:x 48, :y 3.2093613071762426} {:x 49, :y 3.1144823004794873} {:x 50, :y 3.3466401061363023} {:x 51, :y 3.1622776601683795} {:x 52, :y 3.0} {:x 53, :y 3.03315017762062} {:x 54, :y 3.03315017762062} {:x 55, :y 2.9664793948382653} {:x 56, :y 2.9664793948382653} {:x 57, :y 2.9664793948382653} {:x 58, :y 2.9664793948382653} {:x 59, :y 3.0495901363953815} {:x 60, :y 3.0495901363953815} {:x 61, :y 3.0495901363953815} {:x 62, :y 3.1304951684997055} {:x 63, :y 3.0495901363953815} {:x 64, :y 3.0495901363953815} {:x 65, :y 3.0495901363953815} {:x 66, :y 3.0495901363953815} {:x 67, :y 2.9664793948382653} {:x 68, :y 3.4351128074635335} {:x 69, :y 3.4351128074635335} {:x 70, :y 3.4351128074635335} {:x 71, :y 3.4351128074635335} {:x 72, :y 3.4351128074635335} {:x 73, :y 3.5071355833500366} {:x 74, :y 3.5071355833500366} {:x 75, :y 3.5071355833500366} {:x 76, :y 3.420526275297414} {:x 77, :y 3.5355339059327378} {:x 78, :y 3.7013511046643495} {:x 79, :y 3.646916505762094} {:x 80, :y 3.847076812334269} {:x 81, :y 3.847076812334269} {:x 82, :y 3.847076812334269} {:x 83, :y 4.219004621945797} {:x 84, :y 4.722287581247038} {:x 85, :y 4.878524367060187} {:x 86, :y 5.128352561983234} {:x 87, :y 5.128352561983234} {:x 88, :y 4.61519230368573} {:x 89, :y 4.54972526643093} {:x 90, :y 4.54972526643093} {:x 91, :y 4.358898943540674} {:x 92, :y 4.604345773288535} {:x 93, :y 4.898979485566356} {:x 94, :y 4.898979485566356} {:x 95, :y 5.167204273105526} {:x 96, :y 4.868264577855234} {:x 97, :y 4.636809247747852} {:x 98, :y 4.868264577855234} {:x 99, :y 5.167204273105526} {:x 100, :y 5.412947441089743} {:x 101, :y 5.412947441089743} {:x 102, :y 5.412947441089743} {:x 103, :y 5.412947441089743} {:x 104, :y 5.412947441089743} {:x 105, :y 5.683308895353129} {:x 106, :y 5.540758070878027} {:x 107, :y 5.540758070878027} {:x 108, :y 5.540758070878027} {:x 109, :y 5.830951894845301} {:x 110, :y 5.830951894845301} {:x 111, :y 5.830951894845301} {:x 112, :y 5.787918451395113} {:x 113, :y 5.495452665613635} {:x 114, :y 5.366563145999495} {:x 115, :y 5.079370039680118} {:x 116, :y 5.079370039680118} {:x 117, :y 5.079370039680118} {:x 118, :y 5.079370039680118} {:x 119, :y 5.504543577809154} {:x 120, :y 5.504543577809154} {:x 121, :y 5.224940191045253} {:x 122, :y 4.979959839195493} {:x 123, :y 4.979959839195493} {:x 124, :y 4.8166378315169185} {:x 125, :y 4.8166378315169185} {:x 126, :y 5.0990195135927845} {:x 127, :y 5.0990195135927845} {:x 128, :y 5.0990195135927845} {:x 129, :y 5.0990195135927845} {:x 130, :y 5.0990195135927845} {:x 131, :y 5.0990195135927845} {:x 132, :y 4.969909455915671} {:x 133, :y 4.969909455915671} {:x 134, :y 4.8270073544588685} {:x 135, :y 4.505552130427524} {:x 136, :y 4.505552130427524} {:x 137, :y 4.878524367060187} {:x 138, :y 5.319774431308154} {:x 139, :y 5.272570530585627} {:x 140, :y 5.029910535983717} {:x 141, :y 5.504543577809154} {:x 142, :y 5.029910535983717} {:x 143, :y 4.560701700396552} {:x 144, :y 4.560701700396552} {:x 145, :y 4.159326868617084} {:x 146, :y 4.33589667773576} {:x 147, :y 4.54972526643093} {:x 148, :y 4.358898943540674} {:x 149, :y 4.847679857416329} {:x 150, :y 5.06951674225463} {:x 151, :y 5.585696017507576} {:x 152, :y 6.1400325732035} {:x 153, :y 6.1400325732035} {:x 154, :y 6.1400325732035} {:x 155, :y 6.363961030678928} {:x 156, :y 6.363961030678928} {:x 157, :y 6.363961030678928} {:x 158, :y 6.363961030678928} {:x 159, :y 6.1400325732035} {:x 160, :y 6.300793600809346} {:x 161, :y 6.300793600809346} {:x 162, :y 6.06630035524124} {:x 163, :y 6.06630035524124} {:x 164, :y 5.856620185738529} {:x 165, :y 5.805170109479997} {:x 166, :y 5.805170109479997} {:x 167, :y 5.805170109479997} {:x 168, :y 5.449770637375485} {:x 169, :y 5.458937625582473} {:x 170, :y 5.458937625582473} {:x 171, :y 5.683308895353129} {:x 172, :y 5.683308895353129} {:x 173, :y 5.718391382198319} {:x 174, :y 5.718391382198319} {:x 175, :y 5.787918451395113} {:x 176, :y 5.585696017507576} {:x 177, :y 6.016643582596529} {:x 178, :y 6.016643582596529} {:x 179, :y 5.674504383644443} {:x 180, :y 5.549774770204643} {:x 181, :y 5.549774770204643} {:x 182, :y 6.024948132556827} {:x 183, :y 6.465291950097845} {:x 184, :y 6.465291950097845} {:x 185, :y 6.465291950097845} {:x 186, :y 6.465291950097845} {:x 187, :y 6.6558245169174945} {:x 188, :y 6.6558245169174945} {:x 189, :y 6.6558245169174945} {:x 190, :y 6.268971207462991} {:x 191, :y 6.06630035524124} {:x 192, :y 6.06630035524124} {:x 193, :y 6.2289646009589745} {:x 194, :y 6.3796551630946325} {:x 195, :y 6.670832032063167} {:x 196, :y 6.670832032063167} {:x 197, :y 6.670832032063167} {:x 198, :y 6.670832032063167} {:x 199, :y 6.723094525588644} {:x 200, :y 6.442049363362563} {:x 201, :y 6.442049363362563} {:x 202, :y 6.442049363362563} {:x 203, :y 6.442049363362563} {:x 204, :y 6.442049363362563} {:x 205, :y 6.610597552415364} {:x 206, :y 6.610597552415364} {:x 207, :y 6.610597552415364} {:x 208, :y 6.610597552415364} {:x 209, :y 6.610597552415364} {:x 210, :y 6.6932802122726045} {:x 211, :y 6.54217089351845} {:x 212, :y 6.9498201415576215} {:x 213, :y 7.12039324756716} {:x 214, :y 7.3824115301167} {:x 215, :y 7.0} {:x 216, :y 7.293833011524188} {:x 217, :y 7.791020472312982} {:x 218, :y 7.791020472312982} {:x 219, :y 8.258329128825032} {:x 220, :y 8.258329128825032} {:x 221, :y 8.258329128825032} {:x 222, :y 8.258329128825032} {:x 223, :y 8.258329128825032} {:x 224, :y 7.968688725254614} {:x 225, :y 7.694153624668538} {:x 226, :y 7.694153624668538} {:x 227, :y 7.694153624668538} {:x 228, :y 7.694153624668538} {:x 229, :y 7.694153624668538} {:x 230, :y 7.694153624668538} {:x 231, :y 7.694153624668538} {:x 232, :y 7.694153624668538} {:x 233, :y 7.745966692414834} {:x 234, :y 7.745966692414834} {:x 235, :y 8.031189202104505} {:x 236, :y 8.031189202104505} {:x 237, :y 7.791020472312982} {:x 238, :y 7.791020472312982} {:x 239, :y 7.791020472312982} {:x 240, :y 7.395944834840239} {:x 241, :y 6.942621983083913} {:x 242, :y 6.730527468185536} {:x 243, :y 6.418722614352485} {:x 244, :y 6.348228099241551} {:x 245, :y 6.348228099241551} {:x 246, :y 6.348228099241551} {:x 247, :y 6.348228099241551} {:x 248, :y 6.348228099241551} {:x 249, :y 6.348228099241551} {:x 250, :y 6.418722614352485} {:x 251, :y 6.418722614352485} {:x 252, :y 6.418722614352485} {:x 253, :y 6.418722614352485} {:x 254, :y 6.016643582596529} {:x 255, :y 6.016643582596529} {:x 256, :y 6.016643582596529} {:x 257, :y 6.016643582596529} {:x 258, :y 6.016643582596529} {:x 259, :y 6.016643582596529} {:x 260, :y 6.016643582596529} {:x 261, :y 6.016643582596529} {:x 262, :y 6.123724356957945} {:x 263, :y 5.974947698515862} {:x 264, :y 5.974947698515862} {:x 265, :y 5.974947698515862} {:x 266, :y 5.856620185738529} {:x 267, :y 5.856620185738529} {:x 268, :y 6.016643582596529} {:x 269, :y 6.016643582596529} {:x 270, :y 5.856620185738529} {:x 271, :y 5.856620185738529} {:x 272, :y 5.856620185738529} {:x 273, :y 5.856620185738529} {:x 274, :y 5.856620185738529} {:x 275, :y 5.856620185738529} {:x 276, :y 5.974947698515862} {:x 277, :y 5.974947698515862} {:x 278, :y 6.1400325732035} {:x 279, :y 6.284902544988268} {:x 280, :y 6.041522986797286} {:x 281, :y 6.300793600809346} {:x 282, :y 6.300793600809346} {:x 283, :y 6.300793600809346} {:x 284, :y 6.534523701081817} {:x 285, :y 6.442049363362563} {:x 286, :y 6.442049363362563} {:x 287, :y 6.442049363362563} {:x 288, :y 6.442049363362563} {:x 289, :y 6.534523701081817} {:x 290, :y 6.442049363362563} {:x 291, :y 6.442049363362563} {:x 292, :y 6.610597552415364} {:x 293, :y 6.610597552415364} {:x 294, :y 6.348228099241551} {:x 295, :y 6.610597552415364} {:x 296, :y 6.8044103344816} {:x 297, :y 7.127411872482185} {:x 298, :y 7.503332592921628} {:x 299, :y 7.503332592921628} {:x 300, :y 7.503332592921628})}), :marks ({:type \"line\", :from {:data \"60975aaa-915c-4d46-b470-859048bdcb46\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :stroke {:value \"Blue\"}, :strokeWidth {:value 2}, :strokeOpacity {:value 1}}}} {:type \"line\", :from {:data \"3fbff5f7-e3e1-412d-b281-19a1d347c58f\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :stroke {:value \"Red\"}, :strokeWidth {:value 2}, :strokeOpacity {:value 1}}}} {:type \"line\", :from {:data \"714b7663-6990-43ef-8e70-6944439f39cb\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :stroke {:value \"Green\"}, :strokeWidth {:value 2}, :strokeOpacity {:value 1}}}})}}"}
;; <=

;; **
;;; That's all for this worksheet, but you should also read through the source code in the files in the `src` directory. 
;;; 
;;; Note that you can write player functions that do lots of things, including:
;;; 
;;; - Deciding what to play based on the contents of the `:inventory` displayed on the `:skin` of your opponent.
;;; 
;;; - Advertising additional information (maybe promises to play in certain ways?) on your player's `:skin`, and using information on your opponent's `:skin` when deciding what to play.
;;; 
;;; - Deciding what to play based on histories of prior trades (available in the `:history` of your player).
;;; 
;;; - Storing any other information that you think might be useful in your player's `:memory`, and also using that information in deciding what to play.
;;; 
;;; Have fun making intelligent Rock Paper Stuff players!
;; **
