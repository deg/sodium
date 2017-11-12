;;; Author: David Goldfarb (deg@degel.com)
;;; Copyright (c) 2017, David Goldfarb

(ns sodium.extensions
  (:require
   [clojure.spec.alpha :as s]
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [re-frame.loggers :refer [console]]
   [sodium.core :as na]
   [sodium.re-utils :refer [<sub >evt]]
   [sodium.utils :as utils]))


;;; HEADERS

;;; Various page and section headers/dividers

(defn- header-maker [title size dividing? sub?]
  {:pre [(utils/validate (s/or :string string? :event vector?) title)
         (utils/validate boolean? dividing?)
         (utils/validate :sodium/size size)]}
  (na/header {:content (if (vector? title)
                         (str (<sub title))
                         title)
              :size size
              :dividing? dividing?
              :sub? sub?}))

(defn app-header
  "Large header. Title can be either a string or a subscription vector"
  [title]
  (header-maker title :large true false))

(defn panel-header
  "Medium header. Title can be either a string or a subscription vector"
  [title]
  (header-maker title :medium false false))

(defn panel-subheader
  "Small header. Title can be either a string or a subscription vector"
  [title]
  (header-maker title :small false false))

(defn section-header
  "Medium de-emphasized header. Title can be either a string or a subscription vector"
  [title]
  (header-maker title :medium false true))

(defn subsection-header
  "Small de-emphasized header. Title can be either a string or a subscription vector"
  [title]
  (header-maker title :small false true))



;;; FORM FIELDS

(defn labelled-field
  "Form field with a label and arbitrary content"
  [& {:keys [label content field-key errors inline?]}]
  (let [error (and field-key (field-key errors))]
    [na/form-input {:inline? inline? :label label}
     content
     (when error
       [na/rail {:position "right" :attached? true :class-name "errmsg"} error])]))



;;; TAGSONOMY

(re-frame/reg-sub
 ;; Default sub for getting the set of all tags.
 ::all-tags
 (fn [db _]
   (set (get db ::all-tags))))

(re-frame/reg-event-db
 ;; Default event for setting the set of all tags
 ::all-tags
 (fn [db [_ tags]]
   (assoc db ::all-tags (set tags))))

(re-frame/reg-sub
 ;; Default sub for getting the set of selected tags.
 ::selected-tags
 (fn [db _]
   (set (get db ::selected-tags))))

(re-frame/reg-sub
 ::class-of-tag
 ;; Default sub for getting the class of a tag
 (fn [db [_ tag]] "tag"))


(re-frame/reg-event-db
 ;; Default event for setting set of selected tags
 ::selected-tags
 (fn [db [_ tags]]
   (assoc db ::selected-tags (set tags))))


(defn- draw-tag
  "Draw one tag in a list of tags. See draw-tags"
  [{:keys [selected-tags-sub
           set-selected-tags-event
           class-of-tag-sub
           selected-class
           unselected-class]} tag]
  (let [selected-tags (<sub selected-tags-sub #{})
        selected? (contains? selected-tags tag)]
    [na/list-item {:key tag
                   :on-click #(>evt (conj set-selected-tags-event
                                          ((if selected? disj conj) selected-tags tag)))}
     [:span {:class (str (<sub (conj class-of-tag-sub tag))
                         " "
                         (if selected? selected-class unselected-class))}
      tag]]))


(defn draw-tags
  "Draw a list of tags. Formatting will depend on whether the tag is in the list of
  selected tags. Clicking on a tag will toggle it between selected and unselected.

  Options:
  - :selected-tags-sub       - Re-frame subscription that returns the set of selected tags
  - :set-selected-tags-event - Re-frame event that sets the set of selected tags
  - :class-of-tag-sub       - Re-frame subscription that returns the CSS class for a tag
  - :selected-class          - CSS class name for selected tags
  - :unselected-class        - CSS class name for unselected tags
  - :sort?                   - Should the list of tags be sorted

  - tags                     - Set or sequence of tags to display"
  [{:keys [selected-tags-sub
           set-selected-tags-event
           class-of-tag-sub
           selected-class
           unselected-class
           sort?]
    :or {selected-tags-sub       [::selected-tags]
         set-selected-tags-event [::selected-tags]
         class-of-tag-sub        [::class-of-tag]
         selected-class          "selected-tag"
         unselected-class        "unselected-tag"
         sort?                   true}}
   tags]
  [na/list-na {:class-name "tags"
               :horizontal? true}
   (doall (map (partial draw-tag {:selected-tags-sub       selected-tags-sub
                                  :set-selected-tags-event set-selected-tags-event
                                  :class-of-tag-sub        class-of-tag-sub
                                  :selected-class          selected-class
                                  :unselected-class        unselected-class})
               (if sort?
                 (utils/ci-sort tags)
                 tags)))])


