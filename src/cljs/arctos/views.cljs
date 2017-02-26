(ns arctos.views
  (:require [re-frame.core :as re-frame]
            [reagent.core :as r]))


;; home

(defn home-panel []
  (let [name (re-frame/subscribe [:name])]
    (fn []
      [:div (str "Hello from " @name ". This is the Home Page.")
       [:ul
        [:li [:a {:href "#/about"} "go to About Page"]]
        [:li [:a {:href "#/chat"} "go to Chat Page"]]]])))


;; about

(defn about-panel []
  (fn []
    [:div "This is the About Page."
     [:div [:a {:href "#/"} "go to Home Page"]]]))

(defn chat-panel []
  (let [messages (re-frame/subscribe [:chat/messages])
        username (re-frame/subscribe [:chat/username])
        editing? (r/atom false)
        u (r/atom "")
        m (r/atom "")]
    (fn []
      [:div
       [:a {:href "#/"} "Home"]
       [:h1 "Chat"]
       (if (or (empty? @username) @editing?)
         [:div
          [:h3 "Username"]
          [:input {:type "text"
                   :value @u
                   :on-change #(reset! u (-> % .-target .-value))}]
          [:button {:on-click #(do (re-frame/dispatch [:chat/set-username @u])
                                   (reset! u)
                                   (reset! editing? false))} "Set"]]

         [:div
          [:h3 "Messages"]
          [:div.messages
           (for [{:keys [username text timestamp]} @messages]
             [:div.message {:key timestamp} [:strong (str username ": ")] text])]
          [:a {:on-click #(reset! editing? true)} (str @username ": ")]
          [:input {:type "text"
                   :value @m
                   :on-change #(reset! m (-> % .-target .-value))}]
          [:button {:on-click #(do (re-frame/dispatch [:chat/send-message @m])
                                   (reset! m))} "Send"]])])))


;; main

(defn- panels [panel-name]
  (case panel-name
    :home-panel [home-panel]
    :about-panel [about-panel]
    :chat-panel [chat-panel]
    [:div
     (str "Unknown panel " panel-name)]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [:active-panel])]
    (fn []
      [show-panel @active-panel])))
