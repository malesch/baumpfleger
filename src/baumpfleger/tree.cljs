(ns baumpfleger.tree
  (:require [reagent.core :as r]))

(defn branch? [node]
  (contains? node :children))

(defn- index-tree*
  [tree]
  (let [{:keys [id children]} tree]
    (if (branch? tree)
      [id (assoc tree :children (into {} (map index-tree* children)))]
      [id tree])))

(defn index-tree
  "Transform the tree description into an indexed form where every node
  can be accessed by an ID lookup. This structure simplifies later processing."
  [tree]
  (into {} [(index-tree* tree)]))


(defmulti ->node (fn [tc] (or (:type @tc) :branch)))

(defmethod ->node :branch [tc]
  (fn [tc]
    (let [{:keys [id label children open?] :as inode} @tc]
      ^{:key inode}
      [:ul
       [:li [:span {:style    {:background-color "lightyellow"}
                    :on-click #(swap! tc update :open? not)} label]]
       [:ul {:style {:display (if (:open? @tc) "display" "none")}}
        (doall
          (for [child children]
            ^{:key child}
            [->node (r/cursor tc [:children (first child)])]))]])))

(defmethod ->node :float [tc]
  (fn [tc]
    (let [{:keys [id label] :as inode} @tc]
      ^{:key inode}
      [:li [:span (str label " (float) ")]])))

(defmethod ->node :int [tc]
  (fn [tc]
    (let [{:keys [id label] :as inode} @tc]
      ^{:key inode}
      [:li (str label " (int)")])))

(defn render-tree [itree]
  [:div
   (doall
     (for [inode @itree]
       (let [[idx _] inode]
         ^{:key inode} [->node (r/cursor itree [idx])])))])
