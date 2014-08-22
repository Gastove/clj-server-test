(ns thermador.data.proto
  (:refer-clojure :rename {get core-get
                           extend core-extend}))

(defn beget
  [parent]
  {:prototype parent})

(defn get
  [pobj k]
  (if-let [res (k pobj)]
    res
    (if-let [proto (:prototype pobj)]
      (get proto k)
      nil)))

(defn extend
  [pobj extention]
  {:pre [(map? extention)
         (contains? pobj :prototype)]}
  (into pobj extention))

(defn key-chain
  "A key can exist in many levels of a prototype chain.
  Returns a list of all the values for a key in Parent->Child order."
  [lookup-key pobj]
  (letfn [(woah [acc [k v]]
            (cond
             (= k lookup-key) (conj acc v)
             (and (not= k lookup-key) (not= k :prototype)) acc
             :else (into acc (reduce woah [] v))))]
    (reverse (reduce woah [] pobj))))