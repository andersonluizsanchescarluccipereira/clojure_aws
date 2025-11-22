(ns cep-sqs.service.worker
  (:require
   [cep-sqs.adapter.sqs :as sqs]
   [cep-sqs.repository.db :as repo]
   [cheshire.core :as json]))

(defn loop-worker [queue-url]
  (future
    (while true
      (try
        (when-let [msgs (seq (sqs/receber-mensagem queue-url))]
          (doseq [msg msgs]
            (let [body (.getBody msg)
                  parsed (json/parse-string body true)
                  cep (:cep parsed)
                  dados (:dados parsed)
                  receipt (.getReceiptHandle msg)]

              (println "Worker consumiu mensagem CEP:" cep)

              ;; salvar no banco (atom/pg/etc)
              (repo/salvar-cep! cep dados)

              ;; remover da fila
              (sqs/deletar-mensagem queue-url receipt))))
        (catch Exception e
          (println "Erro no worker:" (.getMessage e))))
      (Thread/sleep 1000))))
