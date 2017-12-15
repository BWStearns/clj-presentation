(ns iheart-demo.core
  (:require
   [reagent.core :as reagent]
   ))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Vars

(defonce app-state
  (reagent/atom {}))

(def characters
  [{:name "Yoda" :image "images/yoda-meme.jpg"}
  {:name "Darth Vader" :image "images/vader.jpg"}
  {:name "Jar Jar Binks" :image "images/jarjar.jpeg"}])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Handlers

(defn update-state [input]
  (swap! app-state 
         assoc :text-input (-> input .-target .-value)))

(defn increase-cast-count []
  (println @app-state)
  (swap! app-state
         assoc :cast-count (inc (:cast-count @app-state 0))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Components

(defn counter []
  (let [c (reagent/atom 0)]
    (fn []
      (js/setTimeout #(swap! c inc) 1000)
      [:div
         "Time since page loaded: " @c])))

(defn character [c]
  [:div 
   [:h2 (:name c)]
   [:img {:src (:image c)}]])

(defn character-list []
  (map character 
       (take (:cast-count @app-state 0) characters)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Page

(defn page [ratom]
  [:main
   [:h1 "Welcome to Clojure(script) Development!"]
   [:div "User input is " (:text-input @app-state "")]
   [:input {:type :text
            ; :default-value (:text-input @app-state "")
            :on-change update-state}]
   [counter]
   [:input 
    {:type "button" 
     :value "Show Another Character" 
     :on-click #(swap! app-state increase-cast-count)}]
   (character-list)])
















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
