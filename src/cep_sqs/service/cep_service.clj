(ns cep-sqs.service.cep-service
  (:require [cep-sqs.adapter.viacep :as via]
            [cep-sqs.adapter.sqs :as sqs]
            [cheshire.core :as json]))

(defn cep-valido? [cep]
  (boolean (re-matches #"\d{8}" cep)))

(defn processar-cep [cep queue-url]
  (if-not (cep-valido? cep)
    {:erro "CEP inválido. Use apenas 8 números."}

    (let [dados (via/buscar-cep cep)
          msg {:cep cep :dados dados}]
      (sqs/enviar-sqs queue-url (json/generate-string msg))
      {:status "enviado-para-sqs"
       :cep cep
       :dados dados})))

