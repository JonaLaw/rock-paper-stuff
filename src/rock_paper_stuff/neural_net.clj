;; gorilla-repl.fileformat = 1

;; @@
(ns my-stuff.neural_net
  (:use ;[rock_paper_stuff.k9]
        [my-stuff util play]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(def a-hand {:rock 1, :paper 2, :scissors 5, :fire 5, :water 5})
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;my-stuff.neural_net/a-hand</span>","value":"#'my-stuff.neural_net/a-hand"}
;; <=

;; @@
(defn val-fractions [inv]
  (let [values (map last inv)
        total (reduce + values)]
    (if (zero? total)
      	values
    	(map #(/ % total) values))))

;(defn inventory-percentages [inv]
;    (map #(/ % (reduce + (map last inv)))
;         (map last inv)))

(val-fractions a-hand)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-ratio'>1/18</span>","value":"1/18"},{"type":"html","content":"<span class='clj-ratio'>1/9</span>","value":"1/9"},{"type":"html","content":"<span class='clj-ratio'>5/18</span>","value":"5/18"},{"type":"html","content":"<span class='clj-ratio'>5/18</span>","value":"5/18"},{"type":"html","content":"<span class='clj-ratio'>5/18</span>","value":"5/18"}],"value":"(1/18 1/9 5/18 5/18 5/18)"}
;; <=

;; @@
(defn consolidateInvs
  "takes in two inventories and consolidates them"
  [inv1 inv2]
  (into [] (concat (val-fractions inv1)
                   (val-fractions inv2))))

(consolidateInvs a-hand a-hand)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-ratio'>1/18</span>","value":"1/18"},{"type":"html","content":"<span class='clj-ratio'>1/9</span>","value":"1/9"},{"type":"html","content":"<span class='clj-ratio'>5/18</span>","value":"5/18"},{"type":"html","content":"<span class='clj-ratio'>5/18</span>","value":"5/18"},{"type":"html","content":"<span class='clj-ratio'>5/18</span>","value":"5/18"},{"type":"html","content":"<span class='clj-ratio'>1/18</span>","value":"1/18"},{"type":"html","content":"<span class='clj-ratio'>1/9</span>","value":"1/9"},{"type":"html","content":"<span class='clj-ratio'>5/18</span>","value":"5/18"},{"type":"html","content":"<span class='clj-ratio'>5/18</span>","value":"5/18"},{"type":"html","content":"<span class='clj-ratio'>5/18</span>","value":"5/18"}],"value":"[1/18 1/9 5/18 5/18 5/18 1/18 1/9 5/18 5/18 5/18]"}
;; <=

;; @@
(defn key-fractions
  "https://clojuredocs.org/clojure.core/reduce#example-588a9031e4b01f4add58fe32"
  [inv]
  (let [total (reduce + (map last inv))]
    (if (zero? total)
      false
      (reduce (fn [p [k v]]
                (into p {k (/ v total)}))
              {} ;first value for p
              inv))))

(key-fractions a-hand)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:rock</span>","value":":rock"},{"type":"html","content":"<span class='clj-ratio'>1/18</span>","value":"1/18"}],"value":"[:rock 1/18]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:paper</span>","value":":paper"},{"type":"html","content":"<span class='clj-ratio'>1/9</span>","value":"1/9"}],"value":"[:paper 1/9]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:scissors</span>","value":":scissors"},{"type":"html","content":"<span class='clj-ratio'>5/18</span>","value":"5/18"}],"value":"[:scissors 5/18]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:fire</span>","value":":fire"},{"type":"html","content":"<span class='clj-ratio'>5/18</span>","value":"5/18"}],"value":"[:fire 5/18]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:water</span>","value":":water"},{"type":"html","content":"<span class='clj-ratio'>5/18</span>","value":"5/18"}],"value":"[:water 5/18]"}],"value":"{:rock 1/18, :paper 1/9, :scissors 5/18, :fire 5/18, :water 5/18}"}
;; <=

;; @@

(comment
(defn getTargetData
  "takes in the previous plays details and it's outcome and
  returns what the NN should've gotten for the last play to /win/"
  [ourLastInv opponentsLastPlay]
  (let [ourKeyFractions (key-fractions ourLastInv)]
    ;(into [] (val-fractions 
    (into {} (for [eachKey (keys ourLastInv)]
               (if (or (zero? (eachKey ourLastInv))
                       (= eachKey opponentsLastPlay))
                 {eachKey -1}
                 (let [ourGain (first (outcome eachKey
                                               opponentsLastPlay))]
                   (do (println eachKey ourGain)
                     (let [multiplier (if (< 1 (count ourGain))
                                        (/ 13 10)
                                        (count ourGain))
                           ;if we don't lose our hand then get the fraction of the hand that we get
                           ;else assign fraction of the hand that we played
                           ourGainKeyFrac (if-not (zero? multiplier)
                                            ((first ourGain) ourKeyFractions)
                                            (/ (eachKey ourKeyFractions) 2))]
                       (if (zero? multiplier)
                         (if (< (/ 1 5) ourGainKeyFrac)
                           {eachKey ourGainKeyFrac}
                           {eachKey 0})
                         (if (> (/ 1 5) ourGainKeyFrac)
                           {eachKey (* (- (eachKey ourKeyFractions) ourGainKeyFrac)
                                       multiplier)}
                           {eachKey 0}))))))))))
)



(defn getTargetData
  "takes in the previous plays details and it's outcome and
  returns what the NN should've gotten for the last play to /win/"
  [ourLastInv opponentsLastPlay]
  (let [ourKeyFractions (key-fractions ourLastInv)]
    (into [] (val-fractions (into {} (for [eachKey (keys ourLastInv)]
                                       (if (or (zero? (eachKey ourLastInv))
                                               (= eachKey opponentsLastPlay))
                                         {eachKey -1}
                                         (let [ourGain (first (outcome eachKey
                                                                       opponentsLastPlay))]
                                           (do (println eachKey ourGain)
                                             (let [multiplier (if (< 1 (count ourGain))
                                                                (/ 13 10)
                                                                1)
                                                   ;if we don't lose our hand then get the fraction of the hand that we get
                                                   ;else assign fraction of the hand that we played
                                                   ourGainKeyFrac (if-not (zero? (count ourGain))
                                                                    ((first ourGain) ourKeyFractions)
                                                                    (/ (eachKey ourKeyFractions) 2))]
                                               {eachKey (* (eachKey ourKeyFractions)
                                                           (- 1 (* ourGainKeyFrac
                                                                   multiplier)))}))))))))))

;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;my-stuff.neural_net/getTargetData</span>","value":"#'my-stuff.neural_net/getTargetData"}
;; <=

;; @@
(eachKey ourKeyFractions) higher-better
(ourGainKeyFrac) lower-better

:paper 10 get :rock 3
10/20 * (1-3/20)
(* (eachKey ourKeyFractions) (- 1 ourGainKeyFrac))

if both low
- don't do
if both high
- consider
if have high gain low
- do
if have low gain high
- don't do
;; @@

;; @@
(getTargetData {:rock 1 :paper 1 :scissors 2 :fire 6 :water 4} :water)
;; @@
;; ->
;;; :rock []
;;; :paper [:paper]
;;; :scissors [:water]
;;; :fire []
;;; 
;; <-
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-ratio'>-27/167</span>","value":"-27/167"},{"type":"html","content":"<span class='clj-ratio'>-26/167</span>","value":"-26/167"},{"type":"html","content":"<span class='clj-ratio'>-40/167</span>","value":"-40/167"},{"type":"html","content":"<span class='clj-ratio'>-132/167</span>","value":"-132/167"},{"type":"html","content":"<span class='clj-ratio'>392/167</span>","value":"392/167"}],"value":"[-27/167 -26/167 -40/167 -132/167 392/167]"}
;; <=

;; @@
(outcome :rock :paper)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:paper</span>","value":":paper"}],"value":"[:paper]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:rock</span>","value":":rock"}],"value":"[:rock]"}],"value":"[[:paper] [:rock]]"}
;; <=

;; @@
(defn getLearningRate
  [inv played]
  (/ (played (key-fractions inv)) 3))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;my-stuff.neural_net/getLearningRate</span>","value":"#'my-stuff.neural_net/getLearningRate"}
;; <=

;; @@
(defn neuralNetPlay
  "takes in the NN and the input data, plugs it into the NN, and
  translates the result into a hand to play, returning it"
  [inputData neuralNetwork]
  (let [output (ff inputData neuralNetwork)]
    ;;it works, decently too
    (key (apply max-key val {:rock (nth output 0),
                             :paper (nth output 1),
                             :scissors (nth output 2),
                             :fire (nth output 3),
                             :water (nth output 4)}))))
;; @@

;; @@
(defn neural-pf
  "brainpower"
  [self other-skin]
  (if-not (nil? (last (:history self)))
    (let [currentInvs (consolidateInvs (:inventory self)
                                       (:inventory other-skin))
          opponentsLastPlay (:opponent-play (last (:history self)))
          newNN (train-data (:nn (:memory self))
                            [(:lastInvs (:memory self))
                             (getTargetData (:ourLastInv (:memory self))
                                            opponentsLastPlay)]
                            (getLearningRate (:theirLastInv (:memory self))
                                             opponentsLastPlay))]
      {:play (neuralNetPlay currentInvs newNN)
       :memory {:nn newNN
                :lastInvs currentInvs
                :ourLastInv (:inventory self)
                :theirLastInv (:inventory self)}})
    ;;fixing?
    {:play (rand-nth [:rock :paper :scissors :fire :water])
     :memory {:nn (construct-network 10 10 5)
              :lastInvs (consolidateInvs (:inventory self)
                                         (:inventory other-skin))}}))
;; @@

;; @@
{:name Rocky, :play :paper, :opponent-play :rock}
{:rock 0, :paper 1, :scissors 2, :fire 3, :water 4} :paper
{:rock 4, :paper 3, :scissors 2, :fire 1, :water 0} :rock
swaps


what we got
what we should've played to get what we wanted

wanted = the hand we could've got that was the least

{:rock 2/3, :paper 1/3, :scissors 1/3, :fire 0, :water 0}

(* (+ 1 percentage))

(for [i (range 0 (count ))]
  (eachKey cd))
(if (zero? (keyAt ourLastInv))
  0
  (if (outcome keyAt (:opponent-play lastPlay))
  (if (== keyAt (:opponent-play lastPlay))
    (/ 1 3)
   ()

gets a hand that is part of the lower half
;; @@
