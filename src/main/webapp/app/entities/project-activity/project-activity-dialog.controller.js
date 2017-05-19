(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .controller('ProjectActivityDialogController', ProjectActivityDialogController);

    ProjectActivityDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProjectActivity', 'Project', 'Activity'];

    function ProjectActivityDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ProjectActivity, Project, Activity) {
        var vm = this;

        vm.projectActivity = entity;
        vm.clear = clear;
        vm.save = save;
        vm.projects = Project.query();
        vm.activities = Activity.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.projectActivity.id !== null) {
                ProjectActivity.update(vm.projectActivity, onSaveSuccess, onSaveError);
            } else {
                ProjectActivity.save(vm.projectActivity, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('monAppelOffreApp:projectActivityUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
