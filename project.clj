(defproject rock-paper-stuff "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [net.mikera/core.matrix "0.61.0"]]
  :plugins [[lein-gorilla "0.4.0"]]
  :main ^:skip-aot rock-paper-stuff.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
