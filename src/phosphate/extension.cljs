(ns phosphate.extension
  (:require ["vscode" :as vscode]
            [clojure.string :as cljstr]
            [promesa.core :as p]))

(def site-name (str "https://pokeapi.co/api/v2/pokemon/" (rand-int 151)))

(def names [{:name "Wolfgang"}
           {:name "Anna"}
           {:name "Peter"}
           {:name "Suzanne"}
           {:name "Beatrice"}])

(defn hello-world []
  (p/let [response (js/fetch site-name)
          json (.json response)
          final (js->clj json :keywordize-keys true)]
    (.. vscode/window (showInformationMessage (str (:name (rand-nth names)) " likes " (cljstr/capitalize (:name final)))))))
;; (defn hello-world [] (.. vscode/window (showInformationMessage (str "Hello there, I am " (:name (rand-nth names))))))

(defn ^:export activate
  [context]
  (let [disposable (.. vscode/commands
                       (registerCommand
                        "phosphate.helloWorld"
                        #'hello-world))]
    (.. context.subscriptions (push disposable))))

(defn ^:export reload
  []
  (.log js/console "Reloading...")
  (js-delete js/require.cache (js/require.resolve "./extension")))

(defn ^:export deactivate [])

;; (def exports #js {:activate activate
;;                   :deactivate deactivate})