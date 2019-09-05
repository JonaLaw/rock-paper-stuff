;; gorilla-repl.fileformat = 1

;; **
;;; # RPS Search Worksheet, Revised
;;; 
;;; This worksheet demonstrates the use of search with Rock Paper Stuff.
;;; 
;;; It would be tricky to use traditional AI search algorithms to find a good move within a single game, both because of the randomness in Rock Paper Stuff and because players make their moves simultaneously. Traditional AI game tree search algorithms like Minimax assume "perfect information" (nothing is hidden or random) and alternating turns.
;;; 
;;; However, it can be interesting to use search here in a different way, to search the space of strategies to find one that beats some opponent strategy. In doing this, we can test each candidate by playing it against the opponent in a tournament, hoping that the effects of randomness will be averaged out over several games.
;;; 
;;; What we'll explore in particular is a search through the space of "sequence repeating" strategies; that is, strategies based on a pre-specified sequence of plays (for example, like `[:rock :fire :scissors]`) that the player will play in order, repeatedly, for a whole game. Can any such strategies be successful? That will depend on what the strategies are of their opponents. For some particular opponent or set of opponents, we can use search to try to find a sequence repeating strategy that will win.
;;; 
;;; First, we'll define the namespace for the code in this file, pulling in everything from the `util` and `play` namespaces in the `rock-paper-stuff` project:
;; **

;; @@
(ns rps-worksheet-revised
  (:use [rock-paper-stuff util play]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; Now we'll define a player function to serve as an opponent as we develop the code. 
;;; 
;;; This one just plays a random kind of stuff, ignoring everything in the game to which it has access in its own history and the inventory of its opponent. 
;;; 
;;; Note that `stuff` is just `[:rock :paper :scissors :fire :water]`, and is defined in `rock-paper-stuff.util`.
;; **

;; @@
(defn opponent-pf
  [self other-skin]
  {:play (rand-nth stuff)})
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;rps-worksheet-revised/opponent-pf</span>","value":"#'rps-worksheet-revised/opponent-pf"}
;; <=

;; **
;;; When we do our search, we'll be searching the "space" of sequences of stuff, and for each sequence we'll want to test a player that uses a player function that plays that sequence over and over.
;;; 
;;; To make that easy, we'll define a function that takes a sequence of stuff as its input, and returns as its output a player function that plays that sequence over and over.
;;; 
;;; The player function will have to keep track, somehow, of where it is in the sequence. We'll do that by having it keep the sequence in its memory, and each time it is called we'll have it play the first thing in the sequence, and then re-store the sequence rotated, with the thing that it just played taken off of the front and added to the end. That way, the next time it plays, it will play the next thing, and so on.
;;; 
;;; When the player function is called the first time, however, the memory of the player will be empty. So the player function will check for this, and in that case it will use the original sequence rather than a sequence taken from memory.
;; **

;; @@
(defn sequence-pf-maker
  [sequence]
  (fn [self other-skin] ;; the returned function will take the player and the skin of the opponent
    (let [s (if (empty? (:sequence (:memory self))) ;; if there's no sequence in memory, then
              sequence                              ;; use the original sequence
              (:sequence (:memory self)))]          ;; else use the sequence from memory
      {:play (first s)                              ;; return a map that says to play the first thing
       :memory {:sequence (concat (rest s) [(first s)])}}))) ;; and store the rotated sequence back in memory
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;rps-worksheet-revised/sequence-pf-maker</span>","value":"#'rps-worksheet-revised/sequence-pf-maker"}
;; <=

;; **
;;; Let's test out the sequence player function maker by conducting a tournament of 100 games between the opponent player function defined above (which just plays random stuff each time) against a player that repeatedly plays `:rock` and then `:paper`:
;; **

;; @@
(tournament 100
            [(player "Opponent" opponent-pf)
             (player "Ropa" (sequence-pf-maker [:rock :paper]))])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Opponent&quot;</span>","value":"\"Opponent\""},{"type":"html","content":"<span class='clj-long'>77</span>","value":"77"}],"value":"[\"Opponent\" 77]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Ropa&quot;</span>","value":"\"Ropa\""},{"type":"html","content":"<span class='clj-long'>22</span>","value":"22"}],"value":"[\"Ropa\" 22]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Nobody&quot;</span>","value":"\"Nobody\""},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[\"Nobody\" 1]"}],"value":"([\"Opponent\" 77] [\"Ropa\" 22] [\"Nobody\" 1])"}
