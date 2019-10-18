sap.ui.define([
   "sap/ui/core/mvc/Controller",
   "sap/ui/model/json/JSONModel"
], function (Controller, JSONModel) {
   "use strict";
   return Controller.extend("sap.io.demo.controller.App", {
      onInit : function () {
		 let host = 'http://localhost:8080';
		 if( window['env'] !== 'undefined') {
			 host = window['env'].apiroot
		 }
		 let serviceUrl = host + '/api/projects'; // update the url in function of your landscape.
		 fetch(serviceUrl).then(function(response) {
			if (response.ok) {
				return response.json();
			} else {
				console.log('Error loading projects. Status Code: ' + response.status);
				return {};
			}
			})
			.then(function(data) {
				console.table(data);
				// set data model on view
				var catalog = {
				   projects : data
				};
				var oModel = new JSONModel(catalog);
				this.getView().setModel(oModel);
			}.bind(this)
		 );
         
      }
   });
});