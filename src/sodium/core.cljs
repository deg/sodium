;;; Author: David Goldfarb (deg@degel.com)
;;; Copyright (c) 2017, David Goldfarb

(ns sodium.core
  (:require-macros
   [sodium.macros :refer [defcomponent def-simple-component]])
  (:require
   [clojure.spec.alpha :as s]
   [re-frame.loggers :refer [console]]
   [soda-ash.core :as sa]
   [iron.re-utils :refer [<sub >evt]]
   [iron.utils :refer [validate camelize-map-keys negligible?]]
   [sodium.utils :as utils]))

;; [TODO] Need _much_ more type-checking for CSS/HTML/Semantic-UI enumerated types
(s/def :sodium/size #{:tiny :small :medium :large :huge})


(defn value
  "A bit hackish, but I think this is enough to get the useful value
  from any Semantic-UI-React element."
  [_dom_event data]
  (js->clj
   (or (.-value data) (.-checked data))))

(defn value->event-fn
  "Return a function that will collect the value from a
  react-semantic-ui dom event and pass it to a re-frame event"
  ([event] (value->event-fn event {}))
  ([event {:keys [default coercer] :as >evt-params}]
   (fn [dom_event data]
     (>evt event (value dom_event data) >evt-params))))

(defn value->atom-fn
  "Return a function that will collect the value from a
  react-semantic-ui dom event and pass it into an atom"
  ([atom] (value->atom-fn atom {}))
  ([atom {:keys [default coercer assoc-path] :or {coercer identity}}]
   (fn [dom_event data]
     (let [raw-value (value dom_event data)
           value (coercer (if (negligible? raw-value)
                            default
                            raw-value))]
       (if assoc-path
         (swap! atom assoc-in assoc-path value)
         (reset! atom value))
       ;; (See https://github.com/Day8/re-frame/wiki/Beware-Returning-False)
       nil))))


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
   :pre [(validate (s/nilable ifn?) (:on-click params))
         (validate (s/nilable string?) (:data-tooltip params))]}
  [sa/FormButton (-> params
                     (update :type #(or % "button"))
                     (camelize-map-keys :exclude [:data-tooltip]))])

(defcomponent button [params]
  {::key-sets [:basic :button]
   ::keys [data-tooltip]
   :pre [(validate (s/nilable ifn?) (:on-click params))
         (validate (s/nilable string?) (:data-tooltip params))]}
  [sa/Button (-> params
                 (update :type #(or % "button"))
                 (camelize-map-keys :exclude [:data-tooltip]))])
