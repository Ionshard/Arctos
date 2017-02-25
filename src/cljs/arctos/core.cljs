(ns arctos.core
  (:require [arctos.events]
            [arctos.subs]
            [arctos.routes :as routes]
            [arctos.views :as views]
            [arctos.config :as config]
            [arctos.websockets :as websockets]
            [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [re-frisk.core :refer [enable-re-frisk!]]))

(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (enable-re-frisk!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (routes/app-routes)
  (websockets/event-loop)
  (re-frame/dispatch-sync [:initialize-db])
  (dev-setup)
  (mount-root))
