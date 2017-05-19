(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .controller('ProjectPicController', ProjectPicController);

    ProjectPicController.$inject = ['ProjectPic', 'ProjectPicSearch'];

    function ProjectPicController(ProjectPic, ProjectPicSearch) {

        var vm = this;

        vm.projectPics = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            ProjectPic.query(function(result) {
                vm.projectPics = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ProjectPicSearch.query({query: vm.searchQuery}, function(result) {
                vm.projectPics = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