;; <=

;; **
;;; We see that the random opponent player wins most of the time. Note that `tournament` returns a list of pairs of name and games won, sorted so that the player that won the most games is first. That means we can get the name of the winner by taking the `first` of the `first` of the result, which will come in handy below.
;;; 
;;; In order to perform search in the space of sequence repeating strategies, we need a "successors" function that takes a sequence of stuff as its input and returns new sequences of stuff to try. Our successors function will just return all of the sequences that you get from adding one kind of stuff to the end of the input sequence:
;; **

;; @@
(defn rps-sequence-successors
  [s]
  (map #(concat s [%]) [:rock :paper :scissors :fire :water]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;rps-worksheet-revised/rps-sequence-successors</span>","value":"#'rps-worksheet-revised/rps-sequence-successors"}
;; <=

;; **
;;; Let's try that out:
;; **

;; @@
(rps-sequence-successors [:paper :water])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:paper</span>","value":":paper"},{"type":"html","content":"<span class='clj-keyword'>:water</span>","value":":water"},{"type":"html","content":"<span class='clj-keyword'>:rock</span>","value":":rock"}],"value":"(:paper :water :rock)"},{"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:paper</span>","value":":paper"},{"type":"html","content":"<span class='clj-keyword'>:water</span>","value":":water"},{"type":"html","content":"<span class='clj-keyword'>:paper</span>","value":":paper"}],"value":"(:paper :water :paper)"},{"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:paper</span>","value":":paper"},{"type":"html","content":"<span class='clj-keyword'>:water</span>","value":":water"},{"type":"html","content":"<span class='clj-keyword'>:scissors</span>","value":":scissors"}],"value":"(:paper :water :scissors)"},{"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:paper</span>","value":":paper"},{"type":"html","content":"<span class='clj-keyword'>:water</span>","value":":water"},{"type":"html","content":"<span class='clj-keyword'>:fire</span>","value":":fire"}],"value":"(:paper :water :fire)"},{"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:paper</span>","value":":paper"},{"type":"html","content":"<span class='clj-keyword'>:water</span>","value":":water"},{"type":"html","content":"<span class='clj-keyword'>:water</span>","value":":water"}],"value":"(:paper :water :water)"}],"value":"((:paper :water :rock) (:paper :water :paper) (:paper :water :scissors) (:paper :water :fire) (:paper :water :water))"}
;; <=

;; **
;;; Now let's define the search function. We'll start with the general search function developed in our classes/worksheet on search, which is general enough to work with any goal, any starting point, any "combiner" function (for which different funcrtions will give different kinds of search, including depth-first, breadth-first, or heuristic search), and any successors function.
;;; 
;;; However, in this case we don't really have a _single_ starting point that makes sense. If we start with an empty sequence, then we will indeed reach all possible sequences by repeatedly applying the `rps-sequence-successors` function. But the empty sequence itself doesn't really make sense, since it doesn't have any elements in it to play.
;;; 
;;; One could work around this in a couple of ways. What I've done here is to change the search function to take a collection of starting points, rather than a single one. Specifically, when we call this we'll start with all of the minimal sequences of stuff: `[:rock]`, `[:paper]`, `[:scissors]`, `[:fire]`, and `[:water]`. These will all be in the search frontier at the start of search, and repeated applications of `rps-sequence-successors` to these can eventually produce any sequence.
;;; 
;;; The lines that are changed from the original version of this function are marked with `;;**`:
;; **

;; @@
(defn search
  [goal start-collection combiner successors] ;;**
  (loop [frontier (map #(hash-map :contents % :history []) start-collection) ;;**
         seen (set start-collection) ;;**
         steps 0]
    (if (empty? frontier)
      false
      (let [f (first frontier)
            r (rest frontier)]
        (if (goal (:contents f)) 
          [f {:seen (count seen) :steps steps}]
          (let [unseen-successors (clojure.set/difference
                                    (set (successors (:contents f)))
                                    seen)]
            (recur 
              (combiner 
                (map #(hash-map :contents % 
                                :history (conj (:history f) (:contents f))) 
                     unseen-successors)
                r)
              (clojure.set/union seen unseen-successors)
              (inc steps))))))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;rps-worksheet-revised/search</span>","value":"#'rps-worksheet-revised/search"}
;; <=

;; **
;;; Now we can search for a sequence repeating strategy that beats the randomly-playing "opponent" strategy.
;;; 
;;; We'll do this by using a goal function that runs a tournament between the opponent and a player made to repeat the sequence that we're testing, to see if it satisfies the goal. We'll name the one we're testing "Beater", and the goal function will return `true` if the first element of the first element of the result of the tournament is "Beater". 
;;; 
;;; We'll start with the collection `[[:rock][:paper][:scissors][:fire][:water]]`.
;;; 
;;; And for now we'll do breadth-first search, which we do by specifying a combiner function that puts the new successors at the after all of the current sequences on the frontier (which we do by providing a function that calls `concat` with its arguments reversed).
;;; 
;;; For the successors argument, we'll use the `rps-sequence-successors` function defined above.
;; **

;; @@
(search #(= (first (first (tournament 100 [(player "Opponent" opponent-pf)
                                           (player "Beater" (sequence-pf-maker %))])))
            "Beater")
        [[:rock][:paper][:scissors][:fire][:water]]
        #(concat %2 %1)
        rps-sequence-successors)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:history</span>","value":":history"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[],"value":"[]"}],"value":"[:history []]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:contents</span>","value":":contents"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:paper</span>","value":":paper"}],"value":"[:paper]"}],"value":"[:contents [:paper]]"}],"value":"{:history [], :contents [:paper]}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:seen</span>","value":":seen"},{"type":"html","content":"<span class='clj-unkown'>10</span>","value":"10"}],"value":"[:seen 10]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:steps</span>","value":":steps"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[:steps 1]"}],"value":"{:seen 10, :steps 1}"}],"value":"[{:history [], :contents [:paper]} {:seen 10, :steps 1}]"}
