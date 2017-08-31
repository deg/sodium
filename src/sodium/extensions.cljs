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

(defn labelled-field [& {:keys [label content field-key errors inline?]}]
  (let [error (and field-key (field-key errors))]
    [na/form-input {:inline? inline? :label label}
     content
     (when error
       [na/rail {:position "right" :attached? true :class-name "errmsg"} error])]))
