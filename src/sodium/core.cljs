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
  "Return a function suitable for an on-* handler, to deliver the value
  into into a re-frame event. See also >atom.
  The first argument is a re-frame event vector, into which the value
  will be conjed as the final element.
  It is followed by two optional arguments: a default value that will
  be used when the value is empty, and a 'coercer' function to convert
  the value into a suitable form for the event.
  Note that the default value is _not_ passed through the coercer."
  ([event]
   (>event event ""))
  ([event default]
   (>event event default identity))
  ([event default coercer]
   #(>evt (let [value (or (.-value %2) (.-checked %2))]
            (conj event
                  (coercer (if (empty? value)
                             default
                             value)))))))

(defn >atom
  "Return a function suitable for an on-* handler, to deliver the value
  into into an atom. This would typically be used to store a result into
  a local reagent atom. See also >event.
  The first argument is a re-frame event vector, into which the value
  will be conjed as the final element.
  It is followed by two optional arguments: a default value that will
  be used when the value is empty, and a 'coercer' function to convert
  the value into a suitable form for the event.
  Note that the default value is _not_ passed through the coercer."
  ([atom]
   (>atom atom ""))
  ([atom default]
   (>atom atom default identity))
  ([atom default coercer]
   #(->> (or (.-value %2) (.-checked %2))
         coercer
         (reset! atom))))


(defn list-option
  "Convert value and text info format suitable for a React list element
  for Dropdown or Select lists."
  [value text]
  {:key value :value value :text text})

(defn dropdown-list
  "Generate a list suitable for a Dropdown or Select list of options.
  Items is the raw list, while value-n and text-fn extract value and
  text from each element."
  [items value-fn text-fn]
  (mapv (fn [item]
         (list-option (value-fn item) (text-fn item)))
       items))


(defn <atom
  "Get a value from an atom. Suitable to use, e.g., as the :value or
  :default-value parameter to a control.
  Atom is the atom to be dereferenced. It will be translated by access-fn.
  If null, default will be supplied instead."
  ([atom]
   (<atom atom nil))
  ([atom default]
   (<atom atom default identity))
  ([atom default access-fn]
   (or (access-fn @atom) default)))


;;; Controls that we supply so far. More coming soon.
;;; The final argument here is a descriptor the acceptable parameters.
;;; For more details about this, see keys.clj and macros.clj
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

;;; This is (at least for now) how we define controls that don't fit
;;; into the def-simple-control cookie-cutter regime.
;;; Actually, all that's really non-standard here is the supplying
;;; of "button" as the default type.
(defcontrol form-button [params]
  {::key-sets [:basic :form-field :button]
   ::keys [data-tooltip]
   :pre [(utils/validate (s/nilable ifn?) on-click)
         (utils/validate (s/nilable string?) data-tooltip)]}
  [sa/FormButton (-> params
                     (assoc :type (or type "button"))
                     (utils/camelize-map-keys :exclude [:data-tooltip]))])

