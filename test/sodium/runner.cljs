(ns sodium.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [sodium.utils-test]
              [sodium.core-test]))

(doo-tests 'sodium.utils-test
           'sodium.core-test)
