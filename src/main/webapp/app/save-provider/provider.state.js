(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('provider.new', {
            parent: 'entity',
            url: '/provider_spe/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/save-provider/provider-dialog.html',
                    controller: 'ProviderDialogSaveController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                            	lastName: null,
                                firstName: null,
                                mail: null,
                                password: null,
                                registrationDate: null,
                                companyName: null,
                                siret: null,
                                interventionZone: null,
                                id: null
                            };
                        }
                    }
                });
             
               
            }]
        });
    }

})();
