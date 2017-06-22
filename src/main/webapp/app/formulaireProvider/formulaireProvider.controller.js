(function() {
	'use strict';

	angular
	.module('monAppelOffreApp')
	.controller('formulaireProviderController', formulaireProviderController);

	formulaireProviderController.$inject = ['$timeout', '$scope', 
		'$stateParams',  '$q', 'Provider', 'User', 'Quote', 
		'ProviderEligibility', 'ProviderActivity', 'Registration', 
		'Activity','$http'];

	function formulaireProviderController ($timeout, $scope, $stateParams, 
			$q, Provider, User, Quote, ProviderEligibility,
			ProviderActivity, Registration, 
			Activity,$http) {
		var vm = this;

		vm.activities = [];
		vm.provider = Provider.query();
		vm.clear = clear;
		vm.datePickerOpenStatus = {};
		vm.openCalendar = openCalendar;
		vm.save = save;
		vm.users = User.query();
		vm.activities = Activity.query();
		vm.quotes = Quote.query();
		vm.providereligibilities = ProviderEligibility.query();
		vm.provideractivities = ProviderActivity.query();
		vm.registrations = Registration.query();

		loadAll();

		$timeout(function (){
			angular.element('.form-group:eq(1)>input').focus();
		});

		function loadAll() {
			Activity.query(function(result) {
				vm.activities = result;
				vm.searchQuery = null;
			});
		}

		function clear () {

		}

		function save () {
			vm.isSaving = true;

			Provider.save(vm.provider, onSaveSuccess, onSaveError);

		}

		function onSaveSuccess (result) {
			$scope.$emit('monAppelOffreApp:providerUpdate', result);

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
