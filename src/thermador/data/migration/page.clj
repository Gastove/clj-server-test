(ns thermador.data.migration.page
  (:require [thermador.data.page :as page]
            [thermador.data.model :as model]
            [clojure.string :as string]))

(defn make-title-from-md
  [md]
  (string/trim (second (re-find #"([\w !?]+)" md))))

(defn create-page-from-markdown-text
  [name markdown]
  (let [title (make-title-from-md markdown)]
    (page/create-page name title markdown)))

(defn update-page
  [lookup-key model id markdown]
  (let [pobj (model/retrieve :lookup-id lookup-key sync-model id)
        f #(assoc % :body markdown)]
    (model/transform pobj f)))

(def page-migration
  {page/Page {:path "/the_range/markdown"
              :lookup-key page/lookup-key
              :create-fn create-page-from-markdown-text
              :update-fn update-page}})
