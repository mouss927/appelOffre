(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .controller('ProviderActivityDeleteController',ProviderActivityDeleteController);

    ProviderActivityDeleteController.$inject = ['$uibModalInstance', 'entity', 'ProviderActivity'];

    function ProviderActivityDeleteController($uibModalInstance, entity, ProviderActivity) {
        var vm = this;

        vm.providerActivity = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ProviderActivity.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
