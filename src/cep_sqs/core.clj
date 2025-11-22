(ns cep-sqs.core
  (:require [ring.adapter.jetty :as jetty]
            [cep-sqs.controller.http :as http]
            [cep-sqs.service.worker :as worker]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]))

(def app
  (-> http/app
      (wrap-json-body {:keywords? true})
      wrap-json-response))

(defn -main []
  (println "Iniciando worker SQS...")
  (worker/loop-worker http/queue-url)

  (println "Subindo servidor na porta 3000...")
  (jetty/run-jetty app {:port 3000 :join? false}))