;; <=

;; **
;;; Well, that was easy! It looks like just playing `:paper` over and over beats the randomly-playing strategy.
;;; 
;;; Let's try a tournament manually and see if it's true:
;; **

;; @@
(tournament 100 [(player "Opponent" opponent-pf)
                 (player "Beater" (sequence-pf-maker [:paper]))])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Beater&quot;</span>","value":"\"Beater\""},{"type":"html","content":"<span class='clj-long'>78</span>","value":"78"}],"value":"[\"Beater\" 78]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Opponent&quot;</span>","value":"\"Opponent\""},{"type":"html","content":"<span class='clj-long'>22</span>","value":"22"}],"value":"[\"Opponent\" 22]"}],"value":"([\"Beater\" 78] [\"Opponent\" 22])"}
;; <=

;; **
;;; Yep, the `:paper`-playing Beater wins around 80% of the time.
;;; 
;;; Can we find a sequence repeating strategy that beats a somewhat smarter strategy, like the one that always plays whatever it has most of?
;;; 
;;; Here we'll use the same player function for this that was defined in the Rock Paper Stuff tutorial:
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
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;rps-worksheet-revised/maximum-pf</span>","value":"#'rps-worksheet-revised/maximum-pf"}
;; <=

;; **
;;; So, how does the player that just plays `:paper` fare against this?
;; **

;; @@
(tournament 100 [(player "Maxy" maximum-pf)
                 (player "Beater" (sequence-pf-maker [:paper]))])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Maxy&quot;</span>","value":"\"Maxy\""},{"type":"html","content":"<span class='clj-long'>90</span>","value":"90"}],"value":"[\"Maxy\" 90]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Beater&quot;</span>","value":"\"Beater\""},{"type":"html","content":"<span class='clj-long'>10</span>","value":"10"}],"value":"[\"Beater\" 10]"}],"value":"([\"Maxy\" 90] [\"Beater\" 10])"}
;; <=

;; **
;;; Terribly! Okay, let's try search to see if we can find a sequence repeating strategy that does better.
;; **

