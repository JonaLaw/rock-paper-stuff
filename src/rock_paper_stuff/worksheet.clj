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
  (:use [rock-paper-stuff util play player-functions]
        [clojure.pprint]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

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
;;; {"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:survivors</span>","value":":survivors"},{"type":"html","content":"<span class='clj-unkown'>2</span>","value":"2"}],"value":"[:survivors 2]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:winner</span>","value":":winner"},{"type":"html","content":"<span class='clj-string'>&quot;Sandy&quot;</span>","value":"\"Sandy\""}],"value":"[:winner \"Sandy\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:summary</span>","value":":summary"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:name</span>","value":":name"},{"type":"html","content":"<span class='clj-string'>&quot;Sandy&quot;</span>","value":"\"Sandy\""}],"value":"[:name \"Sandy\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:deviance</span>","value":":deviance"},{"type":"html","content":"<span class='clj-double'>2.8809720581775866</span>","value":"2.8809720581775866"}],"value":"[:deviance 2.8809720581775866]"}],"value":"{:name \"Sandy\", :deviance 2.8809720581775866}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:name</span>","value":":name"},{"type":"html","content":"<span class='clj-string'>&quot;Randy&quot;</span>","value":"\"Randy\""}],"value":"[:name \"Randy\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:deviance</span>","value":":deviance"},{"type":"html","content":"<span class='clj-double'>5.504543577809154</span>","value":"5.504543577809154"}],"value":"[:deviance 5.504543577809154]"}],"value":"{:name \"Randy\", :deviance 5.504543577809154}"}],"value":"[{:name \"Sandy\", :deviance 2.8809720581775866} {:name \"Randy\", :deviance 5.504543577809154}]"}],"value":"[:summary [{:name \"Sandy\", :deviance 2.8809720581775866} {:name \"Randy\", :deviance 5.504543577809154}]]"}],"value":"{:survivors 2, :winner \"Sandy\", :summary [{:name \"Sandy\", :deviance 2.8809720581775866} {:name \"Randy\", :deviance 5.504543577809154}]}"}
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
;;;  :winner &quot;Sandy&quot;,
;;;  :summary
;;;  [{:name &quot;Sandy&quot;, :deviance 3.2093613071762426}
;;;   {:name &quot;Randy&quot;, :deviance 4.39317652729776}]}
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
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:name</span>","value":":name"},{"type":"html","content":"<span class='clj-string'>&quot;Randy&quot;</span>","value":"\"Randy\""}],"value":"[:name \"Randy\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:deviance</span>","value":":deviance"},{"type":"html","content":"<span class='clj-double'>4.43846820423443</span>","value":"4.43846820423443"}],"value":"[:deviance 4.43846820423443]"}],"value":"{:name \"Randy\", :deviance 4.43846820423443}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:name</span>","value":":name"},{"type":"html","content":"<span class='clj-string'>&quot;Sandy&quot;</span>","value":"\"Sandy\""}],"value":"[:name \"Sandy\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:deviance</span>","value":":deviance"},{"type":"html","content":"<span class='clj-double'>5.630275304103699</span>","value":"5.630275304103699"}],"value":"[:deviance 5.630275304103699]"}],"value":"{:name \"Sandy\", :deviance 5.630275304103699}"}],"value":"[{:name \"Randy\", :deviance 4.43846820423443} {:name \"Sandy\", :deviance 5.630275304103699}]"}
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
;;; Randy inventory: {:fire 10, :paper 10, :scissors 10, :water 10, :rock 10}
;;; Sandy inventory: {:fire 10, :paper 10, :scissors 10, :water 10, :rock 10}
;;; Sandy plays :paper  -  Randy plays :fire
;;; Randy inventory: {:fire 11, :paper 10, :scissors 10, :water 10, :rock 10}
;;; Sandy inventory: {:fire 10, :paper 9, :scissors 10, :water 10, :rock 10}
;;; Sandy plays :fire  -  Randy plays :scissors
;;; Randy inventory: {:fire 11, :paper 10, :scissors 11, :water 10, :rock 10}
;;; Sandy inventory: {:fire 10, :paper 9, :scissors 10, :water 10, :rock 10}
;;; Randy plays :fire  -  Sandy plays :paper
;;; Randy inventory: {:fire 12, :paper 10, :scissors 11, :water 10, :rock 10}
;;; Sandy inventory: {:fire 10, :paper 8, :scissors 10, :water 10, :rock 10}
;;; Randy plays :scissors  -  Sandy plays :fire
;;; Randy inventory: {:fire 12, :paper 10, :scissors 12, :water 10, :rock 10}
;;; Sandy inventory: {:fire 10, :paper 8, :scissors 10, :water 10, :rock 10}
;;; Randy plays :water  -  Sandy plays :paper
;;; Randy inventory: {:fire 12, :paper 10, :scissors 12, :water 9, :rock 10}
;;; Sandy inventory: {:fire 10, :paper 8, :scissors 10, :water 10, :rock 10}
;;; Randy plays :rock  -  Sandy plays :rock
;;; Randy inventory: {:fire 12, :paper 10, :scissors 12, :water 9, :rock 10}
;;; Sandy inventory: {:fire 10, :paper 8, :scissors 10, :water 10, :rock 10}
;;; Sandy plays :fire  -  Randy plays :water
;;; Randy inventory: {:fire 12, :paper 10, :scissors 12, :water 9, :rock 10}
;;; Sandy inventory: {:fire 9, :paper 8, :scissors 10, :water 10, :rock 10}
;;; Sandy plays :rock  -  Randy plays :fire
;;; Randy inventory: {:fire 11, :paper 10, :scissors 13, :water 9, :rock 10}
;;; Sandy inventory: {:fire 9, :paper 8, :scissors 10, :water 10, :rock 10}
;;; Sandy plays :fire  -  Randy plays :fire
;;; Randy inventory: {:fire 11, :paper 10, :scissors 13, :water 9, :rock 10}
;;; Sandy inventory: {:fire 9, :paper 8, :scissors 10, :water 10, :rock 10}
;;; Sandy plays :water  -  Randy plays :paper
;;; Randy inventory: {:fire 11, :paper 10, :scissors 13, :water 9, :rock 10}
;;; Sandy inventory: {:fire 9, :paper 8, :scissors 10, :water 9, :rock 10}
;;; Randy plays :fire  -  Sandy plays :rock
;;; Randy inventory: {:fire 10, :paper 10, :scissors 14, :water 9, :rock 10}
;;; Sandy inventory: {:fire 9, :paper 8, :scissors 10, :water 9, :rock 10}
;;; Randy plays :fire  -  Sandy plays :rock
;;; Randy inventory: {:fire 9, :paper 10, :scissors 15, :water 9, :rock 10}
;;; Sandy inventory: {:fire 9, :paper 8, :scissors 10, :water 9, :rock 10}
;;; Randy plays :scissors  -  Sandy plays :scissors
;;; Randy inventory: {:fire 9, :paper 10, :scissors 15, :water 9, :rock 10}
;;; Sandy inventory: {:fire 9, :paper 8, :scissors 10, :water 9, :rock 10}
;;; Sandy plays :rock  -  Randy plays :scissors
;;; Randy inventory: {:fire 9, :paper 10, :scissors 14, :water 9, :rock 11}
;;; Sandy inventory: {:fire 10, :paper 8, :scissors 10, :water 9, :rock 9}
;;; Sandy plays :fire  -  Randy plays :water
;;; Randy inventory: {:fire 9, :paper 10, :scissors 14, :water 9, :rock 11}
;;; Sandy inventory: {:fire 9, :paper 8, :scissors 10, :water 9, :rock 9}
;;; Sandy plays :water  -  Randy plays :scissors
;;; Randy inventory: {:fire 9, :paper 10, :scissors 13, :water 10, :rock 11}
;;; Sandy inventory: {:fire 9, :paper 8, :scissors 10, :water 8, :rock 10}
;;; Sandy plays :rock  -  Randy plays :fire
;;; Randy inventory: {:fire 8, :paper 10, :scissors 14, :water 10, :rock 11}
;;; Sandy inventory: {:fire 9, :paper 8, :scissors 10, :water 8, :rock 10}
;;; Randy plays :scissors  -  Sandy plays :water
;;; Randy inventory: {:fire 8, :paper 10, :scissors 13, :water 11, :rock 11}
;;; Sandy inventory: {:fire 9, :paper 8, :scissors 10, :water 7, :rock 11}
;;; Randy plays :rock  -  Sandy plays :water
;;; Randy inventory: {:fire 8, :paper 10, :scissors 13, :water 11, :rock 10}
;;; Sandy inventory: {:fire 9, :paper 8, :scissors 10, :water 8, :rock 11}
;;; Sandy plays :scissors  -  Randy plays :rock
;;; Randy inventory: {:fire 9, :paper 10, :scissors 13, :water 11, :rock 9}
;;; Sandy inventory: {:fire 9, :paper 8, :scissors 9, :water 8, :rock 12}
;;; Randy plays :fire  -  Sandy plays :scissors
;;; Randy inventory: {:fire 9, :paper 10, :scissors 13, :water 11, :rock 9}
;;; Sandy inventory: {:fire 9, :paper 8, :scissors 10, :water 8, :rock 12}
;;; Randy plays :fire  -  Sandy plays :paper
;;; Randy inventory: {:fire 10, :paper 10, :scissors 13, :water 11, :rock 9}
;;; Sandy inventory: {:fire 9, :paper 7, :scissors 10, :water 8, :rock 12}
;;; Randy plays :rock  -  Sandy plays :water
;;; Randy inventory: {:fire 10, :paper 10, :scissors 13, :water 11, :rock 8}
;;; Sandy inventory: {:fire 9, :paper 7, :scissors 10, :water 9, :rock 12}
;;; Randy plays :paper  -  Sandy plays :scissors
;;; Randy inventory: {:fire 10, :paper 11, :scissors 13, :water 11, :rock 8}
;;; Sandy inventory: {:fire 9, :paper 7, :scissors 10, :water 9, :rock 12}
;;; Randy plays :scissors  -  Sandy plays :paper
;;; Randy inventory: {:fire 10, :paper 11, :scissors 13, :water 11, :rock 8}
;;; Sandy inventory: {:fire 9, :paper 8, :scissors 10, :water 9, :rock 12}
;;; Sandy plays :rock  -  Randy plays :water
;;; Randy inventory: {:fire 10, :paper 11, :scissors 13, :water 12, :rock 8}
;;; Sandy inventory: {:fire 9, :paper 8, :scissors 10, :water 9, :rock 11}
;;; Sandy plays :paper  -  Randy plays :water
;;; Randy inventory: {:fire 10, :paper 11, :scissors 13, :water 11, :rock 8}
;;; Sandy inventory: {:fire 9, :paper 8, :scissors 10, :water 9, :rock 11}
;;; Sandy plays :scissors  -  Randy plays :water
;;; Randy inventory: {:fire 10, :paper 11, :scissors 13, :water 10, :rock 9}
;;; Sandy inventory: {:fire 9, :paper 8, :scissors 9, :water 10, :rock 11}
;;; Randy plays :fire  -  Sandy plays :paper
;;; Randy inventory: {:fire 11, :paper 11, :scissors 13, :water 10, :rock 9}
;;; Sandy inventory: {:fire 9, :paper 7, :scissors 9, :water 10, :rock 11}
;;; Randy plays :scissors  -  Sandy plays :scissors
;;; Randy inventory: {:fire 11, :paper 11, :scissors 13, :water 10, :rock 9}
;;; Sandy inventory: {:fire 9, :paper 7, :scissors 9, :water 10, :rock 11}
;;; Sandy plays :fire  -  Randy plays :paper
;;; Randy inventory: {:fire 11, :paper 10, :scissors 13, :water 10, :rock 9}
;;; Sandy inventory: {:fire 10, :paper 7, :scissors 9, :water 10, :rock 11}
;;; Sandy plays :water  -  Randy plays :scissors
;;; Randy inventory: {:fire 11, :paper 10, :scissors 12, :water 11, :rock 9}
;;; Sandy inventory: {:fire 10, :paper 7, :scissors 9, :water 9, :rock 12}
;;; Sandy plays :water  -  Randy plays :water
;;; Randy inventory: {:fire 11, :paper 10, :scissors 12, :water 11, :rock 9}
;;; Sandy inventory: {:fire 10, :paper 7, :scissors 9, :water 9, :rock 12}
;;; Randy plays :scissors  -  Sandy plays :rock
;;; Randy inventory: {:fire 11, :paper 10, :scissors 11, :water 11, :rock 10}
;;; Sandy inventory: {:fire 11, :paper 7, :scissors 9, :water 9, :rock 11}
;;; Sandy plays :rock  -  Randy plays :water
;;; Randy inventory: {:fire 11, :paper 10, :scissors 11, :water 12, :rock 10}
;;; Sandy inventory: {:fire 11, :paper 7, :scissors 9, :water 9, :rock 10}
;;; Sandy plays :rock  -  Randy plays :paper
;;; Randy inventory: {:fire 11, :paper 9, :scissors 11, :water 12, :rock 11}
;;; Sandy inventory: {:fire 11, :paper 8, :scissors 9, :water 9, :rock 9}
;;; Sandy plays :paper  -  Randy plays :paper
;;; Randy inventory: {:fire 11, :paper 9, :scissors 11, :water 12, :rock 11}
;;; Sandy inventory: {:fire 11, :paper 8, :scissors 9, :water 9, :rock 9}
;;; Sandy plays :paper  -  Randy plays :fire
;;; Randy inventory: {:fire 12, :paper 9, :scissors 11, :water 12, :rock 11}
;;; Sandy inventory: {:fire 11, :paper 7, :scissors 9, :water 9, :rock 9}
;;; Randy plays :fire  -  Sandy plays :scissors
;;; Randy inventory: {:fire 12, :paper 9, :scissors 11, :water 12, :rock 11}
;;; Sandy inventory: {:fire 11, :paper 7, :scissors 10, :water 9, :rock 9}
;;; Randy plays :rock  -  Sandy plays :rock
;;; Randy inventory: {:fire 12, :paper 9, :scissors 11, :water 12, :rock 11}
;;; Sandy inventory: {:fire 11, :paper 7, :scissors 10, :water 9, :rock 9}
;;; Randy plays :water  -  Sandy plays :fire
;;; Randy inventory: {:fire 12, :paper 9, :scissors 11, :water 12, :rock 11}
;;; Sandy inventory: {:fire 10, :paper 7, :scissors 10, :water 9, :rock 9}
;;; Randy plays :fire  -  Sandy plays :rock
;;; Randy inventory: {:fire 11, :paper 9, :scissors 12, :water 12, :rock 11}
;;; Sandy inventory: {:fire 10, :paper 7, :scissors 10, :water 9, :rock 9}
;;; Randy plays :scissors  -  Sandy plays :rock
;;; Randy inventory: {:fire 11, :paper 9, :scissors 11, :water 12, :rock 12}
;;; Sandy inventory: {:fire 11, :paper 7, :scissors 10, :water 9, :rock 8}
;;; Randy plays :water  -  Sandy plays :fire
;;; Randy inventory: {:fire 11, :paper 9, :scissors 11, :water 12, :rock 12}
;;; Sandy inventory: {:fire 10, :paper 7, :scissors 10, :water 9, :rock 8}
;;; Sandy plays :rock  -  Randy plays :fire
;;; Randy inventory: {:fire 10, :paper 9, :scissors 12, :water 12, :rock 12}
;;; Sandy inventory: {:fire 10, :paper 7, :scissors 10, :water 9, :rock 8}
;;; Randy plays :paper  -  Sandy plays :fire
;;; Randy inventory: {:fire 10, :paper 8, :scissors 12, :water 12, :rock 12}
;;; Sandy inventory: {:fire 11, :paper 7, :scissors 10, :water 9, :rock 8}
;;; Randy plays :fire  -  Sandy plays :fire
;;; Randy inventory: {:fire 10, :paper 8, :scissors 12, :water 12, :rock 12}
;;; Sandy inventory: {:fire 11, :paper 7, :scissors 10, :water 9, :rock 8}
;;; Sandy plays :scissors  -  Randy plays :paper
;;; Randy inventory: {:fire 10, :paper 9, :scissors 12, :water 12, :rock 12}
;;; Sandy inventory: {:fire 11, :paper 7, :scissors 10, :water 9, :rock 8}
;;; Randy plays :scissors  -  Sandy plays :paper
;;; Randy inventory: {:fire 10, :paper 9, :scissors 12, :water 12, :rock 12}
;;; Sandy inventory: {:fire 11, :paper 8, :scissors 10, :water 9, :rock 8}
;;; Randy plays :rock  -  Sandy plays :scissors
;;; Randy inventory: {:fire 11, :paper 9, :scissors 12, :water 12, :rock 11}
;;; Sandy inventory: {:fire 11, :paper 8, :scissors 9, :water 9, :rock 9}
;;; Randy plays :fire  -  Sandy plays :scissors
;;; Randy inventory: {:fire 11, :paper 9, :scissors 12, :water 12, :rock 11}
;;; Sandy inventory: {:fire 11, :paper 8, :scissors 10, :water 9, :rock 9}
;;; Sandy plays :fire  -  Randy plays :paper
;;; Randy inventory: {:fire 11, :paper 8, :scissors 12, :water 12, :rock 11}
;;; Sandy inventory: {:fire 12, :paper 8, :scissors 10, :water 9, :rock 9}
;;; Randy plays :scissors  -  Sandy plays :water
;;; Randy inventory: {:fire 11, :paper 8, :scissors 11, :water 13, :rock 11}
;;; Sandy inventory: {:fire 12, :paper 8, :scissors 10, :water 8, :rock 10}
;;; Randy plays :water  -  Sandy plays :water
;;; Randy inventory: {:fire 11, :paper 8, :scissors 11, :water 13, :rock 11}
;;; Sandy inventory: {:fire 12, :paper 8, :scissors 10, :water 8, :rock 10}
;;; Sandy plays :scissors  -  Randy plays :water
;;; Randy inventory: {:fire 11, :paper 8, :scissors 11, :water 12, :rock 12}
;;; Sandy inventory: {:fire 12, :paper 8, :scissors 9, :water 9, :rock 10}
;;; Sandy plays :paper  -  Randy plays :paper
;;; Randy inventory: {:fire 11, :paper 8, :scissors 11, :water 12, :rock 12}
;;; Sandy inventory: {:fire 12, :paper 8, :scissors 9, :water 9, :rock 10}
;;; Sandy plays :water  -  Randy plays :rock
;;; Randy inventory: {:fire 11, :paper 8, :scissors 11, :water 12, :rock 11}
;;; Sandy inventory: {:fire 12, :paper 8, :scissors 9, :water 10, :rock 10}
;;; Randy plays :paper  -  Sandy plays :water
;;; Randy inventory: {:fire 11, :paper 8, :scissors 11, :water 12, :rock 11}
;;; Sandy inventory: {:fire 12, :paper 8, :scissors 9, :water 9, :rock 10}
;;; Randy plays :water  -  Sandy plays :rock
;;; Randy inventory: {:fire 11, :paper 8, :scissors 11, :water 13, :rock 11}
;;; Sandy inventory: {:fire 12, :paper 8, :scissors 9, :water 9, :rock 9}
;;; Sandy plays :rock  -  Randy plays :water
;;; Randy inventory: {:fire 11, :paper 8, :scissors 11, :water 14, :rock 11}
;;; Sandy inventory: {:fire 12, :paper 8, :scissors 9, :water 9, :rock 8}
;;; Randy plays :fire  -  Sandy plays :paper
;;; Randy inventory: {:fire 12, :paper 8, :scissors 11, :water 14, :rock 11}
;;; Sandy inventory: {:fire 12, :paper 7, :scissors 9, :water 9, :rock 8}
;;; Randy plays :rock  -  Sandy plays :rock
;;; Randy inventory: {:fire 12, :paper 8, :scissors 11, :water 14, :rock 11}
;;; Sandy inventory: {:fire 12, :paper 7, :scissors 9, :water 9, :rock 8}
;;; Randy plays :rock  -  Sandy plays :rock
;;; Randy inventory: {:fire 12, :paper 8, :scissors 11, :water 14, :rock 11}
;;; Sandy inventory: {:fire 12, :paper 7, :scissors 9, :water 9, :rock 8}
;;; Sandy plays :paper  -  Randy plays :fire
;;; Randy inventory: {:fire 13, :paper 8, :scissors 11, :water 14, :rock 11}
;;; Sandy inventory: {:fire 12, :paper 6, :scissors 9, :water 9, :rock 8}
;;; Sandy plays :scissors  -  Randy plays :water
;;; Randy inventory: {:fire 13, :paper 8, :scissors 11, :water 13, :rock 12}
;;; Sandy inventory: {:fire 12, :paper 6, :scissors 8, :water 10, :rock 8}
;;; Randy plays :water  -  Sandy plays :water
;;; Randy inventory: {:fire 13, :paper 8, :scissors 11, :water 13, :rock 12}
;;; Sandy inventory: {:fire 12, :paper 6, :scissors 8, :water 10, :rock 8}
;;; Sandy plays :fire  -  Randy plays :water
;;; Randy inventory: {:fire 13, :paper 8, :scissors 11, :water 13, :rock 12}
;;; Sandy inventory: {:fire 11, :paper 6, :scissors 8, :water 10, :rock 8}
;;; Randy plays :rock  -  Sandy plays :water
;;; Randy inventory: {:fire 13, :paper 8, :scissors 11, :water 13, :rock 11}
;;; Sandy inventory: {:fire 11, :paper 6, :scissors 8, :water 11, :rock 8}
;;; Sandy plays :scissors  -  Randy plays :water
;;; Randy inventory: {:fire 13, :paper 8, :scissors 11, :water 12, :rock 12}
;;; Sandy inventory: {:fire 11, :paper 6, :scissors 7, :water 12, :rock 8}
;;; Randy plays :scissors  -  Sandy plays :fire
;;; Randy inventory: {:fire 13, :paper 8, :scissors 12, :water 12, :rock 12}
;;; Sandy inventory: {:fire 11, :paper 6, :scissors 7, :water 12, :rock 8}
;;; Sandy plays :paper  -  Randy plays :paper
;;; Randy inventory: {:fire 13, :paper 8, :scissors 12, :water 12, :rock 12}
;;; Sandy inventory: {:fire 11, :paper 6, :scissors 7, :water 12, :rock 8}
;;; Sandy plays :paper  -  Randy plays :rock
;;; Randy inventory: {:fire 13, :paper 9, :scissors 12, :water 12, :rock 11}
;;; Sandy inventory: {:fire 11, :paper 5, :scissors 7, :water 12, :rock 9}
;;; Randy plays :fire  -  Sandy plays :scissors
;;; Randy inventory: {:fire 13, :paper 9, :scissors 12, :water 12, :rock 11}
;;; Sandy inventory: {:fire 11, :paper 5, :scissors 8, :water 12, :rock 9}
;;; Sandy plays :scissors  -  Randy plays :water
;;; Randy inventory: {:fire 13, :paper 9, :scissors 12, :water 11, :rock 12}
;;; Sandy inventory: {:fire 11, :paper 5, :scissors 7, :water 13, :rock 9}
;;; Randy plays :fire  -  Sandy plays :fire
;;; Randy inventory: {:fire 13, :paper 9, :scissors 12, :water 11, :rock 12}
;;; Sandy inventory: {:fire 11, :paper 5, :scissors 7, :water 13, :rock 9}
;;; Sandy plays :paper  -  Randy plays :paper
;;; Randy inventory: {:fire 13, :paper 9, :scissors 12, :water 11, :rock 12}
;;; Sandy inventory: {:fire 11, :paper 5, :scissors 7, :water 13, :rock 9}
;;; Randy plays :paper  -  Sandy plays :paper
;;; Randy inventory: {:fire 13, :paper 9, :scissors 12, :water 11, :rock 12}
;;; Sandy inventory: {:fire 11, :paper 5, :scissors 7, :water 13, :rock 9}
;;; Sandy plays :scissors  -  Randy plays :paper
;;; Randy inventory: {:fire 13, :paper 10, :scissors 12, :water 11, :rock 12}
;;; Sandy inventory: {:fire 11, :paper 5, :scissors 7, :water 13, :rock 9}
;;; Sandy plays :water  -  Randy plays :rock
;;; Randy inventory: {:fire 13, :paper 10, :scissors 12, :water 11, :rock 11}
;;; Sandy inventory: {:fire 11, :paper 5, :scissors 7, :water 14, :rock 9}
;;; Sandy plays :scissors  -  Randy plays :paper
;;; Randy inventory: {:fire 13, :paper 11, :scissors 12, :water 11, :rock 11}
;;; Sandy inventory: {:fire 11, :paper 5, :scissors 7, :water 14, :rock 9}
;;; Sandy plays :fire  -  Randy plays :scissors
;;; Randy inventory: {:fire 13, :paper 11, :scissors 13, :water 11, :rock 11}
;;; Sandy inventory: {:fire 11, :paper 5, :scissors 7, :water 14, :rock 9}
;;; Sandy plays :water  -  Randy plays :scissors
;;; Randy inventory: {:fire 13, :paper 11, :scissors 12, :water 12, :rock 11}
;;; Sandy inventory: {:fire 11, :paper 5, :scissors 7, :water 13, :rock 10}
;;; Randy plays :rock  -  Sandy plays :paper
;;; Randy inventory: {:fire 13, :paper 12, :scissors 12, :water 12, :rock 10}
;;; Sandy inventory: {:fire 11, :paper 4, :scissors 7, :water 13, :rock 11}
;;; Randy plays :water  -  Sandy plays :fire
;;; Randy inventory: {:fire 13, :paper 12, :scissors 12, :water 12, :rock 10}
;;; Sandy inventory: {:fire 10, :paper 4, :scissors 7, :water 13, :rock 11}
;;; Sandy plays :fire  -  Randy plays :scissors
;;; Randy inventory: {:fire 13, :paper 12, :scissors 13, :water 12, :rock 10}
;;; Sandy inventory: {:fire 10, :paper 4, :scissors 7, :water 13, :rock 11}
;;; Randy plays :paper  -  Sandy plays :paper
;;; Randy inventory: {:fire 13, :paper 12, :scissors 13, :water 12, :rock 10}
;;; Sandy inventory: {:fire 10, :paper 4, :scissors 7, :water 13, :rock 11}
;;; Randy plays :fire  -  Sandy plays :fire
;;; Randy inventory: {:fire 13, :paper 12, :scissors 13, :water 12, :rock 10}
;;; Sandy inventory: {:fire 10, :paper 4, :scissors 7, :water 13, :rock 11}
;;; Randy plays :water  -  Sandy plays :rock
;;; Randy inventory: {:fire 13, :paper 12, :scissors 13, :water 13, :rock 10}
;;; Sandy inventory: {:fire 10, :paper 4, :scissors 7, :water 13, :rock 10}
;;; Sandy plays :water  -  Randy plays :rock
;;; Randy inventory: {:fire 13, :paper 12, :scissors 13, :water 13, :rock 9}
;;; Sandy inventory: {:fire 10, :paper 4, :scissors 7, :water 14, :rock 10}
;;; Randy plays :scissors  -  Sandy plays :rock
;;; Randy inventory: {:fire 13, :paper 12, :scissors 12, :water 13, :rock 10}
;;; Sandy inventory: {:fire 11, :paper 4, :scissors 7, :water 14, :rock 9}
;;; Sandy plays :fire  -  Randy plays :scissors
;;; Randy inventory: {:fire 13, :paper 12, :scissors 13, :water 13, :rock 10}
;;; Sandy inventory: {:fire 11, :paper 4, :scissors 7, :water 14, :rock 9}
;;; Randy plays :water  -  Sandy plays :water
;;; Randy inventory: {:fire 13, :paper 12, :scissors 13, :water 13, :rock 10}
;;; Sandy inventory: {:fire 11, :paper 4, :scissors 7, :water 14, :rock 9}
;;; Randy plays :rock  -  Sandy plays :rock
;;; Randy inventory: {:fire 13, :paper 12, :scissors 13, :water 13, :rock 10}
;;; Sandy inventory: {:fire 11, :paper 4, :scissors 7, :water 14, :rock 9}
;;; Randy plays :scissors  -  Sandy plays :paper
;;; Randy inventory: {:fire 13, :paper 12, :scissors 13, :water 13, :rock 10}
;;; Sandy inventory: {:fire 11, :paper 5, :scissors 7, :water 14, :rock 9}
;;; Sandy plays :rock  -  Randy plays :scissors
;;; Randy inventory: {:fire 13, :paper 12, :scissors 12, :water 13, :rock 11}
;;; Sandy inventory: {:fire 12, :paper 5, :scissors 7, :water 14, :rock 8}
;;; Sandy plays :scissors  -  Randy plays :rock
;;; Randy inventory: {:fire 14, :paper 12, :scissors 12, :water 13, :rock 10}
;;; Sandy inventory: {:fire 12, :paper 5, :scissors 6, :water 14, :rock 9}
;;; Sandy plays :scissors  -  Randy plays :water
;;; Randy inventory: {:fire 14, :paper 12, :scissors 12, :water 12, :rock 11}
;;; Sandy inventory: {:fire 12, :paper 5, :scissors 5, :water 15, :rock 9}
;;; Sandy plays :paper  -  Randy plays :water
;;; Randy inventory: {:fire 14, :paper 12, :scissors 12, :water 11, :rock 11}
;;; Sandy inventory: {:fire 12, :paper 5, :scissors 5, :water 15, :rock 9}
;;; Randy plays :water  -  Sandy plays :water
;;; Randy inventory: {:fire 14, :paper 12, :scissors 12, :water 11, :rock 11}
;;; Sandy inventory: {:fire 12, :paper 5, :scissors 5, :water 15, :rock 9}
;;; Randy plays :paper  -  Sandy plays :fire
;;; Randy inventory: {:fire 14, :paper 11, :scissors 12, :water 11, :rock 11}
;;; Sandy inventory: {:fire 13, :paper 5, :scissors 5, :water 15, :rock 9}
;;; 
;; <-
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:name</span>","value":":name"},{"type":"html","content":"<span class='clj-string'>&quot;Randy&quot;</span>","value":"\"Randy\""}],"value":"[:name \"Randy\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:deviance</span>","value":":deviance"},{"type":"html","content":"<span class='clj-double'>1.3038404810405297</span>","value":"1.3038404810405297"}],"value":"[:deviance 1.3038404810405297]"}],"value":"{:name \"Randy\", :deviance 1.3038404810405297}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:name</span>","value":":name"},{"type":"html","content":"<span class='clj-string'>&quot;Sandy&quot;</span>","value":"\"Sandy\""}],"value":"[:name \"Sandy\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:deviance</span>","value":":deviance"},{"type":"html","content":"<span class='clj-double'>4.560701700396552</span>","value":"4.560701700396552"}],"value":"[:deviance 4.560701700396552]"}],"value":"{:name \"Sandy\", :deviance 4.560701700396552}"}],"value":"[{:name \"Randy\", :deviance 1.3038404810405297} {:name \"Sandy\", :deviance 4.560701700396552}]"}
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
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:name</span>","value":":name"},{"type":"html","content":"<span class='clj-string'>&quot;Randy&quot;</span>","value":"\"Randy\""}],"value":"[:name \"Randy\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:deviance</span>","value":":deviance"},{"type":"html","content":"<span class='clj-double'>6.268971207462991</span>","value":"6.268971207462991"}],"value":"[:deviance 6.268971207462991]"}],"value":"{:name \"Randy\", :deviance 6.268971207462991}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:name</span>","value":":name"},{"type":"html","content":"<span class='clj-string'>&quot;Rocky&quot;</span>","value":"\"Rocky\""}],"value":"[:name \"Rocky\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:deviance</span>","value":":deviance"},{"type":"html","content":"<span class='clj-double'>10.963576058932597</span>","value":"10.963576058932597"}],"value":"[:deviance 10.963576058932597]"}],"value":"{:name \"Rocky\", :deviance 10.963576058932597}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:name</span>","value":":name"},{"type":"html","content":"<span class='clj-string'>&quot;Sandy&quot;</span>","value":"\"Sandy\""}],"value":"[:name \"Sandy\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:deviance</span>","value":":deviance"},{"type":"html","content":"<span class='clj-double'>14.53272169966796</span>","value":"14.53272169966796"}],"value":"[:deviance 14.53272169966796]"}],"value":"{:name \"Sandy\", :deviance 14.53272169966796}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:name</span>","value":":name"},{"type":"html","content":"<span class='clj-string'>&quot;Wally&quot;</span>","value":"\"Wally\""}],"value":"[:name \"Wally\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:deviance</span>","value":":deviance"},{"type":"html","content":"<span class='clj-double'>16.64331697709324</span>","value":"16.64331697709324"}],"value":"[:deviance 16.64331697709324]"}],"value":"{:name \"Wally\", :deviance 16.64331697709324}"}],"value":"[{:name \"Randy\", :deviance 6.268971207462991} {:name \"Rocky\", :deviance 10.963576058932597} {:name \"Sandy\", :deviance 14.53272169966796} {:name \"Wally\", :deviance 16.64331697709324}]"}
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
;;; [{:name &quot;Randy&quot;, :deviance 8.955445270895245}
;;;  {:name &quot;Sandy&quot;, :deviance 9.710818709048171}
;;;  {:name &quot;Wally&quot;, :deviance 13.989281611290838}
;;;  {:name &quot;Rocky&quot;, :deviance 16.410362579784763}]
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
             (player "Harry" water-hoarder-pf)])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Margie&quot;</span>","value":"\"Margie\""},{"type":"html","content":"<span class='clj-long'>70</span>","value":"70"}],"value":"[\"Margie\" 70]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Minnie&quot;</span>","value":"\"Minnie\""},{"type":"html","content":"<span class='clj-long'>14</span>","value":"14"}],"value":"[\"Minnie\" 14]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Pappy&quot;</span>","value":"\"Pappy\""},{"type":"html","content":"<span class='clj-long'>10</span>","value":"10"}],"value":"[\"Pappy\" 10]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Randy&quot;</span>","value":"\"Randy\""},{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"}],"value":"[\"Randy\" 3]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Harry&quot;</span>","value":"\"Harry\""},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"}],"value":"[\"Harry\" 2]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Suzy&quot;</span>","value":"\"Suzy\""},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[\"Suzy\" 1]"}],"value":"([\"Margie\" 70] [\"Minnie\" 14] [\"Pappy\" 10] [\"Randy\" 3] [\"Harry\" 2] [\"Suzy\" 1])"}
