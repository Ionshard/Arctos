(ns arctos.systems
  (:require
   [arctos.handler :refer [handler dev-handler]]
   [config.core :refer [env]]
   [system.core :refer [defsystem]]
   (system.components
    [http-kit :refer [new-web-server]])))

(defsystem dev-system
  [:web (new-web-server (or (env :port) 3000) handler)])

