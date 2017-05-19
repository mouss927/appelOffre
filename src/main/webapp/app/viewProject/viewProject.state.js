(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('ViewProject', {
            parent: 'app',
            url: '/ViewProject',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/viewProject/viewProject.html',
                    controller: 'viewProjectController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
             
            }
        });
    }
})();
