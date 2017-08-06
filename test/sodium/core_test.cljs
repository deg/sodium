(ns sodium.core-test
  (:require [cljs.test :refer-macros [deftest testing is are]]
            [sodium.core :as na]))

(deftest subst-key
  (is (= (na/subst-key {:a 1 :b 2 :c 3} :x :x identity) {:a 1 :b 2 :c 3}))
  (is (= (na/subst-key {:a 1 :b 2 :c 3} :b :d identity) {:a 1 :d 2 :c 3}))
  (is (= (na/subst-key {:a 1 :b 2 :c 3} :b :e #(+ % %)) {:a 1 :e 4 :c 3})))
