(ns baumpfleger.core
  (:require [clojure.spec :as s]
            [reagent.core :as r]
            [baumpfleger.tree :refer [render-tree index-tree]]
            [baumpfleger.spec :as bs]))

(enable-console-print!)

(defonce app-state (r/atom {:text "Hallo Baumpfleger!"}))

(defn build-tree [tree-desc]
  (if (s/valid? ::bs/node tree-desc)
    (do
      (swap! app-state assoc :tree (index-tree tree-desc))
      (render-tree (r/cursor app-state [:tree])))
    (throw (ex-info "Invalid tree description" {:error (s/explain-data ::bs/node tree-desc)}))))

(defn render [tree-desc]
  (r/render-component (build-tree tree-desc) (. js/document (getElementById "app"))))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
