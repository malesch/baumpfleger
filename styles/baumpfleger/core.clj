 (ns baumpfleger.core
   (:require [garden.def :refer [defstylesheet defstyles]]
             [garden.units :refer [px percent]]
             [garden.selectors :as s]))


(defstyles styles
  [:body
   {:font-family "sans-serif"
    :font-size (px 12)
    :line-height 1.8}]

  [:div.baumpfleger {:width      (px 380)
                     :background "#f6f6f6"
                     :margin "auto"}

   ;; indentation levels
   [:ul {:list-style   :none
         :margin-left  (px 0)
         :padding-left (px 0)}
    [:ul {:margin-left (px 20)}
     [:ul {:margin-right (px 20)}
      [:ul {:margin-right (px 20)}
       [:ul {:margin-right (px 20)}]]]]]

   ;; edit cell
   [:div.edit-cell {:width  (percent 60)
                    :margin 0}
    [:div.field {:width  (percent 80)
                 :float  :left}
     [:input {:width (px 50)
              :float :right
              :margin-right (px 10)}]

     [:input.invalid {:border "1px dotted red"}]
     [:input.commit {:border "1px solid green"}]]

    [:div.msg {:width  (percent 20)
               :float  :right}
     [:span.error {:color :red
                   :white-space :nowrap}]]]
   [(s/div :.edit-cell (s/after)) {:display :block
                                   :content "\"\""
                                   :clear :both}]

   [:.open:before {:content "\"▼\""
                   :margin-right (px 5)}]
   [:.closed:before {:content "\"►\""
                     :margin-right (px 5)}]]


  ;; Styles for tree with data display
  [:div#two-col {:width "100%" :overflow "auto"}]
  [:div#col-1 {:width "40%" :float "left" :border-right "1px dashed lightgrey"}]
  [:div#col-2 {:float "left" :margin-left (px 20) :margin-top (px 10)}]
  [:div.pp
   [:div.pp-control
    [:label {:margin-right (px 10)}]]])

