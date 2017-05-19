(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .controller('CustomerController', CustomerController);

    CustomerController.$inject = ['Customer', 'CustomerSearch'];

    function CustomerController(Customer, CustomerSearch) {

        var vm = this;

        vm.customers = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Customer.query(function(result) {
                vm.customers = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            CustomerSearch.query({query: vm.searchQuery}, function(result) {
                vm.customers = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
