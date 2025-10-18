(ns clojure-crud-rest-api.core-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [clojure-crud-rest-api.core :refer :all]
            [cheshire.core :as json]))

(deftest test-items-crud
  (testing "CRUD operations"
    ;; Create
    (let [resp (app (-> (mock/request :post "/items")
                        (mock/json-body {:name "foo"})))]
      (is (= 201 (:status resp)))
      (let [item (:body resp)
            id (:id item)]
        ;; Read
        (let [get-resp (app (mock/request :get (str "/items/" id)))]
          (is (= 200 (:status get-resp)))
          (is (= "foo" (get-in get-resp [:body :name]))))
        ;; Update
        (let [update-resp (app (-> (mock/request :put (str "/items/" id))
                                   (mock/json-body {:name "bar"})))]
          (is (= 200 (:status update-resp)))
          (is (= "bar" (get-in update-resp [:body :name]))))
        ;; Delete
        (let [del-resp (app (mock/request :delete (str "/items/" id)))]
          (is (= 204 (:status del-resp))))
        ;; Not found after delete
        (let [get-resp (app (mock/request :get (str "/items/" id)))]
          (is (= 404 (:status get-resp))))))))