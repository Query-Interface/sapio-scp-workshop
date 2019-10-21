# Exercise 2: Leveraging Services

## Learning Goal
SCP provides lots of services to ease development application. We will see how to discover them and how to consume them in your application.

## Step 1: Discover services
- Open the [SCP Cockpit](https://cockpit.hanatrial.ondemand.com/#/home/welcome), login with your credentials and navigate to your **space** e.g. `dev`
- Navigate to the `Marketplace` to check which services are available in the SCP trial instance
- Alternatively, one can execute the command: `cf marketplace`
- It will display the list of available services

## Step 2: Adding the services
- Create a service instance of Feature-Flag with plan lite by executiing the following command: `cf create-service feature-flags lite feature-flags-instance`
- Bind the service to the REST application using the following command: `cf bind-service sapio-scp-<username> feature-flags-instance`
- Restage the application so as to take new settings into account

## Step 3: Update the applications
- TODO

## Step 4: Create a new flag
- Navigate to the feature flag dashboard on the SAP Cloud Platform following this link: [ https://feature-flags-dashboard.cfapps.eu10.hana.ondemand.com/manageinstances/<instance-id>](https://feature-flags-dashboard.cfapps.eu10.hana.ondemand.com/manageinstances/<instance-id>)
    - Could also be accessed through the cockpit (Service Instances > Actions > Open Dashboard icon)
- Click on new Flag
- Fill in the fields and ensure the state is set to `OFF`
- Test the service response by calling the following URI: `https://sapio-scp-<username>.cfapps.eu10.hana.ondemand.com/api/ff/test`
- Enable the flag in the dashboard and test again


