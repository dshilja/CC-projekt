'use strict';
/* App Module */
var petClinicApp = angular.module('petClinicApp', [
    'ui.router', 'layoutNav', 'layoutFooter',
    'productList', 'productDetails', 'vetList']);

petClinicApp.config(['$stateProvider', '$urlRouterProvider', '$locationProvider', '$httpProvider', function(
    $stateProvider, $urlRouterProvider, $locationProvider, $httpProvider) {

    // safari turns to be lazy sending the Cache-Control header
    $httpProvider.defaults.headers.common["Cache-Control"] = 'no-cache';

    $locationProvider.hashPrefix('!');

    $urlRouterProvider.otherwise('/products');
    $stateProvider
        .state('app', {
            abstract: true,
            url: '',
            template: '<ui-view></ui-view>'
        })
}]);

['welcome', 'nav', 'footer'].forEach(function(c) {
    var mod = 'layout' + c.toUpperCase().substring(0, 1) + c.substring(1);
    angular.module(mod, []);
    angular.module(mod).component(mod, {
        templateUrl: "scripts/fragments/" + c + ".html"
    });
});