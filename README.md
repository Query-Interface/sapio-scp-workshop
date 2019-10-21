# SAP.iO workshop to discover SAP Cloud Platform

## Goal

Demonstrate how an integration with S/4 HANA Cloud could be developed leveraging the SAP Cloud Platform capabilities. You will learn the following:
- Deploy applications to SAP Cloud Platform Cloud Foundry
- Basic Knowledge of SCP Cockpit and CF CLI
- SAP API Hub
- Discover services in CF Marketplace, deploy services and consume services


## Prerequisites

### Java JDK

 - Make sure JDK 8 or JDK 11 is installed on your machine.
 - Install any IDE with Maven support (for example Eclipse, Intellij Idea or VS Code with Java plugins).

### NPM
 - Install [nodejs](https://nodejs.org/en/)

### GIT

Make sure you have git installed on your machine or install it:

- For Windows: if you do not have git installed yet, install [Git Bash](https://gitforwindows.org/), then you can skip the next step. Alternatively, you can activate the WSL (Windows Subsystem for Linux) feature and then install one of the distribution available on the windows store ([see an example with Ubuntu](https://linuxhint.com/install_ubuntu_windows_10_wsl/))

- For Mac: choose one of the options described [here](https://git-scm.com/book/en/v1/Getting-Started-Installing-Git#Installing-on-Mac).


### Cloud Foundry CLI

Install the Cloud Foundry Command Line Interface (CLI) by following [this guide](https://github.com/cloudfoundry/cli) .

### SAP Cloud Platform Account (Cloud Foundry)

For the training you should be able to deploy a test application to Cloud Foundry. To do this you need a space inside Cloud Foundry in which you can deploy the application.

- In case you do not have access to a Cloud Foundry space yet, please request it in SAP Cloud Platform Trial Cockpit:

  1. Go to the [SAP Cloud Platform Cockpit](https://cockpit.hanatrial.ondemand.com/#/home/welcome) and request a Trial account by clicking the "Cloud Foundry Trial" tile.
  
  2. Login to Cloud Foundry via:
   `cf login -a https://api.cf.eu10.hana.ondemand.com`
      
  3. Check that you can execute the command `cf apps` without error message.

## Credits

- Source code related to Feature Flags service in the REST Service comes from the following repository https://github.com/SAP/cloud-cf-feature-flags-sample

## Other resources
  

