(defproject
  com.degel/sodium "0.1.0-SNAPSHOT"
  :description "A wrapper around soda-ash and semantic-ui-react"
  :url "https://github.com/deg/sodium"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha17"]
                 [org.clojure/clojurescript "1.9.671"]
                 [reagent "0.7.0"]
                 [soda-ash "0.3.0"]]
  :plugins [[lein-cljsbuild "1.1.7"]]
  :cljsbuild {:builds [{:source-paths ["src-cljs"]
                        :compiler {:optimizations :whitespace
                                   :pretty-print true}}]})
