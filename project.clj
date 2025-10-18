(defproject clojure-crud-rest-api "0.1.0-SNAPSHOT"
  :description "A simple Clojure CRUD REST API"
  :url "http://example.com/clojure-crud-rest-api"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [ring "1.11.0"]
                 [compojure "1.7.1"]
                 [cheshire "5.12.0"]]
  :plugins [[lein-ring "0.12.5"]]
  :ring {:handler clojure-crud-rest-api.core/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.4.0"]]}})