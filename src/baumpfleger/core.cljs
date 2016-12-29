(ns baumpfleger.core
  (:require [clojure.spec :as s]
            [clojure.pprint :refer [pprint]]
            [reagent.core :as r]
            [baumpfleger.tree :refer [tree-component]]
            [baumpfleger.spec :as bs]
            [baumpfleger.example :as ex]))

(enable-console-print!)

(defonce app-state (r/atom {:text "Hallo Baumpfleger!"}))

(defn register-tree
  "Register the tree description in the application state and return
  a cursor to the data structure."
  [tree-desc]
  (if (s/valid? ::bs/node tree-desc)
    (do
      (swap! app-state assoc :tree tree-desc)
      (r/cursor app-state [:tree]))
    (throw (ex-info "Invalid tree description" {:error (s/explain-data ::bs/node tree-desc)}))))

(defn render-tree [tree-desc]
  (let [tree-cursor (register-tree tree-desc)]
    (tree-component tree-cursor)))

(defn extract-values [tree-desc]
  (let [{:keys [id label value children]} tree-desc]
    (if (seq children)
      {id (into {} (map extract-values children))}
      {id value})))

(defn pp-data [tree-cursor]
  (let [only-values (r/atom true)]
    (fn [state]
      [:div.pp
       [:div.pp-control
        [:div.checkbox
         [:label "Only values"]
         [:input {:type      "checkbox"
                  :checked   @only-values
                  :on-change #(swap! only-values not)}]]]
       [:div.pp-data
        [:pre
         (with-out-str
           (pprint (if @only-values
                     (extract-values @state)
                     @state)))]]])))

(defn render-tree-with-data [tree-desc]
  (let [tree-cursor (register-tree tree-desc)]
    [:div#two-col
     [:div#col-1
      [tree-component tree-cursor]]
     [:div#col-2
      [pp-data tree-cursor]]]))

(r/render-component (render-tree-with-data ex/param-tree-example) (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
