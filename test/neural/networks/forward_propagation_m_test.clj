(ns neural.networks.forward-propagation-m-test
  (:require [clojure.test :refer :all]
            [neural.networks.forward-propagation-m :refer :all]
            [logistic-regression :as lr]))

(deftest test-add-bias-to-activation
  (testing "add-bias-to-activation should add a new column of 1 to a given matrix"
    (let [add-bias #'neural.networks.forward-propagation-m/add-bias-to-activation]
      (is (= [[1 2 5] [1 3 6] [1 4 7]] (add-bias [[2 5] [3 6] [4 7]])))
      (is (= [[1 2 5 0] [1 3 6 4] [1 4 7 1]] (add-bias [[2 5 0] [3 6 4] [4 7 1]])))
      (is (= [[1 1] [1 2] [1 3]] (add-bias [[1] [2] [3]])))
      ;an array is seen as a one column matrix
      (is (= [[1 2] [1 3] [1 4]] (add-bias [2 3 4]))))))

;a mock of sigmoid function that does nothing.
(defn- sigmoid-mock [m]
  m)

(deftest test-calc-next-activation
  (testing ""
    (let [calc-next-activation #'neural.networks.forward-propagation-m/calc-next-activation]
      (is (= [[1 38.0 59.0 26.0 37.0] [1 47.0 78.0 41.0 42.0] [1 54.0 91.0 49.0 47.0]]
             (with-redefs-fn
               {#'lr/sigmoid sigmoid-mock}
               #(calc-next-activation [[1 2 6] [1 4 7] [1 5 8]] [[4 2 5] [5 6 7] [6 7 1] [7 0 5]]))))
      (is (= [[1 17.0 11.0 19.0 12.0] [1 12.0 8.0 14.0 9.0]]
             (with-redefs-fn
               {#'lr/sigmoid sigmoid-mock}
               #(calc-next-activation [[1 2 4] [1 1 3]] [[1 2 3] [3 2 1] [1 1 4] [2 1 2]])))))))
