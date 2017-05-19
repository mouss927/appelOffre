(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .controller('ProjectPicDialogController', ProjectPicDialogController);

    ProjectPicDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProjectPic', 'Project'];

    function ProjectPicDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ProjectPic, Project) {
        var vm = this;

        vm.projectPic = entity;
        vm.clear = clear;
        vm.save = save;
        vm.projects = Project.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.projectPic.id !== null) {
                ProjectPic.update(vm.projectPic, onSaveSuccess, onSaveError);
            } else {
                ProjectPic.save(vm.projectPic, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('monAppelOffreApp:projectPicUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
