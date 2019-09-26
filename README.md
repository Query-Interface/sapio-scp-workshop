# SAP.iO workshop to discover SAP Cloud Platform

## Prerequisites

### Java JDK

 - Make sure JDK 8 or JDK 11 is installed on your machine.
 - Install any IDE with Maven support (Eclipse, Intellij Idea or VS Code with Java plugins).


### GIT

Make sure you have git installed on your machine or install it:

- For Windows: If you do not have git installed yet, install [Git Bash](https://gitforwindows.org/), then you can skip the next step. Alternatively, you can activate the WSL (Windows Subsystem for Linux) feature and then install one of the distribution available on the windows store ([see an example with Ubuntu](https://linuxhint.com/install_ubuntu_windows_10_wsl/))

- For Mac: Choose one of the options described [here](https://git-scm.com/book/en/v1/Getting-Started-Installing-Git#Installing-on-Mac).


### Cloud Foundry CLI

Install the Cloud Foundry Command Line Interface (CLI) by following [this guide](https://github.com/cloudfoundry/cli) .

### Cloud Foundry Account

For the training you should be able to deploy a test application to Cloud Foundry. To do this you need a space inside Cloud Foundry in which you can deploy the application.

- In case you do not have access to a Cloud Foundry space yet, please request a Cloud foundry Trial account:

  1. Go to the [Cloud Cockpit](https://cockpit.hanatrial.ondemand.com/#/home/welcome) and request a Trial account by clicking the "Cloud Foundry Trial" tile.
  
  2. Login to Cloud Foundry via:
   `cf login -a https://api.cf.eu10.hana.ondemand.com`
      
  3. Check that you can execute the command `cf apps` without error message.
  
### Optionally, install Docker
