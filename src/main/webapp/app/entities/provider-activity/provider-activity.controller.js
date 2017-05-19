(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .controller('ProviderActivityController', ProviderActivityController);

    ProviderActivityController.$inject = ['ProviderActivity', 'ProviderActivitySearch'];

    function ProviderActivityController(ProviderActivity, ProviderActivitySearch) {

        var vm = this;

        vm.providerActivities = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            ProviderActivity.query(function(result) {
                vm.providerActivities = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ProviderActivitySearch.query({query: vm.searchQuery}, function(result) {
                vm.providerActivities = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
