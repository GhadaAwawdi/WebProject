

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

		        		})
		        		.error(function(data, status, headers,config){
		        			console.log(data);
		        		});
						$scope.myVar = true;
		                  // alert('suuu')
		                   }
		            }
		        });

		    }
		    console.log("ajaaax")
		    timerhandler = setInterval($scope.CheckNow,50000);
		    
			$http.get('GetAllEbooks').success(function(data, status, headers,config) {
				$scope.myData = data;
				$scope.statuscode = status;
			})
			.error(function(data, status, headers,config){
				console.log(data);
			});
		    
	
			
			
        var allcookies = document.cookie;
        cookiearray = allcookies.split(';');
           username = cookiearray[0].split('=')[1]; 
           type = cookiearray[1].split('=')[1];  
           console.log(username);

       var parameters = { params: {
			username : username
          }
       }


       $scope.logout = function(){
			$http.get("Logout").success(function(data, status, headers,config){
               window.location.href='index.html';

			})
			.error(function(data, status, headers,config){
				console.log(data);
			});
		}

		$scope.getReviews =function (id,title){
			document.getElementById('ebookName').innerHTML =  title;
			var temp = {
				title: title
			};
			var jsonId = JSON.stringify(temp);
			var pars = {
				params:{title: title}
			};
			console.log(jsonId)
			$http.get('GetAllReviewsByTitle',pars).success(function(data, status, headers,config){
				$scope.reviews = data;
				$scope.statusCode = status;
			})
			.error(function(data, status, headers,config){
				console.log(data);
			});
		}
		
		$scope.goToprofile =function (title,nickname){
			document.getElementById(title+nickname).setAttribute('href', 'profile.html?nickname=' + nickname);
		}
		
		$scope.myFunction = function(bookTitle) {
		//	 alert('myFunction');
			console.log(bookTitle);
			var pars = {
					params:{title: bookTitle}
				};
				$http.get('GetAllNicknamesByEbookLikes',pars).success(function(data, status, headers,config) {
					$scope.booklikes = data;
					$scope.likeStatusCode = status;
				})
				.error(function(data, status, headers,config){
					console.log(data);
				});
		//$scope.likes = {name : "sampleName", age : 18, email : "mail"};
	//	var popup = document.getElementById(bookTitle);
	//	 popup.clasList.add('ghada','heeey');
		// popup.classList.toggle("show");
		}
		
		$scope.openImg = function(imgId,source,alter){
	    	var modal = document.getElementById('ghadaModal');

	    	// Get the image and insert it inside the modal - use its "alt" text as a caption
	    	var img = document.getElementById(imgId);
	    	var modalImg = document.getElementById("img01");
	    	var captionText = document.getElementById("caption");
	    	    modal.style.display = "block";
	    	    modalImg.src = source;
	    	    captionText.innerHTML = alter;
	    	// Get the <span> element that closes the modal
	    	var span = document.getElementsByClassName("close")[0];

	    	// When the user clicks on <span> (x), close the modal
	    	span.onclick = function() { 
	    	    modal.style.display = "none";
	    	}
	}

	});