(ns iheart-demo.core
  (:require
   [reagent.core :as reagent]
   ))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Vars

(defonce app-state
  (reagent/atom {}))



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Handlers

(defn update-state [input]
  (swap! app-state 
         assoc :text-input (-> input .-target .-value)))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Components

(defn counter []
  (let [c (reagent/atom 0)]
    (fn []
      (js/setTimeout #(swap! c inc) 1000)
      [:div
         "Time since page loaded: " @c])))



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Page

(defn page [ratom]
  [:main
   [:h1 "Welcome to Clojure(script) Development!!!"]
   [:div "User input is " (:text-input @app-state "")]
   [:input {:type :text
            ; :default-value (:text-input @app-state "")
            :on-change update-state}]
   [counter]])



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Initialize App

(defn dev-setup []
  (when ^boolean js/goog.DEBUG
    (enable-console-print!)
    (println "dev mode")
    ))

(defn reload []
  (reagent/render [page app-state]
                  (.getElementById js/document "app")))

(defn ^:export main []
  (dev-setup)
  (reload))
