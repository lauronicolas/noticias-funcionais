(ns noticias-funcionais.handler
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :as route]
            [cheshire.core :as json]
            [noticias-funcionais.entidades :as entidades]
            [hiccup.page :refer [html5]]))

(defn render-tabela [ranking]
  (let [labels (map first ranking)
        values (map second ranking)]
    (html5
     [:head
      [:meta {:charset "UTF-8"}]
      [:title "Ranking de Entidades"]
      [:script {:src "https://cdn.jsdelivr.net/npm/chart.js"}]
      [:style "body{font-family:sans-serif;margin:40px;}
               table{border-collapse:collapse;margin-top:20px;}
               th,td{border:1px solid #ccc;padding:8px;}"]]
     [:body
      [:h1 "🧠 Entidades mais citadas"]

      [:canvas {:id "rankingChart" :width "800" :height "400"}]
      [:script
       (str
        "const ctx = document.getElementById('rankingChart').getContext('2d');"
        "new Chart(ctx, {"
        "  type: 'bar',"
        "  data: {"
        "    labels: " (json/generate-string (vec labels)) ","
        "    datasets: [{"
        "      label: 'Frequência',"
        "      data: " (json/generate-string (vec values)) ","
        "      backgroundColor: 'rgba(54, 162, 235, 0.6)',"
        "      borderColor: 'rgba(54, 162, 235, 1)',"
        "      borderWidth: 1"
        "    }]"
        "  },"
        "  options: {"
        "    scales: { y: { beginAtZero: true } },"
        "    plugins: { legend: { display: false } }"
        "  }"
        "});")]

      [:h2 "📋 Tabela de Dados"]
      [:table
       [:thead
        [:tr
         [:th "Entidade"]
         [:th "Frequência"]]]
       [:tbody
        (for [[ent freq] ranking]
          [:tr [:td ent] [:td freq]])]]])))

(defroutes app-routes
  (GET "/" [] "<h1>📰 API de Entidades Funcionais</h1>")
  (GET "/entidades" []
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body (json/generate-string (entidades/get-ranking))})
  (GET "/dashboard" []
    {:status 200
     :headers {"Content-Type" "text/html"}
     :body (render-tabela (entidades/get-ranking))})
  (route/not-found "404 - Not Found"))