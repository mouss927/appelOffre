(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('project-pic', {
            parent: 'entity',
            url: '/project-pic',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ProjectPics'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/project-pic/project-pics.html',
                    controller: 'ProjectPicController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('project-pic-detail', {
            parent: 'project-pic',
            url: '/project-pic/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ProjectPic'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/project-pic/project-pic-detail.html',
                    controller: 'ProjectPicDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ProjectPic', function($stateParams, ProjectPic) {
                    return ProjectPic.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'project-pic',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('project-pic-detail.edit', {
            parent: 'project-pic-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/project-pic/project-pic-dialog.html',
                    controller: 'ProjectPicDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProjectPic', function(ProjectPic) {
                            return ProjectPic.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('project-pic.new', {
            parent: 'project-pic',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/project-pic/project-pic-dialog.html',
                    controller: 'ProjectPicDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                link: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('project-pic', null, { reload: 'project-pic' });
                }, function() {
                    $state.go('project-pic');
                });
            }]
        })
        .state('project-pic.edit', {
            parent: 'project-pic',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/project-pic/project-pic-dialog.html',
                    controller: 'ProjectPicDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProjectPic', function(ProjectPic) {
                            return ProjectPic.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('project-pic', null, { reload: 'project-pic' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('project-pic.delete', {
            parent: 'project-pic',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/project-pic/project-pic-delete-dialog.html',
                    controller: 'ProjectPicDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ProjectPic', function(ProjectPic) {
                            return ProjectPic.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('project-pic', null, { reload: 'project-pic' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
