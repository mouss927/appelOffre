(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .controller('viewProjectController', viewProjectController);

    viewProjectController.$inject = ['$scope', 'Principal', 'LoginService', '$state', '$uibModal'];

    function viewProjectController ($scope, Principal, LoginService, $state, $uibModal) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
      
        function register(){
        	$state.go('register');
        }
        vm.newviewproject = function(){
        	 $uibModal.open({
                 templateUrl: 'app/entities/project/project-dialog.html',
                 controller: 'ProjectDialogController',
                 controllerAs: 'vm',
                 backdrop: 'static',
                 size: 'lg',
                 resolve: {
                     entity: function () {
                         return {
                             title: null,
                             description: null,
                             dateSend: null,
                             postalCode: null,
                             city: null,
                             street: null,
                             streetNumber: null,
                             complementStreet: null,
                             id: null
                         };
                     }
                 }
             }).result.then(function() {
                 $state.go('project', null, { reload: 'project' });
             }, function() {
                 $state.go('project');
             });
        }
        
       
    }
})();




