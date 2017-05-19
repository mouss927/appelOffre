(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .controller('ProviderEligibilityDeleteController',ProviderEligibilityDeleteController);

    ProviderEligibilityDeleteController.$inject = ['$uibModalInstance', 'entity', 'ProviderEligibility'];

    function ProviderEligibilityDeleteController($uibModalInstance, entity, ProviderEligibility) {
        var vm = this;

        vm.providerEligibility = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ProviderEligibility.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
