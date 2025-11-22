(ns cep-sqs.repository.db)

(def db (atom {}))

(defn salvar-cep! [cep dados]
  (swap! db assoc cep dados))

(defn buscar-cep [cep]
  (get @db cep))

(defn deletar-cep! [cep]
  (swap! db dissoc cep))

(defn listar-ceps []
  @db)