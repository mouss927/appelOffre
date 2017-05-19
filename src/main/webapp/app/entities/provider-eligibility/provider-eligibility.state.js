(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('provider-eligibility', {
            parent: 'entity',
            url: '/provider-eligibility',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ProviderEligibilities'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/provider-eligibility/provider-eligibilities.html',
                    controller: 'ProviderEligibilityController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('provider-eligibility-detail', {
            parent: 'provider-eligibility',
            url: '/provider-eligibility/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ProviderEligibility'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/provider-eligibility/provider-eligibility-detail.html',
                    controller: 'ProviderEligibilityDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ProviderEligibility', function($stateParams, ProviderEligibility) {
                    return ProviderEligibility.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'provider-eligibility',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('provider-eligibility-detail.edit', {
            parent: 'provider-eligibility-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/provider-eligibility/provider-eligibility-dialog.html',
                    controller: 'ProviderEligibilityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProviderEligibility', function(ProviderEligibility) {
                            return ProviderEligibility.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('provider-eligibility.new', {
            parent: 'provider-eligibility',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/provider-eligibility/provider-eligibility-dialog.html',
                    controller: 'ProviderEligibilityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('provider-eligibility', null, { reload: 'provider-eligibility' });
                }, function() {
                    $state.go('provider-eligibility');
                });
            }]
        })
        .state('provider-eligibility.edit', {
            parent: 'provider-eligibility',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/provider-eligibility/provider-eligibility-dialog.html',
                    controller: 'ProviderEligibilityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProviderEligibility', function(ProviderEligibility) {
                            return ProviderEligibility.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('provider-eligibility', null, { reload: 'provider-eligibility' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('provider-eligibility.delete', {
            parent: 'provider-eligibility',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/provider-eligibility/provider-eligibility-delete-dialog.html',
                    controller: 'ProviderEligibilityDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ProviderEligibility', function(ProviderEligibility) {
                            return ProviderEligibility.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('provider-eligibility', null, { reload: 'provider-eligibility' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
