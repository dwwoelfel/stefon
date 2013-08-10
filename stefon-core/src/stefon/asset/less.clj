(ns stefon.asset.less
  (:require [stefon.pools :as pools]
            stefon.asset.css
            [stefon.settings :as settings]
            [stefon.asset :as asset])
  (:use [stefon.jsengine :only (run-compiler)]))

(def pool (pools/make-pool))

;; TODO: pass options
(defn preprocess-less [file]
  (run-compiler pool
                ["less-wrapper.js" "less-rhino-1.3.3.js"]
                "compileLess"
                file))

(defrecord Less [file]
  stefon.asset.Asset
  (read-asset [this]
    (-> this
        :file
        stefon.asset.css.Css.
        (assoc :content (preprocess-less (:file this))))))

(asset/register "less" map->Less)