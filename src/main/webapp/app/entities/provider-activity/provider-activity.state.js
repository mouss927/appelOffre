(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('provider-activity', {
            parent: 'entity',
            url: '/provider-activity',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ProviderActivities'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/provider-activity/provider-activities.html',
                    controller: 'ProviderActivityController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('provider-activity-detail', {
            parent: 'provider-activity',
            url: '/provider-activity/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ProviderActivity'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/provider-activity/provider-activity-detail.html',
                    controller: 'ProviderActivityDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ProviderActivity', function($stateParams, ProviderActivity) {
                    return ProviderActivity.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'provider-activity',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('provider-activity-detail.edit', {
            parent: 'provider-activity-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/provider-activity/provider-activity-dialog.html',
                    controller: 'ProviderActivityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProviderActivity', function(ProviderActivity) {
                            return ProviderActivity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('provider-activity.new', {
            parent: 'provider-activity',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/provider-activity/provider-activity-dialog.html',
                    controller: 'ProviderActivityDialogController',
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
                    $state.go('provider-activity', null, { reload: 'provider-activity' });
                }, function() {
                    $state.go('provider-activity');
                });
            }]
        })
        .state('provider-activity.edit', {
            parent: 'provider-activity',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/provider-activity/provider-activity-dialog.html',
                    controller: 'ProviderActivityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProviderActivity', function(ProviderActivity) {
                            return ProviderActivity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('provider-activity', null, { reload: 'provider-activity' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('provider-activity.delete', {
            parent: 'provider-activity',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/provider-activity/provider-activity-delete-dialog.html',
                    controller: 'ProviderActivityDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ProviderActivity', function(ProviderActivity) {
                            return ProviderActivity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('provider-activity', null, { reload: 'provider-activity' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
