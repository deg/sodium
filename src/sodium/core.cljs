;;; Author: David Goldfarb (deg@degel.com)
;;; Copyright (c) 2017, David Goldfarb

(ns sodium.core
  (:require-macros
   [sodium.macros :refer [defcontrol def-simple-control]])
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

(defn >atom
  ([atom]
   (>atom atom identity))
  ([atom coercer]
   #(->> (or (.-value %2) (.-checked %2))
         coercer
         (reset! atom))))

(defn list-option [value text]
  {:key value :value value :text text})

(defn dropdown-list [items value-fn text-fn]
  (mapv (fn [item]
         (list-option (value-fn item) (text-fn item)))
       items))

(defn <atom
  ([atom]
   (<atom atom nil))
  ([atom default]
   (<atom atom default identity))
  ([atom default access-fn]
   (or (access-fn @atom) default)))

(defcontrol form-button [params]
  {::key-sets [:basic :form-field :button]
   ::keys [data-tooltip]
   :pre [(utils/validate (s/nilable ifn?) on-click)
         (utils/validate (s/nilable string?) data-tooltip)]}
  [sa/FormButton (-> params
                     (assoc :type (or type "button"))
                     (utils/camelize-map-keys :exclude [:data-tooltip]))])

(def-simple-control checkbox   sa/Checkbox  [:basic :checkbox])
(def-simple-control container  sa/Container [:basic :container])
(def-simple-control dropdown   sa/Dropdown  [:basic :dropdown])
(def-simple-control form       sa/Form      [:basic :form])
(def-simple-control form-input sa/FormInput [:basic :form-field :input :input-html])
(def-simple-control form-group sa/FormGroup [:basic :form-group])
(def-simple-control grid       sa/Grid      [:basic :grid])
(def-simple-control header     sa/Header    [:basic :header])
(def-simple-control input      sa/Input     [:basic :form-field :input :input-html])
(def-simple-control menu       sa/Menu      [:basic :menu])
(def-simple-control menu-item  sa/MenuItem  [:basic :menu-item])
(def-simple-control rail       sa/Rail      [:basic :rail])
(def-simple-control text-area  sa/TextArea  [:basic :form-field :input :input-html :text-area])
