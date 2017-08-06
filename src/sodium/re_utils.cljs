;;; Author: David Goldfarb (deg@degel.com)
;;; Copyright (c) 2017, David Goldfarb

(ns sodium.re-utils
  (:require
   [clojure.spec.alpha :as s]
   [clojure.string :as str]
   [re-frame.core :as re-frame]))

;; Nice sugar from https://lambdaisland.com/blog/11-02-2017-re-frame-form-1-subscriptions

(def <sub "Shorthand for re-frame subscribe and deref."
  (comp deref re-frame/subscribe))

(def >evt "Shorthand for re-frame dispatch to event."
  re-frame/dispatch)

