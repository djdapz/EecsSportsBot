var appServices = angular.module('myApp.services');

appServices.service('QuestionService', ["$resource", '$http', function($resource, $http){

    var askQuestion = function(question, success, failure){
        $http({
            method: "GET",
            url: "/question",
            params: {query: question}
        }).then(success, failure);
    };


    return {
        ask: askQuestion
    }

}]);
