(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .controller('ProviderActivityDialogController', ProviderActivityDialogController);

    ProviderActivityDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProviderActivity', 'Provider', 'Activity'];

    function ProviderActivityDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ProviderActivity, Provider, Activity) {
        var vm = this;

        vm.providerActivity = entity;
        vm.clear = clear;
        vm.save = save;
        vm.providers = Provider.query();
        vm.activities = Activity.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.providerActivity.id !== null) {
                ProviderActivity.update(vm.providerActivity, onSaveSuccess, onSaveError);
            } else {
                ProviderActivity.save(vm.providerActivity, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('monAppelOffreApp:providerActivityUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
