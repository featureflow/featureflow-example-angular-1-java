(function() {
    'use strict';

    angular
        .module('featureflowExampleAngularOneJavaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('transaction-account', {
            parent: 'entity',
            url: '/transaction-account',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'featureflowExampleAngularOneJavaApp.transactionAccount.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/transaction-account/transaction-accounts.html',
                    controller: 'TransactionAccountController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('transactionAccount');
                    $translatePartialLoader.addPart('accountType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('transaction-account-detail', {
            parent: 'transaction-account',
            url: '/transaction-account/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'featureflowExampleAngularOneJavaApp.transactionAccount.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/transaction-account/transaction-account-detail.html',
                    controller: 'TransactionAccountDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('transactionAccount');
                    $translatePartialLoader.addPart('accountType');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TransactionAccount', function($stateParams, TransactionAccount) {
                    return TransactionAccount.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'transaction-account',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('transaction-account-detail.edit', {
            parent: 'transaction-account-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/transaction-account/transaction-account-dialog.html',
                    controller: 'TransactionAccountDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TransactionAccount', function(TransactionAccount) {
                            return TransactionAccount.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('transaction-account.new', {
            parent: 'transaction-account',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/transaction-account/transaction-account-dialog.html',
                    controller: 'TransactionAccountDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                balance: null,
                                type: null,
                                accountType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('transaction-account', null, { reload: 'transaction-account' });
                }, function() {
                    $state.go('transaction-account');
                });
            }]
        })
        .state('transaction-account.edit', {
            parent: 'transaction-account',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/transaction-account/transaction-account-dialog.html',
                    controller: 'TransactionAccountDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TransactionAccount', function(TransactionAccount) {
                            return TransactionAccount.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('transaction-account', null, { reload: 'transaction-account' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('transaction-account.delete', {
            parent: 'transaction-account',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/transaction-account/transaction-account-delete-dialog.html',
                    controller: 'TransactionAccountDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TransactionAccount', function(TransactionAccount) {
                            return TransactionAccount.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('transaction-account', null, { reload: 'transaction-account' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
