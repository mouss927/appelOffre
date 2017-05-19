(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .controller('ProjectPicDetailController', ProjectPicDetailController);

    ProjectPicDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ProjectPic', 'Project'];

    function ProjectPicDetailController($scope, $rootScope, $stateParams, previousState, entity, ProjectPic, Project) {
        var vm = this;

        vm.projectPic = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('monAppelOffreApp:projectPicUpdate', function(event, result) {
            vm.projectPic = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
