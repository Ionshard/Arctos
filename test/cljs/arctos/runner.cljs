(ns arctos.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [arctos.core-test]))

(doo-tests 'arctos.core-test)
