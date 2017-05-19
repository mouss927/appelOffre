(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('provider', {
            parent: 'entity',
            url: '/provider',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Providers'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/provider/providers.html',
                    controller: 'ProviderController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('provider-detail', {
            parent: 'provider',
            url: '/provider/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Provider'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/provider/provider-detail.html',
                    controller: 'ProviderDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Provider', function($stateParams, Provider) {
                    return Provider.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'provider',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('provider-detail.edit', {
            parent: 'provider-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/provider/provider-dialog.html',
                    controller: 'ProviderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Provider', function(Provider) {
                            return Provider.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('provider.new', {
            parent: 'provider',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/provider/provider-dialog.html',
                    controller: 'ProviderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                lastName: null,
                                firstName: null,
                                mail: null,
                                password: null,
                                registrationDate: null,
                                companyName: null,
                                siret: null,
                                interventionZone: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('provider', null, { reload: 'provider' });
                }, function() {
                    $state.go('provider');
                });
            }]
        })
        .state('provider.edit', {
            parent: 'provider',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/provider/provider-dialog.html',
                    controller: 'ProviderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Provider', function(Provider) {
                            return Provider.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('provider', null, { reload: 'provider' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('provider.delete', {
            parent: 'provider',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/provider/provider-delete-dialog.html',
                    controller: 'ProviderDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Provider', function(Provider) {
                            return Provider.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('provider', null, { reload: 'provider' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
