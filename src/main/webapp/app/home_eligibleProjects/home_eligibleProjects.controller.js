(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .controller('home_eligibleProjectsController', home_eligibleProjectsController);

    home_eligibleProjectsController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'EligibleProject'];

    function home_eligibleProjectsController ($scope, Principal, LoginService, $state, EligibleProject) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        
        // Ajouter la liste des projets eligibles dans le model d'Angular. Par defaut c'est liste vide
        vm.eligibleProjects = [];
        
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();
        
        getEligibleProjects();
        
        // Fonction qui ramene les projets elegible depuis le controller SpringMVC (ProjectResource), et appeler la methode /api/eligibleProjects
        // Le JSON retournee va etre affecte au model vm.eligibleProjects = [];
        function getEligibleProjects() {
        	EligibleProject.query(function(result) {
                vm.eligibleProjects = result;
            });
        }

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
      
        function register(){
        	$state.go('register');
        }
        
    }
})();