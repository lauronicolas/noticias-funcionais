(defproject noticias-funcionais "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [clj-http "3.12.3"]
                 [org.clojure/data.xml "0.2.0-alpha6"]
                 [org.clojure/data.zip "1.0.0"]
                 [org.apache.opennlp/opennlp-tools "1.9.4"]
                 [cheshire "5.11.0"]
                 [ring "1.9.6"]
                 [compojure "1.7.0"]
                 [hiccup "1.0.5"]]
  :repl-options {:init-ns noticias-funcionais.core}
  :main noticias-funcionais.core)

