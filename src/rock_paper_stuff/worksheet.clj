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
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Fido&quot;</span>","value":"\"Fido\""},{"type":"html","content":"<span class='clj-long'>87</span>","value":"87"}],"value":"[\"Fido\" 87]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Pythagoras&quot;</span>","value":"\"Pythagoras\""},{"type":"html","content":"<span class='clj-long'>11</span>","value":"11"}],"value":"[\"Pythagoras\" 11]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Nobody&quot;</span>","value":"\"Nobody\""},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"}],"value":"[\"Nobody\" 2]"}],"value":"([\"Fido\" 87] [\"Pythagoras\" 11] [\"Nobody\" 2])"}
;; <=

;; **
;;; The moral is that you have to know who you're playing against in order to beat them. And you might not have any way of knowing, aside from watching them and observing their behavior.
;; **

;; @@

;; @@

;; @@

;; @@

;; @@

;; @@

;; @@

;; @@

;; **
;;; Adding an extra argument of `:print` causes every trade to be printed as it happens, along with inventories before each trade:
;; **

;; @@
(map summary (:players (play-game [(player "Randy" random-pf)
                                   (player "Sandy" random-pf)]
                                  :print)))
;; @@
;; ->
;;;      Randy inventory: {:fire 10, :paper 10, :scissors 10, :water 10, :rock 10}
;;;      Sandy inventory: {:fire 10, :paper 10, :scissors 10, :water 10, :rock 10}
;;; Randy plays :scissors  -  Sandy plays :rock
;;;      Randy inventory: {:fire 10, :paper 10, :scissors 9, :water 10, :rock 11}
;;;      Sandy inventory: {:fire 11, :paper 10, :scissors 10, :water 10, :rock 9}
;;; Sandy plays :rock  -  Randy plays :rock
;;;      Randy inventory: {:fire 10, :paper 10, :scissors 9, :water 10, :rock 10}
;;;      Sandy inventory: {:fire 11, :paper 10, :scissors 10, :water 10, :rock 10}
;;; Sandy plays :water  -  Randy plays :paper
;;;      Randy inventory: {:fire 10, :paper 10, :scissors 9, :water 10, :rock 10}
;;;      Sandy inventory: {:fire 11, :paper 10, :scissors 10, :water 9, :rock 10}
;;; Sandy plays :water  -  Randy plays :scissors
;;;      Randy inventory: {:fire 10, :paper 10, :scissors 8, :water 11, :rock 10}
;;;      Sandy inventory: {:fire 11, :paper 10, :scissors 10, :water 8, :rock 11}
;;; Randy plays :scissors  -  Sandy plays :water
;;;      Randy inventory: {:fire 10, :paper 10, :scissors 7, :water 12, :rock 10}
;;;      Sandy inventory: {:fire 11, :paper 10, :scissors 10, :water 7, :rock 12}
;;; Randy plays :water  -  Sandy plays :paper
;;;      Randy inventory: {:fire 10, :paper 10, :scissors 7, :water 11, :rock 10}
;;;      Sandy inventory: {:fire 11, :paper 10, :scissors 10, :water 7, :rock 12}
;;; Randy plays :water  -  Sandy plays :water
;;;      Randy inventory: {:fire 10, :paper 10, :scissors 7, :water 12, :rock 10}
;;;      Sandy inventory: {:fire 11, :paper 10, :scissors 10, :water 6, :rock 12}
;;; Randy plays :fire  -  Sandy plays :water
;;;      Randy inventory: {:fire 9, :paper 10, :scissors 7, :water 12, :rock 10}
;;;      Sandy inventory: {:fire 11, :paper 10, :scissors 10, :water 6, :rock 12}
;;; Randy plays :scissors  -  Sandy plays :rock
;;;      Randy inventory: {:fire 9, :paper 10, :scissors 6, :water 12, :rock 11}
;;;      Sandy inventory: {:fire 12, :paper 10, :scissors 10, :water 6, :rock 11}
;;; Randy plays :paper  -  Sandy plays :rock
;;;      Randy inventory: {:fire 9, :paper 9, :scissors 6, :water 12, :rock 12}
;;;      Sandy inventory: {:fire 12, :paper 11, :scissors 10, :water 6, :rock 10}
;;; Sandy plays :paper  -  Randy plays :fire
;;;      Randy inventory: {:fire 10, :paper 9, :scissors 6, :water 12, :rock 12}
;;;      Sandy inventory: {:fire 12, :paper 10, :scissors 10, :water 6, :rock 10}
;;; Randy plays :paper  -  Sandy plays :paper
;;;      Randy inventory: {:fire 10, :paper 8, :scissors 6, :water 12, :rock 12}
;;;      Sandy inventory: {:fire 12, :paper 11, :scissors 10, :water 6, :rock 10}
;;; Sandy plays :water  -  Randy plays :rock
;;;      Randy inventory: {:fire 10, :paper 8, :scissors 6, :water 12, :rock 11}
;;;      Sandy inventory: {:fire 12, :paper 11, :scissors 10, :water 7, :rock 10}
;;; Sandy plays :scissors  -  Randy plays :fire
;;;      Randy inventory: {:fire 10, :paper 8, :scissors 6, :water 12, :rock 11}
;;;      Sandy inventory: {:fire 12, :paper 11, :scissors 11, :water 7, :rock 10}
;;; Sandy plays :fire  -  Randy plays :fire
;;;      Randy inventory: {:fire 9, :paper 8, :scissors 6, :water 12, :rock 11}
;;;      Sandy inventory: {:fire 13, :paper 11, :scissors 11, :water 7, :rock 10}
;;; Randy plays :scissors  -  Sandy plays :water
;;;      Randy inventory: {:fire 9, :paper 8, :scissors 5, :water 13, :rock 11}
;;;      Sandy inventory: {:fire 13, :paper 11, :scissors 11, :water 6, :rock 11}
;;; Sandy plays :rock  -  Randy plays :rock
;;;      Randy inventory: {:fire 9, :paper 8, :scissors 5, :water 13, :rock 10}
;;;      Sandy inventory: {:fire 13, :paper 11, :scissors 11, :water 6, :rock 12}
;;; Sandy plays :paper  -  Randy plays :water
;;;      Randy inventory: {:fire 9, :paper 8, :scissors 5, :water 12, :rock 10}
;;;      Sandy inventory: {:fire 13, :paper 11, :scissors 11, :water 6, :rock 12}
;;; Sandy plays :fire  -  Randy plays :scissors
;;;      Randy inventory: {:fire 9, :paper 8, :scissors 6, :water 12, :rock 10}
;;;      Sandy inventory: {:fire 13, :paper 11, :scissors 11, :water 6, :rock 12}
;;; Randy plays :scissors  -  Sandy plays :water
;;;      Randy inventory: {:fire 9, :paper 8, :scissors 5, :water 13, :rock 10}
;;;      Sandy inventory: {:fire 13, :paper 11, :scissors 11, :water 5, :rock 13}
;;; Sandy plays :paper  -  Randy plays :paper
;;;      Randy inventory: {:fire 9, :paper 9, :scissors 5, :water 13, :rock 10}
;;;      Sandy inventory: {:fire 13, :paper 10, :scissors 11, :water 5, :rock 13}
;;; Sandy plays :paper  -  Randy plays :scissors
;;;      Randy inventory: {:fire 9, :paper 9, :scissors 5, :water 13, :rock 10}
;;;      Sandy inventory: {:fire 13, :paper 11, :scissors 11, :water 5, :rock 13}
;;; Sandy plays :scissors  -  Randy plays :scissors
;;;      Randy inventory: {:fire 9, :paper 9, :scissors 4, :water 13, :rock 10}
;;;      Sandy inventory: {:fire 13, :paper 11, :scissors 12, :water 5, :rock 13}
;;; Sandy plays :rock  -  Randy plays :fire
;;;      Randy inventory: {:fire 8, :paper 9, :scissors 5, :water 13, :rock 10}
;;;      Sandy inventory: {:fire 13, :paper 11, :scissors 12, :water 5, :rock 13}
;;; Sandy plays :fire  -  Randy plays :scissors
;;;      Randy inventory: {:fire 8, :paper 9, :scissors 6, :water 13, :rock 10}
;;;      Sandy inventory: {:fire 13, :paper 11, :scissors 12, :water 5, :rock 13}
;;; Randy plays :rock  -  Sandy plays :scissors
;;;      Randy inventory: {:fire 9, :paper 9, :scissors 6, :water 13, :rock 9}
;;;      Sandy inventory: {:fire 13, :paper 11, :scissors 11, :water 5, :rock 14}
;;; Randy plays :rock  -  Sandy plays :scissors
;;;      Randy inventory: {:fire 10, :paper 9, :scissors 6, :water 13, :rock 8}
;;;      Sandy inventory: {:fire 13, :paper 11, :scissors 10, :water 5, :rock 15}
;;; Sandy plays :paper  -  Randy plays :water
;;;      Randy inventory: {:fire 10, :paper 9, :scissors 6, :water 12, :rock 8}
;;;      Sandy inventory: {:fire 13, :paper 11, :scissors 10, :water 5, :rock 15}
;;; Sandy plays :rock  -  Randy plays :rock
;;;      Randy inventory: {:fire 10, :paper 9, :scissors 6, :water 12, :rock 9}
;;;      Sandy inventory: {:fire 13, :paper 11, :scissors 10, :water 5, :rock 14}
;;; Randy plays :paper  -  Sandy plays :scissors
;;;      Randy inventory: {:fire 10, :paper 10, :scissors 6, :water 12, :rock 9}
;;;      Sandy inventory: {:fire 13, :paper 11, :scissors 10, :water 5, :rock 14}
;;; Sandy plays :scissors  -  Randy plays :fire
;;;      Randy inventory: {:fire 10, :paper 10, :scissors 6, :water 12, :rock 9}
;;;      Sandy inventory: {:fire 13, :paper 11, :scissors 11, :water 5, :rock 14}
;;; Sandy plays :scissors  -  Randy plays :rock
;;;      Randy inventory: {:fire 11, :paper 10, :scissors 6, :water 12, :rock 8}
;;;      Sandy inventory: {:fire 13, :paper 11, :scissors 10, :water 5, :rock 15}
;;; Sandy plays :paper  -  Randy plays :scissors
;;;      Randy inventory: {:fire 11, :paper 10, :scissors 6, :water 12, :rock 8}
;;;      Sandy inventory: {:fire 13, :paper 12, :scissors 10, :water 5, :rock 15}
;;; Sandy plays :fire  -  Randy plays :paper
;;;      Randy inventory: {:fire 11, :paper 9, :scissors 6, :water 12, :rock 8}
;;;      Sandy inventory: {:fire 14, :paper 12, :scissors 10, :water 5, :rock 15}
;;; Sandy plays :rock  -  Randy plays :water
;;;      Randy inventory: {:fire 11, :paper 9, :scissors 6, :water 13, :rock 8}
;;;      Sandy inventory: {:fire 14, :paper 12, :scissors 10, :water 5, :rock 14}
;;; Randy plays :rock  -  Sandy plays :rock
;;;      Randy inventory: {:fire 11, :paper 9, :scissors 6, :water 13, :rock 7}
;;;      Sandy inventory: {:fire 14, :paper 12, :scissors 10, :water 5, :rock 15}
;;; Randy plays :scissors  -  Sandy plays :fire
;;;      Randy inventory: {:fire 11, :paper 9, :scissors 7, :water 13, :rock 7}
;;;      Sandy inventory: {:fire 14, :paper 12, :scissors 10, :water 5, :rock 15}
;;; Sandy plays :rock  -  Randy plays :rock
;;;      Randy inventory: {:fire 11, :paper 9, :scissors 7, :water 13, :rock 6}
;;;      Sandy inventory: {:fire 14, :paper 12, :scissors 10, :water 5, :rock 16}
;;; Randy plays :water  -  Sandy plays :rock
;;;      Randy inventory: {:fire 11, :paper 9, :scissors 7, :water 14, :rock 6}
;;;      Sandy inventory: {:fire 14, :paper 12, :scissors 10, :water 5, :rock 15}
;;; Randy plays :water  -  Sandy plays :paper
;;;      Randy inventory: {:fire 11, :paper 9, :scissors 7, :water 13, :rock 6}
;;;      Sandy inventory: {:fire 14, :paper 12, :scissors 10, :water 5, :rock 15}
;;; Randy plays :paper  -  Sandy plays :paper
;;;      Randy inventory: {:fire 11, :paper 10, :scissors 7, :water 13, :rock 6}
;;;      Sandy inventory: {:fire 14, :paper 11, :scissors 10, :water 5, :rock 15}
;;; Randy plays :scissors  -  Sandy plays :water
;;;      Randy inventory: {:fire 11, :paper 10, :scissors 6, :water 14, :rock 6}
;;;      Sandy inventory: {:fire 14, :paper 11, :scissors 10, :water 4, :rock 16}
;;; Randy plays :scissors  -  Sandy plays :rock
;;;      Randy inventory: {:fire 11, :paper 10, :scissors 5, :water 14, :rock 7}
;;;      Sandy inventory: {:fire 15, :paper 11, :scissors 10, :water 4, :rock 15}
;;; Randy plays :scissors  -  Sandy plays :fire
;;;      Randy inventory: {:fire 11, :paper 10, :scissors 6, :water 14, :rock 7}
;;;      Sandy inventory: {:fire 15, :paper 11, :scissors 10, :water 4, :rock 15}
;;; Sandy plays :scissors  -  Randy plays :scissors
;;;      Randy inventory: {:fire 11, :paper 10, :scissors 7, :water 14, :rock 7}
;;;      Sandy inventory: {:fire 15, :paper 11, :scissors 9, :water 4, :rock 15}
;;; Randy plays :scissors  -  Sandy plays :rock
;;;      Randy inventory: {:fire 11, :paper 10, :scissors 6, :water 14, :rock 8}
;;;      Sandy inventory: {:fire 16, :paper 11, :scissors 9, :water 4, :rock 14}
;;; Sandy plays :water  -  Randy plays :paper
;;;      Randy inventory: {:fire 11, :paper 10, :scissors 6, :water 14, :rock 8}
;;;      Sandy inventory: {:fire 16, :paper 11, :scissors 9, :water 3, :rock 14}
;;; Sandy plays :water  -  Randy plays :scissors
;;;      Randy inventory: {:fire 11, :paper 10, :scissors 5, :water 15, :rock 8}
;;;      Sandy inventory: {:fire 16, :paper 11, :scissors 9, :water 2, :rock 15}
;;; Randy plays :scissors  -  Sandy plays :scissors
;;;      Randy inventory: {:fire 11, :paper 10, :scissors 6, :water 15, :rock 8}
;;;      Sandy inventory: {:fire 16, :paper 11, :scissors 8, :water 2, :rock 15}
;;; Sandy plays :fire  -  Randy plays :paper
;;;      Randy inventory: {:fire 11, :paper 9, :scissors 6, :water 15, :rock 8}
;;;      Sandy inventory: {:fire 17, :paper 11, :scissors 8, :water 2, :rock 15}
;;; Randy plays :water  -  Sandy plays :water
;;;      Randy inventory: {:fire 11, :paper 9, :scissors 6, :water 14, :rock 8}
;;;      Sandy inventory: {:fire 17, :paper 11, :scissors 8, :water 3, :rock 15}
;;; Randy plays :fire  -  Sandy plays :paper
;;;      Randy inventory: {:fire 12, :paper 9, :scissors 6, :water 14, :rock 8}
;;;      Sandy inventory: {:fire 17, :paper 10, :scissors 8, :water 3, :rock 15}
;;; Sandy plays :scissors  -  Randy plays :rock
;;;      Randy inventory: {:fire 13, :paper 9, :scissors 6, :water 14, :rock 7}
;;;      Sandy inventory: {:fire 17, :paper 10, :scissors 7, :water 3, :rock 16}
;;; Sandy plays :paper  -  Randy plays :scissors
;;;      Randy inventory: {:fire 13, :paper 9, :scissors 6, :water 14, :rock 7}
;;;      Sandy inventory: {:fire 17, :paper 11, :scissors 7, :water 3, :rock 16}
;;; Sandy plays :water  -  Randy plays :paper
;;;      Randy inventory: {:fire 13, :paper 9, :scissors 6, :water 14, :rock 7}
;;;      Sandy inventory: {:fire 17, :paper 11, :scissors 7, :water 2, :rock 16}
;;; Sandy plays :water  -  Randy plays :fire
;;;      Randy inventory: {:fire 12, :paper 9, :scissors 6, :water 14, :rock 7}
;;;      Sandy inventory: {:fire 17, :paper 11, :scissors 7, :water 2, :rock 16}
;;; Sandy plays :paper  -  Randy plays :scissors
;;;      Randy inventory: {:fire 12, :paper 9, :scissors 6, :water 14, :rock 7}
;;;      Sandy inventory: {:fire 17, :paper 12, :scissors 7, :water 2, :rock 16}
;;; Randy plays :scissors  -  Sandy plays :water
;;;      Randy inventory: {:fire 12, :paper 9, :scissors 5, :water 15, :rock 7}
;;;      Sandy inventory: {:fire 17, :paper 12, :scissors 7, :water 1, :rock 17}
;;; Sandy plays :rock  -  Randy plays :scissors
;;;      Randy inventory: {:fire 12, :paper 9, :scissors 4, :water 15, :rock 8}
;;;      Sandy inventory: {:fire 18, :paper 12, :scissors 7, :water 1, :rock 16}
;;; Randy plays :rock  -  Sandy plays :rock
;;;      Randy inventory: {:fire 12, :paper 9, :scissors 4, :water 15, :rock 9}
;;;      Sandy inventory: {:fire 18, :paper 12, :scissors 7, :water 1, :rock 15}
;;; Sandy plays :fire  -  Randy plays :paper
;;;      Randy inventory: {:fire 12, :paper 8, :scissors 4, :water 15, :rock 9}
;;;      Sandy inventory: {:fire 19, :paper 12, :scissors 7, :water 1, :rock 15}
;;; Sandy plays :fire  -  Randy plays :scissors
;;;      Randy inventory: {:fire 12, :paper 8, :scissors 5, :water 15, :rock 9}
;;;      Sandy inventory: {:fire 19, :paper 12, :scissors 7, :water 1, :rock 15}
;;; Sandy plays :water  -  Randy plays :scissors
;;;      Randy inventory: {:fire 12, :paper 8, :scissors 4, :water 16, :rock 9}
;;;      Sandy inventory: {:fire 19, :paper 12, :scissors 7, :water 0, :rock 16}
;;; Randy plays :water  -  Sandy plays :paper
;;;      Randy inventory: {:fire 12, :paper 8, :scissors 4, :water 15, :rock 9}
;;;      Sandy inventory: {:fire 19, :paper 12, :scissors 7, :water 0, :rock 16}
;;; Sandy plays :fire  -  Randy plays :paper
;;;      Randy inventory: {:fire 12, :paper 7, :scissors 4, :water 15, :rock 9}
;;;      Sandy inventory: {:fire 20, :paper 12, :scissors 7, :water 0, :rock 16}
;;; Randy plays :rock  -  Sandy plays :scissors
;;;      Randy inventory: {:fire 13, :paper 7, :scissors 4, :water 15, :rock 8}
;;;      Sandy inventory: {:fire 20, :paper 12, :scissors 6, :water 0, :rock 17}
;;; Randy plays :rock  -  Sandy plays :rock
;;;      Randy inventory: {:fire 13, :paper 7, :scissors 4, :water 15, :rock 7}
;;;      Sandy inventory: {:fire 20, :paper 12, :scissors 6, :water 0, :rock 18}
;;; Randy plays :water  -  Sandy plays :scissors
;;;      Randy inventory: {:fire 13, :paper 7, :scissors 4, :water 14, :rock 8}
;;;      Sandy inventory: {:fire 20, :paper 12, :scissors 5, :water 1, :rock 18}
;;; Sandy plays :scissors  -  Randy plays :scissors
;;;      Randy inventory: {:fire 13, :paper 7, :scissors 5, :water 14, :rock 8}
;;;      Sandy inventory: {:fire 20, :paper 12, :scissors 4, :water 1, :rock 18}
;;; Sandy plays :scissors  -  Randy plays :paper
;;;      Randy inventory: {:fire 13, :paper 8, :scissors 5, :water 14, :rock 8}
;;;      Sandy inventory: {:fire 20, :paper 12, :scissors 4, :water 1, :rock 18}
;;; Sandy plays :scissors  -  Randy plays :rock
;;;      Randy inventory: {:fire 14, :paper 8, :scissors 5, :water 14, :rock 7}
;;;      Sandy inventory: {:fire 20, :paper 12, :scissors 3, :water 1, :rock 19}
;;; Randy plays :fire  -  Sandy plays :scissors
;;;      Randy inventory: {:fire 14, :paper 8, :scissors 5, :water 14, :rock 7}
;;;      Sandy inventory: {:fire 20, :paper 12, :scissors 4, :water 1, :rock 19}
;;; Sandy plays :scissors  -  Randy plays :rock
;;;      Randy inventory: {:fire 15, :paper 8, :scissors 5, :water 14, :rock 6}
;;;      Sandy inventory: {:fire 20, :paper 12, :scissors 3, :water 1, :rock 20}
;;; Sandy plays :scissors  -  Randy plays :paper
;;;      Randy inventory: {:fire 15, :paper 9, :scissors 5, :water 14, :rock 6}
;;;      Sandy inventory: {:fire 20, :paper 12, :scissors 3, :water 1, :rock 20}
;;; Sandy plays :fire  -  Randy plays :fire
;;;      Randy inventory: {:fire 14, :paper 9, :scissors 5, :water 14, :rock 6}
;;;      Sandy inventory: {:fire 21, :paper 12, :scissors 3, :water 1, :rock 20}
;;; Sandy plays :rock  -  Randy plays :water
;;;      Randy inventory: {:fire 14, :paper 9, :scissors 5, :water 15, :rock 6}
;;;      Sandy inventory: {:fire 21, :paper 12, :scissors 3, :water 1, :rock 19}
;;; Sandy plays :water  -  Randy plays :water
;;;      Randy inventory: {:fire 14, :paper 9, :scissors 5, :water 16, :rock 6}
;;;      Sandy inventory: {:fire 21, :paper 12, :scissors 3, :water 0, :rock 19}
;;; Randy plays :paper  -  Sandy plays :paper
;;;      Randy inventory: {:fire 14, :paper 10, :scissors 5, :water 16, :rock 6}
;;;      Sandy inventory: {:fire 21, :paper 11, :scissors 3, :water 0, :rock 19}
;;; Sandy plays :scissors  -  Randy plays :scissors
;;;      Randy inventory: {:fire 14, :paper 10, :scissors 4, :water 16, :rock 6}
;;;      Sandy inventory: {:fire 21, :paper 11, :scissors 4, :water 0, :rock 19}
;;; Randy plays :scissors  -  Sandy plays :fire
;;;      Randy inventory: {:fire 14, :paper 10, :scissors 5, :water 16, :rock 6}
;;;      Sandy inventory: {:fire 21, :paper 11, :scissors 4, :water 0, :rock 19}
;;; Sandy plays :scissors  -  Randy plays :paper
;;;      Randy inventory: {:fire 14, :paper 11, :scissors 5, :water 16, :rock 6}
;;;      Sandy inventory: {:fire 21, :paper 11, :scissors 4, :water 0, :rock 19}
;;; Randy plays :rock  -  Sandy plays :rock
;;;      Randy inventory: {:fire 14, :paper 11, :scissors 5, :water 16, :rock 5}
;;;      Sandy inventory: {:fire 21, :paper 11, :scissors 4, :water 0, :rock 20}
;;; Sandy plays :paper  -  Randy plays :scissors
;;;      Randy inventory: {:fire 14, :paper 11, :scissors 5, :water 16, :rock 5}
;;;      Sandy inventory: {:fire 21, :paper 12, :scissors 4, :water 0, :rock 20}
;;; Sandy plays :rock  -  Randy plays :fire
;;;      Randy inventory: {:fire 13, :paper 11, :scissors 6, :water 16, :rock 5}
;;;      Sandy inventory: {:fire 21, :paper 12, :scissors 4, :water 0, :rock 20}
;;; Randy plays :rock  -  Sandy plays :scissors
;;;      Randy inventory: {:fire 14, :paper 11, :scissors 6, :water 16, :rock 4}
;;;      Sandy inventory: {:fire 21, :paper 12, :scissors 3, :water 0, :rock 21}
;;; Sandy plays :scissors  -  Randy plays :paper
;;;      Randy inventory: {:fire 14, :paper 12, :scissors 6, :water 16, :rock 4}
;;;      Sandy inventory: {:fire 21, :paper 12, :scissors 3, :water 0, :rock 21}
;;; Randy plays :water  -  Sandy plays :paper
;;;      Randy inventory: {:fire 14, :paper 12, :scissors 6, :water 15, :rock 4}
;;;      Sandy inventory: {:fire 21, :paper 12, :scissors 3, :water 0, :rock 21}
;;; Sandy plays :paper  -  Randy plays :fire
;;;      Randy inventory: {:fire 15, :paper 12, :scissors 6, :water 15, :rock 4}
;;;      Sandy inventory: {:fire 21, :paper 11, :scissors 3, :water 0, :rock 21}
;;; Sandy plays :rock  -  Randy plays :water
;;;      Randy inventory: {:fire 15, :paper 12, :scissors 6, :water 16, :rock 4}
;;;      Sandy inventory: {:fire 21, :paper 11, :scissors 3, :water 0, :rock 20}
;;; Sandy plays :rock  -  Randy plays :fire
;;;      Randy inventory: {:fire 14, :paper 12, :scissors 7, :water 16, :rock 4}
;;;      Sandy inventory: {:fire 21, :paper 11, :scissors 3, :water 0, :rock 20}
;;; Randy plays :rock  -  Sandy plays :fire
;;;      Randy inventory: {:fire 14, :paper 12, :scissors 7, :water 16, :rock 4}
;;;      Sandy inventory: {:fire 20, :paper 11, :scissors 4, :water 0, :rock 20}
;;; Randy plays :scissors  -  Sandy plays :rock
;;;      Randy inventory: {:fire 14, :paper 12, :scissors 6, :water 16, :rock 5}
;;;      Sandy inventory: {:fire 21, :paper 11, :scissors 4, :water 0, :rock 19}
;;; Sandy plays :rock  -  Randy plays :water
;;;      Randy inventory: {:fire 14, :paper 12, :scissors 6, :water 17, :rock 5}
;;;      Sandy inventory: {:fire 21, :paper 11, :scissors 4, :water 0, :rock 18}
;;; Randy plays :fire  -  Sandy plays :scissors
;;;      Randy inventory: {:fire 14, :paper 12, :scissors 6, :water 17, :rock 5}
;;;      Sandy inventory: {:fire 21, :paper 11, :scissors 5, :water 0, :rock 18}
;;; Randy plays :paper  -  Sandy plays :rock
;;;      Randy inventory: {:fire 14, :paper 11, :scissors 6, :water 17, :rock 6}
;;;      Sandy inventory: {:fire 21, :paper 12, :scissors 5, :water 0, :rock 17}
;;; Sandy plays :paper  -  Randy plays :water
;;;      Randy inventory: {:fire 14, :paper 11, :scissors 6, :water 16, :rock 6}
;;;      Sandy inventory: {:fire 21, :paper 12, :scissors 5, :water 0, :rock 17}
;;; Sandy plays :scissors  -  Randy plays :paper
;;;      Randy inventory: {:fire 14, :paper 12, :scissors 6, :water 16, :rock 6}
;;;      Sandy inventory: {:fire 21, :paper 12, :scissors 5, :water 0, :rock 17}
;;; Sandy plays :rock  -  Randy plays :water
;;;      Randy inventory: {:fire 14, :paper 12, :scissors 6, :water 17, :rock 6}
;;;      Sandy inventory: {:fire 21, :paper 12, :scissors 5, :water 0, :rock 16}
;;; Randy plays :rock  -  Sandy plays :scissors
;;;      Randy inventory: {:fire 15, :paper 12, :scissors 6, :water 17, :rock 5}
;;;      Sandy inventory: {:fire 21, :paper 12, :scissors 4, :water 0, :rock 17}
;;; Sandy plays :fire  -  Randy plays :fire
;;;      Randy inventory: {:fire 14, :paper 12, :scissors 6, :water 17, :rock 5}
;;;      Sandy inventory: {:fire 22, :paper 12, :scissors 4, :water 0, :rock 17}
;;; 
;; <-
;; =>
;;; {"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:name</span>","value":":name"},{"type":"html","content":"<span class='clj-string'>&quot;Randy&quot;</span>","value":"\"Randy\""}],"value":"[:name \"Randy\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:deviance</span>","value":":deviance"},{"type":"html","content":"<span class='clj-double'>5.167204273105526</span>","value":"5.167204273105526"}],"value":"[:deviance 5.167204273105526]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:inventory</span>","value":":inventory"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:fire</span>","value":":fire"},{"type":"html","content":"<span class='clj-long'>14</span>","value":"14"}],"value":"[:fire 14]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:paper</span>","value":":paper"},{"type":"html","content":"<span class='clj-long'>12</span>","value":"12"}],"value":"[:paper 12]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:scissors</span>","value":":scissors"},{"type":"html","content":"<span class='clj-long'>6</span>","value":"6"}],"value":"[:scissors 6]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:water</span>","value":":water"},{"type":"html","content":"<span class='clj-long'>17</span>","value":"17"}],"value":"[:water 17]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:rock</span>","value":":rock"},{"type":"html","content":"<span class='clj-long'>5</span>","value":"5"}],"value":"[:rock 5]"}],"value":"{:fire 14, :paper 12, :scissors 6, :water 17, :rock 5}"}],"value":"[:inventory {:fire 14, :paper 12, :scissors 6, :water 17, :rock 5}]"}],"value":"{:name \"Randy\", :deviance 5.167204273105526, :inventory {:fire 14, :paper 12, :scissors 6, :water 17, :rock 5}}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:name</span>","value":":name"},{"type":"html","content":"<span class='clj-string'>&quot;Sandy&quot;</span>","value":"\"Sandy\""}],"value":"[:name \"Sandy\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:deviance</span>","value":":deviance"},{"type":"html","content":"<span class='clj-double'>9.055385138137417</span>","value":"9.055385138137417"}],"value":"[:deviance 9.055385138137417]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:inventory</span>","value":":inventory"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:fire</span>","value":":fire"},{"type":"html","content":"<span class='clj-long'>22</span>","value":"22"}],"value":"[:fire 22]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:paper</span>","value":":paper"},{"type":"html","content":"<span class='clj-long'>12</span>","value":"12"}],"value":"[:paper 12]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:scissors</span>","value":":scissors"},{"type":"html","content":"<span class='clj-long'>4</span>","value":"4"}],"value":"[:scissors 4]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:water</span>","value":":water"},{"type":"html","content":"<span class='clj-long'>0</span>","value":"0"}],"value":"[:water 0]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:rock</span>","value":":rock"},{"type":"html","content":"<span class='clj-long'>17</span>","value":"17"}],"value":"[:rock 17]"}],"value":"{:fire 22, :paper 12, :scissors 4, :water 0, :rock 17}"}],"value":"[:inventory {:fire 22, :paper 12, :scissors 4, :water 0, :rock 17}]"}],"value":"{:name \"Sandy\", :deviance 9.055385138137417, :inventory {:fire 22, :paper 12, :scissors 4, :water 0, :rock 17}}"}],"value":"({:name \"Randy\", :deviance 5.167204273105526, :inventory {:fire 14, :paper 12, :scissors 6, :water 17, :rock 5}} {:name \"Sandy\", :deviance 9.055385138137417, :inventory {:fire 22, :paper 12, :scissors 4, :water 0, :rock 17}})"}
;; <=

