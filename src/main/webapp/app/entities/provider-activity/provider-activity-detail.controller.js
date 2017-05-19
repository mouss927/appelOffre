(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .controller('ProviderActivityDetailController', ProviderActivityDetailController);

    ProviderActivityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ProviderActivity', 'Provider', 'Activity'];

    function ProviderActivityDetailController($scope, $rootScope, $stateParams, previousState, entity, ProviderActivity, Provider, Activity) {
        var vm = this;

        vm.providerActivity = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('monAppelOffreApp:providerActivityUpdate', function(event, result) {
            vm.providerActivity = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
