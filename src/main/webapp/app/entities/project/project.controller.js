(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .controller('ProjectController', ProjectController);

    ProjectController.$inject = ['DataUtils', 'Project', 'ProjectSearch'];

    function ProjectController(DataUtils, Project, ProjectSearch) {

        var vm = this;

        vm.projects = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Project.query(function(result) {
                vm.projects = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ProjectSearch.query({query: vm.searchQuery}, function(result) {
                vm.projects = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