;; @@
(search #(= (first (first (tournament 100 [(player "Maxy" maximum-pf)
                                           (player "Beater" (sequence-pf-maker %))])))
            "Beater")
        [[:rock][:paper][:scissors][:fire][:water]]
        #(concat %2 %1)
        rps-sequence-successors)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:history</span>","value":":history"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[],"value":"[]"}],"value":"[:history []]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:contents</span>","value":":contents"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:fire</span>","value":":fire"}],"value":"[:fire]"}],"value":"[:contents [:fire]]"}],"value":"{:history [], :contents [:fire]}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:seen</span>","value":":seen"},{"type":"html","content":"<span class='clj-unkown'>20</span>","value":"20"}],"value":"[:seen 20]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:steps</span>","value":":steps"},{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"}],"value":"[:steps 3]"}],"value":"{:seen 20, :steps 3}"}],"value":"[{:history [], :contents [:fire]} {:seen 20, :steps 3}]"}
;; <=

;; **
;;; Huh -- seems like something different but equally simple, just playing `:fire`, works here. Let's check:
;; **

;; @@
(tournament 100 [(player "Maxy" maximum-pf)
                 (player "Beater" (sequence-pf-maker [:fire]))])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Beater&quot;</span>","value":"\"Beater\""},{"type":"html","content":"<span class='clj-long'>94</span>","value":"94"}],"value":"[\"Beater\" 94]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Maxy&quot;</span>","value":"\"Maxy\""},{"type":"html","content":"<span class='clj-long'>6</span>","value":"6"}],"value":"[\"Maxy\" 6]"}],"value":"([\"Beater\" 94] [\"Maxy\" 6])"}
;; <=

;; **
;;; Yep. Okay, let's try something that's probably trickier. Let's try finding a sequence repeating strategy that wins in a tournament with three other players, one of which just plays `:rock`, one of which just plays `:paper`, and one of which just plays `:scissors`.
;; **

;; @@
(defn rock-pf
  [self other-skin]
  {:play :rock})

(defn paper-pf
  [self other-skin]
  {:play :paper})

(defn scissors-pf
  [self other-skin]
  {:play :scissors})

(search #(= (first (first (tournament 100 [(player "Rocky" rock-pf)
                                           (player "Pappy" paper-pf)
                                           (player "Suzy" scissors-pf)
                                           (player "Beater" (sequence-pf-maker %))])))
            "Beater")
        [[:rock][:paper][:scissors][:fire][:water]]
        #(concat %2 %1)
        rps-sequence-successors)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:history</span>","value":":history"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[],"value":"[]"}],"value":"[:history []]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:contents</span>","value":":contents"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:scissors</span>","value":":scissors"}],"value":"[:scissors]"}],"value":"[:contents [:scissors]]"}],"value":"{:history [], :contents [:scissors]}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:seen</span>","value":":seen"},{"type":"html","content":"<span class='clj-unkown'>15</span>","value":"15"}],"value":"[:seen 15]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:steps</span>","value":":steps"},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"}],"value":"[:steps 2]"}],"value":"{:seen 15, :steps 2}"}],"value":"[{:history [], :contents [:scissors]} {:seen 15, :steps 2}]"}
;; <=

;; **
;;; Huh, just playing `:scissors` wins? That doesn't make a lot of sense, since one of the other players here also always plays `:scissors`. Let's try that manually a couple of times:
;; **

;; @@
(tournament 500 [(player "Rocky" rock-pf)
                 (player "Pappy" paper-pf)
                 (player "Suzy" scissors-pf)
                 (player "Beater" (sequence-pf-maker [:scissors]))])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Beater&quot;</span>","value":"\"Beater\""},{"type":"html","content":"<span class='clj-long'>257</span>","value":"257"}],"value":"[\"Beater\" 257]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Suzy&quot;</span>","value":"\"Suzy\""},{"type":"html","content":"<span class='clj-long'>239</span>","value":"239"}],"value":"[\"Suzy\" 239]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Pappy&quot;</span>","value":"\"Pappy\""},{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"}],"value":"[\"Pappy\" 3]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Rocky&quot;</span>","value":"\"Rocky\""},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[\"Rocky\" 1]"}],"value":"([\"Beater\" 257] [\"Suzy\" 239] [\"Pappy\" 3] [\"Rocky\" 1])"}
;; <=

