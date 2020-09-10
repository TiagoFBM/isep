#!/usr/bin/env bash

#REM set the class path,
#REM assumes the build was executed with maven copy-dependencies
export BASE_CP=base.app.smm/target/base.app.smm-1.3.0-SNAPSHOT.jar:base.app.smm/target/dependency/*;

#REM call the java VM, e.g,
java -cp $BASE_CP fabrica.smm.BaseSMM