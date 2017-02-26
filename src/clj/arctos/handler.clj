(ns arctos.handler
  (:require [compojure.core :refer [GET routes]]
            [compojure.route :refer [resources]]
            [ring.util.response :refer [resource-response]]
            [system.repl :refer [system]]))

(defn app-routes
  [system]
  (routes
   (GET "/" [] (resource-response "index.html" {:root "public"}))
   (resources "/")))

(defn broadcast
  [message]
  (let [{:keys [chsk-send! connected-uids]} (:sente system)]
    (doseq [uid (:any @connected-uids)]
     (chsk-send! uid [:chat/add-message message]))))

(defn sente-routes [system]
  (fn [{:as ev-msg :keys [event id ?data send-fn ?reply-fn uid ring-req client-id]}]
    (let [session (:session ring-req)
          headers (:headers ring-req)
          [id data] event]
      (when-not (= :chsk/ws-ping id) (println (:uri ring-req) id event uid))
      (case id
        :chat/message (broadcast (second event))
        :else))))
