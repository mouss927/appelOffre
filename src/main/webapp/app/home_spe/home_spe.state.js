(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('home_spe', {
            parent: 'app',
            url: '/home_spe',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/home_spe/home_spe.html',
                    controller: 'home_speController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
             
            }
        });
    }
})();
