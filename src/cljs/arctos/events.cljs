(ns arctos.events
    (:require [re-frame.core :as re-frame]
              [arctos.db :as db]
              [arctos.websockets :as ws]))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/reg-event-db
 :set-active-panel
 (fn [db [_ active-panel]]
   (assoc db :active-panel active-panel)))

(re-frame/reg-event-db
 :chat/add-message
 (fn [db [_ message]]
   (update-in db [:chat :messages] conj message)))

(re-frame/reg-event-fx
 :chat/send-message
 (fn [{:keys [db]} [_ text]]
   (let [message {:username (-> db :chat :username)
                  :text text
                  :timestamp (.getTime (js/Date.))}]
     (ws/chsk-send! [:chat/message message])
     {})))

(re-frame/reg-event-db
 :chat/set-username
 (fn [db [_ username]]
   (assoc-in db [:chat :username] username)))
