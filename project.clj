(defproject clojure-crud-rest-api "1.0.0"
  :description "A simple Clojure CRUD REST API"
  :license {:name "MIT License"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [ring "1.11.0"]
                 [compojure "1.7.1"]
                 [cheshire "5.12.0"]]
  :plugins [[lein-ring "0.12.5"]]
  :ring {:handler clojure-crud-rest-api.core/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.4.0"]]}})
