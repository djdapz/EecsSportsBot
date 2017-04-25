
var AppController = function($scope, ItemFactory, QuestionService) {
    ItemFactory.query(function(response) {
        $scope.items = response ? response : [];
    });

    // $scope.addItem = function(description) {
    //     new Item({
    //         description: description,
    //         checked: false
    //     }).$save(function(item) {
    //         $scope.items.push(item);
    //     });
    //     $scope.newItem = "";
    // };
    //
    // $scope.updateItem = function(item) {
    //     item.$update();
    // };
    //
    // $scope.deleteItem = function(item) {
    //     item.$remove(function() {
    //         $scope.items.splice($scope.items.indexOf(item), 1);
    //     });
    // };

    $scope.sendClicked = function(){
        if($scope.queryText.length > 0){
            $scope.chat.push({
                role:"USER",
                text: $scope.queryText
            });

            QuestionService.ask($scope.queryText,

                //success
                function(response){
                    $scope.chat.push({
                        role: "CHATBOT",
                        text: response.data.response
                    });
                },

                //failure
                function(error){
                    $scope.chat.push({
                        role: "CHATBOT",
                        text: "ERROR: reaching server"
                    });
                    console.log(error);
                }
            );

            $scope.queryText = undefined;

        }
    };

    $scope.chat = [
        {
            role: "CHATBOT",
            text: "What's up?"
        }
    ];

    $scope.queryText = undefined;
};

angular
    .module('myApp')
    .controller("AppController", AppController );

AppController.$inject = ['$scope', "ItemFactory", "QuestionService"];

