(ns cep-sqs.adapter.viacep
  (:require [clj-http.client :as http]
            [cheshire.core :as json]))

(defn buscar-cep [cep]
  (let [url (str "http://viacep.com.br/ws/" cep "/json/")
        resp (http/get url {:as :json})]
    (:body resp)))