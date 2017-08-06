;;; Author: David Goldfarb (deg@degel.com)
;;; Copyright (c) 2017, David Goldfarb

(ns sodium.utils
  (:require
   [clojure.spec.alpha :as s]
   [clojure.string :as str]
   [re-frame.core :as re-frame]))

;; Nice sugar from https://lambdaisland.com/blog/11-02-2017-re-frame-form-1-subscriptions
(def <sub "Shorthand for re-frame subscribe and deref."
  (comp deref re-frame/subscribe))
(def >evt "Shorthand for re-frame dispatch to event."
  re-frame/dispatch)

(defn validate
  "Like s/valid?, but show the error like s/assert. Useful for pre-conditions."
  [spec x]
  (or (s/valid? spec x)
      (s/explain spec x)))

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

(def preserved-keys #{:data-tooltip})

(defn camelize-key
  "Convert a keyword from ClojureScript to JavaScript conventions.
  - Replace hyphens with camelCase
  - Remove trailing '?'
  - Preserve namespaces as-is
  - [UGLY ALERT] Do not change certain keywords that JavaScript wants to see hyphenated"
  [k]
  (if (contains? preserved-keys k)
    k
    (keyword (namespace k)
             (camelize-str (name k)))))

(defn camelize-map-keys
  "Convert a map from ClojureScript to JavaScript conventions. Change the map
  keys, but leave the values alone."
  [m]
  (reduce-kv (fn [m k v] (assoc m (camelize-key k) v))
             {} m))




