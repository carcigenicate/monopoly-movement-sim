(defproject monopoly-movement-sim "1"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [helpers "1"]]

  :main ^:skip-aot monopoly-movement-sim.main

  :target-path "target/%s"

  :profiles {:uberjar {:aot :all}})
