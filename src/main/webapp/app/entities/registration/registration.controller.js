(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .controller('RegistrationController', RegistrationController);

    RegistrationController.$inject = ['Registration', 'RegistrationSearch'];

    function RegistrationController(Registration, RegistrationSearch) {

        var vm = this;

        vm.registrations = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Registration.query(function(result) {
                vm.registrations = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            RegistrationSearch.query({query: vm.searchQuery}, function(result) {
                vm.registrations = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
