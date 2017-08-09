;;; Author: David Goldfarb (deg@degel.com)
;;; Copyright (c) 2017, David Goldfarb

(ns sodium.keys
  [:require [sodium.macros :refer [add-key-set]]])


;;; Common parameters; elided from the other sets below
(add-key-set :basic
             [:as             ;; custom
              :children       ;; node
              :class-name     ;; string
              ])


;;; From https://react.semantic-ui.com/modules/checkbox
(add-key-set :checkbox
             [:checked?
              :default-checked?
              :default-indeterminate?
              :disabled?
              :fitted?
              :indeterminate?
              :label
              :name
              :on-change
              :on-click
              :on-mouse-down
              :radio
              :read-only?
              :slider
              :tab-index
              :toggle
              :type
              :value])


;;; From https://react.semantic-ui.com/elements/container
(add-key-set :container
             [:fluid?
              :text
              :text-align])

;;; From https://react.semantic-ui.com/modules/dropdown
(add-key-set :dropdown
             [:addition-label
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
              ])


;;; From https://react.semantic-ui.com/collections/grid
(add-key-set :grid
             [:celled
              :centered?
              :columns
              :container?
              :divided
              :doubling?
              :inverted?
              :padded
              :relaxed
              :reversed
              :stackable?
              :stretched?
              :text-align
              :vertical-align])

;;; From https://react.semantic-ui.com/collections/form
(add-key-set :form
             [:action
              :error?
              :inverted?
              :loading?
              :on-submit
              :reply?
              :size
              :success?
              :warning?
              :widths
              ])


;;; From https://react.semantic-ui.com/collections/form (tab: Form.Field)
(add-key-set :form-field
             [:control        ;; custom (mutually exclusive with :children)
              :disabled?      ;; bool
              :error?         ;; bool
              :inline?        ;; bool
              :label          ;; node|object (mutually exclusive with :children)
              :required?      ;; bool
              :type           ;; custom
              :width          ;; enum: [1,,, 16, one,,, sixteen]
              ])

;;; From https://react.semantic-ui.com/collections/form
(add-key-set :form-group
             [:grouped
              :inline
              :widths])


;;; From https://react.semantic-ui.com/elements/button
(add-key-set :button
             [:active?        ;; bool
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
              ])


;;; From https://react.semantic-ui.com/elements/header
(add-key-set :header
             [:attached
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
              ])


;;; From https://react.semantic-ui.com/elements/input
(add-key-set :input
             [:action
              :action-position
              :default-value           ;; *** NOT LISTED IN DOC!
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
              ])


;;; HTML input parameters not listed in the semantic-ui docs
(add-key-set :input-html
             [:value
              :step
              :placeholder
              ])


;;; From https://react.semantic-ui.com/collections/menu
(add-key-set :menu
             [:active-index
              :attached
              :color
              :compact?
              :default-active-index
              :fixed
              :floated
              :fluid?
              :icon
              :inverted?
              :items
              :on-item-click
              :pagination?
              :pointing?
              :secondary?
              :size
              :stackable?
              :tabular?
              :text?
              :vertical?
              :widths
              ])


;;; From https://react.semantic-ui.com/collections/menu (tab: Menu.Item)
(add-key-set :menu-item
             [:active?
              :color
              :content
              :disabled?
              :fitted
              :header?
              :icon
              :index
              :link?
              :name
              :on-click
              :position
              ])

;;; From https://react.semantic-ui.com/elements/rail
(add-key-set :rail
             [:attached?
              :close
              :dividing?
              :internal?
              :position
              :size])


;;; From https://react.semantic-ui.com/addons/text-area
(add-key-set :text-area
             [:auto-height
              :on-change
              :rows
              :style
              :value])
