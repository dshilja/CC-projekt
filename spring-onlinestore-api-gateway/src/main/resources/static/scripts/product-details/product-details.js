'use strict';

angular.module('productDetails', ['ui.router'])
    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider
            .state('productDetails', {
                parent: 'app',
                url: '/products/details/:productId',
                template: '<product-details></product-details>'
            })
    }]);