	var app = angular.module('myApp', []);
	app.controller('ebooksCtrl', function($scope, $http,$window) {		
		
			var timerhandler;

		    $scope.CheckNow = function()
		    {

		        $.ajax(
		        {
		            type: 'GET',
		            url: 'GetAllLikesNumOrdered',
		            success: function(data) 
		            {
		     //       	alert('success');
		                var allcookies = document.cookie;
		                cookiearray = allcookies.split(';');
		                  // username = cookiearray[0].split('=')[1]; 
		                   type = cookiearray[1].split('=')[1];  
		                   console.log(type)
		                   if(type=="user"){
		                       window.location.href='homeUser.html';
		                   }
		                   else{
		           		$http.get('GetAllEbooks').success(function(data, status, headers,config) {
		        			$scope.allEbooks = data;

		        		}).error(function(data, status, headers,config){
							console.log(data);

               			});
	               		$http.get('GetAllUnapprovedReviews').success(function(data, status, headers,config) {
	            			$scope.reviews = data;
	            			$scope.statuscode = status;
	            		}).error(function(data, status, headers,config){
							console.log(data);

               			});
						$scope.myVar = true;
		             //      alert('suuu')
		                   }
		            }
		        });

		    }
		    //console.log("ajaaax")
		    timerhandler = setInterval($scope.CheckNow,50000);
					

			$scope.ApproveReview = function(username,button,bookId,review) {
		    	var btn = document.getElementById(button);
					var data = {
							username : username,
							id : bookId,
							review : review
						};
						//Call the services
						var jsonData = JSON.stringify(data);
				    //Logic to delete the item
				    $http.post('ApproveEbookReview',jsonData).success(function(data, status, headers,config) {
				$scope.users = data;
				$scope.statuscode = status;
			}).error(function(data, status, headers,config){
				console.log(data);

   			});
					  var row = btn.parentNode.parentNode;
					  row.parentNode.removeChild(row);
			}
			$scope.IgnoreReview = function(username,button,bookId,review) {
		    	var btn = document.getElementById(button);
					var data = {
							username : username,
							id : bookId,
							review : review
						};
						//Call the services
						var jsonData = JSON.stringify(data);
				    //Logic to delete the item
				    $http.post('IgnoreEbookReview',jsonData).success(function(data, status, headers,config) {
				$scope.users =data;
				$scope.statuscode = status;
			}).error(function(data, status, headers,config){
				console.log(data);

   			});
					  var row = btn.parentNode.parentNode;
					  row.parentNode.removeChild(row);
			}
			
			$http.get('GetAllPurchasedEbooks').success(function(data, status, headers,config) {
				$scope.purchases = data;
				$scope.statuscode = status;
			}).error(function(data, status, headers,config){
				console.log(data);

   			});
			
			$scope.logout = function(){
				$http.get("Logout").success(function(data, status, headers,config){
                    window.location.href='index.html';

				}).error(function(data, status, headers,config){
					console.log(data);

	   			});
			}
	});