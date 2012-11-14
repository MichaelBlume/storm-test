(ns storm.test.util
  (:use [clojure.contrib.logging])
  (:import [org.apache.log4j Logger]))


(defn set-log-level [level]
  (.. (Logger/getLogger "org.apache.zookeeper.server.NIOServerCnxn")
    (setLevel level))
  (.. (impl-get-log "") getLogger getParent
    (setLevel level)))

(defmacro with-quiet-logs [level & body]
  `(let [ old-level# (.. (impl-get-log "") getLogger getParent getLevel) ]
     (set-log-level @level)
     (let [ ret# (do ~@body) ]
       (set-log-level old-level#)
       ret#)))

