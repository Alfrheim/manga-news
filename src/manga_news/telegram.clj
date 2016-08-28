(ns manga-news.telegram
  (:require [morse.api :as api]
            [environ.core :refer [env]]))

(def token-bot (env :token-bot)) ;;TOKEN_BOT=XxXxXxXxX
(def chat-id (env :chat-id)) ;;CHAT_ID=888888

;;(api/send-text token-bot chat-id "Hello, fellows")

;;(api/send-text token-bot chat-id
;;               {:parse_mode "Markdown"}
;;               "**Hello**, fellows")

(defn send-text
  "sends a text to the chat"
  [text]
  (api/send-text token-bot chat-id text))
