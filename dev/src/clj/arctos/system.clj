(ns arctos.system
  (:require
   [arctos.handler :as handler]
   [com.stuartsierra.component :as component]
   [config.core :refer [env]]
   [figwheel-sidecar.system :as figwheel]
   [ring.middleware.reload :as middleware.reload]
   [ring.middleware.keyword-params :as middleware.keyword-params]
   [ring.middleware.params :as middleware.params]
   [system.components.endpoint :as system.endpoint]
   [system.components.handler :as system.handler]
   [system.components.http-kit :as system.http-kit]
   [system.components.middleware :as system.middleware]
   [system.components.sente :as system.sente]
   [system.core :refer [defsystem]]
   [taoensso.sente.server-adapters.http-kit :refer [sente-web-server-adapter]]))

(def middleware [[middleware.reload/wrap-reload]
                 [middleware.keyword-params/wrap-keyword-params]
                 [middleware.params/wrap-params]])

(defsystem dev-system
  [:figwheel (figwheel/figwheel-system (figwheel/fetch-config))
   :sente (system.sente/new-channel-socket-server handler/sente-routes sente-web-server-adapter {:wrap-component? true})
   :app-endpoint (system.endpoint/new-endpoint handler/app-routes)
   :sente-endpoint (-> (system.endpoint/new-endpoint system.sente/sente-routes)
                       (component/using [:sente]))
   :middleware (system.middleware/new-middleware {:middleware middleware})
   :handler (-> (system.handler/new-handler)
                (component/using [:app-endpoint :sente-endpoint :middleware]))
   :web (-> (system.http-kit/new-web-server (or (env :port) 3000))
            (component/using [:handler]))])
