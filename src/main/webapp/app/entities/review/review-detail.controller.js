(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .controller('ReviewDetailController', ReviewDetailController);

    ReviewDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Review', 'Quote'];

    function ReviewDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Review, Quote) {
        var vm = this;

        vm.review = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('monAppelOffreApp:reviewUpdate', function(event, result) {
            vm.review = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
