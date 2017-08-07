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

(defn >event
  ([event]
   (>event event ""))
  ([event default]
   (>event event default identity))
  ([event default coercer]
   #(utils/>evt (let [value (.-value %2)]
                  (conj event
                        (coercer (if (empty? value)
                                   default
                                   value)))))))

(defn >atom [atom]
  #(reset! atom (.-value %2)))

(defn- vconcat [& vecs]
  (vec (apply concat vecs)))

(defn subst-key [m from-key to-key update-fn]
  (if-let [raw (from-key m)]
    (-> m
        (dissoc from-key)
        (assoc to-key (update-fn raw)))
    m))

(defcontrol header [[params [:basic :header] []]]
  {:pre [(no-forbidden? params)]}
  [sa/Header (utils/camelize-map-keys params)])

(defcontrol form [[params [:basic :form] []] & body]
  (vconcat [sa/Form (utils/camelize-map-keys params)]
           body))

(defcontrol form-button [[params [:basic :form-field :button] [data-tooltip]]]
  {:pre [(no-forbidden? params)
         (utils/validate (s/nilable ifn?) on-click)
         (utils/validate (s/nilable string?) data-tooltip)]}
  [sa/FormButton (-> params
                     (assoc :type (or type "button"))
                     utils/camelize-map-keys)])

(defcontrol form-input [[params [:basic :form-field :input] []] & body]
  (vconcat [sa/FormInput (utils/camelize-map-keys params)]
           body))

(defcontrol input [[params [:basic :form-field :input] []] & body]
  (vconcat [sa/Input (utils/camelize-map-keys params)]
           body))

(defcontrol rail [[params [:basic :rail] []] & body]
  (vconcat [sa/Rail (utils/camelize-map-keys params)]
           body))
