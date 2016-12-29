(ns baumpfleger.tree
  (:require [reagent.core :as r]))

(defn branch? [node]
  (contains? node :children))

(defmulti ->node (fn [tc] (or (:type @tc) :branch)))

(defmethod ->node :branch [node-cursor]
  (let [nc node-cursor]
    (fn []
      (let [{:keys [id label children open?] :as node} @nc]
        ^{:key node}
        [:li.branch
         [:span {:on-click #(swap! nc update :open? not)
                 :class    (if open? "open" "closed")} label]
         [:ul {:style {:display (if open? "display" "none")}}
          (for [idx (range (count children))]
            ^{:key idx}
            [->node (r/cursor nc [:children idx])])]]))))

(defmethod ->node :float [node-cursor]
  (let [nc node-cursor]
    (fn []
      (let [{:keys [id label value] :as node} @nc]
        ^{:key node}
        [:li.input
         [:div.input-float
          [:label label]
          [:input {:type      "text"
                   :default   ""
                   :value     (:value @nc)
                   :on-change #(let [v (-> % .-target .-value)]
                                 (swap! nc assoc :value v))}]]]))))

(defmethod ->node :int [node-cursor]
  (let [nc node-cursor]
    (fn []
      (let [{:keys [id label value] :as node} @nc]
        ^{:key node}
        [:li.input
         [:div.input-int
          [:label label]
          [:input {:type      "text"
                   :default   ""
                   :value     (:value @nc)
                   :on-change #(let [v (-> % .-target .-value)]
                                 (swap! nc assoc :value v))}]]]))))

(defn render-tree [root-cursor]
  (let [rc root-cursor]
    (fn []
      [:div.baumpfleger
       [:ul
        [->node rc]]])))
