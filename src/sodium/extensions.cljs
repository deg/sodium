;;; Author: David Goldfarb (deg@degel.com)
;;; Copyright (c) 2017, David Goldfarb

(ns sodium.extensions
  (:require
   [clojure.spec.alpha :as s]
   [sodium.core :as na]
   [sodium.re-utils :refer [<sub >evt]]
   [sodium.utils :as utils]))


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

(defn labelled-field
  "Form field with a label and arbitrary content"
  [& {:keys [label content field-key errors inline?]}]
  (let [error (and field-key (field-key errors))]
    [na/form-input {:inline? inline? :label label}
     content
     (when error
       [na/rail {:position "right" :attached? true :class-name "errmsg"} error])]))