;; **
;;; In addition to playing single games, we can play tournaments of many games among the same players. The `tournament` function returns a sequence of pairs, each of which contains a player name and the number of games won by that player in the tournament. The sequence is sorted, with the winningest player listed first. Here's a tournament of 100 games among nine players (which will take a little while to run, since there will be 3600 trades per game, or 360000 trades total):
;; **

;; **
;;; For games that have no single winner, the win is credited to "Nobody":
;; **

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
;;; {"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:fire</span>","value":":fire"},{"type":"html","content":"<span class='clj-long'>283</span>","value":"283"}],"value":"[:fire 283]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:paper</span>","value":":paper"},{"type":"html","content":"<span class='clj-long'>209</span>","value":"209"}],"value":"[:paper 209]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:scissors</span>","value":":scissors"},{"type":"html","content":"<span class='clj-long'>324</span>","value":"324"}],"value":"[:scissors 324]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:water</span>","value":":water"},{"type":"html","content":"<span class='clj-long'>156</span>","value":"156"}],"value":"[:water 156]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:rock</span>","value":":rock"},{"type":"html","content":"<span class='clj-long'>238</span>","value":"238"}],"value":"[:rock 238]"}],"value":"{:fire 283, :paper 209, :scissors 324, :water 156, :rock 238}"}
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
               (player "Phyllis" foil-foiler-pf)]]
  (doseq [p1 players]
    (doseq [p2 players]
      (println (:name p1) 
               "vs"  
               (:name p2)
               (tournament 100 [p1 p2])))))
