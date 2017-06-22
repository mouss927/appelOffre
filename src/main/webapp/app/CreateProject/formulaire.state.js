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
      
        })
    }

})();