;; @@
(tournament 500 [(player "Rocky" rock-pf)
                 (player "Pappy" paper-pf)
                 (player "Suzy" scissors-pf)
                 (player "Beater" (sequence-pf-maker [:scissors]))])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Beater&quot;</span>","value":"\"Beater\""},{"type":"html","content":"<span class='clj-long'>252</span>","value":"252"}],"value":"[\"Beater\" 252]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Suzy&quot;</span>","value":"\"Suzy\""},{"type":"html","content":"<span class='clj-long'>245</span>","value":"245"}],"value":"[\"Suzy\" 245]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Pappy&quot;</span>","value":"\"Pappy\""},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"}],"value":"[\"Pappy\" 2]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Nobody&quot;</span>","value":"\"Nobody\""},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[\"Nobody\" 1]"}],"value":"([\"Beater\" 252] [\"Suzy\" 245] [\"Pappy\" 2] [\"Nobody\" 1])"}
;; <=

;; @@
(tournament 500 [(player "Rocky" rock-pf)
                 (player "Pappy" paper-pf)
                 (player "Suzy" scissors-pf)
                 (player "Beater" (sequence-pf-maker [:scissors]))])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Suzy&quot;</span>","value":"\"Suzy\""},{"type":"html","content":"<span class='clj-long'>255</span>","value":"255"}],"value":"[\"Suzy\" 255]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Beater&quot;</span>","value":"\"Beater\""},{"type":"html","content":"<span class='clj-long'>244</span>","value":"244"}],"value":"[\"Beater\" 244]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Nobody&quot;</span>","value":"\"Nobody\""},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[\"Nobody\" 1]"}],"value":"([\"Suzy\" 255] [\"Beater\" 244] [\"Nobody\" 1])"}
;; <=

;; **
;;; It looks like just playing `:scissors` is good in this situation, and if there are two players who do it then one or the other of them will win, randomly, with the other one not far behind.
;;; 
;;; So... that's not what we were looking for. And in retrospect, this is a problem with the whole setup so far: If you're basically equal to some other strategy, then you'll come out ahead, randomly, half the time.
;;; 
;;; So we should come up with a way to search for something that's better than just tied.
;;; 
;;; We can do that by changing the goal function. Here we do the same tournament, but then require something more demanding of the result: Not only do we demand that the winner be Beater, but we also require that the number of games won by Beater be at least twice the number of games won by the player who finishes second:
;; **

;; @@
(search #(let [result (tournament 100 [(player "Rocky" rock-pf)
                                       (player "Pappy" paper-pf)
                                       (player "Suzy" scissors-pf)
                                       (player "Beater" (sequence-pf-maker %))])]
           (and (= (first (first result)) "Beater")
                (> (second (first result))
                   (* 2 (second (second result))))))
        [[:rock][:paper][:scissors][:fire][:water]]
        #(concat %2 %1)
        rps-sequence-successors)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:history</span>","value":":history"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:paper</span>","value":":paper"}],"value":"[:paper]"},{"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:paper</span>","value":":paper"},{"type":"html","content":"<span class='clj-keyword'>:scissors</span>","value":":scissors"}],"value":"(:paper :scissors)"},{"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:paper</span>","value":":paper"},{"type":"html","content":"<span class='clj-keyword'>:scissors</span>","value":":scissors"},{"type":"html","content":"<span class='clj-keyword'>:fire</span>","value":":fire"}],"value":"(:paper :scissors :fire)"}],"value":"[[:paper] (:paper :scissors) (:paper :scissors :fire)]"}],"value":"[:history [[:paper] (:paper :scissors) (:paper :scissors :fire)]]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:contents</span>","value":":contents"},{"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:paper</span>","value":":paper"},{"type":"html","content":"<span class='clj-keyword'>:scissors</span>","value":":scissors"},{"type":"html","content":"<span class='clj-keyword'>:fire</span>","value":":fire"},{"type":"html","content":"<span class='clj-keyword'>:scissors</span>","value":":scissors"}],"value":"(:paper :scissors :fire :scissors)"}],"value":"[:contents (:paper :scissors :fire :scissors)]"}],"value":"{:history [[:paper] (:paper :scissors) (:paper :scissors :fire)], :contents (:paper :scissors :fire :scissors)}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:seen</span>","value":":seen"},{"type":"html","content":"<span class='clj-unkown'>1840</span>","value":"1840"}],"value":"[:seen 1840]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:steps</span>","value":":steps"},{"type":"html","content":"<span class='clj-long'>367</span>","value":"367"}],"value":"[:steps 367]"}],"value":"{:seen 1840, :steps 367}"}],"value":"[{:history [[:paper] (:paper :scissors) (:paper :scissors :fire)], :contents (:paper :scissors :fire :scissors)} {:seen 1840, :steps 367}]"}
