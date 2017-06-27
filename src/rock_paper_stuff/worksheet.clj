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
;;; {"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:name</span>","value":":name"},{"type":"html","content":"<span class='clj-string'>&quot;Randy&quot;</span>","value":"\"Randy\""}],"value":"[:name \"Randy\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:deviance</span>","value":":deviance"},{"type":"html","content":"<span class='clj-double'>4.979959839195493</span>","value":"4.979959839195493"}],"value":"[:deviance 4.979959839195493]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:inventory</span>","value":":inventory"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:fire</span>","value":":fire"},{"type":"html","content":"<span class='clj-long'>7</span>","value":"7"}],"value":"[:fire 7]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:paper</span>","value":":paper"},{"type":"html","content":"<span class='clj-long'>7</span>","value":"7"}],"value":"[:paper 7]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:scissors</span>","value":":scissors"},{"type":"html","content":"<span class='clj-long'>11</span>","value":"11"}],"value":"[:scissors 11]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:water</span>","value":":water"},{"type":"html","content":"<span class='clj-long'>13</span>","value":"13"}],"value":"[:water 13]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:rock</span>","value":":rock"},{"type":"html","content":"<span class='clj-long'>19</span>","value":"19"}],"value":"[:rock 19]"}],"value":"{:fire 7, :paper 7, :scissors 11, :water 13, :rock 19}"}],"value":"[:inventory {:fire 7, :paper 7, :scissors 11, :water 13, :rock 19}]"}],"value":"{:name \"Randy\", :deviance 4.979959839195493, :inventory {:fire 7, :paper 7, :scissors 11, :water 13, :rock 19}}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:name</span>","value":":name"},{"type":"html","content":"<span class='clj-string'>&quot;Rocky&quot;</span>","value":"\"Rocky\""}],"value":"[:name \"Rocky\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:deviance</span>","value":":deviance"},{"type":"html","content":"<span class='clj-double'>6.6932802122726045</span>","value":"6.6932802122726045"}],"value":"[:deviance 6.6932802122726045]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:inventory</span>","value":":inventory"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:fire</span>","value":":fire"},{"type":"html","content":"<span class='clj-long'>18</span>","value":"18"}],"value":"[:fire 18]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:paper</span>","value":":paper"},{"type":"html","content":"<span class='clj-long'>8</span>","value":"8"}],"value":"[:paper 8]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:scissors</span>","value":":scissors"},{"type":"html","content":"<span class='clj-long'>13</span>","value":"13"}],"value":"[:scissors 13]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:water</span>","value":":water"},{"type":"html","content":"<span class='clj-long'>8</span>","value":"8"}],"value":"[:water 8]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:rock</span>","value":":rock"},{"type":"html","content":"<span class='clj-long'>0</span>","value":"0"}],"value":"[:rock 0]"}],"value":"{:fire 18, :paper 8, :scissors 13, :water 8, :rock 0}"}],"value":"[:inventory {:fire 18, :paper 8, :scissors 13, :water 8, :rock 0}]"}],"value":"{:name \"Rocky\", :deviance 6.6932802122726045, :inventory {:fire 18, :paper 8, :scissors 13, :water 8, :rock 0}}"}],"value":"({:name \"Randy\", :deviance 4.979959839195493, :inventory {:fire 7, :paper 7, :scissors 11, :water 13, :rock 19}} {:name \"Rocky\", :deviance 6.6932802122726045, :inventory {:fire 18, :paper 8, :scissors 13, :water 8, :rock 0}})"}
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
;;;   :deviance 4.0,
;;;   :inventory {:fire 11, :paper 11, :scissors 10, :water 9, :rock 19}}
;;;  {:name &quot;Rocky&quot;,
;;;   :deviance 5.176871642217914,
;;;   :inventory {:fire 5, :paper 11, :scissors 9, :water 13, :rock 0}})
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
;;; ({:name &quot;Rocky&quot;,
;;;   :deviance 8.105553651663778,
;;;   :inventory {:fire 9, :paper 21, :scissors 15, :water 6, :rock 0}}
;;;  {:name &quot;Pappy&quot;,
;;;   :deviance 9.071934744033381,
;;;   :inventory {:fire 5, :paper 0, :scissors 4, :water 17, :rock 21}})
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
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Pappy&quot;</span>","value":"\"Pappy\""},{"type":"html","content":"<span class='clj-long'>56</span>","value":"56"}],"value":"[\"Pappy\" 56]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Rocky&quot;</span>","value":"\"Rocky\""},{"type":"html","content":"<span class='clj-long'>43</span>","value":"43"}],"value":"[\"Rocky\" 43]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Nobody&quot;</span>","value":"\"Nobody\""},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[\"Nobody\" 1]"}],"value":"([\"Pappy\" 56] [\"Rocky\" 43] [\"Nobody\" 1])"}
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
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;rock-paper-stuff.worksheet/water-pf</span>","value":"#'rock-paper-stuff.worksheet/water-pf"}
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
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Pappy&quot;</span>","value":"\"Pappy\""},{"type":"html","content":"<span class='clj-long'>65</span>","value":"65"}],"value":"[\"Pappy\" 65]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Wally&quot;</span>","value":"\"Wally\""},{"type":"html","content":"<span class='clj-long'>26</span>","value":"26"}],"value":"[\"Wally\" 26]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Fido&quot;</span>","value":"\"Fido\""},{"type":"html","content":"<span class='clj-long'>8</span>","value":"8"}],"value":"[\"Fido\" 8]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Rocky&quot;</span>","value":"\"Rocky\""},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[\"Rocky\" 1]"}],"value":"([\"Pappy\" 65] [\"Wally\" 26] [\"Fido\" 8] [\"Rocky\" 1])"}
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
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Randy&quot;</span>","value":"\"Randy\""},{"type":"html","content":"<span class='clj-long'>41</span>","value":"41"}],"value":"[\"Randy\" 41]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Pappy&quot;</span>","value":"\"Pappy\""},{"type":"html","content":"<span class='clj-long'>27</span>","value":"27"}],"value":"[\"Pappy\" 27]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Wally&quot;</span>","value":"\"Wally\""},{"type":"html","content":"<span class='clj-long'>25</span>","value":"25"}],"value":"[\"Wally\" 25]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Rocky&quot;</span>","value":"\"Rocky\""},{"type":"html","content":"<span class='clj-long'>4</span>","value":"4"}],"value":"[\"Rocky\" 4]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Fido&quot;</span>","value":"\"Fido\""},{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"}],"value":"[\"Fido\" 3]"}],"value":"([\"Randy\" 41] [\"Pappy\" 27] [\"Wally\" 25] [\"Rocky\" 4] [\"Fido\" 3])"}
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
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Maxy&quot;</span>","value":"\"Maxy\""},{"type":"html","content":"<span class='clj-long'>64</span>","value":"64"}],"value":"[\"Maxy\" 64]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Pappy&quot;</span>","value":"\"Pappy\""},{"type":"html","content":"<span class='clj-long'>28</span>","value":"28"}],"value":"[\"Pappy\" 28]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Suzy&quot;</span>","value":"\"Suzy\""},{"type":"html","content":"<span class='clj-long'>6</span>","value":"6"}],"value":"[\"Suzy\" 6]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Fido&quot;</span>","value":"\"Fido\""},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"}],"value":"[\"Fido\" 2]"}],"value":"([\"Maxy\" 64] [\"Pappy\" 28] [\"Suzy\" 6] [\"Fido\" 2])"}
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
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Maxy&quot;</span>","value":"\"Maxy\""},{"type":"html","content":"<span class='clj-long'>45</span>","value":"45"}],"value":"[\"Maxy\" 45]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Foxy&quot;</span>","value":"\"Foxy\""},{"type":"html","content":"<span class='clj-long'>33</span>","value":"33"}],"value":"[\"Foxy\" 33]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Pappy&quot;</span>","value":"\"Pappy\""},{"type":"html","content":"<span class='clj-long'>12</span>","value":"12"}],"value":"[\"Pappy\" 12]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Fido&quot;</span>","value":"\"Fido\""},{"type":"html","content":"<span class='clj-long'>5</span>","value":"5"}],"value":"[\"Fido\" 5]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Randy&quot;</span>","value":"\"Randy\""},{"type":"html","content":"<span class='clj-long'>4</span>","value":"4"}],"value":"[\"Randy\" 4]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Wally&quot;</span>","value":"\"Wally\""},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[\"Wally\" 1]"}],"value":"([\"Maxy\" 45] [\"Foxy\" 33] [\"Pappy\" 12] [\"Fido\" 5] [\"Randy\" 4] [\"Wally\" 1])"}
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
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Foxy&quot;</span>","value":"\"Foxy\""},{"type":"html","content":"<span class='clj-long'>96</span>","value":"96"}],"value":"[\"Foxy\" 96]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Maxy&quot;</span>","value":"\"Maxy\""},{"type":"html","content":"<span class='clj-long'>4</span>","value":"4"}],"value":"[\"Maxy\" 4]"}],"value":"([\"Foxy\" 96] [\"Maxy\" 4])"}
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
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Phyllis&quot;</span>","value":"\"Phyllis\""},{"type":"html","content":"<span class='clj-long'>80</span>","value":"80"}],"value":"[\"Phyllis\" 80]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Foxy&quot;</span>","value":"\"Foxy\""},{"type":"html","content":"<span class='clj-long'>20</span>","value":"20"}],"value":"[\"Foxy\" 20]"}],"value":"([\"Phyllis\" 80] [\"Foxy\" 20])"}
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
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Pythagoras&quot;</span>","value":"\"Pythagoras\""},{"type":"html","content":"<span class='clj-long'>79</span>","value":"79"}],"value":"[\"Pythagoras\" 79]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Phyllis&quot;</span>","value":"\"Phyllis\""},{"type":"html","content":"<span class='clj-long'>21</span>","value":"21"}],"value":"[\"Phyllis\" 21]"}],"value":"([\"Pythagoras\" 79] [\"Phyllis\" 21])"}
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
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Fido&quot;</span>","value":"\"Fido\""},{"type":"html","content":"<span class='clj-long'>83</span>","value":"83"}],"value":"[\"Fido\" 83]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Pythagoras&quot;</span>","value":"\"Pythagoras\""},{"type":"html","content":"<span class='clj-long'>17</span>","value":"17"}],"value":"[\"Pythagoras\" 17]"}],"value":"([\"Fido\" 83] [\"Pythagoras\" 17])"}
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
;;; {"type":"vega","content":{"width":400,"height":247.2187957763672,"padding":{"top":10,"left":55,"bottom":40,"right":10},"scales":[{"name":"x","type":"linear","range":"width","zero":false,"domain":{"data":"7eaba6d6-1812-4a80-9991-f82ece767baa","field":"data.x"}},{"name":"y","type":"linear","range":"height","nice":true,"zero":false,"domain":[0.0,12.891857895586655]}],"axes":[{"type":"x","scale":"x"},{"type":"y","scale":"y"}],"data":[{"name":"7eaba6d6-1812-4a80-9991-f82ece767baa","values":[{"x":0,"y":0.0},{"x":1,"y":0.0},{"x":2,"y":0.0},{"x":3,"y":0.4472135954999579},{"x":4,"y":1.0954451150103321},{"x":5,"y":0.7071067811865476},{"x":6,"y":0.7071067811865476},{"x":7,"y":0.4472135954999579},{"x":8,"y":0.4472135954999579},{"x":9,"y":0.7071067811865476},{"x":10,"y":0.7071067811865476},{"x":11,"y":0.7071067811865476},{"x":12,"y":0.4472135954999579},{"x":13,"y":0.4472135954999579},{"x":14,"y":0.5477225575051661},{"x":15,"y":0.8944271909999159},{"x":16,"y":1.51657508881031},{"x":17,"y":1.51657508881031},{"x":18,"y":1.51657508881031},{"x":19,"y":1.51657508881031},{"x":20,"y":1.51657508881031},{"x":21,"y":1.51657508881031},{"x":22,"y":2.1908902300206643},{"x":23,"y":2.1908902300206643},{"x":24,"y":1.9493588689617927},{"x":25,"y":1.7888543819998317},{"x":26,"y":1.7888543819998317},{"x":27,"y":1.7888543819998317},{"x":28,"y":1.7888543819998317},{"x":29,"y":1.7888543819998317},{"x":30,"y":1.7888543819998317},{"x":31,"y":1.7888543819998317},{"x":32,"y":1.7888543819998317},{"x":33,"y":1.9493588689617927},{"x":34,"y":1.9493588689617927},{"x":35,"y":1.9493588689617927},{"x":36,"y":1.9493588689617927},{"x":37,"y":1.9493588689617927},{"x":38,"y":1.9493588689617927},{"x":39,"y":1.9493588689617927},{"x":40,"y":1.9493588689617927},{"x":41,"y":1.9493588689617927},{"x":42,"y":1.9493588689617927},{"x":43,"y":2.1908902300206643},{"x":44,"y":2.1908902300206643},{"x":45,"y":1.9493588689617927},{"x":46,"y":1.9493588689617927},{"x":47,"y":1.7888543819998317},{"x":48,"y":1.7888543819998317},{"x":49,"y":1.7888543819998317},{"x":50,"y":1.7320508075688772},{"x":51,"y":1.7320508075688772},{"x":52,"y":1.7320508075688772},{"x":53,"y":1.7888543819998317},{"x":54,"y":1.7320508075688772},{"x":55,"y":1.7888543819998317},{"x":56,"y":2.16794833886788},{"x":57,"y":2.23606797749979},{"x":58,"y":2.23606797749979},{"x":59,"y":2.16794833886788},{"x":60,"y":2.16794833886788},{"x":61,"y":2.6832815729997477},{"x":62,"y":3.271085446759225},{"x":63,"y":3.271085446759225},{"x":64,"y":3.1304951684997055},{"x":65,"y":3.271085446759225},{"x":66,"y":3.4641016151377544},{"x":67,"y":3.4641016151377544},{"x":68,"y":3.7013511046643495},{"x":69,"y":4.381780460041329},{"x":70,"y":5.06951674225463},{"x":71,"y":4.795831523312719},{"x":72,"y":4.54972526643093},{"x":73,"y":4.33589667773576},{"x":74,"y":4.54972526643093},{"x":75,"y":4.54972526643093},{"x":76,"y":5.215361924162119},{"x":77,"y":5.215361924162119},{"x":78,"y":5.215361924162119},{"x":79,"y":4.979959839195493},{"x":80,"y":5.639148871948674},{"x":81,"y":5.639148871948674},{"x":82,"y":5.890670590009257},{"x":83,"y":5.890670590009257},{"x":84,"y":5.639148871948674},{"x":85,"y":5.639148871948674},{"x":86,"y":5.639148871948674},{"x":87,"y":5.412947441089743},{"x":88,"y":6.06630035524124},{"x":89,"y":6.730527468185536},{"x":90,"y":6.730527468185536},{"x":91,"y":6.730527468185536},{"x":92,"y":6.730527468185536},{"x":93,"y":6.496152707564686},{"x":94,"y":6.496152707564686},{"x":95,"y":6.730527468185536},{"x":96,"y":6.730527468185536},{"x":97,"y":6.496152707564686},{"x":98,"y":6.496152707564686},{"x":99,"y":7.155417527999327},{"x":100,"y":7.402702209328699},{"x":101,"y":7.402702209328699},{"x":102,"y":7.6681158050723255},{"x":103,"y":7.402702209328699},{"x":104,"y":7.6681158050723255},{"x":105,"y":8.35463942968217},{"x":106,"y":8.35463942968217},{"x":107,"y":8.64291617453276},{"x":108,"y":8.94427190999916},{"x":109,"y":9.643650760992955},{"x":110,"y":9.338094023943002},{"x":111,"y":9.044335243676011},{"x":112,"y":9.736529155710468},{"x":113,"y":9.736529155710468},{"x":114,"y":9.736529155710468},{"x":115,"y":9.736529155710468},{"x":116,"y":10.03493896344168},{"x":117,"y":10.03493896344168},{"x":118,"y":10.73312629199899},{"x":119,"y":10.430723848324238},{"x":120,"y":10.73312629199899},{"x":121,"y":11.43241006962224},{"x":122,"y":11.43241006962224},{"x":123,"y":11.43241006962224},{"x":124,"y":11.74734012447073},{"x":125,"y":11.43241006962224},{"x":126,"y":12.13260071048248},{"x":127,"y":12.13260071048248},{"x":128,"y":12.449899597988733},{"x":129,"y":12.477980605851252},{"x":130,"y":12.449899597988733},{"x":131,"y":12.165525060596439},{"x":132,"y":12.477980605851252},{"x":133,"y":12.477980605851252},{"x":134,"y":12.477980605851252},{"x":135,"y":12.477980605851252},{"x":136,"y":12.501999840025595},{"x":137,"y":12.477980605851252},{"x":138,"y":12.477980605851252},{"x":139,"y":12.070625501605125},{"x":140,"y":12.070625501605125},{"x":141,"y":12.062338081814818},{"x":142,"y":12.049896265113654},{"x":143,"y":12.049896265113654},{"x":144,"y":12.033287165193059},{"x":145,"y":12.033287165193059},{"x":146,"y":12.033287165193059},{"x":147,"y":12.033287165193059},{"x":148,"y":12.033287165193059},{"x":149,"y":12.033287165193059},{"x":150,"y":12.054044964243332},{"x":151,"y":12.054044964243332},{"x":152,"y":12.074767078498866},{"x":153,"y":12.054044964243332},{"x":154,"y":12.054044964243332},{"x":155,"y":12.054044964243332},{"x":156,"y":11.717508267545622},{"x":157,"y":11.388590782006348},{"x":158,"y":11.388590782006348},{"x":159,"y":12.091319200153471},{"x":160,"y":12.421755109484328},{"x":161,"y":12.461942063739505},{"x":162,"y":12.461942063739505},{"x":163,"y":12.457929201917949},{"x":164,"y":12.049896265113654},{"x":165,"y":12.457929201917949},{"x":166,"y":12.457929201917949},{"x":167,"y":12.891857895586655},{"x":168,"y":12.83744522870497},{"x":169,"y":12.798437404620925},{"x":170,"y":12.421755109484328},{"x":171,"y":12.437845472588892},{"x":172,"y":12.437845472588892},{"x":173,"y":12.449899597988733},{"x":174,"y":12.449899597988733},{"x":175,"y":12.449899597988733},{"x":176,"y":12.437845472588892},{"x":177,"y":12.049896265113654},{"x":178,"y":12.054044964243332},{"x":179,"y":12.074767078498866},{"x":180,"y":12.074767078498866},{"x":181,"y":11.717508267545622},{"x":182,"y":11.717508267545622},{"x":183,"y":11.691877522451216},{"x":184,"y":11.691877522451216},{"x":185,"y":11.683321445547923},{"x":186,"y":11.670475568716126},{"x":187,"y":11.670475568716126},{"x":188,"y":11.683321445547923},{"x":189,"y":11.683321445547923},{"x":190,"y":11.683321445547923},{"x":191,"y":11.670475568716126},{"x":192,"y":11.670475568716126},{"x":193,"y":11.670475568716126},{"x":194,"y":11.670475568716126},{"x":195,"y":11.670475568716126},{"x":196,"y":11.670475568716126},{"x":197,"y":11.313708498984761},{"x":198,"y":11.313708498984761},{"x":199,"y":11.322543883774529},{"x":200,"y":11.322543883774529},{"x":201,"y":11.322543883774529},{"x":202,"y":11.326958991715296},{"x":203,"y":11.326958991715296},{"x":204,"y":11.326958991715296},{"x":205,"y":11.349008767288886},{"x":206,"y":11.349008767288886},{"x":207,"y":11.371015785759864},{"x":208,"y":11.371015785759864},{"x":209,"y":11.371015785759864},{"x":210,"y":11.414902540100814},{"x":211,"y":11.43241006962224},{"x":212,"y":11.489125293076057},{"x":213,"y":11.489125293076057},{"x":214,"y":11.489125293076057},{"x":215,"y":11.832159566199232},{"x":216,"y":11.832159566199232},{"x":217,"y":11.832159566199232},{"x":218,"y":11.832159566199232},{"x":219,"y":11.445523142259598},{"x":220,"y":11.445523142259598},{"x":221,"y":11.519548602267365},{"x":222,"y":11.610340218959994},{"x":223,"y":11.610340218959994},{"x":224,"y":11.610340218959994},{"x":225,"y":11.610340218959994},{"x":226,"y":11.610340218959994},{"x":227,"y":11.610340218959994},{"x":228,"y":11.233877335986895},{"x":229,"y":11.335784048754634},{"x":230,"y":11.30044246921332},{"x":231,"y":11.30044246921332},{"x":232,"y":11.30044246921332},{"x":233,"y":11.7983049630021},{"x":234,"y":11.7983049630021},{"x":235,"y":11.811011811017716},{"x":236,"y":11.811011811017716},{"x":237,"y":11.811011811017716},{"x":238,"y":11.811011811017716},{"x":239,"y":11.811011811017716},{"x":240,"y":11.811011811017716},{"x":241,"y":11.811011811017716},{"x":242,"y":11.811011811017716},{"x":243,"y":11.945710527214361},{"x":244,"y":12.477980605851252},{"x":245,"y":12.509996003196804},{"x":246,"y":12.509996003196804},{"x":247,"y":12.509996003196804},{"x":248,"y":12.884098726725126},{"x":249,"y":12.549900398011133},{"x":250,"y":12.397580409095962},{"x":251,"y":12.397580409095962},{"x":252,"y":12.397580409095962},{"x":253,"y":12.397580409095962},{"x":254,"y":12.409673645990857},{"x":255,"y":12.409673645990857},{"x":256,"y":12.477980605851252},{"x":257,"y":12.409673645990857},{"x":258,"y":12.409673645990857},{"x":259,"y":12.275992831539126},{"x":260,"y":12.275992831539126},{"x":261,"y":12.227019260637483},{"x":262,"y":12.227019260637483},{"x":263,"y":12.227019260637483},{"x":264,"y":12.227019260637483},{"x":265,"y":12.227019260637483},{"x":266,"y":12.227019260637483},{"x":267,"y":12.227019260637483},{"x":268,"y":11.726039399558575},{"x":269,"y":11.726039399558575},{"x":270,"y":11.726039399558575},{"x":271,"y":11.726039399558575},{"x":272,"y":11.726039399558575},{"x":273,"y":11.34460224071342},{"x":274,"y":11.34460224071342},{"x":275,"y":11.34460224071342},{"x":276,"y":11.34460224071342},{"x":277,"y":11.34460224071342},{"x":278,"y":11.34460224071342},{"x":279,"y":10.96813566655701},{"x":280,"y":10.96813566655701},{"x":281,"y":10.963576058932597},{"x":282,"y":10.977249200050075},{"x":283,"y":10.51189802081432},{"x":284,"y":10.51189802081432},{"x":285,"y":10.51189802081432},{"x":286,"y":10.158740079360236},{"x":287,"y":10.114346246792227},{"x":288,"y":10.114346246792227},{"x":289,"y":10.114346246792227},{"x":290,"y":10.114346246792227},{"x":291,"y":10.114346246792227},{"x":292,"y":9.813256340277675},{"x":293,"y":9.85900603509299},{"x":294,"y":9.813256340277675},{"x":295,"y":9.813256340277675},{"x":296,"y":9.91463564635635},{"x":297,"y":9.91463564635635},{"x":298,"y":9.91463564635635},{"x":299,"y":9.989994994993742},{"x":300,"y":9.964938534682489}]},{"name":"36b24e4d-38fe-4d39-a671-e68760e6760f","values":[{"x":0,"y":0.0},{"x":1,"y":0.4472135954999579},{"x":2,"y":0.8944271909999159},{"x":3,"y":0.8944271909999159},{"x":4,"y":0.8944271909999159},{"x":5,"y":0.8944271909999159},{"x":6,"y":1.3416407864998738},{"x":7,"y":1.3416407864998738},{"x":8,"y":1.7888543819998317},{"x":9,"y":1.7888543819998317},{"x":10,"y":2.23606797749979},{"x":11,"y":2.6832815729997477},{"x":12,"y":2.6832815729997477},{"x":13,"y":3.1304951684997055},{"x":14,"y":3.1304951684997055},{"x":15,"y":3.1304951684997055},{"x":16,"y":3.1304951684997055},{"x":17,"y":3.1304951684997055},{"x":18,"y":2.6832815729997477},{"x":19,"y":3.271085446759225},{"x":20,"y":2.8284271247461903},{"x":21,"y":3.271085446759225},{"x":22,"y":3.271085446759225},{"x":23,"y":3.714835124201342},{"x":24,"y":3.714835124201342},{"x":25,"y":3.714835124201342},{"x":26,"y":4.159326868617084},{"x":27,"y":4.604345773288535},{"x":28,"y":5.049752469181039},{"x":29,"y":5.495452665613635},{"x":30,"y":5.495452665613635},{"x":31,"y":5.049752469181039},{"x":32,"y":5.049752469181039},{"x":33,"y":5.049752469181039},{"x":34,"y":5.495452665613635},{"x":35,"y":5.049752469181039},{"x":36,"y":4.604345773288535},{"x":37,"y":5.049752469181039},{"x":38,"y":5.049752469181039},{"x":39,"y":4.604345773288535},{"x":40,"y":5.049752469181039},{"x":41,"y":5.495452665613635},{"x":42,"y":5.941380311005179},{"x":43,"y":5.941380311005179},{"x":44,"y":6.387487769068525},{"x":45,"y":6.387487769068525},{"x":46,"y":6.833739825307955},{"x":47,"y":6.833739825307955},{"x":48,"y":6.870225614927067},{"x":49,"y":6.745368781616021},{"x":50,"y":6.648308055437865},{"x":51,"y":6.610597552415364},{"x":52,"y":6.610597552415364},{"x":53,"y":6.610597552415364},{"x":54,"y":6.54217089351845},{"x":55,"y":6.5038450166036395},{"x":56,"y":6.54217089351845},{"x":57,"y":6.54217089351845},{"x":58,"y":6.54217089351845},{"x":59,"y":6.54217089351845},{"x":60,"y":6.6932802122726045},{"x":61,"y":6.6932802122726045},{"x":62,"y":6.6932802122726045},{"x":63,"y":6.6932802122726045},{"x":64,"y":6.534523701081817},{"x":65,"y":6.534523701081817},{"x":66,"y":6.6932802122726045},{"x":67,"y":6.6932802122726045},{"x":68,"y":6.6932802122726045},{"x":69,"y":6.6932802122726045},{"x":70,"y":6.8044103344816},{"x":71,"y":6.610597552415364},{"x":72,"y":6.610597552415364},{"x":73,"y":6.610597552415364},{"x":74,"y":6.610597552415364},{"x":75,"y":6.8044103344816},{"x":76,"y":6.8044103344816},{"x":77,"y":6.8044103344816},{"x":78,"y":6.8044103344816},{"x":79,"y":6.8044103344816},{"x":80,"y":6.985699678629192},{"x":81,"y":6.760177512462229},{"x":82,"y":6.985699678629192},{"x":83,"y":7.197221686178632},{"x":84,"y":6.978538528947161},{"x":85,"y":7.197221686178632},{"x":86,"y":7.014271166700073},{"x":87,"y":7.0710678118654755},{"x":88,"y":7.245688373094719},{"x":89,"y":7.483314773547883},{"x":90,"y":7.726577508832744},{"x":91,"y":7.726577508832744},{"x":92,"y":8.07465169527454},{"x":93,"y":8.12403840463596},{"x":94,"y":7.854934754662192},{"x":95,"y":7.854934754662192},{"x":96,"y":7.854934754662192},{"x":97,"y":7.602631123499285},{"x":98,"y":7.854934754662192},{"x":99,"y":7.854934754662192},{"x":100,"y":8.093207028119323},{"x":101,"y":8.093207028119323},{"x":102,"y":8.093207028119323},{"x":103,"y":7.854934754662192},{"x":104,"y":7.854934754662192},{"x":105,"y":7.854934754662192},{"x":106,"y":7.854934754662192},{"x":107,"y":8.093207028119323},{"x":108,"y":8.093207028119323},{"x":109,"y":8.455767262643882},{"x":110,"y":8.526429498916883},{"x":111,"y":8.526429498916883},{"x":112,"y":8.927485648266257},{"x":113,"y":8.927485648266257},{"x":114,"y":8.648699324175862},{"x":115,"y":8.526429498916883},{"x":116,"y":8.526429498916883},{"x":117,"y":8.5848704125339},{"x":118,"y":8.5848704125339},{"x":119,"y":8.648699324175862},{"x":120,"y":8.648699324175862},{"x":121,"y":8.648699324175862},{"x":122,"y":8.648699324175862},{"x":123,"y":8.648699324175862},{"x":124,"y":8.648699324175862},{"x":125,"y":8.384509526501834},{"x":126,"y":8.384509526501834},{"x":127,"y":8.792041856133306},{"x":128,"y":8.792041856133306},{"x":129,"y":8.792041856133306},{"x":130,"y":8.555699854482976},{"x":131,"y":8.228000972289685},{"x":132,"y":8.228000972289685},{"x":133,"y":8.526429498916883},{"x":134,"y":8.955445270895245},{"x":135,"y":8.689073598491383},{"x":136,"y":8.689073598491383},{"x":137,"y":8.689073598491383},{"x":138,"y":8.64291617453276},{"x":139,"y":8.64291617453276},{"x":140,"y":8.689073598491383},{"x":141,"y":8.438009243891594},{"x":142,"y":8.438009243891594},{"x":143,"y":8.396427811873332},{"x":144,"y":8.5848704125339},{"x":145,"y":8.5848704125339},{"x":146,"y":8.5848704125339},{"x":147,"y":8.5848704125339},{"x":148,"y":8.61974477580398},{"x":149,"y":8.61974477580398},{"x":150,"y":8.87130204648675},{"x":151,"y":9.137833441248533},{"x":152,"y":8.899438184514796},{"x":153,"y":8.899438184514796},{"x":154,"y":8.717797887081348},{"x":155,"y":8.455767262643882},{"x":156,"y":8.455767262643882},{"x":157,"y":8.497058314499201},{"x":158,"y":8.497058314499201},{"x":159,"y":8.983317872590282},{"x":160,"y":8.983317872590282},{"x":161,"y":8.983317872590282},{"x":162,"y":8.792041856133306},{"x":163,"y":8.792041856133306},{"x":164,"y":8.792041856133306},{"x":165,"y":9.044335243676011},{"x":166,"y":8.792041856133306},{"x":167,"y":9.044335243676011},{"x":168,"y":8.848728722251575},{"x":169,"y":8.848728722251575},{"x":170,"y":8.848728722251575},{"x":171,"y":8.677557259966655},{"x":172,"y":8.677557259966655},{"x":173,"y":8.677557259966655},{"x":174,"y":8.763560920082657},{"x":175,"y":8.438009243891594},{"x":176,"y":8.438009243891594},{"x":177,"y":8.526429498916883},{"x":178,"y":8.336666000266533},{"x":179,"y":8.336666000266533},{"x":180,"y":8.860022573334675},{"x":181,"y":8.860022573334675},{"x":182,"y":8.689073598491383},{"x":183,"y":8.46758525200662},{"x":184,"y":8.689073598491383},{"x":185,"y":8.46758525200662},{"x":186,"y":8.46758525200662},{"x":187,"y":8.264381404557755},{"x":188,"y":8.080841540334768},{"x":189,"y":8.049844718999243},{"x":190,"y":8.264381404557755},{"x":191,"y":8.497058314499201},{"x":192,"y":8.5848704125339},{"x":193,"y":8.438009243891594},{"x":194,"y":8.555699854482976},{"x":195,"y":8.555699854482976},{"x":196,"y":8.689073598491383},{"x":197,"y":9.038805230781334},{"x":198,"y":8.905054744357274},{"x":199,"y":9.071934744033381},{"x":200,"y":9.418067742376884},{"x":201,"y":9.311283477587825},{"x":202,"y":9.082951062292475},{"x":203,"y":9.192388155425117},{"x":204,"y":9.192388155425117},{"x":205,"y":8.955445270895245},{"x":206,"y":8.955445270895245},{"x":207,"y":8.955445270895245},{"x":208,"y":8.955445270895245},{"x":209,"y":8.734987120768983},{"x":210,"y":8.532291603080617},{"x":211,"y":8.34865258589672},{"x":212,"y":8.18535277187245},{"x":213,"y":8.18535277187245},{"x":214,"y":8.18535277187245},{"x":215,"y":8.18535277187245},{"x":216,"y":8.34865258589672},{"x":217,"y":8.532291603080617},{"x":218,"y":8.61974477580398},{"x":219,"y":8.61974477580398},{"x":220,"y":8.848728722251575},{"x":221,"y":8.61974477580398},{"x":222,"y":8.408329203831164},{"x":223,"y":8.408329203831164},{"x":224,"y":8.61974477580398},{"x":225,"y":8.408329203831164},{"x":226,"y":8.408329203831164},{"x":227,"y":8.215838362577491},{"x":228,"y":8.215838362577491},{"x":229,"y":8.043631020876083},{"x":230,"y":8.043631020876083},{"x":231,"y":8.215838362577491},{"x":232,"y":8.306623862918075},{"x":233,"y":8.306623862918075},{"x":234,"y":8.526429498916883},{"x":235,"y":8.526429498916883},{"x":236,"y":8.700574693662482},{"x":237,"y":8.927485648266257},{"x":238,"y":8.927485648266257},{"x":239,"y":8.927485648266257},{"x":240,"y":9.20326029187483},{"x":241,"y":9.539392014169456},{"x":242,"y":9.539392014169456},{"x":243,"y":9.539392014169456},{"x":244,"y":9.539392014169456},{"x":245,"y":9.418067742376884},{"x":246,"y":9.433981132056603},{"x":247,"y":9.746794344808963},{"x":248,"y":9.695359714832659},{"x":249,"y":9.695359714832659},{"x":250,"y":9.695359714832659},{"x":251,"y":9.695359714832659},{"x":252,"y":9.444575162494075},{"x":253,"y":9.695359714832659},{"x":254,"y":9.695359714832659},{"x":255,"y":9.695359714832659},{"x":256,"y":9.959919678390985},{"x":257,"y":9.959919678390985},{"x":258,"y":9.959919678390985},{"x":259,"y":9.959919678390985},{"x":260,"y":10.0},{"x":261,"y":10.0},{"x":262,"y":9.884331034521255},{"x":263,"y":10.114346246792227},{"x":264,"y":9.884331034521255},{"x":265,"y":9.555103348473002},{"x":266,"y":9.528903399657276},{"x":267,"y":9.787747442593725},{"x":268,"y":9.787747442593725},{"x":269,"y":9.47100839404126},{"x":270,"y":9.47100839404126},{"x":271,"y":9.47100839404126},{"x":272,"y":9.72111104761179},{"x":273,"y":9.72111104761179},{"x":274,"y":9.418067742376884},{"x":275,"y":9.126883367283709},{"x":276,"y":9.23038460737146},{"x":277,"y":9.23038460737146},{"x":278,"y":9.354143466934854},{"x":279,"y":9.354143466934854},{"x":280,"y":9.354143466934854},{"x":281,"y":9.354143466934854},{"x":282,"y":9.354143466934854},{"x":283,"y":9.354143466934854},{"x":284,"y":9.497368056467012},{"x":285,"y":9.497368056467012},{"x":286,"y":9.762171889492624},{"x":287,"y":9.762171889492624},{"x":288,"y":9.939818911831342},{"x":289,"y":10.237187113655782},{"x":290,"y":10.114346246792227},{"x":291,"y":10.114346246792227},{"x":292,"y":9.787747442593725},{"x":293,"y":9.787747442593725},{"x":294,"y":9.787747442593725},{"x":295,"y":9.47100839404126},{"x":296,"y":9.47100839404126},{"x":297,"y":9.47100839404126},{"x":298,"y":9.121403400793104},{"x":299,"y":9.055385138137417},{"x":300,"y":9.055385138137417}]},{"name":"b7025b7f-cbe1-43c3-8849-36e0f487b98a","values":[{"x":0,"y":0.0},{"x":1,"y":0.0},{"x":2,"y":0.0},{"x":3,"y":0.4472135954999579},{"x":4,"y":1.0954451150103321},{"x":5,"y":1.224744871391589},{"x":6,"y":1.224744871391589},{"x":7,"y":0.8366600265340756},{"x":8,"y":0.8366600265340756},{"x":9,"y":0.8366600265340756},{"x":10,"y":0.8366600265340756},{"x":11,"y":0.8366600265340756},{"x":12,"y":1.140175425099138},{"x":13,"y":1.140175425099138},{"x":14,"y":1.51657508881031},{"x":15,"y":1.9493588689617927},{"x":16,"y":2.5099800796022267},{"x":17,"y":2.3021728866442674},{"x":18,"y":2.3021728866442674},{"x":19,"y":2.3021728866442674},{"x":20,"y":2.280350850198276},{"x":21,"y":2.280350850198276},{"x":22,"y":2.8635642126552705},{"x":23,"y":2.8635642126552705},{"x":24,"y":3.082207001484488},{"x":25,"y":3.3466401061363023},{"x":26,"y":3.3466401061363023},{"x":27,"y":3.3466401061363023},{"x":28,"y":3.391164991562634},{"x":29,"y":3.391164991562634},{"x":30,"y":3.3466401061363023},{"x":31,"y":3.361547262794322},{"x":32,"y":3.271085446759225},{"x":33,"y":3.271085446759225},{"x":34,"y":3.271085446759225},{"x":35,"y":3.286335345030997},{"x":36,"y":3.361547262794322},{"x":37,"y":3.361547262794322},{"x":38,"y":3.361547262794322},{"x":39,"y":3.4351128074635335},{"x":40,"y":3.4351128074635335},{"x":41,"y":3.4351128074635335},{"x":42,"y":3.4351128074635335},{"x":43,"y":3.847076812334269},{"x":44,"y":3.847076812334269},{"x":45,"y":4.09878030638384},{"x":46,"y":4.09878030638384},{"x":47,"y":4.381780460041329},{"x":48,"y":4.207136793592526},{"x":49,"y":4.159326868617084},{"x":50,"y":4.159326868617084},{"x":51,"y":3.5071355833500366},{"x":52,"y":3.5637059362410923},{"x":53,"y":3.5637059362410923},{"x":54,"y":3.5637059362410923},{"x":55,"y":3.5637059362410923},{"x":56,"y":3.5637059362410923},{"x":57,"y":3.5637059362410923},{"x":58,"y":3.6742346141747673},{"x":59,"y":3.8987177379235853},{"x":60,"y":3.8340579025361627},{"x":61,"y":4.207136793592526},{"x":62,"y":4.658325879540846},{"x":63,"y":4.774934554525329},{"x":64,"y":4.774934554525329},{"x":65,"y":4.774934554525329},{"x":66,"y":4.774934554525329},{"x":67,"y":4.33589667773576},{"x":68,"y":4.33589667773576},{"x":69,"y":4.8270073544588685},{"x":70,"y":4.8270073544588685},{"x":71,"y":4.8270073544588685},{"x":72,"y":4.381780460041329},{"x":73,"y":3.9370039370059056},{"x":74,"y":3.9370039370059056},{"x":75,"y":4.08656334834051},{"x":76,"y":4.604345773288535},{"x":77,"y":4.722287581247038},{"x":78,"y":4.878524367060187},{"x":79,"y":4.878524367060187},{"x":80,"y":4.878524367060187},{"x":81,"y":4.878524367060187},{"x":82,"y":4.878524367060187},{"x":83,"y":4.8270073544588685},{"x":84,"y":4.8270073544588685},{"x":85,"y":4.969909455915671},{"x":86,"y":5.412947441089743},{"x":87,"y":5.412947441089743},{"x":88,"y":5.412947441089743},{"x":89,"y":5.412947441089743},{"x":90,"y":5.540758070878027},{"x":91,"y":5.630275304103699},{"x":92,"y":5.019960159204453},{"x":93,"y":5.019960159204453},{"x":94,"y":5.019960159204453},{"x":95,"y":5.019960159204453},{"x":96,"y":5.0990195135927845},{"x":97,"y":5.0990195135927845},{"x":98,"y":5.0990195135927845},{"x":99,"y":5.612486080160912},{"x":100,"y":5.612486080160912},{"x":101,"y":5.656854249492381},{"x":102,"y":5.656854249492381},{"x":103,"y":5.656854249492381},{"x":104,"y":5.656854249492381},{"x":105,"y":6.164414002968976},{"x":106,"y":6.300793600809346},{"x":107,"y":6.300793600809346},{"x":108,"y":6.300793600809346},{"x":109,"y":6.300793600809346},{"x":110,"y":6.300793600809346},{"x":111,"y":6.4265076052238514},{"x":112,"y":6.4265076052238514},{"x":113,"y":6.58027355054484},{"x":114,"y":6.58027355054484},{"x":115,"y":6.58027355054484},{"x":116,"y":6.58027355054484},{"x":117,"y":6.58027355054484},{"x":118,"y":7.092249290598858},{"x":119,"y":7.092249290598858},{"x":120,"y":7.092249290598858},{"x":121,"y":7.635443667528429},{"x":122,"y":7.19027120489902},{"x":123,"y":7.224956747275377},{"x":124,"y":7.224956747275377},{"x":125,"y":7.224956747275377},{"x":126,"y":7.758865896508329},{"x":127,"y":7.19027120489902},{"x":128,"y":7.19027120489902},{"x":129,"y":7.19027120489902},{"x":130,"y":7.19027120489902},{"x":131,"y":7.19027120489902},{"x":132,"y":7.19027120489902},{"x":133,"y":7.19027120489902},{"x":134,"y":6.685805860178712},{"x":135,"y":6.685805860178712},{"x":136,"y":6.54217089351845},{"x":137,"y":6.685805860178712},{"x":138,"y":6.745368781616021},{"x":139,"y":6.819090848492928},{"x":140,"y":7.259476565152615},{"x":141,"y":7.259476565152615},{"x":142,"y":7.259476565152615},{"x":143,"y":7.314369419163897},{"x":144,"y":7.314369419163897},{"x":145,"y":7.429670248402684},{"x":146,"y":7.314369419163897},{"x":147,"y":6.870225614927067},{"x":148,"y":7.314369419163897},{"x":149,"y":6.870225614927067},{"x":150,"y":6.870225614927067},{"x":151,"y":6.4265076052238514},{"x":152,"y":6.4265076052238514},{"x":153,"y":6.4265076052238514},{"x":154,"y":6.4265076052238514},{"x":155,"y":6.4265076052238514},{"x":156,"y":5.89915248150105},{"x":157,"y":5.89915248150105},{"x":158,"y":6.06630035524124},{"x":159,"y":6.06630035524124},{"x":160,"y":6.06630035524124},{"x":161,"y":5.89915248150105},{"x":162,"y":5.848076606885378},{"x":163,"y":5.848076606885378},{"x":164,"y":5.848076606885378},{"x":165,"y":5.848076606885378},{"x":166,"y":5.848076606885378},{"x":167,"y":5.848076606885378},{"x":168,"y":5.848076606885378},{"x":169,"y":5.848076606885378},{"x":170,"y":5.787918451395113},{"x":171,"y":5.787918451395113},{"x":172,"y":5.974947698515862},{"x":173,"y":5.787918451395113},{"x":174,"y":5.761944116355173},{"x":175,"y":5.761944116355173},{"x":176,"y":5.761944116355173},{"x":177,"y":5.761944116355173},{"x":178,"y":5.761944116355173},{"x":179,"y":5.958187643906492},{"x":180,"y":5.5677643628300215},{"x":181,"y":5.974947698515862},{"x":182,"y":6.220932405998316},{"x":183,"y":6.220932405998316},{"x":184,"y":6.220932405998316},{"x":185,"y":6.220932405998316},{"x":186,"y":6.220932405998316},{"x":187,"y":6.442049363362563},{"x":188,"y":6.442049363362563},{"x":189,"y":6.442049363362563},{"x":190,"y":6.442049363362563},{"x":191,"y":6.442049363362563},{"x":192,"y":6.442049363362563},{"x":193,"y":6.745368781616021},{"x":194,"y":6.745368781616021},{"x":195,"y":6.782329983125268},{"x":196,"y":6.610597552415364},{"x":197,"y":6.610597552415364},{"x":198,"y":6.610597552415364},{"x":199,"y":6.610597552415364},{"x":200,"y":6.519202405202649},{"x":201,"y":6.782329983125268},{"x":202,"y":6.782329983125268},{"x":203,"y":6.782329983125268},{"x":204,"y":6.760177512462229},{"x":205,"y":6.760177512462229},{"x":206,"y":6.767569726275452},{"x":207,"y":6.767569726275452},{"x":208,"y":6.58027355054484},{"x":209,"y":6.58027355054484},{"x":210,"y":6.58027355054484},{"x":211,"y":6.58027355054484},{"x":212,"y":6.58027355054484},{"x":213,"y":6.4265076052238514},{"x":214,"y":6.300793600809346},{"x":215,"y":5.932958789676531},{"x":216,"y":5.656854249492381},{"x":217,"y":5.403702434442518},{"x":218,"y":5.403702434442518},{"x":219,"y":5.630275304103699},{"x":220,"y":5.630275304103699},{"x":221,"y":5.630275304103699},{"x":222,"y":5.630275304103699},{"x":223,"y":5.244044240850758},{"x":224,"y":5.019960159204453},{"x":225,"y":5.0990195135927845},{"x":226,"y":4.919349550499537},{"x":227,"y":4.919349550499537},{"x":228,"y":5.029910535983717},{"x":229,"y":5.029910535983717},{"x":230,"y":4.878524367060187},{"x":231,"y":4.658325879540846},{"x":232,"y":4.658325879540846},{"x":233,"y":4.43846820423443},{"x":234,"y":4.43846820423443},{"x":235,"y":4.560701700396552},{"x":236,"y":4.560701700396552},{"x":237,"y":4.560701700396552},{"x":238,"y":4.43846820423443},{"x":239,"y":4.03732584763727},{"x":240,"y":4.03732584763727},{"x":241,"y":3.9749213828703582},{"x":242,"y":4.159326868617084},{"x":243,"y":3.9115214431215892},{"x":244,"y":3.7815340802378077},{"x":245,"y":3.7815340802378077},{"x":246,"y":3.847076812334269},{"x":247,"y":3.9749213828703582},{"x":248,"y":3.9749213828703582},{"x":249,"y":4.159326868617084},{"x":250,"y":4.324349662087931},{"x":251,"y":4.219004621945797},{"x":252,"y":4.43846820423443},{"x":253,"y":4.277849927241488},{"x":254,"y":4.219004621945797},{"x":255,"y":4.207136793592526},{"x":256,"y":4.207136793592526},{"x":257,"y":4.207136793592526},{"x":258,"y":3.847076812334269},{"x":259,"y":3.847076812334269},{"x":260,"y":3.847076812334269},{"x":261,"y":3.847076812334269},{"x":262,"y":3.7013511046643495},{"x":263,"y":3.8078865529319543},{"x":264,"y":3.7013511046643495},{"x":265,"y":3.7013511046643495},{"x":266,"y":3.8340579025361627},{"x":267,"y":3.8340579025361627},{"x":268,"y":4.324349662087931},{"x":269,"y":4.324349662087931},{"x":270,"y":4.43846820423443},{"x":271,"y":4.123105625617661},{"x":272,"y":4.324349662087931},{"x":273,"y":4.560701700396552},{"x":274,"y":4.560701700396552},{"x":275,"y":4.560701700396552},{"x":276,"y":4.604345773288535},{"x":277,"y":4.795831523312719},{"x":278,"y":4.795831523312719},{"x":279,"y":5.06951674225463},{"x":280,"y":5.263078946776307},{"x":281,"y":5.263078946776307},{"x":282,"y":5.291502622129181},{"x":283,"y":5.787918451395113},{"x":284,"y":5.787918451395113},{"x":285,"y":5.540758070878027},{"x":286,"y":5.540758070878027},{"x":287,"y":5.540758070878027},{"x":288,"y":5.585696017507576},{"x":289,"y":5.291502622129181},{"x":290,"y":5.06951674225463},{"x":291,"y":4.847679857416329},{"x":292,"y":4.847679857416329},{"x":293,"y":4.847679857416329},{"x":294,"y":4.847679857416329},{"x":295,"y":5.06951674225463},{"x":296,"y":4.878524367060187},{"x":297,"y":4.658325879540846},{"x":298,"y":4.207136793592526},{"x":299,"y":4.207136793592526},{"x":300,"y":4.207136793592526}]}],"marks":[{"type":"line","from":{"data":"7eaba6d6-1812-4a80-9991-f82ece767baa"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"stroke":{"value":"Blue"},"strokeWidth":{"value":2},"strokeOpacity":{"value":1}}}},{"type":"line","from":{"data":"36b24e4d-38fe-4d39-a671-e68760e6760f"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"stroke":{"value":"Red"},"strokeWidth":{"value":2},"strokeOpacity":{"value":1}}}},{"type":"line","from":{"data":"b7025b7f-cbe1-43c3-8849-36e0f487b98a"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"stroke":{"value":"Green"},"strokeWidth":{"value":2},"strokeOpacity":{"value":1}}}}]},"value":"#gorilla_repl.vega.VegaView{:content {:width 400, :height 247.2188, :padding {:top 10, :left 55, :bottom 40, :right 10}, :scales [{:name \"x\", :type \"linear\", :range \"width\", :zero false, :domain {:data \"7eaba6d6-1812-4a80-9991-f82ece767baa\", :field \"data.x\"}} {:name \"y\", :type \"linear\", :range \"height\", :nice true, :zero false, :domain [0.0 12.891857895586655]}], :axes [{:type \"x\", :scale \"x\"} {:type \"y\", :scale \"y\"}], :data ({:name \"7eaba6d6-1812-4a80-9991-f82ece767baa\", :values ({:x 0, :y 0.0} {:x 1, :y 0.0} {:x 2, :y 0.0} {:x 3, :y 0.4472135954999579} {:x 4, :y 1.0954451150103321} {:x 5, :y 0.7071067811865476} {:x 6, :y 0.7071067811865476} {:x 7, :y 0.4472135954999579} {:x 8, :y 0.4472135954999579} {:x 9, :y 0.7071067811865476} {:x 10, :y 0.7071067811865476} {:x 11, :y 0.7071067811865476} {:x 12, :y 0.4472135954999579} {:x 13, :y 0.4472135954999579} {:x 14, :y 0.5477225575051661} {:x 15, :y 0.8944271909999159} {:x 16, :y 1.51657508881031} {:x 17, :y 1.51657508881031} {:x 18, :y 1.51657508881031} {:x 19, :y 1.51657508881031} {:x 20, :y 1.51657508881031} {:x 21, :y 1.51657508881031} {:x 22, :y 2.1908902300206643} {:x 23, :y 2.1908902300206643} {:x 24, :y 1.9493588689617927} {:x 25, :y 1.7888543819998317} {:x 26, :y 1.7888543819998317} {:x 27, :y 1.7888543819998317} {:x 28, :y 1.7888543819998317} {:x 29, :y 1.7888543819998317} {:x 30, :y 1.7888543819998317} {:x 31, :y 1.7888543819998317} {:x 32, :y 1.7888543819998317} {:x 33, :y 1.9493588689617927} {:x 34, :y 1.9493588689617927} {:x 35, :y 1.9493588689617927} {:x 36, :y 1.9493588689617927} {:x 37, :y 1.9493588689617927} {:x 38, :y 1.9493588689617927} {:x 39, :y 1.9493588689617927} {:x 40, :y 1.9493588689617927} {:x 41, :y 1.9493588689617927} {:x 42, :y 1.9493588689617927} {:x 43, :y 2.1908902300206643} {:x 44, :y 2.1908902300206643} {:x 45, :y 1.9493588689617927} {:x 46, :y 1.9493588689617927} {:x 47, :y 1.7888543819998317} {:x 48, :y 1.7888543819998317} {:x 49, :y 1.7888543819998317} {:x 50, :y 1.7320508075688772} {:x 51, :y 1.7320508075688772} {:x 52, :y 1.7320508075688772} {:x 53, :y 1.7888543819998317} {:x 54, :y 1.7320508075688772} {:x 55, :y 1.7888543819998317} {:x 56, :y 2.16794833886788} {:x 57, :y 2.23606797749979} {:x 58, :y 2.23606797749979} {:x 59, :y 2.16794833886788} {:x 60, :y 2.16794833886788} {:x 61, :y 2.6832815729997477} {:x 62, :y 3.271085446759225} {:x 63, :y 3.271085446759225} {:x 64, :y 3.1304951684997055} {:x 65, :y 3.271085446759225} {:x 66, :y 3.4641016151377544} {:x 67, :y 3.4641016151377544} {:x 68, :y 3.7013511046643495} {:x 69, :y 4.381780460041329} {:x 70, :y 5.06951674225463} {:x 71, :y 4.795831523312719} {:x 72, :y 4.54972526643093} {:x 73, :y 4.33589667773576} {:x 74, :y 4.54972526643093} {:x 75, :y 4.54972526643093} {:x 76, :y 5.215361924162119} {:x 77, :y 5.215361924162119} {:x 78, :y 5.215361924162119} {:x 79, :y 4.979959839195493} {:x 80, :y 5.639148871948674} {:x 81, :y 5.639148871948674} {:x 82, :y 5.890670590009257} {:x 83, :y 5.890670590009257} {:x 84, :y 5.639148871948674} {:x 85, :y 5.639148871948674} {:x 86, :y 5.639148871948674} {:x 87, :y 5.412947441089743} {:x 88, :y 6.06630035524124} {:x 89, :y 6.730527468185536} {:x 90, :y 6.730527468185536} {:x 91, :y 6.730527468185536} {:x 92, :y 6.730527468185536} {:x 93, :y 6.496152707564686} {:x 94, :y 6.496152707564686} {:x 95, :y 6.730527468185536} {:x 96, :y 6.730527468185536} {:x 97, :y 6.496152707564686} {:x 98, :y 6.496152707564686} {:x 99, :y 7.155417527999327} {:x 100, :y 7.402702209328699} {:x 101, :y 7.402702209328699} {:x 102, :y 7.6681158050723255} {:x 103, :y 7.402702209328699} {:x 104, :y 7.6681158050723255} {:x 105, :y 8.35463942968217} {:x 106, :y 8.35463942968217} {:x 107, :y 8.64291617453276} {:x 108, :y 8.94427190999916} {:x 109, :y 9.643650760992955} {:x 110, :y 9.338094023943002} {:x 111, :y 9.044335243676011} {:x 112, :y 9.736529155710468} {:x 113, :y 9.736529155710468} {:x 114, :y 9.736529155710468} {:x 115, :y 9.736529155710468} {:x 116, :y 10.03493896344168} {:x 117, :y 10.03493896344168} {:x 118, :y 10.73312629199899} {:x 119, :y 10.430723848324238} {:x 120, :y 10.73312629199899} {:x 121, :y 11.43241006962224} {:x 122, :y 11.43241006962224} {:x 123, :y 11.43241006962224} {:x 124, :y 11.74734012447073} {:x 125, :y 11.43241006962224} {:x 126, :y 12.13260071048248} {:x 127, :y 12.13260071048248} {:x 128, :y 12.449899597988733} {:x 129, :y 12.477980605851252} {:x 130, :y 12.449899597988733} {:x 131, :y 12.165525060596439} {:x 132, :y 12.477980605851252} {:x 133, :y 12.477980605851252} {:x 134, :y 12.477980605851252} {:x 135, :y 12.477980605851252} {:x 136, :y 12.501999840025595} {:x 137, :y 12.477980605851252} {:x 138, :y 12.477980605851252} {:x 139, :y 12.070625501605125} {:x 140, :y 12.070625501605125} {:x 141, :y 12.062338081814818} {:x 142, :y 12.049896265113654} {:x 143, :y 12.049896265113654} {:x 144, :y 12.033287165193059} {:x 145, :y 12.033287165193059} {:x 146, :y 12.033287165193059} {:x 147, :y 12.033287165193059} {:x 148, :y 12.033287165193059} {:x 149, :y 12.033287165193059} {:x 150, :y 12.054044964243332} {:x 151, :y 12.054044964243332} {:x 152, :y 12.074767078498866} {:x 153, :y 12.054044964243332} {:x 154, :y 12.054044964243332} {:x 155, :y 12.054044964243332} {:x 156, :y 11.717508267545622} {:x 157, :y 11.388590782006348} {:x 158, :y 11.388590782006348} {:x 159, :y 12.091319200153471} {:x 160, :y 12.421755109484328} {:x 161, :y 12.461942063739505} {:x 162, :y 12.461942063739505} {:x 163, :y 12.457929201917949} {:x 164, :y 12.049896265113654} {:x 165, :y 12.457929201917949} {:x 166, :y 12.457929201917949} {:x 167, :y 12.891857895586655} {:x 168, :y 12.83744522870497} {:x 169, :y 12.798437404620925} {:x 170, :y 12.421755109484328} {:x 171, :y 12.437845472588892} {:x 172, :y 12.437845472588892} {:x 173, :y 12.449899597988733} {:x 174, :y 12.449899597988733} {:x 175, :y 12.449899597988733} {:x 176, :y 12.437845472588892} {:x 177, :y 12.049896265113654} {:x 178, :y 12.054044964243332} {:x 179, :y 12.074767078498866} {:x 180, :y 12.074767078498866} {:x 181, :y 11.717508267545622} {:x 182, :y 11.717508267545622} {:x 183, :y 11.691877522451216} {:x 184, :y 11.691877522451216} {:x 185, :y 11.683321445547923} {:x 186, :y 11.670475568716126} {:x 187, :y 11.670475568716126} {:x 188, :y 11.683321445547923} {:x 189, :y 11.683321445547923} {:x 190, :y 11.683321445547923} {:x 191, :y 11.670475568716126} {:x 192, :y 11.670475568716126} {:x 193, :y 11.670475568716126} {:x 194, :y 11.670475568716126} {:x 195, :y 11.670475568716126} {:x 196, :y 11.670475568716126} {:x 197, :y 11.313708498984761} {:x 198, :y 11.313708498984761} {:x 199, :y 11.322543883774529} {:x 200, :y 11.322543883774529} {:x 201, :y 11.322543883774529} {:x 202, :y 11.326958991715296} {:x 203, :y 11.326958991715296} {:x 204, :y 11.326958991715296} {:x 205, :y 11.349008767288886} {:x 206, :y 11.349008767288886} {:x 207, :y 11.371015785759864} {:x 208, :y 11.371015785759864} {:x 209, :y 11.371015785759864} {:x 210, :y 11.414902540100814} {:x 211, :y 11.43241006962224} {:x 212, :y 11.489125293076057} {:x 213, :y 11.489125293076057} {:x 214, :y 11.489125293076057} {:x 215, :y 11.832159566199232} {:x 216, :y 11.832159566199232} {:x 217, :y 11.832159566199232} {:x 218, :y 11.832159566199232} {:x 219, :y 11.445523142259598} {:x 220, :y 11.445523142259598} {:x 221, :y 11.519548602267365} {:x 222, :y 11.610340218959994} {:x 223, :y 11.610340218959994} {:x 224, :y 11.610340218959994} {:x 225, :y 11.610340218959994} {:x 226, :y 11.610340218959994} {:x 227, :y 11.610340218959994} {:x 228, :y 11.233877335986895} {:x 229, :y 11.335784048754634} {:x 230, :y 11.30044246921332} {:x 231, :y 11.30044246921332} {:x 232, :y 11.30044246921332} {:x 233, :y 11.7983049630021} {:x 234, :y 11.7983049630021} {:x 235, :y 11.811011811017716} {:x 236, :y 11.811011811017716} {:x 237, :y 11.811011811017716} {:x 238, :y 11.811011811017716} {:x 239, :y 11.811011811017716} {:x 240, :y 11.811011811017716} {:x 241, :y 11.811011811017716} {:x 242, :y 11.811011811017716} {:x 243, :y 11.945710527214361} {:x 244, :y 12.477980605851252} {:x 245, :y 12.509996003196804} {:x 246, :y 12.509996003196804} {:x 247, :y 12.509996003196804} {:x 248, :y 12.884098726725126} {:x 249, :y 12.549900398011133} {:x 250, :y 12.397580409095962} {:x 251, :y 12.397580409095962} {:x 252, :y 12.397580409095962} {:x 253, :y 12.397580409095962} {:x 254, :y 12.409673645990857} {:x 255, :y 12.409673645990857} {:x 256, :y 12.477980605851252} {:x 257, :y 12.409673645990857} {:x 258, :y 12.409673645990857} {:x 259, :y 12.275992831539126} {:x 260, :y 12.275992831539126} {:x 261, :y 12.227019260637483} {:x 262, :y 12.227019260637483} {:x 263, :y 12.227019260637483} {:x 264, :y 12.227019260637483} {:x 265, :y 12.227019260637483} {:x 266, :y 12.227019260637483} {:x 267, :y 12.227019260637483} {:x 268, :y 11.726039399558575} {:x 269, :y 11.726039399558575} {:x 270, :y 11.726039399558575} {:x 271, :y 11.726039399558575} {:x 272, :y 11.726039399558575} {:x 273, :y 11.34460224071342} {:x 274, :y 11.34460224071342} {:x 275, :y 11.34460224071342} {:x 276, :y 11.34460224071342} {:x 277, :y 11.34460224071342} {:x 278, :y 11.34460224071342} {:x 279, :y 10.96813566655701} {:x 280, :y 10.96813566655701} {:x 281, :y 10.963576058932597} {:x 282, :y 10.977249200050075} {:x 283, :y 10.51189802081432} {:x 284, :y 10.51189802081432} {:x 285, :y 10.51189802081432} {:x 286, :y 10.158740079360236} {:x 287, :y 10.114346246792227} {:x 288, :y 10.114346246792227} {:x 289, :y 10.114346246792227} {:x 290, :y 10.114346246792227} {:x 291, :y 10.114346246792227} {:x 292, :y 9.813256340277675} {:x 293, :y 9.85900603509299} {:x 294, :y 9.813256340277675} {:x 295, :y 9.813256340277675} {:x 296, :y 9.91463564635635} {:x 297, :y 9.91463564635635} {:x 298, :y 9.91463564635635} {:x 299, :y 9.989994994993742} {:x 300, :y 9.964938534682489})} {:name \"36b24e4d-38fe-4d39-a671-e68760e6760f\", :values ({:x 0, :y 0.0} {:x 1, :y 0.4472135954999579} {:x 2, :y 0.8944271909999159} {:x 3, :y 0.8944271909999159} {:x 4, :y 0.8944271909999159} {:x 5, :y 0.8944271909999159} {:x 6, :y 1.3416407864998738} {:x 7, :y 1.3416407864998738} {:x 8, :y 1.7888543819998317} {:x 9, :y 1.7888543819998317} {:x 10, :y 2.23606797749979} {:x 11, :y 2.6832815729997477} {:x 12, :y 2.6832815729997477} {:x 13, :y 3.1304951684997055} {:x 14, :y 3.1304951684997055} {:x 15, :y 3.1304951684997055} {:x 16, :y 3.1304951684997055} {:x 17, :y 3.1304951684997055} {:x 18, :y 2.6832815729997477} {:x 19, :y 3.271085446759225} {:x 20, :y 2.8284271247461903} {:x 21, :y 3.271085446759225} {:x 22, :y 3.271085446759225} {:x 23, :y 3.714835124201342} {:x 24, :y 3.714835124201342} {:x 25, :y 3.714835124201342} {:x 26, :y 4.159326868617084} {:x 27, :y 4.604345773288535} {:x 28, :y 5.049752469181039} {:x 29, :y 5.495452665613635} {:x 30, :y 5.495452665613635} {:x 31, :y 5.049752469181039} {:x 32, :y 5.049752469181039} {:x 33, :y 5.049752469181039} {:x 34, :y 5.495452665613635} {:x 35, :y 5.049752469181039} {:x 36, :y 4.604345773288535} {:x 37, :y 5.049752469181039} {:x 38, :y 5.049752469181039} {:x 39, :y 4.604345773288535} {:x 40, :y 5.049752469181039} {:x 41, :y 5.495452665613635} {:x 42, :y 5.941380311005179} {:x 43, :y 5.941380311005179} {:x 44, :y 6.387487769068525} {:x 45, :y 6.387487769068525} {:x 46, :y 6.833739825307955} {:x 47, :y 6.833739825307955} {:x 48, :y 6.870225614927067} {:x 49, :y 6.745368781616021} {:x 50, :y 6.648308055437865} {:x 51, :y 6.610597552415364} {:x 52, :y 6.610597552415364} {:x 53, :y 6.610597552415364} {:x 54, :y 6.54217089351845} {:x 55, :y 6.5038450166036395} {:x 56, :y 6.54217089351845} {:x 57, :y 6.54217089351845} {:x 58, :y 6.54217089351845} {:x 59, :y 6.54217089351845} {:x 60, :y 6.6932802122726045} {:x 61, :y 6.6932802122726045} {:x 62, :y 6.6932802122726045} {:x 63, :y 6.6932802122726045} {:x 64, :y 6.534523701081817} {:x 65, :y 6.534523701081817} {:x 66, :y 6.6932802122726045} {:x 67, :y 6.6932802122726045} {:x 68, :y 6.6932802122726045} {:x 69, :y 6.6932802122726045} {:x 70, :y 6.8044103344816} {:x 71, :y 6.610597552415364} {:x 72, :y 6.610597552415364} {:x 73, :y 6.610597552415364} {:x 74, :y 6.610597552415364} {:x 75, :y 6.8044103344816} {:x 76, :y 6.8044103344816} {:x 77, :y 6.8044103344816} {:x 78, :y 6.8044103344816} {:x 79, :y 6.8044103344816} {:x 80, :y 6.985699678629192} {:x 81, :y 6.760177512462229} {:x 82, :y 6.985699678629192} {:x 83, :y 7.197221686178632} {:x 84, :y 6.978538528947161} {:x 85, :y 7.197221686178632} {:x 86, :y 7.014271166700073} {:x 87, :y 7.0710678118654755} {:x 88, :y 7.245688373094719} {:x 89, :y 7.483314773547883} {:x 90, :y 7.726577508832744} {:x 91, :y 7.726577508832744} {:x 92, :y 8.07465169527454} {:x 93, :y 8.12403840463596} {:x 94, :y 7.854934754662192} {:x 95, :y 7.854934754662192} {:x 96, :y 7.854934754662192} {:x 97, :y 7.602631123499285} {:x 98, :y 7.854934754662192} {:x 99, :y 7.854934754662192} {:x 100, :y 8.093207028119323} {:x 101, :y 8.093207028119323} {:x 102, :y 8.093207028119323} {:x 103, :y 7.854934754662192} {:x 104, :y 7.854934754662192} {:x 105, :y 7.854934754662192} {:x 106, :y 7.854934754662192} {:x 107, :y 8.093207028119323} {:x 108, :y 8.093207028119323} {:x 109, :y 8.455767262643882} {:x 110, :y 8.526429498916883} {:x 111, :y 8.526429498916883} {:x 112, :y 8.927485648266257} {:x 113, :y 8.927485648266257} {:x 114, :y 8.648699324175862} {:x 115, :y 8.526429498916883} {:x 116, :y 8.526429498916883} {:x 117, :y 8.5848704125339} {:x 118, :y 8.5848704125339} {:x 119, :y 8.648699324175862} {:x 120, :y 8.648699324175862} {:x 121, :y 8.648699324175862} {:x 122, :y 8.648699324175862} {:x 123, :y 8.648699324175862} {:x 124, :y 8.648699324175862} {:x 125, :y 8.384509526501834} {:x 126, :y 8.384509526501834} {:x 127, :y 8.792041856133306} {:x 128, :y 8.792041856133306} {:x 129, :y 8.792041856133306} {:x 130, :y 8.555699854482976} {:x 131, :y 8.228000972289685} {:x 132, :y 8.228000972289685} {:x 133, :y 8.526429498916883} {:x 134, :y 8.955445270895245} {:x 135, :y 8.689073598491383} {:x 136, :y 8.689073598491383} {:x 137, :y 8.689073598491383} {:x 138, :y 8.64291617453276} {:x 139, :y 8.64291617453276} {:x 140, :y 8.689073598491383} {:x 141, :y 8.438009243891594} {:x 142, :y 8.438009243891594} {:x 143, :y 8.396427811873332} {:x 144, :y 8.5848704125339} {:x 145, :y 8.5848704125339} {:x 146, :y 8.5848704125339} {:x 147, :y 8.5848704125339} {:x 148, :y 8.61974477580398} {:x 149, :y 8.61974477580398} {:x 150, :y 8.87130204648675} {:x 151, :y 9.137833441248533} {:x 152, :y 8.899438184514796} {:x 153, :y 8.899438184514796} {:x 154, :y 8.717797887081348} {:x 155, :y 8.455767262643882} {:x 156, :y 8.455767262643882} {:x 157, :y 8.497058314499201} {:x 158, :y 8.497058314499201} {:x 159, :y 8.983317872590282} {:x 160, :y 8.983317872590282} {:x 161, :y 8.983317872590282} {:x 162, :y 8.792041856133306} {:x 163, :y 8.792041856133306} {:x 164, :y 8.792041856133306} {:x 165, :y 9.044335243676011} {:x 166, :y 8.792041856133306} {:x 167, :y 9.044335243676011} {:x 168, :y 8.848728722251575} {:x 169, :y 8.848728722251575} {:x 170, :y 8.848728722251575} {:x 171, :y 8.677557259966655} {:x 172, :y 8.677557259966655} {:x 173, :y 8.677557259966655} {:x 174, :y 8.763560920082657} {:x 175, :y 8.438009243891594} {:x 176, :y 8.438009243891594} {:x 177, :y 8.526429498916883} {:x 178, :y 8.336666000266533} {:x 179, :y 8.336666000266533} {:x 180, :y 8.860022573334675} {:x 181, :y 8.860022573334675} {:x 182, :y 8.689073598491383} {:x 183, :y 8.46758525200662} {:x 184, :y 8.689073598491383} {:x 185, :y 8.46758525200662} {:x 186, :y 8.46758525200662} {:x 187, :y 8.264381404557755} {:x 188, :y 8.080841540334768} {:x 189, :y 8.049844718999243} {:x 190, :y 8.264381404557755} {:x 191, :y 8.497058314499201} {:x 192, :y 8.5848704125339} {:x 193, :y 8.438009243891594} {:x 194, :y 8.555699854482976} {:x 195, :y 8.555699854482976} {:x 196, :y 8.689073598491383} {:x 197, :y 9.038805230781334} {:x 198, :y 8.905054744357274} {:x 199, :y 9.071934744033381} {:x 200, :y 9.418067742376884} {:x 201, :y 9.311283477587825} {:x 202, :y 9.082951062292475} {:x 203, :y 9.192388155425117} {:x 204, :y 9.192388155425117} {:x 205, :y 8.955445270895245} {:x 206, :y 8.955445270895245} {:x 207, :y 8.955445270895245} {:x 208, :y 8.955445270895245} {:x 209, :y 8.734987120768983} {:x 210, :y 8.532291603080617} {:x 211, :y 8.34865258589672} {:x 212, :y 8.18535277187245} {:x 213, :y 8.18535277187245} {:x 214, :y 8.18535277187245} {:x 215, :y 8.18535277187245} {:x 216, :y 8.34865258589672} {:x 217, :y 8.532291603080617} {:x 218, :y 8.61974477580398} {:x 219, :y 8.61974477580398} {:x 220, :y 8.848728722251575} {:x 221, :y 8.61974477580398} {:x 222, :y 8.408329203831164} {:x 223, :y 8.408329203831164} {:x 224, :y 8.61974477580398} {:x 225, :y 8.408329203831164} {:x 226, :y 8.408329203831164} {:x 227, :y 8.215838362577491} {:x 228, :y 8.215838362577491} {:x 229, :y 8.043631020876083} {:x 230, :y 8.043631020876083} {:x 231, :y 8.215838362577491} {:x 232, :y 8.306623862918075} {:x 233, :y 8.306623862918075} {:x 234, :y 8.526429498916883} {:x 235, :y 8.526429498916883} {:x 236, :y 8.700574693662482} {:x 237, :y 8.927485648266257} {:x 238, :y 8.927485648266257} {:x 239, :y 8.927485648266257} {:x 240, :y 9.20326029187483} {:x 241, :y 9.539392014169456} {:x 242, :y 9.539392014169456} {:x 243, :y 9.539392014169456} {:x 244, :y 9.539392014169456} {:x 245, :y 9.418067742376884} {:x 246, :y 9.433981132056603} {:x 247, :y 9.746794344808963} {:x 248, :y 9.695359714832659} {:x 249, :y 9.695359714832659} {:x 250, :y 9.695359714832659} {:x 251, :y 9.695359714832659} {:x 252, :y 9.444575162494075} {:x 253, :y 9.695359714832659} {:x 254, :y 9.695359714832659} {:x 255, :y 9.695359714832659} {:x 256, :y 9.959919678390985} {:x 257, :y 9.959919678390985} {:x 258, :y 9.959919678390985} {:x 259, :y 9.959919678390985} {:x 260, :y 10.0} {:x 261, :y 10.0} {:x 262, :y 9.884331034521255} {:x 263, :y 10.114346246792227} {:x 264, :y 9.884331034521255} {:x 265, :y 9.555103348473002} {:x 266, :y 9.528903399657276} {:x 267, :y 9.787747442593725} {:x 268, :y 9.787747442593725} {:x 269, :y 9.47100839404126} {:x 270, :y 9.47100839404126} {:x 271, :y 9.47100839404126} {:x 272, :y 9.72111104761179} {:x 273, :y 9.72111104761179} {:x 274, :y 9.418067742376884} {:x 275, :y 9.126883367283709} {:x 276, :y 9.23038460737146} {:x 277, :y 9.23038460737146} {:x 278, :y 9.354143466934854} {:x 279, :y 9.354143466934854} {:x 280, :y 9.354143466934854} {:x 281, :y 9.354143466934854} {:x 282, :y 9.354143466934854} {:x 283, :y 9.354143466934854} {:x 284, :y 9.497368056467012} {:x 285, :y 9.497368056467012} {:x 286, :y 9.762171889492624} {:x 287, :y 9.762171889492624} {:x 288, :y 9.939818911831342} {:x 289, :y 10.237187113655782} {:x 290, :y 10.114346246792227} {:x 291, :y 10.114346246792227} {:x 292, :y 9.787747442593725} {:x 293, :y 9.787747442593725} {:x 294, :y 9.787747442593725} {:x 295, :y 9.47100839404126} {:x 296, :y 9.47100839404126} {:x 297, :y 9.47100839404126} {:x 298, :y 9.121403400793104} {:x 299, :y 9.055385138137417} {:x 300, :y 9.055385138137417})} {:name \"b7025b7f-cbe1-43c3-8849-36e0f487b98a\", :values ({:x 0, :y 0.0} {:x 1, :y 0.0} {:x 2, :y 0.0} {:x 3, :y 0.4472135954999579} {:x 4, :y 1.0954451150103321} {:x 5, :y 1.224744871391589} {:x 6, :y 1.224744871391589} {:x 7, :y 0.8366600265340756} {:x 8, :y 0.8366600265340756} {:x 9, :y 0.8366600265340756} {:x 10, :y 0.8366600265340756} {:x 11, :y 0.8366600265340756} {:x 12, :y 1.140175425099138} {:x 13, :y 1.140175425099138} {:x 14, :y 1.51657508881031} {:x 15, :y 1.9493588689617927} {:x 16, :y 2.5099800796022267} {:x 17, :y 2.3021728866442674} {:x 18, :y 2.3021728866442674} {:x 19, :y 2.3021728866442674} {:x 20, :y 2.280350850198276} {:x 21, :y 2.280350850198276} {:x 22, :y 2.8635642126552705} {:x 23, :y 2.8635642126552705} {:x 24, :y 3.082207001484488} {:x 25, :y 3.3466401061363023} {:x 26, :y 3.3466401061363023} {:x 27, :y 3.3466401061363023} {:x 28, :y 3.391164991562634} {:x 29, :y 3.391164991562634} {:x 30, :y 3.3466401061363023} {:x 31, :y 3.361547262794322} {:x 32, :y 3.271085446759225} {:x 33, :y 3.271085446759225} {:x 34, :y 3.271085446759225} {:x 35, :y 3.286335345030997} {:x 36, :y 3.361547262794322} {:x 37, :y 3.361547262794322} {:x 38, :y 3.361547262794322} {:x 39, :y 3.4351128074635335} {:x 40, :y 3.4351128074635335} {:x 41, :y 3.4351128074635335} {:x 42, :y 3.4351128074635335} {:x 43, :y 3.847076812334269} {:x 44, :y 3.847076812334269} {:x 45, :y 4.09878030638384} {:x 46, :y 4.09878030638384} {:x 47, :y 4.381780460041329} {:x 48, :y 4.207136793592526} {:x 49, :y 4.159326868617084} {:x 50, :y 4.159326868617084} {:x 51, :y 3.5071355833500366} {:x 52, :y 3.5637059362410923} {:x 53, :y 3.5637059362410923} {:x 54, :y 3.5637059362410923} {:x 55, :y 3.5637059362410923} {:x 56, :y 3.5637059362410923} {:x 57, :y 3.5637059362410923} {:x 58, :y 3.6742346141747673} {:x 59, :y 3.8987177379235853} {:x 60, :y 3.8340579025361627} {:x 61, :y 4.207136793592526} {:x 62, :y 4.658325879540846} {:x 63, :y 4.774934554525329} {:x 64, :y 4.774934554525329} {:x 65, :y 4.774934554525329} {:x 66, :y 4.774934554525329} {:x 67, :y 4.33589667773576} {:x 68, :y 4.33589667773576} {:x 69, :y 4.8270073544588685} {:x 70, :y 4.8270073544588685} {:x 71, :y 4.8270073544588685} {:x 72, :y 4.381780460041329} {:x 73, :y 3.9370039370059056} {:x 74, :y 3.9370039370059056} {:x 75, :y 4.08656334834051} {:x 76, :y 4.604345773288535} {:x 77, :y 4.722287581247038} {:x 78, :y 4.878524367060187} {:x 79, :y 4.878524367060187} {:x 80, :y 4.878524367060187} {:x 81, :y 4.878524367060187} {:x 82, :y 4.878524367060187} {:x 83, :y 4.8270073544588685} {:x 84, :y 4.8270073544588685} {:x 85, :y 4.969909455915671} {:x 86, :y 5.412947441089743} {:x 87, :y 5.412947441089743} {:x 88, :y 5.412947441089743} {:x 89, :y 5.412947441089743} {:x 90, :y 5.540758070878027} {:x 91, :y 5.630275304103699} {:x 92, :y 5.019960159204453} {:x 93, :y 5.019960159204453} {:x 94, :y 5.019960159204453} {:x 95, :y 5.019960159204453} {:x 96, :y 5.0990195135927845} {:x 97, :y 5.0990195135927845} {:x 98, :y 5.0990195135927845} {:x 99, :y 5.612486080160912} {:x 100, :y 5.612486080160912} {:x 101, :y 5.656854249492381} {:x 102, :y 5.656854249492381} {:x 103, :y 5.656854249492381} {:x 104, :y 5.656854249492381} {:x 105, :y 6.164414002968976} {:x 106, :y 6.300793600809346} {:x 107, :y 6.300793600809346} {:x 108, :y 6.300793600809346} {:x 109, :y 6.300793600809346} {:x 110, :y 6.300793600809346} {:x 111, :y 6.4265076052238514} {:x 112, :y 6.4265076052238514} {:x 113, :y 6.58027355054484} {:x 114, :y 6.58027355054484} {:x 115, :y 6.58027355054484} {:x 116, :y 6.58027355054484} {:x 117, :y 6.58027355054484} {:x 118, :y 7.092249290598858} {:x 119, :y 7.092249290598858} {:x 120, :y 7.092249290598858} {:x 121, :y 7.635443667528429} {:x 122, :y 7.19027120489902} {:x 123, :y 7.224956747275377} {:x 124, :y 7.224956747275377} {:x 125, :y 7.224956747275377} {:x 126, :y 7.758865896508329} {:x 127, :y 7.19027120489902} {:x 128, :y 7.19027120489902} {:x 129, :y 7.19027120489902} {:x 130, :y 7.19027120489902} {:x 131, :y 7.19027120489902} {:x 132, :y 7.19027120489902} {:x 133, :y 7.19027120489902} {:x 134, :y 6.685805860178712} {:x 135, :y 6.685805860178712} {:x 136, :y 6.54217089351845} {:x 137, :y 6.685805860178712} {:x 138, :y 6.745368781616021} {:x 139, :y 6.819090848492928} {:x 140, :y 7.259476565152615} {:x 141, :y 7.259476565152615} {:x 142, :y 7.259476565152615} {:x 143, :y 7.314369419163897} {:x 144, :y 7.314369419163897} {:x 145, :y 7.429670248402684} {:x 146, :y 7.314369419163897} {:x 147, :y 6.870225614927067} {:x 148, :y 7.314369419163897} {:x 149, :y 6.870225614927067} {:x 150, :y 6.870225614927067} {:x 151, :y 6.4265076052238514} {:x 152, :y 6.4265076052238514} {:x 153, :y 6.4265076052238514} {:x 154, :y 6.4265076052238514} {:x 155, :y 6.4265076052238514} {:x 156, :y 5.89915248150105} {:x 157, :y 5.89915248150105} {:x 158, :y 6.06630035524124} {:x 159, :y 6.06630035524124} {:x 160, :y 6.06630035524124} {:x 161, :y 5.89915248150105} {:x 162, :y 5.848076606885378} {:x 163, :y 5.848076606885378} {:x 164, :y 5.848076606885378} {:x 165, :y 5.848076606885378} {:x 166, :y 5.848076606885378} {:x 167, :y 5.848076606885378} {:x 168, :y 5.848076606885378} {:x 169, :y 5.848076606885378} {:x 170, :y 5.787918451395113} {:x 171, :y 5.787918451395113} {:x 172, :y 5.974947698515862} {:x 173, :y 5.787918451395113} {:x 174, :y 5.761944116355173} {:x 175, :y 5.761944116355173} {:x 176, :y 5.761944116355173} {:x 177, :y 5.761944116355173} {:x 178, :y 5.761944116355173} {:x 179, :y 5.958187643906492} {:x 180, :y 5.5677643628300215} {:x 181, :y 5.974947698515862} {:x 182, :y 6.220932405998316} {:x 183, :y 6.220932405998316} {:x 184, :y 6.220932405998316} {:x 185, :y 6.220932405998316} {:x 186, :y 6.220932405998316} {:x 187, :y 6.442049363362563} {:x 188, :y 6.442049363362563} {:x 189, :y 6.442049363362563} {:x 190, :y 6.442049363362563} {:x 191, :y 6.442049363362563} {:x 192, :y 6.442049363362563} {:x 193, :y 6.745368781616021} {:x 194, :y 6.745368781616021} {:x 195, :y 6.782329983125268} {:x 196, :y 6.610597552415364} {:x 197, :y 6.610597552415364} {:x 198, :y 6.610597552415364} {:x 199, :y 6.610597552415364} {:x 200, :y 6.519202405202649} {:x 201, :y 6.782329983125268} {:x 202, :y 6.782329983125268} {:x 203, :y 6.782329983125268} {:x 204, :y 6.760177512462229} {:x 205, :y 6.760177512462229} {:x 206, :y 6.767569726275452} {:x 207, :y 6.767569726275452} {:x 208, :y 6.58027355054484} {:x 209, :y 6.58027355054484} {:x 210, :y 6.58027355054484} {:x 211, :y 6.58027355054484} {:x 212, :y 6.58027355054484} {:x 213, :y 6.4265076052238514} {:x 214, :y 6.300793600809346} {:x 215, :y 5.932958789676531} {:x 216, :y 5.656854249492381} {:x 217, :y 5.403702434442518} {:x 218, :y 5.403702434442518} {:x 219, :y 5.630275304103699} {:x 220, :y 5.630275304103699} {:x 221, :y 5.630275304103699} {:x 222, :y 5.630275304103699} {:x 223, :y 5.244044240850758} {:x 224, :y 5.019960159204453} {:x 225, :y 5.0990195135927845} {:x 226, :y 4.919349550499537} {:x 227, :y 4.919349550499537} {:x 228, :y 5.029910535983717} {:x 229, :y 5.029910535983717} {:x 230, :y 4.878524367060187} {:x 231, :y 4.658325879540846} {:x 232, :y 4.658325879540846} {:x 233, :y 4.43846820423443} {:x 234, :y 4.43846820423443} {:x 235, :y 4.560701700396552} {:x 236, :y 4.560701700396552} {:x 237, :y 4.560701700396552} {:x 238, :y 4.43846820423443} {:x 239, :y 4.03732584763727} {:x 240, :y 4.03732584763727} {:x 241, :y 3.9749213828703582} {:x 242, :y 4.159326868617084} {:x 243, :y 3.9115214431215892} {:x 244, :y 3.7815340802378077} {:x 245, :y 3.7815340802378077} {:x 246, :y 3.847076812334269} {:x 247, :y 3.9749213828703582} {:x 248, :y 3.9749213828703582} {:x 249, :y 4.159326868617084} {:x 250, :y 4.324349662087931} {:x 251, :y 4.219004621945797} {:x 252, :y 4.43846820423443} {:x 253, :y 4.277849927241488} {:x 254, :y 4.219004621945797} {:x 255, :y 4.207136793592526} {:x 256, :y 4.207136793592526} {:x 257, :y 4.207136793592526} {:x 258, :y 3.847076812334269} {:x 259, :y 3.847076812334269} {:x 260, :y 3.847076812334269} {:x 261, :y 3.847076812334269} {:x 262, :y 3.7013511046643495} {:x 263, :y 3.8078865529319543} {:x 264, :y 3.7013511046643495} {:x 265, :y 3.7013511046643495} {:x 266, :y 3.8340579025361627} {:x 267, :y 3.8340579025361627} {:x 268, :y 4.324349662087931} {:x 269, :y 4.324349662087931} {:x 270, :y 4.43846820423443} {:x 271, :y 4.123105625617661} {:x 272, :y 4.324349662087931} {:x 273, :y 4.560701700396552} {:x 274, :y 4.560701700396552} {:x 275, :y 4.560701700396552} {:x 276, :y 4.604345773288535} {:x 277, :y 4.795831523312719} {:x 278, :y 4.795831523312719} {:x 279, :y 5.06951674225463} {:x 280, :y 5.263078946776307} {:x 281, :y 5.263078946776307} {:x 282, :y 5.291502622129181} {:x 283, :y 5.787918451395113} {:x 284, :y 5.787918451395113} {:x 285, :y 5.540758070878027} {:x 286, :y 5.540758070878027} {:x 287, :y 5.540758070878027} {:x 288, :y 5.585696017507576} {:x 289, :y 5.291502622129181} {:x 290, :y 5.06951674225463} {:x 291, :y 4.847679857416329} {:x 292, :y 4.847679857416329} {:x 293, :y 4.847679857416329} {:x 294, :y 4.847679857416329} {:x 295, :y 5.06951674225463} {:x 296, :y 4.878524367060187} {:x 297, :y 4.658325879540846} {:x 298, :y 4.207136793592526} {:x 299, :y 4.207136793592526} {:x 300, :y 4.207136793592526})}), :marks ({:type \"line\", :from {:data \"7eaba6d6-1812-4a80-9991-f82ece767baa\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :stroke {:value \"Blue\"}, :strokeWidth {:value 2}, :strokeOpacity {:value 1}}}} {:type \"line\", :from {:data \"36b24e4d-38fe-4d39-a671-e68760e6760f\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :stroke {:value \"Red\"}, :strokeWidth {:value 2}, :strokeOpacity {:value 1}}}} {:type \"line\", :from {:data \"b7025b7f-cbe1-43c3-8849-36e0f487b98a\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :stroke {:value \"Green\"}, :strokeWidth {:value 2}, :strokeOpacity {:value 1}}}})}}"}
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
