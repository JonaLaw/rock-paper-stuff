;; gorilla-repl.fileformat = 1

;; @@
(ns rock-paper-stuff.neural_net2
  (:use [rock-paper-stuff k9 util play]
        [clojure.pprint]))
;; @@

;; @@
;example hand
(def a-hand {:rock 0, :paper 2, :scissors 5, :fire 6, :water 7})
;; @@

;; @@
(defn val-fractions
  "used to get the fractions of a maps contents"
  [inv]
  (let [values (map last inv)
        total (reduce + values)]
    (if (zero? total)
      values
      (map #(if (zero? %)
               0
               (/ % total))
           values))))

;I wonder which is faster?
;(defn val-fractions [inv]
;    (map #(/ % (reduce + (map last inv)))
;         (map last inv)))

(val-fractions a-hand)
;; @@

;; @@
(defn consolidateInvs
  "takes in two inventories and consolidates them
  used as the neural networks input data"
  [inv1 inv2]
  (into [] (concat (val-fractions inv1)
                   (val-fractions inv2))))

(consolidateInvs a-hand a-hand)
;; @@

;; @@
(defn key-fractions
  "used to get a similar list as val-fractions but with keys so they can be referenced
  https://clojuredocs.org/clojure.core/reduce#example-588a9031e4b01f4add58fe32"
  [inv]
  (let [total (reduce + (map last inv))]
    (if (zero? total)
      false
      (reduce (fn [p [k v]]
                (into p {k (/ v total)}))
              {} ;first value for p
              inv))))

(def a-hand-key-frac (key-fractions a-hand))
a-hand-key-frac
;; @@

;; @@
(defn getRating
  "used to get the rating of a previous hand play 
  general purpose use, isn't final rating"
  [handKey resultKeys ourKeyFractions]
  (let [handFrac (handKey ourKeyFractions)]
    ;if we get nothing, still reward getting rid of hands we have too many of
    (if (zero? (count resultKeys))
      ;finiky, leaving it low
      (* handFrac (/ 1 5))
      (let [resultFrac ((first resultKeys) ourKeyFractions)]
        ;if we get the same hand as we played
        (* (if (= handKey (first resultKeys))
             ;reward a bit if we have enough to play but not if we have too many already
             (if (and (> (/ 3 10) handFrac)
                      (< (/ 3 20) handFrac))
               (- handFrac (* resultFrac (/ 3 4)))
               0)
             ;else rating is equal to what we played - what we got
             ;get rid of hands we have a lot of and aim for hands we have few of
             (- handFrac resultFrac))
           ;reward getting multiples
           (if (= 2 (count resultKeys))
             (/ 5 4)
             1))))))
;; @@

;; @@
(def testKey :scissors)
(def testResult (first (outcome testKey :fire)))

(getRating testKey testResult a-hand-key-frac)
;; @@

;; @@
;explanation in the function declaration below this one
(defn getTargetData
  [ourLastInv opponentsLastPlay]
  (let [ourKeyFractions (key-fractions ourLastInv)]
    (into [] (val-fractions (into {} (for [eachKey (keys ourLastInv)]
                                       (if (or (zero? (eachKey ourLastInv))
                                               (= eachKey opponentsLastPlay))
                                         {eachKey 0}
                                         (let [ourGain (first (outcome eachKey opponentsLastPlay))]
                                           {eachKey (let [rating (getRating eachKey
                                                                            ourGain
                                                                            ourKeyFractions)]
                                                      (if (neg? rating)
                                                        0
                                                        rating))}))))))))
;; @@

;; @@
(defn getTargetDataExample
  "takes in the previous plays details and it's outcome and
  returns what the NN should've gotten for the last play to 'win'"
  [ourLastInv opponentsLastPlay]
  (let [ourKeyFractions (key-fractions ourLastInv)]
    ;into keymap - results from looping through the keys
    (into {} (for [eachKey (keys ourLastInv)]
               ;don't try to play something we don't have or what they played
               (if (or (zero? (eachKey ourLastInv))
                       (= eachKey opponentsLastPlay))
                 (do (println eachKey "can't play")
                   ;this key shall never be picked
                   {eachKey 0})
                 ;get what we gain for playing this key
                 (let [ourGain (first (outcome eachKey opponentsLastPlay))]
                   ;assign this key a rating
                   {eachKey (float (let [rating (getRating eachKey
                                                    ourGain
                                                    ourKeyFractions)]
                              (do (println eachKey "got" ourGain "with a rating of" rating)
                                ;if the rating is negative or zero (convert 0N to int)
                                (if (neg? rating)
                                  ;this key shall never be picked
                                  0
                                  rating))))}))))))
;; @@

;; @@
(getTargetData a-hand :fire)
;; @@

;; @@
(getTargetDataExample a-hand :fire)
;; @@

;; @@
;redefine example hand
(def a-hand {:rock 10, :paper 16, :scissors 12, :fire 10, :water 11})
;; @@

;; @@
(getTargetData a-hand :water)
;; @@

;; @@
(defn getLearningRate
  "learning rate based on the fraction of the played hand's inventory count and then reduced
  this is not ideal for sure"
  [inv played]
  ;(/ (played (key-fractions inv)) 3))
  ;it sure wasn't
  0.2)
;; @@

;; @@
(defn neuralNetPlay
  "takes in the NN and the input data, plugs it into the NN, and
  translates the result into a hand to play, returning it"
  [inputData neuralNetwork]
  (let [output (ff inputData neuralNetwork)]
    ;it works, decently too
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
  ;checking to see if the game is just starting
  (if-not (nil? (last (:history self)))
    ;the input, used to get target data, the new nn
    (let [currentInvs (consolidateInvs (:inventory self)
                                       (:inventory other-skin))
          opponentsLastPlay (:opponent-play (last (:history self)))
          newNN (train-data (:nn (:memory self))
                            [[(:lastInvs (:memory self))
                             (getTargetData (:ourLastInv (:memory self))
                                            opponentsLastPlay)]]
                            (getLearningRate (:theirLastInv (:memory self))
                                             opponentsLastPlay))
          toPlay (neuralNetPlay currentInvs newNN)]
      ;(do (print toPlay)
        {:play toPlay
         :memory {:nn newNN
                  :lastInvs currentInvs
                  :ourLastInv (:inventory self)
                  :theirLastInv (:inventory other-skin)}});)
    ;fixing?
    {:play (rand-nth [:rock :paper :scissors :fire :water])
     :memory {:nn (construct-network 10 10 5)
              :lastInvs (consolidateInvs (:inventory self)
                                         (:inventory other-skin))
              :ourLastInv (:inventory self)
              :theirLastInv (:inventory other-skin)}}))
;; @@

;; @@
(defn rock-pf
  "A player function that always plays :rock."
  [self other-skin]
  {:play :rock})
;; @@

;; @@
(defn maximum-pf
  "A player function that plays one of the kinds of stuff of which the 
  player has most."
  [self other-skin]
  {:play (let [max-val (apply max (vals (:inventory self)))
               candidates (filter (fn [[k v]] (= v max-val)) (:inventory self))]
           (first (first (shuffle candidates))))})
;; @@

;; @@
(pprint
  (map summary
       (:players (play-game [(player "Maxi" maximum-pf)
                             (player "Skynet" neural-pf)]))))
;; @@

;; @@
(:winner (play-game [(player "Maxi" rock-pf)
                     (player "Skynet" neural-pf)]))
;; @@
