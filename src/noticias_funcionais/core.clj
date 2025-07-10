(ns noticias-funcionais.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [noticias-funcionais.handler :as handler]))


(defn start-api []
  (println "🌐 Iniciando API na porta 3000...")
  (run-jetty handler/app-routes {:port 3000}))

(defn -main
  [& args]
  (println "🔍 Lendo notícias...\n")
  (start-api))


    