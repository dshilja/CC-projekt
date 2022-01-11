'use strict';

angular.module('productDetails')
    .controller('ProductDetailsController', ['$http', '$stateParams', function ($http, $stateParams) {
        var self = this;

        $http.get('api/gateway/products/' + $stateParams.productId).then(function (resp) {
            self.product = resp.data;
        });
    }]);
