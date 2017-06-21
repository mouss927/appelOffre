(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .config(stateConfig);
    
    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('formulaire', {
            parent: 'app',
            url: '/formulaire/new',
            data: {
                authorities: ['ROLE_USER']
            },
            
            views: {
                'content@': {
                	templateUrl: 'app/CreateProject/formulaire.html',
                    controller: 'FormulaireController',
                    controllerAs: 'vm',
                }
            },
            resolve: {
             
            }
            
            /*onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/CreateProject/formulaire.html',
                    controller: 'FormulaireController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('formulaire', null, { reload: 'formulaire' });
                }, function() {
                    $state.go('formulaire');
                });
            }]*/
        })
    }

})();
