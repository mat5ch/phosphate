(ns phosphate.extension
  (:require ["vscode" :as vscode]))

(defn hella-world [] (.. vscode/window (showInformationMessage "Hello World")))

(defn ^:export activate
  [context]
  (let [disposable (.. vscode/commands
                       (registerCommand
                        "phosphate.helloWorld"
                        #'hella-world))]
    (.. context.subscriptions (push disposable))))

(defn reload
  []
  (.log js/console "Reloading...")
  (js-delete js/require.cache (js/require.resolve "./extension")))

(defn ^:export deactivate [])

;; (def exports #js {:activate activate
;;                   :deactivate deactivate})