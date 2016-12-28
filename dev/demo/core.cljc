(ns demo.core
  (:require
    [demo.example :refer [param-tree]]
    [baumpfleger.core :refer [render]]))


;; render demo tree
(defn render-demo []
  (render param-tree))

