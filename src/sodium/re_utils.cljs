;;; Author: David Goldfarb (deg@degel.com)
;;; Copyright (c) 2017, David Goldfarb

(ns sodium.re-utils
  (:require
   [clojure.spec.alpha :as s]
   [clojure.string :as str]
   [re-frame.core :as re-frame]
   [sodium.utils :as utils]))


(defn sub2
  "Shorthand for simple 'layer 2` usage of re-sub"
  [key db-path]
  (re-frame/reg-sub
   key
   (fn [db _] (get-in db db-path))))


;; Ideas based on https://lambdaisland.com/blog/11-02-2017-re-frame-form-1-subscriptions

(def >evt "Shorthand for re-frame dispatch to event."
  re-frame/dispatch)

(defn <sub
  "Shorthand for re-frame subscribe and deref."
  ([subscription]
   (-> subscription re-frame/subscribe deref))
  ([subscription default]
   (or (<sub subscription) default)))



(s/def :re-frame/vec-or-fn (s/or :event-or-sub vector? :function fn?))

(defn- vec->fn [vec-or-fn key-fn]
  {:pre [(utils/validate (s/nilable :re-frame/vec-or-fn) vec-or-fn)]
   :post (fn? %)}
  (if (vector? vec-or-fn)
    #(key-fn (conj vec-or-fn %))
    vec-or-fn))

;; [TODO]
;; - sub->fn should not expect a parameter (right?)
;; - Write doc string for sub->fn
;; - Fix the core >/< functions to use these, rather than duplicating them


(defn event->fn
  "For contexts that want to pass an argument to a sink function: accept
  either a function or a re-frame event vector.
  If a vector is received, convert it to a function that dispatches to that
  event, with the parameter conj'd on to the end."
  [event-or-fn]
  (vec->fn event-or-fn >evt))

(defn sub->fn [sub-or-fn]
  (vec->fn sub-or-fn <sub))

