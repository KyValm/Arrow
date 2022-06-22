# Step One- Fill out the UNIT_TWO_REPO_NAME and GITHUB_USERNAME

# Step Two - configure your shell to always have these variables.
# For OSX / Linux
# Copy and paste ALL of the properties below into your .bash_profile in your home directly

# For Windows
# Copy and paste ALL of the properties below into your .bashrc file in your home directory


# Fill out the following values
# The path of your repo on github.  Don't but the whole URL, just the part after github.com/
export LBC_REPO_NAME=KenzieAcademy-SoftwareEngineering/ata-lbc-project-Arrow

# Do not modify the rest of these unless you have been instructed to do so.
export LBC_PROJECT_NAME=unitproject4
export LBC_PIPELINE_STACK=$LBC_PROJECT_NAME-$GITHUB_USERNAME
export LBC_ARTIFACT_BUCKET=$LBC_PROJECT_NAME-$GITHUB_USERNAME-artifacts
export LBC_DEPLOY_STACK=$LBC_PROJECT_NAME-$GITHUB_USERNAME-application
export LBC_APPLICATION_NAME=$LBC_PROJECT_NAME-$GITHUB_USERNAME-application
export LBC_ENVIRONMENT_NAME=$LBC_PROJECT_NAME-$GITHUB_USERNAME-environment-dev