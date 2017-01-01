(ns baumpfleger.tree
  (:require [clojure.string :as string]
            [reagent.core :as r]))

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

(defn to-float
  "Return the float value for the string input or throw an error, if it
  is invalid or not in the specified range."
  [s [from to]]
  (when s
    (let [fval (js/parseFloat s)]
      (when (js/isNaN fval)
        (throw (js/Error. "Not a number" {})))
      (when-not (<= from fval to)
        (throw (js/Error. (str "Valid range: " from " to " to) {})))
      fval)))

(defmethod ->node :float [node-cursor]
  (let [nc node-cursor
        inp (r/atom (:value @nc))
        error (r/atom nil)]
    (fn []
      (let [{:keys [id label range] :as node} @nc]
        ^{:key node}
        [:li.input
         [:div.edit-cell
          [:div.field
           [:label label]
           [:input {:class        (when @error "invalid")
                    :type         "text"
                    :value        @inp
                    :on-key-press #(try
                                     (let [fval (to-float @inp range)]
                                       (when (= 13 (.-charCode %))
                                         (r/rswap! nc assoc :value fval)))
                                     (catch js/Error e
                                       (reset! error (.-message e))))
                    :on-change    #(let [v (-> % .-target .-value)]
                                     (reset! inp v)
                                     (when (string/blank? v)
                                       (r/rswap! nc assoc :value nil)))}]]
          [:div.msg
           (when error
             [:span.error @error])]]]))))

(defn to-int
  "Return the int value for the string input or throw an error, if it
  is invalid or not in the specified range."
  [s [from to]]
  (when s
    (let [ival (js/parseInt s)]
      (when (js/isNaN ival)
        (throw (js/Error. "Not a number")))
      (when-not (<= from ival to)
        (throw (js/Error. (str "Valid range: " from " to " to))))
      ival)))

(defmethod ->node :int [node-cursor]
  (let [nc node-cursor
        inp (r/atom (:value @nc))
        error (r/atom nil)]
    (fn []
      (let [{:keys [id label range] :as node} @nc]
        ^{:key node}
        [:li.input
         [:div.edit-cell
          [:div.field
           [:label label]
           [:input {:type         "text"
                    :value        @inp
                    :on-key-press #(try
                                     (when (= 13 (.-charCode %))
                                       (r/rswap! nc assoc :value (to-int @inp range)))
                                     (catch js/Error e
                                       (reset! error (.-message e))))
                    :on-change    #(let [v (-> % .-target .-value)]
                                     (reset! inp v))}]]
          [:div.msg
           (when error
             [:span.error @error])]]]))))

(defn tree-component [root-cursor]
  (let [rc root-cursor]
    (fn []
      [:div.baumpfleger
       [:ul
        [->node rc]]])))
