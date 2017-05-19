(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .controller('viewquoteController', viewquoteController);

    viewquoteController.$inject = ['Quote'];

    function viewquoteController(Quote) {

        var vm = this;

        vm.quotes = [];

        loadAll();

        function loadAll() {
            Quote.query(function(result) {
                vm.quotes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