(defn tag-adder
  "Component that lets the user add a tag (existing or new) to the set of selected tags.

  Options:
  - :all-tags-sub            - Re-frame subscription that returns the set of all tags
  - :selected-tags-sub       - Re-frame subscription that returns the set of selected tags
  - :set-selected-tags-event - Re-frame event that sets the set of selected tags
  - :partial-tag-text        - Atom to use to hold text of new tag before it is added. This
                               parameter is not often needed, but is important if something
                               outside us needs to watch our exact state. I use this, for
                               example, when I don't want to let a dialog close if the user
                               has started to create a new tag but has not yet saved it.   "
  [{:keys [all-tags-sub selected-tags-sub set-selected-tags-event partial-tag-text]
    :or {all-tags-sub            [::all-tags]
         selected-tags-sub       [::selected-tags]
         set-selected-tags-event [::selected-tags]
         partial-tag-text        (reagent/atom "")}}]
  (fn []
    (let [all-tags (<sub all-tags-sub)
          selected-tags (<sub selected-tags-sub #{})
          available-tags (utils/ci-sort (clojure.set/difference all-tags selected-tags))
          list-id (str (gensym "tags-"))
          input-id (str (gensym "tags-input-"))]
      [na/grid {:container? true}
       [na/grid-row {}
        [draw-tags {:selected-tags-sub       selected-tags-sub
                    :set-selected-tags-event set-selected-tags-event}
         selected-tags]]
       [na/grid-row {}
        `[:datalist {:id ~list-id}
          ~(map (fn [tag] [:option {:key tag :value tag}])
                available-tags)]
        [na/input {:type :text
                   :id input-id
                   :list list-id
                   ;; [???] Setting :value fails subtly, updating datalist options for
                   ;; the previous character entered as each character is entered.
                   ;; So, :default-value and pay the piper below
                   :default-value (na/<atom partial-tag-text "")
                   :on-change (na/>atom partial-tag-text)
                   :action (when-not (empty? @partial-tag-text)
                             {:icon "add"
                              :on-click #(let [tag (conj selected-tags @partial-tag-text)]
                                           (>evt (conj set-selected-tags-event tag))
                                           (reset! partial-tag-text "")
                                           ;; Need to clear field explicitly, because
                                           ;; :default-value above
                                           (set! (.-value (.getElementById js/document input-id)) ""))})
                   :placeholder "add tag"}]]])))


(defn tag-selector
  "Component that lets the user select tags

  Options:
  - :all-tags-sub            - Re-frame subscription that returns the set of all tags
  - :selected-tags-sub       - Re-frame subscription that returns the set of selected tags
  - :set-selected-tags-event - Re-frame event that sets the set of selected tags"
  [{:keys [all-tags-sub selected-tags-sub set-selected-tags-event]
    :or {all-tags-sub            [::all-tags]
         selected-tags-sub       [::selected-tags]
         set-selected-tags-event [::selected-tags]}}]
  (let [available-tags (utils/ci-sort (<sub all-tags-sub))
        chosen-tags (utils/ci-sort (<sub selected-tags-sub #{}))]
    [na/dropdown {:multiple? true
                  :button? true
                  :value chosen-tags
                  :on-change (na/>event set-selected-tags-event #{} set)
                  :options (na/dropdown-list available-tags identity identity)}]))


;;; GOOGLE ADS

(defn google-ad
  "Google advert component. See
  https://react.semantic-ui.com/views/advertisement and
  https://www.google.com/adsense.

  - unit, ad-client, ad-slot - Supplied by your Google ad campaign
  - test - Text to render instead of a real ad. You will typically supply
  this in your development builds."
  [& {:keys [unit ad-client ad-slot test]}]
  (reagent/create-class
   {:display-name "google-ad"
    :component-did-mount
    #(when (and js.window.adsbygoogle (not test))
       (. js.window.adsbygoogle push {}))
    :reagent-render
    (fn [& {:keys [unit ad-client ad-slot]}]
      [na/advertisement {:unit unit :centered? true :test test}
       (when-not test
         [:ins {:class-name "adsbygoogle"
                :style {:display "block"}
                :data-ad-format "auto"
                :data-ad-client ad-client
                :data-ad-slot ad-slot}])])}))

