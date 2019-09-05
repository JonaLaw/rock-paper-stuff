;; gorilla-repl.fileformat = 1

;; @@
(defn bestplay
  [player1 player2]
  (let [player1Inv (:inventory (summary player1))
        player2Max (key (apply max-key val (:inventory (summary player2))))]
    (if (apply = (vals player1Inv))
      player2Max
      (getToPlay (keys (into (sorted-map-by (fn [key1 key2] (compare (key2 player1Inv) (key1 player1Inv)))) player1Inv))
                 player2Max))))

(defn getToPlay
  [inv1 max2]
  (if (= (last inv1) (first (first (outcome (first inv1) max2))))
      (first inv1)
      (recur (rest inv1) max2)))
;; @@

;; @@
(defn getSortedKeys
  [inv]
  (keys (into (sorted-map-by (fn [key1 key2] (compare (key2 inv) (key1 inv)))) inv)))

(defn bestplay2
  [inv1 inv2]
  (let [sortedInv1Keys (getSortedKeys inv1)
        sortedInv2Keys (getSortedKeys inv2)]
    (if (apply = (vals inv1)
        (first sortedInv2Keys)
        (loop [at 0]
          (let [toPlay (getToPlay2 sortedInv1 (nth sortedInv2 at)]
            (if (nil? toPlay)
              (recur (+ at 1))
              toPlay))))))))

(defn getToPlay2
  [inv1 max2]
  (if (empty? inv1)
    nil
    (if (= (first inv1) max2)
      nil
    (if (= (last inv1) (first (first (outcome (first inv1) max2))))
        (first inv1)
        (recur (rest inv1) max2)))))
;; @@

;; @@
(defn getSorted
  [inv]
  (into (sorted-map-by (fn [key1 key2]
                       (compare [(get inv key2) key2]
                                [(get inv key1) key1])))
        inv))

(defn bestplay3
  [inv1 inv2]
  (let [sortedInv1 (getSorted inv1)
        sortedInv2 (getSorted inv2)]
    (if (apply = (vals inv1))
      (key (first sortedInv2));;;add opponent hurting strategy
      (loop [inv1Search sortedInv1
             inv2Search sortedInv2
             plays {}]
        (if (> 4 (count inv1Search))
          ;(key (first (getSorted plays)))
          plays
          (let [toPlay (getToPlay3 sortedInv1
                                   inv2Search)
                                   {}])
            (if (nil? toPlay)
              (recur inv1Search
                     (merge {} (rest inv2Search))
                     plays)
              (recur (drop-last inv1Search)
                     sortedInv2
                     (merge-with + plays toPlay)))))))))

