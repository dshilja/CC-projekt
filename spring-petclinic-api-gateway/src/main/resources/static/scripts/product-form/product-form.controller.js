'use strict';

angular.module('productForm')
    .controller('ProductFormController', ["$http", '$state', '$stateParams', function ($http, $state, $stateParams) {
        var self = this;

        var productId = $stateParams.productId || 0;

        if (!productId) {
            self.product = {};
        } else {
            $http.get("api/customer/products/" + productId).then(function (resp) {
                self.product = resp.data;
            });
        }

        self.submitProductForm = function () {
            var id = self.product.id;
            var req;
            if (id) {
                req = $http.put("api/customer/products/" + id, self.product);
            } else {
                req = $http.post("api/customer/products", self.product);
            }

            req.then(function () {
                $state.go('products');
            }, function (response) {
                var error = response.data;
                alert(error.error + "\r\n" + error.errors.map(function (e) {
                        return e.field + ": " + e.defaultMessage;
                    }).join("\r\n"));
            });
        };
    }]);