;; @@
;; ->
;;; Rocky vs Rocky ([Nobody 58] [Rocky 42])
;;; Rocky vs Pappy ([Rocky 53] [Pappy 47])
;;; Rocky vs Suzy ([Suzy 70] [Rocky 29] [Nobody 1])
;;; Rocky vs Fido ([Rocky 74] [Fido 26])
;;; Rocky vs Wally ([Wally 67] [Rocky 33])
;;; Rocky vs Randy ([Randy 58] [Rocky 42])
;;; Rocky vs Maxy ([Maxy 93] [Rocky 6] [Nobody 1])
;;; Rocky vs Foxy ([Foxy 82] [Rocky 17] [Nobody 1])
;;; Rocky vs Phyllis ([Rocky 51] [Phyllis 49])
;;; Pappy vs Rocky ([Pappy 59] [Rocky 40] [Nobody 1])
;;; Pappy vs Pappy ([Pappy 61] [Nobody 39])
;;; Pappy vs Suzy ([Suzy 100])
;;; Pappy vs Fido ([Pappy 55] [Fido 45])
;;; Pappy vs Wally ([Pappy 85] [Wally 15])
;;; Pappy vs Randy ([Pappy 76] [Randy 24])
;;; Pappy vs Maxy ([Maxy 91] [Pappy 8] [Nobody 1])
;;; Pappy vs Foxy ([Foxy 68] [Pappy 32])
;;; Pappy vs Phyllis ([Phyllis 100])
;;; Suzy vs Rocky ([Suzy 72] [Rocky 28])
;;; Suzy vs Pappy ([Suzy 100])
;;; Suzy vs Suzy ([Suzy 55] [Nobody 45])
;;; Suzy vs Fido ([Fido 100])
;;; Suzy vs Wally ([Suzy 51] [Wally 49])
;;; Suzy vs Randy ([Suzy 64] [Randy 36])
;;; Suzy vs Maxy ([Maxy 66] [Suzy 34])
;;; Suzy vs Foxy ([Foxy 100])
;;; Suzy vs Phyllis ([Phyllis 100])
;;; Fido vs Rocky ([Rocky 74] [Fido 26])
;;; Fido vs Pappy ([Pappy 60] [Fido 40])
;;; Fido vs Suzy ([Fido 100])
;;; Fido vs Fido ([Fido 65] [Nobody 35])
;;; Fido vs Wally ([Fido 60] [Wally 39] [Nobody 1])
;;; Fido vs Randy ([Randy 57] [Fido 42] [Nobody 1])
;;; Fido vs Maxy ([Fido 99] [Maxy 1])
;;; Fido vs Foxy ([Fido 82] [Foxy 17] [Nobody 1])
;;; Fido vs Phyllis ([Phyllis 51] [Fido 49])
;;; Wally vs Rocky ([Wally 62] [Rocky 38])
;;; Wally vs Pappy ([Pappy 77] [Wally 23])
;;; Wally vs Suzy ([Suzy 60] [Wally 40])
;;; Wally vs Fido ([Fido 52] [Wally 48])
;;; Wally vs Wally ([Wally 57] [Nobody 43])
;;; Wally vs Randy ([Wally 56] [Randy 44])
;;; Wally vs Maxy ([Maxy 90] [Wally 10])
;;; Wally vs Foxy ([Foxy 55] [Wally 45])
;;; Wally vs Phyllis ([Phyllis 65] [Wally 34] [Nobody 1])
;;; Randy vs Rocky ([Randy 56] [Rocky 43] [Nobody 1])
;;; Randy vs Pappy ([Pappy 78] [Randy 22])
;;; Randy vs Suzy ([Suzy 57] [Randy 43])
;;; Randy vs Fido ([Randy 58] [Fido 42])
;;; Randy vs Wally ([Randy 53] [Wally 45] [Nobody 2])
;;; Randy vs Randy ([Randy 100])
;;; Randy vs Maxy ([Maxy 100])
;;; Randy vs Foxy ([Foxy 58] [Randy 41] [Nobody 1])
;;; Randy vs Phyllis ([Phyllis 75] [Randy 25])
;;; Maxy vs Rocky ([Maxy 96] [Rocky 3] [Nobody 1])
;;; Maxy vs Pappy ([Maxy 100])
;;; Maxy vs Suzy ([Maxy 70] [Suzy 30])
;;; Maxy vs Fido ([Fido 98] [Maxy 2])
;;; Maxy vs Wally ([Maxy 96] [Wally 4])
;;; Maxy vs Randy ([Maxy 100])
;;; Maxy vs Maxy ([Maxy 93] [Nobody 7])
;;; Maxy vs Foxy ([Foxy 93] [Maxy 7])
;;; Maxy vs Phyllis ([Phyllis 53] [Maxy 47])
;;; Foxy vs Rocky ([Foxy 77] [Rocky 23])
;;; Foxy vs Pappy ([Foxy 67] [Pappy 33])
;;; Foxy vs Suzy ([Foxy 100])
;;; Foxy vs Fido ([Fido 77] [Foxy 22] [Nobody 1])
;;; Foxy vs Wally ([Foxy 57] [Wally 43])
;;; Foxy vs Randy ([Foxy 69] [Randy 31])
;;; Foxy vs Maxy ([Foxy 92] [Maxy 8])
;;; Foxy vs Foxy ([Foxy 100])
;;; Foxy vs Phyllis ([Phyllis 67] [Foxy 31] [Nobody 2])
;;; Phyllis vs Rocky ([Rocky 51] [Phyllis 49])
;;; Phyllis vs Pappy ([Phyllis 100])
;;; Phyllis vs Suzy ([Phyllis 100])
;;; Phyllis vs Fido ([Fido 51] [Phyllis 49])
;;; Phyllis vs Wally ([Phyllis 67] [Wally 33])
;;; Phyllis vs Randy ([Phyllis 86] [Randy 14])
;;; Phyllis vs Maxy ([Phyllis 50] [Maxy 50])
;;; Phyllis vs Foxy ([Phyllis 77] [Foxy 23])
;;; Phyllis vs Phyllis ([Phyllis 95] [Nobody 5])
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; Let's do that again and produce a bar chart of the results, using the `bar-chart` function provided by Gorilla REPL:
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
;;; {"type":"vega","content":{"width":400,"height":247.2187957763672,"padding":{"top":10,"left":55,"bottom":40,"right":10},"data":[{"name":"ea0f8df5-ce00-43c6-843d-8198b2f33c26","values":[{"x":"Pappy","y":63},{"x":"Wally","y":21},{"x":"Fido","y":10},{"x":"Rocky","y":5},{"x":"Suzy","y":1}]}],"marks":[{"type":"rect","from":{"data":"ea0f8df5-ce00-43c6-843d-8198b2f33c26"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"width":{"scale":"x","band":true,"offset":-1},"y":{"scale":"y","field":"data.y"},"y2":{"scale":"y","value":0}},"update":{"fill":{"value":"steelblue"},"opacity":{"value":1}},"hover":{"fill":{"value":"#FF29D2"}}}}],"scales":[{"name":"x","type":"ordinal","range":"width","domain":{"data":"ea0f8df5-ce00-43c6-843d-8198b2f33c26","field":"data.x"}},{"name":"y","range":"height","nice":true,"domain":{"data":"ea0f8df5-ce00-43c6-843d-8198b2f33c26","field":"data.y"}}],"axes":[{"type":"x","scale":"x"},{"type":"y","scale":"y"}]},"value":"#gorilla_repl.vega.VegaView{:content {:width 400, :height 247.2188, :padding {:top 10, :left 55, :bottom 40, :right 10}, :data [{:name \"ea0f8df5-ce00-43c6-843d-8198b2f33c26\", :values ({:x \"Pappy\", :y 63} {:x \"Wally\", :y 21} {:x \"Fido\", :y 10} {:x \"Rocky\", :y 5} {:x \"Suzy\", :y 1})}], :marks [{:type \"rect\", :from {:data \"ea0f8df5-ce00-43c6-843d-8198b2f33c26\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :width {:scale \"x\", :band true, :offset -1}, :y {:scale \"y\", :field \"data.y\"}, :y2 {:scale \"y\", :value 0}}, :update {:fill {:value \"steelblue\"}, :opacity {:value 1}}, :hover {:fill {:value \"#FF29D2\"}}}}], :scales [{:name \"x\", :type \"ordinal\", :range \"width\", :domain {:data \"ea0f8df5-ce00-43c6-843d-8198b2f33c26\", :field \"data.x\"}} {:name \"y\", :range \"height\", :nice true, :domain {:data \"ea0f8df5-ce00-43c6-843d-8198b2f33c26\", :field \"data.y\"}}], :axes [{:type \"x\", :scale \"x\"} {:type \"y\", :scale \"y\"}]}}"}
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
      wally-devs (map dev (map #(get % "Wally") ghist))
      fido-devs (map dev (map #(get % "Fido") ghist))
      randy-devs (map dev (map #(get % "Randy") ghist))
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
;;; {"type":"vega","content":{"width":400,"height":247.2187957763672,"padding":{"top":10,"left":55,"bottom":40,"right":10},"scales":[{"name":"x","type":"linear","range":"width","zero":false,"domain":{"data":"72fc6814-8d97-4e90-9ae7-d08ee8ed5199","field":"data.x"}},{"name":"y","type":"linear","range":"height","nice":true,"zero":false,"domain":[0.0,11.696153213770756]}],"axes":[{"type":"x","scale":"x"},{"type":"y","scale":"y"}],"data":[{"name":"72fc6814-8d97-4e90-9ae7-d08ee8ed5199","values":[{"x":0,"y":0.0},{"x":1,"y":0.0},{"x":2,"y":0.0},{"x":3,"y":0.0},{"x":4,"y":0.0},{"x":5,"y":0.4472135954999579},{"x":6,"y":0.4472135954999579},{"x":7,"y":0.4472135954999579},{"x":8,"y":0.4472135954999579},{"x":9,"y":0.4472135954999579},{"x":10,"y":0.4472135954999579},{"x":11,"y":0.4472135954999579},{"x":12,"y":1.0954451150103321},{"x":13,"y":1.0954451150103321},{"x":14,"y":1.0954451150103321},{"x":15,"y":0.8944271909999159},{"x":16,"y":0.8944271909999159},{"x":17,"y":0.8944271909999159},{"x":18,"y":0.8944271909999159},{"x":19,"y":0.8944271909999159},{"x":20,"y":0.8944271909999159},{"x":21,"y":0.8944271909999159},{"x":22,"y":0.8944271909999159},{"x":23,"y":0.8944271909999159},{"x":24,"y":0.8944271909999159},{"x":25,"y":0.8944271909999159},{"x":26,"y":1.51657508881031},{"x":27,"y":1.7888543819998317},{"x":28,"y":2.4899799195977463},{"x":29,"y":2.1908902300206643},{"x":30,"y":2.1908902300206643},{"x":31,"y":2.1908902300206643},{"x":32,"y":2.1908902300206643},{"x":33,"y":1.9493588689617927},{"x":34,"y":2.1908902300206643},{"x":35,"y":2.8809720581775866},{"x":36,"y":2.8809720581775866},{"x":37,"y":3.1937438845342623},{"x":38,"y":3.8987177379235853},{"x":39,"y":3.8987177379235853},{"x":40,"y":3.5777087639996634},{"x":41,"y":3.5777087639996634},{"x":42,"y":3.286335345030997},{"x":43,"y":3.286335345030997},{"x":44,"y":3.286335345030997},{"x":45,"y":3.286335345030997},{"x":46,"y":3.286335345030997},{"x":47,"y":3.03315017762062},{"x":48,"y":3.286335345030997},{"x":49,"y":3.03315017762062},{"x":50,"y":3.286335345030997},{"x":51,"y":3.03315017762062},{"x":52,"y":3.03315017762062},{"x":53,"y":3.286335345030997},{"x":54,"y":3.9749213828703582},{"x":55,"y":4.277849927241488},{"x":56,"y":4.277849927241488},{"x":57,"y":4.604345773288535},{"x":58,"y":4.949747468305833},{"x":59,"y":4.949747468305833},{"x":60,"y":4.949747468305833},{"x":61,"y":4.949747468305833},{"x":62,"y":4.949747468305833},{"x":63,"y":5.656854249492381},{"x":64,"y":6.016643582596529},{"x":65,"y":6.723094525588644},{"x":66,"y":6.723094525588644},{"x":67,"y":6.723094525588644},{"x":68,"y":6.363961030678928},{"x":69,"y":7.0710678118654755},{"x":70,"y":6.723094525588644},{"x":71,"y":6.387487769068525},{"x":72,"y":6.06630035524124},{"x":73,"y":6.06630035524124},{"x":74,"y":6.06630035524124},{"x":75,"y":5.761944116355173},{"x":76,"y":5.761944116355173},{"x":77,"y":5.477225575051661},{"x":78,"y":5.215361924162119},{"x":79,"y":4.979959839195493},{"x":80,"y":4.774934554525329},{"x":81,"y":4.979959839195493},{"x":82,"y":4.979959839195493},{"x":83,"y":4.979959839195493},{"x":84,"y":5.215361924162119},{"x":85,"y":5.215361924162119},{"x":86,"y":4.979959839195493},{"x":87,"y":4.774934554525329},{"x":88,"y":4.604345773288535},{"x":89,"y":4.604345773288535},{"x":90,"y":5.215361924162119},{"x":91,"y":5.215361924162119},{"x":92,"y":5.848076606885378},{"x":93,"y":6.06630035524124},{"x":94,"y":6.730527468185536},{"x":95,"y":6.730527468185536},{"x":96,"y":6.496152707564686},{"x":97,"y":6.284902544988268},{"x":98,"y":6.496152707564686},{"x":99,"y":6.496152707564686},{"x":100,"y":7.155417527999327},{"x":101,"y":7.402702209328699},{"x":102,"y":7.155417527999327},{"x":103,"y":7.402702209328699},{"x":104,"y":7.6681158050723255},{"x":105,"y":7.9498427657407165},{"x":106,"y":7.9498427657407165},{"x":107,"y":8.246211251235321},{"x":108,"y":8.246211251235321},{"x":109,"y":8.246211251235321},{"x":110,"y":8.555699854482976},{"x":111,"y":8.87693640846886},{"x":112,"y":8.555699854482976},{"x":113,"y":9.257429448826494},{"x":114,"y":9.959919678390985},{"x":115,"y":9.643650760992955},{"x":116,"y":9.338094023943002},{"x":117,"y":9.643650760992955},{"x":118,"y":9.338094023943002},{"x":119,"y":9.338094023943002},{"x":120,"y":10.03493896344168},{"x":121,"y":10.73312629199899},{"x":122,"y":11.045361017187261},{"x":123,"y":11.045361017187261},{"x":124,"y":11.366617790706258},{"x":125,"y":11.696153213770756},{"x":126,"y":11.696153213770756},{"x":127,"y":11.326958991715296},{"x":128,"y":11.326958991715296},{"x":129,"y":11.326958991715296},{"x":130,"y":11.349008767288886},{"x":131,"y":11.349008767288886},{"x":132,"y":11.366617790706258},{"x":133,"y":11.357816691600547},{"x":134,"y":10.986355173577815},{"x":135,"y":10.986355173577815},{"x":136,"y":10.986355173577815},{"x":137,"y":10.986355173577815},{"x":138,"y":10.986355173577815},{"x":139,"y":10.986355173577815},{"x":140,"y":11.388590782006348},{"x":141,"y":11.388590782006348},{"x":142,"y":11.436782764396638},{"x":143,"y":11.436782764396638},{"x":144,"y":11.388590782006348},{"x":145,"y":10.986355173577815},{"x":146,"y":10.977249200050075},{"x":147,"y":11.379806676741042},{"x":148,"y":11.379806676741042},{"x":149,"y":11.379806676741042},{"x":150,"y":10.977249200050075},{"x":151,"y":10.963576058932597},{"x":152,"y":10.977249200050075},{"x":153,"y":10.963576058932597},{"x":154,"y":10.977249200050075},{"x":155,"y":10.677078252031311},{"x":156,"y":11.009087155618309},{"x":157,"y":11.43241006962224},{"x":158,"y":11.43241006962224},{"x":159,"y":11.43241006962224},{"x":160,"y":11.502173707608488},{"x":161,"y":11.148990985734986},{"x":162,"y":11.126544836560898},{"x":163,"y":11.122050170719426},{"x":164,"y":11.135528725660043},{"x":165,"y":11.135528725660043},{"x":166,"y":11.135528725660043},{"x":167,"y":11.135528725660043},{"x":168,"y":11.135528725660043},{"x":169,"y":10.908712114635714},{"x":170,"y":11.211601134539169},{"x":171,"y":11.211601134539169},{"x":172,"y":11.135528725660043},{"x":173,"y":11.122050170719426},{"x":174,"y":11.122050170719426},{"x":175,"y":11.122050170719426},{"x":176,"y":11.148990985734986},{"x":177,"y":11.193748255164577},{"x":178,"y":11.193748255164577},{"x":179,"y":11.588787684654509},{"x":180,"y":11.588787684654509},{"x":181,"y":11.193748255164577},{"x":182,"y":11.148990985734986},{"x":183,"y":11.166915420114902},{"x":184,"y":10.917875251164945},{"x":185,"y":10.917875251164945},{"x":186,"y":10.917875251164945},{"x":187,"y":11.606032913963324},{"x":188,"y":11.166915420114902},{"x":189,"y":10.756393447619885},{"x":190,"y":10.756393447619885},{"x":191,"y":10.756393447619885},{"x":192,"y":10.756393447619885},{"x":193,"y":10.700467279516348},{"x":194,"y":10.73312629199899},{"x":195,"y":10.73312629199899},{"x":196,"y":10.353743284435827},{"x":197,"y":10.310189135025603},{"x":198,"y":10.310189135025603},{"x":199,"y":10.310189135025603},{"x":200,"y":10.310189135025603},{"x":201,"y":10.329569206893384},{"x":202,"y":10.03493896344168},{"x":203,"y":10.014988766843425},{"x":204,"y":10.014988766843425},{"x":205,"y":10.064790112068906},{"x":206,"y":10.358571330062848},{"x":207,"y":10.08959860450355},{"x":208,"y":10.425929215182693},{"x":209,"y":10.425929215182693},{"x":210,"y":10.51189802081432},{"x":211,"y":10.41633332799983},{"x":212,"y":10.41633332799983},{"x":213,"y":10.41633332799983},{"x":214,"y":10.353743284435827},{"x":215,"y":10.358571330062848},{"x":216,"y":10.064790112068906},{"x":217,"y":10.009995004993758},{"x":218,"y":10.039920318408907},{"x":219,"y":9.762171889492624},{"x":220,"y":9.762171889492624},{"x":221,"y":9.705668446840743},{"x":222,"y":9.669539802906858},{"x":223,"y":9.628083921528727},{"x":224,"y":9.338094023943002},{"x":225,"y":9.038805230781334},{"x":226,"y":8.689073598491383},{"x":227,"y":8.34865258589672},{"x":228,"y":8.689073598491383},{"x":229,"y":9.038805230781334},{"x":230,"y":8.689073598491383},{"x":231,"y":8.689073598491383},{"x":232,"y":9.38083151964686},{"x":233,"y":10.074720839804943},{"x":234,"y":10.074720839804943},{"x":235,"y":10.074720839804943},{"x":236,"y":10.074720839804943},{"x":237,"y":10.074720839804943},{"x":238,"y":9.772410142846033},{"x":239,"y":9.772410142846033},{"x":240,"y":10.074720839804943},{"x":241,"y":10.074720839804943},{"x":242,"y":10.074720839804943},{"x":243,"y":10.074720839804943},{"x":244,"y":10.074720839804943},{"x":245,"y":9.974968671630002},{"x":246,"y":9.934787365615835},{"x":247,"y":9.934787365615835},{"x":248,"y":9.934787365615835},{"x":249,"y":9.964938534682489},{"x":250,"y":9.6591925128346},{"x":251,"y":9.316651759081692},{"x":252,"y":8.983317872590282},{"x":253,"y":8.660254037844387},{"x":254,"y":8.660254037844387},{"x":255,"y":8.34865258589672},{"x":256,"y":8.660254037844387},{"x":257,"y":8.660254037844387},{"x":258,"y":8.34865258589672},{"x":259,"y":8.34865258589672},{"x":260,"y":8.34865258589672},{"x":261,"y":8.660254037844387},{"x":262,"y":8.660254037844387},{"x":263,"y":8.660254037844387},{"x":264,"y":8.983317872590282},{"x":265,"y":8.660254037844387},{"x":266,"y":8.660254037844387},{"x":267,"y":8.660254037844387},{"x":268,"y":8.660254037844387},{"x":269,"y":8.34865258589672},{"x":270,"y":8.049844718999243},{"x":271,"y":7.76530746332687},{"x":272,"y":7.76530746332687},{"x":273,"y":8.44393273303382},{"x":274,"y":8.734987120768983},{"x":275,"y":8.44393273303382},{"x":276,"y":8.734987120768983},{"x":277,"y":8.734987120768983},{"x":278,"y":8.734987120768983},{"x":279,"y":8.44393273303382},{"x":280,"y":8.167006795638168},{"x":281,"y":8.167006795638168},{"x":282,"y":8.843076387773658},{"x":283,"y":8.843076387773658},{"x":284,"y":8.843076387773658},{"x":285,"y":8.843076387773658},{"x":286,"y":9.126883367283709},{"x":287,"y":9.813256340277675},{"x":288,"y":10.114346246792227},{"x":289,"y":10.114346246792227},{"x":290,"y":9.813256340277675},{"x":291,"y":9.813256340277675},{"x":292,"y":10.114346246792227},{"x":293,"y":10.807404868885037},{"x":294,"y":10.502380682492898},{"x":295,"y":10.807404868885037},{"x":296,"y":10.807404868885037},{"x":297,"y":10.502380682492898},{"x":298,"y":10.502380682492898},{"x":299,"y":10.207840124139876}]},{"name":"0a2d6e63-8574-4c48-9112-4616dbd07271","values":[{"x":0,"y":0.4472135954999579},{"x":1,"y":0.4472135954999579},{"x":2,"y":0.8944271909999159},{"x":3,"y":1.3416407864998738},{"x":4,"y":1.3416407864998738},{"x":5,"y":1.3416407864998738},{"x":6,"y":1.3416407864998738},{"x":7,"y":1.3416407864998738},{"x":8,"y":1.7888543819998317},{"x":9,"y":1.7888543819998317},{"x":10,"y":2.23606797749979},{"x":11,"y":2.6832815729997477},{"x":12,"y":2.6832815729997477},{"x":13,"y":3.271085446759225},{"x":14,"y":3.714835124201342},{"x":15,"y":3.714835124201342},{"x":16,"y":4.159326868617084},{"x":17,"y":3.714835124201342},{"x":18,"y":3.714835124201342},{"x":19,"y":4.159326868617084},{"x":20,"y":4.159326868617084},{"x":21,"y":4.604345773288535},{"x":22,"y":5.049752469181039},{"x":23,"y":5.049752469181039},{"x":24,"y":5.049752469181039},{"x":25,"y":5.656854249492381},{"x":26,"y":5.656854249492381},{"x":27,"y":5.656854249492381},{"x":28,"y":5.656854249492381},{"x":29,"y":5.656854249492381},{"x":30,"y":6.284902544988268},{"x":31,"y":6.723094525588644},{"x":32,"y":7.162401831787993},{"x":33,"y":7.162401831787993},{"x":34,"y":7.162401831787993},{"x":35,"y":7.162401831787993},{"x":36,"y":6.985699678629192},{"x":37,"y":6.985699678629192},{"x":38,"y":6.985699678629192},{"x":39,"y":6.906518659932803},{"x":40,"y":6.855654600401044},{"x":41,"y":6.855654600401044},{"x":42,"y":6.760177512462229},{"x":43,"y":6.648308055437865},{"x":44,"y":6.58027355054484},{"x":45,"y":6.5038450166036395},{"x":46,"y":6.58027355054484},{"x":47,"y":6.54217089351845},{"x":48,"y":6.54217089351845},{"x":49,"y":6.54217089351845},{"x":50,"y":6.54217089351845},{"x":51,"y":6.534523701081817},{"x":52,"y":6.534523701081817},{"x":53,"y":6.534523701081817},{"x":54,"y":6.534523701081817},{"x":55,"y":6.534523701081817},{"x":56,"y":6.534523701081817},{"x":57,"y":6.534523701081817},{"x":58,"y":6.534523701081817},{"x":59,"y":6.1400325732035},{"x":60,"y":5.727128425310541},{"x":61,"y":6.1400325732035},{"x":62,"y":6.557438524302},{"x":63,"y":6.519202405202649},{"x":64,"y":6.519202405202649},{"x":65,"y":6.557438524302},{"x":66,"y":6.760177512462229},{"x":67,"y":6.978538528947161},{"x":68,"y":6.978538528947161},{"x":69,"y":7.12039324756716},{"x":70,"y":6.892024376045111},{"x":71,"y":6.978538528947161},{"x":72,"y":6.978538528947161},{"x":73,"y":6.723094525588644},{"x":74,"y":7.19027120489902},{"x":75,"y":7.334848328356899},{"x":76,"y":7.231873892705818},{"x":77,"y":7.395944834840239},{"x":78,"y":7.176350047203662},{"x":79,"y":7.176350047203662},{"x":80,"y":7.362064927722384},{"x":81,"y":7.362064927722384},{"x":82,"y":7.582875444051551},{"x":83,"y":7.791020472312982},{"x":84,"y":7.791020472312982},{"x":85,"y":7.569676347110224},{"x":86,"y":7.569676347110224},{"x":87,"y":7.569676347110224},{"x":88,"y":7.569676347110224},{"x":89,"y":7.791020472312982},{"x":90,"y":7.791020472312982},{"x":91,"y":7.791020472312982},{"x":92,"y":7.791020472312982},{"x":93,"y":7.791020472312982},{"x":94,"y":7.9498427657407165},{"x":95,"y":7.9498427657407165},{"x":96,"y":8.17312669668102},{"x":97,"y":8.17312669668102},{"x":98,"y":8.17312669668102},{"x":99,"y":8.61974477580398},{"x":100,"y":8.820430828479978},{"x":101,"y":8.820430828479978},{"x":102,"y":8.561541917201597},{"x":103,"y":8.561541917201597},{"x":104,"y":8.561541917201597},{"x":105,"y":8.820430828479978},{"x":106,"y":9.071934744033381},{"x":107,"y":9.071934744033381},{"x":108,"y":9.316651759081692},{"x":109,"y":9.60728889958036},{"x":110,"y":9.909591313469996},{"x":111,"y":9.909591313469996},{"x":112,"y":9.60728889958036},{"x":113,"y":9.60728889958036},{"x":114,"y":9.91463564635635},{"x":115,"y":9.91463564635635},{"x":116,"y":9.60728889958036},{"x":117,"y":9.60728889958036},{"x":118,"y":9.311283477587825},{"x":119,"y":9.60728889958036},{"x":120,"y":9.939818911831342},{"x":121,"y":10.310189135025603},{"x":122,"y":10.310189135025603},{"x":123,"y":10.310189135025603},{"x":124,"y":10.310189135025603},{"x":125,"y":10.310189135025603},{"x":126,"y":10.310189135025603},{"x":127,"y":10.644247272588137},{"x":128,"y":10.478549517943788},{"x":129,"y":10.478549517943788},{"x":130,"y":10.478549517943788},{"x":131,"y":10.430723848324238},{"x":132,"y":10.430723848324238},{"x":133,"y":10.430723848324238},{"x":134,"y":10.779610382569492},{"x":135,"y":10.779610382569492},{"x":136,"y":10.747092630102339},{"x":137,"y":10.747092630102339},{"x":138,"y":10.173494974687902},{"x":139,"y":10.52140675005011},{"x":140,"y":10.894952959971878},{"x":141,"y":10.894952959971878},{"x":142,"y":11.058933040759403},{"x":143,"y":11.23832727766904},{"x":144,"y":11.23832727766904},{"x":145,"y":10.830512453249845},{"x":146,"y":10.830512453249845},{"x":147,"y":11.23832727766904},{"x":148,"y":11.23832727766904},{"x":149,"y":11.058933040759403},{"x":150,"y":11.058933040759403},{"x":151,"y":10.894952959971878},{"x":152,"y":10.894952959971878},{"x":153,"y":10.894952959971878},{"x":154,"y":10.876580344942981},{"x":155,"y":10.310189135025603},{"x":156,"y":10.310189135025603},{"x":157,"y":10.310189135025603},{"x":158,"y":10.545141061171254},{"x":159,"y":10.545141061171254},{"x":160,"y":10.545141061171254},{"x":161,"y":10.158740079360236},{"x":162,"y":10.158740079360236},{"x":163,"y":10.158740079360236},{"x":164,"y":10.158740079360236},{"x":165,"y":10.158740079360236},{"x":166,"y":10.16366075781753},{"x":167,"y":10.16366075781753},{"x":168,"y":10.16366075781753},{"x":169,"y":10.16366075781753},{"x":170,"y":10.16366075781753},{"x":171,"y":9.939818911831342},{"x":172,"y":9.939818911831342},{"x":173,"y":9.731392500562292},{"x":174,"y":9.939818911831342},{"x":175,"y":10.188228501560022},{"x":176,"y":9.959919678390985},{"x":177,"y":9.959919678390985},{"x":178,"y":10.232301793829187},{"x":179,"y":10.232301793829187},{"x":180,"y":10.0},{"x":181,"y":10.0},{"x":182,"y":10.0},{"x":183,"y":10.0},{"x":184,"y":10.0},{"x":185,"y":10.232301793829187},{"x":186,"y":10.478549517943788},{"x":187,"y":10.478549517943788},{"x":188,"y":10.478549517943788},{"x":189,"y":10.478549517943788},{"x":190,"y":10.61602562167217},{"x":191,"y":10.478549517943788},{"x":192,"y":10.61602562167217},{"x":193,"y":10.61602562167217},{"x":194,"y":10.478549517943788},{"x":195,"y":10.16366075781753},{"x":196,"y":10.16366075781753},{"x":197,"y":10.16366075781753},{"x":198,"y":10.310189135025603},{"x":199,"y":10.310189135025603},{"x":200,"y":10.16366075781753},{"x":201,"y":10.16366075781753},{"x":202,"y":9.787747442593725},{"x":203,"y":9.787747442593725},{"x":204,"y":9.787747442593725},{"x":205,"y":9.787747442593725},{"x":206,"y":9.787747442593725},{"x":207,"y":9.787747442593725},{"x":208,"y":9.787747442593725},{"x":209,"y":9.47100839404126},{"x":210,"y":9.47100839404126},{"x":211,"y":9.47100839404126},{"x":212,"y":9.17605579756357},{"x":213,"y":9.235799911215054},{"x":214,"y":9.235799911215054},{"x":215,"y":9.121403400793104},{"x":216,"y":8.814760348415605},{"x":217,"y":8.905054744357274},{"x":218,"y":8.905054744357274},{"x":219,"y":8.905054744357274},{"x":220,"y":8.384509526501834},{"x":221,"y":8.384509526501834},{"x":222,"y":8.384509526501834},{"x":223,"y":8.384509526501834},{"x":224,"y":8.384509526501834},{"x":225,"y":8.384509526501834},{"x":226,"y":8.384509526501834},{"x":227,"y":8.384509526501834},{"x":228,"y":8.384509526501834},{"x":229,"y":8.384509526501834},{"x":230,"y":8.384509526501834},{"x":231,"y":8.1117199162693},{"x":232,"y":8.384509526501834},{"x":233,"y":8.384509526501834},{"x":234,"y":8.17312669668102},{"x":235,"y":8.018728078691781},{"x":236,"y":8.018728078691781},{"x":237,"y":8.318653737234168},{"x":238,"y":8.136338242723197},{"x":239,"y":7.7781745930520225},{"x":240,"y":7.968688725254614},{"x":241,"y":8.136338242723197},{"x":242,"y":7.797435475847171},{"x":243,"y":7.987490219086343},{"x":244,"y":7.918333157931662},{"x":245,"y":7.918333157931662},{"x":246,"y":7.874007874011811},{"x":247,"y":7.874007874011811},{"x":248,"y":7.981227975693966},{"x":249,"y":7.981227975693966},{"x":250,"y":7.981227975693966},{"x":251,"y":7.648529270389178},{"x":252,"y":7.854934754662192},{"x":253,"y":7.854934754662192},{"x":254,"y":8.18535277187245},{"x":255,"y":8.408329203831164},{"x":256,"y":8.408329203831164},{"x":257,"y":8.167006795638168},{"x":258,"y":8.167006795638168},{"x":259,"y":7.981227975693966},{"x":260,"y":7.968688725254614},{"x":261,"y":7.968688725254614},{"x":262,"y":7.628892449104261},{"x":263,"y":7.503332592921628},{"x":264,"y":7.503332592921628},{"x":265,"y":7.503332592921628},{"x":266,"y":7.503332592921628},{"x":267,"y":7.700649323271382},{"x":268,"y":7.700649323271382},{"x":269,"y":7.918333157931662},{"x":270,"y":8.154753215150045},{"x":271,"y":8.154753215150045},{"x":272,"y":8.154753215150045},{"x":273,"y":8.48528137423857},{"x":274,"y":8.48528137423857},{"x":275,"y":8.48528137423857},{"x":276,"y":8.48528137423857},{"x":277,"y":7.905694150420948},{"x":278,"y":7.3484692283495345},{"x":279,"y":7.3484692283495345},{"x":280,"y":7.3484692283495345},{"x":281,"y":7.3484692283495345},{"x":282,"y":7.713624310270756},{"x":283,"y":7.416198487095663},{"x":284,"y":7.791020472312982},{"x":285,"y":7.791020472312982},{"x":286,"y":7.791020472312982},{"x":287,"y":7.791020472312982},{"x":288,"y":7.791020472312982},{"x":289,"y":7.76530746332687},{"x":290,"y":7.9498427657407165},{"x":291,"y":7.9498427657407165},{"x":292,"y":8.324662155306964},{"x":293,"y":8.324662155306964},{"x":294,"y":8.526429498916883},{"x":295,"y":8.526429498916883},{"x":296,"y":8.905054744357274},{"x":297,"y":8.905054744357274},{"x":298,"y":8.648699324175862},{"x":299,"y":8.648699324175862}]},{"name":"1bf50af7-d4f4-4470-8fc4-ec617ac7f8d0","values":[{"x":0,"y":0.0},{"x":1,"y":0.4472135954999579},{"x":2,"y":0.4472135954999579},{"x":3,"y":0.4472135954999579},{"x":4,"y":0.8944271909999159},{"x":5,"y":0.8944271909999159},{"x":6,"y":1.3038404810405297},{"x":7,"y":1.51657508881031},{"x":8,"y":1.51657508881031},{"x":9,"y":1.3416407864998738},{"x":10,"y":1.3416407864998738},{"x":11,"y":1.3416407864998738},{"x":12,"y":1.51657508881031},{"x":13,"y":1.51657508881031},{"x":14,"y":1.51657508881031},{"x":15,"y":1.3038404810405297},{"x":16,"y":1.3038404810405297},{"x":17,"y":1.7320508075688772},{"x":18,"y":2.16794833886788},{"x":19,"y":2.16794833886788},{"x":20,"y":2.6076809620810595},{"x":21,"y":2.6076809620810595},{"x":22,"y":2.6076809620810595},{"x":23,"y":3.0495901363953815},{"x":24,"y":3.1304951684997055},{"x":25,"y":3.1304951684997055},{"x":26,"y":3.2093613071762426},{"x":27,"y":3.2093613071762426},{"x":28,"y":3.4351128074635335},{"x":29,"y":3.361547262794322},{"x":30,"y":3.361547262794322},{"x":31,"y":3.361547262794322},{"x":32,"y":3.361547262794322},{"x":33,"y":3.3466401061363023},{"x":34,"y":3.3466401061363023},{"x":35,"y":3.7013511046643495},{"x":36,"y":3.646916505762094},{"x":37,"y":3.646916505762094},{"x":38,"y":4.03732584763727},{"x":39,"y":4.03732584763727},{"x":40,"y":4.03732584763727},{"x":41,"y":4.381780460041329},{"x":42,"y":4.381780460041329},{"x":43,"y":4.08656334834051},{"x":44,"y":4.39317652729776},{"x":45,"y":4.449719092257398},{"x":46,"y":4.39317652729776},{"x":47,"y":4.39317652729776},{"x":48,"y":4.39317652729776},{"x":49,"y":4.381780460041329},{"x":50,"y":4.722287581247038},{"x":51,"y":4.722287581247038},{"x":52,"y":5.06951674225463},{"x":53,"y":5.06951674225463},{"x":54,"y":5.495452665613635},{"x":55,"y":5.495452665613635},{"x":56,"y":5.830951894845301},{"x":57,"y":6.180614856144977},{"x":58,"y":6.180614856144977},{"x":59,"y":6.300793600809346},{"x":60,"y":6.244997998398398},{"x":61,"y":6.244997998398398},{"x":62,"y":6.244997998398398},{"x":63,"y":6.244997998398398},{"x":64,"y":6.244997998398398},{"x":65,"y":6.244997998398398},{"x":66,"y":5.890670590009257},{"x":67,"y":5.932958789676531},{"x":68,"y":5.856620185738529},{"x":69,"y":5.856620185738529},{"x":70,"y":5.856620185738529},{"x":71,"y":5.856620185738529},{"x":72,"y":5.813776741499453},{"x":73,"y":5.9833101206606365},{"x":74,"y":5.9833101206606365},{"x":75,"y":5.9833101206606365},{"x":76,"y":5.856620185738529},{"x":77,"y":5.856620185738529},{"x":78,"y":5.856620185738529},{"x":79,"y":5.813776741499453},{"x":80,"y":5.813776741499453},{"x":81,"y":5.813776741499453},{"x":82,"y":5.805170109479997},{"x":83,"y":5.813776741499453},{"x":84,"y":5.813776741499453},{"x":85,"y":5.813776741499453},{"x":86,"y":5.805170109479997},{"x":87,"y":5.431390245600108},{"x":88,"y":5.06951674225463},{"x":89,"y":4.722287581247038},{"x":90,"y":5.176871642217914},{"x":91,"y":5.504543577809154},{"x":92,"y":5.9833101206606365},{"x":93,"y":5.9833101206606365},{"x":94,"y":5.9833101206606365},{"x":95,"y":6.300793600809346},{"x":96,"y":6.300793600809346},{"x":97,"y":6.284902544988268},{"x":98,"y":6.284902544988268},{"x":99,"y":6.244997998398398},{"x":100,"y":6.244997998398398},{"x":101,"y":6.610597552415364},{"x":102,"y":6.610597552415364},{"x":103,"y":6.610597552415364},{"x":104,"y":6.985699678629192},{"x":105,"y":6.985699678629192},{"x":106,"y":6.6558245169174945},{"x":107,"y":6.6558245169174945},{"x":108,"y":7.021395872616783},{"x":109,"y":6.985699678629192},{"x":110,"y":6.985699678629192},{"x":111,"y":6.985699678629192},{"x":112,"y":6.985699678629192},{"x":113,"y":7.503332592921628},{"x":114,"y":7.503332592921628},{"x":115,"y":7.496665925596525},{"x":116,"y":7.496665925596525},{"x":117,"y":7.496665925596525},{"x":118,"y":7.496665925596525},{"x":119,"y":7.516648189186454},{"x":120,"y":7.516648189186454},{"x":121,"y":7.516648189186454},{"x":122,"y":7.516648189186454},{"x":123,"y":7.791020472312982},{"x":124,"y":7.791020472312982},{"x":125,"y":7.791020472312982},{"x":126,"y":7.402702209328699},{"x":127,"y":7.402702209328699},{"x":128,"y":7.12039324756716},{"x":129,"y":6.730527468185536},{"x":130,"y":6.730527468185536},{"x":131,"y":6.730527468185536},{"x":132,"y":6.457553716385176},{"x":133,"y":6.324555320336759},{"x":134,"y":6.324555320336759},{"x":135,"y":6.519202405202649},{"x":136,"y":6.519202405202649},{"x":137,"y":6.457553716385176},{"x":138,"y":6.906518659932803},{"x":139,"y":6.519202405202649},{"x":140,"y":6.519202405202649},{"x":141,"y":6.572670690061994},{"x":142,"y":6.572670690061994},{"x":143,"y":6.519202405202649},{"x":144,"y":6.519202405202649},{"x":145,"y":6.519202405202649},{"x":146,"y":6.833739825307955},{"x":147,"y":6.833739825307955},{"x":148,"y":6.892024376045111},{"x":149,"y":6.572670690061994},{"x":150,"y":6.496152707564686},{"x":151,"y":6.496152707564686},{"x":152,"y":6.4265076052238514},{"x":153,"y":6.496152707564686},{"x":154,"y":6.496152707564686},{"x":155,"y":6.496152707564686},{"x":156,"y":6.496152707564686},{"x":157,"y":6.496152707564686},{"x":158,"y":6.496152707564686},{"x":159,"y":6.123724356957945},{"x":160,"y":6.058052492344384},{"x":161,"y":6.058052492344384},{"x":162,"y":5.727128425310541},{"x":163,"y":5.727128425310541},{"x":164,"y":5.412947441089743},{"x":165,"y":5.029910535983717},{"x":166,"y":5.118593556827891},{"x":167,"y":5.176871642217914},{"x":168,"y":5.319774431308154},{"x":169,"y":5.029910535983717},{"x":170,"y":5.029910535983717},{"x":171,"y":4.969909455915671},{"x":172,"y":4.969909455915671},{"x":173,"y":4.969909455915671},{"x":174,"y":4.669047011971501},{"x":175,"y":4.669047011971501},{"x":176,"y":4.669047011971501},{"x":177,"y":4.39317652729776},{"x":178,"y":4.39317652729776},{"x":179,"y":4.39317652729776},{"x":180,"y":4.669047011971501},{"x":181,"y":4.669047011971501},{"x":182,"y":4.669047011971501},{"x":183,"y":4.658325879540846},{"x":184,"y":4.43846820423443},{"x":185,"y":4.43846820423443},{"x":186,"y":4.43846820423443},{"x":187,"y":4.969909455915671},{"x":188,"y":5.357238094391549},{"x":189,"y":5.805170109479997},{"x":190,"y":5.830951894845301},{"x":191,"y":5.630275304103699},{"x":192,"y":5.630275304103699},{"x":193,"y":5.630275304103699},{"x":194,"y":5.630275304103699},{"x":195,"y":5.630275304103699},{"x":196,"y":6.1400325732035},{"x":197,"y":6.1400325732035},{"x":198,"y":6.1400325732035},{"x":199,"y":5.856620185738529},{"x":200,"y":5.856620185738529},{"x":201,"y":5.932958789676531},{"x":202,"y":5.932958789676531},{"x":203,"y":5.932958789676531},{"x":204,"y":6.123724356957945},{"x":205,"y":5.890670590009257},{"x":206,"y":5.585696017507576},{"x":207,"y":5.540758070878027},{"x":208,"y":5.540758070878027},{"x":209,"y":5.813776741499453},{"x":210,"y":5.594640292279746},{"x":211,"y":5.594640292279746},{"x":212,"y":5.128352561983234},{"x":213,"y":5.128352561983234},{"x":214,"y":5.128352561983234},{"x":215,"y":5.128352561983234},{"x":216,"y":5.128352561983234},{"x":217,"y":5.128352561983234},{"x":218,"y":5.224940191045253},{"x":219,"y":5.683308895353129},{"x":220,"y":6.2289646009589745},{"x":221,"y":6.2289646009589745},{"x":222,"y":6.148170459575759},{"x":223,"y":6.148170459575759},{"x":224,"y":6.6558245169174945},{"x":225,"y":6.6558245169174945},{"x":226,"y":6.387487769068525},{"x":227,"y":6.1400325732035},{"x":228,"y":6.1400325732035},{"x":229,"y":6.1400325732035},{"x":230,"y":5.916079783099616},{"x":231,"y":5.70087712549569},{"x":232,"y":5.70087712549569},{"x":233,"y":6.324555320336759},{"x":234,"y":6.2048368229954285},{"x":235,"y":6.670832032063167},{"x":236,"y":6.340346993658944},{"x":237,"y":6.340346993658944},{"x":238,"y":6.340346993658944},{"x":239,"y":6.340346993658944},{"x":240,"y":6.340346993658944},{"x":241,"y":6.6558245169174945},{"x":242,"y":6.985699678629192},{"x":243,"y":7.328028384224504},{"x":244,"y":7.035623639735144},{"x":245,"y":7.035623639735144},{"x":246,"y":7.035623639735144},{"x":247,"y":6.96419413859206},{"x":248,"y":6.978538528947161},{"x":249,"y":7.021395872616783},{"x":250,"y":6.8044103344816},{"x":251,"y":6.8044103344816},{"x":252,"y":6.8044103344816},{"x":253,"y":6.465291950097845},{"x":254,"y":6.300793600809346},{"x":255,"y":6.300793600809346},{"x":256,"y":6.300793600809346},{"x":257,"y":5.974947698515862},{"x":258,"y":5.873670062235365},{"x":259,"y":5.656854249492381},{"x":260,"y":5.656854249492381},{"x":261,"y":5.656854249492381},{"x":262,"y":6.058052492344384},{"x":263,"y":5.941380311005179},{"x":264,"y":5.941380311005179},{"x":265,"y":5.932958789676531},{"x":266,"y":6.041522986797286},{"x":267,"y":6.058052492344384},{"x":268,"y":6.2048368229954285},{"x":269,"y":6.2048368229954285},{"x":270,"y":6.2048368229954285},{"x":271,"y":6.180614856144977},{"x":272,"y":5.770615218501403},{"x":273,"y":5.770615218501403},{"x":274,"y":5.770615218501403},{"x":275,"y":5.366563145999495},{"x":276,"y":5.366563145999495},{"x":277,"y":6.024948132556827},{"x":278,"y":6.6932802122726045},{"x":279,"y":6.685805860178712},{"x":280,"y":6.284902544988268},{"x":281,"y":6.164414002968976},{"x":282,"y":6.164414002968976},{"x":283,"y":6.2048368229954285},{"x":284,"y":6.260990336999411},{"x":285,"y":6.4265076052238514},{"x":286,"y":6.833739825307955},{"x":287,"y":7.463243262818116},{"x":288,"y":7.874007874011811},{"x":289,"y":7.874007874011811},{"x":290,"y":7.874007874011811},{"x":291,"y":8.043631020876083},{"x":292,"y":8.043631020876083},{"x":293,"y":8.67179335547152},{"x":294,"y":8.67179335547152},{"x":295,"y":8.67179335547152},{"x":296,"y":8.264381404557755},{"x":297,"y":8.324662155306964},{"x":298,"y":8.473488065725943},{"x":299,"y":8.5848704125339}]}],"marks":[{"type":"line","from":{"data":"72fc6814-8d97-4e90-9ae7-d08ee8ed5199"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"stroke":{"value":"Blue"},"strokeWidth":{"value":2},"strokeOpacity":{"value":1}}}},{"type":"line","from":{"data":"0a2d6e63-8574-4c48-9112-4616dbd07271"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"stroke":{"value":"Red"},"strokeWidth":{"value":2},"strokeOpacity":{"value":1}}}},{"type":"line","from":{"data":"1bf50af7-d4f4-4470-8fc4-ec617ac7f8d0"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"stroke":{"value":"Green"},"strokeWidth":{"value":2},"strokeOpacity":{"value":1}}}}]},"value":"#gorilla_repl.vega.VegaView{:content {:width 400, :height 247.2188, :padding {:top 10, :left 55, :bottom 40, :right 10}, :scales [{:name \"x\", :type \"linear\", :range \"width\", :zero false, :domain {:data \"72fc6814-8d97-4e90-9ae7-d08ee8ed5199\", :field \"data.x\"}} {:name \"y\", :type \"linear\", :range \"height\", :nice true, :zero false, :domain [0.0 11.696153213770756]}], :axes [{:type \"x\", :scale \"x\"} {:type \"y\", :scale \"y\"}], :data ({:name \"72fc6814-8d97-4e90-9ae7-d08ee8ed5199\", :values ({:x 0, :y 0.0} {:x 1, :y 0.0} {:x 2, :y 0.0} {:x 3, :y 0.0} {:x 4, :y 0.0} {:x 5, :y 0.4472135954999579} {:x 6, :y 0.4472135954999579} {:x 7, :y 0.4472135954999579} {:x 8, :y 0.4472135954999579} {:x 9, :y 0.4472135954999579} {:x 10, :y 0.4472135954999579} {:x 11, :y 0.4472135954999579} {:x 12, :y 1.0954451150103321} {:x 13, :y 1.0954451150103321} {:x 14, :y 1.0954451150103321} {:x 15, :y 0.8944271909999159} {:x 16, :y 0.8944271909999159} {:x 17, :y 0.8944271909999159} {:x 18, :y 0.8944271909999159} {:x 19, :y 0.8944271909999159} {:x 20, :y 0.8944271909999159} {:x 21, :y 0.8944271909999159} {:x 22, :y 0.8944271909999159} {:x 23, :y 0.8944271909999159} {:x 24, :y 0.8944271909999159} {:x 25, :y 0.8944271909999159} {:x 26, :y 1.51657508881031} {:x 27, :y 1.7888543819998317} {:x 28, :y 2.4899799195977463} {:x 29, :y 2.1908902300206643} {:x 30, :y 2.1908902300206643} {:x 31, :y 2.1908902300206643} {:x 32, :y 2.1908902300206643} {:x 33, :y 1.9493588689617927} {:x 34, :y 2.1908902300206643} {:x 35, :y 2.8809720581775866} {:x 36, :y 2.8809720581775866} {:x 37, :y 3.1937438845342623} {:x 38, :y 3.8987177379235853} {:x 39, :y 3.8987177379235853} {:x 40, :y 3.5777087639996634} {:x 41, :y 3.5777087639996634} {:x 42, :y 3.286335345030997} {:x 43, :y 3.286335345030997} {:x 44, :y 3.286335345030997} {:x 45, :y 3.286335345030997} {:x 46, :y 3.286335345030997} {:x 47, :y 3.03315017762062} {:x 48, :y 3.286335345030997} {:x 49, :y 3.03315017762062} {:x 50, :y 3.286335345030997} {:x 51, :y 3.03315017762062} {:x 52, :y 3.03315017762062} {:x 53, :y 3.286335345030997} {:x 54, :y 3.9749213828703582} {:x 55, :y 4.277849927241488} {:x 56, :y 4.277849927241488} {:x 57, :y 4.604345773288535} {:x 58, :y 4.949747468305833} {:x 59, :y 4.949747468305833} {:x 60, :y 4.949747468305833} {:x 61, :y 4.949747468305833} {:x 62, :y 4.949747468305833} {:x 63, :y 5.656854249492381} {:x 64, :y 6.016643582596529} {:x 65, :y 6.723094525588644} {:x 66, :y 6.723094525588644} {:x 67, :y 6.723094525588644} {:x 68, :y 6.363961030678928} {:x 69, :y 7.0710678118654755} {:x 70, :y 6.723094525588644} {:x 71, :y 6.387487769068525} {:x 72, :y 6.06630035524124} {:x 73, :y 6.06630035524124} {:x 74, :y 6.06630035524124} {:x 75, :y 5.761944116355173} {:x 76, :y 5.761944116355173} {:x 77, :y 5.477225575051661} {:x 78, :y 5.215361924162119} {:x 79, :y 4.979959839195493} {:x 80, :y 4.774934554525329} {:x 81, :y 4.979959839195493} {:x 82, :y 4.979959839195493} {:x 83, :y 4.979959839195493} {:x 84, :y 5.215361924162119} {:x 85, :y 5.215361924162119} {:x 86, :y 4.979959839195493} {:x 87, :y 4.774934554525329} {:x 88, :y 4.604345773288535} {:x 89, :y 4.604345773288535} {:x 90, :y 5.215361924162119} {:x 91, :y 5.215361924162119} {:x 92, :y 5.848076606885378} {:x 93, :y 6.06630035524124} {:x 94, :y 6.730527468185536} {:x 95, :y 6.730527468185536} {:x 96, :y 6.496152707564686} {:x 97, :y 6.284902544988268} {:x 98, :y 6.496152707564686} {:x 99, :y 6.496152707564686} {:x 100, :y 7.155417527999327} {:x 101, :y 7.402702209328699} {:x 102, :y 7.155417527999327} {:x 103, :y 7.402702209328699} {:x 104, :y 7.6681158050723255} {:x 105, :y 7.9498427657407165} {:x 106, :y 7.9498427657407165} {:x 107, :y 8.246211251235321} {:x 108, :y 8.246211251235321} {:x 109, :y 8.246211251235321} {:x 110, :y 8.555699854482976} {:x 111, :y 8.87693640846886} {:x 112, :y 8.555699854482976} {:x 113, :y 9.257429448826494} {:x 114, :y 9.959919678390985} {:x 115, :y 9.643650760992955} {:x 116, :y 9.338094023943002} {:x 117, :y 9.643650760992955} {:x 118, :y 9.338094023943002} {:x 119, :y 9.338094023943002} {:x 120, :y 10.03493896344168} {:x 121, :y 10.73312629199899} {:x 122, :y 11.045361017187261} {:x 123, :y 11.045361017187261} {:x 124, :y 11.366617790706258} {:x 125, :y 11.696153213770756} {:x 126, :y 11.696153213770756} {:x 127, :y 11.326958991715296} {:x 128, :y 11.326958991715296} {:x 129, :y 11.326958991715296} {:x 130, :y 11.349008767288886} {:x 131, :y 11.349008767288886} {:x 132, :y 11.366617790706258} {:x 133, :y 11.357816691600547} {:x 134, :y 10.986355173577815} {:x 135, :y 10.986355173577815} {:x 136, :y 10.986355173577815} {:x 137, :y 10.986355173577815} {:x 138, :y 10.986355173577815} {:x 139, :y 10.986355173577815} {:x 140, :y 11.388590782006348} {:x 141, :y 11.388590782006348} {:x 142, :y 11.436782764396638} {:x 143, :y 11.436782764396638} {:x 144, :y 11.388590782006348} {:x 145, :y 10.986355173577815} {:x 146, :y 10.977249200050075} {:x 147, :y 11.379806676741042} {:x 148, :y 11.379806676741042} {:x 149, :y 11.379806676741042} {:x 150, :y 10.977249200050075} {:x 151, :y 10.963576058932597} {:x 152, :y 10.977249200050075} {:x 153, :y 10.963576058932597} {:x 154, :y 10.977249200050075} {:x 155, :y 10.677078252031311} {:x 156, :y 11.009087155618309} {:x 157, :y 11.43241006962224} {:x 158, :y 11.43241006962224} {:x 159, :y 11.43241006962224} {:x 160, :y 11.502173707608488} {:x 161, :y 11.148990985734986} {:x 162, :y 11.126544836560898} {:x 163, :y 11.122050170719426} {:x 164, :y 11.135528725660043} {:x 165, :y 11.135528725660043} {:x 166, :y 11.135528725660043} {:x 167, :y 11.135528725660043} {:x 168, :y 11.135528725660043} {:x 169, :y 10.908712114635714} {:x 170, :y 11.211601134539169} {:x 171, :y 11.211601134539169} {:x 172, :y 11.135528725660043} {:x 173, :y 11.122050170719426} {:x 174, :y 11.122050170719426} {:x 175, :y 11.122050170719426} {:x 176, :y 11.148990985734986} {:x 177, :y 11.193748255164577} {:x 178, :y 11.193748255164577} {:x 179, :y 11.588787684654509} {:x 180, :y 11.588787684654509} {:x 181, :y 11.193748255164577} {:x 182, :y 11.148990985734986} {:x 183, :y 11.166915420114902} {:x 184, :y 10.917875251164945} {:x 185, :y 10.917875251164945} {:x 186, :y 10.917875251164945} {:x 187, :y 11.606032913963324} {:x 188, :y 11.166915420114902} {:x 189, :y 10.756393447619885} {:x 190, :y 10.756393447619885} {:x 191, :y 10.756393447619885} {:x 192, :y 10.756393447619885} {:x 193, :y 10.700467279516348} {:x 194, :y 10.73312629199899} {:x 195, :y 10.73312629199899} {:x 196, :y 10.353743284435827} {:x 197, :y 10.310189135025603} {:x 198, :y 10.310189135025603} {:x 199, :y 10.310189135025603} {:x 200, :y 10.310189135025603} {:x 201, :y 10.329569206893384} {:x 202, :y 10.03493896344168} {:x 203, :y 10.014988766843425} {:x 204, :y 10.014988766843425} {:x 205, :y 10.064790112068906} {:x 206, :y 10.358571330062848} {:x 207, :y 10.08959860450355} {:x 208, :y 10.425929215182693} {:x 209, :y 10.425929215182693} {:x 210, :y 10.51189802081432} {:x 211, :y 10.41633332799983} {:x 212, :y 10.41633332799983} {:x 213, :y 10.41633332799983} {:x 214, :y 10.353743284435827} {:x 215, :y 10.358571330062848} {:x 216, :y 10.064790112068906} {:x 217, :y 10.009995004993758} {:x 218, :y 10.039920318408907} {:x 219, :y 9.762171889492624} {:x 220, :y 9.762171889492624} {:x 221, :y 9.705668446840743} {:x 222, :y 9.669539802906858} {:x 223, :y 9.628083921528727} {:x 224, :y 9.338094023943002} {:x 225, :y 9.038805230781334} {:x 226, :y 8.689073598491383} {:x 227, :y 8.34865258589672} {:x 228, :y 8.689073598491383} {:x 229, :y 9.038805230781334} {:x 230, :y 8.689073598491383} {:x 231, :y 8.689073598491383} {:x 232, :y 9.38083151964686} {:x 233, :y 10.074720839804943} {:x 234, :y 10.074720839804943} {:x 235, :y 10.074720839804943} {:x 236, :y 10.074720839804943} {:x 237, :y 10.074720839804943} {:x 238, :y 9.772410142846033} {:x 239, :y 9.772410142846033} {:x 240, :y 10.074720839804943} {:x 241, :y 10.074720839804943} {:x 242, :y 10.074720839804943} {:x 243, :y 10.074720839804943} {:x 244, :y 10.074720839804943} {:x 245, :y 9.974968671630002} {:x 246, :y 9.934787365615835} {:x 247, :y 9.934787365615835} {:x 248, :y 9.934787365615835} {:x 249, :y 9.964938534682489} {:x 250, :y 9.6591925128346} {:x 251, :y 9.316651759081692} {:x 252, :y 8.983317872590282} {:x 253, :y 8.660254037844387} {:x 254, :y 8.660254037844387} {:x 255, :y 8.34865258589672} {:x 256, :y 8.660254037844387} {:x 257, :y 8.660254037844387} {:x 258, :y 8.34865258589672} {:x 259, :y 8.34865258589672} {:x 260, :y 8.34865258589672} {:x 261, :y 8.660254037844387} {:x 262, :y 8.660254037844387} {:x 263, :y 8.660254037844387} {:x 264, :y 8.983317872590282} {:x 265, :y 8.660254037844387} {:x 266, :y 8.660254037844387} {:x 267, :y 8.660254037844387} {:x 268, :y 8.660254037844387} {:x 269, :y 8.34865258589672} {:x 270, :y 8.049844718999243} {:x 271, :y 7.76530746332687} {:x 272, :y 7.76530746332687} {:x 273, :y 8.44393273303382} {:x 274, :y 8.734987120768983} {:x 275, :y 8.44393273303382} {:x 276, :y 8.734987120768983} {:x 277, :y 8.734987120768983} {:x 278, :y 8.734987120768983} {:x 279, :y 8.44393273303382} {:x 280, :y 8.167006795638168} {:x 281, :y 8.167006795638168} {:x 282, :y 8.843076387773658} {:x 283, :y 8.843076387773658} {:x 284, :y 8.843076387773658} {:x 285, :y 8.843076387773658} {:x 286, :y 9.126883367283709} {:x 287, :y 9.813256340277675} {:x 288, :y 10.114346246792227} {:x 289, :y 10.114346246792227} {:x 290, :y 9.813256340277675} {:x 291, :y 9.813256340277675} {:x 292, :y 10.114346246792227} {:x 293, :y 10.807404868885037} {:x 294, :y 10.502380682492898} {:x 295, :y 10.807404868885037} {:x 296, :y 10.807404868885037} {:x 297, :y 10.502380682492898} {:x 298, :y 10.502380682492898} {:x 299, :y 10.207840124139876})} {:name \"0a2d6e63-8574-4c48-9112-4616dbd07271\", :values ({:x 0, :y 0.4472135954999579} {:x 1, :y 0.4472135954999579} {:x 2, :y 0.8944271909999159} {:x 3, :y 1.3416407864998738} {:x 4, :y 1.3416407864998738} {:x 5, :y 1.3416407864998738} {:x 6, :y 1.3416407864998738} {:x 7, :y 1.3416407864998738} {:x 8, :y 1.7888543819998317} {:x 9, :y 1.7888543819998317} {:x 10, :y 2.23606797749979} {:x 11, :y 2.6832815729997477} {:x 12, :y 2.6832815729997477} {:x 13, :y 3.271085446759225} {:x 14, :y 3.714835124201342} {:x 15, :y 3.714835124201342} {:x 16, :y 4.159326868617084} {:x 17, :y 3.714835124201342} {:x 18, :y 3.714835124201342} {:x 19, :y 4.159326868617084} {:x 20, :y 4.159326868617084} {:x 21, :y 4.604345773288535} {:x 22, :y 5.049752469181039} {:x 23, :y 5.049752469181039} {:x 24, :y 5.049752469181039} {:x 25, :y 5.656854249492381} {:x 26, :y 5.656854249492381} {:x 27, :y 5.656854249492381} {:x 28, :y 5.656854249492381} {:x 29, :y 5.656854249492381} {:x 30, :y 6.284902544988268} {:x 31, :y 6.723094525588644} {:x 32, :y 7.162401831787993} {:x 33, :y 7.162401831787993} {:x 34, :y 7.162401831787993} {:x 35, :y 7.162401831787993} {:x 36, :y 6.985699678629192} {:x 37, :y 6.985699678629192} {:x 38, :y 6.985699678629192} {:x 39, :y 6.906518659932803} {:x 40, :y 6.855654600401044} {:x 41, :y 6.855654600401044} {:x 42, :y 6.760177512462229} {:x 43, :y 6.648308055437865} {:x 44, :y 6.58027355054484} {:x 45, :y 6.5038450166036395} {:x 46, :y 6.58027355054484} {:x 47, :y 6.54217089351845} {:x 48, :y 6.54217089351845} {:x 49, :y 6.54217089351845} {:x 50, :y 6.54217089351845} {:x 51, :y 6.534523701081817} {:x 52, :y 6.534523701081817} {:x 53, :y 6.534523701081817} {:x 54, :y 6.534523701081817} {:x 55, :y 6.534523701081817} {:x 56, :y 6.534523701081817} {:x 57, :y 6.534523701081817} {:x 58, :y 6.534523701081817} {:x 59, :y 6.1400325732035} {:x 60, :y 5.727128425310541} {:x 61, :y 6.1400325732035} {:x 62, :y 6.557438524302} {:x 63, :y 6.519202405202649} {:x 64, :y 6.519202405202649} {:x 65, :y 6.557438524302} {:x 66, :y 6.760177512462229} {:x 67, :y 6.978538528947161} {:x 68, :y 6.978538528947161} {:x 69, :y 7.12039324756716} {:x 70, :y 6.892024376045111} {:x 71, :y 6.978538528947161} {:x 72, :y 6.978538528947161} {:x 73, :y 6.723094525588644} {:x 74, :y 7.19027120489902} {:x 75, :y 7.334848328356899} {:x 76, :y 7.231873892705818} {:x 77, :y 7.395944834840239} {:x 78, :y 7.176350047203662} {:x 79, :y 7.176350047203662} {:x 80, :y 7.362064927722384} {:x 81, :y 7.362064927722384} {:x 82, :y 7.582875444051551} {:x 83, :y 7.791020472312982} {:x 84, :y 7.791020472312982} {:x 85, :y 7.569676347110224} {:x 86, :y 7.569676347110224} {:x 87, :y 7.569676347110224} {:x 88, :y 7.569676347110224} {:x 89, :y 7.791020472312982} {:x 90, :y 7.791020472312982} {:x 91, :y 7.791020472312982} {:x 92, :y 7.791020472312982} {:x 93, :y 7.791020472312982} {:x 94, :y 7.9498427657407165} {:x 95, :y 7.9498427657407165} {:x 96, :y 8.17312669668102} {:x 97, :y 8.17312669668102} {:x 98, :y 8.17312669668102} {:x 99, :y 8.61974477580398} {:x 100, :y 8.820430828479978} {:x 101, :y 8.820430828479978} {:x 102, :y 8.561541917201597} {:x 103, :y 8.561541917201597} {:x 104, :y 8.561541917201597} {:x 105, :y 8.820430828479978} {:x 106, :y 9.071934744033381} {:x 107, :y 9.071934744033381} {:x 108, :y 9.316651759081692} {:x 109, :y 9.60728889958036} {:x 110, :y 9.909591313469996} {:x 111, :y 9.909591313469996} {:x 112, :y 9.60728889958036} {:x 113, :y 9.60728889958036} {:x 114, :y 9.91463564635635} {:x 115, :y 9.91463564635635} {:x 116, :y 9.60728889958036} {:x 117, :y 9.60728889958036} {:x 118, :y 9.311283477587825} {:x 119, :y 9.60728889958036} {:x 120, :y 9.939818911831342} {:x 121, :y 10.310189135025603} {:x 122, :y 10.310189135025603} {:x 123, :y 10.310189135025603} {:x 124, :y 10.310189135025603} {:x 125, :y 10.310189135025603} {:x 126, :y 10.310189135025603} {:x 127, :y 10.644247272588137} {:x 128, :y 10.478549517943788} {:x 129, :y 10.478549517943788} {:x 130, :y 10.478549517943788} {:x 131, :y 10.430723848324238} {:x 132, :y 10.430723848324238} {:x 133, :y 10.430723848324238} {:x 134, :y 10.779610382569492} {:x 135, :y 10.779610382569492} {:x 136, :y 10.747092630102339} {:x 137, :y 10.747092630102339} {:x 138, :y 10.173494974687902} {:x 139, :y 10.52140675005011} {:x 140, :y 10.894952959971878} {:x 141, :y 10.894952959971878} {:x 142, :y 11.058933040759403} {:x 143, :y 11.23832727766904} {:x 144, :y 11.23832727766904} {:x 145, :y 10.830512453249845} {:x 146, :y 10.830512453249845} {:x 147, :y 11.23832727766904} {:x 148, :y 11.23832727766904} {:x 149, :y 11.058933040759403} {:x 150, :y 11.058933040759403} {:x 151, :y 10.894952959971878} {:x 152, :y 10.894952959971878} {:x 153, :y 10.894952959971878} {:x 154, :y 10.876580344942981} {:x 155, :y 10.310189135025603} {:x 156, :y 10.310189135025603} {:x 157, :y 10.310189135025603} {:x 158, :y 10.545141061171254} {:x 159, :y 10.545141061171254} {:x 160, :y 10.545141061171254} {:x 161, :y 10.158740079360236} {:x 162, :y 10.158740079360236} {:x 163, :y 10.158740079360236} {:x 164, :y 10.158740079360236} {:x 165, :y 10.158740079360236} {:x 166, :y 10.16366075781753} {:x 167, :y 10.16366075781753} {:x 168, :y 10.16366075781753} {:x 169, :y 10.16366075781753} {:x 170, :y 10.16366075781753} {:x 171, :y 9.939818911831342} {:x 172, :y 9.939818911831342} {:x 173, :y 9.731392500562292} {:x 174, :y 9.939818911831342} {:x 175, :y 10.188228501560022} {:x 176, :y 9.959919678390985} {:x 177, :y 9.959919678390985} {:x 178, :y 10.232301793829187} {:x 179, :y 10.232301793829187} {:x 180, :y 10.0} {:x 181, :y 10.0} {:x 182, :y 10.0} {:x 183, :y 10.0} {:x 184, :y 10.0} {:x 185, :y 10.232301793829187} {:x 186, :y 10.478549517943788} {:x 187, :y 10.478549517943788} {:x 188, :y 10.478549517943788} {:x 189, :y 10.478549517943788} {:x 190, :y 10.61602562167217} {:x 191, :y 10.478549517943788} {:x 192, :y 10.61602562167217} {:x 193, :y 10.61602562167217} {:x 194, :y 10.478549517943788} {:x 195, :y 10.16366075781753} {:x 196, :y 10.16366075781753} {:x 197, :y 10.16366075781753} {:x 198, :y 10.310189135025603} {:x 199, :y 10.310189135025603} {:x 200, :y 10.16366075781753} {:x 201, :y 10.16366075781753} {:x 202, :y 9.787747442593725} {:x 203, :y 9.787747442593725} {:x 204, :y 9.787747442593725} {:x 205, :y 9.787747442593725} {:x 206, :y 9.787747442593725} {:x 207, :y 9.787747442593725} {:x 208, :y 9.787747442593725} {:x 209, :y 9.47100839404126} {:x 210, :y 9.47100839404126} {:x 211, :y 9.47100839404126} {:x 212, :y 9.17605579756357} {:x 213, :y 9.235799911215054} {:x 214, :y 9.235799911215054} {:x 215, :y 9.121403400793104} {:x 216, :y 8.814760348415605} {:x 217, :y 8.905054744357274} {:x 218, :y 8.905054744357274} {:x 219, :y 8.905054744357274} {:x 220, :y 8.384509526501834} {:x 221, :y 8.384509526501834} {:x 222, :y 8.384509526501834} {:x 223, :y 8.384509526501834} {:x 224, :y 8.384509526501834} {:x 225, :y 8.384509526501834} {:x 226, :y 8.384509526501834} {:x 227, :y 8.384509526501834} {:x 228, :y 8.384509526501834} {:x 229, :y 8.384509526501834} {:x 230, :y 8.384509526501834} {:x 231, :y 8.1117199162693} {:x 232, :y 8.384509526501834} {:x 233, :y 8.384509526501834} {:x 234, :y 8.17312669668102} {:x 235, :y 8.018728078691781} {:x 236, :y 8.018728078691781} {:x 237, :y 8.318653737234168} {:x 238, :y 8.136338242723197} {:x 239, :y 7.7781745930520225} {:x 240, :y 7.968688725254614} {:x 241, :y 8.136338242723197} {:x 242, :y 7.797435475847171} {:x 243, :y 7.987490219086343} {:x 244, :y 7.918333157931662} {:x 245, :y 7.918333157931662} {:x 246, :y 7.874007874011811} {:x 247, :y 7.874007874011811} {:x 248, :y 7.981227975693966} {:x 249, :y 7.981227975693966} {:x 250, :y 7.981227975693966} {:x 251, :y 7.648529270389178} {:x 252, :y 7.854934754662192} {:x 253, :y 7.854934754662192} {:x 254, :y 8.18535277187245} {:x 255, :y 8.408329203831164} {:x 256, :y 8.408329203831164} {:x 257, :y 8.167006795638168} {:x 258, :y 8.167006795638168} {:x 259, :y 7.981227975693966} {:x 260, :y 7.968688725254614} {:x 261, :y 7.968688725254614} {:x 262, :y 7.628892449104261} {:x 263, :y 7.503332592921628} {:x 264, :y 7.503332592921628} {:x 265, :y 7.503332592921628} {:x 266, :y 7.503332592921628} {:x 267, :y 7.700649323271382} {:x 268, :y 7.700649323271382} {:x 269, :y 7.918333157931662} {:x 270, :y 8.154753215150045} {:x 271, :y 8.154753215150045} {:x 272, :y 8.154753215150045} {:x 273, :y 8.48528137423857} {:x 274, :y 8.48528137423857} {:x 275, :y 8.48528137423857} {:x 276, :y 8.48528137423857} {:x 277, :y 7.905694150420948} {:x 278, :y 7.3484692283495345} {:x 279, :y 7.3484692283495345} {:x 280, :y 7.3484692283495345} {:x 281, :y 7.3484692283495345} {:x 282, :y 7.713624310270756} {:x 283, :y 7.416198487095663} {:x 284, :y 7.791020472312982} {:x 285, :y 7.791020472312982} {:x 286, :y 7.791020472312982} {:x 287, :y 7.791020472312982} {:x 288, :y 7.791020472312982} {:x 289, :y 7.76530746332687} {:x 290, :y 7.9498427657407165} {:x 291, :y 7.9498427657407165} {:x 292, :y 8.324662155306964} {:x 293, :y 8.324662155306964} {:x 294, :y 8.526429498916883} {:x 295, :y 8.526429498916883} {:x 296, :y 8.905054744357274} {:x 297, :y 8.905054744357274} {:x 298, :y 8.648699324175862} {:x 299, :y 8.648699324175862})} {:name \"1bf50af7-d4f4-4470-8fc4-ec617ac7f8d0\", :values ({:x 0, :y 0.0} {:x 1, :y 0.4472135954999579} {:x 2, :y 0.4472135954999579} {:x 3, :y 0.4472135954999579} {:x 4, :y 0.8944271909999159} {:x 5, :y 0.8944271909999159} {:x 6, :y 1.3038404810405297} {:x 7, :y 1.51657508881031} {:x 8, :y 1.51657508881031} {:x 9, :y 1.3416407864998738} {:x 10, :y 1.3416407864998738} {:x 11, :y 1.3416407864998738} {:x 12, :y 1.51657508881031} {:x 13, :y 1.51657508881031} {:x 14, :y 1.51657508881031} {:x 15, :y 1.3038404810405297} {:x 16, :y 1.3038404810405297} {:x 17, :y 1.7320508075688772} {:x 18, :y 2.16794833886788} {:x 19, :y 2.16794833886788} {:x 20, :y 2.6076809620810595} {:x 21, :y 2.6076809620810595} {:x 22, :y 2.6076809620810595} {:x 23, :y 3.0495901363953815} {:x 24, :y 3.1304951684997055} {:x 25, :y 3.1304951684997055} {:x 26, :y 3.2093613071762426} {:x 27, :y 3.2093613071762426} {:x 28, :y 3.4351128074635335} {:x 29, :y 3.361547262794322} {:x 30, :y 3.361547262794322} {:x 31, :y 3.361547262794322} {:x 32, :y 3.361547262794322} {:x 33, :y 3.3466401061363023} {:x 34, :y 3.3466401061363023} {:x 35, :y 3.7013511046643495} {:x 36, :y 3.646916505762094} {:x 37, :y 3.646916505762094} {:x 38, :y 4.03732584763727} {:x 39, :y 4.03732584763727} {:x 40, :y 4.03732584763727} {:x 41, :y 4.381780460041329} {:x 42, :y 4.381780460041329} {:x 43, :y 4.08656334834051} {:x 44, :y 4.39317652729776} {:x 45, :y 4.449719092257398} {:x 46, :y 4.39317652729776} {:x 47, :y 4.39317652729776} {:x 48, :y 4.39317652729776} {:x 49, :y 4.381780460041329} {:x 50, :y 4.722287581247038} {:x 51, :y 4.722287581247038} {:x 52, :y 5.06951674225463} {:x 53, :y 5.06951674225463} {:x 54, :y 5.495452665613635} {:x 55, :y 5.495452665613635} {:x 56, :y 5.830951894845301} {:x 57, :y 6.180614856144977} {:x 58, :y 6.180614856144977} {:x 59, :y 6.300793600809346} {:x 60, :y 6.244997998398398} {:x 61, :y 6.244997998398398} {:x 62, :y 6.244997998398398} {:x 63, :y 6.244997998398398} {:x 64, :y 6.244997998398398} {:x 65, :y 6.244997998398398} {:x 66, :y 5.890670590009257} {:x 67, :y 5.932958789676531} {:x 68, :y 5.856620185738529} {:x 69, :y 5.856620185738529} {:x 70, :y 5.856620185738529} {:x 71, :y 5.856620185738529} {:x 72, :y 5.813776741499453} {:x 73, :y 5.9833101206606365} {:x 74, :y 5.9833101206606365} {:x 75, :y 5.9833101206606365} {:x 76, :y 5.856620185738529} {:x 77, :y 5.856620185738529} {:x 78, :y 5.856620185738529} {:x 79, :y 5.813776741499453} {:x 80, :y 5.813776741499453} {:x 81, :y 5.813776741499453} {:x 82, :y 5.805170109479997} {:x 83, :y 5.813776741499453} {:x 84, :y 5.813776741499453} {:x 85, :y 5.813776741499453} {:x 86, :y 5.805170109479997} {:x 87, :y 5.431390245600108} {:x 88, :y 5.06951674225463} {:x 89, :y 4.722287581247038} {:x 90, :y 5.176871642217914} {:x 91, :y 5.504543577809154} {:x 92, :y 5.9833101206606365} {:x 93, :y 5.9833101206606365} {:x 94, :y 5.9833101206606365} {:x 95, :y 6.300793600809346} {:x 96, :y 6.300793600809346} {:x 97, :y 6.284902544988268} {:x 98, :y 6.284902544988268} {:x 99, :y 6.244997998398398} {:x 100, :y 6.244997998398398} {:x 101, :y 6.610597552415364} {:x 102, :y 6.610597552415364} {:x 103, :y 6.610597552415364} {:x 104, :y 6.985699678629192} {:x 105, :y 6.985699678629192} {:x 106, :y 6.6558245169174945} {:x 107, :y 6.6558245169174945} {:x 108, :y 7.021395872616783} {:x 109, :y 6.985699678629192} {:x 110, :y 6.985699678629192} {:x 111, :y 6.985699678629192} {:x 112, :y 6.985699678629192} {:x 113, :y 7.503332592921628} {:x 114, :y 7.503332592921628} {:x 115, :y 7.496665925596525} {:x 116, :y 7.496665925596525} {:x 117, :y 7.496665925596525} {:x 118, :y 7.496665925596525} {:x 119, :y 7.516648189186454} {:x 120, :y 7.516648189186454} {:x 121, :y 7.516648189186454} {:x 122, :y 7.516648189186454} {:x 123, :y 7.791020472312982} {:x 124, :y 7.791020472312982} {:x 125, :y 7.791020472312982} {:x 126, :y 7.402702209328699} {:x 127, :y 7.402702209328699} {:x 128, :y 7.12039324756716} {:x 129, :y 6.730527468185536} {:x 130, :y 6.730527468185536} {:x 131, :y 6.730527468185536} {:x 132, :y 6.457553716385176} {:x 133, :y 6.324555320336759} {:x 134, :y 6.324555320336759} {:x 135, :y 6.519202405202649} {:x 136, :y 6.519202405202649} {:x 137, :y 6.457553716385176} {:x 138, :y 6.906518659932803} {:x 139, :y 6.519202405202649} {:x 140, :y 6.519202405202649} {:x 141, :y 6.572670690061994} {:x 142, :y 6.572670690061994} {:x 143, :y 6.519202405202649} {:x 144, :y 6.519202405202649} {:x 145, :y 6.519202405202649} {:x 146, :y 6.833739825307955} {:x 147, :y 6.833739825307955} {:x 148, :y 6.892024376045111} {:x 149, :y 6.572670690061994} {:x 150, :y 6.496152707564686} {:x 151, :y 6.496152707564686} {:x 152, :y 6.4265076052238514} {:x 153, :y 6.496152707564686} {:x 154, :y 6.496152707564686} {:x 155, :y 6.496152707564686} {:x 156, :y 6.496152707564686} {:x 157, :y 6.496152707564686} {:x 158, :y 6.496152707564686} {:x 159, :y 6.123724356957945} {:x 160, :y 6.058052492344384} {:x 161, :y 6.058052492344384} {:x 162, :y 5.727128425310541} {:x 163, :y 5.727128425310541} {:x 164, :y 5.412947441089743} {:x 165, :y 5.029910535983717} {:x 166, :y 5.118593556827891} {:x 167, :y 5.176871642217914} {:x 168, :y 5.319774431308154} {:x 169, :y 5.029910535983717} {:x 170, :y 5.029910535983717} {:x 171, :y 4.969909455915671} {:x 172, :y 4.969909455915671} {:x 173, :y 4.969909455915671} {:x 174, :y 4.669047011971501} {:x 175, :y 4.669047011971501} {:x 176, :y 4.669047011971501} {:x 177, :y 4.39317652729776} {:x 178, :y 4.39317652729776} {:x 179, :y 4.39317652729776} {:x 180, :y 4.669047011971501} {:x 181, :y 4.669047011971501} {:x 182, :y 4.669047011971501} {:x 183, :y 4.658325879540846} {:x 184, :y 4.43846820423443} {:x 185, :y 4.43846820423443} {:x 186, :y 4.43846820423443} {:x 187, :y 4.969909455915671} {:x 188, :y 5.357238094391549} {:x 189, :y 5.805170109479997} {:x 190, :y 5.830951894845301} {:x 191, :y 5.630275304103699} {:x 192, :y 5.630275304103699} {:x 193, :y 5.630275304103699} {:x 194, :y 5.630275304103699} {:x 195, :y 5.630275304103699} {:x 196, :y 6.1400325732035} {:x 197, :y 6.1400325732035} {:x 198, :y 6.1400325732035} {:x 199, :y 5.856620185738529} {:x 200, :y 5.856620185738529} {:x 201, :y 5.932958789676531} {:x 202, :y 5.932958789676531} {:x 203, :y 5.932958789676531} {:x 204, :y 6.123724356957945} {:x 205, :y 5.890670590009257} {:x 206, :y 5.585696017507576} {:x 207, :y 5.540758070878027} {:x 208, :y 5.540758070878027} {:x 209, :y 5.813776741499453} {:x 210, :y 5.594640292279746} {:x 211, :y 5.594640292279746} {:x 212, :y 5.128352561983234} {:x 213, :y 5.128352561983234} {:x 214, :y 5.128352561983234} {:x 215, :y 5.128352561983234} {:x 216, :y 5.128352561983234} {:x 217, :y 5.128352561983234} {:x 218, :y 5.224940191045253} {:x 219, :y 5.683308895353129} {:x 220, :y 6.2289646009589745} {:x 221, :y 6.2289646009589745} {:x 222, :y 6.148170459575759} {:x 223, :y 6.148170459575759} {:x 224, :y 6.6558245169174945} {:x 225, :y 6.6558245169174945} {:x 226, :y 6.387487769068525} {:x 227, :y 6.1400325732035} {:x 228, :y 6.1400325732035} {:x 229, :y 6.1400325732035} {:x 230, :y 5.916079783099616} {:x 231, :y 5.70087712549569} {:x 232, :y 5.70087712549569} {:x 233, :y 6.324555320336759} {:x 234, :y 6.2048368229954285} {:x 235, :y 6.670832032063167} {:x 236, :y 6.340346993658944} {:x 237, :y 6.340346993658944} {:x 238, :y 6.340346993658944} {:x 239, :y 6.340346993658944} {:x 240, :y 6.340346993658944} {:x 241, :y 6.6558245169174945} {:x 242, :y 6.985699678629192} {:x 243, :y 7.328028384224504} {:x 244, :y 7.035623639735144} {:x 245, :y 7.035623639735144} {:x 246, :y 7.035623639735144} {:x 247, :y 6.96419413859206} {:x 248, :y 6.978538528947161} {:x 249, :y 7.021395872616783} {:x 250, :y 6.8044103344816} {:x 251, :y 6.8044103344816} {:x 252, :y 6.8044103344816} {:x 253, :y 6.465291950097845} {:x 254, :y 6.300793600809346} {:x 255, :y 6.300793600809346} {:x 256, :y 6.300793600809346} {:x 257, :y 5.974947698515862} {:x 258, :y 5.873670062235365} {:x 259, :y 5.656854249492381} {:x 260, :y 5.656854249492381} {:x 261, :y 5.656854249492381} {:x 262, :y 6.058052492344384} {:x 263, :y 5.941380311005179} {:x 264, :y 5.941380311005179} {:x 265, :y 5.932958789676531} {:x 266, :y 6.041522986797286} {:x 267, :y 6.058052492344384} {:x 268, :y 6.2048368229954285} {:x 269, :y 6.2048368229954285} {:x 270, :y 6.2048368229954285} {:x 271, :y 6.180614856144977} {:x 272, :y 5.770615218501403} {:x 273, :y 5.770615218501403} {:x 274, :y 5.770615218501403} {:x 275, :y 5.366563145999495} {:x 276, :y 5.366563145999495} {:x 277, :y 6.024948132556827} {:x 278, :y 6.6932802122726045} {:x 279, :y 6.685805860178712} {:x 280, :y 6.284902544988268} {:x 281, :y 6.164414002968976} {:x 282, :y 6.164414002968976} {:x 283, :y 6.2048368229954285} {:x 284, :y 6.260990336999411} {:x 285, :y 6.4265076052238514} {:x 286, :y 6.833739825307955} {:x 287, :y 7.463243262818116} {:x 288, :y 7.874007874011811} {:x 289, :y 7.874007874011811} {:x 290, :y 7.874007874011811} {:x 291, :y 8.043631020876083} {:x 292, :y 8.043631020876083} {:x 293, :y 8.67179335547152} {:x 294, :y 8.67179335547152} {:x 295, :y 8.67179335547152} {:x 296, :y 8.264381404557755} {:x 297, :y 8.324662155306964} {:x 298, :y 8.473488065725943} {:x 299, :y 8.5848704125339})}), :marks ({:type \"line\", :from {:data \"72fc6814-8d97-4e90-9ae7-d08ee8ed5199\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :stroke {:value \"Blue\"}, :strokeWidth {:value 2}, :strokeOpacity {:value 1}}}} {:type \"line\", :from {:data \"0a2d6e63-8574-4c48-9112-4616dbd07271\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :stroke {:value \"Red\"}, :strokeWidth {:value 2}, :strokeOpacity {:value 1}}}} {:type \"line\", :from {:data \"1bf50af7-d4f4-4470-8fc4-ec617ac7f8d0\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :stroke {:value \"Green\"}, :strokeWidth {:value 2}, :strokeOpacity {:value 1}}}})}}"}
;; <=

;; **
;;; That's all for this worksheet, but you should also read through the source code in the files in the `src` directory. 
;;; 
;;; Note that you can write player functions that do fancier things than have been demonstrated here, including:
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
