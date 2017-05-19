(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .controller('ActivityController', ActivityController);

    ActivityController.$inject = ['Activity', 'ActivitySearch'];

    function ActivityController(Activity, ActivitySearch) {

        var vm = this;

        vm.activities = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Activity.query(function(result) {
                vm.activities = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ActivitySearch.query({query: vm.searchQuery}, function(result) {
                vm.activities = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
