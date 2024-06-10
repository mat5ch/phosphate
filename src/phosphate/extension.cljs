(ns phosphate.extension
  (:require ["vscode" :as vscode]))

(def names [{:name "Wolfgang"}
           {:name "Anna"}
           {:name "Peter"}
           {:name "Suzanne"}
           {:name "Beatrice"}])

(defn hello-world [] (.. vscode/window (showInformationMessage (str "Hello there, I am " (:name (rand-nth names))))))

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