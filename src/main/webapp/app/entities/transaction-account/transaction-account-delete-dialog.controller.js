(function() {
    'use strict';

    angular
        .module('featureflowExampleAngularOneJavaApp')
        .controller('TransactionAccountDeleteController',TransactionAccountDeleteController);

    TransactionAccountDeleteController.$inject = ['$uibModalInstance', 'entity', 'TransactionAccount'];

    function TransactionAccountDeleteController($uibModalInstance, entity, TransactionAccount) {
        var vm = this;

        vm.transactionAccount = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TransactionAccount.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
