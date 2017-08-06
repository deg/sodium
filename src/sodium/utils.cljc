;;; Author: David Goldfarb (deg@degel.com)
;;; Copyright (c) 2017, David Goldfarb

(ns sodium.utils
  (:require
   [clojure.spec.alpha :as s]
   [clojure.string :as str]))

(defn validate
  "Like s/valid?, but show the error like s/assert. Useful for pre-conditions."
  [spec x]
  (or (s/valid? spec x)
      (s/explain spec x)))

(defn vconcat [& vecs]
  (vec (apply concat vecs)))

(s/def ::event-vector (s/cat :event keyword? :params (s/* any?)))

(defn unpredicate
  "Remove trailing '?' from predicate, to make suitable for JavaScript"
  [s]
  (if (str/ends-with? s "?")
    (subs s 0 (-> s count dec))
    s))

(defn camelize-str
  "Convert a string from ClojureScript to JavaScript conventions.
  - Replace hyphens with camelCase
  - Remove trailing '?'"
  [s]
  (let [[first-word & more] (str/split (unpredicate s) "-")]
    (if more
      (str first-word (str/join (map str/capitalize more)))
      first-word)))

(defn camelize-key
  "Convert a keyword from ClojureScript to JavaScript conventions.
  - Replace hyphens with camelCase
  - Remove trailing '?'
  - Preserve namespaces as-is"
  [k]
  (keyword (namespace k)
           (camelize-str (name k))))

(defn camelize-map-keys
  "Convert a map from ClojureScript to JavaScript conventions. Change the map
  keys, but leave the values alone.  For convenience, you can pass in a seq
  of keywords that must be excluded (left unchanged)."
  [m & {:keys [exclude]}]
  (reduce-kv (fn [m k v]
               (assoc m
                      (if (some #{k} exclude) k (camelize-key k))
                      v))
             {} m))

(defn err [& strings]
  (apply #?(:clj println :cljs js/console.error) strings))

(defn all-keys-valid? [keys universe]
  {:pre [(validate set? universe)]}
  (if (clojure.set/subset? (set keys) (set universe))
    true
    (run! (fn [key]
            (when-not (contains? universe key)
              (err "Keyword " key " is invalid. Not in " universe)))
          keys)))
