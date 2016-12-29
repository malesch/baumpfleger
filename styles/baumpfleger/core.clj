 (ns baumpfleger.core
   (:require [garden.def :refer [defstylesheet defstyles]]
             [garden.units :refer [px percent]]))


(defstyles styles
  [:body
   {:font-family "sans-serif"
    :font-size (px 12)
    :line-height 1.8}]

  [:div.baumpfleger {:width      (px 300)
                     :background "#f6f6f6"
                     :margin "auto"}

   ;; indention levels
   [:ul {:margin-left (px 0)
         :padding-left (px 0)}
    [:ul {:margin-left (px 20)}
     [:ul {:margin-right (px 20)}
      [:ul {:margin-right (px 20)}
       [:ul {:margin-right (px 20)}]]]]]

   [:ul {:list-style :none}
    [:li.input
     [:input {:width (px 50)
              :margin-left (px 20)}]]]

   [:.open:before {:content "\"▼\""
                   :margin-right (px 5)}]
   [:.closed:before {:content "\"►\""
                     :margin-right (px 5)}]])

