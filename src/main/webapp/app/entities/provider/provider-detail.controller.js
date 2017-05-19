(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .controller('ProviderDetailController', ProviderDetailController);

    ProviderDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Provider', 'User', 'Quote', 'ProviderEligibility', 'ProviderActivity', 'Registration'];

    function ProviderDetailController($scope, $rootScope, $stateParams, previousState, entity, Provider, User, Quote, ProviderEligibility, ProviderActivity, Registration) {
        var vm = this;

        vm.provider = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('monAppelOffreApp:providerUpdate', function(event, result) {
            vm.provider = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
