(ns arctos.system
  (:require
   [arctos.handler :refer [handler]]
   [config.core :refer [env]]
   [system.components.http-kit :as system.http-kit]
   [system.core :refer [defsystem]]))

(defsystem prod-system
  [:web (system.http-kit/new-web-server (or (env :port) 3000) handler)])
