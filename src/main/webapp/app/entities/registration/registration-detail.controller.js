(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .controller('RegistrationDetailController', RegistrationDetailController);

    RegistrationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Registration', 'Provider'];

    function RegistrationDetailController($scope, $rootScope, $stateParams, previousState, entity, Registration, Provider) {
        var vm = this;

        vm.registration = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('monAppelOffreApp:registrationUpdate', function(event, result) {
            vm.registration = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
