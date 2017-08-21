;;; Author: David Goldfarb (deg@degel.com)
;;; Copyright (c) 2017, David Goldfarb

(ns sodium.chrome-utils)

(defn console-dir
  "Inspect an object in the Chrome console, preceded by a text message.
   This is useful in general, but especially nice for JavaScript objects
   that print opaquely in the REPL."
  [msg obj]
  (when msg
    (js/console.log msg))
  (when obj
    (js/console.dir obj)))
