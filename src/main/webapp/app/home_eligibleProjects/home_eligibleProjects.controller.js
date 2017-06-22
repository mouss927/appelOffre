(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .controller('home_eligibleProjectsController', home_eligibleProjectsController);

    home_eligibleProjectsController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'EligibleProject', '$http'];

    function home_eligibleProjectsController ($scope, Principal, LoginService, $state, EligibleProject, $http) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        vm.uploadFiles = uploadFiles;
        vm.getCookie = getCookie;
        
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
        
        function uploadFiles(files) {
        	if (!files || files.length === 0) {
            	console.log("nothing to upload");
        	}
        	else{
        	console.log("uploading..");
       	 	var fd = new FormData();
       	 
       	 	fd.append("file",files[0]);
       	  
       	 	console.log(files.length);
   

   	        
    	    var req = $http.post('/api/saveFile',fd, { 
    	    	
    	        transformRequest: angular.identity,
    	        headers: {
    	        	'Content-Type': undefined,
    	        	'Accept': "*/*"
    	        }
    	    
    	    
    	    
    	    }).success(function(data, status, headers, config) {
    	    	console.log("uploading imageData OK");
    	    	

    	    	console.log("data test : " + data);
    
    	    	console.log("fd test : " + fd);
    	    	
    	    	
    	    })
    	    .error(function(err) {
    	    	console.log("uploading imageData ERROR", err);
    	    });
    	    
    	    
    	    console.log(req);
    	    
    	 }
        }
        
        function getCookie(name) {
        	  var value = "; " + document.cookie;
        	  var parts = value.split("; " + name + "=");
        	  if (parts.length == 2) return parts.pop().split(";").shift();
        }
        
    }
})();