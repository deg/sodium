;;; Author: David Goldfarb (deg@degel.com)
;;; Copyright (c) 2017, David Goldfarb

(ns sodium.core
  (:require-macros
   [sodium.macros :refer [defcomponent def-simple-component]])
  (:require
   [clojure.spec.alpha :as s]
   [re-frame.loggers :refer [console]]
   [soda-ash.core :as sa]
   [sodium.re-utils :refer [<sub >evt]]
   [sodium.utils :as utils]))

(s/def :sodium/size #{:tiny :small :medium :large :huge})

(defn- negligible?
  [x]
  (if (seqable? x) (empty? x) (not x)))

(defn >event
  "Return a function suitable for an on-* handler, to deliver the value
  into into a re-frame event. See also >atom.
  The first argument is a re-frame event vector, into which the value
  will be conjed as the final element.
  It is followed by two optional arguments: a default value that will
  be used when the value is empty, and a 'coercer' function to convert
  the value into a suitable form for the event.
  Note that the default value is _not_ passed through the coercer."
  ;; [TODO] The line about the default value is obviously wrong. Check users and fix!
  ([event]
   (>event event ""))
  ([event default]
   (>event event default identity))
  ([event default coercer]
   #(>evt (let [value (or (.-value %2) (.-checked %2))]
            (conj event
                  (coercer (if (negligible? value)
                             default
                             value)))))))

(defn >events
  "Utility function to dispatch multiple events from an on-* hander.
  The syntax is a bit opaque, because we have to wrap both the event
  parameters and the parameters to >event (default and coercer).

  So, a usage looks like:

    (na/>events [[[:update-age :in-minutes] 42]
                 [[:set-color] :cyan nearest-color]])
  "
  [events]
  (fn [dom-event param-map]
    (run! (fn [event]
            ((apply >event event) dom-event param-map))
          events)))

(defn >atom
  "Return a function suitable for an on-* handler, to deliver the value
  into into an atom. This would typically be used to store a result into
  a local reagent atom. See also >event.
  The first argument is an atom, which the value will be reset! into.
  It is followed by two optional arguments: a default value that will
  be used when the value is empty, and a 'coercer' function to convert
  the value into a suitable form for the event.
  Note that the default value is _not_ passed through the coercer."
  ;; [TODO] Default wrong here too
  ([atom]
   (>atom atom ""))
  ([atom default]
   (>atom atom default identity))
  ([atom default coercer]
   #(->> (or (.-value %2) (.-checked %2))
         js->clj
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
  :default-value parameter to a component.
  Atom is the atom to be dereferenced. It will be translated by access-fn.
  If null, default will be supplied instead."
  ([atom]
   (<atom atom nil))
  ([atom default]
   (<atom atom default identity))
  ([atom default access-fn]
   (or (access-fn @atom) default)))


;;; Components that we supply so far. More coming soon.
;;; The final argument here is the descriptor groups of the acceptable parameters.
;;; For more details about this, see keys.clj and macros.clj
(def-simple-component advertisement     sa/Advertisement    [:basic :advertisement])
(def-simple-component checkbox          sa/Checkbox         [:basic :checkbox])
(def-simple-component container         sa/Container        [:basic :container])
(def-simple-component dropdown          sa/Dropdown         [:basic :dropdown])
(def-simple-component divider           sa/Divider          [:basic :divider])
(def-simple-component form              sa/Form             [:basic :form])
(def-simple-component form-input        sa/FormInput        [:basic :form-field :input :input-html])
(def-simple-component form-group        sa/FormGroup        [:basic :form-group])
(def-simple-component grid              sa/Grid             [:basic :grid])
(def-simple-component grid-column       sa/GridColumn       [:basic :grid-column])
(def-simple-component grid-row          sa/GridRow          [:basic :grid-row])
(def-simple-component header            sa/Header           [:basic :header])
(def-simple-component image             sa/Image            [:basic :image])
(def-simple-component icon              sa/Icon             [:basic :icon])
(def-simple-component icon-group        sa/IconGroup        [:basic :icon-group])
(def-simple-component input             sa/Input            [:basic :form-field :input :input-html])
(def-simple-component label             sa/Label            [:basic :label])
(def-simple-component list-na           sa/ListSA           [:basic :list]) ;; (Renamed, to avoid clash with clojure.core/list)
(def-simple-component list-item         sa/ListItem         [:basic :list-item])
(def-simple-component menu              sa/Menu             [:basic :menu])
(def-simple-component menu-header       sa/MenuHeader       [:basic :menu-header])
(def-simple-component menu-item         sa/MenuItem         [:basic :menu-item])
(def-simple-component menu-menu         sa/MenuMenu         [:basic :menu-menu])
(def-simple-component modal             sa/Modal            [:basic :modal])
(def-simple-component modal-header      sa/ModalHeader      [:basic :modal-header])
(def-simple-component modal-content     sa/ModalContent     [:basic :modal-content])
(def-simple-component modal-description sa/ModalDescription [:basic :modal-description])
(def-simple-component modal-actions     sa/ModalActions     [:basic :modal-actions])
(def-simple-component rail              sa/Rail             [:basic :rail])
(def-simple-component search            sa/Search           [:basic :search])
(def-simple-component segment           sa/Segment          [:basic :segment])
(def-simple-component segment-group     sa/SegmentGroup     [:basic :segment-group])
(def-simple-component text-area         sa/TextArea         [:basic :form-field :input :input-html :text-area])

;;; This is (at least for now) how we define components that don't fit
;;; into the def-simple-component cookie-cutter regime.
;;; Actually, all that's really non-standard here is the supplying
;;; of "button" as the default type.
;;; [TODO] Explain data-tooltip too
;;; [TODO] Combine form-button and button code
(defcomponent form-button [params]
  {::key-sets [:basic :form-field :button]
   ::keys [data-tooltip]
   :pre [(utils/validate (s/nilable ifn?) (:on-click params))
         (utils/validate (s/nilable string?) (:data-tooltip params))]}
  [sa/FormButton (-> params
                     (update :type #(or % "button"))
                     (utils/camelize-map-keys :exclude [:data-tooltip]))])

(defcomponent button [params]
  {::key-sets [:basic :button]
   ::keys [data-tooltip]
   :pre [(utils/validate (s/nilable ifn?) (:on-click params))
         (utils/validate (s/nilable string?) (:data-tooltip params))]}
  [sa/Button (-> params
                 (update :type #(or % "button"))
                 (utils/camelize-map-keys :exclude [:data-tooltip]))])


