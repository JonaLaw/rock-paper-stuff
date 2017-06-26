;; gorilla-repl.fileformat = 1

;; **
;;; # Rock Paper Stuff
;; **

;; **
;;; This [Gorilla REPL](http://gorilla-repl.org) worksheet demonstrates basic features of the [Rock Paper Stuff](https://github.com/lspector/rock-paper-stuff) environment. If you scroll to the bottom you'll see a few graphs that also demonstrate Gorilla REPL's plotting features.
;;; 
;;; Here we define the namespace, pulling in names from some others:
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

;; @@
(defn rock-pf
  "A player function that always plays :rock."
  [self other-skin]
  {:play :rock})
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;rock-paper-stuff.worksheet/rock-pf</span>","value":"#'rock-paper-stuff.worksheet/rock-pf"}
;; <=

;; @@
(:winner (play-game [(player "Rocky" rock-pf)
                     (player "Roxy" rock-pf)]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-string'>&quot;Nobody&quot;</span>","value":"\"Nobody\""}
;; <=

;; @@
(defn random-pf
  "A player function that returns a random kind of stuff to play."
  [self other-skin]
  {:play (rand-nth [:rock :paper :scissors :fire :water])})
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;rock-paper-stuff.worksheet/random-pf</span>","value":"#'rock-paper-stuff.worksheet/random-pf"}
;; <=

;; @@
(:winner (play-game [(player "Rocky" rock-pf)
                     (player "Randy" random-pf)]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-string'>&quot;Rocky&quot;</span>","value":"\"Rocky\""}
;; <=

;; @@
(:summary (play-game [(player "Rocky" rock-pf)
                      (player "Randy" random-pf)]))
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:name</span>","value":":name"},{"type":"html","content":"<span class='clj-string'>&quot;Randy&quot;</span>","value":"\"Randy\""}],"value":"[:name \"Randy\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:deviance</span>","value":":deviance"},{"type":"html","content":"<span class='clj-double'>5.029910535983717</span>","value":"5.029910535983717"}],"value":"[:deviance 5.029910535983717]"}],"value":"{:name \"Randy\", :deviance 5.029910535983717}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:name</span>","value":":name"},{"type":"html","content":"<span class='clj-string'>&quot;Rocky&quot;</span>","value":"\"Rocky\""}],"value":"[:name \"Rocky\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:deviance</span>","value":":deviance"},{"type":"html","content":"<span class='clj-double'>6.48074069840786</span>","value":"6.48074069840786"}],"value":"[:deviance 6.48074069840786]"}],"value":"{:name \"Rocky\", :deviance 6.48074069840786}"}],"value":"[{:name \"Randy\", :deviance 5.029910535983717} {:name \"Rocky\", :deviance 6.48074069840786}]"}
;; <=

;; @@
(map summary
     (:players (play-game [(player "Rocky" rock-pf)
                           (player "Randy" random-pf)])))
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Rocky&quot;</span>","value":"\"Rocky\""},{"type":"html","content":"<span class='clj-double'>5.224940191045253</span>","value":"5.224940191045253"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:fire</span>","value":":fire"},{"type":"html","content":"<span class='clj-long'>13</span>","value":"13"}],"value":"[:fire 13]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:paper</span>","value":":paper"},{"type":"html","content":"<span class='clj-long'>12</span>","value":"12"}],"value":"[:paper 12]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:scissors</span>","value":":scissors"},{"type":"html","content":"<span class='clj-long'>6</span>","value":"6"}],"value":"[:scissors 6]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:water</span>","value":":water"},{"type":"html","content":"<span class='clj-long'>7</span>","value":"7"}],"value":"[:water 7]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:rock</span>","value":":rock"},{"type":"html","content":"<span class='clj-long'>0</span>","value":"0"}],"value":"[:rock 0]"}],"value":"{:fire 13, :paper 12, :scissors 6, :water 7, :rock 0}"}],"value":"[\"Rocky\" 5.224940191045253 {:fire 13, :paper 12, :scissors 6, :water 7, :rock 0}]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Randy&quot;</span>","value":"\"Randy\""},{"type":"html","content":"<span class='clj-double'>7.014271166700073</span>","value":"7.014271166700073"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:fire</span>","value":":fire"},{"type":"html","content":"<span class='clj-long'>6</span>","value":"6"}],"value":"[:fire 6]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:paper</span>","value":":paper"},{"type":"html","content":"<span class='clj-long'>8</span>","value":"8"}],"value":"[:paper 8]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:scissors</span>","value":":scissors"},{"type":"html","content":"<span class='clj-long'>11</span>","value":"11"}],"value":"[:scissors 11]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:water</span>","value":":water"},{"type":"html","content":"<span class='clj-long'>24</span>","value":"24"}],"value":"[:water 24]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:rock</span>","value":":rock"},{"type":"html","content":"<span class='clj-long'>12</span>","value":"12"}],"value":"[:rock 12]"}],"value":"{:fire 6, :paper 8, :scissors 11, :water 24, :rock 12}"}],"value":"[\"Randy\" 7.014271166700073 {:fire 6, :paper 8, :scissors 11, :water 24, :rock 12}]"}],"value":"([\"Rocky\" 5.224940191045253 {:fire 13, :paper 12, :scissors 6, :water 7, :rock 0}] [\"Randy\" 7.014271166700073 {:fire 6, :paper 8, :scissors 11, :water 24, :rock 12}])"}
;; <=

;; @@

;; @@

;; @@

;; @@

;; @@

;; @@

;; @@

;; @@

;; **
;;; Now let's play a game with two players who use the `random-pf` player function, which is defined (along with other player functions used in the examples below) in the `rock-paper-stuff.player-functions` namespace. 
;;; 
;;; We'll see the full result, which is *big and messy* because it includes each player's full history, and it's printed in a way that's not intended for human consumption. Don't worry about the details in this result now; just scroll past it to look at the examples that follow. We'll see nicer ways to display game results (or usually just parts of them) further below:
;; **

;; @@
(dissoc (play-game [(player "Randy" random-pf)
                    (player "Sandy" random-pf)])
        :global-history 
        :players)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:survivors</span>","value":":survivors"},{"type":"html","content":"<span class='clj-unkown'>2</span>","value":"2"}],"value":"[:survivors 2]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:winner</span>","value":":winner"},{"type":"html","content":"<span class='clj-string'>&quot;Randy&quot;</span>","value":"\"Randy\""}],"value":"[:winner \"Randy\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:summary</span>","value":":summary"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:name</span>","value":":name"},{"type":"html","content":"<span class='clj-string'>&quot;Randy&quot;</span>","value":"\"Randy\""}],"value":"[:name \"Randy\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:deviance</span>","value":":deviance"},{"type":"html","content":"<span class='clj-double'>1.9235384061671346</span>","value":"1.9235384061671346"}],"value":"[:deviance 1.9235384061671346]"}],"value":"{:name \"Randy\", :deviance 1.9235384061671346}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:name</span>","value":":name"},{"type":"html","content":"<span class='clj-string'>&quot;Sandy&quot;</span>","value":"\"Sandy\""}],"value":"[:name \"Sandy\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:deviance</span>","value":":deviance"},{"type":"html","content":"<span class='clj-double'>3.1144823004794873</span>","value":"3.1144823004794873"}],"value":"[:deviance 3.1144823004794873]"}],"value":"{:name \"Sandy\", :deviance 3.1144823004794873}"}],"value":"[{:name \"Randy\", :deviance 1.9235384061671346} {:name \"Sandy\", :deviance 3.1144823004794873}]"}],"value":"[:summary [{:name \"Randy\", :deviance 1.9235384061671346} {:name \"Sandy\", :deviance 3.1144823004794873}]]"}],"value":"{:survivors 2, :winner \"Randy\", :summary [{:name \"Randy\", :deviance 1.9235384061671346} {:name \"Sandy\", :deviance 3.1144823004794873}]}"}
;; <=

;; **
;;; Here's a game result in a more human-readable (but still long!) form, obtained by wrapping the call to `play-game` in a call to the "pretty printing" function `pprint`:
;; **

;; @@
(pprint (dissoc (play-game [(player "Randy" random-pf)
                            (player "Sandy" random-pf)])
                :global-history 
                :players))
;; @@
;; ->
;;; {:survivors 2,
;;;  :winner &quot;Randy&quot;,
;;;  :summary
;;;  [{:name &quot;Randy&quot;, :deviance 4.722287581247038}
;;;   {:name &quot;Sandy&quot;, :deviance 5.718391382198319}]}
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; Often we'll just want to look at the summary of a game:
;; **

;; @@
(:summary (play-game [(player "Randy" random-pf)
                      (player "Sandy" random-pf)]))
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:name</span>","value":":name"},{"type":"html","content":"<span class='clj-string'>&quot;Sandy&quot;</span>","value":"\"Sandy\""}],"value":"[:name \"Sandy\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:deviance</span>","value":":deviance"},{"type":"html","content":"<span class='clj-double'>4.219004621945797</span>","value":"4.219004621945797"}],"value":"[:deviance 4.219004621945797]"}],"value":"{:name \"Sandy\", :deviance 4.219004621945797}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:name</span>","value":":name"},{"type":"html","content":"<span class='clj-string'>&quot;Randy&quot;</span>","value":"\"Randy\""}],"value":"[:name \"Randy\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:deviance</span>","value":":deviance"},{"type":"html","content":"<span class='clj-double'>4.969909455915671</span>","value":"4.969909455915671"}],"value":"[:deviance 4.969909455915671]"}],"value":"{:name \"Randy\", :deviance 4.969909455915671}"}],"value":"[{:name \"Sandy\", :deviance 4.219004621945797} {:name \"Randy\", :deviance 4.969909455915671}]"}
;; <=

;; **
;;; Adding an extra argument of `:print` causes every trade to be printed as it happens, along with inventories before each trade:
;; **

;; @@
(:summary (play-game [(player "Randy" random-pf)
                      (player "Sandy" random-pf)]
                     :print))
