;; gorilla-repl.fileformat = 1

;; **
;;; # RPS Search Worksheet
;; **

;; @@
(ns rps-worksheet
  (:use [rock-paper-stuff util play]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(defn opponent-pf
  [self other-skin]
  {:play (rand-nth [:rock :paper :scissors :fire :water])})
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;search_worksheet/opponent-pf</span>","value":"#'search_worksheet/opponent-pf"}
;; <=

;; @@
(defn sequence-pf-maker
  [sequence]
  (fn [self other-skin]
    (let [s (if (empty? (:sequence (:memory self)))
              sequence
              (:sequence (:memory self)))]
      {:play (first s)
       :memory {:sequence (concat (rest s) [(first s)])}})))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;search_worksheet/sequence-pf-maker</span>","value":"#'search_worksheet/sequence-pf-maker"}
;; <=

;; @@
(tournament 100
            [(player "Opponent" opponent-pf)
             (player "Ropa" (sequence-pf-maker [:rock :paper]))])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Opponent&quot;</span>","value":"\"Opponent\""},{"type":"html","content":"<span class='clj-long'>80</span>","value":"80"}],"value":"[\"Opponent\" 80]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Ropa&quot;</span>","value":"\"Ropa\""},{"type":"html","content":"<span class='clj-long'>20</span>","value":"20"}],"value":"[\"Ropa\" 20]"}],"value":"([\"Opponent\" 80] [\"Ropa\" 20])"}
;; <=

;; @@
(defn rps-sequence-successors
  [s]
  (map #(concat s [%]) [:rock :paper :scissors :fire :water]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;search_worksheet/rps-sequence-successors</span>","value":"#'search_worksheet/rps-sequence-successors"}
;; <=

;; @@
(rps-sequence-successors [:paper :water])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:paper</span>","value":":paper"},{"type":"html","content":"<span class='clj-keyword'>:water</span>","value":":water"},{"type":"html","content":"<span class='clj-keyword'>:rock</span>","value":":rock"}],"value":"(:paper :water :rock)"},{"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:paper</span>","value":":paper"},{"type":"html","content":"<span class='clj-keyword'>:water</span>","value":":water"},{"type":"html","content":"<span class='clj-keyword'>:paper</span>","value":":paper"}],"value":"(:paper :water :paper)"},{"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:paper</span>","value":":paper"},{"type":"html","content":"<span class='clj-keyword'>:water</span>","value":":water"},{"type":"html","content":"<span class='clj-keyword'>:scissors</span>","value":":scissors"}],"value":"(:paper :water :scissors)"},{"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:paper</span>","value":":paper"},{"type":"html","content":"<span class='clj-keyword'>:water</span>","value":":water"},{"type":"html","content":"<span class='clj-keyword'>:fire</span>","value":":fire"}],"value":"(:paper :water :fire)"},{"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:paper</span>","value":":paper"},{"type":"html","content":"<span class='clj-keyword'>:water</span>","value":":water"},{"type":"html","content":"<span class='clj-keyword'>:water</span>","value":":water"}],"value":"(:paper :water :water)"}],"value":"((:paper :water :rock) (:paper :water :paper) (:paper :water :scissors) (:paper :water :fire) (:paper :water :water))"}
;; <=

;; @@
(defn search
  [goal start combiner successors]
  (loop [frontier [(hash-map :contents start :history [])]
         seen #{start}
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
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;search_worksheet/search</span>","value":"#'search_worksheet/search"}
;; <=

;; @@
(search #(= (first (first (tournament 10 [(player "Opponent" opponent-pf)
                                          (player "Beater" (sequence-pf-maker %))])))
            "Beater")
        [:rock]
        #(concat %2 %1)
        rps-sequence-successors)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:history</span>","value":":history"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:rock</span>","value":":rock"}],"value":"[:rock]"}],"value":"[[:rock]]"}],"value":"[:history [[:rock]]]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:contents</span>","value":":contents"},{"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:rock</span>","value":":rock"},{"type":"html","content":"<span class='clj-keyword'>:water</span>","value":":water"}],"value":"(:rock :water)"}],"value":"[:contents (:rock :water)]"}],"value":"{:history [[:rock]], :contents (:rock :water)}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:seen</span>","value":":seen"},{"type":"html","content":"<span class='clj-unkown'>11</span>","value":"11"}],"value":"[:seen 11]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:steps</span>","value":":steps"},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"}],"value":"[:steps 2]"}],"value":"{:seen 11, :steps 2}"}],"value":"[{:history [[:rock]], :contents (:rock :water)} {:seen 11, :steps 2}]"}
