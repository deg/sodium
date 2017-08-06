;;; Author: David Goldfarb (deg@degel.com)
;;; Copyright (c) 2017, David Goldfarb

(ns sodium.core
  (:require
   [clojure.spec.alpha :as s]
   [soda-ash.core :as sa]
   [sodium.utils :as utils]))

(def forbidden-keywords #{:active :block :circular :className :compact :disabled :dividing :error :inline
                          :inverted :loading :negative :onClick :positive :primary :required :secondary :sub
                          :tabIndex :toggle})

(defn forbidden-keys [params]
  (-> params
      keys
      set
      (clojure.set/intersection forbidden-keywords)))

(defn no-forbidden? [params]
  (let [forbidden (forbidden-keys params)]
    (if (empty? forbidden)
      true
      (do (js/console.error "Found forbidden keys: " forbidden "in " params)
        false))))

(defn header [& {:keys [as attached block? children class-name color content disabled? dividing?
                        floated icon image inverted? size sub? subheader text-align]
                 :as params}]
  {:pre [(no-forbidden? params)]}
  [sa/Header (utils/camelize-map-keys params)])

(defn event-dispatcher [event]
  #(utils/>evt (if-let [value (.-value %2)]
                 (conj event value)
                 event)))

(defn subst-key [m from-key to-key update-fn]
  (if-let [raw (from-key m)]
    (-> m
        (dissoc from-key)
        (assoc to-key (update-fn raw)))
    m))

(defn form-button [& {:keys [active? animated as attached basic
                             children circular? class-name ::click-event color compact? content control
                             data-tooltip
                             disabled? error? floated fluid icon
                             inline? inverted? label label-position loading? negative?
                             on-click positive? primary? required? secondary? size tab-index
                             toggle? type width]
                      :as params}]
  {:pre [(no-forbidden? params)
         (utils/validate (s/nilable ifn?) on-click)
         (utils/validate (s/nilable ::utils/event-vector) click-event)
         (utils/validate (s/nilable string?) data-tooltip)]}
  [sa/FormButton (-> params
                     (assoc :type (or type "button"))
                     (subst-key ::click-event :on-click event-dispatcher)
                     utils/camelize-map-keys)])