;; @@
;; ->
;;;      Randy inventory: {:fire 10, :paper 10, :scissors 10, :water 10, :rock 10}
;;;      Sandy inventory: {:fire 10, :paper 10, :scissors 10, :water 10, :rock 10}
;;; Randy plays :paper  -  Sandy plays :scissors
;;;      Randy inventory: {:fire 10, :paper 11, :scissors 10, :water 10, :rock 10}
;;;      Sandy inventory: {:fire 10, :paper 10, :scissors 10, :water 10, :rock 10}
;;; Randy plays :water  -  Sandy plays :scissors
;;;      Randy inventory: {:fire 10, :paper 11, :scissors 10, :water 9, :rock 11}
;;;      Sandy inventory: {:fire 10, :paper 10, :scissors 9, :water 11, :rock 10}
;;; Sandy plays :rock  -  Randy plays :rock
;;;      Randy inventory: {:fire 10, :paper 11, :scissors 10, :water 9, :rock 10}
;;;      Sandy inventory: {:fire 10, :paper 10, :scissors 9, :water 11, :rock 11}
;;; Randy plays :rock  -  Sandy plays :rock
;;;      Randy inventory: {:fire 10, :paper 11, :scissors 10, :water 9, :rock 9}
;;;      Sandy inventory: {:fire 10, :paper 10, :scissors 9, :water 11, :rock 12}
;;; Sandy plays :water  -  Randy plays :scissors
;;;      Randy inventory: {:fire 10, :paper 11, :scissors 9, :water 10, :rock 9}
;;;      Sandy inventory: {:fire 10, :paper 10, :scissors 9, :water 10, :rock 13}
;;; Sandy plays :fire  -  Randy plays :rock
;;;      Randy inventory: {:fire 10, :paper 11, :scissors 9, :water 10, :rock 9}
;;;      Sandy inventory: {:fire 9, :paper 10, :scissors 10, :water 10, :rock 13}
;;; Sandy plays :rock  -  Randy plays :water
;;;      Randy inventory: {:fire 10, :paper 11, :scissors 9, :water 11, :rock 9}
;;;      Sandy inventory: {:fire 9, :paper 10, :scissors 10, :water 10, :rock 12}
;;; Sandy plays :water  -  Randy plays :scissors
;;;      Randy inventory: {:fire 10, :paper 11, :scissors 8, :water 12, :rock 9}
;;;      Sandy inventory: {:fire 9, :paper 10, :scissors 10, :water 9, :rock 13}
;;; Sandy plays :fire  -  Randy plays :paper
;;;      Randy inventory: {:fire 10, :paper 10, :scissors 8, :water 12, :rock 9}
;;;      Sandy inventory: {:fire 10, :paper 10, :scissors 10, :water 9, :rock 13}
;;; Randy plays :scissors  -  Sandy plays :scissors
;;;      Randy inventory: {:fire 10, :paper 10, :scissors 9, :water 12, :rock 9}
;;;      Sandy inventory: {:fire 10, :paper 10, :scissors 9, :water 9, :rock 13}
;;; Randy plays :water  -  Sandy plays :paper
;;;      Randy inventory: {:fire 10, :paper 10, :scissors 9, :water 11, :rock 9}
;;;      Sandy inventory: {:fire 10, :paper 10, :scissors 9, :water 9, :rock 13}
;;; Sandy plays :scissors  -  Randy plays :scissors
;;;      Randy inventory: {:fire 10, :paper 10, :scissors 8, :water 11, :rock 9}
;;;      Sandy inventory: {:fire 10, :paper 10, :scissors 10, :water 9, :rock 13}
;;; Sandy plays :fire  -  Randy plays :paper
;;;      Randy inventory: {:fire 10, :paper 9, :scissors 8, :water 11, :rock 9}
;;;      Sandy inventory: {:fire 11, :paper 10, :scissors 10, :water 9, :rock 13}
;;; Sandy plays :scissors  -  Randy plays :rock
;;;      Randy inventory: {:fire 11, :paper 9, :scissors 8, :water 11, :rock 8}
;;;      Sandy inventory: {:fire 11, :paper 10, :scissors 9, :water 9, :rock 14}
;;; Sandy plays :fire  -  Randy plays :paper
;;;      Randy inventory: {:fire 11, :paper 8, :scissors 8, :water 11, :rock 8}
;;;      Sandy inventory: {:fire 12, :paper 10, :scissors 9, :water 9, :rock 14}
;;; Sandy plays :fire  -  Randy plays :paper
;;;      Randy inventory: {:fire 11, :paper 7, :scissors 8, :water 11, :rock 8}
;;;      Sandy inventory: {:fire 13, :paper 10, :scissors 9, :water 9, :rock 14}
;;; Randy plays :rock  -  Sandy plays :fire
;;;      Randy inventory: {:fire 11, :paper 7, :scissors 8, :water 11, :rock 8}
;;;      Sandy inventory: {:fire 12, :paper 10, :scissors 10, :water 9, :rock 14}
;;; Sandy plays :scissors  -  Randy plays :paper
;;;      Randy inventory: {:fire 11, :paper 8, :scissors 8, :water 11, :rock 8}
;;;      Sandy inventory: {:fire 12, :paper 10, :scissors 10, :water 9, :rock 14}
;;; Sandy plays :water  -  Randy plays :fire
;;;      Randy inventory: {:fire 10, :paper 8, :scissors 8, :water 11, :rock 8}
;;;      Sandy inventory: {:fire 12, :paper 10, :scissors 10, :water 9, :rock 14}
;;; Sandy plays :water  -  Randy plays :fire
;;;      Randy inventory: {:fire 9, :paper 8, :scissors 8, :water 11, :rock 8}
;;;      Sandy inventory: {:fire 12, :paper 10, :scissors 10, :water 9, :rock 14}
;;; Randy plays :fire  -  Sandy plays :paper
;;;      Randy inventory: {:fire 10, :paper 8, :scissors 8, :water 11, :rock 8}
;;;      Sandy inventory: {:fire 12, :paper 9, :scissors 10, :water 9, :rock 14}
;;; Randy plays :paper  -  Sandy plays :paper
;;;      Randy inventory: {:fire 10, :paper 9, :scissors 8, :water 11, :rock 8}
;;;      Sandy inventory: {:fire 12, :paper 8, :scissors 10, :water 9, :rock 14}
;;; Randy plays :paper  -  Sandy plays :fire
;;;      Randy inventory: {:fire 10, :paper 8, :scissors 8, :water 11, :rock 8}
;;;      Sandy inventory: {:fire 13, :paper 8, :scissors 10, :water 9, :rock 14}
;;; Sandy plays :rock  -  Randy plays :paper
;;;      Randy inventory: {:fire 10, :paper 7, :scissors 8, :water 11, :rock 9}
;;;      Sandy inventory: {:fire 13, :paper 9, :scissors 10, :water 9, :rock 13}
;;; Sandy plays :water  -  Randy plays :water
;;;      Randy inventory: {:fire 10, :paper 7, :scissors 8, :water 10, :rock 9}
;;;      Sandy inventory: {:fire 13, :paper 9, :scissors 10, :water 10, :rock 13}
;;; Randy plays :rock  -  Sandy plays :rock
;;;      Randy inventory: {:fire 10, :paper 7, :scissors 8, :water 10, :rock 10}
;;;      Sandy inventory: {:fire 13, :paper 9, :scissors 10, :water 10, :rock 12}
;;; Sandy plays :rock  -  Randy plays :rock
;;;      Randy inventory: {:fire 10, :paper 7, :scissors 8, :water 10, :rock 11}
;;;      Sandy inventory: {:fire 13, :paper 9, :scissors 10, :water 10, :rock 11}
;;; Randy plays :scissors  -  Sandy plays :paper
;;;      Randy inventory: {:fire 10, :paper 7, :scissors 8, :water 10, :rock 11}
;;;      Sandy inventory: {:fire 13, :paper 10, :scissors 10, :water 10, :rock 11}
;;; Sandy plays :paper  -  Randy plays :scissors
;;;      Randy inventory: {:fire 10, :paper 7, :scissors 8, :water 10, :rock 11}
;;;      Sandy inventory: {:fire 13, :paper 11, :scissors 10, :water 10, :rock 11}
;;; Sandy plays :paper  -  Randy plays :paper
;;;      Randy inventory: {:fire 10, :paper 6, :scissors 8, :water 10, :rock 11}
;;;      Sandy inventory: {:fire 13, :paper 12, :scissors 10, :water 10, :rock 11}
;;; Sandy plays :water  -  Randy plays :water
;;;      Randy inventory: {:fire 10, :paper 6, :scissors 8, :water 9, :rock 11}
;;;      Sandy inventory: {:fire 13, :paper 12, :scissors 10, :water 11, :rock 11}
;;; Randy plays :water  -  Sandy plays :water
;;;      Randy inventory: {:fire 10, :paper 6, :scissors 8, :water 8, :rock 11}
;;;      Sandy inventory: {:fire 13, :paper 12, :scissors 10, :water 12, :rock 11}
;;; Randy plays :fire  -  Sandy plays :paper
;;;      Randy inventory: {:fire 11, :paper 6, :scissors 8, :water 8, :rock 11}
;;;      Sandy inventory: {:fire 13, :paper 11, :scissors 10, :water 12, :rock 11}
;;; Randy plays :paper  -  Sandy plays :rock
;;;      Randy inventory: {:fire 11, :paper 5, :scissors 8, :water 8, :rock 12}
;;;      Sandy inventory: {:fire 13, :paper 12, :scissors 10, :water 12, :rock 10}
;;; Randy plays :rock  -  Sandy plays :fire
;;;      Randy inventory: {:fire 11, :paper 5, :scissors 8, :water 8, :rock 12}
;;;      Sandy inventory: {:fire 12, :paper 12, :scissors 11, :water 12, :rock 10}
;;; Sandy plays :scissors  -  Randy plays :rock
;;;      Randy inventory: {:fire 12, :paper 5, :scissors 8, :water 8, :rock 11}
;;;      Sandy inventory: {:fire 12, :paper 12, :scissors 10, :water 12, :rock 11}
;;; Sandy plays :paper  -  Randy plays :water
;;;      Randy inventory: {:fire 12, :paper 5, :scissors 8, :water 7, :rock 11}
;;;      Sandy inventory: {:fire 12, :paper 12, :scissors 10, :water 12, :rock 11}
;;; Sandy plays :water  -  Randy plays :water
;;;      Randy inventory: {:fire 12, :paper 5, :scissors 8, :water 8, :rock 11}
;;;      Sandy inventory: {:fire 12, :paper 12, :scissors 10, :water 11, :rock 11}
;;; Randy plays :rock  -  Sandy plays :paper
;;;      Randy inventory: {:fire 12, :paper 6, :scissors 8, :water 8, :rock 10}
;;;      Sandy inventory: {:fire 12, :paper 11, :scissors 10, :water 11, :rock 12}
;;; Sandy plays :water  -  Randy plays :scissors
;;;      Randy inventory: {:fire 12, :paper 6, :scissors 7, :water 9, :rock 10}
;;;      Sandy inventory: {:fire 12, :paper 11, :scissors 10, :water 10, :rock 13}
;;; Randy plays :paper  -  Sandy plays :paper
;;;      Randy inventory: {:fire 12, :paper 7, :scissors 7, :water 9, :rock 10}
;;;      Sandy inventory: {:fire 12, :paper 10, :scissors 10, :water 10, :rock 13}
;;; Sandy plays :fire  -  Randy plays :scissors
;;;      Randy inventory: {:fire 12, :paper 7, :scissors 8, :water 9, :rock 10}
;;;      Sandy inventory: {:fire 12, :paper 10, :scissors 10, :water 10, :rock 13}
;;; Sandy plays :scissors  -  Randy plays :scissors
;;;      Randy inventory: {:fire 12, :paper 7, :scissors 9, :water 9, :rock 10}
;;;      Sandy inventory: {:fire 12, :paper 10, :scissors 9, :water 10, :rock 13}
;;; Sandy plays :rock  -  Randy plays :scissors
;;;      Randy inventory: {:fire 12, :paper 7, :scissors 8, :water 9, :rock 11}
;;;      Sandy inventory: {:fire 13, :paper 10, :scissors 9, :water 10, :rock 12}
;;; Randy plays :rock  -  Sandy plays :rock
;;;      Randy inventory: {:fire 12, :paper 7, :scissors 8, :water 9, :rock 12}
;;;      Sandy inventory: {:fire 13, :paper 10, :scissors 9, :water 10, :rock 11}
;;; Sandy plays :scissors  -  Randy plays :fire
;;;      Randy inventory: {:fire 12, :paper 7, :scissors 8, :water 9, :rock 12}
;;;      Sandy inventory: {:fire 13, :paper 10, :scissors 10, :water 10, :rock 11}
;;; Sandy plays :water  -  Randy plays :rock
;;;      Randy inventory: {:fire 12, :paper 7, :scissors 8, :water 9, :rock 11}
;;;      Sandy inventory: {:fire 13, :paper 10, :scissors 10, :water 11, :rock 11}
;;; Sandy plays :paper  -  Randy plays :rock
;;;      Randy inventory: {:fire 12, :paper 8, :scissors 8, :water 9, :rock 10}
;;;      Sandy inventory: {:fire 13, :paper 9, :scissors 10, :water 11, :rock 12}
;;; Randy plays :paper  -  Sandy plays :paper
;;;      Randy inventory: {:fire 12, :paper 7, :scissors 8, :water 9, :rock 10}
;;;      Sandy inventory: {:fire 13, :paper 10, :scissors 10, :water 11, :rock 12}
;;; Sandy plays :paper  -  Randy plays :rock
;;;      Randy inventory: {:fire 12, :paper 8, :scissors 8, :water 9, :rock 9}
;;;      Sandy inventory: {:fire 13, :paper 9, :scissors 10, :water 11, :rock 13}
;;; Randy plays :fire  -  Sandy plays :water
;;;      Randy inventory: {:fire 11, :paper 8, :scissors 8, :water 9, :rock 9}
;;;      Sandy inventory: {:fire 13, :paper 9, :scissors 10, :water 11, :rock 13}
;;; Sandy plays :rock  -  Randy plays :paper
;;;      Randy inventory: {:fire 11, :paper 7, :scissors 8, :water 9, :rock 10}
;;;      Sandy inventory: {:fire 13, :paper 10, :scissors 10, :water 11, :rock 12}
;;; Randy plays :water  -  Sandy plays :water
;;;      Randy inventory: {:fire 11, :paper 7, :scissors 8, :water 10, :rock 10}
;;;      Sandy inventory: {:fire 13, :paper 10, :scissors 10, :water 10, :rock 12}
;;; Sandy plays :scissors  -  Randy plays :scissors
;;;      Randy inventory: {:fire 11, :paper 7, :scissors 9, :water 10, :rock 10}
;;;      Sandy inventory: {:fire 13, :paper 10, :scissors 9, :water 10, :rock 12}
;;; Sandy plays :rock  -  Randy plays :paper
;;;      Randy inventory: {:fire 11, :paper 6, :scissors 9, :water 10, :rock 11}
;;;      Sandy inventory: {:fire 13, :paper 11, :scissors 9, :water 10, :rock 11}
;;; Sandy plays :scissors  -  Randy plays :fire
;;;      Randy inventory: {:fire 11, :paper 6, :scissors 9, :water 10, :rock 11}
;;;      Sandy inventory: {:fire 13, :paper 11, :scissors 10, :water 10, :rock 11}
;;; Sandy plays :scissors  -  Randy plays :fire
;;;      Randy inventory: {:fire 11, :paper 6, :scissors 9, :water 10, :rock 11}
;;;      Sandy inventory: {:fire 13, :paper 11, :scissors 11, :water 10, :rock 11}
;;; Sandy plays :paper  -  Randy plays :fire
;;;      Randy inventory: {:fire 12, :paper 6, :scissors 9, :water 10, :rock 11}
;;;      Sandy inventory: {:fire 13, :paper 10, :scissors 11, :water 10, :rock 11}
;;; Sandy plays :water  -  Randy plays :fire
;;;      Randy inventory: {:fire 11, :paper 6, :scissors 9, :water 10, :rock 11}
;;;      Sandy inventory: {:fire 13, :paper 10, :scissors 11, :water 10, :rock 11}
;;; Randy plays :rock  -  Sandy plays :scissors
;;;      Randy inventory: {:fire 12, :paper 6, :scissors 9, :water 10, :rock 10}
;;;      Sandy inventory: {:fire 13, :paper 10, :scissors 10, :water 10, :rock 12}
;;; Randy plays :water  -  Sandy plays :fire
;;;      Randy inventory: {:fire 12, :paper 6, :scissors 9, :water 10, :rock 10}
;;;      Sandy inventory: {:fire 12, :paper 10, :scissors 10, :water 10, :rock 12}
;;; Randy plays :paper  -  Sandy plays :rock
;;;      Randy inventory: {:fire 12, :paper 5, :scissors 9, :water 10, :rock 11}
;;;      Sandy inventory: {:fire 12, :paper 11, :scissors 10, :water 10, :rock 11}
;;; Sandy plays :paper  -  Randy plays :water
;;;      Randy inventory: {:fire 12, :paper 5, :scissors 9, :water 9, :rock 11}
;;;      Sandy inventory: {:fire 12, :paper 11, :scissors 10, :water 10, :rock 11}
;;; Sandy plays :rock  -  Randy plays :scissors
;;;      Randy inventory: {:fire 12, :paper 5, :scissors 8, :water 9, :rock 12}
;;;      Sandy inventory: {:fire 13, :paper 11, :scissors 10, :water 10, :rock 10}
;;; Randy plays :rock  -  Sandy plays :scissors
;;;      Randy inventory: {:fire 13, :paper 5, :scissors 8, :water 9, :rock 11}
;;;      Sandy inventory: {:fire 13, :paper 11, :scissors 9, :water 10, :rock 11}
;;; Sandy plays :rock  -  Randy plays :fire
;;;      Randy inventory: {:fire 12, :paper 5, :scissors 9, :water 9, :rock 11}
;;;      Sandy inventory: {:fire 13, :paper 11, :scissors 9, :water 10, :rock 11}
;;; Sandy plays :water  -  Randy plays :fire
;;;      Randy inventory: {:fire 11, :paper 5, :scissors 9, :water 9, :rock 11}
;;;      Sandy inventory: {:fire 13, :paper 11, :scissors 9, :water 10, :rock 11}
;;; Sandy plays :water  -  Randy plays :fire
;;;      Randy inventory: {:fire 10, :paper 5, :scissors 9, :water 9, :rock 11}
;;;      Sandy inventory: {:fire 13, :paper 11, :scissors 9, :water 10, :rock 11}
;;; Randy plays :paper  -  Sandy plays :fire
;;;      Randy inventory: {:fire 10, :paper 4, :scissors 9, :water 9, :rock 11}
;;;      Sandy inventory: {:fire 14, :paper 11, :scissors 9, :water 10, :rock 11}
;;; Randy plays :rock  -  Sandy plays :paper
;;;      Randy inventory: {:fire 10, :paper 5, :scissors 9, :water 9, :rock 10}
;;;      Sandy inventory: {:fire 14, :paper 10, :scissors 9, :water 10, :rock 12}
;;; Sandy plays :water  -  Randy plays :water
;;;      Randy inventory: {:fire 10, :paper 5, :scissors 9, :water 10, :rock 10}
;;;      Sandy inventory: {:fire 14, :paper 10, :scissors 9, :water 9, :rock 12}
;;; Randy plays :water  -  Sandy plays :paper
;;;      Randy inventory: {:fire 10, :paper 5, :scissors 9, :water 9, :rock 10}
;;;      Sandy inventory: {:fire 14, :paper 10, :scissors 9, :water 9, :rock 12}
;;; Randy plays :scissors  -  Sandy plays :fire
;;;      Randy inventory: {:fire 10, :paper 5, :scissors 10, :water 9, :rock 10}
;;;      Sandy inventory: {:fire 14, :paper 10, :scissors 9, :water 9, :rock 12}
;;; Sandy plays :paper  -  Randy plays :paper
;;;      Randy inventory: {:fire 10, :paper 4, :scissors 10, :water 9, :rock 10}
;;;      Sandy inventory: {:fire 14, :paper 11, :scissors 9, :water 9, :rock 12}
;;; Sandy plays :water  -  Randy plays :water
;;;      Randy inventory: {:fire 10, :paper 4, :scissors 10, :water 8, :rock 10}
;;;      Sandy inventory: {:fire 14, :paper 11, :scissors 9, :water 10, :rock 12}
;;; Randy plays :rock  -  Sandy plays :water
;;;      Randy inventory: {:fire 10, :paper 4, :scissors 10, :water 8, :rock 9}
;;;      Sandy inventory: {:fire 14, :paper 11, :scissors 9, :water 11, :rock 12}
;;; Sandy plays :paper  -  Randy plays :water
;;;      Randy inventory: {:fire 10, :paper 4, :scissors 10, :water 7, :rock 9}
;;;      Sandy inventory: {:fire 14, :paper 11, :scissors 9, :water 11, :rock 12}
;;; Sandy plays :water  -  Randy plays :water
;;;      Randy inventory: {:fire 10, :paper 4, :scissors 10, :water 6, :rock 9}
;;;      Sandy inventory: {:fire 14, :paper 11, :scissors 9, :water 12, :rock 12}
;;; Randy plays :rock  -  Sandy plays :water
;;;      Randy inventory: {:fire 10, :paper 4, :scissors 10, :water 6, :rock 8}
;;;      Sandy inventory: {:fire 14, :paper 11, :scissors 9, :water 13, :rock 12}
;;; Sandy plays :water  -  Randy plays :scissors
;;;      Randy inventory: {:fire 10, :paper 4, :scissors 9, :water 7, :rock 8}
;;;      Sandy inventory: {:fire 14, :paper 11, :scissors 9, :water 12, :rock 13}
;;; Sandy plays :fire  -  Randy plays :rock
;;;      Randy inventory: {:fire 10, :paper 4, :scissors 9, :water 7, :rock 8}
;;;      Sandy inventory: {:fire 13, :paper 11, :scissors 10, :water 12, :rock 13}
;;; Randy plays :water  -  Sandy plays :rock
;;;      Randy inventory: {:fire 10, :paper 4, :scissors 9, :water 8, :rock 8}
;;;      Sandy inventory: {:fire 13, :paper 11, :scissors 10, :water 12, :rock 12}
;;; Sandy plays :rock  -  Randy plays :water
;;;      Randy inventory: {:fire 10, :paper 4, :scissors 9, :water 9, :rock 8}
;;;      Sandy inventory: {:fire 13, :paper 11, :scissors 10, :water 12, :rock 11}
;;; Randy plays :fire  -  Sandy plays :paper
;;;      Randy inventory: {:fire 11, :paper 4, :scissors 9, :water 9, :rock 8}
;;;      Sandy inventory: {:fire 13, :paper 10, :scissors 10, :water 12, :rock 11}
;;; Sandy plays :water  -  Randy plays :paper
;;;      Randy inventory: {:fire 11, :paper 4, :scissors 9, :water 9, :rock 8}
;;;      Sandy inventory: {:fire 13, :paper 10, :scissors 10, :water 11, :rock 11}
;;; Randy plays :scissors  -  Sandy plays :water
;;;      Randy inventory: {:fire 11, :paper 4, :scissors 8, :water 10, :rock 8}
;;;      Sandy inventory: {:fire 13, :paper 10, :scissors 10, :water 10, :rock 12}
;;; Randy plays :fire  -  Sandy plays :fire
;;;      Randy inventory: {:fire 10, :paper 4, :scissors 8, :water 10, :rock 8}
;;;      Sandy inventory: {:fire 14, :paper 10, :scissors 10, :water 10, :rock 12}
;;; Sandy plays :scissors  -  Randy plays :rock
;;;      Randy inventory: {:fire 11, :paper 4, :scissors 8, :water 10, :rock 7}
;;;      Sandy inventory: {:fire 14, :paper 10, :scissors 9, :water 10, :rock 13}
;;; Sandy plays :rock  -  Randy plays :rock
;;;      Randy inventory: {:fire 11, :paper 4, :scissors 8, :water 10, :rock 6}
;;;      Sandy inventory: {:fire 14, :paper 10, :scissors 9, :water 10, :rock 14}
;;; Sandy plays :water  -  Randy plays :water
;;;      Randy inventory: {:fire 11, :paper 4, :scissors 8, :water 9, :rock 6}
;;;      Sandy inventory: {:fire 14, :paper 10, :scissors 9, :water 11, :rock 14}
;;; Randy plays :paper  -  Sandy plays :scissors
;;;      Randy inventory: {:fire 11, :paper 5, :scissors 8, :water 9, :rock 6}
;;;      Sandy inventory: {:fire 14, :paper 10, :scissors 9, :water 11, :rock 14}
;;; Randy plays :water  -  Sandy plays :rock
;;;      Randy inventory: {:fire 11, :paper 5, :scissors 8, :water 10, :rock 6}
;;;      Sandy inventory: {:fire 14, :paper 10, :scissors 9, :water 11, :rock 13}
;;; Randy plays :water  -  Sandy plays :scissors
;;;      Randy inventory: {:fire 11, :paper 5, :scissors 8, :water 9, :rock 7}
;;;      Sandy inventory: {:fire 14, :paper 10, :scissors 8, :water 12, :rock 13}
;;; Sandy plays :scissors  -  Randy plays :rock
;;;      Randy inventory: {:fire 12, :paper 5, :scissors 8, :water 9, :rock 6}
;;;      Sandy inventory: {:fire 14, :paper 10, :scissors 7, :water 12, :rock 14}
;;; Sandy plays :fire  -  Randy plays :scissors
;;;      Randy inventory: {:fire 12, :paper 5, :scissors 9, :water 9, :rock 6}
;;;      Sandy inventory: {:fire 14, :paper 10, :scissors 7, :water 12, :rock 14}
;;; Sandy plays :scissors  -  Randy plays :paper
;;;      Randy inventory: {:fire 12, :paper 6, :scissors 9, :water 9, :rock 6}
;;;      Sandy inventory: {:fire 14, :paper 10, :scissors 7, :water 12, :rock 14}
;;; Sandy plays :paper  -  Randy plays :fire
;;;      Randy inventory: {:fire 13, :paper 6, :scissors 9, :water 9, :rock 6}
;;;      Sandy inventory: {:fire 14, :paper 9, :scissors 7, :water 12, :rock 14}
;;; Sandy plays :rock  -  Randy plays :rock
;;;      Randy inventory: {:fire 13, :paper 6, :scissors 9, :water 9, :rock 7}
;;;      Sandy inventory: {:fire 14, :paper 9, :scissors 7, :water 12, :rock 13}
;;; Randy plays :rock  -  Sandy plays :rock
;;;      Randy inventory: {:fire 13, :paper 6, :scissors 9, :water 9, :rock 8}
;;;      Sandy inventory: {:fire 14, :paper 9, :scissors 7, :water 12, :rock 12}
;;; Sandy plays :water  -  Randy plays :scissors
;;;      Randy inventory: {:fire 13, :paper 6, :scissors 8, :water 10, :rock 8}
;;;      Sandy inventory: {:fire 14, :paper 9, :scissors 7, :water 11, :rock 13}
;;; 
;; <-
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:name</span>","value":":name"},{"type":"html","content":"<span class='clj-string'>&quot;Randy&quot;</span>","value":"\"Randy\""}],"value":"[:name \"Randy\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:deviance</span>","value":":deviance"},{"type":"html","content":"<span class='clj-double'>2.6457513110645907</span>","value":"2.6457513110645907"}],"value":"[:deviance 2.6457513110645907]"}],"value":"{:name \"Randy\", :deviance 2.6457513110645907}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:name</span>","value":":name"},{"type":"html","content":"<span class='clj-string'>&quot;Sandy&quot;</span>","value":"\"Sandy\""}],"value":"[:name \"Sandy\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:deviance</span>","value":":deviance"},{"type":"html","content":"<span class='clj-double'>2.8635642126552705</span>","value":"2.8635642126552705"}],"value":"[:deviance 2.8635642126552705]"}],"value":"{:name \"Sandy\", :deviance 2.8635642126552705}"}],"value":"[{:name \"Randy\", :deviance 2.6457513110645907} {:name \"Sandy\", :deviance 2.8635642126552705}]"}
;; <=

