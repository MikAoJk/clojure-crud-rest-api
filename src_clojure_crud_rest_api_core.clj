(ns clojure-crud-rest-api.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [cheshire.core :as json]))

(defonce db (atom {}))

(defn list-items []
  (vals @db))

(defn get-item [id]
  (get @db id))

(defn create-item [item]
  (let [id (str (java.util.UUID/randomUUID))
        item-with-id (assoc item :id id)]
    (swap! db assoc id item-with-id)
    item-with-id))

(defn update-item [id item]
  (if (contains? @db id)
    (let [updated (assoc item :id id)]
      (swap! db assoc id updated)
      updated)
    nil))

(defn delete-item [id]
  (let [exists (contains? @db id)]
    (swap! db dissoc id)
    exists))

(defroutes app-routes
  (GET "/items" [] {:status 200 :body (list-items)})
  (GET "/items/:id" [id]
       (let [item (get-item id)]
         (if item
           {:status 200 :body item}
           {:status 404 :body {:error "Not found"}})))
  (POST "/items" req
        (let [item (get-in req [:body])]
          {:status 201 :body (create-item item)}))
  (PUT "/items/:id" [id :as req]
       (let [item (get-in req [:body])
             updated (update-item id item)]
         (if updated
           {:status 200 :body updated}
           {:status 404 :body {:error "Not found"}})))
  (DELETE "/items/:id" [id]
          (if (delete-item id)
            {:status 204}
            {:status 404 :body {:error "Not found"}}))
  (route/not-found {:error "Not found"}))

(def app
  (-> app-routes
      (wrap-json-body {:keywords? true})
      wrap-json-response))