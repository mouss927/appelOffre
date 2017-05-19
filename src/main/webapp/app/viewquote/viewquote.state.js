(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('viewquote', {
            parent: 'entity',
            url: '/viewquote',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Quotes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/viewquote/viewquote.html',
                    controller: 'viewquoteController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        
    }

})();
