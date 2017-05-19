(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('project-activity', {
            parent: 'entity',
            url: '/project-activity',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ProjectActivities'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/project-activity/project-activities.html',
                    controller: 'ProjectActivityController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('project-activity-detail', {
            parent: 'project-activity',
            url: '/project-activity/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ProjectActivity'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/project-activity/project-activity-detail.html',
                    controller: 'ProjectActivityDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ProjectActivity', function($stateParams, ProjectActivity) {
                    return ProjectActivity.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'project-activity',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('project-activity-detail.edit', {
            parent: 'project-activity-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/project-activity/project-activity-dialog.html',
                    controller: 'ProjectActivityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProjectActivity', function(ProjectActivity) {
                            return ProjectActivity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('project-activity.new', {
            parent: 'project-activity',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/project-activity/project-activity-dialog.html',
                    controller: 'ProjectActivityDialogController',
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
                    $state.go('project-activity', null, { reload: 'project-activity' });
                }, function() {
                    $state.go('project-activity');
                });
            }]
        })
        .state('project-activity.edit', {
            parent: 'project-activity',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/project-activity/project-activity-dialog.html',
                    controller: 'ProjectActivityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProjectActivity', function(ProjectActivity) {
                            return ProjectActivity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('project-activity', null, { reload: 'project-activity' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('project-activity.delete', {
            parent: 'project-activity',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/project-activity/project-activity-delete-dialog.html',
                    controller: 'ProjectActivityDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ProjectActivity', function(ProjectActivity) {
                            return ProjectActivity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('project-activity', null, { reload: 'project-activity' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
