(ns cep-sqs.adapter.sqs
  (:import
   (com.amazonaws.services.sqs AmazonSQSClientBuilder)
   (com.amazonaws.client.builder AwsClientBuilder$EndpointConfiguration)
   (com.amazonaws.services.sqs.model SendMessageRequest
                                     ReceiveMessageRequest
                                     DeleteMessageRequest)))

;; se estiver rodando no Docker, use LOCALSTACK_HOST=localstack
(def localstack-host
  (or (System/getenv "LOCALSTACK_HOST") "localhost"))

(def endpoint (str "http://" localstack-host ":4566"))

(def region "us-east-1")

(defn sqs-client []
  (-> (AmazonSQSClientBuilder/standard)
      (.withEndpointConfiguration
       (AwsClientBuilder$EndpointConfiguration.
        endpoint region))
      (.build)))

(def client (delay (sqs-client)))

(defn enviar-sqs [queue-url msg]
  (.sendMessage @client (SendMessageRequest. queue-url msg)))

(defn receber-mensagem [queue-url]
  (let [req (doto (ReceiveMessageRequest. queue-url)
              (.setMaxNumberOfMessages (int 1))
              (.setWaitTimeSeconds (int 5)))
        result (.receiveMessage @client req)]
    (.getMessages result)))

(defn deletar-mensagem [queue-url receipt]
  (.deleteMessage @client (DeleteMessageRequest. queue-url receipt)))
