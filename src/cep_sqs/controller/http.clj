(ns cep-sqs.controller.http
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :as resp]
            [cep-sqs.service.cep-service :as service]
            [cep-sqs.repository.db :as repo]))

;; ---------------------------
;; PEGA DA VARIÁVEL DE AMBIENTE
;; OU USA LOCALSTACK COMO DEFAULT
;; ---------------------------
(def queue-url
  (or (System/getenv "QUEUE_URL")
      "http://localhost:4566/000000000000/poc-queue"))

;; ---------------------------
;; ROTAS HTTP
;; ---------------------------
(defroutes app
(POST "/cep" req
  (let [cep (get-in req [:body :cep])]
    (resp/response
     (service/processar-cep cep queue-url))))


  (GET "/cep/:cep" [cep]
    (if-let [dados (repo/buscar-cep cep)]
      (resp/response dados)
      (resp/not-found {:erro "CEP não encontrado no banco."})))

  (route/not-found "Rota não encontrada"))
