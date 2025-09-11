@call cd ..

@rem Downloading dependencies
@if not exist utils (
	@echo Cloning git repo for project utils
    @call git clone --branch 2.0-SNAPSHOT --single-branch https://github.com/Pierre-Emmanuel41/utils
) else ( 
	@call cd utils

	@rem Pulling latest changes
	@call git pull
)

@if not exist communication (
	@echo Cloning git repo for project communication
    @call git clone --branch 2.0-SNAPSHOT --single-branch https://github.com/Pierre-Emmanuel41/communication
) else (
	@call cd communication

	@echo Pulling latest changes for project protocol
	@call git pull
)

@if not exist protocol (
	@echo Cloning git repo for project protocol
    @call git clone --branch 1.0-SNAPSHOT --single-branch https://github.com/Pierre-Emmanuel41/protocol
) else ( 
	@call cd protocol

	@echo Pulling latest changes for project protocol
	@call git pull
)

@rem Building dependencies
@echo Building project utils
@call cd ../utils
@call mvn clean package install

@echo Building project communication
@call cd ../communication
@call mvn clean package install

@echo Building project protocol
@call cd ../protocol
@call mvn clean package install

@echo Building project messenger
@call cd ../messenger
@call mvn clean package install