;; <=

;; **
;;; That took a long time, but it looks like playing `:paper :scissors :fire :scissors` repeatedly wins pretty decisively in tournaments  with players who play just `:rock`, just `:paper`, and just `:scissors`. 
;;; 
;;; Let's run a couple of tournaments manually to see if this holds up.
;; **

;; @@
(tournament 100 [(player "Rocky" rock-pf)
                 (player "Pappy" paper-pf)
                 (player "Suzy" scissors-pf)
                 (player "Beater" (sequence-pf-maker [:paper :scissors :fire :scissors]))])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Beater&quot;</span>","value":"\"Beater\""},{"type":"html","content":"<span class='clj-long'>60</span>","value":"60"}],"value":"[\"Beater\" 60]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Suzy&quot;</span>","value":"\"Suzy\""},{"type":"html","content":"<span class='clj-long'>38</span>","value":"38"}],"value":"[\"Suzy\" 38]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Pappy&quot;</span>","value":"\"Pappy\""},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"}],"value":"[\"Pappy\" 2]"}],"value":"([\"Beater\" 60] [\"Suzy\" 38] [\"Pappy\" 2])"}
;; <=

;; @@
(tournament 500 [(player "Rocky" rock-pf)
                 (player "Pappy" paper-pf)
                 (player "Suzy" scissors-pf)
                 (player "Beater" (sequence-pf-maker [:paper :scissors :fire :scissors]))])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Beater&quot;</span>","value":"\"Beater\""},{"type":"html","content":"<span class='clj-long'>331</span>","value":"331"}],"value":"[\"Beater\" 331]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Suzy&quot;</span>","value":"\"Suzy\""},{"type":"html","content":"<span class='clj-long'>164</span>","value":"164"}],"value":"[\"Suzy\" 164]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Pappy&quot;</span>","value":"\"Pappy\""},{"type":"html","content":"<span class='clj-long'>4</span>","value":"4"}],"value":"[\"Pappy\" 4]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Nobody&quot;</span>","value":"\"Nobody\""},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[\"Nobody\" 1]"}],"value":"([\"Beater\" 331] [\"Suzy\" 164] [\"Pappy\" 4] [\"Nobody\" 1])"}
;; <=

;; @@
(tournament 500 [(player "Rocky" rock-pf)
                 (player "Pappy" paper-pf)
                 (player "Suzy" scissors-pf)
                 (player "Beater" (sequence-pf-maker [:paper :scissors :fire :scissors]))])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Beater&quot;</span>","value":"\"Beater\""},{"type":"html","content":"<span class='clj-long'>345</span>","value":"345"}],"value":"[\"Beater\" 345]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Suzy&quot;</span>","value":"\"Suzy\""},{"type":"html","content":"<span class='clj-long'>152</span>","value":"152"}],"value":"[\"Suzy\" 152]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Pappy&quot;</span>","value":"\"Pappy\""},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"}],"value":"[\"Pappy\" 2]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Nobody&quot;</span>","value":"\"Nobody\""},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[\"Nobody\" 1]"}],"value":"([\"Beater\" 345] [\"Suzy\" 152] [\"Pappy\" 2] [\"Nobody\" 1])"}
;; <=

;; **
;;; Yep, it looks like it really does. In that first tournament of 100 games, the strategy found by search doesn't quite win twice as often as the `:scissors` strategy, but it does in the larger tournaments of 500 games. In any event, it does indeed seem to win decisively in these situations.
;;; 
;;; This final search took a long time, but that was because it used blind, breadth-first search. Using a heuristic sorting function as the combiner might find a winner more quickly. This would be an interesting thing to experiment with!
;; **
