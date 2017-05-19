(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .controller('ReviewController', ReviewController);

    ReviewController.$inject = ['DataUtils', 'Review', 'ReviewSearch'];

    function ReviewController(DataUtils, Review, ReviewSearch) {

        var vm = this;

        vm.reviews = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Review.query(function(result) {
                vm.reviews = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ReviewSearch.query({query: vm.searchQuery}, function(result) {
                vm.reviews = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