;; **
;;; Here's a game with four players:
;; **

;; @@
(:summary (play-game [(player "Randy" random-pf) 
                      (player "Sandy" random-pf) 
                      (player "Rocky" rock-pf) 
                      (player "Wally" water-pf)]))
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:name</span>","value":":name"},{"type":"html","content":"<span class='clj-string'>&quot;Randy&quot;</span>","value":"\"Randy\""}],"value":"[:name \"Randy\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:deviance</span>","value":":deviance"},{"type":"html","content":"<span class='clj-double'>10.099504938362077</span>","value":"10.099504938362077"}],"value":"[:deviance 10.099504938362077]"}],"value":"{:name \"Randy\", :deviance 10.099504938362077}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:name</span>","value":":name"},{"type":"html","content":"<span class='clj-string'>&quot;Rocky&quot;</span>","value":"\"Rocky\""}],"value":"[:name \"Rocky\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:deviance</span>","value":":deviance"},{"type":"html","content":"<span class='clj-double'>12.095453691366853</span>","value":"12.095453691366853"}],"value":"[:deviance 12.095453691366853]"}],"value":"{:name \"Rocky\", :deviance 12.095453691366853}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:name</span>","value":":name"},{"type":"html","content":"<span class='clj-string'>&quot;Sandy&quot;</span>","value":"\"Sandy\""}],"value":"[:name \"Sandy\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:deviance</span>","value":":deviance"},{"type":"html","content":"<span class='clj-double'>12.095453691366853</span>","value":"12.095453691366853"}],"value":"[:deviance 12.095453691366853]"}],"value":"{:name \"Sandy\", :deviance 12.095453691366853}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:name</span>","value":":name"},{"type":"html","content":"<span class='clj-string'>&quot;Wally&quot;</span>","value":"\"Wally\""}],"value":"[:name \"Wally\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:deviance</span>","value":":deviance"},{"type":"html","content":"<span class='clj-double'>21.087911228948208</span>","value":"21.087911228948208"}],"value":"[:deviance 21.087911228948208]"}],"value":"{:name \"Wally\", :deviance 21.087911228948208}"}],"value":"[{:name \"Randy\", :deviance 10.099504938362077} {:name \"Rocky\", :deviance 12.095453691366853} {:name \"Sandy\", :deviance 12.095453691366853} {:name \"Wally\", :deviance 21.087911228948208}]"}
;; <=

;; **
;;; Game summaries can also be pretty-printed:
;; **

;; @@
(pprint (:summary (play-game [(player "Randy" random-pf) 
                              (player "Sandy" random-pf) 
                              (player "Rocky" rock-pf) 
                              (player "Wally" water-pf)])))
;; @@
;; ->
;;; [{:name &quot;Sandy&quot;, :deviance 10.295630140987}
;;;  {:name &quot;Randy&quot;, :deviance 15.690761613127643}
;;;  {:name &quot;Rocky&quot;, :deviance 17.58408371226661}
;;;  {:name &quot;Wally&quot;, :deviance 19.50128200913981}]
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; In addition to playing single games, we can play tournaments of many games among the same players. The `tournament` function returns a sequence of pairs, each of which contains a player name and the number of games won by that player in the tournament. The sequence is sorted, with the winningest player listed first. Here's a tournament of 100 games among nine players (which will take a little while to run, since there will be 3600 trades per game, or 360000 trades total):
;; **

;; @@
(tournament 100
            [(player "Randy" random-pf) 
             (player "Rocky" rock-pf) 
             (player "Pappy" paper-pf)
             (player "Suzy" scissors-pf)
             (player "Fido" fire-pf)
             (player "Wally" water-pf)
             (player "Margie" majority-pf)
             (player "Minnie" minority-pf)
             (player "Harry" water-hoarder-pf)
             (player "Maggy" margie-hater-pf)])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Margie&quot;</span>","value":"\"Margie\""},{"type":"html","content":"<span class='clj-long'>54</span>","value":"54"}],"value":"[\"Margie\" 54]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Maggy&quot;</span>","value":"\"Maggy\""},{"type":"html","content":"<span class='clj-long'>19</span>","value":"19"}],"value":"[\"Maggy\" 19]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Harry&quot;</span>","value":"\"Harry\""},{"type":"html","content":"<span class='clj-long'>7</span>","value":"7"}],"value":"[\"Harry\" 7]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Randy&quot;</span>","value":"\"Randy\""},{"type":"html","content":"<span class='clj-long'>7</span>","value":"7"}],"value":"[\"Randy\" 7]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Pappy&quot;</span>","value":"\"Pappy\""},{"type":"html","content":"<span class='clj-long'>6</span>","value":"6"}],"value":"[\"Pappy\" 6]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Minnie&quot;</span>","value":"\"Minnie\""},{"type":"html","content":"<span class='clj-long'>4</span>","value":"4"}],"value":"[\"Minnie\" 4]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Wally&quot;</span>","value":"\"Wally\""},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"}],"value":"[\"Wally\" 2]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Fido&quot;</span>","value":"\"Fido\""},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[\"Fido\" 1]"}],"value":"([\"Margie\" 54] [\"Maggy\" 19] [\"Harry\" 7] [\"Randy\" 7] [\"Pappy\" 6] [\"Minnie\" 4] [\"Wally\" 2] [\"Fido\" 1])"}
;; <=

;; **
;;; For games that have no single winner, the win is credited to "Nobody":
;; **

;; @@
(tournament 100
            [(player "Margie" majority-pf)
             (player "Pappy" paper-pf)
             (player "Harry" water-hoarder-pf)])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Margie&quot;</span>","value":"\"Margie\""},{"type":"html","content":"<span class='clj-long'>91</span>","value":"91"}],"value":"[\"Margie\" 91]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Pappy&quot;</span>","value":"\"Pappy\""},{"type":"html","content":"<span class='clj-long'>7</span>","value":"7"}],"value":"[\"Pappy\" 7]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Harry&quot;</span>","value":"\"Harry\""},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"}],"value":"[\"Harry\" 2]"}],"value":"([\"Margie\" 91] [\"Pappy\" 7] [\"Harry\" 2])"}
;; <=

;; @@
(tournament 100
            [(player "Margie" majority-pf)
             (player "Maggy" margie-hater-pf)])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Maggy&quot;</span>","value":"\"Maggy\""},{"type":"html","content":"<span class='clj-long'>89</span>","value":"89"}],"value":"[\"Maggy\" 89]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Margie&quot;</span>","value":"\"Margie\""},{"type":"html","content":"<span class='clj-long'>11</span>","value":"11"}],"value":"[\"Margie\" 11]"}],"value":"([\"Maggy\" 89] [\"Margie\" 11])"}
;; <=

;; @@
(tournament 100
            [(player "Fido" fire-pf)
             (player "Maggy" margie-hater-pf)])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Fido&quot;</span>","value":"\"Fido\""},{"type":"html","content":"<span class='clj-long'>75</span>","value":"75"}],"value":"[\"Fido\" 75]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Maggy&quot;</span>","value":"\"Maggy\""},{"type":"html","content":"<span class='clj-long'>25</span>","value":"25"}],"value":"[\"Maggy\" 25]"}],"value":"([\"Fido\" 75] [\"Maggy\" 25])"}
;; <=

;; **
;;; The players provided in `rock-paper-stuff.player-functions` are simple, but by playing them against one another we can learn some basic things about the game environment.
;;; 
;;; For example, always playing Rock usually beats always playing Scissors, one-on-one:
;; **

;; @@
(tournament 100
            [(player "Rocky" rock-pf)
             (player "Suzy" scissors-pf)])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Suzy&quot;</span>","value":"\"Suzy\""},{"type":"html","content":"<span class='clj-long'>69</span>","value":"69"}],"value":"[\"Suzy\" 69]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Rocky&quot;</span>","value":"\"Rocky\""},{"type":"html","content":"<span class='clj-long'>30</span>","value":"30"}],"value":"[\"Rocky\" 30]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Nobody&quot;</span>","value":"\"Nobody\""},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[\"Nobody\" 1]"}],"value":"([\"Suzy\" 69] [\"Rocky\" 30] [\"Nobody\" 1])"}
;; <=

;; **
;;; But if a few random players are also in the game, then always playing Scissors wins more often:
;; **

;; @@
(tournament 100
            [(player "Randy" random-pf) 
             (player "Rudy" random-pf) 
             (player "Trudy" random-pf)  
             (player "Rocky" rock-pf) 
             (player "Suzy" scissors-pf)])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Randy&quot;</span>","value":"\"Randy\""},{"type":"html","content":"<span class='clj-long'>36</span>","value":"36"}],"value":"[\"Randy\" 36]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Trudy&quot;</span>","value":"\"Trudy\""},{"type":"html","content":"<span class='clj-long'>35</span>","value":"35"}],"value":"[\"Trudy\" 35]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Rudy&quot;</span>","value":"\"Rudy\""},{"type":"html","content":"<span class='clj-long'>29</span>","value":"29"}],"value":"[\"Rudy\" 29]"}],"value":"([\"Randy\" 36] [\"Trudy\" 35] [\"Rudy\" 29])"}
;; <=

;; **
;;; We can use `merge-with +` on the inventories of all of the players at the end of a game, to see what the total final inventories/debts are:
;; **

;; @@
(apply merge-with + (map :inventory 
                         (:players (play-game [(player "Randy" random-pf) 
                                               (player "Rocky" rock-pf) 
                                               (player "Pappy" paper-pf)
                                               (player "Suzy" scissors-pf)
                                               (player "Fido" fire-pf)
                                               (player "Wally" water-pf)
                                               (player "Margie" majority-pf)
                                               (player "Minnie" minority-pf)]))))
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:fire</span>","value":":fire"},{"type":"html","content":"<span class='clj-long'>606</span>","value":"606"}],"value":"[:fire 606]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:paper</span>","value":":paper"},{"type":"html","content":"<span class='clj-long'>525</span>","value":"525"}],"value":"[:paper 525]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:scissors</span>","value":":scissors"},{"type":"html","content":"<span class='clj-long'>604</span>","value":"604"}],"value":"[:scissors 604]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:water</span>","value":":water"},{"type":"html","content":"<span class='clj-long'>265</span>","value":"265"}],"value":"[:water 265]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:rock</span>","value":":rock"},{"type":"html","content":"<span class='clj-long'>412</span>","value":"412"}],"value":"[:rock 412]"}],"value":"{:fire 606, :paper 525, :scissors 604, :water 265, :rock 412}"}
;; <=

;; **
;;; Here we print the results of two-player tournaments among all pairs of players from the provided collection: 
;; **

;; @@
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
;; @@
;; ->
;;; Randy vs Randy ([Randy 100])
;;; Randy vs Rocky ([Randy 60] [Rocky 40])
;;; Randy vs Pappy ([Pappy 73] [Randy 27])
;;; Randy vs Suzy ([Suzy 66] [Randy 34])
;;; Randy vs Fido ([Randy 67] [Fido 33])
;;; Randy vs Wally ([Randy 59] [Wally 40] [Nobody 1])
;;; Randy vs Margie ([Margie 99] [Randy 1])
;;; Randy vs Minnie ([Randy 58] [Minnie 42])
;;; Randy vs Harry ([Harry 67] [Randy 33])
;;; Randy vs Maggy ([Maggy 66] [Randy 34])
;;; Rocky vs Randy ([Randy 61] [Rocky 39])
;;; Rocky vs Rocky ([Rocky 55] [Nobody 45])
;;; Rocky vs Pappy ([Pappy 61] [Rocky 38] [Nobody 1])
;;; Rocky vs Suzy ([Suzy 71] [Rocky 29])
;;; Rocky vs Fido ([Rocky 75] [Fido 24] [Nobody 1])
;;; Rocky vs Wally ([Wally 71] [Rocky 28] [Nobody 1])
;;; Rocky vs Margie ([Margie 91] [Rocky 9])
;;; Rocky vs Minnie ([Rocky 57] [Minnie 42] [Nobody 1])
;;; Rocky vs Harry ([Harry 79] [Rocky 21])
;;; Rocky vs Maggy ([Maggy 79] [Rocky 21])
;;; Pappy vs Randy ([Pappy 78] [Randy 22])
;;; Pappy vs Rocky ([Pappy 53] [Rocky 47])
;;; Pappy vs Pappy ([Pappy 58] [Nobody 42])
;;; Pappy vs Suzy ([Suzy 100])
;;; Pappy vs Fido ([Pappy 63] [Fido 37])
;;; Pappy vs Wally ([Pappy 79] [Wally 21])
;;; Pappy vs Margie ([Margie 91] [Pappy 9])
;;; Pappy vs Minnie ([Pappy 68] [Minnie 32])
;;; Pappy vs Harry ([Pappy 52] [Harry 48])
;;; Pappy vs Maggy ([Maggy 66] [Pappy 34])
;;; Suzy vs Randy ([Suzy 62] [Randy 38])
;;; Suzy vs Rocky ([Suzy 68] [Rocky 32])
;;; Suzy vs Pappy ([Suzy 100])
;;; Suzy vs Suzy ([Suzy 63] [Nobody 37])
;;; Suzy vs Fido ([Fido 100])
;;; Suzy vs Wally ([Suzy 52] [Wally 47] [Nobody 1])
;;; Suzy vs Margie ([Margie 71] [Suzy 28] [Nobody 1])
;;; Suzy vs Minnie ([Suzy 65] [Minnie 35])
;;; Suzy vs Harry ([Suzy 97] [Harry 3])
;;; Suzy vs Maggy ([Maggy 100])
;;; Fido vs Randy ([Randy 71] [Fido 29])
;;; Fido vs Rocky ([Rocky 82] [Fido 17] [Nobody 1])
;;; Fido vs Pappy ([Pappy 55] [Fido 45])
;;; Fido vs Suzy ([Fido 100])
;;; Fido vs Fido ([Fido 59] [Nobody 41])
;;; Fido vs Wally ([Wally 50] [Fido 50])
;;; Fido vs Margie ([Fido 100])
;;; Fido vs Minnie ([Minnie 55] [Fido 45])
;;; Fido vs Harry ([Harry 61] [Fido 39])
;;; Fido vs Maggy ([Fido 80] [Maggy 20])
;;; Wally vs Randy ([Wally 54] [Randy 46])
;;; Wally vs Rocky ([Wally 69] [Rocky 29] [Nobody 2])
;;; Wally vs Pappy ([Pappy 78] [Wally 22])
;;; Wally vs Suzy ([Suzy 57] [Wally 43])
;;; Wally vs Fido ([Fido 54] [Wally 46])
;;; Wally vs Wally ([Wally 58] [Nobody 42])
;;; Wally vs Margie ([Margie 91] [Wally 9])
;;; Wally vs Minnie ([Wally 60] [Minnie 38] [Nobody 2])
;;; Wally vs Harry ([Harry 66] [Wally 34])
;;; Wally vs Maggy ([Maggy 63] [Wally 37])
;;; Margie vs Randy ([Margie 98] [Randy 2])
;;; Margie vs Rocky ([Margie 92] [Rocky 6] [Nobody 2])
;;; Margie vs Pappy ([Margie 95] [Pappy 5])
;;; Margie vs Suzy ([Margie 71] [Suzy 29])
;;; Margie vs Fido ([Fido 98] [Margie 2])
;;; Margie vs Wally ([Margie 89] [Wally 10] [Nobody 1])
;;; Margie vs Margie ([Margie 97] [Nobody 3])
;;; Margie vs Minnie ([Margie 81] [Minnie 16] [Nobody 3])
;;; Margie vs Harry ([Margie 93] [Harry 7])
;;; Margie vs Maggy ([Maggy 95] [Margie 5])
;;; Minnie vs Randy ([Randy 53] [Minnie 47])
;;; Minnie vs Rocky ([Rocky 54] [Minnie 46])
;;; Minnie vs Pappy ([Pappy 65] [Minnie 35])
;;; Minnie vs Suzy ([Suzy 65] [Minnie 35])
;;; Minnie vs Fido ([Minnie 54] [Fido 46])
;;; Minnie vs Wally ([Wally 61] [Minnie 39])
;;; Minnie vs Margie ([Margie 76] [Minnie 23] [Nobody 1])
;;; Minnie vs Minnie ([Minnie 99] [Nobody 1])
;;; Minnie vs Harry ([Harry 81] [Minnie 19])
;;; Minnie vs Maggy ([Maggy 64] [Minnie 36])
;;; Harry vs Randy ([Harry 72] [Randy 28])
;;; Harry vs Rocky ([Harry 85] [Rocky 14] [Nobody 1])
;;; Harry vs Pappy ([Pappy 51] [Harry 48] [Nobody 1])
;;; Harry vs Suzy ([Suzy 94] [Harry 6])
;;; Harry vs Fido ([Harry 55] [Fido 45])
;;; Harry vs Wally ([Harry 61] [Wally 39])
;;; Harry vs Margie ([Margie 92] [Harry 8])
;;; Harry vs Minnie ([Harry 72] [Minnie 28])
;;; Harry vs Harry ([Harry 100])
;;; Harry vs Maggy ([Maggy 71] [Harry 29])
;;; Maggy vs Randy ([Maggy 69] [Randy 31])
;;; Maggy vs Rocky ([Maggy 82] [Rocky 18])
;;; Maggy vs Pappy ([Maggy 67] [Pappy 32] [Nobody 1])
;;; Maggy vs Suzy ([Maggy 100])
;;; Maggy vs Fido ([Fido 73] [Maggy 27])
;;; Maggy vs Wally ([Maggy 61] [Wally 39])
;;; Maggy vs Margie ([Maggy 94] [Margie 6])
;;; Maggy vs Minnie ([Maggy 65] [Minnie 34] [Nobody 1])
;;; Maggy vs Harry ([Maggy 60] [Harry 40])
;;; Maggy vs Maggy ([Maggy 100])
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; Here we see that Maggy dominates Margie, not only one-on-one, but also in the context of some other players:
;; **