(defn getToPlay3
  [inv1 inv2 plays]
  (if (> 3 (count inv1))
    plays
    (if (= (key (last inv1))
           (first (first (outcome (key (first inv1))
                                  (key (first inv2))))))
      (recur inv1
             (merge {} (rest inv2))
             (merge-with + plays (assoc nil
                                   (key (first inv1))
                                   (- (+ ((key (first inv1)) inv1)
                                         ((key (first inv2)) inv2))
                                      ((key (last inv1)) inv1)))))
      (recur (merge {} (rest inv1))
             inv2
             plays))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;rock-paper-stuff.play/bestplay3</span>","value":"#'rock-paper-stuff.play/bestplay3"}
;; <=

;; @@
(defn bestplay4
  [inv1 inv2]
  (let [sortedInv1 (getSorted inv1)
        sortedInv2 (getSorted inv2)]
    (if (apply = (vals inv1))
      (key (first sortedInv2));;;add opponent hurting strategy
      (loop [inv1Search sortedInv1
             inv2Search sortedInv2
             plays {}]
        (if (> 4 (count inv1Search))
          (key (first (getSorted plays)))
          (let [toPlay (getToPlay3 sortedInv1 (first inv2Search))]
            (if (nil? toPlay)
              (recur inv1Search
                     (rest inv2Search)
                     plays)
              (recur (drop-last inv1Search)
                     sortedInv2
                     (merge-with + plays toPlay)))))))))
(defn getToPlay4
  [inv1 inv2]
  (if (> 3 (count inv1))
    nil
    (if (= (key (last inv1)) (first (first (outcome (key (first inv1)) (key (first inv2))))))
      (assoc nil
        (key (first inv1))
        (- (+ ((key (first inv1)) inv1)
              ((key (first inv2)) inv2))
           ((key (last inv1)) inv1)))
      (recur (rest inv1)
             inv2))))
;; @@

;; @@
(defn getSorted
  [inv]
  (merge {} (into (sorted-map-by (fn [key1 key2]
                       (compare [(get inv key2) key2]
                                [(get inv key1) key1])))
        inv)))

(defn bestplay5
  [inv1 inv2]
  (let [sortedInv1 (getSorted inv1)
        sortedInv2 (getSorted inv2)]
    (if (apply = (vals inv1))
      (key (last (sortedInv2)))
      (loop [inv2Search sortedInv2
             plays {}]
        (if (or (empty? inv2Search)
                (zero? ((key (first inv2Search)) inv2Search)))
                plays
                (recur (merge {} (rest inv2Search)) ;;necessary reformating for count to work
                       (merge-with + plays (getToPlay5 sortedInv1
                                                       (first inv2Search)
                                                       inv1
                                                       inv2
                                                       {}))))))))

(defn getToPlay5
  [inv1Search inv2At inv1 inv2 plays]
  (if (or (empty? inv1Search)
          (zero? ((key (first inv1Search)) inv1)))
    plays
    (if (= (key (first inv1Search))
           (key inv2At))
      (recur (merge {} (rest inv1Search))
             inv2At
             inv1
             inv2
             plays)
      (let [theoutcome (outcome (key (first inv1Search))
                                (key inv2At))]
        (if (zero? (count (first theoutcome)))
          (recur (merge {} (rest inv1Search))
                 inv2At
                 inv1
                 inv2
                 plays);20
            (recur (merge {} (rest inv1Search))
                   inv2At
                   inv1
                   inv2
                   (merge-with + plays (assoc nil
                                         (key (first inv1Search))
                                         (+ ((key (first inv1Search)) inv1)
                                            ((key inv2At) inv2)
                                            (* -1
                                               ;(count (first theoutcome))
                                               ((first (first theoutcome)) inv1))
                                            (if (zero? (count (last theoutcome)))
                                              0
                                              (* -1
                                                 (count (last theoutcome))
                                                 ((first (last theoutcome)) inv2))))))))))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;rock-paper-stuff.play/getToPlay5</span>","value":"#'rock-paper-stuff.play/getToPlay5"}
;; <=

;; @@
(defn getSorted
  [inv]
  (merge {} (into (sorted-map-by (fn [key1 key2]
                                   (compare [(get inv key2) key2]
                                            [(get inv key1) key1])))
                  inv)))

(defn bestplay6
  [inv1 inv2]
  (let [inv1 (getSorted inv1)
        inv2 (getSorted inv2)]
    (if (apply = (vals inv1))
      (key (last inv2))
      (loop [inv1Search inv1
             plays {}]
        (if (or (empty? inv1Search)
                (zero? ((key (first inv1Search)) inv1Search)))
          ;plays
          (key (apply max-key val plays))
          (recur (merge {} (rest inv1Search)) ;;necessary reformating for count to work
                 (merge plays (assoc nil 
                                (key (first inv1Search))
                                (getToPlay6 inv2
                                          (first inv1Search)
                                          inv1
                                          inv2
                                          0)))))))))

(defn getToPlay6
  [inv2Search inv1At inv1 inv2 playRating]
  (if (or (empty? inv2Search)
          (zero? ((key (first inv2Search)) inv2Search)))
    playRating
    (if (= (key (first inv2Search))
           (key inv1At))
      (recur (merge {} (rest inv2Search))
             inv1At
             inv1
             inv2
             playRating)
      (let [theoutcome (outcome (key inv1At)
                                (key (first inv2Search)))]
        (if (zero? (count (first theoutcome)))
          (recur (merge {} (rest inv2Search))
                 inv1At
                 inv1
                 inv2
                 playRating);ln20
          (recur (merge {} (rest inv2Search))
                 inv1At
                 inv1
                 inv2
                 (+ playRating
                       					   ;# of the hand that we play
                    (Math/round (double (+ ((key inv1At) inv1)
                                           ;# of the hand that they play
                                           ((key (first inv2Search)) inv2)
                                           ;# of the hand that we get that we already have * by # we get modified
                                           (* (if (= 1 (count (first theoutcome)))
                                                -1
                                                (/ -4 3))
                                              ((first (first theoutcome)) inv1))
                                           ;# of the hand that they get that they already have * by # they get modified
                                           (if (zero? (count (last theoutcome)))
                                             0
                                             (* (if (= 1 (count (last theoutcome)))
                                                	-1
                                                  	(/ -4 3))
                                                ((first (last theoutcome)) inv2)))))))))))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;rock-paper-stuff.play/getToPlay6</span>","value":"#'rock-paper-stuff.play/getToPlay6"}
;; <=

;; @@
(def inv1 (getSorted {:rock 7 :paper 9 :scissors 10 :fire 11 :water 0}))
(def inv2 (getSorted {:rock 11 :paper 7 :scissors 12 :fire 8 :water 9}))
inv1
inv2
(bestplay5 inv1 inv2)
(bestplay6 inv1 inv2)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-keyword'>:paper</span>","value":":paper"}
;; <=
