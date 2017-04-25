var appServices = angular.module('myApp.services');

appServices.factory('ItemFactory', ["$resource", function($resource){
    return $resource('/items/:id', {id: '@id'}, {
        update: {
            method: "PUT"
        },
        remove: {
            method: "DELETE"
        }
    });

}]);
