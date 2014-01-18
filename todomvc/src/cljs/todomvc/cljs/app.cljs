(ns todomvc.cljs.app
  (:require [cljs.core.async :as a]
            [todomvc.cljs.todomvc-widget :refer [make-todomvc]]
            [todomvc.cljs.todomvc-model :as model]
            [dommy.core :as d])
  (:require-macros [dommy.macros :refer [sel1]]))

(enable-console-print!)

(defn test-todos []
  (->> (for [x (range 5)]
         [x {:caption (str "Test todo " x)}])
       (into {})))

(set! (.-onload js/window)
      (fn []
        (let [!todos (atom (test-todos))
              events-ch (doto (a/chan)
                          (model/watch-events! !todos))]

          (d/replace-contents! (sel1 :#content) (make-todomvc !todos events-ch)))))
