(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('activity', {
            parent: 'entity',
            url: '/activity',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Activities'
            },
            views: {
                'content@': {
                    templateUrl: 'app/save-provider/provder-dialog.html',
                    controller: 'ActivityController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        
        });
    }

})();