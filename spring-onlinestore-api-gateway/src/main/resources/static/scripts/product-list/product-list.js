'use strict';

angular.module('productList', ['ui.router'])
    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider
            .state('products', {
                parent: 'app',
                url: '/products',
                template: '<product-list></product-list>'
            })
    }]);