;; @@
(tournament 100
            [;(player "Rocky" rock-pf) 
             ;(player "Suzy" scissors-pf)
             (player "Margie" majority-pf)
             ;(player "Pappy" paper-pf)
             (player "Maggy" margie-hater-pf)])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Maggy&quot;</span>","value":"\"Maggy\""},{"type":"html","content":"<span class='clj-long'>89</span>","value":"89"}],"value":"[\"Maggy\" 89]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Margie&quot;</span>","value":"\"Margie\""},{"type":"html","content":"<span class='clj-long'>11</span>","value":"11"}],"value":"[\"Margie\" 11]"}],"value":"([\"Maggy\" 89] [\"Margie\" 11])"}
;; <=

;; @@
(tournament 100
            [(player "Maggy" margie-hater-pf)
             (player "Naggy" maggie-hater-pf)])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Naggy&quot;</span>","value":"\"Naggy\""},{"type":"html","content":"<span class='clj-long'>74</span>","value":"74"}],"value":"[\"Naggy\" 74]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Maggy&quot;</span>","value":"\"Maggy\""},{"type":"html","content":"<span class='clj-long'>26</span>","value":"26"}],"value":"[\"Maggy\" 26]"}],"value":"([\"Naggy\" 74] [\"Maggy\" 26])"}
;; <=

;; **
;;; Here we see that Harry (the water-hoarder), which beat all of the simple strategies tried against it above one-on-one, can be beaten in the context of more players in the same game.
;; **

;; @@
(tournament 100
            [(player "Randy" random-pf) 
             (player "Harry" water-hoarder-pf)
             (player "Margie" majority-pf)
             (player "Maggy" margie-hater-pf)])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Maggy&quot;</span>","value":"\"Maggy\""},{"type":"html","content":"<span class='clj-long'>48</span>","value":"48"}],"value":"[\"Maggy\" 48]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Harry&quot;</span>","value":"\"Harry\""},{"type":"html","content":"<span class='clj-long'>20</span>","value":"20"}],"value":"[\"Harry\" 20]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Randy&quot;</span>","value":"\"Randy\""},{"type":"html","content":"<span class='clj-long'>16</span>","value":"16"}],"value":"[\"Randy\" 16]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Margie&quot;</span>","value":"\"Margie\""},{"type":"html","content":"<span class='clj-long'>16</span>","value":"16"}],"value":"[\"Margie\" 16]"}],"value":"([\"Maggy\" 48] [\"Harry\" 20] [\"Randy\" 16] [\"Margie\" 16])"}
;; <=

;; @@
(tournament 100
            [(player "Randy" random-pf) 
             (player "Harry" water-hoarder-pf)
             (player "Margie" majority-pf)
             ;(player "Maggy" margie-hater-pf)
             (player "Maxaminion" max-or-min-pf)])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Margie&quot;</span>","value":"\"Margie\""},{"type":"html","content":"<span class='clj-long'>83</span>","value":"83"}],"value":"[\"Margie\" 83]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Maxaminion&quot;</span>","value":"\"Maxaminion\""},{"type":"html","content":"<span class='clj-long'>11</span>","value":"11"}],"value":"[\"Maxaminion\" 11]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Harry&quot;</span>","value":"\"Harry\""},{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"}],"value":"[\"Harry\" 3]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Randy&quot;</span>","value":"\"Randy\""},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"}],"value":"[\"Randy\" 2]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Nobody&quot;</span>","value":"\"Nobody\""},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[\"Nobody\" 1]"}],"value":"([\"Margie\" 83] [\"Maxaminion\" 11] [\"Harry\" 3] [\"Randy\" 2] [\"Nobody\" 1])"}
;; <=

;; **
;;; Let's do that again and produce a bar chart of the results, using the `bar-chart` function provided by Gorilla REPL:
;; **

;; @@
(let [results (tournament 100
                          [(player "Randy" random-pf) 
                           (player "Harry" water-hoarder-pf)
                           (player "Margie" majority-pf)
                           (player "Maggy" margie-hater-pf)])]
  (plot/bar-chart (map first results) (map second results)))
;; @@
;; =>
;;; {"type":"vega","content":{"width":400,"height":247.2187957763672,"padding":{"top":10,"left":55,"bottom":40,"right":10},"data":[{"name":"050af6b4-62fc-4445-a66b-ff9739f2b982","values":[{"x":"Maggy","y":60},{"x":"Randy","y":17},{"x":"Harry","y":14},{"x":"Margie","y":9}]}],"marks":[{"type":"rect","from":{"data":"050af6b4-62fc-4445-a66b-ff9739f2b982"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"width":{"scale":"x","band":true,"offset":-1},"y":{"scale":"y","field":"data.y"},"y2":{"scale":"y","value":0}},"update":{"fill":{"value":"steelblue"},"opacity":{"value":1}},"hover":{"fill":{"value":"#FF29D2"}}}}],"scales":[{"name":"x","type":"ordinal","range":"width","domain":{"data":"050af6b4-62fc-4445-a66b-ff9739f2b982","field":"data.x"}},{"name":"y","range":"height","nice":true,"domain":{"data":"050af6b4-62fc-4445-a66b-ff9739f2b982","field":"data.y"}}],"axes":[{"type":"x","scale":"x"},{"type":"y","scale":"y"}]},"value":"#gorilla_repl.vega.VegaView{:content {:width 400, :height 247.2188, :padding {:top 10, :left 55, :bottom 40, :right 10}, :data [{:name \"050af6b4-62fc-4445-a66b-ff9739f2b982\", :values ({:x \"Maggy\", :y 60} {:x \"Randy\", :y 17} {:x \"Harry\", :y 14} {:x \"Margie\", :y 9})}], :marks [{:type \"rect\", :from {:data \"050af6b4-62fc-4445-a66b-ff9739f2b982\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :width {:scale \"x\", :band true, :offset -1}, :y {:scale \"y\", :field \"data.y\"}, :y2 {:scale \"y\", :value 0}}, :update {:fill {:value \"steelblue\"}, :opacity {:value 1}}, :hover {:fill {:value \"#FF29D2\"}}}}], :scales [{:name \"x\", :type \"ordinal\", :range \"width\", :domain {:data \"050af6b4-62fc-4445-a66b-ff9739f2b982\", :field \"data.x\"}} {:name \"y\", :range \"height\", :nice true, :domain {:data \"050af6b4-62fc-4445-a66b-ff9739f2b982\", :field \"data.y\"}}], :axes [{:type \"x\", :scale \"x\"} {:type \"y\", :scale \"y\"}]}}"}
;; <=

;; **
;;; Gorilla REPL's plotting functions aren't as full-featured as those of dedicated graphics packages, but they can be handy. For example, here we  use `list-plot` and `compose` to plot the water levels of players over a game, which are stored in the `:global-history` of the game result:
;; **

