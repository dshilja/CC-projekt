'use strict';

angular.module('productForm', ['ui.router'])
    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider
            .state('productNew', {
                parent: 'app',
                url: '/products/new',
                template: '<product-form></product-form>'
            })
            .state('productEdit', {
                parent: 'app',
                url: '/products/:productId/edit',
                template: '<product-form></product-form>'
            })
    }]);
