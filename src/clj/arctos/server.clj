(ns arctos.server
  (:require [arctos.system :refer [prod-system]]
            [system.repl :refer [set-init! start]])
  (:gen-class))

(defn -main
  "Start the application"
  [& args]
  (println "Starting System")
  (set-init! #'prod-system)
  (start))
