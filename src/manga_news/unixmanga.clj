(ns manga-news.unixmanga
    (:require [net.cgrand.enlive-html :as html]
              [clojure.data :as data]
              [manga-news.telegram :as telegram]
              [manga-news.utils :as u]
              [environ.core :refer [env]]))

(def selector [[:tr #{:.snF}]])
(def selector-href [[:a (html/attr? :href)]])
(def url "http://unixmanga.nl/onlinereading/0-desc-date.php")
(def url-mangas-file (env :url-mangas-file))
(def mangas #{"Kingdom" "Tower of God II" "Cavalier of the Abyss" "Tough" "Berserk" "The God of High School"})
(def last-manga (atom ""))

(def get-name (comp first :content))

(defn get-manga-list
  "Gets a set of desired mangas"
  []
  (try
    (into #{} (u/fetch-url url-mangas-file))
    (catch Exception e
      mangas)))

(defn valid-manga?
  "Checks if the map of manga is one of our selected mangas"
  [manga-map]
  (let [filtered (filter (get-manga-list) (vector (get-name manga-map)))]
    (if (empty? filtered)
      false
      true)))

(defn selected-mangas
  "example:
  (take-news-mangas (elements-unixmanga))"
  [a-map]
  (filter valid-manga? a-map))


(defn take-news-mangas
  [last-manga a-map]
  (take-while #(not= (get-name %) last-manga)  a-map))

(defn resource
  "Parses a url"
  [url]
  (html/html-resource (java.net.URL. url)))

(defn elements-unixmanga
  []
  (-> (resource url)
      (html/select-nodes* selector)
      rest
      (html/select selector-href)))

(defn update-last-manga
  "Updates last manga"
  [a-map]
  (reset! last-manga (get-name (first a-map))))

(defn send-message
  ""
  [a-list]
  (doseq [manga-map a-list]
    (telegram/send-text (get-in manga-map [:attrs :href]))))



(defn init
  ""
  []
  (let [unixmanga (elements-unixmanga)]
    (->> unixmanga
         (take-news-mangas @last-manga)
         (filter valid-manga?)
         send-message)
    (update-last-manga unixmanga)))

