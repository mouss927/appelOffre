(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .controller('ProjectActivityDetailController', ProjectActivityDetailController);

    ProjectActivityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ProjectActivity', 'Project', 'Activity'];

    function ProjectActivityDetailController($scope, $rootScope, $stateParams, previousState, entity, ProjectActivity, Project, Activity) {
        var vm = this;

        vm.projectActivity = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('monAppelOffreApp:projectActivityUpdate', function(event, result) {
            vm.projectActivity = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
