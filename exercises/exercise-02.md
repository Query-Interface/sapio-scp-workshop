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
- Restage the application to take new settings into account
- Run the command `cf env sapio-scp-<username>` to check the result of the previous steps

## Step 3: Update the applications
- Update the frontend source code by copying the code in the `exercices/solution` folder.
- Build and deploy the frontend running the following commands:
    - `npm install`
    - `npm run build`
    - `cf push -f cf/manifest.yml`

## Step 4: Create a new flag
- Navigate to the feature flag dashboard, for this go to the [SCP Cockpit](https://cockpit.hanatrial.ondemand.com/#/home/welcome). Then Navigate to your space and click on the rest application (`sapio-scp-<username>`). Go to `Service Bindings` and in the `Actions` section, click on the `Open Dashboard` icon
- Click on new Flag
    - set name as `search`
    - set a description
    - ensure the state is set to `OFF`
- Test the service response by calling the following URI: `https://sapio-scp-<username>.cfapps.eu10.hana.ondemand.com/api/ff/search`
- Enable the flag in the dashboard and test again
