;;; Author: David Goldfarb (deg@degel.com)
;;; Copyright (c) 2017, David Goldfarb

(ns sodium.core
  (:require-macros
   [sodium.macros :refer [defcontrol]])
  (:require
   [clojure.spec.alpha :as s]
   [soda-ash.core :as sa]
   [sodium.re-utils :refer [<sub >evt]]
   [sodium.utils :as utils]))

(defn >event
  ([event]
   (>event event ""))
  ([event default]
   (>event event default identity))
  ([event default coercer]
   #(>evt (let [value (.-value %2)]
            (conj event
                  (coercer (if (empty? value)
                             default
                             value)))))))

(defn >atom [atom]
  #(reset! atom (.-value %2)))

(defn subst-key [m from-key to-key update-fn]
  (if-let [raw (from-key m)]
    (-> m
        (dissoc from-key)
        (assoc to-key (update-fn raw)))
    m))

(defcontrol header [params]
  {::key-sets [:basic :header]}
  [sa/Header (utils/camelize-map-keys params)])

(defcontrol form [params & body]
  {::key-sets [:basic :form]}
  (utils/vconcat [sa/Form (utils/camelize-map-keys params)]
                 body))

(defcontrol form-button [params]
  {::key-sets [:basic :form-field :button]
   ::keys [data-tooltip]
   :pre [(utils/validate (s/nilable ifn?) on-click)
         (utils/validate (s/nilable string?) data-tooltip)]}
  [sa/FormButton (-> params
                     (assoc :type (or type "button"))
                     utils/camelize-map-keys)])

(defcontrol form-input [params & body]
  {::key-sets [:basic :form-field :input]}
  (utils/vconcat [sa/FormInput (utils/camelize-map-keys params)]
           body))

(defcontrol input [params & body]
  {::key-sets [:basic :form-field :input]}
  (utils/vconcat [sa/Input (utils/camelize-map-keys params)]
                 body))

(defcontrol rail [params & body]
  {::key-sets [:basic :rail]}
  (utils/vconcat [sa/Rail (utils/camelize-map-keys params)]
                 body))

(defcontrol dropdown [params & body]
  {::key-sets [:basic :dropdown]}
  (utils/vconcat [sa/Dropdown (utils/camelize-map-keys params)]
                 body))


