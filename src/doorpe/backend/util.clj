(ns doorpe.backend.util
  (:require [monger.collection :as mc]
            [monger.util :refer [get-id]]
            [clojure.edn :as edn])
  (:import [org.bson.types ObjectId]))

(defn exists-and-not-empty?
  "Return true if collection exists and is not empty"
  [db coll]
  (and
   (mc/exists? db coll)
   (not (mc/empty? db coll))))

(defn valid-hexa-string?
  [s]
  (org.bson.types.ObjectId/isValid s))

(defn bson-object-id
  ([]
   (ObjectId.))

  ([hexa-string]
   (ObjectId. hexa-string)))

(defn bson-object-id->str
  ([]
   (-> (ObjectId.)
       .toString))

  ([object-id]
   (.toString object-id)))

(defn coll-exists?
  [db coll]
  (mc/exists? db coll))

(defn doc-object-id->str
  "Accepts a map"
  [doc]
  (if (contains? doc :_id)
    (let [id (get-id doc)
          hexa-string (str id)]
      (and (valid-hexa-string? hexa-string) (assoc doc :_id hexa-string)))
    nil))

(defn docs-object-id->str
  "Accepts a vector of maps"
  [docs]
  (pmap #(doc-object-id->str %)
        docs))

(defn valid-coll-name?
  "mongodb valid coll name, check for name starts with, whether contain symbol.... - pending"
  [coll]
  (and (instance? String coll)))

(defn str->int
  [s]
  (edn/read-string s))