;; <=

;; @@
(tournament 100 [(player "Opponent" opponent-pf)
                 (player "Beater" (sequence-pf-maker [:rock :water]))])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Beater&quot;</span>","value":"\"Beater\""},{"type":"html","content":"<span class='clj-long'>80</span>","value":"80"}],"value":"[\"Beater\" 80]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Opponent&quot;</span>","value":"\"Opponent\""},{"type":"html","content":"<span class='clj-long'>19</span>","value":"19"}],"value":"[\"Opponent\" 19]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Nobody&quot;</span>","value":"\"Nobody\""},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[\"Nobody\" 1]"}],"value":"([\"Beater\" 80] [\"Opponent\" 19] [\"Nobody\" 1])"}
;; <=

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
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;search_worksheet/maximum-pf</span>","value":"#'search_worksheet/maximum-pf"}
;; <=

;; @@
(tournament 100 [(player "Maxy" maximum-pf)
                 (player "Beater" (sequence-pf-maker [:rock :water]))])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Maxy&quot;</span>","value":"\"Maxy\""},{"type":"html","content":"<span class='clj-long'>100</span>","value":"100"}],"value":"[\"Maxy\" 100]"}],"value":"([\"Maxy\" 100])"}
;; <=

;; @@
(search #(= (first (first (tournament 10 [(player "Maxy" maximum-pf)
                                          (player "Beater" (sequence-pf-maker %))])))
            "Beater")
        [:rock]
        #(concat %2 %1)
        rps-sequence-successors)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:history</span>","value":":history"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:rock</span>","value":":rock"}],"value":"[:rock]"},{"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:rock</span>","value":":rock"},{"type":"html","content":"<span class='clj-keyword'>:scissors</span>","value":":scissors"}],"value":"(:rock :scissors)"},{"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:rock</span>","value":":rock"},{"type":"html","content":"<span class='clj-keyword'>:scissors</span>","value":":scissors"},{"type":"html","content":"<span class='clj-keyword'>:fire</span>","value":":fire"}],"value":"(:rock :scissors :fire)"}],"value":"[[:rock] (:rock :scissors) (:rock :scissors :fire)]"}],"value":"[:history [[:rock] (:rock :scissors) (:rock :scissors :fire)]]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:contents</span>","value":":contents"},{"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:rock</span>","value":":rock"},{"type":"html","content":"<span class='clj-keyword'>:scissors</span>","value":":scissors"},{"type":"html","content":"<span class='clj-keyword'>:fire</span>","value":":fire"},{"type":"html","content":"<span class='clj-keyword'>:fire</span>","value":":fire"}],"value":"(:rock :scissors :fire :fire)"}],"value":"[:contents (:rock :scissors :fire :fire)]"}],"value":"{:history [[:rock] (:rock :scissors) (:rock :scissors :fire)], :contents (:rock :scissors :fire :fire)}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:seen</span>","value":":seen"},{"type":"html","content":"<span class='clj-unkown'>221</span>","value":"221"}],"value":"[:seen 221]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:steps</span>","value":":steps"},{"type":"html","content":"<span class='clj-long'>44</span>","value":"44"}],"value":"[:steps 44]"}],"value":"{:seen 221, :steps 44}"}],"value":"[{:history [[:rock] (:rock :scissors) (:rock :scissors :fire)], :contents (:rock :scissors :fire :fire)} {:seen 221, :steps 44}]"}
;; <=

;; @@
(tournament 100 [(player "Maxy" maximum-pf)
                 (player "Beater" (sequence-pf-maker [:rock :scissors :fire :fire]))])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Beater&quot;</span>","value":"\"Beater\""},{"type":"html","content":"<span class='clj-long'>75</span>","value":"75"}],"value":"[\"Beater\" 75]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;Maxy&quot;</span>","value":"\"Maxy\""},{"type":"html","content":"<span class='clj-long'>25</span>","value":"25"}],"value":"[\"Maxy\" 25]"}],"value":"([\"Beater\" 75] [\"Maxy\" 25])"}
;; <=

;; @@

;; @@
