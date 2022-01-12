'use strict';

angular.module('petForm', ['ui.router'])
    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider
            .state('petNew', {
                parent: 'app',
                url: '/products/:productId/new-pet',
                template: '<pet-form></pet-form>'
            })
            .state('petEdit', {
                parent: 'app',
                url: '/products/:productId/pets/:petId',
                template: '<pet-form></pet-form>'
            })
    }]);
