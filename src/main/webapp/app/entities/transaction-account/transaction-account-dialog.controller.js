(function() {
    'use strict';

    angular
        .module('featureflowExampleAngularOneJavaApp')
        .controller('TransactionAccountDialogController', TransactionAccountDialogController);

    TransactionAccountDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TransactionAccount'];

    function TransactionAccountDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TransactionAccount) {
        var vm = this;

        vm.transactionAccount = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.transactionAccount.id !== null) {
                TransactionAccount.update(vm.transactionAccount, onSaveSuccess, onSaveError);
            } else {
                TransactionAccount.save(vm.transactionAccount, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('featureflowExampleAngularOneJavaApp:transactionAccountUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
