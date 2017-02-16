(ns arctos.system
  (:require
   [arctos.handler :refer [dev-handler]]
   [config.core :refer [env]]
   [figwheel-sidecar.system :as figwheel]
   [system.components.http-kit :as system.http-kit]
   [system.core :refer [defsystem]]))

(defsystem dev-system
  [:web (system.http-kit/new-web-server (or (env :port) 3000) dev-handler)
   :figwheel (figwheel/figwheel-system (figwheel/fetch-config))])
