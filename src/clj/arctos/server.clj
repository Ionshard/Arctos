(ns arctos.server
  (:require [arctos.handler :refer [handler]]
            [config.core :refer [env]]
            [org.httpkit.server :refer [run-server]])
  (:gen-class))

 (defn -main [& args]
   (let [port (Integer/parseInt (or (env :port) "3000"))]
     (run-server handler {:port port :join? false})))
