'use strict';

angular.module('productList')
    .controller('ProductListController', ['$http', function ($http) {
        var self = this;

        $http.get('api/customer/products').then(function (resp) {
            self.products = resp.data;
        });
    }]);