;; <=

;; **
;;; For games that have no single winner, the win is credited to "Nobody":
;; **

;; @@
(tournament 100
            [(player "Margie" majority-pf)
             (player "Pappy" paper-pf)])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Margie&quot;</span>","value":"\"Margie\""},{"type":"html","content":"<span class='clj-long'>51</span>","value":"51"}],"value":"[\"Margie\" 51]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Pappy&quot;</span>","value":"\"Pappy\""},{"type":"html","content":"<span class='clj-long'>28</span>","value":"28"}],"value":"[\"Pappy\" 28]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Nobody&quot;</span>","value":"\"Nobody\""},{"type":"html","content":"<span class='clj-long'>21</span>","value":"21"}],"value":"[\"Nobody\" 21]"}],"value":"([\"Margie\" 51] [\"Pappy\" 28] [\"Nobody\" 21])"}
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
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Suzy&quot;</span>","value":"\"Suzy\""},{"type":"html","content":"<span class='clj-long'>75</span>","value":"75"}],"value":"[\"Suzy\" 75]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Rocky&quot;</span>","value":"\"Rocky\""},{"type":"html","content":"<span class='clj-long'>25</span>","value":"25"}],"value":"[\"Rocky\" 25]"}],"value":"([\"Suzy\" 75] [\"Rocky\" 25])"}
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
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Randy&quot;</span>","value":"\"Randy\""},{"type":"html","content":"<span class='clj-long'>37</span>","value":"37"}],"value":"[\"Randy\" 37]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Trudy&quot;</span>","value":"\"Trudy\""},{"type":"html","content":"<span class='clj-long'>36</span>","value":"36"}],"value":"[\"Trudy\" 36]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Rudy&quot;</span>","value":"\"Rudy\""},{"type":"html","content":"<span class='clj-long'>27</span>","value":"27"}],"value":"[\"Rudy\" 27]"}],"value":"([\"Randy\" 37] [\"Trudy\" 36] [\"Rudy\" 27])"}
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
;;; {"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:fire</span>","value":":fire"},{"type":"html","content":"<span class='clj-long'>687</span>","value":"687"}],"value":"[:fire 687]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:paper</span>","value":":paper"},{"type":"html","content":"<span class='clj-long'>470</span>","value":"470"}],"value":"[:paper 470]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:scissors</span>","value":":scissors"},{"type":"html","content":"<span class='clj-long'>716</span>","value":"716"}],"value":"[:scissors 716]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:water</span>","value":":water"},{"type":"html","content":"<span class='clj-long'>302</span>","value":"302"}],"value":"[:water 302]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:rock</span>","value":":rock"},{"type":"html","content":"<span class='clj-long'>409</span>","value":"409"}],"value":"[:rock 409]"}],"value":"{:fire 687, :paper 470, :scissors 716, :water 302, :rock 409}"}
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
;;; Randy vs Rocky ([Randy 65] [Rocky 35])
;;; Randy vs Pappy ([Pappy 79] [Randy 21])
;;; Randy vs Suzy ([Suzy 58] [Randy 42])
;;; Randy vs Fido ([Randy 60] [Fido 40])
;;; Randy vs Wally ([Wally 58] [Randy 42])
;;; Randy vs Margie ([Margie 100])
;;; Randy vs Minnie ([Randy 63] [Minnie 37])
;;; Randy vs Harry ([Harry 67] [Randy 33])
;;; Randy vs Maggy ([Maggy 52] [Randy 48])
;;; Rocky vs Randy ([Randy 61] [Rocky 39])
;;; Rocky vs Rocky ([Nobody 100])
;;; Rocky vs Pappy ([Pappy 51] [Rocky 48] [Nobody 1])
;;; Rocky vs Suzy ([Suzy 65] [Rocky 35])
;;; Rocky vs Fido ([Rocky 67] [Fido 33])
;;; Rocky vs Wally ([Wally 77] [Rocky 22] [Nobody 1])
;;; Rocky vs Margie ([Nobody 72] [Margie 27] [Rocky 1])
;;; Rocky vs Minnie ([Rocky 55] [Minnie 45])
;;; Rocky vs Harry ([Harry 82] [Rocky 18])
;;; Rocky vs Maggy ([Maggy 76] [Rocky 24])
;;; Pappy vs Randy ([Pappy 73] [Randy 27])
;;; Pappy vs Rocky ([Pappy 55] [Rocky 45])
;;; Pappy vs Pappy ([Nobody 100])
;;; Pappy vs Suzy ([Suzy 100])
;;; Pappy vs Fido ([Pappy 66] [Fido 34])
;;; Pappy vs Wally ([Pappy 80] [Wally 20])
;;; Pappy vs Margie ([Margie 44] [Pappy 31] [Nobody 25])
;;; Pappy vs Minnie ([Pappy 75] [Minnie 25])
;;; Pappy vs Harry ([Pappy 63] [Harry 37])
;;; Pappy vs Maggy ([Maggy 53] [Pappy 47])
;;; Suzy vs Randy ([Suzy 64] [Randy 36])
;;; Suzy vs Rocky ([Suzy 66] [Rocky 34])
;;; Suzy vs Pappy ([Suzy 100])
;;; Suzy vs Suzy ([Nobody 100])
;;; Suzy vs Fido ([Fido 100])
;;; Suzy vs Wally ([Suzy 53] [Wally 47])
;;; Suzy vs Margie ([Margie 66] [Suzy 34])
;;; Suzy vs Minnie ([Minnie 52] [Suzy 48])
;;; Suzy vs Harry ([Suzy 95] [Harry 5])
;;; Suzy vs Maggy ([Maggy 100])
;;; Fido vs Randy ([Randy 70] [Fido 30])
;;; Fido vs Rocky ([Rocky 77] [Fido 23])
;;; Fido vs Pappy ([Pappy 55] [Fido 45])
;;; Fido vs Suzy ([Fido 100])
;;; Fido vs Fido ([Nobody 100])
;;; Fido vs Wally ([Fido 55] [Wally 45])
;;; Fido vs Margie ([Fido 99] [Margie 1])
;;; Fido vs Minnie ([Minnie 69] [Fido 30] [Nobody 1])
;;; Fido vs Harry ([Harry 61] [Fido 39])
;;; Fido vs Maggy ([Maggy 55] [Fido 45])
;;; Wally vs Randy ([Wally 51] [Randy 49])
;;; Wally vs Rocky ([Wally 73] [Rocky 27])
;;; Wally vs Pappy ([Pappy 72] [Wally 28])
;;; Wally vs Suzy ([Suzy 55] [Wally 44] [Nobody 1])
;;; Wally vs Fido ([Wally 52] [Fido 48])
;;; Wally vs Wally ([Nobody 100])
;;; Wally vs Margie ([Margie 38] [Wally 35] [Nobody 27])
;;; Wally vs Minnie ([Wally 53] [Minnie 47])
;;; Wally vs Harry ([Harry 57] [Wally 43])
;;; Wally vs Maggy ([Maggy 59] [Wally 41])
;;; Margie vs Randy ([Margie 100])
;;; Margie vs Rocky ([Nobody 70] [Margie 29] [Rocky 1])
;;; Margie vs Pappy ([Margie 47] [Nobody 28] [Pappy 25])
;;; Margie vs Suzy ([Margie 66] [Suzy 34])
;;; Margie vs Fido ([Fido 98] [Margie 2])
;;; Margie vs Wally ([Wally 43] [Margie 31] [Nobody 26])
;;; Margie vs Margie ([Margie 91] [Nobody 9])
;;; Margie vs Minnie ([Margie 61] [Nobody 23] [Minnie 16])
;;; Margie vs Harry ([Margie 98] [Harry 2])
;;; Margie vs Maggy ([Maggy 91] [Margie 9])
;;; Minnie vs Randy ([Randy 55] [Minnie 45])
;;; Minnie vs Rocky ([Rocky 53] [Minnie 47])
;;; Minnie vs Pappy ([Pappy 65] [Minnie 35])
;;; Minnie vs Suzy ([Suzy 58] [Minnie 41] [Nobody 1])
;;; Minnie vs Fido ([Minnie 73] [Fido 27])
;;; Minnie vs Wally ([Wally 61] [Minnie 39])
;;; Minnie vs Margie ([Margie 64] [Nobody 24] [Minnie 12])
;;; Minnie vs Minnie ([Minnie 100])
;;; Minnie vs Harry ([Harry 78] [Minnie 22])
;;; Minnie vs Maggy ([Maggy 62] [Minnie 37] [Nobody 1])
;;; Harry vs Randy ([Harry 70] [Randy 30])
;;; Harry vs Rocky ([Harry 82] [Rocky 18])
;;; Harry vs Pappy ([Pappy 58] [Harry 42])
;;; Harry vs Suzy ([Suzy 99] [Harry 1])
;;; Harry vs Fido ([Harry 66] [Fido 34])
;;; Harry vs Wally ([Harry 70] [Wally 30])
;;; Harry vs Margie ([Margie 99] [Harry 1])
;;; Harry vs Minnie ([Harry 73] [Minnie 27])
;;; Harry vs Harry ([Harry 100])
;;; Harry vs Maggy ([Maggy 55] [Harry 45])
;;; Maggy vs Randy ([Maggy 52] [Randy 48])
;;; Maggy vs Rocky ([Maggy 84] [Rocky 16])
;;; Maggy vs Pappy ([Maggy 54] [Pappy 46])
;;; Maggy vs Suzy ([Maggy 100])
;;; Maggy vs Fido ([Fido 63] [Maggy 37])
;;; Maggy vs Wally ([Maggy 52] [Wally 48])
;;; Maggy vs Margie ([Maggy 94] [Margie 6])
;;; Maggy vs Minnie ([Maggy 57] [Minnie 42] [Nobody 1])
;;; Maggy vs Harry ([Maggy 57] [Harry 43])
;;; Maggy vs Maggy ([Maggy 96] [Nobody 4])
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
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Maggy&quot;</span>","value":"\"Maggy\""},{"type":"html","content":"<span class='clj-long'>92</span>","value":"92"}],"value":"[\"Maggy\" 92]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Margie&quot;</span>","value":"\"Margie\""},{"type":"html","content":"<span class='clj-long'>8</span>","value":"8"}],"value":"[\"Margie\" 8]"}],"value":"([\"Maggy\" 92] [\"Margie\" 8])"}
;; <=

