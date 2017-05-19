(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .controller('QuoteController', QuoteController);

    QuoteController.$inject = ['Quote', 'QuoteSearch'];

    function QuoteController(Quote, QuoteSearch) {

        var vm = this;

        vm.quotes = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Quote.query(function(result) {
                vm.quotes = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            QuoteSearch.query({query: vm.searchQuery}, function(result) {
                vm.quotes = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
