(ns arctos.handler
  (:require [compojure.core :refer [GET routes]]
            [compojure.route :refer [resources]]
            [ring.util.response :refer [resource-response]]))

(defn app-routes
  [system]
  (routes
   (GET "/" [] (resource-response "index.html" {:root "public"}))
   (resources "/")))
