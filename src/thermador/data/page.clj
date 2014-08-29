(ns thermador.data.page
  (:require ;; [thermador.rest :as rest-api]
            [thermador.data.model :as model]
            [compojure.core :refer [defroutes GET]]
            [cheshire.core :refer [generate-string]]))

(def -PageEntityFields
  {:title ""
   :body ""
   :name ""
   :datum-name ""
   :created-on (model/now)})

(def -PageModelFields
  {:datum-name "page"})

(def Page (model/create-model -PageModelFields))

(def lookup-key :datum-name)

(defn create-page
  ([fields]
     (model/create Page -PageEntityFields fields))
  ([name title body & {:keys [datum-name] :or {datum-name name}}]
     (let [fields {:name name
                   :title title
                   :body body
                   :datum-name datum-name}]
       (create-page fields))))

;; (defroutes page-routes
;;   (GET "/:id" [id] (rest-api/make-return (model/retrieve :lookup-id :datum-name Page id)))
;;   (GET "/" [] (rest-api/make-return (model/retrieve :all :datum-name Page))))
