(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .controller('ActivityDetailController', ActivityDetailController);

    ActivityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Activity', 'ProviderActivity', 'ProjectActivity'];

    function ActivityDetailController($scope, $rootScope, $stateParams, previousState, entity, Activity, ProviderActivity, ProjectActivity) {
        var vm = this;

        vm.activity = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('monAppelOffreApp:activityUpdate', function(event, result) {
            vm.activity = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
