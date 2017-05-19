(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('customer.news', {
            parent: 'entity',
            url: '/home_spe/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/save-customer/customer-dialog.html',
                    controller: 'CustomerDialogSaveController',
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
                                postalCode: null,
                                city: null,
                                street: null,
                                streetNumber: null,
                                complementStreet: null,
                                id: null
                            };
                        }
                    }
                });
               
            }]
        });
    }

})();
