(function() {
    'use strict';

    angular
        .module('featureflowExampleAngularOneJavaApp')
        .controller('TransactionDetailController', TransactionDetailController);

    TransactionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Transaction'];

    function TransactionDetailController($scope, $rootScope, $stateParams, previousState, entity, Transaction) {
        var vm = this;

        vm.transaction = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('featureflowExampleAngularOneJavaApp:transactionUpdate', function(event, result) {
            vm.transaction = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
