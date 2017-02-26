(ns arctos.websockets
  (:require [com.stuartsierra.component :as component]
            [system.components.sente :refer [new-channel-socket-client]]
            [taoensso.sente :as sente]
            [re-frame.core :as re-frame])
  (:require-macros
   [cljs.core.async.macros :refer [go go-loop]]))

(def sente-client (component/start (new-channel-socket-client)))
(def chsk (:chsk sente-client))
(def chsk-send! (:chsk-send! sente-client))
(def chsk-state (:chsk-state sente-client))

(defmulti -event-msg-handler :id)

(defn event-msg-handler
  "Wraps `-event-msg-handler` with logging, error catching, etc."
  [{:as ev-msg :keys [id ?data event]}]
  (-event-msg-handler ev-msg))

(defmethod -event-msg-handler :default
  [{:as ev-msg :keys [event]}]
  (println "Unhandled event: %s" event))

(defmethod -event-msg-handler :chsk/state
  [{:as ev-msg :keys [?data]}]
  (let [[old-state-map new-state-map] ?data]
    (if (:first-open? new-state-map)
      (println "Channel socket successfully established!: %s" new-state-map)
      (println "Channel socket state change: %s"              new-state-map))))

(defmethod -event-msg-handler :chsk/recv
  [{:as ev-msg :keys [?data]}]
  (case (first ?data)
    :chat/add-message (re-frame/dispatch [:chat/add-message (second ?data)])
    :else)
  (println "Push event from server: %s" ?data))

(defmethod -event-msg-handler :chsk/handshake
  [{:as ev-msg :keys [?data]}]
  (let [[?uid ?csrf-token ?handshake-data] ?data]
    (println "Handshake: %s" ?data)))

(defn event-loop
  "Handle inbound events."
  []
  (go-loop []
    (let [ev-msg (<! (:ch-chsk sente-client))]
      (event-msg-handler ev-msg)
      (recur))))

