(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('home_eligibleProjects', {
            parent: 'app',
            url: '/home_eligibleProjects',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/home_eligibleProjects/home_eligibleProjects.html',
                    controller: 'home_eligibleProjectsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
             
            }
        });
    }
})();
