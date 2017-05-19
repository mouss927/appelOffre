(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .controller('ProviderEligibilityController', ProviderEligibilityController);

    ProviderEligibilityController.$inject = ['ProviderEligibility', 'ProviderEligibilitySearch'];

    function ProviderEligibilityController(ProviderEligibility, ProviderEligibilitySearch) {

        var vm = this;

        vm.providerEligibilities = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            ProviderEligibility.query(function(result) {
                vm.providerEligibilities = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ProviderEligibilitySearch.query({query: vm.searchQuery}, function(result) {
                vm.providerEligibilities = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
