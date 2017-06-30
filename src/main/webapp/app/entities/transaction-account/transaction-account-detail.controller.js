(function() {
    'use strict';

    angular
        .module('featureflowExampleAngularOneJavaApp')
        .controller('TransactionAccountDetailController', TransactionAccountDetailController);

    TransactionAccountDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TransactionAccount'];

    function TransactionAccountDetailController($scope, $rootScope, $stateParams, previousState, entity, TransactionAccount) {
        var vm = this;

        vm.transactionAccount = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('featureflowExampleAngularOneJavaApp:transactionAccountUpdate', function(event, result) {
            vm.transactionAccount = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
