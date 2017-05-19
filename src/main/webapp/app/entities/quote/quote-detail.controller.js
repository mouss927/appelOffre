(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .controller('QuoteDetailController', QuoteDetailController);

    QuoteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Quote', 'Review', 'Provider', 'Project'];

    function QuoteDetailController($scope, $rootScope, $stateParams, previousState, entity, Quote, Review, Provider, Project) {
        var vm = this;

        vm.quote = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('monAppelOffreApp:quoteUpdate', function(event, result) {
            vm.quote = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
