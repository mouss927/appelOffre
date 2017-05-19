(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .controller('ProviderEligibilityDetailController', ProviderEligibilityDetailController);

    ProviderEligibilityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ProviderEligibility', 'Project', 'Provider'];

    function ProviderEligibilityDetailController($scope, $rootScope, $stateParams, previousState, entity, ProviderEligibility, Project, Provider) {
        var vm = this;

        vm.providerEligibility = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('monAppelOffreApp:providerEligibilityUpdate', function(event, result) {
            vm.providerEligibility = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
