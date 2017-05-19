(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .controller('ProviderEligibilityDialogController', ProviderEligibilityDialogController);

    ProviderEligibilityDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProviderEligibility', 'Project', 'Provider'];

    function ProviderEligibilityDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ProviderEligibility, Project, Provider) {
        var vm = this;

        vm.providerEligibility = entity;
        vm.clear = clear;
        vm.save = save;
        vm.projects = Project.query();
        vm.providers = Provider.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.providerEligibility.id !== null) {
                ProviderEligibility.update(vm.providerEligibility, onSaveSuccess, onSaveError);
            } else {
                ProviderEligibility.save(vm.providerEligibility, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('monAppelOffreApp:providerEligibilityUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