;; @@
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
;; @@
;; =>
;;; {"type":"vega","content":{"width":400,"height":247.2187957763672,"padding":{"top":10,"left":55,"bottom":40,"right":10},"scales":[{"name":"x","type":"linear","range":"width","zero":false,"domain":{"data":"ea78fc21-4e19-499f-9303-8514c1c15f14","field":"data.x"}},{"name":"y","type":"linear","range":"height","nice":true,"zero":false,"domain":[0.0,12.581732790041283]}],"axes":[{"type":"x","scale":"x"},{"type":"y","scale":"y"}],"data":[{"name":"ea78fc21-4e19-499f-9303-8514c1c15f14","values":[{"x":0,"y":0.4472135954999579},{"x":1,"y":0.8944271909999159},{"x":2,"y":0.4472135954999579},{"x":3,"y":0.4472135954999579},{"x":4,"y":0.0},{"x":5,"y":0.0},{"x":6,"y":0.0},{"x":7,"y":0.7071067811865476},{"x":8,"y":0.4472135954999579},{"x":9,"y":0.7071067811865476},{"x":10,"y":0.7071067811865476},{"x":11,"y":0.7071067811865476},{"x":12,"y":0.7071067811865476},{"x":13,"y":0.4472135954999579},{"x":14,"y":1.0954451150103321},{"x":15,"y":1.7888543819998317},{"x":16,"y":1.51657508881031},{"x":17,"y":1.51657508881031},{"x":18,"y":1.51657508881031},{"x":19,"y":1.7888543819998317},{"x":20,"y":1.7888543819998317},{"x":21,"y":2.1213203435596424},{"x":22,"y":2.1213203435596424},{"x":23,"y":2.1213203435596424},{"x":24,"y":2.8284271247461903},{"x":25,"y":2.4899799195977463},{"x":26,"y":2.4899799195977463},{"x":27,"y":3.1937438845342623},{"x":28,"y":3.1937438845342623},{"x":29,"y":2.8809720581775866},{"x":30,"y":2.6076809620810595},{"x":31,"y":2.6076809620810595},{"x":32,"y":2.6076809620810595},{"x":33,"y":2.6076809620810595},{"x":34,"y":2.6076809620810595},{"x":35,"y":2.6076809620810595},{"x":36,"y":2.8809720581775866},{"x":37,"y":2.8809720581775866},{"x":38,"y":2.8809720581775866},{"x":39,"y":3.5777087639996634},{"x":40,"y":3.5777087639996634},{"x":41,"y":3.5777087639996634},{"x":42,"y":3.286335345030997},{"x":43,"y":3.286335345030997},{"x":44,"y":3.03315017762062},{"x":45,"y":3.7013511046643495},{"x":46,"y":3.4641016151377544},{"x":47,"y":3.7013511046643495},{"x":48,"y":3.7013511046643495},{"x":49,"y":3.7013511046643495},{"x":50,"y":4.381780460041329},{"x":51,"y":5.06951674225463},{"x":52,"y":4.795831523312719},{"x":53,"y":4.795831523312719},{"x":54,"y":4.795831523312719},{"x":55,"y":5.477225575051661},{"x":56,"y":5.761944116355173},{"x":57,"y":5.761944116355173},{"x":58,"y":6.457553716385176},{"x":59,"y":7.155417527999327},{"x":60,"y":7.155417527999327},{"x":61,"y":7.155417527999327},{"x":62,"y":6.855654600401044},{"x":63,"y":6.572670690061994},{"x":64,"y":6.572670690061994},{"x":65,"y":6.855654600401044},{"x":66,"y":7.54983443527075},{"x":67,"y":7.54983443527075},{"x":68,"y":8.246211251235321},{"x":69,"y":8.246211251235321},{"x":70,"y":8.246211251235321},{"x":71,"y":8.246211251235321},{"x":72,"y":8.246211251235321},{"x":73,"y":8.555699854482976},{"x":74,"y":9.257429448826494},{"x":75,"y":9.257429448826494},{"x":76,"y":8.94427190999916},{"x":77,"y":8.94427190999916},{"x":78,"y":9.257429448826494},{"x":79,"y":9.581231653602787},{"x":80,"y":9.257429448826494},{"x":81,"y":9.581231653602787},{"x":82,"y":9.581231653602787},{"x":83,"y":9.581231653602787},{"x":84,"y":9.581231653602787},{"x":85,"y":9.581231653602787},{"x":86,"y":9.257429448826494},{"x":87,"y":9.257429448826494},{"x":88,"y":9.257429448826494},{"x":89,"y":9.581231653602787},{"x":90,"y":9.91463564635635},{"x":91,"y":9.91463564635635},{"x":92,"y":9.581231653602787},{"x":93,"y":9.581231653602787},{"x":94,"y":10.285912696499032},{"x":95,"y":9.959919678390985},{"x":96,"y":9.643650760992955},{"x":97,"y":9.959919678390985},{"x":98,"y":9.959919678390985},{"x":99,"y":9.959919678390985},{"x":100,"y":9.959919678390985},{"x":101,"y":9.643650760992955},{"x":102,"y":9.643650760992955},{"x":103,"y":9.643650760992955},{"x":104,"y":9.643650760992955},{"x":105,"y":9.643650760992955},{"x":106,"y":9.643650760992955},{"x":107,"y":9.338094023943002},{"x":108,"y":9.338094023943002},{"x":109,"y":10.03493896344168},{"x":110,"y":10.344080432788601},{"x":111,"y":10.66302021005306},{"x":112,"y":10.344080432788601},{"x":113,"y":11.045361017187261},{"x":114,"y":11.366617790706258},{"x":115,"y":11.366617790706258},{"x":116,"y":11.366617790706258},{"x":117,"y":11.366617790706258},{"x":118,"y":11.696153213770756},{"x":119,"y":11.717508267545622},{"x":120,"y":11.717508267545622},{"x":121,"y":11.717508267545622},{"x":122,"y":11.717508267545622},{"x":123,"y":11.717508267545622},{"x":124,"y":11.691877522451216},{"x":125,"y":11.691877522451216},{"x":126,"y":11.683321445547923},{"x":127,"y":11.683321445547923},{"x":128,"y":11.335784048754634},{"x":129,"y":11.34460224071342},{"x":130,"y":11.34460224071342},{"x":131,"y":11.34460224071342},{"x":132,"y":10.986355173577815},{"x":133,"y":10.986355173577815},{"x":134,"y":10.986355173577815},{"x":135,"y":10.986355173577815},{"x":136,"y":11.013627921806693},{"x":137,"y":11.058933040759403},{"x":138,"y":10.807404868885037},{"x":139,"y":10.807404868885037},{"x":140,"y":10.807404868885037},{"x":141,"y":10.807404868885037},{"x":142,"y":10.807404868885037},{"x":143,"y":10.825894882179487},{"x":144,"y":10.825894882179487},{"x":145,"y":10.825894882179487},{"x":146,"y":10.825894882179487},{"x":147,"y":10.825894882179487},{"x":148,"y":10.825894882179487},{"x":149,"y":10.825894882179487},{"x":150,"y":10.825894882179487},{"x":151,"y":10.425929215182693},{"x":152,"y":10.807404868885037},{"x":153,"y":10.807404868885037},{"x":154,"y":10.597169433391164},{"x":155,"y":10.597169433391164},{"x":156,"y":10.644247272588137},{"x":157,"y":10.644247272588137},{"x":158,"y":10.644247272588137},{"x":159,"y":10.644247272588137},{"x":160,"y":10.644247272588137},{"x":161,"y":10.549881515922348},{"x":162,"y":10.549881515922348},{"x":163,"y":10.549881515922348},{"x":164,"y":10.549881515922348},{"x":165,"y":10.549881515922348},{"x":166,"y":10.549881515922348},{"x":167,"y":10.549881515922348},{"x":168,"y":10.358571330062848},{"x":169,"y":10.358571330062848},{"x":170,"y":10.256705123966467},{"x":171,"y":10.256705123966467},{"x":172,"y":10.256705123966467},{"x":173,"y":10.52140675005011},{"x":174,"y":10.825894882179487},{"x":175,"y":10.825894882179487},{"x":176,"y":10.825894882179487},{"x":177,"y":10.825894882179487},{"x":178,"y":10.497618777608569},{"x":179,"y":10.497618777608569},{"x":180,"y":10.502380682492898},{"x":181,"y":10.502380682492898},{"x":182,"y":10.502380682492898},{"x":183,"y":10.502380682492898},{"x":184,"y":10.502380682492898},{"x":185,"y":10.502380682492898},{"x":186,"y":10.526157893552615},{"x":187,"y":10.549881515922348},{"x":188,"y":10.549881515922348},{"x":189,"y":10.549881515922348},{"x":190,"y":10.597169433391164},{"x":191,"y":10.597169433391164},{"x":192,"y":10.526157893552615},{"x":193,"y":10.526157893552615},{"x":194,"y":10.526157893552615},{"x":195,"y":10.644247272588137},{"x":196,"y":10.644247272588137},{"x":197,"y":10.709808588392232},{"x":198,"y":10.709808588392232},{"x":199,"y":10.709808588392232},{"x":200,"y":10.709808588392232},{"x":201,"y":10.709808588392232},{"x":202,"y":10.709808588392232},{"x":203,"y":10.807404868885037},{"x":204,"y":10.756393447619885},{"x":205,"y":10.723805294763608},{"x":206,"y":10.723805294763608},{"x":207,"y":10.848963084092414},{"x":208,"y":10.497618777608569},{"x":209,"y":10.497618777608569},{"x":210,"y":9.984988733093292},{"x":211,"y":10.66302021005306},{"x":212,"y":10.66302021005306},{"x":213,"y":10.66302021005306},{"x":214,"y":10.66302021005306},{"x":215,"y":10.691117808723277},{"x":216,"y":10.691117808723277},{"x":217,"y":10.691117808723277},{"x":218,"y":10.691117808723277},{"x":219,"y":10.66302021005306},{"x":220,"y":10.66302021005306},{"x":221,"y":10.36822067666386},{"x":222,"y":10.497618777608569},{"x":223,"y":10.497618777608569},{"x":224,"y":10.256705123966467},{"x":225,"y":10.406728592598157},{"x":226,"y":10.406728592598157},{"x":227,"y":10.47377677822093},{"x":228,"y":10.47377677822093},{"x":229,"y":10.47377677822093},{"x":230,"y":10.47377677822093},{"x":231,"y":10.406728592598157},{"x":232,"y":10.644247272588137},{"x":233,"y":10.876580344942981},{"x":234,"y":10.876580344942981},{"x":235,"y":11.148990985734986},{"x":236,"y":11.148990985734986},{"x":237,"y":11.148990985734986},{"x":238,"y":11.458621208504974},{"x":239,"y":11.717508267545622},{"x":240,"y":12.054044964243332},{"x":241,"y":11.674759098157015},{"x":242,"y":11.104053313993049},{"x":243,"y":10.761040841851685},{"x":244,"y":10.761040841851685},{"x":245,"y":10.425929215182693},{"x":246,"y":10.761040841851685},{"x":247,"y":10.761040841851685},{"x":248,"y":10.761040841851685},{"x":249,"y":10.761040841851685},{"x":250,"y":10.761040841851685},{"x":251,"y":10.761040841851685},{"x":252,"y":11.104053313993049},{"x":253,"y":11.104053313993049},{"x":254,"y":11.717508267545622},{"x":255,"y":12.0124934963562},{"x":256,"y":11.631852818876277},{"x":257,"y":11.631852818876277},{"x":258,"y":11.631852818876277},{"x":259,"y":11.081516141756055},{"x":260,"y":10.737783756436894},{"x":261,"y":10.737783756436894},{"x":262,"y":11.081516141756055},{"x":263,"y":11.081516141756055},{"x":264,"y":11.081516141756055},{"x":265,"y":11.081516141756055},{"x":266,"y":11.081516141756055},{"x":267,"y":11.717508267545622},{"x":268,"y":11.717508267545622},{"x":269,"y":11.696153213770756},{"x":270,"y":11.696153213770756},{"x":271,"y":11.882760622010357},{"x":272,"y":11.882760622010357},{"x":273,"y":11.34460224071342},{"x":274,"y":11.0},{"x":275,"y":10.66302021005306},{"x":276,"y":10.66302021005306},{"x":277,"y":11.0},{"x":278,"y":11.0},{"x":279,"y":11.640446726822816},{"x":280,"y":11.640446726822816},{"x":281,"y":11.640446726822816},{"x":282,"y":11.987493482792805},{"x":283,"y":11.987493482792805},{"x":284,"y":11.987493482792805},{"x":285,"y":11.987493482792805},{"x":286,"y":11.987493482792805},{"x":287,"y":11.987493482792805},{"x":288,"y":11.987493482792805},{"x":289,"y":11.987493482792805},{"x":290,"y":12.275992831539126},{"x":291,"y":12.275992831539126},{"x":292,"y":12.165525060596439},{"x":293,"y":11.789826122551595},{"x":294,"y":11.789826122551595},{"x":295,"y":11.510864433221338},{"x":296,"y":11.510864433221338},{"x":297,"y":11.510864433221338},{"x":298,"y":11.789826122551595},{"x":299,"y":11.987493482792805}]},{"name":"0e10cf1b-209d-4334-bc6a-86a636a3d188","values":[{"x":0,"y":0.0},{"x":1,"y":0.0},{"x":2,"y":0.4472135954999579},{"x":3,"y":0.5477225575051661},{"x":4,"y":0.5477225575051661},{"x":5,"y":0.5477225575051661},{"x":6,"y":0.5477225575051661},{"x":7,"y":0.5477225575051661},{"x":8,"y":0.8944271909999159},{"x":9,"y":0.8944271909999159},{"x":10,"y":0.8944271909999159},{"x":11,"y":1.0954451150103321},{"x":12,"y":1.4142135623730951},{"x":13,"y":1.6431676725154984},{"x":14,"y":1.7888543819998317},{"x":15,"y":1.7888543819998317},{"x":16,"y":2.073644135332772},{"x":17,"y":2.073644135332772},{"x":18,"y":2.16794833886788},{"x":19,"y":2.16794833886788},{"x":20,"y":2.4083189157584592},{"x":21,"y":2.4083189157584592},{"x":22,"y":2.073644135332772},{"x":23,"y":2.073644135332772},{"x":24,"y":2.3021728866442674},{"x":25,"y":2.3021728866442674},{"x":26,"y":2.3021728866442674},{"x":27,"y":2.701851217221259},{"x":28,"y":2.701851217221259},{"x":29,"y":2.8809720581775866},{"x":30,"y":3.1144823004794873},{"x":31,"y":3.1144823004794873},{"x":32,"y":3.5637059362410923},{"x":33,"y":3.5637059362410923},{"x":34,"y":3.5355339059327378},{"x":35,"y":3.5355339059327378},{"x":36,"y":3.5355339059327378},{"x":37,"y":3.7013511046643495},{"x":38,"y":3.9115214431215892},{"x":39,"y":3.9115214431215892},{"x":40,"y":3.9115214431215892},{"x":41,"y":3.9115214431215892},{"x":42,"y":4.09878030638384},{"x":43,"y":4.09878030638384},{"x":44,"y":4.324349662087931},{"x":45,"y":4.324349662087931},{"x":46,"y":4.58257569495584},{"x":47,"y":4.58257569495584},{"x":48,"y":4.764451699828638},{"x":49,"y":4.795831523312719},{"x":50,"y":5.1478150704935},{"x":51,"y":5.1478150704935},{"x":52,"y":5.1478150704935},{"x":53,"y":5.1478150704935},{"x":54,"y":5.1478150704935},{"x":55,"y":5.5677643628300215},{"x":56,"y":5.5677643628300215},{"x":57,"y":5.5677643628300215},{"x":58,"y":6.041522986797286},{"x":59,"y":6.557438524302},{"x":60,"y":6.557438524302},{"x":61,"y":6.534523701081817},{"x":62,"y":6.730527468185536},{"x":63,"y":6.730527468185536},{"x":64,"y":6.730527468185536},{"x":65,"y":6.730527468185536},{"x":66,"y":7.26636084983398},{"x":67,"y":7.402702209328699},{"x":68,"y":7.402702209328699},{"x":69,"y":7.26636084983398},{"x":70,"y":7.26636084983398},{"x":71,"y":7.26636084983398},{"x":72,"y":7.26636084983398},{"x":73,"y":7.26636084983398},{"x":74,"y":7.829431652425353},{"x":75,"y":7.92464510246358},{"x":76,"y":7.92464510246358},{"x":77,"y":8.018728078691781},{"x":78,"y":8.018728078691781},{"x":79,"y":8.018728078691781},{"x":80,"y":8.018728078691781},{"x":81,"y":8.018728078691781},{"x":82,"y":8.018728078691781},{"x":83,"y":8.018728078691781},{"x":84,"y":8.018728078691781},{"x":85,"y":8.136338242723197},{"x":86,"y":8.136338242723197},{"x":87,"y":8.018728078691781},{"x":88,"y":8.018728078691781},{"x":89,"y":8.018728078691781},{"x":90,"y":8.018728078691781},{"x":91,"y":8.018728078691781},{"x":92,"y":8.228000972289685},{"x":93,"y":8.228000972289685},{"x":94,"y":8.757853618324527},{"x":95,"y":8.757853618324527},{"x":96,"y":8.972179222463181},{"x":97,"y":8.972179222463181},{"x":98,"y":9.093954035511725},{"x":99,"y":9.364827814754525},{"x":100,"y":9.364827814754525},{"x":101,"y":9.364827814754525},{"x":102,"y":9.364827814754525},{"x":103,"y":9.246621004453464},{"x":104,"y":9.364827814754525},{"x":105,"y":9.418067742376884},{"x":106,"y":9.418067742376884},{"x":107,"y":9.6591925128346},{"x":108,"y":9.6591925128346},{"x":109,"y":10.13903348450926},{"x":110,"y":10.13903348450926},{"x":111,"y":10.13903348450926},{"x":112,"y":10.382677881933928},{"x":113,"y":10.382677881933928},{"x":114,"y":10.382677881933928},{"x":115,"y":10.4546640309481},{"x":116,"y":10.310189135025603},{"x":117,"y":10.310189135025603},{"x":118,"y":10.310189135025603},{"x":119,"y":10.310189135025603},{"x":120,"y":10.261578825892242},{"x":121,"y":10.13903348450926},{"x":122,"y":10.13903348450926},{"x":123,"y":10.114346246792227},{"x":124,"y":10.114346246792227},{"x":125,"y":10.13903348450926},{"x":126,"y":10.13903348450926},{"x":127,"y":10.13903348450926},{"x":128,"y":10.13903348450926},{"x":129,"y":10.13903348450926},{"x":130,"y":10.084641788382967},{"x":131,"y":10.084641788382967},{"x":132,"y":10.084641788382967},{"x":133,"y":10.084641788382967},{"x":134,"y":10.084641788382967},{"x":135,"y":10.124228365658293},{"x":136,"y":10.124228365658293},{"x":137,"y":10.124228365658293},{"x":138,"y":10.124228365658293},{"x":139,"y":10.124228365658293},{"x":140,"y":10.009995004993758},{"x":141,"y":9.964938534682489},{"x":142,"y":9.964938534682489},{"x":143,"y":9.964938534682489},{"x":144,"y":9.939818911831342},{"x":145,"y":9.939818911831342},{"x":146,"y":9.633275663033837},{"x":147,"y":9.628083921528727},{"x":148,"y":9.628083921528727},{"x":149,"y":9.643650760992955},{"x":150,"y":9.643650760992955},{"x":151,"y":9.643650760992955},{"x":152,"y":9.643650760992955},{"x":153,"y":9.654014708917737},{"x":154,"y":9.364827814754525},{"x":155,"y":9.396807968666806},{"x":156,"y":9.396807968666806},{"x":157,"y":9.396807968666806},{"x":158,"y":9.396807968666806},{"x":159,"y":9.396807968666806},{"x":160,"y":9.60728889958036},{"x":161,"y":9.60728889958036},{"x":162,"y":9.60728889958036},{"x":163,"y":9.60728889958036},{"x":164,"y":9.60728889958036},{"x":165,"y":9.60728889958036},{"x":166,"y":9.60728889958036},{"x":167,"y":9.60728889958036},{"x":168,"y":9.60728889958036},{"x":169,"y":9.864076236526156},{"x":170,"y":9.67987603226405},{"x":171,"y":9.67987603226405},{"x":172,"y":9.959919678390985},{"x":173,"y":10.059821071967434},{"x":174,"y":10.207840124139876},{"x":175,"y":10.47377677822093},{"x":176,"y":10.779610382569492},{"x":177,"y":10.96813566655701},{"x":178,"y":10.549881515922348},{"x":179,"y":10.549881515922348},{"x":180,"y":10.549881515922348},{"x":181,"y":10.549881515922348},{"x":182,"y":10.549881515922348},{"x":183,"y":10.549881515922348},{"x":184,"y":10.691117808723277},{"x":185,"y":10.691117808723277},{"x":186,"y":10.691117808723277},{"x":187,"y":10.691117808723277},{"x":188,"y":10.691117808723277},{"x":189,"y":10.691117808723277},{"x":190,"y":10.691117808723277},{"x":191,"y":10.691117808723277},{"x":192,"y":10.497618777608569},{"x":193,"y":10.497618777608569},{"x":194,"y":10.084641788382967},{"x":195,"y":10.084641788382967},{"x":196,"y":10.329569206893384},{"x":197,"y":10.329569206893384},{"x":198,"y":10.319883720275147},{"x":199,"y":10.074720839804943},{"x":200,"y":10.2810505299799},{"x":201,"y":10.2810505299799},{"x":202,"y":10.2810505299799},{"x":203,"y":10.2810505299799},{"x":204,"y":10.2810505299799},{"x":205,"y":10.2810505299799},{"x":206,"y":10.2810505299799},{"x":207,"y":10.2810505299799},{"x":208,"y":9.959919678390985},{"x":209,"y":9.984988733093292},{"x":210,"y":9.984988733093292},{"x":211,"y":10.305338422390601},{"x":212,"y":10.305338422390601},{"x":213,"y":10.305338422390601},{"x":214,"y":10.334408546211051},{"x":215,"y":10.334408546211051},{"x":216,"y":10.334408546211051},{"x":217,"y":10.334408546211051},{"x":218,"y":10.334408546211051},{"x":219,"y":10.158740079360236},{"x":220,"y":10.158740079360236},{"x":221,"y":10.158740079360236},{"x":222,"y":10.158740079360236},{"x":223,"y":10.158740079360236},{"x":224,"y":10.158740079360236},{"x":225,"y":10.158740079360236},{"x":226,"y":10.158740079360236},{"x":227,"y":10.158740079360236},{"x":228,"y":10.158740079360236},{"x":229,"y":10.158740079360236},{"x":230,"y":10.222524150130436},{"x":231,"y":10.222524150130436},{"x":232,"y":10.44030650891055},{"x":233,"y":10.44030650891055},{"x":234,"y":10.2810505299799},{"x":235,"y":10.2810505299799},{"x":236,"y":10.13903348450926},{"x":237,"y":10.114346246792227},{"x":238,"y":10.114346246792227},{"x":239,"y":10.406728592598157},{"x":240,"y":10.406728592598157},{"x":241,"y":10.064790112068906},{"x":242,"y":10.064790112068906},{"x":243,"y":10.064790112068906},{"x":244,"y":10.21273714534943},{"x":245,"y":10.497618777608569},{"x":246,"y":10.497618777608569},{"x":247,"y":10.848963084092414},{"x":248,"y":11.0},{"x":249,"y":11.313708498984761},{"x":250,"y":11.313708498984761},{"x":251,"y":11.476062042355817},{"x":252,"y":11.476062042355817},{"x":253,"y":11.653325705565772},{"x":254,"y":11.653325705565772},{"x":255,"y":11.653325705565772},{"x":256,"y":11.653325705565772},{"x":257,"y":11.691877522451216},{"x":258,"y":11.691877522451216},{"x":259,"y":11.691877522451216},{"x":260,"y":11.691877522451216},{"x":261,"y":11.691877522451216},{"x":262,"y":11.691877522451216},{"x":263,"y":11.388590782006348},{"x":264,"y":11.388590782006348},{"x":265,"y":11.20267825120404},{"x":266,"y":11.388590782006348},{"x":267,"y":11.734564329364767},{"x":268,"y":11.734564329364767},{"x":269,"y":11.734564329364767},{"x":270,"y":11.734564329364767},{"x":271,"y":11.734564329364767},{"x":272,"y":11.734564329364767},{"x":273,"y":11.734564329364767},{"x":274,"y":11.991663771137015},{"x":275,"y":11.991663771137015},{"x":276,"y":11.991663771137015},{"x":277,"y":11.991663771137015},{"x":278,"y":12.177848742696717},{"x":279,"y":12.541929676090518},{"x":280,"y":12.541929676090518},{"x":281,"y":12.541929676090518},{"x":282,"y":12.541929676090518},{"x":283,"y":12.541929676090518},{"x":284,"y":12.541929676090518},{"x":285,"y":12.517987058628874},{"x":286,"y":12.541929676090518},{"x":287,"y":12.541929676090518},{"x":288,"y":12.581732790041283},{"x":289,"y":12.340988615179905},{"x":290,"y":12.340988615179905},{"x":291,"y":12.581732790041283},{"x":292,"y":12.581732790041283},{"x":293,"y":12.300406497347964},{"x":294,"y":12.361229712289955},{"x":295,"y":12.136721138759018},{"x":296,"y":12.31665539016173},{"x":297,"y":12.31665539016173},{"x":298,"y":12.537942414925983},{"x":299,"y":12.537942414925983}]},{"name":"6d866402-a82c-451a-91d6-ef2656a0c269","values":[{"x":0,"y":0.0},{"x":1,"y":0.0},{"x":2,"y":0.0},{"x":3,"y":0.0},{"x":4,"y":0.4472135954999579},{"x":5,"y":0.4472135954999579},{"x":6,"y":0.4472135954999579},{"x":7,"y":0.8366600265340756},{"x":8,"y":0.8366600265340756},{"x":9,"y":0.8366600265340756},{"x":10,"y":0.8944271909999159},{"x":11,"y":0.8944271909999159},{"x":12,"y":0.8944271909999159},{"x":13,"y":0.8944271909999159},{"x":14,"y":0.8944271909999159},{"x":15,"y":1.51657508881031},{"x":16,"y":1.51657508881031},{"x":17,"y":1.6733200530681511},{"x":18,"y":1.6733200530681511},{"x":19,"y":1.6733200530681511},{"x":20,"y":1.6733200530681511},{"x":21,"y":1.6733200530681511},{"x":22,"y":1.9493588689617927},{"x":23,"y":1.816590212458495},{"x":24,"y":1.816590212458495},{"x":25,"y":2.073644135332772},{"x":26,"y":2.073644135332772},{"x":27,"y":2.073644135332772},{"x":28,"y":1.7888543819998317},{"x":29,"y":1.7888543819998317},{"x":30,"y":1.7888543819998317},{"x":31,"y":2.0},{"x":32,"y":1.5811388300841898},{"x":33,"y":1.5811388300841898},{"x":34,"y":1.3038404810405297},{"x":35,"y":1.3038404810405297},{"x":36,"y":1.3038404810405297},{"x":37,"y":1.3038404810405297},{"x":38,"y":1.3038404810405297},{"x":39,"y":1.6431676725154984},{"x":40,"y":1.8708286933869707},{"x":41,"y":2.16794833886788},{"x":42,"y":2.16794833886788},{"x":43,"y":2.1213203435596424},{"x":44,"y":2.1213203435596424},{"x":45,"y":2.449489742783178},{"x":46,"y":2.449489742783178},{"x":47,"y":2.449489742783178},{"x":48,"y":2.449489742783178},{"x":49,"y":2.449489742783178},{"x":50,"y":2.449489742783178},{"x":51,"y":2.9154759474226504},{"x":52,"y":3.03315017762062},{"x":53,"y":3.03315017762062},{"x":54,"y":3.03315017762062},{"x":55,"y":3.03315017762062},{"x":56,"y":3.03315017762062},{"x":57,"y":3.286335345030997},{"x":58,"y":3.286335345030997},{"x":59,"y":3.286335345030997},{"x":60,"y":3.5777087639996634},{"x":61,"y":3.286335345030997},{"x":62,"y":3.286335345030997},{"x":63,"y":3.4351128074635335},{"x":64,"y":3.4351128074635335},{"x":65,"y":3.4351128074635335},{"x":66,"y":3.4351128074635335},{"x":67,"y":3.4351128074635335},{"x":68,"y":3.9115214431215892},{"x":69,"y":3.7682887362833544},{"x":70,"y":3.391164991562634},{"x":71,"y":3.63318042491699},{"x":72,"y":3.286335345030997},{"x":73,"y":3.286335345030997},{"x":74,"y":3.286335345030997},{"x":75,"y":3.361547262794322},{"x":76,"y":3.4351128074635335},{"x":77,"y":3.4351128074635335},{"x":78,"y":3.4351128074635335},{"x":79,"y":3.4351128074635335},{"x":80,"y":3.5637059362410923},{"x":81,"y":3.5637059362410923},{"x":82,"y":3.5637059362410923},{"x":83,"y":3.5637059362410923},{"x":84,"y":3.5637059362410923},{"x":85,"y":3.5637059362410923},{"x":86,"y":3.7416573867739413},{"x":87,"y":3.7416573867739413},{"x":88,"y":3.7416573867739413},{"x":89,"y":3.7416573867739413},{"x":90,"y":3.7416573867739413},{"x":91,"y":3.96232255123179},{"x":92,"y":3.96232255123179},{"x":93,"y":3.96232255123179},{"x":94,"y":3.96232255123179},{"x":95,"y":4.183300132670378},{"x":96,"y":4.183300132670378},{"x":97,"y":4.183300132670378},{"x":98,"y":4.183300132670378},{"x":99,"y":3.7416573867739413},{"x":100,"y":3.7416573867739413},{"x":101,"y":3.96232255123179},{"x":102,"y":3.646916505762094},{"x":103,"y":3.4351128074635335},{"x":104,"y":3.4351128074635335},{"x":105,"y":3.4351128074635335},{"x":106,"y":3.63318042491699},{"x":107,"y":3.63318042491699},{"x":108,"y":3.847076812334269},{"x":109,"y":3.847076812334269},{"x":110,"y":3.847076812334269},{"x":111,"y":3.847076812334269},{"x":112,"y":3.847076812334269},{"x":113,"y":4.277849927241488},{"x":114,"y":4.277849927241488},{"x":115,"y":4.277849927241488},{"x":116,"y":4.08656334834051},{"x":117,"y":3.9749213828703582},{"x":118,"y":3.9749213828703582},{"x":119,"y":3.7815340802378077},{"x":120,"y":3.7815340802378077},{"x":121,"y":3.646916505762094},{"x":122,"y":3.646916505762094},{"x":123,"y":3.5071355833500366},{"x":124,"y":3.5071355833500366},{"x":125,"y":3.5071355833500366},{"x":126,"y":3.5071355833500366},{"x":127,"y":3.5071355833500366},{"x":128,"y":3.2093613071762426},{"x":129,"y":3.2093613071762426},{"x":130,"y":3.2093613071762426},{"x":131,"y":3.2093613071762426},{"x":132,"y":3.2093613071762426},{"x":133,"y":3.2093613071762426},{"x":134,"y":3.2093613071762426},{"x":135,"y":3.2093613071762426},{"x":136,"y":3.2093613071762426},{"x":137,"y":3.2093613071762426},{"x":138,"y":3.1304951684997055},{"x":139,"y":3.1304951684997055},{"x":140,"y":3.2093613071762426},{"x":141,"y":3.2093613071762426},{"x":142,"y":3.2093613071762426},{"x":143,"y":3.2093613071762426},{"x":144,"y":3.2093613071762426},{"x":145,"y":3.1304951684997055},{"x":146,"y":3.2093613071762426},{"x":147,"y":3.2093613071762426},{"x":148,"y":3.2093613071762426},{"x":149,"y":3.2093613071762426},{"x":150,"y":3.2093613071762426},{"x":151,"y":2.701851217221259},{"x":152,"y":2.701851217221259},{"x":153,"y":2.701851217221259},{"x":154,"y":2.701851217221259},{"x":155,"y":2.701851217221259},{"x":156,"y":2.701851217221259},{"x":157,"y":2.3021728866442674},{"x":158,"y":2.3021728866442674},{"x":159,"y":2.073644135332772},{"x":160,"y":2.073644135332772},{"x":161,"y":1.9235384061671346},{"x":162,"y":1.9235384061671346},{"x":163,"y":2.0},{"x":164,"y":2.0},{"x":165,"y":1.7888543819998317},{"x":166,"y":1.7888543819998317},{"x":167,"y":1.7888543819998317},{"x":168,"y":1.6431676725154984},{"x":169,"y":1.6431676725154984},{"x":170,"y":1.6431676725154984},{"x":171,"y":1.224744871391589},{"x":172,"y":1.224744871391589},{"x":173,"y":1.224744871391589},{"x":174,"y":1.224744871391589},{"x":175,"y":1.224744871391589},{"x":176,"y":1.224744871391589},{"x":177,"y":1.224744871391589},{"x":178,"y":1.224744871391589},{"x":179,"y":1.4142135623730951},{"x":180,"y":1.4142135623730951},{"x":181,"y":1.4142135623730951},{"x":182,"y":1.4832396974191326},{"x":183,"y":1.4142135623730951},{"x":184,"y":1.4142135623730951},{"x":185,"y":1.4142135623730951},{"x":186,"y":1.4142135623730951},{"x":187,"y":1.4142135623730951},{"x":188,"y":1.4142135623730951},{"x":189,"y":1.8708286933869707},{"x":190,"y":1.8708286933869707},{"x":191,"y":1.8708286933869707},{"x":192,"y":1.8708286933869707},{"x":193,"y":2.04939015319192},{"x":194,"y":1.7888543819998317},{"x":195,"y":1.7888543819998317},{"x":196,"y":1.4832396974191326},{"x":197,"y":1.4832396974191326},{"x":198,"y":1.4832396974191326},{"x":199,"y":1.6431676725154984},{"x":200,"y":1.6431676725154984},{"x":201,"y":1.6431676725154984},{"x":202,"y":2.280350850198276},{"x":203,"y":2.280350850198276},{"x":204,"y":1.9493588689617927},{"x":205,"y":1.6733200530681511},{"x":206,"y":1.6733200530681511},{"x":207,"y":1.6733200530681511},{"x":208,"y":1.6733200530681511},{"x":209,"y":1.140175425099138},{"x":210,"y":1.140175425099138},{"x":211,"y":1.140175425099138},{"x":212,"y":1.140175425099138},{"x":213,"y":1.816590212458495},{"x":214,"y":1.4832396974191326},{"x":215,"y":1.4832396974191326},{"x":216,"y":1.4832396974191326},{"x":217,"y":1.4832396974191326},{"x":218,"y":1.4832396974191326},{"x":219,"y":1.4832396974191326},{"x":220,"y":1.4832396974191326},{"x":221,"y":1.4142135623730951},{"x":222,"y":1.4142135623730951},{"x":223,"y":1.4142135623730951},{"x":224,"y":1.5811388300841898},{"x":225,"y":1.5811388300841898},{"x":226,"y":1.5811388300841898},{"x":227,"y":1.5811388300841898},{"x":228,"y":1.6431676725154984},{"x":229,"y":1.6431676725154984},{"x":230,"y":1.6431676725154984},{"x":231,"y":1.816590212458495},{"x":232,"y":1.816590212458495},{"x":233,"y":1.816590212458495},{"x":234,"y":2.073644135332772},{"x":235,"y":2.073644135332772},{"x":236,"y":2.3874672772626644},{"x":237,"y":2.3874672772626644},{"x":238,"y":2.3874672772626644},{"x":239,"y":2.3874672772626644},{"x":240,"y":2.3874672772626644},{"x":241,"y":2.3874672772626644},{"x":242,"y":2.6832815729997477},{"x":243,"y":2.449489742783178},{"x":244,"y":2.449489742783178},{"x":245,"y":2.449489742783178},{"x":246,"y":2.449489742783178},{"x":247,"y":2.8284271247461903},{"x":248,"y":2.8284271247461903},{"x":249,"y":3.391164991562634},{"x":250,"y":3.391164991562634},{"x":251,"y":3.391164991562634},{"x":252,"y":3.391164991562634},{"x":253,"y":3.391164991562634},{"x":254,"y":3.082207001484488},{"x":255,"y":2.449489742783178},{"x":256,"y":2.6457513110645907},{"x":257,"y":2.6457513110645907},{"x":258,"y":2.7748873851023217},{"x":259,"y":3.3466401061363023},{"x":260,"y":2.9664793948382653},{"x":261,"y":2.9664793948382653},{"x":262,"y":2.9664793948382653},{"x":263,"y":2.4083189157584592},{"x":264,"y":2.4083189157584592},{"x":265,"y":2.6076809620810595},{"x":266,"y":2.6076809620810595},{"x":267,"y":2.6076809620810595},{"x":268,"y":2.792848008753788},{"x":269,"y":2.792848008753788},{"x":270,"y":2.588435821108957},{"x":271,"y":2.588435821108957},{"x":272,"y":2.8284271247461903},{"x":273,"y":3.391164991562634},{"x":274,"y":3.391164991562634},{"x":275,"y":3.1144823004794873},{"x":276,"y":3.1144823004794873},{"x":277,"y":3.1144823004794873},{"x":278,"y":3.1144823004794873},{"x":279,"y":3.1144823004794873},{"x":280,"y":3.1144823004794873},{"x":281,"y":3.1144823004794873},{"x":282,"y":3.1144823004794873},{"x":283,"y":3.1144823004794873},{"x":284,"y":3.1144823004794873},{"x":285,"y":3.082207001484488},{"x":286,"y":3.082207001484488},{"x":287,"y":3.082207001484488},{"x":288,"y":3.082207001484488},{"x":289,"y":2.7386127875258306},{"x":290,"y":2.7386127875258306},{"x":291,"y":3.082207001484488},{"x":292,"y":3.082207001484488},{"x":293,"y":3.082207001484488},{"x":294,"y":3.1622776601683795},{"x":295,"y":3.1622776601683795},{"x":296,"y":3.1622776601683795},{"x":297,"y":3.03315017762062},{"x":298,"y":3.03315017762062},{"x":299,"y":3.03315017762062}]}],"marks":[{"type":"line","from":{"data":"ea78fc21-4e19-499f-9303-8514c1c15f14"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"stroke":{"value":"Blue"},"strokeWidth":{"value":2},"strokeOpacity":{"value":1}}}},{"type":"line","from":{"data":"0e10cf1b-209d-4334-bc6a-86a636a3d188"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"stroke":{"value":"Red"},"strokeWidth":{"value":2},"strokeOpacity":{"value":1}}}},{"type":"line","from":{"data":"6d866402-a82c-451a-91d6-ef2656a0c269"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"stroke":{"value":"Green"},"strokeWidth":{"value":2},"strokeOpacity":{"value":1}}}}]},"value":"#gorilla_repl.vega.VegaView{:content {:width 400, :height 247.2188, :padding {:top 10, :left 55, :bottom 40, :right 10}, :scales [{:name \"x\", :type \"linear\", :range \"width\", :zero false, :domain {:data \"ea78fc21-4e19-499f-9303-8514c1c15f14\", :field \"data.x\"}} {:name \"y\", :type \"linear\", :range \"height\", :nice true, :zero false, :domain [0.0 12.581732790041283]}], :axes [{:type \"x\", :scale \"x\"} {:type \"y\", :scale \"y\"}], :data ({:name \"ea78fc21-4e19-499f-9303-8514c1c15f14\", :values ({:x 0, :y 0.4472135954999579} {:x 1, :y 0.8944271909999159} {:x 2, :y 0.4472135954999579} {:x 3, :y 0.4472135954999579} {:x 4, :y 0.0} {:x 5, :y 0.0} {:x 6, :y 0.0} {:x 7, :y 0.7071067811865476} {:x 8, :y 0.4472135954999579} {:x 9, :y 0.7071067811865476} {:x 10, :y 0.7071067811865476} {:x 11, :y 0.7071067811865476} {:x 12, :y 0.7071067811865476} {:x 13, :y 0.4472135954999579} {:x 14, :y 1.0954451150103321} {:x 15, :y 1.7888543819998317} {:x 16, :y 1.51657508881031} {:x 17, :y 1.51657508881031} {:x 18, :y 1.51657508881031} {:x 19, :y 1.7888543819998317} {:x 20, :y 1.7888543819998317} {:x 21, :y 2.1213203435596424} {:x 22, :y 2.1213203435596424} {:x 23, :y 2.1213203435596424} {:x 24, :y 2.8284271247461903} {:x 25, :y 2.4899799195977463} {:x 26, :y 2.4899799195977463} {:x 27, :y 3.1937438845342623} {:x 28, :y 3.1937438845342623} {:x 29, :y 2.8809720581775866} {:x 30, :y 2.6076809620810595} {:x 31, :y 2.6076809620810595} {:x 32, :y 2.6076809620810595} {:x 33, :y 2.6076809620810595} {:x 34, :y 2.6076809620810595} {:x 35, :y 2.6076809620810595} {:x 36, :y 2.8809720581775866} {:x 37, :y 2.8809720581775866} {:x 38, :y 2.8809720581775866} {:x 39, :y 3.5777087639996634} {:x 40, :y 3.5777087639996634} {:x 41, :y 3.5777087639996634} {:x 42, :y 3.286335345030997} {:x 43, :y 3.286335345030997} {:x 44, :y 3.03315017762062} {:x 45, :y 3.7013511046643495} {:x 46, :y 3.4641016151377544} {:x 47, :y 3.7013511046643495} {:x 48, :y 3.7013511046643495} {:x 49, :y 3.7013511046643495} {:x 50, :y 4.381780460041329} {:x 51, :y 5.06951674225463} {:x 52, :y 4.795831523312719} {:x 53, :y 4.795831523312719} {:x 54, :y 4.795831523312719} {:x 55, :y 5.477225575051661} {:x 56, :y 5.761944116355173} {:x 57, :y 5.761944116355173} {:x 58, :y 6.457553716385176} {:x 59, :y 7.155417527999327} {:x 60, :y 7.155417527999327} {:x 61, :y 7.155417527999327} {:x 62, :y 6.855654600401044} {:x 63, :y 6.572670690061994} {:x 64, :y 6.572670690061994} {:x 65, :y 6.855654600401044} {:x 66, :y 7.54983443527075} {:x 67, :y 7.54983443527075} {:x 68, :y 8.246211251235321} {:x 69, :y 8.246211251235321} {:x 70, :y 8.246211251235321} {:x 71, :y 8.246211251235321} {:x 72, :y 8.246211251235321} {:x 73, :y 8.555699854482976} {:x 74, :y 9.257429448826494} {:x 75, :y 9.257429448826494} {:x 76, :y 8.94427190999916} {:x 77, :y 8.94427190999916} {:x 78, :y 9.257429448826494} {:x 79, :y 9.581231653602787} {:x 80, :y 9.257429448826494} {:x 81, :y 9.581231653602787} {:x 82, :y 9.581231653602787} {:x 83, :y 9.581231653602787} {:x 84, :y 9.581231653602787} {:x 85, :y 9.581231653602787} {:x 86, :y 9.257429448826494} {:x 87, :y 9.257429448826494} {:x 88, :y 9.257429448826494} {:x 89, :y 9.581231653602787} {:x 90, :y 9.91463564635635} {:x 91, :y 9.91463564635635} {:x 92, :y 9.581231653602787} {:x 93, :y 9.581231653602787} {:x 94, :y 10.285912696499032} {:x 95, :y 9.959919678390985} {:x 96, :y 9.643650760992955} {:x 97, :y 9.959919678390985} {:x 98, :y 9.959919678390985} {:x 99, :y 9.959919678390985} {:x 100, :y 9.959919678390985} {:x 101, :y 9.643650760992955} {:x 102, :y 9.643650760992955} {:x 103, :y 9.643650760992955} {:x 104, :y 9.643650760992955} {:x 105, :y 9.643650760992955} {:x 106, :y 9.643650760992955} {:x 107, :y 9.338094023943002} {:x 108, :y 9.338094023943002} {:x 109, :y 10.03493896344168} {:x 110, :y 10.344080432788601} {:x 111, :y 10.66302021005306} {:x 112, :y 10.344080432788601} {:x 113, :y 11.045361017187261} {:x 114, :y 11.366617790706258} {:x 115, :y 11.366617790706258} {:x 116, :y 11.366617790706258} {:x 117, :y 11.366617790706258} {:x 118, :y 11.696153213770756} {:x 119, :y 11.717508267545622} {:x 120, :y 11.717508267545622} {:x 121, :y 11.717508267545622} {:x 122, :y 11.717508267545622} {:x 123, :y 11.717508267545622} {:x 124, :y 11.691877522451216} {:x 125, :y 11.691877522451216} {:x 126, :y 11.683321445547923} {:x 127, :y 11.683321445547923} {:x 128, :y 11.335784048754634} {:x 129, :y 11.34460224071342} {:x 130, :y 11.34460224071342} {:x 131, :y 11.34460224071342} {:x 132, :y 10.986355173577815} {:x 133, :y 10.986355173577815} {:x 134, :y 10.986355173577815} {:x 135, :y 10.986355173577815} {:x 136, :y 11.013627921806693} {:x 137, :y 11.058933040759403} {:x 138, :y 10.807404868885037} {:x 139, :y 10.807404868885037} {:x 140, :y 10.807404868885037} {:x 141, :y 10.807404868885037} {:x 142, :y 10.807404868885037} {:x 143, :y 10.825894882179487} {:x 144, :y 10.825894882179487} {:x 145, :y 10.825894882179487} {:x 146, :y 10.825894882179487} {:x 147, :y 10.825894882179487} {:x 148, :y 10.825894882179487} {:x 149, :y 10.825894882179487} {:x 150, :y 10.825894882179487} {:x 151, :y 10.425929215182693} {:x 152, :y 10.807404868885037} {:x 153, :y 10.807404868885037} {:x 154, :y 10.597169433391164} {:x 155, :y 10.597169433391164} {:x 156, :y 10.644247272588137} {:x 157, :y 10.644247272588137} {:x 158, :y 10.644247272588137} {:x 159, :y 10.644247272588137} {:x 160, :y 10.644247272588137} {:x 161, :y 10.549881515922348} {:x 162, :y 10.549881515922348} {:x 163, :y 10.549881515922348} {:x 164, :y 10.549881515922348} {:x 165, :y 10.549881515922348} {:x 166, :y 10.549881515922348} {:x 167, :y 10.549881515922348} {:x 168, :y 10.358571330062848} {:x 169, :y 10.358571330062848} {:x 170, :y 10.256705123966467} {:x 171, :y 10.256705123966467} {:x 172, :y 10.256705123966467} {:x 173, :y 10.52140675005011} {:x 174, :y 10.825894882179487} {:x 175, :y 10.825894882179487} {:x 176, :y 10.825894882179487} {:x 177, :y 10.825894882179487} {:x 178, :y 10.497618777608569} {:x 179, :y 10.497618777608569} {:x 180, :y 10.502380682492898} {:x 181, :y 10.502380682492898} {:x 182, :y 10.502380682492898} {:x 183, :y 10.502380682492898} {:x 184, :y 10.502380682492898} {:x 185, :y 10.502380682492898} {:x 186, :y 10.526157893552615} {:x 187, :y 10.549881515922348} {:x 188, :y 10.549881515922348} {:x 189, :y 10.549881515922348} {:x 190, :y 10.597169433391164} {:x 191, :y 10.597169433391164} {:x 192, :y 10.526157893552615} {:x 193, :y 10.526157893552615} {:x 194, :y 10.526157893552615} {:x 195, :y 10.644247272588137} {:x 196, :y 10.644247272588137} {:x 197, :y 10.709808588392232} {:x 198, :y 10.709808588392232} {:x 199, :y 10.709808588392232} {:x 200, :y 10.709808588392232} {:x 201, :y 10.709808588392232} {:x 202, :y 10.709808588392232} {:x 203, :y 10.807404868885037} {:x 204, :y 10.756393447619885} {:x 205, :y 10.723805294763608} {:x 206, :y 10.723805294763608} {:x 207, :y 10.848963084092414} {:x 208, :y 10.497618777608569} {:x 209, :y 10.497618777608569} {:x 210, :y 9.984988733093292} {:x 211, :y 10.66302021005306} {:x 212, :y 10.66302021005306} {:x 213, :y 10.66302021005306} {:x 214, :y 10.66302021005306} {:x 215, :y 10.691117808723277} {:x 216, :y 10.691117808723277} {:x 217, :y 10.691117808723277} {:x 218, :y 10.691117808723277} {:x 219, :y 10.66302021005306} {:x 220, :y 10.66302021005306} {:x 221, :y 10.36822067666386} {:x 222, :y 10.497618777608569} {:x 223, :y 10.497618777608569} {:x 224, :y 10.256705123966467} {:x 225, :y 10.406728592598157} {:x 226, :y 10.406728592598157} {:x 227, :y 10.47377677822093} {:x 228, :y 10.47377677822093} {:x 229, :y 10.47377677822093} {:x 230, :y 10.47377677822093} {:x 231, :y 10.406728592598157} {:x 232, :y 10.644247272588137} {:x 233, :y 10.876580344942981} {:x 234, :y 10.876580344942981} {:x 235, :y 11.148990985734986} {:x 236, :y 11.148990985734986} {:x 237, :y 11.148990985734986} {:x 238, :y 11.458621208504974} {:x 239, :y 11.717508267545622} {:x 240, :y 12.054044964243332} {:x 241, :y 11.674759098157015} {:x 242, :y 11.104053313993049} {:x 243, :y 10.761040841851685} {:x 244, :y 10.761040841851685} {:x 245, :y 10.425929215182693} {:x 246, :y 10.761040841851685} {:x 247, :y 10.761040841851685} {:x 248, :y 10.761040841851685} {:x 249, :y 10.761040841851685} {:x 250, :y 10.761040841851685} {:x 251, :y 10.761040841851685} {:x 252, :y 11.104053313993049} {:x 253, :y 11.104053313993049} {:x 254, :y 11.717508267545622} {:x 255, :y 12.0124934963562} {:x 256, :y 11.631852818876277} {:x 257, :y 11.631852818876277} {:x 258, :y 11.631852818876277} {:x 259, :y 11.081516141756055} {:x 260, :y 10.737783756436894} {:x 261, :y 10.737783756436894} {:x 262, :y 11.081516141756055} {:x 263, :y 11.081516141756055} {:x 264, :y 11.081516141756055} {:x 265, :y 11.081516141756055} {:x 266, :y 11.081516141756055} {:x 267, :y 11.717508267545622} {:x 268, :y 11.717508267545622} {:x 269, :y 11.696153213770756} {:x 270, :y 11.696153213770756} {:x 271, :y 11.882760622010357} {:x 272, :y 11.882760622010357} {:x 273, :y 11.34460224071342} {:x 274, :y 11.0} {:x 275, :y 10.66302021005306} {:x 276, :y 10.66302021005306} {:x 277, :y 11.0} {:x 278, :y 11.0} {:x 279, :y 11.640446726822816} {:x 280, :y 11.640446726822816} {:x 281, :y 11.640446726822816} {:x 282, :y 11.987493482792805} {:x 283, :y 11.987493482792805} {:x 284, :y 11.987493482792805} {:x 285, :y 11.987493482792805} {:x 286, :y 11.987493482792805} {:x 287, :y 11.987493482792805} {:x 288, :y 11.987493482792805} {:x 289, :y 11.987493482792805} {:x 290, :y 12.275992831539126} {:x 291, :y 12.275992831539126} {:x 292, :y 12.165525060596439} {:x 293, :y 11.789826122551595} {:x 294, :y 11.789826122551595} {:x 295, :y 11.510864433221338} {:x 296, :y 11.510864433221338} {:x 297, :y 11.510864433221338} {:x 298, :y 11.789826122551595} {:x 299, :y 11.987493482792805})} {:name \"0e10cf1b-209d-4334-bc6a-86a636a3d188\", :values ({:x 0, :y 0.0} {:x 1, :y 0.0} {:x 2, :y 0.4472135954999579} {:x 3, :y 0.5477225575051661} {:x 4, :y 0.5477225575051661} {:x 5, :y 0.5477225575051661} {:x 6, :y 0.5477225575051661} {:x 7, :y 0.5477225575051661} {:x 8, :y 0.8944271909999159} {:x 9, :y 0.8944271909999159} {:x 10, :y 0.8944271909999159} {:x 11, :y 1.0954451150103321} {:x 12, :y 1.4142135623730951} {:x 13, :y 1.6431676725154984} {:x 14, :y 1.7888543819998317} {:x 15, :y 1.7888543819998317} {:x 16, :y 2.073644135332772} {:x 17, :y 2.073644135332772} {:x 18, :y 2.16794833886788} {:x 19, :y 2.16794833886788} {:x 20, :y 2.4083189157584592} {:x 21, :y 2.4083189157584592} {:x 22, :y 2.073644135332772} {:x 23, :y 2.073644135332772} {:x 24, :y 2.3021728866442674} {:x 25, :y 2.3021728866442674} {:x 26, :y 2.3021728866442674} {:x 27, :y 2.701851217221259} {:x 28, :y 2.701851217221259} {:x 29, :y 2.8809720581775866} {:x 30, :y 3.1144823004794873} {:x 31, :y 3.1144823004794873} {:x 32, :y 3.5637059362410923} {:x 33, :y 3.5637059362410923} {:x 34, :y 3.5355339059327378} {:x 35, :y 3.5355339059327378} {:x 36, :y 3.5355339059327378} {:x 37, :y 3.7013511046643495} {:x 38, :y 3.9115214431215892} {:x 39, :y 3.9115214431215892} {:x 40, :y 3.9115214431215892} {:x 41, :y 3.9115214431215892} {:x 42, :y 4.09878030638384} {:x 43, :y 4.09878030638384} {:x 44, :y 4.324349662087931} {:x 45, :y 4.324349662087931} {:x 46, :y 4.58257569495584} {:x 47, :y 4.58257569495584} {:x 48, :y 4.764451699828638} {:x 49, :y 4.795831523312719} {:x 50, :y 5.1478150704935} {:x 51, :y 5.1478150704935} {:x 52, :y 5.1478150704935} {:x 53, :y 5.1478150704935} {:x 54, :y 5.1478150704935} {:x 55, :y 5.5677643628300215} {:x 56, :y 5.5677643628300215} {:x 57, :y 5.5677643628300215} {:x 58, :y 6.041522986797286} {:x 59, :y 6.557438524302} {:x 60, :y 6.557438524302} {:x 61, :y 6.534523701081817} {:x 62, :y 6.730527468185536} {:x 63, :y 6.730527468185536} {:x 64, :y 6.730527468185536} {:x 65, :y 6.730527468185536} {:x 66, :y 7.26636084983398} {:x 67, :y 7.402702209328699} {:x 68, :y 7.402702209328699} {:x 69, :y 7.26636084983398} {:x 70, :y 7.26636084983398} {:x 71, :y 7.26636084983398} {:x 72, :y 7.26636084983398} {:x 73, :y 7.26636084983398} {:x 74, :y 7.829431652425353} {:x 75, :y 7.92464510246358} {:x 76, :y 7.92464510246358} {:x 77, :y 8.018728078691781} {:x 78, :y 8.018728078691781} {:x 79, :y 8.018728078691781} {:x 80, :y 8.018728078691781} {:x 81, :y 8.018728078691781} {:x 82, :y 8.018728078691781} {:x 83, :y 8.018728078691781} {:x 84, :y 8.018728078691781} {:x 85, :y 8.136338242723197} {:x 86, :y 8.136338242723197} {:x 87, :y 8.018728078691781} {:x 88, :y 8.018728078691781} {:x 89, :y 8.018728078691781} {:x 90, :y 8.018728078691781} {:x 91, :y 8.018728078691781} {:x 92, :y 8.228000972289685} {:x 93, :y 8.228000972289685} {:x 94, :y 8.757853618324527} {:x 95, :y 8.757853618324527} {:x 96, :y 8.972179222463181} {:x 97, :y 8.972179222463181} {:x 98, :y 9.093954035511725} {:x 99, :y 9.364827814754525} {:x 100, :y 9.364827814754525} {:x 101, :y 9.364827814754525} {:x 102, :y 9.364827814754525} {:x 103, :y 9.246621004453464} {:x 104, :y 9.364827814754525} {:x 105, :y 9.418067742376884} {:x 106, :y 9.418067742376884} {:x 107, :y 9.6591925128346} {:x 108, :y 9.6591925128346} {:x 109, :y 10.13903348450926} {:x 110, :y 10.13903348450926} {:x 111, :y 10.13903348450926} {:x 112, :y 10.382677881933928} {:x 113, :y 10.382677881933928} {:x 114, :y 10.382677881933928} {:x 115, :y 10.4546640309481} {:x 116, :y 10.310189135025603} {:x 117, :y 10.310189135025603} {:x 118, :y 10.310189135025603} {:x 119, :y 10.310189135025603} {:x 120, :y 10.261578825892242} {:x 121, :y 10.13903348450926} {:x 122, :y 10.13903348450926} {:x 123, :y 10.114346246792227} {:x 124, :y 10.114346246792227} {:x 125, :y 10.13903348450926} {:x 126, :y 10.13903348450926} {:x 127, :y 10.13903348450926} {:x 128, :y 10.13903348450926} {:x 129, :y 10.13903348450926} {:x 130, :y 10.084641788382967} {:x 131, :y 10.084641788382967} {:x 132, :y 10.084641788382967} {:x 133, :y 10.084641788382967} {:x 134, :y 10.084641788382967} {:x 135, :y 10.124228365658293} {:x 136, :y 10.124228365658293} {:x 137, :y 10.124228365658293} {:x 138, :y 10.124228365658293} {:x 139, :y 10.124228365658293} {:x 140, :y 10.009995004993758} {:x 141, :y 9.964938534682489} {:x 142, :y 9.964938534682489} {:x 143, :y 9.964938534682489} {:x 144, :y 9.939818911831342} {:x 145, :y 9.939818911831342} {:x 146, :y 9.633275663033837} {:x 147, :y 9.628083921528727} {:x 148, :y 9.628083921528727} {:x 149, :y 9.643650760992955} {:x 150, :y 9.643650760992955} {:x 151, :y 9.643650760992955} {:x 152, :y 9.643650760992955} {:x 153, :y 9.654014708917737} {:x 154, :y 9.364827814754525} {:x 155, :y 9.396807968666806} {:x 156, :y 9.396807968666806} {:x 157, :y 9.396807968666806} {:x 158, :y 9.396807968666806} {:x 159, :y 9.396807968666806} {:x 160, :y 9.60728889958036} {:x 161, :y 9.60728889958036} {:x 162, :y 9.60728889958036} {:x 163, :y 9.60728889958036} {:x 164, :y 9.60728889958036} {:x 165, :y 9.60728889958036} {:x 166, :y 9.60728889958036} {:x 167, :y 9.60728889958036} {:x 168, :y 9.60728889958036} {:x 169, :y 9.864076236526156} {:x 170, :y 9.67987603226405} {:x 171, :y 9.67987603226405} {:x 172, :y 9.959919678390985} {:x 173, :y 10.059821071967434} {:x 174, :y 10.207840124139876} {:x 175, :y 10.47377677822093} {:x 176, :y 10.779610382569492} {:x 177, :y 10.96813566655701} {:x 178, :y 10.549881515922348} {:x 179, :y 10.549881515922348} {:x 180, :y 10.549881515922348} {:x 181, :y 10.549881515922348} {:x 182, :y 10.549881515922348} {:x 183, :y 10.549881515922348} {:x 184, :y 10.691117808723277} {:x 185, :y 10.691117808723277} {:x 186, :y 10.691117808723277} {:x 187, :y 10.691117808723277} {:x 188, :y 10.691117808723277} {:x 189, :y 10.691117808723277} {:x 190, :y 10.691117808723277} {:x 191, :y 10.691117808723277} {:x 192, :y 10.497618777608569} {:x 193, :y 10.497618777608569} {:x 194, :y 10.084641788382967} {:x 195, :y 10.084641788382967} {:x 196, :y 10.329569206893384} {:x 197, :y 10.329569206893384} {:x 198, :y 10.319883720275147} {:x 199, :y 10.074720839804943} {:x 200, :y 10.2810505299799} {:x 201, :y 10.2810505299799} {:x 202, :y 10.2810505299799} {:x 203, :y 10.2810505299799} {:x 204, :y 10.2810505299799} {:x 205, :y 10.2810505299799} {:x 206, :y 10.2810505299799} {:x 207, :y 10.2810505299799} {:x 208, :y 9.959919678390985} {:x 209, :y 9.984988733093292} {:x 210, :y 9.984988733093292} {:x 211, :y 10.305338422390601} {:x 212, :y 10.305338422390601} {:x 213, :y 10.305338422390601} {:x 214, :y 10.334408546211051} {:x 215, :y 10.334408546211051} {:x 216, :y 10.334408546211051} {:x 217, :y 10.334408546211051} {:x 218, :y 10.334408546211051} {:x 219, :y 10.158740079360236} {:x 220, :y 10.158740079360236} {:x 221, :y 10.158740079360236} {:x 222, :y 10.158740079360236} {:x 223, :y 10.158740079360236} {:x 224, :y 10.158740079360236} {:x 225, :y 10.158740079360236} {:x 226, :y 10.158740079360236} {:x 227, :y 10.158740079360236} {:x 228, :y 10.158740079360236} {:x 229, :y 10.158740079360236} {:x 230, :y 10.222524150130436} {:x 231, :y 10.222524150130436} {:x 232, :y 10.44030650891055} {:x 233, :y 10.44030650891055} {:x 234, :y 10.2810505299799} {:x 235, :y 10.2810505299799} {:x 236, :y 10.13903348450926} {:x 237, :y 10.114346246792227} {:x 238, :y 10.114346246792227} {:x 239, :y 10.406728592598157} {:x 240, :y 10.406728592598157} {:x 241, :y 10.064790112068906} {:x 242, :y 10.064790112068906} {:x 243, :y 10.064790112068906} {:x 244, :y 10.21273714534943} {:x 245, :y 10.497618777608569} {:x 246, :y 10.497618777608569} {:x 247, :y 10.848963084092414} {:x 248, :y 11.0} {:x 249, :y 11.313708498984761} {:x 250, :y 11.313708498984761} {:x 251, :y 11.476062042355817} {:x 252, :y 11.476062042355817} {:x 253, :y 11.653325705565772} {:x 254, :y 11.653325705565772} {:x 255, :y 11.653325705565772} {:x 256, :y 11.653325705565772} {:x 257, :y 11.691877522451216} {:x 258, :y 11.691877522451216} {:x 259, :y 11.691877522451216} {:x 260, :y 11.691877522451216} {:x 261, :y 11.691877522451216} {:x 262, :y 11.691877522451216} {:x 263, :y 11.388590782006348} {:x 264, :y 11.388590782006348} {:x 265, :y 11.20267825120404} {:x 266, :y 11.388590782006348} {:x 267, :y 11.734564329364767} {:x 268, :y 11.734564329364767} {:x 269, :y 11.734564329364767} {:x 270, :y 11.734564329364767} {:x 271, :y 11.734564329364767} {:x 272, :y 11.734564329364767} {:x 273, :y 11.734564329364767} {:x 274, :y 11.991663771137015} {:x 275, :y 11.991663771137015} {:x 276, :y 11.991663771137015} {:x 277, :y 11.991663771137015} {:x 278, :y 12.177848742696717} {:x 279, :y 12.541929676090518} {:x 280, :y 12.541929676090518} {:x 281, :y 12.541929676090518} {:x 282, :y 12.541929676090518} {:x 283, :y 12.541929676090518} {:x 284, :y 12.541929676090518} {:x 285, :y 12.517987058628874} {:x 286, :y 12.541929676090518} {:x 287, :y 12.541929676090518} {:x 288, :y 12.581732790041283} {:x 289, :y 12.340988615179905} {:x 290, :y 12.340988615179905} {:x 291, :y 12.581732790041283} {:x 292, :y 12.581732790041283} {:x 293, :y 12.300406497347964} {:x 294, :y 12.361229712289955} {:x 295, :y 12.136721138759018} {:x 296, :y 12.31665539016173} {:x 297, :y 12.31665539016173} {:x 298, :y 12.537942414925983} {:x 299, :y 12.537942414925983})} {:name \"6d866402-a82c-451a-91d6-ef2656a0c269\", :values ({:x 0, :y 0.0} {:x 1, :y 0.0} {:x 2, :y 0.0} {:x 3, :y 0.0} {:x 4, :y 0.4472135954999579} {:x 5, :y 0.4472135954999579} {:x 6, :y 0.4472135954999579} {:x 7, :y 0.8366600265340756} {:x 8, :y 0.8366600265340756} {:x 9, :y 0.8366600265340756} {:x 10, :y 0.8944271909999159} {:x 11, :y 0.8944271909999159} {:x 12, :y 0.8944271909999159} {:x 13, :y 0.8944271909999159} {:x 14, :y 0.8944271909999159} {:x 15, :y 1.51657508881031} {:x 16, :y 1.51657508881031} {:x 17, :y 1.6733200530681511} {:x 18, :y 1.6733200530681511} {:x 19, :y 1.6733200530681511} {:x 20, :y 1.6733200530681511} {:x 21, :y 1.6733200530681511} {:x 22, :y 1.9493588689617927} {:x 23, :y 1.816590212458495} {:x 24, :y 1.816590212458495} {:x 25, :y 2.073644135332772} {:x 26, :y 2.073644135332772} {:x 27, :y 2.073644135332772} {:x 28, :y 1.7888543819998317} {:x 29, :y 1.7888543819998317} {:x 30, :y 1.7888543819998317} {:x 31, :y 2.0} {:x 32, :y 1.5811388300841898} {:x 33, :y 1.5811388300841898} {:x 34, :y 1.3038404810405297} {:x 35, :y 1.3038404810405297} {:x 36, :y 1.3038404810405297} {:x 37, :y 1.3038404810405297} {:x 38, :y 1.3038404810405297} {:x 39, :y 1.6431676725154984} {:x 40, :y 1.8708286933869707} {:x 41, :y 2.16794833886788} {:x 42, :y 2.16794833886788} {:x 43, :y 2.1213203435596424} {:x 44, :y 2.1213203435596424} {:x 45, :y 2.449489742783178} {:x 46, :y 2.449489742783178} {:x 47, :y 2.449489742783178} {:x 48, :y 2.449489742783178} {:x 49, :y 2.449489742783178} {:x 50, :y 2.449489742783178} {:x 51, :y 2.9154759474226504} {:x 52, :y 3.03315017762062} {:x 53, :y 3.03315017762062} {:x 54, :y 3.03315017762062} {:x 55, :y 3.03315017762062} {:x 56, :y 3.03315017762062} {:x 57, :y 3.286335345030997} {:x 58, :y 3.286335345030997} {:x 59, :y 3.286335345030997} {:x 60, :y 3.5777087639996634} {:x 61, :y 3.286335345030997} {:x 62, :y 3.286335345030997} {:x 63, :y 3.4351128074635335} {:x 64, :y 3.4351128074635335} {:x 65, :y 3.4351128074635335} {:x 66, :y 3.4351128074635335} {:x 67, :y 3.4351128074635335} {:x 68, :y 3.9115214431215892} {:x 69, :y 3.7682887362833544} {:x 70, :y 3.391164991562634} {:x 71, :y 3.63318042491699} {:x 72, :y 3.286335345030997} {:x 73, :y 3.286335345030997} {:x 74, :y 3.286335345030997} {:x 75, :y 3.361547262794322} {:x 76, :y 3.4351128074635335} {:x 77, :y 3.4351128074635335} {:x 78, :y 3.4351128074635335} {:x 79, :y 3.4351128074635335} {:x 80, :y 3.5637059362410923} {:x 81, :y 3.5637059362410923} {:x 82, :y 3.5637059362410923} {:x 83, :y 3.5637059362410923} {:x 84, :y 3.5637059362410923} {:x 85, :y 3.5637059362410923} {:x 86, :y 3.7416573867739413} {:x 87, :y 3.7416573867739413} {:x 88, :y 3.7416573867739413} {:x 89, :y 3.7416573867739413} {:x 90, :y 3.7416573867739413} {:x 91, :y 3.96232255123179} {:x 92, :y 3.96232255123179} {:x 93, :y 3.96232255123179} {:x 94, :y 3.96232255123179} {:x 95, :y 4.183300132670378} {:x 96, :y 4.183300132670378} {:x 97, :y 4.183300132670378} {:x 98, :y 4.183300132670378} {:x 99, :y 3.7416573867739413} {:x 100, :y 3.7416573867739413} {:x 101, :y 3.96232255123179} {:x 102, :y 3.646916505762094} {:x 103, :y 3.4351128074635335} {:x 104, :y 3.4351128074635335} {:x 105, :y 3.4351128074635335} {:x 106, :y 3.63318042491699} {:x 107, :y 3.63318042491699} {:x 108, :y 3.847076812334269} {:x 109, :y 3.847076812334269} {:x 110, :y 3.847076812334269} {:x 111, :y 3.847076812334269} {:x 112, :y 3.847076812334269} {:x 113, :y 4.277849927241488} {:x 114, :y 4.277849927241488} {:x 115, :y 4.277849927241488} {:x 116, :y 4.08656334834051} {:x 117, :y 3.9749213828703582} {:x 118, :y 3.9749213828703582} {:x 119, :y 3.7815340802378077} {:x 120, :y 3.7815340802378077} {:x 121, :y 3.646916505762094} {:x 122, :y 3.646916505762094} {:x 123, :y 3.5071355833500366} {:x 124, :y 3.5071355833500366} {:x 125, :y 3.5071355833500366} {:x 126, :y 3.5071355833500366} {:x 127, :y 3.5071355833500366} {:x 128, :y 3.2093613071762426} {:x 129, :y 3.2093613071762426} {:x 130, :y 3.2093613071762426} {:x 131, :y 3.2093613071762426} {:x 132, :y 3.2093613071762426} {:x 133, :y 3.2093613071762426} {:x 134, :y 3.2093613071762426} {:x 135, :y 3.2093613071762426} {:x 136, :y 3.2093613071762426} {:x 137, :y 3.2093613071762426} {:x 138, :y 3.1304951684997055} {:x 139, :y 3.1304951684997055} {:x 140, :y 3.2093613071762426} {:x 141, :y 3.2093613071762426} {:x 142, :y 3.2093613071762426} {:x 143, :y 3.2093613071762426} {:x 144, :y 3.2093613071762426} {:x 145, :y 3.1304951684997055} {:x 146, :y 3.2093613071762426} {:x 147, :y 3.2093613071762426} {:x 148, :y 3.2093613071762426} {:x 149, :y 3.2093613071762426} {:x 150, :y 3.2093613071762426} {:x 151, :y 2.701851217221259} {:x 152, :y 2.701851217221259} {:x 153, :y 2.701851217221259} {:x 154, :y 2.701851217221259} {:x 155, :y 2.701851217221259} {:x 156, :y 2.701851217221259} {:x 157, :y 2.3021728866442674} {:x 158, :y 2.3021728866442674} {:x 159, :y 2.073644135332772} {:x 160, :y 2.073644135332772} {:x 161, :y 1.9235384061671346} {:x 162, :y 1.9235384061671346} {:x 163, :y 2.0} {:x 164, :y 2.0} {:x 165, :y 1.7888543819998317} {:x 166, :y 1.7888543819998317} {:x 167, :y 1.7888543819998317} {:x 168, :y 1.6431676725154984} {:x 169, :y 1.6431676725154984} {:x 170, :y 1.6431676725154984} {:x 171, :y 1.224744871391589} {:x 172, :y 1.224744871391589} {:x 173, :y 1.224744871391589} {:x 174, :y 1.224744871391589} {:x 175, :y 1.224744871391589} {:x 176, :y 1.224744871391589} {:x 177, :y 1.224744871391589} {:x 178, :y 1.224744871391589} {:x 179, :y 1.4142135623730951} {:x 180, :y 1.4142135623730951} {:x 181, :y 1.4142135623730951} {:x 182, :y 1.4832396974191326} {:x 183, :y 1.4142135623730951} {:x 184, :y 1.4142135623730951} {:x 185, :y 1.4142135623730951} {:x 186, :y 1.4142135623730951} {:x 187, :y 1.4142135623730951} {:x 188, :y 1.4142135623730951} {:x 189, :y 1.8708286933869707} {:x 190, :y 1.8708286933869707} {:x 191, :y 1.8708286933869707} {:x 192, :y 1.8708286933869707} {:x 193, :y 2.04939015319192} {:x 194, :y 1.7888543819998317} {:x 195, :y 1.7888543819998317} {:x 196, :y 1.4832396974191326} {:x 197, :y 1.4832396974191326} {:x 198, :y 1.4832396974191326} {:x 199, :y 1.6431676725154984} {:x 200, :y 1.6431676725154984} {:x 201, :y 1.6431676725154984} {:x 202, :y 2.280350850198276} {:x 203, :y 2.280350850198276} {:x 204, :y 1.9493588689617927} {:x 205, :y 1.6733200530681511} {:x 206, :y 1.6733200530681511} {:x 207, :y 1.6733200530681511} {:x 208, :y 1.6733200530681511} {:x 209, :y 1.140175425099138} {:x 210, :y 1.140175425099138} {:x 211, :y 1.140175425099138} {:x 212, :y 1.140175425099138} {:x 213, :y 1.816590212458495} {:x 214, :y 1.4832396974191326} {:x 215, :y 1.4832396974191326} {:x 216, :y 1.4832396974191326} {:x 217, :y 1.4832396974191326} {:x 218, :y 1.4832396974191326} {:x 219, :y 1.4832396974191326} {:x 220, :y 1.4832396974191326} {:x 221, :y 1.4142135623730951} {:x 222, :y 1.4142135623730951} {:x 223, :y 1.4142135623730951} {:x 224, :y 1.5811388300841898} {:x 225, :y 1.5811388300841898} {:x 226, :y 1.5811388300841898} {:x 227, :y 1.5811388300841898} {:x 228, :y 1.6431676725154984} {:x 229, :y 1.6431676725154984} {:x 230, :y 1.6431676725154984} {:x 231, :y 1.816590212458495} {:x 232, :y 1.816590212458495} {:x 233, :y 1.816590212458495} {:x 234, :y 2.073644135332772} {:x 235, :y 2.073644135332772} {:x 236, :y 2.3874672772626644} {:x 237, :y 2.3874672772626644} {:x 238, :y 2.3874672772626644} {:x 239, :y 2.3874672772626644} {:x 240, :y 2.3874672772626644} {:x 241, :y 2.3874672772626644} {:x 242, :y 2.6832815729997477} {:x 243, :y 2.449489742783178} {:x 244, :y 2.449489742783178} {:x 245, :y 2.449489742783178} {:x 246, :y 2.449489742783178} {:x 247, :y 2.8284271247461903} {:x 248, :y 2.8284271247461903} {:x 249, :y 3.391164991562634} {:x 250, :y 3.391164991562634} {:x 251, :y 3.391164991562634} {:x 252, :y 3.391164991562634} {:x 253, :y 3.391164991562634} {:x 254, :y 3.082207001484488} {:x 255, :y 2.449489742783178} {:x 256, :y 2.6457513110645907} {:x 257, :y 2.6457513110645907} {:x 258, :y 2.7748873851023217} {:x 259, :y 3.3466401061363023} {:x 260, :y 2.9664793948382653} {:x 261, :y 2.9664793948382653} {:x 262, :y 2.9664793948382653} {:x 263, :y 2.4083189157584592} {:x 264, :y 2.4083189157584592} {:x 265, :y 2.6076809620810595} {:x 266, :y 2.6076809620810595} {:x 267, :y 2.6076809620810595} {:x 268, :y 2.792848008753788} {:x 269, :y 2.792848008753788} {:x 270, :y 2.588435821108957} {:x 271, :y 2.588435821108957} {:x 272, :y 2.8284271247461903} {:x 273, :y 3.391164991562634} {:x 274, :y 3.391164991562634} {:x 275, :y 3.1144823004794873} {:x 276, :y 3.1144823004794873} {:x 277, :y 3.1144823004794873} {:x 278, :y 3.1144823004794873} {:x 279, :y 3.1144823004794873} {:x 280, :y 3.1144823004794873} {:x 281, :y 3.1144823004794873} {:x 282, :y 3.1144823004794873} {:x 283, :y 3.1144823004794873} {:x 284, :y 3.1144823004794873} {:x 285, :y 3.082207001484488} {:x 286, :y 3.082207001484488} {:x 287, :y 3.082207001484488} {:x 288, :y 3.082207001484488} {:x 289, :y 2.7386127875258306} {:x 290, :y 2.7386127875258306} {:x 291, :y 3.082207001484488} {:x 292, :y 3.082207001484488} {:x 293, :y 3.082207001484488} {:x 294, :y 3.1622776601683795} {:x 295, :y 3.1622776601683795} {:x 296, :y 3.1622776601683795} {:x 297, :y 3.03315017762062} {:x 298, :y 3.03315017762062} {:x 299, :y 3.03315017762062})}), :marks ({:type \"line\", :from {:data \"ea78fc21-4e19-499f-9303-8514c1c15f14\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :stroke {:value \"Blue\"}, :strokeWidth {:value 2}, :strokeOpacity {:value 1}}}} {:type \"line\", :from {:data \"0e10cf1b-209d-4334-bc6a-86a636a3d188\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :stroke {:value \"Red\"}, :strokeWidth {:value 2}, :strokeOpacity {:value 1}}}} {:type \"line\", :from {:data \"6d866402-a82c-451a-91d6-ef2656a0c269\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :stroke {:value \"Green\"}, :strokeWidth {:value 2}, :strokeOpacity {:value 1}}}})}}"}
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
