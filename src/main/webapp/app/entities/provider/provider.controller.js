(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .controller('ProviderController', ProviderController);

    ProviderController.$inject = ['Provider', 'ProviderSearch'];

    function ProviderController(Provider, ProviderSearch) {

        var vm = this;

        vm.providers = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Provider.query(function(result) {
                vm.providers = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ProviderSearch.query({query: vm.searchQuery}, function(result) {
                vm.providers = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