;; @@
(tournament 100
            [(player "Maggy" margie-hater-pf)
             (player "Naggy" maggie-hater-pf)])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Naggy&quot;</span>","value":"\"Naggy\""},{"type":"html","content":"<span class='clj-long'>76</span>","value":"76"}],"value":"[\"Naggy\" 76]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Maggy&quot;</span>","value":"\"Maggy\""},{"type":"html","content":"<span class='clj-long'>24</span>","value":"24"}],"value":"[\"Maggy\" 24]"}],"value":"([\"Naggy\" 76] [\"Maggy\" 24])"}
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
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Maggy&quot;</span>","value":"\"Maggy\""},{"type":"html","content":"<span class='clj-long'>63</span>","value":"63"}],"value":"[\"Maggy\" 63]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Randy&quot;</span>","value":"\"Randy\""},{"type":"html","content":"<span class='clj-long'>16</span>","value":"16"}],"value":"[\"Randy\" 16]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Harry&quot;</span>","value":"\"Harry\""},{"type":"html","content":"<span class='clj-long'>11</span>","value":"11"}],"value":"[\"Harry\" 11]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Margie&quot;</span>","value":"\"Margie\""},{"type":"html","content":"<span class='clj-long'>10</span>","value":"10"}],"value":"[\"Margie\" 10]"}],"value":"([\"Maggy\" 63] [\"Randy\" 16] [\"Harry\" 11] [\"Margie\" 10])"}
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
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Margie&quot;</span>","value":"\"Margie\""},{"type":"html","content":"<span class='clj-long'>32</span>","value":"32"}],"value":"[\"Margie\" 32]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Maggy&quot;</span>","value":"\"Maggy\""},{"type":"html","content":"<span class='clj-long'>25</span>","value":"25"}],"value":"[\"Maggy\" 25]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Harry&quot;</span>","value":"\"Harry\""},{"type":"html","content":"<span class='clj-long'>16</span>","value":"16"}],"value":"[\"Harry\" 16]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Randy&quot;</span>","value":"\"Randy\""},{"type":"html","content":"<span class='clj-long'>15</span>","value":"15"}],"value":"[\"Randy\" 15]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Maxaminion&quot;</span>","value":"\"Maxaminion\""},{"type":"html","content":"<span class='clj-long'>12</span>","value":"12"}],"value":"[\"Maxaminion\" 12]"}],"value":"([\"Margie\" 32] [\"Maggy\" 25] [\"Harry\" 16] [\"Randy\" 15] [\"Maxaminion\" 12])"}
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
