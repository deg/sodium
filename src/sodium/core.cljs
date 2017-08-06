;;; Author: David Goldfarb (deg@degel.com)
;;; Copyright (c) 2017, David Goldfarb

(ns sodium.core
  (:require-macros
   [sodium.macros :refer [defcontrol]])
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

(defcontrol form-button [:basic :form :button] [params [::click-event data-tooltip]]
  {:pre [(no-forbidden? params)
         (utils/validate (s/nilable ifn?) on-click)
         (utils/validate (s/nilable ::utils/event-vector) click-event)
         (utils/validate (s/nilable string?) data-tooltip)]}
  [sa/FormButton (-> params
                     (assoc :type (or type "button"))
                     (subst-key ::click-event :on-click event-dispatcher)
                     utils/camelize-map-keys)])

(defcontrol header [:basic :header] [params []]
  {:pre [(no-forbidden? params)]}
  [sa/Header (utils/camelize-map-keys params)])

