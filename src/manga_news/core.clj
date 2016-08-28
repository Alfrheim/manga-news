(ns manga-news.core
  (:require [manga-news.unixmanga :as unixmanga])
  (:import [org.apache.commons.daemon Daemon DaemonContext])
  (:gen-class
   :implements [org.apache.commons.daemon.Daemon]))
;;http://www.rkn.io/2014/02/06/clojure-cookbook-daemons/

;; A crude approximation of your application's state.
(def state (atom {}))

(defn init [args]
  (swap! state assoc :running true))

(defn start []
  (while (:running @state)
    (unixmanga/init)
    (Thread/sleep 600000)))

(defn stop []
  (swap! state assoc :running false))

;; Daemon implementation

(defn -init [this ^DaemonContext context]
  (init (.getArguments context)))

(defn -start [this]
  (future (start)))

(defn -stop [this]
  (stop))

;; Enable command-line invocation
(defn -main [& args]
  (init args)
  (start))

