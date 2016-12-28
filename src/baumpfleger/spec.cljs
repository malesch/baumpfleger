(ns baumpfleger.spec
  (:require [clojure.spec :as s]))

(s/def ::common-node (s/keys :req-un [::id] :opt-un [::label]))

(defmulti node #(or (:type %) :branch))

;; Branch node
(s/def ::children (s/coll-of ::node))
(defmethod node :branch [_]
  (s/merge ::common-node
    (s/keys :req-un [::open? ::children])))

;; Value/leaf nodes
(defmethod node :float [_]
  (s/merge ::common-node
    (s/keys :req-un [::range])))

(defmethod node :int [_]
  (s/merge ::common-node
    (s/keys :req-un [::range])))

(defmethod node :string [_]
  (s/merge ::common-node
    (s/keys :opt-un [::max-length])))

(s/def ::node (s/multi-spec node :type))
