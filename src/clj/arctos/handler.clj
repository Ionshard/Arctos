(ns arctos.handler
  (:require [compojure.core :refer [GET routes]]
            [compojure.route :refer [resources]]
            [ring.util.response :refer [resource-response]]))

(defn app-routes
  [system]
  (routes
   (GET "/" [] (resource-response "index.html" {:root "public"}))
   (resources "/")))

(defn sente-routes [system]
  (fn [{:as ev-msg :keys [event id ?data send-fn ?reply-fn uid ring-req client-id]}]
    (let [session (:session ring-req)
          headers (:headers ring-req)
          [id data :as ev] event]
      (println (:uri ring-req) event uid)
      (case id
        :else))))
