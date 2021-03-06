(ns arctos.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 :active-panel
 (fn [db _]
   (:active-panel db)))

(re-frame/reg-sub
 :chat/messages
 (fn [db _]
   (get-in db [:chat :messages])))

(re-frame/reg-sub
 :chat/username
 (fn [db _]
   (get-in db [:chat :username])))
