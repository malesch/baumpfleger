(ns example
  (:require [clojure.spec :as s]
            [baumpfleger.spec :as bs]))

(def param-tree
  {:id       :params
   :label    "Parameter"
   :open?    true
   :children [{:id    :max-requests
               :label "Max. Requests"
               :type  :int
               :range [0 500]
               :value 42}
              {:id    :count
               :label "Score Threshold"
               :type  :int
               :range [0 1000]
               :value 100}
              {:id       :class-a
               :label    "Class A"
               :open?    true
               :children [{:id    :param-1
                           :label "Param 1"
                           :type  :float
                           :range [0.0 1.0]
                           :value 0.4}
                          {:id    :param-2
                           :label "Param 2"
                           :type  :float
                           :range [0.0 1.0]}
                          {:id    :param-3
                           :label "Param 3"
                           :type  :float
                           :range [0.0 50.0]
                           :value 34.5}]}
              {:id       :class-b
               :label    "Class B"
               :open?    true
               :children [{:id       :type-foo
                           :label    "Foo"
                           :type     :branch
                           :open?    true
                           :children [{:id    :var-1
                                       :label "Var 1"
                                       :type  :float
                                       :range [0.0 1.0]}
                                      {:id    :var-2
                                       :label "Var 2"
                                       :type  :float
                                       :range [0.0 1.0]}
                                      {:id    :var-3
                                       :label "Var 3"
                                       :type  :float
                                       :range [0.0 1.0]}
                                      {:id    :var-4
                                       :label "Var 4"
                                       :type  :float
                                       :range [0.0 1.0]}]}
                          {:id       :type-baz
                           :label    "Baz"
                           :open?    true
                           :children [{:id    :var-5
                                       :label "Var 5"
                                       :type  :float
                                       :range [0.0 1.0]}
                                      {:id    :var-6
                                       :label "Var 6"
                                       :type  :float
                                       :range [0.0 1.0]}
                                      {:id    :var-7
                                       :label "Var 7"
                                       :type  :float
                                       :range [0.0 1.0]}]}]}
              {:id       :class-c
               :label    "Class C"
               :open?    true
               :children [{:id    :default
                           :label "Default"
                           :type  :float
                           :range [0.0 1.0]}
                          {:id    :weak
                           :label "Weak"
                           :type  :float
                           :range [0.0 1.0]}
                          {:id    :strong
                           :label "Strong"
                           :type  :float
                           :range [0.0 1.0]}]}
              {:id       :class-d
               :label    "Class D"
               :open?    true
               :children [{:id    :f-val
                           :type  :float
                           :range [0.0 1.0]}
                          {:id    :i-val
                           :type  :int
                           :range [0 100]}]}]})