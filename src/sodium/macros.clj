;;; Author: David Goldfarb (deg@degel.com)
;;; Copyright (c) 2017, David Goldfarb

(ns sodium.macros
  (:require
   [clojure.spec.alpha :as s]
   [sodium.utils :as utils]
   [sodium.keys :as keys]))


;;; Force early load of key sets. We need them available before the
;;; defcomponent macro is expanded.
;;; [TODO] What's the best way to clean up this hack?
(keys/key-set :basic)


(defn merge-keys
  "Combined keys with all the elements of the sets of keys in key-sets."
  [keys key-sets]
  (->> (reduce (fn [so-far key]
                 (into so-far (map #(-> % name symbol) (keys/key-set key))))
               (set keys) key-sets)
       (into [])
       (sort-by name)
       (into [])))


;;; [TODO] It would be nice to make the properties map be optional like it is
;;;        when using vanilla soda-ash. But, I don't see how to do that, and
;;;        still retain a nice arglist display.
(defmacro defcomponent
  [name [params-var & other-params]
   {:keys [pre post :sodium.core/keys :sodium.core/key-sets]}
   & component-body]
  {:pre [(utils/validate symbol? name)
         (utils/validate symbol? params-var)]}
  (let [all-keys (merge-keys keys key-sets)]
    `(defn ~name [{:keys ~all-keys :as ~params-var} ~@other-params]
       {:pre ~(utils/vconcat pre
                             `[(utils/all-keys-valid? (keys ~params-var)
                                                      ~(set (map keyword all-keys)))])
        :post ~post}
       ~@component-body)))

(defmacro def-simple-component [name parent key-sets]
  `(defcomponent ~name [params# & body#]
     {:sodium.core/key-sets ~key-sets}
     (utils/vconcat [~parent (utils/camelize-map-keys params#)]
                    body#)))
