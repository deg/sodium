(ns sodium.utils-test
  (:require [cljs.test :refer-macros [deftest testing is are]]
            [sodium.utils :as utils]))

(deftest camelize-str
  (are [pre-string post-string] (= (utils/camelize-str pre-string) post-string)
    "string"       "string"
    "the_string"   "the_string"
    "the-string"   "theString"
    "the-string-"  "theString"
    "-the-string"  "TheString"
    "even?"        "even"
    "long-string?" "longString"))

(deftest camelize-key
  (are [pre-key post-key] (= (utils/camelize-key pre-key) post-key)
    :a       :a
    :a-b     :aB
    :a-b?    :aB
    :foo/a   :foo/a
    :foo/a-b :foo/aB
    ::x?     ::x)
  (run! #(is (= % (utils/camelize-key %)))
        utils/preserved-keys))

(deftest camelize-map-keys
  (is (= {:word :word                  ;; Simple
          :twoWords :two-words         ;; Camel
          :predicate :predicate?       ;; Predicate
          :evenNumber :even-number?    ;; Predicate/Camel
          :data-tooltip :data-tooltip  ;; Preserved
          }
         (utils/camelize-map-keys
          {:word :word
           :two-words :two-words
           :predicate? :predicate?
           :even-number? :even-number?
           :data-tooltip :data-tooltip}))))
