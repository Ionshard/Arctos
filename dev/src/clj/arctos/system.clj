(ns arctos.system
  (:require
   [arctos.handler :as handler]
   [com.stuartsierra.component :as component]
   [config.core :refer [env]]
   [figwheel-sidecar.system :as figwheel]
   [ring.middleware.reload :refer [wrap-reload]]
   [system.components.endpoint :as system.endpoint]
   [system.components.handler :as system.handler]
   [system.components.http-kit :as system.http-kit]
   [system.components.middleware :as system.middleware]
   [system.core :refer [defsystem]]))

(def middleware [[wrap-reload]])

(defsystem dev-system
  [:figwheel (figwheel/figwheel-system (figwheel/fetch-config))
   :app-endpoint (system.endpoint/new-endpoint handler/app-routes)
   :middleware (system.middleware/new-middleware {:middleware middleware})
   :handler (-> (system.handler/new-handler)
                (component/using [:app-endpoint :middleware]))
   :web (-> (system.http-kit/new-web-server (or (env :port) 3000))
            (component/using [:handler]))])
