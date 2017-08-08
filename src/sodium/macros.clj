;;; Author: David Goldfarb (deg@degel.com)
;;; Copyright (c) 2017, David Goldfarb

(ns sodium.macros
  (:require
   [clojure.spec.alpha :as s]
   [sodium.utils :as utils]
   #_[soda-ash-macros :refer [semantic-ui-react-tags]]))


(def basic-params
  #{:as             ;; custom
    :children       ;; node
    :class-name     ;; string
    })

(def dropdown-params
  #{:addition-label
    :addition-position
    :allow-additions
    :basic?
    :button?
    :close-on-blur?
    :close-on-change?
    :compact?
    :default-open?
    :default-selected-label
    :default-value
    :disabled?
    :error?
    :floating?
    :fluid?
    :header
    :icon
    :inline?
    :item?
    :labeled?
    :loading?
    :min-characters
    :multiple?
    :no-results-message
    :on-add-item
    :on-blur
    :on-change
    :on-click
    :on-close
    :on-focus
    :on-label-click
    :on-mouse-down
    :on-open
    :on-search-change
    :open?
    :open-on-focus?
    :options
    :placehoder
    :pointing
    :render-label
    :scrolling?
    :search
    :search-input
    :select-on-blur
    :selected-label
    :selection
    :simple?
    :tab-index
    :test
    :trigger
    :upward?
    :value
    })

(def form-params
  #{:action
    :error?
    :inverted?
    :loading?
    :on-submit
    :reply?
    :size
    :success?
    :warning?
    :widths
    })

(def form-field-params
  #{:control        ;; custom (mutually exclusive with :children)
    :disabled?      ;; bool
    :error?         ;; bool
    :inline?        ;; bool
    :label          ;; node|object (mutually exclusive with :children)
    :required?      ;; bool
    :type           ;; custom
    :width          ;; enum: [1,,, 16, one,,, sixteen]
    })

(def button-params
  #{:active?        ;; bool
    :animated       ;; bool|enum
    :attached       ;; enum: [left, right, top, bottom]
    :basic?         ;; bool
    :circular?      ;; bool
    :color          ;; enum: [red orange yellow olive green teal blue violet purple pink brown grey black facebook google plus instagram linkedin twitter vk youtube]
    :compact?       ;; bool
    :content        ;; custom
    :floated        ;; enum: [left right]
    :fluid?         ;; bool
    :icon           ;; custom
    :inverted?      ;; bool
    :label          ;; custom
    :label-position ;; enum [right left]
    :loading?       ;; bool
    :negative?      ;; bool
    :on-click       ;; func
    :positive?      ;; bool
    :primary?       ;; bool
    :secondary?     ;; bool
    :size           ;; enum: [mini tiny small medium large big huge massive]
    :tab-index      ;; number|string
    :toggle?        ;; bool
    })

(def header-params
  #{:attached
    :block?
    :color
    :content
    :dividing?
    :floated
    :icon
    :image
    :inverted?
    :size
    :sub?
    :subheader
    :textAlign
    })

(def input-params
  #{:action
    :action-position
    :error?
    :fluid?
    :focus?
    :icon
    :icon-position
    :input
    :inverted?
    :label
    :label-position
    :loading
    :on-change
    :size
    :tab-index
    :transparent?
    :type
    :value    ;; [TODO] NOT IN LIST ... check!
    :step     ;; [TODO] NOT IN LIST ... check!
    })

(def rail-params
  #{:attached?
    :close
    :dividing?
    :internal?
    :position
    :size})

(defn key-set [key]
  (case key
    :basic      basic-params
    :button     button-params
    :dropdown   dropdown-params
    :form       form-params
    :form-field form-field-params
    :header     header-params
    :input      input-params
    :rail       rail-params
    nil))

(defn merge-keys [keys key-sets]
  (->> (reduce (fn [so-far key]
                 (into so-far (map #(-> % name symbol) (key-set key))))
               (set keys) key-sets)
       (into [])
       (sort-by name)
       (into [])))

(defmacro defcontrol [name [params-var & other-params]
                      {:keys [pre post :sodium.core/keys :sodium.core/key-sets]}
                      & control-body]
  {:pre [(utils/validate symbol? name)
         (utils/validate symbol? params-var)]}
  (let [all-keys (merge-keys keys key-sets)]
    `(defn ~name [{:keys ~all-keys :as ~params-var} ~@other-params]
       {:pre ~(utils/vconcat pre
                             `[(utils/all-keys-valid? (keys ~params-var)
                                                      ~(set (map keyword all-keys)))])
        :post ~post}
       ~@control-body)))



