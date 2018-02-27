
	var app = angular.module('myApp', []);
	app.controller('ebooksCtrl', function($scope, $http,$window) {		
			var timerhandler;
			$scope.CheckNow = function()
		    {
//	        	alert('success');

		        $.ajax(
		        {
		            type: 'GET',
		            url: 'GetAllLikesNumOrdered',
		            success: function(data) 
		            {
		            	//alert('success');
		                var allcookies = document.cookie;
		                cookiearray = allcookies.split(';');
		                  // username = cookiearray[0].split('=')[1]; 
		                   type = cookiearray[1].split('=')[1];  
		                   console.log(type)
		                   if(type=="admin"){
		                       window.location.href='homeAdmin.html';
		                   }
		                   else{
 		           		$http.get('GetAllEbooks').then(function(response) {
		        			$scope.allEbooks = response.data;

		        		}); 
						$scope.myVar = true;
		        //           alert('suuu')
		                   }
		            }
		        });

		    }
		    
		    
		    console.log("ajaaax")
		    timerhandler = setInterval($scope.CheckNow,60000);
		    
		    $scope.logout = function(){
				$http.get("Logout").success(function(data, status, headers,config){
	               window.location.href='index.html';

				})
				.error(function(data, status, headers,config){
					console.log(data);
				});
			}
	});