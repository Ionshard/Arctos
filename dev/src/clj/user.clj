(ns user
  (:require [arctos.system :as system]
            [figwheel-sidecar.system :as figwheel]
            [system.repl :refer [system set-init! start stop reset]]))

(set-init! #'system/dev-system)

(defn cljs-repl []
  (figwheel/cljs-repl (:figwheel system)))
