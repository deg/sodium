;;; Author: David Goldfarb (deg@degel.com)
;;; Copyright (c) 2017, David Goldfarb

(ns sodium.extensions
  (:require
   [clojure.spec.alpha :as s]
   [sodium.core :as na]
   [sodium.re-utils :refer [<sub >evt]]
   [sodium.utils :as utils]))

(defn app-title [title]
  {:pre [(utils/validate (s/or :string string? :event vector?) title)]}
  (na/header {:content (if (vector? title)
                         (str (<sub title))
                         title)
              :dividing? true
              :size :large}))

(defn tabs-row [tabs]
  (into [na/menu {:tabular? true}]
        (map (fn [{:keys [id label]}]
               (let [active? (= id (or (<sub [:page]) :home))
                     handler #(routes/goto-page id (<sub [:server]))]
                 [na/menu-item {:name label
                                :active? active?
                                :color (if active? "blue" "grey")
                                :on-click handler}]))
             tabs)))
