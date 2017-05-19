(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .controller('ProjectActivityController', ProjectActivityController);

    ProjectActivityController.$inject = ['ProjectActivity', 'ProjectActivitySearch'];

    function ProjectActivityController(ProjectActivity, ProjectActivitySearch) {

        var vm = this;

        vm.projectActivities = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            ProjectActivity.query(function(result) {
                vm.projectActivities = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ProjectActivitySearch.query({query: vm.searchQuery}, function(result) {
                vm.projectActivities = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
