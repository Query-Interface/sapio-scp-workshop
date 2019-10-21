sap.ui.define([
    "sap/ui/core/mvc/Controller",
    "sap/ui/model/json/JSONModel",
    "sap/ui/model/Filter",
	"sap/ui/model/FilterOperator"
 ], function (Controller, JSONModel, Filter, FilterOperator) {
    "use strict";
    return Controller.extend("sap.io.demo.project.controller.ProjectList", {
       onInit : function () {
        let host = 'http://localhost:8080';
        if( window['env'] !== 'undefined') {
            host = window['env'].apiroot
        }
        // updates the url in function of your landscape.
        let projectServiceUrl = host + '/api/projects';
        let featureFlagServiceUrl = host + '/api/ff/';
        
        // TODO: for exercice 2, update the code to take feature flag into account
        var inputField = this.byId('searchField');
        inputField.setVisible(false);

        fetch(featureFlagServiceUrl+"search").then(function(response) {
            if (response.ok) {
                return response.json();
            } else {
                console.log('Error loading feature flag. Status Code: ' + response.status);
                return {};
            }
        }).then(function(data) {
            if (data.status === 'enabled') {
                inputField.setVisible(true);
            }
        });

        fetch(projectServiceUrl).then(function(response) {
                if (response.ok) {
                    return response.json();
                } else {
                    console.log('Error loading projects. Status Code: ' + response.status);
                    return {};
                }
            }).then(function(data) {
                // set data model on view
                var catalog = {
                   projects : data
                };
                var oModel = new JSONModel(catalog);
                this.getView().setModel(oModel);
            }.bind(this));
       },

       onSearch : function (oEvent) {
            if (oEvent.getParameters().refreshButtonPressed) {
                // Search field's 'refresh' button has been pressed.
                // This is visible if you select any master list item.
                // In this case no new search is triggered, we only
                // refresh the list binding.
                this.onRefresh();
            } else {
                var aTableSearchState = [];
                var sQuery = oEvent.getParameter("query");

                if (sQuery && sQuery.length > 0) {
                    aTableSearchState = [new Filter("name", FilterOperator.Contains, sQuery)];
                }
                this._applySearch(aTableSearchState);
            }
        },

        /**
		 * Internal helper method to apply both filter and search state together on the list binding
		 * @param {sap.ui.model.Filter[]} aTableSearchState An array of filters for the search
		 * @private
		 */
		_applySearch: function(aTableSearchState) {
			var oTable = this.byId("table");
			oTable.getBinding("items").filter(aTableSearchState, "Application");
		}
    });
 });
