(function() {
	'use strict';

	angular
	.module('monAppelOffreApp')
	.config(stateConfig);

	stateConfig.$inject = ['$stateProvider'];

	function stateConfig($stateProvider) {
		$stateProvider
		.state('formulairePrvider', {
			parent: 'app',
			url: '/formulaireProvider/new',
			data: {
				authorities: ['ROLE_USER']
			},

			views: {
				'content@': {
					templateUrl: 'app/formulaireProvider/formulaireProvider.html',
					controller: 'formulaireProviderController',
					controllerAs: 'vm',
				}
			},
			resolve: {
				entity: function ()  {
					return{

						lastName: null,
						firstName: null,			
						companyName: null,
						siret: null,
						activity: null
					};

				}
			}




		})
	}

})();