# Exercise 1: Discover SCP Cloud Foundry (PaaS: Platform as a Service)

## Learning Goal
You will discover basic concepts and tools used in SAP Cloud Platform (Cloud Foundry). We will deploy applications and get familiar with the cockpit and Cloud Foundry command line (CF CLI).

## Prerequisite

### Create a trial account
Get a Trial account on [SAP Cloud platform](https://cockpit.hanatrial.ondemand.com/#/home/welcome), download and install Cloud Foundry Command Line Interface (CF CLI client) as described here as part of the [Getting Started](https://help.cf.sap.hana.ondemand.com/frameset.htm?b8ee7894fe0b4df5b78f61dd1ac178ee.html) section.

### Get the exercices code
Download the code from [Github](https://github.com/Query-Interface/sapio-scp-workshop).
Either use git or simply download it as a zip file.

## Step 1: SCP Cockpit
- Open the [SCP Cockpit](https://cockpit.hanatrial.ondemand.com/#/home/welcome), login with your credentials and navigate to your **space** e.g. `dev`
- Have a look at the navigation bar, discover some of the features available in SCP. `Services` the `Service Marketplace` provides some services (Database, ML, Cloud specific services, ...) that can be leverages by your applications.


## Step 2: CF Command Line Interface (CF CLI)
Open a Command Prompt and try the following command.

- `cf api https://api.cf.eu10.hana.ondemand.com` to target the SCP trial instance
- `cf login` (use the same credential as the one for the SCP Cockpit)
- Select your trial organization, if your user is already assigned to severals spaces

## Step 3: Compile the REST API
- Navigate to backend folder
- Run the following command: `mvn package -DskipTests` (if maven is not install, you can use `mvnw.cmd package -DskipTests`

## Step 4: Deploy the application
- `cf push sapio-scp-<username> -p target/sapio-scp-workshop-0.0.1-SNAPSHOT.jar -b java_buildpack -m 700M`
- Try to access the execute the following request in a browser on a REST Client: `sapio-scp-<username>.cfapps.eu10.hana.ondemand.com/api/projects`
- Try to identify the issue by checking the logs using the following command: `cf logs sapio-scp-<username> --recent`

## Step 5: Getting the API Key
The REST API consume the following service: [https://api.sap.com/api/_CPD_SC_EXTERNAL_SERVICES_SRV/resource](https://api.sap.com/api/_CPD_SC_EXTERNAL_SERVICES_SRV/resource).
- Navigate to [SAP API Hub](https://api.sap.com/api/_CPD_SC_EXTERNAL_SERVICES_SRV/resource)
- Login if required
- Get the API Key
- The API Key is retrieved by the REST API from the environment variable `API_KEY`
- Check environment variables declared for your application using the command: `cf env sapio-scp-<username>`
- Setup the API Key using the command: `cf set-env sapio-scp-<username> API_KEY your-key`
- Use `cf restage sapio-scp-<username>` to ensure that your application will take into account the new environment variables

## Step 6: Deploy the frontend
- Navigate to frontend folder
- Build the frontend running the following commands:
    - `npm install`
    - `npm run build`
- Update the `env.config.js` file to declare your API endpoint
    - use `cf apps` to find the API url
- Edit the manifest file: update the application name
- Execute the following command: `cf push -f cf/manifest.yml`

The `manifest.yml` describe how your application should be deployed by Cloud Foundry. It provides the following application to SCP: where to find the application, how much memory and disk is required by the application, the buildpack, route information, the required services,... More information can be found [here](https://docs.cloudfoundry.org/devguide/deploy-apps/manifest.html).
