(function() {
	'use strict';

	angular.module('monAppelOffreApp').controller('FormulaireController',
			FormulaireController);

	FormulaireController.$inject = [ '$timeout', '$scope', '$stateParams',
			'DataUtils', 'Project', 'Quote', 'ProviderEligibility',
			'ProjectActivity', 'Customer', 'ProjectPic', 'ProviderActivity',
			'Formulaire', 'Activity', '$http' ];

	function FormulaireController($timeout, $scope, $stateParams, DataUtils,
			Project, Quote, ProviderEligibility, ProjectActivity, Customer,
			ProjectPic, ProviderActivity, Formulaire, Activity, $http) {

		var vm = this;
		
		vm.project = {
			
		};

		vm.activities = [];
		vm.clear = clear;
		vm.datePickerOpenStatus = {};
		vm.openCalendar = openCalendar;
		vm.byteSize = DataUtils.byteSize;
		vm.openFile = DataUtils.openFile;
		vm.save = save;
		vm.quotes = Quote.query();
		vm.providereligibilities = ProviderEligibility.query();
		vm.projectactivities = ProjectActivity.query();
		vm.customers = Customer.query();
		vm.projectpics = ProjectPic.query();

		vm.provideractivities = ProviderActivity.query();
		vm.projectactivities = ProjectActivity.query();
		
		vm.files = null;

		loadAll();

		function loadAll() {
			Activity.query(function(result) {
				vm.activities = result;
				vm.searchQuery = null;
			});
		}

		$timeout(function() {
			angular.element('.form-group:eq(1)>input').focus();
		});

		function clear() {
			
		}

		function save() {
			vm.isSaving = true;
			
			Formulaire.save(vm.project, {}, onSaveSuccess, onSaveError);
			
		}

		function onSaveSuccess(result) {
			$scope.$emit('monAppelOffreApp:projectUpdate', result);
			
			vm.isSaving = false;
		}

		function onSaveError() {
			vm.isSaving = false;
		}

		vm.setImages = function($file, project) {
			if ($file) {
				DataUtils.toBase64($file, function(base64Data) {
					$scope.$apply(function() {
						project.images = base64Data;
						project.imagesContentType = $file.type;
					});
				});
			}
		};
		vm.datePickerOpenStatus.dateSend = false;

		function openCalendar(date) {
			vm.datePickerOpenStatus[date] = true;
		}
		
		vm.setFiles = function(files) {
			console.log("oooo");
			vm.files = files;
		}
		
		vm.uploadFiles = function() {
        	if (!vm.files || vm.files.length === 0) {
            	console.log("nothing to upload");
        	}
        	else{
        	console.log("uploading..");
       	 	var fd = new FormData();
       	 	
       	 	fd.append("images",vm.files[0]);
       	 	fd.append("title", vm.project.title);
       	 	fd.append("description", vm.project.description);
       	 	fd.append("activity", vm.project.activity);
       	    
       	 	console.log(vm.files.length);
       	 

   	        
    	    var req = $http.post('/api/formulaire',fd, { 
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
	}
})();
