(defproject cep-sqs "0.1.0-SNAPSHOT"
  :description "Exemplo MVC com SQS + ViaCEP + Worker"
  :dependencies [[org.clojure/clojure "1.11.1"]

                 ;; Web
                 [ring/ring-core "1.10.0"]
                 [ring/ring-jetty-adapter "1.10.0"]
                 [ring/ring-json "0.5.1"]  
                 [compojure "1.7.1"]

                 ;; HTTP / JSON
                 [clj-http "3.12.3"]
                 [cheshire "5.11.0"]

                 ;; AWS SQS (Java SDK v1)
                 [com.amazonaws/aws-java-sdk-sqs "1.12.772"]]

  :main cep-sqs.core
  :uberjar-name "cep-sqs.jar")
