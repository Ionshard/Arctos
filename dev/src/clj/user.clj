(ns user
  (:require [arctos.system :refer [dev-system]]
            [figwheel-sidecar.system :as figwheel]
            [system.repl :refer [system set-init! start stop reset]]))

(set-init! #'dev-system)

(defn cljs-repl []
  (figwheel/cljs-repl (:figwheel system)))
