;;; Author: David Goldfarb (deg@degel.com)
;;; Copyright (c) 2017, David Goldfarb

(ns sodium.keys)

;;; [TODO] Fill in more keys
;;; For mapping of HTML attributes to elements, see https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes
;;; For list of all HTML tags, see http://www.fillster.com/htmlcodes/htmltags.html
;;;   also, https://developer.mozilla.org/en/docs/Web/HTML/Element
;;;   and https://www.w3schools.com/TAGs/default.asp
;;; See also http://learnsemantic.com/




(def ui-key-set-map (atom {}))

(defn key-set [key]
  (key @ui-key-set-map))

(defn add-key-set [key key-set]
  (swap! ui-key-set-map assoc key (set key-set)))


;;; Common parameters; elided from the other sets below
(add-key-set :basic
             [:as             ;; custom
              :children       ;; node
              :class-name     ;; string

              ;; *** NOT LISTED IN DOC!
              :id
              :key
              :style
              :on-click
              ])

;;; From https://react.semantic-ui.com/views/advertisement
(add-key-set :advertisement
             [:centered?
              :test
              :unit])

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
              :disabled?      ;; bool
              :floated        ;; enum: [left right]
              :fluid?         ;; bool
              :icon           ;; custom
              :inverted?      ;; bool
              :label          ;; custom
              :label-position ;; enum [right left]
              :loading?       ;; bool
              :negative?      ;; bool
              :positive?      ;; bool
              :primary?       ;; bool
              :secondary?     ;; bool
              :size           ;; enum: [mini tiny small medium large big huge massive]
              :tab-index      ;; number|string
              :toggle?        ;; bool
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
              :text?
              :text-align])

;;; From https://react.semantic-ui.com/elements/divider
(add-key-set :divider
             [:clearing?
              :fitted?
              :hidden?
              :horizontal?
              :inverted?
              :section?
              :vertical?])

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


;;; From https://react.semantic-ui.com/collections/grid
(add-key-set :grid
             [:celled
              :celled?
              :centered?
              :columns
              :container?
              :divided
              :divided?
              :doubling?
              :inverted?
              :padded
              :relaxed
              :reversed
              :stackable?
              :stretched?
              :text-align
              :vertical-align])

;;; From https://react.semantic-ui.com/collections/grid (tab: Grid.Column)
(add-key-set :grid-column
             [:color
              :computer
              :floated
              :large-screen
              :mobile
              :only
              :stretched?
              :tablet
              :text-align
              :vertical-align
              :wide-screen
              :width])

;;; From https://react.semantic-ui.com/collections/grid (tab: Grid.Row)
(add-key-set :grid-row
             [:centered?
              :color
              :columns
              :divided?
              :only
              :reversed
              :stretched?
              :text-align
              :vertical-align])


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
              :text-align
              ])


;;; From https://react.semantic-ui.com/elements/icon
(add-key-set :icon
             [:bordered?
              :circular?
              :color
              :corner?
              :disabled?
              :fitted?
              :flipped
              :inverted?
              :link?
              :loading?
              :name
              :rotated
              :size
              ])

;;; From https://react.semantic-ui.com/elements/icon (tab: Icon.Group)
(add-key-set :icon-group
             [:size
              ])

;;; From https://react.semantic-ui.com/elements/image
(add-key-set :image
             [:alt
              :avatar?
              :bordered?
              :centered?
              :dimmer
              :disabled?
              :floated
              :Fluid
              :height
              :href
              :inline?
              :label
              :shape
              :size
              :spadced
              :src
              :ui?
              :vertical-align
              :width
              :wrapped
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
              :list                    ;; *** NOT LISTED IN DOC!
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


;;; From https://react.semantic-ui.com/elements/label
(add-key-set :label
             [:active?
              :attached
              :basic?
              :circular?
              :color
              :content
              :corner
              :detail
              :empty
              :floating?
              :horizontal?
              :icon
              :image
              :on-remove
              :pointing
              :remove-icon
              :ribbon
              :size
              :tag?])

;;; From https://react.semantic-ui.com/elements/list
(add-key-set :list
             [:animated?
              :bulleted?
              :celled?
              :divided?
              :floated
              :horizontal?
              :inverted?
              :items
              :link?
              :on-item-click
              :ordered?
              :relaxed
              :selection?
              :size
              :vertical-align])

;;; From https://react.semantic-ui.com/elements/list (tab: List.Item)
(add-key-set :list-item
             [:active?
              :content
              :description
              :disabled?
              :header
              :icon
              :image
              :value])

;;; From https://react.semantic-ui.com/collections/menu
(add-key-set :menu
             [:active-index
              :attached
              :borderless?
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


;;; From https://react.semantic-ui.com/collections/menu (tab: Menu.Header)
(add-key-set :menu-header
             [:content
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
              :position
              ])

;;; From https://react.semantic-ui.com/collections/menu (tab: Menu.Menu)
(add-key-set :menu-menu
             [:position
              ])

;;; From https://react.semantic-ui.com/modules/modal
(add-key-set :modal
             [:actions
              :basic?
              :close-icon
              :close-on-dimmer-click?
              :close-on-document-click?
              :content
              :default-open?
              :dimmer
              :event-pool
              :header
              :mount-node
              :on-action-click
              :on-close
              :on-mount
              :on-open
              :on-unmount
              :open?
              :size
              :style
              ])

;;; From https://react.semantic-ui.com/modules/modal (tab: Modal.Header)
(add-key-set :modal-header
             [:content
              ])

;;; From https://react.semantic-ui.com/modules/modal (tab: Modal.Content)
(add-key-set :modal-content
             [:content
              :image?
              :scrolling?
              ])

;;; From https://react.semantic-ui.com/modules/modal (tab: Modal.Description)
(add-key-set :modal-description
             [
              ])

;;; From https://react.semantic-ui.com/modules/modal (tab: Modal.Actions)
(add-key-set :modal-actions
             [:actions
              :on-action-click])


;;; From https://react.semantic-ui.com/elements/rail
(add-key-set :rail
             [:attached?
              :close
              :dividing?
              :internal?
              :position
              :size])


;;; From https://react.semantic-ui.com/modules/search
(add-key-set :search
             [:aligned
              :category?
              :category-renderer
              :default-open?
              :default-value
              :fluid?
              :icon
              :input
              :loading?
              :min-characters
              :no-results-description
              :no-results-message
              :on-blur
              :on-focus
              :on-mouse-down
              :on-results-select
              :on-search-change
              :on-selection-change
              :open?
              :result-renderer
              :results
              :select-first-result?
              :show-no-results?
              :size
              :value])

;;; From https://react.semantic-ui.com/elements/segment
(add-key-set :segment
             [:attached
              :basic?
              :circular?
              :clearing?
              :color
              :compact?
              :disabled?
              :floated
              :inverted?
              :loading?
              :padded
              :piled?
              :raised?
              :secondary?
              :size
              :stacked?
              :tertiary?
              :text-align
              :vertical?
              ])

;;; From https://react.semantic-ui.com/elements/segment (tab: Segment.Group)
(add-key-set :segment-group
             [:compact?
              :horizontal?
              :piled?
              :raised?
              :size
              :stacked?])

;;; From https://react.semantic-ui.com/addons/text-area
(add-key-set :text-area
             [:auto-height
              :on-change
              :rows
              :style
              :value])
