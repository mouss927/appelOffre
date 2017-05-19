(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .controller('ProviderDialogSaveController', ProviderDialogSaveController);

    ProviderDialogSaveController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Provider', 'Project'];

    function ProviderDialogSaveController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Provider, Project) {
    
    	        var vm = this;

    	        vm.provider = entity;
    	        vm.clear = clear;
    	        vm.datePickerOpenStatus = {};
    	        vm.openCalendar = openCalendar;
    	        vm.save = save;
    	        vm.quotes = Quote.query();
    	        vm.providereligibilities = ProviderEligibility.query();
    	        vm.provideractivities = ProviderActivity.query();
    	        vm.registrations = Registration.query();

    	        $timeout(function (){
    	            angular.element('.form-group:eq(1)>input').focus();
    	        });

    	        function clear () {
    	            $uibModalInstance.dismiss('cancel');
    	        }

    	        function save () {
    	            vm.isSaving = true;
    	            if (vm.provider.id !== null) {
    	                Provider.update(vm.provider, onSaveSuccess, onSaveError);
    	            } else {
    	                Provider.save(vm.provider, onSaveSuccess, onSaveError);
    	            }
    	        }

    	        function onSaveSuccess (result) {
    	            $scope.$emit('monappeloffreApp:providerUpdate', result);
    	            $uibModalInstance.close(result);
    	            vm.isSaving = false;
    	        }

    	        function onSaveError () {
    	            vm.isSaving = false;
    	        }

    	        vm.datePickerOpenStatus.registrationDate = false;

    	        function openCalendar (date) {
    	            vm.datePickerOpenStatus[date] = true;
    	        }
    }
})();
