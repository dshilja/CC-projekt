'use strict';

angular.module('visits', ['ui.router'])
    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider
            .state('visits', {
                parent: 'app',
                url: '/products/:productId/pets/:petId/visits',
                template: '<visits></visits>'
            })
    }]);
