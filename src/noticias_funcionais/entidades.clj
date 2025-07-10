(ns noticias-funcionais.entidades
  (:require [clj-http.client :as http]
            [clojure.data.xml :as xml]
            [clojure.data.zip.xml :as zx]
            [clojure.zip :as zip])
  (:import (java.io ByteArrayInputStream)))


(defn parse-xml [xml-str]
  (-> xml-str
      .getBytes
      ByteArrayInputStream.
      xml/parse
      zip/xml-zip))

(defn get-titles-from-feed [url]
  (try
    (let [response (:body (http/get url))
          feed (parse-xml response)]
      (->> (zx/xml-> feed :channel :item :title zx/text)
           (take 10)))
    (catch Exception e
      (println "⚠️  Erro ao acessar feed:" url)
      (println "   →" (.getMessage e))
      [])))


(require '[cheshire.core :as json])

(defn extract-entities-spacy [text]
  (let [response (http/post "http://localhost:5000/extract_entities"
                            {:body (json/generate-string {:text text})
                             :headers {"Content-type" "application/json"}
                             :as :json})]
    (:body response)))

(def rss-feeds
  ["https://g1.globo.com/rss/g1/"
   "https://rss.uol.com.br/feed/noticias.xml"
   "https://feeds.folha.uol.com.br/emcimadahora/rss091.xml"
   "https://www.cnnbrasil.com.br/feed/"
   "https://feeds.feedburner.com/estadao-geral"])

(defn get-all-titles [feeds]
  (->> feeds
       (mapcat get-titles-from-feed)
       distinct))

(defn get-ranking []
  (let [titles (get-all-titles rss-feeds)
        todas-entidades
        (mapcat #(map :text (extract-entities-spacy %)) titles)]
    (->> todas-entidades
         frequencies
         (sort-by val >)
         (take 20))))