;;; Author: David Goldfarb (deg@degel.com)
;;; Copyright (c) 2017, David Goldfarb

(ns sodium.macros
  #_(:require
     [soda-ash-macros :refer [semantic-ui-react-tags]]))

(def basic-params
  #{:as             ;; custom
    :children       ;; node
    :class-name     ;; string
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

(defmacro defcontrol [name [[params-var key-sets keys] & other-params] & control-body]
  `(defn ~name [{:keys ~(merge-keys keys key-sets) :as ~params-var} ~@other-params]
     ~@control-body))
